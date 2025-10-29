/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.IstanzaTemplateQuadroApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.IstanzaTemplateQuadroApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.IstanzeApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.TemplateQuadroApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.IstanzaTemplateFoDTO;
import it.csi.scriva.scrivafoweb.dto.QuadroFoDTO;
import it.csi.scriva.scrivafoweb.dto.TemplateFoDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Istanza template quadro api service.
 */
@Component
public class IstanzaTemplateQuadroApiServiceImpl extends AbstractApiServiceImpl implements IstanzaTemplateQuadroApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private IstanzaTemplateQuadroApiServiceHelper istanzaTemplateQuadroApiServiceHelper;

    @Autowired
    private IstanzeApiServiceHelper istanzeApiServiceHelper;

    @Autowired
    private TemplateQuadroApiServiceHelper templateQuadroApiServiceHelper;

    /**
     * Gets istanza template quadro by id istanza.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanza template quadro by id istanza
     */
    @Override
    public Response getIstanzaTemplateQuadroByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        List<IstanzaTemplateFoDTO> list = new ArrayList<>();
        List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroList = istanzaTemplateQuadroApiServiceHelper.getIstanzaTemplateQuadroByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        //// SCRIVA 1620/1625 sino a  questo momento i risultati della query venivano considerati a prescindere dalla visibilità del quadro per componente app richiedente.
        /// questo causava un errore randomico quando veniva preso il primo elemento della lista per estrarre i dati generali del template. Se questi non era visibile per quella  componente 
        ///  la query successiva dava errore.
        List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroFilteredList = filtraPerIndVisibile( istanzaTemplateQuadroList,  httpHeaders); 
        List<IstanzaExtendedDTO> istanze = istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        if (istanze == null || istanze.isEmpty()) {
            String errorMessage = "";
            ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
            logError(className, err);
            return Response.serverError().entity(err).status(404).build();
        }
        IstanzaTemplateFoDTO istanzaTemplateFo = new IstanzaTemplateFoDTO();
        istanzaTemplateFo.setIstanza(istanze.get(0));
        TemplateFoDTO templateFo = null;
        //for (IstanzaTemplateQuadroExtendedDTO istanzaTemplateQuadro : istanzaTemplateQuadroList) {
        if (istanzaTemplateQuadroFilteredList!=null && !istanzaTemplateQuadroFilteredList.isEmpty()) {
            IstanzaTemplateQuadroExtendedDTO istanzaTemplateQuadro = istanzaTemplateQuadroFilteredList.get(0);
            Long idTemplateQuadro = istanzaTemplateQuadro.getTemplateQuadro().getIdTemplateQuadro();
            List<TemplateQuadroExtendedDTO> templateQuadroList = templateQuadroApiServiceHelper.getTemplateQuadriByIdTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTemplateQuadro);
            if (!templateQuadroList.isEmpty()) {
                TemplateQuadroExtendedDTO templateQuadro = templateQuadroList.get(0);
                //if (templateFo == null) {
                    templateFo = getTemplateFo(templateQuadro);
                //}
                templateFo.setQuadri(getQuadriFoFromTemplateQuadro(istanzaTemplateQuadroList, templateQuadroList, componenteApplicativa(httpHeaders)));
                istanzaTemplateFo.setTemplate(templateFo);
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                logError(className, err);
                return Response.serverError().entity(err).status(404).build();
            }
        }

        list.add(istanzaTemplateFo);

        return getResponseWithSharedHeaders(list, istanzaTemplateQuadroApiServiceHelper);
    }

    /**
     * Gets istanza template quadro.
     *
     * @param idIstanza        the id istanza
     * @param idTemplateQuadro the id template quadro
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the istanza template quadro
     */
    @Override
    public Response getIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]");
        List<IstanzaTemplateFoDTO> list = new ArrayList<>();
        List<TemplateQuadroExtendedDTO> templateQuadroList = templateQuadroApiServiceHelper.getTemplateQuadriByIdTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTemplateQuadro);
        List<IstanzaExtendedDTO> istanze = istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        if (istanze == null || templateQuadroList == null || istanze.isEmpty() || templateQuadroList.isEmpty()) {
            String errorMessage = "";
            ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
            logError(className, err);
            return Response.serverError().entity(err).status(404).build();
        }
        List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroList = istanzaTemplateQuadroApiServiceHelper.getIstanzaTemplateQuadroByPK(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, idTemplateQuadro);
        IstanzaTemplateFoDTO istanzaTemplateFo = getIstanzaTemplateFo(templateQuadroList, istanze, istanzaTemplateQuadroList);
        list.add(istanzaTemplateFo);
        return getResponseWithSharedHeaders(list, istanzaTemplateQuadroApiServiceHelper);
    }

    /**
     * Gets template quadri by code template and id istanza.
     *
     * @param codeTemplate    the code template
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the template quadri by code template and id istanza
     */
    @Override
    public Response getTemplateQuadriByCodeTemplateAndIdIstanza(String codeTemplate, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codeTemplate [" + codeTemplate + "]");
        List<IstanzaTemplateFoDTO> list = new ArrayList<>();
        List<TemplateQuadroExtendedDTO> templateQuadroList = templateQuadroApiServiceHelper.getTemplateQuadriByCodeTemplate(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeTemplate);
        List<IstanzaExtendedDTO> istanze = istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        if (istanze == null || templateQuadroList == null || istanze.isEmpty() || templateQuadroList.isEmpty()) {
            String errorMessage = "";
            ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
            logError(className, err);
            return Response.serverError().entity(err).status(404).build();
        }
        List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroList = istanzaTemplateQuadroApiServiceHelper.getIstanzaTemplateQuadroByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        IstanzaTemplateFoDTO istanzaTemplateFo = getIstanzaTemplateFo(templateQuadroList, istanze, istanzaTemplateQuadroList);
        list.add(istanzaTemplateFo);
        return getResponseWithSharedHeaders(list, istanzaTemplateQuadroApiServiceHelper);
    }

    /**
     * Save istanza template quadro response.
     *
     * @param istanzaTemplateQuadro the istanza template quadro
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @Override
    public Response saveIstanzaTemplateQuadro(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, istanzaTemplateQuadro);
        UserInfo user = getSessionUser(httpRequest);
        istanzaTemplateQuadro.setGestAttoreIns(user.getCodFisc());
        return getResponseWithSharedHeaders(istanzaTemplateQuadroApiServiceHelper.saveIstanzaTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaTemplateQuadro), istanzaTemplateQuadroApiServiceHelper, 201);
    }

    /**
     * Update istanza template quadro response.
     *
     * @param istanzaTemplateQuadro the istanza template quadro
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @Override
    public Response updateIstanzaTemplateQuadro(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, istanzaTemplateQuadro);
        UserInfo user = getSessionUser(httpRequest);
        istanzaTemplateQuadro.setGestAttoreUpd(user.getCodFisc());
        return getResponseWithSharedHeaders(istanzaTemplateQuadroApiServiceHelper.updateIstanzaTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaTemplateQuadro), istanzaTemplateQuadroApiServiceHelper);
    }

    /**
     * Delete istanza template quadro response.
     *
     * @param idIstanza        the id istanza
     * @param idTemplateQuadro the id template quadro
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response deleteIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]");
        istanzaTemplateQuadroApiServiceHelper.deleteIstanzaTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, idTemplateQuadro);
        return Response.noContent().build();
    }

    /**
     * Gets istanza template fo.
     *
     * @param templateQuadroList        the template quadro list
     * @param istanze                   the istanze
     * @param istanzaTemplateQuadroList the istanza template quadro list
     * @return the istanza template fo
     */
    private IstanzaTemplateFoDTO getIstanzaTemplateFo(List<TemplateQuadroExtendedDTO> templateQuadroList, List<IstanzaExtendedDTO> istanze, List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroList) {
        logBegin(className);
        IstanzaTemplateFoDTO istanzaTemplateFo = new IstanzaTemplateFoDTO();
        istanzaTemplateFo.setIstanza(istanze.get(0));
        TemplateQuadroExtendedDTO templateQuadro = templateQuadroList.get(0);
        TemplateFoDTO templateFo = getTemplateFo(templateQuadro);
        templateFo.setQuadri(getQuadriFoFromTemplateQuadro(istanzaTemplateQuadroList, templateQuadroList, null));
        istanzaTemplateFo.setTemplate(templateFo);
        logEnd(className);
        return istanzaTemplateFo;
    }

    /**
     * Gets template fo.
     *
     * @param templateQuadro the template quadro
     * @return the template fo
     */
    private TemplateFoDTO getTemplateFo(TemplateQuadroExtendedDTO templateQuadro) {
        logBegin(className);
        TemplateFoDTO templateFo = new TemplateFoDTO();
        TemplateExtendedDTO template = templateQuadro.getTemplate();
        templateFo.setCodTemplate(template.getCodTemplate());
        templateFo.setDesTemplate(template.getDesTemplate());
        templateFo.setDataInizioValidita(template.getDataInizioValidita());
        templateFo.setDataCessazione(template.getDataCessazione());
        templateFo.setIdTemplate(template.getIdTemplate());
        templateFo.setJsonConfguraTemplate(template.getJsonConfguraTemplate());
        templateFo.setAdempimento(template.getAdempimento());
        logEnd(className);
        return templateFo;
    }

    /**
     * Gets quadri fo from template quadro.
     *
     * @param istanzaTemplateQuadroList the istanza template quadro list
     * @param templateQuadroList        the template quadro list
     * @return the quadri fo from template quadro
     */
    private List<QuadroFoDTO> getQuadriFoFromTemplateQuadro(List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroList, List<TemplateQuadroExtendedDTO> templateQuadroList, String componente) {
        logBegin(className);
        List<QuadroFoDTO> quadri = new ArrayList<>();


        for (TemplateQuadroExtendedDTO templateQuadro : templateQuadroList) {
            for (IstanzaTemplateQuadroExtendedDTO istanzaTemplateQuadro : istanzaTemplateQuadroList) {
                QuadroFoDTO quadroFo = new QuadroFoDTO();
                QuadroExtendedDTO quadro = istanzaTemplateQuadro.getTemplateQuadro().getQuadro();
                quadroFo.setFlgTipoGestione(quadro.getFlgTipoGestione());
                quadroFo.setIdQuadro(quadro.getIdQuadro());
                quadroFo.setCodQuadro(quadro.getCodQuadro());
                quadroFo.setDesQuadro(quadro.getDesQuadro());
                quadroFo.setOrdinamentoTemplateQuadro(istanzaTemplateQuadro.getTemplateQuadro().getOrdinamentoTemplateQuadro());
                quadroFo.setTipoQuadro(quadro.getTipoQuadro());
                quadroFo.setJsonConfiguraQuadro(quadro.getJsonConfiguraQuadro());
                quadroFo.setIdTemplateQuadro(istanzaTemplateQuadro.getTemplateQuadro().getIdTemplateQuadro());
                quadroFo.setJsonConfiguraRiepilogo(quadro.getJsonConfiguraRiepilogo());
                quadroFo.setJsonVestizioneQuadro(istanzaTemplateQuadro.getTemplateQuadro().getJsonVestizioneQuadro());
                quadroFo.setJsonDataQuadro(istanzaTemplateQuadro.getJsonDataQuadro());
                if(StringUtils.isNotEmpty(componente)){ // verifica se sono state passate le info sulla visibilità del quadro in base al componente applicativo e se il quadro è visibile lo aggiunge alla lista
                    
                    String indVisibile = istanzaTemplateQuadro.getTemplateQuadro().getIndVisibile();
                            
                    if (StringUtils.isNotEmpty(indVisibile) && StringUtils.contains(indVisibile, componente)){
                        quadri.add(quadroFo);
                    }
                }
                else{
                    quadri.add(quadroFo);
                }
                
            }
        }
        logEnd(className);
        return quadri;

    }

    /**
     * Estrae dalla lista dei quadri solo quelli visibili per la componente app 
     * dell'attore in linea // SCRIVA 1620/1625
     *
     * @param istanzaTemplateQuadroList the istanza template quadro list
     * @param httpHeaders        the template quadro list
     * @return the istanza template quadro list filtered
     * 
     */

    private List<IstanzaTemplateQuadroExtendedDTO> filtraPerIndVisibile(List<IstanzaTemplateQuadroExtendedDTO> istanzaTemplateQuadroList, HttpHeaders httpHeaders) {
    String componente = componenteApplicativa(httpHeaders);

    return istanzaTemplateQuadroList.stream()
            .filter(istanza -> istanza.getTemplateQuadro() != null
                    && istanza.getTemplateQuadro().getIndVisibile() != null
                    && istanza.getTemplateQuadro().getIndVisibile().contains(componente))
            .collect(Collectors.toList());
}


}
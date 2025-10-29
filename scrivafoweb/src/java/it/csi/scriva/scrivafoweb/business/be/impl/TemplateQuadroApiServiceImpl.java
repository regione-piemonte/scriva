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

import it.csi.scriva.scrivafoweb.business.be.TemplateQuadroApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.TemplateQuadroApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.QuadroFoDTO;
import it.csi.scriva.scrivafoweb.dto.TemplateFoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface Template quadro api.
 *
 * @author CSI PIEMONTE
 */
@Component
public class TemplateQuadroApiServiceImpl extends AbstractApiServiceImpl implements TemplateQuadroApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TemplateQuadroApiServiceHelper templateQuadroApiServiceHelper;

    /**
     * Gets template quadri by code adempimento.
     *
     * @param codeAdempimento codeAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     httpRequest
     * @return Response template quadri by code adempimento
     * @throws GenericException the generic exception
     */
    @Override
    public Response getTemplateQuadriByCodeAdempimento(String codeAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) codeAdempimento);
        return getResponseTemplateQuadri(templateQuadroApiServiceHelper.getTemplateQuadriByCodeAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeAdempimento));
    }


    /**
     * Gets template quadri by code template.
     *
     * @param codeTemplate    codeTemplate
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     httpRequest
     * @return Response template quadri by code template
     * @throws GenericException the generic exception
     */
    @Override
    public Response getTemplateQuadriByCodeTemplate(String codeTemplate, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) codeTemplate);
        return getResponseTemplateQuadri(templateQuadroApiServiceHelper.getTemplateQuadriByCodeTemplate(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeTemplate));
    }

    /**
     * Gets template quadri by id template quadro.
     *
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      httpRequest
     * @return Response template quadri by id template quadro
     * @throws GenericException the generic exception
     */
    @Override
    public Response getTemplateQuadriByIdTemplateQuadro(Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idTemplateQuadro);
        return getResponseTemplateQuadri(templateQuadroApiServiceHelper.getTemplateQuadriByIdTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTemplateQuadro));
    }

    /**
     * Gets template fo.
     *
     * @param templateQuadroList the template quadro list
     * @return the template fo
     */
    private TemplateFoDTO getTemplateFo(List<TemplateQuadroExtendedDTO> templateQuadroList) {
        logBegin(className);
        TemplateExtendedDTO template = templateQuadroList.get(0).getTemplate();
        TemplateFoDTO templateFo = new TemplateFoDTO();
        templateFo.setCodTemplate(template.getCodTemplate());
        templateFo.setDesTemplate(template.getDesTemplate());
        templateFo.setDataInizioValidita(template.getDataInizioValidita());
        templateFo.setDataCessazione(template.getDataCessazione());
        templateFo.setIdTemplate(template.getIdTemplate());
        templateFo.setJsonConfguraTemplate(template.getJsonConfguraTemplate());
        templateFo.setAdempimento(template.getAdempimento());
        templateFo.setQuadri(getQuadriFoFromTemplateQuadro(templateQuadroList));
        logEnd(className);
        return templateFo;
    }

    /**
     * Gets quadri fo from template quadro.
     *
     * @param templateQuadroList the template quadro list
     * @return the quadri fo from template quadro
     */
    private List<QuadroFoDTO> getQuadriFoFromTemplateQuadro(List<TemplateQuadroExtendedDTO> templateQuadroList) {
        logBegin(className);
        List<QuadroFoDTO> quadroFoList = new ArrayList<>();
        for (TemplateQuadroExtendedDTO templateQuadro : templateQuadroList) {
            QuadroExtendedDTO quadro = templateQuadro.getQuadro();
            QuadroFoDTO quadroFo = new QuadroFoDTO();
            quadroFo.setFlgTipoGestione(quadro.getFlgTipoGestione());
            quadroFo.setIdQuadro(quadro.getIdQuadro());
            quadroFo.setCodQuadro(quadro.getCodQuadro());
            quadroFo.setDesQuadro(quadro.getDesQuadro());
            quadroFo.setOrdinamentoTemplateQuadro(templateQuadro.getOrdinamentoTemplateQuadro());
            quadroFo.setTipoQuadro(quadro.getTipoQuadro());
            quadroFo.setJsonConfiguraQuadro(quadro.getJsonConfiguraQuadro());
            quadroFo.setIdTemplateQuadro(templateQuadro.getIdTemplateQuadro());
            quadroFo.setJsonConfiguraRiepilogo(quadro.getJsonConfiguraRiepilogo());
            quadroFo.setJsonVestizioneQuadro(templateQuadro.getJsonVestizioneQuadro());
            quadroFoList.add(quadroFo);
        }
        logEnd(className);
        return quadroFoList;
    }

    /**
     * Gets response template quadri.
     *
     * @param templateQuadroList the template quadro list
     * @return the response template quadri
     */
    private Response getResponseTemplateQuadri(List<TemplateQuadroExtendedDTO> templateQuadroList) {
        if (CollectionUtils.isEmpty(templateQuadroList)) {
            String errorMessage = "";
            ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
            logError(className, err);
            return Response.serverError().entity(err).status(404).build();
        }
        List<TemplateFoDTO> templateFoList = new ArrayList<>();
        templateFoList.add(getTemplateFo(templateQuadroList));
        return getResponseWithSharedHeaders(className, templateFoList, templateQuadroApiServiceHelper, 200);
    }
}
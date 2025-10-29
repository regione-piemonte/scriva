/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.IstanzaTemplateQuadroApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaTemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaTemplateQuadroDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaTemplateQuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Istanza template quadro api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IstanzaTemplateQuadroApiServiceImpl extends BaseApiServiceImpl implements IstanzaTemplateQuadroApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private IstanzaTemplateQuadroDAO istanzaTemplateQuadroDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private TemplateQuadroDAO templateQuadroDAO;

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadIstanzaTemplateQuadroByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        Response responseList = getResponseList(istanzaTemplateQuadroDAO.loadIstanzaTemplateQuadroByIdIstanza(idIstanza), className);
        return responseList;
    }

    /**
     * @param idIstanza        idIstanza
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]");
        Response response = setAttoreRight(httpHeaders, idIstanza); //COMMENTARE NEL CASO NON RISOLVA IL PROBLEMA DELL'ERRORE RANDOM E DECOMMENTARE RIGA 77
        //Response response = setAttoreRight2(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(istanzaTemplateQuadroDAO.loadIstanzaTemplateQuadroByPK(idIstanza, idTemplateQuadro), className);
    }

    /**
     * @param istanzaTemplateQuadro IstanzaTemplateQuadroDTO
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response saveIstanzaTemplateQuadro(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaTemplateQuadro);
        Response response = setAttoreRight(httpHeaders, istanzaTemplateQuadro.getIdIstanza());
        if (response != null) {
            return response;
        }

        ErrorDTO error = this.validateDTO(istanzaTemplateQuadro);
        if (null != error) {
            logError(className, error);
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(istanzaTemplateQuadro.getGestAttoreUpd())) {
            istanzaTemplateQuadro.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        }

        this.updateJsonDataIstanza(istanzaTemplateQuadro);

      
        updateIstanzaPraticaTimestampAttore(istanzaTemplateQuadro.getIdIstanza(), attoreScriva);
        // Se il quadro non è del tipo QDR_CONFIG
        if (istanzaTemplateQuadro.getIdTemplateQuadro() != null && istanzaTemplateQuadro.getIdTemplateQuadro() >= 0) {
            IstanzaTemplateQuadroExtendedDTO istanzaTemplateQuadroExtended = istanzaTemplateQuadroDAO.loadIstanzaTemplateQuadroByPK(istanzaTemplateQuadro.getIdIstanza(), istanzaTemplateQuadro.getIdTemplateQuadro()).get(0);
            istanzaTemplateQuadro.setGestUID(istanzaTemplateQuadroExtended.getGestUID());
        }
        logInfo(className, "istanzaTemplateQuadro : \n" + istanzaTemplateQuadro);
        Response finalResp = getResponseSave(istanzaTemplateQuadro, className, "/istanze-template-quadri/id-istanza/" + istanzaTemplateQuadro.getIdIstanza() + "/id-template-quadro/" + istanzaTemplateQuadro.getIdTemplateQuadro());
        updateIstanzaPraticaTimestampAttore(istanzaTemplateQuadro.getIdIstanza(), attoreScriva);
        return finalResp;
    }

    /**
     * @param istanzaTemplateQuadro IstanzaTemplateQuadroDTO
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response
     */
    @Override
    @Transactional
    public Response updateIstanzaTemplateQuadro(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaTemplateQuadro);
        Response response = setAttoreRight(httpHeaders, istanzaTemplateQuadro.getIdIstanza());
        if (response != null) {
            return response;
        }
        ErrorDTO error = this.validateDTO(istanzaTemplateQuadro);
        if (null != error) {
            logError(className, error);
            return getResponseError(className, error);
        }

        if (StringUtils.isBlank(istanzaTemplateQuadro.getGestAttoreUpd())) {
            istanzaTemplateQuadro.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        }
        this.updateJsonDataIstanza(istanzaTemplateQuadro);
        Response finalResp = getResponseSaveUpdate(istanzaTemplateQuadro, className);
        updateIstanzaPraticaTimestampAttore(istanzaTemplateQuadro.getIdIstanza(), attoreScriva);
        return finalResp;
    }

    /**
     * @param idIstanza        idIstanza
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]");
        /*
        Integer res = istanzaTemplateQuadroDAO.deleteIstanzaTemplateQuadro(idIstanza, idTemplateQuadro);
        if (res == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] ERROR : idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]\n" + error);
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] END");
            return Response.serverError().entity(error).status(500).build();
        } else if (res < 1) {
            ErrorDTO error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento;  causa: elemento non trovato", null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] ERROR : idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]\n" + error);
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] END");
            return Response.serverError().entity(error).status(404).build();
        } else {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] END");
            return Response.noContent().build();
        }
         */
        return null;
    }

    private void updateJsonDataIstanza(IstanzaTemplateQuadroDTO istanzaTemplateQuadro) {
        String codiceTipoQuadro;
        if ((istanzaTemplateQuadro.getIdTemplateQuadro() == null && StringUtils.isNotBlank(istanzaTemplateQuadro.getCodTipoQuadro())) || istanzaTemplateQuadro.getIdTemplateQuadro() == -1L) {
            codiceTipoQuadro = StringUtils.isNotBlank(istanzaTemplateQuadro.getCodTipoQuadro()) ? istanzaTemplateQuadro.getCodTipoQuadro() : "QDR_CONFIG";
            updateJsonDataQuadroIstanza(istanzaTemplateQuadro, codiceTipoQuadro);
        } else {
            List<TemplateQuadroExtendedDTO> templateQuadroE = templateQuadroDAO.loadTemplateQuadroById(istanzaTemplateQuadro.getIdTemplateQuadro(), null);
            if (null != templateQuadroE && !templateQuadroE.isEmpty()) {
                TemplateQuadroExtendedDTO tqe = templateQuadroE.get(0);
                codiceTipoQuadro = tqe.getQuadro().getTipoQuadro().getCodiceTipoQuadro();
                updateJsonDataQuadroIstanza(istanzaTemplateQuadro, codiceTipoQuadro);
            }
        }
    }

    private void updateJsonDataQuadroIstanza(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, String codiceTipoQuadro) {
        List<IstanzaExtendedDTO> istanza = istanzaDAO.loadIstanza(istanzaTemplateQuadro.getIdIstanza());
        if (null != istanza && !istanza.isEmpty()) {
            JSONObject jsonDataIstanza = new JSONObject(istanza.get(0).getJsonData());
            JSONObject jsonDataQuadro = new JSONObject(istanzaTemplateQuadro.getJsonDataQuadro());
            JSONObject jsonDataIstanzaExist = jsonDataIstanza.optJSONObject(codiceTipoQuadro);
            JSONObject newJson = null;
            if (jsonDataIstanzaExist == null || jsonDataIstanzaExist.isEmpty()) {
                newJson = jsonDataIstanza.put(codiceTipoQuadro, jsonDataQuadro);
            } else {
                JSONObject merged = new JSONObject(jsonDataIstanzaExist, JSONObject.getNames(jsonDataIstanzaExist));
                if (!jsonDataQuadro.isEmpty()) {
                    for (String key : JSONObject.getNames(jsonDataQuadro)) {
                        merged.put(key, jsonDataQuadro.get(key));
                    }
                }
                newJson = jsonDataIstanza.put(codiceTipoQuadro, merged);
            }
            if (null != newJson) {
                IstanzaDTO dto = new IstanzaDTO();
                dto.setIdIstanza(istanzaTemplateQuadro.getIdIstanza());
                dto.setJsonData(newJson.toString());
                dto.setGestAttoreUpd(StringUtils.isBlank(istanzaTemplateQuadro.getGestAttoreUpd()) ? "SYSTEM" : istanzaTemplateQuadro.getGestAttoreUpd());
                istanzaDAO.updateJsonDataIstanza(dto);
            }
        }
    }

    private ErrorDTO validateDTO(IstanzaTemplateQuadroDTO dto) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        if (dto.getIdIstanza() == null) {
            details.put("id_istanza", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (dto.getIdTemplateQuadro() == null && StringUtils.isBlank(dto.getCodTipoQuadro())) {
            details.put("template_quadro-cod_tipo_quadro", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }
        return error;
    }

}
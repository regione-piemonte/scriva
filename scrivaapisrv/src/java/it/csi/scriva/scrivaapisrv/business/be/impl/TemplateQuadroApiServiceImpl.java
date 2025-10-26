/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.TemplateQuadroApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.TemplateQuadroApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.QuadroFoDTO;
import it.csi.scriva.scrivaapisrv.dto.TemplateFoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateQuadroApiServiceImpl extends AbstractApiServiceImpl implements TemplateQuadroApi {

    @Autowired
    private TemplateQuadroApiServiceHelper templateQuadroApiServiceHelper;

    @Override
    public Response getTemplateQuadriByCodeAdempimento(String codeAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeAdempimento] BEGIN");
        LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeAdempimento] Parametro in input codeAdempimento [" + codeAdempimento + "]");
        List<TemplateFoDTO> list = new ArrayList<>();
        try {
            List<TemplateQuadroExtendedDTO> tqeDTOs = templateQuadroApiServiceHelper.getTemplateQuadriByCodeAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeAdempimento);

            TemplateFoDTO templateDTO = new TemplateFoDTO();
            if (!tqeDTOs.isEmpty()) {
                TemplateQuadroExtendedDTO tqext = tqeDTOs.get(0);

                TemplateExtendedDTO te = tqext.getTemplate();
                templateDTO.setCodTemplate(te.getCodTemplate());
                templateDTO.setDesTemplate(te.getDesTemplate());
                templateDTO.setDataInizioValidita(te.getDataInizioValidita());
                ;
                templateDTO.setDataCessazione(te.getDataCessazione());
                templateDTO.setIdTemplate(te.getIdTemplate());
                templateDTO.setJsonConfguraTemplate(te.getJsonConfguraTemplate());
                templateDTO.setAdempimento(te.getAdempimento());
                List<QuadroFoDTO> quadri = new ArrayList<>();

                for (TemplateQuadroExtendedDTO temp : tqeDTOs) {
                    QuadroExtendedDTO q = temp.getQuadro();
                    QuadroFoDTO quadro = new QuadroFoDTO();
                    quadro.setFlgTipoGestione(q.getFlgTipoGestione());
                    quadro.setIdQuadro(q.getIdQuadro());
                    quadro.setNumVersione(q.getNumVersione());
                    quadro.setOrdinamentoTemplateQuadro(temp.getOrdinamentoTemplateQuadro());
                    quadro.setTipoQuadro(q.getTipoQuadro());
                    quadro.setJsonConfiguraQuadro(q.getJsonConfiguraQuadro());
                    quadro.setIdTemplateQuadro(temp.getIdTemplateQuadro());
                    quadro.setJsonConfiguraRiepilogo(q.getJsonConfiguraRiepilogo());

                    quadri.add(quadro);
                }
                templateDTO.setQuadri(quadri);
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeAdempimento] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }

            list.add(templateDTO);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeAdempimento] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();

    }

    @Override
    public Response getTemplateQuadriByCodeTemplate(String codeTemplate, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplate] BEGIN");
        LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplate] Parametro in input codeTemplate [" + codeTemplate + "]");
        List<TemplateFoDTO> list = new ArrayList<>();
        try {
            List<TemplateQuadroExtendedDTO> tqeDTOs = templateQuadroApiServiceHelper.getTemplateQuadriByCodeTemplate(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeTemplate);

            TemplateFoDTO templateDTO = new TemplateFoDTO();
            if (!tqeDTOs.isEmpty()) {
                TemplateQuadroExtendedDTO tqext = tqeDTOs.get(0);

                TemplateExtendedDTO te = tqext.getTemplate();
                templateDTO.setCodTemplate(te.getCodTemplate());
                templateDTO.setDesTemplate(te.getDesTemplate());
                templateDTO.setDataInizioValidita(te.getDataInizioValidita());
                ;
                templateDTO.setDataCessazione(te.getDataCessazione());
                templateDTO.setIdTemplate(te.getIdTemplate());
                templateDTO.setJsonConfguraTemplate(te.getJsonConfguraTemplate());
                templateDTO.setAdempimento(te.getAdempimento());
                List<QuadroFoDTO> quadri = new ArrayList<>();

                for (TemplateQuadroExtendedDTO temp : tqeDTOs) {
                    QuadroExtendedDTO q = temp.getQuadro();
                    QuadroFoDTO quadro = new QuadroFoDTO();
                    quadro.setFlgTipoGestione(q.getFlgTipoGestione());
                    quadro.setIdQuadro(q.getIdQuadro());
                    quadro.setNumVersione(q.getNumVersione());
                    quadro.setOrdinamentoTemplateQuadro(temp.getOrdinamentoTemplateQuadro());
                    quadro.setTipoQuadro(q.getTipoQuadro());
                    quadro.setJsonConfiguraQuadro(q.getJsonConfiguraQuadro());
                    quadro.setIdTemplateQuadro(temp.getIdTemplateQuadro());
                    quadro.setJsonConfiguraRiepilogo(q.getJsonConfiguraRiepilogo());

                    quadri.add(quadro);
                }
                templateDTO.setQuadri(quadri);
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplate] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }

            list.add(templateDTO);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplate] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplate] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplate] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getTemplateQuadriByIdTemplateQuadro(Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByIdTemplateQuadro] BEGIN");
        LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByIdTemplateQuadro] Parametro in input idTemplateQuadro [" + idTemplateQuadro + "]");
        List<TemplateFoDTO> list = new ArrayList<>();
        try {
            List<TemplateQuadroExtendedDTO> tqeDTOs = templateQuadroApiServiceHelper.getTemplateQuadriByIdTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTemplateQuadro);

            TemplateFoDTO templateDTO = new TemplateFoDTO();
            if (!tqeDTOs.isEmpty()) {
                TemplateQuadroExtendedDTO tqext = tqeDTOs.get(0);

                TemplateExtendedDTO te = tqext.getTemplate();
                templateDTO.setCodTemplate(te.getCodTemplate());
                templateDTO.setDesTemplate(te.getDesTemplate());
                templateDTO.setDataInizioValidita(te.getDataInizioValidita());
                ;
                templateDTO.setDataCessazione(te.getDataCessazione());
                templateDTO.setIdTemplate(te.getIdTemplate());
                templateDTO.setJsonConfguraTemplate(te.getJsonConfguraTemplate());
                templateDTO.setAdempimento(te.getAdempimento());
                List<QuadroFoDTO> quadri = new ArrayList<>();

                for (TemplateQuadroExtendedDTO temp : tqeDTOs) {
                    QuadroExtendedDTO q = temp.getQuadro();
                    QuadroFoDTO quadro = new QuadroFoDTO();
                    quadro.setFlgTipoGestione(q.getFlgTipoGestione());
                    quadro.setIdQuadro(q.getIdQuadro());
                    quadro.setNumVersione(q.getNumVersione());
                    quadro.setOrdinamentoTemplateQuadro(temp.getOrdinamentoTemplateQuadro());
                    quadro.setTipoQuadro(q.getTipoQuadro());
                    quadro.setJsonConfiguraQuadro(q.getJsonConfiguraQuadro());
                    quadro.setIdTemplateQuadro(temp.getIdTemplateQuadro());
                    quadro.setJsonConfiguraRiepilogo(q.getJsonConfiguraRiepilogo());

                    quadri.add(quadro);
                }
                templateDTO.setQuadri(quadri);
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByIdTemplateQuadro] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }

            list.add(templateDTO);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByIdTemplateQuadro] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TemplateQuadroApiServiceImpl::getTemplateQuadriByIdTemplateQuadro] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TemplateQuadroApiServiceImpl::getTemplateQuadriByIdTemplateQuadro] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}
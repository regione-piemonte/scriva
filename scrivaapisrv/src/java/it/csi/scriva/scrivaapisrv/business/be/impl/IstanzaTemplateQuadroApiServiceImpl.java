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

import it.csi.scriva.scrivaapisrv.business.be.IstanzaTemplateQuadroApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.IstanzaTemplateQuadroApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.IstanzeApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.TemplateQuadroApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.IstanzaTemplateFoDTO;
import it.csi.scriva.scrivaapisrv.dto.QuadroFoDTO;
import it.csi.scriva.scrivaapisrv.dto.TemplateFoDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class IstanzaTemplateQuadroApiServiceImpl extends AbstractApiServiceImpl implements IstanzaTemplateQuadroApi {

    @Autowired
    private IstanzaTemplateQuadroApiServiceHelper istanzaTemplateQuadroApiServiceHelper;

    @Autowired
    private IstanzeApiServiceHelper istanzeApiServiceHelper;

    @Autowired
    private TemplateQuadroApiServiceHelper templateQuadroApiServiceHelper;

    @Override
    public Response getIstanzaTemplateQuadroByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] BEGIN");
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] Parametro in input idIstanza [" + idIstanza + "]");
        List<IstanzaTemplateFoDTO> list = new ArrayList<>();
        try {
            List<IstanzaTemplateQuadroExtendedDTO> listTemp = istanzaTemplateQuadroApiServiceHelper.getIstanzaTemplateQuadroByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);

            List<IstanzaExtendedDTO> istanze = istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
            IstanzaTemplateFoDTO dto = new IstanzaTemplateFoDTO();
            if (!istanze.isEmpty()) {
                dto.setIstanza(istanze.get(0));
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }
            TemplateFoDTO templateDTO = null;
            List<QuadroFoDTO> quadri = new ArrayList<>();
            for (IstanzaTemplateQuadroExtendedDTO tempItqe : listTemp) {
                Long idTemplateQuadro = tempItqe.getTemplateQuadro().getIdTemplateQuadro();

                List<TemplateQuadroExtendedDTO> tqeDTOs = templateQuadroApiServiceHelper.getTemplateQuadriByIdTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTemplateQuadro);

                if (!tqeDTOs.isEmpty()) {
                    TemplateQuadroExtendedDTO tqext = tqeDTOs.get(0);
                    if (null == templateDTO) {
                        templateDTO = new TemplateFoDTO();
                        TemplateExtendedDTO te = tqext.getTemplate();
                        templateDTO.setCodTemplate(te.getCodTemplate());
                        templateDTO.setDesTemplate(te.getDesTemplate());
                        templateDTO.setDataInizioValidita(te.getDataInizioValidita());
                        ;
                        templateDTO.setDataCessazione(te.getDataCessazione());
                        templateDTO.setIdTemplate(te.getIdTemplate());
                        templateDTO.setJsonConfguraTemplate(te.getJsonConfguraTemplate());
                        templateDTO.setAdempimento(te.getAdempimento());
                    }
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

                        if (!listTemp.isEmpty()) {
                            listTemp.stream().filter(istanzaTemplateQuadroExtendedDTO -> istanzaTemplateQuadroExtendedDTO.getTemplateQuadro().getQuadro().getIdQuadro().equals(q.getIdQuadro()))
                                    .findAny().ifPresent(istanzaTemplateQuadro -> quadro.setJsonDataQuadro(istanzaTemplateQuadro.getJsonDataQuadro()));
                        }

                        quadri.add(quadro);
                    }
                    templateDTO.setQuadri(quadri);
                    dto.setTemplate(templateDTO);
                } else {
                    String errorMessage = "";
                    ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                    LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] ERROR : " + errorMessage);
                    return Response.serverError().entity(err).status(404).build();
                }
            }
            list.add(dto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getIstanzaTemplateQuadroByIdIstanza] END");
        }
        return getResponseWithSharedHeaders(list, istanzaTemplateQuadroApiServiceHelper);
        //return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response saveIstanzaTemplateQuadro(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::saveIstanzaTemplateQuadro] BEGIN");
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::saveIstanzaTemplateQuadro] Parametro in input istanzaTemplateQuadro :\r\n " + istanzaTemplateQuadro.toString());
        IstanzaTemplateQuadroDTO dto = new IstanzaTemplateQuadroDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaTemplateQuadro.setGestAttoreIns(user.getCodFisc());
        try {
            dto = istanzaTemplateQuadroApiServiceHelper.saveIstanzaTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaTemplateQuadro);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::saveIstanzaTemplateQuadro] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::saveIstanzaTemplateQuadro] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::saveIstanzaTemplateQuadro] END");
        }
        return getResponseWithSharedHeaders(dto, istanzaTemplateQuadroApiServiceHelper, 201);
    }

    @Override
    public Response updateIstanzaTemplateQuadro(IstanzaTemplateQuadroDTO istanzaTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::updateIstanzaTemplateQuadro] BEGIN");
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::updateIstanzaTemplateQuadro] Parametro in input istanzaTemplateQuadro :\r\n " + istanzaTemplateQuadro.toString());
        IstanzaTemplateQuadroDTO dto = new IstanzaTemplateQuadroDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaTemplateQuadro.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = istanzaTemplateQuadroApiServiceHelper.updateIstanzaTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaTemplateQuadro);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::updateIstanzaTemplateQuadro] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::updateIstanzaTemplateQuadro] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::updateIstanzaTemplateQuadro] END");
        }
        return getResponseWithSharedHeaders(dto, istanzaTemplateQuadroApiServiceHelper);
    }

    @Override
    public Response deleteIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] BEGIN");
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] Parametro in input idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]");
        try {
            istanzaTemplateQuadroApiServiceHelper.deleteIstanzaTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, idTemplateQuadro);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::deleteIstanzaTemplateQuadro] END");
        }
        return Response.noContent().build();
    }


    @Override
    public Response getTemplateQuadriByCodeTemplateAndIdIstanza(String codeTemplate, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] BEGIN");
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] Parametro in input codeTemplate [" + codeTemplate + "]");
        List<IstanzaTemplateFoDTO> list = new ArrayList<>();
        try {
            List<TemplateQuadroExtendedDTO> tqeDTOs = templateQuadroApiServiceHelper.getTemplateQuadriByCodeTemplate(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeTemplate);
            List<IstanzaExtendedDTO> istanze = istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
            List<IstanzaTemplateQuadroExtendedDTO> itqeDTOs = istanzaTemplateQuadroApiServiceHelper.getIstanzaTemplateQuadroByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);

            IstanzaTemplateFoDTO dto = new IstanzaTemplateFoDTO();
            if (!istanze.isEmpty()) {
                dto.setIstanza(istanze.get(0));
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }
            if (!tqeDTOs.isEmpty()) {
                TemplateQuadroExtendedDTO tqext = tqeDTOs.get(0);
                TemplateFoDTO templateDTO = new TemplateFoDTO();
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

                    if (!itqeDTOs.isEmpty()) {
                        itqeDTOs.stream().filter(istanzaTemplateQuadroExtendedDTO -> istanzaTemplateQuadroExtendedDTO.getTemplateQuadro().getQuadro().getIdQuadro().equals(q.getIdQuadro()))
                                .findAny().ifPresent(istanzaTemplateQuadro -> quadro.setJsonDataQuadro(istanzaTemplateQuadro.getJsonDataQuadro()));
//                        for (IstanzaTemplateQuadroExtendedDTO itqe : itqeDTOs) {
//                            Long idQuadro = itqe.getTemplateQuadro().getQuadro().getIdQuadro();
//                            if(idQuadro.equals(q.getIdQuadro())) {
//                                quadro.setJsonDataQuadro(itqe.getJsonDataQuadro());
//                                break;
//                            }
//                        }
                    }
                    quadri.add(quadro);
                }
                templateDTO.setQuadri(quadri);
                dto.setTemplate(templateDTO);
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }

            list.add(dto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] END");
        }
        return getResponseWithSharedHeaders(list, istanzaTemplateQuadroApiServiceHelper);
    }

    @Override
    public Response getIstanzaTemplateQuadro(Long idIstanza, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] BEGIN");
        LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] Parametro in input idIstanza [" + idIstanza + "] - idTemplateQuadro [" + idTemplateQuadro + "]");
        List<IstanzaTemplateFoDTO> list = new ArrayList<>();
        try {
            List<TemplateQuadroExtendedDTO> tqeDTOs = templateQuadroApiServiceHelper.getTemplateQuadriByIdTemplateQuadro(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTemplateQuadro);
            List<IstanzaExtendedDTO> istanze = istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
            List<IstanzaTemplateQuadroExtendedDTO> itqeDTOs = istanzaTemplateQuadroApiServiceHelper.getIstanzaTemplateQuadroByPK(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, idTemplateQuadro);

            IstanzaTemplateFoDTO dto = new IstanzaTemplateFoDTO();
            if (!istanze.isEmpty()) {
                dto.setIstanza(istanze.get(0));
            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }
            if (!tqeDTOs.isEmpty()) {
                TemplateQuadroExtendedDTO tqext = tqeDTOs.get(0);
                TemplateFoDTO templateDTO = new TemplateFoDTO();
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

                    if (!itqeDTOs.isEmpty()) {
                        itqeDTOs.stream().filter(istanzaTemplateQuadroExtendedDTO -> istanzaTemplateQuadroExtendedDTO.getTemplateQuadro().getQuadro().getIdQuadro().equals(q.getIdQuadro()))
                                .findAny().ifPresent(istanzaTemplateQuadro -> quadro.setJsonDataQuadro(istanzaTemplateQuadro.getJsonDataQuadro()));
//                        for (IstanzaTemplateQuadroExtendedDTO itqe : itqeDTOs) {
//                            Long idQuadro = itqe.getTemplateQuadro().getQuadro().getIdQuadro();
//                            if(idQuadro.equals(q.getIdQuadro())) {
//                                quadro.setJsonDataQuadro(itqe.getJsonDataQuadro());
//                                break;
//                            }
//                        }
                    }

                    quadri.add(quadro);
                }
                templateDTO.setQuadri(quadri);
                dto.setTemplate(templateDTO);

            } else {
                String errorMessage = "";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + errorMessage);
                return Response.serverError().entity(err).status(404).build();
            }

            list.add(dto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[IstanzaTemplateQuadroApiServiceImpl::getTemplateQuadriByCodeTemplateAndIdIstanza] END");
        }
        return getResponseWithSharedHeaders(list, istanzaTemplateQuadroApiServiceHelper);
    }

}
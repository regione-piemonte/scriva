/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Istanze api service helper.
 */
public class IstanzeApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/istanze";

    /**
     * Instantiates a new Istanze api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public IstanzeApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets istanze.
     *
     * @param requestHeaders the request headers
     * @return the istanze
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanze(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        List<IstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets istanze by cf compilante.
     *
     * @param requestHeaders the request headers
     * @param cfCompilante   the cf compilante
     * @return the istanze by cf compilante
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanzeByCfCompilante(MultivaluedMap<String, Object> requestHeaders, String cfCompilante) throws GenericException {
        logBegin(className);
        List<IstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilante/cf/" + cfCompilante;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets istanze by id compilante.
     *
     * @param requestHeaders the request headers
     * @param idCompilante   the id compilante
     * @return the istanze by id compilante
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanzeByIdCompilante(MultivaluedMap<String, Object> requestHeaders, Long idCompilante) throws GenericException {
        logBegin(className);
        List<IstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilante/" + idCompilante;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the istanza
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        List<IstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            resp.bufferEntity();
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            setHeaderProfiliApp(resp.getHeaderString(Constants.HEADER_PROFILI_APP));
            setHeaderTipoAdempimentoOggApp(resp.getHeaderString(Constants.HEADER_TIPO_ADEMPI_OGG_APP));
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets json data istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @param codTipoQuadro  the cod tipo quadro
     * @return the json data istanza
     * @throws GenericException the generic exception
     */
    public String getJsonDataIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String codTipoQuadro) throws GenericException {
        logBegin(className);
        String result = null;
        String targetUrl = this.endpointBase + "/" + idIstanza +"/json-data"+ (StringUtils.isNotBlank(codTipoQuadro) ? "?cod_tipo_quadro=" + codTipoQuadro : "");
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            setHeaderProfiliApp(resp.getHeaderString(Constants.HEADER_PROFILI_APP));
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Save istanza istanza extended dto.
     *
     * @param requestHeaders the request headers
     * @param istanza        the istanza
     * @return the istanza extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaExtendedDTO saveIstanza(MultivaluedMap<String, Object> requestHeaders, IstanzaExtendedDTO istanza) throws GenericException {
        logBegin(className);
        IstanzaExtendedDTO result = new IstanzaExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<IstanzaExtendedDTO> entity = Entity.json(istanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<IstanzaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update istanza istanza extended dto.
     *
     * @param requestHeaders the request headers
     * @param istanza        the istanza
     * @param flgCreaPratica the flg crea pratica
     * @return the istanza extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaExtendedDTO updateIstanza(MultivaluedMap<String, Object> requestHeaders, IstanzaExtendedDTO istanza, Boolean flgCreaPratica) throws GenericException {
        logBegin(className);
        IstanzaExtendedDTO result = new IstanzaExtendedDTO();
        String targetUrl = this.endpointBase + "?flg_crea_pratica=" + flgCreaPratica;
        try {
            Entity<IstanzaExtendedDTO> entity = Entity.json(istanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<IstanzaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update stato istanza list.
     *
     * @param requestHeaders the request headers
     * @param uidIstanza     the uid istanza
     * @param idStatoIstanza the id stato istanza
     * @param gestAttoreUpd  the gest attore upd
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> updateStatoIstanza(MultivaluedMap<String, Object> requestHeaders, String uidIstanza, Long idStatoIstanza, String gestAttoreUpd) throws GenericException {
        logBegin(className);
        List<IstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/stato-istanza/uid-istanza/" + uidIstanza + "/id-stato-istanza/" + idStatoIstanza;
        if (StringUtils.isNotBlank(gestAttoreUpd)) {
            targetUrl = targetUrl + "?gestAttoreUpd=" + gestAttoreUpd;
        }
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(null);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<IstanzaExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Delete istanza boolean.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteIstanza(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Create pdf file fo dto.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the file fo dto
     * @throws GenericException the generic exception
     */
    public FileFoDTO createPDF(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        FileFoDTO fileFoDTO = new FileFoDTO();
        String targetUrl = this.endpointBase + "/pdf/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            GenericType<byte[]> bao = new GenericType<byte[]>() {
            };
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                fileFoDTO.setFileContent(resp.readEntity(bao));
                fileFoDTO.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
                return fileFoDTO;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Upload pdf modulo firmato istanza list.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @param formDataInput  the form data input
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<AllegatoIstanzaExtendedDTO> uploadPDFModuloFirmatoIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, MultipartFormDataInput formDataInput) throws GenericException {
        logBegin(className);
        List<AllegatoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/pdf-modulo-firmato-istanza/" + idIstanza;
        try {
            /// Recupero filename
            InputPart inputPart = formDataInput.getFormDataMap().get("file").get(0);
            String filename = inputPart.getHeaders().getFirst(Constants.HEADER_CONTENT_DISPOSITION);
            // recupero valori nel form di input
            File file = formDataInput.getFormDataPart("file", File.class, null);
            // predisposizione form per la chiamata
            MultipartFormDataOutput output = new MultipartFormDataOutput();
            OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            objPart.getHeaders().putSingle(Constants.HEADER_CONTENT_DISPOSITION, "form-data; name=file; filename=\"" + filename + "\"");

            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(Entity.entity(output, MediaType.MULTIPART_FORM_DATA));
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<AllegatoIstanzaExtendedDTO>>() {
            });
        } catch (ProcessingException | IOException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Add referente referente istanza dto.
     *
     * @param requestHeaders the request headers
     * @param referente      the referente
     * @return the referente istanza dto
     * @throws GenericException the generic exception
     */
    public ReferenteIstanzaDTO addReferente(MultivaluedMap<String, Object> requestHeaders, ReferenteIstanzaDTO referente) throws GenericException {
        logBegin(className);
        ReferenteIstanzaDTO result = new ReferenteIstanzaDTO();
        String targetUrl = this.endpointBase + "/referenti";
        try {
            Entity<ReferenteIstanzaDTO> entity = Entity.json(referente);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<ReferenteIstanzaDTO>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update referente referente istanza dto.
     *
     * @param requestHeaders the request headers
     * @param referente      the referente
     * @return the referente istanza dto
     * @throws GenericException the generic exception
     */
    public ReferenteIstanzaDTO updateReferente(MultivaluedMap<String, Object> requestHeaders, ReferenteIstanzaDTO referente) throws GenericException {
        logBegin(className);
        ReferenteIstanzaDTO result = new ReferenteIstanzaDTO();
        String targetUrl = this.endpointBase + "/referenti";
        try {
            Entity<ReferenteIstanzaDTO> entity = Entity.json(referente);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<ReferenteIstanzaDTO>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Delete referente boolean.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteReferente(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/referenti/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets referenti by istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the referenti by istanza
     * @throws GenericException the generic exception
     */
    public List<ReferenteIstanzaDTO> getReferentiByIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        List<ReferenteIstanzaDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/referenti/id-istanza/" + idIstanza;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets referenti by code istanza.
     *
     * @param requestHeaders the request headers
     * @param codeIstanza    the code istanza
     * @return the referenti by code istanza
     * @throws GenericException the generic exception
     */
    public List<ReferenteIstanzaDTO> getReferentiByCodeIstanza(MultivaluedMap<String, Object> requestHeaders, String codeIstanza) throws GenericException {
        logBegin(className);
        List<ReferenteIstanzaDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/referenti/codice-istanza/" + codeIstanza;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Check istanza boolean.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @param codAdempimento the cod adempimento
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean checkIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String codAdempimento) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/check-istanza/id-istanza/" + idIstanza + "/adempimento/" + codAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Download pdf allegati file fo dto.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the file fo dto
     * @throws GenericException the generic exception
     */
    public FileFoDTO downloadPDFAllegati(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        FileFoDTO fileFoDTO = new FileFoDTO();
        String targetUrl = this.endpointBase + "/pdf-allegati/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            GenericType<byte[]> bao = new GenericType<byte[]>() {
            };
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                fileFoDTO.setFileContent(resp.readEntity(bao));
                fileFoDTO.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
                return fileFoDTO;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Create modulo delega file fo dto.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the file fo dto
     * @throws GenericException the generic exception
     */
    public FileFoDTO createModuloDelega(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        FileFoDTO fileFoDTO = new FileFoDTO();
        String targetUrl = this.endpointBase + "/doc-modulo-delega-istanza/" + idIstanza;
        try {
            //Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(null);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(null);
            GenericType<byte[]> bao = new GenericType<byte[]>() {
            };
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                fileFoDTO.setFileContent(resp.readEntity(bao));
                fileFoDTO.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
                return fileFoDTO;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets istanza document.
     *
     * @param servicePath       the service path
     * @param requestParameters the request parameters
     * @param requestHeaders    the request headers
     * @return the istanza document
     * @throws GenericException the generic exception
     */
    public FileFoDTO getIstanzaDocument(String servicePath, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        FileFoDTO fileFoDTO = new FileFoDTO();
        try {
            Response resp = getInvocationBuilder(endpointBase.substring(0, endpointBase.length() - ENDPOINT_CLASS.length()) + servicePath, requestHeaders, requestParameters).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                fileFoDTO.setFileContent(resp.readEntity(new GenericType<byte[]>() {
                }));
                fileFoDTO.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
                return fileFoDTO;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + endpointBase + servicePath + " ]");
        } finally {
            logEnd(className);
        }
    }

}
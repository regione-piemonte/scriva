/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.dto.FileFoDTO;
import it.csi.scriva.scrivaapisrv.dto.ObjectListFoDTO;
import it.csi.scriva.scrivaapisrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * The type Allegati istanza api service helper.
 */
public class AllegatiIstanzaApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/allegati";

    /**
     * Instantiates a new Allegati istanza api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public AllegatiIstanzaApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets allegati by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @param codAllegato    the cod allegato
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @param system         the system
     * @return the allegati by id istanza
     * @throws GenericException the generic exception
     */
    public ObjectListFoDTO getAllegati(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String codAllegato, Integer offset, Integer limit, String sort, Boolean system) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input idIstanza: [" + idIstanza + "] - offset: [" + offset + "] - limit: [" + limit + "] - sort: [" + sort + "] - system: [" + system + "]");
        ObjectListFoDTO result = new ObjectListFoDTO();
        String targetUrl = this.endpointBase;
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "cod_allegato", codAllegato);
            queryParameters = buildQueryParameters(queryParameters, "offset", offset);
            queryParameters = buildQueryParameters(queryParameters, "limit", limit);
            queryParameters = buildQueryParameters(queryParameters, "sort", sort);
            queryParameters = buildQueryParameters(queryParameters, "sys", system);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setObjectList(resp.readEntity(new GenericType<List<AllegatoIstanzaExtendedDTO>>() {
            }));
            result.setPaginationInfoHeader(resp.getHeaderString("PaginationInfo"));
            return result;
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets allegati by uuid index.
     *
     * @param requestHeaders the request headers
     * @param uuidIndex      the uuid index
     * @return the allegati by uuid index
     * @throws GenericException the generic exception
     */
    public FileFoDTO getAllegatiByUuidIndex(MultivaluedMap<String, Object> requestHeaders, String uuidIndex) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input uuidIndex: [" + uuidIndex + "]");
        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/" + uuidIndex;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setFile(resp.readEntity(new GenericType<File>() {
            }));
            result.setContentDispositionHeader(resp.getHeaderString("Content-Disposition"));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Upload index file list.
     *
     * @param requestHeaders the request headers
     * @param formDataInput  the form data input
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<AllegatoIstanzaExtendedDTO> uploadIndexFile(MultivaluedMap<String, Object> requestHeaders, MultipartFormDataInput formDataInput) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input formDataInput:\n" + formDataInput + "\n");
        List<AllegatoIstanzaExtendedDTO> result = null;
        String targetUrl = this.endpointBase;
        try {
            /// Recupero filename
            InputPart inputPart = formDataInput.getFormDataMap().get("documento").get(0);
            String filename = inputPart.getHeaders().getFirst("Content-Disposition");
            // recupero valori nel form di input
            File file = formDataInput.getFormDataPart("documento", File.class, null);
            String allegatoIstanza = formDataInput.getFormDataPart("dati_documento", String.class, null);
            // predisposizione form per la chiamata
            MultipartFormDataOutput output = new MultipartFormDataOutput();
            OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            objPart.getHeaders().putSingle("Content-Disposition", "form-data; name=file; filename=\"" + filename + "\""); //getFileName(headers));
            output.addFormData("allegatoIstanza", allegatoIstanza, MediaType.TEXT_PLAIN_TYPE);
            result = uploadIndexFile(requestHeaders, output);
        } catch (ProcessingException | IOException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Upload index file list.
     *
     * @param requestHeaders  the request headers
     * @param file            the file
     * @param allegatoIstanza the allegato istanza
     * @param fileName        the file name
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<AllegatoIstanzaExtendedDTO> uploadIndexFile(MultivaluedMap<String, Object> requestHeaders, File file, AllegatoIstanzaExtendedDTO allegatoIstanza, String fileName) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input allegatoIstanza:\n" + allegatoIstanza + "\n");
        List<AllegatoIstanzaExtendedDTO> result = null;
        String targetUrl = this.endpointBase;
        try {
            // trasformazione oggetto in json string
            ObjectMapper mapper = new ObjectMapper();
            // predisposizione form per la chiamata
            MultipartFormDataOutput output = new MultipartFormDataOutput();
            OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            objPart.getHeaders().putSingle("Content-Disposition", "form-data; name=file; filename=\"" + fileName + "\"");
            output.addFormData("allegatoIstanza", mapper.writeValueAsString(allegatoIstanza), MediaType.TEXT_PLAIN_TYPE);
            result = uploadIndexFile(requestHeaders, output);
        } catch (ProcessingException | IOException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Upload index file list.
     *
     * @param requestHeaders the request headers
     * @param formDataOutput the form data output
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<AllegatoIstanzaExtendedDTO> uploadIndexFile(MultivaluedMap<String, Object> requestHeaders, MultipartFormDataOutput formDataOutput) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input formDataOutput:\n" + formDataOutput + "\n");
        List<AllegatoIstanzaExtendedDTO> result = null;
        String targetUrl = this.endpointBase;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(Entity.entity(formDataOutput, MediaType.MULTIPART_FORM_DATA));
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Errore durante upload del documento.");
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update index file list.
     *
     * @param requestHeaders  the request headers
     * @param allegatoIstanza the allegato istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<AllegatoIstanzaExtendedDTO> updateIndexFile(MultivaluedMap<String, Object> requestHeaders, AllegatoIstanzaExtendedDTO allegatoIstanza) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input allegatoIstanza:\n" + allegatoIstanza + "\n");
        List<AllegatoIstanzaExtendedDTO> result = null;
        String targetUrl = this.endpointBase;
        try {
            Entity<AllegatoIstanzaExtendedDTO> entity = Entity.json(allegatoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Errore nel caricamento/aggiornamento documento.");
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Delete index file boolean.
     *
     * @param requestHeaders the request headers
     * @param uuidIndex      the uuid index
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteIndexFile(MultivaluedMap<String, Object> requestHeaders, String uuidIndex) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input uuidIndex: [" + uuidIndex + "]");
        String targetUrl = this.endpointBase + "/" + uuidIndex;
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
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets allegati csv by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @param outputFormat   the output format
     * @return the allegati csv by id istanza
     * @throws GenericException the generic exception
     */
    public FileFoDTO getFileAllegati(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String outputFormat) throws GenericException {
        logBeginInfo(className, idIstanza);
        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/_download";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "output_format", outputFormat);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setFile(resp.readEntity(new GenericType<File>() {
            }));
            result.setContentDispositionHeader(resp.getHeaderString("Content-Disposition"));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets categoria allegato mandatory by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the categoria allegato mandatory by id istanza
     * @throws GenericException the generic exception
     */
    public List<CategoriaAllegatoDTO> getCategoriaAllegatoMandatoryByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        List<CategoriaAllegatoDTO> result = null;
        String targetUrl = this.endpointBase + "/_mandatory-categoria";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<CategoriaAllegatoDTO>>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets categoria allegato mandatory delega firmata by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @throws GenericException the generic exception
     */
    public void getCategoriaAllegatoMandatoryDelegaFirmataByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        String targetUrl = this.endpointBase + "/_mandatory-delega";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets categoria allegato mandatory adempimento by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the categoria allegato mandatory adempimento by id istanza
     * @throws GenericException the generic exception
     */
    public List<CategoriaAllegatoDTO> getCategoriaAllegatoMandatoryAdempimentoByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        List<CategoriaAllegatoDTO> result = null;
        String targetUrl = this.endpointBase + "/categorie-adempimento";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Create pdf allegati by id istanza string.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the string
     * @throws GenericException the generic exception
     */
    public String createPdfAllegatiByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        String result = null;
        String targetUrl = this.endpointBase + "/pdf-allegati/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(String.class);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets allegati cosmo.
     *
     * @param requestHeaders the request headers
     * @param linkdocumento  the linkdocumento
     * @return the allegati cosmo
     * @throws GenericException the generic exception
     */
    public FileFoDTO getAllegatiCosmo(MultivaluedMap<String, Object> requestHeaders, String linkdocumento) throws GenericException {
        logBegin(className);
        logDebug(className, "Parametro in input linkdocumento: [" + linkdocumento + "]");
        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/cosmo?url_cosmo=" + linkdocumento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            result.setFile(resp.readEntity(new GenericType<File>() {
            }));
            String disposition = resp.getHeaderString(HttpHeaders.CONTENT_DISPOSITION);
            String fileName = StringUtils.isNotBlank(disposition) ? disposition.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1") : "noname";
            result.setContentDispositionHeader(disposition);
            result.setFileName(fileName);

        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

}
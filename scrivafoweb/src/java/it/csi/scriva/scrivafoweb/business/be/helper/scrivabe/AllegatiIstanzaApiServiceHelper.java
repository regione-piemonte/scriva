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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.GenericInputParDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.dto.ObjectListFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
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
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * The type Allegati istanza api service helper.
 */
public class AllegatiIstanzaApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Allegati istanza api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public AllegatiIstanzaApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets allegati by id istanza.
     *
     * @param requestHeaders       the request headers
     * @param idIstanza            the id istanza
     * @param codAllegato          the cod allegato
     * @param codCategoriaAllegato the cod categoria allegato
     * @param flgCancLogica        the flg canc logica
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @param system               the system
     * @return the allegati by id istanza
     * @throws GenericException the generic exception
     */
    public ObjectListFoDTO getAllegati(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String codAllegato, String codCategoriaAllegato, String flgCancLogica, Integer offset, Integer limit, String sort, Boolean system) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza [" + idIstanza + "] - codCategoriaAllegato [" + codCategoriaAllegato + "] - flgCancLogica [" + flgCancLogica + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        ObjectListFoDTO result = new ObjectListFoDTO();
        String targetUrl = this.endpointBase + "/allegati";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "cod_allegato", codAllegato);
            queryParameters = buildQueryParameters(queryParameters, "cod_categoria_allegato", codCategoriaAllegato);
            queryParameters = buildQueryParameters(queryParameters, "flg_canc_logica", flgCancLogica);
            queryParameters = buildQueryParameters(queryParameters, "offset", offset);
            queryParameters = buildQueryParameters(queryParameters, "limit", limit);
            queryParameters = buildQueryParameters(queryParameters, "sort", sort);
            queryParameters = buildQueryParameters(queryParameters, "sys", system);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            //result = setGetResult(className, methodName, targetUrl + queryParameters, requestHeaders, result);
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setObjectList(resp.readEntity(new GenericType<List<AllegatoIstanzaExtendedDTO>>() {
            }));
            result.setPaginationInfoHeader(resp.getHeaderString(Constants.HEADER_PAGINATION_INFO));
            return result;
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uuidIndex: [" + uuidIndex + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));

        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/allegati/" + uuidIndex;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setFile(resp.readEntity(new GenericType<File>() {
            }));
            result.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input formDataInput:\n" + formDataInput + "\n";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        long initTime = System.currentTimeMillis();
        logInfo(className, methodName, "TIMER:  BEGIN: "+ initTime);
        
        
        List<AllegatoIstanzaExtendedDTO> result = null;
        String targetUrl = this.endpointBase + "/allegati";
        try {
        	
        	
            /// Recupero filename
            InputPart inputPart = formDataInput.getFormDataMap().get("file").get(0);
            String filename = inputPart.getHeaders().getFirst(Constants.HEADER_CONTENT_DISPOSITION);
            
            // recupero valori nel form di input
            File file = formDataInput.getFormDataPart("file", File.class, null);
            logger.info(getClassFunctionDebugString(className, methodName,"TIMER creazione file: "+ (System.currentTimeMillis() - initTime) ));
            
            String allegatoIstanza = formDataInput.getFormDataPart("allegatoIstanza", String.class, null);
            // predisposizione form per la chiamata
            MultipartFormDataOutput output = new MultipartFormDataOutput();
            OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            objPart.getHeaders().putSingle(Constants.HEADER_CONTENT_DISPOSITION, "form-data; name=file; filename=\"" + filename + "\""); //getFileName(headers));
            output.addFormData("allegatoIstanza", allegatoIstanza, MediaType.TEXT_PLAIN_TYPE);
            
            logger.info(getClassFunctionDebugString(className, methodName,"TIMER request pronta : "+ (System.currentTimeMillis() - initTime) ));
            if(filename.contains("nodo1")) targetUrl = "http://dv-wf1-be-scriva.site02.nivolapiemonte.it:10210/scrivabesrv/api/v1/allegati";
           
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(Entity.entity(output, MediaType.MULTIPART_FORM_DATA));
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            logger.info(getClassFunctionDebugString(className, methodName,"TIMER fine request  : "+ (System.currentTimeMillis() - initTime) ));
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<AllegatoIstanzaExtendedDTO>>() {
            });
        } catch (ProcessingException | IOException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
        	logger.info(getClassFunctionDebugString(className, methodName,"TIMER END "+ (System.currentTimeMillis() - initTime) ));
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input allegatoIstanza:\n" + allegatoIstanza + "\n";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));

        List<AllegatoIstanzaExtendedDTO> result = null;
        String targetUrl = this.endpointBase + "/allegati";
        try {
            Entity<AllegatoIstanzaExtendedDTO> entity = Entity.json(allegatoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<AllegatoIstanzaExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uuidIndex: [" + uuidIndex + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));

        String targetUrl = this.endpointBase + "/allegati/" + uuidIndex;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
    }

    /**
     * Gets allegati csv by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @param codAllegato    the cod allegato
     * @param outputFormat   the output format
     * @return the allegati csv by id istanza
     * @throws GenericException the generic exception
     */
    public FileFoDTO getFileAllegati(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String codAllegato, String outputFormat) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - outputFormat [" + outputFormat + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/allegati/_download";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "cod_allegato", codAllegato);
            queryParameters = buildQueryParameters(queryParameters, "output_format", outputFormat);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setFile(resp.readEntity(new GenericType<File>() {
            }));
            result.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Gets file allegati selezionati.
     *
     * @param requestHeaders the request headers
     * @param codiciAllegato the codici allegato
     * @param idIstanza      the id istanza
     * @param codAllegato    the cod allegato
     * @param idOsservazione the id osservazione
     * @param outputFormat   the output format
     * @return the file allegati selezionati
     * @throws GenericException the generic exception
     */
    public FileFoDTO getFileAllegatiSelezionati(MultivaluedMap<String, Object> requestHeaders, GenericInputParDTO codiciAllegato, Long idIstanza, String codAllegato, Long idOsservazione, String outputFormat) throws GenericException {
        logBeginInfo(className, "Parametro in input idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - idOsservazione [" + idOsservazione + "] - outputFormat [" + outputFormat + "] \ncodici allegato:\n" + codiciAllegato);
        FileFoDTO result = new FileFoDTO();
        String targetUrl = this.endpointBase + "/allegati/_download";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "cod_allegato", codAllegato);
            queryParameters = buildQueryParameters(queryParameters, "id_osservazione", idOsservazione);
            queryParameters = buildQueryParameters(queryParameters, "output_format", outputFormat);
            Entity<GenericInputParDTO> entity = Entity.json(codiciAllegato);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result.setFile(resp.readEntity(new GenericType<File>() {
            }));
            result.setContentDispositionHeader(resp.getHeaderString(Constants.HEADER_CONTENT_DISPOSITION));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        List<CategoriaAllegatoDTO> result = null;
        String targetUrl = this.endpointBase + "/allegati/_mandatory-categoria";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<CategoriaAllegatoDTO>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        String targetUrl = this.endpointBase + "/allegati/_mandatory-delega";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        List<CategoriaAllegatoDTO> result = null;
        String targetUrl = this.endpointBase + "/allegati/categorie-adempimento";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<CategoriaAllegatoDTO>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        String result = null;
        String targetUrl = this.endpointBase + "/allegati/pdf-allegati/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(String.class);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }


    private MultipartFormDataOutput fromFormDataInputToFormDataOutput(MultipartFormDataInput input, String filename) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        MultipartFormDataOutput mdo = new MultipartFormDataOutput();
        int i = 0;
        for (Map.Entry<String, List<InputPart>> inputPartEntry : input.getFormDataMap().entrySet()) {
            String partId = inputPartEntry.getKey();
            partId = partId.replaceAll("[^a-z,A-Z]", ""); //remove number al final
            List<InputPart> inputParts = inputPartEntry.getValue();

            for (InputPart part : inputParts) {
                MultivaluedMap<String, String> headers = part.getHeaders();

                InputStream inputStream;
                try {
                    inputStream = part.getBody(InputStream.class, null);
                    OutputPart objPart = mdo.addFormData(partId, inputStream, part.getMediaType());
                    objPart.getHeaders().putSingle(Constants.HEADER_CONTENT_DISPOSITION, "form-data; name=" + partId + "; filename=\"" + filename + "\""); //getFileName(headers));
                } catch (IOException e) {
                    logger.error(getClassFunctionErrorInfo(className, methodName, e));
                }
            }
        }
        return mdo;
    }


}
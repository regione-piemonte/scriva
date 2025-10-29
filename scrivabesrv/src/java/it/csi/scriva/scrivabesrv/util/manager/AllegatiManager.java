/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.manager;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.index.IndexServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.ErrorMessage;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Property;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.SearchParams;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.SharingInfo;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.VerifyReport;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoEstensioneAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoEstensioneAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IndexMetadataPropertyDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.VerificaFirmaDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Allegati manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AllegatiManager {

    public static final String ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR = "[AllegatiManager::uploadAllegato] ERROR : ";
    public static final String ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END = "[AllegatiManager::uploadAllegato] END";
    /**
     * The constant LOGGER.
     */
    protected static Logger logger = Logger.getLogger(Constants.LOGGER_NAME + ".business");
    private final String className = this.getClass().getSimpleName();
    @Autowired
    private AllegatiService allegatiService;

    @Autowired
    private IndexServiceHelper indexServiceHelper;

    @Autowired
    private AdempimentoEstensioneAllegatoDAO adempimentoEstensioneAllegatoDAO;

    @Autowired
    private AdempimentoTipoAllegatoDAO adempimentoTipoAllegatoDAO;

    @Autowired
    private AllegatoIstanzaDAO allegatoIstanzaDAO;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private TipoAdempimentoDAO tipoAdempimentoDAO;

    @Autowired
    private ErrorManager errorManager;

    @Autowired
    private IstanzaAttoreManager istanzaAttoreManager;

    /**
     * Upload allegato long.
     *
     * @param idIstanza            id istanza
     * @param attoreScriva         the attore scriva
     * @param file                 file
     * @param fileName             nome file
     * @param codCategoriaAlleagto codice categoria allegato
     * @param codTipologiaAllegato codice tipologia allegato
     * @return Long id allegato istanza
     * @throws GenericException GenericException
     */
    public Long uploadAllegato(@NotNull Long idIstanza, @NotNull AttoreScriva attoreScriva, @NotNull File file, @NotNull String fileName, @NotNull String codCategoriaAlleagto, @NotNull String codTipologiaAllegato) throws GenericException {
        logger.debug("[AllegatiManager::uploadAllegato] BEGIN");
        Long idAllegatoIstanza = null;
        String data = "idIstanza [" + idIstanza + "] - filename [" + fileName + "]\n";
        ErrorDTO error = null;
        VerificaFirmaDTO verificaFirma = null;
        AllegatoIstanzaExtendedDTO allegatoIstanza = null;

        long initTime = System.currentTimeMillis();
        
        // Recupero istanza
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        IstanzaExtendedDTO istanza = istanzaList != null && !istanzaList.isEmpty() ? istanzaList.get(0) : null;
        if (istanza == null) {
            String errorMessage = "Errore durante il recupero dell'istanza.";
            error = errorManager.getError("500", "I011", errorMessage, null, null);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + errorMessage);
            logger.debug(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }
        logger.info("[AllegatiManager::uploadAllegato] TIMER Recupero istanza: "+(System.currentTimeMillis() - initTime));
        
        // Verifica validità estensione file
        error = validateFileExtension(fileName, istanza.getAdempimento().getIdAdempimento());
        logger.info("[AllegatiManager::uploadAllegato] TIMER Verifica validità estensione file: "+(System.currentTimeMillis() - initTime));
        if (null != error) {
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + error);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }

        // recupero tipo allegato per adempimento
        List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoList = adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(istanza.getAdempimento().getCodAdempimento(), codCategoriaAlleagto, attoreScriva.getComponente());
        List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoFilteredList = adempimentoTipoAllegatoList != null && !adempimentoTipoAllegatoList.isEmpty() ? adempimentoTipoAllegatoList.stream()
                .filter(tipo -> tipo.getTipologiaAllegato().getCodTipologiaAllegato().equalsIgnoreCase(codTipologiaAllegato))
                .collect(Collectors.toList()) : null;
        AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = adempimentoTipoAllegatoFilteredList != null && !adempimentoTipoAllegatoFilteredList.isEmpty() ? adempimentoTipoAllegatoFilteredList.get(0) : null;
        
        logger.info("[AllegatiManager::uploadAllegato] TIMER recupero tipo allegato per adempimento: "+(System.currentTimeMillis() - initTime));
        if (adempimentoTipoAllegato == null) {
            String errorMessage = "Errore durante il recupero delle tipologie allegati.";
            error = errorManager.getError("500", "I011", errorMessage, null, null);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + errorMessage);
            logger.debug(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }

        // Verifica nome file duplicato
        error = verifyDuplicateFilename(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento(), istanza.getIdIstanza(), fileName);
        logger.info("[AllegatiManager::uploadAllegato] TIMER Verifica nome file duplicato: "+(System.currentTimeMillis() - initTime));
        if (null != error) {
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + error);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }

        // caricamento file su Index
        String uuidIndex = uploadFileOnIndex(istanza, file, fileName);
        logger.info("[AllegatiManager::uploadAllegato] TIMER caricamento file su Index: "+(System.currentTimeMillis() - initTime));
        if (uuidIndex == null || StringUtils.isBlank(uuidIndex)) {
            String errorMessage = "Errore durante il caricamento dei file su index.";
            error = errorManager.getError("500", "I011", errorMessage, null, null);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + errorMessage);
            logger.debug(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }

        // verifica firma
        if (Boolean.TRUE.equals(adempimentoTipoAllegato.getFlgFirmaDigitale())) {

            // chiamata index di verifica firma digitale
            verificaFirma = StringUtils.isNotBlank(uuidIndex) ? verificaFirma(uuidIndex, null) : null;
            logger.info("[AllegatiManager::uploadAllegato] TIMER chiamata index di verifica firma digitale: "+(System.currentTimeMillis() - initTime));
            // verifica se la mancanza di firma digitale è bloccante
            if (Boolean.TRUE.equals(adempimentoTipoAllegato.getFlgFirmaNonValidaBloccante() && verificaFirma != null) && (verificaFirma.getIndFirma() != null && (verificaFirma.getIndFirma() == 1 || verificaFirma.getIndFirma() == 3))) {
                // cancellazione file da index
                deleteContenutoByUuid(uuidIndex);
                String errorMessage = "Risultano delle anomalie nel documento firmato digitalmente.";
                error = errorManager.getError("500", "E015", errorMessage, null, null);
                error.setTitle(error.getTitle().replace("{PH_NOME_ALLEGATO}", "'" + fileName + "'"));
                logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + errorMessage);
                logger.debug(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
                throw new GenericException(error);
            }
        }

        // recupero metadata da index
        Node indexNode = getMetadataIndexByUuid(uuidIndex);
        logger.info("[AllegatiManager::uploadAllegato] TIMER recupero metadata da index: "+(System.currentTimeMillis() - initTime));
        
        // populate allegato istanza
        allegatoIstanza = new AllegatoIstanzaExtendedDTO();
        populateAllegatoIstanza(allegatoIstanza, indexNode, uuidIndex, fileName, file.length(), verificaFirma != null ? verificaFirma.getIndFirma() : null, false, istanza, adempimentoTipoAllegato);
        Long idIstanzaAttoreCompilante = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ?
                istanzaAttoreManager.getIdIstanzaAttoreCompilante(Constants.CF_COMPILANTE_FITTIZIO_BO, idIstanza, ComponenteAppEnum.FO, attoreScriva.getCodiceFiscale()) :
                istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), idIstanza, ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
        allegatoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
        if (allegatoIstanza.getClasseAllegato() == null) {
            allegatoIstanza.setClasseAllegato(allegatiService.getClasseAllegato(istanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato(), null));
        } else {
            if (allegatoIstanza.getClasseAllegato().getIdClasseAllegato() == null && StringUtils.isNotBlank(allegatoIstanza.getClasseAllegato().getCodClasseAllegato())) {
                allegatoIstanza.setClasseAllegato(allegatiService.getClasseAllegatoByCode(allegatoIstanza.getClasseAllegato().getCodClasseAllegato()));
            }
        }
        logger.info("[AllegatiManager::uploadAllegato] TIMER populate allegato istanza: "+(System.currentTimeMillis() - initTime));
        
        // save allegato istanza
        idAllegatoIstanza = allegatoIstanzaDAO.saveAllegatoIstanza(allegatoIstanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato());
        if (idAllegatoIstanza == null) {
            // cancellazione file da index
            deleteContenutoByUuid(uuidIndex);

            String errorMessage = "Errore durante il salvataggio dei metadati su SCRIVA.";
            error = errorManager.getError("500", "I011", errorMessage, null, null);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + errorMessage);
            logger.debug(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }

        logger.info("[AllegatiManager::uploadAllegato] TIMER save allegato istanza: "+(System.currentTimeMillis() - initTime));
        
        //before update retrieve data from db
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaById(idAllegatoIstanza);
        allegatoIstanza = allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty() ? allegatoIstanzaList.get(0) : allegatoIstanza;

        // update metadata index
        IndexMetadataPropertyDTO indexMetadataProperty = getindexMetadataProperty(istanza, allegatoIstanza, adempimentoTipoAllegato.getFlgObbligo(), (verificaFirma != null ? verificaFirma.getTipoFirma() : null), (verificaFirma != null ? verificaFirma.getErrorCode() : null), null, null);
        error = updateMetadataIndexByUuid(uuidIndex, indexMetadataProperty);
        logger.info("[AllegatiManager::uploadAllegato] TIMER update metadata index: "+(System.currentTimeMillis() - initTime));
        if (error != null) {
            // cancellazione file da index
            deleteContenutoByUuid(uuidIndex);

            String errorMessage = "Errore durante l'aggiornamento dei metadati su INDEX.";
            error = errorManager.getError("500", "I011", errorMessage, null, null);
            logger.error(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_ERROR + data + errorMessage);
            logger.debug(ALLEGATI_MANAGER_UPLOAD_ALLEGATO_END);
            throw new GenericException(error);
        }
        
        logger.info("[AllegatiManager::uploadAllegato] TIMER END: "+(System.currentTimeMillis() - initTime));
        return idAllegatoIstanza;
    }

    /**
     * Recupera e restituisce il file identificato dall'uuid passato in input
     *
     * @param uuid uid index
     * @return File file from index by uuid
     */
    public File getFileFromIndexByUuid(String uuid) {
        logger.debug("[AllegatiManager::getFileFromIndexByUuid]");
        return indexServiceHelper.retrieveContentData(uuid);
    }

    /**
     * Elimina la cartella o il file identificato dall'uuid passato in input. In caso di errore restituisce il relativo oggetto.
     *
     * @param uuid uid index
     * @return ErrorDTO error dto
     */
    public ErrorDTO deleteContenutoByUuid(String uuid) {
        logger.debug("[AllegatiManager::deleteContenutoByUuid] BEGIN");
        ErrorDTO error = null;
        ErrorMessage errorMessage = indexServiceHelper.deleteContent(uuid);
        if (errorMessage != null) {
            logger.error("[AllegatiManager::deleteContenutoByUuid] Index Error :\n" + uuid + "\n" + errorMessage.toString());
            error = errorManager.getError("500", "E100", "Si e' verificata un'anomalia durante la cancellazione del file.", null, null);
        }
        logger.debug("[AllegatiManager::deleteContenutoByUuid] END");
        return error;
    }

    /**
     * Chiamata index per verifica firma digitale
     *
     * @param uuid uuidIndex
     * @param file file
     * @return VerifyReport verify report
     */
    public VerifyReport verificaFirmaIndex(String uuid, File file) {
        logger.debug("[AllegatiManager::verificaFirmaIndex] BEGIN");
        VerifyReport verifyReport = null;
        verifyReport = indexServiceHelper.verifySignedDocument(uuid, file);
        logger.debug("[AllegatiManager::verificaFirmaIndex] END");
        return verifyReport;
    }

    /**
     * Effettua la chiamata ad index per la verifica della firma e popola l'oggetto con i dati estratti
     *
     * @param uuid uuidIndex
     * @param file file
     * @return VerificaFirmaDTO verifica firma dto
     */
    public VerificaFirmaDTO verificaFirma(String uuid, File file) {
        logger.debug("[AllegatiManager::verificaFirma] BEGIN");
        VerificaFirmaDTO verificaFirma = null;
        VerifyReport verifyReport = indexServiceHelper.verifySignedDocument(uuid, file);
        if (verifyReport != null) {
            verificaFirma = new VerificaFirmaDTO();
            verificaFirma.setTipoFirma(verifyReport.getTipoFirma());
            verificaFirma.setErrorCode(verifyReport.getErrorCode());
        }
        logger.debug("[AllegatiManager::verificaFirma] END");
        return verificaFirma;
    }

    /**
     * Creazione dei path e upload dei file su index
     *
     * @param istanza  IstanzaExtendedDTO
     * @param file     file
     * @param fileName fileName
     * @return String string
     */
    public String uploadFileOnIndex(@NotNull IstanzaExtendedDTO istanza, @NotNull File file, @NotNull String fileName) {
   
        logger.info("[AllegatiManager::uploadFileOnIndex] TIMER: BEGIN");
        String uuidIndexTipoAdempimento = null;
        String uuidIndexIstanza = null;
        String uuidIndexFile = null;

        if (istanza != null && file != null) {
            uuidIndexIstanza = istanza.getUuidIndex();
            if (uuidIndexIstanza == null || StringUtils.isBlank(uuidIndexIstanza)) {
                TipoAdempimentoExtendedDTO tipoAdempimento = istanza.getAdempimento() != null ? istanza.getAdempimento().getTipoAdempimento() : null;
                uuidIndexTipoAdempimento = tipoAdempimento != null ? tipoAdempimento.getUuidIndex() : null;

                // CREAZIONE FOLDER PER TIPO ADEMPIMENTO
                if (uuidIndexTipoAdempimento == null && tipoAdempimento != null) {
                    // recupero uuid della root
                    List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey(Constants.CONFIG_KEY_ALLEGATI_TENANT_UUID);
                    String uuidIndexRoot = configurazioneList != null && !configurazioneList.isEmpty() && configurazioneList.get(0) != null ? configurazioneList.get(0).getValore() : null;

                    // crea folder per tipoAdempimento
                    String folderName = tipoAdempimento.getCodTipoAdempimento();
                    uuidIndexTipoAdempimento = uuidIndexRoot != null && folderName != null ? indexServiceHelper.createContent(folderName, uuidIndexRoot, null, null) : null;

                    // aggiornamento db con uuidIndex per tipo adempimento
                    if (StringUtils.isNotBlank(uuidIndexTipoAdempimento)) {
                        tipoAdempimentoDAO.updateUuidIndex(tipoAdempimento.getIdTipoAdempimento(), uuidIndexTipoAdempimento);
                    }
                }

                // CREAZIONE FOLDER PER ISTANZA
                if (uuidIndexTipoAdempimento != null) {
                    // crea folder per istanza
                    String folderName = istanza.getIdIstanza().toString();
                    uuidIndexIstanza = indexServiceHelper.createContent(folderName, uuidIndexTipoAdempimento, null, null);

                    // aggiornamento db con uuidIndex dell'istanza
                    if (StringUtils.isNotBlank(uuidIndexIstanza)) {
                        istanzaDAO.updateUuidIndex(istanza.getIdIstanza(), uuidIndexIstanza);
                    }
                }
            }
            logger.info("[AllegatiManager::uploadFileOnIndex] TIMER inizio caricamento del file BEGIN");
            // UPLOAD FILE
            uuidIndexFile = uuidIndexIstanza != null ? indexServiceHelper.createContent(fileName, uuidIndexIstanza, file, null) : null;
            logger.info("[AllegatiManager::uploadFileOnIndex] TIMER fine caricamento del file END");
            logger.info("[AllegatiManager::uploadFileOnIndex]  TIMER END");

        }
        return uuidIndexFile;
    }

    /**
     * Aggiorna i metadati sull'uid index passati in input
     *
     * @param uuid                  uuidIndex
     * @param indexMetadataProperty indexMetadataProperty
     * @return the error dto
     */
    public ErrorDTO updateMetadataIndexByUuid(String uuid, IndexMetadataPropertyDTO indexMetadataProperty) {
        logger.debug("[AllegatiManager::updateMetadataIndexByUuid] BEGIN");
        ErrorDTO error = null;
        ErrorMessage errorMessage = indexServiceHelper.updateMetadata(uuid, indexMetadataProperty);
        if (errorMessage != null) {
            logger.error("[AllegatiManager::updateMetadataIndexByUuid] Index Error :\n" + errorMessage.toString());
            error = errorManager.getError("500", "E100", "Si e' verificata un'anomalia durante l'aggiornamento dei dati.", null, null);
        }
        logger.debug("[AllegatiManager::updateMetadataIndexByUuid] END");
        return error;
    }

    /**
     * Restituisce i metadati dell'uuid index passato in input
     *
     * @param uuid uuidIndex
     * @return Node metadata index by uuid
     */
    public Node getMetadataIndexByUuid(String uuid) {
        return indexServiceHelper.getContentMetadata(uuid);
    }

    /**
     * Restituisce il file recuperato da index sulla base dell'uuid passato in input
     *
     * @param uuid uuidIndex
     * @return File file by uuid
     */
    public File getFileByUuid(String uuid) {
        return indexServiceHelper.retrieveContentData(uuid);
    }

    /**
     * Get sharing info sharing info.
     *
     * @param fileName the file name
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the sharing info
     */
    public SharingInfo setSharingInfo(String fileName, String fromDate, String toDate, SharingInfo sharingInfo) {
        if (sharingInfo == null) {
            sharingInfo = new SharingInfo();
        }
        sharingInfo.setContentPropertyPrefixedName("cm:content");
        sharingInfo.setSource("internet");
        if (StringUtils.isNotBlank(fromDate)) {
            fromDate = fromDate.replace(" ", "T") + ":00.000+01:00";
            sharingInfo.setFromDate(fromDate);
        }
        if (StringUtils.isNotBlank(toDate)) {
            toDate = toDate.replace(" ", "T") + ":00.000+01:00";
            sharingInfo.setToDate(toDate);
        }
        sharingInfo.setResultContentDisposition("inline; filename=\"" + fileName + "\"");
        sharingInfo.setResultPropertyPrefixedName("cm:name");
        return sharingInfo;
    }

    /**
     * Gets sharing infos.
     *
     * @param uuid     the uuid
     * @param fileName the file name
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the sharing infos
     */
    public String shareDocument(String uuid, String fileName, String fromDate, String toDate) {
        return indexServiceHelper.shareDocument(uuid, setSharingInfo(fileName, fromDate, toDate, null));
    }

    /**
     * Update shared link string.
     *
     * @param uuid        the uuid
     * @param sharingInfo the sharing info
     * @return the string
     */
    public String updateSharedLink(String uuid, SharingInfo sharingInfo) {
        indexServiceHelper.updateSharedLink(uuid, getShareLinkId(sharingInfo), sharingInfo);
        return sharingInfo.getSharedLink();
    }

    /**
     * Gets sharing infos.
     *
     * @param uuid the uuid
     * @return the sharing infos
     */
    public SharingInfo getSharingInfos(String uuid) {
        return indexServiceHelper.getSharingInfos(uuid);
    }

    /**
     * Verifica esistenza di un determinato path su index
     *
     * @param codTipoAdempimento codice tipo adempimento
     * @param idIstanza          id istanza
     * @param filename           nome del file
     * @return Boolean boolean
     */
    public Boolean indexPathExist(String codTipoAdempimento, Long idIstanza, String filename) {
        List<Node> nodeList = indexServiceHelper.luceneSearch(Boolean.TRUE, getIndexSearchParams(codTipoAdempimento, idIstanza, filename));
        return nodeList != null && !nodeList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Recupero nome del file
     *
     * @param formDataInput MultipartFormDataInput
     * @param fileFieldName the file field name
     * @return string filename
     */
    public String getFileName(MultipartFormDataInput formDataInput, String fileFieldName) {
        String fileName = "unknown";
        try {
            Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
            List<InputPart> inputParts = uploadForm.get(fileFieldName);
            MultivaluedMap<String, String> header = inputParts != null ? inputParts.get(0).getHeaders() : null;
            assert header != null;
            String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
            for (String filename : contentDisposition) {
                if ((filename.trim().startsWith("filename"))) {
                    String[] name = filename.split("=");
                    fileName = name[1].trim().replace("\"", "");
                }
            }
        } catch (Exception e) {
            logger.debug("[AllegatiManager::getFileName] ERROR : ", e);
        }
        return fileName;
    }

    /**
     * Verifica che l'estensione del file da salvare sia tra quelli consentiti
     *
     * @param fileName      Nome del file
     * @param idAdempimento Id dell'adempimento
     * @return ErrorDTO error dto
     */
    public ErrorDTO validateFileExtension(String fileName, Long idAdempimento) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        List<AdempimentoEstensioneAllegatoDTO> adempimentoEstensioneAllegatoList = adempimentoEstensioneAllegatoDAO.loadAdempimentoEstensioneAllegatoByIdAdempimento(idAdempimento);
        String estensioneFile = FilenameUtils.getExtension(fileName);

        List<AdempimentoEstensioneAllegatoDTO> adempimentoEstensioneAllegatoFiltered = adempimentoEstensioneAllegatoList.stream().filter(est -> est.getEstensioneAllegato().equalsIgnoreCase(estensioneFile)).collect(Collectors.toList());
        if (adempimentoEstensioneAllegatoFiltered.isEmpty()) {
            details.put("estensioneFile", ValidationResultEnum.INVALID.getDescription());
        }

        if (!details.isEmpty()) {
            error = errorManager.getError("400", "E019", "Estensione del file non consentita.", details, null);
        }

        return error;
    }

    /**
     * Verify duplicate filename error dto.
     *
     * @param codTipoAdempimento the cod tipo adempimento
     * @param idIstanza          the id istanza
     * @param filename           the filename
     * @return the error dto
     */
    public ErrorDTO verifyDuplicateFilename(String codTipoAdempimento, Long idIstanza, String filename) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndNome(idIstanza, filename);
        if (allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
            details.put("nomeFile", ValidationResultEnum.DUPLICATE.getDescription());
        } else {
            if (Boolean.TRUE.equals(indexPathExist(codTipoAdempimento, idIstanza, filename))) {
                details.put("nomeFile", ValidationResultEnum.DUPLICATE.getDescription());
            }
        }

        if (!details.isEmpty()) {
            error = errorManager.getError("400", "E021", "Attenzione: la denominazione dell'allegato che stai caricando e' gia' stata usata per un documento caricato in precedenza.", null, null);
        }

        return error;
    }


    /**
     * Popola l'oggetto allegatoIstanza con i valori passati in input
     *
     * @param allegatoIstanza         allegatoIstanza
     * @param indexNode               indexNode
     * @param uuidIndex               uuidIndex
     * @param fileName                fileName
     * @param fileLength              fileLength
     * @param indFirma                indFirma
     * @param flgCancellato           flgCancellato
     * @param istanza                 istanza
     * @param adempimentoTipoAllegato adempimentoTipoAllegatoExtendedDTO
     */
    public void populateAllegatoIstanza(AllegatoIstanzaExtendedDTO allegatoIstanza, Node indexNode, String uuidIndex, String fileName, Long fileLength, Integer indFirma, Boolean flgCancellato, IstanzaExtendedDTO istanza, AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato) {
        try {
            if (allegatoIstanza == null) {
                allegatoIstanza = new AllegatoIstanzaExtendedDTO();
            }

            if (StringUtils.isBlank(allegatoIstanza.getGestAttoreIns())) {
                allegatoIstanza.setGestAttoreIns("SYSTEM");
            }

            if (StringUtils.isBlank(allegatoIstanza.getGestAttoreUpd())) {
                allegatoIstanza.setGestAttoreUpd("SYSTEM");
            }

            if (istanza != null) {
                allegatoIstanza.setIdIstanza(istanza.getIdIstanza());
                // START SCRIVA-725
                /*
                if (istanza.getDataPubblicazione() != null && Boolean.TRUE.equals(allegatoIstanza.getFlgDaPubblicare())) {
                    allegatoIstanza.setDataPubblicazione(new Timestamp(System.currentTimeMillis()));
                }
                */
                // END SCRIVA-725
            }

            if (adempimentoTipoAllegato != null) {
                allegatoIstanza.setTipologiaAllegato(adempimentoTipoAllegato.getTipologiaAllegato());
                allegatoIstanza.setFlgRiservato(adempimentoTipoAllegato.getFlgRiservato());
            }

            allegatoIstanza.setUuidIndex(uuidIndex);
            allegatoIstanza.setNomeAllegato(fileName);
            allegatoIstanza.setDimensioneUpload(fileLength);
            allegatoIstanza.setIndFirma(indFirma);
            allegatoIstanza.setFlgCancellato(flgCancellato);


            List<Property> properties = indexNode.getProperties();
            String created = getPropertyValue(properties, "cm:created");
            String[] createdSplits = StringUtils.isNotBlank(created) ? created.split("\\.") : null;

            if (createdSplits != null && createdSplits.length > 0) {
                created = createdSplits[0].replace("T", " ");
                allegatoIstanza.setDataUpload(Timestamp.valueOf(created));
            }

        } catch (Exception e) {
            logger.debug("[AllegatiManager::populateAllegatoIstanza] ERROR : ", e);
        }
    }

    /**
     * Costruisce le properties necessarie al Node index con i parametri passati in input
     *
     * @param istanza                  istanza
     * @param allegatoIstanza          allegatoIstanza
     * @param flgODocumentobbligatorio flgODocumentobbligatorio
     * @param tipoFirma                tipoFirma
     * @param errorCode                errorCode
     * @param flgCancellato            flgCancellato
     * @param dataCancellazione        dataCancellazione
     * @return IndexMetadataPropertyDTO metadata property
     */
    public IndexMetadataPropertyDTO getindexMetadataProperty(IstanzaExtendedDTO istanza, AllegatoIstanzaExtendedDTO allegatoIstanza, Boolean flgODocumentobbligatorio, Integer tipoFirma, Integer errorCode, Boolean flgCancellato, Date dataCancellazione) {
        IndexMetadataPropertyDTO indexMetadataProperty = new IndexMetadataPropertyDTO();
        if (istanza != null) {
            indexMetadataProperty.setIdIstanza(String.valueOf(istanza.getIdIstanza()));
            indexMetadataProperty.setCodiceIstanza(istanza.getCodIstanza());
            indexMetadataProperty.setCodiceTipoAdempimento(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
            indexMetadataProperty.setDescrizioneTipoAdempimento(istanza.getAdempimento().getTipoAdempimento().getDesTipoAdempimento());
            indexMetadataProperty.setCodiceAdempimento(istanza.getAdempimento().getCodAdempimento());
            indexMetadataProperty.setDescrizioneAdempimento(istanza.getAdempimento().getDesAdempimento());
        }

        if (allegatoIstanza != null) {
            if (allegatoIstanza.getDimensioneUpload() != null) {
                indexMetadataProperty.setDimensioneByte(String.valueOf(allegatoIstanza.getDimensioneUpload()));
            }
            if (StringUtils.isNotBlank(allegatoIstanza.getCodAllegato())) {
                indexMetadataProperty.setCodiceAllegato(allegatoIstanza.getCodAllegato());
            }
            if (allegatoIstanza.getTipologiaAllegato() != null) {
                if (allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato() != null) {
                    if (StringUtils.isNotBlank(allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato())) {
                        indexMetadataProperty.setCodiceCategoriaAllegato(allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato());
                    }
                    if (StringUtils.isNotBlank(allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getDesCategoriaAllegato())) {
                        indexMetadataProperty.setDescrizioneCategoriaAllegato(allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getDesCategoriaAllegato());
                    }
                }
                if (StringUtils.isNotBlank(allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato())) {
                    indexMetadataProperty.setCodiceTipologiaAllegato(allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato());
                }
                if (StringUtils.isNotBlank(allegatoIstanza.getTipologiaAllegato().getDesTipologiaAllegato())) {
                    indexMetadataProperty.setCodiceTipologiaAllegato(allegatoIstanza.getTipologiaAllegato().getDesTipologiaAllegato());
                }
            }
            if (StringUtils.isNotBlank(allegatoIstanza.getNote())) {
                indexMetadataProperty.setNoteAllegato(allegatoIstanza.getNote());
            }
            // Aspect
            if (allegatoIstanza.getIndFirma() != null) {
                indexMetadataProperty.setFirmato(String.valueOf(allegatoIstanza.getIndFirma()));
            }
            if (allegatoIstanza.getFlgRiservato() != null) {
                indexMetadataProperty.setRiservato(String.valueOf(allegatoIstanza.getFlgRiservato()));
            }
        }
        indexMetadataProperty.setDocumentoObbligatorio(flgODocumentobbligatorio != null ? String.valueOf(flgODocumentobbligatorio) : null);

        /* Aspects */
        indexMetadataProperty.setTipoFirma(tipoFirma != null ? String.valueOf(tipoFirma) : null);
        indexMetadataProperty.setErrorCode(errorCode != null ? String.valueOf(errorCode) : null);
        indexMetadataProperty.setCancellato(flgCancellato != null ? String.valueOf(flgCancellato) : null);
       // indexMetadataProperty.setDataCancellazione(dataCancellazione != null ? String.valueOf(dataCancellazione) : null);
        // rollback 1492
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00");
        String formattedDate= dataCancellazione != null ? sdf.format(dataCancellazione) : null;
        //String formattedDate = sdf.format(dataCancellazione);
        indexMetadataProperty.setDataCancellazione(formattedDate);
        return indexMetadataProperty;
    }

    /**
     * @param codTipoAdempimento codice tipo adempimento
     * @param idIstanza          id istanza
     * @param filename           nome del file
     * @return SearchParams
     */
    private SearchParams getIndexSearchParams(String codTipoAdempimento, Long idIstanza, String filename) {
        logger.debug("[AllegatiManager::getIndexSearchParams] BEGIN");
        logger.debug("[AllegatiManager::getIndexSearchParams] Parametro in input codTipoAdempimento [" + codTipoAdempimento + "] - idIstanza [" + idIstanza + "] - filename [" + filename + "]");
        SearchParams searchParams = new SearchParams();
        String luceneQuery = "PATH:\"/app:company_home/cm:scriva";
        if (StringUtils.isNotBlank(codTipoAdempimento)) {
            luceneQuery += "/cm:" + codTipoAdempimento;
            if (idIstanza != null) {
                luceneQuery += "/cm:" + idIstanza;
            }
        }
        luceneQuery += StringUtils.isNotBlank(filename) ? "/cm:" + filename + "\"" : "/*\"";
        searchParams.setLuceneQuery(luceneQuery);
        logger.debug("[AllegatiManager::getIndexSearchParams] luceneQuery : [" + luceneQuery + "]");
        logger.debug("[AllegatiManager::getIndexSearchParams] END");
        return searchParams;
    }

    /**
     * Restituisce la lista dei valori della property trovata con il parametro passato in input
     *
     * @param properties       lista di property index
     * @param propertyToSearch property da ricercare
     * @return lista di valori
     */
    public List<String> getPropertyValues(List<Property> properties, String propertyToSearch) {
        List<Property> propertiesFiltered = properties.stream().filter(prop -> prop.getPrefixedName().equalsIgnoreCase(propertyToSearch)).collect(Collectors.toList());
        return !propertiesFiltered.isEmpty() ? propertiesFiltered.get(0).getValues() : null;
    }

    /**
     * Restituisce il valore della prima occorrenza della property trovata con il parametro passato in input
     *
     * @param properties       lista di property index
     * @param propertyToSearch property da ricercare
     * @return valore della prima occorrenza trovata
     */
    public String getPropertyValue(List<Property> properties, String propertyToSearch) {
        List<String> values = this.getPropertyValues(properties, propertyToSearch);
        return values != null ? values.get(0) : null;
    }

    /**
     * Gets share link id.
     *
     * @param sharingInfo the sharing info
     * @return the share link id
     */
    public String getShareLinkId(SharingInfo sharingInfo) {
        String sharedLink = sharingInfo != null ? sharingInfo.getSharedLink() : null;
        String[] sharedLinkSplitted = StringUtils.isNotBlank(sharedLink) ? sharedLink.split("/") : null;
        return sharedLinkSplitted != null && sharedLinkSplitted.length > 0 ? sharedLinkSplitted[sharedLinkSplitted.length - 1] : null;
    }

    /**
     * Test chiamate all'helper index
     *
     * @param idIstanza id istanza
     * @return Response
     */
    private Response testCallIndex(Long idIstanza) {
        String uuidIndex = "1b552159-976f-11eb-b6fc-33a38835d03a"; // cm:test.pdf
        String uuidRootFolder = "9f2b11a3-80c3-11eb-bc68-2941b0514579";
        List<IstanzaExtendedDTO> istanzeList = istanzaDAO.loadIstanza(idIstanza);

        if (null != istanzeList && !istanzeList.isEmpty()) {
            IstanzaExtendedDTO istanza = istanzeList.get(0);
            IndexMetadataPropertyDTO indexMetadataProperty = new IndexMetadataPropertyDTO();
            indexMetadataProperty.setIdIstanza(String.valueOf(idIstanza));
            indexMetadataProperty.setCodiceIstanza(istanza.getCodIstanza());
            indexMetadataProperty.setCodiceAllegato("codice allegato");
            indexMetadataProperty.setCodiceTipoAdempimento(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
            indexMetadataProperty.setDescrizioneTipoAdempimento(istanza.getAdempimento().getTipoAdempimento().getDesTipoAdempimento());
            indexMetadataProperty.setCodiceAdempimento(istanza.getAdempimento().getCodAdempimento());
            indexMetadataProperty.setDescrizioneAdempimento(istanza.getAdempimento().getDesAdempimento());
            indexMetadataProperty.setNoteAllegato("nuova nota aggiornata");
            indexMetadataProperty.setDocumentoObbligatorio("false");

            indexMetadataProperty.setRiservato("true");
            // update metadata
            indexServiceHelper.updateMetadata(uuidIndex, indexMetadataProperty);
            Node indexNode = indexServiceHelper.getContentMetadata(uuidIndex);
            return Response.ok(indexNode).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
        }

        /* Create folder */
        /*
        String folderName = "test_folder";
        String uuidFolder = indexServiceHelper.createContent(folderName, uuidRootFolder, null, null);
        if (StringUtils.isNotBlank(uuidFolder)) {
            LOGGER.info("[AllegatiManager::createContent] Creata folder [" + folderName + "] con uid [" + uuidFolder + "]");
        }
        */


        /* Delete */
        /*
        String uuidDelete = "cb84f4d6-93d4-11eb-b6fc-33a38835d03a";
        ErrorMessage errorMessage = indexServiceHelper.deleteContent(uuidDelete);
        if (errorMessage != null){
            return Response.serverError().entity(errorMessage).status(500).build();
        }
        */

        /* Recupero file */
        /*
        String uuidFileRetrieve = "81eba49b-92e2-11eb-b6fc-33a38835d03a";
        Node indexNode = indexServiceHelper.getContentMetadata(uuidFileRetrieve);
        if (indexNode != null) {
            LOGGER.info("\n\n" + indexNode.toString() + "\n\n");
            // non necessario se si recupera nome da db
            List<Property> properties = indexNode.getProperties();
            List<Property> propertyName = properties.stream().filter(prop -> prop.getPrefixedName().equalsIgnoreCase("cm:name")).collect(Collectors.toList());
            String filename = !propertyName.isEmpty() ? propertyName.get(0).getValues().get(0) : null;
            File file = indexServiceHelper.retrieveContentData(uuidFileRetrieve);
            if (file == null) {
                return Response.serverError().entity("Error retrieving file").status(500).build();
            } else {
                return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename="+ filename).build();
            }
        }
        */

        /* Verifica firma */
        /*
        File file = new File("D:\\download.pdf");
        VerifyReport verifyReport = null;
        try {
            verifyReport = indexServiceHelper.verifySignedDocument(file);
        } catch (GenericException e) {
            e.printStackTrace();
        }
        if (verifyReport != null) {
            return Response.ok(verifyReport).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
        }
        */

        /* Aggiornamento binario file */
        /*
        File file = new File("D:\\determinazioni.png");
        uuidIndex = "81eba49b-92e2-11eb-b6fc-33a38835d03a";
        String errorMessage = indexServiceHelper.updateContentData(uuidIndex, file);
        if (errorMessage != null) {
            return Response.serverError().entity(errorMessage).status(500).build();
        }
         */

        /* Ricerca */
        /*
        List<Node> nodeList = indexServiceHelper.luceneSearch(Boolean.TRUE, getIndexSearchParams(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento(), idIstanza, fileName));
        */

        return Response.ok().header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();

    }

}
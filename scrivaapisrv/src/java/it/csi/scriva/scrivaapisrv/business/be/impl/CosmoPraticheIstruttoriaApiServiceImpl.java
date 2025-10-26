/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.CosmoPraticheIstruttoriaApi;
import it.csi.scriva.scrivaapisrv.business.be.common.CustomMapper;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.DownloadFileFromURLServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AllegatiIstanzaApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.IstanzaEventiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.IstanzeApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.TipiEventoApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoEventoExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.FileFoDTO;
import it.csi.scriva.scrivaapisrv.dto.ObjectListFoDTO;
import it.csi.scriva.scrivaapisrv.dto.PraticaIstruttoriaDTO;
import it.csi.scriva.scrivaapisrv.dto.PraticaIstruttoriaOldDTO;
import it.csi.scriva.scrivaapisrv.dto.PubDocIstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.PubDocIstanzaLinkDTO;
import it.csi.scriva.scrivaapisrv.dto.PubIstanzaDTO;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Cosmo pratiche istruttoria api service.
 */
@Component
public class CosmoPraticheIstruttoriaApiServiceImpl extends AbstractApiServiceImpl implements CosmoPraticheIstruttoriaApi {

    private final String className = this.getClass().getSimpleName();

    private AllegatoIstanzaExtendedDTO allegatoIstanzaRollback = null;
    private IstanzaExtendedDTO istanzaRollback = null;
    private String uuidIndexRollback = null;

    private List<AllegatoIstanzaExtendedDTO> allegatoIstanzaRollbackList = new ArrayList<>();
    private List<String> uuidIndexRollbackList = new ArrayList<>();

    @Autowired
    private AllegatiIstanzaApiServiceHelper allegatiIstanzaApiServiceHelper;

    @Autowired
    private DownloadFileFromURLServiceHelper downloadFileFromURLServiceHelper;

    @Autowired
    private IstanzeApiServiceHelper istanzeApiServiceHelper;

    @Autowired
    private TipiEventoApiServiceHelper tipiEventoApiServiceHelper;

    @Autowired
    private IstanzaEventiApiServiceHelper istanzaEventiApiServiceHelper;

    @Autowired
    private CustomMapper customMapper;


    /**
     * Gestione pratiche istruttoria new response.
     *
     * @param codIstanza          the cod istanza
     * @param codTipoEvento       the cod tipo evento
     * @param flagCodicePratica   the flag codice pratica
     * @param flagPubblicaPratica the flag pubblica pratica
     * @param praticaIstruttoria  the pratica istruttoria
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    public Response gestionePraticheIstruttoriaNEW(String codIstanza, String codTipoEvento, Boolean flagCodicePratica, Boolean flagPubblicaPratica, PraticaIstruttoriaDTO praticaIstruttoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        logDebug(className, "Parametri in input cosmoPraticheIstruttoria [" + praticaIstruttoria != null && (praticaIstruttoria instanceof PraticaIstruttoriaDTO) ? praticaIstruttoria.toString() : "NULL" + "] - codIstanza [" + codIstanza + "] - codTipoEvento [" + codTipoEvento + "] - flagCodicePratica [" + flagCodicePratica + "] - flagPubblicaPratica [" + flagPubblicaPratica + "]");

        boolean trace;
        istanzaRollback = null;
        uuidIndexRollback = null;

        allegatoIstanzaRollbackList = new ArrayList<>();
        uuidIndexRollbackList = new ArrayList<>();
        try {
            MultivaluedMap<String, Object> httpHeadersMap = getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest);

            //Recupero istanza
            IstanzaExtendedDTO istanza = getIstanza(httpHeadersMap, codIstanza);

            //Verifica validità eventuale evento da tracciare
            trace = verifyTraceEvento(httpHeadersMap, codTipoEvento);

            if ((praticaIstruttoria instanceof PraticaIstruttoriaDTO)) {

                // Aggiornamento dati istanza
                istanzaRollback = updateDatiIstanza(httpHeadersMap, istanza.getIdIstanza(), istanza, praticaIstruttoria, flagCodicePratica, flagPubblicaPratica);

                // Aggiornamento documenti
                uuidIndexRollbackList = updateDocumentiIstanza(httpHeadersMap, istanza.getIdIstanza(), praticaIstruttoria);
            }

            // Tracciatura evento
            if (Boolean.TRUE.equals(trace)) {
                istanzaEventiApiServiceHelper.traceIstanzaEvento(httpHeadersMap, istanza.getIdIstanza(), codTipoEvento);
            }

        } catch (GenericException | ProcessingException e) {
            logError(className, e);
            try {
                rollback(httpHeaders, httpRequest);
            } catch (GenericException ex) {
                ErrorDTO err = ex.getError();
                logError(className, err);
            }
            if (e instanceof GenericException) {
                ErrorDTO err = ((GenericException) e).getError();
                logError(className, err);
                return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
            } else {
                String errorMessage = e.getMessage();
                ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
                return Response.serverError().entity(err).status(500).build();
            }
        } finally {
            logEnd(className);
        }
        return Response.ok("{}").status(200).build();

    }

    /**
     * Gets istanza.
     *
     * @param httpHeadersMap the http headers map
     * @param codIstanza     the cod istanza
     * @return the istanza
     * @throws GenericException the generic exception
     */
    private IstanzaExtendedDTO getIstanza(MultivaluedMap<String, Object> httpHeadersMap, String codIstanza) throws GenericException {
        logBegin(className);
        try {
            List<IstanzaExtendedDTO> istanzaList = istanzeApiServiceHelper.getIstanza(httpHeadersMap, codIstanza);
            if (istanzaList == null || istanzaList.isEmpty()) {
                ErrorDTO err = new ErrorDTO("404", "E100", "Parametri in ingresso non validi. Istanza non trovata con codice : [" + codIstanza + "].", null, null);
                logError(className, err);
                throw new GenericException(err);
            }
            return istanzaList.get(0);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Verify trace evento boolean.
     *
     * @param httpHeadersMap the http headers map
     * @param codTipoEvento  the cod tipo evento
     * @return the boolean
     * @throws GenericException the generic exception
     */
    private boolean verifyTraceEvento(MultivaluedMap<String, Object> httpHeadersMap, String codTipoEvento) throws GenericException {
        logBegin(className);
        try {
            if (StringUtils.isNotBlank(codTipoEvento)) {
                List<TipoEventoExtendedDTO> tipoEventoList = tipiEventoApiServiceHelper.getTipiEvento(httpHeadersMap, codTipoEvento);
                if (tipoEventoList == null || tipoEventoList.isEmpty()) {
                    ErrorDTO err = new ErrorDTO("400", "E100", "Parametri in ingresso non validi. Tipo evento non valido.", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
                return true;
            }
            return false;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets codice pratica.
     *
     * @param httpHeadersMap    the http headers map
     * @param flagCodicePratica the flag codice pratica
     * @param idIstanza         the id istanza
     * @return the codice pratica
     * @throws GenericException the generic exception
     */
    private String getCodicePratica(MultivaluedMap<String, Object> httpHeadersMap, boolean flagCodicePratica, Long idIstanza) throws GenericException {
        logBegin(className);
        try {
            String codicePratica = null;
            if (Boolean.TRUE.equals(flagCodicePratica)) {
                List<String> strings = istanzeApiServiceHelper.getCodicePratica(httpHeadersMap, idIstanza);
                if (strings == null || strings.isEmpty()) {
                    ErrorDTO err = new ErrorDTO("500", "E100", "Errore nella generazione del codice della pratica.", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
                codicePratica = strings.get(0);
            }
            return codicePratica;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update dati istanza istanza extended dto.
     *
     * @param httpHeadersMap      the http headers map
     * @param idIstanza           the id istanza
     * @param istanza             the istanza
     * @param praticaIstruttoria  the pratica istruttoria
     * @param creaCodicePratica   the crea codice pratica
     * @param flagPubblicaPratica the flag pubblica pratica
     * @return the istanza extended dto
     * @throws GenericException the generic exception
     */
    private IstanzaExtendedDTO updateDatiIstanza(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, IstanzaExtendedDTO istanza, PraticaIstruttoriaDTO praticaIstruttoria, boolean creaCodicePratica, boolean flagPubblicaPratica) throws GenericException {
        logBegin(className);
        IstanzaExtendedDTO istanzaClonata = null;
        try {
            if ((praticaIstruttoria instanceof PraticaIstruttoriaDTO) && praticaIstruttoria.getDatiIstanza() != null) {
                PubIstanzaDTO datiIstanza = praticaIstruttoria.getDatiIstanza();
                if (datiIstanza != null) {
                    //se i dati si riferiscono ad una istanza diversa
                    if (StringUtils.isBlank(datiIstanza.getCodIstanza()) || !istanza.getCodIstanza().equals(datiIstanza.getCodIstanza())) {
                        ErrorDTO err = new ErrorDTO("400", "E100", "Parametri in ingresso non validi. Dati istanza e codice istanza non coerenti.", null, null);
                        logError(className, err);
                        throw new GenericException(err);
                    }
                    istanzaClonata = SerializationUtils.clone(istanza);
                    fillIstanzaToUpdate(istanza, datiIstanza);
                    IstanzaExtendedDTO istanzaUpdated = istanzeApiServiceHelper.updateIstanza(httpHeadersMap, istanza, creaCodicePratica);
                    if (istanzaUpdated == null) {
                        ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante l'aggiornamento dell'istanza", null, null);
                        logError(className, err);
                        throw new GenericException(err);
                    }
                }
            } else if (Boolean.TRUE.equals(creaCodicePratica)) {
                istanzaClonata = SerializationUtils.clone(istanza);
                IstanzaExtendedDTO istanzaUpdated = istanzeApiServiceHelper.updateIstanza(httpHeadersMap, istanza, creaCodicePratica);
                if (istanzaUpdated == null) {
                    ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante l'aggiornamento dell'istanza", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            }
            if (Boolean.TRUE.equals(flagPubblicaPratica)) {
                istanzaClonata = istanzaClonata == null ? SerializationUtils.clone(istanza) : istanzaClonata;
                List<IstanzaExtendedDTO> istanzaUpdatedList = istanzeApiServiceHelper.pubblicaIstanza(httpHeadersMap, idIstanza);
                if (istanzaUpdatedList == null || istanzaUpdatedList.isEmpty()) {
                    ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante la pubblicazione dell'istanza", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            }
        } finally {
            logEnd(className);
        }
        return istanzaClonata;
    }

    /**
     * Update documenti istanza list.
     *
     * @param httpHeadersMap     the http headers map
     * @param idIstanza          the id istanza
     * @param praticaIstruttoria the pratica istruttoria
     * @return the list
     * @throws GenericException the generic exception
     */
    private List<String> updateDocumentiIstanza(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, PraticaIstruttoriaDTO praticaIstruttoria) throws GenericException {
        logBegin(className);
        List<String> uuidIndexList = new ArrayList<>();
        try {
            if ((praticaIstruttoria instanceof PraticaIstruttoriaDTO) && !CollectionUtils.isEmpty(praticaIstruttoria.getDatiDocumento())) {
                for (PubDocIstanzaLinkDTO pubDocIstanzaLink : praticaIstruttoria.getDatiDocumento()) {
                    String linkDocumento = pubDocIstanzaLink.getLinkDocumento();
                    String codAllegatoPadre = pubDocIstanzaLink.getCodAllegatoPadre();
                    AllegatoIstanzaExtendedDTO allegatoIstanzaAlreadyExists = getAllegatoIstanza(httpHeadersMap, idIstanza, pubDocIstanzaLink.getCodAllegato());
                    AllegatoIstanzaExtendedDTO allegatoIstanzaPadre = StringUtils.isNotBlank(codAllegatoPadre) ? getAllegatoIstanza(httpHeadersMap, idIstanza, codAllegatoPadre) : null;
                    //mappo sull'oggetto da salvare
                    AllegatoIstanzaExtendedDTO allegatoIstanzaDatiDocumento = customMapper.map(pubDocIstanzaLink, AllegatoIstanzaExtendedDTO.class);
                    allegatoIstanzaDatiDocumento.setIdIstanza(idIstanza);
                    allegatoIstanzaDatiDocumento.setIdAllegatoIstanzaPadre(allegatoIstanzaPadre != null ? allegatoIstanzaPadre.getIdAllegatoIstanza() : null);
                    //se il file esiste aggiorno i metadati, altrimenti (se mi è stato inviato il link) effettuo upload del nuovo file
                    String uuidIndex = updateUploadAllegato(httpHeadersMap, idIstanza, allegatoIstanzaAlreadyExists, linkDocumento, allegatoIstanzaDatiDocumento);
                    if (StringUtils.isNotBlank(uuidIndex)) {
                        uuidIndexList.add(uuidIndex);
                    }
                    if (allegatoIstanzaAlreadyExists != null) {
                        allegatoIstanzaRollbackList.add(allegatoIstanzaAlreadyExists);
                    }
                }
            }
        } finally {
            logEnd(className);
        }
        return uuidIndexList;
    }

    /**
     * Update upload allegato string.
     *
     * @param httpHeadersMap               the http headers map
     * @param idIstanza                    the id istanza
     * @param allegatoIstanzaRollback      the allegato istanza rollback
     * @param linkDocumento                the link documento
     * @param allegatoIstanzaDatiDocumento the allegato istanza dati documento
     * @return the string
     * @throws GenericException the generic exception
     */
    public String updateUploadAllegato(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, AllegatoIstanzaExtendedDTO allegatoIstanzaRollback, String linkDocumento, AllegatoIstanzaExtendedDTO allegatoIstanzaDatiDocumento) throws GenericException {
        ErrorDTO err;
        String uuidIndex = null;
        String codAllegato = allegatoIstanzaDatiDocumento.getCodAllegato();
        // Allegato esistente. Permesso solo aggiornamento dati
        if (allegatoIstanzaRollback != null) {
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaNewList = allegatiIstanzaApiServiceHelper.updateIndexFile(httpHeadersMap, getAllegatoIstanzaAggiornato(allegatoIstanzaRollback, allegatoIstanzaDatiDocumento));
            if (CollectionUtils.isEmpty(allegatoIstanzaNewList)) {
                err = new ErrorDTO("500", "E100", "Errore nel caricamento/aggiornamento documento.", null, null);
                logError(className, err);
                throw new GenericException(err);
            }
            // Allegato non presente. Effettuo upload del file
        } else if (StringUtils.isNotBlank(linkDocumento)) {
            FileFoDTO fileFo = allegatiIstanzaApiServiceHelper.getAllegatiCosmo(httpHeadersMap, linkDocumento);
            if (fileFo != null) {
                String fileName = StringUtils.isNotBlank(allegatoIstanzaDatiDocumento.getNomeAllegato()) ? allegatoIstanzaDatiDocumento.getNomeAllegato() : fileFo.getFileName();
                List<AllegatoIstanzaExtendedDTO> allegatoIstanzaNewList = allegatiIstanzaApiServiceHelper.uploadIndexFile(httpHeadersMap, fileFo.getFile(), allegatoIstanzaDatiDocumento, fileName);
                //se va tutto bene memorizzo lo uuid index del file per una eventuale canellazione
                if (allegatoIstanzaNewList != null && !allegatoIstanzaNewList.isEmpty()) {
                    uuidIndex = allegatoIstanzaNewList.get(0).getUuidIndex();
                } else {
                    err = new ErrorDTO("500", "E100", "Errore nel caricamento del documento.", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            } else {
                err = new ErrorDTO("500", "E100", "Errore nel recupero file da link.", null, null);
                logError(className, err);
                throw new GenericException(err);
            }
        } else {
            err = new ErrorDTO("400", "E100", "Errore nei dati del documento " + codAllegato + ". E' un nuovo documento ma non è presente il link per il recupero.", null, null);
            logError(className, err);
            throw new GenericException(err);
        }
        return uuidIndex;
    }

    /**
     * Gets allegato istanza aggiornato.
     *
     * @param allegatoIstanzaRollback      the allegato istanza rollback
     * @param allegatoIstanzaDatiDocumento the allegato istanza dati documento
     * @return the allegato istanza aggiornato
     */
    public AllegatoIstanzaExtendedDTO getAllegatoIstanzaAggiornato(AllegatoIstanzaExtendedDTO allegatoIstanzaRollback, AllegatoIstanzaExtendedDTO allegatoIstanzaDatiDocumento) {
        logBegin(className);
        AllegatoIstanzaExtendedDTO allegatoIstanzaAggiornato = customMapper.map(allegatoIstanzaRollback, AllegatoIstanzaExtendedDTO.class);
        customMapper.map(allegatoIstanzaDatiDocumento, allegatoIstanzaAggiornato);
        logEnd(className);
        return allegatoIstanzaAggiornato;
    }

    /**
     * Gets allegato istanza already exists.
     *
     * @param httpHeadersMap the http headers map
     * @param idIstanza      the id istanza
     * @param codAllegato    the cod allegato
     * @return the allegato istanza already exists
     * @throws GenericException the generic exception
     */
    public AllegatoIstanzaExtendedDTO getAllegatoIstanza(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, String codAllegato) throws GenericException {
        logBegin(className);
        try {
            ObjectListFoDTO objectListFoDTO = allegatiIstanzaApiServiceHelper.getAllegati(httpHeadersMap, idIstanza, codAllegato, null, null, null, true);
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = (List<AllegatoIstanzaExtendedDTO>) objectListFoDTO.getObjectList();
            //verifico se esiste un allegato con lo stesso codice allegato
            if (!CollectionUtils.isEmpty(allegatoIstanzaList)) {
                for (AllegatoIstanzaExtendedDTO allegatoIstanzaTemp : allegatoIstanzaList) {
                    if (allegatoIstanzaTemp.getCodAllegato().equals(codAllegato)) {
                        return allegatoIstanzaTemp;
                    }
                }
            }
            return null;
        } finally {
            logEnd(className);
        }
    }

    /*
    private Long getIdAllegatoPadre(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, String codAllegatoPadre) {

    }

     */

    /**
     * Fill istanza to update.
     *
     * @param istanza       the istanza
     * @param pubIstanzaDTO the pub istanza dto
     */
    private void fillIstanzaToUpdate(IstanzaExtendedDTO istanza, PubIstanzaDTO pubIstanzaDTO) {
        logBegin(className);
        if (StringUtils.isNotBlank(pubIstanzaDTO.getCodPratica())) {
            istanza.setCodPratica(pubIstanzaDTO.getCodPratica());
        }
        if (pubIstanzaDTO.getDataInizioOsservazione() != null) {
            istanza.setDataInizioOsservazioni(pubIstanzaDTO.getDataInizioOsservazione());
        }
        if (pubIstanzaDTO.getDataFineOsservazione() != null) {
            istanza.setDataFineOsservazioni(pubIstanzaDTO.getDataFineOsservazione());
        }
        if (pubIstanzaDTO.getDataProtocollo() != null) {
            istanza.setDataProtocolloIstanza(pubIstanzaDTO.getDataProtocollo());
        }
        if (StringUtils.isNotBlank(pubIstanzaDTO.getNumeroProtocollo())) {
            istanza.setNumProtocolloIstanza(pubIstanzaDTO.getNumeroProtocollo());
        }
        if (pubIstanzaDTO.getDataPubblicazione() != null) {
            istanza.setDataPubblicazione(pubIstanzaDTO.getDataPubblicazione());
        }
        /*
        if (pubIstanzaDTO.getDataInserimentoIstanza()!=null) {
            istanza.setDataInserimentoIstanza(pubIstanzaDTO.getDataInserimentoIstanza());
        }
        if (pubIstanzaDTO.getDataInserimentoPratica()!=null) {
            istanza.setDataInserimentoPratica(pubIstanzaDTO.getDataInserimentoPratica());
        }
        if (pubIstanzaDTO.getDataModificaIstanza()!=null) {
            istanza.setDataModificaIstanza(pubIstanzaDTO.getDataModificaIstanza());
        }
        if (pubIstanzaDTO.getDataModificaPratica()!=null) {
            istanza.setDataModificaPratica(pubIstanzaDTO.getDataModificaPratica());
        }
        if (pubIstanzaDTO.getDesStatoSintesiPagamento()!= null) {
            istanza.setDesStatoSintesiPagamento(pubIstanzaDTO.getDesStatoSintesiPagamento());
        }
        if (StringUtils.isNotBlank(pubIstanzaDTO.getDesEsitoProcedimentoStatale())) {
            istanza.setDesEsitoProcedimentoStatale(pubIstanzaDTO.getDesEsitoProcedimentoStatale());
        }
        if (StringUtils.isNotBlank(pubIstanzaDTO.getDesStatoProcedimentoStatale())) {
            istanza.setDesStatoProcedimentoStatale(pubIstanzaDTO.getDesStatoProcedimentoStatale());
        }
        */
        logEnd(className);
    }

    /**
     * Rollback.
     *
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @throws GenericException the generic exception
     */
    private void rollback(HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        if (null != istanzaRollback) {
            istanzeApiServiceHelper.updateIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaRollback, Boolean.FALSE);
        }
        if (!CollectionUtils.isEmpty(uuidIndexRollbackList)) {
            for (String uuidIndex : uuidIndexRollbackList) {
                allegatiIstanzaApiServiceHelper.deleteIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uuidIndex);
            }
        }
        if (!CollectionUtils.isEmpty(allegatoIstanzaRollbackList)) {
            for (AllegatoIstanzaExtendedDTO allegatoIstanza : allegatoIstanzaRollbackList) {
                allegatiIstanzaApiServiceHelper.updateIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), allegatoIstanza);
            }
        }
        logEnd(className);
    }

    /**
     * Get null property names string [].
     *
     * @param source the source
     * @return the string [ ]
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /********************
     **
     **     OLD
     **
     ********************/

    /**
     * Gestione pratiche istruttoria response.
     *
     * @param codIstanza          the cod istanza
     * @param codTipoEvento       the cod tipo evento
     * @param flagCodicePratica   the flag codice pratica
     * @param flagPubblicaPratica the flag pubblica pratica
     * @param praticaIstruttoria  the cosmo pratiche istruttoria
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    public Response gestionePraticheIstruttoriaOLD(String codIstanza, String codTipoEvento, Boolean flagCodicePratica, Boolean flagPubblicaPratica,
                                                   PraticaIstruttoriaOldDTO praticaIstruttoria,
                                                   SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        logDebug(className, "Parametri in input cosmoPraticheIstruttoria [" + praticaIstruttoria != null && (praticaIstruttoria instanceof PraticaIstruttoriaOldDTO) ? praticaIstruttoria.toString() : "NULL" + "] - codIstanza [" + codIstanza + "] - codTipoEvento [" + codTipoEvento + "] - flagCodicePratica [" + flagCodicePratica + "] - flagPubblicaPratica [" + flagPubblicaPratica + "]");

        boolean trace = false;
        try {
            MultivaluedMap<String, Object> httpHeadersMap = getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest);

            //Recupero istanza
            IstanzaExtendedDTO istanza = getIstanza(httpHeadersMap, codIstanza);

            //Verifica validità eventuale evento da tracciare
            trace = verifyTraceEvento(httpHeadersMap, codTipoEvento);

            if (praticaIstruttoria != null && (praticaIstruttoria instanceof PraticaIstruttoriaOldDTO)) {
                //Verifica validità dati per allegati
                verifyAllegatoIstanza(httpHeadersMap, praticaIstruttoria);

                // Aggiornamento dati istanza
                istanzaRollback = updateDatiIstanzaOLD(httpHeadersMap, istanza.getIdIstanza(), istanza, praticaIstruttoria, flagCodicePratica, flagPubblicaPratica);

                // Aggiornamento documenti
                uuidIndexRollback = updateDocumentiIstanzaOLD(httpHeadersMap, istanza.getIdIstanza(), praticaIstruttoria);
            }

            // Tracciatura evento
            if (Boolean.TRUE.equals(trace)) {
                istanzaEventiApiServiceHelper.traceIstanzaEvento(httpHeadersMap, istanza.getIdIstanza(), codTipoEvento);
            }

        } catch (GenericException | ProcessingException e) {
            logError(className, e);
            try {
                rollbackOLD(httpHeaders, httpRequest, allegatoIstanzaRollback, istanzaRollback, uuidIndexRollback);
            } catch (GenericException ex) {
                ErrorDTO err = ex.getError();
                logError(className, err);
            }
            if (e instanceof GenericException) {
                ErrorDTO err = ((GenericException) e).getError();
                logError(className, err);
                return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
            } else {
                String errorMessage = e.getMessage();
                ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
                return Response.serverError().entity(err).status(500).build();
            }
        } finally {
            logEnd(className);
        }
        return Response.ok("{}").status(200).build();
    }

    /**
     * Verify allegato istanza.
     *
     * @param httpHeadersMap     the http headers map
     * @param praticaIstruttoria the pratica istruttoria
     * @throws GenericException the generic exception
     */
    public void verifyAllegatoIstanza(MultivaluedMap<String, Object> httpHeadersMap, PraticaIstruttoriaOldDTO praticaIstruttoria) throws GenericException {
        logBegin(className);
        try {
            if (praticaIstruttoria != null && (praticaIstruttoria instanceof PraticaIstruttoriaOldDTO) && StringUtils.isNotBlank(praticaIstruttoria.getLinkDocumento()) && praticaIstruttoria.getDatiDocumento() == null) {
                ErrorDTO err = new ErrorDTO("400", "E100", "Parametri in ingresso non validi. Indicato link documento ma dati assenti.", null, null);
                logError(className, err);
                throw new GenericException(err);
            }
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update dati istanza.
     *
     * @param httpHeadersMap     the http headers map
     * @param idIstanza          the id istanza
     * @param istanza            the istanza
     * @param praticaIstruttoria the cosmo pratica istruttoria
     * @param creaCodicePratica  the crea codice pratica
     * @throws GenericException the generic exception
     */
    private IstanzaExtendedDTO updateDatiIstanzaOLD(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, IstanzaExtendedDTO istanza, PraticaIstruttoriaOldDTO praticaIstruttoria, boolean creaCodicePratica, boolean flagPubblicaPratica) throws GenericException {
        logBegin(className);
        IstanzaExtendedDTO istanzaClonata = null;
        try {
            if (praticaIstruttoria != null && (praticaIstruttoria instanceof PraticaIstruttoriaOldDTO) && praticaIstruttoria.getDatiIstanza() != null) {
                PubIstanzaDTO datiIstanza = praticaIstruttoria.getDatiIstanza();
                if (datiIstanza != null) {
                    //se i dati si riferiscono ad una istanza diversa
                    if (StringUtils.isBlank(datiIstanza.getCodIstanza()) || !istanza.getCodIstanza().equals(datiIstanza.getCodIstanza())) {
                        ErrorDTO err = new ErrorDTO("400", "E100", "Parametri in ingresso non validi. Dati istanza e codice istanza non coerenti.", null, null);
                        logError(className, err);
                        throw new GenericException(err);
                    }
                    istanzaClonata = SerializationUtils.clone(istanza);
                    fillIstanzaToUpdate(istanza, datiIstanza);
                    IstanzaExtendedDTO istanzaUpdated = istanzeApiServiceHelper.updateIstanza(httpHeadersMap, istanza, creaCodicePratica);
                    if (istanzaUpdated == null) {
                        ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante l'aggiornamento dell'istanza", null, null);
                        logError(className, err);
                        throw new GenericException(err);
                    }
                }
            } else if (Boolean.TRUE.equals(creaCodicePratica)) {
                istanzaClonata = SerializationUtils.clone(istanza);
                IstanzaExtendedDTO istanzaUpdated = istanzeApiServiceHelper.updateIstanza(httpHeadersMap, istanza, creaCodicePratica);
                if (istanzaUpdated == null) {
                    ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante l'aggiornamento dell'istanza", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            }
            if (Boolean.TRUE.equals(flagPubblicaPratica)) {
                istanzaClonata = istanzaClonata == null ? SerializationUtils.clone(istanza) : istanzaClonata;
                List<IstanzaExtendedDTO> istanzaUpdatedList = istanzeApiServiceHelper.pubblicaIstanza(httpHeadersMap, idIstanza);
                if (istanzaUpdatedList == null || istanzaUpdatedList.isEmpty()) {
                    ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante la pubblicazione dell'istanza", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            }
        } finally {
            logEnd(className);
        }
        return istanzaClonata;
    }

    /**
     * Update documenti istanza string.
     *
     * @param httpHeadersMap     the http headers map
     * @param idIstanza          the id istanza
     * @param praticaIstruttoria the cosmo pratica istruttoria
     * @return the string
     * @throws GenericException the generic exception
     */
    private String updateDocumentiIstanzaOLD(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, PraticaIstruttoriaOldDTO praticaIstruttoria) throws GenericException {
        logBegin(className);
        String uuidIndex = null;
        try {
            if (praticaIstruttoria != null && (praticaIstruttoria instanceof PraticaIstruttoriaOldDTO) && (StringUtils.isNotBlank(praticaIstruttoria.getLinkDocumento()) || praticaIstruttoria.getDatiDocumento() != null)) {
                String linkDocumento = praticaIstruttoria.getLinkDocumento();
                PubDocIstanzaExtendedDTO pubDocIstanzaDatiDocumento = praticaIstruttoria.getDatiDocumento();
                AllegatoIstanzaExtendedDTO allegatoIstanzaDatiDocumento = customMapper.map(pubDocIstanzaDatiDocumento, AllegatoIstanzaExtendedDTO.class);
                allegatoIstanzaDatiDocumento.setIdIstanza(idIstanza);
                this.allegatoIstanzaRollback = getAllegatoIstanza(httpHeadersMap, idIstanza, allegatoIstanzaDatiDocumento.getCodAllegato());
                //se il file esiste aggiorno i metadati, altrimenti (se mi è stato inviato il link) effettuo upload del nuovo file
                uuidIndex = updateUploadAllegato(httpHeadersMap, idIstanza, allegatoIstanzaRollback, linkDocumento, allegatoIstanzaDatiDocumento);
            }
        } finally {
            logEnd(className);
        }
        return uuidIndex;
    }

    /**
     * Update upload allegato string.
     *
     * @param httpHeadersMap               the http headers map
     * @param idIstanza                    the id istanza
     * @param allegatoIstanzaRollback      the allegato istanza rollback
     * @param linkDocumento                the link documento
     * @param allegatoIstanzaDatiDocumento the allegato istanza dati documento
     * @return the string
     * @throws GenericException the generic exception
     */
    private String updateUploadAllegatoOLD(MultivaluedMap<String, Object> httpHeadersMap, Long idIstanza, AllegatoIstanzaExtendedDTO allegatoIstanzaRollback, String linkDocumento, AllegatoIstanzaExtendedDTO allegatoIstanzaDatiDocumento) throws GenericException {
        ErrorDTO err;
        String uuidIndex = null;
        // Allegato esistente. Permesso solo aggiornamento dati
        if (allegatoIstanzaRollback != null) {
            if (StringUtils.isNotBlank(linkDocumento)) {
                err = new ErrorDTO("400", "E100", "Parametri in ingresso non validi. Codice allegato già presente, non è possibile effettuare upload di file.", null, null);
                logError(className, err);
                throw new GenericException(err);
            } else {
                List<AllegatoIstanzaExtendedDTO> allegatoIstanzaNewList = allegatiIstanzaApiServiceHelper.updateIndexFile(httpHeadersMap, getAllegatoIstanzaAggiornato(allegatoIstanzaRollback, allegatoIstanzaDatiDocumento));
                if (allegatoIstanzaNewList == null || allegatoIstanzaNewList.isEmpty()) {
                    err = new ErrorDTO("500", "E100", "Errore nel caricamento/aggiornamento documento.", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            }
            // Allegato non presente. Effettuo upload del file
        } else if (StringUtils.isNotBlank(linkDocumento)) {
            FileFoDTO fileFo = allegatiIstanzaApiServiceHelper.getAllegatiCosmo(httpHeadersMap, linkDocumento);
            if (fileFo != null) {
                List<AllegatoIstanzaExtendedDTO> allegatoIstanzaNewList = allegatiIstanzaApiServiceHelper.uploadIndexFile(httpHeadersMap, fileFo.getFile(), allegatoIstanzaDatiDocumento, fileFo.getFileName());
                //se va tutto bene memorizzo lo uuid index del file per una eventuale canellazione
                if (allegatoIstanzaNewList != null && !allegatoIstanzaNewList.isEmpty()) {
                    uuidIndex = allegatoIstanzaNewList.get(0).getUuidIndex();
                } else {
                    err = new ErrorDTO("500", "E100", "Errore nel caricamento del documento.", null, null);
                    logError(className, err);
                    throw new GenericException(err);
                }
            } else {
                err = new ErrorDTO("500", "E100", "Errore nel recupero file da link.", null, null);
                logError(className, err);
                throw new GenericException(err);
            }
        }
        return uuidIndex;
    }


    /**
     * Rollback.
     *
     * @param httpHeaders                        the http headers
     * @param httpRequest                        the http request
     * @param allegatoIstanzaExtendedDTORollback the allegato istanza extended dto rollback
     * @param istanzaRollback                    the istanza rollback
     * @param uuidIndexRollback                  the uuid index rollback
     * @throws it.csi.scriva.scrivaapisrv.business.be.exception.GenericException the generic exception
     */
    private void rollbackOLD(HttpHeaders httpHeaders, HttpServletRequest httpRequest, AllegatoIstanzaExtendedDTO
            allegatoIstanzaExtendedDTORollback, IstanzaExtendedDTO istanzaRollback, String uuidIndexRollback) throws
            GenericException {
        logBegin(className);
        if (null != istanzaRollback) {
            istanzeApiServiceHelper.updateIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaRollback, Boolean.FALSE);
        }
        if (StringUtils.isNotBlank(uuidIndexRollback)) {
            allegatiIstanzaApiServiceHelper.deleteIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uuidIndexRollback);
        }
        if (allegatoIstanzaExtendedDTORollback != null) {
            allegatiIstanzaApiServiceHelper.updateIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), allegatoIstanzaExtendedDTORollback);
        }
        logEnd(className);
    }
}
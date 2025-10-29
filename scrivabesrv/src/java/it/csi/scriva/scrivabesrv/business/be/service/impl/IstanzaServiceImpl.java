/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import java.io.File;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaResponsabiliDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaStatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.ActionRoleManager;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.validation.ValidationUtil;


/**
 * The type Istanza service.
 */
@Component
public class IstanzaServiceImpl extends BaseApiServiceImpl implements IstanzaService {

    private final String className = this.getClass().getSimpleName();
    

    /**
     * The Allegato istanza dao.
     */
    @Autowired
    AllegatoIstanzaDAO allegatoIstanzaDAO;
    /**
     * The Adempimento tipo allegato dao.
     */
    @Autowired
    AdempimentoTipoAllegatoDAO adempimentoTipoAllegatoDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Istanza attore dao.
     */
    @Autowired
    IstanzaAttoreDAO istanzaAttoreDAO;

    /**
     * The Istanza stato dao.
     */
    @Autowired
    IstanzaStatoDAO istanzaStatoDAO;

    /**
     * The Stato istanza dao.
     */
    @Autowired
    StatoIstanzaDAO statoIstanzaDAO;

    /**
     * The Allegati service.
     */
    @Autowired
    AllegatiService allegatiService;

    @Autowired
    ActionRoleManager actionRoleManager;

    /**
     * The Allegati manager.
     */
    @Autowired
    AllegatiManager allegatiManager;

    /**
     * The Istanza attore manager.
     */
    @Autowired
    IstanzaAttoreManager istanzaAttoreManager;

    /**
     * The Istanza evento service.
     */
    @Autowired
    IstanzaEventoService istanzaEventoService;

    /**
     * The Istanza responsabili dao.
     */
    @Autowired
    IstanzaResponsabiliDAO istanzaResponsabiliDAO;

    /**
     * Gets istanza.
     *
     * @param idIstanza the id istanza
     * @return the istanza
     */
    @Override
    public IstanzaExtendedDTO getIstanza(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        logEnd(className);
        return istanzaList != null && !istanzaList.isEmpty() ? istanzaList.get(0) : null;
    }

    /**
     * Update istanza istanza extended dto.
     *
     * @param istanza the istanza
     * @return the istanza extended dto
     */
    @Override
    public IstanzaExtendedDTO updateIstanza(Boolean flgCreaPratica, IstanzaExtendedDTO istanza, AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, istanza);
        ErrorDTO error;
        Integer res;
        /* // Test lock
        try {
            lock.readLock().lock();
            res = istanzaDAO.updateIstanza(istanza.getDTO(), this.saveStorico(istanza.getIdStatoIstanza()), flgCreaPratica);
        } finally {
            lock.readLock().unlock();
        }
        */
        IstanzaDTO istanzaOld = istanzaDAO.findByPK(istanza.getIdIstanza());
        res = istanzaDAO.updateIstanza(istanza.getDTO(), this.saveStorico(istanza.getStatoIstanza().getIdStatoIstanza()), flgCreaPratica);
        if (res == null || res < 1) {
            error = res == null ? getErrorManager().getError("500", "E100", null, null, null) : getErrorManager().getError("404", "", "Errore nell'aggiornamento dell'elemento;  causa: elemento non trovato", null, null);
            logError(className, error);
            throw new GenericException(error);
        } else {
            istanza = istanzaDAO.loadIstanza(istanza.getIdIstanza()).get(0);
            // (SCRIVA-1144) Se la data protocollo o il numero protocollo viene aggiornato, verrà aggiornato anche la data
            // e il numero protocollo dei documento dell'istanza, dell'elenco allegati e dei relativi figli
            if (istanzaOld.getDataProtocolloIstanza() != istanza.getDataProtocolloIstanza() || (istanzaOld.getNumProtocolloIstanza() != null && !istanzaOld.getNumProtocolloIstanza().equalsIgnoreCase(istanza.getNumProtocolloIstanza()))) {
                updateProtocolloAllegatiIstanza(istanza, attoreScriva);
            }

            istanza = this.updateJsonDataIstanza(istanza);
            istanzaDAO.updateJsonDataIstanza(istanza.getDTO());
            logEnd(className);
            return istanza;
        }
    }

    /**
     * Update json data istanza istanza extended dto.
     *
     * @param istanza the istanza
     * @return the istanza extended dto
     */
    @Override
    public IstanzaExtendedDTO updateJsonDataIstanza(IstanzaExtendedDTO istanza) {
        logBeginInfo(className, istanza);
        if (null != istanza) {
            JSONObject jsonIstanza = null; //istanza.toJsonObj();
/*
            JSONObject jsonDataIstanza = null != istanza.getJsonData() ? new JSONObject(istanza.getJsonData()) : new JSONObject();
            JSONObject newJson = jsonDataIstanza.put("QDR_ISTANZA", jsonIstanza);
            if (null != newJson) {
                istanza.setJsonData(newJson.toString());
            }
*/

            JSONObject jsonDataIstanza = null != istanza.getJsonData() ? new JSONObject(istanza.getJsonData()) : new JSONObject();
            if (null != istanza.getJsonData()) {
                JSONArray jsonCompetenzeTerritorioArray;
                try {
                    jsonCompetenzeTerritorioArray = new JSONObject(istanza.getJsonData()).getJSONObject("QDR_ISTANZA").getJSONArray("competenze_territorio");
                } catch (Exception e) {
                    jsonCompetenzeTerritorioArray = null;
                }

                if (jsonCompetenzeTerritorioArray != null) {
                    jsonIstanza = istanza.toJsonObj().put("competenze_territorio", jsonCompetenzeTerritorioArray);
                }

                // SCRIVA-265 : ORIENTAMENTO - salvataggio dichiarazione selezionata
                JSONArray jsonQdrConfigDchrArray;
                try {
                    jsonQdrConfigDchrArray = new JSONObject(istanza.getJsonData()).getJSONArray("dichiarazioni");
                } catch (Exception e) {
                    jsonQdrConfigDchrArray = null;
                }

                if (jsonQdrConfigDchrArray != null) {
                    jsonIstanza = jsonIstanza == null ? istanza.toJsonObj().put("dichiarazioni", jsonQdrConfigDchrArray) : jsonIstanza.put("dichiarazioni", jsonQdrConfigDchrArray);
                    jsonDataIstanza.remove("dichiarazioni");
                } else {
                    jsonIstanza = istanza.toJsonObj();
                }

            }
            JSONObject newJson = jsonDataIstanza.put("QDR_ISTANZA", jsonIstanza);
            if (null != newJson) {
                istanza.setJsonData(newJson.toString());
            }
        }
        logEnd(className);
        return istanza;
    }

    /**
     * Save istanza attore long.
     * 
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the long
     */
    @Override
    public Long saveIstanzaAttore(Long idIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, idIstanza);
        String codiceFiscale = attoreScriva.getCodiceFiscale();
        String componenteApp = attoreScriva.getComponente();
        // se l'istanza viene inserita da BO, il compilante è fittizio e la componente settata sarà FO
        if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
            codiceFiscale = Constants.CF_COMPILANTE_FITTIZIO_BO;
            componenteApp = ComponenteAppEnum.FO.name();
        }
        logEnd(className);
        return istanzaAttoreDAO.saveIstanzaAttore(istanzaAttoreManager.createIstanzaAttoreCompilante(codiceFiscale, idIstanza, ComponenteAppEnum.findByDescr(componenteApp), codiceFiscale));
    }

    /**
     * Gets cod tipologia allegato.
     *
     * @param idIstanza the id istanza
     * @return the cod tipologia allegato
     */
    @Override
    public String getCodTipologiaAllegato(Long idIstanza) {
        logBegin(className);
        return adempimentoTipoAllegatoDAO.loadCodTipologiaAllegatoByIdIstanza(idIstanza);
    }

    /**
     * Gets adempimento tipo allegato.
     *
     * @param codAllegato   the cod allegato
     * @param idAdempimento the id adempimento
     * @return the adempimento tipo allegato
     */
    @Override
    public AdempimentoTipoAllegatoExtendedDTO getAdempimentoTipoAllegato(String codAllegato, Long idAdempimento) {
        logBegin(className);
        List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoList = adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodAllegato(codAllegato, idAdempimento);
        return adempimentoTipoAllegatoList != null && !adempimentoTipoAllegatoList.isEmpty() ? adempimentoTipoAllegatoList.get(0) : null;
    }

    /**
     * Upload allegati istanza long.
     *
     * @param idIstanza            the id istanza
     * @param file                 the file
     * @param fileName             the file name
     * @param codCategoriaAllegato the cod categoria allegato
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param attoreScriva         the attore scriva
     * @return the long
     * @throws GenericException the generic exception
     */
    @Override
    public Long uploadAllegatiIstanza(Long idIstanza, File file, String fileName, String codCategoriaAllegato, String codTipologiaAllegato, AttoreScriva attoreScriva) throws GenericException {
        logBegin(className);
        return allegatiManager.uploadAllegato(idIstanza, attoreScriva, file, fileName, codCategoriaAllegato, codTipologiaAllegato);
    }

    /**
     * Archivia allegati istanza.
     *
     * @param istanza              the istanza
     * @param file                 the file
     * @param fileName             the file name
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param cancella             the cancella
     * @param attoreScriva         the attore scriva
     * @throws GenericException the generic exception
     */
    @Override
    public void archiviaAllegatiIstanza(IstanzaExtendedDTO istanza, File file, String fileName, String codTipologiaAllegato, Boolean cancella, AttoreScriva attoreScriva) throws GenericException {
        logBegin(className);
        AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = this.getAdempimentoTipoAllegato(codTipologiaAllegato, istanza.getAdempimento().getIdAdempimento()); //determino il tipo di adempimento
        List<AllegatoIstanzaExtendedDTO> allegatiIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndCodTipologia(istanza.getIdIstanza(), codTipologiaAllegato); // verifica se l'istanza ha allegati di quel tipo
        if (Boolean.TRUE.equals(cancella) && allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty()) { // se flag cancella è true 
            allegatiManager.deleteContenutoByUuid(allegatiIstanzaList.get(0).getUuidIndex()); // cancella da index
            allegatoIstanzaDAO.deleteIdPadre(allegatiIstanzaList.get(0).getIdAllegatoIstanza()); // mette a null l'id allegato padre di eventuali figli del doc cancellato
            allegatoIstanzaDAO.deleteAllegatoIstanzaByUuidIndex(allegatiIstanzaList.get(0).getUuidIndex()); // cancella da db l'allegato (cancellazione fisica)
        }
        //Verifica e carica su index il doc creato precedentemente e passato al metodo
        Long idAllegatoIstanza = allegatiManager.uploadAllegato(istanza.getIdIstanza(), attoreScriva, file, fileName, adempimentoTipoAllegato != null ? adempimentoTipoAllegato.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato() : Constants.COD_CATEGORIA_ALLEGATI_SISTEMA, codTipologiaAllegato);
        //Aggiornamento legame padre figlio
        allegatiService.updateIdPadreFiglio(istanza.getIdIstanza(), idAllegatoIstanza, codTipologiaAllegato);
        //aggiorno dati di integrazione con le informazioni dei figli (SCRIVA-800)
        allegatiService.updateDataPadreFromFiglio(idAllegatoIstanza);
        logEnd(className);
    }

    /**
     * Create codice pratica string.
     *
     * @param idIstanza the id istanza
     * @return the string
     */
    @Override
    public IstanzaExtendedDTO createCodicePratica(Long idIstanza, HttpHeaders httpHeaders, AttoreScriva attoreScriva) throws GenericException {
        logBegin(className);
        IstanzaExtendedDTO istanza = getIstanza(idIstanza);
        if (istanza != null) {
            istanza.setCodPratica(istanzaDAO.loadCodicePratica(idIstanza, istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento(), istanza.getAdempimento().getCodAdempimento()));
            istanza = this.updateIstanza(Boolean.TRUE, istanza, attoreScriva);
        }
        return istanza;
    }

    /**
     * Validate dto error dto.
     *
     * @param istanza  the istanza
     * @param isUpdate the is update
     * @return the error dto
     */
    @Override
    public ErrorDTO validateDTO(IstanzaExtendedDTO istanza, Boolean isUpdate) {
        logBegin(className);
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        if (istanza.getIdTemplate() == null) {
            details.put("id_template", ValidationResultEnum.MANDATORY.getDescription());
        }
        if (istanza.getAdempimento() == null || (istanza.getAdempimento() != null && istanza.getAdempimento().getIdAdempimento() == null)) {
            details.put("id_adempimento", ValidationResultEnum.MANDATORY.getDescription());
        }
        if (Boolean.TRUE.equals(isUpdate) && (istanza.getStatoIstanza() == null || (istanza.getStatoIstanza() != null && istanza.getStatoIstanza().getIdStatoIstanza() == null))) {
            details.put("stato_istanza", ValidationResultEnum.MANDATORY.getDescription());
        }
        // SCRIVA-1141 : data protocollazione istanza e allegati non può essere futura
        if (istanza.getDataProtocolloIstanza() != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (istanza.getDataProtocolloIstanza().after(now)) {
                details.put("data_protocollo_istanza", ValidationResultEnum.INVALID_DATE.getDescription());
            }
        }
        // SCRIVA-1142 : Validazione date osservazioni
        if (istanza.getDataInizioOsservazioni() != null && istanza.getDataFineOsservazioni() != null && istanza.getDataInizioOsservazioni().after(istanza.getDataFineOsservazioni())) {
            details.put("data_fine_osservazione", ValidationResultEnum.INVALID_DATE.getDescription());
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }
        logEnd(className);
        return error;
    }

    /**
     * Validate referente dto error dto.
     *
     * @param referente the referente
     * @return the error dto
     */
    @Override
    public ErrorDTO validateReferenteDTO(ReferenteIstanzaDTO referente) {
        logBegin(className);
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();
        if (StringUtils.isNotBlank(referente.getCognomeReferente())) {
            if (referente.getCognomeReferente().length() > 50) {
                details.put("cognome_referente", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidOnlyChar(referente.getCognomeReferente())) {
                details.put("cognome_referente", ValidationResultEnum.INVALID.getDescription());
            }
        }

        if (StringUtils.isNotBlank(referente.getNomeReferente())) {
            if (referente.getNomeReferente().length() > 50) {
                details.put("nome_referente", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidOnlyChar(referente.getNomeReferente())) {
                details.put("nome_referente", ValidationResultEnum.INVALID.getDescription());
            }
        }

        if (StringUtils.isNotBlank(referente.getNumTelReferente()) && referente.getNumTelReferente().length() > 25) {
            details.put("num_tel_referente", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }
        if (StringUtils.isNotBlank(referente.getDesEmailReferente())) {
            if (referente.getDesEmailReferente().length() > 100) {
                details.put("des_email_referente", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidEmail(referente.getDesEmailReferente())) {
                details.put("des_email_referente", ValidationResultEnum.INVALID_EMAIL.getDescription());
            }
        }

        if (StringUtils.isNotBlank(referente.getDesMansioneReferente()) && referente.getDesMansioneReferente().length() > 50) {
            details.put("des_mansione_referente", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }
        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }
        logEnd(className);
        return error;
    }

    /**
     * Save storico boolean.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the boolean
     */
    @Override
    public boolean saveStorico(Long idStatoIstanza) {
        logBegin(className);
        StatoIstanzaDTO statoIstanza = idStatoIstanza != null ? statoIstanzaDAO.loadStatoIstanza(idStatoIstanza) : null;
        logEnd(className);
        return statoIstanza != null ? statoIstanza.getFlgStoricoIstanza() : Boolean.FALSE;
    }

    /**
     * Load istanza stato list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaStatoExtendedDTO> loadIstanzaStato(Long idIstanza) {
        logBegin(className);
        return istanzaStatoDAO.loadIstanzaStatiByIstanza(idIstanza);
    }

    /**
     * Patch data pubblicazione istanza list.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param attoreScriva      the attore scriva
     * @return the list
     */
    @Override
    public List<IstanzaExtendedDTO> patchDataPubblicazioneIstanza(Long idIstanza, Date dataPubblicazione, AttoreScriva attoreScriva) {
        logBegin(className);
        Date dtPubb = dataPubblicazione;
        if (dtPubb == null) {
            Calendar cal = Calendar.getInstance();
            dtPubb = cal.getTime();
        }
        IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
        if (istanza != null) {
            // Aggiornamento data di pubblicazione su istanza
            Long idIstanzaAttore = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null;
            Long idFunzionario = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null;
            Integer result = istanzaDAO.updateDataPubblicazione(idIstanza, dtPubb, attoreScriva.getCodiceFiscale(), idIstanzaAttore, idFunzionario, saveStorico(istanza.getIdStatoIstanza()));
            if (result > 0) {
                // Aggiornamento data pubblicazione allegati
                allegatoIstanzaDAO.updateDataPubblicazione(idIstanza, dtPubb, attoreScriva.getCodiceFiscale());
                return istanzaDAO.loadIstanza(idIstanza);
            }
        }
        return null;
    }

    /**
     * Load istanza responsabili list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaResponsabileExtendedDTO> loadIstanzaResponsabili(Long idIstanza) {
        logBegin(className);
        return istanzaResponsabiliDAO.loadIstanzaResponsabiliByIstanza(idIstanza);
    }

    /**
     * Update data atto from provvedimento finale integer.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateDataConclProcedimentoFromProvvedimentoFinale(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        Long idIstanzaAttore = ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null;
        Long idFunzionario = ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null;
        return istanzaDAO.updateDataConclProcedimentoFromProvvedimentoFinale(idIstanza, attoreScriva.getCodiceFiscale(), idIstanzaAttore, idFunzionario, Boolean.FALSE);
    }

    /**
     * Update protocollo allegati istanza integer.
     *
     * @param istanza      the istanza
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateProtocolloAllegatiIstanza(IstanzaExtendedDTO istanza, AttoreScriva attoreScriva) {
        logBegin(className);
        if (istanza.getDataProtocolloIstanza() != null && StringUtils.isNotBlank(istanza.getNumProtocolloIstanza())) {
            return allegatiService.updateDataProtocolloModIstanzaElencoAllegati(istanza.getIdIstanza(), istanza.getNumProtocolloIstanza(), istanza.getDataProtocolloIstanza(), attoreScriva);
        }
        return 0;
    }

    /**
     * Gets tipo adempimento ogg app istanza.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the tipo adempimento ogg app istanza
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> getTipoAdempimentoOggAppIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        List<TipoAdempimentoOggettoAppExtendedDTO> result = new ArrayList<>();
        try {
            if (idIstanza != null && attoreScriva != null) {
                if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                    if (!ProfiloAppEnum.READ_ALL.name().equalsIgnoreCase(attoreScriva.getProfiloAppIstanza())) {
                        result = actionRoleManager.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.TRUE);
                    }
                } else {
                    result = actionRoleManager.getTipoAdempimentoOggettoApp(idIstanza, null, attoreScriva, Boolean.TRUE);
                }
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets tipo adempimento ogg app istanza json.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the tipo adempimento ogg app istanza json
     */
    @Override
    public String getTipoAdempimentoOggAppIstanzaJson(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        StringWriter stringWriter = new StringWriter();
        try {
            List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = getTipoAdempimentoOggAppIstanza(idIstanza, attoreScriva);
            if (CollectionUtils.isNotEmpty(tipoAdempimentoOggettoAppList)) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(stringWriter, tipoAdempimentoOggettoAppList);
                return stringWriter.toString();
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return stringWriter.toString();
    }


}
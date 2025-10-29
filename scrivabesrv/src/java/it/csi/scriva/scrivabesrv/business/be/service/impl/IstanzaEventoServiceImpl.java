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

import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CatastoUbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComponenteAppDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaEventoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaStatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaAdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoEventoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompetenzeTerritorioService;
import it.csi.scriva.scrivabesrv.business.be.service.CosmoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.TipiEventoService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoIstanzaEnum;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import it.csi.scriva.scrivabesrv.util.notification.NotificationManager;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Istanza evento service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class IstanzaEventoServiceImpl extends BaseApiServiceImpl implements IstanzaEventoService {

    //scriva_d_stato_istanza => ind_aggiorna_oggetto  
    private static final String E = "E"; 
    private static final String S = "S";

    private final String className = this.getClass().getSimpleName();

    /**
     * The Competenze territorio service.
     */
    @Autowired
    CompetenzeTerritorioService competenzeTerritorioService;

    /**
     * The Cosmo service.
     */
    @Autowired
    CosmoService cosmoService;

    /**
     * The Istanza service.
     */
    @Autowired
    IstanzaService istanzaService;

    /**
     * The Tipi evento service.
     */
    @Autowired
    TipiEventoService tipiEventoService;

    /**
     * The Adempimento config dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Catasto ubicazione oggetto istanza dao.
     */
    @Autowired
    CatastoUbicazioneOggettoIstanzaDAO catastoUbicazioneOggettoIstanzaDAO;

    /**
     * The Componente app dao.
     */
    @Autowired
    ComponenteAppDAO componenteAppDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Istanza evento dao.
     */
    @Autowired
    IstanzaEventoDAO istanzaEventoDAO;

    /**
     * The Istanza stato dao.
     */
    @Autowired
    IstanzaStatoDAO istanzaStatoDAO;

    /**
     * The Oggetto istanza dao.
     */
    @Autowired
    OggettoIstanzaDAO oggettoIstanzaDAO;

    /**
     * The Oggetto dao.
     */
    @Autowired
    OggettoDAO oggettoDAO;

    /**
     * The Stato istanza dao.
     */
    @Autowired
    StatoIstanzaDAO statoIstanzaDAO;

    /**
     * The Stato istanza adempimento dao.
     */
    @Autowired
    StatoIstanzaAdempimentoDAO statoIstanzaAdempimentoDAO;

    /**
     * The Tipo evento dao.
     */
    @Autowired
    TipoEventoDAO tipoEventoDAO;

    /**
     * The Ubicazione oggetto dao.
     */
    @Autowired
    UbicazioneOggettoDAO ubicazioneOggettoDAO;

    /**
     * The Istanza attore manager.
     */
    @Autowired
    IstanzaAttoreManager istanzaAttoreManager;

    /**
     * The Json data manager.
     */
    @Autowired
    JsonDataManager jsonDataManager;

    /**
     * The Notification manager.
     */
    @Autowired
    NotificationManager notificationManager;

    /**
     * Load istanza evento list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaEventoExtendedDTO> loadIstanzaEvento(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        return istanzaEventoDAO.loadIstanzaEventoByIdIstanza(idIstanza);
    }

    /**
     * Trace istanza evento by code tipo evento list.
     *
     * @param idIstanza         the id istanza
     * @param codeTipoEvento    the code tipo evento
     * @param rifCanaleNotifica the rif canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param uidRichiesta      the uid richiesta
     * @param dataIntegrazione  the data integrazione
     * @param tipoRichiesta     the tipo richiesta
     * @param attoreScriva      the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    @Override
    public List<IstanzaEventoExtendedDTO> traceIstanzaEventoByCodeTipoEvento(Long idIstanza,
                                                                             String codeTipoEvento,
                                                                             String rifCanaleNotifica,
                                                                             String codCanaleNotifica,
                                                                             String uidRichiesta,
                                                                             Date dataIntegrazione,
                                                                             String tipoRichiesta,
                                                                             AttoreScriva attoreScriva) throws GenericException {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - codeTipoEvento : [" + codeTipoEvento + "] - rifCanaleNotifica : [" + rifCanaleNotifica + "] - codCanaleNotifica : [" + codCanaleNotifica + "] - uidRichiesta : [" + uidRichiesta + "] - tipoRichiesta : [" + tipoRichiesta + "] - dataIntegrazione : [" + dataIntegrazione + "]");
        TipoEventoExtendedDTO tipoEvento = null;
        try {
            if (idIstanza != null) {
                IstanzaExtendedDTO istanza = istanzaService.getIstanza(idIstanza);
                if (istanza == null) {
                    ErrorDTO error = getErrorManager().getError("404", "", "Istanza non trovata", null, null);
                    throw new GenericException(error);
                }
                tipoEvento = tipiEventoService.getTipoEventoByCode(codeTipoEvento);
                if (tipoEvento == null) {
                    ErrorDTO error = getErrorManager().getError("404", "", "Tipo evento non trovato", null, null);
                    throw new GenericException(error);
                }

                Long idIstanzaEvento = saveIstanzaEvento(istanza.getDTO(), tipoEvento, attoreScriva);
                if (idIstanzaEvento != null && tipoEvento.getStatoIstanzaEvento() != null && tipoEvento.getStatoIstanzaEvento().getIdStatoIstanza() != null) {
                    if (isNewStatoCompatible(istanza.getDTO(), tipoEvento)) {
                        boolean saveStorico = mustSaveStorico(tipoEvento.getStatoIstanzaEvento().getIdStatoIstanza());
                        if (Boolean.TRUE.equals(saveStorico)) {
                            JSONObject jsonData = jsonDataManager.generateJsonDataFromConfig(idIstanza);
                            if (jsonData != null) {
                                istanza.setJsonData(jsonData.toString());
                            }
                        }
                        // cambio stato istanza
                        istanzaDAO.updateStatoIstanza(istanza.getGestUID(),
                                tipoEvento.getStatoIstanzaEvento().getIdStatoIstanza(),
                                attoreScriva.getCodiceFiscale(),
                                istanza.getJsonData(),
                                saveStorico);

                        istanza = istanzaService.getIstanza(idIstanza);
                        IstanzaExtendedDTO istanzaNew = istanzaService.updateJsonDataIstanza(istanza);
                        istanzaDAO.updateJsonDataIstanza(istanzaNew.getDTO());
                        updateAnagraficaOggetto(istanza);
                        updateIstanzaStato(istanza, tipoEvento.getStatoIstanzaEvento().getIdStatoIstanza(), attoreScriva.getCodiceFiscale());
                        gestioneProcesso(istanza, tipoEvento, attoreScriva);
                        notificationManager.sendNotifica(idIstanza, codeTipoEvento, rifCanaleNotifica, codCanaleNotifica, uidRichiesta, tipoRichiesta, dataIntegrazione, attoreScriva);
                    } else {
                        logError(className, "Nuovo stato istanza non compatibile con il vecchio (idIstanza [" + idIstanza + "])");
                        throw new GenericException(tipoEvento.getStatoIstanzaEvento().getCodiceStatoIstanza());
                    }
                }
            } else {
                notificationManager.sendNotifica(idIstanza, codeTipoEvento, rifCanaleNotifica, codCanaleNotifica, uidRichiesta, tipoRichiesta, dataIntegrazione, attoreScriva);
            }


        } catch (GenericException | ParseException | CosmoException | IOException e) {
            ErrorDTO error = e instanceof CosmoException ? getErrorManager().getError("500", "E100", null, null, null) : getErrorManager().getError("409", "E037", null, null, null);
            if (error != null) {
                String statoIstanzaEvento = tipoEvento != null && tipoEvento.getStatoIstanzaEvento() != null ? tipoEvento.getStatoIstanzaEvento().getDescrizioneStatoIstanza() : "";
                error.setTitle(e instanceof CosmoException ? e.getMessage() : error.getTitle().replace("{PH_STATO_ISTANZA_EVENTO}", statoIstanzaEvento));
            }
            logError(className, error);
            throw new GenericException(error);
        } finally {
            logEnd(className);
        }
        return idIstanza != null ? loadIstanzaEvento(idIstanza) : new ArrayList<>();
    }

    private TipoEventoExtendedDTO getTipoEventoById(Long idTipoEvento) {
        logBeginInfo(className, idTipoEvento);
        List<TipoEventoExtendedDTO> tipoEventoList = tipoEventoDAO.loadTipoEventoById(idTipoEvento, null);
        return tipoEventoList != null && !tipoEventoList.isEmpty() ? tipoEventoList.get(0) : null;
    }

    private List<StatoIstanzaAdempimentoDTO> getStatoIstanzaAdempimento(Long idAdempimeto, Long idStatoIstanza, Long idStatoIstanzaAmmesso) {
        logBeginInfo(className, "idAdempimeto: [" + idAdempimeto + "] - idStatoIstanza : [" + idStatoIstanza + "] - idStatoIstanzaAmmesso : [" + idStatoIstanzaAmmesso + "]");
        return statoIstanzaAdempimentoDAO.loadStatoIstanzaAdempimentoByIdAdempimentoIdStatoIstanzaNewOld(idAdempimeto, idStatoIstanza, idStatoIstanzaAmmesso);
    }

    /**
     * Is new stato compatible boolean.
     *
     * @param istanza    the istanza
     * @param tipoEvento the tipo evento
     * @return the boolean
     */
    private boolean isNewStatoCompatible(IstanzaDTO istanza, TipoEventoExtendedDTO tipoEvento) {
        logBeginInfo(className, "istanza:\n" + istanza + " \ntipoEvento\n" + tipoEvento + "\n");
        if (tipoEvento != null && tipoEvento.getStatoIstanzaEvento() == null) {
            return Boolean.TRUE;
        }
        List<StatoIstanzaAdempimentoDTO> statoIstanzaAdempimento = tipoEvento != null ? getStatoIstanzaAdempimento(istanza.getIdAdempimento(), istanza.getIdStatoIstanza(), tipoEvento.getStatoIstanzaEvento().getIdStatoIstanza()) : null;
        return statoIstanzaAdempimento != null && !statoIstanzaAdempimento.isEmpty();
    }

    /**
     * Save istanza evento.
     *
     * @param istanza      the istanza
     * @param tipoEvento   the tipo evento
     * @param attoreScriva the attore scriva
     * @return the long
     */
    public Long saveIstanzaEvento(IstanzaDTO istanza, TipoEventoExtendedDTO tipoEvento, AttoreScriva attoreScriva) {
        logBeginInfo(className, "istanza:\n" + istanza + " \ntipoEvento\n" + tipoEvento + "\n");
        Long result = null;
        try {
            IstanzaEventoDTO istanzaEvento = new IstanzaEventoDTO();
            istanzaEvento.setIdIstanza(istanza.getIdIstanza());
            istanzaEvento.setIdTipoEvento(tipoEvento.getIdTipoEvento());
            istanzaEvento.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            if (ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                istanzaEvento.setIdFunzionario(attoreScriva.getIdAttore());
            } else {
                Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), istanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                istanzaEvento.setIdIstanzaAttore(idIstanzaAttoreCompilante);
            }
            List<ComponenteAppDTO> componenteAppList = componenteAppDAO.loadComponenteAppByCode(attoreScriva.getComponente());
            istanzaEvento.setIdComponenteApp(componenteAppList != null && !componenteAppList.isEmpty() ? componenteAppList.get(0).getIdComponenteApp() : ComponenteAppEnum.findByDescr(attoreScriva.getComponente()).getIdComponenteApp());
            result = istanzaEventoDAO.saveIstanzaEvento(istanzaEvento);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Save storico boolean.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the boolean
     */
    public boolean mustSaveStorico(Long idStatoIstanza) {
        logBeginInfo(className, idStatoIstanza);
        StatoIstanzaDTO statoIstanza = idStatoIstanza != null ? statoIstanzaDAO.loadStatoIstanza(idStatoIstanza) : null;
        logEnd(className);
        return statoIstanza != null ? statoIstanza.getFlgStoricoIstanza() : Boolean.FALSE;
    }

    /**
     * Must update anagrafica oggetto boolean.
     *
     * @param codStatoIstanza the cod stato istanza
     * @param idOggetto       the id oggetto
     * @return the boolean
     */
    public boolean mustUpdateAnagraficaOggetto(String codStatoIstanza, Long idOggetto, Long idIstanza) {
        logBeginInfo(className, codStatoIstanza);
        try {
            //Aggiorna se lo stato dell'istanza è BOZZA e l'ultimo aggiornamento effettuato sull'oggetto è della stessa istanza
            if (StringUtils.isNotBlank(codStatoIstanza) && idOggetto != null && StatoIstanzaEnum.BOZZA.name().equalsIgnoreCase(codStatoIstanza)) {
                OggettoDTO oggetto = oggettoDAO.findByPK(idOggetto);
                return oggetto != null && oggetto.getIdIstanzaAggiornamento() != null && oggetto.getIdIstanzaAggiornamento() > 0 ? oggetto.getIdIstanzaAggiornamento().equals(idIstanza) : Boolean.FALSE;
            }
            //Aggiorna se lo stato in cui si trova l'istanza è configurato per consentire l'aggiornamento
            StatoIstanzaDTO statoIstanza = StringUtils.isNotBlank(codStatoIstanza) ? statoIstanzaDAO.loadStatoIstanzaByCodice(codStatoIstanza) : null;
            //return statoIstanza != null ? statoIstanza.getFlgAggiornaOggetto() : Boolean.FALSE;
            if (statoIstanza == null) {
                logError(className, "Stato istanza non trovato per codice: " + codStatoIstanza);
                return Boolean.FALSE;
            }
            return BooleanUtils.toBooleanDefaultIfNull(statoIstanza.getFlgAggiornaOggetto(), Boolean.FALSE);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Must update anagrafica oggetto boolean.
     *
     * @param idStatoIstanza the id stato istanza
     * @return the boolean
     */
    public boolean mustUpdateAnagraficaOggetto(Long idStatoIstanza) {
        logBeginInfo(className, idStatoIstanza);
        try {
            //Aggiorna se lo stato in cui si trova l'istanza è configurato per consentire l'aggiornamento
            StatoIstanzaDTO statoIstanza = idStatoIstanza != null ? statoIstanzaDAO.loadStatoIstanza(idStatoIstanza) : null;
            //return statoIstanza != null ? statoIstanza.getFlgAggiornaOggetto() : Boolean.FALSE;
          if (statoIstanza == null) {
                logError(className, "Stato istanza non trovato per codice: " + idStatoIstanza);
                return Boolean.FALSE;
            }
            return BooleanUtils.toBooleanDefaultIfNull(statoIstanza.getFlgAggiornaOggetto(), Boolean.FALSE);
        } finally {
            logEnd(className);
        }
    }

    /* SCRIVA-1205
     * 1) RIBALTAMENTO DA OGGETTO-ISTANZA A OGGETTO-ANAGRAFICA
     * Documento Indice generale: Algoritmo 7gen – Gestione stato istanza/pratica
     */
    public boolean mustUpdateAnagraficaOggetto(IstanzaExtendedDTO istanza) {
        boolean esito = Boolean.FALSE;
        try {
            //controllo l'esistenza dello statoIstanza
            StatoIstanzaDTO statoIstanza = istanza.getStatoIstanza();
            if (statoIstanza == null) {
                logError(className, "Stato istanza non trovato per codice: " + istanza.getIdIstanza());
                esito = Boolean.FALSE;
            }else{
                //controllo scriva_d_stato_istanza => flg_aggiorna_oggetto
                boolean isFlgAggiornaOggetto = BooleanUtils.toBooleanDefaultIfNull(statoIstanza.getFlgAggiornaOggetto(), Boolean.FALSE);
                //controllo scriva_d_stato_istanza => ind_aggiorna_oggetto
                String indAggiornaOggetto = statoIstanza.getIndAggiornaOggetto();
                boolean isIndAggiornaOggettoE = StringUtils.isNotBlank(indAggiornaOggetto) && indAggiornaOggetto.equals(E);
                boolean isIndAggiornaOggettoS = StringUtils.isNotBlank(indAggiornaOggetto) && indAggiornaOggetto.equals(S);

                EsitoProcedimentoDTO esitoProcedimento = istanza.getEsitoProcedimento();

                //controlli validi affinchè avvenga il ribaltamento dell'oggetto
                //1) se isFlgAggiornaOggetto = true
                // E
                //2) se ind aggiorna oggetto = S
                // oppure
                //3) se flg positivo = true & ind aggiorna oggetto = E
                if(isFlgAggiornaOggetto && (isIndAggiornaOggettoS || 
                 (esitoProcedimento != null && esitoProcedimento.getFlgPositivo() && isIndAggiornaOggettoE))){
                     esito = Boolean.TRUE;                
                }
            }
        } finally {
            logEnd(className);
        }

        return esito;
    }

    /**
     * Update istanza stato.
     *
     * @param istanza the istanza
     */
    private void updateIstanzaStato(IstanzaExtendedDTO istanza, Long idStatoIstanza, String cfAttore) {
        logBeginInfo(className, "istanza:\n" + istanza + " \n");
        try {
            if (istanza != null) {
                IstanzaStatoDTO istanzaStatoDTO = new IstanzaStatoDTO();
                istanzaStatoDTO.setIdIstanza(istanza.getIdIstanza());
                istanzaStatoDTO.setIdStatoIstanza(idStatoIstanza);
                istanzaStatoDTO.setGestAttoreIns(cfAttore);
                istanzaStatoDAO.saveIstanzaStato(istanzaStatoDTO);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update anagrafica oggetto.
     *
     * @param istanza the istanza
     */
    public void updateAnagraficaOggetto(IstanzaExtendedDTO istanza) { //devo avere IstanzaExtendedDTO !!!
        logBeginInfo(className, "istanza:\n" + istanza + " \n");
        try {
            if (mustUpdateAnagraficaOggetto(istanza)){ 
                //recupera tutti gli oggetti-istanza legati all'istanza
                List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(istanza.getIdIstanza());
                if (oggettoIstanzaList != null && !oggettoIstanzaList.isEmpty()) {
                    //per ogni oggetto-istanza effettua aggiornamento del relativo oggetto associato
                    for (OggettoIstanzaExtendedDTO oggettoIstanza : oggettoIstanzaList) {
                        oggettoDAO.updateOggettoByOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                        // per ogni ubicazione legata all'oggetto-istanza aggiornare le ubicazioni dell'oggetto associato (prima delete delle vecchie, poi insert delle nuove)
                        ubicazioneOggettoDAO.deleteUbicazioneOggettoByIdOggetto(oggettoIstanza.getIdOggetto());
                        ubicazioneOggettoDAO.copyUbicazioniOggettoByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza(), oggettoIstanza.getIdOggetto());
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }


    private void gestioneProcesso(IstanzaExtendedDTO istanza, TipoEventoExtendedDTO tipoEvento, AttoreScriva attoreScriva) throws CosmoException, IOException {
        logBegin(className);
        try {
            if (tipoEvento != null && tipoEvento.getIdComponenteGestoreProcesso() != null && tipoEvento.getIdComponenteGestoreProcesso() > 0) {
                List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = competenzeTerritorioService.extractIstanzaCompetenzaList(istanza.getIdIstanza());
                if (istanzaCompetenzaList != null && istanzaCompetenzaList.size() == 1) {
                    IstanzaCompetenzaExtendedDTO istanzaCompetenza = istanzaCompetenzaList.get(0);
                    ComponenteAppDTO componenteApp = componenteAppDAO.loadComponenteAppByIdAdempimentoCompTerritorio(istanza.getAdempimento().getIdAdempimento(), istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio());
                    if (componenteApp != null) {
                        switch (componenteApp.getCodComponenteApp()) {
                            case "COSMO":
                                // chiamata a cosmo
                                manageCosmoProcess(istanza.getIdIstanza(), istanza.getAdempimento().getCodAdempimento(), tipoEvento.getCodTipoEvento(), attoreScriva);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            /*
        } catch (Exception e) {
            logError(className, e); */
        } finally {
            logEnd(className);
        }
    }

    /**
     * Manage cosmo process.
     *
     * @param idIstanza      the id istanza
     * @param codAdempimento the cod adempimento
     * @param codTipoEvento  the cod tipo evento
     * @param attoreScriva   the attore scriva
     * @throws CosmoException the cosmo exception
     * @throws IOException    the io exception
     */
    public void manageCosmoProcess(Long idIstanza, String codAdempimento, String codTipoEvento, AttoreScriva attoreScriva) throws CosmoException, IOException {
        logBeginInfo(className, new StringBuilder("idIstanza [").append(idIstanza).append("] - codAdempimento [").append(codAdempimento).append("] - codTipoEvento [").append(codTipoEvento).append("]"));
        try {
            List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codAdempimento, "SERVIZI_COSMO", codTipoEvento);
            if (adempimentoConfigList != null && !adempimentoConfigList.isEmpty()) {
                switch (adempimentoConfigList.get(0).getValore()) {
                    case "CreazionePraticaCosmo":
                        logDebug(className, "Creazione pratica cosmo");
                        cosmoService.creazionePraticaCosmo(idIstanza);
                        break;
                    case "AggiornaPraticaCosmo":
                        logDebug(className, "Aggiornamento pratica cosmo");
                        cosmoService.aggiornaPraticaCosmo(idIstanza);
                        break;
                    case "AggiornaPraticaDocumentiCosmo":
                        logDebug(className, "Creazione pratica documenti cosmo");
                        cosmoService.aggiornaPraticaDocumentiCosmo(idIstanza, codTipoEvento);
                        break;
                    default:
                        logDebug(className, "Evento non gestito");
                        break;
                }
            }
            /*
        } catch (Exception e) {
            logError(className, e);

             */
        } finally {
            logEnd(className);
        }
    }

}
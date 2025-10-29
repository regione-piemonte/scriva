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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.anagamb.AnagambServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComuneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.MasterdataDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipologiaOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.MasterdataDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoUbicazionePraticaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PraticaCollegataDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.MasterdataEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoSoggettoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.service.integration.anagamb.SedeOperativa;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Oggetti service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class OggettiServiceImpl extends BaseApiServiceImpl implements OggettiService {

    private final String className = this.getClass().getSimpleName();
    //scriva_d_stato_istanza => ind_ricerca_oggetto  
    private static final String E = "E"; 
    private static final String S = "S";


    @Autowired
    private AnagambServiceHelper anagambServiceHelper;

    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;

    @Autowired
    private AdempimentoDAO adempimentoDAO;

    @Autowired
    private ComuneDAO comuneDAO;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private MasterdataDAO masterdataDAO;

    @Autowired
    private OggettoDAO oggettoDAO;

    @Autowired
    private SoggettoDAO soggettoDAO;

    @Autowired
    private TipologiaOggettoDAO tipologiaOggettoDAO;

    @Autowired
    private UbicazioneOggettoDAO ubicazioneOggettoDAO;

    /**
     * Load oggetti list.
     *
     * @return the list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> loadOggetti() {
        logBegin(className);
        try {
            List<OggettoExtendedDTO> oggettiList = oggettoDAO.loadOggetti();
            return oggettiList != null && !oggettiList.isEmpty() ? this.getOggettoUbicazioneList(oggettiList) : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load oggetto by id list.
     *
     * @param idOggetto the id oggetto
     * @return the list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> loadOggettoById(Long idOggetto) {
        logBeginInfo(className, idOggetto);
        try {
            List<OggettoExtendedDTO> oggettiList = oggettoDAO.loadOggettoById(idOggetto);
            return oggettiList != null && !oggettiList.isEmpty() ? this.getOggettoUbicazioneList(oggettiList) : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load oggetto by id comune list.
     *
     * @param idComune the id comune
     * @return the list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> loadOggettoByIdComune(Long idComune) {
        logBeginInfo(className, idComune);
        try {
            List<OggettoExtendedDTO> oggettiList = oggettoDAO.loadOggettoByIdComune(idComune);
            return oggettiList != null && !oggettiList.isEmpty() ? this.getOggettoUbicazioneList(oggettiList) : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load oggetto by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> loadOggettoByIdIstanza(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        try {
            List<OggettoExtendedDTO> oggettiList = oggettoDAO.loadOggettoByIdIstanza(idIstanza);
            return oggettiList != null && !oggettiList.isEmpty() ? this.getOggettoUbicazioneList(oggettiList) : new ArrayList<>();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load oggetto by uid list.
     *
     * @param uidOggetto the uid oggetto
     * @return the list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> loadOggettoByUID(String uidOggetto) {
        logBeginInfo(className, (Object) uidOggetto);
        try {
            List<OggettoExtendedDTO> oggettiList = oggettoDAO.loadOggettoByUID(uidOggetto);
            return oggettiList != null && !oggettiList.isEmpty() ? this.getOggettoUbicazioneList(oggettiList) : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Search oggetto list.
     *
     * @param searchOggetto the search oggetto
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> searchOggetto(SearchOggettoDTO searchOggetto, AttoreScriva attoreScriva) {
        logBeginInfo(className, searchOggetto);

        List<OggettoUbicazioneExtendedDTO> listaOggettiUbicazione = new ArrayList<>();
        try {
            List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(searchOggetto.getIdIstanza());
            
            if(!istanze.isEmpty()) {
                IstanzaExtendedDTO istanza = istanze.get(0) ;
                StatoIstanzaDTO statoIstanza = istanza.getStatoIstanza();

                //SCRIVA-1205 - 2) RICERCA OGGETTO**
                //controllo scriva_d_stato_istanza => ind_aggiorna_oggetto
                String indRicercaOggetto = statoIstanza.getIndRicercaOggetto();
                boolean isIndRicercaOggettoE = StringUtils.isNotBlank(indRicercaOggetto) && indRicercaOggetto.equals(E);
                boolean isIndRicercaOggettoS = StringUtils.isNotBlank(indRicercaOggetto) && indRicercaOggetto.equals(S);

                EsitoProcedimentoDTO esitoProcedimento = istanza.getEsitoProcedimento();

                //controlli validi affinchè avvenga la ricerca dell'oggetto
                //1) se ind ricerca oggetto = S
                //oppure
                //2) se flg positivo = 1 & ind ricerca oggetto = E
                if(isIndRicercaOggettoS || 
                    (esitoProcedimento != null && esitoProcedimento.getFlgPositivo() && isIndRicercaOggettoE)){
                        
                    String codAdempimento = getAdempimento(searchOggetto, istanza);

                    List<String> fontiEsterne = getFontiList(codAdempimento);

                    if (fontiEsterne != null && !fontiEsterne.isEmpty() && fontiEsterne.contains(MasterdataEnum.ANAGAMB.name())) {
                        List<SedeOperativa> sediOperative = getSediOperativeFromAnagamb(searchOggetto.getIdSoggetti(), searchOggetto.getIdComune());
                        logDebug(className, "Sedi operative trovate : " + sediOperative.size());
                        saveUbicazioniBySediOperative(searchOggetto, sediOperative, attoreScriva);
                    }

                    setCodTipologiaOggettoToSearch(searchOggetto, codAdempimento);

                    List<OggettoExtendedDTO> oggettiList = oggettoDAO.searchOggetto(searchOggetto, null, null);
                    if(!oggettiList.isEmpty() ){
                        listaOggettiUbicazione = this.getOggettoUbicazioneList(oggettiList);
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }

        return listaOggettiUbicazione;
    }

    /**
     * Gets fonti list.
     *
     * @param codAdempimento the cod adempimento
     * @return the fonti list
     */
    public List<String> getFontiList(String codAdempimento) {
        logBegin(className);
        List<String> fonti = null;
        try {
            if (StringUtils.isNotBlank(codAdempimento)) {
                List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codAdempimento, InformazioniScrivaEnum.OGGETTO.name(), Constants.CONFIG_KEY_FONTE_RICERCA);
                logDebug(className, "Fonti trovate : " + adempimentoConfigList.size());
                fonti = adempimentoConfigList.stream()
                        .map(AdempimentoConfigDTO::getValore)
                        .collect(Collectors.toList());
            } else {
                List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey(Constants.CONFIG_KEY_OGGETTO_FONTE);
                String oggettoFonte = configurazioneList != null && !configurazioneList.isEmpty() ? configurazioneList.get(0).getValore() : null;
                fonti = StringUtils.isNotBlank(oggettoFonte) ? Arrays.asList(oggettoFonte.split("\\|")) : null;
            }
        } finally {
            logEnd(className);
        }
        return fonti;
    }

    /**
     * Gets adempimento.
     *
     * @param searchOggetto the search oggetto
     * @param istanza       the istanza
     * @return the adempimento
     */
    public String getAdempimento(SearchOggettoDTO searchOggetto, IstanzaExtendedDTO istanza) {
        logBegin(className);
        String codAdempimento = null;
        try {
            if (istanza != null && istanza.getAdempimento() != null) {
                codAdempimento = istanza.getAdempimento().getCodAdempimento();
                searchOggetto.setIdAdempimento(istanza.getAdempimento().getIdAdempimento());
            } else if (searchOggetto.getIdAdempimento() != null) {
                AdempimentoDTO adempimento = adempimentoDAO.findByPK(searchOggetto.getIdAdempimento());
                if (adempimento != null) {
                    codAdempimento = adempimento.getCodAdempimento();
                    searchOggetto.setIdAdempimento(adempimento.getIdAdempimento());
                }
            }
        } finally {
            logEnd(className);
        }
        return codAdempimento;
    }

    private void setCodTipologiaOggettoToSearch(SearchOggettoDTO searchOggetto, String codAdempimento) {
        logBegin(className);
        List<String> codTipologiaOggettoList = null;
        try {
            //Se valorizzato l'utente definisce le tipologie di oggetto da ricercare
            if (StringUtils.isNotBlank(searchOggetto.getCodTipologiaOggetto())) {
                codTipologiaOggettoList = Arrays.asList(searchOggetto.getCodTipologiaOggetto().split("\\|"));

                // Se le tipologie di oggetto da ricercare sono definite dalle categorie associate all'oggetto istanza principale
            } else if (searchOggetto.getIdIstanza() != null && Boolean.TRUE.equals(searchOggetto.isFlgCatOgg())) {
                codTipologiaOggettoList = getTipologiaOggettoByOggIstanzaCategoria(searchOggetto.getIdIstanza());


            /*
                Se il parametro COD-TIPO-OGG è valorizzato effettuare ricerca nella tabella scriva_r_adempi_config
                WHERE id_adempimento={identificativo adempimento di interesse} AND id_informazione_scriva='OGGETTO' and chiave={valore inserito in COD-TIPO-OGG}
            */
            } else if (StringUtils.isNotBlank(codAdempimento) && StringUtils.isNotBlank(searchOggetto.getCodTipoOggetto())) {
                codTipologiaOggettoList = getTipologiaOggettoByAdempiConfig(codAdempimento, searchOggetto.getCodTipoOggetto());

            /*
                Se il parametro COD-TIPO-OGG non è valorizzato il servizio ricerca tutte le tipologie di oggetto censite per l’adempimento
                nella tabella scriva_r_adempi_tipo_oggetto where id_adempimento={identificativo adempimento di interesse}
            */
            } else if (StringUtils.isNotBlank(codAdempimento) && Boolean.FALSE.equals(searchOggetto.isFlgCatOgg())) {
                codTipologiaOggettoList = getTipologiaOggettoByCodAdempimento(codAdempimento);
            }

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }

        searchOggetto.setCodTipoOggettoSearchList(codTipologiaOggettoList);
    }

    /**
     * Gets tipologia oggetto by cod adempimento.
     *
     * @param idIstanza the id istanza
     * @return the tipologia oggetto by cod adempimento
     */
    private List<String> getTipologiaOggettoByOggIstanzaCategoria(Long idIstanza) {
        logBegin(className);
        List<String> codTipologiaOggettoList = new ArrayList<>();
        try {
            List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggettoByOggIstanzaCategoria(idIstanza, null);
            if (tipologiaOggettoList != null && !tipologiaOggettoList.isEmpty()) {
                logDebug(className, "Tipologie oggetti trovate per categoria : " + tipologiaOggettoList.size());
                codTipologiaOggettoList = tipologiaOggettoList.stream()
                        .map(TipologiaOggettoExtendedDTO::getCodTipologiaOggetto)
                        .collect(Collectors.toList());
            }
        } finally {
            logEnd(className);
        }
        return codTipologiaOggettoList;
    }

    /**
     * Gets tipologia oggetto by cod adempimento.
     *
     * @param codAdempimento the cod adempimento
     * @return the tipologia oggetto by cod adempimento
     */
    public List<String> getTipologiaOggettoByCodAdempimento(String codAdempimento) {
        logBegin(className);
        List<String> codTipologiaOggettoList = new ArrayList<>();
        try {
            List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggettoByCodeAdempimento(codAdempimento);
            if (tipologiaOggettoList != null && !tipologiaOggettoList.isEmpty()) {
                logDebug(className, "Tipologie oggetti trovate per adempimento : " + tipologiaOggettoList.size());
                codTipologiaOggettoList = tipologiaOggettoList.stream()
                        .map(TipologiaOggettoExtendedDTO::getCodTipologiaOggetto)
                        .collect(Collectors.toList());
            }
        } finally {
            logEnd(className);
        }
        return codTipologiaOggettoList;
    }

    /**
     * Gets tipologia oggetto by adempi config.
     *
     * @param codAdempimento the cod adempimento
     * @param key            the key
     * @return the tipologia oggetto by adempi config
     */
    private List<String> getTipologiaOggettoByAdempiConfig(String codAdempimento, String key) {
        logBegin(className);
        List<String> codTipologiaOggettoList = new ArrayList<>();
        try {
            List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codAdempimento, InformazioniScrivaEnum.OGGETTO.name(), key);
            if (adempimentoConfigList != null && !adempimentoConfigList.isEmpty()) {
                logDebug(className, "Tipo oggetti trovati : " + adempimentoConfigList.size());
                String valore = adempimentoConfigList.get(0).getValore();
                codTipologiaOggettoList = StringUtils.isNotBlank(valore) ? Arrays.asList(valore.split("\\|")) : new ArrayList<>();
            }
        } finally {
            logEnd(className);
        }
        return codTipologiaOggettoList;
    }

    /**
     * Sets pratiche collegate oggetto.
     *
     * @param oggettoUbicazioneList the oggetto ubicazione list
     * @param annoPresentazione     the anno presentazione
     * @return the list
     */
    @Override
    public List<OggettoUbicazionePraticaExtendedDTO> setPraticheCollegateOggetto(List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList, Integer annoPresentazione) {
        logBeginInfo(className, oggettoUbicazioneList + "\nannoPresentazione: [" + annoPresentazione + "]");
        try {
            List<PraticaCollegataDTO> praticaCollegataList = getPraticheCollegateOggetto(oggettoUbicazioneList, annoPresentazione);
            return getOggettoUbicazionePraticaList(oggettoUbicazioneList, praticaCollegataList);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets pratiche collegate oggetto.
     *
     * @param oggettoUbicazioneList the oggetto ubicazione list
     * @param annoPresentazione     the anno presentazione
     * @return the pratiche collegate oggetto
     */
    @Override
    public List<PraticaCollegataDTO> getPraticheCollegateOggetto(List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList, Integer annoPresentazione) {
        logBegin(className);
        List<PraticaCollegataDTO> praticaCollegataList = null;
        try {
            List<Long> idOggetti = oggettoUbicazioneList.stream()
                    .map(OggettoUbicazioneExtendedDTO::getIdOggetto)
                    .collect(Collectors.toList());
            praticaCollegataList = !idOggetti.isEmpty() ? istanzaDAO.loadPraticheCollegateOggetto(idOggetti, annoPresentazione) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return praticaCollegataList;
    }

    private List<OggettoUbicazionePraticaExtendedDTO> getOggettoUbicazionePraticaList(List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList, List<PraticaCollegataDTO> praticaCollegataList) {
        logBegin(className);
        List<OggettoUbicazionePraticaExtendedDTO> oggettoUbicazionePraticaList = new ArrayList<>();
        try {
            if (oggettoUbicazioneList != null) {
                for (OggettoUbicazioneExtendedDTO oggettoUbicazione : oggettoUbicazioneList) {
                    OggettoUbicazionePraticaExtendedDTO oggettoUbicazionePratica = new OggettoUbicazionePraticaExtendedDTO(oggettoUbicazione);
                    List<PraticaCollegataDTO> praticaCollegataOggettoList = praticaCollegataList.stream()
                            .filter(praticaCollegata -> praticaCollegata.getIdOggetto().equals(oggettoUbicazione.getIdOggetto()))
                            .collect(Collectors.toList());
                    if (!praticaCollegataOggettoList.isEmpty()) {
                        oggettoUbicazionePratica.setPraticaCollegata(praticaCollegataOggettoList);
                        oggettoUbicazionePraticaList.add(oggettoUbicazionePratica);
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoUbicazionePraticaList;
    }

    /**
     * Save oggetto long.
     *
     * @param oggetto      the oggetto
     * @param attoreScriva the attore scriva
     * @return the long
     */
    @Override
    public Long saveOggetto(OggettoUbicazioneExtendedDTO oggetto, AttoreScriva attoreScriva) {
        logBeginInfo(className, oggetto);
        Long idOggetto = null;
        try {
            if (oggetto != null) {
                oggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                oggetto.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
                if (StringUtils.isBlank(oggetto.getCodScriva())) {
                    oggetto.setCodScriva(oggettoDAO.getNextCodScriva());
                }

                idOggetto = oggettoDAO.saveOggetto(oggetto.getDTO());
                // Inserimento delle ubicazioni dell'oggetto
                if (idOggetto != null && CollectionUtils.isNotEmpty(oggetto.getUbicazioneOggetto())) {
                    Long idUbicazione = saveUbicazioniOggetto(idOggetto, oggetto.getUbicazioneOggetto(), attoreScriva);
                    idOggetto = idUbicazione != null ? idOggetto : null;
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idOggetto;
    }

    /**
     * Update oggetto long.
     *
     * @param oggetto      the oggetto
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateOggetto(OggettoUbicazioneExtendedDTO oggetto, AttoreScriva attoreScriva) {
        logBegin(className);
        Integer resUpdateOggetto = null;
        try {
            if (oggetto != null) {
                if (StringUtils.isBlank(oggetto.getGestAttoreUpd())) {
                    oggetto.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                }
                resUpdateOggetto = oggettoDAO.updateOggetto(oggetto.getDTO());
                // Aggiornamento delle ubicazioni dell'oggetto
                if (resUpdateOggetto != null && oggetto.getUbicazioneOggetto() != null) {
                    resUpdateOggetto = this.updateUbicazioniOggetto(oggetto.getIdOggetto(), oggetto.getUbicazioneOggetto(), attoreScriva);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return resUpdateOggetto;
    }

    /**
     * Delete oggetto long.
     *
     * @param uidOggetto the uid oggetto
     * @return the integer
     */
    @Override
    public Integer deleteOggetto(String uidOggetto) {
        logBegin(className);
        Integer resDeleteOggetto = null;
        try {
            if (StringUtils.isNotBlank(uidOggetto)) {
                ubicazioneOggettoDAO.deleteUbicazioneOggettoByUidOggetto(uidOggetto);
                resDeleteOggetto = oggettoDAO.deleteOggetto(uidOggetto);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return resDeleteOggetto;
    }

    /**
     * Gets oggetto ubicazione list.
     *
     * @param oggettiList the oggetti list
     * @return the oggetto ubicazione list
     */
    @Override
    public List<OggettoUbicazioneExtendedDTO> getOggettoUbicazioneList(List<OggettoExtendedDTO> oggettiList) {
        logBegin(className);
        List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList = new ArrayList<>();
        try {
            if (!oggettiList.isEmpty()) {
                for (OggettoExtendedDTO oggetto : oggettiList) {
                    OggettoUbicazioneExtendedDTO oggettoUbicazione = new OggettoUbicazioneExtendedDTO();
                    oggettoUbicazione.setIdOggetto(oggetto.getIdOggetto());
                    oggettoUbicazione.setTipologiaOggetto(oggetto.getTipologiaOggetto());
                    oggettoUbicazione.setStatoOggetto(oggetto.getStatoOggetto());
                    oggettoUbicazione.setCodOggettoFonte(oggetto.getCodOggettoFonte());
                    oggettoUbicazione.setCodScriva(oggetto.getCodScriva());
                    oggettoUbicazione.setDenOggetto(oggetto.getDenOggetto());
                    oggettoUbicazione.setDesOggetto(oggetto.getDesOggetto());
                    oggettoUbicazione.setCoordinataX(oggetto.getCoordinataX());
                    oggettoUbicazione.setCoordinataY(oggetto.getCoordinataY());
                    oggettoUbicazione.setIdMasterdata(oggetto.getIdMasterdata());
                    oggettoUbicazione.setGestUID(oggetto.getGestUID());
                    //oggettoUbicazione.setIdIstanzaAggiornamento(oggetto.getIdIstanzaAggiornamento()); SCRIVA-1308 --> DA VERIFICARE PERCHE' SPACCA NELL'EDIT DEL QUADRO PROGETTO
                    oggettoUbicazione.setIdMasterdataOrigine(oggetto.getIdMasterdataOrigine());
                    List<UbicazioneOggettoExtendedDTO> ubicazioniOggettoList = ubicazioneOggettoDAO.loadUbicazioneOggettoByIdOggetto(oggetto.getIdOggetto());
                    oggettoUbicazione.setUbicazioneOggetto(ubicazioniOggettoList);
                    oggettoUbicazioneList.add(oggettoUbicazione);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return oggettoUbicazioneList;
    }

    /**
     * Gets sedi operative from anagamb.
     *
     * @param idSoggetti the id soggetti
     * @param idComune   the id comune
     * @return the sedi operative from anagamb
     */
    @Override
    public List<SedeOperativa> getSediOperativeFromAnagamb(List<Long> idSoggetti, Long idComune) {
        logBegin(className);
        List<SedeOperativa> sedeOperativaList = new ArrayList<>();
        try {
            List<SoggettoExtendedDTO> soggetti = soggettoDAO.loadSoggettiByIdList(idSoggetti);
            logDebug(className, "Soggetti SCRIVA trovati : " + soggetti.size());
            List<String> cfs = new ArrayList<>();
            for (SoggettoExtendedDTO sogg : soggetti) {
                if (sogg.getTipoSoggetto().getCodiceTipoSoggetto().equals(TipoSoggettoEnum.PG.name()) || sogg.getTipoSoggetto().getCodiceTipoSoggetto().equals(TipoSoggettoEnum.PB.name())) {
                    cfs.add(sogg.getCfSoggetto());
                }
            }

            if (!cfs.isEmpty()) {
                List<ComuneExtendedDTO> comuneList = comuneDAO.loadComuneById(idComune);
                ComuneExtendedDTO comune = comuneList != null && !comuneList.isEmpty() ? comuneList.get(0) : null;
                if (comune != null) {
                    for (String cf : cfs) {
                        sedeOperativaList.addAll(anagambServiceHelper.getSediOperative(comune.getCodIstatComune(), cf));
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return sedeOperativaList;
    }

    /**
     * Save ubicazioni by sedi operative.
     *
     * @param sedeOperativaList the sede operativa list
     * @param attoreScriva      the attore scriva
     */
    @Override
    public void saveUbicazioniBySediOperative(SearchOggettoDTO searchOggetto, List<SedeOperativa> sedeOperativaList, AttoreScriva attoreScriva) {
        logBegin(className);
        for (SedeOperativa sedeOperativa : sedeOperativaList) {
            String codSira = StringUtils.isNotBlank(sedeOperativa.getCodiceSira()) ? sedeOperativa.getCodiceSira() : sedeOperativa.getCodiceSiraValidato();
            List<OggettoExtendedDTO> oggettoList = oggettoDAO.searchOggetto(searchOggetto);
            Optional<OggettoExtendedDTO> oggettoOpt = oggettoList.stream().filter(ogg -> codSira.equals(String.valueOf(ogg.getCodOggettoFonte()))).findFirst();
            if (oggettoOpt.isEmpty()) {
                saveUbicazioneOggettoBySedeOperativa(saveOggettoBySedeOperativa(sedeOperativa, attoreScriva), sedeOperativa, attoreScriva);
            }
        }
    }

    /**
     * Save ubicazioni oggetto.
     *
     * @param idOggetto             the id oggetto
     * @param ubicazioneOggettoList the ubicazione oggetto list
     * @param attoreScriva          the attore scriva
     * @return the long
     */
    @Override
    public Long saveUbicazioniOggetto(Long idOggetto, List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoList, AttoreScriva attoreScriva) {
        logBegin(className);
        Long idUbicazione = null;
        try {
            if (idOggetto != null && ubicazioneOggettoList != null && !ubicazioneOggettoList.isEmpty()) {
                for (UbicazioneOggettoExtendedDTO ubicazioneOggetto : ubicazioneOggettoList) {
                    ubicazioneOggetto.setIdOggetto(idOggetto);
                    if (StringUtils.isBlank(ubicazioneOggetto.getGestAttoreIns())) {
                        ubicazioneOggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                    }
                    idUbicazione = ubicazioneOggettoDAO.saveUbicazioneOggetto(ubicazioneOggetto.getDTO());
                    if (idUbicazione == null) {
                        // Cancellazione di tutti i record creati
                        ubicazioneOggettoDAO.deleteUbicazioneOggettoByIdOggetto(idOggetto);
                        oggettoDAO.deleteOggettoById(idOggetto);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idUbicazione;
    }

    /**
     * Update ubicazioni oggetto integer.
     *
     * @param ubicazioneOggettoList the ubicazione oggetto list
     * @param attoreScriva          the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateUbicazioniOggetto(Long idOggetto, List<UbicazioneOggettoExtendedDTO> ubicazioneOggettoList, AttoreScriva attoreScriva) {
        logBegin(className);
        Integer resUpdateUbicazione = null;
        try {
            if (ubicazioneOggettoList != null && !ubicazioneOggettoList.isEmpty()) {
                // Lista comuni aggiornati
                List<Long> idsUbicazioniAggiornate = new ArrayList<>();
                // Caricamento ubicazioni associate attualmente all'oggetto
                List<UbicazioneOggettoExtendedDTO> ubicazioniAttualiOggettoList = ubicazioneOggettoDAO.loadUbicazioneOggettoByIdOggetto(idOggetto);
                logInfo(className, "N. ubicazioni per oggetto : " + ubicazioniAttualiOggettoList.size());

                for (UbicazioneOggettoExtendedDTO ubicazioneOggetto : ubicazioneOggettoList) {
                    if (StringUtils.isBlank(ubicazioneOggetto.getGestAttoreUpd())) {
                        ubicazioneOggetto.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                    }
                    // Verifica esistenza ubicazione per comune
                    List<UbicazioneOggettoExtendedDTO> ubicazioniFiltratePerComuneList = ubicazioniAttualiOggettoList.stream().filter(ubica -> ubicazioneOggetto.getComune().getIdComune().equals(ubica.getComune().getIdComune())).collect(Collectors.toList());
                    logInfo(className, "N. ubicazioni filtrate per comune : " + ubicazioniFiltratePerComuneList.size());

                    if (ubicazioniFiltratePerComuneList.isEmpty()) {
                        // se l'ubicazione non è già stata inserita in precedenza crea una nuova
                        if (StringUtils.isBlank(ubicazioneOggetto.getGestAttoreIns())) {
                            ubicazioneOggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                        }
                        Long idUbicazione = ubicazioneOggettoDAO.saveUbicazioneOggetto(ubicazioneOggetto.getDTO());
                        if (null == idUbicazione) {
                            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                            throw new GenericException(error);
                        }
                        idsUbicazioniAggiornate.add(idUbicazione);
                    } else {
                        for (UbicazioneOggettoExtendedDTO ubicazioneDaAggiornare : ubicazioniFiltratePerComuneList) {
                            ubicazioneOggetto.setIdUbicazioneOggetto(ubicazioneDaAggiornare.getIdUbicazioneOggetto());
                            ubicazioneOggetto.setGestUID(ubicazioneDaAggiornare.getGestUID());
                            resUpdateUbicazione = ubicazioneOggettoDAO.updateUbicazioneOggetto(ubicazioneOggetto.getDTO());
                            if (resUpdateUbicazione == null || resUpdateUbicazione < 1) {
                                ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                                throw new GenericException(error);
                            } else {
                                idsUbicazioniAggiornate.add(ubicazioneDaAggiornare.getIdUbicazioneOggetto());
                            }
                        }
                    }
                }
                // Cancellazione di tutte le ubicazioni non aggiornate o inserite afferenti all'oggetto
                logInfo(className, "Id oggetto [" + idOggetto + "] Id ubicazioni aggiornate da non cancellare : " + idsUbicazioniAggiornate.toString());
                if (!idsUbicazioniAggiornate.isEmpty()) {
                    ubicazioneOggettoDAO.deleteUbicazioniOggettoByNotInIdUbicazioniOggetto(idOggetto, idsUbicazioniAggiornate);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return resUpdateUbicazione;
    }

    /**
     * Validate dto error dto.
     *
     * @param oggetto        the oggetto
     * @param codAdempimento the cod adempimento
     * @param isUpdate       the is update
     * @return the error dto
     */
    @Override
    public ErrorDTO validateDTO(OggettoUbicazioneExtendedDTO oggetto, String codAdempimento, Boolean isUpdate) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        error = validateCodScriva(oggetto, details);
        if (error != null) {
            return error;
        }
        error = validateTipologiaOggetto(oggetto, codAdempimento, details);
        if (error != null) {
            return error;
        }

        if (StringUtils.isBlank(oggetto.getDenOggetto())) {
            details.put("den_oggetto", ValidationResultEnum.MANDATORY.getDescription());
        } else if (oggetto.getDenOggetto().length() > 500) {
            details.put("den_oggetto", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isBlank(oggetto.getDesOggetto())) {
            details.put("des_oggetto", ValidationResultEnum.MANDATORY.getDescription());
        } else if (oggetto.getDesOggetto().length() > 2000) {
            details.put("des_oggetto", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (null == oggetto.getIdMasterdata()) {
            details.put("id_masterdata", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == oggetto.getIdMasterdataOrigine()) {
            details.put("id_masterdata_origine", ValidationResultEnum.MANDATORY.getDescription());
        }

        /*
        if (isUpdate) {
            if (null == oggetto.getStatoOggetto() || (null != oggetto.getStatoOggetto() && null == oggetto.getStatoOggetto().getIdStatoOggetto())) {
                details.put("stato_oggetto", ValidationResultEnum.MANDATORY.getDescription());
            }
        }
        */

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        } else {
            error = validateUbicazioni(oggetto, details);
        }
        return error;
    }

    /**
     * Validate ubicazioni.
     *
     * @param oggetto the oggetto
     * @param details the details
     */
    private ErrorDTO validateUbicazioni(OggettoUbicazioneExtendedDTO oggetto, Map<String, String> details) {
        logBegin(className);
        ErrorDTO error = null;
        List<UbicazioneOggettoExtendedDTO> ubicazioni = oggetto.getUbicazioneOggetto();
        if (CollectionUtils.isNotEmpty(ubicazioni)) {
            for (UbicazioneOggettoExtendedDTO ubica : ubicazioni) {
                if (null == ubica.getComune() || (null != ubica.getComune() && null == ubica.getComune().getIdComune())) {
                    details.put("comune", ValidationResultEnum.MANDATORY.getDescription());
                }
                if (StringUtils.isNotBlank(ubica.getDenIndirizzo()) && ubica.getDenIndirizzo().length() > 100) {
                    details.put("den_indirizzo", ValidationResultEnum.INVALID_LENGTH.getDescription());
                }
                if (StringUtils.isNotBlank(ubica.getNumCivico()) && ubica.getNumCivico().length() > 30) {
                    details.put("num_civico", ValidationResultEnum.INVALID_LENGTH.getDescription());
                }
                if (StringUtils.isNotBlank(ubica.getDesLocalita()) && ubica.getDesLocalita().length() > 250) {
                    details.put("des_localita", ValidationResultEnum.INVALID_LENGTH.getDescription());
                }
                if (StringUtils.isNotBlank(ubica.getIndGeoProvenienza()) && ubica.getIndGeoProvenienza().length() > 4) {
                    details.put("ind_geo_provenienza", ValidationResultEnum.INVALID_LENGTH.getDescription());
                }
                if (!details.isEmpty()) {
                    error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
                    break;
                }
            }
        }
        logEnd(className);
        return error;
    }

    /**
     * Validate tipologia oggetto.
     *
     * @param oggetto        the oggetto
     * @param codAdempimento the cod adempimento
     * @param details        the details
     */
    private ErrorDTO validateTipologiaOggetto(OggettoUbicazioneExtendedDTO oggetto, String codAdempimento, Map<String, String> details) {
        logBegin(className);
        ErrorDTO error = null;
        if (null == oggetto.getTipologiaOggetto() || (null != oggetto.getTipologiaOggetto() && null == oggetto.getTipologiaOggetto().getIdTipologiaOggetto())) {
            //assegnazione automatica tipologia oggetto
            if (StringUtils.isBlank(codAdempimento)) {
                details.put("tipologia_oggetto", ValidationResultEnum.MANDATORY.getDescription());
            } else {
                List<TipologiaOggettoExtendedDTO> tipologiaOggettoList = tipologiaOggettoDAO.loadTipologiaOggettoByCodeAdempimento(codAdempimento);
                tipologiaOggettoList = tipologiaOggettoList.stream()
                        .filter(tipologiaOggetto -> tipologiaOggetto.getFlgAssegna() == Boolean.TRUE)
                        .collect(Collectors.toList());
                if (!tipologiaOggettoList.isEmpty()) {
                    oggetto.setTipologiaOggetto(tipologiaOggettoList.get(0));
                } else {
                    details.put("tipologia_oggetto", ValidationResultEnum.INVALID.getDescription());
                    error = getErrorManager().getError("400", "E040", "Attenzione: non è possibile attribuire la tipologia all'oggetto dell'istanza, contatta l'assistenza Scrivanie.", details, null);
                }
            }
        }
        logEnd(className);
        return error;
    }

    /**
     * Validate cod scriva.
     *
     * @param oggetto the oggetto
     * @param details the details
     */
    private ErrorDTO validateCodScriva(OggettoUbicazioneExtendedDTO oggetto, Map<String, String> details) {
        logBegin(className);
        ErrorDTO error = null;
        if (StringUtils.isNotBlank(oggetto.getCodScriva())) {
            List<OggettoDTO> oggettoList = oggettoDAO.loadOggettoByCodScriva(oggetto.getCodScriva());
            if (oggettoList != null && !oggettoList.isEmpty()) {
                details.put("cod_scriva", ValidationResultEnum.DUPLICATE.getDescription());
                error = getErrorManager().getError("400", "E048", "Attenzione: il codice inserito è già presente.", details, null);
            }
        } else {
            oggetto.setCodScriva(oggettoDAO.getNextCodScriva());
        }
        logEnd(className);
        return error;
    }

    /**
     * Save oggetto by sede operativa long.
     *
     * @param sedeOperativa the sede operativa
     * @param attoreScriva  the attore scriva
     * @return the long
     */
    private Long saveOggettoBySedeOperativa(SedeOperativa sedeOperativa, AttoreScriva attoreScriva) {
        logBegin(className);
        Long idOggetto = null;
        try {
            if (sedeOperativa != null) {
                String codSira = StringUtils.isNotBlank(sedeOperativa.getCodiceSira()) ? sedeOperativa.getCodiceSira() : sedeOperativa.getCodiceSiraValidato();
                OggettoDTO oggetto = new OggettoDTO();
                oggetto.setCodOggettoFonte(codSira);
                oggetto.setCoordinataX(StringUtils.isNotBlank(sedeOperativa.getCoordX()) ? new BigDecimal(sedeOperativa.getCoordX()) : null);
                oggetto.setCoordinataY(StringUtils.isNotBlank(sedeOperativa.getCoordY()) ? new BigDecimal(sedeOperativa.getCoordY()) : null);
                oggetto.setDenOggetto(sedeOperativa.getDenominazione());
                MasterdataDTO master = masterdataDAO.loadByCode("ANAGAMB");
                oggetto.setIdMasterdata(master.getIdMasterdata());
                oggetto.setIdMasterdataOrigine(master.getIdMasterdata());
                oggetto.setIdStatoOggetto(10L);
                oggetto.setIdTipologiaOggetto(0L);
                if (StringUtils.isBlank(oggetto.getGestAttoreIns())) {
                    oggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idOggetto = oggettoDAO.saveOggetto(oggetto);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idOggetto;
    }

    /**
     * Save ubicazione oggetto by sede operativa long.
     *
     * @param idOggetto     the id oggetto
     * @param sedeOperativa the sede operativa
     * @param attoreScriva  the attore scriva
     * @return the long
     */
    private Long saveUbicazioneOggettoBySedeOperativa(Long idOggetto, SedeOperativa sedeOperativa, AttoreScriva attoreScriva) {
        logBegin(className);
        Long idUbicazioneOggetto = null;
        try {
            if (idOggetto != null && sedeOperativa != null) {
                UbicazioneOggettoDTO ubi = new UbicazioneOggettoDTO();
                ubi.setIdOggetto(idOggetto);
                ubi.setDenIndirizzo(sedeOperativa.getIndirizzo());
                ubi.setNumCivico(sedeOperativa.getNumeroCivico());
                ubi.setDesLocalita(sedeOperativa.getLocalita());
                List<ComuneExtendedDTO> comuneList = comuneDAO.loadComuneByCodIstat(sedeOperativa.getCodiceIstatComune());
                ubi.setIdComune(comuneList != null && !comuneList.isEmpty() ? comuneList.get(0).getIdComune() : null);
                if (StringUtils.isBlank(ubi.getGestAttoreIns())) {
                    ubi.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                idUbicazioneOggetto = ubicazioneOggettoDAO.saveUbicazioneOggetto(ubi);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return idUbicazioneOggetto;
    }


}
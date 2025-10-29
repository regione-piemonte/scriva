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

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.EsitoProcedimentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaResponsabiliDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaNatura2000DAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PubSearchIstanzeDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoProcedimentoStataleDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzePubblicateService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OrganoTecnicoDTO;
import it.csi.scriva.scrivabesrv.dto.ProcedimentoStataleDTO;
import it.csi.scriva.scrivabesrv.dto.PubCompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubNaturaGiuridicaDTO;
import it.csi.scriva.scrivabesrv.dto.PubOggettoIstanzaUbicazioneDTO;
import it.csi.scriva.scrivabesrv.dto.PubSearchDTO;
import it.csi.scriva.scrivabesrv.dto.PubSoggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubTipoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.PubUbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SintesiDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The interface Istanze pubblicate service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class IstanzePubblicateServiceImpl extends BaseServiceImpl implements IstanzePubblicateService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    @Autowired
    private PubSearchIstanzeDAO pubSearchIstanzeDAO;

    @Autowired
    private IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    @Autowired
    private OggettoIstanzaDAO oggettoIstanzaDAO;

    @Autowired
    private UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;

    @Autowired
    private SoggettoIstanzaDAO soggettoIstanzaDAO;


    @Autowired
    private JsonDataManager jsonDataManager;

    @Autowired
    private IstanzaResponsabiliDAO istanzaResponsabiliDAO;


    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private EsitoProcedimentoDAO esitoProcedimentoDAO;

    @Autowired
    private StatoProcedimentoStataleDAO statoProcedimentoStataleDAO;

    @Autowired
    private OggettoIstanzaAreaProtettaDAO oggettoIstanzaAreaProtettaDAO;

    @Autowired
    private OggettoIstanzaNatura2000DAO oggettoIstanzaNatura2000DAO;


    /**
     * Search istanze pubblicate list.
     *
     * @param searchCriteria the search criteria
     * @param attoreScriva   the attore scriva
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
    @Override
    public List<PubIstanzaDTO> searchIstanzePubblicate(PubSearchDTO searchCriteria, AttoreScriva attoreScriva, Integer offset, Integer limit, String sort) {
        logBegin(className);
        List<PubIstanzaDTO> pubIstanzaList = null;
        try {
            pubIstanzaList = pubSearchIstanzeDAO.searchIstanze(
            	searchCriteria, offset == null ? null : String.valueOf(offset), limit == null ? null : String.valueOf(limit), sort, attoreScriva
            );
            for (PubIstanzaDTO pubIstanzaDTO : pubIstanzaList) {
                List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(pubIstanzaDTO.getIdIstanza());


                pubIstanzaDTO.setCompetenzaTerritorio(mapIstanzaCompetenza(istanzaCompetenzaList));
                pubIstanzaDTO.setOggettoIstanza(mapOggettoIstanza(pubIstanzaDTO.getIdIstanza()));
                List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(pubIstanzaDTO.getIdIstanza());
                for(SoggettoIstanzaExtendedDTO soggIstList : soggettoIstanzaList) {
                	if(soggIstList.getTipoSoggetto().getCodiceTipoSoggetto().equalsIgnoreCase("pf")) {
                		soggIstList.setNome("Privato");
                		soggIstList.setCognome("Soggetto");
                	}
                }
                pubIstanzaDTO.setSoggettoIstanza(mapSoggettoIstanza(soggettoIstanzaList));
                // SCRIVA-793 mi prendo gli oggetti nuovi
                if (pubIstanzaDTO.getEsitoProcedimento()!=null && pubIstanzaDTO.getEsitoProcedimento().getIdEsitoProcedimento()>0) {
                	List<EsitoProcedimentoDTO> esitoProcedimentoList =
                		esitoProcedimentoDAO.loadEsitoProcedimento(pubIstanzaDTO.getEsitoProcedimento().getIdEsitoProcedimento(), "");
                    pubIstanzaDTO.setEsitoProcedimento(esitoProcedimentoList.get(0));
                }
                if (pubIstanzaDTO.getEsitoProcedimentoStatale()!=null && pubIstanzaDTO.getEsitoProcedimentoStatale().getIdEsitoProcedimento()>0) {
                	List<EsitoProcedimentoDTO> esitoProcedimentoStataleList =
                		esitoProcedimentoDAO.loadEsitoProcedimento(pubIstanzaDTO.getEsitoProcedimentoStatale().getIdEsitoProcedimento(), "");
                	pubIstanzaDTO.setEsitoProcedimentoStatale(esitoProcedimentoStataleList.get(0));
                }
                if (pubIstanzaDTO.getStatoProcedimentoStatale()!=null && pubIstanzaDTO.getStatoProcedimentoStatale().getIdStatoProcedStatale()>0) {
                	List<StatoProcedimentoStataleDTO>  statoProcedimentoStataleList =
                		statoProcedimentoStataleDAO.loadStatiProcedimentoStataleById(pubIstanzaDTO.getStatoProcedimentoStatale().getIdStatoProcedStatale());
                	pubIstanzaDTO.setStatoProcedimentoStatale(statoProcedimentoStataleList.get(0));
                }
                //SCRIVA-945
                List<Timestamp> dataInizioOsservazioneListDB = pubSearchIstanzeDAO.getStoricoDataInizioOsservazione(pubIstanzaDTO.getIdIstanza());
                List<Timestamp> dataInizioOsservazioneList = new ArrayList<>();
                
                List<Timestamp> dataInizioOssDefault = null;
                
                pubIstanzaDTO.setDataInizioOsservazione(dataInizioOssDefault);
                
                if (dataInizioOsservazioneListDB.size()> 0) {
                	//dataInizioOsservazioneListDB.add(pubIstanzaDTO.getDataInizioOsservazione().get(0)); ---> DA TESTARE PER LA 1460
                	for (Timestamp dataInizioOsservazione: dataInizioOsservazioneListDB) {
                		if (dataInizioOsservazione!=null) {
                			dataInizioOsservazioneList.add(dataInizioOsservazione);
                		}
                	}
                	//TODO: verificare se toglie i duplicati
                	List<Timestamp> listWithoutDuplicates = dataInizioOsservazioneList.stream()
                        .collect(Collectors.toSet())
                        .stream()
                        .collect(Collectors.toList());

                	pubIstanzaDTO.setDataInizioOsservazione(listWithoutDuplicates);
                }

                List<Timestamp> dataFineOsservazione = pubSearchIstanzeDAO.getStoricoMaxDataFineOsservazione(pubIstanzaDTO.getIdIstanza());
                if (dataFineOsservazione.size()> 0 && dataFineOsservazione.get(0)!=null) {
                	if (dataFineOsservazione.get(0).after(pubIstanzaDTO.getDataFineOsservazione())) {
                		pubIstanzaDTO.setDataFineOsservazione(dataFineOsservazione.get(0));
                	}
                }
            }
            setDataFromJsonData(pubIstanzaList);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return pubIstanzaList;
    }

    @Override
	public List<SintesiDTO> searchSintesiIstanzePubblicate(PubSearchDTO searchCriteria, AttoreScriva attoreScriva, Integer offset, Integer limit, String sort) {
	       logBegin(className);
	        List<SintesiDTO> SintesiList = null;
	        try {
	        	SintesiList = pubSearchIstanzeDAO.searchSintesiIstanze(
	            	searchCriteria, offset == null ? null : String.valueOf(offset), limit == null ? null : String.valueOf(limit), sort, attoreScriva
	            );
	        } catch (Exception e) {
	            logError(className, e);
	        } finally {
	            logEnd(className);
	        }
	        return SintesiList;
	}

    /**
     * Load istanza pubblicata by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public PubIstanzaDTO loadIstanzaPubblicataByIdIstanza(Long idIstanza) {
        logBegin(className);
        PubIstanzaDTO pubIstanza = null;
        try {
            List<PubIstanzaDTO> pubIstanzaList = pubSearchIstanzeDAO.loadIstanzaById(idIstanza);
            if (pubIstanzaList != null && !pubIstanzaList.isEmpty()) {
                pubIstanza = pubIstanzaList.get(0);
                pubIstanza.setCompetenzaTerritorio(mapIstanzaCompetenza(istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza)));
                pubIstanza.setOggettoIstanza(mapOggettoIstanza(idIstanza));
                pubIstanza.setSoggettoIstanza(mapSoggettoIstanza(soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza)));
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return pubIstanza;
    }

    /**
     * Load mappe string.
     *
     * @param id the id
     * @return the string
     */
    @Override
    public String loadMappe(Long id) {
        logBegin(className);
        return pubSearchIstanzeDAO.loadMappe(id);
    }

    /**
     * Map istanza competenza list.
     *
     * @param istanzaCompetenzaList the istanza competenza list
     * @return the list
     */
    public List<PubCompetenzaTerritorioDTO> mapIstanzaCompetenza(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBegin(className);
        List<PubCompetenzaTerritorioDTO> pubCompetenzaTerritorioList = new ArrayList<>();
        for (IstanzaCompetenzaExtendedDTO istanzaCompetenzaExtendedDTO : istanzaCompetenzaList) {
            PubCompetenzaTerritorioDTO pubCompetenzaTerritorio = new PubCompetenzaTerritorioDTO();
            CompetenzaTerritorioExtendedDTO competenzaTerritorio = istanzaCompetenzaExtendedDTO.getCompetenzaTerritorio();
            if (competenzaTerritorio != null && (Boolean.TRUE.equals(istanzaCompetenzaExtendedDTO.getFlgAutoritaPrincipale()) || Boolean.TRUE.equals(istanzaCompetenzaExtendedDTO.getFlgAutoritaAssegnataBO()))) {
                pubCompetenzaTerritorio.setCapCompetenza(competenzaTerritorio.getCapCompetenza());
                pubCompetenzaTerritorio.setCodCompetenzaTerritorio(competenzaTerritorio.getCodCompetenzaTerritorio());
                pubCompetenzaTerritorio.setComuneCompetenza(competenzaTerritorio.getComuneCompetenza());
                pubCompetenzaTerritorio.setDataFineValidita(competenzaTerritorio.getDataFineValidita());
                pubCompetenzaTerritorio.setDesCompetenzaTerritorio(competenzaTerritorio.getDesCompetenzaTerritorio());
                pubCompetenzaTerritorio.setDesMailPec(competenzaTerritorio.getDesEmailPec());
                pubCompetenzaTerritorio.setIdCompetenzaTerritorio(competenzaTerritorio.getIdCompetenzaTerritorio());
                pubCompetenzaTerritorio.setIndirizzoCompetenza(competenzaTerritorio.getIndirizzoCompetenza());
                pubCompetenzaTerritorio.setNumCivicoIndirizzo(competenzaTerritorio.getNumCivicoIndirizzo());
                pubCompetenzaTerritorio.setOrario(competenzaTerritorio.getOrario());
                //pubCompetenzaTerritorio.setResponsabile(competenzaTerritorio.getResponsabile());
                pubCompetenzaTerritorio.setSitoWeb(competenzaTerritorio.getSitoWeb());
                pubCompetenzaTerritorio.setTipoCompetenza(competenzaTerritorio.getTipoCompetenza());
                pubCompetenzaTerritorio.setCodIpa(competenzaTerritorio.getCodIpa());
                //SCRIVA-1006
                pubCompetenzaTerritorio.setFlgAutoritaAssegnataBo(istanzaCompetenzaExtendedDTO.getFlgAutoritaAssegnataBO());
                pubCompetenzaTerritorio.setFlgAutoritaPrincipale(istanzaCompetenzaExtendedDTO.getFlgAutoritaPrincipale());
                pubCompetenzaTerritorio.setIndAssegnataDaSistema(istanzaCompetenzaExtendedDTO.getIndAssegnataDaSistema());
                pubCompetenzaTerritorioList.add(pubCompetenzaTerritorio);
            }
        }
        logEnd(className);
        return pubCompetenzaTerritorioList;
    }

    /**
     * Map soggetto istanza list.
     *
     * @param soggettoIstanzaExtendedList the soggetto istanza extended list
     * @return the list
     */
    private List<PubSoggettoIstanzaDTO> mapSoggettoIstanza(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaExtendedList) {
        logBegin(className);
        List<PubSoggettoIstanzaDTO> pubSoggettoIstanzaList = new ArrayList<>();
        for (SoggettoIstanzaExtendedDTO soggettoIstanzaExtendedDTO : soggettoIstanzaExtendedList) {
            if (soggettoIstanzaExtendedDTO.getIdSoggettoPadre() == null) {
                PubSoggettoIstanzaDTO pubSoggettoIstanzaDTO = new PubSoggettoIstanzaDTO();
                pubSoggettoIstanzaDTO.setCapSedeLegale(soggettoIstanzaExtendedDTO.getCapSedeLegale());
                pubSoggettoIstanzaDTO.setCodiceFiscale(soggettoIstanzaExtendedDTO.getCfSoggetto());
                pubSoggettoIstanzaDTO.setCognome(soggettoIstanzaExtendedDTO.getCognome());
                pubSoggettoIstanzaDTO.setComuneSedeLegale(soggettoIstanzaExtendedDTO.getComuneSedeLegale());
                pubSoggettoIstanzaDTO.setDenominazione(soggettoIstanzaExtendedDTO.getDenSoggetto());
                pubSoggettoIstanzaDTO.setIdSoggettoIstanza(soggettoIstanzaExtendedDTO.getIdSoggettoIstanza());
                pubSoggettoIstanzaDTO.setIdSoggettoPadre(soggettoIstanzaExtendedDTO.getIdSoggettoPadre());
                if (soggettoIstanzaExtendedDTO.getTipoNaturaGiuridica() != null) {
                    PubNaturaGiuridicaDTO naturaGiuridica = new PubNaturaGiuridicaDTO();
                    naturaGiuridica.setCodNaturaGiuridica(soggettoIstanzaExtendedDTO.getTipoNaturaGiuridica().getCodiceTipoNaturaGiuridica());
                    naturaGiuridica.setDesNaturaGiuridica(soggettoIstanzaExtendedDTO.getTipoNaturaGiuridica().getDescrizioneTipoNaturaGiuridica());
                    pubSoggettoIstanzaDTO.setNaturaGiuridica(naturaGiuridica);
                }
                pubSoggettoIstanzaDTO.setNazioneSedeLegale(soggettoIstanzaExtendedDTO.getNazioneSedeLegale());
                pubSoggettoIstanzaDTO.setNome(soggettoIstanzaExtendedDTO.getNome());
                pubSoggettoIstanzaDTO.setPartitaIva(soggettoIstanzaExtendedDTO.getPartitaIvaSoggetto());
                if (soggettoIstanzaExtendedDTO.getTipoSoggetto() != null) {
                    PubTipoSoggettoDTO tipoSoggetto = new PubTipoSoggettoDTO();
                    tipoSoggetto.setCodTipoSoggetto(soggettoIstanzaExtendedDTO.getTipoSoggetto().getCodiceTipoSoggetto());
                    tipoSoggetto.setDesTipoSoggetto(soggettoIstanzaExtendedDTO.getTipoSoggetto().getDescrizioneTipoSoggetto());
                    pubSoggettoIstanzaDTO.setTipoSoggetto(tipoSoggetto);
                }
                pubSoggettoIstanzaList.add(pubSoggettoIstanzaDTO);
            }
        }
        logEnd(className);
        return pubSoggettoIstanzaList;
    }

    /**
     * Map soggetto istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    private List<PubOggettoIstanzaUbicazioneDTO> mapOggettoIstanza(Long idIstanza) {
        logBegin(className);
        List<PubOggettoIstanzaUbicazioneDTO> pubOggettoIstanzaUbicazioneList = new ArrayList<>();
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaExtendedList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
        for (OggettoIstanzaExtendedDTO oggettoIstanzaExtendedDTO : oggettoIstanzaExtendedList) {
            List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaExtendedList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(oggettoIstanzaExtendedDTO.getIdOggettoIstanza());

            List<OggettoIstanzaNatura2000ExtendedDTO> sitiNatura2000 = oggettoIstanzaNatura2000DAO.loadOggettiIstanzaNatura2000(null, oggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            List<OggettoIstanzaAreaProtettaExtendedDTO> areeProtette = oggettoIstanzaAreaProtettaDAO.loadOggettiIstanzaAreaProtetta(null, oggettoIstanzaExtendedDTO.getIdOggettoIstanza());

            PubOggettoIstanzaUbicazioneDTO pubOggettoIstanzaUbicazioneDTO = new PubOggettoIstanzaUbicazioneDTO();
            pubOggettoIstanzaUbicazioneDTO.setCoordinataX(oggettoIstanzaExtendedDTO.getCoordinataX());
            pubOggettoIstanzaUbicazioneDTO.setCoordinataY(oggettoIstanzaExtendedDTO.getCoordinataY());
            pubOggettoIstanzaUbicazioneDTO.setDenOggetto(oggettoIstanzaExtendedDTO.getDenOggetto());
            pubOggettoIstanzaUbicazioneDTO.setDesOggetto(oggettoIstanzaExtendedDTO.getDesOggetto());
            pubOggettoIstanzaUbicazioneDTO.setIdOggettoIstanza(oggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            pubOggettoIstanzaUbicazioneDTO.setTipologiaOggetto(oggettoIstanzaExtendedDTO.getTipologiaOggetto());
            pubOggettoIstanzaUbicazioneDTO.setUbicazioneOoggetto(mapPubUbicazioneOggettoIstanza(oggettoIstanzaExtendedDTO.getIdOggettoIstanza()));
            pubOggettoIstanzaUbicazioneDTO.setFlgGeoreferito(StringUtils.isBlank(oggettoIstanzaExtendedDTO.getIndGeoStato()) ? Boolean.FALSE : Boolean.TRUE);

            pubOggettoIstanzaUbicazioneDTO.setOggettoIstanzaAreaProtetta(areeProtette);
            pubOggettoIstanzaUbicazioneDTO.setOggettoIstanzaNatura2000(sitiNatura2000);

            pubOggettoIstanzaUbicazioneList.add(pubOggettoIstanzaUbicazioneDTO);
        }
        logEnd(className);
        return pubOggettoIstanzaUbicazioneList;
    }

    /**
     * Map pub ubicazione oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    private List<PubUbicazioneOggettoIstanzaDTO> mapPubUbicazioneOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        List<PubUbicazioneOggettoIstanzaDTO> pubUbicazioneOggettoIstanzaList = new ArrayList<>();
        List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaExtendedList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(idOggettoIstanza);
        for (UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanzaExtendedDTO : ubicazioneOggettoIstanzaExtendedList) {
            PubUbicazioneOggettoIstanzaDTO pubUbicazioneOggettoIstanzaDTO = new PubUbicazioneOggettoIstanzaDTO();
            pubUbicazioneOggettoIstanzaDTO.setComune(ubicazioneOggettoIstanzaExtendedDTO.getComune());
            pubUbicazioneOggettoIstanzaDTO.setDenIndirizzo(ubicazioneOggettoIstanzaExtendedDTO.getDenIndirizzo());
            pubUbicazioneOggettoIstanzaDTO.setDesLocalita(ubicazioneOggettoIstanzaExtendedDTO.getDesLocalita());
            pubUbicazioneOggettoIstanzaDTO.setIdOggettoIstanza(ubicazioneOggettoIstanzaExtendedDTO.getIdOggettoIstanza());
            pubUbicazioneOggettoIstanzaDTO.setNumCivico(ubicazioneOggettoIstanzaExtendedDTO.getNumCivico());
            pubUbicazioneOggettoIstanzaList.add(pubUbicazioneOggettoIstanzaDTO);
        }
        logEnd(className);
        return pubUbicazioneOggettoIstanzaList;
    }


    /**
     * Sets data from json data.
     *
     * @param pubIstanzaList the pub istanza list
     */
    public void setDataFromJsonData(List<PubIstanzaDTO> pubIstanzaList) {
        logBegin(className);
        if (pubIstanzaList != null && !pubIstanzaList.isEmpty()) {
            for (PubIstanzaDTO pubIstanza : pubIstanzaList) {
                if (pubIstanza != null && pubIstanza.getIdIstanza() != null) {
                    IstanzaDTO istanza = istanzaDAO.findByPK(pubIstanza.getIdIstanza());
                    //pubIstanza.setOrganoTecnico(getOrganoTecnico(istanza.getJsonData()));
                    List<IstanzaResponsabileExtendedDTO> istanzaResponsabili = istanzaResponsabiliDAO.loadIstanzaResponsabiliByIstanza(pubIstanza.getIdIstanza());
                    List<IstanzaResponsabileExtendedDTO> istResp = istanzaResponsabili
                    		.stream()
                    		.filter(c -> c.getFlgRiservato() == true)
                    		.collect(Collectors.toList());
                    pubIstanza.setResponsabiliIstanza(istResp);
                    pubIstanza.setProcedimentoStatale(getProcedimentoStatale(istanza.getJsonData()));
                }
            }
        }
        logEnd(className);
    }

    /**
     * Gets organo tecnico.
     *
     * @param jsonDataIstanza the json data istanza
     * @return the organo tecnico
     */
    private OrganoTecnicoDTO getOrganoTecnico(String jsonDataIstanza) {
        logBegin(className);
        String jsonTag = "js_organo_tecnico";
        try {
            String jsonFragment = getJsonFragment(jsonDataIstanza, jsonTag);
            if (StringUtils.isBlank(jsonFragment)) {
            	jsonTag = "js_organoTecnico";
            	jsonFragment = getJsonFragment(jsonDataIstanza, jsonTag);
            }
            return StringUtils.isNotBlank(jsonFragment) ? new ObjectMapper().readValue(jsonFragment, OrganoTecnicoDTO.class) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    /**
     * Gets procedimento statale.
     *
     * @param jsonDataIstanza the json data istanza
     * @return the procedimento statale
     */
    private ProcedimentoStataleDTO getProcedimentoStatale(String jsonDataIstanza) {
        logBegin(className);
        String jsonTag = "js_procedimento_statale";
        try {
            String jsonFragment = getJsonFragment(jsonDataIstanza, jsonTag);
            if (StringUtils.isBlank(jsonFragment)) {
            	jsonTag = "js_procedimentoStatale";
            	jsonFragment = getJsonFragment(jsonDataIstanza, jsonTag);
            }
            return StringUtils.isNotBlank(jsonFragment) ? new ObjectMapper().readValue(jsonFragment, ProcedimentoStataleDTO.class) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    /**
     * Get json fragment string.
     *
     * @param jsonDataIstanza the json data istanza
     * @param jsonTag         the json tag
     * @return the string
     */
    public String getJsonFragment(String jsonDataIstanza, String jsonTag) {
        return jsonDataManager.searchValueFromJsonData(jsonDataIstanza, jsonTag);
    }



}
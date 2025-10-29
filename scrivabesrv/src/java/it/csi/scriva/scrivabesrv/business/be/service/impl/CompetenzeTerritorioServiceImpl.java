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

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompetenzaTerritorioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaOggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaResponsabiliDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompetenzeTerritorioService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.AssegnataDaSistemaEnum;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Competenze territorio service.
 */
@Component
public class CompetenzeTerritorioServiceImpl extends BaseApiServiceImpl implements CompetenzeTerritorioService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Competenza territorio dao.
     */
    @Autowired
    CompetenzaTerritorioDAO competenzaTerritorioDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Istanza competenza dao.
     */
    @Autowired
    IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    @Autowired
    IstanzaCompetenzaOggettoDAO istanzaCompetenzaOggettoDAO;

    @Autowired
    private JsonDataManager jsonDataManager;

    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    @Autowired
    IstanzaResponsabiliDAO istanzaResponsabiliDAO;


    @Override
    public List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(AttoreScriva attoreScriva) {
        logBegin(className);
        List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioList = null;
        try {
            competenzaTerritorioList = competenzaTerritorioDAO.loadCompetenzeTerritorio(attoreScriva.getComponente());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return competenzaTerritorioList;
    }

    /**
     * Load ambito list.
     *
     * @param idAdempimento the id adempimento
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    @Override
    public List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(Long idAdempimento, AttoreScriva attoreScriva) {
        logBegin(className);
        List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioList = null;
        try {
            competenzaTerritorioList = competenzaTerritorioDAO.loadCompetenzeTerritorio(idAdempimento, attoreScriva.getComponente());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return competenzaTerritorioList;
    }

    /**
     * Load istanza competenze territorio list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzeTerritorio(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;
        List<IstanzaCompetenzaExtendedDTO> istCompResult = new ArrayList<IstanzaCompetenzaExtendedDTO>();
        try {
            istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
            for(IstanzaCompetenzaExtendedDTO istanzaCompetenza : istanzaCompetenzaList) {
            	List<CompetenzaTerritorioResponsabileDTO> istanzaResponsabileList = competenzaTerritorioDAO.loadCompetenzeTerritorioByIdCompTerr(istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio());
            	istanzaCompetenza.getCompetenzaTerritorio().setResponsabiliCompetenzeTerritorio(istanzaResponsabileList);
            	istCompResult.add(istanzaCompetenza);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }

        return istCompResult;
    }

//    /**
//     * Load competenze territorio responsabile
//     *
//     * @param idComepetenzaTerritorioResponsabile the id competenze territorio responsabile
//     * @param attoreScriva  the attore scriva
//     * @return the list
//     */
//    @Override
//    public CompetenzaTerritorioResponsabileDTO loadCompetenzeTerritorioByIdCompResp(Long idCompetenzaTerritorioResponsabile) {
//        logBegin(className);
//        List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioList = null;
//        try {
//            competenzaTerritorioList = competenzaTerritorioDAO.loadCompetenzeTerritorioByIdCompResp(idAdempimento, attoreScriva.getComponente());
//        } catch (Exception e) {
//            logError(className, e);
//        } finally {
//            logEnd(className);
//        }
//        return competenzaTerritorioList;
//    }

    /**
     * Save istanza competenza territorio list.
     *
     * @param istanzaCompetenza the istanza competenza
     * @param attoreScriva      the attore scriva
     * @return the list
     */
    @Override
    public IstanzaCompetenzaExtendedDTO saveIstanzaCompetenzaTerritorio(IstanzaCompetenzaExtendedDTO istanzaCompetenza, AttoreScriva attoreScriva) throws DuplicateRecordException {
        logBegin(className);
        try {
            istanzaCompetenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            Long idIstanza = istanzaCompetenzaDAO.saveIstanzaCompetenza(istanzaCompetenza.getDTO());
            if (idIstanza == null) {
                return null;
            }
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza, istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio());
            // aggiornamento jsonData
            jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanzaCompetenza.getIdIstanza(), istanzaCompetenzaList);
            return istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty() ? istanzaCompetenzaList.get(0) : null;
        } catch (DuplicateRecordException e) {
            logError(className, e);
            throw new DuplicateRecordException();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza competenza territorio istanza competenza extended dto.
     *
     * @param istanzaCompetenza the istanza competenza
     * @param attoreScriva      the attore scriva
     * @return the istanza competenza extended dto
     */
    @Override
    public IstanzaCompetenzaExtendedDTO updateIstanzaCompetenzaTerritorio(IstanzaCompetenzaExtendedDTO istanzaCompetenza, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            istanzaCompetenza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            Integer res = istanzaCompetenzaDAO.updateIstanzaCompetenza(istanzaCompetenza.getDTO());
            if (res == null || res < 1) {
                return null;
            }
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(istanzaCompetenza.getIdIstanza(), istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio());
            // aggiornamento jsonData
            jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanzaCompetenza.getIdIstanza(), istanzaCompetenzaList);
            return istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty() ? istanzaCompetenzaList.get(0) : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Verifica competenze territorio by id istanza list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> verificaCompetenzeTerritorioByIdIstanza(Long idIstanza, AttoreScriva attoreScriva) throws GenericException {
        logBegin(className);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList == null || istanzaList.isEmpty()) {
            return null;
        }
        IstanzaExtendedDTO istanza = istanzaList.get(0);
        istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza); // prende lista ac già  associate dall'utente

        // Caso in cui IndTipoSelezioneAC = S o M (perche non ha associato nessuna)
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty())
        {
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaPrincipaleList = getIstanzaCompetenzaPrincipaleList(istanzaCompetenzaList);  // filtra quelle con flag principale true
            List<Long> idCompetenzeTerritorioVerificaList = getIdIstanzaCompetenzeTerritorioVerificaList(idIstanza, istanza.getAdempimento().getIdAdempimento()); // estrae tutte quelle associabili all'istanza per territorio.
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaNoVerified = verifyIstanzaCompetenzaTerritorioPrincipale(istanzaCompetenzaPrincipaleList, idCompetenzeTerritorioVerificaList);// rimuove quelle nazionali o presenti nell'elenco delle totali assegnabili in modo da essere sicuro che non ce ne siano non assegnabili
            if (istanzaCompetenzaNoVerified != null && !istanzaCompetenzaNoVerified.isEmpty())
            {
                ErrorDTO error = getErrorManager().getError("404", "E027", null, null, null);
                error.setTitle(error.getTitle().replace("{PH_AC}", getIstanzaCompetenzaPrincipaleStringList(istanzaCompetenzaNoVerified)));
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }
        }
        else
        { // Caso in cui IndTipoSelezioneAC = N
            List<Long> idCompetenzeTerritorioPrincipaleToSaveList = getIdIstanzaCompetenzeTerritorioPrincipaliList(idIstanza, istanza.getAdempimento().getIdAdempimento()); // recupera lista di???
            logDebug(className, "idCompetenzeTerritorioPrincipaleToSaveList (idIstanza=" + idIstanza + "): " + idCompetenzeTerritorioPrincipaleToSaveList);

            if (idCompetenzeTerritorioPrincipaleToSaveList != null && !idCompetenzeTerritorioPrincipaleToSaveList.isEmpty())
            {
                istanzaCompetenzaList = saveIstanzaCompetenzaTerritorioList(idIstanza, idCompetenzeTerritorioPrincipaleToSaveList, Boolean.FALSE, Boolean.FALSE, attoreScriva);
                // aggiornamento jsonData
                jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanza.getIdIstanza(), istanzaCompetenzaList);
            }
            else
            {
                ErrorDTO error = getErrorManager().getError("404", "E030", null, null, null);
                logError(className, error);
                logEnd(className);
                throw new GenericException(error);
            }
        }
        logEnd(className);
        return istanzaCompetenzaList;
    }

	/**
	 * Gestione AC.
	 *
	 * @param idIstanza the id istanza
	 * @param ac3 the ac 3
	 * @param tipoSelezione the tipo selezione
	 * @param attoreScriva the attore scriva
	 * @return the list
	 */
	@Override
	public List<IstanzaCompetenzaExtendedDTO> gestioneAC(Long idIstanza, Boolean ac3, String tipoSelezione, AttoreScriva attoreScriva) throws GenericException {
	    logBegin(className);

	    List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzeGiaPresenti = new ArrayList<IstanzaCompetenzaExtendedDTO>();
	    List<CompetenzaTerritorioExtendedDTO> ricalcolaCompetenze = new ArrayList<CompetenzaTerritorioExtendedDTO>(); //le AC da associare all'adempimento
	    List<CompetenzaTerritorioExtendedDTO> competenzeConAdesioneDue= new ArrayList<CompetenzaTerritorioExtendedDTO>() ; //le AC con IndAdesioneAdempimento = 2 (da associare sempre)
	    List<CompetenzaTerritorioExtendedDTO> competenzeNonAncoraAssociatePerDue = new ArrayList<CompetenzaTerritorioExtendedDTO>(); // //le AC con IndAdesioneAdempimento = 2 non ancora associate
	    List<CompetenzaTerritorioExtendedDTO> competenzeConAdesioneTre= new ArrayList<CompetenzaTerritorioExtendedDTO>() ; //le AC con IndAdesioneAdempimento = 3 (da associare se ac3 e true)
	    List<CompetenzaTerritorioExtendedDTO> competenzeNonAncoraAssociatePerTre = new ArrayList<CompetenzaTerritorioExtendedDTO>(); // //le AC con IndAdesioneAdempimento = 3 non ancora associate
	    List<IstanzaCompetenzaExtendedDTO>	competenzeSalvateSinoAdOra = new ArrayList<IstanzaCompetenzaExtendedDTO>();
	    List<IstanzaCompetenzaOggettoDTO> listaCompetenzaPerOggetto = new ArrayList<IstanzaCompetenzaOggettoDTO>();

        istanzaCompetenzeGiaPresenti = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza); // prende lista ac già  associate dall'utente;
        competenzeSalvateSinoAdOra = istanzaCompetenzeGiaPresenti; // variabile di riferimento della consistenza corrente delle competenze durante tutto il processo
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza); //recupera istanza
        if (istanzaList == null || istanzaList.isEmpty()) {
            return null;
        }
        IstanzaExtendedDTO istanza = istanzaList.get(0);
        final Long idAdempimento = istanza.getAdempimento().getIdAdempimento();

        // estrae le AC da associare all'adempimento
         ricalcolaCompetenze = competenzaTerritorioDAO.loadCompetenzeTerritorio(idAdempimento , null);

        // TODO Estrai metodo generico per entrambi
       if (CollectionUtils.isNotEmpty(ricalcolaCompetenze))
       {
           // filtra quelle con IndAdesioneAdempimento = 2 (da associare sempre)
            competenzeConAdesioneDue = ricalcolaCompetenze.stream()
           	    .filter(c -> c.getIndAdesioneAdempimento() != null && c.getIndAdesioneAdempimento() == 2)
           	    .collect(Collectors.toList());

           // verifica quelle eventualmente gia associate e restituisce la lista di quelle non ancora associate e quindi da associare
           if( CollectionUtils.isNotEmpty(competenzeConAdesioneDue) )
           {
        	   final List<IstanzaCompetenzaExtendedDTO>  istanzaCompetenzeGiaPresentiFilter = istanzaCompetenzeGiaPresenti; // per fare il filtro deve essere final
               competenzeNonAncoraAssociatePerDue = competenzeConAdesioneDue.stream()
                	    .filter(c -> istanzaCompetenzeGiaPresentiFilter.stream()
                	        .noneMatch(i -> i.getCompetenzaTerritorio().getIdCompetenzaTerritorio().equals(c.getIdCompetenzaTerritorio())))
                	    .collect(Collectors.toList());
           }

          // salva quelle da associare e restituisce tutte quelle associate sino ad ora
          if(CollectionUtils.isNotEmpty(competenzeNonAncoraAssociatePerDue))
          {
         		competenzeSalvateSinoAdOra = saveIstanzaCompetenzaTerritorioList(idIstanza, competenzeNonAncoraAssociatePerDue, Boolean.FALSE, Boolean.FALSE,AssegnataDaSistemaEnum.ADESIONE_2, attoreScriva);
          }


       }



        // verifica parametro AC3  e che ovviamente ci siano AC associate a quell' adempimento
        if(ac3 == true && CollectionUtils.isNotEmpty(ricalcolaCompetenze) )
        {

        	 // filtra quelle con IndAdesioneAdempimento = 3
        	competenzeConAdesioneTre = ricalcolaCompetenze.stream()
           	    .filter(c -> c.getIndAdesioneAdempimento() != null && c.getIndAdesioneAdempimento() == 3)
           	    .collect(Collectors.toList());

           // verifica quelle eventualmente gia associate e restituisce la lista di quelle non ancora associate e quindi da associare
           if( CollectionUtils.isNotEmpty(competenzeConAdesioneTre) )
           {
        	   final List<IstanzaCompetenzaExtendedDTO>  istanzaCompetenzeGiaPresentiFilter = istanzaCompetenzeGiaPresenti; // per fare il filtro deve essere final
        	   competenzeNonAncoraAssociatePerTre = competenzeConAdesioneTre.stream()
                	    .filter(c -> istanzaCompetenzeGiaPresentiFilter.stream()
                	        .noneMatch(i -> i.getCompetenzaTerritorio().getIdCompetenzaTerritorio().equals(c.getIdCompetenzaTerritorio())))
                	    .collect(Collectors.toList());
           }

          // salva quelle da associare e restituisce tutte quelle associate sino ad ora
          if(CollectionUtils.isNotEmpty(competenzeNonAncoraAssociatePerTre))
          {
         		competenzeSalvateSinoAdOra = saveIstanzaCompetenzaTerritorioList(idIstanza, competenzeNonAncoraAssociatePerTre, Boolean.FALSE, Boolean.FALSE,AssegnataDaSistemaEnum.ADESIONE_3, attoreScriva);
          }


        }
        else
        {
        	//verifica comuni o siti natura2000
        	// estra la configurazione
        	List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByIdAdempimentoChiave(idAdempimento, "IndTipoCalcoloAC") ;
	    	// verifica che ci sia la chiave IndTipoCalcoloAC  per quell'adempimento
        	if (CollectionUtils.isNotEmpty(adempimentoConfigList))
	    	{
        		AdempimentoConfigDTO adempimentoConfig = adempimentoConfigList.get(0);
        		String IndTipoCalcoloAC = adempimentoConfig.getValore();
   // SE COMUNI
        		if(IndTipoCalcoloAC.equalsIgnoreCase("COMUNE"))
        		{
        			// recupera le competenza attribuibili per localizzazione degli oggetti nei comuni
        		   listaCompetenzaPerOggetto = istanzaCompetenzaOggettoDAO.loadIstanzaCompetenzaOggettiPerComuni(idIstanza);

        		  /* if(CollectionUtils.isNotEmpty(listaCompetenzaPerOggetto))
					{*/

						// rende final la lista delle AC presenti attualmente perche è necessario per
						// usarle col filtro
						final List<IstanzaCompetenzaExtendedDTO> competenzeSalvateSinoAdOraTemp = competenzeSalvateSinoAdOra;

						// filtra le AC assegnabili tramite COMUNI all'istanza ripulendo quelle gia
						// assegnate precedentemente dal sistema o dall'utente
						List<IstanzaCompetenzaOggettoDTO> listaCompetenzaPerOggettoPulita = listaCompetenzaPerOggetto
								.stream().filter(unicoIdCompTerr -> {
									boolean idNonPresente = competenzeSalvateSinoAdOraTemp.stream()
											.noneMatch(compSalvata -> {
												int indAssegnato = Integer
														.parseInt(compSalvata.getIndAssegnataDaSistema());
												return unicoIdCompTerr.getIdCompetenzaTerritorio()
														.equals(compSalvata.getIdCompetenzaTerritorio())
														&& (indAssegnato == 0 || indAssegnato == 2
																|| indAssegnato == 3 || indAssegnato == 6);
											});
									return idNonPresente;
								}).collect(Collectors.toList());

						// DA CANCELLARE per test elimina alcune AC per vericare i vari scenari nel
						// caso no le trovi
						// listaCompetenzaPerOggettoPulita.removeIf(oggetto ->
						// oggetto.getIdCompetenzaTerritorio().equals(17L) &&
						// oggetto.getIdOggettoIstanza().equals(731L));

						// DA CANCELLARE per test elimina alcune AC per vericare i vari scenari nel
						// caso no le trovi
						// listaCompetenzaPerOggettoPulita.removeIf(oggetto ->
						// oggetto.getIdCompetenzaTerritorio().equals(42L) );

						// recupera le AC gia assegnate in relazione agli oggetti di pertinenza dalla
						// scriva_r_istanza_competenza_oggetto (SRICO)
						List<IstanzaCompetenzaOggettoDTO> listaCompetenzaPerOggettoGiaPresenti = istanzaCompetenzaOggettoDAO
								.loadIstanzaCompetenzaOggetti(idIstanza);

						// Inserisci gli oggetti non presenti in listaCompetenzaPerOggettoGiaPresenti
						// confrontando la lista delle competenze presenti nella SRICO con quelle da
						// ricalcolate e ripulite listaCompetenzaPerOggettoPulita
						// cicla sulla lista di competenze non ancora inserite
						for (IstanzaCompetenzaOggettoDTO oggettoPulito : listaCompetenzaPerOggettoPulita) {
							// verifica che non ci siano gia copie di idOggetto/idCompetenzaterritorio nella
							// SRICO
							boolean notPresent = listaCompetenzaPerOggettoGiaPresenti.stream()
									.noneMatch(oggettoPresente -> oggettoPulito.getIdCompetenzaTerritorio()
											.equals(oggettoPresente.getIdCompetenzaTerritorio())
											&& oggettoPulito.getIdOggettoIstanza()
													.equals(oggettoPresente.getIdOggettoIstanza()));
							// se non presenti le salva
							if (notPresent) {
								competenzeSalvateSinoAdOra = istanzaCompetenzaDAO
										.loadIstanzaCompetenzaByIdIstanza(idIstanza);
								saveCompetenza(oggettoPulito, competenzeSalvateSinoAdOra, attoreScriva, AssegnataDaSistemaEnum.COMUNE);
							}
						}

						// Rimuovi gli oggetti non presenti in listaCompetenzaPerOggettoPulita
						// cicla gli istanzeCompetenzeOggetto gia presenti
						for (IstanzaCompetenzaOggettoDTO oggettoPresente : listaCompetenzaPerOggettoGiaPresenti) {
							// se non sono presenti nella lista di quelle ricalcolate le elimina
							boolean notPresent = listaCompetenzaPerOggettoPulita.stream()
									.noneMatch(oggettoPulito -> oggettoPresente.getIdCompetenzaTerritorio()
											.equals(oggettoPulito.getIdCompetenzaTerritorio())
											&& oggettoPresente.getIdOggettoIstanza()
													.equals(oggettoPulito.getIdOggettoIstanza()));

							if (notPresent) {
								deleteCompetenza(oggettoPresente);
							}
						}

        		}
   // SE SN2000
        		else if (IndTipoCalcoloAC.equalsIgnoreCase("SN2000 "))
        		{
        			// recupera le competenza attribuibili ai siti Natura 2000
         		   listaCompetenzaPerOggetto = istanzaCompetenzaOggettoDAO.loadIstanzaCompetenzaOggettiPerSN200(idIstanza);

           		  /* if(CollectionUtils.isNotEmpty(listaCompetenzaPerOggetto)) eliminato perche in caso fosse cancellato l'unico sito assegnato in precedenza nn veniva eliminato
    					{*/

    						// rende final la lista delle AC presenti attualmente perche è necessario per
    						// usarle col filtro
    						final List<IstanzaCompetenzaExtendedDTO> competenzeSalvateSinoAdOraTemp = competenzeSalvateSinoAdOra;

    						// filtra le AC assegnabili tramite SN2000 all'istanza, ripulendo quelle gia
    						// assegnate precedentemente dal sistema o dall'utente
    						List<IstanzaCompetenzaOggettoDTO> listaCompetenzaPerOggettoPulita = listaCompetenzaPerOggetto
    								.stream().filter(unicoIdCompTerr -> {
    									boolean idNonPresente = competenzeSalvateSinoAdOraTemp.stream()
    											.noneMatch(compSalvata -> {
    												int indAssegnato = Integer
    														.parseInt(compSalvata.getIndAssegnataDaSistema());
    												return unicoIdCompTerr.getIdCompetenzaTerritorio()
    														.equals(compSalvata.getIdCompetenzaTerritorio())
    														&& (indAssegnato == 0 || indAssegnato == 2
    																|| indAssegnato == 3 || indAssegnato == 6);
    											});
    									return idNonPresente;
    								}).collect(Collectors.toList());


    						// recupera le AC gia assegnate in relazione agli oggetti di pertinenza dalla
    						// scriva_r_istanza_competenza_oggetto (SRICO)
    						List<IstanzaCompetenzaOggettoDTO> listaCompetenzaPerOggettoGiaPresenti = istanzaCompetenzaOggettoDAO
    								.loadIstanzaCompetenzaOggetti(idIstanza);

    						// Inserisci gli oggetti non presenti in listaCompetenzaPerOggettoGiaPresenti
    						// confrontando la lista delle competenze presenti nella SRICO con quelle da
    						// ricalcolate e ripulite listaCompetenzaPerOggettoPulita
    						// cicla sulla lista di competenze non ancora inserite
    						for (IstanzaCompetenzaOggettoDTO oggettoPulito : listaCompetenzaPerOggettoPulita) {
    							// verifica che non ci siano gia copie di idOggetto/idCompetenzaterritorio nella
    							// SRICO
    							boolean notPresent = listaCompetenzaPerOggettoGiaPresenti.stream()
    									.noneMatch(oggettoPresente -> oggettoPulito.getIdCompetenzaTerritorio()
    											.equals(oggettoPresente.getIdCompetenzaTerritorio())
    											&& oggettoPulito.getIdOggettoIstanza()
    													.equals(oggettoPresente.getIdOggettoIstanza()));
    							// se non presenti le salva
    							if (notPresent) {
    								competenzeSalvateSinoAdOra = istanzaCompetenzaDAO
    										.loadIstanzaCompetenzaByIdIstanza(idIstanza);
    								saveCompetenza(oggettoPulito, competenzeSalvateSinoAdOra, attoreScriva, AssegnataDaSistemaEnum.NATURA_2000);
    							}
    						}

    						// Rimuovi gli oggetti non presenti in listaCompetenzaPerOggettoPulita
    						// cicla gliistanzeCompetenzeOggetto gia presenti
    						for (IstanzaCompetenzaOggettoDTO oggettoPresente : listaCompetenzaPerOggettoGiaPresenti) {
    							// se non sono presenti nella lista di quelle ricalcolate le elimina
    							boolean notPresent = listaCompetenzaPerOggettoPulita.stream()
    									.noneMatch(oggettoPulito -> oggettoPresente.getIdCompetenzaTerritorio()
    											.equals(oggettoPulito.getIdCompetenzaTerritorio())
    											&& oggettoPresente.getIdOggettoIstanza()
    													.equals(oggettoPulito.getIdOggettoIstanza()));

    							if (notPresent) {
    								deleteCompetenza(oggettoPresente);
    							}
    						}

        		}
	        }


        }


        // estrae tutte le AC associate
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzeRicalcolate = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
        // se non c'è nessuna autorita configurata oppure c'è ma nessuna è settata come principale da errore.
        if (CollectionUtils.isEmpty(istanzaCompetenzeRicalcolate) || istanzaCompetenzeRicalcolate.stream()
        		.noneMatch(IstanzaCompetenzaExtendedDTO::getFlgAutoritaPrincipale))
	     {
	         ErrorDTO error = getErrorManager().getError("404", "E030", null, null, null);
	         logError(className, error);
	         logEnd(className);
	         throw new GenericException(error);
	     }

        /*CONTROLLO COERENZA UBICAZIONE-AC ASSEGNATI DA BO/PRINCIPALI
         *   (Algoritmo 9bis – Controlli passo Opera – User Story US005 Presentazione Istanza Gestione Opera):
         *	 Il sistema controlla la presenza di almeno un COMUNE per ciascuna autorita competente principale
         *   (selezionata nell’orientamento o attribuita da BO o attribuita dal sistema e se il controllo
         *   non è superato presenta un messaggio di errore bloccante E027.*/


        // estrae tutte le AC principali e/assegnate da BO (escluse quelle che iniziano per (GEST_)
        List<IstanzaCompetenzaExtendedDTO> AutoritaPrincipaliOAssegnateDaBo = istanzaCompetenzeRicalcolate.stream()
                .filter(istanzaFilter -> (istanzaFilter.getFlgAutoritaAssegnataBO() || istanzaFilter.getFlgAutoritaPrincipale())
                        && !istanzaFilter.getCompetenzaTerritorio().getTipoCompetenza().getCodTipoCompetenza().startsWith("GEST_"))
                .collect(Collectors.toList());

         // estrae gli ID di tutte le AC associabili all'istanza calcolate tramite territorio
        List<Long> idCompetenzeTerritorioVerificaList = getIdIstanzaCompetenzeTerritorioVerificaList(idIstanza, istanza.getAdempimento().getIdAdempimento());

        // filtra la lista AutoritaPrincipaliOAssegnateDaBo rimuovendo gli elementi che hanno un ID di CompetenzaTerritorio presente in idIstanzaCompetenzeVerificaList o che hanno un codTipoCompetenza uguale a "NAZIONALE".
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaNoVerified = verifyIstanzaCompetenzaTerritorioPrincipale(AutoritaPrincipaliOAssegnateDaBo, idCompetenzeTerritorioVerificaList);

        if (istanzaCompetenzaNoVerified != null && !istanzaCompetenzaNoVerified.isEmpty())
        {
            ErrorDTO error = getErrorManager().getError("404", "E027", null, null, null);
            error.setTitle(error.getTitle().replace("{PH_AC}", getIstanzaCompetenzaPrincipaleStringList(istanzaCompetenzaNoVerified)));
            logError(className, error);
            logEnd(className);
            throw new GenericException(error);
        }
        
        // verifica se tra le AC ricalcolate ci siano AC precedenti derivanti dal quadro cat. progettuali
        // possibilita relativa alla sola VIA.
        Boolean hasIndAssegnataDaSistemaEqualTo6 = istanzaCompetenzeRicalcolate.stream()
        .anyMatch(myIstanza -> "6".equals(myIstanza.getIndAssegnataDaSistema()));
        
        // in caso affermativo le ricalcola in quanto in seguito alla modifica di un oggetto istanza (es comune di provincia diversa dal precedente)
        // occorre anche modificare le AC associate ferme restando le selezioni fatte dall'utente nel quadro cat. prog.
        if(hasIndAssegnataDaSistemaEqualTo6)
        {
        	calculateIstanzaCompetenzaTerritorioSecondarie(idIstanza, null, attoreScriva);
        	istanzaCompetenzeRicalcolate = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
        }

		return istanzaCompetenzeRicalcolate;
	}


	/**
	 * Delete competenza.
	 *
	 * @param oggettoPresente the oggetto presente
	 */
	private void deleteCompetenza(IstanzaCompetenzaOggettoDTO oggettoPresente ) {

		try {
	            istanzaCompetenzaOggettoDAO.deleteIstanzaCompetenzaOggetto(oggettoPresente.getIdIstanza(), oggettoPresente.getIdCompetenzaTerritorio(), oggettoPresente.getIdOggettoIstanza());
	        } catch (Exception e) {
	            logError(className, e);
	        }

  	   List<IstanzaCompetenzaOggettoDTO> listaCompetenzaPerOggettoPresenti = istanzaCompetenzaOggettoDAO.loadIstanzaCompetenzaOggetti(oggettoPresente.getIdIstanza());

  	   boolean idCompetenzaTerritorioPresente = listaCompetenzaPerOggettoPresenti.stream()
  	            .anyMatch(competenza ->
  	                    competenza.getIdCompetenzaTerritorio().equals(oggettoPresente.getIdCompetenzaTerritorio())
  	            );

  	    // Se idCompetenzaTerritorio non è presente nella lista, rimuovi l'IstanzaCompetenzaExtendedDTO corrispondente dal database
  	    if (!idCompetenzaTerritorioPresente) {

  	    	 try {
  	    		List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaDaCancellare = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(oggettoPresente.getIdIstanza(), oggettoPresente.getIdCompetenzaTerritorio());
  	    		if (CollectionUtils.isNotEmpty(istanzaCompetenzaDaCancellare))
  		    	{
  	    			IstanzaCompetenzaExtendedDTO istanzaCompetenzaDaCancellareSingola = istanzaCompetenzaDaCancellare.get(0);
  	        		try {
	    	            istanzaCompetenzaDAO.deleteIstanzaCompetenza(istanzaCompetenzaDaCancellareSingola.getGestUID());
	    	        } catch (Exception e) {
	    	            logError(className, e);
  		    	}

	    	        }
	  	        } catch (Exception e) {
  	            logError(className, e);
  	        }
  	    }

	}

	/**
	 * Save competenza.
	 *
	 * @param oggettoPulito the oggetto pulito
	 * @param competenzeSalvateSinoAdOra the competenze salvate sino ad ora
	 * @param attoreScriva the attore scriva
	 * @param IndAssegnataDaSistema the ind assegnata da sistema
	 */
	private void saveCompetenza(IstanzaCompetenzaOggettoDTO oggettoPulito, List<IstanzaCompetenzaExtendedDTO> competenzeSalvateSinoAdOra, AttoreScriva attoreScriva, AssegnataDaSistemaEnum IndAssegnataDaSistema ) {
		logBeginInfo(className, "IstanzaCompetenzaOggetto da salvare: [" + oggettoPulito + "]");

		boolean idCompetenzaTerritorioPresente = competenzeSalvateSinoAdOra.stream()
	            .anyMatch(competenza ->
	                    competenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio().equals(oggettoPulito.getIdCompetenzaTerritorio())
	            );

	    // Se idCompetenzaTerritorio non è presente nella lista, crea un nuovo IstanzaCompetenzaExtendedDTO
	    if (!idCompetenzaTerritorioPresente)
	    {
	        IstanzaCompetenzaDTO nuovaCompetenza = new IstanzaCompetenzaDTO();
	        nuovaCompetenza.setIdIstanza(oggettoPulito.getIdIstanza());
	        nuovaCompetenza.setIdCompetenzaTerritorio(oggettoPulito.getIdCompetenzaTerritorio());
	        nuovaCompetenza.setFlgAutoritaPrincipale(oggettoPulito.getFlgAutoritaPrincipale());
	        nuovaCompetenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
	        nuovaCompetenza.setIndAssegnataDaSistema(IndAssegnataDaSistema.getDescrizione()); // Imposta il valore di indAssegnataDaSistema a 0 o un altro valore appropriato

	        // Inserisci la nuova competenza nel database


	        try {
                istanzaCompetenzaDAO.saveIstanzaCompetenza(nuovaCompetenza);
            } catch (DuplicateRecordException e) {
                logError(className, e);
            }
	    }

	    // Inserisci IstanzaCompetenzaOggett nel database
	    try {
	    	saveIstanzaCompetenzaOggetto(oggettoPulito,attoreScriva);
        } catch (Exception e) {
            logError(className, e);
        }

	}

	/**
	 * Save istanza competenza oggetto.
	 *
	 * @param oggettoPulito the oggetto pulito
	 * @param attoreScriva the attore scriva
	 */
	private void saveIstanzaCompetenzaOggetto(IstanzaCompetenzaOggettoDTO oggettoPulito, AttoreScriva attoreScriva) {
		logBeginInfo(className, "IstanzaCompetenzaOggetto da salvare: [" + oggettoPulito + "]");
		IstanzaCompetenzaOggettoDTO istanzaCompetenzaOggetto = new IstanzaCompetenzaOggettoDTO();
		istanzaCompetenzaOggetto.setIdIstanza(oggettoPulito.getIdIstanza());
		istanzaCompetenzaOggetto.setIdCompetenzaTerritorio(oggettoPulito.getIdCompetenzaTerritorio());
		istanzaCompetenzaOggetto.setIdOggettoIstanza(oggettoPulito.getIdOggettoIstanza());
		istanzaCompetenzaOggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
		istanzaCompetenzaOggetto.setGestAttoreUpd(attoreScriva.getCodiceFiscale());

		 try {
	            istanzaCompetenzaOggettoDAO.saveIstanzaCompetenzaOggetto(istanzaCompetenzaOggetto);
	        } catch (Exception e) {
	            logError(className, e);
	        }finally {
	            logEnd(className);
	        }


	}

	/**
	 * Save istanza competenza territorio.
	 *
	 * @param oggettoPulito the oggetto pulito
	 * @param attoreScriva the attore scriva
	 */
	private void saveIstanzaCompetenzaTerritorio(IstanzaCompetenzaOggettoDTO oggettoPulito, AttoreScriva attoreScriva) {
        logBeginInfo(className, "oggettoCompetenza da salvare: [" + oggettoPulito + "]");
        IstanzaCompetenzaDTO istanzaCompetenza = new IstanzaCompetenzaDTO();
        istanzaCompetenza.setIdIstanza(oggettoPulito.getIdIstanza());
        istanzaCompetenza.setIdCompetenzaTerritorio(oggettoPulito.getIdCompetenzaTerritorio());
        istanzaCompetenza.setFlgAutoritaPrincipale(oggettoPulito.getFlgAutoritaPrincipale());
        istanzaCompetenza.setFlgAutoritaAssegnataBO(false);
        istanzaCompetenza.setIndAssegnataDaSistema("4");
        istanzaCompetenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        try {
            istanzaCompetenzaDAO.saveIstanzaCompetenza(istanzaCompetenza);
        } catch (DuplicateRecordException e) {
            logError(className, e);
        }


	}

    /**
     * Calculate istanza competenza territorio secondarie.
     * AL MOMENTO VIENE UTILIZZATO SOLO NEL QUADRO CATEGORIE PROGETTUALI.
     * Non viene inserito nulla nella istanza_competenza_oggetto
     *
     * @param idIstanza         the id istanza
     * @param livelliCompetenza the livelli competenza
     * @param attoreScriva      the attore scriva
     * @return the list
     * @throws GenericException the generic exception
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> calculateIstanzaCompetenzaTerritorioSecondarie(Long idIstanza, String livelliCompetenza, AttoreScriva attoreScriva) throws GenericException {
        
    	logBegin(className);
    	
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = null;       
        List<String> livelliCompetenzaList = null;
        
        // estra l'istanza
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList == null || istanzaList.isEmpty()) {
            return null;
        }
        IstanzaExtendedDTO istanza = istanzaList.get(0);
        
        //estrae le competenze correnti 
        istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
        
  

        // Caso in cui IndTipoSelezioneAC = S o M
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) 
        {
            // ripulisce le AC derivanti da categorie progettuali
        	deleteIstanzaCompetenzaTerritorioCategorieProgettuali(idIstanza);
        	
        	//estrae nuovamente le AC, ma stavolta ripulite dagli eventuali in 6 
            istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);        	
        	
        	//  verifica presenza dei livelli (serve se il metodo viene richiamato internamente da _check_ac per ricalcolare le cat progettuali dopo la modifica dell'oggetto istanza es comuni con diversa provincia dal precedente)
        	if(StringUtils.isNotEmpty(livelliCompetenza))
        	{
             	// trasforma stringa livelli in array di stringhe
                livelliCompetenzaList = StringUtils.isNotBlank(livelliCompetenza) ? Arrays.asList(livelliCompetenza.split("\\s*,\\s*")) : null;     		
        	}	
        	else //  calcolo dei livelli se arrivano vuoti
        	{
        		livelliCompetenzaList = loadLivelliCompetenzaCategorieProgettuali(idIstanza);
        	}
   
            //calcola le AC derivanti da cat. progettuali scelte dall'utente.
            List<Long> idCompetenzeTerritorioSecondarieCalcolateList = getIdIstanzaCompetenzeTerritorioCalcolateList(idIstanza, istanza.getAdempimento().getIdAdempimento(), livelliCompetenzaList);
            
            // Determina quali sono da salvare escludendo quelle già presenti
            List<Long> idCompetenzeTerritorioToSaveList = getIdCompetenzeTerritorioListToSave(istanzaCompetenzaList, idCompetenzeTerritorioSecondarieCalcolateList);
            
            logDebug(className, "idCompetenzeTerritorioToSaveList (idIstanza=" + idIstanza + "): " + idCompetenzeTerritorioToSaveList);
            
            // salva le AC detterminate
            if (idCompetenzeTerritorioToSaveList != null && !idCompetenzeTerritorioToSaveList.isEmpty()) {
                istanzaCompetenzaList = saveIstanzaCompetenzaTerritorioList(idIstanza, idCompetenzeTerritorioToSaveList, Boolean.FALSE, Boolean.FALSE, attoreScriva,AssegnataDaSistemaEnum.CAT_PROGETTUALE);
                // aggiornamento jsonData
                jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanza.getIdIstanza(), istanzaCompetenzaList);
            }
        } else {
            // aggiornamento jsonData
            jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanza.getIdIstanza(), istanzaCompetenzaList);
        }
        logEnd(className);
        return istanzaCompetenzaList;
    }

    /**
     * Delete istanza competenza territorio secondarie int.
     *
     * @param idIstanza the id istanza
     * @return the int
     * 
     * OBSOLETA!
     */
    @Override
    public int deleteIstanzaCompetenzaTerritorioSecondarie(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        int result = 0;
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList == null || istanzaList.isEmpty()) {
            return 0;
        }
        IstanzaExtendedDTO istanza = istanzaList.get(0);
        // carica tutte le competenze assegnate all'istanza
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
        //estrae le secondarie (tutte quelle che non sono la principale
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaSecondarieList = istanzaCompetenzaList.stream()
                .filter(istanzaCompetenza -> Boolean.FALSE.equals(istanzaCompetenza.getFlgAutoritaPrincipale()))
                .collect(Collectors.toList());
         //estrae la principale
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaPrincipaleList = istanzaCompetenzaList.stream()
                .filter(istanzaCompetenza -> Boolean.TRUE.equals(istanzaCompetenza.getFlgAutoritaPrincipale()))
                .collect(Collectors.toList());
        //cancella le secondarie
        if (!istanzaCompetenzaSecondarieList.isEmpty()) {
            for (IstanzaCompetenzaExtendedDTO istanzaCompetenza : istanzaCompetenzaSecondarieList) {
                result = istanzaCompetenzaDAO.deleteIstanzaCompetenza(istanzaCompetenza.getGestUID());
            }
            // aggiornamento jsonData
            jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanza.getIdIstanza(), istanzaCompetenzaPrincipaleList);
        }
        logEnd(className);
        return result;
    }
    

    /**
     * Delete istanza competenza territorio categorie progettuali.
     *
     * @param idIstanza the id istanza
     * @return the int
     */
    @Override
    public int deleteIstanzaCompetenzaTerritorioCategorieProgettuali(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        int result = 0;
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        if (istanzaList == null || istanzaList.isEmpty()) {
            return 0;
        }
        IstanzaExtendedDTO istanza = istanzaList.get(0);
        // carica tutte le competenze assegnate all'istanza
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
        

        //estrae le AC attribuite tramite cat. progettuale (indAssegnataDaSistema = 6)
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaAttribuiteDaCatProgettualiList = istanzaCompetenzaList.stream()
        	    .filter(istanzaCompetenza -> "6".equals(istanzaCompetenza.getIndAssegnataDaSistema()))
        	    .collect(Collectors.toList());

        
        //estrae le altre (derivanti dall'oggetto o scelte dall'utente quindi quelle con IndAssegnataDaSistema != da 6)       
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzeAttribuitedaOggettoList = istanzaCompetenzaList.stream()
        	    .filter(istanzaCompetenza -> !("6").equals(istanzaCompetenza.getIndAssegnataDaSistema()))
        	    .collect(Collectors.toList());

        
        //cancella le secondarie derivanti da cat progettuale
        if (!istanzaCompetenzaAttribuiteDaCatProgettualiList.isEmpty()) {
            for (IstanzaCompetenzaExtendedDTO istanzaCompetenza : istanzaCompetenzaAttribuiteDaCatProgettualiList) {
                result = istanzaCompetenzaDAO.deleteIstanzaCompetenza(istanzaCompetenza.getGestUID());
            }
            // aggiornamento jsonData TODO possibile dismissione dell'aggiornamento del jsondata in quanto non appare utilizzato altrove
            jsonDataManager.updateJsonDataIstanzaCompetenzaTerritorio(istanza.getIdIstanza(), istanzaCompetenzeAttribuitedaOggettoList);
        }
        logEnd(className);
        return result;
    }

    /**
     * Load ambito list.
     *
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the list
     */
    @Override
    public List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorioById(Long idCompetenzaTerritorio, AttoreScriva attoreScriva) {
        logBegin(className);
        List<CompetenzaTerritorioExtendedDTO> competenzaTerritorioList = null;
        try {
            competenzaTerritorioList = competenzaTerritorioDAO.loadCompetenzaTerritorioById(idCompetenzaTerritorio);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return competenzaTerritorioList;
    }

    /**
     * Load competenza territorio responsabile.
     *
     * @param idCompetenzaTerritorioResponsabile the id competenza territorio responsabile
     * @return the list
     */
    @Override
    public CompetenzaTerritorioResponsabileDTO loadCompetenzeTerritorioByIdCompResp(Long idCompetenzaTerritorioResponsabile, AttoreScriva attoreScriva) {
        logBegin(className);
        CompetenzaTerritorioResponsabileDTO competenzaTerritorioResp = null;
        try {
        	competenzaTerritorioResp = competenzaTerritorioDAO.loadCompetenzeTerritorioByIdCompResp(idCompetenzaTerritorioResponsabile);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return competenzaTerritorioResp;
    }

    /**
     * Gets istanza competenza principale list.
     *
     * @param istanzaCompetenzaList the istanza competenza list
     * @return the istanza competenza principale list
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaPrincipaleList(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBeginInfo(className, "istanzaCompetenzaList:\n" + istanzaCompetenzaList);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaPrincipaleList = null;
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
            istanzaCompetenzaPrincipaleList = istanzaCompetenzaList
                    .stream()
                    .filter(IstanzaCompetenzaDTO::getFlgAutoritaPrincipale)
                    .collect(Collectors.toList());
            logDebug(className, "istanzaCompetenzaPrincipaleList (idIstanza=" + istanzaCompetenzaList.get(0).getIdIstanza() + "):\n" + istanzaCompetenzaPrincipaleList);
        }
        logEnd(className);
        return istanzaCompetenzaPrincipaleList;
    }

    /**
     * Gets istanza competenza assegnata bo list.
     *
     * @param istanzaCompetenzaList the istanza competenza list
     * @return the istanza competenza assegnata bo list
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaAssegnataBOList(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBeginInfo(className, "istanzaCompetenzaList:[\n" + istanzaCompetenzaList + "]");
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaAssegnataBOList = null;
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
            istanzaCompetenzaAssegnataBOList = istanzaCompetenzaList
                    .stream()
                    .filter(IstanzaCompetenzaDTO::getFlgAutoritaAssegnataBO)
                    .collect(Collectors.toList());
            logDebug(className, "istanzaCompetenzaAssegnataBOList (idIstanza=" + istanzaCompetenzaList.get(0).getIdIstanza() + "):\n" + istanzaCompetenzaAssegnataBOList);
        }
        logEnd(className);
        return istanzaCompetenzaAssegnataBOList;
    }

    /**
     * Define istanza competenza list list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> extractIstanzaCompetenzaList(Long idIstanza) {
        /*
        Andare sulla scriva_r_istanza_competenza estrarre l’id_competenza_territorio valida (data_fine_validità nulla o futura) con flg_autorita_assegnata_bo = 1.
        Se non restituisce righe, selezionare quella con flg_autorita_principale = 1.
        */
        logBeginInfo(className, idIstanza);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = loadIstanzaCompetenzeTerritorio(idIstanza, null);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenza = getIstanzaCompetenzaAssegnataBOList(istanzaCompetenzaList);
        istanzaCompetenza = CollectionUtils.isNotEmpty(istanzaCompetenza) ? istanzaCompetenza : getIstanzaCompetenzaPrincipaleList(istanzaCompetenzaList);
        logEnd(className);
        return istanzaCompetenza;
    }

    /**
     * Gets istanza competenza principale string list.
     *
     * @param istanzaCompetenzaPrincipaleList List<IstanzaCompetenzaExtendedDTO>
     * @return String istanza competenza principale string list
     */
    public String getIstanzaCompetenzaPrincipaleStringList(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaPrincipaleList) {
        logBeginInfo(className, "istanzaCompetenzaPrincipaleList:\n" + istanzaCompetenzaPrincipaleList);
        String istanzaCompetenzaPrincipaleStringList = "";
        if (istanzaCompetenzaPrincipaleList != null && !istanzaCompetenzaPrincipaleList.isEmpty()) {
            for (IstanzaCompetenzaExtendedDTO istanzaCompetenzaPrincipale : istanzaCompetenzaPrincipaleList) {
                istanzaCompetenzaPrincipaleStringList = StringUtils.isBlank(istanzaCompetenzaPrincipaleStringList) ? istanzaCompetenzaPrincipale.getCompetenzaTerritorio().getDesCompetenzaTerritorio() : istanzaCompetenzaPrincipaleStringList + ", " + istanzaCompetenzaPrincipale.getCompetenzaTerritorio().getDesCompetenzaTerritorio();
            }
        }
        logEnd(className);
        return istanzaCompetenzaPrincipaleStringList;
    }

    /**
     * Gets istanza competenza no principale list.
     *
     * @param istanzaCompetenzaList List<IstanzaCompetenzaExtendedDTO>
     * @return List<IstanzaCompetenzaExtendedDTO>     istanza competenza no principale list
     */
    public List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaNoPrincipaleList(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBeginInfo(className, "istanzaCompetenzaList:\n" + istanzaCompetenzaList);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaNoPrincipaleList = null;
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
            istanzaCompetenzaNoPrincipaleList = istanzaCompetenzaList
                    .stream()
                    .filter(istanzaCompetenza -> !istanzaCompetenza.getFlgAutoritaPrincipale())
                    .collect(Collectors.toList());
            logDebug(className, "istanzaCompetenzaPrincipaleList (idIstanza=" + istanzaCompetenzaList.get(0).getIdIstanza() + "):\n" + istanzaCompetenzaNoPrincipaleList);
        }
        logEnd(className);
        return istanzaCompetenzaNoPrincipaleList;
    }

    /**
     * Verify istanza competenza territorio principale list.
     *
     * il metodo  filtra la lista istanzaCompetenzaList rimuovendo gli elementi
     * che hanno un ID di CompetenzaTerritorio presente in idIstanzaCompetenzeVerificaList o che hanno un codTipoCompetenza uguale a "NAZIONALE".
     *
     * @param istanzaCompetenzaList           List<IstanzaCompetenzaExtendedDTO>
     * @param idIstanzaCompetenzeVerificaList List<IstanzaCompetenzaExtendedDTO>
     * @return List<IstanzaCompetenzaExtendedDTO>     list
     */
    public List<IstanzaCompetenzaExtendedDTO> verifyIstanzaCompetenzaTerritorioPrincipale(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList, List<Long> idIstanzaCompetenzeVerificaList) {
        logBeginInfo(className, "istanzaCompetenzaList:\n" + istanzaCompetenzaList + "\nidIstanzaCompetenzeVerificaList: [" + idIstanzaCompetenzeVerificaList + "]");
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
            for (Long idIstanzaCompetenzaVerifica : idIstanzaCompetenzeVerificaList) {
                istanzaCompetenzaList.removeIf(istanzaCompetenza -> istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio().equals(idIstanzaCompetenzaVerifica)
                		|| istanzaCompetenza.getCompetenzaTerritorio().getTipoCompetenza().getCodTipoCompetenza().equalsIgnoreCase("NAZIONALE"));
            }
        }
        logEnd(className);
        return istanzaCompetenzaList;
    }

    /**
     * Gets id istanza competenze territorio calcolate list.
     *
     * @param idIstanza             id istanza
     * @param idAdempimento         id adempimento
     * @param livelliCompetenzaList lista livelli competenza
     * @return List<Long>     id istanza competenze territorio calcolate list
     */
    public List<Long> getIdIstanzaCompetenzeTerritorioCalcolateList(Long idIstanza, Long idAdempimento, List<String> livelliCompetenzaList) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - idAdempimento: [" + idAdempimento + "] - livelliCompetenzaList : " + livelliCompetenzaList);
        return istanzaCompetenzaDAO.loadCompetenzaTerritorioSecondarieByIdAdempimentoIdIstanza(idAdempimento, idIstanza, livelliCompetenzaList);
    }

    /**
     * Gets id istanza competenze territorio verifica list.
     *
     * @param idIstanza     id istanza
     * @param idAdempimento id adempimento
     * @return List<Long>     id istanza competenze territorio verifica list
     */
    public List<Long> getIdIstanzaCompetenzeTerritorioVerificaList(Long idIstanza, Long idAdempimento) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - idAdempimento: [" + idAdempimento + "]");
        return istanzaCompetenzaDAO.loadCompetenzaTerritorioVerificaByIdAdempimentoIdIstanza(idAdempimento, idIstanza);
    }

    /**
     * Gets id istanza competenze territorio principali list.
     *
     * @param idIstanza     the id istanza
     * @param idAdempimento the id adempimento
     * @return the id istanza competenze territorio principali list
     */
    public List<Long> getIdIstanzaCompetenzeTerritorioPrincipaliList(Long idIstanza, Long idAdempimento) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - idAdempimento: [" + idAdempimento + "]");
        return istanzaCompetenzaDAO.loadCompetenzaTerritorioPrincipaliByIdAdempimentoIdIstanza(idAdempimento, idIstanza);
    }

    /**
     * Gets id competenze territorio list to save.
     *
     * @param istanzaCompetenzaList                      List<IstanzaCompetenzaExtendedDTO>
     * @param idIstanzaCompetenzeSecondarieCalcolateList List<Long>
     * @return List<Long>     id competenze territorio list to save
     */
    public List<Long> getIdCompetenzeTerritorioListToSave(List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList, List<Long> idIstanzaCompetenzeSecondarieCalcolateList) {
        logBeginInfo(className, "istanzaCompetenzaList:\n" + istanzaCompetenzaList + "\nidIstanzaCompetenzeSecondarieCalcolateList: [" + idIstanzaCompetenzeSecondarieCalcolateList + "]");
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
            for (IstanzaCompetenzaExtendedDTO istanzaCompetenza : istanzaCompetenzaList) {
                idIstanzaCompetenzeSecondarieCalcolateList.remove(istanzaCompetenza.getCompetenzaTerritorio().getIdCompetenzaTerritorio());
            }
        }
        logEnd(className);
        return idIstanzaCompetenzeSecondarieCalcolateList;
    }

    /**
     * Save istanza competenza territorio list list.
     *
     * @param idIstanza                  id istanza
     * @param idCompetenzeTerritorioList list id competenze territorio
     * @param flgAutoritaPrincipale      flag autorita principale
     * @param flgAutoritaAssegnataBO     flag autorita assegnata BO
     * @param attoreScriva               the attore scriva
     * @return List<IstanzaCompetenzaExtendedDTO>     list
     */
    public List<IstanzaCompetenzaExtendedDTO> saveIstanzaCompetenzaTerritorioList(Long idIstanza, List<Long> idCompetenzeTerritorioList, boolean flgAutoritaPrincipale, boolean flgAutoritaAssegnataBO, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]\nidCompetenzeTerritorioList: [" + idCompetenzeTerritorioList + "]");
        if (idCompetenzeTerritorioList != null && !idCompetenzeTerritorioList.isEmpty()) {
            for (Long idCompetenzaTerritorio : idCompetenzeTerritorioList) {
                IstanzaCompetenzaDTO istanzaCompetenza = new IstanzaCompetenzaDTO();
                istanzaCompetenza.setIdIstanza(idIstanza);
                istanzaCompetenza.setIdCompetenzaTerritorio(idCompetenzaTerritorio);
                istanzaCompetenza.setFlgAutoritaPrincipale(flgAutoritaPrincipale);
                istanzaCompetenza.setFlgAutoritaAssegnataBO(flgAutoritaAssegnataBO);
                istanzaCompetenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                try {
                    istanzaCompetenzaDAO.saveIstanzaCompetenza(istanzaCompetenza);
                } catch (DuplicateRecordException e) {
                    logError(className, e);
                }
            }
        }
        return istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
    }
    
    /**
     * Save istanza competenza territorio list.
     *
     * @param idIstanza the id istanza
     * @param idCompetenzeTerritorioList the id competenze territorio list
     * @param flgAutoritaPrincipale the flg autorita principale
     * @param flgAutoritaAssegnataBO the flg autorita assegnata BO
     * @param attoreScriva the attore scriva
     * @param indAssegnataDaSistema the ind assegnata da sistema
     * @return List<IstanzaCompetenzaExtendedDTO>     list
     */
    public List<IstanzaCompetenzaExtendedDTO> saveIstanzaCompetenzaTerritorioList(Long idIstanza, List<Long> idCompetenzeTerritorioList, boolean flgAutoritaPrincipale, boolean flgAutoritaAssegnataBO, AttoreScriva attoreScriva, AssegnataDaSistemaEnum indAssegnataDaSistema) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]\nidCompetenzeTerritorioList: [" + idCompetenzeTerritorioList + "]");
        if (idCompetenzeTerritorioList != null && !idCompetenzeTerritorioList.isEmpty()) {
            for (Long idCompetenzaTerritorio : idCompetenzeTerritorioList) {
                IstanzaCompetenzaDTO istanzaCompetenza = new IstanzaCompetenzaDTO();
                istanzaCompetenza.setIdIstanza(idIstanza);
                istanzaCompetenza.setIdCompetenzaTerritorio(idCompetenzaTerritorio);
                istanzaCompetenza.setFlgAutoritaPrincipale(flgAutoritaPrincipale);
                istanzaCompetenza.setFlgAutoritaAssegnataBO(flgAutoritaAssegnataBO);
                istanzaCompetenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                istanzaCompetenza.setIndAssegnataDaSistema(indAssegnataDaSistema.getDescrizione());
                try {
                    istanzaCompetenzaDAO.saveIstanzaCompetenza(istanzaCompetenza);
                } catch (DuplicateRecordException e) {
                    logError(className, e);
                }
            }
        }
        return istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
    }

    /**
     * Save istanza competenza territorio list.
     *
     * @param idIstanza the id istanza
     * @param CompetenzeTerritorioList the competenze territorio list
     * @param flgAutoritaPrincipale the flg autorita principale
     * @param flgAutoritaAssegnataBO the flg autorita assegnata BO
     * @param indAssegnataDaSistema the ind assegnata da sistema
     * @param attoreScriva the attore scriva
     * @return the list
     */
    public List<IstanzaCompetenzaExtendedDTO> saveIstanzaCompetenzaTerritorioList(Long idIstanza, List<CompetenzaTerritorioExtendedDTO> CompetenzeTerritorioList, boolean flgAutoritaPrincipale, boolean flgAutoritaAssegnataBO, AssegnataDaSistemaEnum indAssegnataDaSistema, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]\nidCompetenzeTerritorioList: [" + CompetenzeTerritorioList + "]");

            for (CompetenzaTerritorioExtendedDTO competenzaTerritorio : CompetenzeTerritorioList) {
                IstanzaCompetenzaDTO istanzaCompetenza = new IstanzaCompetenzaDTO();
                istanzaCompetenza.setIdIstanza(idIstanza);
                istanzaCompetenza.setIdCompetenzaTerritorio(competenzaTerritorio.getIdCompetenzaTerritorio());
                istanzaCompetenza.setFlgAutoritaPrincipale(competenzaTerritorio.getFlgPrincipale());
                istanzaCompetenza.setFlgAutoritaAssegnataBO(flgAutoritaAssegnataBO);
                istanzaCompetenza.setIndAssegnataDaSistema(indAssegnataDaSistema.getDescrizione());
                istanzaCompetenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                try {
                    istanzaCompetenzaDAO.saveIstanzaCompetenza(istanzaCompetenza);
                } catch (DuplicateRecordException e) {
                    logError(className, e);
                }
            }

        return istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
    }

    public List<String> loadLivelliCompetenzaCategorieProgettuali(Long idIstanza) 
    {
    	logBeginInfo(className, "idIstanza: [" + idIstanza + "]");
    	List<String> livelliCompetenza = null;
        try {
           livelliCompetenza = istanzaCompetenzaDAO.loadLivelliCompetenzaCategorieProgettuali(idIstanza);
        } catch (Exception e) {
            logError(className, e);
        }
		return livelliCompetenza;
		
	}


    /**
     * Update json data istanza competenza istanza extended dto.
     *
     * @param istanza               the istanza
     * @param istanzaCompetenzaList list istanza competenza
     * @return String istanza extended dto
     */
    public IstanzaExtendedDTO updateJsonDataIstanzaCompetenza(IstanzaExtendedDTO istanza, List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList) {
        logBeginInfo(className, "istanzaCompetenzaList:\n" + istanzaCompetenzaList);
        if (istanzaCompetenzaList != null && !istanzaCompetenzaList.isEmpty()) {
            List<JSONObject> jsonIstanzaCompetenzaList = new ArrayList<>();
            for (IstanzaCompetenzaExtendedDTO istanzaCompetenza : istanzaCompetenzaList) {
                JSONObject jsonIstanzaCompetenza = istanzaCompetenza.toJsonObj();
                logDebug(className, "jsonIstanzaCompetenza (idIstanza=" + istanzaCompetenza.getIdIstanza() + "): " + jsonIstanzaCompetenza);
                jsonIstanzaCompetenzaList.add(jsonIstanzaCompetenza);
            }
            logDebug(className, "jsonIstanzaCompetenzaList (idIstanza=" + istanza.getIdIstanza() + "):\n" + jsonIstanzaCompetenzaList);
            JSONObject jsonDataIstanza = null != istanza.getJsonData() ? new JSONObject(istanza.getJsonData()) : new JSONObject();
            JSONObject newJson = jsonDataIstanza.getJSONObject("QDR_ISTANZA").put("competenze_territorio", jsonIstanzaCompetenzaList);
            logDebug(className, "jsonDataIstanza (idIstanza=" + istanza.getIdIstanza() + "): " + jsonDataIstanza);
            if (null != newJson) {
                istanza.setJsonData(newJson.toString());
            }
        }
        return null;
    }


}
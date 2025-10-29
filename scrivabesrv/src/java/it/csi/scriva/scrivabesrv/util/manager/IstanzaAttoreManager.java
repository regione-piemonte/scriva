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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProfiloAppDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAbilitazioneDAO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAbilitazioneDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoAbilitazioneEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Istanza attore manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class IstanzaAttoreManager {

    @Autowired
    private CompilanteDAO compilanteDAO;

    @Autowired
    private IstanzaAttoreDAO istanzaAttoreDAO;

    @Autowired
    private TipoAbilitazioneDAO tipoAbilitazioneDAO;

    @Autowired
    private ProfiloAppDAO profiloAppDAO;

    /**
     * Create istanza attore compilante istanza attore dto.
     *
     * @param cfAttore          cfAttore
     * @param idIstanza         idIstanza
     * @param componenteAppEnum the componente app enum
     * @param gestAttoreIns     the gest attore ins
     * @return IstanzaAttoreDTO istanza attore dto
     */
    public IstanzaAttoreDTO createIstanzaAttoreCompilante(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, String gestAttoreIns) {
        return createIstanzaAttore(cfAttore, idIstanza, componenteAppEnum, null, ProfiloAppEnum.COMPILANTE, gestAttoreIns);
    }

    /**
     * Create istanza attore richiedente istanza attore dto.
     *
     * @param cfAttore          cfAttore
     * @param idIstanza         idIstanza
     * @param componenteAppEnum the componente app enum
     * @param gestAttoreIns     the gest attore ins
     * @return IstanzaAttoreDTO istanza attore dto
     */
    public IstanzaAttoreDTO createIstanzaAttoreRichiedente(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, String gestAttoreIns) {
        return createIstanzaAttore(cfAttore, idIstanza, componenteAppEnum, null, ProfiloAppEnum.RICHIEDENTE, gestAttoreIns);
    }

    /**
     * Gets id istanza attore compilante.
     *
     * @param cfAttore          cfAttore
     * @param idIstanza         idIstanza
     * @param componenteAppEnum the componente app enum
     * @return id istanza attore compilante
     */
    public Long getIdIstanzaAttoreCompilante(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, String gestAttoreIns) {
        return this.getIdIstanzaAttore(cfAttore, idIstanza, componenteAppEnum, ProfiloAppEnum.COMPILANTE, gestAttoreIns);
    }
    
    /**
     * Gets id istanza attore compilante.
     *
     * @param cfAttore          cfAttore
     * @param idIstanza         idIstanza
     * @param componenteAppEnum the componente app enum
     * @return id istanza attore compilante
     */
    public Long getIdIstanzaAttoreCompilanteForSoggettoIstanza(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, String gestAttoreIns) {
        return this.getIdIstanzaAttoreForSoggettiIstanza(cfAttore, idIstanza, componenteAppEnum, ProfiloAppEnum.COMPILANTE, gestAttoreIns);
    }

    /**
     * Gets id istanza attore richiedente.
     *
     * @param cfAttore          cfAttore
     * @param idIstanza         idIstanza
     * @param componenteAppEnum the componente app enum
     * @return id istanza attore richiedente
     */
    public Long getIdIstanzaAttoreRichiedente(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, String gestAttoreIns) {
        //return this.getIdIstanzaAttore(cfAttore, idIstanza, componenteAppEnum, ProfiloAppEnum.RICHIEDENTE, gestAttoreIns);
        return this.getIdIstanzaAttoreForSoggettiIstanza(cfAttore, idIstanza, componenteAppEnum, ProfiloAppEnum.RICHIEDENTE, gestAttoreIns);
    }

    /**
     * Crea istanza attore al momento della creazione dell'istanza. 
     * Richiamata dalla IstanzaApiServiceImpl.saveIstanza
     * @param cfAttore             cfAttore
     * @param idIstanza            idIstanza
     * @param tipoAbilitazioneEnum tipoAbilitazioneEnum
     * @param profiloAppEnum       profiloAppEnum
     * @return IstanzaAttoreDTO
     */
    private IstanzaAttoreDTO createIstanzaAttore(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, TipoAbilitazioneEnum tipoAbilitazioneEnum, ProfiloAppEnum profiloAppEnum, String gestAttoreIns) {
        IstanzaAttoreExtendedDTO istanzaAttore = null;
        if (cfAttore != null && idIstanza != null && idIstanza > 0) {
            CompilanteDTO compilante = null;
            if (profiloAppEnum.equals(ProfiloAppEnum.COMPILANTE)) {
                List<CompilanteDTO> compilanteList = compilanteDAO.loadCompilanteByCodiceFiscale(cfAttore);
                compilante = CollectionUtils.isNotEmpty(compilanteList) ? compilanteList.get(0) : null;
            }

            TipoAbilitazioneDTO tipoAbilitazione = null;
            if (tipoAbilitazioneEnum != null) {
                List<TipoAbilitazioneDTO> tipoAbilitazioneList = tipoAbilitazioneDAO.loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(tipoAbilitazioneEnum.getDescrizione());
                tipoAbilitazione = CollectionUtils.isNotEmpty(tipoAbilitazioneList) ? tipoAbilitazioneList.get(0) : null;
            }

            List<ProfiloAppExtendedDTO> profiloAppList = profiloAppDAO.loadProfiloAppByCodeProfiloAndComponenteApp(profiloAppEnum.getDescrizione(), ProfiloAppEnum.RICHIEDENTE.name().equalsIgnoreCase(profiloAppEnum.getDescrizione()) ? componenteAppEnum.FO.getDescrizione() : componenteAppEnum.getDescrizione());
            ProfiloAppExtendedDTO profiloApp = CollectionUtils.isNotEmpty(profiloAppList) ? profiloAppList.get(0) : null;

            istanzaAttore = new IstanzaAttoreExtendedDTO();
            istanzaAttore.setIdIstanza(idIstanza);
            istanzaAttore.setTipoAbilitazione(tipoAbilitazione);
            istanzaAttore.setCompilante(compilante);
            istanzaAttore.setProfiloApp(profiloApp);
            istanzaAttore.setCfAttore(cfAttore);
            istanzaAttore.setGestAttoreIns(StringUtils.isNotBlank(gestAttoreIns) ? gestAttoreIns : cfAttore);
        }
        return istanzaAttore != null ? istanzaAttore.getDTO() : null;
    }

    private Long getIdIstanzaAttore(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, ProfiloAppEnum profiloAppEnum, String gestAttoreIns) {
        Long idIstanzaAttore = null;
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfApp(idIstanza, cfAttore, componenteAppEnum.getDescrizione(), profiloAppEnum.getDescrizione()); //USARE NUOVA QUERY OK

        if (CollectionUtils.isNotEmpty(istanzaAttoreList)) {
            idIstanzaAttore = istanzaAttoreList.get(0).getIdIstanzaAttore();
        } 
//        else {
//            IstanzaAttoreDTO istanzaAttore = profiloAppEnum == ProfiloAppEnum.COMPILANTE ? this.createIstanzaAttoreCompilante(cfAttore, idIstanza, componenteAppEnum, gestAttoreIns) : this.createIstanzaAttoreRichiedente(cfAttore, idIstanza, componenteAppEnum, gestAttoreIns);
//            idIstanzaAttore = istanzaAttoreDAO.saveIstanzaAttore(istanzaAttore);
//        }

        return idIstanzaAttore;
    }
//SE LA GET ID ATTORE LA FA UN ABILITATO ALLA GESTIONE, VIENE INSERITO COME COMPILANTE NELL'ISTANZA ATTORE

    private Long getIdIstanzaAttoreForSoggettiIstanza(String cfAttore, Long idIstanza, ComponenteAppEnum componenteAppEnum, ProfiloAppEnum profiloAppEnum, String gestAttoreIns) {
        Long idIstanzaAttore = null;
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfAppForSoggIst(idIstanza, cfAttore, componenteAppEnum.getDescrizione(), profiloAppEnum.getDescrizione()); // USARE VECCHIA QUERY

        if (CollectionUtils.isNotEmpty(istanzaAttoreList)) {
            idIstanzaAttore = istanzaAttoreList.get(0).getIdIstanzaAttore();
            return idIstanzaAttore;
        } 
        if(profiloAppEnum != ProfiloAppEnum.RICHIEDENTE) {
	      //Se non trova il compilante, facciamo un ulteriore ricerca per trovare l'abilitato alla gestione. Se non trova l'abilitato alla gestione (data revoca null) allora inseriamo il compilante TO DO
	        istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanzaCFAttoreCodCompAppCodProfAppForSoggIst(idIstanza, cfAttore, componenteAppEnum.getDescrizione(), ProfiloAppEnum.ABILITATO_GESTIONE.getDescrizione());
	        if (CollectionUtils.isNotEmpty(istanzaAttoreList)) {
	        	idIstanzaAttore = istanzaAttoreList.get(0).getIdIstanzaAttore();
	            return idIstanzaAttore;
	        } //DA NON FARE PER IL RICHIEDENTE
	        
	        else {
	        	IstanzaAttoreDTO istanzaAttore = profiloAppEnum == ProfiloAppEnum.COMPILANTE ? this.createIstanzaAttoreCompilante(cfAttore, idIstanza, componenteAppEnum, gestAttoreIns) : this.createIstanzaAttoreRichiedente(cfAttore, idIstanza, componenteAppEnum, gestAttoreIns);
	            idIstanzaAttore = istanzaAttoreDAO.saveIstanzaAttore(istanzaAttore);
	        }
        }
        else {
        	IstanzaAttoreDTO istanzaAttore = profiloAppEnum == ProfiloAppEnum.COMPILANTE ? this.createIstanzaAttoreCompilante(cfAttore, idIstanza, componenteAppEnum, gestAttoreIns) : this.createIstanzaAttoreRichiedente(cfAttore, idIstanza, componenteAppEnum, gestAttoreIns);
            idIstanzaAttore = istanzaAttoreDAO.saveIstanzaAttore(istanzaAttore);
        }

        return idIstanzaAttore;
    }



}
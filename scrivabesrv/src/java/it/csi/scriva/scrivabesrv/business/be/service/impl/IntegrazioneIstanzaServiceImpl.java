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

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIntegrazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IntegrazioneIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoIntegraAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.IntegrazioneIstanzaService;
import it.csi.scriva.scrivabesrv.dto.AllegatoIntegrazioneDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoIntegraAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoIntegrazioneDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The type Integrazione istanza service.
 */
@Component
public class IntegrazioneIstanzaServiceImpl extends BaseApiServiceImpl implements IntegrazioneIstanzaService {

    private final String className = this.getClass().getSimpleName();

    private final IntegrazioneIstanzaDAO integrazioneIstanzaDAO;

    private final AllegatoIntegrazioneDAO allegatoIntegrazioneDAO;

    private final TipoIntegraAllegatoDAO tipoIntegraAllegatoDAO;

    /**
     * Instantiates a new Integrazione istanza service.
     *
     * @param integrazioneIstanzaDAO  the integrazione istanza dao
     * @param allegatoIntegrazioneDAO the allegato integrazione dao
     * @param tipoIntegraAllegatoDAO  the tipo integra allegato dao
     */
    @Autowired
    public IntegrazioneIstanzaServiceImpl(IntegrazioneIstanzaDAO integrazioneIstanzaDAO,
                                          AllegatoIntegrazioneDAO allegatoIntegrazioneDAO,
                                          TipoIntegraAllegatoDAO tipoIntegraAllegatoDAO) {
        logBegin(className);
        this.integrazioneIstanzaDAO = integrazioneIstanzaDAO;
        this.allegatoIntegrazioneDAO = allegatoIntegrazioneDAO;
        this.tipoIntegraAllegatoDAO = tipoIntegraAllegatoDAO;
    }

    /**
     * Load integrazioni istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanzaByIdIstanza(Long idIstanza, String codTipoIntegrazione) {
        logBeginInfo(className, idIstanza);
        List<IntegrazioneIstanzaExtendedDTO> integrazioneIstanzaList = integrazioneIstanzaDAO.loadIntegrazioniIstanza(idIstanza, null, codTipoIntegrazione);
        fillAllegatoIntegrazioneList(integrazioneIstanzaList);
        // seleziona solo quelle con allegati che hanno il flg_allegato_rif_protocollo = true (SCRIVA-1341)
        integrazioneIstanzaList = integrazioneIstanzaList.stream()
                .filter(i-> i.getDataInvio()!=null) // SCRIVA-1319
                .filter(i -> CollectionUtils.isNotEmpty(i.getAllegatoIntegrazione()) &&
                        CollectionUtils.isNotEmpty(
                                i.getAllegatoIntegrazione().stream()
                                        .filter(a -> Boolean.TRUE.equals(a.getFlgAllegatoRifProtocollo()))
                                        .collect(Collectors.toList())
                        )
                )
                .sorted(Comparator.comparingLong(IntegrazioneIstanzaExtendedDTO::getIdIntegrazioneIstanza).reversed())
                .collect(Collectors.toList());
        return integrazioneIstanzaList;
    }

    /**
     * Load integrazioni istanza by id list.
     *
     * @param idIntegrazioneIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanzaById(Long idIntegrazioneIstanza) {
        logBeginInfo(className, idIntegrazioneIstanza);
        List<IntegrazioneIstanzaExtendedDTO> integrazioneIstanzaList = integrazioneIstanzaDAO.loadIntegrazioniIstanza(null, idIntegrazioneIstanza, null);
        fillAllegatoIntegrazioneList(integrazioneIstanzaList);
        return integrazioneIstanzaList;
    }

    /**
     * Save integrazione istanza long.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param attoreScriva        the attore scriva
     * @return the long
     */
    @Override
    public Long saveIntegrazioneIstanza(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, "integrazioneIstanza:\n" + integrazioneIstanza + "\n");
        Long result = null;
        Date now = Calendar.getInstance().getTime();
        try {
            // Verificare se esiste già un’occorrenza sulla scriva_t_integrazione con id_tipo_integrazione passato nel payload e data_invio nulla, se esiste restituisce questa, diversamente ne inserisce una nuova e la restituisce
            List<IntegrazioneIstanzaExtendedDTO> integrazioneIstanzaList = integrazioneIstanzaDAO.loadIntegrazioniIstanzaNoInviata(integrazioneIstanza.getIdIstanza(), integrazioneIstanza.getTipoIntegrazione().getCodTipoIntegrazione());
            if (CollectionUtils.isNotEmpty(integrazioneIstanzaList)) {
                Long idIntegrazioneIstanza = integrazioneIstanzaList.get(0).getIdIntegrazioneIstanza();
                if (CollectionUtils.isNotEmpty(integrazioneIstanza.getAllegatoIntegrazione())) {
                    allegatoIntegrazioneDAO.deleteAllegatoIntegrazione(idIntegrazioneIstanza);
                    saveAllegatoIntegrazione(idIntegrazioneIstanza, attoreScriva, now, integrazioneIstanza.getAllegatoIntegrazione());
                }
                return idIntegrazioneIstanza;
            }
            setTipoIntegrazione(integrazioneIstanza);
            integrazioneIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            integrazioneIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            result = integrazioneIstanzaDAO.saveIntegrazioneIstanza(integrazioneIstanza.getDTO(), now);
            this.saveAllegatoIntegrazioneList(integrazioneIstanzaList, result, attoreScriva, now);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update integrazione istanza integer.
     *
     * @param integrazioneIstanza the integrazione istanza dto
     * @param attoreScriva        the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateIntegrazioneIstanza(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, "integrazioneIstanza:\n" + integrazioneIstanza + "\n");
        Integer result = null;
        try {
            Date now = Calendar.getInstance().getTime();
            Long idIntegrazioneIstanza = integrazioneIstanza.getIdIntegrazioneIstanza();
            integrazioneIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            integrazioneIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            result = integrazioneIstanzaDAO.updateIntegrazioneIstanza(integrazioneIstanza.getDTO(), now); // aggiorna scriva_t_integrazione_istanza
            allegatoIntegrazioneDAO.deleteAllegatoIntegrazione(idIntegrazioneIstanza); // cancella gli allegati dalla scriva_r_integra_istanza_allegato
            List<IntegrazioneIstanzaExtendedDTO> integrazioneIstanzaList = new ArrayList<>();
            integrazioneIstanzaList.add(integrazioneIstanza);
            this.saveAllegatoIntegrazioneList(integrazioneIstanzaList, idIntegrazioneIstanza, attoreScriva, now); // salva la nuova lista di allegati nella scriva_r_integra_istanza_allegato
            this.updateDataIntegrazioneProtocolloAllegati(integrazioneIstanza, now); // salva la data di integrazione sulla t_allegato_istanza
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update data integrazione protocollo allegati.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param dataIntegrazione    the data integrazione
     */
    private void updateDataIntegrazioneProtocolloAllegati(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, Date dataIntegrazione) {
        logBegin(className);
        if (integrazioneIstanza != null && integrazioneIstanza.getDataInvio() != null) {
            integrazioneIstanzaDAO.updateDataIntegrazioneProtocolloAllegati(integrazioneIstanza.getDTO(), dataIntegrazione);
        }
    }

    /**
     * Fill allegato integrazione list.
     *
     * @param integrazioneIstanzaList the integrazione istanza list
     */
    private void fillAllegatoIntegrazioneList(List<IntegrazioneIstanzaExtendedDTO> integrazioneIstanzaList) {
        logBegin(className);
        if (CollectionUtils.isNotEmpty(integrazioneIstanzaList)) {
            for (IntegrazioneIstanzaExtendedDTO integrazioneIstanza : integrazioneIstanzaList) {
                List<AllegatoIntegrazioneDTO> allegatoIntegrazioneList = allegatoIntegrazioneDAO.loadAllegatoIntegrazione(integrazioneIstanza.getIdIntegrazioneIstanza());
                if (CollectionUtils.isNotEmpty(allegatoIntegrazioneList)) {
                    integrazioneIstanza.setAllegatoIntegrazione(allegatoIntegrazioneList);
                    integrazioneIstanza.setNumProtocollo(allegatoIntegrazioneList.get(0).getNumProtocolloAllegato());
                    integrazioneIstanza.setDataProtocollo(allegatoIntegrazioneList.get(0).getDataProtocolloAllegato());
                }
            }
        }
        logEnd(className);
    }


    /**
     * Save allegato integrazione list.
     *
     * @param integrazioneIstanzaList the integrazione istanza list
     */
    private void saveAllegatoIntegrazioneList(List<IntegrazioneIstanzaExtendedDTO> integrazioneIstanzaList, Long idIntegrazioneIstanza, AttoreScriva attoreScriva, Date dataIns) {
        logBegin(className);
        if (CollectionUtils.isNotEmpty(integrazioneIstanzaList)) {
            for (IntegrazioneIstanzaExtendedDTO integrazioneIstanza : integrazioneIstanzaList) {
                List<AllegatoIntegrazioneDTO> allegatoIntegrazioneList = integrazioneIstanza.getAllegatoIntegrazione();
                saveAllegatoIntegrazione(idIntegrazioneIstanza, attoreScriva, dataIns, allegatoIntegrazioneList);
            }
        }
        logEnd(className);
    }

    /**
     * Save allegato integrazione.
     *
     * @param idIntegrazioneIstanza    the id integrazione istanza
     * @param attoreScriva             the attore scriva
     * @param dataIns                  the data ins
     * @param allegatoIntegrazioneList the allegato integrazione list
     */
    private void saveAllegatoIntegrazione(Long idIntegrazioneIstanza, AttoreScriva attoreScriva, Date dataIns, List<AllegatoIntegrazioneDTO> allegatoIntegrazioneList) {
        logBegin(className);
        if (CollectionUtils.isNotEmpty(allegatoIntegrazioneList)) {
            for (AllegatoIntegrazioneDTO allegatoIntegrazione : allegatoIntegrazioneList) {
                allegatoIntegrazione.setIdIntegrazioneIstanza(idIntegrazioneIstanza);
                allegatoIntegrazione.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                allegatoIntegrazione.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                allegatoIntegrazioneDAO.saveAllegatoIntegrazione(allegatoIntegrazione, dataIns);
            }
        }
        logEnd(className);
    }

    /**
     * Sets tipo integrazione.
     *
     * @param integrazioneIstanza the integrazione istanza
     */
    private void setTipoIntegrazione(IntegrazioneIstanzaExtendedDTO integrazioneIstanza) {
        logBegin(className);
        if (integrazioneIstanza != null
                && integrazioneIstanza.getTipoIntegrazione() != null
                && StringUtils.isNotBlank(integrazioneIstanza.getTipoIntegrazione().getCodTipoIntegrazione())) {
            List<TipoIntegraAllegatoDTO> tipoIntegraAllegatoList = tipoIntegraAllegatoDAO.loadTipoIntegraAllegatoByCode(integrazioneIstanza.getTipoIntegrazione().getCodTipoIntegrazione());
            if (CollectionUtils.isNotEmpty(tipoIntegraAllegatoList)) {
                TipoIntegraAllegatoDTO tipoIntegraAllegato = tipoIntegraAllegatoList.get(0);
                TipoIntegrazioneDTO tipoIntegrazione = new TipoIntegrazioneDTO();
                tipoIntegrazione.setIdTipoIntegrazione(tipoIntegraAllegato.getIdTipoIntegraAllegato());
                integrazioneIstanza.setTipoIntegrazione(tipoIntegrazione);
            }
        }
    }

}
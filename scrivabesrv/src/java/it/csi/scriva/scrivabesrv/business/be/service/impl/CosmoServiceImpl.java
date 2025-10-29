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
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.CosmoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.AvviaProcessoFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.AvviaProcessoFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaDocumentiFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaDocumentoFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaNotificaFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaNotificaFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaPraticaFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.CreaPraticaFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.EsitoCreazioneDocumentiFruitore;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.FileUploadResult;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.InviaSegnaleFruitoreRequest;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.dto.InviaSegnaleFruitoreResponse;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PubSearchIstanzeDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.CompetenzeTerritorioService;
import it.csi.scriva.scrivabesrv.business.be.service.CosmoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzePubblicateService;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaCosmoDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Cosmo service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class CosmoServiceImpl extends BaseApiServiceImpl implements CosmoService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Cosmo service helper.
     */
    @Autowired
    CosmoServiceHelper cosmoServiceHelper;

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
     * The Istanza competenza dao.
     */
    @Autowired
    IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    /**
     * The Oggetto istanza dao.
     */
    @Autowired
    OggettoIstanzaDAO oggettoIstanzaDAO;

    /**
     * The Pub search istanze dao.
     */
    @Autowired
    PubSearchIstanzeDAO pubSearchIstanzeDAO;

    /**
     * The Allegati service.
     */
    @Autowired
    AllegatiService allegatiService;

    /**
     * The Competenze territorio service.
     */
    @Autowired
    CompetenzeTerritorioService competenzeTerritorioService;

    /**
     * The Istanze pubblicate service.
     */
    @Autowired
    IstanzePubblicateService istanzePubblicateService;


    /**
     * Creazione pratica cosmo.
     *
     * @param idIstanza the id istanza
     */
    @Override
    public void creazionePraticaCosmo(Long idIstanza) throws CosmoException, IOException {
        logBeginInfo(className, idIstanza);
        PubIstanzaDTO istanza = istanzePubblicateService.loadIstanzaPubblicataByIdIstanza(idIstanza);
        String codiceIpaEnte = getCodiceIpaEnte(idIstanza);
        String attoreCompilante = getAttoreCompilante(idIstanza);
        CreaPraticaFruitoreResponse creaPraticaFruitoreResponse = this.creaPratica(istanza, codiceIpaEnte, attoreCompilante);
        if (creaPraticaFruitoreResponse != null) {
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatiService.loadAllegatoIstanzaNonInviatoByIdIstanza(idIstanza);
            EsitoCreazioneDocumentiFruitore esitoCreazioneDocumentiFruitore = allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty() ? addDocumenti(istanza, allegatoIstanzaList, codiceIpaEnte) : new EsitoCreazioneDocumentiFruitore();
            if (esitoCreazioneDocumentiFruitore != null) {
                AvviaProcessoFruitoreResponse avviaProcessoFruitoreResponse = this.avviaPratica(istanza, codiceIpaEnte);
                logDebug(className, avviaProcessoFruitoreResponse.toString());
                //InviaSegnaleFruitoreResponse inviaSegnaleFruitoreResponse = this.avanzamentoProcesso(istanza, "", Boolean.FALSE);
            }
        }
    }

    /**
     * Aggiorna pratica documenti cosmo.
     *
     * @param idIstanza the id istanza
     */
    @Override
    public void aggiornaPraticaDocumentiCosmo(Long idIstanza, String codTipoEvento) throws CosmoException, IOException {
        logBegin(className);
        PubIstanzaDTO istanza = istanzePubblicateService.loadIstanzaPubblicataByIdIstanza(idIstanza);
        String codiceIpaEnte = getCodiceIpaEnte(idIstanza);
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatiService.loadAllegatoIstanzaNonInviatoByIdIstanza(idIstanza);
        EsitoCreazioneDocumentiFruitore esitoCreazioneDocumentiFruitore = addDocumenti(istanza, allegatoIstanzaList, codiceIpaEnte);
        logDebug(className, esitoCreazioneDocumentiFruitore != null ? esitoCreazioneDocumentiFruitore.toString() : "esitoCreazioneDocumentiFruitore=null");
        //CreaNotificaFruitoreResponse creaNotificaFruitoreResponse = creaNotifica(istanza, "", new ArrayList<>());
        segnala(istanza, codTipoEvento);
        logEnd(className);
    }

    /**
     * Aggiorna pratica cosmo.
     *
     * @param idIstanza the id istanza
     */
    @Override
    public void aggiornaPraticaCosmo(Long idIstanza) throws CosmoException {
        logBegin(className);
        PubIstanzaDTO istanza = istanzePubblicateService.loadIstanzaPubblicataByIdIstanza(idIstanza);
        CreaNotificaFruitoreResponse creaNotificaFruitoreResponse = creaNotifica(istanza, "", new ArrayList<>());
        logEnd(className);
    }

    /**
     * Gets file from cosmo.
     *
     * @param urlCosmo the url cosmo
     * @return the file from cosmo
     * @throws CosmoException the cosmo exception
     */
    @Override
    public File getFileFromCosmo(String urlCosmo) throws CosmoException {
        logBegin(className);
        File docCosmo = cosmoServiceHelper.getDocumento(urlCosmo);
        logEnd(className);
        return docCosmo;
    }

    /**
     * Gets istanza.
     *
     * @param idIstanza the id istanza
     * @return the istanza
     */
    private IstanzaExtendedDTO getIstanza(Long idIstanza) {
        logBegin(className);
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        logEnd(className);
        return istanzaList != null ? istanzaList.get(0) : null;
    }

    /**
     * Gets istanza competenza principale.
     *
     * @param idIstanza the id istanza
     * @return the istanza competenza principale
     */
    private IstanzaCompetenzaExtendedDTO getIstanzaCompetenzaPrincipale(Long idIstanza) {
        logBegin(className);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaPrincipaliList = istanzaCompetenzaList != null ?
                istanzaCompetenzaList.stream()
                        .filter(istanzaCompetenza -> Boolean.TRUE.equals(istanzaCompetenza.getFlgAutoritaPrincipale()))
                        .collect(Collectors.toList()) : null;
        logEnd(className);
        return istanzaCompetenzaPrincipaliList != null && !istanzaCompetenzaPrincipaliList.isEmpty() ? istanzaCompetenzaPrincipaliList.get(0) : null;
    }

    /**
     * Gets oggetto istanza.
     *
     * @param idIstanza the id istanza
     * @return the oggetto istanza
     */
    private List<OggettoIstanzaExtendedDTO> getOggettoIstanza(Long idIstanza) {
        logBegin(className);
        logEnd(className);
        return oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
    }

    /**
     * Gets codice ipa ente.
     *
     * @param idIstanza the id istanza
     * @return the codice ipa ente
     */
    private String getCodiceIpaEnte(Long idIstanza) {
        logBegin(className);
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = competenzeTerritorioService.extractIstanzaCompetenzaList(idIstanza);
        IstanzaCompetenzaExtendedDTO istanzaCompetenza = istanzaCompetenzaList != null && istanzaCompetenzaList.size() == 1 ? istanzaCompetenzaList.get(0) : null;
        logEnd(className);
        return istanzaCompetenza != null && istanzaCompetenza.getCompetenzaTerritorio() != null ? istanzaCompetenza.getCompetenzaTerritorio().getCodIpa() : null;
    }

    /**
     * Crea pratica crea pratica fruitore response.
     *
     * @param istanza       the istanza
     * @param codiceIpaEnte the codice ipa ente
     * @return the crea pratica fruitore response
     * @throws CosmoException the cosmo exception
     */
    private CreaPraticaFruitoreResponse creaPratica(PubIstanzaDTO istanza, String codiceIpaEnte, String cfCompilante) throws CosmoException {
        logBegin(className);
        CreaPraticaFruitoreResponse creaPraticaFruitoreResponse = null;
        if (istanza != null) {
            CreaPraticaFruitoreRequest creaPraticaFruitoreRequest = new CreaPraticaFruitoreRequest();
            creaPraticaFruitoreRequest.setCodiceIpaEnte(codiceIpaEnte); // scriva_t_competenza_territorio.cod_ipa_ente
            creaPraticaFruitoreRequest.setIdPratica(istanza.getCodIstanza()); // scriva_t_istanza.cod_istanza
            creaPraticaFruitoreRequest.setCodiceTipologia(istanza.getAdempimento().getCodTipoAdempimento() + "_" + istanza.getAdempimento().getCodAdempimento()); // scriva_d_tipo_adempimento.cod_tipo_adempimento _ scriva_d_adempimento.cod_adempimento
            creaPraticaFruitoreRequest.setOggetto(istanza.getOggettoIstanza() != null ? istanza.getOggettoIstanza().get(0).getDenOggetto() : null); // scriva_t_oggetto_istanza.den_oggetto
            creaPraticaFruitoreRequest.setRiassunto(istanza.getOggettoIstanza() != null ? istanza.getOggettoIstanza().get(0).getDesOggetto() : null); // scriva_t_oggetto_istanza.des_oggetto
            creaPraticaFruitoreRequest.setUtenteCreazionePratica(cfCompilante); // cf segreteria ac??? o cf compilante
            creaPraticaFruitoreRequest.setMetadati(istanza.toJsonString());
            creaPraticaFruitoreResponse = cosmoServiceHelper.creaPratica(creaPraticaFruitoreRequest);
        }
        logEnd(className);
        return creaPraticaFruitoreResponse;
    }

    /**
     * Add documenti esito creazione documenti fruitore.
     *
     * @param istanza             the istanza
     * @param allegatoIstanzaList the allegato istanza list
     * @param codiceIpaEnte       the codice ipa ente
     * @return the esito creazione documenti fruitore
     * @throws CosmoException the cosmo exception
     * @throws IOException    the io exception
     */
    public EsitoCreazioneDocumentiFruitore addDocumenti(PubIstanzaDTO istanza, List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList, String codiceIpaEnte) throws CosmoException, IOException {
        logBegin(className);
        EsitoCreazioneDocumentiFruitore esitoCreazioneDocumentiFruitore = null;
        if (allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty()) {
            List<AllegatoIstanzaCosmoDTO> allegatoIstanzaCosmoList = new ArrayList<>();
            for (AllegatoIstanzaExtendedDTO allegatoIstanza : allegatoIstanzaList) {
                AllegatoIstanzaCosmoDTO allegatoIstanzaCosmo = new AllegatoIstanzaCosmoDTO();
                File file = allegatiService.getFileFromIndex(allegatoIstanza.getUuidIndex());
                FileUploadResult fileUploadResult = uploadDocumento(file, allegatoIstanza.getNomeAllegato());
                allegatoIstanzaCosmo.setUuidCosmo(fileUploadResult != null ? fileUploadResult.getUploadUUID() : null);
                allegatoIstanzaCosmo.setAllegatoIstanza(allegatoIstanza);
                allegatoIstanzaCosmoList.add(allegatoIstanzaCosmo);
            }
            esitoCreazioneDocumentiFruitore = setMetadatiDocumento(istanza, allegatoIstanzaCosmoList, codiceIpaEnte);
        }
        logEnd(className);
        return esitoCreazioneDocumentiFruitore;
    }

    /**
     * Upload documento file upload result.
     *
     * @param file the file
     * @return the file upload result
     * @throws CosmoException the cosmo exception
     */
    private FileUploadResult uploadDocumento(File file, String fileName) throws CosmoException {
        logBegin(className);
        return file != null ? cosmoServiceHelper.uploadDocumento(file, fileName) : null;
    }

    /**
     * Sets metadati documento.
     *
     * @param istanza                  the istanza
     * @param allegatoIstanzaCosmoList the allegato istanza list
     * @return the metadati documento
     * @throws CosmoException the cosmo exception
     */
    private EsitoCreazioneDocumentiFruitore setMetadatiDocumento(PubIstanzaDTO istanza, List<AllegatoIstanzaCosmoDTO> allegatoIstanzaCosmoList, String codiceIpaEnte) throws CosmoException {
        logBegin(className);
        EsitoCreazioneDocumentiFruitore esitoCreazioneDocumentiFruitore = null;
        if (istanza != null && allegatoIstanzaCosmoList != null && !allegatoIstanzaCosmoList.isEmpty()) {
            CreaDocumentiFruitoreRequest creaDocumentiFruitoreRequest = new CreaDocumentiFruitoreRequest();
            creaDocumentiFruitoreRequest.setIdPratica(istanza.getCodIstanza());
            creaDocumentiFruitoreRequest.setCodiceIpaEnte(codiceIpaEnte);
            for (AllegatoIstanzaCosmoDTO allegatoIstanza : allegatoIstanzaCosmoList) {
                CreaDocumentoFruitoreRequest creaDocumentoFruitoreRequest = new CreaDocumentoFruitoreRequest();
                creaDocumentoFruitoreRequest.id(allegatoIstanza.getAllegatoIstanza().getCodAllegato()); // codice allegato
                creaDocumentoFruitoreRequest.idPadre(getCodAllegatoByIdAllegatoIstanza(allegatoIstanza.getAllegatoIstanza().getIdAllegatoIstanzaPadre())); //  valorizzato con cod_allegato padre
                creaDocumentoFruitoreRequest.titolo(allegatoIstanza.getAllegatoIstanza().getTitoloAllegato()); // titolo allegato
                creaDocumentoFruitoreRequest.descrizione(allegatoIstanza.getAllegatoIstanza().getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato() + "_" + allegatoIstanza.getAllegatoIstanza().getTipologiaAllegato().getCodTipologiaAllegato()); // des_categoria_allegato+"-"+des_tipologia_allegato
                creaDocumentoFruitoreRequest.autore(allegatoIstanza.getAllegatoIstanza().getAutoreAllegato()); // autore allegato
                creaDocumentoFruitoreRequest.setCodiceTipo(allegatoIstanza.getAllegatoIstanza().getTipologiaAllegato().getCodTipologiaAllegato()); // cod_Tipologia_allegato
                //creaDocumentoFruitoreRequest.setCodiceTipo(allegatoIstanza.getAllegatoIstanza().getClasseAllegato().getCodClasseAllegato()); // cod_classe_allegato
                creaDocumentoFruitoreRequest.uploadUUID(allegatoIstanza.getUuidCosmo()); // uuid del file ottenuto dalla chiamata di Upload
                creaDocumentiFruitoreRequest.addDocumentiItem(creaDocumentoFruitoreRequest);
            }
            esitoCreazioneDocumentiFruitore = cosmoServiceHelper.caricaDocumento(istanza.getCodIstanza(), creaDocumentiFruitoreRequest);
            updateDataInvioEsterno(allegatoIstanzaCosmoList);
        }
        logEnd(className);
        return esitoCreazioneDocumentiFruitore;
    }

    /**
     * Avvia pratica.
     *
     * @param istanza       the istanza
     * @param codiceIpaEnte the codice ipa ente
     * @return the metadati documento
     * @throws CosmoException the cosmo exception
     */
    public AvviaProcessoFruitoreResponse avviaPratica(PubIstanzaDTO istanza, String codiceIpaEnte) throws CosmoException {
        logBegin(className);
        AvviaProcessoFruitoreResponse avviaProcessoFruitoreResponse = null;
        if (istanza != null) {
            AvviaProcessoFruitoreRequest avviaProcessoFruitoreRequest = new AvviaProcessoFruitoreRequest();
            avviaProcessoFruitoreRequest.setCodiceIpaEnte(codiceIpaEnte);
            avviaProcessoFruitoreRequest.setIdPratica(istanza.getCodIstanza());
            avviaProcessoFruitoreResponse = cosmoServiceHelper.avviaPratica(avviaProcessoFruitoreRequest);
        }
        logEnd(className);
        return avviaProcessoFruitoreResponse;
    }

    /**
     * Avanzamento processo invia segnale fruitore response.
     *
     * @param istanza          the istanza
     * @param codiceSegnale    the codice segnale
     * @param richiediCallback the richiedi callback
     * @return the invia segnale fruitore response
     * @throws CosmoException the cosmo exception
     */
    public InviaSegnaleFruitoreResponse avanzamentoProcesso(PubIstanzaDTO istanza, String codiceSegnale, Boolean richiediCallback) throws CosmoException {
        logBegin(className);
        InviaSegnaleFruitoreResponse inviaSegnaleFruitoreResponse = null;
        if (istanza != null) {
            InviaSegnaleFruitoreRequest inviaSegnaleFruitoreRequest = new InviaSegnaleFruitoreRequest();
            inviaSegnaleFruitoreRequest.setCodiceSegnale(codiceSegnale);
            inviaSegnaleFruitoreRequest.setRichiediCallback(richiediCallback);
            inviaSegnaleFruitoreRequest.setVariabili(null);
            inviaSegnaleFruitoreResponse = cosmoServiceHelper.avanzaProcesso(istanza.getCodIstanza(), inviaSegnaleFruitoreRequest);
        }
        logEnd(className);
        return inviaSegnaleFruitoreResponse;
    }

    /**
     * Crea notifica crea notifica fruitore response.
     *
     * @param istanza     the istanza
     * @param descrizione the descrizione
     * @param destinatari the destinatari
     * @return the crea notifica fruitore response
     * @throws CosmoException the cosmo exception
     */
    private CreaNotificaFruitoreResponse creaNotifica(PubIstanzaDTO istanza, String descrizione, List<String> destinatari) throws CosmoException {
        logBegin(className);
        CreaNotificaFruitoreResponse creaNotificaFruitoreResponse = null;
        if (istanza != null) {
            CreaNotificaFruitoreRequest creaNotificaFruitoreRequest = new CreaNotificaFruitoreRequest();
            creaNotificaFruitoreRequest.setCodiceIpaEnte(istanza.getCompetenzaTerritorio().get(0).getResponsabile());
            creaNotificaFruitoreRequest.setIdPratica(istanza.getCodIstanza());
            creaNotificaFruitoreRequest.setDescrizione(descrizione);
            creaNotificaFruitoreRequest.setDestinatari(destinatari);
            creaNotificaFruitoreResponse = cosmoServiceHelper.creaNotifica(creaNotificaFruitoreRequest);
        }
        logEnd(className);
        return creaNotificaFruitoreResponse;
    }

    /**
     * Update data invio esterno integer.
     *
     * @param allegatoIstanzaCosmoList the allegato istanza cosmo list
     * @return the integer
     */
    private Integer updateDataInvioEsterno(List<AllegatoIstanzaCosmoDTO> allegatoIstanzaCosmoList) {
        logBegin(className);
        if (allegatoIstanzaCosmoList != null) {
            List<Long> idAllegatiDaAggiornare = allegatoIstanzaCosmoList.stream()
                    .map(AllegatoIstanzaCosmoDTO::getAllegatoIstanza)
                    .map(AllegatoIstanzaExtendedDTO::getIdAllegatoIstanza)
                    .collect(Collectors.toList());
            logEnd(className);
            return !idAllegatiDaAggiornare.isEmpty() ? allegatiService.updateDataInvioEsterno(idAllegatiDaAggiornare) : 0;
        }
        logEnd(className);
        return 0;
    }

    /**
     * Gets attore compilante.
     *
     * @param idIstanza the id istanza
     * @return the attore compilante
     */
    private String getAttoreCompilante(Long idIstanza) {
        logBegin(className);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList = istanzaAttoreDAO.loadIstanzaAttoreByIdIstanza(idIstanza);
        List<IstanzaAttoreExtendedDTO> istanzaAttoreCompilanteList = istanzaAttoreList != null ?
                istanzaAttoreList.stream()
                        .filter(istanzaAttore -> ProfiloAppEnum.COMPILANTE.name().equalsIgnoreCase(istanzaAttore.getProfiloApp().getCodProfiloApp()))
                        .collect(Collectors.toList()) :
                null;
        return istanzaAttoreCompilanteList != null && !istanzaAttoreCompilanteList.isEmpty() ? istanzaAttoreCompilanteList.get(0).getCfAttore() : null;
    }

    /**
     * Gets cod allegato by id allegato istanza.
     *
     * @param idAllegatoIstanza the id allegato istanza
     * @return the cod allegato by id allegato istanza
     */
    public String getCodAllegatoByIdAllegatoIstanza(Long idAllegatoIstanza) {
        logBegin(className);
        String codAllegato = null;
        if (idAllegatoIstanza != null && idAllegatoIstanza > 0) {
            AllegatoIstanzaExtendedDTO allegatoIstanza = allegatiService.loadAllegatiById(idAllegatoIstanza);
            codAllegato = allegatoIstanza != null ? allegatoIstanza.getCodAllegato() : null;
        }
        logEnd(className);
        return codAllegato;
    }

    /**
     * Segnala invia segnale fruitore response.
     *
     * @param istanza       the istanza
     * @param codTipoEvento the cod tipo evento
     * @return the invia segnale fruitore response
     */
    public InviaSegnaleFruitoreResponse segnala(PubIstanzaDTO istanza, String codTipoEvento) {
        logBegin(className);
        InviaSegnaleFruitoreResponse inviaSegnaleFruitoreResponse = null;
        if (istanza != null) {
            try {
                InviaSegnaleFruitoreRequest inviaSegnaleFruitoreRequest = new InviaSegnaleFruitoreRequest();
                inviaSegnaleFruitoreRequest.setCodiceSegnale(codTipoEvento);
                inviaSegnaleFruitoreRequest.setRichiediCallback(Boolean.TRUE);
                inviaSegnaleFruitoreRequest.setVariabili(new ArrayList<>());
                inviaSegnaleFruitoreResponse = cosmoServiceHelper.segnala(istanza.getCodIstanza(), inviaSegnaleFruitoreRequest);
            } catch (Exception e){
                logError(className, e);
            }
        }
        logEnd(className);
        return inviaSegnaleFruitoreResponse;
    }

}
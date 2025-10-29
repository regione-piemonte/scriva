/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import it.csi.scriva.scrivabesrv.business.be.PagamentiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.PiemontePayServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayPagamentoMarcaBolloInDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayPagamentoMarcaBolloOutDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayRtInDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayRtOutDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.factory.PiemontePayFactory;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PagamentoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RiscossioneEnteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TentativoDettaglioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TentativoPagamentoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.PagamentiService;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PPayResultDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RiscossioneEnteExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UrlDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.GruppoPagamentoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.PPayCodiceEsitoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoPagamentoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoSintesiPagamentoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoTentativoPagamentoEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.hashing.HashingUtil;
import it.csi.scriva.scrivabesrv.util.mail.EmailDTO;
import it.csi.scriva.scrivabesrv.util.mail.MailManager;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Pagamenti api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PagamentiApiServiceImpl extends BaseApiServiceImpl implements PagamentiApi {

    private final String className = this.getClass().getSimpleName();

    private static final String KEY_PROVINCIA_MARCA_BOLLO = "SCRIVA_PROVINCIA_MARCA_DA_BOLLO";
    private static final String KEY_IMPORTO_MARCA_BOLLO = "SCRIVA_IMPORTO_MARCA_DA_BOLLO";

    private static final String KEY_PPAY_USER = "SCRIVA_PPAY_USER";
    private static final String KEY_PPAY_PWD = "SCRIVA_PPAY_PWD";

    private static final String KEY_MAIL_SERVER_HOST = "SCRIVA_SERVER_POSTA_HOST";
    private static final String KEY_MAIL_SERVER_PORT = "SCRIVA_SERVER_POSTA_PORTA";
    private static final String KEY_MAIL_SENDER_ADDRESS = "SCRIVA_SERVER_POSTA_MITTENTE";
    private static final String KEY_MAIL_ASSIST_ADDRESS = "SCRIVA_EMAIL_ASSISTENZA_VIA";
    
    private static final String KEY_MAIL_SERVER_USERNAME = "SCRIVA_SERVER_POSTA_USERNAME";
    private static final String KEY_MAIL_SERVER_PASSWORD = "SCRIVA_SERVER_POSTA_PASSWORD";

    private final List<String> KEYS = Arrays.asList(KEY_PROVINCIA_MARCA_BOLLO, KEY_IMPORTO_MARCA_BOLLO, KEY_PPAY_USER, KEY_PPAY_PWD,KEY_MAIL_SERVER_USERNAME,KEY_MAIL_SERVER_PASSWORD);

    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;

    @Autowired
    private AllegatoIstanzaDAO allegatoIstanzaDAO;

    @Autowired
    private CompilanteDAO compilanteDAO;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private PagamentoIstanzaDAO pagamentoIstanzaDAO;

    @Autowired
    private RiscossioneEnteDAO riscossioneEnteDAO;

    @Autowired
    private TentativoDettaglioDAO tentativoDettaglioDAO;

    @Autowired
    private TentativoPagamentoDAO tentativoPagamentoDAO;

    @Autowired
    private PagamentiService pagamentiService;

    @Autowired
    private PiemontePayServiceHelper piemontePayServiceHelper;

    @Autowired
    private AllegatiManager allegatiManager;

    /**
     * Load stati istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadPagamentiIstanza(String xRequestAuth, String xRequestId, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanza(idIstanza, attoreScriva.getComponente()), className);
    }

    /**
     * Load stati istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    @Transactional
    public Response loadPagamentiIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);

        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList = new ArrayList<>();
        List<RiscossioneEnteExtendedDTO> riscossioneEnteList;
        if (istanza != null) {
            pagamentoIstanzaList = pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanza(idIstanza, attoreScriva.getComponente());
            riscossioneEnteList = riscossioneEnteDAO.loadRiscossioneEnteByIdIstanza(idIstanza, attoreScriva.getComponente());
            //addPagamentoIstanzaList(istanza, pagamentoIstanzaList, riscossioneEnteList);
            //deletePagamentoIstanzaList(istanza, pagamentoIstanzaList, riscossioneEnteList);
            addPagamentoIstanzaNoBolloList(istanza, pagamentoIstanzaList, riscossioneEnteList);
            addPagamentoIstanzaBolloList(istanza, pagamentoIstanzaList, riscossioneEnteList);
        }
        pagamentoIstanzaList = pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanza(idIstanza, attoreScriva.getComponente());
        /*
        for (PagamentoIstanzaExtendedDTO pagamentoIstanza : pagamentoIstanzaList) {
            List<TentativoDettaglioExtendedDTO> tentativoDettaglioPagamento = tentativoDettaglioDAO.loadTentativoDettaglioByIdPagamentoIstanza(pagamentoIstanza.getIdPagamentoIstanza());
        }
        */
        updateStatoSintesiPagamentoIstanza(idIstanza, attoreScriva);
        return getResponseList(pagamentoIstanzaList, className);
    }

    /**
     * Save tentativo pagamento response.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response saveTentativoPagamento(PagamentoIstanzaExtendedDTO pagamentoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, pagamentoIstanza);
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioList;
        UrlDTO url = new UrlDTO();

        // Verifica permessi su istanza
        //Response response = setAttoreRight(httpHeaders, pagamentoIstanza.getIdIstanza(), Boolean.TRUE);
        Response response = setAttoreRight(httpHeaders, pagamentoIstanza.getIdIstanza());
        if (response != null) {
            return response;
        }

        try {
            //update importo pagamento istanza se inserito da utente
            updateFlgImportoNonDovutoPagamentoIstanza(pagamentoIstanza);

            //Lista di tutti i pagamenti collegati
            List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList = pagamentoIstanzaDAO.loadPagamentoIstanzaHierarchyById(pagamentoIstanza.getIdPagamentoIstanza());
            if (pagamentoIstanzaList != null && !pagamentoIstanzaList.isEmpty()) {
                //verifica esistenza tentativo di pagamento non fallito
                if (Boolean.FALSE.equals(tentativoPagamentoExist(pagamentoIstanza.getIdPagamentoIstanza()))) {
                    // Inserisce tentativo pagamento se non esiste
                    TentativoPagamentoDTO tentativoPagamento = new TentativoPagamentoDTO();
                    tentativoPagamento.setIdStatoTentativoPagamento(1L);
                    tentativoPagamento.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                    Long idTentativoPagamento = tentativoPagamentoDAO.saveTentativoPagamento(tentativoPagamento);
                    if (idTentativoPagamento != null) {
                        List<TentativoPagamentoExtendedDTO> tentativoPagamentoNewList = tentativoPagamentoDAO.loadTentativoPagamentoById(idTentativoPagamento);
                        TentativoPagamentoDTO tentativoPagamentoNew = tentativoPagamentoNewList != null && !tentativoPagamentoNewList.isEmpty() ? tentativoPagamentoNewList.get(0).getDTO() : null;
                        if (tentativoPagamentoNew != null) {
                            for (PagamentoIstanzaExtendedDTO pagamentoIstanzaToIns : pagamentoIstanzaList) {
                                // inserire dettaglio pagamento
                                TentativoDettaglioDTO tentativoDettaglio = new TentativoDettaglioDTO();
                                tentativoDettaglio.setIdTentativoPagamento(idTentativoPagamento);
                                tentativoDettaglio.setIdPagamentoIstanza(pagamentoIstanzaToIns.getIdPagamentoIstanza());
                                tentativoDettaglio.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                                tentativoDettaglioDAO.saveTentativoDettaglio(tentativoDettaglio);
                            }
                            // recupero tentativi dettaglio inseriti
                            tentativoDettaglioList = tentativoDettaglioDAO.loadTentativoDettaglioExtendedByIdTentativoPagamento(idTentativoPagamento);

                            // aggiornarmento tentativo pagamento
                            getTentativoPagamentoByDettaglio(tentativoPagamentoNew, tentativoDettaglioList);
                            tentativoPagamentoDAO.updateTentativoPagamento(tentativoPagamentoNew);

                            // recupero oggetto aggiornato
                            tentativoPagamentoNewList = tentativoPagamentoDAO.loadTentativoPagamentoById(idTentativoPagamento);
                            if (tentativoPagamentoNewList != null && !tentativoPagamentoNewList.isEmpty()) {
                                TentativoPagamentoExtendedDTO tentativoPagamentoExtended = tentativoPagamentoNewList.get(0);
                                tentativoPagamentoExtended.setTentativoDettaglio(tentativoDettaglioList);
                                CompilanteDTO compilante = getCompilante();

                                Map<String, String> parametri = configurazioneDAO.loadConfigByKeyList(KEYS);
                                if (parametri != null && parametri.containsKey(KEY_PROVINCIA_MARCA_BOLLO) && parametri.containsKey(KEY_IMPORTO_MARCA_BOLLO) && parametri.containsKey(KEY_PPAY_USER) && parametri.containsKey(KEY_PPAY_PWD)) {
                                    PPayPagamentoMarcaBolloInDTO pPayPagamentoMarcaBolloInput = getPPayPagamentoMarcaBolloInput(compilante, tentativoPagamentoExtended, parametri);
                                    logDebug(className, "pPayPagamentoMarcaBolloInput : \n" + pPayPagamentoMarcaBolloInput);

                                    // chiamata servizio
                                    piemontePayServiceHelper.setUser(parametri.get(KEY_PPAY_USER));
                                    piemontePayServiceHelper.setPassword(parametri.get(KEY_PPAY_PWD));
                                    PPayPagamentoMarcaBolloOutDTO pPayPagamentoMarcaBolloOutput = null;
                                    try {
                                        pPayPagamentoMarcaBolloOutput = piemontePayServiceHelper.pagamentoMarcaDaBollo(pPayPagamentoMarcaBolloInput);
                                    } catch (Exception e) {
                                        sendMailAssistenza(e.getMessage().length() > 21 ? StringUtils.substring(e.getMessage(), 21) : e.getMessage());
                                    }

                                    //recupero url in caso positivo
                                    url.setUrl(setEsitoRichiestaPagamentoBollo(tentativoPagamentoExtended, pPayPagamentoMarcaBolloOutput));

                                } else {
                                    throw new Exception("Verificare parametri configurazione");
                                }
                            }
                            logDebug(className, tentativoPagamentoNew.toString());
                        }
                    }
                }
            } else {
                String error = "Tentativo pagamento already exist";
                logError(className, error);
                return getResponseList(null, className);
            }
        } catch (Exception e) {
            logError(className, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return getResponseList(null, className);
        }

        return getResponseSaveUpdate(url, className);
    }

    /**
     * Save tentativo pagamento response.
     *
     * @param pPayResult      the p pay result
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response saveResultTentativoPagamento(PPayResultDTO pPayResult, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, pPayResult);
        IstanzaExtendedDTO istanza = null;

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            String identificativoPagamento = pPayResult.getIdPagamento();
            List<TentativoPagamentoExtendedDTO> tentativoPagamentoList = tentativoPagamentoDAO.loadTentativoPagamentoByIdentificativoPagamentoPPay(identificativoPagamento);
            if (tentativoPagamentoList != null && !tentativoPagamentoList.isEmpty()) {
                TentativoPagamentoExtendedDTO tentativoPagamento = tentativoPagamentoList.get(0);
                List<TentativoDettaglioExtendedDTO> tentativoDettaglioList = tentativoDettaglioDAO.loadTentativoDettaglioExtendedByIdTentativoPagamento(tentativoPagamento.getIdTentativoPagamento());
                tentativoPagamento.setTentativoDettaglio(tentativoDettaglioList);
                setEsitoResultPagamentoBollo(tentativoPagamento, pPayResult);
                istanza = istanzaDAO.loadIstanza(tentativoPagamento.getTentativoDettaglio().get(0).getPagamentoIstanza().getIdIstanza()).get(0);
                updateStatoSintesiPagamentoIstanza(istanza.getIdIstanza(), attoreScriva);
            } else {
                throw new Exception("Tentativo pagamento non trovato");
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logError(className, e);
            return getResponseSaveUpdate(0, className);
        }
        return getResponseSaveUpdate(istanza, className);
    }

    /**
     * Save tentativo pagamento altri canali response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveTentativoPagamentoAltriCanali(PagamentoIstanzaExtendedDTO pagamentoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, pagamentoIstanza);
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, pagamentoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }
        pagamentoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        Integer res = pagamentoIstanzaDAO.updatePagamentoIstanza(pagamentoIstanza.getDTO());
        updateStatoSintesiPagamentoIstanza(pagamentoIstanza.getIdIstanza(), attoreScriva);
        return getResponseSaveUpdate(res != null && res > 0 ? pagamentoIstanza : res, className);
    }

    @Override
    public Response getRTByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response result = null;

        IstanzaDTO istanza = istanzaDAO.findByPK(idIstanza);
        List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaList;
        // Verifica permessi su istanza

        Response response = setAttoreRight(httpHeaders, idIstanza);
        /*
        if (response != null) {
            return response;
        }
        */

        if (istanza != null) {
            pagamentoIstanzaList = pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanza(idIstanza, attoreScriva != null ? attoreScriva.getComponente() : "FO");

            for (PagamentoIstanzaExtendedDTO pagamentoIstanza : pagamentoIstanzaList) {
                // filtrare quelle in attesa di ricevuta 020
                if (StatoPagamentoEnum.ATTESA_RICEVUTA.getCodice().equals(pagamentoIstanza.getStatoPagamento().getCodiceStatoPagamento())) {
                    List<TentativoDettaglioExtendedDTO> tentativoDettaglioList = tentativoDettaglioDAO.loadTentativoDettaglioByIdPagamentoIstanza(pagamentoIstanza.getIdPagamentoIstanza());
                    if (tentativoDettaglioList != null && !tentativoDettaglioList.isEmpty()) {
                        List<TentativoDettaglioExtendedDTO> tentativoDettaglioWithIuvList = getTentativoDettaglioWithIuvList(tentativoDettaglioList);
                        Map<String, String> parametri = configurazioneDAO.loadConfigByKeyList(KEYS);
                        piemontePayServiceHelper.setUser(parametri.get(KEY_PPAY_USER));
                        piemontePayServiceHelper.setPassword(parametri.get(KEY_PPAY_PWD));
                        for (TentativoDettaglioExtendedDTO tentativoDettaglioWithIuv : tentativoDettaglioWithIuvList) {
                            String codiceFiscaleEnteCreditore = tentativoDettaglioWithIuv.getPagamentoIstanza().getRiscossioneEnte().getAdempimentoTipoPagamento().getEnteCreditore().getCfEnteCreditore();
                            String codiceFiscalePagatore = tentativoDettaglioWithIuv.getGestAttoreIns();
                            PPayRtInDTO pPayRtInputXML = PiemontePayFactory.createPPayRTXmlInput(tentativoDettaglioWithIuv.getTentativoPagamento(), tentativoDettaglioWithIuv, codiceFiscalePagatore, codiceFiscaleEnteCreditore);
                            //riga di test
                            //PPayRtInDTO pPayRtInputXML = PiemontePayFactory.createPPayRTXmlInput(tentativoDettaglioWithIuv.getTentativoPagamento(), tentativoDettaglioWithIuv, "TSTPNI70D01L219V", codiceFiscaleEnteCreditore);
                            PPayRtOutDTO pPayRtOutputXML = piemontePayServiceHelper.gerRT(pPayRtInputXML);

                            if (pPayRtOutputXML != null && pPayRtOutputXML.getCodiceEsito() != null) {
                                if (pPayRtOutputXML.getCodiceEsito().equalsIgnoreCase("000")) {
                                    // Pagamento fallito
                                    if (pPayRtOutputXML.getDescrizioneStatoPagamento().equalsIgnoreCase("Fallito")) {
                                        String rtXml = pPayRtOutputXML.getRtXml();
                                        PagamentoIstanzaDTO pagamento = pagamentoIstanza.getDTO();
                                        pagamento.setIdStatoPagamento(StatoPagamentoEnum.EFFETTUATO_FALLITO.getIdStatoPagamento());
                                        pagamento.setRtXml(rtXml);
                                        pagamento.setGestAttoreUpd("RT_PROCESS");
                                        pagamentoIstanzaDAO.updatePagamentoIstanza(pagamento);
                                        // creare una copia di quella fallita
                                        pagamento.setIdStatoPagamento(StatoPagamentoEnum.DA_EFFETTUARE.getIdStatoPagamento());
                                        pagamento.setGestAttoreIns("RT_PROCESS");
                                        Long idPagamentoIstanzaNew = pagamentoIstanzaDAO.savePagamentoIstanza(pagamentoIstanza);
                                        pagamento.setIdPagamentoIstanza(idPagamentoIstanzaNew);
                                        //TODO verifica associazioni
                                    } else {
                                        JSONObject jsonRtXML = pPayRtOutputXML.getRtXmlJson();
                                        String rtXml = pPayRtOutputXML.getRtXml();
                                        Timestamp dataEffettivoPagamento = null;
                                        BigDecimal importoPagato = null;
                                        if (jsonRtXML != null) {
                                            String json = jsonRtXML.toString();
                                            try {
                                                //recupero data effettivo pagamento da xml
                                                //String data_path = "$.RT.dataOraMessaggioRicevuta";
                                                String data_path = "$.pay_i:RT.pay_i:dataOraMessaggioRicevuta";
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                String data = JsonPath.read(json, data_path);
                                                Date parsedDate = dateFormat.parse(data);
                                                dataEffettivoPagamento = new Timestamp(parsedDate.getTime());
                                            } catch (PathNotFoundException | ParseException e) {
                                                logError(className, e);
                                            }
                                            try {
                                                //recupero importo effettivo pagamento da xml
                                                String importo_path = "$.pay_i:RT.pay_i:datiPagamento.pay_i:importoTotalePagato";
                                                Integer importo = JsonPath.read(json, importo_path);
                                                importoPagato = new BigDecimal(importo);
                                            } catch (Exception e) {
                                                logError(className, e);
                                            }

                                        }

                                        // Ricevuta PDF
                                        PPayRtInDTO pPayRtInputPDF = PiemontePayFactory.createPPayRTPdfInput(tentativoDettaglioWithIuv.getTentativoPagamento(), tentativoDettaglioWithIuv, codiceFiscalePagatore, codiceFiscaleEnteCreditore);
                                        PPayRtOutDTO pPayRtOutputPDF = piemontePayServiceHelper.gerRT(pPayRtInputPDF);
                                        if (pPayRtOutputPDF != null && pPayRtOutputPDF.getCodiceEsito() != null) {
                                            if (pPayRtOutputPDF.getCodiceEsito().equalsIgnoreCase("000")) {
                                                //salvataggio pdf allegati
                                                byte[] bytesRicevutaRTPdf = pPayRtOutputPDF.getRtPdf();
                                                String filename = tentativoDettaglioWithIuv.getIuvTentativoPagamento() + ".pdf";
                                                try {
                                                    FileUtils.writeByteArrayToFile(new File(filename), bytesRicevutaRTPdf);
                                                    File ricevutaRT = new File(filename);
                                                    //Long idAllegatoIstanza = 10L; // test
                                                    List<AllegatoIstanzaExtendedDTO> allegatoIstanza = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndNome(idIstanza, filename);
                                                    Long idAllegatoIstanza = allegatoIstanza != null && !allegatoIstanza.isEmpty() ? allegatoIstanza.get(0).getIdAllegatoIstanza() : allegatiManager.uploadAllegato(idIstanza, attoreScriva, ricevutaRT, filename, Constants.COD_CATEGORIA_ALLEGATI_SISTEMA, Constants.COD_TIPOLOGIA_RICEVUTE_PAGAMENTI);
                                                    if (idAllegatoIstanza != null) {
                                                        PagamentoIstanzaDTO pagamento = pagamentoIstanza.getDTO();
                                                        pagamento.setIdAllegatoIstanza(idAllegatoIstanza);
                                                        pagamento.setRtXml(rtXml);
                                                        pagamento.setIdStatoPagamento(StatoPagamentoEnum.PAGATO.getIdStatoPagamento());
                                                        pagamento.setDataEffettivoPagamento(dataEffettivoPagamento);
                                                        pagamento.setImportoPagato(importoPagato);
                                                        pagamento.setIuv(pPayRtOutputPDF.getIuvEffettivo());
                                                        //pagamento.setIubd(pPayRtOutputPDF.get);
                                                        pagamento.setGestAttoreUpd("RT_PROCESS");
                                                        pagamentoIstanzaDAO.updatePagamentoIstanza(pagamento);
                                                        result = Response.ok().status(200).build();
                                                    }
                                                    //} catch (IOException e) {
                                                } catch (IOException | GenericException e) {
                                                    logError(className, e);
                                                    return getResponseSaveUpdate(null, className);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    String error = "idIstanza [" + idIstanza + "] : " + pPayRtOutputXML.getCodiceEsito() + " : " + pPayRtOutputXML.getDescrizioneStatoPagamento();
                                    logError(className, error);
                                    return getResponseSaveUpdate(null, className);
                                }
                            }
                        }
                    }
                }
            }
        }
        // 200 - sì
        // 204 - nessun cambiamento
        updateStatoSintesiPagamentoIstanza(idIstanza, attoreScriva);
        return result;
    }

    /**
     * Aggiorna importo dovuto qualora fosse inserito dall'utente
     *
     * @param pagamentoIstanza pagamentoIstanza
     * @throws Exception e
     */
    private void updateFlgImportoNonDovutoPagamentoIstanza(PagamentoIstanzaExtendedDTO pagamentoIstanza) throws Exception {
        PagamentoIstanzaDTO pagamentoIstanzaOld = pagamentoIstanzaDAO.findByPK(pagamentoIstanza.getIdPagamentoIstanza());
        if (pagamentoIstanzaOld != null && (!pagamentoIstanzaOld.getFlgNonDovuto().equals(pagamentoIstanza.getFlgNonDovuto()) || pagamentoIstanzaOld.getImportoDovuto().compareTo(pagamentoIstanza.getImportoDovuto()) != 0)) {
            pagamentoIstanza.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            int numRecordUpdated = pagamentoIstanzaDAO.updatePagamentoIstanza(pagamentoIstanza.getDTO());
            if (numRecordUpdated == 0) {
                throw new Exception("PagamentoIstanza ID [" + pagamentoIstanza.getIdPagamentoIstanza() + "] No updated");
            }
        }
    }

    /**
     * Imposta i parametri tipoBollo, flgSoloMarca, identificativoPagamento, hashPagamento
     *
     * @param tentativoPagamento     tentativoPagamento
     * @param tentativoDettaglioList tentativoDettaglioList
     */
    private void getTentativoPagamentoByDettaglio(TentativoPagamentoDTO tentativoPagamento, List<TentativoDettaglioExtendedDTO> tentativoDettaglioList) {
        logBeginInfo(className, "tentativoPagamento : \n" + tentativoPagamento + "\ntentativoDettaglioList :\n" + tentativoDettaglioList);
        int countTentativiDettaglio = tentativoDettaglioList.size();
        String hash = "";
        Date now = Calendar.getInstance().getTime();

        List<TentativoDettaglioExtendedDTO> tentativoDettaglioBolloList = getTentativoDettaglioBolloList(tentativoDettaglioList);
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioNoBolloList = getTentativoDettaglioNoBolloList(tentativoDettaglioList);

        String identificativoPagamento = tentativoPagamento.getIdTentativoPagamento() + "_" + now.getTime() + HashingUtil.generateRandomAlphanumericString(2);
        tentativoPagamento.setIdentificativoPagamentoPpay(identificativoPagamento.substring(0, Math.min(50, identificativoPagamento.length())));
        tentativoPagamento.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        if (countTentativiDettaglio == 1 && tentativoDettaglioBolloList.size() > 0 && tentativoDettaglioNoBolloList.size() == 0) {
            // solo marca da bollo
            tentativoPagamento.setTipoBollo("01");
            tentativoPagamento.setFlgSoloMarca(Boolean.TRUE);
        } else if (countTentativiDettaglio > 0 && tentativoDettaglioBolloList.size() == 0 && tentativoDettaglioNoBolloList.size() > 0) {
            // solo oneri
            // Se esiste solo un onere (o un onere con componenti) associarlo ad una marca da bollo fittizia
            tentativoPagamento.setTipoBollo("XX");
            tentativoPagamento.setFlgSoloMarca(Boolean.FALSE);
        } else if (countTentativiDettaglio > 0 && tentativoDettaglioBolloList.size() > 0 && tentativoDettaglioNoBolloList.size() > 0) {
            // marca da bollo + oneri
            tentativoPagamento.setTipoBollo("01");
            tentativoPagamento.setFlgSoloMarca(Boolean.FALSE);
        }
        // calcolo HASH
        try {
            hash = HashingUtil.encodeSHA256Hash(tentativoPagamento.toString() + now);
        } catch (Exception e) {
            logError(className, e);
        }
        tentativoPagamento.setHashPagamento(hash);
    }

    private void addPagamentoIstanzaNoBolloList(IstanzaDTO istanza, List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaListOld, List<RiscossioneEnteExtendedDTO> riscossioneEnteList) {
        if (riscossioneEnteList != null && !riscossioneEnteList.isEmpty()) {
            //Filtro gli oneri diversi dalle Marche da bollo
            List<RiscossioneEnteExtendedDTO> riscossioneEnteNoMarcaBolloList = riscossioneEnteList.stream()
                    .filter(riscossioneEnte -> !riscossioneEnte.getAdempimentoTipoPagamento().getTipoPagamento().getGruppoPagamento().getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                    .collect(Collectors.toList());
            addPagamentoIstanzaList(istanza, pagamentoIstanzaListOld, riscossioneEnteNoMarcaBolloList);
        }
    }

    private void addPagamentoIstanzaBolloList(IstanzaDTO istanza, List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaListOld, List<RiscossioneEnteExtendedDTO> riscossioneEnteList) {
        if (riscossioneEnteList != null && !riscossioneEnteList.isEmpty()) {
            //Filtro i pagamenti di tipo Marche da bollo
            List<RiscossioneEnteExtendedDTO> riscossioneEnteMarcaBolloList = riscossioneEnteList.stream()
                    .filter(riscossioneEnte -> riscossioneEnte.getAdempimentoTipoPagamento().getTipoPagamento().getGruppoPagamento().getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                    .collect(Collectors.toList());

            if (!riscossioneEnteMarcaBolloList.isEmpty()) {
                if (riscossioneEnteMarcaBolloList.size() > 1) {
                    // Qualora le marche da bollo fossero più di una, selezionare quella che fa capo all'autorità competente definita come principale
                    // nella scriva_r_adempi_competenza
                    riscossioneEnteMarcaBolloList = riscossioneEnteMarcaBolloList.stream()
                            .filter(riscossioneEnte -> riscossioneEnte.getFlgCompetenzaPrincipale().equals(Boolean.TRUE))
                            .collect(Collectors.toList());
                }

                for (RiscossioneEnteExtendedDTO riscossioneEnteMarcaBollo : riscossioneEnteMarcaBolloList) {
                    if (istanza != null && riscossioneEnteMarcaBollo != null && Boolean.FALSE.equals(pagamentoIstanzaExist(istanza, pagamentoIstanzaListOld, riscossioneEnteMarcaBollo))) {
                        //inserimento Marca da bollo
                        PagamentoIstanzaDTO pagamentoIstanza = new PagamentoIstanzaDTO();
                        pagamentoIstanza.setIdIstanza(istanza.getIdIstanza());
                        pagamentoIstanza.setIdRiscossioneEnte(riscossioneEnteMarcaBollo.getIdRiscossioneEnte());
                        pagamentoIstanza.setIdStatoPagamento(1L);
                        pagamentoIstanza.setImportoDovuto(riscossioneEnteMarcaBollo.getAdempimentoTipoPagamento().getImportoPrevisto());
                        pagamentoIstanza.setDataInserimentoPagamento(istanza.getDataInserimentoIstanza() != null ? new Timestamp(istanza.getDataInserimentoIstanza().getTime()) : null); //data inserimento istanza
                        pagamentoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                        Long idPagamentoIstanzaBollo = pagamentoIstanzaDAO.savePagamentoIstanza(pagamentoIstanza);

                        //selezione pagamenti di tipo onere cui associare la Marca da bollo
                        List<String> gruppiPagamento = Collections.singletonList(GruppoPagamentoEnum.ONERE.name());
                        List<PagamentoIstanzaDTO> pagamentoIstanzaOneriAssociatiBolloList = pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanzaEnteCredCodGruppoPagamento(istanza.getIdIstanza(), riscossioneEnteMarcaBollo.getAdempimentoTipoPagamento().getEnteCreditore().getIdEnteCreditore(), gruppiPagamento);
                        List<PagamentoIstanzaDTO> pagamentoIstanzaOneriDaAssociareList = new ArrayList<>();

                        if (pagamentoIstanzaOneriAssociatiBolloList != null && pagamentoIstanzaOneriAssociatiBolloList.size() > 1) {
                            //Se esistono più oneri di pagamento con codice versamento differenti selezionare il record con id_pagamento_istanza minore
                            Optional<PagamentoIstanzaDTO> pagamentoOnereDaAssociare = pagamentoIstanzaOneriAssociatiBolloList.stream()
                                    .min(Comparator.comparing(PagamentoIstanzaDTO::getIdPagamentoIstanza));
                            pagamentoOnereDaAssociare.ifPresent(pagamentoIstanzaOneriDaAssociareList::add);
                        } else {
                            pagamentoIstanzaOneriDaAssociareList = pagamentoIstanzaOneriAssociatiBolloList;
                        }

                        if (CollectionUtils.isNotEmpty(pagamentoIstanzaOneriDaAssociareList)) {
                            //Aggiungere le componenti dell'onere (sanzioni ed interessi) al gruppo a cui associare il bollo
                            gruppiPagamento = Arrays.asList(GruppoPagamentoEnum.INTERESSE.name(), GruppoPagamentoEnum.SANZIONE.name());
                            List<PagamentoIstanzaDTO> pagamentoIstanzaInteressiOneriAssociatiBolloList = pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanzaEnteCredCodGruppoPagamento(istanza.getIdIstanza(), riscossioneEnteMarcaBollo.getAdempimentoTipoPagamento().getEnteCreditore().getIdEnteCreditore(), gruppiPagamento);
                            if (pagamentoIstanzaInteressiOneriAssociatiBolloList != null && !pagamentoIstanzaInteressiOneriAssociatiBolloList.isEmpty()) {
                                pagamentoIstanzaOneriDaAssociareList.addAll(pagamentoIstanzaInteressiOneriAssociatiBolloList);
                            }
                        }

                        associaOneriMarcaBollo(pagamentoIstanzaOneriDaAssociareList, idPagamentoIstanzaBollo);
                    }
                }
            }
        }
    }

    private void associaOneriMarcaBollo(List<PagamentoIstanzaDTO> pagamentoIstanzaOneriList, Long idPagamentoIstanzaBollo) {
        if (pagamentoIstanzaOneriList != null && !pagamentoIstanzaOneriList.isEmpty()) {
            for (PagamentoIstanzaDTO pagamentoIstanzaOneri : pagamentoIstanzaOneriList) {
                pagamentoIstanzaOneri.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                pagamentoIstanzaOneri.setIdOnerePadre(idPagamentoIstanzaBollo);
                Integer numRecordUpdated = pagamentoIstanzaDAO.updatePagamentoIstanza(pagamentoIstanzaOneri);
            }
        }
    }

    private void addPagamentoIstanzaList(IstanzaDTO istanza, List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaListOld, List<RiscossioneEnteExtendedDTO> riscossioneEnteList) {
        if (istanza != null && riscossioneEnteList != null && !riscossioneEnteList.isEmpty()) {
            for (RiscossioneEnteExtendedDTO riscossioneEnteNoMarcaBollo : riscossioneEnteList) {
                if (!pagamentoIstanzaExist(istanza, pagamentoIstanzaListOld, riscossioneEnteNoMarcaBollo)) {
                    PagamentoIstanzaDTO pagamentoIstanza = new PagamentoIstanzaDTO();
                    pagamentoIstanza.setIdIstanza(istanza.getIdIstanza());
                    pagamentoIstanza.setIdRiscossioneEnte(riscossioneEnteNoMarcaBollo != null ? riscossioneEnteNoMarcaBollo.getIdRiscossioneEnte() : null);
                    pagamentoIstanza.setIdStatoPagamento(1L);
                    pagamentoIstanza.setImportoDovuto(riscossioneEnteNoMarcaBollo != null ? riscossioneEnteNoMarcaBollo.getAdempimentoTipoPagamento().getImportoPrevisto() : null);
                    pagamentoIstanza.setDataInserimentoPagamento(istanza.getDataInserimentoIstanza() != null ? new Timestamp(istanza.getDataInserimentoIstanza().getTime()) : null); //data inserimento istanza
                    pagamentoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                    Long idPagamentoIstanza = pagamentoIstanzaDAO.savePagamentoIstanza(pagamentoIstanza);
                }
            }
        }
    }

    private Boolean pagamentoIstanzaExist(IstanzaDTO istanza, List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaListOld, RiscossioneEnteExtendedDTO riscossioneEnte) {
        Boolean result = Boolean.FALSE;
        if (istanza != null && riscossioneEnte != null && pagamentoIstanzaListOld != null && !pagamentoIstanzaListOld.isEmpty()) {
            List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaExist = pagamentoIstanzaListOld.stream()
                    .filter(pagamentoIstanzaOld -> pagamentoIstanzaOld.getIdIstanza().equals(istanza.getIdIstanza()) &&
                            pagamentoIstanzaOld.getRiscossioneEnte().getIdRiscossioneEnte().equals(riscossioneEnte.getIdRiscossioneEnte()) &&
                            pagamentoIstanzaOld.getRiscossioneEnte().getAdempimentoTipoPagamento().getCodiceVersamento().equals(riscossioneEnte.getAdempimentoTipoPagamento().getCodiceVersamento()
                                    //&&   pagamentoIstanzaOld.getStatoPagamento()
                            )
                    )
                    .collect(Collectors.toList());
            result = !pagamentoIstanzaExist.isEmpty();
        }
        return result;
    }

    private Boolean tentativoPagamentoExist(Long idPagamentoIstanza) {
        Boolean result = Boolean.FALSE;
        if (idPagamentoIstanza != null) {
            // tentativi di pagamento
            List<TentativoPagamentoExtendedDTO> tentativoPagamentoList = tentativoPagamentoDAO.loadTentativoPagamentoByIdPagamentoIstanza(idPagamentoIstanza);

            // tentativi dettaglio pagamento
            List<TentativoDettaglioExtendedDTO> tentativoDettaglioList = tentativoDettaglioDAO.loadTentativoDettaglioByIdPagamentoIstanza(idPagamentoIstanza);

            // tentativi dettaglio pagamento con IUV valorizzato
            List<TentativoDettaglioExtendedDTO> tentativoDettaglioWithIUVList = tentativoDettaglioList != null && !tentativoDettaglioList.isEmpty() ?
                    tentativoDettaglioList.stream()
                            .filter(tentativoDettaglio -> StringUtils.isNotBlank(tentativoDettaglio.getIuvTentativoPagamento()))
                            .collect(Collectors.toList())
                    : new ArrayList<>();

            // tentativi di pagamento non falliti
            List<TentativoPagamentoExtendedDTO> tentativoPagamentoNoFallitoList = tentativoPagamentoList != null && !tentativoPagamentoList.isEmpty() ?
                    tentativoPagamentoList.stream()
                            .filter(tentativoPagamento -> !tentativoPagamento.getStatoTentativoPagamento().getCodiceStatoTentativoPagamento().equalsIgnoreCase(StatoTentativoPagamentoEnum.FALLITO.getDescrizione()))
                            .collect(Collectors.toList())
                    : new ArrayList<>();

            // se è già presente un tentativo di pagamento non fallito O un tentativo dettaglio pagamento con IUV valorizzato
            result = !tentativoPagamentoNoFallitoList.isEmpty() || !tentativoDettaglioWithIUVList.isEmpty();
        }
        return result;
    }

    private String setEsitoRichiestaPagamentoBollo(TentativoPagamentoExtendedDTO tentativoPagamentoExtended, PPayPagamentoMarcaBolloOutDTO pPayPagamentoMarcaBolloOutput) {
        logBeginInfo(className, "tentativoPagamento :\n" + tentativoPagamentoExtended + "\npPayPagamentoMarcaBolloOutput :\n" + pPayPagamentoMarcaBolloOutput);
        String url = null;
        if (tentativoPagamentoExtended != null && pPayPagamentoMarcaBolloOutput != null) {
            String codiceEsito = pPayPagamentoMarcaBolloOutput.getCodiceEsito();
            if (PPayCodiceEsitoEnum.SUCCESSO.getCodiceEsito().equalsIgnoreCase(codiceEsito)) {
                updateTentativoDettaglioIuv(tentativoPagamentoExtended.getTentativoDettaglio(), pPayPagamentoMarcaBolloOutput);
                TentativoPagamentoDTO tentativoPagamento = tentativoPagamentoExtended.getDTO();
                tentativoPagamento.setIdStatoTentativoPagamento(StatoTentativoPagamentoEnum.SUCCESSO.getIdStatoTentativoPagamento()); //set tentativo successo
                tentativoPagamento.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                tentativoPagamentoDAO.updateTentativoPagamento(tentativoPagamento);
                url = pPayPagamentoMarcaBolloOutput.getUrlWisp();
            } else if (PPayCodiceEsitoEnum.FALLITO.getCodiceEsito().equalsIgnoreCase(codiceEsito)) {
                TentativoPagamentoDTO tentativoPagamento = tentativoPagamentoExtended.getDTO();
                tentativoPagamento.setIdStatoTentativoPagamento(StatoTentativoPagamentoEnum.FALLITO.getIdStatoTentativoPagamento()); //set tentativo fallito
                tentativoPagamento.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                tentativoPagamentoDAO.updateTentativoPagamento(tentativoPagamento);
                sendMailAssistenza(pPayPagamentoMarcaBolloOutput.toString());
            }
        }
        logEnd(className);
        return url;
    }

    private void setEsitoResultPagamentoBollo(TentativoPagamentoExtendedDTO tentativoPagamentoExtended, PPayResultDTO pPayResult) {
        logBeginInfo(className, "tentativoPagamento :\n" + tentativoPagamentoExtended + "\npPayResult :\n" + pPayResult);
        if (tentativoPagamentoExtended != null && pPayResult != null) {
            String descEsito = pPayResult.getDescEsito();
            List<TentativoDettaglioExtendedDTO> tentativoDettaglioList = tentativoPagamentoExtended.getTentativoDettaglio();
            Long idStatoPagamento = 0L;

            if (PPayCodiceEsitoEnum.SUCCESSO.getDescrizione().equalsIgnoreCase(descEsito)) {
                idStatoPagamento = StatoPagamentoEnum.ATTESA_RICEVUTA.getIdStatoPagamento();
            } else if (PPayCodiceEsitoEnum.FALLITO.getDescrizione().equalsIgnoreCase(descEsito)) {
                idStatoPagamento = StatoPagamentoEnum.FALLITO.getIdStatoPagamento();
            } else if (PPayCodiceEsitoEnum.ANNULLATO.getDescrizione().equalsIgnoreCase(descEsito)) {
                idStatoPagamento = StatoPagamentoEnum.ANNULLATO_UTENTE.getIdStatoPagamento();
            }

            Map<Long, Long> idPagamentoOldNewMap = new HashMap<>();
            List<PagamentoIstanzaDTO> pagamentoIstanzaNewList = new ArrayList<>();

            if (PPayCodiceEsitoEnum.SUCCESSO.getDescrizione().equalsIgnoreCase(descEsito) || PPayCodiceEsitoEnum.FALLITO.getDescrizione().equalsIgnoreCase(descEsito)) {
                for (TentativoDettaglioExtendedDTO tentativoDettaglio : tentativoDettaglioList) {
                    PagamentoIstanzaDTO pagamentoIstanza = tentativoDettaglio.getPagamentoIstanza().getDTO();
                    pagamentoIstanza.setIdStatoPagamento(idStatoPagamento);
                    pagamentoIstanza.setGestAttoreUpd("PPAY_RESULT");
                    pagamentoIstanzaDAO.updatePagamentoIstanza(pagamentoIstanza);

                    if (PPayCodiceEsitoEnum.FALLITO.getDescrizione().equalsIgnoreCase(descEsito)) {
                        // In caso di fallimento inserire una nuova pagamento_istanza copia della precedente
                        Long idPagamentoIstanzaOld = pagamentoIstanza.getIdPagamentoIstanza();
                        pagamentoIstanza.setIdStatoPagamento(StatoPagamentoEnum.DA_EFFETTUARE.getIdStatoPagamento());
                        pagamentoIstanza.setGestAttoreIns("PPAY_RESULT");
                        Long idPagamentoIstanzaNew = pagamentoIstanzaDAO.savePagamentoIstanza(pagamentoIstanza);
                        pagamentoIstanza.setIdPagamentoIstanza(idPagamentoIstanzaNew);
                        pagamentoIstanzaNewList.add(pagamentoIstanza);
                        idPagamentoOldNewMap.put(idPagamentoIstanzaOld, idPagamentoIstanzaNew);
                    }
                }

                if (PPayCodiceEsitoEnum.FALLITO.getDescrizione().equalsIgnoreCase(descEsito)) {
                    // In caso di fallimento aggiornamento oneri padre con i nuovi id
                    for (PagamentoIstanzaDTO pagamentoIstanzaNew : pagamentoIstanzaNewList) {
                        pagamentoIstanzaNew.setIdOnerePadre(idPagamentoOldNewMap.get(pagamentoIstanzaNew.getIdOnerePadre())); // verificare
                        pagamentoIstanzaDAO.updatePagamentoIstanza(pagamentoIstanzaNew);
                    }
                }
            }
        }
    }

    private void updateTentativoDettaglioIuv(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList, PPayPagamentoMarcaBolloOutDTO pPayPagamentoMarcaBolloOutput) {
        if (tentativoDettaglioList != null && !tentativoDettaglioList.isEmpty() && pPayPagamentoMarcaBolloOutput != null) {
            String iuvDocumento = pPayPagamentoMarcaBolloOutput.getIuvDocumento();
            List<String> elencoIuvMarcaBollo = pPayPagamentoMarcaBolloOutput.getElencoIuvMarcaBollo();
            String iuvMarcaBollo = elencoIuvMarcaBollo != null && !elencoIuvMarcaBollo.isEmpty() ? elencoIuvMarcaBollo.get(0) : null;
            updateTentativoDettaglioIuvDoc(tentativoDettaglioList, iuvDocumento);
            updateTentativoDettaglioIuvMarcaBollo(tentativoDettaglioList, iuvMarcaBollo);
        }
    }

    private void updateTentativoDettaglioIuvDoc(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList, String iuv) {
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioNoBolloList = tentativoDettaglioList != null && !tentativoDettaglioList.isEmpty() ? getTentativoDettaglioNoBolloList(tentativoDettaglioList) : null;
        if (tentativoDettaglioNoBolloList != null && !tentativoDettaglioNoBolloList.isEmpty()) {
            for (TentativoDettaglioExtendedDTO tentativoDettaglio : tentativoDettaglioNoBolloList) {
                tentativoDettaglio.setIuvTentativoPagamento(iuv);
                tentativoDettaglio.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                tentativoDettaglioDAO.updateTentativoDettaglio(tentativoDettaglio.getDTO());
            }
        }
    }

    private void updateTentativoDettaglioIuvMarcaBollo(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList, String iuv) {
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioBolloList = tentativoDettaglioList != null && !tentativoDettaglioList.isEmpty() ? getTentativoDettaglioBolloList(tentativoDettaglioList) : null;
        if (tentativoDettaglioBolloList != null && !tentativoDettaglioBolloList.isEmpty()) {
            for (TentativoDettaglioExtendedDTO tentativoDettaglio : tentativoDettaglioBolloList) {
                tentativoDettaglio.setIuvTentativoPagamento(iuv);
                tentativoDettaglio.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                tentativoDettaglioDAO.updateTentativoDettaglio(tentativoDettaglio.getDTO());
            }
        }
    }

    private List<TentativoDettaglioExtendedDTO> getTentativoDettaglioBolloList(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList) {
        return tentativoDettaglioList.stream()
                .filter(tentativoDett -> tentativoDett.getPagamentoIstanza()
                        .getRiscossioneEnte()
                        .getAdempimentoTipoPagamento()
                        .getTipoPagamento()
                        .getGruppoPagamento()
                        .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                .collect(Collectors.toList());
    }

    private List<TentativoDettaglioExtendedDTO> getTentativoDettaglioNoBolloList(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList) {
        return tentativoDettaglioList.stream()
                .filter(tentativoDett -> !tentativoDett.getPagamentoIstanza()
                        .getRiscossioneEnte()
                        .getAdempimentoTipoPagamento()
                        .getTipoPagamento()
                        .getGruppoPagamento()
                        .getCodiceGruppoPagamento().equalsIgnoreCase(GruppoPagamentoEnum.MARCA_BOLLO.name()))
                .collect(Collectors.toList());
    }

    private List<TentativoDettaglioExtendedDTO> getTentativoDettaglioWithIuvList(List<TentativoDettaglioExtendedDTO> tentativoDettaglioList) {
        return tentativoDettaglioList.stream()
                .filter(tentativoDett -> tentativoDett.getIuvTentativoPagamento() != null)
                .collect(Collectors.toList());
    }

    private PPayPagamentoMarcaBolloInDTO getPPayPagamentoMarcaBolloInput(CompilanteDTO compilante, TentativoPagamentoExtendedDTO tentativoPagamento, Map<String, String> parametri) {
        String provincia = parametri.get(KEY_PROVINCIA_MARCA_BOLLO);
        BigDecimal importoMarcaBollo = new BigDecimal(parametri.get(KEY_IMPORTO_MARCA_BOLLO));
        BigDecimal importoTotaleComponenti = new BigDecimal(0);
        int marcaFittizzia = tentativoPagamento.getTipoBollo().equalsIgnoreCase("XX") ? 1 : 0;

        // Recupero IUV associata
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioBolloList = getTentativoDettaglioBolloList(tentativoPagamento.getTentativoDettaglio());
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioWithIuvList = tentativoDettaglioBolloList != null && !tentativoDettaglioBolloList.isEmpty() ? getTentativoDettaglioWithIuvList(tentativoDettaglioBolloList) : null;
        String iuvAssociata = tentativoDettaglioWithIuvList != null && !tentativoDettaglioWithIuvList.isEmpty() ? tentativoDettaglioWithIuvList.get(0).getIuvTentativoPagamento() : null;

        // Recupero totale importo da pagare
        List<TentativoDettaglioExtendedDTO> tentativoDettaglioNoBolloList = getTentativoDettaglioNoBolloList(tentativoPagamento.getTentativoDettaglio());

        if (!tentativoDettaglioNoBolloList.isEmpty()) {
            importoTotaleComponenti = new BigDecimal(0);
            for (TentativoDettaglioExtendedDTO tentativoDettaglio : tentativoDettaglioNoBolloList) {
                importoTotaleComponenti = importoTotaleComponenti.add(tentativoDettaglio.getPagamentoIstanza().getImportoDovuto());
            }
        }
        return PiemontePayFactory.createPPayPagamentoMarcaBolloInput(compilante, tentativoPagamento, provincia, importoMarcaBollo, marcaFittizzia, iuvAssociata, importoTotaleComponenti);
    }

    private void deletePagamentoIstanzaList(IstanzaDTO istanza, List<PagamentoIstanzaExtendedDTO> pagamentoIstanzaListOld, List<RiscossioneEnteExtendedDTO> riscossioneEnteList) {

    }

    private void sendMailAssistenza(String corpoMail) {
        logBegin(className);
        List<String> keys = new ArrayList<>();
        keys.add(KEY_MAIL_SERVER_HOST);
        keys.add(KEY_MAIL_SERVER_PORT);
        keys.add(KEY_MAIL_SENDER_ADDRESS);
        keys.add(KEY_MAIL_ASSIST_ADDRESS);
        keys.add(KEY_MAIL_SERVER_USERNAME);
        keys.add(KEY_MAIL_SERVER_PASSWORD);

        Map<String, String> configs = configurazioneDAO.loadConfigByKeyList(keys);
        if (null == configs || configs.isEmpty()) {
            logError(className, "configs null");
            return;
        } else if (!configs.containsKey(KEY_MAIL_SERVER_HOST)) {
            logError(className, "config " + KEY_MAIL_SERVER_HOST + " assente");
        } else if (!configs.containsKey(KEY_MAIL_SERVER_PORT)) {
            logError(className, "config " + KEY_MAIL_SERVER_PORT + " assente");
        } else if (!configs.containsKey(KEY_MAIL_SENDER_ADDRESS)) {
            logError(className, "config " + KEY_MAIL_SENDER_ADDRESS + " assente");
        } else if (!configs.containsKey(KEY_MAIL_ASSIST_ADDRESS)) {
            logError(className, "config " + KEY_MAIL_ASSIST_ADDRESS + " assente");
        }else if (!configs.containsKey(KEY_MAIL_SERVER_USERNAME)) {
            logError(className, "config " + KEY_MAIL_SERVER_USERNAME + " assente");
        }else if (!configs.containsKey(KEY_MAIL_SERVER_PASSWORD)) {
            logError(className, "config " + KEY_MAIL_SERVER_PASSWORD + " assente");
        }



        EmailDTO email = new EmailDTO();
        email.setHost(configs.get(KEY_MAIL_SERVER_HOST));
        email.setPort(configs.get(KEY_MAIL_SERVER_PORT));
        email.setUserID(configs.get(KEY_MAIL_SERVER_USERNAME));
        email.setPassword(configs.get(KEY_MAIL_SERVER_PASSWORD));
        email.setMittente(configs.get(KEY_MAIL_SENDER_ADDRESS));


        List<String> destinatari = new ArrayList<>();
        destinatari.add(configs.get(KEY_MAIL_ASSIST_ADDRESS));
        email.setDestinatari(destinatari);
        email.setOggetto("Pagamenti - Errore chiamata pagamento Marca Bollo");
        email.setCorpo(corpoMail);

        try {
            MailManager.sendMail(email);
        } catch (Exception e) {
            logError(className, "corpoMail :\n" + corpoMail + "\n" + e);
        }
    }

    private int updateStatoSintesiPagamentoIstanza(Long idIstanza, AttoreScriva attoreScriva) {
        logBeginInfo(className, idIstanza);
        int result = 0;
        StatoSintesiPagamentoEnum statoSintesiPagamento = pagamentiService.getStatoSintesiPagamentoByIdIstanza(idIstanza, attoreScriva.getComponente());
        if (statoSintesiPagamento != null) {
            result = pagamentiService.setStatoSintesiPagamento(idIstanza, statoSintesiPagamento, attoreScriva);
        }
        return result;
    }
}
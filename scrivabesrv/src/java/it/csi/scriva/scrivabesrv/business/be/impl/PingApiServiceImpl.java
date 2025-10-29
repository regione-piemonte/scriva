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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;
import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.PingApi;
import it.csi.scriva.scrivabesrv.business.be.exception.DAOException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.anagamb.AnagambServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.cosmo.CosmoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Property;
import it.csi.scriva.scrivabesrv.business.be.helper.loccsi.LOCCSIServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.ParkApiServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.parkapi.areeprotette.AreaProtettaFiltriEstesi;
import it.csi.scriva.scrivabesrv.business.be.helper.vincoloidrogeologico.VincoloIdrogeologicoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoPagamentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigJsonDataDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.DynamicSqlDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.HelpDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaTemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PagamentoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PingDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RiscossioneEnteDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.CosmoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.business.be.service.PagamentiService;
import it.csi.scriva.scrivabesrv.business.be.service.TemplateService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OrganoTecnicoDTO;
import it.csi.scriva.scrivabesrv.dto.Ping;
import it.csi.scriva.scrivabesrv.dto.ProcedimentoStataleDTO;
import it.csi.scriva.scrivabesrv.dto.UrlDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.StatoSintesiPagamentoEnum;
import it.csi.scriva.scrivabesrv.filter.IrideIdAdapterFilter;
import it.csi.scriva.scrivabesrv.util.CalendarUtils;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.mail.EmailDTO;
import it.csi.scriva.scrivabesrv.util.mail.MailManager;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import it.csi.scriva.scrivabesrv.util.manager.MandatoryInfoIstanzaManager;
import it.csi.scriva.scrivabesrv.util.notification.NotificationManager;
import it.csi.scriva.scrivabesrv.util.notification.service.TemplateNotificationService;
import it.csi.scriva.scrivabesrv.util.placeholder.PlaceHolderUtil;
import it.csi.scriva.scrivabesrv.util.placeholder.enumeration.PlaceHolderEnum;
import it.csi.scriva.scrivabesrv.util.templating.TemplateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * The type Ping api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PingApiServiceImpl extends BaseApiServiceImpl implements PingApi {

    /**
     * The constant CONTENT_DISPOSITION.
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    private final String className = this.getClass().getSimpleName();

    /**
     * The Anagamb service helper.
     */
    @Autowired
    AnagambServiceHelper anagambServiceHelper;

    /**
     * The Allegati manager.
     */
    @Autowired
    AllegatiManager allegatiManager;

    /**
     * The Json data manager.
     */
    @Autowired
    JsonDataManager jsonDataManager;

    /**
     * The Cosmo service helper.
     */
    @Autowired
    CosmoServiceHelper cosmoServiceHelper;

    /**
     * The Mandatory info istanza manager.
     */
    @Autowired
    MandatoryInfoIstanzaManager mandatoryInfoIstanzaManager;

    /**
     * The Park api service helper.
     */
    @Autowired
    ParkApiServiceHelper parkApiServiceHelper;

    /**
     * The Allegati service.
     */
    @Autowired
    AllegatiService allegatiService;

    /**
     * The Template util.
     */
    @Autowired
    TemplateUtil templateUtil;

    /**
     * The Cosmo service.
     */
    @Autowired
    CosmoService cosmoService;

    /**
     * The Istanza evento service.
     */
    @Autowired
    IstanzaEventoService istanzaEventoService;

    /**
     * The Pagamenti service.
     */
    @Autowired
    PagamentiService pagamentiService;

    /**
     * The Template service.
     */
    @Autowired
    TemplateService templateService;


    /**
     * The Adempimento config dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Dynamic sql dao.
     */
    @Autowired
    DynamicSqlDAO dynamicSqlDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Istanza template quadro dao.
     */
    @Autowired
    IstanzaTemplateQuadroDAO istanzaTemplateQuadroDAO;

    /**
     * The Help dao.
     */
    @Autowired
    HelpDAO helpDAO;


    /**
     * The Config json data dao.
     */
    @Autowired
    ConfigJsonDataDAO configJsonDataDAO;

    /**
     * The Riscossione ente dao.
     */
    @Autowired
    RiscossioneEnteDAO riscossioneEnteDAO;

    /**
     * The Adempimento tipo pagamento dao.
     */
    @Autowired
    AdempimentoTipoPagamentoDAO adempimentoTipoPagamentoDAO;

    /**
     * The Pagamento istanza dao.
     */
    @Autowired
    PagamentoIstanzaDAO pagamentoIstanzaDAO;

    @Autowired
    private ParkApiServiceHelper territorioServiceHelper;

    @Autowired
    private LOCCSIServiceHelper loccsiServiceHelper;

    @Autowired
    private VincoloIdrogeologicoServiceHelper vincoloIdrogeologicoServiceHelper;

    @Autowired
    private TemplateNotificationService templateNotificationService;

    @Autowired
    private NotificationManager notificationManager;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response ping(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        PingDAO pingDAO = (PingDAO) SpringApplicationContextHelper.getBean("pingDAO");
        List<Ping> pings = new ArrayList<>();
        try {
            Ping ping = new Ping();
            pingDAO.pingDB();
            ping.setLabel("Database");
            ping.setStatus("OK");
            pings.add(ping);
        } catch (DAOException e) {
            Ping ping = new Ping();
            ping.setLabel("Database");
            ping.setStatus("KO");
            pings.add(ping);
        }
        logEnd(className);
        return Response.ok(pings).build();
    }

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response testAnagamb(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(anagambServiceHelper.getSediOperative(null, null), className);
    }

    @Override
    public Response testGetTipoNotificaEvento(String codTipoEvento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        String result;
        String testo = "Buongiorno,\n" +
                "per il progetto ubicato nel/nei comuni di {PH_OGG_IST_DENOM_COMUNE}\n" +
                "<se:PH_OGG_IST_SITO_DES_SITO_2000>e sono presenti i seguenti siti Rete Natura 2000:</se:PH_OGG_IST_SITO_DES_SITO_2000>\n" +
                "{PH_OGG_IST_SITO_DES_SITO_2000}";
        String placeholder = "{PH_OGG_IST_SITO_DES_SITO_2000}";
        String phValue = StringUtils.EMPTY;
        //String phValue = "Sito rn2000";

        testo = "Buongiorno,\n" +
                "la presente per comunicarLe che l'istanza {PH_IST_COD_ISTANZA} da lei presentata in data {PH_IST_DATA_ PRESENTATA_ISTANZA} è stata inoltrata alla/e autorità competenti interessa-ta/e: \n" +
                "{PH_ACP_DES_COMPETENZA},\n";
        placeholder = "{PH_ACP_DES_COMPETENZA}";
        phValue = "Compet1,Compet2,Compet3";

        PlaceHolderEnum placeHolderEnum = PlaceHolderEnum.findByDescr(placeholder.toUpperCase());

        String phKey = placeHolderEnum != null ? placeHolderEnum.name() : StringUtils.EMPTY;
        String tagApertura = "<se:" + phKey + ">";
        String tagChiusura = "</se:" + phKey + ">";
        if (StringUtils.isNotBlank(phValue)) {
            int phPos = testo.indexOf(placeholder);
            int arrSeparator = phPos >= 0 ? phPos + placeholder.length() : phPos;
            String separator = ", ";
            if (arrSeparator >= 0) {
                int incr = (arrSeparator + 2) > testo.length() ? 1 : 2;
                separator = testo.substring(arrSeparator, arrSeparator + incr);
            }
            phValue = phValue.replace(",", separator);

            result = testo
                    .replace(tagApertura, StringUtils.EMPTY)
                    .replace(tagChiusura, StringUtils.EMPTY)
                    .replace(placeholder, phValue);


        } else {
            int beginIndex = testo.indexOf(tagApertura);
            int endIndex = testo.indexOf(tagChiusura) + tagChiusura.length();
            String daCanc = beginIndex >= 0 && beginIndex < endIndex ?
                    testo.substring(beginIndex, endIndex) :
                    StringUtils.EMPTY;
            result = testo
                    .replace(daCanc, StringUtils.EMPTY)
                    .replace(placeholder, StringUtils.EMPTY);
        }
        System.out.println(result);


        return getResponseList(templateNotificationService.getTipoNotificaEvento(codTipoEvento), className);
    }


    /* SOLO PER TEST INVIO MAIL - POI DA RIMUOVERE */

    private static final String KEY_MAIL_SERVER_HOST = "SCRIVA_SERVER_POSTA_HOST";
    private static final String KEY_MAIL_SERVER_PORT = "SCRIVA_SERVER_POSTA_PORTA";
    private static final String MAIL_SENDER_ADDRESS = "assistenza.sira@regione.piemonte.it";

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    /**
     * @param emailAddr       email
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response testEmail(String emailAddr, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, emailAddr);
        try {

            return sendMultipartMail(emailAddr);
/*
            List<String> keys = new ArrayList<>();
            keys.add(KEY_MAIL_SERVER_HOST);
            keys.add(KEY_MAIL_SERVER_PORT);

            Map<String, String> configs = configurazioneDAO.loadConfigByKeyList(keys);
            if (null == configs) {
                ErrorDTO error = getErrorManager().getError("500", "", "Errore nel recupero dei parametri del mail server", null, null);
                return getResponseError(className, error);
            } else if (configs.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero dei parametri del mail server;  causa: parametri con chiavi [" + KEY_MAIL_SERVER_HOST + ", " + KEY_MAIL_SERVER_PORT + "] non trovati", null, null);
                return getResponseError(className, error);
            } else if (!configs.containsKey(KEY_MAIL_SERVER_HOST)) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero dell'host del mail server;  causa: parametro con chiave [" + KEY_MAIL_SERVER_HOST + "] non trovato", null, null);
                return getResponseError(className, error);
            } else if (!configs.containsKey(KEY_MAIL_SERVER_PORT)) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero della porta del mail server;  causa: parametro con chiave [" + KEY_MAIL_SERVER_PORT + "] non trovato", null, null);
                return getResponseError(className, error);
            }

            EmailDTO email = new EmailDTO();
            email.setHost(configs.get(KEY_MAIL_SERVER_HOST));
            email.setPort(configs.get(KEY_MAIL_SERVER_PORT));

//            email=getEmailDtoForTest(); //this is only to set parameters for tests

            List<String> destinatari = new ArrayList<>();
            destinatari.add(emailAddr);
            email.setDestinatari(destinatari);
            email.setMittente(MAIL_SENDER_ADDRESS);
            email.setOggetto("Oggetto : TEST INVIO MAIL");
            email.setCorpo("CORPO DELLA MAIL : TEST INVIO MAIL");

            return getResponse(email);

 */
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e).build();
        }


    }

    private Response sendMultipartMail(String emailAddr) {
        logBeginInfo(className, emailAddr);
        try {
            List<String> keys = new ArrayList<>();
            keys.add(Constants.CONF_KEY_SERVER_POSTA_HOST);
            keys.add(Constants.CONF_KEY_SERVER_POSTA_PORTA);
            keys.add(Constants.CONF_KEY_SERVER_POSTA_MITTENTE);

            Map<String, String> configs = configurazioneDAO.loadConfigByKeyList(keys);
            if (null == configs) {
                ErrorDTO error = getErrorManager().getError("500", "", "Errore nel recupero dei parametri del mail server", null, null);
                return getResponseError(className, error);
            } else if (configs.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero dei parametri del mail server;  causa: parametri con chiavi [" + KEY_MAIL_SERVER_HOST + ", " + KEY_MAIL_SERVER_PORT + "] non trovati", null, null);
                return getResponseError(className, error);
            } else if (!configs.containsKey(Constants.CONF_KEY_SERVER_POSTA_HOST) ||
                    !configs.containsKey(Constants.CONF_KEY_SERVER_POSTA_PORTA) ||
                    !configs.containsKey(Constants.CONF_KEY_SERVER_POSTA_MITTENTE)
            ) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero dei parametri di configurazione.", null, null);
                return getResponseError(className, error);
            }

            // Prepare system properties

            //Properties props = System.getProperties();
            //props.setProperty("mail.smtp.ssl.enable", "true");
            //props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //props.setProperty("mail.smtp.ssl.socketFactory.fallback", "false");

            MultiPartEmail email = new MultiPartEmail();
            email.setDebug(Boolean.TRUE);
            email.setHostName(configs.get(Constants.CONF_KEY_SERVER_POSTA_HOST));
            email.setSmtpPort(Integer.parseInt(configs.get(Constants.CONF_KEY_SERVER_POSTA_PORTA)));
            email.setSSL(Boolean.FALSE);
            email.setFrom(configs.get(Constants.CONF_KEY_SERVER_POSTA_MITTENTE));
            email.setMsg("Body del messaggio");
            email.setSubject("Oggetto del messaggio");
            email.addTo(emailAddr);
            email.send();
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e).build();
        }
        return Response.noContent().build();
    }

    /**
     * Test pec response.
     *
     * @param emailAddr       the email
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testPec(String emailAddr, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, emailAddr);
        try {
            List<String> keys = new ArrayList<>();
            keys.add(Constants.CONF_KEY_SERVER_PEC_HOST);
            keys.add(Constants.CONF_KEY_SERVER_PEC_PORTA);
            keys.add(Constants.CONF_KEY_SERVER_PEC_MITTENTE);
            keys.add(Constants.CONF_KEY_SERVER_PEC_USERNAME);
            keys.add(Constants.CONF_KEY_SERVER_PEC_PASSWORD);

            Map<String, String> configs = configurazioneDAO.loadConfigByKeyList(keys);
            if (null == configs) {
                ErrorDTO error = getErrorManager().getError("500", "", "Errore nel recupero dei parametri del mail server", null, null);
                return getResponseError(className, error);
            } else if (configs.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero dei parametri del mail server;  causa: parametri con chiavi [" + KEY_MAIL_SERVER_HOST + ", " + KEY_MAIL_SERVER_PORT + "] non trovati", null, null);
                return getResponseError(className, error);
            } else if (!configs.containsKey(Constants.CONF_KEY_SERVER_PEC_HOST) ||
                    !configs.containsKey(Constants.CONF_KEY_SERVER_PEC_PORTA) ||
                    !configs.containsKey(Constants.CONF_KEY_SERVER_PEC_MITTENTE) ||
                    !configs.containsKey(Constants.CONF_KEY_SERVER_PEC_USERNAME) ||
                    !configs.containsKey(Constants.CONF_KEY_SERVER_PEC_PASSWORD)
            ) {
                ErrorDTO error = getErrorManager().getError("404", "", "Errore nel recupero dei parametri di configurazione.", null, null);
                return getResponseError(className, error);
            }

            // Prepare system properties

            //Properties props = System.getProperties();
            //props.setProperty("mail.smtp.ssl.enable", "true");
            //props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //props.setProperty("mail.smtp.ssl.socketFactory.fallback", "false");

            MultiPartEmail email = new MultiPartEmail();
            email.setDebug(Boolean.TRUE);
            email.setHostName(configs.get(Constants.CONF_KEY_SERVER_PEC_HOST));
            email.setSmtpPort(Integer.parseInt(configs.get(Constants.CONF_KEY_SERVER_PEC_PORTA)));
            email.setAuthentication(configs.get(Constants.CONF_KEY_SERVER_PEC_USERNAME), configs.get(Constants.CONF_KEY_SERVER_PEC_PASSWORD));
            email.setSSL(Boolean.TRUE);
            email.setFrom(configs.get(Constants.CONF_KEY_SERVER_PEC_MITTENTE));
            email.setMsg("Body del messaggio");
            email.setSubject("Oggetto del messaggio");
            email.addTo(emailAddr);
            email.send();
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e).build();
        }
        return Response.noContent().build();
    }

    private Response getResponse(EmailDTO email) {
        try {
            MailManager.sendMail(email);
            return Response.ok("Email Inviata").build();
        } catch (Exception e) {
            ErrorDTO error = getErrorManager().getError("500", "", "Errore nell'invio della mail di verifica", null, null);
            return getResponseError(className, error);
        }
    }

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response testSPID(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return Response.ok(httpRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR)).build();
    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response compilaDoc(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza);
        if (!istanze.isEmpty()) {
            String json = istanze.get(0).getJsonData();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IXDocReport report;
            try {

                InputStream in = this.getClass().getClassLoader().getResourceAsStream("templates/MODULO_TOTALE_PROVAJSON.docx");
                report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
                IContext context = report.createContext();

                Map<String, Object> mappings = new HashMap<>();
                JSONObject obj = new JSONObject(json);
                mappings.put("ist", obj);

                context.put("d", mappings);

                report.process(context, out);

            } catch (Exception e) {
                logError(className, e);
            }
            return Response.ok(out.toByteArray()).header(CONTENT_DISPOSITION, "attachment; filename=MODULO_TOTALE_PROVAJSON.docx").build();
        } else {
            return Response.ok().status(404).build();
        }
    }

    /**
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response testPDF(Long idIstanza, String nomeFile, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        /*
        try {
            //TemplateUtil.createPDFA();
            //Convert.createPDFA();
            //Convert.createPDFA1();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza);

        if (CollectionUtils.isNotEmpty(istanze)) {


            // per file sul server
            // String absolutePathTemplate = templateService.getTemplatePath(istanze.get(0).getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
            // String filename = StringUtils.isNotBlank(absolutePathTemplate) ? templateService.getTemplateFilename(istanze.get(0), TipologiaAllegatoEnum.MOD_IST.name(), absolutePathTemplate) : null;

            // per test in locale
            String absolutePathTemplate = "C:\\temp\\templates";

            //String filename = "VIA_VAL_MOD_IST.docx";


            String filename = nomeFile+".docx";


            if (StringUtils.isNotBlank(absolutePathTemplate) && StringUtils.isNotBlank(filename)) {
                String pathTemplate = absolutePathTemplate + File.separator + filename;
                try {
                    byte[] out = templateUtil.getCompiledTemplatePDF(idIstanza, pathTemplate);
                    return Response.ok(out, "application/pdf").header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + ".pdf\"").build();
                } catch (Exception e) {
                    logError(className, e);
                }
            }

        }
        return Response.serverError().status(500).build();
    }

    /**
     * Test json istanza response.
     *
     * @param idIstanza       idIstanza
     * @param key             the key
     * @param value           the value
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response testJsonIstanza(Long idIstanza, String key, String value, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws JsonProcessingException {
        logBeginInfo(className, idIstanza);
        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza);
        String jsonFragment = null;
        OrganoTecnicoDTO organoTecnico = null;
        ProcedimentoStataleDTO procedimentoStatale = null;

        try {
            jsonFragment = jsonDataManager.removeObjectFromJson(istanze.get(0).getJsonData(), key, value);
            logDebug(className, jsonFragment);

            procedimentoStatale = key.equalsIgnoreCase("js_procedimento_statale") && StringUtils.isNotBlank(jsonFragment) ? jsonDataManager.getClassFromJsonData(istanze.get(0).getJsonData(), key, new TypeReference<ProcedimentoStataleDTO>() {
            }) : null;
            organoTecnico = key.equalsIgnoreCase("JS_ORGANO_TECNICO") && StringUtils.isNotBlank(jsonFragment) ? jsonDataManager.getClassFromJsonData(istanze.get(0).getJsonData(), key, new TypeReference<OrganoTecnicoDTO>() {
            }) : null;
        } catch (Exception t) {
            logError(className, t);
        }
        String resp = procedimentoStatale != null ? new ObjectMapper().writeValueAsString(procedimentoStatale) : jsonFragment;
        resp = organoTecnico != null ? new ObjectMapper().writeValueAsString(organoTecnico) : resp;
        return Response.ok(resp).build();
    }

    @Override
    @Transactional
    public Response testTransaction(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return Response.noContent().build();
    }

    @Override
    public Response testRiscossioneEnte(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(riscossioneEnteDAO.loadRiscossioneEnteByIdIstanza(idIstanza, attoreScriva.getComponente()), className);
    }

    @Override
    public Response testAdempiTipoPagamento(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(adempimentoTipoPagamentoDAO.loadAdempimentoTipoPagamentoIdIstanza(idIstanza, attoreScriva.getComponente()), className);
    }

    public Response testPagamentoIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(pagamentoIstanzaDAO.loadPagamentoIstanzaByIdIstanza(idIstanza, attoreScriva.getComponente()), className);
    }

    @Override
    public Response testIndexScheduled(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Logger loggerIndex = Logger.getLogger(Constants.LOGGER_NAME + ".index");
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String infoMethod = className + "::" + methodName + " ";
        String created = " - ";
        String checkIndexConfigParam = "CHECK_INDEX";
        String checkIndexStartConfigParam = "CHECK_INDEX_START";
        String checkIndexStopConfigParam = "CHECK_INDEX_STOP";
        String checkIndexUuidConfigParam = "CHECK_INDEX_UUID";
        String checkIndexResultConfigParam = "CHECK_INDEX_RESULT";
        List<String> confKeysIndex = Arrays.asList(checkIndexConfigParam, checkIndexStartConfigParam, checkIndexStopConfigParam, checkIndexUuidConfigParam, checkIndexResultConfigParam);

        Map<String, String> configurazioneList = configurazioneDAO.loadConfigByKeyList(confKeysIndex);
        if (configurazioneList != null && !configurazioneList.isEmpty() &&
                (configurazioneList.containsKey(checkIndexConfigParam) && configurazioneList.getOrDefault(checkIndexConfigParam, null) != null && configurazioneList.getOrDefault(checkIndexConfigParam, null).equalsIgnoreCase("Y")) &&
                (configurazioneList.containsKey(checkIndexStartConfigParam) && configurazioneList.getOrDefault(checkIndexStartConfigParam, null) != null && configurazioneList.getOrDefault(checkIndexConfigParam, null).equalsIgnoreCase("Y")) &&
                (configurazioneList.containsKey(checkIndexStopConfigParam) && configurazioneList.getOrDefault(checkIndexStopConfigParam, null) != null && configurazioneList.getOrDefault(checkIndexConfigParam, null).equalsIgnoreCase("Y")) &&
                (configurazioneList.containsKey(checkIndexUuidConfigParam) && configurazioneList.getOrDefault(checkIndexUuidConfigParam, null) != null)
        ) {
            LocalDateTime dateStart = getFormattedDate((Date) CalendarUtils.convertStringToDate(configurazioneList.getOrDefault(checkIndexStartConfigParam, null), CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
            LocalDateTime dateStop = getFormattedDate((Date) CalendarUtils.convertStringToDate(configurazioneList.getOrDefault(checkIndexStopConfigParam, null), CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
            LocalDateTime now = getFormattedDate((Date) CalendarUtils.now());
            if (now.isAfter(dateStart) && now.isBefore(dateStop)) {
                loggerIndex.debug("************************************************************************************************");
                loggerIndex.debug(infoMethod + "Start at " + getFormattedDate((Date) CalendarUtils.now()));
                loggerIndex.debug(infoMethod + "Calling INDEX at " + getFormattedDate((Date) CalendarUtils.now()));
                Node indexNode = allegatiManager.getMetadataIndexByUuid(configurazioneList.getOrDefault(checkIndexUuidConfigParam, null));
                if (indexNode == null) {
                    loggerIndex.debug(infoMethod + " Index node NULL at " + getFormattedDate((Date) CalendarUtils.now()));
                } else {
                    List<Property> properties = indexNode.getProperties();
                    created = getPropertyValue(properties, "cm:created");
                    loggerIndex.debug(infoMethod + " Index cm:created value : [" + created + "]");
                    String[] createdSplits = StringUtils.isNotBlank(created) ? created.split("\\.") : null;
                    if (createdSplits != null && createdSplits.length > 0) {
                        created = createdSplits[0].replace("T", " ");
                        loggerIndex.debug(infoMethod + " Index cm:created value splitted: [" + created + "]");
                        String expectedResult = configurazioneList.getOrDefault(checkIndexResultConfigParam, null);
                        if (StringUtils.isNotBlank(expectedResult) && !created.equalsIgnoreCase(expectedResult.trim())) {
                            loggerIndex.debug(infoMethod + " Index cm:created ERROR value splitted: [" + created + "] - expected result: [" + expectedResult + "]");
                        }
                    }
                }
                loggerIndex.debug(infoMethod + "End at " + getFormattedDate((Date) CalendarUtils.now()));
                loggerIndex.debug("************************************************************************************************");
            }
        }
        return Response.ok(created).build();
    }

    /**
     * Test set stato pagamento istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testSetStatoPagamentoIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }

        List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanze();
        for (IstanzaExtendedDTO istanza : istanze) {
            idIstanza = istanza.getIdIstanza();
            StatoSintesiPagamentoEnum statoSintesiPagamento = pagamentiService.getStatoSintesiPagamentoByIdIstanza(idIstanza, attoreScriva.getComponente());
            if (statoSintesiPagamento != null) {
                pagamentiService.setStatoSintesiPagamento(idIstanza, statoSintesiPagamento, attoreScriva);
            }
        }
        logEnd(className);
        return Response.ok().build();
    }

    /**
     * Test trace evento istanza response.
     *
     * @param idIstanza       the id istanza
     * @param codEvento       the cod evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testTraceEventoIstanza(Long idIstanza, String codEvento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - codEvento : [" + codEvento + "]");

        // Verifica permessi su istanza
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }

        try {
            return getResponseList(istanzaEventoService.traceIstanzaEventoByCodeTipoEvento(idIstanza, codEvento, null, null, null, null,null, attoreScriva), className);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
    }

    /**
     * Test chiamata cosmo response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testChiamataCosmo(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        try {
            cosmoService.creazionePraticaCosmo(417L);
            return Response.ok().build();
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e.getMessage()).status(500).build();
        }
    }

    /**
     * Test chiamata cosmo response.
     *
     * @param uuidIndex       the uuid index
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testLinkIndex(String uuidIndex, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uuidIndex);
        TimeZone timeZone = TimeZone.getTimeZone(ZoneOffset.of("+01:00"));
        java.util.Date now = Calendar.getInstance(timeZone).getTime();
        String fromDate = CalendarUtils.convertDateToString(CalendarUtils.DATE_FORMAT_YYYY_MM_DDHHMM, now);
        java.util.Date afterAddMin = DateUtils.addMinutes(now, 10);
        String toDate = CalendarUtils.convertDateToString(CalendarUtils.DEFAULT_TIME_ZONE, afterAddMin);
        try {
            UrlDTO url = allegatiService.getLinkIndexFile(uuidIndex, fromDate, toDate);
            return Response.ok(url).build();
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e).status(500).build();
        }
    }

    /**
     * Test loc csi response.
     *
     * @param query           the query
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testLocCSI(String query, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, query);
        try {
            return getResponseList(loccsiServiceHelper.suggestToponomastica(null, null, query, null, null), className);
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e).status(500).build();
        }
    }

    /**
     * Test vincolo idrogeologico response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testVincoloIdrogeologico(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        String gmlGeometry = "<gml:Polygon srsName=\"EPSG:32632\"><gml:outerBoundaryIs><gml:LinearRing><gml:coordinates>371801.01751313487,5037016.9067425551 400291.30560420314,5030871.9426444815 418726.19789842388,5011319.7841506116 417050.29859894922,4985064.0284588421 406436.26970227668,4962718.7044658475 392470.44220665505,4944283.8121716268 364538.78721541155,4950987.4093695255 347221.16112084064,4968863.668563921 343869.36252189142,4991767.6256567407 355042.02451838879,5016906.11514886 371801.01751313487,5037016.9067425551</gml:coordinates></gml:LinearRing></gml:outerBoundaryIs></gml:Polygon>";
        try {
            return Response.ok(vincoloIdrogeologicoServiceHelper.isRicadenzaSignificativaByGMLGeometry(gmlGeometry)).build();
        } catch (Exception e) {
            logError(className, e);
            return Response.serverError().entity(e).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Test upload response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testUpload(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String url = "http://dev-unomi.nivolapiemonte.it/parot/v1";
        String accessToken = "1bb2db57-794e-3846-975b-5629475b782d";
        //String filePath = "D:\\Immagine.png";
        String filePath = httpRequest.getParameter("path");
        try {
            // chiamata rest-easy
            File file = new File(filePath);
            Entity<File> entity = Entity.entity(file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
            Client client = ClientBuilder.newBuilder().build();
            WebTarget target = client.target(url);
            Response resp = target.request()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                return Response.serverError().entity(resp.readEntity(String.class)).status(resp.getStatus()).build();
            } else {
                return Response.ok(resp.readEntity(String.class)).build();
            }

        } catch (Exception e) {
            return Response.serverError().status(500).build();
        }
    }

    /**
     * Gets profilo app.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the profilo app
     */
    @Override
    public Response getProfiloApp(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        return Response.ok(getProfiloAppExtended(idIstanza, getAttoreScriva(httpHeaders))).build();
    }

    /**
     * Gets json data.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the json data
     * @throws GenericException the generic exception
     */
    @Override
    public Response getJsonData(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException, ParseException {
        logBegin(className);
        /*
        List<IstanzaExtendedDTO> istanza = istanzaDAO.loadIstanza(idIstanza);
        ErrorDTO errorDTO = mandatoryInfoIstanzaManager.checkIstanzaVIA(istanza.get(0));
        ErrorDTO errorDTO2 = mandatoryInfoIstanzaManager.checkIstanzaVIADb(istanza.get(0));
        */
        return Response.ok(jsonDataManager.generateJsonDataFromConfig(idIstanza).toString()).build();
    }

    /**
     * Gets csv.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the csv
     */
    @Override
    public Response getCSV(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        byte[] out;
        String filename = "Test_" + idIstanza + ".csv";
        try {
            List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
            if (istanzaList != null && !istanzaList.isEmpty()) {
                IstanzaExtendedDTO istanza = istanzaList.get(0);
                List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(istanza.getAdempimento().getCodAdempimento(), InformazioniScrivaEnum.CSV_ALLEGATI.name(), Constants.CSV_ALLEGATI_KEY);
                if (adempimentoConfigList != null && !adempimentoConfigList.isEmpty()) {
                    String query = adempimentoConfigList.get(0).getValore();
                    Map<String, Object> map = new HashMap<>();
                    map.put("idIstanza", idIstanza);
                    out = StringUtils.isNotBlank(query) ? dynamicSqlDAO.getCSVFromQuery(query, Constants.CSV_ALLEGATI_DELIMITER, map, null, null) : new byte[0];
                    return Response.ok(out, MediaType.APPLICATION_OCTET_STREAM).header(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").build();
                }
            }
        } catch (Exception e) {
            logError(className, e);
        }
        return Response.serverError().status(500).build();
    }

    /**
     * Test notifica response.
     *
     * @param idIstanza       the id istanza
     * @param codEvento       the cod evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response testNotifica(Long idIstanza, String codEvento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codEvento);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        PlaceHolderUtil.getDizionarioPlaceHolderList();
        return getResponseList(templateNotificationService.getConfigurazioneNotifica(codEvento, idIstanza, attoreScriva.getComponente()), className);
    }

    /**
     * Create notifiche from conf response.
     *
     * @param listConfigNotifica the list config notifica
     * @param idIstanza          the id istanza
     * @param codCanaleNotifica  the cod canale notifica
     * @param rifCanaleNotifica  the rif canale notifica
     * @param cfAttoreInLinea    the cf attore in linea
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the response
     */
    @Override
    public Response createNotificheFromConf(List<ConfigurazioneNotificaExtendedDTO> listConfigNotifica,
                                            Long idIstanza,
                                            String codCanaleNotifica,
                                            String rifCanaleNotifica,
                                            String cfAttoreInLinea,
                                            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]; codCanaleNotifica: [" + codCanaleNotifica + "]; rifCanaleNotifica: [" + rifCanaleNotifica + "]; cfAttoreInLinea: [" + cfAttoreInLinea + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return getResponseList(templateNotificationService.createNotificheFromConf(listConfigNotifica, idIstanza, codCanaleNotifica, rifCanaleNotifica, cfAttoreInLinea), className);
    }

    @Override
    public Response managerNotifiche(Long idIstanza,
                                     String codTipoevento,
                                     String rifCanaleNotifica,
                                     String codCanaleNotifica,
                                     String uidRichiesta,
                                     String desTipoRichiesta,
                                     Date dataIntegrazione,
                                     SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String infoParam = "\nidIstanza : [" + idIstanza + "]\ncodTipoevento : [" + codTipoevento + "]\nrifCanaleNotifica : [" + rifCanaleNotifica + "]\ncodCanaleNotifica : [" + codCanaleNotifica + "]\ndataIntegrazione : [" + dataIntegrazione + "]\n";

        logBeginInfo(className, infoParam);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            notificationManager.sendNotifica(idIstanza, codTipoevento, rifCanaleNotifica, codCanaleNotifica, uidRichiesta, desTipoRichiesta, dataIntegrazione, attoreScriva);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return Response.ok().build();
    }

    /**
     * Manager notifiche response.
     *
     * @param codiciIstatComuni the codici istat comuni
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response getAreeProtette(String codiciIstatComuni, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codiciIstatComuni);
        List<String> codiciIstatComuniList = new ArrayList<>();
        StringTokenizer tokenizerCodiciIstat = new StringTokenizer(codiciIstatComuni, ",");
        while (tokenizerCodiciIstat.hasMoreTokens()) {
            String codiceIstat = tokenizerCodiciIstat.nextToken().trim();
            if (StringUtils.isNotBlank(codiceIstat)) {
                codiciIstatComuniList.add(codiceIstat);
            }
        }
        //List<String> codiciIstatComuniList = Arrays.asList(codiciIstatComuni.split("\\s*,\\s*"));
        List<AreaProtettaFiltriEstesi> areaProtettaFiltriEstesiList = parkApiServiceHelper.getAreaProtetta(codiciIstatComuniList);
        return Response.ok(areaProtettaFiltriEstesiList).build();
    }

    /**
     * Test absolute path byte [ ].
     *
     * @param json         the json
     * @param templatePath the template path
     * @param filename     the filename
     * @return the byte [ ]
     */
    public byte[] testAbsolutePath(String json, String templatePath, String filename) throws IOException, XDocReportException, ParseException {

        //String rootPath = "/appserv/jboss/awf170/part002dvscrivabenode01/standalone/scriva_config/";
        String rootPath = templatePath + File.pathSeparator;
        //rootPath = "D:/Sviluppo/workspace-csi/scrivabesrv/docs/templates/";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IXDocReport report;
        // File fileLogo = new File("D:\\temp\\logo2.png");
        //IImageProvider ratioSizeLogo = new FileImageProvider(fileLogo);
        //InputStream in = new FileInputStream(rootPath + "VIA_001_MOD_IST.docx"); // Absolute path
        InputStream in = new FileInputStream(rootPath + File.pathSeparator + filename); // Absolute path

        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        Map<String, Object> mappings = new HashMap<>();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(json);
        mappings.put("ist", obj);
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));

        // Create fields metadata to manage image
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("logo", NullImageBehaviour.RemoveImageTemplate);
        IImageProvider ratioSizeLogo = new ClassPathImageProvider(TemplateUtil.class, "/templates/logo.png");
        ratioSizeLogo.setUseImageSize(true);
        ratioSizeLogo.setWidth(100f);
        ratioSizeLogo.setResize(true);

        IContext context = report.createContext();
        context.put("d", mappings);
        context.put("logo", ratioSizeLogo);

        Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
        report.convert(context, options, out);

        try (OutputStream os = new FileOutputStream(rootPath + "test_abcd.pdf")) {
            os.write(out.toByteArray());
        }

        return out.toByteArray();
    }

    private LocalDateTime getFormattedDate(Date date) {
        return CalendarUtils.dateToLocalDateTime(date);
    }

    private String getPropertyValue(List<Property> properties, String propertyToSearch) {
        List<String> values = getPropertyValues(properties, propertyToSearch);
        return values != null ? values.get(0) : null;
    }

    private List<String> getPropertyValues(List<Property> properties, String propertyToSearch) {
        List<Property> propertiesFiltered = properties.stream().filter(prop -> prop.getPrefixedName().equalsIgnoreCase(propertyToSearch)).collect(Collectors.toList());
        return !propertiesFiltered.isEmpty() ? propertiesFiltered.get(0).getValues() : null;
    }

    /**
     * Convert to local date via instant local date.
     *
     * @param dateToConvert the date to convert
     * @return the local date
     */
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


}
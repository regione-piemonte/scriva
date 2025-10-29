/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.service.impl;

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.exception.NoRecipientsForNotificationException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.*;
import it.csi.scriva.scrivabesrv.business.be.service.CompilantePreferenzaCanaleService;
import it.csi.scriva.scrivabesrv.dto.*;
import it.csi.scriva.scrivabesrv.dto.enumeration.ErrorMsgEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.IndAllegaEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.IndChannelTypeEnum;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.NotifyAllEnum;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.NotifyStatusEnum;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.TipoDestinatarioEnum;
import it.csi.scriva.scrivabesrv.util.notification.service.TemplateNotificationService;
import it.csi.scriva.scrivabesrv.util.placeholder.PlaceHolderUtil;
import it.csi.scriva.scrivabesrv.util.placeholder.enumeration.PlaceHolderEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Template notification service.
 */
@Component
public class TemplateNotificationServiceImpl extends BaseApiServiceImpl implements TemplateNotificationService {

    /**
     * The constant EMAIL.
     */
    public static final String EMAIL = "EMAIL";
    /**
     * The constant PEC.
     */
    public static final String PEC = "PEC";
    /**
     * The constant APP_IO.
     */
    public static final String APP_IO = "APP_IO";
    /**
     * The constant SCRIVA_FO.
     */
    public static final String SCRIVA_FO = "SCRIVA_FO";
    /**
     * The constant SERVIZIO.
     */
    public static final String SERVIZIO = "SERVIZIO";
    /**
     * The constant SCRIVA_BO.
     */
    public static final String SCRIVA_BO = "SCRIVA_BO";
    private final String className = this.getClass().getSimpleName();

    private static final List<String> CONF_KEYS_NOTIFICHE = Arrays.asList(Constants.CONF_KEY_NOTIFY_ALL,
            Constants.CONF_KEY_NOTIFY_CANALE_EMAIL,
            Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_FO,
            Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_BO,
            Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_SERVIZIO,
            Constants.CONF_KEY_NOTIFY_CANALE_PEC,
            Constants.CONF_KEY_NOTIFY_CANALE_APP_IO,
            Constants.CONF_KEY_SERVER_POSTA_HOST,
            Constants.CONF_KEY_SERVER_POSTA_PORTA,
            Constants.CONF_KEY_SERVER_POSTA_MITTENTE,
            Constants.CONF_KEY_SERVER_PEC_HOST,
            Constants.CONF_KEY_SERVER_PEC_PORTA,
            Constants.CONF_KEY_SERVER_PEC_MITTENTE);

    private static final List<String> CONF_KEYS_NOTIFY_CANALE = Arrays.asList(Constants.CONF_KEY_NOTIFY_CANALE_EMAIL,
            Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_FO,
            Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_BO,
            Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_SERVIZIO,
            Constants.CONF_KEY_NOTIFY_CANALE_PEC,
            Constants.CONF_KEY_NOTIFY_CANALE_APP_IO);

    private static final List<String> CONF_KEYS_NOTIFY_CANALE_SCRIVA = Arrays
            .asList(Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_FO, Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_BO);
    private static final List<String> CONF_KEYS_SERVER_POSTA = Arrays.asList(Constants.CONF_KEY_SERVER_POSTA_HOST,
            Constants.CONF_KEY_SERVER_POSTA_PORTA, Constants.CONF_KEY_SERVER_POSTA_MITTENTE);
    private static final List<String> CONF_KEYS_SERVER_PEC = Arrays.asList(Constants.CONF_KEY_SERVER_PEC_HOST,
            Constants.CONF_KEY_SERVER_PEC_PORTA, Constants.CONF_KEY_SERVER_PEC_MITTENTE);

    private List<DizionarioPlaceholderDTO> dizionarioPlaceholderList;
    private List<String> dizionarioPlaceHolderCodList;
    private Map<String, String> configs;
    private String errorE071;

    /**
     * The Adempimento config dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Allegato istanza dao.
     */
    @Autowired
    AllegatoIstanzaDAO allegatoIstanzaDAO;

    /**
     * The Canale notifica dao.
     */
    @Autowired
    CanaleNotificaDAO canaleNotificaDAO;

    /**
     * The Configurazione dao.
     */
    @Autowired
    ConfigurazioneDAO configurazioneDAO;

    /**
     * The Configurazione notifica dao.
     */
    @Autowired
    ConfigurazioneNotificaDAO configurazioneNotificaDAO;

    /**
     * The Configurazione notifica allegato dao.
     */
    @Autowired
    ConfigurazioneNotificaAllegatoDAO configurazioneNotificaAllegatoDAO;

    /**
     * The Tipo notifica evento dao.
     */
    @Autowired
    TipoNotificaEventoDAO tipoNotificaEventoDAO;

    /**
     * The Istanza competenza dao.
     */
    @Autowired
    IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    /**
     * The Template notification dao.
     */
    @Autowired
    TemplateNotificationDao templateNotificationDao;

    /**
     * The Compilante preferenza canale service.
     */
    @Autowired
    CompilantePreferenzaCanaleService compilantePreferenzaCanaleService;

    /**
     * The Dizionario placeholder dao.
     */
    @Autowired
    DizionarioPlaceholderDAO dizionarioPlaceholderDAO;

    /**
     * The Messaggio dao.
     */
    @Autowired
    MessaggioDAO messaggioDAO;

    /**
     * The Allegati manager.
     */
    @Autowired
    AllegatiManager allegatiManager;

    /**
     * Instantiates a new Template notification service.
     */
    public TemplateNotificationServiceImpl() {
        dizionarioPlaceholderDAO = (DizionarioPlaceholderDAO) SpringApplicationContextHelper
                .getBean("dizionarioPlaceholderDAO");
        configurazioneDAO = (ConfigurazioneDAO) SpringApplicationContextHelper.getBean("configurazioneDAO");
        messaggioDAO = (MessaggioDAO) SpringApplicationContextHelper.getBean("messaggioDAO");
        getConfigurazioni();
        getDizionarioPlaceHolderList();
        getErrorE071();
    }

    /**
     * Gets the tipo notifica evento.
     *
     * @param codTipoEvento the cod tipo evento
     * @return the tipo notifica evento
     */
    @Override
    public List<TipoNotificaEventoExtendedDTO> getTipoNotificaEvento(String codTipoEvento) {
        logBeginInfo(className, "codTipoEvento : [" + codTipoEvento + "]");
        List<TipoNotificaEventoExtendedDTO> tipiNotificheEventi = null;
        try {
            tipiNotificheEventi = tipoNotificaEventoDAO.loadTipoNotificaEvento(codTipoEvento);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return tipiNotificheEventi;
    }

    @Override
    public List<NotificaExtendedDTO> createNotificheFromConf(List<ConfigurazioneNotificaExtendedDTO> listConfigNotifica,
            Long idIstanza, String codCanaleNotifica, String rifCanaleNotifica, String cfAttoreInLinea) {
        logBegin(className);
        List<NotificaExtendedDTO> listaNotificheDaMandare = new ArrayList<>();

        // se uno dei due è nullo genera errore ed esce
        if (!(codCanaleNotifica == null || codCanaleNotifica.isEmpty())
                ^ !(rifCanaleNotifica == null || rifCanaleNotifica.isEmpty())) {
            throw new IllegalArgumentException(
                    "codCanaleNotifica e rifCanaleNotifica devono essere entrambi valorizzati o entrambi nulli");
        }

        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                && !rifCanaleNotifica.isEmpty()) {
            // verifica se codCanaleNotifica e rifCanaleNotifica sono antrambi validi e
            // popolati. Se antrambi validi:
            // fai filtro per il canale di notifica codCanaleNotifica sull'oggetto
            // listConfigNotifica
            List<ConfigurazioneNotificaExtendedDTO> listConfigNotificaFiltered = listConfigNotifica.stream()
                    .filter(p -> p.getCanaleNotifica().getCodCanaleNotifica().equals(codCanaleNotifica))
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(listConfigNotificaFiltered)) {
                listConfigNotifica = listConfigNotificaFiltered; // rimpiazza la lista con quella filtrata
            }
            // se lista vuota genera errore ed esce
            else {
                throw new IllegalArgumentException("Configurazione non presente per parametri passati in input ");
            }

        }

        // se nulli entrambi vai avanti

        for (ConfigurazioneNotificaExtendedDTO configNotifica : listConfigNotifica) {
            try {

                switch (TipoDestinatarioEnum
                        .valueOf(configNotifica.getDestinatario().getTipoDestinatario().getCodTipoDestinatario())) {

                    case PROFILO_FO:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null
                                && !codCanaleNotifica.isEmpty()
                                && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_FO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else {
                                throw new IllegalArgumentException(
                                        "PROFILO_FO: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }

                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email di notifica dal destinatario presente in
                            // configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null
                                && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email di notifica dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else if (idIstanza != null && StringUtils
                                .isNotBlank(configNotifica.getDestinatario().getProfiloApp().getCodProfiloApp())) {
                            // esegue la query sui compilanti
                            List<NotificaExtendedDTO> listaNotificheEstratte;
                            listaNotificheEstratte = templateNotificationDao.loadCompilanti(idIstanza,
                                    configNotifica.getDestinatario().getProfiloApp().getCodProfiloApp());
                            // la lista non è vuota e contiene almeno un oggetto
                            if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {
                                for (NotificaExtendedDTO notifica : listaNotificheEstratte) {
                                    String canale = configNotifica.getCanaleNotifica().getCodCanaleNotifica();
                                    if (Boolean.FALSE.equals(compilantePreferenzaCanaleService
                                            .checkCompilantePreferenzeCanale(notifica.getCfDestinatario(),
                                                    configNotifica.getCanaleNotifica().getCodCanaleNotifica()))) // verifica
                                                                                                                 // le
                                                                                                                 // preferenze
                                                                                                                 // utente
                                                                                                                 // per
                                                                                                                 // la
                                                                                                                 // ricezione
                                                                                                                 // notifiche
                                    {
                                        String error = "il destinatario individuato ha le notifiche disabilitate";
                                        logError(className, error);
                                        setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica, configNotifica,
                                                idIstanza, cfAttoreInLinea, error);
                                    } else if ((canale.equals(EMAIL) || canale.equals(PEC) || canale.equals(SERVIZIO)
                                            || canale.equals(APP_IO))
                                            && !StringUtils.isNotBlank(notifica.getRifCanale())) {
                                        String error = "PROFILO_FO: riferimento canale Email, Pec, APP_IO o servizio non disponibile per il destinatario individuato";
                                        logError(className, error);
                                        setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica, configNotifica,
                                                idIstanza, cfAttoreInLinea, error);
                                    } else {
                                        setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                idIstanza, cfAttoreInLinea, null);
                                    }

                                    listaNotificheDaMandare.add(notifica);
                                }

                            } else {
                                // la lista è vuota o è nulla
                                throw new NoRecipientsForNotificationException(
                                        "PROFILO_FO: La query sui compilanti non ha restituito risultati ");
                            }

                        } else {
                            throw new IllegalArgumentException(
                                    "PROFILO_FO: Parametri insufficienti per determinare almeno un destinatario di notifica ");
                        }

                        break;

                    case PROFILO_BO:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null
                                && !codCanaleNotifica.isEmpty()
                                && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_BO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else {

                                throw new IllegalArgumentException(
                                        "PROFILO_BO: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }

                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null
                                && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } // se la notifica e su canale applicativo allora cerca il codice fiscale. Non
                          // valido per notifica via email in quanto per i funzionari si notificasolo alla
                          // mail di gruppo
                        else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(SCRIVA_BO)) {
                            if (StringUtils
                                    .isNotBlank(configNotifica.getDestinatario().getProfiloApp().getCodProfiloApp())) {
                                // esegue la query sui funzionari
                                List<NotificaExtendedDTO> listaNotificheEstratte;
                                listaNotificheEstratte = templateNotificationDao.loadFunzionari(
                                        configNotifica.getDestinatario().getProfiloApp().getCodProfiloApp());
                                // la lista non è vuota e contiene almeno un oggetto
                                if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {
                                    for (NotificaExtendedDTO notifica : listaNotificheEstratte) {
                                        setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                idIstanza, cfAttoreInLinea, null);
                                        listaNotificheDaMandare.add(notifica);
                                    }
                                } else {
                                    throw new NoRecipientsForNotificationException(
                                            "PROFILO_BO: La query sui funzionari non ha restituito risultati ");
                                }
                            } else {
                                throw new IllegalArgumentException(
                                        "PROFILO_BO: Parametri insufficienti per determinare almeno un destinatario di notifica ");
                            }

                        } else {
                            throw new IllegalArgumentException(
                                    "PROFILO_BO: Email di gruppo non presenti per il canale di notifica EMAIL per il tipo destinatario corrente");
                        }

                        break;

                    case AC_PRINCIPALE:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL) || codCanaleNotifica.equals("PEC")
                                    || codCanaleNotifica.equals(SERVIZIO)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_BO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else {
                                throw new IllegalArgumentException(
                                        "AC_PRINCIPALE: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }

                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null
                                && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else // Se è valorizzato ID_COMPETENZA_TERRITORIO, bisogna selezionare tutte AC
                               // principali di una istanza
                        {
                            List<String> codACList;
                            List<String> tipoACList;
                            if (idIstanza != null) {
                                // SE NOTIFICA A FUNZIONARIO
                                if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(SCRIVA_BO)) {
                                    // fare query
                                    List<NotificaExtendedDTO> listaNotificheEstratte = templateNotificationDao
                                            .loadFunzionariAcPrincipale(idIstanza);
                                    if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {
                                        for (NotificaExtendedDTO notifica : listaNotificheEstratte) {

                                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, null);
                                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica alla lista
                                                                                   // che le dovrebbe contenere tutte
                                        }
                                    } else {
                                        throw new NoRecipientsForNotificationException(
                                                "AC_PRINCIPALE: La query sui funzionari per ac principale non ha restituito risultati ");

                                    }

                                } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                        || configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals("PEC")) {

                                    // estrae tutte le istanze competenze abbinate a idIstanza e per una data
                                    // idcompetenza territoriale
                                    List<IstanzaCompetenzaExtendedDTO> istanzeCompetenza = istanzaCompetenzaDAO
                                            .loadIstanzaCompetenzaByIdIstanzaClean(idIstanza);
                                    // se query restituisce almeno un record
                                    if (CollectionUtils.isNotEmpty(istanzeCompetenza)) {

                                        // se idCompetenzaTerritorio è valorizzato e idTipoCompetenza è null
                                        List<IstanzaCompetenzaExtendedDTO> istanzeCompetenzaFiltered;
                                        if (configNotifica.getIdCompetenzaTerritorio() != null
                                                && configNotifica.getIdTipoCompetenza() == null) {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (o.getCompetenzaTerritorio().getIdCompetenzaTerritorio()
                                                                    .equals(configNotifica.getIdCompetenzaTerritorio())) // verifica
                                                                                                                         // competenza
                                                                                                                         // territorio
                                                                                                                         // passsata
                                                                                                                         // in
                                                                                                                         // input
                                                            && (Boolean.TRUE.equals(o.getFlgAutoritaAssegnataBO())
                                                                    || (Boolean.FALSE
                                                                            .equals(o.getFlgAutoritaAssegnataBO())
                                                                            && Boolean.TRUE.equals(
                                                                                    o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }
                                        // se idCompetenzaTerritorio è valorizzato e idTipoCompetenza è valorizzato
                                        else if (configNotifica.getIdCompetenzaTerritorio() != null
                                                && configNotifica.getIdTipoCompetenza() != null) {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (o.getCompetenzaTerritorio().getIdCompetenzaTerritorio()
                                                                    .equals(configNotifica.getIdCompetenzaTerritorio())) // verifica
                                                                                                                         // competenza
                                                                                                                         // territorio
                                                                                                                         // passsata
                                                                                                                         // in
                                                                                                                         // input
                                                            && (o.getCompetenzaTerritorio().getTipoCompetenza()
                                                                    .getIdTipoCompetenza()
                                                                    .equals(configNotifica.getIdTipoCompetenza())) // verifica
                                                                                                                   // competenza
                                                                                                                   // territorio
                                                                                                                   // passsata
                                                                                                                   // in
                                                                                                                   // input
                                                            && (Boolean.TRUE.equals(o.getFlgAutoritaAssegnataBO())
                                                                    || (Boolean.FALSE
                                                                            .equals(o.getFlgAutoritaAssegnataBO())
                                                                            && Boolean.TRUE.equals(
                                                                                    o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }
                                        // se idCompetenzaTerritorio è null e idTipoCompetenza è valorizzato
                                        else if (configNotifica.getIdCompetenzaTerritorio() == null
                                                && configNotifica.getIdTipoCompetenza() != null) {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (o.getCompetenzaTerritorio().getTipoCompetenza()
                                                                    .getIdTipoCompetenza()
                                                                    .equals(configNotifica.getIdTipoCompetenza())) // verifica
                                                                                                                   // competenza
                                                                                                                   // territorio
                                                                                                                   // passsata
                                                                                                                   // in
                                                                                                                   // input
                                                            && (Boolean.TRUE.equals(o.getFlgAutoritaAssegnataBO())
                                                                    || (Boolean.FALSE
                                                                            .equals(o.getFlgAutoritaAssegnataBO())
                                                                            && Boolean.TRUE.equals(
                                                                                    o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }
                                        // se idCompetenzaTerritorio è null e idTipoCompetenza è null
                                        else {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (Boolean.TRUE.equals(o.getFlgAutoritaAssegnataBO())
                                                                    || (Boolean.FALSE
                                                                            .equals(o.getFlgAutoritaAssegnataBO())
                                                                            && Boolean.TRUE.equals(
                                                                                    o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }

                                        if (CollectionUtils.isNotEmpty(istanzeCompetenzaFiltered)) {
                                            // cicla la lista ripulita per estrarre mail o pec ( a seconda del canale
                                            // della configurazione) da inserire nella notifica
                                            List<NotificaExtendedDTO> listaNotificheEstratte = new ArrayList<>();

                                            for (IstanzaCompetenzaExtendedDTO ict : istanzeCompetenzaFiltered) {
                                                NotificaExtendedDTO notifica = new NotificaExtendedDTO();

                                                if (configNotifica.getCanaleNotifica().getCodCanaleNotifica()
                                                        .equals(EMAIL)) {
                                                    if (ict.getCompetenzaTerritorio().getDesEmailAdempi() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmailAdempi()
                                                                    .isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmailAdempi());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmail() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmail().isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmail());
                                                    }
                                                    // verifica a casccata se non c'è pec speficica per l'adempimento
                                                    // prende quella generica della AC, se non c'è nessuna pec
                                                    // ocnfigurata controlal le email (jira 1356)

                                                } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica()
                                                        .equals("PEC")) {
                                                    if (ict.getCompetenzaTerritorio().getDesPecAdempi() != null && !ict
                                                            .getCompetenzaTerritorio().getDesPecAdempi().isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesPecAdempi());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmailPec() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmailPec()
                                                                    .isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmailPec());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmailAdempi() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmailAdempi()
                                                                    .isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmailAdempi());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmail() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmail().isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmail());
                                                    }

                                                }
                                                // inserisce i dati restanti nell'oggetto notifica

                                                if (ict.getIdCompetenzaTerritorio() != null) {
                                                    notifica.setIdCompetenzaTerritorioCalcolata(
                                                            ict.getIdCompetenzaTerritorio());
                                                }

                                                if (ict.getCompetenzaTerritorio().getIdTipoCompetenza() != null) {
                                                    notifica.setIdTipoCompetenzaCalcolata(
                                                            ict.getCompetenzaTerritorio().getIdTipoCompetenza());
                                                }

                                                if (!StringUtils.isNotBlank(notifica.getRifCanale())) {
                                                    String error = "AC_PRINCIPALE: Email, Pec o servizio non disponibile per il destinatario individuato";
                                                    logError(className, error);
                                                    setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica,
                                                            configNotifica, idIstanza, cfAttoreInLinea, error);
                                                } else {
                                                    setNotifica(NotifyStatusEnum.CREATA.getId(), notifica,
                                                            configNotifica, idIstanza, cfAttoreInLinea, null);
                                                }

                                                listaNotificheEstratte.add(notifica); // aggiunge la notifica alla lista
                                                                                      // temporanea
                                            }

                                            // verifica se le ac sono contenute nella lista delle autorità competenti da
                                            // escludere:
                                            codACList = getCodACExcluded(idIstanza); // crea la liata da escludere
                                            tipoACList = getTipoCodACExcluded(idIstanza); // crea la liata da escludere
                                            List<String> finalCodACList = codACList;
                                            List<String> finalTipoACList = tipoACList;

                                            List<NotificaExtendedDTO> listaNotificheEstratteFiltered = listaNotificheEstratte
                                                    .stream()
                                                    .filter(obj -> !finalCodACList.contains(
                                                            obj.getIdCompetenzaTerritorioCalcolata().toString())
                                                            || !finalTipoACList.contains(
                                                                    obj.getIdTipoCompetenzaCalcolata().toString()))
                                                    .collect(Collectors.toList());

                                            if (CollectionUtils.isNotEmpty(listaNotificheEstratteFiltered)) {
                                                // jira 1253: TO aggregati in una sola mail
                                                List<String> toAggregati = listaNotificheEstratteFiltered.stream()
                                                        .map(NotificaExtendedDTO::getRifCanale)
                                                        .filter(StringUtils::isNotBlank)
                                                        .distinct()
                                                        .collect(Collectors.toList());

                                                List<NotificaExtendedDTO> fallite = listaNotificheEstratteFiltered
                                                        .stream()
                                                        .filter(n -> !StringUtils.isNotBlank(n.getRifCanale()))
                                                        .collect(Collectors.toList());

                                                if (CollectionUtils.isNotEmpty(toAggregati)) {
                                                    NotificaExtendedDTO notificaAggregata = new NotificaExtendedDTO();
                                                    notificaAggregata.setRifCanale(String.join(";", toAggregati));
                                                    setNotifica(NotifyStatusEnum.CREATA.getId(), notificaAggregata,
                                                            configNotifica, idIstanza, cfAttoreInLinea, null);

                                                    listaNotificheDaMandare.add(notificaAggregata);
                                                    if (CollectionUtils.isNotEmpty(fallite)) {
                                                        listaNotificheDaMandare.addAll(fallite);
                                                    }
                                                } else {
                                                    throw new NoRecipientsForNotificationException(
                                                            "AC_PRINCIPALE: Nessun destinatario residuo dopo il filtro autorità competenti da escludere ");
                                                }
                                            } else {
                                                throw new NoRecipientsForNotificationException(
                                                        "AC_PRINCIPALE: Nessun destinatario residuo dopo il filtro autorità competenti da escludere ");
                                            }

                                        } else {
                                            throw new NoRecipientsForNotificationException(
                                                    "AC_PRINCIPALE: Nessun destinatario residuo dopo il filtro ");

                                        }
                                    } else {
                                        throw new NoRecipientsForNotificationException(
                                                "AC_PRINCIPALE: La query loadIstanzaCompetenzaByIdIstanza non ha restituito risultati ");
                                    }

                                } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(SERVIZIO)) {
                                    // TBD
                                }

                            } else {
                                throw new IllegalArgumentException(
                                        "AC_PRINCIPALE: Non è stato fornito l'idIstanza; non è possibile eseguire la query per individuare le AC principali ");

                            }
                        }

                        break;

                    case AC_SECONDARIA:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL) || codCanaleNotifica.equals("PEC")
                                    || codCanaleNotifica.equals(SERVIZIO)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_BO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else {
                                throw new IllegalArgumentException(
                                        "AC_SECONDARIA: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }

                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null
                                && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else // Se è valorizzato ID_COMPETENZA_TERRITORIO, bisogna selezionare tutte AC
                               // principali di una istanza
                        {
                            List<String> codACList;
                            List<String> tipoACList;
                            if (idIstanza != null) {
                                if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(SCRIVA_BO)) {
                                    // fare query
                                    List<NotificaExtendedDTO> listaNotificheEstratte = templateNotificationDao
                                            .loadFunzionariAcSecondaria(idIstanza);

                                    if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {
                                        for (NotificaExtendedDTO notifica : listaNotificheEstratte) {

                                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, null);
                                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica alla lista
                                                                                   // che le dovrebbe contenere tutte
                                        }
                                    } else {
                                        throw new NoRecipientsForNotificationException(
                                                "AC_SECONDARIA: La query sui funzionari per ac principale non ha restituito risultati ");

                                    }

                                } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                        || configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals("PEC")) {

                                    // estrae tutte le istanze competenze abinate a idIstanza e per una data
                                    // idcompetenza territoriale
                                    List<IstanzaCompetenzaExtendedDTO> istanzeCompetenza = istanzaCompetenzaDAO
                                            .loadIstanzaCompetenzaByIdIstanzaClean(idIstanza);
                                    // se query restituisce almeno un record
                                    if (CollectionUtils.isNotEmpty(istanzeCompetenza)) {

                                        // se idCompetenzaTerritorio è valorizzato e idTipoCompetenza è null
                                        List<IstanzaCompetenzaExtendedDTO> istanzeCompetenzaFiltered;
                                        if (configNotifica.getIdCompetenzaTerritorio() != null
                                                && configNotifica.getIdTipoCompetenza() == null) {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (o.getCompetenzaTerritorio().getIdCompetenzaTerritorio()
                                                                    .equals(configNotifica.getIdCompetenzaTerritorio())) // verifica
                                                                                                                         // competenza
                                                                                                                         // territorio
                                                                                                                         // passsata
                                                                                                                         // in
                                                                                                                         // input
                                                            && ((Boolean.FALSE.equals(o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }
                                        // se idCompetenzaTerritorio è valorizzato e idTipoCompetenza è valorizzato
                                        else if (configNotifica.getIdCompetenzaTerritorio() != null
                                                && configNotifica.getIdTipoCompetenza() != null) {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (o.getCompetenzaTerritorio().getIdCompetenzaTerritorio()
                                                                    .equals(configNotifica.getIdCompetenzaTerritorio())) // verifica
                                                                                                                         // competenza
                                                                                                                         // territorio
                                                                                                                         // passsata
                                                                                                                         // in
                                                                                                                         // input
                                                            && (o.getCompetenzaTerritorio().getTipoCompetenza()
                                                                    .getIdTipoCompetenza()
                                                                    .equals(configNotifica.getIdTipoCompetenza())) // verifica
                                                                                                                   // competenza
                                                                                                                   // territorio
                                                                                                                   // passsata
                                                                                                                   // in
                                                                                                                   // input
                                                            && ((Boolean.FALSE.equals(o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }
                                        // se idCompetenzaTerritorio è null e idTipoCompetenza è valorizzato
                                        else if (configNotifica.getIdCompetenzaTerritorio() == null
                                                && configNotifica.getIdTipoCompetenza() != null) {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && (o.getCompetenzaTerritorio().getTipoCompetenza()
                                                                    .getIdTipoCompetenza()
                                                                    .equals(configNotifica.getIdTipoCompetenza())) // verifica
                                                                                                                   // competenza
                                                                                                                   // territorio
                                                                                                                   // passsata
                                                                                                                   // in
                                                                                                                   // input
                                                            && ((Boolean.FALSE.equals(o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }
                                        // se idCompetenzaTerritorio è null e idTipoCompetenza è null
                                        else {
                                            istanzeCompetenzaFiltered = istanzeCompetenza.stream()
                                                    .filter(o -> o.getDataFineValidita() == null
                                                            && ((Boolean.FALSE.equals(o.getFlgAutoritaPrincipale()))))
                                                    .collect(Collectors.toList());
                                        }

                                        if (CollectionUtils.isNotEmpty(istanzeCompetenzaFiltered)) {
                                            // cicla la lsita ripulita per estrarre mail o pec ( a seconda del canale
                                            // della configurazione) da inserire nella notifica
                                            List<NotificaExtendedDTO> listaNotificheEstratte = new ArrayList<>();

                                            for (IstanzaCompetenzaExtendedDTO ict : istanzeCompetenzaFiltered) {
                                                NotificaExtendedDTO notifica = new NotificaExtendedDTO();

                                                if (configNotifica.getCanaleNotifica().getCodCanaleNotifica()
                                                        .equals(EMAIL)) {
                                                    if (ict.getCompetenzaTerritorio().getDesEmailAdempi() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmailAdempi()
                                                                    .isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmailAdempi());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmail() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmail().isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmail());
                                                    }

                                                } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica()
                                                        .equals("PEC")) { // verifica a casccata se non c'è pec
                                                                          // speficica per l'adempimento prende quella
                                                                          // generica della AC, se non c'è nessuna pec
                                                                          // ocnfigurata controlal le email (jira 1356)
                                                    if (ict.getCompetenzaTerritorio().getDesPecAdempi() != null && !ict
                                                            .getCompetenzaTerritorio().getDesPecAdempi().isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesPecAdempi());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmailPec() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmailPec()
                                                                    .isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmailPec());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmailAdempi() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmailAdempi()
                                                                    .isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmailAdempi());
                                                    } else if (ict.getCompetenzaTerritorio().getDesEmail() != null
                                                            && !ict.getCompetenzaTerritorio().getDesEmail().isEmpty()) {
                                                        notifica.setRifCanale(
                                                                ict.getCompetenzaTerritorio().getDesEmail());
                                                    }

                                                }
                                                // inserisce i dati restanti nell'oggetto notifica

                                                if (ict.getIdCompetenzaTerritorio() != null) {
                                                    notifica.setIdCompetenzaTerritorioCalcolata(
                                                            ict.getIdCompetenzaTerritorio());
                                                }

                                                if (ict.getCompetenzaTerritorio().getIdTipoCompetenza() != null) {
                                                    notifica.setIdTipoCompetenzaCalcolata(
                                                            ict.getCompetenzaTerritorio().getIdTipoCompetenza());
                                                }

                                                if (!StringUtils.isNotBlank(notifica.getRifCanale())) {
                                                    String error = "AC_SECONDARIA:Email, Pec o servizio non disponibile per il destinatario individuato";
                                                    logError(className, error);
                                                    setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica,
                                                            configNotifica, idIstanza, cfAttoreInLinea, error);
                                                } else {
                                                    setNotifica(NotifyStatusEnum.CREATA.getId(), notifica,
                                                            configNotifica, idIstanza, cfAttoreInLinea, null);
                                                }

                                                listaNotificheEstratte.add(notifica); // aggiunge la notifica alla lista
                                                                                      // che le dovrebbe contenere tutte

                                            }

                                            // verifica se le ac sono contentue nella ista delle atrità competenti da
                                            // escludere:
                                            codACList = getCodACExcluded(idIstanza); // crea la liata da escludere
                                            tipoACList = getTipoCodACExcluded(idIstanza); // crea la liata da escludere
                                            List<String> finalCodACList = codACList;
                                            List<String> finalTipoACList = tipoACList;

                                            List<NotificaExtendedDTO> listaNotificheEstratteFiltered = listaNotificheEstratte
                                                    .stream()
                                                    .filter(obj -> !finalCodACList.contains(
                                                            obj.getIdCompetenzaTerritorioCalcolata().toString())
                                                            || !finalTipoACList.contains(
                                                                    obj.getIdTipoCompetenzaCalcolata().toString()))
                                                    .collect(Collectors.toList());

                                            if (CollectionUtils.isNotEmpty(listaNotificheEstratteFiltered)) {
                                                // jira 1253: TO aggregati in una sola mail
                                                List<String> toAggregati = listaNotificheEstratteFiltered.stream()
                                                        .map(NotificaExtendedDTO::getRifCanale)
                                                        .filter(StringUtils::isNotBlank)
                                                        .distinct()
                                                        .collect(Collectors.toList());

                                                List<NotificaExtendedDTO> fallite = listaNotificheEstratteFiltered
                                                        .stream()
                                                        .filter(n -> !StringUtils.isNotBlank(n.getRifCanale()))
                                                        .collect(Collectors.toList());

                                                if (CollectionUtils.isNotEmpty(toAggregati)) {
                                                    NotificaExtendedDTO notificaAggregata = new NotificaExtendedDTO();
                                                    notificaAggregata.setRifCanale(String.join(";", toAggregati));
                                                    setNotifica(NotifyStatusEnum.CREATA.getId(), notificaAggregata,
                                                            configNotifica, idIstanza, cfAttoreInLinea, null);

                                                    listaNotificheDaMandare.add(notificaAggregata);
                                                    if (CollectionUtils.isNotEmpty(fallite)) {
                                                        listaNotificheDaMandare.addAll(fallite);
                                                    }
                                                } else {
                                                    throw new NoRecipientsForNotificationException(
                                                            "AC_SECONDARIA: Nessun destinatario residuo dopo il filtro autorità competenti da escludere ");
                                                }
                                            } else {
                                                throw new NoRecipientsForNotificationException(
                                                        "AC_SECONDARIA: Nessun destinatario residuo dopo il filtro autorità competenti da escludere ");
                                            }

                                        } else {
                                            throw new NoRecipientsForNotificationException(
                                                    "AC_SECONDARIA: Nessun destinatario residuo dopo il filtro ");

                                        }
                                    } else {
                                        throw new NoRecipientsForNotificationException(
                                                "AC_SECONDARIA: La query loadIstanzaCompetenzaByIdIstanza non ha restituito risultati ");
                                    }

                                } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(SERVIZIO)) {
                                    // TBD
                                }

                            } else {
                                throw new IllegalArgumentException(
                                        "AC_SECONDARIA: Non è stato fornito l'idIstanza; non è possibile eseguire la query per individuare le AC principali ");

                            }
                        }

                        break;

                    case ATTORE_FO:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null
                                && !codCanaleNotifica.isEmpty()
                                && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_FO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else {

                                throw new IllegalArgumentException(
                                        "ATTORE_FO: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email di notifica dal destinatario presente in
                            // configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email di notifica dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else {

                            if (StringUtils.isNotBlank(cfAttoreInLinea)) {
                                List<NotificaExtendedDTO> listaNotificheEstratte = templateNotificationDao
                                        .loadCompilanteInLinea(cfAttoreInLinea);
                                if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {
                                    for (NotificaExtendedDTO notifica : listaNotificheEstratte) {
                                        // per i compilanti trovati popola una notifica
                                        String canale = configNotifica.getCanaleNotifica().getCodCanaleNotifica();
                                        if ((canale.equals(EMAIL) || canale.equals(PEC) || canale.equals(SERVIZIO)
                                                || canale.equals(APP_IO))
                                                && !StringUtils.isNotBlank(notifica.getRifCanale())) {
                                            String error = "ATTORE_FO: riferimento canale Email, Pec, APP_IO o servizio non disponibile per il destinatario individuato";
                                            logError(className, error);
                                            setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, error);
                                        } else {
                                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, null);
                                        }

                                        listaNotificheDaMandare.add(notifica);
                                    }
                                } else {
                                    throw new NoRecipientsForNotificationException(
                                            "ATTORE_FO: La query sui compilanti non ha restituito risultati ");
                                }

                            } else {
                                throw new IllegalArgumentException(
                                        "ATTORE_FO: Non è stato fornito cf Attore In Linea; non è possibile eseguire la query per individuare attore ");

                            }
                        }
                        break;

                    case ATTORE_BO:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_BO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else {

                                throw new IllegalArgumentException(
                                        "ATTORE_BO: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email di notifica dal destinatario presente in
                            // configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email di notifica dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else {
                            if (StringUtils.isNotBlank(cfAttoreInLinea)) {
                                List<NotificaExtendedDTO> listaNotificheEstratte = templateNotificationDao
                                        .loadFunzionarioInLinea(cfAttoreInLinea);
                                if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {
                                    for (NotificaExtendedDTO notifica : listaNotificheEstratte) {
                                        // per i compilanti trovati popola una notifica
                                        String canale = configNotifica.getCanaleNotifica().getCodCanaleNotifica();
                                        if ((canale.equals(EMAIL) || canale.equals(PEC) || canale.equals(SERVIZIO)
                                                || canale.equals(APP_IO))
                                                && !StringUtils.isNotBlank(notifica.getRifCanale())) {
                                            String error = "ATTORE_BO: riferimento canale Email, Pec, APP_IO o servizio non disponibile per il destinatario individuato";
                                            logError(className, error);
                                            setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, error);
                                        } else {
                                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, null);
                                        }
                                        listaNotificheDaMandare.add(notifica);
                                    }
                                } else {
                                    throw new NoRecipientsForNotificationException(
                                            "ATTORE_BO: Nessun funzionario trovato con il codice fiscale fornito");
                                }

                            } else {
                                throw new IllegalArgumentException(
                                        "ATTORE_BO: Non è stato fornito cf Attore In Linea ; non è possibile eseguire la query per individuare attore ");

                            }
                        }

                        break;

                    case PROFILO_RICHIEDENTE:
                        // se notifica con parametri destinatari in input
                        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else if (codCanaleNotifica.equals(SCRIVA_FO)) {
                                notifica.setCfDestinatario(cfAttoreInLinea);
                            } else if (codCanaleNotifica.equals("PEC")) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else {

                                throw new IllegalArgumentException(
                                        "PROFILO_RICHIEDENTE: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }

                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else {
                            if (configNotifica.getDestinatario().getCodDestinatario() != null
                                    && !configNotifica.getDestinatario().getCodDestinatario().isEmpty()
                                    || idIstanza != null) {
                                List<NotificaExtendedDTO> listaNotificheEstratte = new ArrayList<>();
                                if (configNotifica.getDestinatario().getCodDestinatario().equals("PROPONENTE_PG")) {
                                    if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoPG(idIstanza,
                                                true);
                                    } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(PEC)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoPG(idIstanza,
                                                false);

                                        if (CollectionUtils.isEmpty(listaNotificheEstratte)) {
                                            listaNotificheEstratte = templateNotificationDao.loadSoggettoPG(idIstanza,
                                                    true);
                                        }
                                    }
                                } else if (configNotifica.getDestinatario().getCodDestinatario()
                                        .equals("PROPONENTE_PF")) {
                                    if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoPF(idIstanza,
                                                true);
                                    } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(PEC)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoPF(idIstanza,
                                                false);
                                    } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica()
                                            .equals(APP_IO)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoPF(idIstanza,
                                                null);
                                    }
                                } else if (configNotifica.getDestinatario().getCodDestinatario()
                                        .equals("PF_LEGALE_RAPPR")) {
                                    if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoLR(idIstanza,
                                                true);
                                    } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(PEC)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoLR(idIstanza,
                                                false);
                                    } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica()
                                            .equals(APP_IO)) {
                                        listaNotificheEstratte = templateNotificationDao.loadSoggettoLR(idIstanza,
                                                null);
                                    }
                                } else {
                                    throw new IllegalArgumentException(
                                            "PROFILO_RICHIEDENTE: codice destinatario non previsto");

                                }

                                // la lista non è vuota e contiene almeno un oggetto
                                if (CollectionUtils.isNotEmpty(listaNotificheEstratte)) {

                                    for (NotificaExtendedDTO notifica : listaNotificheEstratte) {
                                        // per i compilanti trovati popola una notifica

                                        // per i compilanti trovati popola una notifica
                                        String canale = configNotifica.getCanaleNotifica().getCodCanaleNotifica();
                                        if ((canale.equals(EMAIL) || canale.equals("PEC") || canale.equals(SERVIZIO))
                                                && !StringUtils.isNotBlank(notifica.getRifCanale())) {
                                            String error = "PROFILO_RICHIEDENTE: Email, Pec o servizio non disponibile per il destinatario individuato";
                                            logError(className, error);
                                            setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, error);
                                        } else {
                                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica,
                                                    idIstanza, cfAttoreInLinea, null);
                                        }

                                        listaNotificheDaMandare.add(notifica);

                                    }
                                } else {
                                    // la lista è vuota o è nulla
                                    throw new NoRecipientsForNotificationException(
                                            "PROFILO_RICHIEDENTE: La query sui soggetti non ha restituito risultati ");
                                }
                            } else {
                                throw new IllegalArgumentException(
                                        " PROFILO_RICHIEDENTE: non è stato fornito il codice destinatario o l'id istanza");
                            }
                        }

                        break;

                    case ALTRO_DESTINATARIO:
                        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL) || codCanaleNotifica.equals("PEC")) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else {

                                throw new IllegalArgumentException(
                                        "ALTRO_DESTINATARIO: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null
                                && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals("PEC")
                                && configNotifica.getDesPec() != null
                                && !configNotifica.getDesPec().isEmpty()) {
                            // genera notifica prendendo la pec del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesPec());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else {
                            throw new NoRecipientsForNotificationException(
                                    "ALTRO_DESTINATARIO:: Nessun destinatio valido per la configurazione indicata ");
                        }

                        break;

                    case ALTRO_SISTEMA:
                        if (codCanaleNotifica != null && !codCanaleNotifica.isEmpty() && rifCanaleNotifica != null
                                && !rifCanaleNotifica.isEmpty()) {
                            // genera notifica prendendo i dati del destinatario di notifica dai parametri
                            // passati
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            if (codCanaleNotifica.equals(EMAIL) || codCanaleNotifica.equals("PEC")
                                    || codCanaleNotifica.equals(SERVIZIO)) {
                                notifica.setRifCanale(rifCanaleNotifica);
                            } else {

                                throw new IllegalArgumentException(
                                        "ALTRO_SISTEMA: Configurazione per canale di notifica richiesto non presente per parametri passati ");
                            }
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(SERVIZIO)
                                && configNotifica.getDestinatario().getDesEmail() != null
                                && !configNotifica.getDestinatario().getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario di notifica dal
                            // destinatario
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDestinatario().getDesServizioApplicativo());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata

                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(EMAIL)
                                && configNotifica.getDesEmail() != null
                                && !configNotifica.getDesEmail().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesEmail());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals("PEC")
                                && configNotifica.getDesPec() != null
                                && !configNotifica.getDesPec().isEmpty()) {
                            // genera notifica prendendo l'email del destinatario dalla configurazione
                            NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                            notifica.setRifCanale(configNotifica.getDesPec());
                            setNotifica(NotifyStatusEnum.CREATA.getId(), notifica, configNotifica, idIstanza,
                                    cfAttoreInLinea, null);
                            listaNotificheDaMandare.add(notifica); // aggiunge la notifica appena creata
                        } else {
                            throw new NoRecipientsForNotificationException(
                                    "ALTRO_SISTEMA:: Nessun destinatio valido per la configurazione indicata ");
                        }

                        break;

                    default:
                        break;
                }

            } catch (Exception e) {
                logError(className, e);
                NotificaExtendedDTO notifica = new NotificaExtendedDTO();
                setNotifica(NotifyStatusEnum.FALLITA.getId(), notifica, configNotifica, idIstanza, cfAttoreInLinea,
                        e.getMessage());
                listaNotificheDaMandare.add(notifica);
                // TODO: invia mail assitenza
            }
        }

        logEnd(className);
        return listaNotificheDaMandare;

    }

    /**
     * Sets the notifica.
     *
     * @param idStatoNotifica the id stato notifica
     * @param notifica        the notifica
     * @param configNotifica  the config notifica
     * @param idIstanza       the id istanza
     * @param cfAttoreInLinea the cf attore in linea
     * @param desSgnalazione  the des sgnalazione
     * @return the notifica extended DTO
     */
    private NotificaExtendedDTO setNotifica(Long idStatoNotifica,
            NotificaExtendedDTO notifica,
            ConfigurazioneNotificaExtendedDTO configNotifica,
            Long idIstanza,
            String cfAttoreInLinea,
            String desSgnalazione) {
        logBegin(className);
        notifica.setIdStatoNotifica(idStatoNotifica);
        if (configNotifica.getIdNotificaConfigurazione() != null)
            notifica.setIdNotificaConfigurazione(configNotifica.getIdNotificaConfigurazione());
        if (idIstanza != null)
            notifica.setIdIstanza(idIstanza);
        if (configNotifica.getCanaleNotifica().getIdComponenteApplPush() != null
                && configNotifica.getCanaleNotifica().getIdComponenteApplPush() > 0)
            notifica.setIdComponenteAppPush(configNotifica.getCanaleNotifica().getIdComponenteApplPush());

        if (StringUtils.isNotBlank(configNotifica.getContenutoNotifica()))
            notifica.setDesMessaggio(configNotifica.getContenutoNotifica());

        if (StringUtils.isNotBlank(configNotifica.getOggettoNotifica()))
            notifica.setDesOggetto(configNotifica.getOggettoNotifica());

        if (StringUtils.isNotBlank(desSgnalazione))
            notifica.setDesSegnalazione(desSgnalazione);

        if (StringUtils.isNotBlank(cfAttoreInLinea)) {
            notifica.setGestAttoreIns(cfAttoreInLinea);
            notifica.setGestAttoreUpd(cfAttoreInLinea);
        }

        if (StringUtils.isNotBlank(configNotifica.getDesNotificaCc())) {
            notifica.setRifCanaleCc(configNotifica.getDesNotificaCc());
        }
        if (StringUtils.isNotBlank(configNotifica.getCanaleNotifica().getCodCanaleNotifica())) {
            if (configNotifica.getCanaleNotifica().getCodCanaleNotifica().equals(APP_IO)) {
                notifica.setRifCanale(notifica.getCfDestinatario());
            }
        }

        notifica.setConfigurazioneNotificaExtendedDTO(configNotifica); // aggiunge i dati di configurazione
        logEnd(className);
        return notifica;
    }

    /**
     * Gets configurazione notifica.
     *
     * @param codTipoEvento    the cod tipo evento
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @return the configurazione notifica
     */
    @Override
    public List<ConfigurazioneNotificaExtendedDTO> getConfigurazioneNotifica(String codTipoEvento, Long idIstanza,
            String codComponenteApp) {
        logBegin(className);
        List<ConfigurazioneNotificaExtendedDTO> result = new ArrayList<>();
        try {
            String notifyAll = getConfNotifyAll();
            logInfo(className, "NotifyAll : [" + notifyAll + "]");
            Map<String, String> activeChannels;
            IndChannelTypeEnum indChannelType = null;
            switch (NotifyAllEnum.valueOf(notifyAll)) {
                case NO:
                    /*
                     * Il sistema non esegue nessun altro controllo: non crea e non invia alcuna
                     * notifica
                     */
                    return result;
                case PUSH:
                    /*
                     * Il sistema verifica quali canali applicativi siano attivi
                     * (SCRIVA_NOTIFY_CANALE_SCRIVA_FO, SCRIVA_NOTIFY_CANALE_SCRIVA_BO)
                     * La modalità PUSH serve per disattivare l’invio di notifiche ai canali esterni
                     * (scriva_d_canale_notifica.ind_tipo_canale=E).
                     * Per canali applicativi si intendono i canali con l’informazione
                     * (scriva_d_canale_notifica.ind_tipo_canale=A)
                     */
                    activeChannels = this.getConfNotifyCanaleScriva();
                    indChannelType = IndChannelTypeEnum.A;
                    break;
                case TRACE:
                    /*
                     * Il sistema verifica quali canali siano attivi (SCRIVA_NOTIFY_CANALE_*) e
                     * traccia le notifiche per tutti i canali
                     * attivi su SCRIVA_T_NOTIFICA ma non invia le notifiche e non popola la tabella
                     * SCRIVA_T_NOTIFICA_APPLICATIVA
                     */
                case SI:
                default:
                    /*
                     * Se non è presente la configurazione il default è SI.
                     */
                    activeChannels = this.getConfNotifyCanale();
                    logInfo(className, activeChannels.toString());
                    break;
            }
            List<String> canali = activeChannels != null ? new ArrayList<>(activeChannels.keySet()) : new ArrayList<>();
            replace(canali);
            logInfo(className, "canali attivi :\n" + canali);
            result = configurazioneNotificaDAO.loadConfigurazioneNotificaByCodeTipoEvento(codTipoEvento, idIstanza,
                    canali, indChannelType, codComponenteApp, Boolean.TRUE);
            if (idIstanza != null && CollectionUtils.isEmpty(result)) {
                result = configurazioneNotificaDAO.loadConfigurazioneNotificaByCodeTipoEvento(codTipoEvento, null,
                        canali, indChannelType, codComponenteApp, Boolean.TRUE);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets configurazione allegato notifica.
     *
     * @param configurazioneNotificaList the configurazione notifica list
     * @return the configurazione notifica
     */
    @Override
    public List<ConfigurazioneNotificaExtendedDTO> getConfigurazioneAllegatoNotifica(
            List<ConfigurazioneNotificaExtendedDTO> configurazioneNotificaList) {
        logBegin(className);
        List<ConfigurazioneNotificaExtendedDTO> result = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(configurazioneNotificaList)) {
                // recupero della lista degli id_notifica_configurazione
                List<Long> idConfigurazioneNotificaList = configurazioneNotificaList.stream()
                        .map(ConfigurazioneNotificaExtendedDTO::getIdNotificaConfigurazione)
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(idConfigurazioneNotificaList)) {
                    // recupero delle eventuali configurazioni allegati da associare alla notifica
                    List<ConfigurazioneNotificaAllegatoDTO> configurazioneNotificaAllegatoList = configurazioneNotificaAllegatoDAO
                            .loadConfigurazioneNotificaAllegato(idConfigurazioneNotificaList);
                    // associazione delle configurazioni allegato alle configurazioni notifiche
                    for (ConfigurazioneNotificaExtendedDTO configurazioneNotifica : configurazioneNotificaList) {
                        List<ConfigurazioneNotificaAllegatoDTO> configurazioneNotificaAllegatoListById = configurazioneNotificaAllegatoList
                                .stream()
                                .filter(confAllegato -> confAllegato
                                        .getIdNotificaConfigurazione() == configurazioneNotifica
                                                .getIdNotificaConfigurazione())
                                .collect(Collectors.toList());
                        configurazioneNotifica
                                .setConfigurazioneNotificaAllegato(configurazioneNotificaAllegatoListById);
                    }
                    result = configurazioneNotificaList;
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;

    }

    /**
     * Verify place holder.
     *
     * @param notificaList the notifica list
     * @param uidRichiesta the uid richiesta
     */
    @Override
    public void verifyPlaceHolder(List<NotificaExtendedDTO> notificaList, String uidRichiesta) {
        logBeginInfo(className, notificaList);
        List<NotificaExtendedDTO> notificaListFiltered = notificaList.stream()
                .filter(p -> p.getIdStatoNotifica().equals(NotifyStatusEnum.CREATA.getId()))
                .collect(Collectors.toList());
        try {
            if (CollectionUtils.isNotEmpty(notificaListFiltered)) {
                for (NotificaExtendedDTO notifica : notificaListFiltered) {
                    try {
                        // Estrazione di tutti i placeholder dai campi des_oggetto, des_messaggio,
                        // rif_canale_cc
                        List<String> notificaPlaceHolderList = getPlaceHolderFromNotifica(notifica);
                        logDebug(className, notificaPlaceHolderList.toString());
                        if (CollectionUtils.isNotEmpty(notificaPlaceHolderList)) {
                            // Se non ci sono tutti i placeholder nel dizionario settare errore nella
                            // notifica
                            List<String> phNotPresent = getPlaceHolderNotPresentOnDB(notificaPlaceHolderList);
                            if (notifica != null && CollectionUtils.isNotEmpty(phNotPresent)) {
                                String joinedPhString = String.join("; ", phNotPresent);
                                notifica.setIdStatoNotifica(NotifyStatusEnum.FALLITA.getId()); // stato errore
                                // Il placeholder {PH_ELENCO_PLACEHOLDER} del messaggio dell'errore E071 deve
                                // essere valorizzato con uno o l’elenco dei placeholder non trovati sulla
                                // tabella
                                notifica.setDesSegnalazione(getErrorE071().replace(
                                        PlaceHolderEnum.PH_ELENCO_PLACEHOLDER.getDescrizione(), joinedPhString));
                            } else {
                                // Qualora
                                Map<String, String> placeHolderValuesMap = PlaceHolderUtil
                                        .getPlaceHolderMap(uidRichiesta, notifica, notificaPlaceHolderList);

                                // Add all strings from placeHolderCodList not present in result with null value
                                for (String placeHolder : notificaPlaceHolderList) {
                                    placeHolder = "{" + placeHolder + "}";
                                    if (!placeHolderValuesMap.containsKey(placeHolder)) {
                                        placeHolderValuesMap.put(placeHolder, null);
                                    }
                                }

                                setValuesPlaceHolderOnNotifica(notifica, placeHolderValuesMap);
                            }
                        }
                    } catch (Exception e) {
                        logError(className, e);
                    }
                }
            } else {
                logError(className, "Nessuna notifica valida disponibile");
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets configurazioni.
     *
     * @return the configurazioni
     */
    @Override
    public Map<String, String> getConfigurazioni() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return configs;
    }

    /**
     * Gets conf notify all.
     *
     * @return the conf notify all
     */
    @Override
    public String getConfNotifyAll() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return configs.get(Constants.CONF_KEY_NOTIFY_ALL);
    }

    /**
     * Gets conf notify canale.
     *
     * @return the conf notify canale
     */
    @Override
    public Map<String, String> getConfNotifyCanale() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return getConfFromKeyList(CONF_KEYS_NOTIFY_CANALE)
                .entrySet().stream()
                .filter(e -> Boolean.TRUE.toString().equalsIgnoreCase(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Gets conf notify canale scriva.
     *
     * @return the conf notify canale scriva
     */
    @Override
    public Map<String, String> getConfNotifyCanaleScriva() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return getConfFromKeyList(CONF_KEYS_NOTIFY_CANALE_SCRIVA)
                .entrySet().stream()
                .filter(e -> Boolean.TRUE.toString().equalsIgnoreCase(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Gets conf server posta.
     *
     * @return the conf server posta
     */
    @Override
    public Map<String, String> getConfServerPosta() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return getConfFromKeyList(CONF_KEYS_SERVER_POSTA);
    }

    /**
     * Gets conf server pec.
     *
     * @return the conf server pec
     */
    @Override
    public Map<String, String> getConfServerPEC() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return getConfFromKeyList(CONF_KEYS_SERVER_PEC);
    }

    /**
     * Sets allegati from conf.
     *
     * @param configurazioneNotificaList the configurazione notifica list
     * @param notificaList               the notifica list
     * @return the allegati from conf
     */
    @Override
    public List<NotificaExtendedDTO> setAllegatiFromConf(
            List<ConfigurazioneNotificaExtendedDTO> configurazioneNotificaList, List<NotificaExtendedDTO> notificaList,
            String gestUidRichiesta, String desTipoRichiesta) {
        logBegin(className);
        List<NotificaExtendedDTO> listaNotificheDaInviare = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(configurazioneNotificaList) && CollectionUtils.isNotEmpty(notificaList)) {
                List<NotificaExtendedDTO> notificaListFiltered = notificaList.stream()
                        .filter(p -> p.getIdStatoNotifica().equals(NotifyStatusEnum.CREATA.getId()))
                        .collect(Collectors.toList());
                for (NotificaExtendedDTO notifica : notificaListFiltered) {
                    Long idIstanza = notifica.getIdIstanza();
                    // Recupero le configurazioni relativa alla notifica selezionata
                    List<ConfigurazioneNotificaExtendedDTO> configurazioneNotificaListFiltered = configurazioneNotificaList
                            .stream()
                            .filter(conf -> conf.getIdNotificaConfigurazione() != null && conf
                                    .getIdNotificaConfigurazione().equals(notifica.getIdNotificaConfigurazione()))
                            .collect(Collectors.toList());
                    List<NotificaAllegatoDTO> notificaAllegatoList = new ArrayList<>();
                    // Per ciascuna configurazione recupero la relativa configurazione degli
                    // allegati
                    for (ConfigurazioneNotificaExtendedDTO configurazioneNotifica : configurazioneNotificaListFiltered) {
                        List<ConfigurazioneNotificaAllegatoDTO> configurazioneNotificaAllegatoList = configurazioneNotifica
                                .getConfigurazioneNotificaAllegato();
                        // Se presente una configurazione allegato
                        if (CollectionUtils.isNotEmpty(configurazioneNotificaAllegatoList)) {
                            // controlli su allegati
                            for (ConfigurazioneNotificaAllegatoDTO configurazioneNotificaAllegato : configurazioneNotificaAllegatoList) {
                                String indAllegaPadre = configurazioneNotificaAllegato.getIndAllegaPadre();
                                String indAllegaFigli = configurazioneNotificaAllegato.getIndAllegaFigli();
                                List<AllegatoIstanzaDTO> allegatoPadreList = null;
                                List<AllegatoIstanzaDTO> allegatiFigliList;
                                NotificaAllegatoSearchDTO notificaAllegatoSearch = getNotificaAllegatoSearch(idIstanza,
                                        gestUidRichiesta, desTipoRichiesta,
                                        configurazioneNotifica.getIdNotificaConfigurazione(), null,
                                        configurazioneNotificaAllegato);

                                if (IndAllegaEnum.M.name().equals(indAllegaPadre)
                                        || IndAllegaEnum.O.name().equals(indAllegaPadre)) {
                                    // Verifica presenza doc padre
                                    allegatoPadreList = allegatoIstanzaDAO
                                            .loadAllegatoIstanzaForNotifiche(notificaAllegatoSearch);
                                }

                                if (IndAllegaEnum.M.name().equals(indAllegaPadre)
                                        && CollectionUtils.isEmpty(allegatoPadreList)) {
                                    notifica.setIdStatoNotifica(NotifyStatusEnum.FALLITA.getId());
                                    notifica.setDesSegnalazione(
                                            ErrorMsgEnum.EN01.name() + ErrorMsgEnum.EN01.getDescrizione());
                                    break;
                                }

                                AllegatoIstanzaDTO allegatoPadre = CollectionUtils.isNotEmpty(allegatoPadreList)
                                        ? allegatoPadreList.get(0)
                                        : null;
                                List<Long> idAllegatoPadreList = CollectionUtils.isNotEmpty(allegatoPadreList)
                                        ? allegatoPadreList.stream()
                                                .map(AllegatoIstanzaDTO::getIdAllegatoIstanza)
                                                .collect(Collectors.toList())
                                        : null;

                                NotificaAllegatoDTO notificaAllegatoPadre = null;
                                if (allegatoPadre != null && !IndAllegaEnum.N.name().equals(indAllegaPadre)) {
                                    notificaAllegatoPadre = new NotificaAllegatoDTO();
                                    notificaAllegatoPadre.setGestAttoreIns(notifica.getGestAttoreIns());
                                    notificaAllegatoPadre.setGestAttoreUpd(notifica.getGestAttoreUpd());
                                    notificaAllegatoPadre.setIdNotifica(notifica.getIdNotifica());
                                    notificaAllegatoPadre.setIdAllegatoIstanza(allegatoPadre.getIdAllegatoIstanza());
                                    notificaAllegatoPadre.setCodAllegato(allegatoPadre.getCodAllegato());
                                    notificaAllegatoPadre.setNomeAllegato(allegatoPadre.getNomeAllegato());
                                    notificaAllegatoPadre.setGestUidRichiesta(gestUidRichiesta);
                                    notificaAllegatoPadre.setDesTipoRichiesta(desTipoRichiesta);
                                    notificaAllegatoPadre.setUuidIndex(allegatoPadre.getUuidIndex());
                                    notificaAllegatoPadre.setFlgAllegatoFallito(Boolean.FALSE);
                                    notificaAllegatoList.add(notificaAllegatoPadre);
                                }

                                if (notificaAllegatoPadre != null && CollectionUtils.isNotEmpty(idAllegatoPadreList) &&
                                        (IndAllegaEnum.M.name().equals(indAllegaFigli)
                                                || IndAllegaEnum.O.name().equals(indAllegaFigli))) {
                                    notificaAllegatoSearch.setIdAllegatoPadreList(idAllegatoPadreList);
                                    allegatiFigliList = allegatoIstanzaDAO
                                            .loadAllegatoIstanzaForNotifiche(notificaAllegatoSearch);
                                    // Se ind_allega_figli è M (mandatory) e non ne sono stati trovati -> Errore
                                    if (IndAllegaEnum.M.name().equals(indAllegaFigli)
                                            && CollectionUtils.isEmpty(allegatiFigliList)) {
                                        notifica.setIdStatoNotifica(NotifyStatusEnum.FALLITA.getId());
                                        notifica.setDesSegnalazione(
                                                ErrorMsgEnum.EN02.name() + ErrorMsgEnum.EN02.getDescrizione());
                                        notificaAllegatoPadre.setFlgAllegatoFallito(Boolean.TRUE);
                                        notificaAllegatoPadre.setDesSegnalazione(
                                                ErrorMsgEnum.EN03.name() + ErrorMsgEnum.EN03.getDescrizione());
                                        break;
                                    }
                                    // Aggiunge allegati figli se ind_allega_figli è M o O e sono stati trovati
                                    if (!IndAllegaEnum.N.name().equals(indAllegaFigli)
                                            && CollectionUtils.isNotEmpty(allegatiFigliList)) {
                                        NotificaAllegatoDTO notificaAllegatoFiglio = new NotificaAllegatoDTO();
                                        for (AllegatoIstanzaDTO allegatoFiglio : allegatiFigliList) {
                                            notificaAllegatoFiglio.setGestAttoreIns(notifica.getGestAttoreIns());
                                            notificaAllegatoFiglio.setGestAttoreUpd(notifica.getGestAttoreUpd());
                                            notificaAllegatoFiglio.setIdNotifica(notifica.getIdNotifica());
                                            notificaAllegatoFiglio
                                                    .setIdAllegatoIstanza(allegatoFiglio.getIdAllegatoIstanza());
                                            notificaAllegatoFiglio.setCodAllegato(allegatoFiglio.getCodAllegato());
                                            notificaAllegatoFiglio.setNomeAllegato(allegatoFiglio.getNomeAllegato());
                                            notificaAllegatoFiglio.setGestUidRichiesta(gestUidRichiesta);
                                            notificaAllegatoFiglio.setDesTipoRichiesta(desTipoRichiesta);
                                            notificaAllegatoFiglio.setUuidIndex(allegatoFiglio.getUuidIndex());
                                            notificaAllegatoFiglio.setFlgAllegatoFallito(Boolean.FALSE);
                                            notificaAllegatoList.add(notificaAllegatoFiglio);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    notifica.setNotificaAllegatoList(notificaAllegatoList);
                }
                // recupero file da index
                getAllegatiFromIndex(notificaList);
            }
            listaNotificheDaInviare = notificaList;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return listaNotificheDaInviare;

    }

    private void getAllegatiFromIndex(List<NotificaExtendedDTO> notificaList) {
        logBegin(className);
        try {
            if (CollectionUtils.isNotEmpty(notificaList)) {
                List<NotificaExtendedDTO> notificaListFiltered = notificaList.stream()
                        .filter(p -> p.getIdStatoNotifica().equals(NotifyStatusEnum.CREATA.getId()))
                        .collect(Collectors.toList());
                for (NotificaExtendedDTO notifica : notificaListFiltered) {
                    Long idIstanza = notifica.getIdIstanza();
                    // Recupero gli allegati relativi alla notifica selezionata
                    List<NotificaAllegatoDTO> notificaAllegatoList = notifica.getNotificaAllegatoList();
                    if (CollectionUtils.isNotEmpty(notificaAllegatoList)) {
                        for (NotificaAllegatoDTO notificaAllegato : notificaAllegatoList) {
                            String uuidIndex = notificaAllegato.getUuidIndex();
                            if (StringUtils.isNotBlank(uuidIndex)) {
                                // recupero file da index
                                notificaAllegato.setFileAllegato(allegatiManager.getFileFromIndexByUuid(uuidIndex));
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets notifica allegato search.
     *
     * @param idIstanza                      the id istanza
     * @param gestUidRichiesta               the gest uid richiesta
     * @param desTipoRichiesta               the des tipo richiesta
     * @param idNotificaConfigurazione       the id notifica configurazione
     * @param idAllegatoPadreList            the id allegato padre list
     * @param configurazioneNotificaAllegato the configurazione notifica allegato
     * @return the notifica allegato search
     */
    private NotificaAllegatoSearchDTO getNotificaAllegatoSearch(Long idIstanza, String gestUidRichiesta,
            String desTipoRichiesta, Long idNotificaConfigurazione, List<Long> idAllegatoPadreList,
            ConfigurazioneNotificaAllegatoDTO configurazioneNotificaAllegato) {
        logBegin(className);
        NotificaAllegatoSearchDTO notificaAllegatoSearch = new NotificaAllegatoSearchDTO();
        try {
            notificaAllegatoSearch.setIdIstanza(idIstanza);
            notificaAllegatoSearch.setIdNotificaConfigurazione(idNotificaConfigurazione);
            notificaAllegatoSearch
                    .setIdNotificaConfigurazioneAllegato(configurazioneNotificaAllegato.getIdNotificaConfigAllegato());
            notificaAllegatoSearch.setGestUidRichiesta(gestUidRichiesta);
            notificaAllegatoSearch.setTipoRichiesta(desTipoRichiesta);
            notificaAllegatoSearch.setIdAllegatoPadreList(idAllegatoPadreList);
            notificaAllegatoSearch
                    .setFlgUltimoDoc(Boolean.TRUE.equals(configurazioneNotificaAllegato.getFlgUltimoDoc()) ? 1 : 0);
            notificaAllegatoSearch.setFlgRifAllegatoProtocollo(
                    Boolean.TRUE.equals(configurazioneNotificaAllegato.getFlgAllegatoRifProtocollo()) ? 1 : 0);
            notificaAllegatoSearch
                    .setFlgPubblicabili(Boolean.TRUE.equals(configurazioneNotificaAllegato.getFlgPubblicati()) ? 1 : 0);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return notificaAllegatoSearch;
    }

    /**
     * Gets conf from key list.
     *
     * @param keyList the key list
     * @return the conf from key list
     */
    public Map<String, String> getConfFromKeyList(List<String> keyList) {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_NOTIFICHE);
        }
        return configs.entrySet().stream()
                .filter(e -> keyList.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Replace.
     *
     * @param list the list
     */
    private void replace(List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            ListIterator<String> it = list.listIterator();
            while (it.hasNext()) {
                it.set(it.next().replace(Constants.CONF_KEY_NOTIFY_CANALE, ""));
            }
        }
    }

    /**
     * Gets cod ac excluded.
     *
     * @param idIstanza the id istanza
     * @return the cod ac excluded
     */
    private List<String> getCodACExcluded(Long idIstanza) {
        List<String> result = new ArrayList<>();
        try {
            List<AdempimentoConfigDTO> adempimentoConfigCodACList = adempimentoConfigDAO
                    .loadAdempimentoConfigByIdIstanzaInformazioneChiave(idIstanza, Constants.INFO_DISABILITA_NOTIFICA,
                            Constants.KEY_COD_COMPETENZA_TERRITORIO);
            if (CollectionUtils.isNotEmpty(adempimentoConfigCodACList)) {
                result = adempimentoConfigCodACList.stream()
                        .map(AdempimentoConfigDTO::getValore)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets tipo cod ac excluded.
     *
     * @param idIstanza the id istanza
     * @return the tipo cod ac excluded
     */
    private List<String> getTipoCodACExcluded(Long idIstanza) {
        List<String> result = new ArrayList<>();
        try {
            List<AdempimentoConfigDTO> adempimentoConfigTipoACList = adempimentoConfigDAO
                    .loadAdempimentoConfigByIdIstanzaInformazioneChiave(idIstanza, Constants.INFO_DISABILITA_NOTIFICA,
                            Constants.KEY_TIPO_COMPETENZA_TERRITORIO);
            if (CollectionUtils.isNotEmpty(adempimentoConfigTipoACList)) {
                result = adempimentoConfigTipoACList.stream()
                        .map(AdempimentoConfigDTO::getValore)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets place holder list.
     *
     * @return the place holder list
     */
    private List<String> getDizionarioPlaceHolderList() {
        logBegin(className);
        if (CollectionUtils.isEmpty(dizionarioPlaceHolderCodList)) {
            dizionarioPlaceholderList = PlaceHolderUtil.getDizionarioPlaceHolderList();
            dizionarioPlaceHolderCodList = CollectionUtils.isNotEmpty(dizionarioPlaceholderList)
                    ? dizionarioPlaceholderList.stream()
                            .map(DizionarioPlaceholderDTO::getCodDizionarioPlaceholder)
                            .collect(Collectors.toList())
                    : new ArrayList<>();
        }
        logEnd(className);
        return dizionarioPlaceHolderCodList;
    }

    /**
     * Gets message.
     *
     * @param code the code
     * @return the message
     */
    private String getMessage(String code) {
        logBegin(className);
        List<MessaggioExtendedDTO> messaggioList = messaggioDAO.loadMessaggioByCode(code);
        logEnd(className);
        return CollectionUtils.isNotEmpty(messaggioList) ? messaggioList.get(0).getDesTestoMessaggio() : null;
    }

    /**
     * Gets error e 071.
     *
     * @return the error e 071
     */
    private String getErrorE071() {
        logBegin(className);
        if (StringUtils.isBlank(errorE071)) {
            errorE071 = getMessage("E071");
        }
        logEnd(className);
        return errorE071;
    }

    /**
     * Gets place holder from notifica.
     *
     * @param notifica the notifica
     * @return the place holder from notifica
     */
    private List<String> getPlaceHolderFromNotifica(NotificaDTO notifica) {
        logBeginInfo(className, notifica);
        List<String> placeHolderList = new ArrayList<>();
        try {
            if (notifica != null) {
                // Estrazione di tutti i placeholder dai campi des_oggetto, des_messaggio,
                // rif_canale_cc
                List<String> phDesOggettoList = PlaceHolderUtil.getPlaceHolder(notifica.getDesOggetto());
                List<String> phDesMessaggioList = PlaceHolderUtil.getPlaceHolder(notifica.getDesMessaggio());
                List<String> phRifCanaleCcList = PlaceHolderUtil.getPlaceHolder(notifica.getRifCanaleCc());
                placeHolderList = Stream
                        .of(phDesOggettoList, phDesMessaggioList, phRifCanaleCcList)
                        .flatMap(Collection::stream)
                        .distinct()
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return placeHolderList;
    }

    /**
     * Sets values place holder on notifica.
     *
     * @param notifica             the notifica
     * @param placeHolderValuesMap the place holder values map
     */
    private void setValuesPlaceHolderOnNotifica(NotificaDTO notifica, Map<String, String> placeHolderValuesMap) {
        logBeginInfo(className, notifica);
        try {
            if (notifica != null && !placeHolderValuesMap.isEmpty()) {
                for (Map.Entry<String, String> entry : placeHolderValuesMap.entrySet()) {
                    // (SCRIVA-1261) Se placeholder non è valorizzato il sistema non deve
                    // presentarlo nella notifica
                    String value = StringUtils.isNotBlank(entry.getValue()) ? entry.getValue() : StringUtils.EMPTY;
                    if (StringUtils.isNotBlank(notifica.getDesOggetto())) {
                        // notifica.setDesOggetto(notifica.getDesOggetto().replace(entry.getKey(),
                        // value));
                        notifica.setDesOggetto(replaceTagPlaceHolder(notifica.getDesOggetto(), entry.getKey(), value)); // (SCRIVA-1261)
                    }
                    if (StringUtils.isNotBlank(notifica.getDesMessaggio())) {
                        // notifica.setDesMessaggio(notifica.getDesMessaggio().replace(entry.getKey(),
                        // value));
                        notifica.setDesMessaggio(
                                replaceTagPlaceHolder(notifica.getDesMessaggio(), entry.getKey(), value)); // (SCRIVA-1261)
                    }
                    if (StringUtils.isNotBlank(notifica.getRifCanaleCc())) {
                        // elimina eventuali ";" iniziali e finali per evitare che venga aggiunto un ";"
                        // in più
                        notifica.setRifCanaleCc(
                                notifica.getRifCanaleCc().replace(entry.getKey(), value).replaceAll("^;+|;+$", ""));
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Replace tag place holder string.
     *
     * @param testo       the testo
     * @param placeholder the placeholder
     * @param phValue     the ph value
     * @return the string
     */
    private String replaceTagPlaceHolder(String testo, String placeholder, String phValue) {
        logBegin(className);
        String result = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(testo) && StringUtils.isNotBlank(placeholder)) {
                PlaceHolderEnum placeHolderEnum = PlaceHolderEnum.findByDescr(placeholder.toUpperCase());
                String phKey = placeHolderEnum != null ? placeHolderEnum.name() : StringUtils.EMPTY;
                String tagApertura = "<se:" + phKey + ">";
                String tagChiusura = "</se:" + phKey + ">";
                if (StringUtils.isNotBlank(phValue)) {
                    phValue = getPhValueWithSeparator(testo, placeholder, phValue);
                    result = testo
                            .replace(tagApertura, StringUtils.EMPTY)
                            .replace(tagChiusura, StringUtils.EMPTY)
                            .replace(placeholder, phValue);
                } else {
                    int beginIndex = testo.indexOf(tagApertura);
                    int endIndex = testo.indexOf(tagChiusura) + tagChiusura.length();
                    String daCanc = beginIndex >= 0 && beginIndex < endIndex ? testo.substring(beginIndex, endIndex)
                            : StringUtils.EMPTY;
                    result = testo
                            .replace(daCanc, StringUtils.EMPTY)
                            .replace(placeholder, StringUtils.EMPTY);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    private String getPhValueWithSeparator(String testo, String placeholder, String phValue) {
        logBegin(className);
        String result = phValue;
        try {
            if (StringUtils.isNotBlank(testo) && StringUtils.isNotBlank(placeholder)
                    && StringUtils.isNotBlank(phValue)) {
                int placeHolderStartPos = testo.indexOf(placeholder); // posizione iniziale placeholder
                int placeHolderEndPos = placeHolderStartPos >= 0 ? placeHolderStartPos + placeholder.length()
                        : placeHolderStartPos; // posizione finale placeholder
                String separator = ", ";
                if (placeHolderEndPos >= 0) {
                    int incr = 0;
                    incr = (placeHolderEndPos + 1) > testo.length() ? incr : 1;
                    incr = (placeHolderEndPos + 2) > testo.length() ? incr : 2;
                    separator = incr > 0 ? testo.substring(placeHolderEndPos, placeHolderEndPos + incr) : separator;
                }
                result = phValue.replace(", ", separator);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets place holder not present on db.
     *
     * @param phOnNotifica the ph on notifica
     * @return the place holder not present on db
     */
    private List<String> getPlaceHolderNotPresentOnDB(List<String> phOnNotifica) {
        logBeginInfo(className, phOnNotifica);
        List<String> phNotPresent = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(phOnNotifica)) {
                for (String el : phOnNotifica) {
                    if (!getDizionarioPlaceHolderList().contains(el)) {
                        phNotPresent.add(el);
                    }
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return phNotPresent;
    }

}
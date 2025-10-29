/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * The type Constants.
 *
 * @author CSI PIEMONTE
 */
public class Constants {
    /**
     * The constant COMPONENT_NAME.
     */
    public static final String COMPONENT_NAME = "scrivabesrv";
    /**
     * The constant LOGGER_NAME.
     */
    public static final String LOGGER_NAME = "scrivabesrv";

    /**
     * The constant HEADER_ATTORE_GESTIONE.
     */
    public static final String HEADER_ATTORE_GESTIONE = "Attore-Gestione";

    /**
     * The constant HEADER_PROFILI_APP.
     */
    public static final String HEADER_PROFILI_APP = "ProfiliApp";

    /**
     * The constant HEADER_TIPO_ADEMPI_OGG_APP.
     */
    public static final String HEADER_TIPO_ADEMPI_OGG_APP = "TipoAdempimentoOggApp";

    /**
     * The constant HEADER_X_REQUEST_AUTH.
     */
    public static final String HEADER_X_REQUEST_AUTH = "X-Request-Auth";

    /**
     * The constant IDENTITY.
     */
    public static final String IDENTITY = "identity";

    /**
     * The constant KEY_FILE_FORMAT.
     */
    public static final String KEY_FILE_FORMAT = "formati_allegato";
    /**
     * The constant KEY_FILE_SIZE_MAX.
     */
    public static final String KEY_FILE_SIZE_MAX = "max_dim_allegato";

    /**
     * The constant DATE_FORMAT.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * The constant DATE_FORMAT_2.
     */
    public static final String DATE_FORMAT_2 = "dd/MM/yyyy";

    /**
     * The constant CONFIG_KEY_ALLEGATI_TENANT_UUID.
     */
    public static final String CONFIG_KEY_ALLEGATI_TENANT_UUID = "SCRIVA_INDEX_TENANT_UUID";
    /**
     * The constant CSV_ALLEGATI_COD_INFORMAZIONE_SCRIVA.
     */
    public static final String CSV_ALLEGATI_COD_INFORMAZIONE_SCRIVA = "CSV_ALLEGATI";

    /**
     * The constant CSV_OSSERVAZIONI_COD_INFORMAZIONE_SCRIVA.
     */
    public static final String CSV_OSSERVAZIONI_COD_INFORMAZIONE_SCRIVA = "CSV_DOC_OSS";
    /**
     * The constant CSV_ALLEGATI_KEY.
     */
    public static final String CSV_ALLEGATI_KEY = "Query";
    /**
     * The constant CSV_NOME_FILE.
     */
    public static final String CSV_NOME_FILE = "NomeFile";
    /**
     * The constant CSV_ALLEGATI_DELIMITER.
     */
    public static final String CSV_ALLEGATI_DELIMITER = ";";
    /**
     * The constant COD_CATEGORIA_ALLEGATI_SISTEMA.
     */
    public static final String COD_CATEGORIA_ALLEGATI_SISTEMA = "SYS"; // SISTEMA
    /**
     * The constant COD_TIPOLOGIA_ALLEGATI_ELENCO.
     */
    public static final String COD_TIPOLOGIA_ALLEGATI_ELENCO = "ELENCO_ALLEGATI"; // ELENCO ALLEGATI
    /**
     * The constant COD_TIPOLOGIA_ALLEGATI_MODULO_ISTANZA_FIRMATO.
     */
    public static final String COD_TIPOLOGIA_ALLEGATI_MODULO_ISTANZA_FIRMATO = "MOD_IST"; // MODULO ISTANZA FIRMATA
    /**
     * The constant COD_TIPOLOGIA_RICEVUTE_PAGAMENTI.
     */
    public static final String COD_TIPOLOGIA_RICEVUTE_PAGAMENTI = "RT_PAG"; // RICEVUTE PAGAMENTI
    /**
     * The constant COD_TIPOLOGIA_DELEGA_FIRMA.
     */
    public static final String COD_TIPOLOGIA_DELEGA_FIRMA = "DEL_FIRMA"; // DELEGA FIRMA


    /**
     * The constant CATEGORIA_MODIFICA_REGIONALE_KEY.
     */
    public static final String CONFIG_KEY_CATEGORIA_MODIFICA_REGIONALE = "CategoriaModificaRegionale";

    /**
     * The constant CATEOGIRA_MODIFICA_PROVINCIALE_KEY.
     */
    public static final String CONFIG_KEY_CATEGORIA_MODIFICA_PROVINCIALE = "CategoriaModificaProvinciale";

    /**
     * The constant CATEOGIRA_MODIFICA_COMUNALE_KEY.
     */
    public static final String CONFIG_KEY_CATEGORIA_MODIFICA_COMUNALE = "CategoriaModificaComunale";

    /**
     * The constant ADEMPIMENTO_CONFIG_KEY_FONTE_RICERCA.
     */
    public static final String CONFIG_KEY_FONTE_RICERCA = "FonteRicerca";

    /**
     * The constant CONFIG_KEY_ANNI_PUBB_CONCLUSE.
     */
    public static final String CONFIG_KEY_ANNI_PUBB_CONCLUSE = "ANNI_PUBB_CONCLUSE";

    /**
     * The constant CONFIG_KEY_ROOT_PATH.
     */
    public static final String CONFIG_KEY_PATH_ROOT = "SCRIVA_PATH_ROOT";

    /**
     * The constant CONFIG_KEY_ROOT_PATH.
     */
    public static final String CONFIG_KEY_PATH_TEMPLATE_MODULO = "SCRIVA_PATH_TEMPLATE_MODULO";

    /**
     * The constant CONFIG_KEY_ANNI_AVVISO_PUB.
     */
    public static final String CONFIG_KEY_ANNI_AVVISO_PUB = "AVVISO-PUB";

    /**
     * The constant CONFIG_KEY_IND_TIPO_CALCOLO_AC.
     */
    public static final String CONFIG_KEY_IND_TIPO_CALCOLO_AC = "IndTipoCalcoloAC";

    /**
     * The constant CONFIG_KEY_IND_GEO_MODE.
     */
    public static final String CONFIG_KEY_IND_GEO_MODE = "IndGeoMode";

    /**
     * The constant CONFIG_KEY_OGGETTO_FONTE.
     */
    public static final String CONFIG_KEY_OGGETTO_FONTE = "SCRIVA_OGGETTO_FONTE";

    /**
     * The constant CF_COMPILANTE_FITTIZIO_BO.
     */
    public static final String CF_COMPILANTE_FITTIZIO_BO = "SISCMP21A01A000A";

    /**** JSONDATA PATH */
    public static final String PATH_JSONDATA_QDR_CONFIG = "$.QDR_CONFIG";
    /**
     * The constant PATH_JSONDATA_QDR_CONFIG_OGGETTO.
     */
    public static final String PATH_JSONDATA_QDR_CONFIG_OGGETTO = PATH_JSONDATA_QDR_CONFIG + ".QDR_OGGETTO";
    /**
     * The constant PATH_JSONDATA_QDR_CONFIG_SITI_NATURA.
     */
    public static final String PATH_JSONDATA_QDR_CONFIG_SITI_NATURA = PATH_JSONDATA_QDR_CONFIG_OGGETTO + ".siti_rete_natura";
    /**
     * The constant PATH_JSONDATA_QDR_CONFIG_AREE_PROTETTE.
     */
    public static final String PATH_JSONDATA_QDR_CONFIG_AREE_PROTETTE = PATH_JSONDATA_QDR_CONFIG_OGGETTO + ".aree_protette";
    /**
     * The constant PATH_JSONDATA_QDR_CONFIG_VINCOLI.
     */
    public static final String PATH_JSONDATA_QDR_CONFIG_VINCOLI = PATH_JSONDATA_QDR_CONFIG_OGGETTO + ".vincoli";
    /**
     * The constant PATH_JSONDATA_QDR_CONFIG_DATI_CATASTO.
     */
    public static final String PATH_JSONDATA_QDR_CONFIG_DATI_CATASTO = PATH_JSONDATA_QDR_CONFIG_OGGETTO + ".dati_catastali";

    /**
     * The constant CONF_KEY_APIMAN_CONSUMER_KEY.
     */
    public static final String CONF_KEY_APIMAN_CONSUMER_KEY = "SCRIVA_APIMAN_CONSUMERKEY";
    /**
     * The constant CONF_KEY_APIMAN_CONSUMER_SECRET.
     */
    public static final String CONF_KEY_APIMAN_CONSUMER_SECRET = "SCRIVA_APIMAN_CONSUMERSECRET";

    /**
     * The constant CONF_KEY_NOTIFY_ALL.
     */
    public static final String CONF_KEY_NOTIFY_ALL = "SCRIVA_NOTIFY_ALL";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE.
     */
    public static final String CONF_KEY_NOTIFY_CANALE = "SCRIVA_NOTIFY_CANALE_";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE_EMAIL.
     */
    public static final String CONF_KEY_NOTIFY_CANALE_EMAIL = "SCRIVA_NOTIFY_CANALE_EMAIL";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE_SCRIVA_FO.
     */
    public static final String CONF_KEY_NOTIFY_CANALE_SCRIVA_FO = "SCRIVA_NOTIFY_CANALE_SCRIVA_FO";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE_SCRIVA_BO.
     */
    public static final String CONF_KEY_NOTIFY_CANALE_SCRIVA_BO = "SCRIVA_NOTIFY_CANALE_SCRIVA_BO";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE_SCRIVA_SERVIZIO.
     */
    public static final String CONF_KEY_NOTIFY_CANALE_SCRIVA_SERVIZIO = "SCRIVA_NOTIFY_CANALE_SCRIVA_SERVIZIO";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE_SCRIVA_PEC.
     */
    public static final String CONF_KEY_NOTIFY_CANALE_PEC = "SCRIVA_NOTIFY_CANALE_PEC";

    /**
     * The constant CONF_KEY_NOTIFY_CANALE_SCRIVA_APP_IO.
     */
    public static final String CONF_KEY_NOTIFY_CANALE_APP_IO = "SCRIVA_NOTIFY_CANALE_APP_IO";

    /**
     * The constant CONF_KEY_SERVER_POSTA_HOST.
     */
    public static final String CONF_KEY_SERVER_POSTA_HOST = "SCRIVA_SERVER_POSTA_HOST";

    /**
     * The constant CONF_KEY_SERVER_POSTA_PORTA.
     */
    public static final String CONF_KEY_SERVER_POSTA_PORTA = "SCRIVA_SERVER_POSTA_PORTA";

    /**
     * The constant CONF_KEY_SERVER_POSTA_MITTENTE.
     */
    public static final String CONF_KEY_SERVER_POSTA_MITTENTE = "SCRIVA_SERVER_POSTA_MITTENTE";
    
    /**
     * The constant CONF_KEY_SERVER_POSTA_MITTENTE.
     */
    public static final String CONF_KEY_SERVER_POSTA_PASSWORD = "SCRIVA_SERVER_POSTA_PASSWORD";
    
    /**
     * The constant CONF_KEY_SERVER_POSTA_MITTENTE.
     */
    public static final String CONF_KEY_SERVER_POSTA_USERNAME = "SCRIVA_SERVER_POSTA_USERNAME";

    /**
     * The constant CONF_KEY_SERVER_PEC_HOST.
     */
    public static final String CONF_KEY_SERVER_PEC_HOST = "SCRIVA_SERVER_PEC_HOST";

    /**
     * The constant CONF_KEY_SERVER_PEC_PORTA.
     */
    public static final String CONF_KEY_SERVER_PEC_PORTA = "SCRIVA_SERVER_PEC_PORTA";

    /**
     * The constant CONF_KEY_SERVER_PEC_MITTENTE.
     */
    public static final String CONF_KEY_SERVER_PEC_MITTENTE = "SCRIVA_SERVER_PEC_MITTENTE";
    /**
     * The constant CONF_KEY_SERVER_PEC_USERNAME.
     */
    public static final String CONF_KEY_SERVER_PEC_USERNAME = "SCRIVA_SERVER_PEC_USERNAME";
    /**
     * The constant CONF_KEY_SERVER_PEC_PASSWORD.
     */
    public static final String CONF_KEY_SERVER_PEC_PASSWORD = "SCRIVA_SERVER_PEC_PASSWORD";

     /**
     * The constant CONF_KEY_LIMITE_OGGETTO_NOTIFICA.
     */
    public static final String CONF_KEY_LIMITE_OGGETTO_NOTIFICA = "SCRIVA_LIMITE_OGGETTO_NOTIFICA";
    
    /**
     * The constant SCRIVA_NOTIFY_APP_IO_ENDPOINT_URL.
     */
    public static final String CONF_KEY_IO_ENDPOINT_URL = "SCRIVA_NOTIFY_APP_IO_ENDPOINT_URL";
    
    /**
     * The constant SCRIVA_NOTIFY_APP_IO_ENDPOINT_TOKEN.
     */
    public static final String CONF_KEY_IO_ENDPOINT_TOKEN = "SCRIVA_NOTIFY_APP_IO_ENDPOINT_TOKEN";

    /**
     * The constant METHOD_NAME_WHITELIST.
     */
    public static final List<String> METHOD_NAME_WHITELIST = List.of("somemethod", "anothermethod");

    /**
     * The constant COMPONENT_SCRIVA.
     */
    public static final List<String> COMPONENT_SCRIVA_LIST = List.of("FO", "BO", "PUBWEB", "COSMO");

    /**
     * The constant PATTERN_PLACEHOLDER.
     */
    public static final Pattern PATTERN_PLACEHOLDER = Pattern.compile("\\{(.*?)\\}");

    /**
     * The constant INFO_DISABILITA_NOTIFICA.
     */
    public static final String INFO_DISABILITA_NOTIFICA = "DISABILITA_NOTIFICA";

    /**
     * The constant KEY_COD_COMPETENZA_TERRITORIO.
     */
    public static final String KEY_COD_COMPETENZA_TERRITORIO = "COD_COMPETENZA_TERRITORIO";

    /**
     * The constant KEY_TIPO_COMPETENZA_TERRITORIO.
     */
    public static final String KEY_TIPO_COMPETENZA_TERRITORIO = "COD_TIPO_COMPETENZA";

    /**
     * The constant COD_INFO_RICEVUTA_DOC.
     */
    public static final String COD_INFO_RICEVUTA_DOC = "RICEVUTA_DOC";

    /**
     * The constant KEY_RICEVUTA_DOC.
     */
    public static final String KEY_RICEVUTA_DOC = "RCV_DOC";

    /**
     * The constant EXT_DOCX.
     */
    public static final String EXT_DOCX = ".docx";

    /**
     * The constant EXT_DOC.
     */
    public static final String EXT_DOC = ".doc";

    /**
     * The constant EXT_PDF.
     */
    public static final String EXT_PDF = ".pdf";

    /**
     * The constant EXT_ZIP.
     */
    public static final String EXT_ZIP = ".zip";

}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.placeholder;

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AccreditamentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.DizionarioPlaceholderDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotificaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaNatura2000DAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiIstanzaService;
import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.DizionarioPlaceholderDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaAreaProtettaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAreaProtettaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNatura2000DTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.placeholder.enumeration.PlaceHolderEnum;
import it.csi.scriva.scrivabesrv.util.placeholder.enumeration.PlaceHolderTableEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.Objects;

/**
 * The type Place holder util.
 *
 * @author CSI PIEMONTE
 */
@Component
public class PlaceHolderUtil {

    private static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".util");

    private static final String CLASS_NAME = "PlaceHolderUtil";

    private static final List<String> confKeys = Arrays.asList(Constants.CONF_KEY_SERVER_POSTA_MITTENTE,
            Constants.CONF_KEY_SERVER_PEC_MITTENTE);

    // @Autowired
    private static DizionarioPlaceholderDAO dizionarioPlaceholderDAO;

    // @Autowired
    private static AccreditamentoDAO accreditamentoDAO;

    // @Autowired
    private static CompilanteDAO compilanteDAO;

    // @Autowired
    private static ConfigurazioneDAO configurazioneDAO;

    // @Autowired
    private static IstanzaDAO istanzaDAO;

    // @Autowired
    private static IstanzaAttoreDAO istanzaAttoreDAO;

    // @Autowired
    private static IstanzaCompetenzaDAO istanzaCompetenzaDAO;

    // @Autowired
    private static NotificaDAO notificaDAO;

    // @Autowired
    private static OggettoIstanzaDAO oggettoIstanzaDAO;

    // @Autowired
    private static OggettoIstanzaAreaProtettaDAO oggettoIstanzaAreaProtettaDAO;

    // @Autowired
    private static OggettoIstanzaNatura2000DAO oggettoIstanzaNatura2000DAO;

    // @Autowired
    private static SoggettoIstanzaDAO soggettoIstanzaDAO;

    // @Autowired
    private static TipoCompetenzaDAO tipoCompetenzaDAO;

    // @Autowired
    private static OggettiIstanzaService oggettiIstanzaService;

    // TODO Richiesta accredito

    private static List<DizionarioPlaceholderDTO> dizionarioPlaceholderList;
    private static Map<String, String> configs;
    private static List<CompilanteDTO> compilanteList;
    private static List<IstanzaExtendedDTO> istanzaList;
    private static List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList;
    private static List<OggettoIstanzaUbicazioneExtendedDTO> oggettoIstanzaUbicazioneList;
    private static List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList;

    private PlaceHolderUtil() {
        initializeBeans();
        getDizionarioPlaceHolderList();
        setConfigs();
    }

    /**
     * Gets dizionario place holder list.
     *
     * @return the dizionario place holder list
     */
    public static List<DizionarioPlaceholderDTO> getDizionarioPlaceHolderList() {
        String debugInfo = "[" + CLASS_NAME + "::getDizionarioPlaceHolderList] ";
        LOGGER.debug(debugInfo + "BEGIN");
        if (CollectionUtils.isEmpty(dizionarioPlaceholderList)) {
            dizionarioPlaceholderDAO = dizionarioPlaceholderDAO == null
                    ? (DizionarioPlaceholderDAO) SpringApplicationContextHelper.getBean("dizionarioPlaceholderDAO")
                    : dizionarioPlaceholderDAO;
            dizionarioPlaceholderList = dizionarioPlaceholderDAO.loadDizionariPlaceholder();
        }
        LOGGER.debug(debugInfo + "END");
        return dizionarioPlaceholderList;
    }

    /**
     * Gets place holder.
     *
     * @param in the in
     * @return the place holder
     */
    public static List<String> getPlaceHolder(String in) {
        String debugInfo = "[" + CLASS_NAME + "::getPlaceHolder] ";
        LOGGER.debug(debugInfo + "BEGIN");
        List<String> result = new ArrayList<>();
        if (StringUtils.isNotBlank(in)) {
            Matcher m = Constants.PATTERN_PLACEHOLDER.matcher(in);
            while (m.find()) {
                result.add(m.group(1));
            }
        }
        LOGGER.debug(debugInfo + "END");
        return result;
    }

    /**
     * Get place holder map.
     *
     * @param notifica           the notifica
     * @param placeHolderCodList the place holder cod list
     * @return the map
     */
    public static Map<String, String> getPlaceHolderMap(String uidRichiesta, NotificaExtendedDTO notifica,
            List<String> placeHolderCodList) {
        String debugInfo = "[" + CLASS_NAME + "::replacePlaceHolder] ";
        LOGGER.debug(debugInfo + "BEGIN");
        Map<String, String> placeHolderValues = new HashMap<>();
        try {
            retrieveDataFromDb(notifica.getIdIstanza(), notifica.getCfDestinatario(), placeHolderCodList);
            placeHolderValues = getPlaceHolderValues(notifica, uidRichiesta, placeHolderCodList);
        } catch (Exception e) {
            LOGGER.error(debugInfo + e);
        }
        return placeHolderValues;
    }

    /**
     * Retrieve data from db.
     *
     * @param idIstanza          the id istanza
     * @param codiceFiscale      the codice fiscale
     * @param placeHolderCodList the place holder cod list
     */
    public static void retrieveDataFromDb(Long idIstanza, String codiceFiscale, List<String> placeHolderCodList) {
        String debugInfo = "[" + CLASS_NAME + "::retrieveDataFromDb] ";
        LOGGER.debug(debugInfo + "BEGIN");
        if (CollectionUtils.isNotEmpty(placeHolderCodList)) {
            List<String> nomeTabellaList = getDizionarioPlaceHolderList().stream()
                    .filter(diz -> placeHolderCodList.contains(diz.getCodDizionarioPlaceholder()))
                    .map(DizionarioPlaceholderDTO::getNomeTabella)
                    .distinct()
                    .collect(Collectors.toList());
            for (String nomeTabella : nomeTabellaList) {
                try {
                    switch (PlaceHolderTableEnum.valueOf(nomeTabella.toUpperCase())) {

                        case SCRIVA_D_CONFIGURAZIONE:
                            setConfigs();
                            break;

                        case SCRIVA_D_TIPO_COMPETENZA:
                        case SCRIVA_T_COMPETENZA_TERRITORIO:
                        case SCRIVA_R_ADEMPI_COMPETENZA:
                            istanzaCompetenzaList = idIstanza != null
                                    ? istanzaCompetenzaDAO.loadIstanzaCompetenzaByIdIstanza(idIstanza)
                                    : null;
                            break;

                        case SCRIVA_D_TIPO_ARE_PROTETTA:
                        case SCRIVA_R_OGG_AREA_PROTETTA:
                        case SCRIVA_R_OGG_NATURA_2000:
                        case SCRIVA_D_TIPO_NATURA_2000:
                        case SCRIVA_R_UBICA_OGGETTO_ISTANZA:
                        case SCRIVA_T_OGGETTO_ISTANZA:
                            oggettoIstanzaUbicazioneList = idIstanza != null
                                    ? oggettiIstanzaService.loadOggettoIstanzaByIdIstanza(idIstanza)
                                    : null;
                            break;

                        case SCRIVA_T_COMPILANTE:
                            compilanteList = compilanteDAO.loadCompilanteByCodiceFiscale(codiceFiscale);
                            break;

                        case SCRIVA_T_ISTANZA:
                            istanzaList = getIstanza(idIstanza);
                            break;

                        case SCRIVA_T_SOGGETTO_ISTANZA:
                            soggettoIstanzaList = idIstanza != null
                                    ? soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza)
                                    : null;
                            break;

                        case SCRIVA_R_RICHIESTA_ACCREDITO:
                        case SCRIVA_T_NOTIFICA:
                        case SCRIVA_R_NOTIFICA_APPLICATIVA:
                        case SCRIVA_D_STATO_NOTIFICA:
                        default:
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.error(debugInfo + e);
                }
            }

        }

        LOGGER.debug(debugInfo + "END");
    }

    /**
     * Gets place holder values.
     *
     * @param notifica           the notifica
     * @param placeHolderCodList the place holder cod list
     * @return the place holder values
     */
    public static Map<String, String> getPlaceHolderValues(NotificaExtendedDTO notifica, String uidRichiesta,
            List<String> placeHolderCodList) {
        String debugInfo = "[" + CLASS_NAME + "::retrieveDataFromDb] ";
        LOGGER.debug(debugInfo + "BEGIN");
        Map<String, String> result = new HashMap<>();
        Long idIstanza = notifica.getIdIstanza();
        if (CollectionUtils.isNotEmpty(placeHolderCodList)) {
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaPrincipaleList = null;
            List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaSecondariaList = null;
            List<SoggettoIstanzaExtendedDTO> richiedenteList = null;
            for (String placeHolderCode : placeHolderCodList) {
                try {
                    PlaceHolderEnum phEnum = PlaceHolderEnum.valueOf(placeHolderCode.toUpperCase());
                    String phEnumDesc = phEnum != null ? phEnum.getDescrizione() : null;
                    if (StringUtils.isNotBlank(phEnumDesc)) {
                        switch (phEnum) {
                            // *** AUTORITÀ COMPETENTE PRINCIPALE ***
                            case PH_ACP_ADEMPI_EMAIL:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesEmailAdempi)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));
                                }
                                break;
                            case PH_ACP_ADEMPI_PEC:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    /*
                                     * result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                     * .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                     * .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                     * .map(CompetenzaTerritorioExtendedDTO::getDesPecAdempi)
                                     * .collect(Collectors.joining(";")));
                                     */
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(competenzaTerritorio -> (competenzaTerritorio.getDesPecAdempi() != null
                                                    && !competenzaTerritorio.getDesPecAdempi().isEmpty())
                                                            ? competenzaTerritorio.getDesPecAdempi()
                                                            : competenzaTerritorio.getDesEmailAdempi())
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));
                                }
                                break;
                            case PH_ACP_CAP_COMPETENZA:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getCapCompetenza)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", ")));
                                }
                                break;
                            case PH_ACP_DES_COMPETENZA_TERRITORIO:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesCompetenzaTerritorio)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACP_DES_COMPETENZA_TERRITORIO_ESTESA:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesCompetenzaTerritorioEstesa)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACP_DES_TIPO_COMPETENZA_ESTESA:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getTipoCompetenza)
                                            .map(TipoCompetenzaDTO::getDesTipoCompetenzaEstesa)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACP_EMAIL:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesEmail)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));
                                }
                                break;
                            case PH_ACP_INDIRIZZO_COMPETENZA:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getIndirizzoCompetenza)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACP_NUM_CIVICO:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getNumCivicoIndirizzo)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACP_PEC:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    /*
                                     * result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                     * .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                     * .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                     * .map(CompetenzaTerritorioExtendedDTO::getDesEmailPec)
                                     * .collect(Collectors.joining(";")));
                                     */
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(competenzaTerritorio -> (competenzaTerritorio.getDesEmailPec() != null
                                                    && !competenzaTerritorio.getDesEmailPec().isEmpty())
                                                            ? competenzaTerritorio.getDesEmailPec()
                                                            : competenzaTerritorio.getDesEmail())
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));

                                }
                                break;
                            case PH_ACP_SITO_WEB:
                                istanzaCompetenzaPrincipaleList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaPrincipaleList) ? getIstanzaCompetenzaPrincipaleList()
                                                : istanzaCompetenzaPrincipaleList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaPrincipaleList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getSitoWeb)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;

                            // *** AUTORITÀ COMPETENTE SECONDARIA ***
                            case PH_ACS_ADEMPI_EMAIL:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesEmailAdempi)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));
                                }
                                break;
                            case PH_ACS_ADEMPI_PEC:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    /*
                                     * result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                     * .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                     * .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                     * .map(CompetenzaTerritorioExtendedDTO::getDesPecAdempi)
                                     * .collect(Collectors.joining(";")));
                                     */
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(competenzaTerritorio -> (competenzaTerritorio.getDesPecAdempi() != null
                                                    && !competenzaTerritorio.getDesPecAdempi().isEmpty())
                                                            ? competenzaTerritorio.getDesPecAdempi()
                                                            : competenzaTerritorio.getDesEmailAdempi())
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", ")));
                                }
                                break;
                            case PH_ACS_CAP_COMPETENZA:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getCapCompetenza)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACS_DES_COMPETENZA_TERRITORIO:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesCompetenzaTerritorio)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACS_DES_COMPETENZA_TERRITORIO_ESTESA:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesCompetenzaTerritorioEstesa)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACS_DES_TIPO_COMPETENZA_ESTESA:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getTipoCompetenza)
                                            .map(TipoCompetenzaDTO::getDesTipoCompetenza)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACS_EMAIL:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesEmail)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));

                                    result.put("{PH_ACS_EMAIL_TEST}", istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getDesEmail)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", ")));
                                }
                                break;
                            case PH_ACS_INDIRIZZO_COMPETENZA:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getIndirizzoCompetenza)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACS_NUM_CIVICO:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getNumCivicoIndirizzo)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_ACS_PEC:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    /*
                                     * result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                     * .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                     * .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                     * .map(CompetenzaTerritorioExtendedDTO::getDesEmailPec)
                                     * .collect(Collectors.joining(";")));
                                     */
                                    result.put(phEnumDesc, istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(competenzaTerritorio -> (competenzaTerritorio.getDesEmailPec() != null
                                                    && !competenzaTerritorio.getDesEmailPec().isEmpty())
                                                            ? competenzaTerritorio.getDesEmailPec()
                                                            : competenzaTerritorio.getDesEmail())
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));
                                }
                                break;
                            case PH_ACS_SITO_WEB:
                                istanzaCompetenzaSecondariaList = CollectionUtils
                                        .isEmpty(istanzaCompetenzaSecondariaList) ? getIstanzaCompetenzaSecondariaList()
                                                : istanzaCompetenzaSecondariaList;
                                if (CollectionUtils.isNotEmpty(istanzaCompetenzaSecondariaList)) {
                                    String value = istanzaCompetenzaList.stream()
                                            .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                                            .map(IstanzaCompetenzaExtendedDTO::getCompetenzaTerritorio)
                                            .map(CompetenzaTerritorioExtendedDTO::getSitoWeb)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;

                            // *** COMPILANTE ***
                            case PH_COMPILANTE_CF:
                                if (CollectionUtils.isNotEmpty(compilanteList)) {
                                    result.put(phEnumDesc,
                                            compilanteList.stream().map(CompilanteDTO::getCodiceFiscaleCompilante)
                                                    .filter(Objects::nonNull).collect(Collectors.joining(", ")));
                                }
                                break;
                            case PH_COMPILANTE_COGNOME:
                                if (CollectionUtils.isNotEmpty(compilanteList)) {
                                    result.put(phEnumDesc,
                                            compilanteList.stream().map(CompilanteDTO::getCognomeCompilante)
                                                    .filter(Objects::nonNull).collect(Collectors.joining(", ")));
                                }
                                break;
                            case PH_COMPILANTE_NOME:
                                if (CollectionUtils.isNotEmpty(compilanteList)) {
                                    result.put(phEnumDesc, compilanteList.stream().map(CompilanteDTO::getNomeCompilante)
                                            .filter(Objects::nonNull).collect(Collectors.joining(", ")));
                                }
                                break;

                            case PH_DES_LOCALITA:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                                        String value = ubicazioneOggettoIstanzaList.stream()
                                                .map(UbicazioneOggettoIstanzaExtendedDTO::getDesLocalita)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;

                            // *** ISTANZA ***
                            case PH_IST_COD_ISTANZA:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getCodIstanza());
                                }
                                break;
                            case PH_IST_COD_PRATICA:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getCodPratica());
                                }
                                break;
                            case PH_IST_DATA_CONCLUSIONE_PROCEDIMENTO:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc,
                                            istanzaList.get(0).getDataConclusioneProcedimento().toString());
                                }
                                break;
                            case PH_IST_DATA_INSERIMENTO_ISTANZA:
                            case PH_IST_DATA_PRESENTATA_ISTANZA: // da approfondire come gestire in maniera ottimale i
                                                                 // vari stati potremmo usare PH_DATA_ISTANZA_+ codice
                                                                 // stato per rendere dinamica la selezione
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getDataInserimentoIstanza().toString());
                                }
                                break;
                            case PH_IST_DATA_INSERIMENTO_PRATICA:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getDataInserimentoPratica().toString());
                                }
                                break;
                            case PH_IST_DATA_PRESENTA_INTEGRAZIONE:
                                // Data passata in input
                                /*
                                 * if (CollectionUtils.isNotEmpty(istanzaList)) {
                                 * result.put(phEnumDesc,
                                 * istanzaList.get(0).getDataInserimentoIstanza().toString());
                                 * }
                                 */
                                break;
                            case PH_IST_DATA_PUBBLICAZIONE:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getDataPubblicazione().toString());
                                }
                                break;
                            case PH_IST_DES_ADEMPIMENTO:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getAdempimento().getDesAdempimento());
                                }
                                break;
                            case PH_IST_DES_ESTESA_ADEMPIMENTO:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc,
                                            istanzaList.get(0).getAdempimento().getDesEstesaAdempimento());
                                }
                                break;
                            case PH_IST_NUM_PROTOCOLLO_ISTANZA:
                                istanzaList = getIstanza(idIstanza);
                                if (CollectionUtils.isNotEmpty(istanzaList)) {
                                    result.put(phEnumDesc, istanzaList.get(0).getNumProtocolloIstanza());
                                }
                                break;

                            // *** CONFIGURAZIONE ***
                            case PH_MITTENTE_EMAIL:
                                if (!configs.isEmpty()) {
                                    result.put(phEnumDesc, configs.get(Constants.CONF_KEY_SERVER_POSTA_MITTENTE));
                                }
                                break;
                            case PH_MITTENTE_PEC:
                                if (!configs.isEmpty()) {
                                    result.put(phEnumDesc, configs.get(Constants.CONF_KEY_SERVER_PEC_MITTENTE));
                                }
                                break;

                            // *** NOTIFICA ***
                            case PH_NOTIFICA_APP_DATA_CANCELLAZIONE:
                                break;
                            case PH_NOTIFICA_APP_DATA_INSERIMENTO:
                                result.put(phEnumDesc, String.valueOf(notifica.getDataInserimento()));
                                break;
                            case PH_NOTIFICA_APP_DATA_LETTURA:
                                break;
                            case PH_NOTIFICA_APP_CF_DESTINATARIO:
                            case PH_NOTIFICA_CF_DESTINATARIO:
                                result.put(phEnumDesc, notifica.getCfDestinatario());
                                break;
                            case PH_NOTIFICA_DATA_INVIO:
                                result.put(phEnumDesc, String.valueOf(notifica.getDataInvio()));
                                break;
                            case PH_NOTIFICA_OTP:
                                break;
                            case PH_NOTIFICA_RIF_CANALE:
                                result.put(phEnumDesc, notifica.getRifCanale());
                                break;
                            case PH_NOTIFICA_RIF_CANALE_CC:
                                result.put(phEnumDesc, notifica.getRifCanaleCc());
                                break;
                            case PH_NOTIFICA_STATO:
                                result.put(phEnumDesc, String.valueOf(notifica.getIdStatoNotifica()));
                                break;

                            // *** OGGETTO ISTANZA ***
                            case PH_NUM_CIVICO:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    String value = ubicazioneOggettoIstanzaList.stream()
                                            .map(UbicazioneOggettoIstanzaExtendedDTO::getNumCivico)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_OGG_IST_AREA_COD_AMMINISTRATIVO_2000:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaAreaProtettaList)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaAreaProtettaList)) {
                                        String value = oggettoIstanzaAreaProtettaList.stream()
                                                .map(OggettoIstanzaAreaProtettaExtendedDTO::getCodAmministrativo)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_AREA_DES_AREA_PROTETTA:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaAreaProtettaList)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaAreaProtettaList)) {
                                        String value = oggettoIstanzaAreaProtettaList.stream()
                                                .map(OggettoIstanzaAreaProtettaExtendedDTO::getTipoAreaProtetta)
                                                .map(TipoAreaProtettaDTO::getDesTipoAreaProtetta)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_AREA_DES_SITO_NATURA_2000:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaAreaProtettaList)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaNatura2000List)) {
                                        String value = oggettoIstanzaNatura2000List.stream()
                                                .map(OggettoIstanzaAreaProtettaExtendedDTO::getDesAreaProtetta)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_AREA_FLG_RICADE:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaAreaProtettaExtendedDTO> oggettoIstanzaAreaProtettaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaAreaProtettaList)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaAreaProtettaList)) {
                                        String value = oggettoIstanzaAreaProtettaList.stream()
                                                .map(OggettoIstanzaAreaProtettaExtendedDTO::getFlgRicade)
                                                .map(Object::toString)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_CAP_COMUNE:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                                        String value = ubicazioneOggettoIstanzaList.stream()
                                                .map(UbicazioneOggettoIstanzaExtendedDTO::getComune)
                                                .map(ComuneExtendedDTO::getCapComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_DEN_INDIRIZZO:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                                        String value = ubicazioneOggettoIstanzaList.stream()
                                                .map(UbicazioneOggettoIstanzaExtendedDTO::getDenIndirizzo)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_DENOM_COMUNE:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                                        String value = ubicazioneOggettoIstanzaList.stream()
                                                .map(UbicazioneOggettoIstanzaExtendedDTO::getComune)
                                                .map(ComuneExtendedDTO::getDenomComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_DENOM_PROVINCIA:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                                        String value = ubicazioneOggettoIstanzaList.stream()
                                                .map(UbicazioneOggettoIstanzaExtendedDTO::getComune)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getDenomProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_SIGLA_PROVINCIA:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getUbicazioneOggettoIstanza)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(ubicazioneOggettoIstanzaList)) {
                                        String value = ubicazioneOggettoIstanzaList.stream()
                                                .map(UbicazioneOggettoIstanzaExtendedDTO::getComune)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getSiglaProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_SITO_COD_AMMINISTRATIVO_2000:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaNatura2000List)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaNatura2000List)) {
                                        String value = oggettoIstanzaNatura2000List.stream()
                                                .map(OggettoIstanzaNatura2000ExtendedDTO::getCodAmministrativo)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_SITO_DES_SITO_2000:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaNatura2000List)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaNatura2000List)) {
                                        String value = oggettoIstanzaNatura2000List.stream()
                                                .map(OggettoIstanzaNatura2000ExtendedDTO::getTipoNatura2000)
                                                .map(TipoNatura2000DTO::getDesTipoNatura2000)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_SITO_DES_SITO_NATURA_2000:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaNatura2000List)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaNatura2000List)) {
                                        String value = oggettoIstanzaNatura2000List.stream()
                                                .map(OggettoIstanzaNatura2000ExtendedDTO::getDesSitoNatura2000)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_SITO_FLG_RICADE:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaNatura2000List)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaNatura2000List)) {
                                        String value = oggettoIstanzaNatura2000List.stream()
                                                .map(OggettoIstanzaNatura2000ExtendedDTO::getFlgRicade)
                                                .filter(Objects::nonNull)
                                                .map(Object::toString).collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OGG_IST_SITO_NUM_DISTANZA:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    List<OggettoIstanzaNatura2000ExtendedDTO> oggettoIstanzaNatura2000List = oggettoIstanzaUbicazioneList
                                            .stream()
                                            .map(OggettoIstanzaUbicazioneExtendedDTO::getOggettoIstanzaNatura2000List)
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    if (CollectionUtils.isNotEmpty(oggettoIstanzaNatura2000List)) {
                                        String value = oggettoIstanzaNatura2000List.stream()
                                                .map(OggettoIstanzaNatura2000ExtendedDTO::getNumDistanza)
                                                .map(Object::toString)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;

                            case PH_OGG_IST_DEN_OGGETTO:
                                if (CollectionUtils.isNotEmpty(oggettoIstanzaUbicazioneList)) {
                                    String denOggetto = oggettoIstanzaUbicazioneList.get(0).getDenOggetto();

                                    result.put(phEnumDesc, denOggetto);

                                }
                                break;

                            // *** RICHIEDENTE ***
                            case PH_RICHIEDENTE_IST_CAP_COMUNE_RESIDENZA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                            .map(ComuneExtendedDTO::getCapComune)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_CITTA_ESTERA_RESIDENZA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getCittaEsteraResidenza)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_DENOM_COMUNE_RESIDENZA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                            .map(ComuneExtendedDTO::getDenomComune)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_DENOM_PROVINCIA_RESIDENZA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                            .map(ComuneExtendedDTO::getProvincia)
                                            .map(ProvinciaExtendedDTO::getDenomProvincia)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_DES_EMAIL:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getDesEmail)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_DES_LOCALITA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getDesLocalita)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_DES_PEC:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    /*
                                     * String value = richiedenteList.stream()
                                     * .map(SoggettoIstanzaExtendedDTO::getDesPec)
                                     * .map(Object::toString)
                                     * .collect(Collectors.joining(", "));
                                     * value = StringUtils.isNotBlank(value) &&
                                     * ", ".equals(value.substring(value.length() - 2)) ? value.substring(0,
                                     * value.length() - 2) : value;
                                     * result.put(phEnumDesc, value);
                                     */

                                    result.put(phEnumDesc, richiedenteList.stream()
                                            .map(soggetto -> StringUtils.isNotBlank(soggetto.getDesPec())
                                                    ? soggetto.getDesPec()
                                                    : soggetto.getDesEmail())
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(";")));
                                }
                                break;
                            case PH_RICHIEDENTE_IST_ID_NAZIONE_RESIDENZA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getIdNazioneResidenza)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_INDIRIZZO_SOGGETTO:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getIndirizzoSoggetto)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_NUM_CELLULARE:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getNumCellulare)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_NUM_CIVICO_INDIRIZZO:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getNumCivicoIndirizzo)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;
                            case PH_RICHIEDENTE_IST_SIGLA_PROVINCIA_RESIDENZA:
                                richiedenteList = CollectionUtils.isEmpty(richiedenteList)
                                        ? getSoggettoRichiedenteIstanzaList()
                                        : richiedenteList;
                                if (CollectionUtils.isNotEmpty(richiedenteList)) {
                                    String value = richiedenteList.stream()
                                            .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                            .map(ComuneExtendedDTO::getProvincia)
                                            .map(ProvinciaExtendedDTO::getSiglaProvincia)
                                            .map(Object::toString)
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.joining(", "));
                                    value = StringUtils.isNotBlank(value)
                                            && ", ".equals(value.substring(value.length() - 2))
                                                    ? value.substring(0, value.length() - 2)
                                                    : value;
                                    result.put(phEnumDesc, value);
                                }
                                break;

                            // *** SOGGETTO ISTANZA TITOLARE (id_padre non valorizzato) ***
                            case PH_SOGG_IST_CAP_COMUNE_NASCITA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneNascita)
                                                .map(ComuneExtendedDTO::getCapComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_CAP_COMUNE_RESIDENZA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                                .map(ComuneExtendedDTO::getCapComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_CAP_COMUNE_SEDE_LEG:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneSedeLegale)
                                                .map(ComuneExtendedDTO::getCapComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_CITTA_ESTERA_RESIDENZA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getCittaEsteraResidenza)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_CITTA_ESTERA_SEDE_LEGALE:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getCittaEsteraSedeLegale)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DENOM_COMUNE_NASCITA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneNascita)
                                                .map(ComuneExtendedDTO::getDenomComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DENOM_COMUNE_RESIDENZA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                                .map(ComuneExtendedDTO::getDenomComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DENOM_COMUNE_SEDE_LEG:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneSedeLegale)
                                                .map(ComuneExtendedDTO::getDenomComune)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DENOM_PROVINCIA_NASCITA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneNascita)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getDenomProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DENOM_PROVINCIA_RESIDENZA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getDenomProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DENOM_PROVINCIA_SEDE_LEGALE:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneSedeLegale)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getDenomProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_DES_LOCALITA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getDesLocalita)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_ID_NAZIONE_RESIDENZA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getIdNazioneResidenza)
                                                .map(Object::toString)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_ID_NAZIONE_SEDE_LEGALE:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getIdNazioneSedeLegale)
                                                .map(Object::toString)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_INDIRIZZO_SOGGETTO:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getIndirizzoSoggetto)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_NUM_CIVICO_INDIRIZZO:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getNumCivicoIndirizzo)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_SIGLA_PROVINCIA_NASCITA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneNascita)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getSiglaProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_SIGLA_PROVINCIA_RESIDENZA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneResidenza)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getSiglaProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOGG_IST_SIGLA_PROVINCIA_SEDE_LEGALE:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getComuneSedeLegale)
                                                .map(ComuneExtendedDTO::getProvincia)
                                                .map(ProvinciaExtendedDTO::getSiglaProvincia)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_CF_SOGGETTO:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getCfSoggetto)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_CITTA_ESTERA_NASCITA:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getCittaEsteraNascita)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_COGNOME:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getCognome)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_DATA_NASCITA_SOGGETTO:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getDataNascitaSoggetto)
                                                .map(Object::toString)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_DEN_SOGGETTO:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getDenSoggetto)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_DES_EMAIL:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        result.put(phEnumDesc, soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getDesEmail)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(";")));
                                    }
                                }
                                break;
                            case PH_SOG_IST_DES_PEC:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        /*
                                         * result.put(phEnumDesc, soggettoIstanzaTitolareList.stream()
                                         * .map(SoggettoIstanzaExtendedDTO::getDesPec)
                                         * .collect(Collectors.joining(";")));
                                         */
                                        result.put(phEnumDesc, soggettoIstanzaTitolareList.stream()
                                                .map(soggetto -> soggetto.getDesPec() != null
                                                        && !soggetto.getDesPec().isEmpty() ? soggetto.getDesPec()
                                                                : soggetto.getDesEmail())
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(";")));
                                    }
                                }
                                break;
                            case PH_SOG_IST_NOME:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getNome)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_SOG_IST_NUM_CELLULARE:
                                if (CollectionUtils.isNotEmpty(soggettoIstanzaList)) {
                                    List<SoggettoIstanzaExtendedDTO> soggettoIstanzaTitolareList = getSoggettoTitolareIstanzaList();
                                    if (CollectionUtils.isNotEmpty(soggettoIstanzaTitolareList)) {
                                        String value = soggettoIstanzaTitolareList.stream()
                                                .map(SoggettoIstanzaExtendedDTO::getNumCellulare)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.joining(", "));
                                        value = StringUtils.isNotBlank(value)
                                                && ", ".equals(value.substring(value.length() - 2))
                                                        ? value.substring(0, value.length() - 2)
                                                        : value;
                                        result.put(phEnumDesc, value);
                                    }
                                }
                                break;
                            case PH_OTP_NOTIFICA:
                                if (StringUtils.isNotBlank(uidRichiesta)) {
                                    AccreditamentoDTO accreditamento = accreditamentoDAO
                                            .loadAccreditamentoByUid(uidRichiesta);
                                    if (accreditamento != null) {
                                        result.put(phEnumDesc, accreditamento.getCodeVerifica());
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.error(debugInfo + e);
                }
            }
        }
        LOGGER.debug(debugInfo + "END");
        return result;
    }

    /**
     * Sets configs.
     */
    private static void setConfigs() {
        configs = configs == null || configs.isEmpty() ? configurazioneDAO.loadConfigByKeyList(confKeys) : configs;
    }

    /**
     * Gets istanza competenza principale list.
     *
     * @return the istanza competenza principale list
     */
    private static List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaPrincipaleList() {
        return CollectionUtils.isNotEmpty(istanzaCompetenzaList) ? istanzaCompetenzaList.stream()
                .filter(comp -> Boolean.TRUE.equals(comp.getFlgAutoritaPrincipale()))
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Gets istanza competenza secondaria list.
     *
     * @return the istanza competenza secondaria list
     */
    private static List<IstanzaCompetenzaExtendedDTO> getIstanzaCompetenzaSecondariaList() {
        return CollectionUtils.isNotEmpty(istanzaCompetenzaList) ? istanzaCompetenzaList.stream()
                .filter(comp -> Boolean.FALSE.equals(comp.getFlgAutoritaPrincipale()))
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Gets soggetto titolare istanza list.
     *
     * @return the soggetto titolare istanza list
     */
    private static List<SoggettoIstanzaExtendedDTO> getSoggettoTitolareIstanzaList() {
        return CollectionUtils.isNotEmpty(soggettoIstanzaList) ? soggettoIstanzaList.stream()
                .filter(sogg -> sogg.getIdSoggettoPadre() == null)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Gets soggetto richiedente istanza list.
     *
     * @return the soggetto richiedente istanza list
     */
    private static List<SoggettoIstanzaExtendedDTO> getSoggettoRichiedenteIstanzaList() {
        return CollectionUtils.isNotEmpty(soggettoIstanzaList) ? soggettoIstanzaList.stream()
                .filter(sogg -> sogg.getIdSoggettoPadre() != null)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    /**
     * Gets istanza.
     *
     * @param idIstanza the id istanza
     * @return the istanza
     */
    private static List<IstanzaExtendedDTO> getIstanza(Long idIstanza) {
        istanzaList = idIstanza != null ? istanzaDAO.loadIstanza(idIstanza) : null;
        return istanzaList;
    }

    /**
     * Initialize beans.
     */
    private static void initializeBeans() {
        dizionarioPlaceholderDAO = (DizionarioPlaceholderDAO) SpringApplicationContextHelper
                .getBean("dizionarioPlaceholderDAO");
        accreditamentoDAO = (AccreditamentoDAO) SpringApplicationContextHelper.getBean("accreditamentoDAO");
        compilanteDAO = (CompilanteDAO) SpringApplicationContextHelper.getBean("compilanteDAO");
        configurazioneDAO = (ConfigurazioneDAO) SpringApplicationContextHelper.getBean("configurazioneDAO");
        istanzaDAO = (IstanzaDAO) SpringApplicationContextHelper.getBean("istanzaDAO");
        istanzaAttoreDAO = (IstanzaAttoreDAO) SpringApplicationContextHelper.getBean("istanzaAttoreDAO");
        istanzaCompetenzaDAO = (IstanzaCompetenzaDAO) SpringApplicationContextHelper.getBean("istanzaCompetenzaDAO");
        notificaDAO = (NotificaDAO) SpringApplicationContextHelper.getBean("notificaDAO");
        oggettoIstanzaDAO = (OggettoIstanzaDAO) SpringApplicationContextHelper.getBean("oggettoIstanzaDAO");
        oggettoIstanzaAreaProtettaDAO = (OggettoIstanzaAreaProtettaDAO) SpringApplicationContextHelper
                .getBean("oggettoIstanzaAreaProtettaDAO");
        oggettoIstanzaNatura2000DAO = (OggettoIstanzaNatura2000DAO) SpringApplicationContextHelper
                .getBean("oggettoIstanzaNatura2000DAO");
        soggettoIstanzaDAO = (SoggettoIstanzaDAO) SpringApplicationContextHelper.getBean("soggettoIstanzaDAO");
        tipoCompetenzaDAO = (TipoCompetenzaDAO) SpringApplicationContextHelper.getBean("tipoCompetenzaDAO");
        oggettiIstanzaService = (OggettiIstanzaService) SpringApplicationContextHelper.getBean("oggettiIstanzaService");
    }

}
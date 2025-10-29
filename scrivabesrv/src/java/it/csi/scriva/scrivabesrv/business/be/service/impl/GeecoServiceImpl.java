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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.GeecoServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.Configuration;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.GeecoDefaultProperty;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaFiglioDAO;
import it.csi.scriva.scrivabesrv.business.be.service.ConfigurazioniService;
import it.csi.scriva.scrivabesrv.business.be.service.GeecoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiIstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.TipologieOggettoService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoUrlDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoUrlReqDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaFiglioDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.InformazioniScrivaEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipologiaOggettoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Geeco service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class GeecoServiceImpl extends BaseServiceImpl implements GeecoService {

    private static final String ID_LAYERS = "id_layers";
    private static final String LVL_ESTRAZIONE = "LivelloEstrazioneOggettoPadre";
    private static final String QUIT_URL_GEECO = "progetto-geeco-istanza?idIstanza=";

    private final String className = this.getClass().getSimpleName();

    /**
     * The Geeco service helper.
     */
    @Autowired
    GeecoServiceHelper geecoServiceHelper;

    /**
     * The Adempimento tipo oggetto service.
     */
    @Autowired
    AdempimentoTipoOggettoServiceImpl adempimentoTipoOggettoService;

    /**
     * The Configurazioni service.
     */
    @Autowired
    ConfigurazioniService configurazioniService;

    /**
     * The Istanza service.
     */
    @Autowired
    IstanzaService istanzaService;

    /**
     * The Tipologie oggetto service.
     */
    @Autowired
    TipologieOggettoService tipologieOggettoService;

    /**
     * The Oggetti istanza service.
     */
    @Autowired
    OggettiIstanzaService oggettiIstanzaService;

    /**
     * The Oggetto istanza figlio dao.
     */
    @Autowired
    OggettoIstanzaFiglioDAO oggettoIstanzaFiglioDAO;

    /**
     * The Adempimento config dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * Gets geeco url.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param oggetto          the oggetto
     * @param attoreScriva     the attore scriva
     * @return the geeco url
     */
    @Override
    public String getGeecoUrl(Long idOggettoIstanza, OggettoIstanzaGeecoDTO oggetto, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idOggettoIstanza : [" + idOggettoIstanza + "]\noggetto : \n" + oggetto + "\n");
        String url = "";

        //Configuration configuration = geecoServiceHelper.getGeecoConfiguration(idOggettoIstanza, oggetto.getDenOggettoIstanza(), oggetto.getIdAdempimento(), idRuoloApplicativo);
        Configuration configuration = geecoServiceHelper.getGeecoConfiguration(idOggettoIstanza, oggetto.getDenOggettoIstanza(), oggetto.getIdAdempimento(), attoreScriva.getProfiloAppIstanza(), attoreScriva.getComponente());

        /*
        IstanzaExtendedDTO istanza = istanzaService.getIstanza(oggetto.getIdIstanza());
        List<Long> idOggettoIstanzaList = List.of(idOggettoIstanza);
        List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList = oggettiIstanzaService.getOggettoIstanzaGeeco(oggetto.getIdIstanza(), idOggettoIstanzaList, null, null);
        GeecoDefaultProperty geecoDefaultProperty = getGeecoDefaultProperty(attoreScriva, idOggettoIstanzaList, null, istanza, oggettoIstanzaGeecoList);
        Configuration configuration = geecoServiceHelper.getGeecoConfiguration(geecoDefaultProperty, oggetto.getIdAdempimento());
         */

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(configuration);
            logDebug(className, json);
        } catch (JsonProcessingException e) {
            logError(className, e);
        }

        try {
            url = geecoServiceHelper.getGeecoUrl(configuration);
        } catch (GenericException e) {
            logError(className, e);
        }

        logEnd(className);
        return url;
    }

    /**
     * Gets geeco url.
     *
     * @param geecoUrlReq the geeco url req dto
     * @return the list
     */
    @Override
    public GeecoUrlDTO getUrl(GeecoUrlReqDTO geecoUrlReq, AttoreScriva attoreScriva) {
        logBeginInfo(className, geecoUrlReq);
        GeecoUrlDTO geecoUrl = new GeecoUrlDTO();
        geecoUrl.setUrl("");
        Long idIstanza = geecoUrlReq.getIdIstanza();
        Long idSoggetto = geecoUrlReq.getIdSoggetto();
        String chiaveConfig = geecoUrlReq.getChiaveConfig();
        String codTipologiaOggetto = geecoUrlReq.getCodTipologiaOggetto();
        List<Long> idOggettoIstanzaList = geecoUrlReq.getIdOggettoIstanzaList();
        String quitUrlParams = geecoUrlReq.getQuitUrlParams();
        List<Long> idLayerList = new ArrayList<>();
        Long indLivelloEstrazione = null;

        //Recupero dell'istanza
        IstanzaExtendedDTO istanza = istanzaService.getIstanza(idIstanza);
        //Recupero adempimento dall'istanza
        String codAdempimento = istanza.getAdempimento().getCodAdempimento();

        //Se presente "chiave_config" ricerca su scriva_r_adempi_config (id_layers, LivelloEstrazioneOggettoPadre)
        if (StringUtils.isNotBlank(chiaveConfig)) {
            List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByAdempimentoInformazioneChiave(codAdempimento, InformazioniScrivaEnum.OGGETTO.name(), chiaveConfig);
            List<AdempimentoConfigDTO> configIdLayers = getAdempimentoConfigByKey(adempimentoConfigList, ID_LAYERS);
            if (configIdLayers != null && !configIdLayers.isEmpty()) {
                String configValue = configIdLayers.get(0).getValore();
                String[] idLayerSplit = configValue.split("\\|");
                for (String id : idLayerSplit) {
                    idLayerList.add(Long.parseLong(id));
                }
            }
            List<AdempimentoConfigDTO> configLvlEstrazione = getAdempimentoConfigByKey(adempimentoConfigList, LVL_ESTRAZIONE);
            if (CollectionUtils.isNotEmpty(configLvlEstrazione)) {
                indLivelloEstrazione = Long.parseLong(configLvlEstrazione.get(0).getValore());
            }
        }

        //Estrazione di tutti gli oggetti interessati
        //List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList = oggettiIstanzaService.getOggettoIstanzaGeeco(idIstanza, idOggettoIstanzaList, idLayerList, indLivelloEstrazione);
        //List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList = oggettiIstanzaService.getOggettoIstanzaGeeco(idIstanza, idOggettoIstanzaList, null, CollectionUtils.isEmpty(idOggettoIstanzaList) ? indLivelloEstrazione : null);
        List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList = oggettiIstanzaService.getOggettoIstanzaGeeco(idIstanza, idOggettoIstanzaList, null, null);

        // parametri per il recupero della configurazione
        try {
            GeecoDefaultProperty geecoDefaultProperty = getGeecoDefaultProperty(attoreScriva, idSoggetto, idOggettoIstanzaList, idLayerList, istanza, oggettoIstanzaGeecoList, codTipologiaOggetto, quitUrlParams);
            Configuration configuration = geecoServiceHelper.getGeecoConfiguration(geecoDefaultProperty, istanza.getAdempimento().getIdAdempimento());
            geecoUrl.setUrl(geecoServiceHelper.getGeecoUrl(configuration));
        } catch (GenericException e) {
            logError(className, e);
        }

        logEnd(className);

        return geecoUrl;
    }

    /**
     * Validate error dto.
     *
     * @param geecoUrlReq the geeco url req
     * @return the error dto
     */
    @Override
    public ErrorDTO validate(GeecoUrlReqDTO geecoUrlReq) {
        logBegin(className);
        Map<String, String> details = new HashMap<>();
        if (geecoUrlReq.getIdIstanza() == null) {
            details.put("id_istanza", ValidationResultEnum.MANDATORY.getDescription());
            return getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }
        /*
        if (CollectionUtils.isEmpty(geecoUrlReq.getIdOggettoIstanzaList()) && StringUtils.isBlank(geecoUrlReq.getChiaveConfig())) {
            details.put("id_oggetto_istanza OR chiave_config", ValidationResultEnum.MANDATORY.getDescription());
        }
        */

        if ((CollectionUtils.isNotEmpty(geecoUrlReq.getIdOggettoIstanzaList()) || StringUtils.isNotBlank(geecoUrlReq.getChiaveConfig()))
                && StringUtils.isNotBlank(geecoUrlReq.getCodTipologiaOggetto())
        ) {
            details.put("cod_tipologia_oggetto together with id_oggetto_istanza OR chiave_config", ValidationResultEnum.INVALID.getDescription());
            return getErrorManager().getError("400", "E070", "I dati inseriti non sono corretti.", details, null);
        }
        return null;
    }


    /**
     * Gets adempimento config by key.
     *
     * @param adempimentoConfigList the adempimento config list
     * @param key                   the key
     * @return the adempimento config by key
     */
    private List<AdempimentoConfigDTO> getAdempimentoConfigByKey(List<AdempimentoConfigDTO> adempimentoConfigList, String key) {
        logBeginInfo(className, adempimentoConfigList);
        if (CollectionUtils.isNotEmpty(adempimentoConfigList)) {
            return adempimentoConfigList.stream()
                    .filter(config -> config.getChiave().contains(key))
                    .collect(Collectors.toList());
        }
        return adempimentoConfigList;
    }

    /**
     * Gets geeco default property.
     *
     * @param attoreScriva            the attore scriva
     * @param idOggettoIstanzaList    the id oggetto istanza list
     * @param idLayerList             the id layer list
     * @param istanza                 the istanza
     * @param oggettoIstanzaGeecoList the oggetto istanza geeco list
     * @return the geeco default property
     */
    private GeecoDefaultProperty getGeecoDefaultProperty(AttoreScriva attoreScriva, Long idSoggetto, List<Long> idOggettoIstanzaList, List<Long> idLayerList, IstanzaExtendedDTO istanza, List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList, String codTipologiaOggetto, String quitUrlParams) {
        logBegin(className);
        GeecoDefaultProperty geecoDefaultProperty = new GeecoDefaultProperty();
        geecoDefaultProperty.setIdIstanza(istanza.getIdIstanza());
        geecoDefaultProperty.setIdSoggetto(idSoggetto);
        geecoDefaultProperty.setIdOggettoIstanzaList(getIdOggettoIstanzaList(idOggettoIstanzaList, oggettoIstanzaGeecoList));
        geecoDefaultProperty.setDenOggetto(oggettoIstanzaGeecoList != null && oggettoIstanzaGeecoList.size() == 1 ? oggettoIstanzaGeecoList.get(0).getDenOggettoIstanza() : "");
        geecoDefaultProperty.setDesOggetto("");
        geecoDefaultProperty.setAttoreScriva(attoreScriva);
        geecoDefaultProperty.setIdOggettoIstanzaPadre(getIdOggettoIstanzaPadre(oggettoIstanzaGeecoList));
        geecoDefaultProperty.setFlgAggiornaOggetto(CollectionUtils.isNotEmpty(idOggettoIstanzaList) ? Boolean.TRUE : Boolean.FALSE);
        geecoDefaultProperty.setIdLayerList(idLayerList);

        Map<Long, String> idCodTipoOggettoMap = getIdTipologiaOggetto(geecoDefaultProperty, istanza.getAdempimento().getCodAdempimento(), idLayerList, codTipologiaOggetto);
        if (idCodTipoOggettoMap != null && !idCodTipoOggettoMap.isEmpty()) {
            if (idCodTipoOggettoMap.size() > 1) {
                geecoDefaultProperty.setTipologiaOggettoList(new ArrayList<>(idCodTipoOggettoMap.values()));
            } else {
                geecoDefaultProperty.setIdTipologiaOggetto(idCodTipoOggettoMap.keySet().stream().findFirst().orElse(0L));
            }
        }
        geecoDefaultProperty.setQuitUrl(getQuitUrl(istanza.getIdIstanza(), attoreScriva.getComponente()) + (StringUtils.isNotBlank(quitUrlParams) ? quitUrlParams : ""));
        logEnd(className);
        return geecoDefaultProperty;
    }

    /**
     * Gets id oggetto istanza list.
     *
     * @param idOggettoIstanzaList    the id oggetto istanza list
     * @param oggettoIstanzaGeecoList the oggetto istanza geeco list
     * @return the id oggetto istanza list
     */
    private List<Long> getIdOggettoIstanzaList(List<Long> idOggettoIstanzaList, List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList) {
        logBegin(className);
        List<Long> result = idOggettoIstanzaList;
        if (CollectionUtils.isEmpty(idOggettoIstanzaList) && CollectionUtils.isNotEmpty(oggettoIstanzaGeecoList)) {
            result = oggettoIstanzaGeecoList.stream()
                    .filter(oi -> oi.getIndLivello() > 1)
                    .map(OggettoIstanzaGeecoDTO::getIdOggettoIstanza)
                    .collect(Collectors.toList());
        }
        logEnd(className);
        return result;
    }

    /**
     * Get id oggetto istanza padre long.
     *
     * @param oggettoIstanzaGeecoList the oggetto istanza geeco list
     * @return the long
     */
    private Long getIdOggettoIstanzaPadre(List<OggettoIstanzaGeecoDTO> oggettoIstanzaGeecoList) {
        logBegin(className);
        Long idOggettoIstanzaPadre = null;
        if (CollectionUtils.isNotEmpty(oggettoIstanzaGeecoList)) {
            List<Long> idOggettoIstanzaPadreList = oggettoIstanzaGeecoList.stream()
                    .filter(oi -> oi.getIndLivello() <= 1)
                    .map(OggettoIstanzaGeecoDTO::getIdOggettoIstanza)
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(idOggettoIstanzaPadreList)) {
                idOggettoIstanzaPadre = idOggettoIstanzaPadreList.get(0);
            } else {
                //Estraggo il padre sulla base dei figli
                List<OggettoIstanzaFiglioDTO> oggettoIstanzaFiglioList = oggettoIstanzaFiglioDAO.loadOggettoIstanzaFiglioByIdOggIstFiglio(oggettoIstanzaGeecoList.get(0).getIdOggettoIstanza());
                idOggettoIstanzaPadre = CollectionUtils.isNotEmpty(oggettoIstanzaFiglioList) ? oggettoIstanzaFiglioList.get(0).getIdOggettoIstanzaPadre() : null;
            }
        }
        logEnd(className);
        return idOggettoIstanzaPadre;
    }

    /**
     * Gets id tipologia oggetto.
     *
     * @param codAdempimento the cod adempimento
     * @param idLayerList    the id layer list
     * @return the id tipologia oggetto
     */
    private Map<Long, String> getIdTipologiaOggetto(GeecoDefaultProperty geecoDefaultProperty, String codAdempimento, List<Long> idLayerList, String codTipologiaOggetto) {
        logBegin(className);
        Map<Long, String> mapResult = null;
        List<TipologiaOggettoDTO> tipologiaOggettoList = idLayerList != null && !idLayerList.isEmpty() ? tipologieOggettoService.loadTipologiaOggettoByIdLayers(idLayerList) : null;
        if (CollectionUtils.isNotEmpty(tipologiaOggettoList)) {
            geecoDefaultProperty.setTipologiaOggettoObjList(tipologiaOggettoList);
            mapResult = tipologiaOggettoList.stream().collect(Collectors.toMap(TipologiaOggettoDTO::getIdTipologiaOggetto, TipologiaOggettoDTO::getCodTipologiaOggetto));
        }
        if (mapResult == null) {
            List<TipologiaOggettoExtendedDTO> tipologiaOggettoExtList = tipologieOggettoService.loadTipologiaOggettoByCodeAdempimento(codAdempimento);
            if (StringUtils.isNotBlank(codTipologiaOggetto) && CollectionUtils.isNotEmpty(tipologiaOggettoExtList)) {
                if (TipologiaOggettoEnum.DEFAULT.name().equalsIgnoreCase(codTipologiaOggetto)) {
                    /*
                        Se il servizio riceve il parametro DEFAULT deve individuare la tipologia oggetto verificando la configurazione sulla tabella scriva_r_adempi_tipo_oggetto (selezionando l’unica occorrenza o quella con flg_assegna=1).
                    */
                    tipologiaOggettoExtList = tipologiaOggettoExtList.size() > 1 ? tipologiaOggettoExtList.stream().filter(tipologiaOggetto -> Boolean.TRUE.equals(tipologiaOggetto.getFlgAssegna())).collect(Collectors.toList()) : tipologiaOggettoExtList;
                } else if (!TipologiaOggettoEnum.TUTTI.name().equalsIgnoreCase(codTipologiaOggetto)) {
                    /*
                        Se il servizio riceve il parametro valorizzato con TUTTI deve estrarre tutte le tipologie di oggetto configurate per l’adempimento di interesse.
                        La tipologia deve essere selezionata dall’utente tramite la maschera GEECO.
                        Se il parametro non è valorizzato con TUTTI o DEFAULT
                    */
                    tipologiaOggettoExtList = tipologiaOggettoExtList.stream().filter(tipologiaOggetto -> codTipologiaOggetto.equalsIgnoreCase(tipologiaOggetto.getCodTipologiaOggetto())).collect(Collectors.toList());
                }
            }
            if (CollectionUtils.isNotEmpty(tipologiaOggettoExtList)) {
                mapResult = tipologiaOggettoExtList.stream().collect(Collectors.toMap(TipologiaOggettoExtendedDTO::getIdTipologiaOggetto, TipologiaOggettoExtendedDTO::getCodTipologiaOggetto));
                geecoDefaultProperty.setTipologiaOggettoObjList(CollectionUtils.isNotEmpty(tipologiaOggettoExtList) ? tipologiaOggettoExtList.stream().map(TipologiaOggettoExtendedDTO::getDTO).collect(Collectors.toList()) : null);
            }
        }
        logEnd(className);
        return mapResult;
    }

    /**
     * Gets quit url.
     *
     * @param idIstanza     the id istanza
     * @param componenteApp the componente app
     * @return the quit url
     */
    private String getQuitUrl(Long idIstanza, String componenteApp) {
        logBegin(className);
        String url = null;
        if (StringUtils.isNotBlank(componenteApp)) {
            List<ConfigurazioneDTO> configurazioneList = configurazioniService.loadConfigurazioneByKey("SCRIVA_" + componenteApp + "_URL");
            url = configurazioneList != null && !configurazioneList.isEmpty() ? configurazioneList.get(0).getValore() + QUIT_URL_GEECO + idIstanza : null;
        }
        logEnd(className);
        return url;
    }

}
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
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.TemplateService;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * The type Template service.
 */
@Component
public class TemplateServiceImpl extends BaseApiServiceImpl implements TemplateService {

    public static final String SEARCH_PATH = "] - searchPath: [";
    public static final String PARAMETRO_IN_INPUT_ID_ISTANZA = "Parametro in input idIstanza:\n";
    public static final String COD_TIPOLOGIA_ALLEGATO = "\ncodTipologiaAllegato: [";
    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanza dao.
     */
    IstanzaDAO istanzaDAO;

    /**
     * The Configurazione dao.
     */
    ConfigurazioneDAO configurazioneDAO;

    @Autowired
    public TemplateServiceImpl(IstanzaDAO istanzaDAO, ConfigurazioneDAO configurazioneDAO) {
        this.istanzaDAO = istanzaDAO;
        this.configurazioneDAO = configurazioneDAO;
    }

    /**
     * Gets istanza.
     *
     * @param idIstanza the id istanza
     * @return the istanza
     */
    IstanzaExtendedDTO getIstanza(Long idIstanza) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
        return istanzaList != null && !istanzaList.isEmpty() ? istanzaList.get(0) : null;
    }

    /**
     * Gets template path.
     *
     * @return the template path
     */
    @Override
    public String getTemplatePath(@NotNull String tipoAdempimento) {
        logBegin(className);
        String result = null;
        try {
            List<String> keyConfigList = Arrays.asList(Constants.CONFIG_KEY_PATH_ROOT, Constants.CONFIG_KEY_PATH_TEMPLATE_MODULO);
            Map<String, String> configurazioneMap = configurazioneDAO.loadConfigByKeyList(keyConfigList);
            if (configurazioneMap != null && !configurazioneMap.isEmpty() &&
                    configurazioneMap.containsKey(Constants.CONFIG_KEY_PATH_ROOT) &&
                    configurazioneMap.containsKey(Constants.CONFIG_KEY_PATH_TEMPLATE_MODULO)) {
                String path = configurazioneMap.get(Constants.CONFIG_KEY_PATH_ROOT) + File.separator + tipoAdempimento.toLowerCase() + configurazioneMap.get(Constants.CONFIG_KEY_PATH_TEMPLATE_MODULO);
                //result = Files.isDirectory(Path.of(path)) ? path : "/templates" + File.separator + tipoAdempimento.toLowerCase(); Jira 1621
                if (Files.isDirectory(Path.of(path))) {
                    result = path;
                } else {
                    throw new IllegalStateException("Il percorso dei templates configurato a DB non è valido: " + path);
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
     * Gets template filename.
     *
     * @param idIstanza            the id istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param searchPath           the search path
     * @return the template filename
     */
    @Override
    public String getTemplateFilename(Long idIstanza, String codTipologiaAllegato, String searchPath) {
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "] - codTipologiaAllegato: [" + codTipologiaAllegato + SEARCH_PATH + searchPath + "]";
        logBeginInfo(className, inputParam);
        String result = null;
        try {
            IstanzaExtendedDTO istanza = this.getIstanza(idIstanza);
            result = this.getTemplateFilename(istanza, codTipologiaAllegato, searchPath);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets template filename.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param searchPath           the search path
     * @return the template filename
     */
    @Override
    public String getTemplateFilename(IstanzaExtendedDTO istanza, String codTipologiaAllegato, String searchPath) {
        String inputParam = PARAMETRO_IN_INPUT_ID_ISTANZA + istanza + COD_TIPOLOGIA_ALLEGATO + codTipologiaAllegato + SEARCH_PATH + searchPath + "]";
        logBeginInfo(className, inputParam);
        try {
            String tipoAdempimento = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(istanza).getAdempimento())).getTipoAdempimento() != null ? istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento() : null;
            String adempimento = istanza.getAdempimento().getCodAdempimento();

            // 1 search criteria
            String filename = tipoAdempimento + "_" + adempimento + "_" + codTipologiaAllegato + Constants.EXT_DOCX;
            String filePath = searchPath + File.separator + filename;
            File file = new File(filePath);
            if (file.exists() && !file.isDirectory()) {
                return filename;
            }

            // 2 search criteria
            filename = tipoAdempimento + "_" + codTipologiaAllegato + Constants.EXT_DOCX;
            filePath = searchPath + File.separator + filename;
            file = new File(filePath);
            if (file.exists() && !file.isDirectory()) {
                return filename;
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return getTemplateFilenameWithInputStream(istanza, codTipologiaAllegato, searchPath);
    }

    /**
     * Gets template filename with input stream.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param searchPath           the search path
     * @return the template filename with input stream
     */
    @Override
    public String getTemplateFilenameWithInputStream(IstanzaExtendedDTO istanza, String codTipologiaAllegato, String searchPath) {
        String inputParam = PARAMETRO_IN_INPUT_ID_ISTANZA + istanza + COD_TIPOLOGIA_ALLEGATO + codTipologiaAllegato + SEARCH_PATH + searchPath + "]";
        logBeginInfo(className, inputParam);
        try {
            String tipoAdempimento = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(istanza).getAdempimento())).getTipoAdempimento() != null ? istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento() : null;
            String adempimento = istanza.getAdempimento().getCodAdempimento();

            // 1 search criteria
            String filename = tipoAdempimento + "_" + adempimento + "_" + codTipologiaAllegato + Constants.EXT_DOCX;
            String filePath = searchPath + File.separator + filename;
            if (TemplateServiceImpl.class.getResource(filePath) != null) {
                return filename;
            } else {
                logInfo(className, "getResource : NOT FOUND  " + filePath);
            }

            // 2 search criteria
            filename = tipoAdempimento + "_" + codTipologiaAllegato + Constants.EXT_DOCX;
            filePath = searchPath + File.separator + filename;
            if (TemplateServiceImpl.class.getResource(filePath) != null) {
                return filename;
            } else {
                logInfo(className, "getResource : NOT FOUND  " + filePath);
            }

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }

    /**
     * Gets template filename with input stream.
     *
     * @param istanza              the istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param codTipoEvento        the cod tipo evento
     * @param searchPath           the search path
     * @return the template filename with input stream
     */
    @Override
    public String getTemplateFilenameWithInputStream(IstanzaExtendedDTO istanza, String codTipologiaAllegato, String codTipoEvento, String searchPath) {
        String inputParam = PARAMETRO_IN_INPUT_ID_ISTANZA + istanza + COD_TIPOLOGIA_ALLEGATO + codTipologiaAllegato + SEARCH_PATH + searchPath + "]";
        logBeginInfo(className, inputParam);
        try {
            String tipoAdempimento = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(istanza).getAdempimento())).getTipoAdempimento() != null ? istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento() : null;
            String adempimento = istanza.getAdempimento().getCodAdempimento();

            // 1 search criteria
            String filename = tipoAdempimento + "_" + adempimento + "_" + codTipoEvento + codTipologiaAllegato + Constants.EXT_DOCX;
            String filePath = searchPath + File.separator + filename;
            if (TemplateServiceImpl.class.getClassLoader().getResourceAsStream(filePath) != null) {
                return filename;
            }
            logInfo(className, "Not found " + filePath);

            // 2 search criteria
            filename = tipoAdempimento + "_" + codTipoEvento + codTipologiaAllegato + Constants.EXT_DOCX;
            filePath = searchPath + File.separator + filename;
            if (TemplateServiceImpl.class.getClassLoader().getResourceAsStream(filePath) != null) {
                return filename;
            }
            logInfo(className, "Not found " + filePath);

            // 3 search criteria
            filename = codTipoEvento + codTipologiaAllegato + Constants.EXT_DOCX;
            filePath = searchPath + File.separator + filename;
            if (TemplateServiceImpl.class.getClassLoader().getResourceAsStream(filePath) != null) {
                return filename;
            }
            logInfo(className, "Not found " + filePath);

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }


}
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

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.exception.UserNotAllowedException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioCompetenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioProfiloDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoOggettoAppDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoProfiloDAO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioAutorizzatoDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioProfiloExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoProfiloDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Funzionario manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class FunzionarioManager {

    /**
     * The constant LOGGER.
     */
    protected static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    private final String className = this.getClass().getSimpleName();

    private FunzionarioAutorizzatoDTO funzionarioAutorizzato;

    private String methodName;

    /**
     * The Ambito dao.
     */
    @Autowired
    AmbitoDAO ambitoDAO;

    /**
     * The Funzionario dao.
     */
    @Autowired
    FunzionarioDAO funzionarioDAO;

    /**
     * The Funzionario competenza dao.
     */
    @Autowired
    FunzionarioCompetenzaDAO funzionarioCompetenzaDAO;

    /**
     * The Funzionario profilo dao.
     */
    @Autowired
    FunzionarioProfiloDAO funzionarioProfiloDAO;

    /**
     * The Tipo adempimento oggetto app dao.
     */
    @Autowired
    TipoAdempimentoOggettoAppDAO tipoAdempimentoOggettoAppDAO;

    /**
     * The Tipo adempimento profilo dao.
     */
    @Autowired
    TipoAdempimentoProfiloDAO tipoAdempimentoProfiloDAO;

    /**
     * The Error manager.
     */
    @Autowired
    ErrorManager errorManager;

    /**
     * Gets method name.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets method name.
     *
     * @param methodName the method name
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets error manager.
     *
     * @return the error manager
     */
    public ErrorManager getErrorManager() {
        if (errorManager == null) {
            errorManager = (ErrorManager) SpringApplicationContextHelper.getBean("errorManager");
        }
        return errorManager;
    }

    /**
     * Gets funzionario autorizzato by cf.
     *
     * @param cfFunzionario the cf funzionario
     * @return the funzionario autorizzato by cf
     * @throws UserNotAllowedException the user not allowed exception
     */
    public FunzionarioAutorizzatoDTO getFunzionarioAutorizzatoByCF(String cfFunzionario) throws UserNotAllowedException {
        setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        logStartFunction();
        String inputParam = "Parametro in input cfFunzionario [" + cfFunzionario + "]";
        logDebugString(inputParam);

        List<FunzionarioDTO> funzionarioList = funzionarioDAO.loadFunzionarioByCf(cfFunzionario);
        if (funzionarioList == null || funzionarioList.isEmpty()) {
            //ErrorDTO err = getErrorManager() != null ? getErrorManager().getError("400", null, "Attore non identificato.", null, null) : new ErrorDTO("400", null, "Attore non identificato.", null, null);
            ErrorDTO err = getErrorManager().getError("404", "E036", "Utente non abilitato ad accedere alla scrivania del funzionario. Contattare assistenza.", null, null);
            logErrorString(err);
            throw new UserNotAllowedException(err);
        }

        List<FunzionarioCompetenzaExtendedDTO> funzionarioCompetenzaList = funzionarioCompetenzaDAO.loadFunzionarioCompetenzaByCf(cfFunzionario);
        //List<FunzionarioProfiloExtendedDTO> funzionarioProfiloList = funzionarioProfiloDAO.loadFunzionarioProfiloByCf(cfFunzionario);
        List<FunzionarioProfiloExtendedDTO> funzionarioProfiloList = getFunzionarioProfiloListByCf(cfFunzionario);

        funzionarioAutorizzato = new FunzionarioAutorizzatoDTO();
        funzionarioAutorizzato.setFunzionario(funzionarioList.get(0));
        funzionarioAutorizzato.setFunzionarioCompetenza(funzionarioCompetenzaList);
        funzionarioAutorizzato.setFunzionarioProfilo(funzionarioProfiloList);
        return funzionarioAutorizzato;
    }

    /**
     * Gets funzionario profilo list by cf.
     *
     * @param cfFunzionario the cf funzionario
     * @return the funzionario profilo list by cf
     */
    public List<FunzionarioProfiloExtendedDTO> getFunzionarioProfiloListByCf(String cfFunzionario) {
        setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        logStartFunction();
        String inputParam = "Parametro in input cfFunzionario [" + cfFunzionario + "]";
        logDebugString(inputParam);

        List<FunzionarioProfiloExtendedDTO> funzionarioProfiloList = null;
        List<TipoAdempimentoProfiloDTO> tipoAdempimentoProfiloList = null;
        List<OggettoAppExtendedDTO> oggettoAppList = new ArrayList<>();

        if (StringUtils.isNotBlank(cfFunzionario)) {
            funzionarioProfiloList = funzionarioProfiloDAO.loadFunzionarioProfiloByCf(cfFunzionario);
            addTipoAdempimentoToFunzionarioProfilo(funzionarioProfiloList);
            addOggettoAppToFunzionarioProfilo(funzionarioProfiloList);
        }
        logEndFunction();
        return funzionarioProfiloList;
    }

    /**
     * Get ambiti attivi by cf list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    public List<AmbitoDTO> getAmbitiAttiviByCf(String cfFunzionario){
        return ambitoDAO.loadAmbitoByCfFunzionario(cfFunzionario);
    }

    /**
     * Gets id funzionario by cf.
     *
     * @param cfFunzionario the cf funzionario
     * @return the id funzionario by cf
     */
    public Long getIdFunzionarioByCf(@NotNull String cfFunzionario) {
        List<FunzionarioDTO> funzionarioList = funzionarioDAO.loadFunzionarioByCf(cfFunzionario);
        return funzionarioList!=null && !funzionarioList.isEmpty() ? funzionarioList.get(0).getIdFunzionario() : null;
    }

    /**
     * Add tipo adempimento to funzionario profilo.
     *
     * @param funzionarioProfiloList the funzionario profilo list
     */
    private void addTipoAdempimentoToFunzionarioProfilo(List<FunzionarioProfiloExtendedDTO> funzionarioProfiloList) {
        setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        logStartFunction();
        String inputParam = "Parametro in input funzionarioProfiloList :\n" + funzionarioProfiloList + "\n";
        logDebugString(inputParam);

        if (funzionarioProfiloList != null && !funzionarioProfiloList.isEmpty()) {
            List<TipoAdempimentoProfiloDTO> tipoAdempimentoProfiloList = null;
            for (FunzionarioProfiloExtendedDTO funzionarioProfilo : funzionarioProfiloList) {
                List<Long> idProfiloAppList = new ArrayList<>();
                if (funzionarioProfilo.getProfiloApp() != null) {
                    idProfiloAppList.add(funzionarioProfilo.getProfiloApp().getIdProfiloApp());
                }
                tipoAdempimentoProfiloList = !idProfiloAppList.isEmpty() ? tipoAdempimentoProfiloDAO.loadTipoAdempimentoProfiloByIdProfiloAppList(idProfiloAppList) : null;
                funzionarioProfilo.setTipoAdempimentoProfilo(tipoAdempimentoProfiloList);
            }
        }
        logEndFunction();
    }

    /**
     * Add oggetto app to funzionario profilo.
     *
     * @param funzionarioProfiloList the funzionario profilo list
     */
    private void addOggettoAppToFunzionarioProfilo(List<FunzionarioProfiloExtendedDTO> funzionarioProfiloList) {
        setMethodName(Thread.currentThread().getStackTrace()[1].getMethodName());
        logStartFunction();
        String inputParam = "Parametro in input funzionarioProfiloList :\n" + funzionarioProfiloList + "\n";
        logDebugString(inputParam);

        if (funzionarioProfiloList != null && !funzionarioProfiloList.isEmpty()) {
            List<OggettoAppExtendedDTO> oggettoAppList = new ArrayList<>();
            for (FunzionarioProfiloExtendedDTO funzionarioProfilo : funzionarioProfiloList) {
                List<TipoAdempimentoProfiloDTO> tipoAdempimentoProfiloList = funzionarioProfilo.getTipoAdempimentoProfilo();
                if (tipoAdempimentoProfiloList != null && !tipoAdempimentoProfiloList.isEmpty()) {
                    for (TipoAdempimentoProfiloDTO tipoAdempimentoProfilo : tipoAdempimentoProfiloList) {
                        List<TipoAdempimentoOggettoAppExtendedDTO> tipoAdempimentoOggettoAppList = tipoAdempimentoOggettoAppDAO.loadTipoAdempimentoOggettoAppByIdTipoAdempimentoProfilo(tipoAdempimentoProfilo.getIdTipoAdempimentoProfilo());
                        List<OggettoAppExtendedDTO> oggettoAppToAdd = tipoAdempimentoOggettoAppList != null && !tipoAdempimentoOggettoAppList.isEmpty() ?
                                tipoAdempimentoOggettoAppList.stream().map(TipoAdempimentoOggettoAppExtendedDTO::getOggettoApp).collect(Collectors.toList()) : null;
                        if (oggettoAppToAdd != null && !oggettoAppToAdd.isEmpty()) {
                            oggettoAppList.addAll(oggettoAppToAdd);
                        }
                    }
                    funzionarioProfilo.setOggettoApp(oggettoAppList);
                }
            }
        }
        logEndFunction();
    }

    /**
     * Gets class function debug string.
     *
     * @param info the info
     * @return the class function debug string
     */
    protected String getClassFunctionDebugString(String info) {
        String functionIdentity = "[" + className + "::" + methodName + "] ";
        return functionIdentity + info;
    }

    /**
     * Log debug string.
     *
     * @param info the info
     */
    protected void logDebugString(String info) {
        LOGGER.debug(getClassFunctionDebugString(info));
    }

    /**
     * Log error string.
     *
     * @param obj the obj
     */
    protected void logErrorString(Object obj) {
        LOGGER.error(getClassFunctionDebugString("EXCEPTION \n" + obj.toString()));
    }

    /**
     * Log start function.
     */
    protected void logStartFunction() {
        logDebugString("START");
    }

    /**
     * Log end function.
     */
    protected void logEndFunction() {
        logDebugString("END");
    }


}
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

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.ErrorManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The type Base service.
 *
 * @author CSI PIEMONTE
 */
@Component
public abstract class BaseServiceImpl {

    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    @Autowired
    private ErrorManager errorManager;

    /**
     * Get method name string.
     *
     * @return the string
     */
    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Get method name string.
     *
     * @param stack the stack
     * @return the string
     */
    public String getMethodName(int stack) {
        return Thread.currentThread().getStackTrace()[stack].getMethodName();
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
     * Log begin.
     *
     * @param classname  the classname
     * @param methodName the method name
     */
    protected void logBegin(String classname, String methodName) {
        logDebug(classname, methodName, "BEGIN");
    }

    /**
     * Log begin.
     *
     * @param classname the classname
     */
    protected void logBegin(String classname) {
        logDebug(classname, getMethodName(3), "BEGIN");
    }

    /**
     * Log begin info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param obj        the obj
     */
    protected void logBeginInfo(String className, String methodName, Object obj) {
        logBegin(className, methodName);
        if (obj != null) {
            String inputParam = "Parametro in input " + obj.getClass().getSimpleName() + ":\n" + obj + "\n";
            logDebug(className, methodName, inputParam);
        }
    }

    /**
     * Log begin info.
     *
     * @param className the class name
     * @param obj       the obj
     */
    protected void logBeginInfo(String className, Object obj) {
        logBeginInfo(className, getMethodName(3), obj);
    }

    /**
     * Log begin info.
     *
     * @param className  the class name
     * @param methodName the method name
     */
    protected void logBeginInfo(String className, String methodName) {
        if (StringUtils.isNotBlank(methodName)) {
            if (methodName.contains("[")) {
                logBeginInfo(className, getMethodName(3), methodName);
            } else {
                logBeginInfo(className, methodName, null);
            }
        } else {
            logBegin(className);
        }
    }

    /**
     * Log end.
     *
     * @param classname  the classname
     * @param methodName the method name
     */
    protected void logEnd(String classname, String methodName) {
        logDebug(classname, methodName, "END");
    }

    /**
     * Log end.
     *
     * @param classname the classname
     */
    protected void logEnd(String classname) {
        logDebug(classname, getMethodName(3), "END");
    }

    /**
     * Log debug.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param info       the info
     */
    protected void logDebug(String classname, String methodName, String info) {
        log(classname, methodName, info, "DEBUG");
    }

    /**
     * Log debug.
     *
     * @param classname the classname
     * @param info      the info
     */
    protected void logDebug(String classname, String info) {
        log(classname, getMethodName(3), info, "DEBUG");
    }

    /**
     * Log error.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param error      the error
     */
    protected void logError(String classname, String methodName, Object error) {
        log(classname, methodName, error, "ERROR");
    }

    /**
     * Log error.
     *
     * @param classname the classname
     * @param error     the error
     */
    protected void logError(String classname, Object error) {
        log(classname, getMethodName(3), error, "ERROR");
    }

    /**
     * Log info.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param info       the info
     */
    protected void logInfo(String classname, String methodName, String info) {
        log(classname, methodName, info, "INFO");
    }

    /**
     * Log info.
     *
     * @param classname the classname
     * @param info      the info
     */
    protected void logInfo(String classname, String info) {
        log(classname, getMethodName(3), info, "INFO");
    }

    /**
     * Log.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param info       the info
     * @param type       the type
     */
    protected void log(String classname, String methodName, Object info, String type) {
        if (StringUtils.isNotBlank(methodName) && info != null && StringUtils.isNotBlank(type)) {
            String log = "[" + classname + "::" + methodName + "] " + info;
            switch (type.toUpperCase(Locale.ROOT)) {
                case "DEBUG":
                    LOGGER.debug(log);
                    break;
                case "ERROR":
                    LOGGER.error(log);
                    break;
                default:
                    LOGGER.info(log);
                    break;
            }
        }
    }

    /**
     * Gets class function begin info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @return the class function begin info
     */
    protected String getClassFunctionBeginInfo(String className, String methodName) {
        return getClassFunctionDebugString(className, methodName, "BEGIN");
    }

    /**
     * Gets class function end info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @return the class function end info
     */
    protected String getClassFunctionEndInfo(String className, String methodName) {
        return getClassFunctionDebugString(className, methodName, "END");
    }

    /**
     * Gets class function error info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param error      the error
     * @return the class function error info
     */
    protected String getClassFunctionErrorInfo(String className, String methodName, Object error) {
        return getClassFunctionDebugString(className, methodName, "ERROR : " + error);
    }

    /**
     * Gets class function debug string.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param info       the info
     * @return the class function debug string
     */
    protected String getClassFunctionDebugString(String className, String methodName, String info) {
        String functionIdentity = "[" + className + "::" + methodName + "] ";
        return functionIdentity + info;
    }

    /**
     * Distinct by key predicate.
     *
     * @param <T>          the type parameter
     * @param keyExtractor the key extractor
     * @return the predicate
     */
    protected static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
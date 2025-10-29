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


import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Property;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduledClass {

    protected static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");
    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AllegatiManager allegatiManager;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    @Scheduled(fixedRate = 5000)
    public void scheduleFixedDelayTask() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }

    public void getRTBatch() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String infoMethod = className + "::" + methodName;
        LOGGER.info(infoMethod + " Start at " + System.currentTimeMillis() / 1000);
    }

    public void getIndexMetadata() {
        Logger LoggerIndex = Logger.getLogger(Constants.LOGGER_NAME + ".index");
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String infoMethod = className + "::" + methodName + " ";

        String checkIndexConfigParam = "CHECK_INDEX";
        String checkIndexStartConfigParam = "CHECK_INDEX_START";
        String checkIndexStopConfigParam = "CHECK_INDEX_STOP";
        String checkIndexUuidConfigParam = "CHECK_INDEX_UUID";
        String checkIndexResultConfigParam = "CHECK_INDEX_RESULT";
        List<String> confKeysIndex = Arrays.asList(checkIndexConfigParam, checkIndexStartConfigParam, checkIndexStopConfigParam, checkIndexUuidConfigParam, checkIndexResultConfigParam);

        try {
            Map<String, String> configurazioneList = configurazioneDAO.loadConfigByKeyList(confKeysIndex);
            if (configurazioneList != null && !configurazioneList.isEmpty() &&
                    (configurazioneList.containsKey(checkIndexConfigParam) && configurazioneList.getOrDefault(checkIndexConfigParam, null) != null && configurazioneList.getOrDefault(checkIndexConfigParam, null).equalsIgnoreCase("Y")) &&
                    (configurazioneList.containsKey(checkIndexStartConfigParam) && configurazioneList.getOrDefault(checkIndexStartConfigParam, null) != null && configurazioneList.getOrDefault(checkIndexConfigParam, null).equalsIgnoreCase("Y")) &&
                    (configurazioneList.containsKey(checkIndexStopConfigParam) && configurazioneList.getOrDefault(checkIndexStopConfigParam, null) != null && configurazioneList.getOrDefault(checkIndexConfigParam, null).equalsIgnoreCase("Y")) &&
                    (configurazioneList.containsKey(checkIndexUuidConfigParam) && configurazioneList.getOrDefault(checkIndexUuidConfigParam, null) != null)
            ) {
                LocalDateTime dateStart = getFormattedDate(CalendarUtils.convertStringToDate(configurazioneList.getOrDefault(checkIndexStartConfigParam, null), CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                LocalDateTime dateStop = getFormattedDate(CalendarUtils.convertStringToDate(configurazioneList.getOrDefault(checkIndexStopConfigParam, null), CalendarUtils.DATE_FORMAT_DDMMYYYYHHMMSS));
                LocalDateTime now = getFormattedDate(CalendarUtils.now());
                if (now.isAfter(dateStart) && now.isBefore(dateStop)) {
                    LoggerIndex.debug("************************************************************************************************");
                    LoggerIndex.debug(infoMethod + "Start at " + getFormattedDate(CalendarUtils.now()));
                    LoggerIndex.debug(infoMethod + "Calling INDEX at " + getFormattedDate(CalendarUtils.now()));
                    Node indexNode = allegatiManager.getMetadataIndexByUuid(configurazioneList.getOrDefault(checkIndexUuidConfigParam, null));
                    if (indexNode == null) {
                        LoggerIndex.debug(infoMethod + " Index node NULL at " + getFormattedDate(CalendarUtils.now()));
                    } else {
                        List<Property> properties = indexNode.getProperties();
                        String created = getPropertyValue(properties, "cm:created");
                        LoggerIndex.debug(infoMethod + " Index cm:created value : [" + created + "]");
                        String[] createdSplits = StringUtils.isNotBlank(created) ? created.split("\\.") : null;
                        if (createdSplits != null && createdSplits.length > 0) {
                            created = createdSplits[0].replace("T", " ");
                            LoggerIndex.debug(infoMethod + " Index cm:created value splitted: [" + created + "]");
                            String expectedResult = configurazioneList.getOrDefault(checkIndexResultConfigParam, null);
                            if (StringUtils.isNotBlank(expectedResult) && !created.equalsIgnoreCase(expectedResult.trim())) {
                                LoggerIndex.debug(infoMethod + " Index cm:created ERROR value splitted: [" + created + "] - expected result: [" + expectedResult + "]");
                            }
                        }
                    }
                    LoggerIndex.debug(infoMethod + "End at " + getFormattedDate(CalendarUtils.now()));
                }
            }
        } catch (Exception e) {

        }
    }

    private String getPropertyValue(List<Property> properties, String propertyToSearch) {
        List<String> values = this.getPropertyValues(properties, propertyToSearch);
        return values != null ? values.get(0) : null;
    }

    private List<String> getPropertyValues(List<Property> properties, String propertyToSearch) {
        List<Property> propertiesFiltered = properties.stream().filter(prop -> prop.getPrefixedName().equalsIgnoreCase(propertyToSearch)).collect(Collectors.toList());
        return !propertiesFiltered.isEmpty() ? propertiesFiltered.get(0).getValues() : null;
    }

    private LocalDateTime getFormattedDate(Date date) {
        return CalendarUtils.dateToLocalDateTime(date);
    }

}
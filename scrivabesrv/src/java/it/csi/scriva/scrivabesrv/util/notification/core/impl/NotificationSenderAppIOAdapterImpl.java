/*
* ========================LICENSE_START=================================
* 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
package it.csi.scriva.scrivabesrv.util.notification.core.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.csi.scriva.scrivabesrv.business.SpringApplicationContextHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderAppIOAdapter;
import it.csi.scriva.scrivabesrv.util.notification.model.Message;
import it.csi.scriva.scrivabesrv.util.notification.service.ExternalServiceCaller;

@Component
public class NotificationSenderAppIOAdapterImpl extends BaseServiceImpl implements NotificationSenderAppIOAdapter{

    private final String className = this.getClass().getSimpleName();

     private static final List<String> CONF_KEYS_APP_IO = Arrays.asList(Constants.CONF_KEY_IO_ENDPOINT_URL, Constants.CONF_KEY_IO_ENDPOINT_TOKEN);

    private Map<String, String> configs;

    @Autowired
    ConfigurazioneDAO configurazioneDAO;

    @Autowired
    private ExternalServiceCaller serviceCaller;

    /**
     * Instantiates a new Notification sender IO adapter.
     */
    public NotificationSenderAppIOAdapterImpl() {
        super();
        //configurazioneDAO = (ConfigurazioneDAO) SpringApplicationContextHelper.getBean("configurazioneDAO");
        getConfAppIO();
       
    }


    @Override
    public long notify(List<Message> messageList) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notify'");
    }

    @Override
    public long notify(Message message) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notify'");
    }

    @Override
    public long notifyByNotificaList(List<NotificaExtendedDTO> notificaList) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notifyByNotificaList'");
    }

    @Override
    public long notifyByNotifica(NotificaExtendedDTO notifica) throws Exception {
        
        logBegin(className);
        long result = 0;
        try {
            
        getConfAppIO();

        String url = configs.get(Constants.CONF_KEY_IO_ENDPOINT_URL);
        String token = configs.get(Constants.CONF_KEY_IO_ENDPOINT_TOKEN);
        String uuid = notifica.getGestUID();
        String id = notifica.getGestUID();


        String userId = notifica.getRifCanale();
        String subject = notifica.getDesOggetto();
        String markdown = notifica.getDesMessaggio();

        ResponseEntity<String> response = serviceCaller.callExternalService(url, token, uuid, id, userId, subject, markdown);

     
        logInfo(className, "Response: " + response.getBody()+" - Status:"+response.getStatusCode());
        result = 1;

    } catch (Exception e) {
        logError(className, e);
        throw new Exception(e);
    } finally {
        logEnd(className);
    }
    return result;
    }

/* 
    private void getConfAppIO() {
        logBegin(className);
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_APP_IO);
        }
    }
*/
    private void getConfAppIO() {
        logBegin(className);
        // randomicamente il bean non viene iniettato correttamente e quindi lo inizializzo manualmente per sicurezza. Sembra comunque un problema che si verifica solo in locale
        configurazioneDAO = (ConfigurazioneDAO) SpringApplicationContextHelper.getBean("configurazioneDAO");
        if (configs == null || configs.isEmpty()) {
            configs = configurazioneDAO.loadConfigByKeyList(CONF_KEYS_APP_IO);
        }
    }
}

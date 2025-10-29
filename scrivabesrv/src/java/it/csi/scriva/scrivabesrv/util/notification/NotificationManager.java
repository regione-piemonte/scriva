/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoEventoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.NotifyAllEnum;
import it.csi.scriva.scrivabesrv.util.notification.service.SchedulingService;
import it.csi.scriva.scrivabesrv.util.notification.service.TemplateNotificationService;
import it.csi.scriva.scrivabesrv.util.notification.service.ValidationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

/**
 * The type Notification manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificationManager extends BaseServiceImpl {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Scheduling service.
     */
    @Autowired
    SchedulingService schedulingService;

    /**
     * The Template notification service.
     */
    @Autowired
    TemplateNotificationService templateNotificationService;

    /**
     * The Validation service.
     */
    @Autowired
    ValidationService validationService;

    /**
     * The Tipo evento dao.
     */
    @Autowired
    TipoEventoDAO tipoEventoDAO;

    /**
     * Send notifica.
     *
     * @param idIstanza         the id istanza
     * @param codTipoevento     the cod tipoevento
     * @param rifCanaleNotifica the rif canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param uidRichiesta      the uid richiesta
     * @param dataIntegrazione  the data integrazione
     * @param attoreScriva      the attore scriva
     */
    public void sendNotifica(Long idIstanza, String codTipoevento, String rifCanaleNotifica, String codCanaleNotifica, String uidRichiesta, String desTipoRichiesta, Date dataIntegrazione, AttoreScriva attoreScriva) {
        String infoParam = "\nidIstanza : [" + idIstanza + "]\ncodTipoevento : [" + codTipoevento + "]\nrifCanaleNotifica : [" + rifCanaleNotifica + "]\ncodCanaleNotifica : [" + codCanaleNotifica + "]\nuidRichiesta : [" + uidRichiesta + "]\ndesTipoRichiesta : [" + desTipoRichiesta + "]\ndataIntegrazione : [" + dataIntegrazione + "]\nattoreScriva : [" + attoreScriva + "]\n";
        logBeginInfo(className, infoParam);
        try {
            // Validazione parametri in ingresso
            ErrorDTO error = validationService.validateInputParameters(idIstanza, codTipoevento, rifCanaleNotifica, codCanaleNotifica, dataIntegrazione, attoreScriva);
            if (error != null) {
                logError(className, error);
                return;
            }

            // Verifica parametro abilitazione notifiche
            String notifyAll = templateNotificationService.getConfNotifyAll();
            if (StringUtils.isNotBlank(notifyAll) && NotifyAllEnum.NO.name().equalsIgnoreCase(notifyAll)) {
                logDebug(className, "Notifiche disabilitate. Parametro " + Constants.CONF_KEY_NOTIFY_ALL + " : [" + notifyAll + "]");
                return;
            }

            // Verifica e recupero configurazione notifica
            List<ConfigurazioneNotificaExtendedDTO> configurazioneNotificaList = templateNotificationService.getConfigurazioneNotifica(codTipoevento, idIstanza, attoreScriva.getComponente());
            if (CollectionUtils.isEmpty(configurazioneNotificaList)) {
                logDebug(className, infoParam + "Nessuna configurazione notifica.");
                return;
            }

            // Recupero delle configurazioni degli allegati
            configurazioneNotificaList = templateNotificationService.getConfigurazioneAllegatoNotifica(configurazioneNotificaList);

            // Individuazione destinatari
            List<NotificaExtendedDTO> notificaList = templateNotificationService.createNotificheFromConf(configurazioneNotificaList, idIstanza, codCanaleNotifica, rifCanaleNotifica, attoreScriva.getCodiceFiscale());
            if (CollectionUtils.isEmpty(notificaList)) {
                logDebug(className, infoParam + "Nessuna notifica estratta.");
                return;
            }

            // Verifica e trascodifica placeholder
            templateNotificationService.verifyPlaceHolder(notificaList, uidRichiesta);

            // Verifica e recupero allegati
            notificaList = templateNotificationService.setAllegatiFromConf(configurazioneNotificaList, notificaList, uidRichiesta, desTipoRichiesta);

            // Generazione ricevuta
            List<TipoEventoExtendedDTO> tipoEventoList = tipoEventoDAO.loadTipoEventoByCode(codTipoevento, attoreScriva.getComponente());
            TipoEventoExtendedDTO tipoEvento = CollectionUtils.isNotEmpty(tipoEventoList) ? tipoEventoList.get(0) : null;
            if (tipoEvento != null && Boolean.TRUE.equals(tipoEvento.getFlgGeneraRicevuta())) {
                schedulingService.generateRicevuta(idIstanza, codTipoevento, attoreScriva);
            }

            // Inserimento notifiche in tabella
            schedulingService.insertNotifiche(notifyAll, notificaList);

            // Invio notifiche (se è configurato solo il tracciamento non viene effettuato l'invio)
            schedulingService.sendNotifiche(notifyAll, notificaList, attoreScriva);

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

}
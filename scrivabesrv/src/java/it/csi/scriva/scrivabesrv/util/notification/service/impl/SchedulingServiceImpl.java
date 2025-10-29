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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.*;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.TemplateService;
import it.csi.scriva.scrivabesrv.dto.*;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipologiaAllegatoEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;
import it.csi.scriva.scrivabesrv.util.notification.core.NotificationSenderHub;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.ChannelTypeEnum;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.NotifyAllEnum;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.NotifyStatusEnum;
import it.csi.scriva.scrivabesrv.util.notification.service.SchedulingService;
import it.csi.scriva.scrivabesrv.util.templating.TemplateUtil;
import it.csi.scriva.scrivabesrv.util.updownfile.FileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Scheduling service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class SchedulingServiceImpl extends BaseApiServiceImpl implements SchedulingService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Adempimento config dao.
     */
    @Autowired
    AdempimentoConfigDAO adempimentoConfigDAO;

    /**
     * The Adempimento tipo allegato dao.
     */
    @Autowired
    AdempimentoTipoAllegatoDAO adempimentoTipoAllegatoDAO;

    /**
     * The Allegato istanza dao.
     */
    @Autowired
    AllegatoIstanzaDAO allegatoIstanzaDAO;

    /**
     * The Istanza dao.
     */
    @Autowired
    IstanzaDAO istanzaDAO;

    /**
     * The Notifica dao.
     */
    @Autowired
    NotificaDAO notificaDAO;

    /**
     * The Notifica allegato dao.
     */
    @Autowired
    NotificaAllegatoDAO notificaAllegatoDAO;

    /**
     * The Allegati manager.
     */
    @Autowired
    AllegatiManager allegatiManager;

    /**
     * The Istanza attore manager.
     */
    @Autowired
    IstanzaAttoreManager istanzaAttoreManager;

    /**
     * The Notification sender hub.
     */
    @Autowired
    NotificationSenderHub notificationSenderHub;

    /**
     * The Allegati service.
     */
    @Autowired
    AllegatiService allegatiService;

    /**
     * The Template service.
     */
    @Autowired
    TemplateService templateService;

    /**
     * The Template util.
     */
    @Autowired
    TemplateUtil templateUtil;


    /**
     * Instantiates a new Insert notifiche.
     *
     * @param notifyAll    the notifyAll
     * @param notificaList the notifica list
     */
    @Override
    public void insertNotifiche(String notifyAll, List<NotificaExtendedDTO> notificaList) {
        logBeginInfo(className, "\nnotifyAll : [" + notifyAll + "]\nnotificaList :\n" + notificaList + "\n");
        try {
            if (CollectionUtils.isNotEmpty(notificaList) && !NotifyAllEnum.NO.getDescrizione().equalsIgnoreCase(notifyAll)) {
                Timestamp today = new Timestamp(System.currentTimeMillis());
                for (NotificaExtendedDTO notifica : notificaList) {
                    // Impostazione data di inserimento
                    notifica.setDataInserimento(today);
                    notifica.setDesMessaggio(notifica.getDesMessaggio());
                    if (NotifyAllEnum.TRACE.getDescrizione().equalsIgnoreCase(notifyAll)) {
                        notifica.setIdStatoNotifica(NotifyStatusEnum.TRACE.getId());
                    }
                    try {
                        Map.Entry<Long, String>  idNotifica = notificaDAO.saveNotifica(notifica.getDTO());
                        notifica.setIdNotifica(idNotifica.getKey());
                        notifica.setGestUID(idNotifica.getValue()); //uid necessario le notifiche verso APP-IO
                        List<NotificaAllegatoDTO> notificaAllegatoList = notifica.getNotificaAllegatoList();
                        if (CollectionUtils.isNotEmpty(notificaAllegatoList)) {
                            for (NotificaAllegatoDTO notificaAllegato : notificaAllegatoList) {
                                notificaAllegato.setIdNotifica(idNotifica.getKey());
                                notificaAllegatoDAO.saveNotificaAllegato(notificaAllegato);
                            }
                        }
                    } catch (Exception e) {
                        notifica.setIdStatoNotifica(NotifyStatusEnum.FALLITA.getId());
                        notifica.setDesSegnalazione("Errore in fase di inserimento : " + e.getMessage());
                        logError(className, e);
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
     * Instantiates a new Send notifiche.
     *
     * @param notifyAll    the notifyall
     * @param notificaList the notifica list
     * @param attoreScriva the attore scriva
     */
    @Override
    public void sendNotifiche(String notifyAll, List<NotificaExtendedDTO> notificaList, AttoreScriva attoreScriva) {
        logBeginInfo(className, "\nnotifyAll : [" + notifyAll + "]\nnotificaList :\n" + notificaList + "\n");
        try {
            if (CollectionUtils.isNotEmpty(notificaList)
                    && !NotifyAllEnum.NO.getDescrizione().equalsIgnoreCase(notifyAll)
                    && !NotifyAllEnum.TRACE.getDescrizione().equalsIgnoreCase(notifyAll)) {
                // Filtrare lista solo per quelli in stato CREATA
                List<NotificaExtendedDTO> notificaToSendList = notificaList.stream()
                        .filter(notif -> NotifyStatusEnum.CREATA.getId().equals(notif.getIdStatoNotifica()))
                        .collect(Collectors.toList());
                for (NotificaExtendedDTO notifica : notificaToSendList) {
                    try {
                        send(notifica);
                    } catch (Exception e) {
                        logError(className, e);
                    }
                    updateNotifica(notifica);
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Send.
     *
     * @param notifica the notifica
     */
    private void send(NotificaExtendedDTO notifica) {
        logBegin(className);
        try {
            // Impostazione data di invio
            notifica.setDataInvio(new Timestamp(System.currentTimeMillis()));
            // Recupero canale invio
            ChannelTypeEnum channelType = ChannelTypeEnum.valueOf(notifica.getConfigurazioneNotificaExtendedDTO().getCanaleNotifica().getCodCanaleNotifica());
            // inviare la notifica
            notificationSenderHub.notifyByNotifica(channelType, notifica);
            notifica.setIdStatoNotifica(NotifyStatusEnum.SUCCESSO.getId());
        } catch (Exception e) {
            notifica.setIdStatoNotifica(NotifyStatusEnum.FALLITA.getId());
            notifica.setDesSegnalazione("Errore in fase di invio : " + e.getMessage());
            logError(className, "Errore in fase di invio :\n" + e);
        } finally {
            notifica.setTentativiInvio(1L);
            logEnd(className);
        }
    }

    /**
     * Update notifica.
     *
     * @param notifica the notifica
     */
    private void updateNotifica(NotificaExtendedDTO notifica) {
        logBegin(className);
        try {
            // aggiornare lo stato della notifica in tabella
            notificaDAO.updateNotifica(notifica.getDTO());
        } catch (Exception e) {
            logError(className, "Errore in fase di aggiornamento post invio :\n" + e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Instantiates a new Send notifiche create.
     *
     * @param notifyAll the notify all
     */
    @Override
    public void sendNotificheCreate(String notifyAll) {
        logBegin(className);
    }

    /**
     * Generate ricevuta.
     *
     * @param idIstanza     the id istanza
     * @param codTipoevento the cod tipoevento
     * @param attoreScriva  the attore scriva
     */
    @Override
    public void generateRicevuta(Long idIstanza, String codTipoevento, AttoreScriva attoreScriva) {
        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - codTipoEvento : [" + codTipoevento + "]");
        List<AdempimentoConfigDTO> adempimentoConfigList = adempimentoConfigDAO.loadAdempimentoConfigByIdIstanzaInformazioneChiave(idIstanza, Constants.COD_INFO_RICEVUTA_DOC, TipologiaAllegatoEnum.RCV_DOC.name());
        String ricevuta = CollectionUtils.isNotEmpty(adempimentoConfigList) ? adempimentoConfigList.get(0).getValore() : "TRUE";
        if (Boolean.TRUE.toString().equalsIgnoreCase(ricevuta) || ricevuta.contains(attoreScriva.getComponente())) {
            List<IstanzaExtendedDTO> istanze = istanzaDAO.loadIstanza(idIstanza);
            if (CollectionUtils.isNotEmpty(istanze)) {
                IstanzaExtendedDTO istanza = istanze.get(0);
                String absolutePathTemplate = templateService.getTemplatePath(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
                String filename = StringUtils.isNotBlank(absolutePathTemplate) ? templateService.getTemplateFilenameWithInputStream(istanza, TipologiaAllegatoEnum.RCV_DOC.name(), codTipoevento, absolutePathTemplate) : null;
                if (StringUtils.isNotBlank(absolutePathTemplate) && StringUtils.isNotBlank(filename)) {
                    String pathTemplate = absolutePathTemplate + File.separator + filename;
                    try {
                        // Generazione ricevuta
                        byte[] out = templateUtil.getCompiledTemplatePDF(idIstanza, pathTemplate);
                        File outputFile = FileUtils.byteArrayToFile(out, filename);

                        // recupero tipo allegato per adempimento
                        List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoList = adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(istanza.getAdempimento().getCodAdempimento(), TipologiaAllegatoEnum.RCV_DOC.name(), attoreScriva.getComponente());
                        List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoFilteredList = adempimentoTipoAllegatoList != null && !adempimentoTipoAllegatoList.isEmpty() ? adempimentoTipoAllegatoList.stream()
                                .filter(tipo -> tipo.getTipologiaAllegato().getCodTipologiaAllegato().equalsIgnoreCase(TipologiaAllegatoEnum.RCV_DOC.name()))
                                .collect(Collectors.toList()) : null;
                        if (CollectionUtils.isEmpty(adempimentoTipoAllegatoFilteredList)) {
                            ErrorDTO error = getErrorManager().getError("400", "E100", "Errore durante il recupero delle tipologie allegati.", null, null);
                            logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                            logEnd(className);
                            throw new GenericException(error);
                        }


                        AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = adempimentoTipoAllegatoFilteredList.get(0);

                        //caricamento file su index
                        String uuidIndex = allegatiManager.uploadFileOnIndex(istanza, outputFile, filename);
                        if (uuidIndex == null || StringUtils.isBlank(uuidIndex)) {
                            ErrorDTO error = getErrorManager().getError("400", "E100", "Errore durante il caricamento dei file su index.", null, null);
                            logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                            logEnd(className);
                            throw new GenericException(error);
                        }

                        // Retrieve metadata from INDEX
                        Node indexNode = allegatiManager.getMetadataIndexByUuid(uuidIndex);

                        // Populate allegato istanza
                        AllegatoIstanzaExtendedDTO allegatoIstanza = new AllegatoIstanzaExtendedDTO();
                        allegatiManager.populateAllegatoIstanza(allegatoIstanza, indexNode, uuidIndex, filename, outputFile.length(), 0, false, istanza, adempimentoTipoAllegato);
                        Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), allegatoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                        allegatoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
                        if (allegatoIstanza.getClasseAllegato() == null) {
                            allegatoIstanza.setClasseAllegato(allegatiService.getClasseAllegato(istanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato(), null));
                        }

                        // Save allegato istanza
                        Long idAllegatoIstanza = allegatoIstanzaDAO.saveAllegatoIstanza(allegatoIstanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato());

                        // Retrieve record and update metadati on INDEX
                        List<AllegatoIstanzaExtendedDTO> allegatiIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndCodTipologia(istanza.getIdIstanza(), Constants.COD_TIPOLOGIA_ALLEGATI_ELENCO);
                        if (allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty()) {
                            // Update metadata index
                            IndexMetadataPropertyDTO indexMetadataProperty = allegatiManager.getindexMetadataProperty(istanza, allegatiIstanzaList.get(0), null, null, null, null, null);
                            allegatiManager.updateMetadataIndexByUuid(uuidIndex, indexMetadataProperty);
                        }

                    } catch (Exception e) {
                        logError(className, e);
                    }
                }
            }
        }
    }
}
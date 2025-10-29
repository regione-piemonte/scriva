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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AccreditamentoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaAttoreDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateComunicazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AccreditamentiService;
import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateComunicazioneDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.mail.EmailDTO;
import it.csi.scriva.scrivabesrv.util.mail.MailManager;
import it.csi.scriva.scrivabesrv.util.validation.ValidationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Accreditamenti service.
 */
@Component
public class AccreditamentiServiceImpl extends BaseApiServiceImpl implements AccreditamentiService {

    public static final String NON_TROVATO = "] non trovato";
    public static final String ACCREDITAMENTO = "accreditamento :\n";
    public static final String UID = "uid [";
    public static final String OTP = "] - otp [";
    private final String className = this.getClass().getSimpleName();

    private final List<String> keys = Arrays.asList(Constants.CONF_KEY_SERVER_POSTA_HOST, Constants.CONF_KEY_SERVER_POSTA_PORTA, Constants.CONF_KEY_SERVER_POSTA_MITTENTE, Constants.CONF_KEY_SERVER_POSTA_USERNAME, Constants.CONF_KEY_SERVER_POSTA_PASSWORD);

    @Autowired
    private AccreditamentoDAO accreditamentoDAO;

    @Autowired
    private CompilanteDAO compilanteDAO;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;

    @Autowired
    private IstanzaAttoreDAO istanzaAttoreDAO;

    @Autowired
    private TemplateComunicazioneDAO templateComunicazioneDAO;

    /**
     * Load accreditamento by pk accreditamento dto.
     *
     * @param pk the pk
     * @return the accreditamento dto
     */
    @Override
    public AccreditamentoDTO loadAccreditamentoByPK(Long pk) {
        logBeginInfo(className, pk);
        try {
            return accreditamentoDAO.loadAccreditamentoByPK(pk);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save accreditamento accreditamento dto.
     *
     * @param accreditamento the accreditamento
     * @return the accreditamento dto
     */
    @Override
    public AccreditamentoDTO saveAccreditamento(AccreditamentoDTO accreditamento) throws GenericException {
        logBeginInfo(className, accreditamento);
        try {
            ErrorDTO error = this.validateDTO(accreditamento);
            if (null != error) {
                logError(className, ACCREDITAMENTO + accreditamento + "\n" + error);
                throw new GenericException(error);
            }

            accreditamento.setGestAttoreIns(accreditamento.getCfAccredito());
            Long idRichiestaAccredito = accreditamentoDAO.saveAccreditamento(accreditamento);

            if (idRichiestaAccredito == null) {
                error = getErrorManager().getError("500", "E100", null, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\nErrore nel salvataggio");
                throw new GenericException(error);
            } else {
                accreditamento = this.loadAccreditamentoByPK(idRichiestaAccredito);
                this.sendMail(accreditamento);
            }
        } finally {
            logEnd(className);
        }
        return accreditamento;
    }

    /**
     * Validate accreditamento list.
     *
     * @param uid the uid
     * @param otp the otp
     * @return the list
     * @throws GenericException the generic exception
     */
    @Override
    public List<CompilanteDTO> validateAccreditamento(String uid, String otp) throws GenericException {
        logBeginInfo(className, UID + uid + OTP + otp + "]");
        List<CompilanteDTO> compilanteList = new ArrayList<>();
        try {
            List<AccreditamentoDTO> list = accreditamentoDAO.loadAccreditamento(uid, otp);
            if (CollectionUtils.isEmpty(list)) {
                ErrorDTO error = getErrorManager().getError("500", "E009", "Attenzione: codice verifica ERRATO", null, null);
                logError(className, UID + uid + OTP + otp + "] : Accreditamento non trovato");
                throw new GenericException(error);
            }
            logDebug(className, UID + uid + OTP + otp + "] : Trovate n° richieste accreditamento [" + list.size() + "]");
            AccreditamentoDTO accreditamento = list.get(0);

            // verifica che il compilante non sia già esistente
            compilanteList = compilanteDAO.loadCompilanteByCodiceFiscale(accreditamento.getCfAccredito());
            if (CollectionUtils.isEmpty(compilanteList)) {
                CompilanteDTO compilante = new CompilanteDTO();
                compilante.setCodiceFiscaleCompilante(accreditamento.getCfAccredito());
                compilante.setCognomeCompilante(accreditamento.getCognomeAccredito());
                compilante.setNomeCompilante(accreditamento.getNomeAccredito());
                compilante.setDesEmailCompilante(accreditamento.getDesEmailAccredito());
                compilante.setGestAttoreIns(accreditamento.getCfAccredito());
                compilante.setGestAttoreUpd(accreditamento.getCfAccredito());

                Long idCompilante = compilanteDAO.saveCompilante(compilante);
                if (idCompilante == null) {
                    ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                    logError(className, UID + uid + OTP + otp + "] : Compilante non salvato");
                    throw new GenericException(error);
                } else {
                    compilanteList = compilanteDAO.loadCompilante(idCompilante);
                    accreditamento.setIdCompilante(idCompilante);
                    accreditamento.setGestAttoreUpd(accreditamento.getCfAccredito());
                    Integer res = accreditamentoDAO.updateAccreditamento(accreditamento);
                    if (res == null) {
                        ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
                        logError(className, UID + uid + OTP + otp + "] : Accreditamento non aggiornato");
                        throw new GenericException(error);
                    }
                    // Aggiornamento eventuali istanza-attore con id compilante appena creato per il cf_attore inserito
                    istanzaAttoreDAO.updateIdCompilanteByCFAttore(idCompilante, accreditamento.getCfAccredito(), accreditamento.getCfAccredito());
                }
            }

        } finally {
            logEnd(className);
        }

        return compilanteList;
    }

    /**
     * Validate dto error dto.
     *
     * @param accreditamento the accreditamento
     * @return the error dto
     */
    @Override
    public ErrorDTO validateDTO(AccreditamentoDTO accreditamento) {
        logBeginInfo(className, accreditamento);
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();
        String cognomeAccredito = "cognome_accredito";
        String nomeAccredito = "nome_accredito";
        String emailAccredito = "des_email_accredito";
        String cfAccredito = "cf_accredito";

        try {
            if (StringUtils.isBlank(accreditamento.getCognomeAccredito())) {
                details.put(cognomeAccredito, ValidationResultEnum.MANDATORY.getDescription());
            } else if (accreditamento.getCognomeAccredito().length() > 50) {
                details.put(cognomeAccredito, ValidationResultEnum.INVALID_LENGTH.getDescription());
            }

            if (StringUtils.isBlank(accreditamento.getNomeAccredito())) {
                details.put(nomeAccredito, ValidationResultEnum.MANDATORY.getDescription());
            } else if (accreditamento.getNomeAccredito().length() > 50) {
                details.put(nomeAccredito, ValidationResultEnum.INVALID_LENGTH.getDescription());
            }

            if (StringUtils.isBlank(accreditamento.getDesEmailAccredito())) {
                details.put(emailAccredito, ValidationResultEnum.MANDATORY.getDescription());
            } else if (accreditamento.getDesEmailAccredito().length() > 100) {
                details.put(emailAccredito, ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidEmail(accreditamento.getDesEmailAccredito())) {
                details.put(emailAccredito, ValidationResultEnum.INVALID_EMAIL.getDescription());
            } else if (accreditamento.getDesEmailAccredito().contains("pec.")) {
                details.put(emailAccredito, ValidationResultEnum.INVALID_EMAIL.getDescription());
            }

            if (StringUtils.isBlank(accreditamento.getCfAccredito())) {
                details.put(cfAccredito, ValidationResultEnum.MANDATORY.getDescription());
            } else {
                String result = ValidationUtil.validateCF(accreditamento.getCfAccredito());
                if (!result.equals(ValidationResultEnum.VALID.name())) {
                    details.put(cfAccredito, ValidationResultEnum.valueOf(result).getDescription());
                }

            }

            if (!details.isEmpty()) {
                error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
            }
        } finally {
            logEnd(className);
        }
        return error;
    }

    /**
     * Send mail.
     *
     * @param accreditamento the accreditamento
     * @throws GenericException the generic exception
     */
    private void sendMail(AccreditamentoDTO accreditamento) throws GenericException {
        logBegin(className);
        ErrorDTO error = null;
        String codiceTemplateAccreditamento = "TACCR001";
        try {
            List<TemplateComunicazioneDTO> templates = templateComunicazioneDAO.loadTemplateByCode(codiceTemplateAccreditamento);
            if (null == templates) {
                error = getErrorManager().getError("500", "E100", null, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n Template comunicazione NULL");
                throw new GenericException(error);
            } else if (templates.isEmpty()) {
                error = getErrorManager().getError("404", "", "Errore nel recupero del template per l'invio della mail;  causa: template con codice [" + codiceTemplateAccreditamento + NON_TROVATO, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n" + error);
                throw new GenericException(error);
            }

            Map<String, String> configs = configurazioneDAO.loadConfigByKeyList(keys);
            if (null == configs) {
                error = getErrorManager().getError("500", "E100", null, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n Configs NULL");
                throw new GenericException(error);
            } else if (configs.isEmpty()) {
                error = getErrorManager().getError("404", "", "Errore nel recupero dei parametri del mail server;  causa: parametri con chiavi [" + Constants.CONF_KEY_SERVER_POSTA_PORTA + ", " + Constants.CONF_KEY_SERVER_POSTA_HOST + "] non trovati", null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n" + error);
                throw new GenericException(error);
            } else if (!configs.containsKey(Constants.CONF_KEY_SERVER_POSTA_HOST)) {
                error = getErrorManager().getError("404", "", "Errore nel recupero dell'host del mail server;  causa: parametro con chiave [" + Constants.CONF_KEY_SERVER_POSTA_HOST + NON_TROVATO, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n" + error);
                throw new GenericException(error);
            } else if (!configs.containsKey(Constants.CONF_KEY_SERVER_POSTA_PORTA)) {
                error = getErrorManager().getError("404", "", "Errore nel recupero della porta del mail server;  causa: parametro con chiave [" + Constants.CONF_KEY_SERVER_POSTA_PORTA + NON_TROVATO, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n" + error);
                throw new GenericException(error);
            }

            EmailDTO email = new EmailDTO();
            email.setHost(configs.get(Constants.CONF_KEY_SERVER_POSTA_HOST));
            email.setPort(configs.get(Constants.CONF_KEY_SERVER_POSTA_PORTA));
            email.setMittente(configs.get(Constants.CONF_KEY_SERVER_POSTA_MITTENTE));
            
            email.setUserID(configs.get(Constants.CONF_KEY_SERVER_POSTA_USERNAME));
            email.setPassword(configs.get(Constants.CONF_KEY_SERVER_POSTA_PASSWORD));

            List<String> destinatari = new ArrayList<>();
            destinatari.add(accreditamento.getDesEmailAccredito());
            email.setDestinatari(destinatari);
            email.setOggetto(templates.get(0).getDesOggetto());

            String corpoMail = templates.get(0).getDesCorpo().replace("__PH_NOME__", accreditamento.getNomeAccredito()).replace("__PH_CODICE__", accreditamento.getCodeVerifica());
            email.setCorpo(corpoMail);

            try {
                MailManager.sendMail(email);
            } catch (Exception e) {
                error = getErrorManager().getError("500", "E100", null, null, null);
                logError(className, ACCREDITAMENTO + accreditamento + "\n" + e);
                throw new GenericException(error);
            }
        } finally {
            logEnd(className);
        }
    }
}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Notifica dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificaExtendedDTO extends NotificaDTO implements Serializable {

    private Long idCompetenzaTerritorioCalcolata;

    private Long idTipoCompetenzaCalcolata;

    private ConfigurazioneNotificaExtendedDTO configurazioneNotificaExtendedDTO;

    /**
     * Gets configurazione notifica extended dto.
     *
     * @return the configurazione notifica extended dto
     */
    public ConfigurazioneNotificaExtendedDTO getConfigurazioneNotificaExtendedDTO() {
        return configurazioneNotificaExtendedDTO;
    }

    /**
     * Sets configurazione notifica extended dto.
     *
     * @param configurazioneNotificaExtendedDTO the configurazione notifica extended dto
     */
    public void setConfigurazioneNotificaExtendedDTO(ConfigurazioneNotificaExtendedDTO configurazioneNotificaExtendedDTO) {
        this.configurazioneNotificaExtendedDTO = configurazioneNotificaExtendedDTO;
    }

    /**
     * Gets id competenza territorio calcolata.
     *
     * @return the idCompetenzaTerritorio
     */
    public Long getIdCompetenzaTerritorioCalcolata() {
        return idCompetenzaTerritorioCalcolata;
    }

    /**
     * Sets id competenza territorio calcolata.
     *
     * @param idCompetenzaTerritorioCalcolata the id competenza territorio calcolata
     */
    public void setIdCompetenzaTerritorioCalcolata(Long idCompetenzaTerritorioCalcolata) {
        this.idCompetenzaTerritorioCalcolata = idCompetenzaTerritorioCalcolata;
    }

    /**
     * Gets id tipo competenza calcolata.
     *
     * @return the idTipoCompetenza
     */
    public Long getIdTipoCompetenzaCalcolata() {
        return idTipoCompetenzaCalcolata;
    }

    /**
     * Sets id tipo competenza calcolata.
     *
     * @param idTipoCompetenzaCalcolata the id tipo competenza calcolata
     */
    public void setIdTipoCompetenzaCalcolata(Long idTipoCompetenzaCalcolata) {
        this.idTipoCompetenzaCalcolata = idTipoCompetenzaCalcolata;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NotificaExtendedDTO that = (NotificaExtendedDTO) o;
        return Objects.equals(configurazioneNotificaExtendedDTO, that.configurazioneNotificaExtendedDTO);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), configurazioneNotificaExtendedDTO);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "NotificaExtendedDTO {\n" +
                super.toString() +
                "         configurazioneNotificaExtendedDTO:" + configurazioneNotificaExtendedDTO +
                "}\n";
    }

    /**
     * Gets dto.
     *
     * @return the dto
     */
    @JsonIgnore
    public NotificaDTO getDTO() {
        NotificaDTO dto = new NotificaDTO();
        dto.setIdNotifica(this.getIdNotifica());
        dto.setIdStatoNotifica(this.getIdStatoNotifica());
        if (this.getConfigurazioneNotificaExtendedDTO() != null) {
            dto.setIdNotificaConfigurazione(this.getConfigurazioneNotificaExtendedDTO().getIdNotificaConfigurazione());
        }
        dto.setIdIstanza(this.getIdIstanza());
        dto.setIdComponenteAppPush(this.getIdComponenteAppPush());
        dto.setCfDestinatario(this.getCfDestinatario());
        dto.setRifCanale(this.getRifCanale());
        dto.setRifCanaleCc(this.getRifCanaleCc());
        dto.setDesOggetto(this.getDesOggetto());
        dto.setDesMessaggio(this.getDesMessaggio());
        dto.setDataInserimento(this.getDataInserimento());
        dto.setDataInvio(this.getDataInvio());
        dto.setTentativiInvio(this.getTentativiInvio());
        dto.setDesSegnalazione(this.getDesSegnalazione());
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestAttoreUpd(this.getGestAttoreUpd());
        dto.setGestDataIns(this.getGestDataIns());
        dto.setGestDataUpd(this.getGestDataUpd());
        dto.setGestUID(this.getGestUID());
        dto.setNotificaAllegatoList(this.getNotificaAllegatoList());
        return dto;
    }

    /**
     * Gets notifica applicativa dto.
     *
     * @return the notifica applicativa dto
     */
    @JsonIgnore
    public NotificaApplicativaDTO getNotificaApplicativaDTO() {
        NotificaApplicativaDTO dto = new NotificaApplicativaDTO();
        long currentTime = System.currentTimeMillis();
        dto.setIdNotifica(this.getIdNotifica());
        dto.setIdIstanza(this.getIdIstanza());
        dto.setIdComponenteAppPush(this.getIdComponenteAppPush());
        dto.setCfDestinatario(this.getCfDestinatario());
        dto.setDesOggetto(this.getDesOggetto());
        dto.setDesMessaggio(this.getDesMessaggio());
        dto.setDataInserimento(new Timestamp(currentTime));
        dto.setDataLettura(null);
        dto.setDataCancellazione(null);
        dto.setGestDataIns(new Timestamp(currentTime));
        dto.setGestAttoreIns(this.getGestAttoreIns());
        dto.setGestDataUpd(new Timestamp(currentTime));
        dto.setGestAttoreUpd(StringUtils.isNotBlank(this.getGestAttoreUpd()) ? this.getGestAttoreUpd() : this.getGestAttoreIns());
        return dto;
    }

}
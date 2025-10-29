/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Ente creditore dto.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnteCreditoreDTO implements Serializable {

    @JsonProperty("id_ente_creditore")
    private Long idEnteCreditore;

    @JsonProperty("cf_ente_creditore")
    private String cfEnteCreditore;

    @JsonProperty("denominazione_ente_creditore")
    private String denominazioneEnteCreditore;

    @JsonProperty("indirizzo_tesoreria")
    private String indirizzoTesoreria;

    @JsonProperty("iban_accredito")
    private String ibanAccredito;

    @JsonProperty("bic_accredito")
    private String bicAccredito;

    @JsonProperty("flg_aderisce_piemontepay")
    private Boolean flgAderiscePiemontepay;

    @JsonProperty("flg_attivo")
    private Boolean flgAttivo;

    /**
     * Gets id ente creditore.
     *
     * @return idEnteCreditore id ente creditore
     */
    public Long getIdEnteCreditore() {
        return idEnteCreditore;
    }

    /**
     * Sets id ente creditore.
     *
     * @param idEnteCreditore idEnteCreditore
     */
    public void setIdEnteCreditore(Long idEnteCreditore) {
        this.idEnteCreditore = idEnteCreditore;
    }

    /**
     * Gets cf ente creditore.
     *
     * @return cfEnteCreditore cf ente creditore
     */
    public String getCfEnteCreditore() {
        return cfEnteCreditore;
    }

    /**
     * Sets cf ente creditore.
     *
     * @param cfEnteCreditore cfEnteCreditore
     */
    public void setCfEnteCreditore(String cfEnteCreditore) {
        this.cfEnteCreditore = cfEnteCreditore;
    }

    /**
     * Gets denominazione ente creditore.
     *
     * @return denominazioneEnteCreditore denominazione ente creditore
     */
    public String getDenominazioneEnteCreditore() {
        return denominazioneEnteCreditore;
    }

    /**
     * Sets denominazione ente creditore.
     *
     * @param denominazioneEnteCreditore denominazioneEnteCreditore
     */
    public void setDenominazioneEnteCreditore(String denominazioneEnteCreditore) {
        this.denominazioneEnteCreditore = denominazioneEnteCreditore;
    }

    /**
     * Gets indirizzo tesoreria.
     *
     * @return indirizzoTesoreria indirizzo tesoreria
     */
    public String getIndirizzoTesoreria() {
        return indirizzoTesoreria;
    }

    /**
     * Sets indirizzo tesoreria.
     *
     * @param indirizzoTesoreria indirizzoTesoreria
     */
    public void setIndirizzoTesoreria(String indirizzoTesoreria) {
        this.indirizzoTesoreria = indirizzoTesoreria;
    }

    /**
     * Gets iban accredito.
     *
     * @return ibanAccredito iban accredito
     */
    public String getIbanAccredito() {
        return ibanAccredito;
    }

    /**
     * Sets iban accredito.
     *
     * @param ibanAccredito ibanAccredito
     */
    public void setIbanAccredito(String ibanAccredito) {
        this.ibanAccredito = ibanAccredito;
    }

    /**
     * Gets bic accredito.
     *
     * @return bicAccredito bic accredito
     */
    public String getBicAccredito() {
        return bicAccredito;
    }

    /**
     * Sets bic accredito.
     *
     * @param bicAccredito bicAccredito
     */
    public void setBicAccredito(String bicAccredito) {
        this.bicAccredito = bicAccredito;
    }

    /**
     * Gets flg aderisce piemontepay.
     *
     * @return the flg aderisce piemontepay
     */
    public Boolean getFlgAderiscePiemontepay() {
        return flgAderiscePiemontepay;
    }

    /**
     * Sets flg aderisce piemontepay.
     *
     * @param flgAderiscePiemontepay the flg aderisce piemontepay
     */
    public void setFlgAderiscePiemontepay(Boolean flgAderiscePiemontepay) {
        this.flgAderiscePiemontepay = flgAderiscePiemontepay;
    }

    /**
     * Gets flg attivo.
     *
     * @return flgAttivo flg attivo
     */
    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    /**
     * Sets flg attivo.
     *
     * @param flgAttivo flgAttivo
     */
    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnteCreditoreDTO that = (EnteCreditoreDTO) o;
        return Objects.equals(idEnteCreditore, that.idEnteCreditore) && Objects.equals(cfEnteCreditore, that.cfEnteCreditore) && Objects.equals(denominazioneEnteCreditore, that.denominazioneEnteCreditore) && Objects.equals(indirizzoTesoreria, that.indirizzoTesoreria) && Objects.equals(ibanAccredito, that.ibanAccredito) && Objects.equals(bicAccredito, that.bicAccredito) && Objects.equals(flgAderiscePiemontepay, that.flgAderiscePiemontepay) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEnteCreditore, cfEnteCreditore, denominazioneEnteCreditore, indirizzoTesoreria, ibanAccredito, bicAccredito, flgAderiscePiemontepay, flgAttivo);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EnteCreditoreDTO {");
        sb.append("         idEnteCreditore:").append(idEnteCreditore);
        sb.append(",         cfEnteCreditore:'").append(cfEnteCreditore).append("'");
        sb.append(",         denominazioneEnteCreditore:'").append(denominazioneEnteCreditore).append("'");
        sb.append(",         indirizzoTesoreria:'").append(indirizzoTesoreria).append("'");
        sb.append(",         ibanAccredito:'").append(ibanAccredito).append("'");
        sb.append(",         bicAccredito:'").append(bicAccredito).append("'");
        sb.append(",         flgAderiscePiemontepay:").append(flgAderiscePiemontepay);
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append("}");
        return sb.toString();
    }
}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Istanza attore dto.
 */
public class IstanzaAttoreDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza_attore")
    private Long idIstanzaAttore;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_abilitazione")
    private Long idTipoAbilitazione;

    @JsonProperty("id_compilante")
    private Long idCompilante;

    @JsonProperty("id_profilo_app")
    private Long idProfiloApp;

    @JsonProperty("cf_attore")
    private String cfAttore;

    @JsonProperty("cf_abilitante_delegante")
    private String cfAbilitanteDelegante;

    @JsonProperty("data_inizio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataInizio;

    @JsonProperty("data_revoca")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataRevoca;

    @JsonProperty("data_delega_con_firma")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataDelegaConFirma;

    /**
     * Gets id istanza attore.
     *
     * @return idIstanzaAttore id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore idIstanzaAttore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    /**
     * Gets id istanza.
     *
     * @return idIstanza id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza idIstanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id tipo abilitazione.
     *
     * @return idTipoAbilitazione id tipo abilitazione
     */
    public Long getIdTipoAbilitazione() {
        return idTipoAbilitazione;
    }

    /**
     * Sets id tipo abilitazione.
     *
     * @param idTipoAbilitazione idTipoAbilitazione
     */
    public void setIdTipoAbilitazione(Long idTipoAbilitazione) {
        this.idTipoAbilitazione = idTipoAbilitazione;
    }

    /**
     * Gets id compilante.
     *
     * @return idCompilante id compilante
     */
    public Long getIdCompilante() {
        return idCompilante;
    }

    /**
     * Sets id compilante.
     *
     * @param idCompilante idCompilante
     */
    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    /**
     * Gets id profilo app.
     *
     * @return idProfiloApp id profilo app
     */
    public Long getIdProfiloApp() {
        return idProfiloApp;
    }

    /**
     * Sets id profilo app.
     *
     * @param idProfiloApp idProfiloApp
     */
    public void setIdProfiloApp(Long idProfiloApp) {
        this.idProfiloApp = idProfiloApp;
    }

    /**
     * Gets cf attore.
     *
     * @return cfAttore cf attore
     */
    public String getCfAttore() {
        return cfAttore;
    }

    /**
     * Sets cf attore.
     *
     * @param cfAttore cfAttore
     */
    public void setCfAttore(String cfAttore) {
        this.cfAttore = cfAttore;
    }

    /**
     * Gets cf abilitante delegante.
     *
     * @return cfAbilitanteDelegante cf abilitante delegante
     */
    public String getCfAbilitanteDelegante() {
        return cfAbilitanteDelegante;
    }

    /**
     * Sets cf abilitante delegante.
     *
     * @param cfAbilitanteDelegante cfAbilitanteDelegante
     */
    public void setCfAbilitanteDelegante(String cfAbilitanteDelegante) {
        this.cfAbilitanteDelegante = cfAbilitanteDelegante;
    }

    /**
     * Gets data inizio.
     *
     * @return dataInizio data inizio
     */
    public Timestamp getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets data inizio.
     *
     * @param dataInizio dataInizio
     */
    public void setDataInizio(Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Gets data revoca.
     *
     * @return dataRevoca data revoca
     */
    public Timestamp getDataRevoca() {
        return dataRevoca;
    }

    /**
     * Sets data revoca.
     *
     * @param dataRevoca dataRevoca
     */
    public void setDataRevoca(Timestamp dataRevoca) {
        this.dataRevoca = dataRevoca;
    }

    /**
     * Gets data delega con firma.
     *
     * @return dataDelegaConFirma data delega con firma
     */
    public Timestamp getDataDelegaConFirma() {
        return dataDelegaConFirma;
    }

    /**
     * Sets data delega con firma.
     *
     * @param dataDelegaConFirma dataDelegaConFirma
     */
    public void setDataDelegaConFirma(Timestamp dataDelegaConFirma) {
        this.dataDelegaConFirma = dataDelegaConFirma;
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
        IstanzaAttoreDTO that = (IstanzaAttoreDTO) o;
        return Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipoAbilitazione, that.idTipoAbilitazione) && Objects.equals(idCompilante, that.idCompilante) && Objects.equals(idProfiloApp, that.idProfiloApp) && Objects.equals(cfAttore, that.cfAttore) && Objects.equals(cfAbilitanteDelegante, that.cfAbilitanteDelegante) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataRevoca, that.dataRevoca) && Objects.equals(dataDelegaConFirma, that.dataDelegaConFirma);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanzaAttore, idIstanza, idTipoAbilitazione, idCompilante, idProfiloApp, cfAttore, cfAbilitanteDelegante, dataInizio, dataRevoca, dataDelegaConFirma);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaAttoreDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idIstanzaAttore:").append(idIstanzaAttore);
        sb.append(",         idIstanza:").append(idIstanza);
        sb.append(",         idTipoAbilitazione:").append(idTipoAbilitazione);
        sb.append(",         idCompilante:").append(idCompilante);
        sb.append(",         idProfiloApp:").append(idProfiloApp);
        sb.append(",         cfAttore:'").append(cfAttore).append("'");
        sb.append(",         cfAbilitanteDelegante:'").append(cfAbilitanteDelegante).append("'");
        sb.append(",         dataInizio:").append(dataInizio);
        sb.append(",         dataRevoca:").append(dataRevoca);
        sb.append(",         dataDelegaConFirma:").append(dataDelegaConFirma);
        sb.append("}");
        return sb.toString();
    }

}
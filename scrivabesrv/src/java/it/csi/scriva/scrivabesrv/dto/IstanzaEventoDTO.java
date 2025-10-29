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
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Istanza evento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IstanzaEventoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza_evento")
    private Long idIstanzaEvento;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_tipo_evento")
    private Long idTipoEvento;

    @JsonProperty("data_evento")
    private Timestamp dataEvento;

    @JsonProperty("id_istanza_attore")
    private Long idIstanzaAttore;

    @JsonProperty("id_funzionario")
    private Long idFunzionario;

    @JsonProperty("id_componente_app")
    private Long idComponenteApp;

    /**
     * Gets id istanza evento.
     *
     * @return the id istanza evento
     */
    public Long getIdIstanzaEvento() {
        return idIstanzaEvento;
    }

    /**
     * Sets id istanza evento.
     *
     * @param idIstanzaEvento the id istanza evento
     */
    public void setIdIstanzaEvento(Long idIstanzaEvento) {
        this.idIstanzaEvento = idIstanzaEvento;
    }

    /**
     * Gets id istanza.
     *
     * @return the id istanza
     */
    public Long getIdIstanza() {
        return idIstanza;
    }

    /**
     * Sets id istanza.
     *
     * @param idIstanza the id istanza
     */
    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    /**
     * Gets id tipo evento.
     *
     * @return the id tipo evento
     */
    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    /**
     * Sets id tipo evento.
     *
     * @param idTipoEvento the id tipo evento
     */
    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    /**
     * Gets data evento.
     *
     * @return the data evento
     */
    public Timestamp getDataEvento() {
        return dataEvento;
    }

    /**
     * Sets data evento.
     *
     * @param dataEvento the data evento
     */
    public void setDataEvento(Timestamp dataEvento) {
        this.dataEvento = dataEvento;
    }

    /**
     * Gets id istanza attore.
     *
     * @return the id istanza attore
     */
    public Long getIdIstanzaAttore() {
        return idIstanzaAttore;
    }

    /**
     * Sets id istanza attore.
     *
     * @param idIstanzaAttore the id istanza attore
     */
    public void setIdIstanzaAttore(Long idIstanzaAttore) {
        this.idIstanzaAttore = idIstanzaAttore;
    }

    /**
     * Gets id funzionario.
     *
     * @return the id funzionario
     */
    public Long getIdFunzionario() {
        return idFunzionario;
    }

    /**
     * Sets id funzionario.
     *
     * @param idFunzionario the id funzionario
     */
    public void setIdFunzionario(Long idFunzionario) {
        this.idFunzionario = idFunzionario;
    }

    /**
     * Gets id componente app.
     *
     * @return the id componente app
     */
    public Long getIdComponenteApp() {
        return idComponenteApp;
    }

    /**
     * Sets id componente app.
     *
     * @param idComponenteApp the id componente app
     */
    public void setIdComponenteApp(Long idComponenteApp) {
        this.idComponenteApp = idComponenteApp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IstanzaEventoDTO that = (IstanzaEventoDTO) o;
        return Objects.equals(idIstanzaEvento, that.idIstanzaEvento) && Objects.equals(idIstanza, that.idIstanza) && Objects.equals(idTipoEvento, that.idTipoEvento) && Objects.equals(dataEvento, that.dataEvento) && Objects.equals(idIstanzaAttore, that.idIstanzaAttore) && Objects.equals(idFunzionario, that.idFunzionario) && Objects.equals(idComponenteApp, that.idComponenteApp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idIstanzaEvento, idIstanza, idTipoEvento, dataEvento, idIstanzaAttore, idFunzionario, idComponenteApp);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IstanzaEventoDTO {\n");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",\n         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",\n         gestDataUpd:").append(gestDataUpd);
        sb.append(",\n         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",\n         gestUID:'").append(gestUID).append("'");
        sb.append(",\n         idIstanzaEvento:").append(idIstanzaEvento);
        sb.append(",\n         idIstanza:").append(idIstanza);
        sb.append(",\n         idTipoEvento:").append(idTipoEvento);
        sb.append(",\n         dataEvento:").append(dataEvento);
        sb.append(",\n         idIstanzaAttore:").append(idIstanzaAttore);
        sb.append(",\n         idFunzionario:").append(idFunzionario);
        sb.append(",\n         idComponenteApp:").append(idComponenteApp);
        sb.append("}\n");
        return sb.toString();
    }
}
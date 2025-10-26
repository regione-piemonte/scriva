/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tipo evento dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoEventoDTO implements Serializable {

    @JsonProperty("id_tipo_evento")
    private Long idTipoEvento;

    @JsonProperty("id_stato_istanza_evento")
    private Long idStatoIstanzaEvento;

    @JsonProperty("cod_tipo_evento")
    private String codTipoEvento;

    @JsonProperty("des_tipo_evento")
    private String desTipoEvento;

    @JsonProperty("ind_visibile")
    private String indVisibile;

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
     * Gets id stato istanza evento.
     *
     * @return the id stato istanza evento
     */
    public Long getIdStatoIstanzaEvento() {
        return idStatoIstanzaEvento;
    }

    /**
     * Sets id stato istanza evento.
     *
     * @param idStatoIstanzaEvento the id stato istanza evento
     */
    public void setIdStatoIstanzaEvento(Long idStatoIstanzaEvento) {
        this.idStatoIstanzaEvento = idStatoIstanzaEvento;
    }

    /**
     * Gets cod tipo evento.
     *
     * @return the cod tipo evento
     */
    public String getCodTipoEvento() {
        return codTipoEvento;
    }

    /**
     * Sets cod tipo evento.
     *
     * @param codTipoEvento the cod tipo evento
     */
    public void setCodTipoEvento(String codTipoEvento) {
        this.codTipoEvento = codTipoEvento;
    }

    /**
     * Gets des tipo evento.
     *
     * @return the des tipo evento
     */
    public String getDesTipoEvento() {
        return desTipoEvento;
    }

    /**
     * Sets des tipo evento.
     *
     * @param desTipoEvento the des tipo evento
     */
    public void setDesTipoEvento(String desTipoEvento) {
        this.desTipoEvento = desTipoEvento;
    }

    /**
     * Gets ind visibile.
     *
     * @return the ind visibile
     */
    public String getIndVisibile() {
        return indVisibile;
    }

    /**
     * Sets ind visibile.
     *
     * @param indVisibile the ind visibile
     */
    public void setIndVisibile(String indVisibile) {
        this.indVisibile = indVisibile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEventoDTO that = (TipoEventoDTO) o;
        return Objects.equals(idTipoEvento, that.idTipoEvento) && Objects.equals(idStatoIstanzaEvento, that.idStatoIstanzaEvento) && Objects.equals(codTipoEvento, that.codTipoEvento) && Objects.equals(desTipoEvento, that.desTipoEvento) && Objects.equals(indVisibile, that.indVisibile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoEvento, idStatoIstanzaEvento, codTipoEvento, desTipoEvento, indVisibile);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoEventoDTO {\n");
        sb.append("         idTipoEvento:").append(idTipoEvento);
        sb.append(",\n         idStatoIstanzaEvento:").append(idStatoIstanzaEvento);
        sb.append(",\n         codTipoEvento:'").append(codTipoEvento).append("'");
        sb.append(",\n         desTipoEvento:'").append(desTipoEvento).append("'");
        sb.append(",\n         indVisibile:'").append(indVisibile).append("'");
        sb.append("}\n");
        return sb.toString();
    }
}
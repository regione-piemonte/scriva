/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class QuadroExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_quadro")
    private Long idQuadro;

    @JsonProperty("tipo_quadro")
    private TipoQuadroDTO tipoQuadro;

    @JsonProperty("num_versione")
    private Integer numVersione;

    @JsonProperty("flg_tipo_gestione")
    private String flgTipoGestione;

    @JsonProperty("json_configura_quadro")
    private String jsonConfiguraQuadro;

    @JsonProperty("json_configura_riepilogo")
    private String jsonConfiguraRiepilogo;

    @JsonIgnore
    private Boolean flgAttivo;

    public Long getIdQuadro() {
        return idQuadro;
    }

    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
    }

    public TipoQuadroDTO getTipoQuadro() {
        return tipoQuadro;
    }

    public void setTipoQuadro(TipoQuadroDTO tipoQuadro) {
        this.tipoQuadro = tipoQuadro;
    }

    public Integer getNumVersione() {
        return numVersione;
    }

    public void setNumVersione(Integer numVersione) {
        this.numVersione = numVersione;
    }

    public String getFlgTipoGestione() {
        return flgTipoGestione;
    }

    public void setFlgTipoGestione(String flgTipoGestione) {
        this.flgTipoGestione = flgTipoGestione;
    }

    public String getJsonConfiguraQuadro() {
        return jsonConfiguraQuadro;
    }

    public void setJsonConfiguraQuadro(String jsonConfiguraQuadro) {
        this.jsonConfiguraQuadro = jsonConfiguraQuadro;
    }

    public Boolean getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(Boolean flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    public String getJsonConfiguraRiepilogo() {
        return jsonConfiguraRiepilogo;
    }

    public void setJsonConfiguraRiepilogo(String jsonConfiguraRiepilogo) {
        this.jsonConfiguraRiepilogo = jsonConfiguraRiepilogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QuadroExtendedDTO that = (QuadroExtendedDTO) o;
        return Objects.equals(idQuadro, that.idQuadro) && Objects.equals(tipoQuadro, that.tipoQuadro) && Objects.equals(numVersione, that.numVersione) && Objects.equals(flgTipoGestione, that.flgTipoGestione) && Objects.equals(jsonConfiguraQuadro, that.jsonConfiguraQuadro) && Objects.equals(jsonConfiguraRiepilogo, that.jsonConfiguraRiepilogo) && Objects.equals(flgAttivo, that.flgAttivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuadro, tipoQuadro, numVersione, flgTipoGestione, jsonConfiguraQuadro, jsonConfiguraRiepilogo, flgAttivo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuadroExtendedDTO {");
        sb.append("         idQuadro:").append(idQuadro);
        sb.append(",         tipoQuadro:").append(tipoQuadro);
        sb.append(",         numVersione:").append(numVersione);
        sb.append(",         flgTipoGestione:'").append(flgTipoGestione).append("'");
        sb.append(",         jsonConfiguraQuadro:'").append(jsonConfiguraQuadro).append("'");
        sb.append(",         jsonConfiguraRiepilogo:'").append(jsonConfiguraRiepilogo).append("'");
        sb.append(",         flgAttivo:").append(flgAttivo);
        sb.append("}");
        return sb.toString();
    }
}
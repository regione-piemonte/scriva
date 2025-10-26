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
 * The type Soggetto gruppo dto.
 *
 * @author CSI PIEMONTE
 */
public class SoggettoGruppoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_gruppo_soggetto")
    private Long idGruppoSoggetto;

    @JsonProperty("id_soggetto_istanza")
    private Long idSoggettoIstanza;

    @JsonProperty("flg_capo_gruppo")
    private Boolean flgCapogruppo;

    /**
     * Gets id gruppo soggetto.
     *
     * @return the id gruppo soggetto
     */
    public Long getIdGruppoSoggetto() {
        return idGruppoSoggetto;
    }

    /**
     * Sets id gruppo soggetto.
     *
     * @param idGruppoSoggetto the id gruppo soggetto
     */
    public void setIdGruppoSoggetto(Long idGruppoSoggetto) {
        this.idGruppoSoggetto = idGruppoSoggetto;
    }

    /**
     * Gets id soggetto istanza.
     *
     * @return the id soggetto istanza
     */
    public Long getIdSoggettoIstanza() {
        return idSoggettoIstanza;
    }

    /**
     * Sets id soggetto istanza.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     */
    public void setIdSoggettoIstanza(Long idSoggettoIstanza) {
        this.idSoggettoIstanza = idSoggettoIstanza;
    }

    /**
     * Gets flg capogruppo.
     *
     * @return the flg capogruppo
     */
    public Boolean getFlgCapogruppo() {
        return flgCapogruppo;
    }

    /**
     * Sets flg capogruppo.
     *
     * @param flgCapogruppo the flg capogruppo
     */
    public void setFlgCapogruppo(Boolean flgCapogruppo) {
        this.flgCapogruppo = flgCapogruppo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SoggettoGruppoDTO that = (SoggettoGruppoDTO) o;
        return Objects.equals(idGruppoSoggetto, that.idGruppoSoggetto) && Objects.equals(idSoggettoIstanza, that.idSoggettoIstanza) && Objects.equals(flgCapogruppo, that.flgCapogruppo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idGruppoSoggetto, idSoggettoIstanza, flgCapogruppo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SoggettoGruppoDTO {");
        sb.append("         gestDataIns:").append(gestDataIns);
        sb.append(",         gestAttoreIns:'").append(gestAttoreIns).append("'");
        sb.append(",         gestDataUpd:").append(gestDataUpd);
        sb.append(",         gestAttoreUpd:'").append(gestAttoreUpd).append("'");
        sb.append(",         gestUID:'").append(gestUID).append("'");
        sb.append(",         idGruppoSoggetto:").append(idGruppoSoggetto);
        sb.append(",         idSoggettoIstanza:").append(idSoggettoIstanza);
        sb.append(",         flgCapogruppo:").append(flgCapogruppo);
        sb.append("}");
        return sb.toString();
    }
}
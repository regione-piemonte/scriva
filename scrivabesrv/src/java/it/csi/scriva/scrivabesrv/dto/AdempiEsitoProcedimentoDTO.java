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
import java.sql.Date;
import java.util.Objects;

/**
 * The type Adempi esito procedimento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdempiEsitoProcedimentoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_adempimento")
    private Long idAdempimento;

    @JsonProperty("id_esito_procedimento")
    private Long idEsitoProcedimento;

    @JsonProperty("data_inizio_validita")
    private Date dataInizioValidita;

    @JsonProperty("data_fine_validita")
    private Date dataFineValidita;

    @JsonProperty("ind_esito")
    private String indEsito;

    /**
     * Gets id adempimento.
     *
     * @return the id adempimento
     */
    public Long getIdAdempimento() {
        return idAdempimento;
    }

    /**
     * Sets id adempimento.
     *
     * @param idAdempimento the id adempimento
     */
    public void setIdAdempimento(Long idAdempimento) {
        this.idAdempimento = idAdempimento;
    }

    /**
     * Gets esito procedimento.
     *
     * @return the esito procedimento
     */
    public Long getIdEsitoProcedimento() {
        return idEsitoProcedimento;
    }

    /**
     * Sets esito procedimento.
     *
     * @param idEsitoProcedimento the id esito procedimento
     */
    public void setIdEsitoProcedimento(Long idEsitoProcedimento) {
        this.idEsitoProcedimento = idEsitoProcedimento;
    }

    /**
     * Gets data inizio validita.
     *
     * @return the data inizio validita
     */
    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets data inizio validita.
     *
     * @param dataInizioValidita the data inizio validita
     */
    public void setDataInizioValidita(Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    /**
     * Gets data fine validita.
     *
     * @return the data fine validita
     */
    public Date getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets data fine validita.
     *
     * @param dataFineValidita the data fine validita
     */
    public void setDataFineValidita(Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    /**
     * Gets ind esito.
     *
     * @return the ind esito
     */
    public String getIndEsito() {
        return indEsito;
    }

    /**
     * Sets ind esito.
     *
     * @param indEsito the ind esito
     */
    public void setIndEsito(String indEsito) {
        this.indEsito = indEsito;
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
        AdempiEsitoProcedimentoDTO that = (AdempiEsitoProcedimentoDTO) o;
        return Objects.equals(idAdempimento, that.idAdempimento) && Objects.equals(idEsitoProcedimento, that.idEsitoProcedimento) && Objects.equals(dataInizioValidita, that.dataInizioValidita) && Objects.equals(dataFineValidita, that.dataFineValidita) && Objects.equals(indEsito, that.indEsito);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idAdempimento, idEsitoProcedimento, dataInizioValidita, dataFineValidita, indEsito);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "AdempiEsitoProcedimentoDTO {\n" +
                "         idAdempimento:" + idAdempimento +
                ",\n         idEsitoProcedimento:" + idEsitoProcedimento +
                ",\n         dataInizioValidita:" + dataInizioValidita +
                ",\n         dataFineValidita:" + dataFineValidita +
                ",\n         indEsito:'" + indEsito + "'" +
                ",\n         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                "}\n";
    }
}
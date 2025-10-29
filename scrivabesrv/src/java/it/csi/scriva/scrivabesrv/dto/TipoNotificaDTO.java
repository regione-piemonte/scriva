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

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The type Tipo notifica dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoNotificaDTO implements Serializable {

    private Long idTipoNotifica;

    private String codTipoNotifica;

    private String desTipoNotifica;

    private Date dataInizio;

    private Date dataFine;

    /**
     * Gets id tipo notifica.
     *
     * @return the id tipo notifica
     */
    public Long getIdTipoNotifica() {
        return idTipoNotifica;
    }

    /**
     * Sets id tipo notifica.
     *
     * @param idTipoNotifica the id tipo notifica
     */
    public void setIdTipoNotifica(Long idTipoNotifica) {
        this.idTipoNotifica = idTipoNotifica;
    }

    /**
     * Gets cod tipo notifica.
     *
     * @return the cod tipo notifica
     */
    public String getCodTipoNotifica() {
        return codTipoNotifica;
    }

    /**
     * Sets cod tipo notifica.
     *
     * @param codTipoNotifica the cod tipo notifica
     */
    public void setCodTipoNotifica(String codTipoNotifica) {
        this.codTipoNotifica = codTipoNotifica;
    }

    /**
     * Gets des tipo notifica.
     *
     * @return the des tipo notifica
     */
    public String getDesTipoNotifica() {
        return desTipoNotifica;
    }

    /**
     * Sets des tipo notifica.
     *
     * @param desTipoNotifica the des tipo notifica
     */
    public void setDesTipoNotifica(String desTipoNotifica) {
        this.desTipoNotifica = desTipoNotifica;
    }

    /**
     * Gets data inizio.
     *
     * @return the data inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets data inizio.
     *
     * @param dataInizio the data inizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Gets data fine.
     *
     * @return the data fine
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Sets data fine.
     *
     * @param dataFine the data fine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoNotificaDTO that = (TipoNotificaDTO) o;
        return Objects.equals(idTipoNotifica, that.idTipoNotifica) && Objects.equals(codTipoNotifica, that.codTipoNotifica) && Objects.equals(desTipoNotifica, that.desTipoNotifica) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoNotifica, codTipoNotifica, desTipoNotifica, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoNotificaDTO {\n" +
                "         idTipoNotifica:" + idTipoNotifica +
                ",\n         codTipoNotifica:'" + codTipoNotifica + "'" +
                ",\n         desTipoNotifica:'" + desTipoNotifica + "'" +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                "}\n";
    }

}
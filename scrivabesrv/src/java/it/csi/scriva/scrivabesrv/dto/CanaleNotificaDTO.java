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
 * The type Canale notifica dto.
 *
 * @author CSI PIEMONTE
 */
public class CanaleNotificaDTO implements Serializable {

    private Long idCanaleNotifica;

    private Long idComponenteApplPush;

    private String codCanaleNotifica;

    private String desCanaleNotifica;

    private Boolean flgCampoCc;

    private Date dataInizio;

    private Date dataFine;

    private String indTipoCanale;

    /**
     * Gets id canale notifica.
     *
     * @return the id canale notifica
     */
    public Long getIdCanaleNotifica() {
        return idCanaleNotifica;
    }

    /**
     * Sets id canale notifica.
     *
     * @param idCanaleNotifica the id canale notifica
     */
    public void setIdCanaleNotifica(Long idCanaleNotifica) {
        this.idCanaleNotifica = idCanaleNotifica;
    }

    /**
     * Gets id componente appl push.
     *
     * @return the id componente appl push
     */
    public Long getIdComponenteApplPush() {
        return idComponenteApplPush;
    }

    /**
     * Sets id componente appl push.
     *
     * @param idComponenteApplPush the id componente appl push
     */
    public void setIdComponenteApplPush(Long idComponenteApplPush) {
        this.idComponenteApplPush = idComponenteApplPush;
    }

    /**
     * Gets cod canale notifica.
     *
     * @return the cod canale notifica
     */
    public String getCodCanaleNotifica() {
        return codCanaleNotifica;
    }

    /**
     * Sets cod canale notifica.
     *
     * @param codCanaleNotifica the cod canale notifica
     */
    public void setCodCanaleNotifica(String codCanaleNotifica) {
        this.codCanaleNotifica = codCanaleNotifica;
    }

    /**
     * Gets des canale notifica.
     *
     * @return the des canale notifica
     */
    public String getDesCanaleNotifica() {
        return desCanaleNotifica;
    }

    /**
     * Sets des canale notifica.
     *
     * @param desCanaleNotifica the des canale notifica
     */
    public void setDesCanaleNotifica(String desCanaleNotifica) {
        this.desCanaleNotifica = desCanaleNotifica;
    }

    /**
     * Gets flg campo cc.
     *
     * @return the flg campo cc
     */
    public Boolean getFlgCampoCc() {
        return flgCampoCc;
    }

    /**
     * Sets flg campo cc.
     *
     * @param flgCampoCc the flg campo cc
     */
    public void setFlgCampoCc(Boolean flgCampoCc) {
        this.flgCampoCc = flgCampoCc;
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
     * Gets ind tipo canale.
     *
     * @return the ind tipo canale
     */
    public String getIndTipoCanale() {
        return indTipoCanale;
    }

    /**
     * Sets ind tipo canale.
     *
     * @param indTipoCanale the ind tipo canale
     */
    public void setIndTipoCanale(String indTipoCanale) {
        this.indTipoCanale = indTipoCanale;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CanaleNotificaDTO that = (CanaleNotificaDTO) o;
        return Objects.equals(idCanaleNotifica, that.idCanaleNotifica) && Objects.equals(idComponenteApplPush, that.idComponenteApplPush) && Objects.equals(codCanaleNotifica, that.codCanaleNotifica) && Objects.equals(desCanaleNotifica, that.desCanaleNotifica) && Objects.equals(flgCampoCc, that.flgCampoCc) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine) && Objects.equals(indTipoCanale, that.indTipoCanale);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCanaleNotifica, idComponenteApplPush, codCanaleNotifica, desCanaleNotifica, flgCampoCc, dataInizio, dataFine, indTipoCanale);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CanaleNotificaDTO {\n" +
                "         idCanaleNotifica:" + idCanaleNotifica +
                ",\n         idComponenteApplPush:" + idComponenteApplPush +
                ",\n         codCanaleNotifica:'" + codCanaleNotifica + "'" +
                ",\n         desCanaleNotifica:'" + desCanaleNotifica + "'" +
                ",\n         flgCampoCc:" + flgCampoCc +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                ",\n         indTipoCanale:'" + indTipoCanale + "'" +
                "}\n";
    }
}
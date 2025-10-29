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
import java.util.Objects;

/**
 * The type Stato notifica dto.
 *
 * @author CSI PIEMONTE
 */
public class StatoNotificaDTO implements Serializable {

    private Long idStatoNotifica;

    private String codStatoNotifica;

    private String desStatoNotifica;

    /**
     * Gets id stato notifica.
     *
     * @return the id stato notifica
     */
    public Long getIdStatoNotifica() {
        return idStatoNotifica;
    }

    /**
     * Sets id stato notifica.
     *
     * @param idStatoNotifica the id stato notifica
     */
    public void setIdStatoNotifica(Long idStatoNotifica) {
        this.idStatoNotifica = idStatoNotifica;
    }

    /**
     * Gets cod stato notifica.
     *
     * @return the cod stato notifica
     */
    public String getCodStatoNotifica() {
        return codStatoNotifica;
    }

    /**
     * Sets cod stato notifica.
     *
     * @param codStatoNotifica the cod stato notifica
     */
    public void setCodStatoNotifica(String codStatoNotifica) {
        this.codStatoNotifica = codStatoNotifica;
    }

    /**
     * Gets des stato notifica.
     *
     * @return the des stato notifica
     */
    public String getDesStatoNotifica() {
        return desStatoNotifica;
    }

    /**
     * Sets des stato notifica.
     *
     * @param desStatoNotifica the des stato notifica
     */
    public void setDesStatoNotifica(String desStatoNotifica) {
        this.desStatoNotifica = desStatoNotifica;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatoNotificaDTO that = (StatoNotificaDTO) o;
        return Objects.equals(idStatoNotifica, that.idStatoNotifica) && Objects.equals(codStatoNotifica, that.codStatoNotifica) && Objects.equals(desStatoNotifica, that.desStatoNotifica);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idStatoNotifica, codStatoNotifica, desStatoNotifica);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "StatoNotificaDTO {\n" +
                "         idStatoNotifica:" + idStatoNotifica +
                ",\n         codStatoNotifica:'" + codStatoNotifica + "'" +
                ",\n         desStatoNotifica:'" + desStatoNotifica + "'" +
                "}\n";
    }
}
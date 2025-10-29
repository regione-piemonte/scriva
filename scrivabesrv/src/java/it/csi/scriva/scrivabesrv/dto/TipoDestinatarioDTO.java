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
 * The type Tipo destinatario dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoDestinatarioDTO implements Serializable {

    private Long idTipoDestinatario;

    private String codTipoDestinatario;

    private String desTipoDestinatario;

    /**
     * Gets id tipo destinatario.
     *
     * @return the id tipo destinatario
     */
    public Long getIdTipoDestinatario() {
        return idTipoDestinatario;
    }

    /**
     * Sets id tipo destinatario.
     *
     * @param idTipoDestinatario the id tipo destinatario
     */
    public void setIdTipoDestinatario(Long idTipoDestinatario) {
        this.idTipoDestinatario = idTipoDestinatario;
    }

    /**
     * Gets cod tipo destinatario.
     *
     * @return the cod tipo destinatario
     */
    public String getCodTipoDestinatario() {
        return codTipoDestinatario;
    }

    /**
     * Sets cod tipo destinatario.
     *
     * @param codTipoDestinatario the cod tipo destinatario
     */
    public void setCodTipoDestinatario(String codTipoDestinatario) {
        this.codTipoDestinatario = codTipoDestinatario;
    }

    /**
     * Gets des tipo destinatario.
     *
     * @return the des tipo destinatario
     */
    public String getDesTipoDestinatario() {
        return desTipoDestinatario;
    }

    /**
     * Sets des tipo destinatario.
     *
     * @param desTipoDestinatario the des tipo destinatario
     */
    public void setDesTipoDestinatario(String desTipoDestinatario) {
        this.desTipoDestinatario = desTipoDestinatario;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoDestinatarioDTO that = (TipoDestinatarioDTO) o;
        return Objects.equals(idTipoDestinatario, that.idTipoDestinatario) && Objects.equals(codTipoDestinatario, that.codTipoDestinatario) && Objects.equals(desTipoDestinatario, that.desTipoDestinatario);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(idTipoDestinatario, codTipoDestinatario, desTipoDestinatario);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoDestinatarioDTO {\n" +
                "         idTipoDestinatario:" + idTipoDestinatario +
                ",\n         codTipoDestinatario:'" + codTipoDestinatario + "'" +
                ",\n         desTipoDestinatario:'" + desTipoDestinatario + "'" +
                "}\n";
    }
}
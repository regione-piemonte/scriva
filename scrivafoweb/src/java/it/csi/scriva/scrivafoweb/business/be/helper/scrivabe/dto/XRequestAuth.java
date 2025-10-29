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

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Attore scriva.
 *
 * @author CSI PIEMONTE
 */
public class XRequestAuth implements Serializable {

    private String componenteApplicativa;

    private String ruolo;

    private String username;

    private Boolean password;

    /**
     * Gets componente applicativa.
     *
     * @return the componente applicativa
     */
    public String getComponenteApplicativa() {
        return componenteApplicativa;
    }

    /**
     * Sets componente applicativa.
     *
     * @param componenteApplicativa the componente applicativa
     */
    public void setComponenteApplicativa(String componenteApplicativa) {
        this.componenteApplicativa = componenteApplicativa;
    }

    /**
     * Gets ruolo.
     *
     * @return the ruolo
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Sets ruolo.
     *
     * @param ruolo the ruolo
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public Boolean getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(Boolean password) {
        this.password = password;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XRequestAuth that = (XRequestAuth) o;
        return Objects.equals(componenteApplicativa, that.componenteApplicativa) && Objects.equals(ruolo, that.ruolo) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(componenteApplicativa, ruolo, username, password);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("XRequestAuth {\n");
        sb.append("         componenteApplicativa:'").append(componenteApplicativa).append("'");
        sb.append(",\n         ruolo:'").append(ruolo).append("'");
        sb.append(",\n         username:'").append(username).append("'");
        sb.append(",\n         password:").append(password);
        sb.append("}\n");
        return sb.toString();
    }
}
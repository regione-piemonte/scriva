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

/**
 * The type Operation context dto.
 */
public class OperationContextDTO implements Serializable {

    private String username;

    private String password;

    private String tenant;

    private String repository;

    private String fruitore;

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
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets tenant.
     *
     * @return the tenant
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * Sets tenant.
     *
     * @param tenant the tenant
     */
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * Gets repository.
     *
     * @return the repository
     */
    public String getRepository() {
        return repository;
    }

    /**
     * Sets repository.
     *
     * @param repository the repository
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    /**
     * Gets fruitore.
     *
     * @return the fruitore
     */
    public String getFruitore() {
        return fruitore;
    }

    /**
     * Sets fruitore.
     *
     * @param fruitore the fruitore
     */
    public void setFruitore(String fruitore) {
        this.fruitore = fruitore;
    }
}
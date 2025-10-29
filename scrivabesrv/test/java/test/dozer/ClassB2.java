/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package test.dozer;

public class ClassB2 {
    private String name;
    private String anni;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnni() {
        return anni;
    }

    public void setAnni(String anni) {
        this.anni = anni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "ClassB2 {\n" +
                "         name:'" + name + "'" +
                ",\n         ciccio:'" + anni + "'" +
                ",\n         address:'" + address + "'" +
                "}\n";
    }
}
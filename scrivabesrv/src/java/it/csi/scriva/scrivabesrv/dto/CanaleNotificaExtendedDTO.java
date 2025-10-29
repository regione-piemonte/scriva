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
 * The type Canale notifica dto.
 *
 * @author CSI PIEMONTE
 */
public class CanaleNotificaExtendedDTO extends CanaleNotificaDTO implements Serializable {

    private ComponenteAppDTO componenteApplPush;

    /**
     * Gets componente appl push.
     *
     * @return the componente appl push
     */
    public ComponenteAppDTO getComponenteApplPush() {
        return componenteApplPush;
    }

    /**
     * Sets componente appl push.
     *
     * @param componenteApplPush the componente appl push
     */
    public void setComponenteApplPush(ComponenteAppDTO componenteApplPush) {
        this.componenteApplPush = componenteApplPush;
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
        CanaleNotificaExtendedDTO that = (CanaleNotificaExtendedDTO) o;
        return Objects.equals(componenteApplPush, that.componenteApplPush);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), componenteApplPush);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "CanaleNotificaExtendedDTO {\n" +
                "         componenteApplPush:" + componenteApplPush +
                super.toString() +
                "}\n";
    }
}
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
 * The type Tipo notifica evento adempi extended dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoNotificaEventoAdempiExtendedDTO extends TipoNotificaEventoAdempiDTO implements Serializable {

    private TipoNotificaEventoExtendedDTO tipoNotificaEvento;

    private AdempimentoDTO adempimentoDTO;

    public TipoNotificaEventoExtendedDTO getTipoNotificaEvento() {
        return tipoNotificaEvento;
    }

    public void setTipoNotificaEvento(TipoNotificaEventoExtendedDTO tipoNotificaEvento) {
        this.tipoNotificaEvento = tipoNotificaEvento;
    }

    public AdempimentoDTO getAdempimentoDTO() {
        return adempimentoDTO;
    }

    public void setAdempimentoDTO(AdempimentoDTO adempimentoDTO) {
        this.adempimentoDTO = adempimentoDTO;
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
        TipoNotificaEventoAdempiExtendedDTO that = (TipoNotificaEventoAdempiExtendedDTO) o;
        return Objects.equals(tipoNotificaEvento, that.tipoNotificaEvento) && Objects.equals(adempimentoDTO, that.adempimentoDTO);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipoNotificaEvento, adempimentoDTO);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoNotificaEventoAdempiExtendedDTO {\n" +
                "         tipoNotificaEvento:" + tipoNotificaEvento +
                ",\n         adempimentoDTO:" + adempimentoDTO +
                super.toString() +
                "}\n";
    }
}
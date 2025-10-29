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
 * The type Tipo notifica evento adempi extended dto.
 *
 * @author CSI PIEMONTE
 */
public class TipoNotificaEventoAdempiDTO extends BaseDTO implements Serializable {

    private Long idTipoNotificaEventoAdempi;

    private Long idTipoNotificaEvento;

    private Long idAdempimentoDTO;

    private Date dataInizio;

    private Date dataFine;

    public Long getIdTipoNotificaEventoAdempi() {
        return idTipoNotificaEventoAdempi;
    }

    public void setIdTipoNotificaEventoAdempi(Long idTipoNotificaEventoAdempi) {
        this.idTipoNotificaEventoAdempi = idTipoNotificaEventoAdempi;
    }

    public Long getIdTipoNotificaEvento() {
        return idTipoNotificaEvento;
    }

    public void setIdTipoNotificaEvento(Long idTipoNotificaEvento) {
        this.idTipoNotificaEvento = idTipoNotificaEvento;
    }

    public Long getIdAdempimentoDTO() {
        return idAdempimentoDTO;
    }

    public void setIdAdempimentoDTO(Long idAdempimentoDTO) {
        this.idAdempimentoDTO = idAdempimentoDTO;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

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
        if (!super.equals(o)) return false;
        TipoNotificaEventoAdempiDTO that = (TipoNotificaEventoAdempiDTO) o;
        return Objects.equals(idTipoNotificaEventoAdempi, that.idTipoNotificaEventoAdempi) && Objects.equals(idTipoNotificaEvento, that.idTipoNotificaEvento) && Objects.equals(idAdempimentoDTO, that.idAdempimentoDTO) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTipoNotificaEventoAdempi, idTipoNotificaEvento, idAdempimentoDTO, dataInizio, dataFine);
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "TipoNotificaEventoAdempiDTO {\n" +
                "         gestDataIns:" + gestDataIns +
                ",\n         gestAttoreIns:'" + gestAttoreIns + "'" +
                ",\n         gestDataUpd:" + gestDataUpd +
                ",\n         gestAttoreUpd:'" + gestAttoreUpd + "'" +
                ",\n         gestUID:'" + gestUID + "'" +
                ",\n         idTipoNotificaEventoAdempi:" + idTipoNotificaEventoAdempi +
                ",\n         idTipoNotificaEvento:" + idTipoNotificaEvento +
                ",\n         idAdempimentoDTO:" + idAdempimentoDTO +
                ",\n         dataInizio:" + dataInizio +
                ",\n         dataFine:" + dataFine +
                "}\n";
    }

}
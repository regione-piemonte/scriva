/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class IstanzaTemplateQuadroDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("id_template_quadro")
    private Long idTemplateQuadro;

    @JsonProperty("json_data_quadro")
    private String jsonDataQuadro;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Long getIdTemplateQuadro() {
        return idTemplateQuadro;
    }

    public void setIdTemplateQuadro(Long idTemplateQuadro) {
        this.idTemplateQuadro = idTemplateQuadro;
    }

    public String getJsonDataQuadro() {
        return jsonDataQuadro;
    }

    public void setJsonDataQuadro(String jsonDataQuadro) {
        this.jsonDataQuadro = jsonDataQuadro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, idTemplateQuadro, jsonDataQuadro);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        IstanzaTemplateQuadroDTO other = (IstanzaTemplateQuadroDTO) obj;
        return Objects.equals(idIstanza, other.idIstanza) && Objects.equals(idTemplateQuadro, other.idTemplateQuadro) && Objects.equals(jsonDataQuadro, other.jsonDataQuadro);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IstanzaTemplateQuadroDTO {\n    idIstanza: ").append(idIstanza).append("\n    idTemplateQuadro: ").append(idTemplateQuadro).append("\n    jsonDataQuadro: ").append(jsonDataQuadro).append("\n    gestDataIns: ").append(gestDataIns).append("\n    gestAttoreIns: ").append(gestAttoreIns).append("\n    gestDataUpd: ").append(gestDataUpd).append("\n    gestAttoreUpd: ").append(gestAttoreUpd).append("\n    gestUID: ").append(gestUID).append("\n}");
        return builder.toString();
    }

}
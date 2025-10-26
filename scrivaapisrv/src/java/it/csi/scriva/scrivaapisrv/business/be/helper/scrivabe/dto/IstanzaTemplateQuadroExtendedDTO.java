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

public class IstanzaTemplateQuadroExtendedDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("template_quadro")
    private TemplateQuadroExtendedDTO templateQuadro;

    @JsonProperty("json_data_quadro")
    private String jsonDataQuadro;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public TemplateQuadroExtendedDTO getTemplateQuadro() {
        return templateQuadro;
    }

    public void setTemplateQuadro(TemplateQuadroExtendedDTO templateQuadro) {
        this.templateQuadro = templateQuadro;
    }

    public String getJsonDataQuadro() {
        return jsonDataQuadro;
    }

    public void setJsonDataQuadro(String jsonDataQuadro) {
        this.jsonDataQuadro = jsonDataQuadro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIstanza, jsonDataQuadro, templateQuadro);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        IstanzaTemplateQuadroExtendedDTO other = (IstanzaTemplateQuadroExtendedDTO) obj;
        return Objects.equals(idIstanza, other.idIstanza) && Objects.equals(jsonDataQuadro, other.jsonDataQuadro) && Objects.equals(templateQuadro, other.templateQuadro);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IstanzaTemplateQuadroExtendedDTO {\n    idIstanza: ").append(idIstanza).append("\n    templateQuadro: ").append(templateQuadro).append("\n    jsonDataQuadro: ").append(jsonDataQuadro).append("\n    gestDataIns: ").append(gestDataIns).append("\n    gestAttoreIns: ").append(gestAttoreIns).append("\n    gestDataUpd: ").append(gestDataUpd).append("\n    gestAttoreUpd: ").append(gestAttoreUpd).append("\n    gestUID: ").append(gestUID).append("\n}");
        return builder.toString();
    }

}
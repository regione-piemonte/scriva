/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class AccreditamentoDTO extends BaseDTO implements Serializable {

    @JsonProperty("id_richiesta_accredito")
    private Long idRichiestaAccredito;

    @JsonProperty("cf_accredito")
    private String cfAccredito;

    @JsonProperty("cognome_accredito")
    private String cognomeAccredito;

    @JsonProperty("nome_accredito")
    private String nomeAccredito;

    @JsonProperty("des_email_accredito")
    private String desEmailAccredito;

    @JsonIgnore
    private String codeVerifica;

    @JsonProperty("flg_autorizza_dati_personali")
    private Boolean flgAutorizzaDatiPersonali;

    @JsonProperty("id_compilante")
    private Long idCompilante;

    public Long getIdRichiestaAccredito() {
        return idRichiestaAccredito;
    }

    public void setIdRichiestaAccredito(Long idRichiestaAccredito) {
        this.idRichiestaAccredito = idRichiestaAccredito;
    }

    public String getCfAccredito() {
        return cfAccredito;
    }

    public void setCfAccredito(String cfAccredito) {
        this.cfAccredito = cfAccredito;
    }

    public String getCognomeAccredito() {
        return cognomeAccredito;
    }

    public void setCognomeAccredito(String cognomeAccredito) {
        this.cognomeAccredito = cognomeAccredito;
    }

    public String getNomeAccredito() {
        return nomeAccredito;
    }

    public void setNomeAccredito(String nomeAccredito) {
        this.nomeAccredito = nomeAccredito;
    }

    public String getDesEmailAccredito() {
        return desEmailAccredito;
    }

    public void setDesEmailAccredito(String desEmailAccredito) {
        this.desEmailAccredito = desEmailAccredito;
    }

    public String getCodeVerifica() {
        return codeVerifica;
    }

    public void setCodeVerifica(String codeVerifica) {
        this.codeVerifica = codeVerifica;
    }

    public Boolean getFlgAutorizzaDatiPersonali() {
        return flgAutorizzaDatiPersonali;
    }

    public void setFlgAutorizzaDatiPersonali(Boolean flgAutorizzaDatiPersonali) {
        this.flgAutorizzaDatiPersonali = flgAutorizzaDatiPersonali;
    }

    public Long getIdCompilante() {
        return idCompilante;
    }

    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cfAccredito, codeVerifica, cognomeAccredito, desEmailAccredito, flgAutorizzaDatiPersonali, idCompilante, idRichiestaAccredito, nomeAccredito);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccreditamentoDTO other = (AccreditamentoDTO) obj;
        return Objects.equals(cfAccredito, other.cfAccredito) && Objects.equals(codeVerifica, other.codeVerifica) && Objects.equals(cognomeAccredito, other.cognomeAccredito) && Objects.equals(desEmailAccredito, other.desEmailAccredito) && Objects.equals(flgAutorizzaDatiPersonali, other.flgAutorizzaDatiPersonali) && Objects.equals(idCompilante, other.idCompilante) && Objects.equals(idRichiestaAccredito, other.idRichiestaAccredito) && Objects.equals(nomeAccredito, other.nomeAccredito);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccreditamentoDTO [idRichiestaAccredito=").append(idRichiestaAccredito).append("\n  cfAccredito=").append(cfAccredito).append("\n  cognomeAccredito=").append(cognomeAccredito).append("\n  nomeAccredito=").append(nomeAccredito).append("\n  desEmailAccredito=").append(desEmailAccredito).append("\n  codeVerifica=").append(codeVerifica).append("\n  flgAutorizzaDatiPersonali=").append(flgAutorizzaDatiPersonali).append("\n  idCompilante=").append(idCompilante).append("]");
        return builder.toString();
    }
}
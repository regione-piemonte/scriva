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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Accreditamento dto.
 *
 * @author CSI PIEMONTE
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    /**
     * getIdRichiestaAccredito
     *
     * @return long id richiesta accredito
     */
    public Long getIdRichiestaAccredito() {
        return idRichiestaAccredito;
    }

    /**
     * setIdRichiestaAccredito
     *
     * @param idRichiestaAccredito idRichiestaAccredito
     */
    public void setIdRichiestaAccredito(Long idRichiestaAccredito) {
        this.idRichiestaAccredito = idRichiestaAccredito;
    }

    /**
     * setIdRichiestaAccredito
     *
     * @return string cf accredito
     */
    public String getCfAccredito() {
        return cfAccredito;
    }

    /**
     * setCfAccredito
     *
     * @param cfAccredito cfAccredito
     */
    public void setCfAccredito(String cfAccredito) {
        this.cfAccredito = cfAccredito;
    }

    /**
     * getCognomeAccredito
     *
     * @return string cognome accredito
     */
    public String getCognomeAccredito() {
        return cognomeAccredito;
    }

    /**
     * setCognomeAccredito
     *
     * @param cognomeAccredito cognomeAccredito
     */
    public void setCognomeAccredito(String cognomeAccredito) {
        this.cognomeAccredito = cognomeAccredito;
    }

    /**
     * getNomeAccredito
     *
     * @return string nome accredito
     */
    public String getNomeAccredito() {
        return nomeAccredito;
    }

    /**
     * setNomeAccredito
     *
     * @param nomeAccredito nomeAccredito
     */
    public void setNomeAccredito(String nomeAccredito) {
        this.nomeAccredito = nomeAccredito;
    }

    /**
     * getDesEmailAccredito
     *
     * @return string des email accredito
     */
    public String getDesEmailAccredito() {
        return desEmailAccredito;
    }

    /**
     * setDesEmailAccredito
     *
     * @param desEmailAccredito desEmailAccredito
     */
    public void setDesEmailAccredito(String desEmailAccredito) {
        this.desEmailAccredito = desEmailAccredito;
    }

    /**
     * getCodeVerifica
     *
     * @return string code verifica
     */
    public String getCodeVerifica() {
        return codeVerifica;
    }

    /**
     * setCodeVerifica
     *
     * @param codeVerifica codeVerifica
     */
    public void setCodeVerifica(String codeVerifica) {
        this.codeVerifica = codeVerifica;
    }

    /**
     * getFlgAutorizzaDatiPersonali
     *
     * @return boolean flg autorizza dati personali
     */
    public Boolean getFlgAutorizzaDatiPersonali() {
        return flgAutorizzaDatiPersonali;
    }

    /**
     * setFlgAutorizzaDatiPersonali
     *
     * @param flgAutorizzaDatiPersonali flgAutorizzaDatiPersonali
     */
    public void setFlgAutorizzaDatiPersonali(Boolean flgAutorizzaDatiPersonali) {
        this.flgAutorizzaDatiPersonali = flgAutorizzaDatiPersonali;
    }

    /**
     * getIdCompilante
     *
     * @return long id compilante
     */
    public Long getIdCompilante() {
        return idCompilante;
    }

    /**
     * setIdCompilante
     *
     * @param idCompilante idCompilante
     */
    public void setIdCompilante(Long idCompilante) {
        this.idCompilante = idCompilante;
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(cfAccredito, codeVerifica, cognomeAccredito, desEmailAccredito, flgAutorizzaDatiPersonali, idCompilante, idRichiestaAccredito, nomeAccredito);
    }

    /**
     * @param obj Object
     * @return boolean
     */
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

    /**
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccreditamentoDTO [idRichiestaAccredito=").append(idRichiestaAccredito).append("\n  cfAccredito=").append(cfAccredito).append("\n  cognomeAccredito=").append(cognomeAccredito).append("\n  nomeAccredito=").append(nomeAccredito).append("\n  desEmailAccredito=").append(desEmailAccredito).append("\n  codeVerifica=").append(codeVerifica).append("\n  flgAutorizzaDatiPersonali=").append(flgAutorizzaDatiPersonali).append("\n  idCompilante=").append(idCompilante).append("]");
        return builder.toString();
    }
}
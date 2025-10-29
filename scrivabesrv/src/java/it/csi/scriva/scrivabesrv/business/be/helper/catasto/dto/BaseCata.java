/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
/**
 * BaseCata
 */
@JsonInclude(Include.NON_NULL)
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-02-04T16:06:02.595Z[GMT]")
public class BaseCata {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("cod_com_belfiore")
  private String codComBelfiore = null;

  @JsonProperty("cod_com_istat")
  private String codComIstat = null;

  @JsonProperty("comune")
  private String comune = null;

  @JsonProperty("sezione")
  private String sezione = null;

  public BaseCata id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BaseCata codComBelfiore(String codComBelfiore) {
    this.codComBelfiore = codComBelfiore;
    return this;
  }

   /**
   * Get codComBelfiore
   * @return codComBelfiore
  **/
  public String getCodComBelfiore() {
    return codComBelfiore;
  }

  public void setCodComBelfiore(String codComBelfiore) {
    this.codComBelfiore = codComBelfiore;
  }

  public BaseCata codComIstat(String codComIstat) {
    this.codComIstat = codComIstat;
    return this;
  }

   /**
   * Get codComIstat
   * @return codComIstat
  **/
  public String getCodComIstat() {
    return codComIstat;
  }

  public void setCodComIstat(String codComIstat) {
    this.codComIstat = codComIstat;
  }

  public BaseCata comune(String comune) {
    this.comune = comune;
    return this;
  }

   /**
   * Get comune
   * @return comune
  **/
  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public BaseCata sezione(String sezione) {
    this.sezione = sezione;
    return this;
  }

   /**
   * Get sezione
   * @return sezione
  **/
  public String getSezione() {
    return sezione;
  }

  public void setSezione(String sezione) {
    this.sezione = sezione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseCata baseCata = (BaseCata) o;
    return Objects.equals(this.id, baseCata.id) &&
        Objects.equals(this.codComBelfiore, baseCata.codComBelfiore) &&
        Objects.equals(this.codComIstat, baseCata.codComIstat) &&
        Objects.equals(this.comune, baseCata.comune) &&
        Objects.equals(this.sezione, baseCata.sezione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codComBelfiore, codComIstat, comune, sezione);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseCata {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    codComBelfiore: ").append(toIndentedString(codComBelfiore)).append("\n");
    sb.append("    codComIstat: ").append(toIndentedString(codComIstat)).append("\n");
    sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
    sb.append("    sezione: ").append(toIndentedString(sezione)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
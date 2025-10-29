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
 * Municipality
 */

@JsonInclude(Include.NON_NULL)
public class Municipality {
  @JsonProperty("comune")
  private String comune = null;

  @JsonProperty("cod_com_istat")
  private String codComIstat = null;

  @JsonProperty("cod_com_belfiore")
  private String codComBelfiore = null;

  @JsonProperty("sigla_provincia")
  private String siglaProvincia = null;

  @JsonProperty("istat_provincia")
  private String istatProvincia = null;

  public Municipality comune(String comune) {
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

  public Municipality codComIstat(String codComIstat) {
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

  public Municipality codComBelfiore(String codComBelfiore) {
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

  public Municipality siglaProvincia(String siglaProvincia) {
    this.siglaProvincia = siglaProvincia;
    return this;
  }

   /**
   * Get siglaProvincia
   * @return siglaProvincia
  **/
  public String getSiglaProvincia() {
    return siglaProvincia;
  }

  public void setSiglaProvincia(String siglaProvincia) {
    this.siglaProvincia = siglaProvincia;
  }

  public Municipality istatProvincia(String istatProvincia) {
    this.istatProvincia = istatProvincia;
    return this;
  }

   /**
   * Get istatProvincia
   * @return istatProvincia
  **/
  public String getIstatProvincia() {
    return istatProvincia;
  }

  public void setIstatProvincia(String istatProvincia) {
    this.istatProvincia = istatProvincia;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Municipality municipality = (Municipality) o;
    return Objects.equals(this.comune, municipality.comune) &&
        Objects.equals(this.codComIstat, municipality.codComIstat) &&
        Objects.equals(this.codComBelfiore, municipality.codComBelfiore) &&
        Objects.equals(this.siglaProvincia, municipality.siglaProvincia) &&
        Objects.equals(this.istatProvincia, municipality.istatProvincia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comune, codComIstat, codComBelfiore, siglaProvincia, istatProvincia);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Municipality {\n");

    sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
    sb.append("    codComIstat: ").append(toIndentedString(codComIstat)).append("\n");
    sb.append("    codComBelfiore: ").append(toIndentedString(codComBelfiore)).append("\n");
    sb.append("    siglaProvincia: ").append(toIndentedString(siglaProvincia)).append("\n");
    sb.append("    istatProvincia: ").append(toIndentedString(istatProvincia)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
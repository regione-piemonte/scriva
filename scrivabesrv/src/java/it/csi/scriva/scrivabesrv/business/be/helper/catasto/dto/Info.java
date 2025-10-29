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
 * Info
 */

@JsonInclude(Include.NON_NULL)
public class Info {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("data_aggiornamento")
  private String dataAggiornamento = null;

  @JsonProperty("disclaimer")
  private String disclaimer = null;

  public Info id(Integer id) {
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

  public Info dataAggiornamento(String dataAggiornamento) {
    this.dataAggiornamento = dataAggiornamento;
    return this;
  }

   /**
   * The date is expressed accordingly the ISO 8601 standard using UTC time
   * @return dataAggiornamento
  **/
  public String getDataAggiornamento() {
    return dataAggiornamento;
  }

  public void setDataAggiornamento(String dataAggiornamento) {
    this.dataAggiornamento = dataAggiornamento;
  }

  public Info disclaimer(String disclaimer) {
    this.disclaimer = disclaimer;
    return this;
  }

   /**
   * Get disclaimer
   * @return disclaimer
  **/
  public String getDisclaimer() {
    return disclaimer;
  }

  public void setDisclaimer(String disclaimer) {
    this.disclaimer = disclaimer;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Info info = (Info) o;
    return Objects.equals(this.id, info.id) &&
        Objects.equals(this.dataAggiornamento, info.dataAggiornamento) &&
        Objects.equals(this.disclaimer, info.disclaimer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dataAggiornamento, disclaimer);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Info {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dataAggiornamento: ").append(toIndentedString(dataAggiornamento)).append("\n");
    sb.append("    disclaimer: ").append(toIndentedString(disclaimer)).append("\n");
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
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

import java.io.Serializable;
import java.util.Objects;
/**
 * CrsProperties
 */

@JsonInclude(Include.NON_NULL)
public class CrsProperties implements Serializable {
  /**
   * Gets or Sets name
   */
//  @JsonAdapter(NameEnum.Adapter.class)
  public enum NameEnum {
    EPSG_32632("EPSG:32632");

    private String value = "EPSG:32632";

    NameEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static NameEnum fromValue(String input) {
      for (NameEnum b : NameEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
//    public static class Adapter extends TypeAdapter<NameEnum> {
//      @Override
//      public void write(final JsonWriter jsonWriter, final NameEnum enumeration) throws IOException {
//        jsonWriter.value(String.valueOf(enumeration.getValue()));
//      }
//
//      @Override
//      public NameEnum read(final JsonReader jsonReader) throws IOException {
//        Object value = jsonReader.nextString();
//        return NameEnum.fromValue((String)(value));
//      }
//    }
  }

  @JsonProperty("name")
  private String name = null;

  public CrsProperties name(NameEnum name) {
    this.name = name.value;
    return this;
  }

   /**
   * Get name
   * @return name
  **/

  public String getName() {
    return name;
  }

public void setName(String name) {
    this.name = "EPSG:32632";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CrsProperties crsProperties = (CrsProperties) o;
    return Objects.equals(this.name, crsProperties.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrsProperties {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
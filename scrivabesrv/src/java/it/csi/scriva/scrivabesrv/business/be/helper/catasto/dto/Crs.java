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
 * Crs
 */

@JsonInclude(Include.NON_NULL)
public class Crs implements Serializable {
  /**
   * Gets or Sets type
   */
//  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    NAME("name"),
    name("name");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static TypeEnum fromValue(String input) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
//    public static class Adapter extends TypeAdapter<TypeEnum> {
//      @Override
//      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
//        jsonWriter.value(String.valueOf(enumeration.getValue()));
//      }
//
//      @Override
//      public TypeEnum read(final JsonReader jsonReader) throws IOException {
//        Object value = jsonReader.nextString();
//        return TypeEnum.fromValue((String)(value));
//      }
//    }
  }

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("properties")
  private CrsProperties properties = null;

  public Crs type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Crs properties(CrsProperties properties) {
    this.properties = properties;
    return this;
  }

   /**
   * Get properties
   * @return properties
  **/
  public CrsProperties getProperties() {
    return properties;
  }

  public void setProperties(CrsProperties properties) {
    this.properties = properties;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Crs crs = (Crs) o;
    return Objects.equals(this.type, crs.type) &&
        Objects.equals(this.properties, crs.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, properties);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Crs {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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
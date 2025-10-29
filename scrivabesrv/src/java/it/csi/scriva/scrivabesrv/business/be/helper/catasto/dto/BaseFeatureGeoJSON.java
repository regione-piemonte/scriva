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
 * BaseFeatureGeoJSON
 */
@JsonInclude(Include.NON_NULL)
public class BaseFeatureGeoJSON {
    /**
     * Gets or Sets type
     */
    //  @JsonAdapter(TypeEnum.Adapter.class)
    public enum TypeEnum {
        FEATURE("Feature"),
        Feature("Feature");

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
    private Object properties = null;

    @JsonProperty("id")
    //private OneOfbaseFeatureGeoJSONId id = null;
    private Long id = null;

    @JsonProperty("crs")
    private Crs crs = null;

    public BaseFeatureGeoJSON type(TypeEnum type) {
        this.type = type;
        return this;
    }

    /**
     * Get type
     *
     * @return type
     **/
    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public BaseFeatureGeoJSON properties(Object properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get properties
     *
     * @return properties
     **/
    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    /*
    public BaseFeatureGeoJSON id(OneOfbaseFeatureGeoJSONId id) {
        this.id = id;
        return this;
    }

     */
    public BaseFeatureGeoJSON id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     *
     * @return id
     **/
    /*
    public OneOfbaseFeatureGeoJSONId getId() {
        return id;
    }
     */
    public Long getId() {
        return id;
    }

    /*
    public void setId(OneOfbaseFeatureGeoJSONId id) {
        this.id = id;
    }
    */

    public void setId(Long id) {
        this.id = id;
    }

    public BaseFeatureGeoJSON crs(Crs crs) {
        this.crs = crs;
        return this;
    }

    /**
     * Get crs
     *
     * @return crs
     **/
    public Crs getCrs() {
        return crs;
    }

    public void setCrs(Crs crs) {
        this.crs = crs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseFeatureGeoJSON baseFeatureGeoJSON = (BaseFeatureGeoJSON) o;
        return Objects.equals(this.type, baseFeatureGeoJSON.type) && Objects.equals(this.properties, baseFeatureGeoJSON.properties) && Objects.equals(this.id, baseFeatureGeoJSON.id) && Objects.equals(this.crs, baseFeatureGeoJSON.crs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, properties, id, crs);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseFeatureGeoJSON {\n");

        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    crs: ").append(toIndentedString(crs)).append("\n");
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
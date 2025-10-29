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

import java.util.List;
import java.util.Objects;

/**
 * BaseFeatureCollectionGeoJSON
 */

@JsonInclude(Include.NON_NULL)
public class BaseFeatureCollectionGeoJSON {
    /**
     * Gets or Sets type
     */
    //  @JsonAdapter(BaseFeatureGeoJSON.TypeEnum.Adapter.class)
    public enum TypeEnum {
        FEATURECOLLECTION("FeatureCollection"),
        FeatureCollection("FeatureCollection");

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

    @JsonProperty("crs")
    private Crs crs = null;

    @JsonProperty("bbox")
    private List<Double> bbox = null;

    public BaseFeatureCollectionGeoJSON type(TypeEnum type) {
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

    public BaseFeatureCollectionGeoJSON crs(Crs crs) {
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


    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseFeatureCollectionGeoJSON baseFeatureCollectionGeoJSON = (BaseFeatureCollectionGeoJSON) o;
        return Objects.equals(this.type, baseFeatureCollectionGeoJSON.type) && Objects.equals(this.crs, baseFeatureCollectionGeoJSON.crs) && Objects.equals(this.bbox, baseFeatureCollectionGeoJSON.bbox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, crs, bbox);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseFeatureCollectionGeoJSON {\n");

        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    crs: ").append(toIndentedString(crs)).append("\n");
        sb.append("    bbox: ").append(toIndentedString(bbox)).append("\n");
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
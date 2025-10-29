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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MultipointGeoJSON
 */

@JsonInclude(Include.NON_NULL)
public class MultipointGeoJSON implements OneOfgeometryGeoJSON {
    /**
     * Gets or Sets type
     */
    //  @JsonAdapter(TypeEnum.Adapter.class)
    public enum TypeEnum {
        MULTIPOINT("MultiPoint");

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

    @JsonProperty("coordinates")
    private List<List<BigDecimal>> coordinates = new ArrayList<List<BigDecimal>>();

    public MultipointGeoJSON type(TypeEnum type) {
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

    public MultipointGeoJSON coordinates(List<List<BigDecimal>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MultipointGeoJSON addCoordinatesItem(List<BigDecimal> coordinatesItem) {
        this.coordinates.add(coordinatesItem);
        return this;
    }

    /**
     * Get coordinates
     *
     * @return coordinates
     **/
    public List<List<BigDecimal>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<BigDecimal>> coordinates) {
        this.coordinates = coordinates;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MultipointGeoJSON multipointGeoJSON = (MultipointGeoJSON) o;
        return Objects.equals(this.type, multipointGeoJSON.type) && Objects.equals(this.coordinates, multipointGeoJSON.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, coordinates);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MultipointGeoJSON {\n");

        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
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
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
 * SheetFeatureGeoJSON
 */
@JsonInclude(Include.NON_NULL)
public class SheetFeatureGeoJSON extends BaseFeatureGeoJSON {
  @JsonProperty("geometry")
  //private PolygonGeoJSON geometry = null;
  private MultipolygonGeoJSON geometry = null;

  @JsonProperty("properties")
  private Sheet properties = null;

  /*
  public SheetFeatureGeoJSON geometry(PolygonGeoJSON geometry) {
    this.geometry = geometry;
    return this;
  }
   */
  public SheetFeatureGeoJSON geometry(MultipolygonGeoJSON geometry) {
    this.geometry = geometry;
    return this;
  }

   /**
   * Get geometry
   * @return geometry
  **/
   /*
  public PolygonGeoJSON getGeometry() {
    return geometry;
  }

  public void setGeometry(PolygonGeoJSON geometry) {
    this.geometry = geometry;
  }
  */
  public MultipolygonGeoJSON getGeometry() {
    return geometry;
  }

  public void setGeometry(MultipolygonGeoJSON geometry) {
    this.geometry = geometry;
  }

  public SheetFeatureGeoJSON properties(Sheet properties) {
    this.properties = properties;
    return this;
  }

   /**
   * Get properties
   * @return properties
  **/
  public Sheet getProperties() {
    return properties;
  }

  public void setProperties(Sheet properties) {
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
    SheetFeatureGeoJSON sheetFeatureGeoJSON = (SheetFeatureGeoJSON) o;
    return Objects.equals(this.geometry, sheetFeatureGeoJSON.geometry) &&
        Objects.equals(this.properties, sheetFeatureGeoJSON.properties) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geometry, properties, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SheetFeatureGeoJSON {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

}
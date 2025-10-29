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
 * PostRequestFeatureGeoJSON
 */

@JsonInclude(Include.NON_NULL)
public class PostRequestFeatureGeoJSON extends BaseFeatureGeoJSON {
  @JsonProperty("geometry")
  private PolygonGeoJSON geometry = null;

  public PostRequestFeatureGeoJSON geometry(PolygonGeoJSON geometry) {
    this.geometry = geometry;
    return this;
  }

   /**
   * Get geometry
   * @return geometry
  **/
  public PolygonGeoJSON getGeometry() {
    return geometry;
  }

  public void setGeometry(PolygonGeoJSON geometry) {
    this.geometry = geometry;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostRequestFeatureGeoJSON postRequestFeatureGeoJSON = (PostRequestFeatureGeoJSON) o;
    return Objects.equals(this.geometry, postRequestFeatureGeoJSON.geometry) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geometry, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostRequestFeatureGeoJSON {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
    sb.append("}");
    return sb.toString();
  }

}
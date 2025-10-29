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
import java.util.Objects;
/**
 * PostRequestJSON
 */

@JsonInclude(Include.NON_NULL)
public class PostRequestJSON {
  @JsonProperty("limit")
  private BigDecimal limit = null;

  @JsonProperty("offset")
  private BigDecimal offset = null;

  @JsonProperty("feature")
  private PostRequestFeatureGeoJSON feature = null;

  public PostRequestJSON limit(BigDecimal limit) {
    this.limit = limit;
    return this;
  }

   /**
   * Get limit
   * @return limit
  **/
  public BigDecimal getLimit() {
    return limit;
  }

  public void setLimit(BigDecimal limit) {
    this.limit = limit;
  }

  public PostRequestJSON offset(BigDecimal offset) {
    this.offset = offset;
    return this;
  }

   /**
   * Get offset
   * @return offset
  **/
  public BigDecimal getOffset() {
    return offset;
  }

  public void setOffset(BigDecimal offset) {
    this.offset = offset;
  }

  public PostRequestJSON feature(PostRequestFeatureGeoJSON feature) {
    this.feature = feature;
    return this;
  }

   /**
   * Get feature
   * @return feature
  **/
  public PostRequestFeatureGeoJSON getFeature() {
    return feature;
  }

  public void setFeature(PostRequestFeatureGeoJSON feature) {
    this.feature = feature;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostRequestJSON postRequestJSON = (PostRequestJSON) o;
    return Objects.equals(this.limit, postRequestJSON.limit) &&
        Objects.equals(this.offset, postRequestJSON.offset) &&
        Objects.equals(this.feature, postRequestJSON.feature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(limit, offset, feature);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostRequestJSON {\n");

    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    feature: ").append(toIndentedString(feature)).append("\n");
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
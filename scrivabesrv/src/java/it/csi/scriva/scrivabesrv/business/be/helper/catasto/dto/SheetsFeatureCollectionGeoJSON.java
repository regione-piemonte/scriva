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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SheetsFeatureCollectionGeoJSON
 */
@JsonInclude(Include.NON_NULL)
public class SheetsFeatureCollectionGeoJSON extends BaseFeatureCollectionGeoJSON {

  @JsonProperty("features")
  private List<SheetFeatureGeoJSON> features = null;

  public SheetsFeatureCollectionGeoJSON features(List<SheetFeatureGeoJSON> features) {
    this.features = features;
    return this;
  }

  public SheetsFeatureCollectionGeoJSON addFeaturesItem(SheetFeatureGeoJSON featuresItem) {
    if (this.features == null) {
      this.features = new ArrayList<SheetFeatureGeoJSON>();
    }
    this.features.add(featuresItem);
    return this;
  }

   /**
   * Get features
   * @return features
  **/
  public List<SheetFeatureGeoJSON> getFeatures() {
    return features;
  }

  public void setFeatures(List<SheetFeatureGeoJSON> features) {
    this.features = features;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SheetsFeatureCollectionGeoJSON sheetsFeatureCollectionGeoJSON = (SheetsFeatureCollectionGeoJSON) o;
    return Objects.equals(this.features, sheetsFeatureCollectionGeoJSON.features) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(features, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SheetsFeatureCollectionGeoJSON {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
    sb.append("}");
    return sb.toString();
  }

}
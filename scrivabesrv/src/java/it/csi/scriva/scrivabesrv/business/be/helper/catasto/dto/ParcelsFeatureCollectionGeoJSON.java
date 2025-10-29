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
 * ParcelsFeatureCollectionGeoJSON
 */

@JsonInclude(Include.NON_NULL)
public class ParcelsFeatureCollectionGeoJSON extends BaseFeatureCollectionGeoJSON {
  @JsonProperty("features")
  private List<ParcelFeatureGeoJSON> features = null;

  public ParcelsFeatureCollectionGeoJSON features(List<ParcelFeatureGeoJSON> features) {
    this.features = features;
    return this;
  }

  public ParcelsFeatureCollectionGeoJSON addFeaturesItem(ParcelFeatureGeoJSON featuresItem) {
    if (this.features == null) {
      this.features = new ArrayList<ParcelFeatureGeoJSON>();
    }
    this.features.add(featuresItem);
    return this;
  }

   /**
   * Get features
   * @return features
  **/
  public List<ParcelFeatureGeoJSON> getFeatures() {
    return features;
  }

  public void setFeatures(List<ParcelFeatureGeoJSON> features) {
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
    ParcelsFeatureCollectionGeoJSON parcelsFeatureCollectionGeoJSON = (ParcelsFeatureCollectionGeoJSON) o;
    return Objects.equals(this.features, parcelsFeatureCollectionGeoJSON.features) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(features, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParcelsFeatureCollectionGeoJSON {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    features: ").append(toIndentedString(features)).append("\n");
    sb.append("}");
    return sb.toString();
  }

}
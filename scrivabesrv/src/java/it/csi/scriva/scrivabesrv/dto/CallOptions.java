/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


/**
 * The type Call options.
 *
 * @author CSI PIEMONTE
 */
public class CallOptions {
    // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled]

    private Integer desiredStatus = null;
    private Integer executionTime = null;

    /**
     * http status che il servizio dovra sollevare
     *
     * @return the desired status
     */


    @JsonProperty("desiredStatus")

    public Integer getDesiredStatus() {
        return desiredStatus;
    }

    /**
     * Sets desired status.
     *
     * @param desiredStatus the desired status
     */
    public void setDesiredStatus(Integer desiredStatus) {
        this.desiredStatus = desiredStatus;
    }

    /**
     * tempo in millisecondi che il servizio dovra impiegare per l&#39;esecuzione (per testare meccanismi di timeout)
     *
     * @return the execution time
     */


    @JsonProperty("executionTime")

    public Integer getExecutionTime() {
        return executionTime;
    }

    /**
     * Sets execution time.
     *
     * @param executionTime the execution time
     */
    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CallOptions callOptions = (CallOptions) o;
        return Objects.equals(desiredStatus, callOptions.desiredStatus) &&
                Objects.equals(executionTime, callOptions.executionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(desiredStatus, executionTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CallOptions {\n");

        sb.append("    desiredStatus: ").append(toIndentedString(desiredStatus)).append("\n");
        sb.append("    executionTime: ").append(toIndentedString(executionTime)).append("\n");
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
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.updownfile;

import java.util.Arrays;
import java.util.Objects;

/**
 * The type File dto.
 *
 * @author CSI PIEMONTE
 */
public class FileDTO {

    private String fileName;

    private byte[] body;

    /**
     * Gets file name.
     *
     * @return fileName file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets file name.
     *
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get body byte [ ].
     *
     * @return byte[] byte [ ]
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body byte[]
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FileDTO fileDTO = (FileDTO) o;
        return Objects.equals(fileName, fileDTO.fileName) && Arrays.equals(body, fileDTO.body);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(fileName);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileDTO {");
        sb.append("         fileName:'").append(fileName).append("'");
        sb.append(",         body:").append(Arrays.toString(body));
        sb.append("}");
        return sb.toString();
    }
}
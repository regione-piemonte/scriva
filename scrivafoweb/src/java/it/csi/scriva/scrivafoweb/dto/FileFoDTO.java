/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.dto;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * The type File fo dto.
 */
public class FileFoDTO {

    /**
     * The Content disposition header.
     */
    String contentDispositionHeader;

    /**
     * The File content.
     */
    byte[] fileContent;

    /**
     * The File.
     */
    File file;

    /**
     * Gets content disposition header.
     *
     * @return the content disposition header
     */
    public String getContentDispositionHeader() {
        return contentDispositionHeader;
    }

    /**
     * Sets content disposition header.
     *
     * @param contentDispositionHeader the content disposition header
     */
    public void setContentDispositionHeader(String contentDispositionHeader) {
        this.contentDispositionHeader = contentDispositionHeader;
    }

    /**
     * Get file content byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getFileContent() {
        return fileContent;
    }

    /**
     * Sets file content.
     *
     * @param fileContent the file content
     */
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets file.
     *
     * @param file the file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileFoDTO fileFoDTO = (FileFoDTO) o;
        return Objects.equals(contentDispositionHeader, fileFoDTO.contentDispositionHeader) && Arrays.equals(fileContent, fileFoDTO.fileContent) && Objects.equals(file, fileFoDTO.file);
    }

    /**
     * @return int
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(contentDispositionHeader, file);
        result = 31 * result + Arrays.hashCode(fileContent);
        return result;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "FileFoDTO {\n" +
                "         contentDispositionHeader:'" + contentDispositionHeader + "'" +
                ",\n         fileContent:" + Arrays.toString(fileContent) +
                ",\n         file:" + file +
                "}\n";
    }
}
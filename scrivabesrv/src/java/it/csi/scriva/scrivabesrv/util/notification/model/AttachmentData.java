/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.model;

import org.apache.commons.io.FileUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * The type Attachment data.
 *
 * @author CSI PIEMONTE
 */
public class AttachmentData {

    private String mimeType;
    private String filename;
    private File file;

    /**
     * Gets mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        return fileTypeMap.getContentType(file);
    }

    /**
     * Sets mime type.
     *
     * @param mimeType the mime type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets filename.
     *
     * @param filename the filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
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
     * Get file as byte array.
     *
     * @return the byte array
     * @throws IOException the io exception
     */
    public byte[] getFileAsByteArray() throws IOException {
        return file != null ? FileUtils.readFileToByteArray(file) : null;
    }

    /**
     * Gets file as input stream.
     *
     * @return the file as input stream
     * @throws IOException the io exception
     */
    public InputStream getFileAsInputStream() throws IOException {
        return file != null ? FileUtils.openInputStream(file) : null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentData that = (AttachmentData) o;
        return Objects.equals(mimeType, that.mimeType) && Objects.equals(filename, that.filename) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mimeType, filename, file);
    }

    @Override
    public String
    toString() {
        return "AttachmentData{" +
                "mimeType='" + mimeType + '\'' +
                ", filename='" + filename + '\'' +
                ", file=" + file +
                '}';
    }
}

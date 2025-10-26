/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

package it.csi.scriva.scrivaapisrv.business.be.helper;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * The type Download file from url service helper.
 */
public class DownloadFileFromURLServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private final String rootFileFolder;

    /**
     * Instantiates a new Abilitazioni api service helper.
     *
     * @param rootFileFolder the root file folder
     */
    public DownloadFileFromURLServiceHelper(String rootFileFolder) {
        this.rootFileFolder = rootFileFolder;
    }

    /**
     * Gets file.
     *
     * @param urlAddress the url address
     * @param id         the id
     * @return the file
     * @throws IOException the io exception
     */
    public File getFile(String urlAddress, String id) throws IOException {
        logBegin(className);
        logInfo(className, "urlAddress : [" + urlAddress + "] - id : [" + id + "]");
        return getFile(urlAddress, id, "");
    }

    /**
     * Gets file.
     *
     * @param urlAddress the url address
     * @param id         the id
     * @param fileName   the file name
     * @return the file
     * @throws IOException the io exception
     */
    public File getFile(String urlAddress, String id, String fileName) throws IOException {
        logBegin(className);
        logInfo(className, "urlAddress : [" + urlAddress + "] - id : [" + id + "] - fileName : [" + fileName + "]");
        URL url = new URL(urlAddress);
        String fileNameNew = StringUtils.isBlank(fileName) ? getFilename(url) : fileName;
        InputStream inputStream = url.openStream();
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        File newDirectory = new File(rootFileFolder, id);
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(rootFileFolder + "/" + id + "/" + fileNameNew);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        logEnd(className);
        return new File(rootFileFolder + "/" + id, fileNameNew);
    }

    /**
     * Gets filename.
     *
     * @param url the url
     * @return the filename
     * @throws IOException the io exception
     */
    public String getFilename(URL url) throws IOException {
        logBegin(className);
        String filename = getFilenameFromHeader(url);
        logEnd(className);
        return StringUtils.isBlank(filename) ? getFilenameFromPath(url) : filename;
    }

    /**
     * Gets filename from header.
     *
     * @param url the url
     * @return the filename from header
     * @throws IOException the io exception
     */
    public String getFilenameFromHeader(URL url) throws IOException {
        logBegin(className);
        URLConnection urlConnection = url.openConnection();
        String fieldValue = urlConnection.getHeaderField("Content-Disposition");
        logEnd(className);
        return StringUtils.isNotBlank(fieldValue) && fieldValue.contains("filename=\"") ? fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1) : null;
    }

    /**
     * Gets filename from path.
     *
     * @param url the url
     * @return the filename from path
     */
    public String getFilenameFromPath(URL url) {
        return url != null ? FilenameUtils.getName(url.getPath()) : null;
    }

}
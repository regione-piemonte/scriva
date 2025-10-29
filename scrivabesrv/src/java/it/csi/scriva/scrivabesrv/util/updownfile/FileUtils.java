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

import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The type Up down file util.
 *
 * @author CSI PIEMONTE
 */
public class FileUtils {

    private static final Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".util");
    private static final String CLASS_NAME = "FileUtils";
    public static final String END = "] END";
    public static final String BEGIN = "] BEGIN";

    private FileUtils() {
        // constructor
    }

    /**
     * Copy file.
     *
     * @param source the source
     * @param dest   the dest
     * @throws IOException the io exception
     */
    public static void copyFile(File source, File dest) throws IOException {
        // crea directory di destinazione se non esiste
        dest.getParentFile().mkdirs();
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Byte array to file file.
     *
     * @param byteArray the byte array
     * @param filename  the filename
     * @return the file
     */
    public static File byteArrayToFile(byte[] byteArray, String filename) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input filename [" + filename + "]";
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + BEGIN);
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + "] " + inputParam);
        File result = new File(filename);
        try {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(result, byteArray);
        } catch (IOException e) {
            LOGGER.error("[" + CLASS_NAME + "::" + methodName + "] " + e);
        } finally {
            LOGGER.debug("[" + CLASS_NAME + "::" + methodName + END);
        }
        return result;
    }

    /**
     * Copy file using stream.
     *
     * @param source the source
     * @param dest   the dest
     * @throws IOException the io exception
     */
    public static void copyFileUsingStream(File source, File dest) throws IOException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try (InputStream is = new FileInputStream(source);
             FileOutputStream os = new FileOutputStream(dest);
        ) {
            // crea directory di destinazione se non esiste
            dest.getParentFile().mkdirs();

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            LOGGER.error("[" + CLASS_NAME + "::" + methodName + "] " + e);
        } finally {
            LOGGER.debug("[" + CLASS_NAME + "::" + methodName + END);
        }
    }

    /**
     * Delete file boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean deleteFile(String filename) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input filename [" + filename + "]";
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + BEGIN);
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + "] " + inputParam);
        boolean result = Boolean.FALSE;
        try {
            Files.delete(Path.of(filename));
            result = Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error("[" + CLASS_NAME + "::" + methodName + "] " + e);
        } finally {
            LOGGER.debug("[" + CLASS_NAME + "::" + methodName + END);
        }
        return result;
    }

    /**
     * Delete folder boolean.
     *
     * @param folder the folder
     * @return the boolean
     */
    public static boolean deleteFolder(String folder) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input folder [" + folder + "]";
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + BEGIN);
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + "] " + inputParam);
        boolean result = Boolean.FALSE;
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(new File(folder));
            result = Boolean.TRUE;
        } catch (Exception e) {
            LOGGER.error("[" + CLASS_NAME + "::" + methodName + "] " + e);
        } finally {
            LOGGER.debug("[" + CLASS_NAME + "::" + methodName + END);
        }
        return result;
    }


    /**
     * Zip folder file output stream.
     *
     * @param folderPath  the folder path
     * @param zipFilename the zip filename
     * @return the file output stream
     */
    public static FileOutputStream zipFolder(String folderPath, String zipFilename) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input folderPath [" + folderPath + "] - zipFilename [" + zipFilename + "]";
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + BEGIN);
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + "] " + inputParam);
        try (FileOutputStream fos = new FileOutputStream(zipFilename)) {
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(folderPath);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            return fos;
        } catch (IOException e) {
            LOGGER.error("[" + CLASS_NAME + "::" + methodName + "] " + e);
        } finally {
            LOGGER.debug("[" + CLASS_NAME + "::" + methodName + END);
        }
        return null;
    }


    /**
     * Zip file.
     *
     * @param fileToZip the file to zip
     * @param fileName  the file name
     * @param zipOut    the zip out
     * @throws IOException the io exception
     */
    public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug("[" + CLASS_NAME + "::" + methodName + BEGIN);
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                }
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        } catch (IOException e) {
            LOGGER.error("[" + CLASS_NAME + "::" + methodName + "] " + e);
        } finally {
            LOGGER.debug("[" + CLASS_NAME + "::" + methodName + END);
        }
    }

    /**
     * Sanitizes a directory name by replacing invalid characters with spaces.
     * 
     * @param name the directory name to sanitize
     * @return the sanitized directory name
     */
    public static String sanitizeDirName(String name) {
        if (name == null) {
            return null;
        }
        // Elenco dei caratteri non validi in Windows e Linux
        String invalidChars = "<>:\"/\\|?*";
        // Sostituisce ogni carattere non valido con uno spazio
        for (char ch : invalidChars.toCharArray()) {
            name = name.replace(ch, ' ');
        }
        // Ritorna il nuovo nome "sanificato"
        return name;
    }


    /**
     * Upload file file dto.
     *
     * @param formDataInput     formDataInput
     * @param inputVariableName inputVariableName
     * @param uploadFilePath    uploadFilePath
     * @return FileDTO file dto
     */
    public static FileDTO uploadFile(MultipartFormDataInput formDataInput, String inputVariableName, String uploadFilePath) {
        FileDTO fileDTO = new FileDTO();
        String fileName = "";
        Map<String, List<InputPart>> uploadForm = formDataInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get(inputVariableName);

        byte[] bytes = null;
        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                bytes = IOUtils.toByteArray(inputStream);
                fileDTO.setFileName(fileName);
                fileDTO.setBody(bytes);
                StringBuilder sbFilename = new StringBuilder();
                if (StringUtils.isNotBlank(uploadFilePath)) {
                    sbFilename.append(uploadFilePath);
                    sbFilename.append(fileName);
                    writeFile(bytes, sbFilename.toString());
                }
            } catch (IOException e) {
                LOGGER.error("[[UpDownFileUtil::uploadFile] ERROR : ", e);
            }
        }
        return fileDTO;
    }

    /**
     * @param header header
     * @return string
     */
    private static String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replace("\"", "");
            }
        }
        return "unknown";
    }

    /**
     * @param content  content
     * @param filename filename
     * @throws IOException IOException
     */
    private static void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        boolean fileExist = file.exists();
        if (!fileExist) {
            fileExist = file.createNewFile();
        }
        if (fileExist) {
            try (FileOutputStream fop = new FileOutputStream(file)) {
                fop.write(content);
                fop.flush();
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
    }

}
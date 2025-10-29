/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.templating;

/**
 * The type Pdfa 3 components.
 */
public class PDFA3Components {

    private String inputFilePath;
    private String colorProfilePath;
    private String outputFilePath;
    private String xmpTemplatePath;
    private String documentFileName;
    private String idDocumento;
    private String dataChiusura;
    private String oggettoDocumento;
    private String soggettoProduttore;
    private String destinatario;

    /**
     * * Use for set file path (input and output)
     *
     * @param inputFilePath      : path of input PDF file
     * @param xmpTemplatePath    ; the xmp template path
     * @param outputFilePath     : name of output PDF/A-3 file, e.g. invoiceA3.pdf
     * @param colorProfilePath   : path of color profile file, e.g. sRGB Color Space Profile.icm
     * @param documentFileName   : name of embed file name
     * @param idDocumento        : the id documento
     * @param dataChiusura       : the data chiusura
     * @param oggettoDocumento   : the oggetto documento
     * @param soggettoProduttore : the soggetto produttore
     * @param destinatario       : the destinatario
     */
    public PDFA3Components(String inputFilePath, String xmpTemplatePath, String outputFilePath, String colorProfilePath, String documentFileName,
                           String idDocumento, String dataChiusura,String oggettoDocumento, String soggettoProduttore, String destinatario) {
        super();
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.colorProfilePath = colorProfilePath;
        this.documentFileName = documentFileName;
        this.xmpTemplatePath = xmpTemplatePath;
        this.idDocumento = idDocumento;
        this.dataChiusura = dataChiusura;
        this.oggettoDocumento = oggettoDocumento;
        this.soggettoProduttore = soggettoProduttore;
        this.destinatario = destinatario;
    }

    /**
     * Gets input file path.
     *
     * @return the input file path
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * Sets input file path.
     *
     * @param inputFilePath the input file path
     */
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * Gets color profile path.
     *
     * @return the color profile path
     */
    public String getColorProfilePath() {
        return colorProfilePath;
    }

    /**
     * Sets color profile path.
     *
     * @param colorProfilePath the color profile path
     */
    public void setColorProfilePath(String colorProfilePath) {
        this.colorProfilePath = colorProfilePath;
    }

    /**
     * Gets output file path.
     *
     * @return the output file path
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * Sets output file path.
     *
     * @param outputFilePath the output file path
     */
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * Gets document file name.
     *
     * @return the document file name
     */
    public String getDocumentFileName() {
        return documentFileName;
    }

    /**
     * Sets document file name.
     *
     * @param documentFileName the document file name
     */
    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }


    /**
     * Gets xmp template path.
     *
     * @return the xmp template path
     */
    public String getXmpTemplatePath() {
        return xmpTemplatePath;
    }

    /**
     * Sets xmp template path.
     *
     * @param xmpTemplatePath the xmp template path
     */
    public void setXmpTemplatePath(String xmpTemplatePath) {
        this.xmpTemplatePath = xmpTemplatePath;
    }

    /**
     * Gets id documento.
     *
     * @return the id documento
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * Sets id documento.
     *
     * @param idDocumento the id documento
     */
    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /**
     * Gets data chiusura.
     *
     * @return the data chiusura
     */
    public String getDataChiusura() {
        return dataChiusura;
    }

    /**
     * Sets data chiusura.
     *
     * @param dataChiusura the data chiusura
     */
    public void setDataChiusura(String dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    /**
     * Gets oggetto documento.
     *
     * @return the oggetto documento
     */
    public String getOggettoDocumento() {
        return oggettoDocumento;
    }

    /**
     * Sets oggetto documento.
     *
     * @param oggettoDocumento the oggetto documento
     */
    public void setOggettoDocumento(String oggettoDocumento) {
        this.oggettoDocumento = oggettoDocumento;
    }

    /**
     * Gets soggetto produttore.
     *
     * @return the soggetto produttore
     */
    public String getSoggettoProduttore() {
        return soggettoProduttore;
    }

    /**
     * Sets soggetto produttore.
     *
     * @param soggettoProduttore the soggetto produttore
     */
    public void setSoggettoProduttore(String soggettoProduttore) {
        this.soggettoProduttore = soggettoProduttore;
    }

    /**
     * Gets destinatario.
     *
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Sets destinatario.
     *
     * @param destinatario the destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}
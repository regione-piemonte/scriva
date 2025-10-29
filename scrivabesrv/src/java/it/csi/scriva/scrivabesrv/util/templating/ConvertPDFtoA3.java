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
 * The type Convert pd fto a 3.
 */
//
//public class ConvertPDFtoA3 {
//
//    private static float pdfVer = 1.7f;
//
//    /**
//     * The convertPDFtoPDFA3(FilePaths, String, String, String) method is used
//     * to convert any kind of PDF(.pdf) to PDF/A-3
//     *
//     * @param pdfa3Components the pdfa 3 components
//     * @throws Exception the exception
//     */
//    public static void Convert(PDFA3Components pdfa3Components) throws Exception {
//        File inputFile = new File(pdfa3Components.getInputFilePath());
//        PDDocument doc = PDDocument.load(inputFile);
//        File colorPFile = new File(pdfa3Components.getColorProfilePath());
//        InputStream colorProfile = new FileInputStream(colorPFile);
//
//        PDDocumentCatalog cat = makeA3compliant(doc, pdfa3Components);
//        addFont(doc);
//        addOutputIntent(doc, cat, colorProfile);
//        doc.setVersion(pdfVer);
//        doc.save(pdfa3Components.getOutputFilePath());
//        doc.close();
//        File outputFile = new File(pdfa3Components.getOutputFilePath());
//        if (outputFile.exists()) {
//            System.out.println(pdfa3Components.getOutputFilePath());
//        } else {
//            System.out.println("Failed to convert.");
//        }
//    }
//
//    private static void addOutputIntent(PDDocument doc, PDDocumentCatalog cat, InputStream colorProfile) throws IOException {
//        if (cat.getOutputIntents().isEmpty()) {
//            PDOutputIntent oi = new PDOutputIntent(doc, colorProfile);
//            oi.setInfo("sRGB IEC61966-2.1");
//            oi.setOutputCondition("sRGB IEC61966-2.1");
//            oi.setOutputConditionIdentifier("sRGB IEC61966-2.1");
//            oi.setRegistryName("http://www.color.org");
//            cat.addOutputIntent(oi);
//        }
//    }
//
//    public static void addFont(PDDocument doc) throws Exception {
//        String fontTimes = "C:\\Windows\\Fonts\\TIMES.TTF";
//        String fontCalibriBold = "C:\\Windows\\Fonts\\calibrib.ttf";
//        String fontCalibri = "C:\\Windows\\Fonts\\calibri.ttf";
//        String fontHelvetica = "C:\\Windows\\Fonts\\Helvetica.ttf";
//        String fontSymbol = "C:\\Windows\\Fonts\\symbol.ttf";
//        //PDFont font = PDType0Font.load(doc, new File(fontTimes));
//        PDFont font = PDType0Font.load(doc, new File(fontCalibri));
//        if (!font.isEmbedded()) {
//            throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                    + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//        }
//        font = PDType0Font.load(doc, new File(fontCalibriBold));
//        if (!font.isEmbedded()) {
//            throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                    + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//        }
//        font = PDType0Font.load(doc, new File(fontHelvetica));
//        if (!font.isEmbedded()) {
//            throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                    + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//        }
//        font = PDType0Font.load(doc, new File(fontSymbol));
//        if (!font.isEmbedded()) {
//            throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                    + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//        }
//    }
//
//    private static PDDocumentCatalog makeA3compliant(PDDocument doc, PDFA3Components pdfa3Components) throws Exception {
//        PDDocumentCatalog cat = doc.getDocumentCatalog();
//        PDDocumentInformation pdd = doc.getDocumentInformation();
//        PDMetadata metadata = new PDMetadata(doc);
//        cat.setMetadata(metadata);
//
//        PDDocumentInformation pdi = new PDDocumentInformation();
///*
//        pdi.setProducer(pdd.getProducer());
//        pdi.setAuthor(pdd.getAuthor());
//        pdi.setTitle(pdd.getTitle());
//        pdi.setSubject(pdd.getSubject());
//        pdi.setKeywords(pdd.getKeywords());
//*/
//        pdi.setProducer("pdd.getProducer()");
//        pdi.setAuthor("pdd.getAuthor()");
//        pdi.setTitle("pdd.getTitle()");
//        pdi.setSubject("pdd.getSubject()");
//        pdi.setKeywords("pdd.getKeywords()");
//
//        // Set OID
//        // pdi.setCustomMetadataValue("OID", "10.2.3.65.5");
//        doc.setDocumentInformation(pdi);
//
//        Charset charset = StandardCharsets.UTF_8;
//
//        byte[] fileBytes = Files.readAllBytes(new File(pdfa3Components.getXmpTemplatePath()).toPath());
//        String content = new String(fileBytes, charset);
//        content = content.replaceAll("@DocumentFileName", pdfa3Components.getDocumentFileName());
//        content = content.replaceAll("@IDDocumento", pdfa3Components.getIdDocumento());
//        content = content.replaceAll("@DataChiusura", pdfa3Components.getDataChiusura());
//        content = content.replaceAll("@OggettoDocumento", pdfa3Components.getOggettoDocumento());
//        content = content.replaceAll("@SoggettoProduttore", pdfa3Components.getSoggettoProduttore());
//        content = content.replaceAll("@Destinatario", pdfa3Components.getDestinatario());
//
//        byte[] editedBytes = content.getBytes(charset);
//
//        metadata.importXMPMetadata(editedBytes);
//        return cat;
//    }
//
//}
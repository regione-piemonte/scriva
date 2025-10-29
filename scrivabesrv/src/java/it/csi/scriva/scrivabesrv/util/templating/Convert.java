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

public class Convert {

//    public static void main(String[] args) throws Exception {
//
//        /**** Sample Input ****/
//		/*
//		String inputFilePath = "src/main/resources/sample.pdf";
//		String embbedFilePath = "src/main/resources/ETDA-invoice.xml";
//		String colorProfilePath = "src/main/resources/sRGB Color Space Profile.icm";
//		String outputFilePath = "target/success.pdf";
//		String documentType = "Tax Invoice";
//		String docFileName = "ETDA-invoice.xml";
//		String docVersion = "2.0";
//		String xmpTemplatePath = "src/main/resources/xmpTemplate.xml";
//		*/
//        String inputPath = "D:\\Download\\PdfAConverter-master\\src\\main\\resources\\";
//        String inputFilePath = inputPath + "sample.pdf";
//        String embbedFilePath = inputPath + "ETDA-invoice.xml";
//        String colorProfilePath = inputPath + "sRGB Color Space Profile.icm";
//        String outputFilePath = inputPath + "success.pdf";
//        String documentType = "Tax Invoice";
//        String docFileName = "ETDA-invoice.xml";
//        String docVersion = "2.0";
//        String xmpTemplatePath = inputPath + "xmpTemplate.xml";
//
//
//        /*
//        String inputFilePath = args[0];
//        String embbedFilePath = args[1];
//        String colorProfilePath = args[2];
//        String outputFilePath = args[3];
//        String documentType = args[4];
//        String docFileName = args[5];
//        String docVersion = args[6];
//        String xmpTemplatePath = args[7];
// */
//        //PDFA3Components pdfa3Components = new PDFA3Components(inputFilePath, colorProfilePath, outputFilePath, documentType, docFileName, docVersion, xmpTemplatePath);
//        PDFA3Components pdfa3Components = null;
//
//        ConvertPDFtoA3.Convert(pdfa3Components);
//    }
//
//    public static void createPDFA() throws Exception {
//
//        String inputPath = "D:\\Download\\PdfAConverter-master\\src\\main\\resources\\";
//        String inputFilePath = "D:\\PDFATest.pdf";
//        String xmpTemplatePath = inputPath + "xmpTemplate2.xml";
//        String outputFilePath = "D:\\success.pdf";
//        String colorProfilePath = inputPath + "sRGB Color Space Profile.icm";
//        String docFileName = "Modulo_istanza";
//
//        String documentType = "Tipo documento";
//        String docVersion = "1.0";
//        String idDocumento = "IDDocumento";
//        String dataChiusura = "01/10/2021";
//        String oggettoDocumento = "Oggetto documento";
//        String soggettoProduttore = "CSI Piemonte";
//        String destinatario = "Destinatario Documento";
//
//        PDFA3Components pdfa3Components = new PDFA3Components(inputFilePath, xmpTemplatePath, outputFilePath, colorProfilePath, docFileName, idDocumento, dataChiusura, oggettoDocumento, soggettoProduttore, destinatario);
//        ConvertPDFtoA3.Convert(pdfa3Components);
//    }
//
//    public static void createPDFA1() {
//        String inputFilePath = "D:\\PDFATest.pdf";
//        String outputFilePath = "D:\\PDFATest_generatoLocalmente.pdf";
//        String title = "Titolo";
//
//        try (PDDocument doc = PDDocument.load(new File(inputFilePath))) {
//            PDPage page = new PDPage();
//            doc.addPage(page);
//            ConvertPDFtoA3.addFont(doc);
//
//            // A PDF/A file needs to have the font embedded if the font is used for text rendering
//            // in rendering modes other than text rendering mode 3.
//            //
//            // This requirement includes the PDF standard fonts, so don't use their static PDFType1Font classes such as
//            // PDFType1Font.HELVETICA.
//            //
//            // As there are many different font licenses it is up to the developer to check if the license terms for the
//            // font loaded allows embedding in the PDF.
//            //
//            /*
//            PDFont font = PDType0Font.load(doc, new File(fontfile)); // load the font as this needs to be embedded
//            if (!font.isEmbedded()) {
//                throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                        + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//            }
//
//             */
//
//            // add XMP metadata
//            XMPMetadata xmp = XMPMetadata.createXMPMetadata();
//
//            try {
//                DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
//                dc.setTitle(title);
//                dc.addCreator("Author");
//                dc.addSubject("Soggetto");
//                dc.addDescription("it", "descrizione");
//
//                //dc.addCreator("creator");
//                //dc.setIdentifier("1234");
//
//                PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
//                id.setPart(1);
//                id.setConformance("B");
//
//                XmpSerializer serializer = new XmpSerializer();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                serializer.serialize(xmp, baos, true);
//
//                PDMetadata metadata = new PDMetadata(doc);
//                metadata.importXMPMetadata(baos.toByteArray());
//                doc.getDocumentCatalog().setMetadata(metadata);
//            } catch (Exception e) {
//                // won't happen here, as the provided value is valid
//                throw new IllegalArgumentException(e);
//            }
//
//            // sRGB output intent
//            InputStream colorProfile = new FileInputStream("D:\\Download\\sRGB.icc");
//            PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
//            intent.setInfo("sRGB IEC61966-2.1");
//            intent.setOutputCondition("sRGB IEC61966-2.1");
//            intent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
//            intent.setRegistryName("http://www.color.org");
//            doc.getDocumentCatalog().addOutputIntent(intent);
//
//            doc.save(outputFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
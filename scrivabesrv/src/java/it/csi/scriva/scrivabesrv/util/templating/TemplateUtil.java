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

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.util.manager.JsonDataManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Template util.
 *
 * @author CSI PIEMONTE
 */
@Component
public class TemplateUtil extends BaseServiceImpl {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private JsonDataManager jsonDataManager;

    /**
     * Get compiled template pdf byte [ ].
     *
     * @param json         json
     * @param templatePath templatePath
     * @return byte[] byte [ ]
     * @throws Exception Exception
     */
    public static byte[] getCompiledTemplatePDF(String json, String templatePath) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IXDocReport report;
        InputStream in = TemplateUtil.class.getClassLoader().getResourceAsStream(templatePath);
        // File fileLogo = new File("D:\\temp\\logo2.png");
        //IImageProvider ratioSizeLogo = new FileImageProvider(fileLogo);
        //InputStream in = new FileInputStream("D:/temp/Modulo_PDF_VIA_CONFIG_V3.docx"); // Absolute path

        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        Map<String, Object> mappings = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        mappings.put("ist", obj);
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Create fields metadata to manage image
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("logo", NullImageBehaviour.RemoveImageTemplate);
        IImageProvider ratioSizeLogo = new ClassPathImageProvider(TemplateUtil.class, "/templates/logo.png");
        ratioSizeLogo.setUseImageSize(true);
        ratioSizeLogo.setWidth(100f);
        ratioSizeLogo.setResize(true);

        IContext context = report.createContext();
        context.put("d", mappings);
        context.put("logo", ratioSizeLogo);

        Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
/*
        // PDF/A Experiment :
        //Error
        //Caused by: fr.opensagres.poi.xwpf.converter.core.XWPFConverterException: com.lowagie.text.pdf.PdfXConformanceException: All the fonts must be embedded. This one isn't: Helvetica

        PdfOptions subOptions = PdfOptions.create();

        subOptions.setConfiguration(new IPdfWriterConfiguration() {

            public void configure(PdfWriter writer) {
                writer.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
                writer.setTagged();

                //PDFFontUtil.embedStandardFonts();

                //writer.setPDFXConformance(PdfWriter.PDFA1A);
                writer.setPDFXConformance(PdfWriter.PDFX1A2001);
                writer.createXmpMetadata();
            }
        });

        subOptions.fontProvider(new IFontProvider() {

            @Override
            public com.lowagie.text.Font getFont(String familyName, String encoding, float size, int style, Color color) {
                String fontLibrary = "C:\\Windows\\Fonts\\";
                try {
                    if (familyName.equalsIgnoreCase("CALIBRI")) {
                        BaseFont baseFont =
                                BaseFont.createFont(fontLibrary + (style == Font.BOLD ? "calibrib.ttf" : "calibri.ttf"), encoding, BaseFont.EMBEDDED);
                        return new com.lowagie.text.Font(baseFont, size, style, color);

                    } else if (familyName.equalsIgnoreCase("TIMES NEW ROMAN")) {
                        BaseFont baseFont =
                                BaseFont.createFont(fontLibrary + "TIMES.TTF", encoding, BaseFont.EMBEDDED);
                        return new com.lowagie.text.Font(baseFont, size, style, color);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                return FontFactory.getFont(familyName, encoding, size, style, color);
            }
        });

        Options options = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF).subOptions(subOptions);
*/

        report.convert(context, options, out);

/*
        OutputStream os = new FileOutputStream("test_abcd.pdf");
        os.write(out.toByteArray());
        os.close();
        getCompiledTemplatePDFA(json, templatePath);
*/
        return out.toByteArray();
    }

    /**
     * Get compiled template doc byte [ ].
     *
     * @param json         the json
     * @param templatePath the template path
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public static byte[] getCompiledTemplateDOC(String json, String templatePath) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IXDocReport report;
        InputStream in = TemplateUtil.class.getClassLoader().getResourceAsStream(templatePath);

        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        Map<String, Object> mappings = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        mappings.put("ist", obj);
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Create fields metadata to manage image
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("logo", NullImageBehaviour.RemoveImageTemplate);
        IImageProvider ratioSizeLogo = new ClassPathImageProvider(TemplateUtil.class, "/templates/logo.png");
        ratioSizeLogo.setUseImageSize(true);
        ratioSizeLogo.setWidth(100f);
        ratioSizeLogo.setResize(true);

        IContext context = report.createContext();
        context.put("d", mappings);
        context.put("logo", ratioSizeLogo);

        report.process(context, out);
        return out.toByteArray();
    }

    /**
     * Get compiled template pdf byte [ ].
     *
     * @param idIstanza    the id istanza
     * @param templatePath the template path
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public byte[] getCompiledTemplatePDF(Long idIstanza, String templatePath) throws Exception {
        logBegin(className);
        return getCompiledTemplate(idIstanza, templatePath, "PDF");
    }

    /**
     * Get compiled template doc byte [ ].
     *
     * @param idIstanza    the id istanza
     * @param templatePath the template path
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public byte[] getCompiledTemplateDOC(Long idIstanza, String templatePath) throws Exception {
        logBegin(className);
        return getCompiledTemplate(idIstanza, templatePath, "DOC");
    }

    /**
     * Get compiled template byte [ ].
     *
     * @param idIstanza    the id istanza
     * @param templatePath the template path
     * @param docType      the doc type
     * @return the byte [ ]
     * @throws Exception the exception
     */
    /* 
    private byte[] getCompiledTemplate(Long idIstanza, String templatePath, String docType) throws Exception {

        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - templatePath : [" + templatePath + "] - docType : [" + docType + "]");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //InputStream in = TemplateUtil.class.getResourceAsStream(templatePath);
        InputStream in = TemplateUtil.class.getResourceAsStream(templatePath);
        in = in != null ? in : new FileInputStream(templatePath);

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        Map<String, Object> mappings = new HashMap<>();
        mappings.put("ist", jsonDataManager.generateJsonDataFromConfig(idIstanza));
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        // Create fields metadata to manage image
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("logo", NullImageBehaviour.RemoveImageTemplate);
        IImageProvider ratioSizeLogo = new ClassPathImageProvider(TemplateUtil.class, "/templates/logo.png");
        ratioSizeLogo.setUseImageSize(true);
        ratioSizeLogo.setWidth(100f);
        ratioSizeLogo.setResize(true);

        IContext context = report.createContext();
        context.put("d", mappings);
        context.put("logo", ratioSizeLogo);

        if ("doc".equalsIgnoreCase(docType)) {
            report.process(context, out);
        } else {
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            report.convert(context, options, out);
        }
        return out.toByteArray();
    }
    */

    /**
     * Get compiled template byte [ ].
     *
     * @param idIstanza    the id istanza
     * @param templatePath the template path
     * @param docType      the doc type
     * @return the byte [ ]
     * @throws Exception the exception
     * 
     * SCRIVA-1608 modificato metodo per i nuovi adempi DER e relativi templates. Si ha necessità di inserire i loghi della AC in modo dinamico
     */

    private byte[] getCompiledTemplate(Long idIstanza, String templatePath, String docType) throws Exception {

        logBeginInfo(className, "idIstanza : [" + idIstanza + "] - templatePath : [" + templatePath + "] - docType : [" + docType + "]");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = TemplateUtil.class.getResourceAsStream(templatePath);
        in = in != null ? in : new FileInputStream(templatePath);

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        // Genera il JSON dinamico dai dati
        JSONObject jsonData = jsonDataManager.generateJsonDataFromConfig(idIstanza);

        Map<String, Object> mappings = new HashMap<>();
        mappings.put("ist",jsonData);
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        
        IContext context = report.createContext();
        context.put("d", mappings); // JSON dinamico + timestamp

        // Estrarre il valore dal path JSON
        String logoName = extractLogoName(jsonData);
        String imageName = logoName + ".png"; // Aggiungi l'estensione al nome del logo
        String imageDir = calculateImagePath(templatePath);
        String imagePath = imageDir + File.separator + imageName;

        // Carica l'immagine corrispondente dal filesystem e crea metadata
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("logo", NullImageBehaviour.RemoveImageTemplate);
        File imageFile = new File(imagePath);

        // se NON esiste il logo dal JSON Data, prendo quello di default logo.png
        if (!imageFile.exists()) {
            logError(className, "Immagine non trovata: " + imagePath);

            String imagePathDefault = imageDir + File.separator + "logo.png";
            File imageDefault = new File(imagePathDefault);
            IImageProvider ratioSizeLogo = new FileImageProvider(imageDefault);
            ratioSizeLogo.setUseImageSize(true);
            ratioSizeLogo.setWidth(100f);
            ratioSizeLogo.setResize(true);
            context.put("logo", ratioSizeLogo); // Immagine dinamica
        }
        else
        {
            IImageProvider ratioSizeLogo = new FileImageProvider(imageFile);

            ratioSizeLogo.setUseImageSize(true);
            ratioSizeLogo.setWidth(100f);
            ratioSizeLogo.setResize(true);
            context.put("logo", ratioSizeLogo); // Immagine dinamica
        }


        // Genera il PDF o il documento Word
        if ("doc".equalsIgnoreCase(docType)) {
            report.process(context, out);
        } else {
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            report.convert(context, options, out);
        }
        return out.toByteArray();
}

    /**
     * Estrae il nome del logo dal JSON, cercando il campo
     * "QDR_ISTANZA" -> "competenze_territorio" -> "cod_competenza_territorio".
     * Se il campo non esiste, restituisce il valore predefinito "logo".
     *
     * @param jsonObject il JSONObject da cui estrarre il nome del logo
     * @return il nome del logo oppure "logo" se non trovato
     */
    
   /*  private String extractLogoName(JSONObject jsonObject) {
        // Naviga nel JSON usando i cast appropriati
        JSONObject qdrIstanza = (JSONObject) jsonObject.get("QDR_ISTANZA");
        if (qdrIstanza != null) {
            JSONObject competenzeTerritorio = (JSONObject) qdrIstanza.get("competenze_territorio");
            if (competenzeTerritorio != null) {
                return (String) competenzeTerritorio.get("cod_competenza_territorio");
            }
        }
        // Se il valore non esiste, ritorna il nome predefinito
        return "logo"; // Nome predefinito
    }
*/

    private String extractLogoName(JSONObject jsonObject) {
        logBegin(className, className);
        // Naviga nel JSON controllando i tipi
        Object qdrIstanzaObj = jsonObject.get("QDR_ISTANZA");
        if (qdrIstanzaObj instanceof JSONObject) {
            JSONObject qdrIstanza = (JSONObject) qdrIstanzaObj;

            Object competenzeTerritorioObj = qdrIstanza.get("istanza_competenza");
            if (competenzeTerritorioObj instanceof JSONArray) {
                // Caso in cui competenze_territorio è un array
                JSONArray competenzeTerritorioArray = (JSONArray) competenzeTerritorioObj;
                if (!competenzeTerritorioArray.isEmpty()) {
                    Object firstElementObj = competenzeTerritorioArray.get(0); // Prendi il primo elemento
                    if (firstElementObj instanceof JSONObject) {
                        JSONObject firstElement = (JSONObject) firstElementObj;
                        Object competenzaTerritorioObj = firstElement.get("competenza_territorio");
                        if (competenzaTerritorioObj instanceof JSONObject) {
                            JSONObject competenza = (JSONObject) competenzaTerritorioObj;
                            Object codice = competenza.get("cod_competenza_territorio");
                            if (codice instanceof String) {
                                return (String) codice;
                            }
                        }
                    }
                }
            }
        }
        // Se il valore non è trovato, ritorna il nome predefinito "logo"
        logEnd(className, className);
        return "logo";
    }

/* 
    private String calculateImagePath(String templatePath) {
        if (templatePath == null || templatePath.isEmpty()) {
            throw new IllegalArgumentException("Il templatePath non può essere nullo o vuoto.");
        }
        // Trova l'indice del penultimo "/"
        int lastSlashIndex = templatePath.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            throw new IllegalArgumentException("Il templatePath non sembra essere un path valido: " + templatePath);
        }
    
        // Troncamento al penultimo segmento
        String basePath = templatePath.substring(0, lastSlashIndex);
        return basePath + "/img"; // Aggiunge il path delle immagini
    }
*/

  
    /**
     * Generates the path to the "img" directory based on a given template path.
     *
     * <p>This method converts the provided template path into a {@code Path} object,
     * obtains its parent directory, and appends an "img" directory to it.</p>
     *
     * @param templatePath the path to the template file; cannot be null or empty
     * @return a string representing the resolved "img" directory path
     * @throws IllegalArgumentException if the template path is null, empty, or invalid
    */
    
    private String calculateImagePath(String templatePath) {

        logBegin(templatePath, templatePath);

        if (templatePath == null || templatePath.isEmpty()) {
            throw new IllegalArgumentException("Il templatePath non può essere nullo o vuoto.");
        }
        // Converte il templatePath in un oggetto Path
        Path path = Paths.get(templatePath);
        
        // Risale il path di due livelli (si posiziona nella dir che corrisponde al tipo_adempimento es "DER" o "VIA")
        Path grandParentPath = path.getParent().getParent();
        if (grandParentPath  == null) {
            throw new IllegalArgumentException("Il templatePath non sembra essere un path valido: " + templatePath);
        }

        // Aggiunge la directory "img" al path genitore
        Path imagePath = grandParentPath.resolve("img");
        logEnd(className, className);
        return imagePath.toString(); // Ritorna il path come stringa
    }

    /**
     * Get compiled template pdf 2 byte [ ].
     *
     * @param json         the json
     * @param templatePath the template path
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public static byte[] getCompiledTemplatePDF2(String json, String templatePath) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = new FileInputStream("D:\\temp\\Modulo_PDF_VIA_CONFIG_V3.docx"); // Absolute path
        File fileLogo = new File("D:\\temp\\logo2.png");

        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        // 2) Create fields metadata to manage image
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("logo", NullImageBehaviour.RemoveImageTemplate);

        // 3) Create context Java model
        IContext context = report.createContext();
        //IImageProvider logo = new ClassPathImageProvider( DocxProjectWithVelocityAndImage.class, "logo.png" );

        // Image with width forced and height computed with ratio
        //IImageProvider ratioSizeLogo = new FileImageProvider(fileLogo);
        IImageProvider ratioSizeLogo = new ClassPathImageProvider(TemplateUtil.class, "templates/logo.png");
        ratioSizeLogo.setUseImageSize(true);
        ratioSizeLogo.setWidth(100f);
        ratioSizeLogo.setResize(true);
        context.put("logo", ratioSizeLogo);

        // All data
        Map<String, Object> mappings = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        mappings.put("ist", obj);
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        context.put("d", mappings);

        Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);

        report.convert(context, options, out);

        return out.toByteArray();
    }


//    public static void createPDFA() throws Exception {
//
//        String outputDir = "D:\\";
//        String fontLibrary = "C:\\Windows\\Fonts\\";
//        String file = "PDFATest_PDFA.pdf";
//        String message = "Test messaggio da inserire";
//        String fontfileTimes = "C:\\Windows\\Fonts\\TIMES.TTF";
//        String fontfileCalibriBold = "C:\\Windows\\Fonts\\calibrib.ttf";
//        String fontfileCalibri = "C:\\Windows\\Fonts\\calibri.ttf";
//        String originFile = "D:\\PDFATest.pdf";
//        String watermarkFile = "D:\\PDFATest_watermark.pdf";
//
//        Path tmpDir = Paths.get(outputDir);
//        addWatermarkText(originFile, watermarkFile, "BOZZA");
//
//        String fileInput = fillForm(watermarkFile, tmpDir);
//        List imageFileList = PDF2Image(fileInput, tmpDir);
//        //PDFtoJPG(fileInput, fileInput + ".jpg");
//        String pdfa = createPDFA(imageFileList, tmpDir);
//
//        //File filePDF = new File(originFile);
//        //try (PDDocument doc = new PDDocument())
//
////        try (PDDocument doc = PDDocument.load(filePDF))
////        {
////            PDPage page = new PDPage();
////            doc.addPage(page);
////
////            // load the font as this needs to be embedded
////            PDFont font = PDType0Font.load(doc, new File(fontfileTimes));
////            font = PDType0Font.load(doc, new File(fontfileCalibriBold));
////            font = PDType0Font.load(doc, new File(fontfileCalibri));
////
////            // A PDF/A file needs to have the font embedded if the font is used for text rendering
////            // in rendering modes other than text rendering mode 3.
////            //
////            // This requirement includes the PDF standard fonts, so don't use their static PDFType1Font classes such as
////            // PDFType1Font.HELVETICA.
////            //
////            // As there are many different font licenses it is up to the developer to check if the license terms for the
////            // font loaded allows embedding in the PDF.
////            //
////            if (!font.isEmbedded())
////            {
////                throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
////                        + " text rendering in rendering modes other than rendering mode 3 are embedded.");
////            }
////
////            // create a page with the message
////            /*
////            try (PDPageContentStream contents = new PDPageContentStream(doc, page))
////            {
////                contents.beginText();
////                contents.setFont(font, 12);
////                contents.newLineAtOffset(100, 700);
////                contents.showText(message);
////                contents.endText();
////            }
////             */
////
////            // add XMP metadata
////            XMPMetadata xmp = XMPMetadata.createXMPMetadata();
////
////            try
////            {
////                DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
////                dc.setTitle(file);
////
////                PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
////                id.setPart(1);
////                id.setConformance("B");
////
////                XmpSerializer serializer = new XmpSerializer();
////                ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                serializer.serialize(xmp, baos, true);
////
////                PDMetadata metadata = new PDMetadata(doc);
////                metadata.importXMPMetadata(baos.toByteArray());
////                doc.getDocumentCatalog().setMetadata(metadata);
////            }
////            catch(BadFieldValueException | TransformerException e)
////            {
////                // won't happen here, as the provided value is valid
////                throw new IllegalArgumentException(e);
////            }
////
////            // sRGB output intent
////            InputStream colorProfile = new FileInputStream("D:\\Download\\sRGB.icc");
////            PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
////            intent.setInfo("sRGB IEC61966-2.1");
////            intent.setOutputCondition("sRGB IEC61966-2.1");
////            intent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
////            intent.setRegistryName("http://www.color.org");
////            doc.getDocumentCatalog().addOutputIntent(intent);
////
////            //doc.save(file, CompressParameters.NO_COMPRESSION);
////            doc.save("D:\\" + file);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//    }
/*
    private static String fillForm(String formTemplate, Path path) throws IOException {
        String fileOut = path + "FillForm.pdf";
        try (PDDocument pdfDocument = PDDocument.load(new File(formTemplate))) {
            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                acroForm.getField(acroForm.getFields().get(0).getFullyQualifiedName()).setValue("TEST");
                acroForm.refreshAppearances();
                acroForm.flatten();
            }
            pdfDocument.save(fileOut);
        }
        return fileOut;
    }

    public static List PDF2Image(String fileInput, Path path) {
        List imageFileList = new List();
        try (final PDDocument document = PDDocument.load(new File(fileInput))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                String fileName = path + "image-" + page + ".png";
                imageFileList.add(fileName);
                ImageIOUtil.writeImage(bim, fileName, 300);
            }
            document.close();
        } catch (IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }
        return imageFileList;
    }

    public static void PDFtoJPG(String fileInput, String fileOutput) throws Exception {
        PDDocument pd = PDDocument.load(new File(fileInput));
        PDFRenderer pr = new PDFRenderer(pd);
        BufferedImage bi = pr.renderImageWithDPI(0, 300);
        ImageIO.write(bi, "JPEG", new File(fileOutput));
    }
*/


//    public static String createPDFA(List imagePathList, Path path) throws IOException {
//        try (PDDocument doc = new PDDocument()) {
//            PDPage page = new PDPage();
//            doc.addPage(page);
//            String fontfileTimes = "C:\\Windows\\Fonts\\TIMES.TTF";
//            //String fontfileCalibriBold = "C:\\Windows\\Fonts\\calibrib.ttf";
//            //String fontfileCalibri = "C:\\Windows\\Fonts\\calibri.ttf";
//            PDFont font = PDType0Font.load(doc, new File(fontfileTimes));
//            if (!font.isEmbedded()) {
//                throw new IllegalStateException("PDF/A compliance requires that all fonts used for"
//                        + " text rendering in rendering modes other than rendering mode 3 are embedded.");
//            }
//            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
//                contents.beginText();
//                contents.setFont(font, 12);
//                contents.newLineAtOffset(100, 700);
//                contents.showText("");
//                contents.endText();
//            }
//
//            // add XMP metadata
//            XMPMetadata xmp = XMPMetadata.createXMPMetadata();
//
//            String fileName = path + "PDFATest_PDFA_Local.pdf";
//            try {
//                DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
//                dc.setTitle(fileName);
//                dc.setDescription("descrizione");
//                dc.setIdentifier("Identifier");
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
//
//                /*
//                String xmpData ="<?xpacket begin=\"\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>\n" +
//                        "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" >\n" +
//                        "    <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" +
//                        "        <rdf:Description rdf:about=\"\"\n" +
//                        "            xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
//                        "            xmlns:pdfaid=\"http://www.aiim.org/pdfa/ns/id/\">\n" +
//                        "            <dc:format>application/pdf</dc:format>\n" +
//                        "            <dc:creator>\n" +
//                        "            <rdf:Seq>\n" +
//                        "                <rdf:li>CSI Piemonte</rdf:li>\n" +
//                        "            </rdf:Seq>\n" +
//                        "            </dc:creator>\n" +
//                        "            <dc:title>\n" +
//                        "            <rdf:Alt>\n" +
//                        "                <rdf:li xml:lang=\"x-default\">TITOLO</rdf:li>\n" +
//                        "            </rdf:Alt>\n" +
//                        "            </dc:title>\n" +
//                        "            <pdfaid:part>1</pdfaid:part>\n" +
//                        "            <pdfaid:conformance>B</pdfaid:conformance>\n" +
//                        "        </rdf:Description>\n" +
//                        "    </rdf:RDF>\n" +
//                        "</x:xmpmeta>\n" +
//                        "<?xpacket end=\"w\"?>";
//                metadata.importXMPMetadata(xmpData.getBytes());
//                */
//                metadata.importXMPMetadata(baos.toByteArray());
//                doc.getDocumentCatalog().setMetadata(metadata);
//            } catch (BadFieldValueException | TransformerException e) {
//                throw new IllegalArgumentException(e);
//            }
//
//            // sRGB output intent
//            InputStream colorProfile = new FileInputStream("D:\\Download\\sRGB.icc");
//            PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
//            intent.setInfo("");
//            intent.setOutputCondition("");
//            intent.setOutputConditionIdentifier("");
//            intent.setRegistryName("");
//            doc.getDocumentCatalog().addOutputIntent(intent);
//
//            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
//                float scale = 1 / 5f;
//                for (int i = 0; i < imagePathList.getItemCount() ; i++) {
//                    PDImageXObject pdImage = PDImageXObject.createFromFile(imagePathList.getItem(i), doc);
//                    contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
//                }
//            }
//            doc.save(fileName);
//            return fileName;
//        }
//    }

    /*
    private static void addWatermarkText(String originFilePath, String destinationFilePath, String watermarkText) throws IOException {
        String fontfileCalibri = "C:\\Windows\\Fonts\\calibri.ttf";
        try (PDDocument doc = PDDocument.load(new File(originFilePath))) {
            PDFont font = PDType0Font.load(doc, new File(fontfileCalibri));
            for (PDPage page : doc.getPages()) {
                try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    float fontHeight = 100; // arbitrary for short text
                    float width = page.getMediaBox().getWidth();
                    float height = page.getMediaBox().getHeight();

                    int rotation = page.getRotation();
                    switch (rotation) {
                        case 90:
                            width = page.getMediaBox().getHeight();
                            height = page.getMediaBox().getWidth();
                            cs.transform(Matrix.getRotateInstance(Math.toRadians(90), height, 0));
                            break;
                        case 180:
                            cs.transform(Matrix.getRotateInstance(Math.toRadians(180), width, height));
                            break;
                        case 270:
                            width = page.getMediaBox().getHeight();
                            height = page.getMediaBox().getWidth();
                            cs.transform(Matrix.getRotateInstance(Math.toRadians(270), 0, width));
                            break;
                        default:
                            break;
                    }

                    float stringWidth = font.getStringWidth(watermarkText) / 1000 * fontHeight;
                    float diagonalLength = (float) Math.sqrt(width * width + height * height);
                    float angle = (float) Math.atan2(height, width);
                    float x = (diagonalLength - stringWidth) / 2; // "horizontal" position in rotated world
                    float y = -fontHeight / 4; // 4 is a trial-and-error thing, this lowers the text a bit
                    cs.transform(Matrix.getRotateInstance(angle, 0, 0));
                    cs.setFont(font, fontHeight);
                    // cs.setRenderingMode(RenderingMode.STROKE) // for "hollow" effect

                    PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
                    gs.setNonStrokingAlphaConstant(0.2f);
                    gs.setStrokingAlphaConstant(0.2f);
                    gs.setBlendMode(BlendMode.MULTIPLY);
                    gs.setLineWidth(3f);
                    cs.setGraphicsStateParameters(gs);

                    cs.setNonStrokingColor(Color.red);
                    cs.setStrokingColor(Color.red);

                    cs.beginText();
                    cs.newLineAtOffset(x, y);
                    cs.showText(watermarkText);
                    cs.endText();
                }
            }
            doc.save(destinationFilePath);
        }

    }
    */
    /*
    public static byte[] getCompiledTemplatePDFAOld(String json, String templatePath) throws Exception {

        IXDocReport report;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = TemplateUtil.class.getClassLoader().getResourceAsStream(templatePath);
        Document document = new Document();
        com.itextpdf.text.pdf.PdfReader reader = null;
        try {

            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfAWriter writer = PdfAWriter.getInstance(document,   new FileOutputStream("PDFATest.pdf"), PdfAConformanceLevel.PDF_A_1B);
            document.addAuthor("Author");
            document.addSubject("Subject");
            document.addLanguage("it-it");
            document.addCreationDate();

            writer.setTagged();
            writer.createXmpMetadata();
            writer.setCompressionLevel(9);
            // step 3: we open the document
            document.open();
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data


            // step 4:
            reader = new com.itextpdf.text.pdf.PdfReader("test_abcd.pdf");
            PdfImportedPage page;
            // Get number of pages:
            int pageCount = reader.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                document.newPage();
                page = writer.getImportedPage(reader, i+1);
                cb.addTemplate(page, 0, 0);
            }

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        // step 5: we close the document
        document.close();

        return null;

    }

    public static byte[] getCompiledTemplatePDFA(String json, String templatePath) throws Exception {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            return getCompiledTemplatePDFA(getCompiledTemplatePDF(json, templatePath));

        } catch (Exception e) {
            LOGGER.error(methodName + " Exception", e);
            throw new Exception(e);
        }
    }

    public static byte[] getCompiledTemplatePDFA(byte[] filePDF) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Document document = new Document();
        com.itextpdf.text.pdf.PdfReader reader = null;
        try {

            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            PdfAWriter writer = PdfAWriter.getInstance(document, outStream, PdfAConformanceLevel.PDF_A_1B);
            document.addAuthor("CSI Piemonte");
            document.addSubject("Subject");
            document.addLanguage("it-it");
            document.addCreationDate();

            writer.setTagged();
            writer.createXmpMetadata();
            writer.setCompressionLevel(9);
            // step 3: we open the document
            document.open();
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data


            // step 4:
            ByteArrayInputStream inStream = new ByteArrayInputStream(filePDF);
            reader = new com.itextpdf.text.pdf.PdfReader(inStream);
            PdfImportedPage page;
            // Get number of pages:
            int pageCount = reader.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                document.newPage();
                page = writer.getImportedPage(reader, i+1);
                cb.addTemplate(page, 0, 0);
            }

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        // step 5: we close the document
        document.close();

        return outStream.toByteArray();

    }
*/
}
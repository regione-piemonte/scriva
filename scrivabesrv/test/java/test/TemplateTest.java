/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package test;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.FileImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.NullImageBehaviour;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TemplateTest {

    public static void main(String[] args) {
        prova1();
        prova2();
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            //InputStream in = new FileInputStream("D:\\temp\\DocxProjectWithVelocityAndImage.docx"); // Absolute path

            InputStream in = new FileInputStream("D:\\temp\\prova.docx"); // Absolute path
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create fields metadata to manage image
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.addFieldAsImage("logo");
            metadata.addFieldAsImage("originalSizeLogo");
            metadata.addFieldAsImage("forcedSizeLogo");
            metadata.addFieldAsImage("ratioSizeLogo");
            metadata.addFieldAsImage("imageNotExistsAndRemoveImageTemplate", NullImageBehaviour.RemoveImageTemplate);
            metadata.addFieldAsImage("imageNotExistsAndKeepImageTemplate", NullImageBehaviour.KeepImageTemplate);
            metadata.addFieldAsImage("test");

            // 3) Create context Java model
            IContext context = report.createContext();
            Project project = new Project("XDocReport");
            context.put("project", project);
            File fileLogo = new File("D:\\temp\\logo2.png");
            //IImageProvider logo = new ClassPathImageProvider( DocxProjectWithVelocityAndImage.class, "logo.png" );
            IImageProvider logo = new FileImageProvider(fileLogo);
            context.put("logo", logo);

            IImageProvider test = new FileImageProvider(fileLogo);
            context.put("test", test);

            boolean useImageSize = true;
            IImageProvider originalSizeLogo = new FileImageProvider(fileLogo, useImageSize);
            context.put("originalSizeLogo", originalSizeLogo);

            // Image with width/height forced
            IImageProvider forcedSizeLogo = new FileImageProvider(fileLogo);
            forcedSizeLogo.setSize(400f, 100f);
            context.put("forcedSizeLogo", forcedSizeLogo);

            // Image with width forced and height computed with ratio
            IImageProvider ratioSizeLogo = new FileImageProvider(fileLogo);
            ratioSizeLogo.setUseImageSize(true);
            ratioSizeLogo.setWidth(100f);
            ratioSizeLogo.setResize(true);
            context.put("ratioSizeLogo", ratioSizeLogo);

            // 4) Generate report by merging Java model with the Docx
            OutputStream out = new FileOutputStream(new File("D:\\temp\\_Out.docx"));
            report.process(context, out);


            //FileUtils.writeByteArrayToFile(new File("D:\\temp\\DocxProjectWithVelocityAndImage_Out.pdf"), out.toByteArray());

            //FileUtils.writeByteArrayToFile(new File("D:\\temp\\DocxProjectWithVelocityAndImage_Out.pdf"), getCompiledTemplatePDF("{}", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prova1() {
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            //InputStream in = new FileInputStream("D:\\temp\\DocxProjectWithVelocityAndImage.docx"); // Absolute path
            InputStream in = new FileInputStream("D:\\temp\\prova.docx"); // Absolute path
            File fileLogo = new File("D:\\temp\\logo2.png");

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create fields metadata to manage image
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.addFieldAsImage("logo");

            // 3) Create context Java model
            IContext context = report.createContext();
            //IImageProvider logo = new ClassPathImageProvider( DocxProjectWithVelocityAndImage.class, "logo.png" );

            // Image with width forced and height computed with ratio
            IImageProvider ratioSizeLogo = new FileImageProvider(fileLogo);
            ratioSizeLogo.setUseImageSize(true);
            ratioSizeLogo.setWidth(100f);
            ratioSizeLogo.setResize(true);
            context.put("logo", ratioSizeLogo);

            // 4) Generate report by merging Java model with the Docx
            OutputStream out = new FileOutputStream(new File("D:\\temp\\_prova1.docx"));
            report.process(context, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void prova2() {
        try {
            // 1) Load Docx file by filling Velocity template engine and cache
            // it to the registry
            //InputStream in = new FileInputStream("D:\\temp\\DocxProjectWithVelocityAndImage.docx"); // Absolute path
            InputStream in = new FileInputStream("D:\\temp\\prova.docx"); // Absolute path
            File fileLogo = new File("D:\\temp\\logo2.png");

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create fields metadata to manage image
            FieldsMetadata metadata = report.createFieldsMetadata();
            metadata.addFieldAsImage("logo");

            // 3) Create context Java model
            IContext context = report.createContext();
            //IImageProvider logo = new ClassPathImageProvider( DocxProjectWithVelocityAndImage.class, "logo.png" );

            // Image with width forced and height computed with ratio
            IImageProvider ratioSizeLogo = new FileImageProvider(fileLogo);
            ratioSizeLogo.setUseImageSize(true);
            ratioSizeLogo.setWidth(100f);
            ratioSizeLogo.setResize(true);
            context.put("logo", ratioSizeLogo);

            OutputStream out = new FileOutputStream(new File("D:\\temp\\_prova2.pdf"));
            // report.process(context, out);
            Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
            //Options options = Options.getTo(ConverterTypeTo.PDF);

            report.convert(context, options, out);

            // 4) Generate report by merging Java model with the Docx
            //OutputStream out = new FileOutputStream(new File("D:\\temp\\_prova2.docx"));
            //report.process(context, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static byte[] getCompiledTemplatePDF(String json, String templatePath) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IXDocReport report;
        InputStream in = new FileInputStream("D:\\temp\\prova.docx"); // Absolute path
        report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        Map<String, Object> mappings = new HashMap<>();
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        mappings.put("ist", obj);
        mappings.put("now", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        IContext context = report.createContext();
        context.put("d", mappings);

        Options options = Options.getTo(ConverterTypeTo.PDF);

        report.convert(context, options, out);

        return out.toByteArray();
    }

    public static class Project {

        private final String name;
        private String url;

        public Project(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setURL(String url) {
            this.url = url;
        }

        public String getURL() {
            return url;
        }
    }
}
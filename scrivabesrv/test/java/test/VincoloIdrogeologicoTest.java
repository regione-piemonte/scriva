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

import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;

public class VincoloIdrogeologicoTest {

    private static final String endpointUrl = "http://tst-applogic.reteunitaria.piemonte.it/siforsrvApplVincoloidrogeologicoWsfad/services/siforsrvVincoloidrogeologico";

    public static void main(String[] args) throws IOException {
        //Non funziona usare API /ping/idro
        String gmlGeometry = "<gml:Polygon srsName=\"EPSG:32632\">\n" +
                "<gml:outerBoundaryIs>\n" +
                "<gml:LinearRing>\n" +
                "<gml:coordinates>\n" +
                "371801.01751313487,5037016.9067425551 400291.30560420314,5030871.9426444815\n" +
                "418726.19789842388,5011319.7841506116 417050.29859894922,4985064.0284588421\n" +
                "406436.26970227668,4962718.7044658475 392470.44220665505,4944283.8121716268\n" +
                "364538.78721541155,4950987.4093695255 347221.16112084064,4968863.668563921\n" +
                "343869.36252189142,4991767.6256567407 355042.02451838879,5016906.11514886\n" +
                "371801.01751313487,5037016.9067425551\n" +
                "</gml:coordinates>\n" +
                "</gml:LinearRing>\n" +
                "</gml:outerBoundaryIs>\n" +
                "</gml:Polygon>";
        String gmlGeometryEscaped  = "&lt;gml:Polygon srsName=&quot;EPSG:32632&quot;&gt;\n" +
                "&lt;gml:outerBoundaryIs&gt;\n" +
                "&lt;gml:LinearRing&gt;\n" +
                "&lt;gml:coordinates&gt;\n" +
                "371801.01751313487,5037016.9067425551 400291.30560420314,5030871.9426444815\n" +
                "418726.19789842388,5011319.7841506116 417050.29859894922,4985064.0284588421\n" +
                "406436.26970227668,4962718.7044658475 392470.44220665505,4944283.8121716268\n" +
                "364538.78721541155,4950987.4093695255 347221.16112084064,4968863.668563921\n" +
                "343869.36252189142,4991767.6256567407 355042.02451838879,5016906.11514886\n" +
                "371801.01751313487,5037016.9067425551\n" +
                "&lt;/gml:coordinates&gt;\n" +
                "&lt;/gml:LinearRing&gt;\n" +
                "&lt;/gml:outerBoundaryIs&gt;\n" +
                "&lt;/gml:Polygon&gt;";
        String gmlGeoEscaped = StringEscapeUtils.escapeXml(gmlGeometry);
        System.out.println(gmlGeoEscaped);
        //VincoloIdrogeologicoServiceHelper vincoloIdrogeologicoService = new VincoloIdrogeologicoServiceHelper(endpointUrl);
        //Ricadenza[] ricadenze = vincoloIdrogeologicoService.determinaRicadenzaSuVincoloIdrogeologicoPerGeometriaGML(gmlGeometryEscaped);
        //System.out.println(ricadenze);
    }

}
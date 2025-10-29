/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package test.tokenretry;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.soap.util.net.TcpTunnelGui;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

//import it.csi.scriva.scrivafoweb.integration.petstore.PetApi;
//import it.csi.scriva.scrivafoweb.integration.petstore.dto.Pet;
import it.csi.scriva.scrivafoweb.util.oauth2.OauthHelper;
import it.csi.scriva.scrivafoweb.util.oauth2.ResteasyOauthWrapper;


public class TestOauth2Petstore {

    public static void launchSniffer() {
        try {
            TcpTunnelGui.main(new String[] {"8888", "tst-interop-api.dominio.it", "80"});
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
    }

    public static String ENDPOINT_BASE = "http://tst-interop-api.dominio.it/api/testSwaggerPetstore-v2/1.0.5/";
    //public static String ENDPOINT_BASE = "http://127.0.0.1:8888/api/testSwaggerPetstore-v2/1.0.5";
    
    public static void main(String[] args) {
        //launchSniffer();
//        ResteasyClient client = new ResteasyClientBuilder().build();
//        ResteasyWebTarget target = client.target(ENDPOINT_BASE);
//
//        OauthHelper oauthHelper = new OauthHelper("http://tst-interop-api.dominio.it/api/token",
//                "clientsecret_scrivafoweb",
//                "key_scrivafoweb",);
//        
//        ResteasyOauthWrapper wrapper = new ResteasyOauthWrapper(target, PetApi.class, oauthHelper);
//        PetApi petApi = (PetApi)Proxy.newProxyInstance(PetApi.class.getClassLoader(), new Class[] {PetApi.class}, wrapper);
//
//        try {
//            List<String> statuses = new ArrayList<String>();
//            statuses.add("available");
//            Response resp = null;
//            
//            resp = petApi.findPetsByStatus(statuses, null, null, null);
//            System.out.println("findPetsByStatus -> " + resp.getStatus());
//            GenericType<List<Pet>> petListType = new GenericType<List<Pet>>() {};
//            List<Pet> pets = resp.readEntity(petListType);
//            System.out.println(pets);
//            
//            resp = petApi.getPetById(1l, null, null, null);
//            System.out.println("getPetById -> " + resp.getStatus());
//            
//            resp = petApi.getPetById(1l, null, null, null);
//            System.out.println("getPetById -> " + resp.getStatus());
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
    }
}
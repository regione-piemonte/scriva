/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business;

import it.csi.scriva.scrivafoweb.business.be.impl.CustomReadExceptionMapper;
import it.csi.scriva.scrivafoweb.business.be.impl.EnvironmentApiServiceImpl;
import it.csi.scriva.scrivafoweb.business.be.impl.FilesApiServiceImpl;
import it.csi.scriva.scrivafoweb.business.be.impl.PingApiServiceImpl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("api/v1")
public class ScrivafowebRestApplication extends Application{
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
    public ScrivafowebRestApplication(){
         //singletons.add(new LgspawclBE());
         singletons.add(new PingApiServiceImpl());
         singletons.add(new FilesApiServiceImpl());
         singletons.add(new EnvironmentApiServiceImpl());
         singletons.add(new CustomReadExceptionMapper());

    }
    @Override
    public Set<Class<?>> getClasses() {
         return empty;
    }
    @Override
    public Set<Object> getSingletons() {
         return singletons;
    }

}
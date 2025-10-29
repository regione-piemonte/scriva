/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


/**
 * classe di avvio dell'applicazione
 */
@ApplicationPath("/api/v1")
//@EnableScheduling
public class ScrivabesrvRestApplication extends Application{
    private final Set<Object> singletons = new HashSet<>();
    private final Set<Class<?>> empty = new HashSet<>();

    /**
     * costruttore della classe
     */
    public ScrivabesrvRestApplication(){
        // Instatiate class
    }

    /**
     * Non utilizzato
     *
     * @return un set vuoto
     */
    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    /**
     * Non utilizzato
     *
     * @return un set vuoto
     */
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
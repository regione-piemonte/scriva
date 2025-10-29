/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * PolicyEnforcerBaseService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.business.be.helper.iride.dto;

public interface PolicyEnforcerBaseService extends java.rmi.Remote {
    public Identita identificaUserPassword(String in0, String in1) throws java.rmi.RemoteException, UnrecoverableException, SystemException, MalformedUsernameException, AuthException, IdProviderNotFoundException, InternalException;
    public Identita identificaCertificato(byte[] in0) throws java.rmi.RemoteException, UnrecoverableException, CertRevokedException, CertOutsideValidityException, SystemException, IdProviderNotFoundException, InternalException;
    public Identita identificaUserPasswordPIN(String in0, String in1, String in2) throws java.rmi.RemoteException, UnrecoverableException, SystemException, MalformedUsernameException, AuthException, IdProviderNotFoundException, InternalException;
    public boolean isPersonaAutorizzataInUseCase(Identita in0, UseCase in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchUseCaseException, NoSuchApplicationException, InternalException;
    public UseCase[] findUseCasesForPersonaInApplication(Identita in0, Application in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchApplicationException, InternalException;
    public boolean isIdentitaAutentica(Identita in0) throws java.rmi.RemoteException, UnrecoverableException, SystemException, InternalException;
    public String getInfoPersonaInUseCase(Identita in0, UseCase in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchUseCaseException, NoSuchApplicationException, InternalException;
    public Ruolo[] findRuoliForPersonaInUseCase(Identita in0, UseCase in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchUseCaseException, NoSuchApplicationException, InternalException;
    public Ruolo[] findRuoliForPersonaInApplication(Identita in0, Application in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchApplicationException, InternalException;
    public String getInfoPersonaSchema(Ruolo in0) throws java.rmi.RemoteException, UnrecoverableException, SystemException, BadRuoloException, InternalException;
    public Actor[] findActorsForPersonaInApplication(Identita in0, Application in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchApplicationException, InternalException;
    public Actor[] findActorsForPersonaInUseCase(Identita in0, UseCase in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, NoSuchUseCaseException, NoSuchApplicationException, InternalException;
    public boolean isPersonaInRuolo(Identita in0, Ruolo in1) throws java.rmi.RemoteException, UnrecoverableException, IdentitaNonAutenticaException, SystemException, BadRuoloException, InternalException;
}
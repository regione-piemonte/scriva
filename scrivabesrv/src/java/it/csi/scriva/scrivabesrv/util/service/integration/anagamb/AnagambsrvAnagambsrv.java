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
 * java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.scriva.scrivabesrv.util.service.integration.anagamb;

/**
 * The interface Anagambsrv anagambsrv.
 */
public interface AnagambsrvAnagambsrv extends java.rmi.Remote {
    /**
     * Test resources boolean.
     *
     * @return the boolean
     * @throws RemoteException the remote exception
     * @throws CSIException    the csi exception
     */
    public boolean testResources() throws java.rmi.RemoteException, CSIException;

    /**
     * Self check invocation node.
     *
     * @param in0 the in 0
     * @return the invocation node
     * @throws RemoteException the remote exception
     * @throws CSIException    the csi exception
     */
    public InvocationNode selfCheck(CalledResource[] in0) throws java.rmi.RemoteException, CSIException;

    /**
     * Has self check boolean.
     *
     * @return the boolean
     * @throws RemoteException the remote exception
     * @throws CSIException    the csi exception
     */
    public boolean hasSelfCheck() throws java.rmi.RemoteException, CSIException;

    /**
     * Get sedi legali sede legale [ ].
     *
     * @param in0 the in 0
     * @param in1 the in 1
     * @param in2 the in 2
     * @param in3 the in 3
     * @param in4 the in 4
     * @return the sede legale [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public SedeLegale[] getSediLegali(String[] in0, String[] in1, String in2, String[] in3, String in4) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Gets sede legale.
     *
     * @param in0 the in 0
     * @return the sede legale
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public DettaglioSedeLegale getSedeLegale(String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Get sedi operative sede operativa [ ].
     *
     * @param codiceIstatProvinciaSL    the codice istat provincia sl
     * @param codiceIstatComuneSL       the codice istat comune sl
     * @param ragioneSocialeSL          the ragione sociale sl
     * @param codiceFiscale             the codice fiscale
     * @param codiceIstatProvinciaSO    the codice istat provincia so
     * @param codiceIstatComuneSO       the codice istat comune so
     * @param codiceSira                the codice sira
     * @param codiceStatoInformazioneSO the codice stato informazione so
     * @param codiceVia                 the codice via
     * @return the sede operativa [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public SedeOperativa[] getSediOperative(String codiceIstatProvinciaSL, String codiceIstatComuneSL, String ragioneSocialeSL, String codiceFiscale, String codiceIstatProvinciaSO, String codiceIstatComuneSO, String codiceSira, String codiceStatoInformazioneSO, String codiceVia) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Gets sede operativa.
     *
     * @param in0 the in 0
     * @return the sede operativa
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public DettaglioSedeOperativa getSedeOperativa(String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Save sede legale string.
     *
     * @param in0 the in 0
     * @return the string
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public String saveSedeLegale(DettaglioSedeLegale in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Save sede operativa string.
     *
     * @param in0 the in 0
     * @return the string
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public String saveSedeOperativa(DettaglioSedeOperativa in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Lista fasce addetti fasce addetti [ ].
     *
     * @return the fasce addetti [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public FasceAddetti[] listaFasceAddetti() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Lista certificazioni ambientali codice descrizione [ ].
     *
     * @return the codice descrizione [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public CodiceDescrizione[] listaCertificazioniAmbientali() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Lista classificazioni acustiche codice descrizione [ ].
     *
     * @return the codice descrizione [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public CodiceDescrizione[] listaClassificazioniAcustiche() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Lista autorizzazioni ambientali codice descrizione [ ].
     *
     * @param in0 the in 0
     * @return the codice descrizione [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public CodiceDescrizione[] listaAutorizzazioniAmbientali(String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Lista ambiti tematici codice descrizione [ ].
     *
     * @return the codice descrizione [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public CodiceDescrizione[] listaAmbitiTematici() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Lista attivita ateco 91 codice descrizione [ ].
     *
     * @return the codice descrizione [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public CodiceDescrizione[] listaAttivitaAteco91() throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Gets dettaglio sede legale.
     *
     * @param in0 the in 0
     * @return the dettaglio sede legale
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public DettaglioSedeLegale getDettaglioSedeLegale(String in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Voltura sede operativa boolean.
     *
     * @param in0 the in 0
     * @param in1 the in 1
     * @param in2 the in 2
     * @return the boolean
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public Boolean volturaSedeOperativa(String in0, String in1, String in2) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Update coord service boolean.
     *
     * @param in0 the in 0
     * @param in1 the in 1
     * @param in2 the in 2
     * @return the boolean
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public boolean updateCoordService(String in0, String in1, String in2) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Save dettaglio sede operativa sede operativa.
     *
     * @param in0 the in 0
     * @return the sede operativa
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public SedeOperativa saveDettaglioSedeOperativa(DettaglioSedeOperativa in0) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;

    /**
     * Get sedi legali by codice fiscale sede legale [ ].
     *
     * @param in0 the in 0
     * @param in1 the in 1
     * @param in2 the in 2
     * @param in3 the in 3
     * @param in4 the in 4
     * @return the sede legale [ ]
     * @throws RemoteException        the remote exception
     * @throws UnrecoverableException the unrecoverable exception
     * @throws CSIException           the csi exception
     * @throws AnagambsrvException    the anagambsrv exception
     * @throws SystemException        the system exception
     */
    public SedeLegale[] getSediLegaliByCodiceFiscale(String[] in0, String[] in1, String in2, String[] in3, String in4) throws java.rmi.RemoteException, UnrecoverableException, CSIException, AnagambsrvException, SystemException;
}
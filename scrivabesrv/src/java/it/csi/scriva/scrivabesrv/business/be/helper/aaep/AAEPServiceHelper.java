/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.aaep;

import it.csi.aaep.aaeporch.business.Cessazione;
import it.csi.aaep.aaeporch.business.DettagliCameraCommercio;
import it.csi.aaep.aaeporch.business.Impresa;
import it.csi.aaep.aaeporch.business.OrchestratoreImplService;
import it.csi.aaep.aaeporch.business.OrchestratoreIntf;
import it.csi.aaep.aaeporch.business.Persona;
import it.csi.aaep.aaeporch.business.PersonaINFOC;
import it.csi.aaep.aaeporch.business.Sede;
import it.csi.aaep.aaeporch.business.SedeListItem;
import it.csi.aaep.aaeporch.business.TipologiaFonte;
import it.csi.aaep.aaeporch.business.Ubicazione;
import it.csi.aaep.aaeporch.business.Utente;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComuneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNaturaGiuridicaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoSoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNaturaGiuridicaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.MasterdataEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoSoggettoEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type Aaep service helper.
 *
 * @author CSI PIEMONTE
 */
public class AAEPServiceHelper extends AbstractServiceHelper {

    private static final String FONTE_NATURA_GIURIDICA = "CodNaturaGiuridica";
    private static final String AAEP_CERCA_PER_CODICE_FISCALE_EXCEPTION_MSG = "it.csi.csi.wrapper.UserException:cercaPerCodiceFiscaleAAEP: Nessun record trovato";
    private static final String AAEP_CERCA_PER_FILTRI_EXCEPTION_MSG = "it.csi.csi.wrapper.UserException:cercaPerFiltri: Nessun record trovato";
    public static final String SERVICE_UNAVAILABLE = "service unavailable";

    private final String className = this.getClass().getSimpleName();
    private final OrchestratoreIntf service;

    /**
     * The Url service.
     */
    protected String urlService;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private ComuneDAO comuneDAO;

    @Autowired
    private NazioneDAO nazioneDAO;

    @Autowired
    private TipoNaturaGiuridicaDAO tipoNaturaGiuridicaDAO;

    @Autowired
    private TipoSoggettoDAO tipoSoggettoDAO;

    /**
     * Instantiates a new Aaep service helper.
     *
     * @param urlService url del servizio
     */
    public AAEPServiceHelper(String urlService) {
        this.urlService = urlService;
        this.service = this.getService(urlService);
    }

    /**
     * Gets service.
     *
     * @param urlService the url service
     * @return the service
     */
    private OrchestratoreIntf getService(String urlService) {
        logBegin(className);
        OrchestratoreIntf aaepService = null;
        try {
            OrchestratoreImplService orchestratoreImplService = new OrchestratoreImplService(new URL(urlService));
            aaepService = orchestratoreImplService.getOrchestratoreImplPort();
            logDebug(className, "Service 'JavaServiceDesc' INITIALIZED");
        } catch (MalformedURLException e) {
            logError(className, "ERROR : invalid url [" + urlService + "]");
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return aaepService;
    }

    /**
     * Gets dettaglio impresa.
     *
     * @param codiceFiscaleSoggetto cf soggetto
     * @return Impresa dettaglio impresa
     * @throws ServiceException ServiceException
     */
    public Impresa getDettaglioImpresa(String codiceFiscaleSoggetto) throws ServiceException {
        logBegin(className);
        Impresa result;
        if (service == null) {
            logError(className, SERVICE_UNAVAILABLE);
            throw new ServiceException("getDettaglioImpresa ERROR : service unavailable");
        }

        try {
            result = this.service.getDettaglioImpresa(null, TipologiaFonte.INFOC.value(), codiceFiscaleSoggetto);
        } catch (Exception e) {
            logError(className, e);
            if (StringUtils.startsWithIgnoreCase(e.getMessage(), AAEP_CERCA_PER_FILTRI_EXCEPTION_MSG)) {
                return null;
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets dettaglio sede.
     *
     * @param sedeInput the sede input
     * @return the dettaglio sede
     * @throws ServiceException the service exception
     */
    public Sede getDettaglioSede(Sede sedeInput) throws ServiceException {
        logBegin(className);
        Sede result;
        if (service == null) {
            logError(className, SERVICE_UNAVAILABLE);
            throw new ServiceException("getDettaglioSede ERROR : service unavailable");
        }

        try {
            result = this.service.getDettaglioSede(null, TipologiaFonte.INFOC, sedeInput);
        } catch (Exception e) {
            logError(className, e);
            if (StringUtils.startsWithIgnoreCase(e.getMessage(), AAEP_CERCA_PER_FILTRI_EXCEPTION_MSG)) {
                return null;
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Cerca impresa by codice fiscale soggetto extended dto.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return SoggettoExtendedDTO soggetto extended dto
     * @throws ServiceException ServiceException
     */
    public SoggettoExtendedDTO cercaImpresaByCodiceFiscale(String codiceFiscaleSoggetto) throws ServiceException {
        logBegin(className);
        SoggettoExtendedDTO result;
        if (service == null) {
            logError(className, SERVICE_UNAVAILABLE);
            throw new ServiceException("cercaImpresaByCodiceFiscale ERROR : service unavailable");
        }

        try {
            Impresa impresa = this.getDettaglioImpresa(codiceFiscaleSoggetto);
            result = this.populateSoggettoImpresaFromAAEP(impresa);
        } catch (Exception e) {
            logError(className, e);
            if (StringUtils.startsWithIgnoreCase(e.getMessage(), AAEP_CERCA_PER_FILTRI_EXCEPTION_MSG)) {
                return null;
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Cerca persone infoc list.
     *
     * @param utente                   utente
     * @param codiceFiscaleImpresa     codiceFiscaleImpresa
     * @param soloRappresentantiLegali soloRappresentantiLegali
     * @return List<Persona>         list
     * @throws ServiceException ServiceException
     */
    public List<Persona> cercaPersoneInfoc(Utente utente, String codiceFiscaleImpresa, Boolean soloRappresentantiLegali) throws ServiceException {
        logBegin(className);
        if (service == null) {
            logError(className, SERVICE_UNAVAILABLE);
            throw new ServiceException("cercaPersoneInfoc ERROR : service unavailable");
        }
        try {
            return this.service.cercaPersoneInfoc(null, codiceFiscaleImpresa, soloRappresentantiLegali);
        } catch (Exception e) {
            logError(className, e);
            if (StringUtils.startsWithIgnoreCase(e.getMessage(), AAEP_CERCA_PER_CODICE_FISCALE_EXCEPTION_MSG)) {
                return null;
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load soggetto from aaep list.
     *
     * @param utente                   utente
     * @param codiceFiscaleImpresa     codiceFiscaleImpresa
     * @param codiceFiscaleSoggetto    codiceFiscaleSoggetto
     * @param soloRappresentantiLegali soloRappresentantiLegali
     * @return List<SoggettoExtendedDTO>         list
     * @throws ServiceException ServiceException
     */
    public List<SoggettoExtendedDTO> loadSoggettoFromAAEP(Utente utente, String codiceFiscaleImpresa, String codiceFiscaleSoggetto, Boolean soloRappresentantiLegali) throws ServiceException {
        logBegin(className);
        if (service == null) {
            logError(className, SERVICE_UNAVAILABLE);
            throw new ServiceException("loadSoggettoFromAAEP ERROR : service unavailable");
        }
        try {
            List<SoggettoExtendedDTO> listSoggetti = new ArrayList<>();
            List<Persona> list = this.cercaPersoneInfoc(null, codiceFiscaleImpresa, soloRappresentantiLegali);
            for (Persona p : list) {
                if (codiceFiscaleSoggetto.equalsIgnoreCase(p.getCodiceFiscale())) {
                    PersonaINFOC personaINFOC = this.loadDettaglioPersoneInfoc(null, p);
                    SoggettoExtendedDTO soggetto = populateSoggettoFromAAEP(personaINFOC);
                    listSoggetti.add(soggetto);
                    break;
                }
            }
            return listSoggetti;
        } catch (Exception e) {
            logError(className, e);
            if (StringUtils.startsWithIgnoreCase(e.getMessage(), AAEP_CERCA_PER_CODICE_FISCALE_EXCEPTION_MSG)) {
                return null;
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load dettaglio persone infoc persona infoc.
     *
     * @param utente  utente
     * @param persona persona
     * @return PersonaINFOC persona infoc
     * @throws ServiceException ServiceException
     */
    public PersonaINFOC loadDettaglioPersoneInfoc(Utente utente, Persona persona) throws ServiceException {
        logBegin(className);
        if (service == null) {
            logError(className, SERVICE_UNAVAILABLE);
            throw new ServiceException("loadDettaglioPersoneInfoc ERROR : service unavailable");
        }
        try {
            return (PersonaINFOC) this.service.getDettaglioPersonaInfoc(utente, persona);
        } catch (Exception e) {
            logError(className, e);
            if (StringUtils.startsWithIgnoreCase(e.getMessage(), AAEP_CERCA_PER_CODICE_FISCALE_EXCEPTION_MSG)) {
                return null;
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            logEnd(className);
        }
    }

    /**
     * Populate soggetto impresa from aaep soggetto extended dto.
     *
     * @param impresa impresa
     * @return SoggettoExtendedDTO soggetto extended dto
     */
    private SoggettoExtendedDTO populateSoggettoImpresaFromAAEP(Impresa impresa) {
        logBeginInfo(className, impresa);
        SoggettoExtendedDTO dto = null;
        try {
            dto = new SoggettoExtendedDTO();
            dto.setCfSoggetto(impresa.getCodiceFiscale());
            dto.setTipoNaturaGiuridica(getTipoNaturaGiuridica(impresa));
            dto.setIdMasterdata(2L);
            dto.setDenSoggetto(impresa.getRagioneSociale());
            dto.setPivaSoggetto(impresa.getPartitaIva());
            dto.setDesPec(impresa.getPostaElettronicaCertificata());

            DettagliCameraCommercio dcc = impresa.getDettagliCameraCommercio();
            if (null != dcc) {
                dto.setDenNumeroCCIAA(dcc.getNumIscrizionePosizioneREA());
                dto.setDenProvinciaCCIAA(dcc.getSiglaProvinciaIscrizioneREA());
                Date dataREA = getFormattedData(dcc.getDataIscrizioneREA());
                if (dataREA != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dataREA);
                    dto.setDenAnnoCCIAA(calendar.get(Calendar.YEAR));
                }
            }

            SedeListItem sedeLegale = getSedeLegale(impresa);
            if (sedeLegale != null && sedeLegale.getUbicazione() != null) {
                Sede sedeLegaleDettaglio = getDettaglioSede((SedeListItem) sedeLegale);
                Ubicazione ubicazioneSedeLegale = sedeLegaleDettaglio != null && sedeLegaleDettaglio.getUbicazione() != null ? sedeLegaleDettaglio.getUbicazione() : sedeLegale.getUbicazione();
                dto.setIndirizzoSoggetto(ubicazioneSedeLegale.getToponimo() + " " + ubicazioneSedeLegale.getIndirizzo());
                dto.setNumCivicoIndirizzo(ubicazioneSedeLegale.getNumeroCivico());
                dto.setCapSedeLegale(ubicazioneSedeLegale.getCap());
                String codiceIstatComuneSL = ubicazioneSedeLegale.getCodISTATComune();
                if (StringUtils.isNotBlank(codiceIstatComuneSL)) {
                    List<ComuneExtendedDTO> comuneSedeLegaleList = comuneDAO.loadComuneByCodIstat(codiceIstatComuneSL);
                    if (null != comuneSedeLegaleList && !comuneSedeLegaleList.isEmpty()) {
                        dto.setComuneSedeLegale(comuneSedeLegaleList.get(0));
                    }
                }
            }
            dto.setDataCessazioneSoggetto(getDataCessazioneImpresa(impresa));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }

        return dto;
    }

    /**
     * Populate soggetto from aaep soggetto extended dto.
     *
     * @param persona PersonaINFOC
     * @return SoggettoExtendedDTO soggetto extended dto
     */
    private SoggettoExtendedDTO populateSoggettoFromAAEP(PersonaINFOC persona) {
        logBeginInfo(className, persona);
        SoggettoExtendedDTO dto = new SoggettoExtendedDTO();
        dto.setCfSoggetto(persona.getCodiceFiscale());
        dto.setCognome(persona.getCognome());
        dto.setNome(persona.getNome());

        List<TipoSoggettoDTO> tipoSoggettoList = tipoSoggettoDAO.loadTipoSoggettoByCode(TipoSoggettoEnum.PF.name());
        if (null != tipoSoggettoList && !tipoSoggettoList.isEmpty()) {
            dto.setTipoSoggetto(tipoSoggettoList.get(0));
        }

        ComuneExtendedDTO comuneNascita = getComune(persona.getCodComuneNascita(), persona.getDescrComuneNascita());
        if (comuneNascita != null) {
            dto.setComuneNascita(comuneNascita);
            dto.setNazioneNascita(comuneNascita.getProvincia().getRegione().getNazione());
        } else {
            dto.setCittaEsteraNascita(persona.getDescrComuneNascita());
            List<NazioneDTO> nazioneNascitaList = nazioneDAO.loadNazioneByCodIso(persona.getCodStatoNascita());
            dto.setNazioneNascita(nazioneNascitaList != null && !nazioneNascitaList.isEmpty() ? nazioneNascitaList.get(0) : null);
        }

        ComuneExtendedDTO comuneResidenza = getComune(persona.getCodComuneRes(), persona.getDescrComuneRes());
        if (comuneResidenza != null) {
            dto.setComuneResidenza(comuneResidenza);
            dto.setNazioneResidenza(comuneResidenza.getProvincia().getRegione().getNazione());
            dto.setCapResidenza(persona.getCapResidenza());
        } else {
            dto.setCittaEsteraResidenza(persona.getDescrComuneRes());
            List<NazioneDTO> nazioneResidenzaList = nazioneDAO.loadNazioneByCodIso(persona.getCodStatoRes());
            dto.setNazioneResidenza(nazioneResidenzaList != null && !nazioneResidenzaList.isEmpty() ? nazioneResidenzaList.get(0) : null);
        }
        dto.setIndirizzoSoggetto(StringUtils.isNotBlank(persona.getDescrToponimoResid()) ? persona.getDescrToponimoResid() + " " + persona.getViaResidenza() : persona.getViaResidenza());
        dto.setNumCivicoIndirizzo(persona.getNumCivicoResid());
        dto.setDataNascitaSoggetto(getFormattedData(persona.getDataNascita()));

        logEnd(className);
        return dto;
    }

    /**
     * Gets data cessazione impresa.
     *
     * @param impresa the impresa
     * @return the data cessazione impresa
     */
    private Date getDataCessazioneImpresa(Impresa impresa) {
        logBeginInfo(className, impresa);
        Date result = null;

        Cessazione cessazione = impresa.getCessazione();
        if (cessazione != null) {
            String dataCessazione = cessazione.getDataCessazione();
            String codCausaleCessazione = cessazione.getCodCausaleCessazione();
            String dataDenunciaCessazione = cessazione.getDataDenunciaCessazione();
            result = verifyDataCessazioneImpresa(dataCessazione, codCausaleCessazione, dataDenunciaCessazione);
        }

        if (result == null) {
            SedeListItem sedeLegale = getSedeLegale(impresa);
            if (sedeLegale != null) {
                String dataCessazione = sedeLegale.getDataCessazione();
                String codCausaleCessazione = sedeLegale.getCodCausaleCessazione();
                result = verifyDataCessazioneImpresa(dataCessazione, codCausaleCessazione, null);
            }
        }
        logEnd(className);
        return result;
    }

    /**
     * Verify data cessazione impresa date.
     *
     * @param dataCessazione         the data cessazione
     * @param codCausaleCessazione   the cod causale cessazione
     * @param dataDenunciaCessazione the data denuncia cessazione
     * @return the date
     */
    private Date verifyDataCessazioneImpresa(String dataCessazione, String codCausaleCessazione, String dataDenunciaCessazione) {
        logBeginInfo(className, "Parametri in input dataCessazione [" + dataCessazione + "] - codCausaleCessazione [" + codCausaleCessazione + "] - dataDenunciaCessazione [" + dataDenunciaCessazione + "]");
        Date result = null;
        try {
            if (StringUtils.isNotBlank(dataCessazione) || StringUtils.isNotBlank(codCausaleCessazione) || StringUtils.isNotBlank(dataDenunciaCessazione)) {
                try {
                    result = StringUtils.isNotBlank(dataCessazione) ? simpleDateFormat.parse(dataCessazione) : new Date();
                } catch (ParseException e) {
                    logError(className, e);
                    result = new Date();
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets sede legale.
     *
     * @param impresa the impresa
     * @return the sede legale
     */
    private SedeListItem getSedeLegale(Impresa impresa) {
        logBeginInfo(className, impresa);
        SedeListItem result = null;
        try {
            List<Sede> sedi = impresa.getSedi();
            for (Sede sede : sedi) {
                if (sede.getDescrTipoSede().equalsIgnoreCase("sede legale")) {
                    result = (SedeListItem) sede;
                    break;
                }
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets comune.
     *
     * @param codiceComune the codice comune nascita
     * @param descrComune  the descr comune nascita
     * @return the comune
     */
    private ComuneExtendedDTO getComune(String codiceComune, String descrComune) {
        logBeginInfo(className, "Parametri in input codiceComune [" + codiceComune + "] - descrComune [" + descrComune + "]");
        ComuneExtendedDTO result = null;
        try {
            List<ComuneExtendedDTO> comune = StringUtils.isNotBlank(codiceComune) ? comuneDAO.loadComuneByCodIstat(codiceComune) : null;
            if (comune == null || comune.isEmpty()) {
                comune = comune == null && StringUtils.isNotBlank(descrComune) ? comuneDAO.loadComuneByDenominazione(descrComune) : null;
            }
            result = comune != null && !comune.isEmpty() ? comune.get(0) : null;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets formatted data.
     *
     * @param data the data
     * @return the formatted data
     */
    private Date getFormattedData(String data) {
        logBeginInfo(className, "data [" + data + "]");
        Date result = null;
        if (StringUtils.isNotBlank(data)) {
            try {
                result = simpleDateFormat.parse(data);
            } catch (Exception e) {
                logError(className, e);
            } finally {
                logEnd(className);
            }
        }
        return result;
    }

    /**
     * Get tipo natura giuridica string.
     *
     * @param impresa the impresa
     * @return the string
     */
    private TipoNaturaGiuridicaDTO getTipoNaturaGiuridica(Impresa impresa) {
        logBeginInfo(className, impresa);
        TipoNaturaGiuridicaDTO result = null;
        try {
            List<TipoNaturaGiuridicaDTO> tipoNaturaGiuridicaList = tipoNaturaGiuridicaDAO.loadTipoNaturaGiuridicaByCodeMasterdataFonte(MasterdataEnum.AAEP.name(), FONTE_NATURA_GIURIDICA, impresa.getCodNaturaGiuridica());
            if (null != tipoNaturaGiuridicaList && !tipoNaturaGiuridicaList.isEmpty()) {
                result = tipoNaturaGiuridicaList.get(0);
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

}
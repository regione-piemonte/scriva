/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComuneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNaturaGiuridicaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoSoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.LimitiAmministrativiService;
import it.csi.scriva.scrivabesrv.business.be.service.SoggettiService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNaturaGiuridicaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoSoggettoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.validation.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Soggetti service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class SoggettiServiceImpl extends BaseApiServiceImpl implements SoggettiService {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Comune dao.
     */
    @Autowired
    ComuneDAO comuneDAO;

    /**
     * The Nazione dao.
     */
    @Autowired
    NazioneDAO nazioneDAO;

    /**
     * The Soggetto dao.
     */
    @Autowired
    SoggettoDAO soggettoDAO;

    /**
     * The Tipo natura giuridica dao.
     */
    @Autowired
    TipoNaturaGiuridicaDAO tipoNaturaGiuridicaDAO;

    /**
     * The Tipo soggetto dao.
     */
    @Autowired
    TipoSoggettoDAO tipoSoggettoDAO;

    /**
     * The Limiti amministrativi service.
     */
    @Autowired
    LimitiAmministrativiService limitiAmministrativiService;

    /**
     * Save soggetto long.
     *
     * @param soggetto     the soggetto
     * @param attoreScriva the attore scriva
     * @return the long
     */
    @Override
    public Long saveSoggetto(SoggettoExtendedDTO soggetto, AttoreScriva attoreScriva) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        Long idSoggetto = null;
        try {
            if (soggetto != null) {
                if (StringUtils.isBlank(soggetto.getGestAttoreIns())) {
                    soggetto.setGestAttoreIns(attoreScriva.getCodiceFiscale());
                }
                soggetto.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
                setDatiByCodice(soggetto, attoreScriva);
                idSoggetto = soggettoDAO.saveSoggetto(soggetto.getDTO());
            }
        } catch (Exception e) {
            logError(className, methodName, e);
        } finally {
            logEnd(className, methodName);
        }
        return idSoggetto;
    }

    /**
     * Update soggetto integer.
     *
     * @param soggetto     the soggetto
     * @param attoreScriva the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateSoggetto(SoggettoExtendedDTO soggetto, AttoreScriva attoreScriva) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        Integer resUpdateSoggetto = null;
        try {
            if (soggetto != null) {
                if (StringUtils.isBlank(soggetto.getGestAttoreUpd())) {
                    soggetto.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
                }
                soggetto.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
                setDatiByCodice(soggetto, attoreScriva);
                resUpdateSoggetto = soggettoDAO.updateSoggetto(soggetto.getDTO());
            }
        } catch (Exception e) {
            logError(className, methodName, e);
        } finally {
            logEnd(className, methodName);
        }
        return resUpdateSoggetto;
    }

    /**
     * Gets comune by cod istat.
     *
     * @param codIstatComune the cod istat comune
     * @return the comune by cod istat
     */
    @Override
    public ComuneExtendedDTO getComuneByCodIstat(String codIstatComune) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        ComuneExtendedDTO comune = null;
        try {
            List<ComuneExtendedDTO> comuneList = comuneDAO.loadComuneByCodIstat(codIstatComune);
            comune = comuneList != null && !comuneList.isEmpty() ? comuneList.get(0) : null;
        } catch (Exception e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return comune;
    }

    /**
     * Gets nazione by cod istat.
     *
     * @param codIstatNazione the cod istat nazione
     * @return the nazione by cod istat
     */
    @Override
    public NazioneDTO getNazioneByCodIstat(String codIstatNazione) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        NazioneDTO nazione = null;
        try {
            List<NazioneDTO> nazioneList = nazioneDAO.loadNazioneByCodIstat(codIstatNazione);
            nazione = nazioneList != null && !nazioneList.isEmpty() ? nazioneList.get(0) : null;
        } catch (Exception e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return nazione;
    }

    /**
     * Gets tipo soggetto by code.
     *
     * @param codTipoSoggetto the cod tipo soggetto
     * @return the tipo soggetto by code
     */
    @Override
    public TipoSoggettoDTO getTipoSoggettoByCode(String codTipoSoggetto) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        TipoSoggettoDTO tipoSoggetto = null;
        try {
            List<TipoSoggettoDTO> tipoSoggettoList = tipoSoggettoDAO.loadTipoSoggettoByCode(codTipoSoggetto);
            tipoSoggetto = tipoSoggettoList != null && !tipoSoggettoList.isEmpty() ? tipoSoggettoList.get(0) : null;
        } catch (Exception e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoSoggetto;
    }

    /**
     * Gets tipo natura giuridica by code.
     *
     * @param codTipoNaturaGiuridica the cod tipo natura giuridica
     * @return the tipo natura giuridica by code
     */
    @Override
    public TipoNaturaGiuridicaDTO getTipoNaturaGiuridicaByCode(String codTipoNaturaGiuridica) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        TipoNaturaGiuridicaDTO tipoNaturaGiuridica = null;
        try {
            List<TipoNaturaGiuridicaDTO> tipoNaturaGiuridicaList = tipoNaturaGiuridicaDAO.loadTipoNaturaGiuridicaByCode(codTipoNaturaGiuridica);
            tipoNaturaGiuridica = tipoNaturaGiuridicaList != null && !tipoNaturaGiuridicaList.isEmpty() ? tipoNaturaGiuridicaList.get(0) : null;
        } catch (Exception e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return tipoNaturaGiuridica;
    }

    /**
     * Validate dto error dto.
     *
     * @param soggetto the soggetto
     * @return the error dto
     */
    @Override
    public ErrorDTO validateDTO(SoggettoExtendedDTO soggetto) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        if (null == soggetto.getTipoSoggetto() || (null != soggetto.getTipoSoggetto() && (null == soggetto.getTipoSoggetto().getIdTipoSoggetto() && null == soggetto.getTipoSoggetto().getCodiceTipoSoggetto()))){
            details.put("tipo_soggetto", ValidationResultEnum.MANDATORY.getDescription());
        }

        if ((null != soggetto.getTipoSoggetto() && "PG".equalsIgnoreCase(soggetto.getTipoSoggetto().getCodiceTipoSoggetto())) &&
                (null == soggetto.getTipoNaturaGiuridica() || (null != soggetto.getTipoNaturaGiuridica() && null == soggetto.getTipoNaturaGiuridica().getIdTipoNaturaGiuridica() && null == soggetto.getTipoNaturaGiuridica().getCodiceTipoNaturaGiuridica()))) {
            details.put("tipo_natura_giuridica", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == soggetto.getIdMasterdata()) {
            details.put("id_masterdata", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (null == soggetto.getIdMasterdataOrigine()) {
            details.put("id_masterdata_origine", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (StringUtils.isBlank(soggetto.getCfSoggetto())) {
            details.put("cf_soggetto", ValidationResultEnum.MANDATORY.getDescription());
        } else {
            String res = null;
            String cfSoggetto = soggetto.getCfSoggetto();
            String codiceTipoSoggetto = soggetto.getTipoSoggetto().getCodiceTipoSoggetto();
            if (TipoSoggettoEnum.PG.name().equalsIgnoreCase(codiceTipoSoggetto) || TipoSoggettoEnum.PB.name().equalsIgnoreCase(codiceTipoSoggetto)) {
                res = ValidationUtil.isValidPIva(soggetto.getCfSoggetto());
                if (!res.equals(ValidationResultEnum.VALID.name())) {
                    res = ValidationUtil.validateCF(soggetto.getCfSoggetto());
                }
            } else {
                res = ValidationUtil.validateCF(soggetto.getCfSoggetto());
            }
            if (!res.equals(ValidationResultEnum.VALID.name())) {
                details.put("cf_soggetto", ValidationResultEnum.valueOf(res).getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggetto.getPivaSoggetto())) {
            String res = ValidationUtil.isValidPIva(soggetto.getPivaSoggetto());
            if (!res.equals(ValidationResultEnum.VALID.name())) {
                details.put("partita_iva_soggetto", ValidationResultEnum.valueOf(res).getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggetto.getNome()) && soggetto.getNome().length() > 100) {
            details.put("nome", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getCognome()) && soggetto.getCognome().length() > 100) {
            details.put("cognome", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getIndirizzoSoggetto()) && soggetto.getIndirizzoSoggetto().length() > 100) {
            details.put("indirizzo_soggetto", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getNumCivicoIndirizzo()) && soggetto.getNumCivicoIndirizzo().length() > 30) {
            details.put("num_civico_indirizzo", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getDesEmail())) {
            if (soggetto.getDesEmail().length() > 100) {
                details.put("des_email", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidEmail(soggetto.getDesEmail())) {
                details.put("des_email", ValidationResultEnum.INVALID_EMAIL.getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggetto.getDesPec())) {
            if (soggetto.getDesPec().length() > 100) {
                details.put("des_pec", ValidationResultEnum.INVALID_LENGTH.getDescription());
            } else if (!ValidationUtil.isValidEmail(soggetto.getDesPec())) {
                details.put("des_pec", ValidationResultEnum.INVALID_EMAIL.getDescription());
            }
        }

        if (StringUtils.isNotBlank(soggetto.getNumTelefono()) && soggetto.getNumTelefono().length() > 25) {
            details.put("num_telefono", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getDenProvinciaCCIAA()) && soggetto.getDenProvinciaCCIAA().length() > 20) {
            details.put("den_provincia_cciaa", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getDenNumeroCCIAA()) && soggetto.getDenNumeroCCIAA().length() > 20) {
            details.put("den_numero_cciaa", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getCittaEsteraNascita()) && soggetto.getCittaEsteraNascita().length() > 100) {
            details.put("citta_estera_nascita", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (StringUtils.isNotBlank(soggetto.getCittaEsteraResidenza()) && soggetto.getCittaEsteraResidenza().length() > 100) {
            details.put("citta_estera_residenza", ValidationResultEnum.INVALID_LENGTH.getDescription());
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }

        return error;
    }


    /**
     * Sets dati by codice.
     *
     * @param soggetto     the soggetto
     * @param attoreScriva the attore scriva
     */
    public void setDatiByCodice(SoggettoExtendedDTO soggetto, AttoreScriva attoreScriva) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logBegin(className, methodName);
        try {
            if (soggetto != null) {
                if (soggetto.getTipoSoggetto() != null && soggetto.getTipoSoggetto().getIdTipoSoggetto() == null && soggetto.getTipoSoggetto().getCodiceTipoSoggetto() != null) {
                    soggetto.setTipoSoggetto(this.getTipoSoggettoByCode(soggetto.getTipoSoggetto().getCodiceTipoSoggetto()));
                }
                if (soggetto.getTipoNaturaGiuridica() != null && soggetto.getTipoNaturaGiuridica().getIdTipoNaturaGiuridica() == null && soggetto.getTipoNaturaGiuridica().getCodiceTipoNaturaGiuridica() != null) {
                    soggetto.setTipoNaturaGiuridica(this.getTipoNaturaGiuridicaByCode(soggetto.getTipoNaturaGiuridica().getCodiceTipoNaturaGiuridica()));
                }
                if (soggetto.getComuneNascita() != null && soggetto.getComuneNascita().getIdComune() == null && soggetto.getComuneNascita().getCodIstatComune() != null) {
                    soggetto.setComuneNascita(this.getComuneByCodIstat(soggetto.getComuneNascita().getCodIstatComune()));
                }
                if (soggetto.getComuneResidenza() != null && soggetto.getComuneResidenza().getIdComune() == null && soggetto.getComuneResidenza().getCodIstatComune() != null) {
                    soggetto.setComuneResidenza(this.getComuneByCodIstat(soggetto.getComuneResidenza().getCodIstatComune()));
                }
                if (soggetto.getComuneSedeLegale() != null && soggetto.getComuneSedeLegale().getIdComune() == null && soggetto.getComuneSedeLegale().getCodIstatComune() != null) {
                    soggetto.setComuneSedeLegale(this.getComuneByCodIstat(soggetto.getComuneSedeLegale().getCodIstatComune()));
                }
                if (soggetto.getNazioneNascita() != null && soggetto.getNazioneNascita().getIdNazione() == null && soggetto.getNazioneNascita().getCodIstatNazione() != null) {
                    soggetto.setNazioneNascita(this.getNazioneByCodIstat(soggetto.getNazioneNascita().getCodIstatNazione()));
                }
                if (soggetto.getNazioneResidenza() != null && soggetto.getNazioneResidenza().getIdNazione() == null && soggetto.getNazioneResidenza().getCodIstatNazione() != null) {
                    soggetto.setNazioneResidenza(this.getNazioneByCodIstat(soggetto.getNazioneResidenza().getCodIstatNazione()));
                }
                if (soggetto.getNazioneSedeLegale() != null && soggetto.getNazioneSedeLegale().getIdNazione() == null && soggetto.getNazioneSedeLegale().getCodIstatNazione() != null) {
                    soggetto.setNazioneResidenza(this.getNazioneByCodIstat(soggetto.getNazioneSedeLegale().getCodIstatNazione()));
                }
            }
        } catch (Exception e) {
            logError(className, methodName, e);
        } finally {
            logEnd(className, methodName);
        }
    }
}
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

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotaIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.NoteIstanzaService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Istanze pubblicate service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NoteIstanzaServiceImpl extends BaseApiServiceImpl implements NoteIstanzaService {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private NotaIstanzaDAO notaIstanzaDAO;

    @Autowired
    private FunzionarioDAO funzionarioDAO;

    /**
     * Search note pubblicate list.
     *
     * @param componenteApplicativa the componente applicativa
     * @param idIstanza             the id istanza
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @return the list
     */
    @Override
    public List<NotaPubblicataExtendedDTO> searchNotePubblicate(String componenteApplicativa, Long idIstanza, Integer offset, Integer limit, String sort) {
        logBegin(className);
        return notaIstanzaDAO.searchNotePubblicate(componenteApplicativa, idIstanza, Integer.toString(offset), Integer.toString(limit), sort);
    }

    /**
     * Load nota pubblicata by id nota pubblicata dto.
     *
     * @param idNotaIstanza the id nota istanza
     * @return the nota pubblicata dto
     */
    @Override
    public NotaPubblicataExtendedDTO loadNotaPubblicataById(Long idNotaIstanza) {
        logBegin(className);
        return notaIstanzaDAO.findByPK(idNotaIstanza);
    }

    /**
     * Load nota pubblicata by uid nota pubblicata dto.
     *
     * @param uidNotaIstanza the uid nota istanza
     * @return the nota pubblicata dto
     */
    @Override
    public NotaPubblicataExtendedDTO loadNotaPubblicataByUID(String uidNotaIstanza) {
        logBegin(className);
        return notaIstanzaDAO.findByUID(uidNotaIstanza);
    }

    /**
     * Save nota pubblicata nota pubblicata dto.
     *
     * @param notaPubblicata the nota pubblicata
     * @param attoreScriva   the attore scriva
     * @return the nota pubblicata dto
     */
    @Override
    public NotaPubblicataDTO saveNotaPubblicata(NotaPubblicataDTO notaPubblicata, AttoreScriva attoreScriva) throws DuplicateRecordException {
        logBegin(className);
        try {
            notaPubblicata.setIdFunzionario(this.getIdFunzionarioByCF(attoreScriva.getCodiceFiscale()));
            notaPubblicata.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            notaPubblicata.setIndVisibile(notaPubblicata.getIndVisibile() != null ? notaPubblicata.getIndVisibile() : ComponenteAppEnum.BO.name());
            Long idNotaIstanza = notaIstanzaDAO.saveNotaIstanza(notaPubblicata);
            if (idNotaIstanza == null) {
                return null;
            }
            updateIstanzaPraticaTimestampAttore(notaPubblicata.getIdIstanza(), attoreScriva);
            return this.loadNotaPubblicataById(idNotaIstanza);
        } catch (DuplicateRecordException e) {
            logError(className, e);
            throw new DuplicateRecordException();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update nota pubblicata nota pubblicata dto.
     *
     * @param notaPubblicata the nota pubblicata
     * @param attoreScriva   the attore scriva
     * @return the nota pubblicata dto
     */
    @Override
    public Integer updateNotaPubblicata(NotaPubblicataDTO notaPubblicata, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            notaPubblicata.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
            updateIstanzaPraticaTimestampAttore(notaPubblicata.getIdIstanza(), attoreScriva);
            return notaIstanzaDAO.updateNotaIstanza(notaPubblicata);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete nota pubblicata integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteNotaPubblicata(String gestUID,  AttoreScriva attoreScriva) {
        
        logBegin(className);
        
        NotaPubblicataExtendedDTO nota = notaIstanzaDAO.findByUID(gestUID);
    
        Long idIstanza = null;
        if (nota != null) {
            idIstanza = nota.getIdIstanza();
        }
    
        Integer result=  notaIstanzaDAO.deleteNotaIstanza(gestUID);
    
        if(result >= 0 && idIstanza != null)
        {
            updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
         
        }
    
        return result;
    }

    public Long getIdFunzionarioByCF(String codiceFiscale) {
        logBegin(className);
        List<FunzionarioDTO> funzionarioList = funzionarioDAO.loadFunzionarioByCf(codiceFiscale);
        logEnd(className);
        return funzionarioList != null && !funzionarioList.isEmpty() ? funzionarioList.get(0).getIdFunzionario() : null;
    }

}
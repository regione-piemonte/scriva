/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataExtendedDTO;

import java.util.List;

/**
 * The interface Istanze pubblicate service.
 *
 * @author CSI PIEMONTE
 */
public interface NoteIstanzaService {

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
    List<NotaPubblicataExtendedDTO> searchNotePubblicate(String componenteApplicativa, Long idIstanza, Integer offset, Integer limit, String sort);

    /**
     * Load nota pubblicata by id nota pubblicata dto.
     *
     * @param idNotaIstanza the id nota istanza
     * @return the nota pubblicata dto
     */
    NotaPubblicataExtendedDTO loadNotaPubblicataById(Long idNotaIstanza);

    /**
     * Load nota pubblicata by uid nota pubblicata dto.
     *
     * @param uidNotaIstanza the uid nota istanza
     * @return the nota pubblicata dto
     */
    NotaPubblicataExtendedDTO loadNotaPubblicataByUID(String uidNotaIstanza);

    /**
     * Save nota pubblicata nota pubblicata dto.
     *
     * @param notaPubblicata the nota pubblicata
     * @param attoreScriva   the attore scriva
     * @return the nota pubblicata dto
     * @throws DuplicateRecordException the duplicate record exception
     */
    NotaPubblicataDTO saveNotaPubblicata(NotaPubblicataDTO notaPubblicata, AttoreScriva attoreScriva) throws DuplicateRecordException;

    /**
     * Update nota pubblicata integer.
     *
     * @param notaPubblicata the nota pubblicata
     * @param attoreScriva   the attore scriva
     * @return the integer
     * @throws DuplicateRecordException the duplicate record exception
     */
    Integer updateNotaPubblicata(NotaPubblicataDTO notaPubblicata, AttoreScriva attoreScriva);

    /**
     * Delete nota pubblicata integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteNotaPubblicata(String gestUID,  AttoreScriva attoreScriva);

}
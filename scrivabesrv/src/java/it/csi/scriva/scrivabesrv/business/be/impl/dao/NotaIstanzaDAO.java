/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataExtendedDTO;

import java.util.List;

/**
 * The interface Search Note Pubblicate dao.
 *
 * @author CSI PIEMONTE
 */
public interface NotaIstanzaDAO {

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
    List<NotaPubblicataExtendedDTO> searchNotePubblicate(String componenteApplicativa, Long idIstanza, String offset, String limit, String sort);

    /**
     * Save nota istanza long.
     *
     * @param dto the dto
     * @return the long
     * @throws DuplicateRecordException the duplicate record exception
     */
    Long saveNotaIstanza(NotaPubblicataDTO dto) throws DuplicateRecordException;

    /**
     * Update nota istanza integer.
     *
     * @param dto the dto
     * @return the integer
     */
    Integer updateNotaIstanza(NotaPubblicataDTO dto);

    /**
     * Delete nota istanza integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteNotaIstanza(String gestUID);

    /**
     * Find by pk nota pubblicata dto.
     *
     * @param idNotaIstanza the id nota istanza
     * @return the nota pubblicata dto
     */
    NotaPubblicataExtendedDTO findByPK(Long idNotaIstanza);

    /**
     * Find by uid nota pubblicata extended dto.
     *
     * @param uidNotaIstanza the uid nota istanza
     * @return the nota pubblicata extended dto
     */
    NotaPubblicataExtendedDTO findByUID(String uidNotaIstanza);
}
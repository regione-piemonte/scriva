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

import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneContatoreDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneDTO;

import java.util.List;

/**
 * The interface Osservazione dao.
 *
 * @author CSI PIEMONTE
 */
public interface StatoOsservazioneContatoreDAO {

   
	List<StatoOsservazioneContatoreDTO> loadStatoOsservazioniContatore(String cfAttore);

   


}
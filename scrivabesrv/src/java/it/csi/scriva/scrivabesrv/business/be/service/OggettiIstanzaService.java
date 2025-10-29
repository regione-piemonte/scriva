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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaFiglioDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneGeometrieExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;

import java.util.Date;
import java.util.List;

/**
 * The interface Oggetti istanza service.
 *
 * @author CSI PIEMONTE
 */
public interface OggettiIstanzaService {

    /**
     * Load oggetti istanza list.
     *
     * @return the list
     */
    List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettiIstanza();

    /**
     * Load oggetto istanza by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettoIstanzaByIdIstanza(Long idIstanza);

    /**
     * Load oggetto istanza by id list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettoIstanzaById(Long idOggettoIstanza);

    /**
     * Load oggetto istanza by id list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */

     List<OggettoIstanzaUbicazioneGeometrieExtendedDTO> loadOggettoIstanzaWithGeometriesById(Long idOggettoIstanza);

    /**
     * Load oggetto by uid list.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @return the list
     */
    List<OggettoIstanzaUbicazioneExtendedDTO> loadOggettoIstanzaByUID(String uidOggettoIstanza);

    /**
     * Save oggetto istanza long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @return the long
     */
    Long saveOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva);

    /**
     * Update oggetto istanza integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    Integer updateOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva);

    /**
     * Update oggetto istanza integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @return the integer
     */
    Integer updateOggettoIstanzaWithComuni(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, List<ComuneExtendedDTO> comuniList);
    
    /**
     * Delete oggetto istanza integer.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @return the integer
     */
    Integer deleteOggettoIstanza(String uidOggettoIstanza);

    /**
     * Validate dto error dto.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param isUpdate       the is update
     * @return the error dto
     */
    ErrorDTO validateDTO(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, Boolean isUpdate);

    /**
     * Save ubicazioni oggetto istanza long.
     *
     * @param idOggettoIstanza             the id oggetto istanza
     * @param ubicazioneOggettoIstanzaList the ubicazione oggetto istanza list
     * @param attoreScriva                 the attore scriva
     * @return the long
     */
    Long saveUbicazioniOggettoIstanza(Long idOggettoIstanza, List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioneOggettoIstanzaList, AttoreScriva attoreScriva);

    /**
     * Save ogg natura 2000 long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    Long saveOggNatura2000(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Save ogg area protetta long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    Long saveOggAreaProtetta(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Save ogg vincolo aut long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    Long saveOggVincoloAut(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Save catasto ubicazione long.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     * @return the long
     */
    Long saveCatastoUbicazione(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Update ubicazioni oggetto istanza.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    void updateUbicazioniOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Update ogg natura 2000 integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    void updateOggNatura2000(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Update ogg area protetta integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    void updateOggAreaProtetta(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Update ogg vincolo aut integer.
     *
     * @param oggettoIstanza the oggetto istanza
     * @param attoreScriva   the attore scriva
     * @param now            the now
     */
    void updateOggVincoloAut(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Update catasto ubicazione.
     *
     * @param ubicazioneOggettoIstanza the ubicazione oggetto istanza
     * @param attoreScriva             the attore scriva
     * @param now                      the now
     */
    void updateCatastoUbicazione(UbicazioneOggettoIstanzaExtendedDTO ubicazioneOggettoIstanza, AttoreScriva attoreScriva, Date now);

    /**
     * Copy last info from oggetto.
     *
     * @param idOggettoIstanzaNew the id oggetto istanza new
     * @param idOggetto           the id oggetto
     * @param gestAttoreIns       the gest attore ins
     * @param now                 the now
     */
    void copyLastInfoFromOggetto(Long idOggettoIstanzaNew, Long idOggetto, String gestAttoreIns, Date now);

    /**
     * Copy last geo ref from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    void copyLastGeoRefFromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns);

    /**
     * Copy last ogg natura 2000 from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    void copyLastOggNatura2000FromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns);

    /**
     * Copy last ogg area protetta from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    void copyLastOggAreaProtettaFromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns);

    /**
     * Copy last ogg vincolo aut from oggetto.
     *
     * @param idAdempimento          the id adempimento
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    void copyLastOggVincoloAutFromOggetto(Long idAdempimento, Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns);

    /**
     * Copy last catasto ubi ogg istanza from oggetto.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param now                    the now
     * @param gestAttoreIns          the gest attore ins
     */
    void copyLastCatastoUbiOggIstanzaFromOggetto(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date now, String gestAttoreIns);

    /**
     * Gets oggetto ubicazione list.
     *
     * @param oggettiIstanzaList the oggetti istanza list
     * @return the oggetto ubicazione list
     */
    List<OggettoIstanzaUbicazioneExtendedDTO> getOggettoUbicazioneList(List<OggettoIstanzaExtendedDTO> oggettiIstanzaList);

    /**
     * Gets oggetto istanza geeco.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the oggetto istanza geeco
     */
    OggettoIstanzaGeecoDTO getOggettoIstanzaGeeco(Long idOggettoIstanza);

    /**
     * Get oggetto istanza geeco list.
     *
     * @param idIstanza            the id istanza
     * @param idOggettoIstanzaList the id oggetto istanza list
     * @param idLayerList          the id layer list
     * @param indLivelloEstrazione the ind livello estrazione
     * @return the list
     */
    List<OggettoIstanzaGeecoDTO> getOggettoIstanzaGeeco(Long idIstanza, List<Long> idOggettoIstanzaList, List<Long> idLayerList, Long indLivelloEstrazione);

    /**
     * Update flg geo modificato integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param value            the value
     * @return the integer
     */
    Integer updateFlgGeoModificatoByIdOggIst(Long idOggettoIstanza, boolean value);

    /**
     * Update flg geo modificato by id ist integer.
     *
     * @param idIstanza the id istanza
     * @param value     the value
     * @return the integer
     */
    Integer updateFlgGeoModificatoByIdIst(Long idIstanza, boolean value);

    /**
     * Gets ind livello by id padre.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the ind livello by id padre
     */
    Integer getIndLivelloByIdPadre(Long idOggettoIstanzaPadre);

    /**
     * Save oggetto istanza figlio oggetto istanza figlio dto.
     *
     * @param oggettoIstanzaUbicazione the oggetto istanza ubicazione
     * @param idOggettoIstanzaPadre    the id oggetto istanza padre
     * @param cfAttore                 the cf attore
     * @return the oggetto istanza figlio dto
     */
    OggettoIstanzaFiglioDTO saveOggettoIstanzaFiglio(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione, Long idOggettoIstanzaPadre, String cfAttore);

	void updateUbicazioniOggettoIstanzaNEW(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza,
			AttoreScriva attoreScriva, Date now);

	/**
	 * @param oggettoIstanza
	 * @param attoreScriva
	 * @param now
	 * @param comuniList
	 */
	void updateUbicazioniOggettoIstanzaWithComuni(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza,
			AttoreScriva attoreScriva, Date now, List<ComuneExtendedDTO> comuniList);

}
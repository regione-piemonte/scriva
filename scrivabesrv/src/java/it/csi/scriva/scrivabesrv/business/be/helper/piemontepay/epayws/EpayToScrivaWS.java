/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.epayws;

//import java.it.csi.risca.riscabesrv.business.be.helper.pagopa.ElaborazionePagamentiResultDTO;
//import java.it.csi.risca.riscabesrv.business.be.helper.pagopa.MailException;
//import java.it.csi.risca.riscabesrv.business.be.helper.pagopa.RpPagopaDTO;
//import java.sql.SQLException;



//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

//import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//import it.csi.epay.epaywso.epaywso2enti.types.EsitoInserimentoListaDiCaricoRequest;
//import it.csi.epay.epaywso.epaywso2enti.types.NotificaPagamentoType;
import it.csi.epay.epaywso.epaywso2enti.types.TrasmettiNotifichePagamentoRequest;
//import it.csi.epay.epaywso.types.PosizioneDebitoriaType;
import it.csi.epay.epaywso.types.ResponseType;
import it.csi.epay.epaywso.types.ResultType;
//import it.csi.scriva.scrivabesrv.business.be.exception.DAOException;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitoDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.EntePagopaDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.RegistroElaboraDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.ElaborazionePagamenti;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitiConfigDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitiDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.ElaboraDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.EmailServizioDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.EntePagopaDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.IuvDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.LottoDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.PagopaListaCarIuvDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.RegistroElaboraDAO;
//import it.csi.scriva.scrivabesrv.business.be.impl.dao.RpPagopaDAO;
//import it.csi.scriva.scrivabesrv.dto.AmbitoConfigDTO;
//import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
//import it.csi.scriva.scrivabesrv.dto.EntePagopaDTO;
//import it.csi.scriva.scrivabesrv.dto.ElaboraDTO;
//import it.csi.scriva.scrivabesrv.dto.ElaborazionePagamentiResultDTO;
//import it.csi.scriva.scrivabesrv.dto.EmailServizioDTO;
//import it.csi.scriva.scrivabesrv.dto.EntePagopaDTO;
//import it.csi.scriva.scrivabesrv.dto.IuvDTO;
//import it.csi.scriva.scrivabesrv.dto.LottoDTO;
//import it.csi.scriva.scrivabesrv.dto.PagopaListaCarIuvDTO;
//import it.csi.scriva.scrivabesrv.dto.RegistroElaboraDTO;
//import it.csi.scriva.scrivabesrv.dto.RegistroElaboraExtendedDTO;
//import it.csi.scriva.scrivabesrv.dto.RpPagopaDTO;
//import it.csi.scriva.scrivabesrv.dto.StatoElaborazioneDTO;
//import it.csi.scriva.scrivabesrv.dto.StatoIuvDTO;
//import it.csi.scriva.scrivabesrv.dto.TipoElaboraExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
//import it.csi.scriva.scrivabesrv.util.mail.AttachmentData;
//import it.csi.scriva.scrivabesrv.util.mail.MailException;
//import it.csi.scriva.scrivabesrv.util.mail.MailInfo;
//import it.csi.scriva.scrivabesrv.util.mail.MailManager;

@WebService(name = "EpayToScrivaWS", targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types", serviceName = "EpayToScrivaWS")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class EpayToScrivaWS extends SpringBeanAutowiringSupport implements IEpayToScrivaWS {

	private static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME);

//	private static final String ATTORE_BS = "riscabatchspec";
//	private static final String ATTORE_BO = "riscabatchord";
	private static final String ATTORE_PP = "pagamentipagopa";
//	private static final String FASE = "Conferma - AcquisizioneIUV";
	private static final String FASE_PP = "Acquisizione Pagamenti PagoPA";
//	private static final String COD_FASE_CONFBS_ACQ_IUV = "CONFBS_ACQ_IUV";
//	private static final String COD_FASE_CONFBO_ACQ_IUV = "CONFBO_ACQ_IUV";
	private static final String COD_FASE_ACQ_PAGAMENTI_PAGOPA = "ACQ_PAGAMENTI_PAGOPA";
//	private static final String TIPO_ELAB_BS = "BS";
//	private static final String TIPO_ELAB_PP = "PP";

	private static final String COD_OK_PAGOPA = "000";

//	private static final String PASSO_VERIF_PRESENZA = "VERIF_PRESENZA";
//	private static final String PASSO_VERIF_IN_ESECUZIONE = "VERIF_IN_ESECUZIONE";
//	private static final String PASSO_LOTTO_RISCA_OK = "LOTTO_RISCA_OK";
//	private static final String PASSO_SET_IUV_LISTA_CAR = "SET_IUV_LISTA_CAR";
//	private static final String PASSO_SET_INSERT_IUV = "INSERT_IUV";
//	private static final String PASSO_SET_RICEVUTO_LOTTO = "SET_RICEVUTO_LOTTO";
//	private static final String PASSO_LOTTO_ANOMALO = "LOTTO_ANOMALO";
//	private static final String PASSO_INVIO_MAIL_SERVIZIO = "INVIO_MAIL_SERVIZIO";

//	private static final String PASSO_INIZIO_STEP = "INIZIO_STEP";
//	private static final String PASSO_FINE_STEP = "FINE_STEP";
//	private static final String PASSO_INS_ELABORA = "INS_ELABORA";
//	private static final String PASSO_INS_PAGAM_IN_WORK = "INS_PAGAM_IN_WORK";
//	private static final String PASSO_CODAVVISO_PRESENTE = "CODAVVISO_PRESENTE";
//
//	private static final String STATO_ELAB_ATTESAIUV = "ATTESAIUV";
//	private static final String STATO_ELAB_IUVOK = "IUVOK";
//	private static final String STATO_ELAB_ATTESAPDF = "ATTESAPDF";
//	private static final String STATO_ELAB_PDFPRONTI = "PDFPRONTI";
//	private static final String STATO_ELAB_PDFOK = "PDFOK";
//	private static final String STATO_ELAB_MAILOK = "MAILOK";
//	private static final String STATO_TERMIN_KO = "TERMIN_KO";
//	private static final String STATO_ELAB_PAGPGPA_ON = "PAGPGPA_ON";
//	private static final String STATO_ELAB_PAGPGPA_KO = "PAGPGPA_KO";
//	private static final String STATO_ELAB_PAGPGPA_OK = "PAGPGPA_OK";
//
//	private static final String AMBITO_CONFIG_CHIAVE_MAIL_MITT = "SRVAPP.MittenteSegnalazioniServizioApplicativo";
//	private static final String AMBITO_CONFIG_CHIAVE_MAIL_DEST = "SRVAPP.DestinatarioServizioApplicativo";
//	private static final String AMBITO_CONFIG_CHIAVE_NOME_ALLEGATO = "SRVAPP.NomeAllegatoServizioApplicativo";
//
//	private static final String DATE_FORMAT = "yyyy-MM-dd";
//	private static final String DATE_FORMAT_MAIL = "dd/MM/yyyy";
//	private static final String HOUR_FORMAT_MAIL = "HH:mm:ss";
//
//	private static final String ERROR_E01_1 = "ERRORE E01.1: sono presenti piu' richieste di elaborazioni di Bollettazione. Impossibile procedere ";
//	private static final String ERROR_E01_2 = "ERRORE E01.2: non sono presenti richieste di elaborazioni di Bollettazione.";
//
//	private static final String ERROR_E00_1_PP = "ERRORE E00.1: i dati ricevuti non sono relativi ad ambiti gestiti da RISCA.";
//	private static final String ERROR_E01_3_PP = "ERRORE E01.3: e' gia' in corso un'elaborazione di Acquisizione Pagamenti da PagoPA per l'ambito.";
//	private static final String ERROR_E01_4_PP = "ERRORE E01.4: caricamento prenotazione Acquisizione Pagamenti da PagoPA fallito.";
//
//	private static final String COD_EMAIL_E_CONFBS_ACQ_IUV_I_BS = "E_CONFBS_ACQ_IUV_I";
//	private static final String COD_EMAIL_E_CONFBS_ACQ_IUV_F_BS = "E_CONFBS_ACQ_IUV_F";
//
//	private static final String COD_EMAIL_E_CONFBS_ACQ_IUV_I_BO = "E_CONFBO_ACQ_IUV_I";
//	private static final String COD_EMAIL_E_CONFBS_ACQ_IUV_F_BO = "E_CONFBO_ACQ_IUV_F";
//
//	private static final String COD_EMAIL_E_ACQ_PAGAM_PAGOPA = "E_ACQ_PAGAM_PAGOPA";

//	@Autowired
//	private RegistroElaboraDAO registroElaboraDAO;

//	@Autowired
//	private AmbitoDAO ambitiDAO;

//	@Autowired
//	private AmbitiConfigDAO ambitiConfigDAO;

//	@Autowired
//	private ElaboraDAO elaboraDAO;

//	@Autowired
//	private EntePagopaDAO entePagopaDAO;

//	@Autowired
//	private LottoDAO lottoDAO;
//
//	@Autowired
//	private PagopaListaCarIuvDAO pagopaListaCarIuvDAO;
//
//	@Autowired
//	private IuvDAO iuvDAO;
//
//	@Autowired
//	private RpPagopaDAO rpPagopaDAO;
//
//	@Autowired
//	private EmailServizioDAO emailServizioDAO;

//	@Autowired
//	private MailManager mailManager;

//	@Autowired
//	private ElaborazionePagamenti elaborazionePagamenti;

//	private ElaboraDTO elabora;
//	private String idAmbito;
//	private Long idElabora;
//	private String nomeLotto;
	private Date dataAcquisizione;
	private String attore;
    private String codFase;

//	@WebResult(name = "EPaywsoServiceResponse", targetNamespace = "http://www.csi.it/epay/epaywso/types", partName = "parameters")
//	@WebMethod(operationName = "EsitoInserimentoListaDiCarico", action = "http://www.csi.it/epay/epaywso/service/EsitoInserimentoListaDiCarico")
//	public ResponseType EsitoInserimentoListaDiCarico(
//			@WebParam(partName = "parameters", name = "EsitoInserimentoListaDiCaricoRequest", targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types") EsitoInserimentoListaDiCaricoRequest parameters)
//			throws Exception {
//		LOGGER.debug("[EpayToRiscaWS::esitoInserimentoListaDiCarico] BEGIN");
//
//		String codiceVersamento = parameters.getTestataEsito().getCodiceVersamento();
//		String cfEnteCreditore = parameters.getTestataEsito().getCFEnteCreditore();
//		String codEsitoAcquisizione = parameters.getResult().getCodice();
//		String descEsitoAcquisizione = parameters.getResult().getMessaggio();
//		nomeLotto = parameters.getTestataEsito().getIdMessaggio();
//		// Annoto la data e ora di acquisizione del lotto (per la mail)
//		dataAcquisizione = new Date();
//
//		EntePagopaDTO ente = entePagopaDAO.loadEntePagopaByCodVersamentoEnteCreditore(codiceVersamento, cfEnteCreditore,
//				true);
//
//		// Ottengo id_ambito
//		idAmbito = "" + ente.getIdAmbito();
//
//		// Cerco una elaborazione in stato ATTESAIUV
//		boolean ok = verificaRichiestaElaborazione(idAmbito);
//		if (ok) {
//			boolean bollettazioneSpeciale = elabora.getTipoElabora().getCodTipoElabora().equals(TIPO_ELAB_BS) ? true
//					: false;
//			attore = bollettazioneSpeciale ? ATTORE_BS : ATTORE_BO;
//			codFase = bollettazioneSpeciale ? COD_FASE_CONFBS_ACQ_IUV : COD_FASE_CONFBO_ACQ_IUV;
//
//			// Verifico che non ci sia gia' un'elaborazione in corso
//			ok = verificaElaborazioneInCorso(idAmbito);
//			if (ok) {
//				// Verifica che il lotto ricevuto sia di RISCA
//				List<LottoDTO> lotti = lottoDAO.loadLottoInviatoByName(idElabora, nomeLotto);
//				if (lotti != null && lotti.size() > 0) {
//
//					logStep(idElabora, PASSO_LOTTO_RISCA_OK, 0,
//							FASE + " - Lotto Ricevuto e' di RISCA - Lotto: " + nomeLotto);
//
//					// Ricavare il numero di lotti attesi
//					List<LottoDTO> listaLottiAttesi = lottoDAO.findLottiAttesi(idElabora);
//					int lottiAttesi = listaLottiAttesi.size();
//					List<Long> idLottiAttesi = (List<Long>) listaLottiAttesi.stream().map(LottoDTO::getIdLotto)
//							.collect(Collectors.toList());
//
//					if (!codEsitoAcquisizione.equals("000")) {
//						// Acquisizione del lotto fallita, registro codice e edescrizione ma proseguo a
//						// verificare l'esito per ogni posizione debitoria
//						lottoDAO.updateFlgRicevutoElaboratoByNomeLotto(idElabora, nomeLotto, 1, 0, codEsitoAcquisizione,
//								descEsitoAcquisizione);
//					}
//
//					int numPosizioniDebitorieRicevute = 0;
//					List<PosizioneDebitoriaType> posizioniDebitorie = parameters.getEsitoInserimento()
//							.getElencoPosizioniDebitorieInserite().getPosizioneDebitoriaInserita();
//
//					boolean lottoOk = true;
//					boolean napOk = true;
//					for (int i = 0; i < posizioniDebitorie.size(); i++) {
//
//						PosizioneDebitoriaType posDeb = posizioniDebitorie.get(i);
//						if (!posDeb.getCodiceEsito().equalsIgnoreCase(COD_OK_PAGOPA)) {
//							lottoOk = false;
//							napOk = false;
//						}
//
//						// settare nella RISCA_R_PAGOPA_LISTA_CAR_IUV lo IUV,
//						// il codice_avviso e l'esito
//						ok = aggiornaEsitoListaCarIuv(posDeb);
//						if (ok) {
//							if (napOk) {
//								// inserire un record nella tabella RISCA_T_IUV
//								ok = saveIuv(codiceVersamento, posDeb);
//								if (ok) {
//									numPosizioniDebitorieRicevute++;
//								} else {
//									terminaElabConErrore("Errore in acquisizione del lotto " + nomeLotto
//											+ ". Fallito inserimento record in RISCA_T_IUV");
//									break;
//								}
//							} else {
//								logStep(idElabora, PASSO_SET_INSERT_IUV, 1,
//										FASE + " - ricevuto un esito negativo dello IUV per l'elaborazione: "
//												+ idElabora + "NAP: " + posDeb.getIdPosizioneDebitoria()
//												+ " ESITO IUV: " + posDeb.getCodiceEsito() + " "
//												+ posDeb.getDescrizioneEsito());
//							}
//						} else {
//							terminaElabConErrore("Errore in acquisizione del lotto " + nomeLotto
//									+ ". Fallito aggiornamento record in RISCA_R_PAGOPA_LISTA_CAR_IUV");
//							break;
//						}
//
//					}
//
//					if (ok) {
//						// Aggiornare la tabella RISCA_T_LOTTO
//						int flgRicevuto = 1;
//						int flgElaborato = lottoOk ? 1 : 0;
//						Integer ret = lottoDAO.updateFlgRicevutoElaboratoByNomeLotto(idElabora, nomeLotto, flgRicevuto,
//								flgElaborato, codEsitoAcquisizione, descEsitoAcquisizione);
//						if (ret != null && ret > 0) {
//							logStep(idElabora, PASSO_SET_RICEVUTO_LOTTO, 0, FASE
//									+ " - Aggiorna Flag Ricevuto Lotto OK : " + idElabora + " Lotto: " + nomeLotto);
//
//							// Ricavare il numero di lotti ricevuti
//							int lottiRicevuti = lottoDAO.findLottiRicevuti(idElabora, idLottiAttesi).size();
//
//							if (lottiAttesi == lottiRicevuti) {
//								int lottiAnomali = lottoDAO.findLottiAnomali(idElabora, idLottiAttesi).size();
//								if (lottiAnomali > 0) {
//									logStep(idElabora, PASSO_LOTTO_ANOMALO, 0, FASE
//											+ " - Esistono lotti non elaborati correttamente (errore su alcuni NAP) per l'elaborazione con idElabora: "
//											+ idElabora);
//								} else {
//									// Tutti i lotti di una elaborazione sono stati acquisiti senza errori su tutti
//									// i NAP
//									aggiornaElabIuvOK();
//								}
//							}
//							// Se non sono uguali, significa che dobbiamo attendere notifiche
//							// di altri lotti. Il servizio termina con l'invio della mail di
//							// riepilogo
//
//							try {
//								invioMailLottoAcquisito(
//										bollettazioneSpeciale ? COD_EMAIL_E_CONFBS_ACQ_IUV_I_BS
//												: COD_EMAIL_E_CONFBS_ACQ_IUV_I_BO,
//										lottiRicevuti, numPosizioniDebitorieRicevute);
//								if (lottiAttesi == lottiRicevuti) {
//									// Se siamo alla fine dell'acquisizione dei lotti invio anche la mai con oggetto
//									// FINE
//									invioMailLottoAcquisito(
//											bollettazioneSpeciale ? COD_EMAIL_E_CONFBS_ACQ_IUV_F_BS
//													: COD_EMAIL_E_CONFBS_ACQ_IUV_F_BO,
//											lottiRicevuti, numPosizioniDebitorieRicevute);
//								}
//							} catch (MailException e) {
//								LOGGER.error("[EpayToRiscaWS::" + FASE + "::InvioMail] Errore invio mail: "
//										+ e.getMessage());
//								logStep(idElabora, PASSO_INVIO_MAIL_SERVIZIO, 1,
//										FASE + " - InvioMail - Errore:  " + e.getMessage());
//							}
//
//						} else {
//							logStep(idElabora, PASSO_SET_RICEVUTO_LOTTO, 1, FASE
//									+ " - Aggiorna Flag Ricevuto Lotto KO : " + idElabora + " Lotto: " + nomeLotto);
//							terminaElabConErrore("Errore in acquisizione del lotto " + nomeLotto
//									+ ". Fallito aggiornamento record in RISCA_T_LOTTO");
//						}
//					}
//				} else {
//					logStep(idElabora, PASSO_LOTTO_RISCA_OK, 1,
//							FASE + " - Lotto Ricevuto NON e' di RISCA - Lotto: " + nomeLotto);
//					invioMailErrore("Errore in acquisizione del lotto " + nomeLotto + ". Il lotto non è di RISCA. ",
//							null, null);
//				}
//			}
//		}
//
//		/*
//		 * Restituisco sempre esito ok, da analisi non sono previsti errori di ritorno a
//		 * pagopa gli errori sono gestiti sul nostro db
//		 */
//		ResponseType response = new ResponseType();
//		ResultType result = new ResultType();
//		result.setCodice(COD_OK_PAGOPA);
//		response.setResult(result);
//
//		LOGGER.debug("[EpayToRiscaWS::esitoInserimentoListaDiCarico] END");
//		return response;
//	}

	@WebResult(name = "EPaywsoServiceResponse", targetNamespace = "http://www.csi.it/epay/epaywso/types", partName = "parameters")
	@WebMethod(operationName = "TrasmettiNotifichePagamento", action = "http://www.csi.it/epay/epaywso/service/TrasmettiNotifichePagamento")
	public ResponseType TrasmettiNotifichePagamento(
			@WebParam(partName = "parameters", name = "TrasmettiNotifichePagamentoRequest", targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types") TrasmettiNotifichePagamentoRequest parameters) {
		LOGGER.debug("[EpayToScrivaWS::trasmettiNotifichePagamento] BEGIN");
		// Annoto la data e ora di acquisizione dei pagamenti (per la mail)
		dataAcquisizione = new Date();
		codFase = COD_FASE_ACQ_PAGAMENTI_PAGOPA;
		attore = ATTORE_PP;
		
		try {
			String codiceVersamento = parameters.getTestata().getCodiceVersamento();
			String cfEnteCreditore = parameters.getTestata().getCFEnteCreditore();

//			EntePagopaDTO ente = entePagopaDAO.loadEntePagopaByCodVersamentoEnteCreditore(codiceVersamento,
//					cfEnteCreditore, false);

//			if (ente == null) {
//				LOGGER.error("[EpayToRiscaWS::trasmettiNotifichePagamento] " + ERROR_E00_1_PP);
//			} else {
//				// Ottengo id_ambito
//				idAmbito = "" + ente.getIdAmbito();
//
//				// Insert su tabella Elabora
////				elabora = insertElaborazionePP(ente.getIdAmbito());
//				if (elabora.getIdElabora() == null) {
//					LOGGER.error("[EpayToRiscaWS::trasmettiNotifichePagamento] " + ERROR_E01_4_PP);
//				} else {
//					idElabora = elabora.getIdElabora();
//					// logStep(idElabora, PASSO_INIZIO_STEP, 0, FASE_PP + " - Step 1 - Inizio");
////					logStep(idElabora, PASSO_INS_ELABORA, 0,
////							COD_FASE_ACQ_PAGAMENTI_PAGOPA + " - Step 1 - Inserimento Prenotazione OK");
//
////					logStep(idElabora, PASSO_INIZIO_STEP, 0, FASE_PP + " - Step 2 - Inizio");
//					List<NotificaPagamentoType> listNotifichePagoPa = parameters.getCorpoNotifichePagamento()
//							.getElencoNotifichePagamento().getNotificaPagamento();
//
//					for (NotificaPagamentoType notificaPagamento : listNotifichePagoPa) {
////						insertRpPagopa(notificaPagamento);
//					}
////					logStep(idElabora, PASSO_FINE_STEP, 0, FASE_PP + " - Step 2 - Fine");
////
////					logStep(idElabora, PASSO_FINE_STEP, 0, FASE_PP + " - Step 3 - Inizio");
////					List<RpPagopaDTO> listaPagopa = rpPagopaDAO.loadRpPagopaByElabora(idElabora);
////					if (listaPagopa.size() == 0) {
////						logStep(idElabora, PASSO_CODAVVISO_PRESENTE, 1, FASE_PP
////								+ " - Step 3 - Estrazione Pagamenti da Working KO per l'elaborazione: " + idElabora);
////						terminaElabConErrorePP();
////					} else {
////						ElaborazionePagamentiResultDTO elabResult = elaborazionePagamenti
////								.elaboraPagamentiPagopa(listaPagopa, idElabora);
////
////						aggiornaElabPagopaOK();
////
////						try {
////							invioMailPagamElaborati(COD_EMAIL_E_ACQ_PAGAM_PAGOPA, elabResult);
////						} catch (MailException e) {
////							LOGGER.error(
////									"[EpayToRiscaWS::" + FASE_PP + "::InvioMail] Errore invio mail: " + e.getMessage());
////							logStep(idElabora, PASSO_INVIO_MAIL_SERVIZIO, 1,
////									FASE + " - InvioMail - Errore:  " + e.getMessage());
////						}
////					}
//				}
//
//			}
		} catch (Exception e) {
			LOGGER.error("[EpayToScrivaWS::" + FASE_PP + "] Eccezione generica: " + e.getMessage());
		}

		/*
		 * Restituisco sempre esito ok, da analisi non sono previsti errori di ritorno a
		 * pagopa gli errori sono gestiti sul nostro db
		 */
		ResponseType response = new ResponseType();
		ResultType result = new ResultType();
		result.setCodice(COD_OK_PAGOPA);
		response.setResult(result);

		LOGGER.debug("[EpayToScrivaWS::trasmettiNotifichePagamento] END");
		return response;
	}

//	private void insertRpPagopa(NotificaPagamentoType notificaPagamento) {
//		RpPagopaDTO pagopaDto = new RpPagopaDTO();
//		try {
//			pagopaDto.setIdElabora(idElabora);
//			pagopaDto.setPosizDebitoria(notificaPagamento.getIdPosizioneDebitoria());
//			pagopaDto.setAnno(notificaPagamento.getAnnoDiRiferimento());
//			pagopaDto.setIuv(notificaPagamento.getIUV());
//			pagopaDto.setImportoPagato(notificaPagamento.getImportoPagato());
//			pagopaDto.setDataScadenza(notificaPagamento.getDataScadenza().toGregorianCalendar().getTime());
//			pagopaDto.setCausale(notificaPagamento.getDescrizioneCausaleVersamento());
//			pagopaDto.setDataEsitoPagamento(notificaPagamento.getDataEsitoPagamento().toGregorianCalendar().getTime());
//			String cognRsocVersante = null;
//			String nomeVersante = null;
//			if (notificaPagamento.getSoggettoDebitore().getPersonaFisica() != null) {
//				cognRsocVersante = notificaPagamento.getSoggettoDebitore().getPersonaFisica().getCognome();
//				nomeVersante = notificaPagamento.getSoggettoDebitore().getPersonaFisica().getNome();
//			} else if (notificaPagamento.getSoggettoDebitore().getPersonaGiuridica() != null) {
//				cognRsocVersante = notificaPagamento.getSoggettoDebitore().getPersonaGiuridica().getRagioneSociale();
//			}
//			pagopaDto.setCognRsocDebitore(cognRsocVersante);
//			pagopaDto.setNomeDebitore(nomeVersante);
//			pagopaDto.setCfPiDebitore(notificaPagamento.getSoggettoDebitore().getIdentificativoUnivocoFiscale());
//			pagopaDto.setImportoTransitato(notificaPagamento.getDatiTransazionePSP().getImportoTransato());
//			pagopaDto.setImportoCommissioni(notificaPagamento.getDatiTransazionePSP().getImportoCommissioni());
//			pagopaDto.setCodiceAvviso(notificaPagamento.getCodiceAvviso());
//			pagopaDto.setNote(notificaPagamento.getNote());
//
//			rpPagopaDAO.saveRpPagopaDTO(pagopaDto);
//			logStep(idElabora, PASSO_INS_PAGAM_IN_WORK, 0,
//					FASE_PP + " - Step 2 - Inserimento Pagamenti in Working OK per l'elaborazione: " + idElabora
//							+ " - IUV: " + notificaPagamento.getIUV());
//		} catch (DAOException e) {
//			LOGGER.error("[EpayToRiscaWS::trasmettiNotifichePagamento] Error inserimento risca_w_rp_pagopa: "
//					+ e.getMessage());
//			logStep(idElabora, PASSO_INS_PAGAM_IN_WORK, 1,
//					FASE_PP + " - Step 2 - Inserimento Pagamenti in Working KO per l'elaborazione: " + idElabora
//							+ " - IUV: " + notificaPagamento.getIUV());
//		}
//	}
//
//	private ElaboraDTO insertElaborazionePP(Long idAmbito) {
//		ElaboraDTO elaboraDto = new ElaboraDTO();
//		try {
//			AmbitoDTO ambito = new AmbitoDTO();
//			ambito.setIdAmbito(idAmbito);
//			elaboraDto.setAmbito(ambito);
//			elaboraDto.setDataRichiesta(new Date());
//			elaboraDto.setGestAttoreIns(ATTORE_PP);
//			elaboraDto.setGestAttoreUpd(ATTORE_PP);
//			StatoElaborazioneDTO statoElab = new StatoElaborazioneDTO();
//			statoElab.setCodStatoElabora(STATO_ELAB_PAGPGPA_ON);
//			elaboraDto.setStatoElabora(statoElab);
//			TipoElaboraExtendedDTO tipoElab = new TipoElaboraExtendedDTO();
//			tipoElab.setCodTipoElabora(TIPO_ELAB_PP);
//			elaboraDto.setTipoElabora(tipoElab);
//			// Questa elaborazione non ha parametri ma occorre settare comunque un array
//			// vuoto
//			elaboraDto.setParametri(new ArrayList<>());
//			elaboraDto = elaboraDAO.saveElabora(elaboraDto);
//		} catch (Exception e) {
//			LOGGER.error("[EpayToRiscaWS::trasmettiNotifichePagamento] Errore inserimento Elaborazione.");
//		}
//		return elaboraDto;
//	}
//
//	private boolean aggiornaEsitoListaCarIuv(PosizioneDebitoriaType posDeb) throws DAOException {
//		Integer res = pagopaListaCarIuvDAO.updateEsitoListaCarIuvByNap(posDeb.getCodiceEsito(),
//				posDeb.getDescrizioneEsito(), posDeb.getCodiceAvviso(), posDeb.getIUV(),
//				posDeb.getIdPosizioneDebitoria());
//		if (res != null) {
//			logStep(idElabora, PASSO_SET_IUV_LISTA_CAR, 0, FASE + " - Aggiornamento IUV ed Esito OK - Elab: "
//					+ idElabora + " NAP: " + posDeb.getIdPosizioneDebitoria() + " IUV: " + posDeb.getIUV());
//			return true;
//		} else {
//			logStep(idElabora, PASSO_SET_IUV_LISTA_CAR, 1, FASE + " - Aggiornamento IUV ed Esito KO - Elab: "
//					+ idElabora + " NAP: " + posDeb.getIdPosizioneDebitoria() + " IUV: " + posDeb.getIUV());
//			return false;
//		}
//	}
//
//	private boolean saveIuv(String codiceVersamento, PosizioneDebitoriaType posDeb) {
//		// Leggo listaCarIuv per recuperare l'importo
//		PagopaListaCarIuvDTO listaCarIuvDto = pagopaListaCarIuvDAO
//				.loadPagopaListaCarIuvByNap(posDeb.getIdPosizioneDebitoria());
//		if (listaCarIuvDto != null) {
//			try {
//				IuvDTO dto = new IuvDTO();
//				dto.setNap(posDeb.getIdPosizioneDebitoria());
//				StatoIuvDTO statoIuv = new StatoIuvDTO();
//				statoIuv.setIdStatoIuv(Long.valueOf(6));
//				dto.setStatoIuv(statoIuv);
//				dto.setIuv(posDeb.getIUV());
//				dto.setCodiceAvviso(posDeb.getCodiceAvviso());
//				dto.setImporto(listaCarIuvDto.getImporto());
//				dto.setCodiceVersamento(codiceVersamento);
//				dto.setGestAttoreIns(attore);
//				dto.setGestAttoreUpd(attore);
//				dto = iuvDAO.saveIuv(dto);
//				if (dto.getIdIuv() != null) {
//					logStep(idElabora, PASSO_SET_INSERT_IUV, 0, FASE + " - Inserimento IUV OK per l'elaborazione: "
//							+ idElabora + " NAP: " + posDeb.getIdPosizioneDebitoria() + " IUV: " + posDeb.getIUV());
//					return true;
//				} else {
//					logStep(idElabora, PASSO_SET_INSERT_IUV, 1, FASE + " - Inserimento IUV KO per l'elaborazione: "
//							+ idElabora + "NAP: " + posDeb.getIdPosizioneDebitoria() + " IUV: " + posDeb.getIUV());
//					return false;
//				}
//			} catch (Exception e) {
//				logStep(idElabora, PASSO_SET_INSERT_IUV, 1, FASE + " - Inserimento IUV KO per l'elaborazione: "
//						+ idElabora + "NAP: " + posDeb.getIdPosizioneDebitoria() + " IUV: " + posDeb.getIUV());
//				return false;
//			}
//		}
//		return false;
//	}
//
//	private boolean verificaRichiestaElaborazione(String idAmbito) {
//		// List<String> codTipoElabora = Arrays.asList(TIPO_ELAB_BS);
//		List<String> codStatoElabora = Arrays.asList(STATO_ELAB_ATTESAIUV);
//		// Devo cercare una elaborazione in stato ATTESAIUV senza guardare il tipo
//		// (BO/BS). Deve essere garantito che non ci siano elaborazioni BO e BS
//		// contemporaneamente in corso
//		List<ElaboraDTO> elaborazioni = elaboraDAO.loadElabora(idAmbito, null, codStatoElabora, null, null, null, null,
//				null, null);
//		if (elaborazioni.size() == 0) {
//			LOGGER.error("[EpayToRiscaWS::esitoInserimentoListaDiCarico] " + ERROR_E01_2);
//			// logStep(idElabora, PASSO_VERIF_PRESENZA, 1, FASE + " - Verifica Presenza
//			// Elaborazione KO");
//			return false;
//		} else if (elaborazioni.size() > 1) {
//			String elabs = "[";
//			for (ElaboraDTO elab : elaborazioni) {
//				elabs += elab.getIdElabora() + ", ";
//			}
//			elabs = elabs.substring(0, elabs.length() - 2) + "]";
//			LOGGER.error("[EpayToRiscaWS::esitoInserimentoListaDiCarico] " + ERROR_E01_1 + elabs);
//			// logStep(idElabora, PASSO_VERIF_PRESENZA, 1, FASE + " - Verifica Presenza
//			// Elaborazione KO");
//			return false;
//		}
//		elabora = elaborazioni.get(0);
//		idElabora = elabora.getIdElabora();
//		logStep(idElabora, PASSO_VERIF_PRESENZA, 0, FASE + " - Verifica Presenza Elaborazione OK");
//		return true;
//	}
//
//	private boolean verificaElaborazioneInCorso(String idAmbito) {
//		// List<String> codTipoElabora = Arrays.asList(TIPO_ELAB_BS);
//		// Devo cercare una elaborazione in stato ATTESAIUV senza guardare il tipo
//		// (BO/BS). Deve essere garantito che non ci siano elaborazioni BO e BS
//		// contemporaneamente in corso in stato ATTESAIUV
//		List<String> codStatoElabora = Arrays.asList(STATO_ELAB_IUVOK, STATO_ELAB_ATTESAPDF, STATO_ELAB_PDFPRONTI,
//				STATO_ELAB_PDFOK, STATO_ELAB_MAILOK);
//		List<ElaboraDTO> elaborazioni = elaboraDAO.loadElabora(idAmbito, null, codStatoElabora, null, null, null, null,
//				null, null);
//		if (elaborazioni.size() == 0) {
//			logStep(idElabora, PASSO_VERIF_IN_ESECUZIONE, 0,
//					FASE + " - Verifica presenza elaborazioni uguali in corso OK");
//			return true;
//		} else {
//			String listaElaborazioni = "";
//			for (ElaboraDTO elab : elaborazioni) {
//				listaElaborazioni += elab.getIdElabora() + " ";
//			}
//			logStep(idElabora, PASSO_VERIF_IN_ESECUZIONE, 1,
//					FASE + " - Verifica presenza elaborazioni uguali in corso KO - Procedura in esecuzione con ID: "
//							+ listaElaborazioni);
//			return false;
//		}
//	}
//
//	private boolean verificaElaborazioneInCorsoPP(String idAmbito) {
//		List<String> codTipoElabora = Arrays.asList(TIPO_ELAB_PP);
//		List<String> codStatoElabora = Arrays.asList(STATO_ELAB_PAGPGPA_ON);
//		List<ElaboraDTO> elaborazioni = elaboraDAO.loadElabora(idAmbito, codTipoElabora, codStatoElabora, null, null,
//				null, null, null, null);
//		if (elaborazioni.size() == 0) {
//			return true;
//		} else {
//			LOGGER.error("[EpayToRiscaWS::trasmettiNotifichePagamento] " + ERROR_E01_3_PP);
//			return false;
//		}
//	}

//	private void logStep(Long idElabora, String codPasso, int esito, String note) {
//		RegistroElaboraDTO registroElabora = new RegistroElaboraDTO();
//		registroElabora.setIdElabora(idElabora);
//		registroElabora.setCodPassoElabora(codPasso);
//		registroElabora.setFlgEsitoElabora(esito);
//		registroElabora.setNotaElabora(note);
//		registroElabora.setGestAttoreIns(attore);
//		registroElabora.setGestAttoreUpd(attore);
//		registroElabora.setCodFaseElabora(codFase);
//		registroElaboraDAO.saveRegistroElabora(registroElabora);
//		LOGGER.debug(note);
//	}
//
//	private void terminaElabConErrore(String messaggio) {
//		StatoElaborazioneDTO statoErr = new StatoElaborazioneDTO();
//		statoErr.setCodStatoElabora(STATO_TERMIN_KO);
//		elabora.setStatoElabora(statoErr);
//		elabora.setGestAttoreUpd(attore);
//		elaboraDAO.updateElabora(elabora);
//
//		// invio mail
//		try {
//			invioMailErrore(messaggio, null, null);
//		} catch (MailException e) {
//			LOGGER.error("[EpayToRiscaWS::" + FASE + "::InvioMail] Errore invio mail: " + e.getMessage());
//			logStep(idElabora, PASSO_INVIO_MAIL_SERVIZIO, 1, FASE + " - InvioMail - Errore:  " + e.getMessage());
//		}
//	}
//
//	private void terminaElabConErrorePP() {
//		StatoElaborazioneDTO statoErr = new StatoElaborazioneDTO();
//		statoErr.setCodStatoElabora(STATO_ELAB_PAGPGPA_KO);
//		elabora.setStatoElabora(statoErr);
//		elabora.setGestAttoreUpd(attore);
//		elaboraDAO.updateElabora(elabora);
//	}
//
//	private void aggiornaElabIuvOK() {
//		StatoElaborazioneDTO stato = new StatoElaborazioneDTO();
//		stato.setCodStatoElabora(STATO_ELAB_IUVOK);
//		elabora.setStatoElabora(stato);
//		elabora.setGestAttoreUpd(attore);
//		elaboraDAO.updateElabora(elabora);
//	}
//
//	private void aggiornaElabPagopaOK() {
//		StatoElaborazioneDTO stato = new StatoElaborazioneDTO();
//		stato.setCodStatoElabora(STATO_ELAB_PAGPGPA_OK);
//		elabora.setStatoElabora(stato);
//		elabora.setGestAttoreUpd(ATTORE_PP);
//		elaboraDAO.updateElabora(elabora);
//	}
//
//	private String getOggettoMail(Date dataAcquisizione, String code) {
//		EmailServizioDTO emailDto = emailServizioDAO.loadEmailServizioByCodEmail(code);
//		String oggetto = emailDto.getOggettoEmailServizio();
//		oggetto = StringUtils.replace(oggetto, "[TIPO_ELABORA]", elabora.getTipoElabora().getDesTipoElabora());
//		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_MAIL);
//		SimpleDateFormat df2 = new SimpleDateFormat(HOUR_FORMAT_MAIL);
//		oggetto = StringUtils.replace(oggetto, "[ID_ELABORA]", "" + elabora.getIdElabora());
//		oggetto = StringUtils.replace(oggetto, "[DATA_RICHIESTA]", df.format(dataAcquisizione));
//		oggetto = StringUtils.replace(oggetto, "[ORA_RICHIESTA]", df2.format(dataAcquisizione));
//		return oggetto;
//	}
//
//	private int invioMailErrore(String messaggio, Integer numLottiAcquisiti, Integer numPosizioniDebitorieRicevute)
//			throws MailException {
//		boolean bollettazioneSpeciale = elabora.getTipoElabora().getCodTipoElabora().equals(TIPO_ELAB_BS) ? true
//				: false;
//		String oggetto = getOggettoMail(dataAcquisizione,
//				bollettazioneSpeciale ? COD_EMAIL_E_CONFBS_ACQ_IUV_I_BS : COD_EMAIL_E_CONFBS_ACQ_IUV_I_BO);
//		String msg = FASE + " - InvioMail ";
//		if (numLottiAcquisiti != null && numPosizioniDebitorieRicevute != null) {
//			msg += "- Statistiche dati elaborati - Numero lotti acquisiti: " + numLottiAcquisiti
//					+ " - Numero posizioni debitorie acquisite: " + numPosizioniDebitorieRicevute;
//		} else {
//			msg += "- Si e' verificato un errore nell'elaborazione del lotto: " + nomeLotto + ", per idElabora: "
//					+ idElabora;
//		}
//		return invioMail(FASE, oggetto, messaggio, msg);
//	}
//
//	private int invioMailLottoAcquisito(String code, Integer numLottiAcquisiti, Integer numPosizioniDebitorieRicevute)
//			throws MailException {
//		String oggetto = getOggettoMail(dataAcquisizione, code);
//
//		StringBuilder testo = new StringBuilder();
//		testo.append(
//				"Nel file di testo in allegato si riportano, per l'elaborazione in oggetto, i passi eseguiti ed il loro esito. \n");
//		testo.append("Lotto ricevuto: " + nomeLotto);
//		testo.append("\n");
//		testo.append("Numero IUV ricevuti: " + numPosizioniDebitorieRicevute);
//		testo.append("\n");
//
//		String msg = FASE + " - InvioMail ";
//		if (numLottiAcquisiti != null && numPosizioniDebitorieRicevute != null) {
//			msg += "- Statistiche dati elaborati - Numero lotti acquisiti: " + numLottiAcquisiti
//					+ " - Numero posizioni debitorie acquisite: " + numPosizioniDebitorieRicevute;
//		} else {
//			msg += "- Si e' verificato un errore nell'elaborazione del lotto: " + nomeLotto + ", per idElabora: "
//					+ idElabora;
//		}
//
//		return invioMail(FASE, oggetto, testo.toString(), msg);
//	}
//
//	private int invioMailPagamElaborati(String code, ElaborazionePagamentiResultDTO elabResult) throws MailException {
//		String oggetto = getOggettoMail(dataAcquisizione, code);
//
//		StringBuilder testo = new StringBuilder();
//		testo.append(
//				"Nel file di testo in allegato si riportano, per l'elaborazione in oggetto, i passi eseguiti ed il loro esito. \n");
//		testo.append("Pagamenti letti: " + elabResult.getNumPagamLetti());
//		testo.append("\n");
//		testo.append("Pagamenti caricati: " + elabResult.getNumPagamCaric());
//		testo.append("\n");
//		testo.append("Pagamenti scartati: " + elabResult.getNumPagamScart());
//		testo.append("\n");
//
//		String logMessage = FASE_PP + " - InvioMail - Statistiche dati elaborati - Numero Pagamenti Letti: "
//				+ elabResult.getNumPagamLetti() + " - Numero Pagamenti Caricati: " + elabResult.getNumPagamCaric()
//				+ " - Numero Pagamenti Scartati:  " + elabResult.getNumPagamScart();
//
//		return invioMail(FASE_PP, oggetto, testo.toString(), logMessage);
//	}
//
//	private int invioMail(String fase, String oggetto, String testo, String logMessage) throws MailException {
//		LOGGER.debug("[EpayToRiscaWS::" + fase + "::InvioMail] BEGIN");
//		int stepStatus = 0;
//
//		AmbitoDTO ambito = ambitiDAO.loadAmbitoByIdAmbito(Long.parseLong(idAmbito));
//		String mailDest = null;
//		String mailMitt = null;
//		List<AmbitoConfigDTO> ambitoConfig = null;
//		try {
//			ambitoConfig = ambitiConfigDAO.loadAmbitiConfigByCodeAndKey(ambito.getCodAmbito(),
//					AMBITO_CONFIG_CHIAVE_MAIL_DEST);
//		} catch (Exception e) {
//			LOGGER.error("[EpayToRiscaWS::" + fase + "::InvioMail] loadAmbitiConfigByCodeAndKey - errore per ambito  "
//					+ ambito.getCodAmbito() + " e chiave " + AMBITO_CONFIG_CHIAVE_MAIL_DEST);
//		}
//		if (ambitoConfig.size() > 0) {
//			mailDest = ambitoConfig.get(0).getValore();
//			try {
//				ambitoConfig = ambitiConfigDAO.loadAmbitiConfigByCodeAndKey(ambito.getCodAmbito(),
//						AMBITO_CONFIG_CHIAVE_MAIL_MITT);
//			} catch (SQLException e) {
//				LOGGER.error(
//						"[EpayToRiscaWS::" + fase + "::InvioMail] loadAmbitiConfigByCodeAndKey - errore per ambito  "
//								+ ambito.getCodAmbito() + " e chiave " + AMBITO_CONFIG_CHIAVE_MAIL_MITT);
//			}
//			if (ambitoConfig.size() > 0) {
//				mailMitt = ambitoConfig.get(0).getValore();
//				LOGGER.debug("[EpayToRiscaWS::" + fase + "::InvioMail] mailDest = " + mailDest);
//				LOGGER.debug("[EpayToRiscaWS::" + fase + "::InvioMail] mailMitt = " + mailMitt);
//				logStep(idElabora, PASSO_INVIO_MAIL_SERVIZIO, 0, logMessage);
//
//			} else {
//				logStep(idElabora, PASSO_INVIO_MAIL_SERVIZIO, 1, fase
//						+ " - InvioMail - La mail del mittente per le comunicazioni al Servizio Applicativo non e' stata configurata per l'ambito  "
//						+ ambito.getCodAmbito());
//			}
//		} else {
//			logStep(idElabora, PASSO_INVIO_MAIL_SERVIZIO, 1, FASE
//					+ " - InvioMail - La mail del destinatario del Servizio Applicativo non e' stata configurata per l'ambito "
//					+ ambito.getCodAmbito());
//		}
//
//		List<RegistroElaboraExtendedDTO> registroElaborazioni = registroElaboraDAO
//				.loadRegistroElaboraByElaboraAndAmbito("" + idElabora, idAmbito, null, codFase);
//		LOGGER.debug("[EpayToRiscaWS::" + fase + "::InvioMail] invioMail - trovate " + registroElaborazioni.size()
//				+ " registrazioni");
//
//		try {
//			ambitoConfig = ambitiConfigDAO.loadAmbitiConfigByCodeAndKey(ambito.getCodAmbito(),
//					AMBITO_CONFIG_CHIAVE_NOME_ALLEGATO);
//		} catch (SQLException e) {
//			LOGGER.error("[EpayToRiscaWS::" + fase + "::InvioMail] loadAmbitiConfigByCodeAndKey - errore per ambito  "
//					+ ambito.getCodAmbito() + " e chiave " + AMBITO_CONFIG_CHIAVE_NOME_ALLEGATO);
//		}
//		String nomeAllegato = ambitoConfig != null ? ambitoConfig.get(0).getValore()
//				: "RISCA-LogElaborazione-{dataElaborazione}.txt";
//
//		MailInfo mailInfo = prepareMail(oggetto, testo, registroElaborazioni, mailDest, mailMitt, nomeAllegato);
//		mailManager.sendMail(mailInfo);
//
//		return stepStatus;
//	}
//
//	private MailInfo prepareMail(String oggetto, String testo, List<RegistroElaboraExtendedDTO> registroElaborazioni,
//			String mailDest, String mailMitt, String nomeAllegato) {
//		MailInfo mailInfo = new MailInfo();
//		mailInfo.setDestinatario(mailDest);
//		mailInfo.setMittente(mailMitt);
//		mailInfo.setHost(mailManager.getMailHost());
//		mailInfo.setPort(mailManager.getMailPort());
//		mailInfo.setOggetto(oggetto);
//		mailInfo.setTesto(testo);
//
//		StringBuffer txtElencoElab = new StringBuffer();
//		for (RegistroElaboraExtendedDTO elab : registroElaborazioni) {
//			txtElencoElab.append(elab.getDesPassoElabora());
//			txtElencoElab.append(" - Esito: ");
//			txtElencoElab.append(elab.getFlgEsitoElabora() == 0 ? "OK" : "Errore");
//			txtElencoElab.append(" - ");
//			txtElencoElab.append(elab.getNotaElabora());
//			txtElencoElab.append("\n");
//		}
//
//		AttachmentData attachment = new AttachmentData();
//		SimpleDateFormat df2 = new SimpleDateFormat(DATE_FORMAT);
//		String filename = nomeAllegato.replace("{dataElaborazione}", df2.format(elabora.getDataRichiesta()));
//		attachment.setFilename(filename);
//		attachment.setData(txtElencoElab.toString().getBytes());
//		attachment.setMimeType("text/plain");
//
//		mailInfo.setAttachments(new AttachmentData[] { attachment });
//
//		return mailInfo;
//	}

}

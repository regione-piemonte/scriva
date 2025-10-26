/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * The type Exception handler.
 */
@Provider
@Component
public class ExceptionHandler implements ExceptionMapper<RuntimeException>{

	private static Logger logger = Logger.getLogger(ExceptionHandler.class);

	//fixme da fare refactor
	@Override
	public Response toResponse(RuntimeException e) {
		logger.debug("[ExceptionHandler::toResponse] Error :" + e);
//		logger.error(e.getMessage(), e);
		//		if(e instanceof BusinessException || e instanceof CreatePdfException ) {
		//			ErrorDTO error = new ErrorDTO("403", "errore_business", e.getMessage(), null, null);
		//			return Response.ok(error).status(Status.FORBIDDEN).build();
		//		}
		//		else if( e instanceof UserNotFoundException) {
		//			ErrorDTO error = new ErrorDTO("401", "errore_business", e.getMessage(), null, null);
		//			return Response.ok(error).status(Status.UNAUTHORIZED).build();
		//		}
		//		else if(e instanceof ValidationException) {
		//			HashMap<String, String> detail = new HashMap<String, String>();
		////			detail.putAll(((ValidationException) e).getDetail());
		//			ErrorDTO error = new ErrorDTO("403", "errore_validazione", null, detail, null);
		//			return Response.ok(error).status(Status.FORBIDDEN).build();
		//		}

		String errorMsg = "Si è verificato un errore nella richiesta. Si prega di riprovare successivamente. Se l'errore persiste contattare l'amministratore";
		ErrorDTO error = new ErrorDTO("500", "errore_interno", errorMsg, null, null);
		return Response.serverError().entity(error).build();
	}
}
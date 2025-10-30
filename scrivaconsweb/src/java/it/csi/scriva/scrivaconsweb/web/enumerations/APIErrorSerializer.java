/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.web.enumerations;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class APIErrorSerializer extends StdSerializer<APIError> {

	public APIErrorSerializer() {
		super(APIError.class);
	}

	public APIErrorSerializer(Class t) {
		super(t);
	}

	@Override
	public void serialize(APIError value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("id");
		generator.writeString(value.name());
		generator.writeFieldName("code");
		generator.writeString(value.getCode());		
		generator.writeFieldName("description");
		generator.writeString(value.getDescription());
		generator.writeEndObject();
	}
}

/* *****************************************************
 * Copyright Regione Piemonte - 2025
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************/

CREATE ROLE scriva              WITH NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN NOREPLICATION NOBYPASSRLS CONNECTION LIMIT -1 PASSWORD 'mypass';
CREATE ROLE scriva_rw           WITH NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN NOREPLICATION NOBYPASSRLS CONNECTION LIMIT -1 PASSWORD 'mypass';

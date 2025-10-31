/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * Enum personalizzato per la gestione dei filtri d'ordinamento.
 */
export enum ScrivaSortTypes {
  crescente = 'asc',
  decrescente = 'desc',
  nessuno = 'none',
}

/**
 * Enum che mappa i tipi di dati richiesti per il servizio HttpClient di Angular.
 */
export enum ResponseTypes {
  json = 'json',
  arraybuffer = 'arraybuffer',
  blob = 'blob',
  text = 'text',
}

/**
 * Enum che mappa i tipi di dati ritornati dal servizio HttpClient di Angular.
 */
export enum BlobTypes {
  pdf = 'application/pdf',
  png = 'image/png',
  jpg = 'image/jpeg',
  tiff = 'image/tiff',
  tif = 'image/tiff',
  bmp = 'image/bmp',
  p7m = 'application/pkcs7-mime',
}

/**
 * Enumeratore per la gestione della funzione di apertura di un file tramite browser.
 */
export enum ScrivaOpenFileMethods {
  download = 'download',
  openInTab = 'openInTab',
}

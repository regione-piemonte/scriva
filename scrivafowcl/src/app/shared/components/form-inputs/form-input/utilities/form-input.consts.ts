/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { SCOrientamento } from './form-input.enums';

/**
 * Interfaccia dedicata al mapping delle costanti.
 * Permette di tipizzare alcune specifiche proprietà.
 */
interface IFormInputConsts {
  HORIZONTAL: string;
  VERTICAL: string;
}

/**
 * Oggetto di costanti contenente una serie di costanti comuni.
 */
export const FormInputConsts: IFormInputConsts = {
  HORIZONTAL: SCOrientamento.orizzontale,
  VERTICAL: SCOrientamento.verticale,
};

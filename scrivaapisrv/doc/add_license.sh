#!/bin/bash
LICENSE="/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */"

find src/java -type f -name "*.java" | while read file; do
    # Controlla se il file contiene già la licenza
    if ! grep -q "LICENSE_START" "$file"; then
        # Inserisci la licenza all'inizio del file
        (echo "$LICENSE"; cat "$file") > "$file.tmp" && mv "$file.tmp" "$file"
    fi
done

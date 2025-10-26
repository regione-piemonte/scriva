$license = @"
/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
"@

Get-ChildItem -Path "src/java" -Recurse -Filter *.java | ForEach-Object {
    $file = $_.FullName
    $content = Get-Content $file -Raw
    if ($content -notmatch "LICENSE_START") {
        Set-Content $file -Value ($license + "`r`n" + $content)
    }
}

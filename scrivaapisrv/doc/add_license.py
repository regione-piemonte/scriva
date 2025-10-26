import os

LICENSE = """/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
"""

for root, dirs, files in os.walk("src/java"):
    for name in files:
        if name.endswith(".java"):
            filepath = os.path.join(root, name)
            with open(filepath, "r", encoding="utf-8") as f:
                content = f.read()
            if "LICENSE_START" not in content:
                with open(filepath, "w", encoding="utf-8") as f:
                    f.write(LICENSE + "\n" + content)

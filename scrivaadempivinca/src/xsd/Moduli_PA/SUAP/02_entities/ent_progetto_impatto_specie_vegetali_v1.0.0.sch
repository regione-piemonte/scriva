<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 26 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_specie_vegetali" prefix="episv"/>

    <sch:pattern id="progetto_impatto_specie_vegetali_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_specie_vegetali" context="episv:progetto_impatto_specie_vegetali">

            <sch:assert id="progetto_impatto_specie_vegetali-a" test="not(episv:impatto_specie_vegetali_non_pertinente = 'true' or episv:impatto_specie_vegetali_non_pertinente = '1') or not(episv:dati_progetto_impatto_specie_vegetali)">
				se impatto_specie_vegetali_non_pertinente = true allora dati_progetto_impatto_specie_vegetali non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_vegetali-b" test="not(episv:impatto_specie_vegetali_non_pertinente = 'false' or episv:impatto_specie_vegetali_non_pertinente = '0') or episv:dati_progetto_impatto_specie_vegetali">
				se impatto_specie_vegetali_non_pertinente = false allora dati_progetto_impatto_specie_vegetali deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_vegetali-c" test="not(.//episv:taglio_specie_vegetali = 'true' or .//episv:taglio_specie_vegetali = '1') or normalize-space(.//episv:desc_taglio_specie_vegetali) != ''">
				se taglio_specie_vegetali = true allora desc_taglio_specie_vegetali non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_vegetali-d" test="not(.//episv:taglio_specie_vegetali = 'false' or .//episv:taglio_specie_vegetali = '0') or normalize-space(.//episv:desc_taglio_specie_vegetali) = ''">
				se taglio_specie_vegetali = false allora desc_taglio_specie_vegetali deve essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_vegetali-e" test="not(.//episv:piantumazione_specie_vegetali = 'true' or .//episv:piantumazione_specie_vegetali = '1') or normalize-space(.//episv:desc_piantumazione_specie_vegetali) != ''">
				se piantumazione_specie_vegetali = true allora desc_piantumazione_specie_vegetali non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_vegetali-f" test="not(.//episv:piantumazione_specie_vegetali = 'false' or .//episv:piantumazione_specie_vegetali = '0') or normalize-space(.//episv:desc_piantumazione_specie_vegetali) = ''">
				se piantumazione_specie_vegetali = false allora desc_piantumazione_specie_vegetali deve essere vuoto
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_specie_vegetali" abstract="false" is-a="progetto_impatto_specie_vegetali_ab">
        <sch:param name="progetto_impatto_specie_vegetali" value="episv:progetto_impatto_specie_vegetali"/>
    </sch:pattern>
</sch:schema>

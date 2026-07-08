<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_manifestazioni" prefix="epiman"/>

    <sch:pattern id="progetto_impatto_manifestazioni_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_manifestazioni" context="epiman:progetto_impatto_manifestazioni">

            <sch:assert id="progetto_impatto_manifestazioni-a" test="not(epiman:impatto_non_pertinente = 'true' or epiman:impatto_non_pertinente  = '1') or not(epiman:dati_progetto_impatto_manifestazioni)">
				se impatto_non_pertinente = true allora dati_progetto_impatto_manifestazioni non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_manifestazioni-b" test="not(epiman:impatto_non_pertinente = 'false' or epiman:impatto_non_pertinente  = '0') or epiman:dati_progetto_impatto_manifestazioni">
				se impatto_non_pertinente = false allora dati_progetto_impatto_manifestazioni deve essere compilato
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_manifestazioni" abstract="false" is-a="progetto_impatto_manifestazioni_ab">
        <sch:param name="progetto_impatto_manifestazioni" value="epiman:progetto_impatto_manifestazioni"/>
    </sch:pattern>
</sch:schema>

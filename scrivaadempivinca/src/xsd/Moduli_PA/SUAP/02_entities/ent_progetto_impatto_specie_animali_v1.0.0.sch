<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 28 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_specie_animali" prefix="episa"/>

    <sch:pattern id="progetto_impatto_specie_animali_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_specie_animali" context="episa:progetto_impatto_specie_animali">

            <sch:assert id="progetto_impatto_specie_animali-a" test="not(episa:impatto_specie_animali_non_pertinente = 'true' or episa:impatto_specie_animali_non_pertinente  = '1') or not(episa:dati_progetto_impatto_specie_animali)">
				se impatto_specie_animali_non_pertinente = true allora dati_progetto_impatto_specie_animali non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_animali-b" test="not(episa:impatto_specie_animali_non_pertinente = 'false' or episa:impatto_specie_animali_non_pertinente  = '0') or episa:dati_progetto_impatto_specie_animali">
				se impatto_specie_animali_non_pertinente = false allora dati_progetto_impatto_specie_animali deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_animali-c" test="not(.//episa:interventi_allevamento_specie_animali = 'true' or .//episa:interventi_allevamento_specie_animali = '1') or normalize-space(.//episa:desc_interventi_allevamento_specie_animali) != ''">
				se interventi_allevamento_specie_animali = true allora desc_interventi_allevamento_specie_animali non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_specie_animali-d" test="not(.//episa:interventi_allevamento_specie_animali = 'false' or .//episa:interventi_allevamento_specie_animali = '0') or normalize-space(.//episa:desc_interventi_allevamento_specie_animali) = ''">
				se interventi_allevamento_specie_animali = false allora desc_interventi_allevamento_specie_animali deve essere vuoto
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_specie_animali" abstract="false" is-a="progetto_impatto_specie_animali_ab">
        <sch:param name="progetto_impatto_specie_animali" value="episa:progetto_impatto_specie_animali"/>
    </sch:pattern>
</sch:schema>

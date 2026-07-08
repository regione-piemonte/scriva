<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 28 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_interventi_ripetuti" prefix="epiirip"/>

    <sch:pattern id="progetto_impatto_interventi_ripetuti_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_interventi_ripetuti" context="epiirip:progetto_impatto_interventi_ripetuti">

            <sch:assert id="progetto_impatto_interventi_ripetuti-a" test="not(epiirip:impatto_non_pertinente = 'true' or epiirip:impatto_non_pertinente  = '1') or not(epiirip:dati_progetto_impatto_interventi_ripetuti)">
				se impatto_non_pertinente = true allora dati_progetto_impatto_interventi_ripetuti non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_interventi_ripetuti-b" test="not(epiirip:impatto_non_pertinente = 'false' or epiirip:impatto_non_pertinente  = '0') or epiirip:dati_progetto_impatto_interventi_ripetuti">
				se impatto_non_pertinente = false allora dati_progetto_impatto_interventi_ripetuti deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_interventi_ripetuti-c" test="not(.//epiirip:frequenza_periodica = 'true' or .//epiirip:frequenza_periodica = '1') or normalize-space(.//epiirip:desc_periodicita) != ''">
				se frequenza_periodica = true allora desc_periodicita non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_interventi_ripetuti-d" test="not(.//epiirip:frequenza_periodica = 'false' or .//epiirip:frequenza_periodica = '0') or normalize-space(.//epiirip:desc_periodicita) = ''">
				se frequenza_periodica = false allora desc_periodicita deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_interventi_ripetuti-e" test="not(.//epiirip:provvedimento_precedente = 'true' or .//epiirip:provvedimento_precedente = '1') or normalize-space(.//epiirip:desc_provvedimento_precedente) != ''">
				se provvedimento_precedente = true allora desc_provvedimento_precedente non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_interventi_ripetuti-f" test="not(.//epiirip:provvedimento_precedente = 'false' or .//epiirip:provvedimento_precedente = '0') or normalize-space(.//epiirip:desc_provvedimento_precedente) = ''">
				se provvedimento_precedente = false allora desc_provvedimento_precedente deve essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_interventi_ripetuti-g" test="not(.//epiirip:provvedimento_precedente = 'true' or .//epiirip:provvedimento_precedente = '1') or normalize-space(.//epiirip:desc_motivo_ripetitivita) != ''">
				se provvedimento_precedente = true allora desc_motivo_ripetitivita non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_interventi_ripetuti-h" test="not(.//epiirip:provvedimento_precedente = 'false' or .//epiirip:provvedimento_precedente = '0') or normalize-space(.//epiirip:desc_motivo_ripetitivita) = ''">
				se provvedimento_precedente = false allora desc_motivo_ripetitivita deve essere vuoto
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_interventi_ripetuti" abstract="false" is-a="progetto_impatto_interventi_ripetuti_ab">
        <sch:param name="progetto_impatto_interventi_ripetuti" value="epiirip:progetto_impatto_interventi_ripetuti"/>
    </sch:pattern>
</sch:schema>

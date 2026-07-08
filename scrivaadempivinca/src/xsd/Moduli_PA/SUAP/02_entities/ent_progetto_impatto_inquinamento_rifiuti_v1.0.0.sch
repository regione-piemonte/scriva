<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_inquinamento_rifiuti" prefix="epiir"/>

    <sch:pattern id="progetto_impatto_inquinamento_rifiuti_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_inquinamento_rifiuti" context="epiir:progetto_impatto_inquinamento_rifiuti">

            <sch:assert id="progetto_impatto_inquinamento_rifiuti-a" test="not(epiir:impatto_non_pertinente = 'true' or epiir:impatto_non_pertinente  = '1') or not(epiir:fonte_inquinamento)">
				se impatto_non_pertinente = true allora fonte_inquinamento non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_inquinamento_rifiuti-b" test="not(epiir:impatto_non_pertinente = 'false' or epiir:impatto_non_pertinente  = '0') or epiir:fonte_inquinamento">
				se impatto_non_pertinente = false allora fonte_inquinamento deve essere compilato
			</sch:assert>

            <sch:assert id="progetto_impatto_inquinamento_rifiuti-c" test="not(.//epiir:fonte_inquinamento[epiir:tipo_fonte_inquinamento = 'Altre fonti' and normalize-space(.//epiir:desc_inquinamento) = ''])">
				se tipo_fonte_inquinamento = Altre fonti allora desc_inquinamento non puo essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_inquinamento_rifiuti-d" test="count(.//epiir:tipo_fonte_inquinamento[text()='Inquinamento luminoso']) &lt;=1">
				tipo_fonte_inquinamento = Inquinamento luminoso non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_inquinamento_rifiuti-e" test="count(.//epiir:tipo_fonte_inquinamento[text()='Inquinamento sonoro']) &lt;=1">
				tipo_fonte_inquinamento = Inquinamento sonoro non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_inquinamento_rifiuti-f" test="count(.//epiir:tipo_fonte_inquinamento[text()='Inquinamento chimico']) &lt;=1">
				tipo_fonte_inquinamento = Inquinamento chimico non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_inquinamento_rifiuti-g" test="count(.//epiir:tipo_fonte_inquinamento[text()='Inquinamento rifiuti']) &lt;=1">
				tipo_fonte_inquinamento = Inquinamento rifiuti non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_inquinamento_rifiuti-h" test="count(.//epiir:tipo_fonte_inquinamento[text()='Altre fonti']) &lt;=1">
				tipo_fonte_inquinamento = Altre fonti non puo occorrere piu di una volta
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_inquinamento_rifiuti" abstract="false" is-a="progetto_impatto_inquinamento_rifiuti_ab">
        <sch:param name="progetto_impatto_inquinamento_rifiuti" value="epiir:progetto_impatto_inquinamento_rifiuti"/>
    </sch:pattern>
</sch:schema>

<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 ottobre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/sito_natura_2000_interferito" prefix="esni"/>

    <sch:pattern id="sito_natura_2000_interferito_ab" abstract="true">

        <sch:rule id="rule_sito_natura_2000_interferito" context="esni:sito_natura_2000_interferito">

            <sch:let name="keysSitoNatura" value="document('../01_vocabularies/voc_siti_natura_2000.xml')//Row"/>

            <sch:let name="sito_natura_2000_interferito" value="normalize-space(esni:codice_amministrativo_nome_sito_natura_2000_interferito)"/>

            <sch:assert id="sito_natura_2000_interferito_ab-ass_sito_natura_2000_interferito_cl_check" test="
                            count($keysSitoNatura[
                            normalize-space(Value[@ColumnRef='codice_amministrativo_nome']/SimpleValue) = $sito_natura_2000_interferito
                            ]) = 1">

                codice_amministrativo_nome_sito_natura_2000_interferito non esiste (<sch:value-of select="$sito_natura_2000_interferito"/>)
            </sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="sito_natura_2000_interferito" abstract="false" is-a="sito_natura_2000_interferito_ab">
        <sch:param name="sito_natura_2000_interferito" value="esni:sito_natura_2000_interferito"/>
    </sch:pattern>
</sch:schema>

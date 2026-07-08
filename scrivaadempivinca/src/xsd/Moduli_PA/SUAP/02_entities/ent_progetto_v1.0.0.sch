<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 ottobre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto" prefix="ep"/>

    <sch:pattern id="progetto_ab" abstract="true">

        <sch:rule id="rule_progetto" context="ep:progetto">

            <sch:let name="keysVincoloProgetto" value="document('../01_vocabularies/voc_vincolo_progetto.xml')//Row"/>

            <sch:let name="dvp" value="normalize-space(ep:desc_vincolo_progetto)"/>

            <sch:assert id="progetto_ab-ass_dvp_cl_check" test="
                            count($keysVincoloProgetto[
                            normalize-space(Value[@ColumnRef='descrizione_vincolo_progetto']/SimpleValue) = $dvp
                            ]) = 1">

                descrizione_vincolo_progetto non esiste (<sch:value-of select="$dvp"/>)
            </sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto" abstract="false" is-a="progetto_ab">
        <sch:param name="progetto" value="ep:progetto"/>
    </sch:pattern>
</sch:schema>

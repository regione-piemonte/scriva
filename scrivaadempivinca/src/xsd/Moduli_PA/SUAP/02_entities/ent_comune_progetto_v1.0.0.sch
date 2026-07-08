<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 ottobre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/comune_progetto" prefix="ecp"/>

    <sch:pattern id="comune_progetto_ab" abstract="true">

        <sch:rule id="rule_comune_progetto" context="ecp:comune_progetto">

            <sch:let name="keysComuniProgetto" value="document('../01_vocabularies/voc_comuni_italiani.xml')//Row"/>

            <sch:let name="codice_istat_comune" value="normalize-space(ecp:codice_istat_comune)"/>

            <sch:assert id="comune_progetto_ab-ass_comune_progetto_cl_check" test="
                            count($keysComuniProgetto[
                            normalize-space(Value[@ColumnRef='codice_istat']/SimpleValue) = $codice_istat_comune
                            ]) = 1">

                codice_istat_comune non esiste (<sch:value-of select="$codice_istat_comune"/>)
            </sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="comune_progetto" abstract="false" is-a="comune_progetto_ab">
        <sch:param name="comune_progetto" value="ecp:comune_progetto"/>
    </sch:pattern>
</sch:schema>

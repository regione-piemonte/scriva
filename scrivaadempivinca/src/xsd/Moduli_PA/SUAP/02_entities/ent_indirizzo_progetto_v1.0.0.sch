<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 ottobre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/indirizzo_progetto" prefix="eip"/>

    <sch:pattern id="indirizzo_progetto_ab" abstract="true">

        <sch:rule id="rule_indirizzo_progetto" context="eip:indirizzo_progetto">

            <sch:let name="keysComuni" value="document('../01_vocabularies/voc_comuni_italiani.xml')//Row"/>
            <sch:let name="keysDUG" value="document('../01_vocabularies/voc_dug.xml')//Row"/>

            <sch:let name="comune" value="normalize-space(eip:codice_istat_comune)"/>
            <sch:let name="dug" value="normalize-space(eip:dug)"/>

            <sch:assert id="indirizzo_progetto_ab-ass_comune_cl_check" test="
                            count($keysComuni[
                            normalize-space(Value[@ColumnRef='codice_istat']/SimpleValue) = $comune
                            ]) = 1">

               codice_istat_comune non esiste (<sch:value-of select="$comune"/>)
            </sch:assert>

            <sch:assert id="indirizzo_progetto_ab-ass_dug_cl_check" test="$dug='' or
                                                                          count($keysDUG[
                                                                          normalize-space(Value[@ColumnRef='denominazione']/SimpleValue) = $dug
                                                                          ]) = 1">

                DUG non esiste (<sch:value-of select="$dug"/>)
            </sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="indirizzo_progetto" abstract="false" is-a="indirizzo_progetto_ab">
        <sch:param name="indirizzo_progetto" value="eip:indirizzo_progetto"/>
    </sch:pattern>
</sch:schema>

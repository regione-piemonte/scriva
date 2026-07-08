<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 29 ottobre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/area_protetta" prefix="eap"/>

    <sch:pattern id="area_protetta_ab" abstract="true">

        <sch:rule id="rule_area_protetta" context="eap:area_protetta">

            <sch:let name="keysAreeProtette" value="document('../01_vocabularies/voc_aree_protette.xml')//Row"/>

            <sch:let name="ap" value="normalize-space(eap:nome_area_protetta)"/>

            <sch:assert id="area_protetta_ab-ass_ap_cl_check" test="
                            count($keysAreeProtette[
                            normalize-space(Value[@ColumnRef='nome_area_protetta']/SimpleValue) = $ap
                            ]) = 1">

                nome_area_protetta non esiste (<sch:value-of select="$ap"/>)
            </sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="area_protetta" abstract="false" is-a="area_protetta_ab">
        <sch:param name="area_protetta" value="eap:area_protetta"/>
    </sch:pattern>
</sch:schema>

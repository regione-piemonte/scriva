<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 24 ottobre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/ruolo_dichiarante" prefix="erd"/>

    <sch:pattern id="ruolo_dichiarante_ab" abstract="true">

        <sch:rule id="rule_ruolo_dichiarante" context="erd:ruolo_dichiarante">

            <sch:let name="keysRuoliDichiarante" value="document('../01_vocabularies/voc_ruoli_dichiarante.xml')//Row"/>

            <sch:let name="des_ruolo_dichiarante" value="normalize-space(erd:des_ruolo_dichiarante)"/>

            <sch:assert id="ruolo_dichiarante_ab-ass_des_ruolo_dichiarante_cl_check" test="
                            count($keysRuoliDichiarante[
                            normalize-space(Value[@ColumnRef='des_ruolo_dichiarante']/SimpleValue) = $des_ruolo_dichiarante
                            ]) = 1">

                des_ruolo_dichiarante non esiste (<sch:value-of select="$des_ruolo_dichiarante"/>)
            </sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="ruolo_dichiarante" abstract="false" is-a="ruolo_dichiarante_ab">
        <sch:param name="ruolo_dichiarante" value="erd:ruolo_dichiarante"/>
    </sch:pattern>
</sch:schema>

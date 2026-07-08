<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 26 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_suolo_habitat" prefix="epish"/>

    <sch:pattern id="progetto_impatto_suolo_habitat_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_suolo_habitat" context="epish:progetto_impatto_suolo_habitat">

            <sch:assert id="progetto_impatto_suolo_habitat_ab-a" test="not(epish:impatto_suolo_habitat_non_pertinente = 'true' or epish:impatto_suolo_habitat_non_pertinente = '1') or not(epish:dati_progetto_impatto_suolo_habitat)">
				se impatto_suolo_habitat_non_pertinente = true allora dati_progetto_impatto_suolo_habitat non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-b" test="not(epish:impatto_suolo_habitat_non_pertinente = 'false' or epish:impatto_suolo_habitat_non_pertinente = '0') or epish:dati_progetto_impatto_suolo_habitat">
				se impatto_suolo_habitat_non_pertinente = false allora dati_progetto_impatto_suolo_habitat deve essere compilato
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-c" test="not(.//epish:trasformazione_uso_suolo = 'true' or .//epish:trasformazione_uso_suolo = '1') or normalize-space(.//epish:tipo_trasformazione) != ''">
				se trasformazione_uso_suolo = true allora tipo_trasformazione non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-d" test="not(.//epish:trasformazione_uso_suolo = 'false' or .//epish:trasformazione_uso_suolo = '0') or normalize-space(.//epish:tipo_trasformazione) = ''">
				se trasformazione_uso_suolo = false allora tipo_trasformazione deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-e" test="not(.//epish:trasformazione_uso_suolo = 'true' or .//epish:trasformazione_uso_suolo = '1') or normalize-space(.//epish:desc_tipo_trasformazione) != ''">
				se trasformazione_uso_suolo = true allora desc_tipo_trasformazione non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-f" test="not(.//epish:trasformazione_uso_suolo = 'false' or .//epish:trasformazione_uso_suolo = '0') or normalize-space(.//epish:desc_tipo_trasformazione) = ''">
				se trasformazione_uso_suolo = false allora desc_tipo_trasformazione deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-g" test="not(.//epish:movimenti_terra = 'true' or .//epish:movimenti_terra = '1') or normalize-space(.//epish:desc_movimenti_terra) != ''">
				se movimenti_terra = true allora desc_movimenti_terra non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-h" test="not(.//epish:movimenti_terra = 'false' or .//epish:movimenti_terra = '0') or normalize-space(.//epish:desc_movimenti_terra) = ''">
				se movimenti_terra = false allora desc_movimenti_terra deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-i" test="not(.//epish:livellamento_spietramento = 'true' or .//epish:livellamento_spietramento = '1') or normalize-space(.//epish:desc_livellamento_spietramento) != ''">
				se livellamento_spietramento = true allora desc_livellamento_spietramento non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-l" test="not(.//epish:livellamento_spietramento = 'false' or .//epish:livellamento_spietramento = '0') or normalize-space(.//epish:desc_livellamento_spietramento) = ''">
				se livellamento_spietramento = false allora desc_livellamento_spietramento deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-m" test="not(.//epish:aree_cantiere_stoccaggio = 'true' or .//epish:aree_cantiere_stoccaggio = '1') or normalize-space(.//epish:desc_aree_cantiere_stoccaggio) != ''">
				se aree_cantiere_stoccaggio = true allora desc_aree_cantiere_stoccaggio non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-n" test="not(.//epish:aree_cantiere_stoccaggio = 'false' or .//epish:aree_cantiere_stoccaggio = '0') or normalize-space(.//epish:desc_aree_cantiere_stoccaggio) = ''">
				se aree_cantiere_stoccaggio = false allora desc_aree_cantiere_stoccaggio deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-o" test="not(.//epish:piste_accesso = 'true' or .//epish:piste_accesso = '1') or normalize-space(.//epish:desc_piste_accesso) != ''">
				se piste_accesso = true allora desc_piste_accesso non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-p" test="not(.//epish:piste_accesso = 'false' or .//epish:piste_accesso = '0') or normalize-space(.//epish:desc_piste_accesso) = ''">
				se piste_accesso = false allora desc_piste_accesso deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-q" test="not(.//epish:ripristino_piste = 'true' or .//epish:ripristino_piste = '1') or normalize-space(.//epish:desc_ripristino_piste) != ''">
				se ripristino_piste = true allora desc_ripristino_piste non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-r" test="not(.//epish:ripristino_piste = 'false' or .//epish:ripristino_piste = '0') or normalize-space(.//epish:desc_ripristino_piste) = ''">
				se ripristino_piste = false allora desc_ripristino_piste deve essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_suolo_habitat_ab-s" test="not(.//epish:tecniche_ingegneria_naturalistica = 'true' or .//epish:tecniche_ingegneria_naturalistica = '1') or normalize-space(.//epish:desc_tecniche_ingegneria_naturalistica) != ''">
				se tecniche_ingegneria_naturalistica = true allora desc_tecniche_ingegneria_naturalistica non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-t" test="not(.//epish:tecniche_ingegneria_naturalistica = 'false' or .//epish:tecniche_ingegneria_naturalistica = '0') or normalize-space(.//epish:desc_tecniche_ingegneria_naturalistica) = ''">
				se tecniche_ingegneria_naturalistica = false allora desc_tecniche_ingegneria_naturalistica deve essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-u" test="not(.//epish:piste_accesso = 'true' or .//epish:piste_accesso = '1') or normalize-space(.//epish:ripristino_piste) != ''">
				se piste_accesso = true allora ripristino_piste non puo essere vuoto
			</sch:assert>

			<sch:assert id="progetto_impatto_suolo_habitat_ab-v" test="not(.//epish:piste_accesso = 'false' or .//epish:piste_accesso = '0') or normalize-space(.//epish:ripristino_piste) = ''">
				se piste_accesso = false allora ripristino_piste deve essere vuoto
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_suolo_habitat" abstract="false" is-a="progetto_impatto_suolo_habitat_ab">
        <sch:param name="progetto_impatto_suolo_habitat" value="epish:progetto_impatto_suolo_habitat"/>
    </sch:pattern>
</sch:schema>

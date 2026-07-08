<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 5 novembre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/inquadramento_progetto" prefix="einqpro"/>

    <sch:pattern id="inquadramento_progetto_ab" abstract="true">

        <sch:rule id="rule_inquadramento_progetto" context="einqpro:inquadramento_progetto">

            <sch:let name="keysTipologiaOggetto" value="document('../01_vocabularies/voc_tipologia_oggetto.xml')//Row"/>

            <sch:let name="tipologia_progetto" value="normalize-space(einqpro:tipologia_progetto)"/>

            <sch:assert id="inquadramento_progetto_ab-a" test="not(einqpro:progetto_finanziato_risorse_pubbliche = 'true' or einqpro:progetto_finanziato_risorse_pubbliche = '1') or normalize-space(einqpro:desc_risorse_pubbliche_finanziamento) != ''">
                se progetto_finanziato_risorse_pubbliche = true allora desc_risorse_pubbliche_finanziamento non puo essere vuoto
            </sch:assert>

            <sch:assert id="inquadramento_progetto_ab-b" test="not(einqpro:progetto_finanziato_risorse_pubbliche = 'false' or einqpro:progetto_finanziato_risorse_pubbliche = '0') or normalize-space(einqpro:desc_risorse_pubbliche_finanziamento) = ''">
				        se progetto_finanziato_risorse_pubbliche = false allora desc_risorse_pubbliche_finanziamento deve essere vuoto
            </sch:assert>

            <sch:assert id="inquadramento_progetto_ab-tipologia_progetto" test="count($keysTipologiaOggetto[
                                                           normalize-space(Value[@ColumnRef='des_tipologia_oggetto']/SimpleValue) = $tipologia_progetto
                                                           ]) = 1">
                tipologia_progetto non esiste (<sch:value-of select="$tipologia_progetto"/>)
            </sch:assert>

            <sch:assert id="inquadramento_progetto_ab-c" test="not(einqpro:altro_progetto_collegato = 'true' or einqpro:altro_progetto_collegato = '1') or normalize-space(einqpro:desc_progetto_collegato) != ''">
                se altro_progetto_collegato = true allora desc_progetto_collegato non puo essere vuoto
            </sch:assert>

            <sch:assert id="inquadramento_progetto_ab-d" test="not(einqpro:altro_progetto_collegato = 'false' or einqpro:altro_progetto_collegato = '0') or normalize-space(einqpro:desc_progetto_collegato) = ''">
                se altro_progetto_collegato = false allora desc_progetto_collegato deve essere vuoto
            </sch:assert>

            <sch:assert id="inquadramento_progetto_ab-e" test="not(einqpro:rispetto_condizioni_obbligo = 'false' or einqpro:rispetto_condizioni_obbligo = '0') or normalize-space(einqpro:motivazione_mancato_rispetto_condizioni) != ''">
				se rispetto_condizioni_obbligo = false allora motivazione_mancato_rispetto_condizioni non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-f" test="not(einqpro:rispetto_condizioni_obbligo = 'true' or einqpro:rispetto_condizioni_obbligo = '1') or normalize-space(einqpro:motivazione_mancato_rispetto_condizioni) = ''">
				se rispetto_condizioni_obbligo = true allora motivazione_mancato_rispetto_condizioni deve essere vuoto
			</sch:assert>

            <sch:assert id="inquadramento_progetto_ab-g" test="not(einqpro:motivazione_mancato_rispetto_condizioni = 'per altri motivi') or normalize-space(einqpro:desc_motivi) != ''">
				se motivazione_mancato_rispetto_condizioni = per altri motivi allora desc_motivi non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-h" test="not(einqpro:motivazione_mancato_rispetto_condizioni != 'per altri motivi') or normalize-space(einqpro:desc_motivi) = ''">
				se motivazione_mancato_rispetto_condizioni != per altri motivi allora desc_motivi deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-i" test="not(exists(einqpro:tipologia_progetto[normalize-space(.) = 'Altro'])) or exists(einqpro:desc_altra_tipologia_progetto[normalize-space(.) != ''])">
				se esiste una tipologia_progetto = Altro allora desc_altra_tipologia_progetto non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-l" test="exists(einqpro:tipologia_progetto[normalize-space(.) = 'Altro']) or not(exists(einqpro:desc_altra_tipologia_progetto[normalize-space(.) != '']))">
				se non esiste una tipologia_progetto = Altro allora desc_altra_tipologia_progetto deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-m" test="not(exists(einqpro:localizzazione_progetto[normalize-space(.) = 'Altro'])) or exists(einqpro:desc_altra_localizzazione[normalize-space(.) != ''])">
				se esiste una localizzazione_progetto = Altro allora desc_altra_localizzazione non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-n" test="exists(einqpro:localizzazione_progetto[normalize-space(.) = 'Altro']) or not(exists(einqpro:desc_altra_localizzazione[normalize-space(.) != '']))">
				se non esiste una localizzazione_progetto = Altro allora desc_altra_localizzazione deve essere vuoto
			</sch:assert>

            <sch:assert id="inquadramento_progetto_ab-o" test="not(einqpro:conforme_normativa = 'true' or einqpro:conforme_normativa = '1') or normalize-space(einqpro:atto_misure_sito_specifiche_PdG) != ''">
				se conforme_normativa = true allora atto_misure_sito_specifiche_PdG non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-p" test="not(einqpro:conforme_normativa = 'false' or einqpro:conforme_normativa = '0') or normalize-space(einqpro:atto_misure_sito_specifiche_PdG) = ''">
				se conforme_normativa = false allora atto_misure_sito_specifiche_PdG deve essere vuoto
			</sch:assert>

            <sch:assert id="inquadramento_progetto_ab-q" test="count(einqpro:localizzazione_progetto[text()='Centro urbano']) &lt;=1">
				localizzazione_progetto = Centro urbano non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-r" test="count(einqpro:localizzazione_progetto[text()='Zona periurbana']) &lt;=1">
				localizzazione_progetto = Zona periurbana non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-s" test="count(einqpro:localizzazione_progetto[text()='Aree agricole']) &lt;=1">
				localizzazione_progetto = Aree agricole non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-t" test="count(einqpro:localizzazione_progetto[text()='Aree industriali']) &lt;=1">
				localizzazione_progetto = Aree industriali non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-u" test="count(einqpro:localizzazione_progetto[text()='Aree naturali']) &lt;=1">
				localizzazione_progetto = Aree naturali non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-v" test="count(einqpro:localizzazione_progetto[text()='Altro']) &lt;=1">
				localizzazione_progetto = Altro non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-z" test="not(einqpro:conforme_normativa = 'true' or einqpro:conforme_normativa = '1') or normalize-space(einqpro:motivo_non_conformita_PdG) = ''">
				se conforme_normativa = true allora motivo_non_conformita_PdG deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-aa" test="not(einqpro:conforme_normativa = 'false' or einqpro:conforme_normativa = '0') or normalize-space(einqpro:motivo_non_conformita_PdG) != ''">
				se conforme_normativa = false allora motivo_non_conformita_PdG non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-ab" test="not(einqpro:conforme_natura_2000 = 'true' or einqpro:conforme_natura_2000 = '1') or normalize-space(einqpro:atto_misure_natura_2000) != ''">
				se conforme_natura_2000 = true allora atto_misure_natura_2000 non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-ac" test="not(einqpro:conforme_natura_2000 = 'false' or einqpro:conforme_natura_2000 = '0') or normalize-space(einqpro:atto_misure_natura_2000) = ''">
				se conforme_natura_2000 = false allora atto_misure_natura_2000 deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-ad" test="not(einqpro:conforme_natura_2000 = 'true' or einqpro:conforme_natura_2000 = '1') or normalize-space(einqpro:motivo_non_conformita_natura_2000) = ''">
				se conforme_natura_2000 = true allora motivo_non_conformita_natura_2000 deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_ab-ae" test="not(einqpro:conforme_natura_2000 = 'false' or einqpro:conforme_natura_2000 = '0') or normalize-space(einqpro:motivo_non_conformita_natura_2000) != ''">
				se conforme_natura_2000 = false allora motivo_non_conformita_natura_2000 non puo essere vuoto
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="inquadramento_progetto" abstract="false" is-a="inquadramento_progetto_ab">
        <sch:param name="inquadramento_progetto" value="einqpro:inquadramento_progetto"/>
    </sch:pattern>
</sch:schema>

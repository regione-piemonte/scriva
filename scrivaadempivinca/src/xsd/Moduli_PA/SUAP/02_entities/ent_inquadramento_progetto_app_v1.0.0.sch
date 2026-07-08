<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 5 novembre 2025
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/inquadramento_progetto_app" prefix="einqproapp"/>

    <sch:pattern id="inquadramento_progetto_app_ab" abstract="true">

        <sch:rule id="rule_inquadramento_progetto_app" context="einqproapp:inquadramento_progetto_app">

            <sch:let name="keysTipologiaOggetto" value="document('../01_vocabularies/voc_tipologia_oggetto.xml')//Row"/>

            <sch:let name="tipologia_progetto" value="normalize-space(einqproapp:tipologia_progetto)"/>

            <sch:assert id="inquadramento_progetto_app_ab-tipologia_progetto" test="count($keysTipologiaOggetto[
                                                           normalize-space(Value[@ColumnRef='des_tipologia_oggetto']/SimpleValue) = $tipologia_progetto
                                                           ]) = 1">
                tipologia_progetto non esiste (<sch:value-of select="$tipologia_progetto"/>)
            </sch:assert>

            <sch:assert id="inquadramento_progetto_app_ab-c" test="not(einqproapp:altro_progetto_collegato = 'true' or einqproapp:altro_progetto_collegato = '1') or normalize-space(einqproapp:desc_progetto_collegato) != ''">
                se altro_progetto_collegato = true allora desc_progetto_collegato non puo essere vuoto
            </sch:assert>

            <sch:assert id="inquadramento_progetto_app_ab-d" test="not(einqproapp:altro_progetto_collegato = 'false' or einqproapp:altro_progetto_collegato = '0') or normalize-space(einqproapp:desc_progetto_collegato) = ''">
                se altro_progetto_collegato = false allora desc_progetto_collegato deve essere vuoto
            </sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-i" test="not(exists(einqproapp:tipologia_progetto[normalize-space(.) = 'Altro'])) or exists(einqproapp:desc_altra_tipologia_progetto[normalize-space(.) != ''])">
				se esiste una tipologia_progetto = Altro allora desc_altra_tipologia_progetto non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-l" test="exists(einqproapp:tipologia_progetto[normalize-space(.) = 'Altro']) or not(exists(einqproapp:desc_altra_tipologia_progetto[normalize-space(.) != '']))">
				se non esiste una tipologia_progetto = Altro allora desc_altra_tipologia_progetto deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-m" test="not(exists(einqproapp:localizzazione_progetto[normalize-space(.) = 'Altro'])) or exists(einqproapp:desc_altra_localizzazione[normalize-space(.) != ''])">
				se esiste una localizzazione_progetto = Altro allora desc_altra_localizzazione non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-n" test="exists(einqproapp:localizzazione_progetto[normalize-space(.) = 'Altro']) or not(exists(einqproapp:desc_altra_localizzazione[normalize-space(.) != '']))">
				se non esiste una localizzazione_progetto = Altro allora desc_altra_localizzazione deve essere vuoto
			</sch:assert>

            <sch:assert id="inquadramento_progetto_app_ab-o" test="not(einqproapp:conforme_normativa = 'true' or einqproapp:conforme_normativa = '1') or normalize-space(einqproapp:atto_misure_sito_specifiche_PdG) != ''">
				se conforme_normativa = true allora atto_misure_sito_specifiche_PdG non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-p" test="not(einqproapp:conforme_normativa = 'false' or einqproapp:conforme_normativa = '0') or normalize-space(einqproapp:atto_misure_sito_specifiche_PdG) = ''">
				se conforme_normativa = false allora atto_misure_sito_specifiche_PdG deve essere vuoto
			</sch:assert>

            <sch:assert id="inquadramento_progetto_app_ab-q" test="count(einqproapp:localizzazione_progetto[text()='Centro urbano']) &lt;=1">
				localizzazione_progetto = Centro urbano non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-r" test="count(einqproapp:localizzazione_progetto[text()='Zona periurbana']) &lt;=1">
				localizzazione_progetto = Zona periurbana non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-s" test="count(einqproapp:localizzazione_progetto[text()='Aree agricole']) &lt;=1">
				localizzazione_progetto = Aree agricole non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-t" test="count(einqproapp:localizzazione_progetto[text()='Aree industriali']) &lt;=1">
				localizzazione_progetto = Aree industriali non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-u" test="count(einqproapp:localizzazione_progetto[text()='Aree naturali']) &lt;=1">
				localizzazione_progetto = Aree naturali non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-v" test="count(einqproapp:localizzazione_progetto[text()='Altro']) &lt;=1">
				localizzazione_progetto = Altro non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-z" test="not(einqproapp:conforme_normativa = 'true' or einqproapp:conforme_normativa = '1') or normalize-space(einqproapp:motivo_non_conformita_PdG) = ''">
				se conforme_normativa = true allora motivo_non_conformita_PdG deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-aa" test="not(einqproapp:conforme_normativa = 'false' or einqproapp:conforme_normativa = '0') or normalize-space(einqproapp:motivo_non_conformita_PdG) != ''">
				se conforme_normativa = false allora motivo_non_conformita_PdG non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-ab" test="not(einqproapp:conforme_natura_2000 = 'true' or einqproapp:conforme_natura_2000 = '1') or normalize-space(einqproapp:atto_misure_natura_2000) != ''">
				se conforme_natura_2000 = true allora atto_misure_natura_2000 non puo essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-ac" test="not(einqproapp:conforme_natura_2000 = 'false' or einqproapp:conforme_natura_2000 = '0') or normalize-space(einqproapp:atto_misure_natura_2000) = ''">
				se conforme_natura_2000 = false allora atto_misure_natura_2000 deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-ad" test="not(einqproapp:conforme_natura_2000 = 'true' or einqproapp:conforme_natura_2000 = '1') or normalize-space(einqproapp:motivo_non_conformita_natura_2000) = ''">
				se conforme_natura_2000 = true allora motivo_non_conformita_natura_2000 deve essere vuoto
			</sch:assert>

			<sch:assert id="inquadramento_progetto_app_ab-ae" test="not(einqproapp:conforme_natura_2000 = 'false' or einqproapp:conforme_natura_2000 = '0') or normalize-space(einqproapp:motivo_non_conformita_natura_2000) != ''">
				se conforme_natura_2000 = false allora motivo_non_conformita_natura_2000 non puo essere vuoto
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="inquadramento_progetto_app" abstract="false" is-a="inquadramento_progetto_app_ab">
        <sch:param name="inquadramento_progetto_app" value="einqproapp:inquadramento_progetto_app"/>
    </sch:pattern>
</sch:schema>

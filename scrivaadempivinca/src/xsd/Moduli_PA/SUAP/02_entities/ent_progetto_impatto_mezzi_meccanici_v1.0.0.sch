<!-- ##################################################### -->
<!-- # Copyright Regione Piemonte - 2026                 # -->
<!-- # SPDX-License-Identifier: CC-BY-4.0                # -->
<!-- ##################################################### -->
<!--
    @data_creazione: 28 gennaio 2026
    @version: 1.0.0
-->

<sch:schema xmlns:sch="http://purl.oclc.org/dsdl/schematron" queryBinding="xslt2">

    <sch:ns uri="../02_entities/progetto_impatto_mezzi_meccanici" prefix="epimm"/>

    <sch:pattern id="progetto_impatto_mezzi_meccanici_ab" abstract="true">

        <sch:rule id="rule_progetto_impatto_mezzi_meccanici" context="epimm:progetto_impatto_mezzi_meccanici">

            <sch:assert id="progetto_impatto_mezzi_meccanici-a" test="not(epimm:impatto_non_pertinente = 'true' or epimm:impatto_non_pertinente  = '1') or not(epimm:dati_progetto_impatto_mezzi_meccanici)">
				se impatto_non_pertinente = true allora dati_progetto_impatto_mezzi_meccanici non deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-b" test="not(epimm:impatto_non_pertinente = 'false' or epimm:impatto_non_pertinente  = '0') or epimm:dati_progetto_impatto_mezzi_meccanici">
				se impatto_non_pertinente = false allora dati_progetto_impatto_mezzi_meccanici deve essere compilato
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-c" test="not(.//epimm:mezzo_movimento_terra[epimm:tipo_mezzo_movimento_terra = 'Altri mezzi per il movimento terra' and normalize-space(.//epimm:desc_mezzo_movimento_terra) = ''])">
				se tipo_mezzo_movimento_terra = Altri mezzi per il movimento terra allora desc_mezzo_movimento_terra non puo essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_mezzi_meccanici-d" test="not(.//epimm:mezzo_pesante[epimm:tipo_mezzo_pesante = 'Altri mezzi pesanti' and normalize-space(.//epimm:desc_mezzo_pesante) = ''])">
				se tipo_mezzo_pesante = Altri mezzi pesanti allora desc_mezzo_pesante non puo essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_mezzi_meccanici-e" test="not(.//epimm:mezzo_aereo_imbarcazione[epimm:tipo_mezzo_aereo_imbarcazione = 'Altri mezzi aerei o imbarcazioni' and normalize-space(.//epimm:desc_mezzo_aereo_imbarcazione) = ''])">
				se tipo_mezzo_aereo_imbarcazione = Altri mezzi aerei o imbarcazioni allora desc_mezzo_aereo_imbarcazione non puo essere vuoto
			</sch:assert>

            <sch:assert id="progetto_impatto_mezzi_meccanici-g" test="count(.//epimm:tipo_mezzo_movimento_terra[text()='Escavatori']) &lt;=1">
				tipo_mezzo_movimento_terra = Escavatori non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-h" test="count(.//epimm:tipo_mezzo_movimento_terra[text()='Ragni']) &lt;=1">
				tipo_mezzo_movimento_terra = Ragni non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-i" test="count(.//epimm:tipo_mezzo_movimento_terra[text()='Pale caricatrici']) &lt;=1">
				tipo_mezzo_movimento_terra = Pale caricatrici non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-l" test="count(.//epimm:tipo_mezzo_movimento_terra[text()='Terne']) &lt;=1">
				tipo_mezzo_movimento_terra = Terne non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-m" test="count(.//epimm:tipo_mezzo_movimento_terra[text()='Skid loader']) &lt;=1">
				tipo_mezzo_movimento_terra = Skid loader non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-n" test="count(.//epimm:tipo_mezzo_movimento_terra[text()='Altri mezzi per il movimento terra']) &lt;=1">
				tipo_mezzo_movimento_terra = Altri mezzi per il movimento terra non puo occorrere piu di una volta
			</sch:assert>

            <sch:assert id="progetto_impatto_mezzi_meccanici-o" test="count(.//epimm:tipo_mezzo_pesante[text()='Autocarri']) &lt;=1">
				tipo_mezzo_pesante = Autocarri non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-p" test="count(.//epimm:tipo_mezzo_pesante[text()='Dumper']) &lt;=1">
				tipo_mezzo_pesante = Dumper non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-q" test="count(.//epimm:tipo_mezzo_pesante[text()='Autogru']) &lt;=1">
				tipo_mezzo_pesante = Autogru non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-r" test="count(.//epimm:tipo_mezzo_pesante[text()='Gru']) &lt;=1">
				tipo_mezzo_pesante = Gru non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-s" test="count(.//epimm:tipo_mezzo_pesante[text()='Betoniera']) &lt;=1">
				tipo_mezzo_pesante = Betoniera non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-t" test="count(.//epimm:tipo_mezzo_pesante[text()='Asfaltatrici']) &lt;=1">
				tipo_mezzo_pesante = Asfaltatrici non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-u" test="count(.//epimm:tipo_mezzo_pesante[text()='Rulli vibranti']) &lt;=1">
				tipo_mezzo_pesante = Rulli vibranti non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-v" test="count(.//epimm:tipo_mezzo_pesante[text()='Compressori']) &lt;=1">
				tipo_mezzo_pesante = Compressori non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-z" test="count(.//epimm:tipo_mezzo_pesante[text()='Generatori']) &lt;=1">
				tipo_mezzo_pesante = Generatori non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-aa" test="count(.//epimm:tipo_mezzo_pesante[text()='Perforatrici']) &lt;=1">
				tipo_mezzo_pesante = Perforatrici non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-ab" test="count(.//epimm:tipo_mezzo_pesante[text()='Altri mezzi pesanti']) &lt;=1">
				tipo_mezzo_pesante = Altri mezzi pesanti non puo occorrere piu di una volta
			</sch:assert>

            <sch:assert id="progetto_impatto_mezzi_meccanici-ac" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Elicotteri']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Elicotteri non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-ad" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Aerei']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Aerei non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-ae" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Droni']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Droni non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-af" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Teleferiche']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Teleferiche non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-ag" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Barche']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Barche non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-ah" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Chiatte']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Chiatte non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-ai" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Pontoni']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Pontoni non puo occorrere piu di una volta
			</sch:assert>

			<sch:assert id="progetto_impatto_mezzi_meccanici-al" test="count(.//epimm:tipo_mezzo_aereo_imbarcazione[text()='Altri mezzi aerei o imbarcazioni']) &lt;=1">
				tipo_mezzo_aereo_imbarcazione = Altri mezzi aerei o imbarcazioni non puo occorrere piu di una volta
			</sch:assert>

        </sch:rule>

    </sch:pattern>

    <sch:pattern id="progetto_impatto_mezzi_meccanici" abstract="false" is-a="progetto_impatto_mezzi_meccanici_ab">
        <sch:param name="progetto_impatto_mezzi_meccanici" value="epimm:progetto_impatto_mezzi_meccanici"/>
    </sch:pattern>
</sch:schema>

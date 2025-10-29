/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.index.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Aspect;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.OperationContext;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Property;
import it.csi.scriva.scrivabesrv.dto.IndexMetadataPropertyDTO;
import it.csi.scriva.scrivabesrv.util.oauth2.OauthHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * The type Index factory.
 */
@Component
public class IndexFactory extends AbstractServiceHelper {

    /**
     * Gets operation context.
     *
     * @param username   the username
     * @param password   the password
     * @param tenant     the tenant
     * @param fruitore   the fruitore
     * @param repository the repository
     * @param nomeFisico the nome fisico
     * @return the operation context
     */
    public static OperationContext getOperationContext(String username, String password, String tenant, String fruitore, String repository, String nomeFisico) {
        final OperationContext operationContext = new OperationContext();
        operationContext.setUsername(username);
        operationContext.setPassword(password);
        operationContext.setTenant(tenant);
        operationContext.setFruitore(fruitore);
        operationContext.setRepository(repository);
        operationContext.setNomeFisico(nomeFisico);
        return operationContext;
    }

    /**
     * Gets token.
     *
     * @param tokenUrl       the token url
     * @param consumerKey    the consumer key
     * @param consumerSecret the consumer secret
     * @return the token
     */
    public static String getTokenIndex(String tokenUrl, String consumerKey, String consumerSecret) {
        OauthHelper oauthHelper = new OauthHelper(
                tokenUrl,
                consumerKey,
                consumerSecret,
                10000);
        String token = oauthHelper.getToken();
        LOGGER.debug("IndexFactory::getToken : " + token);
        return token;
    }

    /**
     * Gets x request auth.
     *
     * @param username   the username
     * @param password   the password
     * @param tenant     the tenant
     * @param fruitore   the fruitore
     * @param repository the repository
     * @param nomeFisico the nome fisico
     * @return the x request auth
     * @throws JsonProcessingException the json processing exception
     */
    public static String getXRequestAuth(String username, String password, String tenant, String fruitore, String repository, String nomeFisico) throws JsonProcessingException {
        OperationContext operationContext = getOperationContext(username, password, tenant, fruitore, repository, nomeFisico);
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();
        String jsonOperationContext = objectMapper.writeValueAsString(operationContext);

        return new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));
    }

    /**
     * Gets url.
     *
     * @param apiUrl the api url
     * @param tenant the tenant
     * @param uid    the uid
     * @return the url
     */
    public static String getUrl(String apiUrl, String tenant, String uid) {
        return apiUrl
                .replaceAll("\\{" + "tenantName" + "\\}", escapeString(tenant))
                .replaceAll("\\{" + "uid" + "\\}", StringUtils.isNotBlank(uid) ? escapeString(uid) : uid);
    }

    /**
     * Gets create folder node.
     *
     * @param folderName the folder name
     * @return the create folder node
     */
    public static Node getCreateFolderNode(String folderName) {
        return createContentNode(folderName, null, null);
    }

    /**
     * Gets create content node.
     *
     * @param filename              the filename
     * @param indexMetadataProperty the index metadata property
     * @return the create content node
     */
    public static Node getCreateContentNode(String filename, IndexMetadataPropertyDTO indexMetadataProperty) {
        return createContentNode(null, filename, indexMetadataProperty);
    }

    /**
     * Create content node node.
     *
     * @param foldername            the foldername
     * @param filename              the filename
     * @param indexMetadataProperty the index metadata property
     * @return the node
     */
    public static Node createContentNode(String foldername, String filename, IndexMetadataPropertyDTO indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:scriva");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
        if (StringUtils.isNotBlank(foldername)) {
            indexNode.setPrefixedName("cm:" + foldername);
            indexNode.setTypePrefixedName("cm:folder");
        } else if (StringUtils.isNotBlank(filename)) {
            indexNode.setPrefixedName("cm:" + filename);
            indexNode.setTypePrefixedName("cm:content");
            indexNode.setContentPropertyPrefixedName("cm:content");
            indexNode.setMimeType("application/octet-stream");
            indexNode.setEncoding("UTF-8");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createMetadataAspects(indexMetadataProperty);
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    /**
     * Create metadata properties list.
     *
     * @param indexMetadataProperty the index metadata property
     * @return the list
     */
    public static List<Property> createMetadataProperties(IndexMetadataPropertyDTO indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values = new ArrayList<>();

        if (StringUtils.isNotBlank(indexMetadataProperty.getIdIstanza())) {
            property = new Property();
            property.setPrefixedName("scriva:idIstanza");
            property.setMultivalue(false);
            property.setDataType("d:int");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getIdIstanza());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceIstanza())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceIstanza");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceIstanza());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodicePratica())) {
            property = new Property();
            property.setPrefixedName("scriva:codicePratica");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodicePratica());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDimensioneByte())) {
            property = new Property();
            property.setPrefixedName("scriva:dimensioneByte");
            property.setMultivalue(false);
            property.setDataType("d:int");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDimensioneByte());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceAllegato())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceTipoAdempimento())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceTipoAdempimento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceTipoAdempimento());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDescrizioneTipoAdempimento())) {
            property = new Property();
            property.setPrefixedName("scriva:descrizioneTipoAdempimento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDescrizioneTipoAdempimento());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceAdempimento())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceAdempimento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceAdempimento());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDescrizioneAdempimento())) {
            property = new Property();
            property.setPrefixedName("scriva:descrizioneAdempimento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDescrizioneAdempimento());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceAutoritaCompetente())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceAutoritaCompetente");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceAutoritaCompetente());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceMultipleAutoritaCompetenti())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceMultipleAutoritaCompetenti");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceMultipleAutoritaCompetenti());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceCategoriaAllegato())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceCategoriaAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceCategoriaAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDescrizioneCategoriaAllegato())) {
            property = new Property();
            property.setPrefixedName("scriva:descrizioneCategoriaAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDescrizioneCategoriaAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceTipologiaAllegato())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceTipologiaAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceTipologiaAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDescrizioneTipologiaAllegato())) {
            property = new Property();
            property.setPrefixedName("scriva:descrizioneTipologiaAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDescrizioneTipologiaAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getNoteAllegato())) {
            property = new Property();
            property.setPrefixedName("scriva:noteAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNoteAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDocumentoObbligatorio())) {
            property = new Property();
            property.setPrefixedName("scriva:documentoObbligatorio");
            property.setMultivalue(false);
            property.setDataType("d:boolean");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDocumentoObbligatorio());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceIntegrazione())) {
            property = new Property();
            property.setPrefixedName("scriva:codiceIntegrazione");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceIntegrazione());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDescrizioneIntegrazione())) {
            property = new Property();
            property.setPrefixedName("scriva:descrizioneIntegrazione");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDescrizioneIntegrazione());
            property.setValues(values);
            properties.add(property);
        }

        /** ASPECT PROPERTIES **/
        if (StringUtils.isNotBlank(indexMetadataProperty.getFirmato())) {
            property = new Property();
            property.setPrefixedName("scriva:firmato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getFirmato());
            property.setValues(values);
            property.setRelativeAspectPrefixedName("scriva:firmaDocumento");
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getTipoFirma())) {
            property = new Property();
            property.setPrefixedName("scriva:tipoFirma");
            property.setMultivalue(false);
            property.setDataType("d:int");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getTipoFirma());
            property.setValues(values);
            property.setRelativeAspectPrefixedName("scriva:firmaDocumento");
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getErrorCode())) {
            property = new Property();
            property.setPrefixedName("scriva:errorCode");
            property.setMultivalue(false);
            property.setDataType("d:int");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getErrorCode());
            property.setValues(values);
            property.setRelativeAspectPrefixedName("scriva:firmaDocumento");
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCancellato())) {
            property = new Property();
            property.setPrefixedName("scriva:cancellato");
            property.setMultivalue(false);
            property.setDataType("d:boolean");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCancellato());
            property.setValues(values);
            property.setRelativeAspectPrefixedName("scriva:cancellato");
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getDataCancellazione())) {
            property = new Property();
            property.setPrefixedName("scriva:dataCancellazione");
            property.setMultivalue(false);
            property.setDataType("d:datetime");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getDataCancellazione());
            property.setValues(values);
            property.setRelativeAspectPrefixedName("scriva:cancellato");
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getRiservato())) {
            property = new Property();
            property.setPrefixedName("scriva:riservato");
            property.setRelativeAspectPrefixedName("scriva:pubblicato");
            property.setMultivalue(false);
            property.setDataType("d:boolean");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getRiservato());
            property.setValues(values);
            properties.add(property);
        }

        return properties;
    }

    /**
     * Create metadata aspects list.
     *
     * @param indexMetadataProperty the index metadata property
     * @return the list
     */
    public static List<Aspect> createMetadataAspects(IndexMetadataPropertyDTO indexMetadataProperty) {
        List<Aspect> aspectList = new ArrayList<>();
        Aspect aspect;

        aspect = new Aspect();
        aspect.setPrefixedName("sys:referenceable");
        aspectList.add(aspect);

        aspect = new Aspect();
        aspect.setPrefixedName("cm:auditable");
        aspectList.add(aspect);

        if (StringUtils.isNotBlank(indexMetadataProperty.getFirmato()) || StringUtils.isNotBlank(indexMetadataProperty.getTipoFirma()) || StringUtils.isNotBlank(indexMetadataProperty.getErrorCode())) {
            aspect = new Aspect();
            aspect.setPrefixedName("scriva:firmaDocumento");
            aspectList.add(aspect);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCancellato()) || StringUtils.isNotBlank(indexMetadataProperty.getDataCancellazione())) {
            aspect = new Aspect();
            aspect.setPrefixedName("scriva:cancellato");
            aspectList.add(aspect);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getRiservato())) {
            aspect = new Aspect();
            aspect.setPrefixedName("scriva:pubblicato");
            aspectList.add(aspect);
        }
        return aspectList;
    }

    private static String escapeString(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }
}
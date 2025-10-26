/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */


package it.csi.scriva.scrivaapisrv.business.be.common;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Custom mapper.
 */
@Component
public class CustomMapper {

    /**
     * The Mapper.
     */
    DozerBeanMapper mapper;

    /**
     * Map.
     *
     * @param source      the source
     * @param destination the destination
     */
    public void copyProperties(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    /**
     * Map.
     *
     * @param source      the source
     * @param destination the destination
     */
    public void map(Object source, Object destination) {
        this.mapper = new DozerBeanMapper();
        this.mapper.addMapping(beanMappingBuilder(source.getClass(), destination.getClass()));
        this.mapper.map(source, destination);
    }

    /**
     * Map t.
     *
     * @param <T>              the type parameter
     * @param source           the source
     * @param destinationClass the destination class
     * @return the t
     */
    public <T> T map(Object source, Class<T> destinationClass) {
        this.mapper = new DozerBeanMapper();
        this.mapper.addMapping(beanMappingBuilder(source.getClass(), destinationClass));
        return this.mapper.map(source, destinationClass);
    }

    /**
     * Bean mapping builder bean mapping builder.
     *
     * @param sourceClass      the source class
     * @param destinationClass the destination class
     * @return the bean mapping builder
     */
    private static BeanMappingBuilder beanMappingBuilder(Class<?> sourceClass, Class<?> destinationClass) {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(sourceClass, destinationClass, TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false));
            }
        };
    }

    /**
     * Get null property names string [].
     *
     * @param source the source
     * @return the string [ ]
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
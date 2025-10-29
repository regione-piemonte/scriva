/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.constraints.impl;

import it.csi.scriva.scrivafoweb.business.be.helper.constraints.FieldMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object firstObj = getPropertyValue(value, firstFieldName);
            final Object secondObj = getPropertyValue(value, secondFieldName);

            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }

    private Object getPropertyValue(Object bean, String property)
            throws IntrospectionException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Class<?> beanClass = bean.getClass();
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(
                beanClass, property);
        if (propertyDescriptor == null) {
            throw new IllegalArgumentException("No such property " + property
                    + " for " + beanClass + " exists");
        }

        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod == null) {
            throw new IllegalStateException("No getter available for property "
                    + property + " on " + beanClass);
        }
        return readMethod.invoke(bean);
    }

    private PropertyDescriptor getPropertyDescriptor(Class<?> beanClass,
                                                     String propertyname) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        PropertyDescriptor propertyDescriptor = null;
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor currentPropertyDescriptor = propertyDescriptors[i];
            if (currentPropertyDescriptor.getName().equals(propertyname)) {
                propertyDescriptor = currentPropertyDescriptor;
            }

        }
        return propertyDescriptor;
    }

}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package test.dozer;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.BeanUtils;

public class SimpleExample {

    public static void main(String[] args) {
        {
            ClassA classA = new ClassA();
            classA.setAddress("India");
            classA.setName("Sajal");
            classA.setAge("50");

            ClassB2 classB2 = new DozerBeanMapper().map(classA, ClassB2.class);
            System.out.println("--- DOZER ---");
            System.out.println(classB2);

            ClassB2 classB2Bean = new ClassB2();
            BeanUtils.copyProperties(classA, classB2Bean);
            System.out.println("--- BEANUTILS classB2Bean ---");
            System.out.println(classB2Bean);

            ClassB classB = new ClassB();
            BeanUtils.copyProperties(classA, classB);
            System.out.println("--- BEANUTILS classB ---");
            System.out.println(classB);

            //ClassB classB = new DozerBeanMapper().map(classA, ClassB.class);
            //System.out.println(classB);
        }
    }
}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.otp;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * The type Random string.
 *
 * @author CSI PIEMONTE
 */
public class RandomString {

    /**
     * The constant upper.
     */
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * The constant lower.
     */
    public static final String lower = upper.toLowerCase(Locale.ROOT);

    /**
     * The constant digits.
     */
    public static final String digits = "0123456789";

    /**
     * The constant alphanum.
     */
    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;


    /**
     * Next string string.
     *
     * @return String string
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    /**
     * Instantiates a new Random string.
     *
     * @param length  length
     * @param random  random
     * @param symbols symbols
     */
    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     *
     * @param length length
     * @param random random
     */
    public RandomString(int length, Random random) {
        this(length, random, alphanum);
    }


    /**
     * Instantiates a new Random string.
     *
     * @param length length
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }


    /**
     * costruttore
     */
    public RandomString() {
        this(21);
    }
}
/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * The type Hashing util.
 *
 * @author CSI PIEMONTE
 */
public class HashingUtil {

    private static Random rand;

    /**
     * Instantiates a new Hashing util.
     *
     */
    public HashingUtil() {
        // Do nothing
    }

    /**
     * Gets rand.
     *
     * @return the rand
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public Random getRand() throws NoSuchAlgorithmException {
        return rand != null ? rand : SecureRandom.getInstanceStrong();
    }

    /**
     * Encode sh 3 string.
     *
     * @param stringToHash stringToHash
     * @return string string
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static String encodeSH3(String stringToHash) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest((stringToHash + generateRandomAlphanumericString(10)).getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }

    /**
     * Encode sha 256 hash string.
     *
     * @param stringToHash the string to hash
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String encodeSHA256Hash(String stringToHash) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hashbytes = digest.digest((stringToHash + generateRandomAlphanumericString(10)).getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashbytes);
    }

    /**
     * Encode sha 256 hash string.
     *
     * @param contentToHash the content to hash
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String encodeSHA256Hash(byte[] contentToHash) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hashbytes = digest.digest(concatByteArray(contentToHash, generateRandomAlphanumericString(10).getBytes(StandardCharsets.UTF_8)));
        return Base64.getEncoder().encodeToString(hashbytes);
    }

    /**
     * @param hash hash
     * @return string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    /**
     * Generate random alphanumeric string string.
     *
     * @param targetStringLength the target string length
     * @return string string
     */
    public static String generateRandomAlphanumericString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        Random random = new SecureRandom();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength == 0 ? 10 : targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static byte[] concatByteArray(byte[] a, byte[] b) {
        int lenA = a.length;
        int lenB = b.length;
        byte[] c = Arrays.copyOf(a, lenA + lenB);
        System.arraycopy(b, 0, c, lenA, lenB);
        return c;
    }
}
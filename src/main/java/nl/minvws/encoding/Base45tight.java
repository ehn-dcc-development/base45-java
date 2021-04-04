/*
 * Copyright 2021 De Staat der Nederlanden, Ministerie van Volksgezondheid, Welzijn en Sport.
 * Licensed under the EUROPEAN UNION PUBLIC LICENCE v. 1.2
 * SPDX-License-Identifier: EUPL-1.2
 */

package nl.minvws.encoding;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import java.math.BigInteger;

public class Base45tight {

    private static final int BaseSize = 45;
    private Base45tight() {
    }

    /**
     * Returns a {@link Encoder} that encodes using the type Base45 encoding scheme.
     * @return A Base45 encoder.
     */
    public static Encoder getEncoder() {
        return Encoder.ENCODER;
    }

    /**
     * Returns a {@link Decoder} that decodes using the type Base45 encoding scheme.
     * @return A Base45 decoder.
     */
    public static Decoder getDecoder() {
        return Decoder.DECODER;
    }

    /**
     * This class implements an encoder for encoding byte data using the Base45 encoding scheme
     * Instances of {@link Encoder} class are safe for use by multiple concurrent threads.
     */
    public static class Encoder {

        /**
         * This array is a lookup table that translates integer
         * index values into their "Base45 Alphabet" equivalents as specified.
         */
        private static final byte[] toBase45 = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '$', '%',
            '*', '+', '-', '.', '/', ':'
        };

        static final Encoder ENCODER = new Encoder();

        /**
         * Encodes all bytes from the specified byte array into a newly-allocated
         * byte array using the {@link Base45tight} encoding scheme. The returned byte
         * array is of the length of the resulting bytes.
         *
         * @param src the byte array to encode
         * @return A newly-allocated byte array containing the resulting encoded bytes.
         */
        public byte[] encode(byte[] src) {
            if (src.length == 0)
                return new byte[0];

            StringBuilder out = new StringBuilder();
            BigInteger divident = new BigInteger(src);
            BigInteger bigQrCharsetLen = BigInteger.valueOf(BaseSize);

            while (divident.bitCount() != 0) {
                BigInteger[] r = divident.divideAndRemainder(bigQrCharsetLen);
                divident = r[0];
                int idx = r[1].intValue();
                out.append((char) toBase45[idx]);
            }
            // postfix (prefix) any leading 0 bytes - as they are not in the bignum
            for (int i = 0; i < src.length && src[i] == 0; i++)
                out.append((char) toBase45[0]);

            return out.reverse().toString().getBytes(StandardCharsets.UTF_8);
        }

        /**
         * Encodes the specified byte array into a String using the {@link Base45tight} encoding scheme.
         *
         * An invocation of this method has exactly the same
         * effect as invoking {@code new String(encode(src), StandardCharsets.ISO_8859_1)}.
         * Even though it's deprecated it's added to be similar to Base64 in java.
         *
         * @param src the byte array to encode
         * @return A String containing the resulting Base45 encoded characters
         */
        @SuppressWarnings("deprecation")
        public String encodeToString(byte[] src) {
            byte[] encoded = encode(src);
            return new String(encoded, 0, 0, encoded.length);
        }

    }

    /**
     * This class implements a decoder for decoding byte data using the
     * Base45 encoding scheme
     *
     * <p> Instances of {@link Decoder} class are safe for use by
     * multiple concurrent threads.
     */
    public static class Decoder {

        private Decoder() {
        }

        /**
         * Lookup table for decoding unicode characters drawn from the
         * "Base45 Alphabet".
         */
        private static final int[] fromBase45 = new int[256];

        static {
            Arrays.fill(fromBase45, -1);
            for (int i = 0; i < Encoder.toBase45.length; i++) {
                fromBase45[Encoder.toBase45[i]] = i;
            }
        }

        static final Decoder DECODER = new Decoder();

        /**
         * Decodes all bytes from the input byte array using the {@link Base45tight}
         * encoding scheme, writing the results into a newly-allocated output
         * byte array. The returned byte array is of the length of the resulting
         * bytes.
         *
         * @param src the byte array to decode
         * @return A newly-allocated byte array containing the decoded bytes.
         * @throws IllegalArgumentException if {@code src} is not in valid Base45 scheme
         */
        public byte[] decode(byte[] src) {

            BigInteger result = BigInteger.ZERO;

            if (src.length == 0)
                return new byte[0];

            for (int i = 0; i < src.length; i++) {
                int idx = fromBase45[src[i]];
                if (-1 == idx)
                    throw new IllegalArgumentException();

                BigInteger factor = BigInteger.valueOf(idx);
                BigInteger weight = BigInteger.valueOf(45);
                result = result.add(weight.pow(src.length - i - 1).multiply(factor));
            }

            int leadingz = 0;
            for(int i =  0; i < src.length && fromBase45[src[i]] == 0; i++)
                leadingz ++;

            if (result.equals(BigInteger.ZERO))
                leadingz --;

            byte [] zeros = new byte[leadingz];
            byte [] rest = result.toByteArray();

            byte[] out = Arrays.copyOf(zeros, zeros.length + rest.length);
            System.arraycopy(rest, 0, out, zeros.length, rest.length);

            return out;
        }

        public byte[] decode(String src) {
            return decode(src.getBytes(StandardCharsets.ISO_8859_1));
        }
    }
}

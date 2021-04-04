package nl.minvws.encoding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class Base45tightTest {

    void check_raw(byte [] in, String out) throws Exception {

        byte[] encoded = Base45tight.getEncoder().encode(in);
        byte[] decoded = Base45tight.getDecoder().decode(encoded);

        if (false) {
            System.out.println("IN   >" + new String(in, StandardCharsets.UTF_8) + "<");
            System.out.println("     >" + byteArrayToHex(in) + "<" + in.length);
            System.out.println("  rr>" + byteArrayToHex(decoded) + "<" + decoded.length);
            System.out.println("    >" + new String(decoded, StandardCharsets.UTF_8) + "<");
            System.out.println("OUT >" + new String(encoded, StandardCharsets.UTF_8) + "<");
            System.out.println("    >" + byteArrayToHex(encoded) + "<" + encoded.length);
            System.out.println("   =>" + out + "<" + encoded.length);
            System.out.println();
        }
        assert (out.equals(new String(encoded, StandardCharsets.UTF_8)));
        assert(in.length == decoded.length);
        assert(java.util.Arrays.equals(in,decoded));
    }

    void check(String inAsStr, String out) throws Exception {
        byte [] in = inAsStr.getBytes(StandardCharsets.UTF_8);
        check_raw(in,out);
    }
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        String prefix = "";
        for(byte b: a) {
            sb.append(String.format("%sx%02x", prefix, b));
            prefix = ",";
        };
        return sb.toString();
    }

    @Test
    void test_all() {
        try {
            check("hello world","K3*J+EGLBVAYYB36");
            check("foo bar","%4VVO:F$X5");
            check("ietf!","19N6HOO$");
            check("Hello!!","Q-*2/.MZ B");
            check("COVID-19","37J$4 QAWL0E");
            check("2021 Digital Green Certificates for travel",
                    "HREOZC%EXPZQX3G6VGTB%7+Q2BISAC87Y5A QC8774F7A2NY6G+MIJ$YUSQ3P");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            check_raw(new byte[]{1, 2, 3} , "WR ");
            check_raw(new byte[]{} , "");
            check_raw(new byte[]{0} , "0");
            check_raw(new byte[]{0,0} , "00");
            check_raw(new byte[]{0,0,0} , "000");
            check_raw(new byte[]{0, 1, 2, 3}, "0WR ");
            check_raw(new byte[]{0, 0, 1, 2, 3} , "00WR ");
            check_raw(new byte[]{0, 0, 0, 1, 2, 3} , "000WR ");
            check_raw(new byte[]{0, 0, 0, 0, 1, 2, 3} , "0000WR ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
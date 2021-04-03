package nl.minvws.encoding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class Bas45Test {

    @Test
    public void testEncodeString() {
        assertEquals("BB8", Base45.getEncoder().encodeToString("AB".getBytes(StandardCharsets.UTF_8)));
        assertEquals("%69 VD92EX0", Base45.getEncoder().encodeToString("Hello!!".getBytes(StandardCharsets.UTF_8)));
        assertEquals("UJCLQE7W581", Base45.getEncoder().encodeToString("base-45".getBytes(StandardCharsets.UTF_8)));
        assertThrows(NullPointerException.class, ()->{ Base45.getEncoder().encodeToString(null); });
    }

    @Test
    public void testEncodeByteArray() {
        assertArrayEquals("BB8".getBytes(StandardCharsets.UTF_8), Base45.getEncoder().encode("AB".getBytes(StandardCharsets.UTF_8)));
        assertArrayEquals("%69 VD92EX0".getBytes(StandardCharsets.UTF_8), Base45.getEncoder().encode("Hello!!".getBytes(StandardCharsets.UTF_8)));
        assertArrayEquals("UJCLQE7W581".getBytes(StandardCharsets.UTF_8), Base45.getEncoder().encode("base-45".getBytes(StandardCharsets.UTF_8)));
        assertThrows(NullPointerException.class, ()->{ Base45.getEncoder().encode(null); });
    }

    @Test
    public void testDecodeString() {
        assertEquals("ietf!", new String(Base45.getDecoder().decode("QED8WEX0")));
        assertEquals("AB", new String(Base45.getDecoder().decode("BB8")));
        assertEquals("Hello!!", new String(Base45.getDecoder().decode("%69 VD92EX0")));
        assertThrows(NullPointerException.class, ()->{ Base45.getDecoder().decode((String) null); });
    }

    @Test
    public void testDecodeByteArray() {
        assertArrayEquals("ietf!".getBytes(StandardCharsets.UTF_8), Base45.getDecoder().decode("QED8WEX0".getBytes(StandardCharsets.UTF_8)));
        assertArrayEquals("AB".getBytes(StandardCharsets.UTF_8), Base45.getDecoder().decode("BB8".getBytes(StandardCharsets.UTF_8)));
        assertArrayEquals("Hello!!".getBytes(StandardCharsets.UTF_8), Base45.getDecoder().decode("%69 VD92EX0".getBytes(StandardCharsets.UTF_8)));
        assertThrows(NullPointerException.class, ()->{ Base45.getDecoder().decode((byte[]) null); });
        assertThrows(IllegalArgumentException.class, ()->{ Base45.getDecoder().decode("a".getBytes(StandardCharsets.UTF_8)); });
    }

}

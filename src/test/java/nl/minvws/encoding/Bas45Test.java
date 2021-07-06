/*
 * Copyright 2021 De Staat der Nederlanden, Ministerie van Volksgezondheid, Welzijn en Sport.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

        // because java is signed make sure we test a value passed 128
        assertEquals("3XO", Base45.getEncoder().encodeToString("è".getBytes(StandardCharsets.UTF_8)));

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
        assertThrows(IllegalArgumentException.class, ()->{ Base45.getDecoder().decode("GGW".getBytes(StandardCharsets.UTF_8)); });
    }
}

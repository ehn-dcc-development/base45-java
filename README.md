# Base45

Simple Base45 implementation, based off https://datatracker.ietf.org/doc/draft-faltstrom-base45/ \
Java 1.8+

Designed to align with QR code (ISO/IEC 18004:2015) standard and the 'Mode 2/0010' alphanummeric
characters set.

# Base45tight
Base45 implementation, based on the https://tools.ietf.org/id/draft-msporny-base58-01.html draft; 
which is commonly used in the bitcoin community for similarly sized strings. Significantly slower
than above Base45; but around 3% more compact.

## Typical usage
Same as Base64.

Encode:
```
String originalInput = "test input";
String encodedString = Base45.getEncoder().encodeToString(originalInput.getBytes());
```

And decode back:
```
byte[] decodedBytes = Base45.getDecoder().decode(encodedString);
String decodedString = new String(decodedBytes);
```

## How to build
It's a typical maven project creating a jar in /target
```
mvn clean install
```

## Code coverage
Locally with the browser:
target/site/jacoco/nl.minvws.encoding/index.html

![branches](.github/badges/branches.svg) ![branches](.github/badges/jacoco.svg)

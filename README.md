# Base45
Simple Base45 implementation, based off https://datatracker.ietf.org/doc/draft-faltstrom-base45/ \
Java 1.8+

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

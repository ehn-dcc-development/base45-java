# Base45
Simple Base45 implementation, based off https://datatracker.ietf.org/doc/draft-faltstrom-base45/ \
Java 1.8+

## Typical usage
Add it as a dependency:
```
<dependency>
  <groupId>io.github.ehn-digital-green-development</groupId>
  <artifactId>base45</artifactId>
  <version>0.0.2</version>
</dependency>
```

After that you can use it the same way you would use Base64.

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

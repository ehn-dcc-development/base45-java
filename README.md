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

## Licensing

Copyright (C) 2021 the eHealth implementors group and all other contributors

Licensed under the **Apache License, Version 2.0** (the "License"); you may not use this file except in compliance with the License.

You may obtain a copy of the License at https://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the [LICENSE](./LICENSE) for the specific
language governing permissions and limitations under the License.

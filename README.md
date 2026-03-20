# Dog API Tests

[![CI](https://github.com/SEUUSER/dog-api-tests/actions/workflows/ci.yml/badge.svg)](https://github.com/SEUUSER/dog-api-tests/actions)

## Pré-requisitos

- Java 11+
- Maven 3.8+

## Executar

```bash
cd dog-api-tests
mvn clean compile
mvn test
mvn allure:serve
```

## Cenários

- TC-001: Listar todas raças
- TC-002: Imagens por raça válida
- TC-003: Raça inválida 404
- TC-004: Imagem random
- TC-005: Imagem random por raça
- TC-006: Múltiplas imagens random (3)
- TC-007: Lista de sub-raças de hound

## COMANDOS PARA EXECUTAR APÓS

```bash
mvn clean compile
mvn test
mvn allure:serve
```

**Importante**

- Todas classes estendem BaseTest
- 100% assertions com JSONPath
- Relatório Allure com detalhes de falhas
- Sem dependências externas além pom.xml

## CI (GitHub Actions)

- Pipeline: Ubuntu + Java 11 + mvn test com suite TestNG.
- Artefatos publicados:
  - `surefire-reports` (logs TestNG)
  - `allure-results` (bruto)
  - `allure-report-html` (HTML estático do Allure)

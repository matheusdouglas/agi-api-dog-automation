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

## Notas

- O TestNG usa o arquivo de suíte em src/test/resources/testng.xml. No PowerShell, se houver erro de parsing do -D, use:
  - `mvn --% -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml test`
- O Allure grava resultados em `target/allure-results` (definido em `src/test/resources/allure.properties`). O relatório HTML local é aberto com `mvn allure:serve` ou gerado em `target/site/allure-maven-plugin` via `mvn allure:report`.

## CI (GitHub Actions)

- Pipeline: Ubuntu + Java 11 + mvn test com suite TestNG.
- Artefatos publicados:
  - `surefire-reports` (logs TestNG)
  - `allure-results` (bruto)
  - `allure-report-html` (HTML estático do Allure)

### Publicação via GitHub Pages

- Já configurado no workflow:
  - Publica o diretório `target/site/allure-maven-plugin` como site.
  - Deploy automático usando GitHub Pages (Actions → executar workflow).
- Como habilitar no repositório:
  - Settings → Pages → Build and deployment = GitHub Actions.
- Onde ver a URL:
  - Na execução do workflow, na etapa “Deploy to GitHub Pages”, copie o `page_url`.

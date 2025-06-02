# Sistema de Alertas de Queimadas 🌋🔥
**Domain-Driven Design • Java 17 • Spring Boot 3**

[![Build](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/<usuario>/alertas-queimadas/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](LICENSE)

> Monitoramento de focos de calor, geração de alertas e gestão de
> ocorrências por agentes ambientais.  
> Demonstra boas práticas de arquitetura em camadas, padrões de projeto
> e API RESTful documentada com Swagger UI.

---

## Índice <!-- omit in toc -->
- [👥 Equipe](#-equipe)
- [✨ Funcionalidades](#-funcionalidades)
- [🏗️ Arquitetura e padrões](#️-arquitetura-e-padrões)
- [🛠️ Stack](#️-stack)
- [🚀 Execução](#-execução)
- [📑 Exemplos de uso](#-exemplos-de-uso)
- [🧪 Roteiro de teste rápido](#-roteiro-de-teste-rápido)
- [📂 Estrutura](#-estrutura)
- [🔭 Possíveis evoluções](#-possíveis-evoluções)
- [🎥 Vídeo](#-vídeo)
- [📜 Licença](#-licença)

---

## 👥 Equipe

| Integrante | RM | Principais frentes |
|------------|----|--------------------|
| **Vitor Pinheiro Nascimento** | RM553693 | Modelagem de domínio, controllers |
| **Pedro Henrique Chaves**     | RM553988 | Persistência, testes, ambiente |

---

## ✨ Funcionalidades

| Categoria | Descrição |
|-----------|-----------|
| **Cadastro de sensores** | Tipos **TORRE** e **DRONE** com atributos específicos |
| **Detecção de foco** | Recebe intensidade, gera alerta automático |
| **Workflow** | Alerta → Aceitação → Ocorrência → Encerramento |
| **Relatórios** | Totais e tempo médio de resposta |
| **Swagger UI** | API explorável em `/swagger-ui.html` |

---

## 🏗️ Arquitetura e padrões

```mermaid
graph TD
  A[Controller] --> B(Service)
  B --> C(Domain)
  C --> D[Repository (Spring Data)]
  C -->|Factory| C1[AlertaFactory]
  C -->|Singleton| C2[SensorRegistry]
````

| Padrão             | Arquivo           | Propósito                           |
| ------------------ | ----------------- | ----------------------------------- |
| **Factory Method** | `AlertaFactory`   | Centraliza regras de severidade     |
| **Singleton**      | `SensorRegistry`  | Cache global de sensores            |
| **VO / DTO**       | `RelatorioResumo` | Dados agregados sem expor entidades |
| **Repository**     | `*Repository`     | Persistência declarativa            |

---

## 🛠️ Stack

| Camada       | Ferramenta                                     |
| ------------ | ---------------------------------------------- |
| Linguagem    | Java 17                                        |
| Framework    | Spring Boot 3.5                                |
| Persistência | Spring Data JPA + H2 (dev) / Oracle 19c (FIAP) |
| Documentação | Springdoc OpenAPI UI                           |
| Build        | Maven Wrapper                                  |
| Serialização | Jackson + `jackson-datatype-hibernate6`        |
| Testes       | JUnit 5                                        |

---

## 🚀 Execução

### Pré-requisitos

```text
Java 17+   •   Maven 3.9+
```

### Ambiente **DEV** (H2)

```bash
git clone https://github.com/P4Chaves/JavaGS2025
cd alertas-queimadas
./mvnw clean spring-boot:run
```

| Endereço                                | Função                                   |
| --------------------------------------- | ---------------------------------------- |
| `http://localhost:8080/swagger-ui.html` | Swagger UI                               |
| `http://localhost:8080/h2-console`      | H2 Console — URL `jdbc:h2:mem:queimadas` |

### Ambiente **ORACLE** (FIAP)

```bash
export ORACLE_USER=RM55988
export ORACLE_PASS=041005

./mvnw clean spring-boot:run \
  -Dspring.profiles.active=oracle \
  -Dspring.datasource.username=$ORACLE_USER \
  -Dspring.datasource.password=$ORACLE_PASS
```

---

## 📑 Exemplos de uso

```bash
# 1. Sensor
curl -X POST -H "Content-Type: application/json" \
  -d '{ "tipo":"TORRE","codigo":"T-001","latitude":-22.8,"longitude":-43.3,"altura":25,"alcanceKm":5 }' \
  http://localhost:8080/sensores

# 2. Ponto de foco
curl -X POST -H "Content-Type: application/json" \
  -d '{ "sensor":{ "id":1,"tipo":"TORRE" }, "intensidade":550 }' \
  http://localhost:8080/pontos-de-foco

# 3. Agente
curl -X POST -H "Content-Type: application/json" \
  -d '{ "nome":"Equipe Alpha","baseOperacional":"Posto X" }' \
  http://localhost:8080/agentes

# 4. Aceitar alerta
curl -X POST "http://localhost:8080/alertas/1/aceitar?agenteId=1"

# 5. Encerrar ocorrência
curl -X PUT -H "Content-Type: text/plain" \
  --data "Foco extinto com linha d’água." \
  http://localhost:8080/ocorrencias/1/encerrar
```

---

## 🧪 Roteiro de teste rápido

1. **POST /sensores** → cadastra `T-001`
2. **POST /pontos-de-foco** → intensidade = 550
3. **POST /agentes** → “Equipe Alpha”
4. **POST /alertas/1/aceitar?agenteId=1\`**
5. **PUT /ocorrencias/1/encerrar** (body: `"Foco extinto …"`)
6. **GET /relatorios?inicio=2025-05-01\&fim=2025-06-30\`**

Tudo deve responder “200/201”.

---

## 📂 Estrutura

```
src/main/java/br/com/queimadas/alertas
 ├─ config/
 ├─ controller/
 ├─ service/
 ├─ domain/
 │   ├─ entity/
 │   ├─ factory/
 │   ├─ singleton/
 │   └─ vo/
 └─ repository/
```

---

## 🔭 Possíveis evoluções

* JWT + Spring Security
* Docker Compose com Oracle XE
* WebSocket para push de alertas
* Integração com satélites INPE

---

## 🎥 Vídeo

> **Link**: [https://www.youtube.com/watch?v=VReZhKCbAOs](https://www.youtube.com/watch?v=VReZhKCbAOs) — 5 min demonstrando o fluxo completo.

---

## 📜 Licença

MIT — veja arquivo [`LICENSE`](LICENSE).

> *“Combater queimadas também é um desafio de software.”* 🚒✨

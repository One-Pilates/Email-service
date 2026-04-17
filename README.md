# Email Service - OnePilates

Microservico responsavel por consumir mensagens de uma fila RabbitMQ e enviar emails HTML transacionais da plataforma OnePilates.

## Visao Geral

Este servico recebe eventos de notificacao via fila, identifica o tipo de email solicitado e monta o HTML correspondente para envio por SMTP.

Fluxo resumido:

1. Mensagem JSON chega na fila `email.queue`.
2. O consumer desserializa para `EmailRequestDTO`.
3. O use case escolhe o handler com base em `typeEmail`.
4. O handler valida payload e cria a entidade de email (HTML).
5. O dispatcher encaminha para o gateway de envio.
6. O gateway envia via `JavaMailSender`.

## Stack Tecnologica

- Java 21
- Spring Boot 4.0.5
- Spring AMQP (RabbitMQ)
- Spring Mail (SMTP)
- Maven Wrapper
- Docker Compose (RabbitMQ local)

## Arquitetura do Projeto

O projeto segue uma separacao por camadas:

- `application`
- `handler`: um handler por tipo de email
- `usecase`: regra de roteamento para o handler correto
- `dispacher`: orquestra o envio para o gateway
- `domain`
- `entity`: geracao e validacao do corpo HTML dos emails
- `enums`: tipos de email suportados
- `gatway`: contrato de envio de email
- `infrastructure`
- `consumer`: listener da fila RabbitMQ
- `config`: declaracao da fila
- `dto`: contrato recebido da fila
- `gatwayImplements`: implementacao SMTP

## Tipos de Email Suportados

Valores aceitos em `typeEmail`:

- `PRIMEIRO_ACESSO`
- `CODIGO_ACESSO`
- `AULA_CRIADA`
- `AULA_ATUALIZADA`
- `AULA_CANCELADA`

## Contrato da Mensagem (Fila)

Formato base esperado na fila:

```json
{
	"typeEmail": "PRIMEIRO_ACESSO",
	"destinatario": "professor@onepilates.com",
	"payload": {}
}
```

### 1) PRIMEIRO_ACESSO

Campos esperados em `payload`:

- `nomeFuncionario`
- `senhaTemporaria`

Exemplo:

```json
{
	"typeEmail": "PRIMEIRO_ACESSO",
	"destinatario": "colaborador@onepilates.com",
	"payload": {
		"nomeFuncionario": "Ana Paula",
		"senhaTemporaria": "Tmp@1234"
	}
}
```

### 2) CODIGO_ACESSO

Campos esperados em `payload`:

- `nomeFuncionario`
- `codigo`

Exemplo:

```json
{
	"typeEmail": "CODIGO_ACESSO",
	"destinatario": "colaborador@onepilates.com",
	"payload": {
		"nomeFuncionario": "Ana Paula",
		"codigo": "845921"
	}
}
```

### 3) AULA_CRIADA

Campos esperados em `payload`:

- `nomeProfessor`
- `nomesDosAlunos` (lista)
- `dataHoraAgendamento` (ISO-8601, ex: `2026-04-16T14:30:00`)
- `nomeSala`
- `nomeEspecialidade`

Exemplo:

```json
{
	"typeEmail": "AULA_CRIADA",
	"destinatario": "professor@onepilates.com",
	"payload": {
		"nomeProfessor": "Carlos",
		"nomesDosAlunos": ["Julia", "Marina"],
		"dataHoraAgendamento": "2026-04-16T14:30:00",
		"nomeSala": "Sala 1",
		"nomeEspecialidade": "Pilates Solo"
	}
}
```

### 4) AULA_ATUALIZADA

Campos esperados em `payload`:

- `nomeProfessor`
- `nomesAlunos` (lista)
- `dataHoraAgendamento` (ISO-8601)
- `nomeSala`
- `nomeEspecialidade`

Exemplo:

```json
{
	"typeEmail": "AULA_ATUALIZADA",
	"destinatario": "professor@onepilates.com",
	"payload": {
		"nomeProfessor": "Carlos",
		"nomesAlunos": ["Julia", "Marina"],
		"dataHoraAgendamento": "2026-04-16T16:00:00",
		"nomeSala": "Sala 2",
		"nomeEspecialidade": "Pilates Aparelhos"
	}
}
```

### 5) AULA_CANCELADA

Campos esperados em `payload`:

- `nomeProfessor`
- `dataHoraAgendamento` (ISO-8601)
- `nomeSala`
- `nomeEspecialidade`

Exemplo:

```json
{
	"typeEmail": "AULA_CANCELADA",
	"destinatario": "professor@onepilates.com",
	"payload": {
		"nomeProfessor": "Carlos",
		"dataHoraAgendamento": "2026-04-16T16:00:00",
		"nomeSala": "Sala 2",
		"nomeEspecialidade": "Pilates Aparelhos"
	}
}
```

## Configuracao

Arquivo atual: `email_service/src/main/resources/application.properties`

Principais propriedades:

- `server.port=8081`
- `spring.rabbitmq.host=localhost`
- `spring.rabbitmq.port=5672`
- `spring.rabbitmq.username=guest`
- `spring.rabbitmq.password=guest`
- `app.rabbitmq.email-queue=email.queue`
- `spring.mail.host=smtp.gmail.com`
- `spring.mail.port=587`
- `spring.mail.username=<usuario-smtp>`
- `spring.mail.password=<senha-ou-app-password>`
- `app.mail.from=no-reply@onepilates.com`

### Recomendacao de seguranca

Nao versionar credenciais reais no repositorio. Use variaveis de ambiente ou gerenciamento de segredo.

Exemplo de propriedades usando variaveis:

```properties
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
app.mail.from=${MAIL_FROM:no-reply@onepilates.com}
```

## Como Executar Localmente

### 1) Subir RabbitMQ

Na pasta `email_service`:

```bash
docker compose up -d
```

RabbitMQ Management:

- URL: `http://localhost:15672`
- Usuario: `guest`
- Senha: `guest`

### 2) Rodar a aplicacao

Na pasta `email_service`:

```bash
./mvnw spring-boot:run
```

No Windows:

```bat
mvnw.cmd spring-boot:run
```

### 3) Executar testes

```bash
./mvnw test
```

No Windows:

```bat
mvnw.cmd test
```

## Observacoes Importantes

- O servico e orientado a eventos (fila), portanto nao ha endpoint REST de envio de email neste projeto.
- A fila consumida esta fixa no listener como `email.queue`.
- O parse de data usa `LocalDateTime.parse`, entao o formato deve ser ISO-8601 (`yyyy-MM-ddTHH:mm:ss`).

## Troubleshooting Rapido

- Erro de autenticacao SMTP: verifique usuario/senha SMTP e, em Gmail, utilize App Password quando necessario.
- Mensagens nao consumidas: confirme RabbitMQ ativo, fila `email.queue` existente e conectividade em `localhost:5672`.
- Falha ao parsear payload: revise os nomes dos campos no JSON e confirme valor valido em `typeEmail`.

## Melhorias Recomendadas

- Externalizar totalmente segredos para variaveis de ambiente.
- Adicionar testes unitarios por handler (validacao de payload e conteudo gerado).
- Padronizar chaves de payload entre `AULA_CRIADA` e `AULA_ATUALIZADA`.
- Adotar logging estruturado no lugar de `System.out.println`.
- Implementar dead-letter queue para mensagens invalidas.

## Licenca

Este projeto utiliza licenca proprietaria da OnePilates.

Consulte o arquivo [LICENSE](LICENSE).


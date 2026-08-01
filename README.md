# notificacao

API REST responsável pelo **envio de notificações por e-mail**, utilizando **Spring Mail** e **Thymeleaf** para geração de templates HTML dinâmicos.

Este serviço faz parte do ecossistema **Agendador de Tarefas**, sendo responsável pelo envio das notificações relacionadas aos eventos agendados pelos usuários.

## Tecnologias

* Java 21
* Spring Boot 3.5.9
* Spring Web
* Spring Mail
* Thymeleaf
* Lombok
* SonarQube
* Gradle

## Melhorias implementadas

* Envio de e-mails em **HTML**
* Templates dinâmicos com **Thymeleaf**
* Suporte a notificações baseadas na **API Comunicação**
* Configuração de **SonarQube** para análise estática
* Workflows de automação com **GitHub Actions**

## Endpoints

Base URL:

```http
/email
```

| Método | Endpoint | Descrição                     |
| ------ | -------- | ----------------------------- |
| POST   | `/email` | Enviar notificação por e-mail |

### Exemplo de envio

```json
{
  "nomeDestinatario": "João Silva",
  "emailDestinatario": "joao@email.com",
  "mensagem": "Sua tarefa foi agendada com sucesso.",
  "dataHoraEnvio": "2026-08-01 14:30:00"
}
```

## Funcionamento do envio

O serviço utiliza **JavaMailSender** para comunicação com o servidor SMTP e **Thymeleaf** para processar o template HTML `notificacao.html`, inserindo dinamicamente os dados do destinatário e da mensagem antes do envio.

## Regras de negócio

* O e-mail do destinatário é obrigatório
* O template HTML é processado antes do envio
* O remetente é configurado através do `application.properties` ou `application.yml`
* Falhas de envio geram **EmailException**

## Tratamento de exceções

* `EmailException` → erros relacionados ao envio de e-mail e processamento da mensagem

## Testes

No momento, esta API **não possui testes unitários implementados**.

## Automação / CI

O projeto possui **GitHub Actions** configurado para automação de build e integração contínua.

## Como executar

```bash
git clone https://github.com/KristianSP45/notificacao
cd notificacao
./gradlew bootRun
```

A aplicação ficará disponível em:

```
http://localhost:8082
```

## Swagger

Atualmente, o projeto **não possui Swagger/OpenAPI configurado**.

## Docker

Atualmente, o projeto **não possui Dockerfile nem Docker Compose configurados**.

## Observações

* Projeto desenvolvido durante um **curso prático de Spring Boot e Microsserviços**, acompanhando as aulas e realizando implementações junto à instrutora.
* Originalmente o serviço tinha como objetivo enviar notificações relacionadas às tarefas do ecossistema.
* Posteriormente, foi **adaptado para funcionar também com a API Comunicação**, permitindo o envio de mensagens mais genéricas além das notificações de tarefas.
* Este serviço é responsável pelo **envio centralizado de notificações por e-mail** do ecossistema da aplicação.

## Autor

**Kristian Pessoa**

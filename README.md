# Concurso Gastronômico 🍴

Este projeto é uma aplicação para um **Concurso Gastronômico**, onde os usuários podem avaliar pratos específicos de restaurantes participantes, atribuindo notas e deixando descrições para cada avaliação. O objetivo é promover uma competição saudável e identificar os melhores pratos e restaurantes do concurso com base nas avaliações dos usuários.

## 📋 Funcionalidades

- **Usuários**
  - Cadastro de usuários com CPF, email e nome.
  - Identificação única por ID.

- **Restaurantes**
  - Cadastro de restaurantes participantes, com nome, endereço, nota média e quantidade de avaliações.

- **Pratos**
  - Cadastro de pratos vinculados aos restaurantes, com nome, descrição, preço, nota média e quantidade de avaliações.

- **Avaliações**
  - Avaliação de restaurantes, com descrição e nota.
  - Avaliação de pratos, com descrição e nota.

- **Relatórios**
  - Apuração das notas médias dos pratos e restaurantes.
  - Identificação dos pratos e restaurantes mais bem avaliados.

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot
- **Banco de Dados**: MySQL
- **Frontend**: Angular/React/Ionic (sugestões para futuras implementações).
- **Padrões de Desenvolvimento**:
  - Arquitetura MVC
  - Uso de DTOs para transporte seguro e eficiente de dados
  - Princípios SOLID

## 🗂️ Diagrama do Banco de Dados

Abaixo está o modelo de dados utilizado no projeto:
[https://github.com/user-attachments/assets/a0a22399-b627-44a5-ac53-b66768e5f0a1](https://github.com/Eduardogarccia/concurso-gastronomico-prefeitura-java/issues/1#issue-2703027133)

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java**: Versão 17 ou superior.
- **MySQL**: Banco de dados relacional.
- **IDE**: Eclipse, IntelliJ ou outra compatível com Spring Boot.
- **Postman** ou **Swagger** para testar as APIs.

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/concurso-gastronomico.git

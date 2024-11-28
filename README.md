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
<img width="724" alt="Captura de Tela 2024-11-26 às 16 20 57" src="https://github.com/user-attachments/assets/45721ab3-8b90-4f4e-b1d7-fd70212774ee">

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
  ```

2. Crie o BD concurso no MySQL:

  ```bash
   create database concurso
  ```

3. Ajuste o arquivo application.properties do projeto:

   ```bash
   spring.datasource.username=(seu user)
   spring.datasource.password=(sua senha)
  ```

4. Rode o projeto "Run As Spring Boot App"

5. Abra o Swegger: http://localhost:8080/swagger-ui.html

6. Passo a Passo dos endpoints:
- Comece criando um User
- Crie um Restaurante (mantenha os campos nota e qtdAvaliacoes iguais a 0)
- Crie um Prato e use o id de um restaurante existente par referenciar (mantenha os campos nota e qtdAvaliacoes iguais a 0)
- Você já pode criar avaliações de pratos ou restaurantes, usando o id do user e o id do prato ou restaurante. 



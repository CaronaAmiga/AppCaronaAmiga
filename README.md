# 🚗 Carona Amiga Universitária - Plataforma de Mobilidade

Esta é a especificação técnica e repositório central do projeto **Carona Amiga Universitária**. A aplicação foi idealizada para conectar estudantes que possuem veículos próprios a colegas que necessitam de transporte, otimizando o deslocamento acadêmico diário.

O sistema funciona como uma rede colaborativa privada, focada na segurança, economia e integração da comunidade universitária.

---

## 💻 Escopo do Sistema

A plataforma foi projetada para operar em ambiente Web e/ou Mobile, dividindo-se em dois perfis principais de uso que interagem de forma dinâmica: **Motorista** e **Passageiro**.

### 🔑 Funcionalidades Principais

*   **Autenticação Restrita:** Sistema de cadastro que prioriza o vínculo com a instituição de ensino (ex: uso de e-mail acadêmico ou matrícula).
*   **Gestão de Rotas (Motorista):** Permite cadastrar o ponto de partida, horários de saída/retorno, dias da semana e a quantidade de vagas disponíveis no veículo.
*   **Busca Inteligente (Passageiro):** Filtros de busca por proximidade geográfica, horários de aula e compatibilidade de rotas.
*   **Fluxo de Agendamento:** Solicitação de vaga por parte do passageiro e aprovação mútua para confirmação da carona.
*   **Divisão de Custos:** Sugestão automática de rateio de despesas (combustível/pedágio) com base na distância percorrida.
*   **Comunicação Direta:** Canal de mensagens interno para que os usuários combinem pontos exatos de embarque e desembarque.
*   **Reputação e Segurança:** Histórico de viagens e sistema de avaliações após o trajeto para garantir a confiabilidade mútua.

---

## 🏗️ Arquitetura Conceitual

O projeto é estruturado de forma modular para garantir que as regras de negócio funcionem independentemente da interface ou do banco de dados utilizado:

*   **Módulo de Usuários:** Gerencia perfis, dados de contato e veículos cadastrados.
*   **Módulo de Caronas:** Controla o ciclo de vida de uma viagem (Agendada, Em Andamento, Concluída, Cancelada).

---


## 🛠️ Tecnologias e Ferramentas

O ecossistema de desenvolvimento da aplicação é composto pelas seguintes tecnologias:

*   **Ambiente de Desenvolvimento:** Java ☕
*   **Framework Principal:** Spring 🍃 
*   **Banco de Dados:** MySQL 🐬

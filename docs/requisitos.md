## 📋 Requisitos do Projeto

Para garantir o escopo alinhado e o correto desenvolvimento da plataforma, foram levantados os seguintes requisitos divididos entre funcionais e não funcionais.

### ⚙️ Requisitos Funcionais (RF)

*   **RF-001 [Cadastro de Usuário]:** O sistema deve permitir o cadastro de usuários exigindo nome, e-mail institucional, senha e matrícula acadêmica.
*   **RF-002 [Gerenciamento de Perfil]:** O usuário deve poder alternar seu perfil ativo entre "Motorista" e "Passageiro" a qualquer momento dentro da plataforma.
*   **RF-003 [Cadastro de Veículo]:** O usuário com perfil de motorista deve poder cadastrar os dados do seu veículo (modelo, cor, placa e número de vagas disponíveis).
*   **RF-004 [Oferta de Carona]:** O motorista deve ser capaz de criar uma oferta de carona especificando a rota (origem e campus de destino), data, horário de partida e valor sugerido de rateio.
*   **RF-005 [Busca de Caronas]:** O passageiro deve conseguir buscar caronas disponíveis filtrando por campus de destino, proximidade geográfica da partida e horário de chegada.
*   **RF-006 [Solicitação de Vaga]:** O passageiro deve poder solicitar uma vaga em uma carona disponível.
*   **RF-007 [Aprovação de Carona]:** O motorista deve receber a solicitação do passageiro e ter a opção de aceitar ou recusar o pedido.
*   **RF-008 [Cálculo de Custos]:** O sistema deve calcular automaticamente uma sugestão de divisão de custos (combustível) com base na quilometragem estimada do trajeto.
*   **RF-009 [Sistema de Avaliação]:** Após a conclusão da viagem, tanto o motorista quanto o passageiro devem poder se avaliar mutuamente através de uma nota (1 a 5 estrelas) e comentário opcional.
*   **RF-010 [Histórico de Viagens]:** O usuário deve ter acesso ao histórico de todas as caronas que ele ofereceu ou participou.

### 🔒 Requisitos Não Funcionais (RNF)

*   **RNF-001 [Segurança/Autenticação]:** O sistema deve criptografar as senhas dos usuários no banco de dados e gerenciar as sessões de forma seguras.
*   **RNF-002 [Persistência de Dados]:** Todos os dados de usuários, veículos, rotas e históricos devem ser armazenados de forma relacional e segura em um banco de dados MySQL.
*   **RNF-003 [Segurança de Acesso]:** Apenas usuários com e-mails institucionais validados (da comunidade universitária) devem ter permissão para acessar os recursos internos do sistema.
*   **RNF-004 [Usabilidade]:** A interface da aplicação deve ser responsiva e fluida.
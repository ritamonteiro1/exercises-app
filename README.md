Este app é o desafio do estágio em Desenvolvimento Android da empresa Leal Apps. 
No aplicativo é possível cadastrar uma lista de treino e também uma lista de exercício, associada a cada treino. 
Para cada treino é possível criar, editar e deletar, assim como para cada exercício.

Neste app foi utilizado:
- o Firebase Firestore para a realização do CRUD (criação, listagem, atualização, remoção) das listas de exercício e de treino; 
- o Firebase Storage para o armazenamento das imagens; 
- o Firebase Authentication para autenticação de usuários;
- o Glide para download e cache das imagens;
- os componentes do Material design para a construção dos layouts do app.

1. Tela login:
- para fazer o login utilize o email: teste@leal.com.br e a senha: 123456;
- nessa tela foram tratados os casos de erro em que o email e a senha foram inválidos.

2. Tela de listagem dos treinos:
- nesta tela é possível visualizar a lista de treinos com as características de cada um;
- deletar um treino ao clicar no ícone da lixeira;
- ser direcionado para a tela de editar treino, clicando no ícone de editar;
- ser direcionado para a tela de criar novo treino ao clicar no botão criar novo treino;
- e por último é possível clicar em um treino e visualizar sua lista de exercício.

3.Tela de editar um treino:
- nesta tela é possível editar a descrição de um treino.

4. Tela de criar um treino:
- nesta tela é possível criar um treino definindo sua descrição;
- a data de cadastro do treino é obtida automaticamente atráves do "Date" atual no momento que o usuário clica no botão de salvar.

5. Tela de listagem dos exercícios:
- nesta tela é possível visualizar a lista de exercício do treino selecionado;
- deletar um exercício nesta tela clicando no ícone da lixeira;
- ser direcionado para tela de editar exercício, ao clicar no ícone de editar;
- ser direcionado para tela de criar novo exercício, clicando no botão criar novo exercício;

6. Tela de editar exercício:
- nesta tela é possível editar as observações do exercício selecionado.

7. Tela de criar um exercício:
- nesta tela é possível criar um exercício definindo o seu tipo (se é aeróbico ou musculação) e suas observações.

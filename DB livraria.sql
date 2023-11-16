CREATE TABLE funcionarios (
    idFuncionario INT PRIMARY KEY auto_increment,
    nomeFuncionario VARCHAR(50),
    senhaFuncionario VARCHAR(50),
    cargo VARCHAR(30)
);

CREATE TABLE livro(
    idLivro INT PRIMARY KEY auto_increment,
    nome VARCHAR(50),
    autor VARCHAR(50),
    Capa BLOB,
    preco FLOAT	 	
);

CREATE TABLE Cliente(
    idCliente INT PRIMARY KEY auto_increment,
    nomeCliente VARCHAR(50),
    contatoCliente VARCHAR(20),
    aquisicao VARCHAR(50),
    gasto Float
);

SELECT * FROM cliente;


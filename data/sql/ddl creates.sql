CREATE DATABASE selecaoppgi
CHARACTER SET UTF8 
COLLATE utf8_general_ci;

USE selecaoppgi;

--
-- USUARIO
--

CREATE TABLE IF NOT EXISTS Usuario
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	cpf VARCHAR(14) NOT NULL DEFAULT "",
	email VARCHAR(80) NOT NULL,
	senha VARCHAR(80) NOT NULL,
	papel INT NOT NULL DEFAULT 0,

	-- campos  de controle de login
	forcaResetSenha INT NOT NULL DEFAULT 0,
	dataUltimoLogin DATETIME,
	contadorLoginFalha INT NOT NULL DEFAULT 0,
	tokenLogin VARCHAR(256) NOT NULL DEFAULT "",
	dataTokenLogin DATETIME,

	-- edital selecionado
	idEditalSelecionado INT,

	PRIMARY KEY(id)
);


--
-- EDITAL
--

CREATE TABLE IF NOT EXISTS Edital
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	status INT NOT NULL DEFAULT 0,
	json LONGTEXT,
	
	PRIMARY KEY(id)
);


--
-- RELACAO ENTRE USUARIO E EDITAL SELECIONADO
--

ALTER TABLE Usuario
ADD CONSTRAINT FOREIGN KEY(idEditalSelecionado) REFERENCES Edital(id);  


--
-- INSCRICAO EM EDITAL
--

CREATE TABLE IF NOT EXISTS Inscricao
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	idCandidato INT NOT NULL,
	cotaNegros INT NOT NULL,
	cotaDeficientes INT NOT NULL,
	
	-- homologacao
 	homologadoInicial INT NOT NULL DEFAULT 0,
 	justificativaHomologacaoInicial VARCHAR(4096),
 	homologadoRecurso INT NOT NULL DEFAULT 0,
 	justificativaHomologacaoRecurso VARCHAR(4096),
 	homologado INT NOT NULL DEFAULT 0,
 	
 	-- dispensa
 	dispensadoProvaInicial INT NOT NULL DEFAULT 0,
 	justificativaDispensaInicial VARCHAR(4096),
 	dispensadoProvaRecurso INT NOT NULL DEFAULT 0,
 	justificativaDispensaRecurso VARCHAR(4096),
 	dispensado INT NOT NULL DEFAULT 0,
 	
 	-- provas escritas
 	aprovadoProvas INT NOT NULL DEFAULT 0,
 	
 	-- inscricao em projetos
	jsonProjetos LONGTEXT NOT NULL,					-- vetor de codigos de projeto de pesquisa e intencoes de pesquisa
	
	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id),
    FOREIGN KEY(idCandidato) REFERENCES Usuario(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	codigoProvaEscrita VARCHAR(8) NOT NULL,
	presente INT NOT NULL,
	notaFinal INT,
	jsonQuestoesInicial LONGTEXT NOT NULL,			-- vetor de notas na avaliacao inicial
	jsonQuestoesRecurso LONGTEXT NOT NULL,			-- vetor de notas na avaliacao do recurso
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProvaAlinhamento
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	codigoProjetoPesquisa VARCHAR(8) NOT NULL,
 	presenteProvaOral INT,
 	justificativaNotasInicial VARCHAR(4096),
 	justificativaNotasRecurso VARCHAR(4096),
	notaFinal INT,
	jsonSubcriteriosInicial LONGTEXT NOT NULL,		-- vetor de pares de código de subcriterio e nota na avaliacao inicial
	jsonSubcriteriosRecurso LONGTEXT NOT NULL,		-- vetor de pares de código de subcriterio e nota na avaliacao por recurso
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id)
);

--
-- FASE FINAL DO PROCESSO DE SELECAO (FORA DO NOSSO ESCOPO)
--

CREATE TABLE IF NOT EXISTS InscricaoSelecao
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	codigoProjetoPesquisa VARCHAR(8) NOT NULL,
	numeroOrdem INT,
	classificado INT,
	idOrientador INT,
	idCoorientador INT,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id),
    FOREIGN KEY(idOrientador) REFERENCES Usuario(id),
    FOREIGN KEY(idCoorientador) REFERENCES Usuario(id)
);

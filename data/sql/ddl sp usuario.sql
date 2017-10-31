--
-- CRIA UM NOVO USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioInsere;
DELIMITER //
CREATE PROCEDURE UsuarioInsere(vNome VARCHAR(80), vEmail VARCHAR(80), vSenha VARCHAR(80), vPapel INT, OUT id INT)
BEGIN
	INSERT INTO Usuario (dataRegistro, dataAtualizacao, nome, email, senha, papel)
	VALUES (NOW(), NOW(), vNome, vEmail, vSenha, vPapel);

	SET id = LAST_INSERT_ID();
END //
DELIMITER ;


--
-- ATUALIZA A SENHA DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioTrocaSenha;
DELIMITER //
CREATE PROCEDURE UsuarioTrocaSenha(vId INT, vSenha VARCHAR(1024))
BEGIN
	UPDATE Usuario
	SET senha = vSenha,
	forcaResetSenha = 0
	WHERE id = VId;
END //
DELIMITER ;


--
-- REGISTRA UM LOGIN BEM SUCEDIDO DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioRegistraLoginSucesso;
DELIMITER //
CREATE PROCEDURE UsuarioRegistraLoginSucesso(vEmail VARCHAR(80))
BEGIN
	UPDATE Usuario
	SET dataUltimoLogin = NOW(),
	contadorLoginFalha = 0
	WHERE email = vEmail;
END //
DELIMITER ;


--
-- REGISTRA UM LOGIN MAL SUCEDIDO DE UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioRegistraLoginFalha;
DELIMITER //
CREATE PROCEDURE UsuarioRegistraLoginFalha(vEmail VARCHAR(80))
BEGIN
	DECLARE lTentativas INT;

	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;

	UPDATE Usuario
	SET contadorLoginFalha = contadorLoginFalha + 1
	WHERE email = vEmail;

	SELECT contadorLoginFalha
	INTO lTentativas
	FROM Usuario
	WHERE email = vEmail;

	IF lTentativas >= 3 THEN 
		UPDATE Usuario
		SET forcaResetSenha = 1
		WHERE email = vEmail;
	END IF;
	
  	COMMIT;
END //
DELIMITER ;


--
-- REGISTRA UM TOKEN DE LOGIN PARA UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioRegistraTokenResetSenha;
DELIMITER //
CREATE PROCEDURE UsuarioRegistraTokenResetSenha(vId INT, vToken VARCHAR(256))
BEGIN
	UPDATE Usuario
	SET dataAtualizacao = NOW(),
	tokenLogin = vToken,
	dataTokenLogin = NOW()
	WHERE id = vId;
END //
DELIMITER ;


--
-- MUDA O EDITAL SELECIONADO POR UM USUARIO
--

DROP PROCEDURE IF EXISTS UsuarioMudaEditalSelecionado;
DELIMITER //
CREATE PROCEDURE UsuarioMudaEditalSelecionado(vIdUsuario INT, vIdEdital INT)
BEGIN
	UPDATE Usuario
	SET dataAtualizacao = NOW(),
	idEditalSelecionado = vIdEdital
	WHERE id = vIdUsuario;
END //
DELIMITER ;



SET SQL_SAFE_UPDATES = 0;
delete from InscricaoProvaAlinhamento;
delete from InscricaoProvaEscrita;
delete from Inscricao;
delete from Usuario where nome like 'Fulano%';

#
# Nova Inscricao
#

CALL UsuarioInsere("Fulano 0001", "fulano0001@somewhere.com", "$2a$10$HflP3AJrzwM.he3.gW78a.FZvW6uVpfSiwWGmVVwTTQL78REd.6UO", 0, @id);

INSERT INTO Inscricao(dataRegistro, dataAtualizacao, idEdital, idCandidato, cotaNegros, cotaDeficientes, jsonProjetos)
SELECT NOW(), NOW(), 7, id, 1, 0, '[{\"codigo\":\"OPER\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"IARS\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"SBSE\",\"intencoes\":\"minhas intencoes de pesquisa\"}]'
FROM Usuario WHERE nome = 'Fulano 0001';

SET @idInscricao = LAST_INSERT_ID();

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'FSI', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'ING', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'RDP', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'EDG', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'ENS', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'OPER', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'IARS', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'SBSE', 0, 0, '', '');

#
# Nova Inscricao
#

CALL UsuarioInsere("Fulano 0002", "fulano0002@somewhere.com", "$2a$10$HflP3AJrzwM.he3.gW78a.FZvW6uVpfSiwWGmVVwTTQL78REd.6UO", 0, @id);

INSERT INTO Inscricao(dataRegistro, dataAtualizacao, idEdital, idCandidato, cotaNegros, cotaDeficientes, jsonProjetos)
SELECT NOW(), NOW(), 7, id, 0, 0, '[{\"codigo\":\"TRAN\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"GOTI\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"RECO\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"OPER\",\"intencoes\":\"minhas intencoes de pesquisa\"}]'
FROM Usuario WHERE nome = 'Fulano 0002';

SET @idInscricao = LAST_INSERT_ID();

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'FSI', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'ING', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'RDP', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'TRAN', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'GOTI', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'RECO', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'OPER', 0, 0, '', '');

#
# Nova Inscricao
#

CALL UsuarioInsere("Fulano 0003", "fulano0003@somewhere.com", "$2a$10$HflP3AJrzwM.he3.gW78a.FZvW6uVpfSiwWGmVVwTTQL78REd.6UO", 0, @id);

INSERT INTO Inscricao(dataRegistro, dataAtualizacao, idEdital, idCandidato, cotaNegros, cotaDeficientes, jsonProjetos)
SELECT NOW(), NOW(), 7, id, 1, 0, '[{\"codigo\":\"CHIC\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"JOGO\",\"intencoes\":\"minhas intencoes de pesquisa\"}]'
FROM Usuario WHERE nome = 'Fulano 0003';

SET @idInscricao = LAST_INSERT_ID();

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'FSI', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'ING', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'CHIC', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'JOGO', 0, 0, '', '');

#
# Nova Inscricao
#

CALL UsuarioInsere("Fulano 0004", "fulano0004@somewhere.com", "$2a$10$HflP3AJrzwM.he3.gW78a.FZvW6uVpfSiwWGmVVwTTQL78REd.6UO", 0, @id);

INSERT INTO Inscricao(dataRegistro, dataAtualizacao, idEdital, idCandidato, cotaNegros, cotaDeficientes, jsonProjetos)
SELECT NOW(), NOW(), 7, id, 1, 1, '[{\"codigo\":\"HEUR\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"JOGO\",\"intencoes\":\"minhas intencoes de pesquisa\"},{\"codigo\":\"PROC\",\"intencoes\":\"minhas intencoes de pesquisa\"}]'
FROM Usuario WHERE nome = 'Fulano 0004';

SET @idInscricao = LAST_INSERT_ID();

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'FSI', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'ING', 0, 0, '', '');

INSERT INTO InscricaoProvaEscrita(idInscricao, codigoProvaEscrita, presente, notaFinal, jsonQuestoesInicial, jsonQuestoesRecurso)
VALUES (@idInscricao, 'EDG', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'HEUR', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'JOGO', 0, 0, '', '');

INSERT INTO InscricaoProvaAlinhamento(idInscricao, codigoProjetoPesquisa, presenteProvaOral, notaFinal, jsonSubcriteriosInicial, jsonSubcriteriosRecurso)
VALUES (@idInscricao, 'PROC', 0, 0, '', '');


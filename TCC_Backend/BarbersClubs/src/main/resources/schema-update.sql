-- Remover as restrições de chave estrangeira da tabela MensagensChat
ALTER TABLE MensagensChat DROP CONSTRAINT IF EXISTS FK_MensagensChat_Remetente;
ALTER TABLE MensagensChat DROP CONSTRAINT IF EXISTS FK_MensagensChat_Destinatario;

-- Recriar a tabela sem restrições de chave estrangeira
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MensagensChat]') AND type in (N'U'))
BEGIN
    DROP TABLE [dbo].[MensagensChat];
END

CREATE TABLE [dbo].[MensagensChat] (
  id INTEGER PRIMARY KEY IDENTITY,
  mensagem VARCHAR(255) NOT NULL,
  lida BIT DEFAULT 0,
  mensagemBot BIT DEFAULT 0,
  iniciadoEm DATETIME DEFAULT CURRENT_TIMESTAMP,
  remetente_id INTEGER NOT NULL,
  destinatario_id INTEGER NOT NULL
);

-- Inserir mensagens de exemplo
INSERT INTO MensagensChat (mensagem, remetente_id, destinatario_id) 
VALUES
('Olá, gostaria de agendar um horário', 1, 1),
('Qual horário você prefere?', 2, 2),
('Obrigado pelo excelente atendimento!', 3, 3);
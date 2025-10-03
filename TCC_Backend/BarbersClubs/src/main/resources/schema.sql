-- Verificar se a tabela MensagensChat existe e criar se não existir
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MensagensChat]') AND type in (N'U'))
BEGIN
    CREATE TABLE [dbo].[MensagensChat](
        [id] [int] IDENTITY(1,1) NOT NULL,
        [mensagem] [nvarchar](max) NULL,
        [lida] [bit] NOT NULL DEFAULT 0,
        [mensagemBot] [bit] NOT NULL DEFAULT 0,
        [iniciadoEm] [datetime2](7) NULL DEFAULT GETDATE(),
        [remetente_id] [int] NULL,
        [destinatario_id] [int] NULL,
        CONSTRAINT [PK_MensagensChat] PRIMARY KEY CLUSTERED ([id] ASC)
    );
END

-- Verificar se existem clientes e criar um admin se não existir
IF NOT EXISTS (SELECT * FROM Cliente WHERE id = 1)
BEGIN
    INSERT INTO Cliente (nome, email, senha) VALUES ('Admin', 'admin@barbershop.com', 'admin123');
END
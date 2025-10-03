-- PostgreSQL Database Creation
DROP DATABASE IF EXISTS bd_barbersclub;
CREATE DATABASE bd_barbersclub;

\c bd_barbersclub;

-- Admin table
CREATE TABLE Admin (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(149) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  senha VARCHAR(25) NOT NULL
);

-- Barbershop table with RazorMap fields
CREATE TABLE Barbearia (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL, 
  descricao VARCHAR(500), 
  endereco VARCHAR(255) NOT NULL,
  telefone VARCHAR(20),
  horarioAbertura TIME DEFAULT '09:00',
  horarioFechamento TIME DEFAULT '20:00',
  diasFuncionamento VARCHAR(100) DEFAULT 'Segunda a Sábado',
  latitude DECIMAL(10, 8),
  longitude DECIMAL(11, 8),
  fotoBarbearia TEXT,
  ativo BOOLEAN DEFAULT TRUE,
  parceira BOOLEAN DEFAULT FALSE,
  dataParceria TIMESTAMP,
  planoAssinatura VARCHAR(20) DEFAULT 'Básico',
  admin_id INT, 
  FOREIGN KEY (admin_id) REFERENCES Admin(id)
);

-- Barbers table
CREATE TABLE Barbeiro (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  senha VARCHAR(25) NOT NULL,
  biografia VARCHAR(512),
  especialidades VARCHAR(500),
  tempoExperiencia INT DEFAULT 0,
  mediaAvaliacao DECIMAL(3,2) DEFAULT 0,
  disponibilidade BOOLEAN DEFAULT TRUE,
  horarioInicial TIME DEFAULT '09:00',
  horarioFinal TIME DEFAULT '18:00',
  criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  atualizadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
  barbearia_id INTEGER, 
  FOREIGN KEY (barbearia_id) REFERENCES Barbearia(id)
);

-- Client table
CREATE TABLE Cliente (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(149) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  senha VARCHAR(25) NOT NULL,
  nivelFidelidade VARCHAR(10), 
  telefone VARCHAR(15),
  fotoPerfil BYTEA,
  criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  atualizadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Services table
CREATE TABLE Servico (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  descricao VARCHAR(255),
  duracao INTEGER NOT NULL,
  preco DECIMAL(5,2) NOT NULL,
  foto BYTEA,
  image_url TEXT,
  categoria VARCHAR(20) CHECK(categoria IN ('Corte de cabelo', 'Barba', 'Combo', 'Especial')) DEFAULT 'Corte de cabelo',
  pontosGanhos INT DEFAULT 0,
  ativo BOOLEAN DEFAULT TRUE
);

-- Appointments table
CREATE TABLE Agendamento (
  id SERIAL PRIMARY KEY,
  dataAgendamento TIMESTAMP NOT NULL,
  statusAgendamento VARCHAR(50), 
  descricao VARCHAR(500),
  criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  cliente_id INT NOT NULL,
  barbeiro_id INT NOT NULL,
  servico_id INT NOT NULL,
  FOREIGN KEY (cliente_id) REFERENCES Cliente(id),
  FOREIGN KEY (barbeiro_id) REFERENCES Barbeiro(id),
  FOREIGN KEY (servico_id) REFERENCES Servico(id)
);

-- Fidelity points table
CREATE TABLE PontosFidelidade (
  id SERIAL PRIMARY KEY,
  pontos INTEGER NOT NULL,
  tipoTransacao VARCHAR(50), 
  descricao VARCHAR(500),
  criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  referencia_id INT,
  cliente_id INTEGER NOT NULL,
  FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

-- Chat messages table
CREATE TABLE MensagensChat (
  id SERIAL PRIMARY KEY,
  mensagem VARCHAR(255) NOT NULL,
  lida BOOLEAN DEFAULT FALSE,
  mensagemBot BOOLEAN DEFAULT FALSE,
  iniciadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  remetente_id INTEGER NOT NULL,
  destinatario_id INTEGER NOT NULL,
  FOREIGN KEY (remetente_id) REFERENCES Cliente(id),
  FOREIGN KEY (destinatario_id) REFERENCES Admin(id)
);

-- Barbershop ratings table for RazorMap
CREATE TABLE AvaliacaoBarbearia (
  id SERIAL PRIMARY KEY,
  nota DECIMAL(3,2) NOT NULL,
  comentario VARCHAR(500),
  dataAvaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  cliente_id INT NOT NULL,
  barbearia_id INT NOT NULL,
  FOREIGN KEY (cliente_id) REFERENCES Cliente(id),
  FOREIGN KEY (barbearia_id) REFERENCES Barbearia(id)
);

-- Insert sample data

-- ADMINS
INSERT INTO Admin (nome, email, senha) 
VALUES
('Admin Master', 'admin@barbershop.com', 'admin123'),
('João Gomes', 'joao.admin@gmail.com', '102030'),
('Ana Gerente', 'ana.gerente@hotmail.com', '405060');

-- BARBEARIAS with RazorMap data
INSERT INTO Barbearia (nome, descricao, endereco, telefone, horarioAbertura, horarioFechamento, diasFuncionamento, latitude, longitude, fotoBarbearia, ativo, parceira, dataParceria, admin_id) 
VALUES
('Barbearia Premium', 'A melhor barbearia da cidade', 'Avenida Paulista, 1000 - São Paulo', '(11) 3456-7890', '09:00', '20:00', 'Segunda a Sábado', -23.5505, -46.6333, 'https://images.unsplash.com/photo-1585747860715-2ba37e788b70', TRUE, TRUE, CURRENT_TIMESTAMP, 1),
('Espaço Masculino', 'Cortes modernos e tradicionais', 'Rua das Flores, 123 - Rio de Janeiro', '(21) 3456-7890', '10:00', '19:00', 'Segunda a Sábado', -22.9068, -43.1729, 'https://images.unsplash.com/photo-1622286342621-4bd786c2447c', TRUE, TRUE, CURRENT_TIMESTAMP, 2),
('Corte & Estilo', 'Seu estilo é nossa especialidade', 'Avenida Central, 456 - Belo Horizonte', '(31) 3456-7890', '09:00', '21:00', 'Segunda a Domingo', -19.9167, -43.9345, 'https://images.unsplash.com/photo-1503951914875-452162b0f3f1', TRUE, TRUE, CURRENT_TIMESTAMP, 3);

-- Additional barbershops for RazorMap
INSERT INTO Barbearia (nome, descricao, endereco, admin_id, telefone, horarioAbertura, horarioFechamento, diasFuncionamento, latitude, longitude, fotoBarbearia, ativo, parceira, dataParceria) 
VALUES
('Barber King', 'Especialistas em cortes modernos', 'Av. Brigadeiro Faria Lima, 1500 - São Paulo', 1, '(11) 4567-8901', '08:00', '22:00', 'Segunda a Sábado', -23.5673, -46.6907, 'https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f', TRUE, TRUE, CURRENT_TIMESTAMP),
('Gentleman''s Club', 'Barbearia premium com ambiente exclusivo', 'Rua Oscar Freire, 123 - São Paulo', 1, '(11) 5678-9012', '09:00', '20:00', 'Segunda a Sábado', -23.5616, -46.6709, 'https://images.unsplash.com/photo-1599351431202-1e0f0137899a', TRUE, TRUE, CURRENT_TIMESTAMP),
('Classic Cuts', 'Tradição em cortes clássicos', 'Av. Paulista, 2000 - São Paulo', 1, '(11) 6789-0123', '09:00', '19:00', 'Segunda a Sábado', -23.5559, -46.6618, 'https://images.unsplash.com/photo-1503951914875-452162b0f3f1', TRUE, TRUE, CURRENT_TIMESTAMP),
('Barba & Navalha', 'Especialistas em barbas', 'Rua Augusta, 500 - São Paulo', 1, '(11) 7890-1234', '10:00', '20:00', 'Segunda a Sábado', -23.5489, -46.6488, 'https://images.unsplash.com/photo-1621605815971-fbc98d665033', TRUE, TRUE, CURRENT_TIMESTAMP);

-- BARBEIROS
INSERT INTO Barbeiro (nome, email, senha, biografia, especialidades, tempoExperiencia, mediaAvaliacao, barbearia_id) 
VALUES
('Pedro Silva', 'pedro.barbeiro@gmail.com', 'pedro123', 'Especialista em cortes modernos e degradês', 'Corte Masculino, Barba, Degradê, Navalhado', 5, 4.8, 1),
('João Santos', 'joao.barbeiro@hotmail.com', 'joao456', 'Mestre da barba tradicional', 'Barba Tradicional, Bigode, Corte Clássico', 8, 4.9, 2),
('Lucas Costa', 'lucas.barbeiro@yahoo.com', 'lucas789', 'Cortes criativos e modernos', 'Corte Moderno, Coloração, Styling, Penteados', 3, 4.7, 3);

-- CLIENTES
INSERT INTO Cliente (nome, email, senha, nivelFidelidade, telefone) 
VALUES
('Maria Silva', 'maria.cliente@gmail.com', 'maria123', 'Bronze', '11 98765-4321'),
('Carlos Santos', 'carlos.cliente@hotmail.com', 'carlos456', 'Prata', '21 99876-5432'),
('Ana Costa', 'ana.cliente@yahoo.com', 'ana789', 'Ouro', '31 97654-3210'),
('Seu Antonhu', 'seuantonhu@outlook.com.br', 'seViraAi', 'Prata', '89 91234-5678');

-- SERVIÇOS
INSERT INTO Servico (nome, descricao, duracao, preco, categoria, pontosGanhos, image_url) 
VALUES
('Corte Social', 'Corte de cabelo Premium com serviço de toalhas quentes', 45, 45.00, 'Corte de cabelo', 45, 'https://images.unsplash.com/photo-1599351431202-1e0f0137899a'),
('Barba Navalhada', 'Barba tradicional com corte navalhado reto', 30, 35.00, 'Barba', 35, 'https://images.unsplash.com/photo-1621605815971-fbc98d665033'),
('Barba Esculpida', 'Corte e modelagem de barba profissional', 30, 30.00, 'Barba', 30, 'https://images.unsplash.com/photo-1503951914875-452162b0f3f1'),
('Pacote VIP', 'Corte de cabelo e depilação e tratamento facial', 90, 89.00, 'Combo', 100, 'https://images.unsplash.com/photo-1622286342621-4bd786c2447c');

-- AGENDAMENTOS
INSERT INTO Agendamento (dataAgendamento, statusAgendamento, descricao, cliente_id, barbeiro_id, servico_id) 
VALUES
('2024-12-19 10:00:00', 'Pendente', 'Corte social', 1, 1, 1),
('2024-12-19 14:30:00', 'Pendente', 'Barba navalhada', 2, 1, 2),
('2024-12-18 16:00:00', 'Pendente', 'Pacote VIP', 3, 1, 4);

-- PONTOS FIDELIDADE
INSERT INTO PontosFidelidade (pontos, tipoTransacao, cliente_id) 
VALUES
(150, 'Ganho', 1),
(300, 'Ganho', 2),
(500, 'Ganho', 3),
(75, 'Ganho', 4);

-- MENSAGENS CHAT
INSERT INTO MensagensChat (mensagem, remetente_id, destinatario_id) 
VALUES
('Olá, gostaria de agendar um horário', 1, 1),
('Qual horário você prefere?', 2, 2),
('Obrigado pelo excelente atendimento!', 3, 3);

-- Avaliações de barbearias para RazorMap
INSERT INTO AvaliacaoBarbearia (nota, comentario, cliente_id, barbearia_id)
VALUES
(4.8, 'Excelente atendimento e ambiente', 1, 1),
(4.5, 'Muito bom, recomendo', 2, 2),
(5.0, 'Perfeito! Melhor barbearia da cidade', 3, 1),
(4.2, 'Bom serviço, preço justo', 4, 3);
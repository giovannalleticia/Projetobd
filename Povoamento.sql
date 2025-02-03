
INSERT INTO java_endereço (rua, numero, bairro, complemento, cep, cidade) VALUES
('Rua das Flores', '123', 'Centro', 'Apto 101', '12345678', 'São Paulo'),
('Av. Brasil', '456', 'Jardim Iguaçu', NULL, '87654321', 'Rio de Janeiro'),
('Rua das Palmeiras', '789', 'Cominese', 'Casa 2', '11223344', 'Belo Horizonte'),
('Av. Bento Munhoz', '589', 'Vila Dos Pelicanos', NULL, '12398756', 'Matinhos'),
('Rua Celeste Freitas', '124', 'Garcia', 'Apto 250', '65498732', 'Maranhão'),
('Av. Comandante Santa Rita', '25', 'Jardim Esperança', NUll, '9846523', 'Curitiba');

INSERT INTO java_conta (nome, telefone, cpf, senha, data_cadastro, id_endereco) VALUES
('João Silva', '11987654321', '12345678901', 's84518666', '2024-01-01', 1),
('Maria Oliveira', '21987654321', '98765432109', '@589625', '2024-02-01', 2),
('Carlos Souza', '31987654321', '11122233344', 'Carlos1985', '2024-03-01', 3),
('Manuel Cardoso', '4186235486', '12355688848', 'M5896@', '2024-04-03', 4),
('Laura Carvalho', '1254863369', '32566584235', 'Julia2203', '2024-04-05', 5),
('Luan Freitas', '6532558948', '12388244462', '8451@Lu_', '2024-05-12', 6);

INSERT INTO java_prestador (id_conta, serviço_prestado) VALUES
(1, 'Corte de cabelo'),
(2, 'Manicure'),
(4, 'Encanador'),
(6, 'Pedreiro');

-- Inserindo usuários
INSERT INTO java_usuario (id_conta, email) VALUES
(3, 'carlos@gmail.com'),
(5, 'Carvalho22@hotmail.com');


-- Inserindo locais de atendimento
INSERT INTO java_locais_atendimento (id_conta, cidade, bairro) VALUES
(1, 'São Paulo', 'Centro'),
(2, 'Rio de Janeiro', 'Jardins'),
(4, 'Matinhos', 'Vila dos Pelicanos'),
(4, 'Curitiba', 'Jardim America'),
(6, 'Curitiba', 'Jardim Esperança'),
(2, 'Salvador', 'Fortaleza');

-- Inserindo serviços
INSERT INTO java_servico (nome_do_serviço, descricao, duracao_estimada, preço_base, id_conta) VALUES
('Corte Masculino', 'Corte simples', '30 min', 50.0, 1),
('Pé e Mão', 'Manicure e Pedicure', '1 hora', 80.0, 2),
('Serviços Hidraulicos', 'Conserto e instação de encanamento', '2 horas', 120.0, 4),
('Construção', 'Construção, reparo e reforma de imoveis', '2 semanas', 600.0, 6);


INSERT INTO java_agendamento (data, status, hora, id_conta) VALUES
('2024-04-01', 'Confirmado', '14:00', 3),
('2024-04-02', 'Pendente', '16:00', 3),
('2024-05-01', 'Cancelado', '09:00', 5);


-- Inserindo pagamentos
INSERT INTO java_pagamento (id_agendamento, valor, forma_pagamento, status_pagamento, data) VALUES
(1, 50.0, 'Cartão de Crédito', 'Pago', '2024-04-01'),
(2, 80.0, 'PIX', 'Pendente', '2024-04-02'),
(3, 250.0, 'Boleto', 'Cancelado', '2024-05-03');


-- Inserindo avaliações
INSERT INTO java_avaliacoes (nota, comentarios, data_avaliacao, id_agendamento) VALUES
('5', 'Excelente serviço!', '2024-04-02', 1),
('4', 'Bom atendimento', '2024-04-03', 2);


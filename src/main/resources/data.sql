-- Seed data: usuarios (password = 'password' para todos, BCrypt hash)
INSERT IGNORE INTO usuarios (username, password_hash, role, active) VALUES
('user',  '$2a$10$dXJ3SW6G7P50lGmMQgel6uVKnuB53OGVG2dFGWzHNWj/dSMhB7JHm', 'USER',  true),
('vet',   '$2a$10$dXJ3SW6G7P50lGmMQgel6uVKnuB53OGVG2dFGWzHNWj/dSMhB7JHm', 'VET',   true),
('admin', '$2a$10$dXJ3SW6G7P50lGmMQgel6uVKnuB53OGVG2dFGWzHNWj/dSMhB7JHm', 'ADMIN', true);

-- Seed data: pacientes
INSERT IGNORE INTO paciente (id, nombre, especie, raza, edad, dueno) VALUES
(1, 'Firulais', 'Perro', 'Labrador',       5, 'Juan Perez'),
(2, 'Michi',    'Gato',  'Siames',          3, 'Maria Lopez'),
(3, 'Rocky',    'Perro', 'Pastor Alemán',   4, 'Pedro Soto');

-- Seed data: citas
INSERT IGNORE INTO cita (id, paciente_id, fecha, hora, motivo_consulta, veterinario_asignado, usuario) VALUES
(1, 1, '2026-03-20', '10:00', 'Vacunación',      'Veterinario 1', 'admin'),
(2, 2, '2026-03-21', '11:30', 'Control general',  'Veterinario 2', 'admin'),
(3, 3, '2026-03-22', '15:00', 'Dolor en pata',    'Veterinario 3', 'admin');

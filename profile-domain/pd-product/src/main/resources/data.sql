INSERT INTO `product` (id, code, name) VALUES
    (0, '000001', 'Cuenta de Ahorros'),
    (1, '000002', 'Tarjeta de Credito'),
    (2, '000003', 'Prestamo');

ALTER SEQUENCE PRODUCT_SEQ RESTART WITH (SELECT MAX(ID) FROM PRODUCT) + 1;
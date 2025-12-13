INSERT INTO `product` (id, code, name) VALUES
    (0, '0001', 'Cuenta de Ahorros'),
    (1, '0002', 'Tarjeta de Credito'),
    (2, '0003', 'Prestamo');

ALTER SEQUENCE PRODUCT_SEQ RESTART WITH (SELECT MAX(ID) FROM PRODUCT) + 1;
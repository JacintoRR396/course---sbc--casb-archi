INSERT INTO `customer` (id, code, name, surname, phone, address, iban) VALUES
    (0, '01','Jacinto','RR','610972145','Corredera 155','000251487'),
    (1, '02','Maria','RB','626245428','Corredera 155','000257849');

INSERT INTO `customer_product` (id, product_id, customer_id) VALUES
    (0, 0, 0),
    (1, 1, 0),
    (2, 2, 1);

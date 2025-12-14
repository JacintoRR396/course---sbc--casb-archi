INSERT INTO `transaction` (id, reference, iban_account, channel, status, amount, fee, description, date) VALUES
    (0, '6524ld', '000251487','OFICINA','Liquidada',450,2,'Consignación','2022-08-15T11:36:07'),
    (1, 'hg52487', '000251487','WEB','Rechazada',100,3,'Retiro','2022-11-10T15:20:00'),
    (2, '53254jks', '000257849','WEB','Liquidada',100,3,'Retiro','2022-11-10T15:20:00');
    
ALTER SEQUENCE TRANSACTION_SEQ RESTART WITH (SELECT MAX(ID) FROM TRANSACTION) + 1;
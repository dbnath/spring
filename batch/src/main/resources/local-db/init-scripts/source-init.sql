CREATE TABLE IF NOT EXISTS BANK_FROM(
    CUST_NO INTEGER,
    BR_NO INTEGER,
    BK_RTE_NO INTEGER,
    BK_ACC_NO VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS SUB_ACCOUNT(
    ACCT_ID INTEGER,
    BR_NO INTEGER,
    VNDR_ACCT_ID INTEGER
);

CREATE TABLE IF NOT EXISTS SEND_BANK(
    CUST_NO INTEGER,
    BK_RTE_NO INTEGER,
    BK_ACC_NO VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS RET_DIST(
    CUST_NO INTEGER,
    BK_RTE_NO INTEGER,
    BK_ACC_NO VARCHAR(30)
);


CREATE TABLE IF NOT EXISTS CUST_ACC_BK(
    ACCT_ID INTEGER,
    BK_RTE_NO INTEGER,
    BK_ACC_NO VARCHAR(30),
    BK_ACC_SEQ_NO INTEGER
);

CREATE TABLE IF NOT EXISTS TRANSFER(
    TRANSFER_ID INTEGER,
    ACCOUNT_ID_ORIGIN VARCHAR(20),
    ACCOUNT_ID_DEST VARCHAR(20),
    AMOUNT DECIMAL,
    TRANSFER_DT TIMESTAMP
);

INSERT INTO TRANSFER VALUES (1, '00121', '11121', 8.99, CURRENT_TIMESTAMP);
COMMIT;
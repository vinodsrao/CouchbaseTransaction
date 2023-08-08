package org.example;

public interface TransactionCallback {
    void doInTransaction(TransactionHolder transactionHolder);
}

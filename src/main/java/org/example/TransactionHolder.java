package org.example;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.transactions.TransactionAttemptContext;

public class TransactionHolder {
    private final TransactionAttemptContext transactionAttemptContext;
    private final Collection collection;

    public TransactionHolder(TransactionAttemptContext transactionAttemptContext, Collection collection) {
        this.transactionAttemptContext = transactionAttemptContext;
        this.collection = collection;
    }

    public TransactionAttemptContext getTransactionAttemptContext() {
        return transactionAttemptContext;
    }

    public Collection getCollection() {
        return collection;
    }
}

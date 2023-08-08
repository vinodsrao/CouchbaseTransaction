package org.example;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.transactions.TransactionGetResult;

public class WorkflowContext {
    private String field1;
    private String field2;
    private TransactionGetResult transactionGetResult;
    private TransactionHolder transactionHolder;
    private JsonObject content;

    public String getField1() {
        return field1;
    }

    public WorkflowContext setField1(String field1) {
        this.field1 = field1;
        return this;
    }

    public String getField2() {
        return field2;
    }

    public WorkflowContext setField2(String field2) {
        this.field2 = field2;
        return this;
    }

    public TransactionHolder getTransactionHolder() {
        return transactionHolder;
    }

    public WorkflowContext setTransactionHolder(TransactionHolder transactionHolder) {
        this.transactionHolder = transactionHolder;
        return this;
    }

    public TransactionGetResult getTransactionGetResult() {
        return transactionGetResult;
    }

    public WorkflowContext setTransactionGetResult(TransactionGetResult transactionGetResult) {
        this.transactionGetResult = transactionGetResult;
        return this;
    }

    public JsonObject getContent() {
        return content;
    }

    public WorkflowContext setContent(JsonObject content) {
        this.content = content;
        return this;
    }
}

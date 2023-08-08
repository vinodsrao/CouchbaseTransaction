package org.example;

import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.transactions.TransactionGetResult;
import com.couchbase.client.java.transactions.error.TransactionCommitAmbiguousException;
import com.couchbase.client.java.transactions.error.TransactionFailedException;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class SampleWorkflow implements TransactionCallback {

    Function<WorkflowContext, CompletableFuture<WorkflowContext>> validationStep = ac -> {
        CompletableFuture<WorkflowContext> stepFuture = new CompletableFuture<>();
        System.out.println(" Performing Validations ");
        stepFuture.complete(ac);
        return stepFuture;
    };

    Function<WorkflowContext, CompletableFuture<WorkflowContext>> fetchDocStep = ac -> {
        CompletableFuture<WorkflowContext> stepFuture = new CompletableFuture<>();
        TransactionGetResult tgr = ac.getTransactionHolder().getTransactionAttemptContext().get(ac.getTransactionHolder().getCollection(), "key");
        ac.setTransactionGetResult(tgr);
        stepFuture.complete(ac);
        return stepFuture;
    };

    Function<WorkflowContext, CompletableFuture<WorkflowContext>> callApiStep = ac -> {
        CompletableFuture<WorkflowContext> stepFuture = new CompletableFuture<>();
        System.out.println("API Call");
        TransactionGetResult tgr = ac.getTransactionGetResult();
        JsonObject content = tgr.contentAs(JsonObject.class);
        content.put("newField", "newValue");
        ac.setContent(content);
        stepFuture.complete(ac);
        return stepFuture;
    };

    Function<WorkflowContext, CompletableFuture<WorkflowContext>> updateDocStep = ac -> {
        CompletableFuture<WorkflowContext> stepFuture = new CompletableFuture<>();
        ac.getTransactionHolder().getTransactionAttemptContext().replace(ac.getTransactionGetResult(),ac.getContent());
        stepFuture.complete(ac);
        return stepFuture;
    };

    Function<WorkflowContext, CompletableFuture<WorkflowContext>> insertDocStep = ac -> {
        CompletableFuture<WorkflowContext> stepFuture = new CompletableFuture<>();
        ac.getTransactionHolder().getTransactionAttemptContext().insert(ac.getTransactionHolder().getCollection(), "id", JsonObject.create().put("field", "value"));
        stepFuture.complete(ac);
        return stepFuture;
    };

    @Override
    public void doInTransaction(TransactionHolder transactionHolder) {
        WorkflowContext workflowContext = new WorkflowContext();
        workflowContext.setTransactionHolder(transactionHolder);

        CompletableFuture<WorkflowContext> workFlowFuture = new CompletableFuture<>();
        workFlowFuture.complete(workflowContext);

        workFlowFuture.thenCompose(validationStep)
                .thenCompose(fetchDocStep)
                .thenCompose(callApiStep)
                .thenCompose(updateDocStep)
                .thenCompose(insertDocStep)
                .exceptionally(throwable -> {
                    if(throwable instanceof TransactionCommitAmbiguousException) {
                        //handle error
                    } else if (throwable instanceof TransactionFailedException) {
                        //handle error
                    }
                    return null;
                });

    }
}

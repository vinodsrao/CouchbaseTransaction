package org.example;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.transactions.Transactions;

import java.time.Duration;

public class App 
{
    // Update these variables to point to your Couchbase Capella instance and credentials.
    static String connectionString = "<connection string>";
    static String username = "<username>";
    static String password = "<password>";
    static String bucketName = "<bucket name>";


    public static void main( String[] args )
    {
        Cluster cluster = Cluster.connect(connectionString, ClusterOptions.clusterOptions(username, password));

        Bucket bucket = cluster.bucket(bucketName);
        bucket.waitUntilReady(Duration.ofSeconds(10));

        Transactions transactions = cluster.transactions();
        Collection collection = bucket.defaultCollection();

        SampleWorkflow workflow = new SampleWorkflow();

        transactions.run(txnCxt -> workflow.doInTransaction(new TransactionHolder(txnCxt,collection)));
    }
}

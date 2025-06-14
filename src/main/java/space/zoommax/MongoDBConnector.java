package space.zoommax;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * MongoDBConnector class
 * This class is used to connect to the MongoDB database and get the collection.
 * It uses the PojoCodecProvider to automatically map the POJO class to the MongoDB collection.
 * @version 1.0
 * @author ZooMMaX
 * @since 2024-02-20
 */
public class MongoDBConnector {

    /**
     * Get collection from the database. Use local mongodb uri
     * @return MongoCollection
     */
    public MongoCollection<?> getCollection(String collectionName, String databaseName, Object pojoClass){
        MongoClient mongoClient = MongoConnectorInstance.getInstance("mongodb://127.0.0.1:27017/?retryWrites=true&w=majority").mongoClient;
        MongoDatabase database = mongoClient.getDatabase(databaseName)
                .withCodecRegistry(MongoConnectorInstance.getInstance("mongodb://127.0.0.1:27017/?retryWrites=true&w=majority").pojoCodecRegistry);
        return database.getCollection(collectionName, pojoClass.getClass());
    }

    /**
     * Get collection from the database. Use custom mongodb uri
     * @return MongoCollection
     */
    public MongoCollection<?> getCollection(String collectionName, String databaseName, String mongodbUri, Object pojoClass){
        MongoClient mongoClient = MongoConnectorInstance.getInstance(mongodbUri).mongoClient;
        MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(MongoConnectorInstance.getInstance(mongodbUri).pojoCodecRegistry);
        return database.getCollection(collectionName, pojoClass.getClass());
    }
}

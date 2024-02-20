package ru.zoommax;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
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
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        String uri = "mongodb://127.0.0.1:27017/?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
        return database.getCollection(collectionName, pojoClass.getClass());
    }

    /**
     * Get collection from the database. Use custom mongodb uri
     * @return MongoCollection
     */
    public MongoCollection<?> getCollection(String collectionName, String databaseName, String mongodbUri, Object pojoClass){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        MongoClient mongoClient = MongoClients.create(mongodbUri);
        MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
        return database.getCollection(collectionName, pojoClass.getClass());
    }
}

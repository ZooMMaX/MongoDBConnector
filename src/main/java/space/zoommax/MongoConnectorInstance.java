package space.zoommax;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.HashMap;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnectorInstance {
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    MongoClient mongoClient;
    private static HashMap<String, MongoConnectorInstance> instances = new HashMap<>();

    private MongoConnectorInstance(String uri) {
        mongoClient = MongoClients.create(uri);
    }

    public static MongoConnectorInstance getInstance(String uri) {
        if (instances.get(uri) == null) {
            instances.put(uri, new MongoConnectorInstance(uri));
        }
        return instances.get(uri);
    }

}

package space.zoommax;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import lombok.Setter;

import static com.mongodb.client.model.Filters.eq;

@Setter
@Getter
public class ExamplePojo extends MongoDBConnector {
    private String name;
    private String surname;
    private int age;
    private String email; // unique
    private String password;
    private String phone;
    private String address;

    public ExamplePojo() {
    }

    /**
     * Get collection with the name of "users" and database of "work"
     * @return MongoCollection
     */
    @SuppressWarnings("unchecked")
    private MongoCollection<ExamplePojo> collection() {
        return (MongoCollection<ExamplePojo>) getCollection("users", "work", this);
    }

    /**
     * Check if the user exists in the database
     * @return boolean
     */
    private boolean exist() {
        return collection().find(eq("email", email)).first() != null;
    }

    /**
     * Insert the user into the database or update if it already exists
     * @return boolean
     */
    public boolean insert() {
        if (exist()) {
            return update();
        }

        final String result = collection().insertOne(this).toString();
        return result.contains("insertedId");
    }

    private boolean update() {
        final String result = collection().replaceOne(eq("email", this.email), this).toString();
        return result.contains("matchedCount=1");
    }

    public boolean delete() {
        final String result = collection().deleteOne(eq("email", this.email)).toString();
        return result.contains("deletedCount=1");
    }

    public ExamplePojo find() {
        return collection().find(eq("email", this.email)).first();
    }

    public ExamplePojo findByAddress() {
        return collection().find(eq("address", this.address)).first();
    }

}

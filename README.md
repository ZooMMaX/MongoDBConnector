# MongoDBConnector

![Maven Central](https://img.shields.io/maven-central/v/ru.zoommax/MongoDBConnector?style=plastic)
![GitHub](https://img.shields.io/github/license/ZooMMaX/MongoDBConnector?style=plastic)
[![GitHub issues](https://img.shields.io/github/issues/ZooMMaX/MongoDBConnector?style=plastic)](https://github.com/ZooMMaX/MongoBDConnector/issues)

This class is used to connect to the MongoDB database and get the collection. It uses the PojoCodecProvider to automatically map the POJO class to the MongoDB collection.

## Methods

### getCollection(String collectionName, String databaseName, Object pojoClass)

This method gets a collection from the database using a local MongoDB URI. It returns a `MongoCollection`.

### getCollection(String collectionName, String databaseName, String mongodbUri, Object pojoClass)

This method gets a collection from the database using a custom MongoDB URI. It returns a `MongoCollection`.

## Dependency

![dependency maven](https://img.shields.io/badge/DEPENDENCY-Maven-C71A36?style=plastic&logo=apachemaven)
```xml
<dependencies>
    <dependency>
        <groupId>ru.zoommax</groupId>
        <artifactId>MongoDBConnector</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

![dependency gradle](https://img.shields.io/badge/DEPENDENCY-Gradle-02303A?style=plastic&logo=gradle)
```groovy
implementation 'ru.zoommax:MongoDBConnector:1.0'
```

## Example

### ExamplePojo.java

```java
package ru.zoommax;

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
```

### ExampleUsePojo.java

```java
package ru.zoommax;

public class ExampleUsePojo {
    public static void main(String[] args) {
        //create a new ExamplePojo object
        ExamplePojo examplePojo = new ExamplePojo();

        //set the values
        examplePojo.setName("John");
        examplePojo.setSurname("Doe");
        examplePojo.setAge(30);
        examplePojo.setEmail("test@test.test");
        examplePojo.setPassword("password");
        examplePojo.setPhone("123456789");
        examplePojo.setAddress("Test street 123");

        //insert the object into the database
        System.out.println(examplePojo.insert());

        //find the object in the database
        ExamplePojo foundExamplePojo = new ExamplePojo();
        foundExamplePojo.setEmail("test@test.test");
        foundExamplePojo = foundExamplePojo.find();
        System.out.println(foundExamplePojo.getName());
        System.out.println(foundExamplePojo.getSurname());
        System.out.println(foundExamplePojo.getAge());
        System.out.println(foundExamplePojo.getEmail());
        System.out.println(foundExamplePojo.getPassword());
        System.out.println(foundExamplePojo.getPhone());
        System.out.println(foundExamplePojo.getAddress());

        //delete the object from the database
        ExamplePojo deleteExamplePojo = new ExamplePojo();
        deleteExamplePojo.setEmail("test@test.test");
        System.out.println(deleteExamplePojo.delete());
    }
}
```

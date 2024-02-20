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

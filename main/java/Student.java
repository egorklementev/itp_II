package main.java;

public class Student extends Patron {

    Student (String username, String password, String firstName, String secondName, String address, String phone) {
        super(username, password, false, firstName, secondName, address, phone, 4);
    }

    Student (User userToCopy) {
        super(userToCopy);
        setPriority(4);
        setFaculty(false);
    }

}

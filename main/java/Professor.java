package main.java;

public class Professor extends Patron {

    Professor (String username, String password, String firstName, String secondName, String address, String phone) {
        super(username, password, true, firstName, secondName, address, phone, 0);
    }

    Professor (User userToCopy) {
        super(userToCopy);
        setPriority(0);
        setFaculty(true);
    }

}

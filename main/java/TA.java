package main.java;

public class TA extends Patron {

    TA (String username, String password, String firstName, String secondName, String address, String phone) {
        super(username, password, true, firstName, secondName, address, phone, 2);
    }

    TA (User userToCopy) {
        super(userToCopy);
        setPriority(2);
        setFaculty(true);
    }

}

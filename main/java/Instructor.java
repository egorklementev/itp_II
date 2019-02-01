package main.java;

class Instructor extends Patron {

    Instructor (String username, String password, String firstName, String secondName, String address, String phone) {
        super(username, password, true, firstName, secondName, address, phone, 3);
    }

    Instructor (User userToCopy) {
        super(userToCopy);
        setPriority(3);
        setFaculty(true);
    }

}

package main.java;

class Patron extends User {

    Patron(String username, String password, boolean isFaculty, String firstName, String secondName, String address, String phone, int priority) {
        super(username, password, isFaculty, firstName, secondName, address, phone, priority);
    }

    Patron (User userToCopy) {
        super(userToCopy);
    }

}

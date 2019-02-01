package main.java;

class VisitingProfessor extends Patron {

    VisitingProfessor(String username, String password, String firstName, String secondName, String address, String phone) {
        super(username, password, false, firstName, secondName, address, phone, 1);
    }

    VisitingProfessor (User userToCopy) {
        super(userToCopy);
        setPriority(1);
        setFaculty(false);
    }

}

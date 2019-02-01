package main.java;

import java.util.ArrayList;

public class User {

    private String username;
    private String firstName;
    private String secondName;
    private String password;
    private String address;
    private String phone;
    private boolean isFaculty;
    private int priority;

    private ArrayList<Notification> notifications;
    private ArrayList<Booking> bookings; // Current documents on hands
    private Object user_id;

    User (String username, String password, boolean isFaculty) {
        this.username = username;
        this.password = password;
        bookings = new ArrayList<>();
        notifications = new ArrayList<>();
        this.isFaculty = isFaculty;
        priority = 4;
    }

    User(String username, String password, boolean isFaculty, String firstName, String secondName, String address, String phone, int priority) {
        this(username, password, isFaculty);
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.phone = phone;
        this.priority = priority;
    }

    // Constructor for getting from DataBase
    User (Object user_id,String username, String password, boolean isFaculty, String firstName, String secondName, String address, String phone, int priority) {
        this(username, password, isFaculty, firstName, secondName, address, phone, priority);
        this.user_id = user_id;
    }

    User (User userToCopy) {
        this(userToCopy.user_id, userToCopy.username, userToCopy.password, userToCopy.isFaculty, userToCopy.firstName, userToCopy.secondName, userToCopy.address, userToCopy.phone, userToCopy.priority);
        copyData(userToCopy);
    }


    /** Addition of new booking
     *  @param booking user's offer */
    void addBooking (Booking booking) { bookings.add(booking); }

    /** Returns all current bookings */
    ArrayList<Booking> getBookings () {
        return bookings;
    }

    /** Auxiliary method to copy all field from another user */
    void copyData (User user){
        setBookings(user.getBookings());
        setFaculty(user.isFaculty());
        setFirstName(user.getFirstName());
        setSecondName(user.getSecondName());
        setAddress(user.getAddress());
        setPhone(user.getPhone());
        setPriority(user.getPriority());
        setNotifications(user.getNotifications());
    }

    /** Returns all current bookings */
    void setBookings (ArrayList<Booking> bookings) {
        this.bookings = new ArrayList<>();
        this.bookings.addAll(bookings);
    }

    /** Auxiliary method that returns the number of the user debts */
    int getDebtsNum() {
        int debts = 0;
        for (int i = 0; i < bookings.size(); ++i) if (bookings.get(i).isOverdue()) debts++;
        return debts;
    }

    /** Returns the booking of the user by given title of the document. */
    Booking findBooking(String title) {
        for (int i = 0; i < bookings.size(); ++i) if (bookings.get(i).getDoc().getTitle().equals(title)) return bookings.get(i);
        return null;
    }

    /** Returns the number of requests for the unavailable documents. */
    int getRequestNum () {
        int num = 0;
        for (int i = 0; i < bookings.size(); ++i) if (!bookings.get(i).hasReceived()) num++;
        return num;
    }

    /** Replaces all notifications. */
    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = new ArrayList<>();
        this.notifications.addAll(notifications);
    }

    /** Adds new notification to the user. */
    public void notify(Notification n) {
        notifications.add(n);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) { this.password = password; }

    boolean isFaculty() {
        return isFaculty;
    }

    void setFaculty(boolean faculty) {
        isFaculty = faculty;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    Object getUser_id(){
        return user_id;
    }

    int getPriority() {
        return priority;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
}

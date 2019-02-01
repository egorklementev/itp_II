package main.java;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

/**
 * INFO:
 * All information in JSON:
 * {
 * "name" : "MongoDB",
 * "type" : "database",
 * "count" : 1,
 * "versions": [ "v3.2", "v3.0", "v2.6" ],
 * "info" : { x : 203, y : 102 }
 * }
 */

public class DataBase {
    // connect to Database
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database = mongoClient.getDatabase("library");
    private static MongoCollection<org.bson.Document> users = database.getCollection("users");
    private static MongoCollection<org.bson.Document> documents = database.getCollection("documents");
    private static MongoCollection<org.bson.Document> orders = database.getCollection("orders");

    /**
     * Add document to DataBase and set id to class
     * If document already in db, REPLACE it
     *
     * @param document what need to add
     */
    public static void addDoc(Document document) {
        // create unique id
        Object id = document.getTitle() + document.getAuthors();
        document.setDocument_id(id);

        String type = "document";
        if (document instanceof Book) type = "book";
        if (document instanceof AudioVideo) type = "av";
        if (document instanceof JournalArticle) type = "ja";

        // create Json document
        org.bson.Document docJson = new org.bson.Document("_id", id)
                .append("type", type)
                .append("title", document.getTitle())
                .append("authors", document.getAuthors())
                .append("description", document.getDescription())
                .append("publisher", document.getPublisher())
                .append("edition", document.getEdition())
                .append("year", document.getYear())
                .append("price", document.getPrice())
                .append("copies", document.getCopies())
                .append("reference", document.isReference())
                .append("best-seller", document.isBestSeller())
                .append("isOutstanding", document.isOutstanding());

        // check if already have in db this id
        Document check = getDoc(id);
        if (check != null) {
            documents.replaceOne(eq("_id", id), docJson);
        } else {
            documents.insertOne(docJson);
        }
    }

    /**
     * Get document in DataBase by id
     *
     * @param id of document
     * @return Document or null if not in DataBase
     */
    private static Document getDoc(Object id) {
        org.bson.Document docJson = documents.find(eq("_id", id)).first();

        // if not in DataBase
        if (docJson == null)
            return null;

        return jsonToDoc(docJson);
    }

    /**
     * Get all Document
     *
     * @return ArrayList with all documents
     */
    public static ArrayList<Document> getAllDoc() {
        ArrayList<Document> allDoc = new ArrayList<>();
        for (org.bson.Document json : documents.find()) {
            allDoc.add(jsonToDoc(json));
        }
        return allDoc;
    }

    /**
     * Parse org.bson.Document to class Document
     *
     * @param docJson that need to parse
     * @return new class Document
     */
    private static Document jsonToDoc(org.bson.Document docJson) {
        if (docJson != null) {
            switch (docJson.get("type").toString()) {
                case "book":
                    return new Book(docJson.get("_id"), docJson.getString("title"),
                            docJson.getString("authors"), docJson.getInteger("price"),
                            docJson.getInteger("copies"), docJson.getBoolean("reference"),
                            docJson.getString("description"), docJson.getString("publisher"),
                            docJson.getInteger("edition"), docJson.getInteger("year"),
                            docJson.getBoolean("best-seller"), docJson.getBoolean("isOutstanding"));
                case "av":
                    return new AudioVideo(docJson.get("_id"), docJson.getString("title"),
                            docJson.getString("authors"), docJson.getInteger("price"),
                            docJson.getInteger("copies"), docJson.getBoolean("reference"),
                            docJson.getString("description"), docJson.getString("publisher"),
                            docJson.getInteger("edition"), docJson.getInteger("year"),
                            docJson.getBoolean("best-seller"), docJson.getBoolean("isOutstanding"));
                case "ja":
                    return new JournalArticle(docJson.get("_id"), docJson.getString("title"),
                            docJson.getString("authors"), docJson.getInteger("price"),
                            docJson.getInteger("copies"), docJson.getBoolean("reference"),
                            docJson.getString("description"), docJson.getString("publisher"),
                            docJson.getInteger("edition"), docJson.getInteger("year"),
                            docJson.getBoolean("best-seller"), docJson.getBoolean("isOutstanding"));
            }
            return new Document(docJson.get("_id"), docJson.getString("title"),
                    docJson.getString("authors"), docJson.getInteger("price"),
                    docJson.getInteger("copies"), docJson.getBoolean("reference"),
                    docJson.getString("description"), docJson.getString("publisher"),
                    docJson.getInteger("edition"), docJson.getInteger("year"),
                    docJson.getBoolean("best-seller"), docJson.getBoolean("isOutstanding"));
        }
        return null;
    }

    /**
     * Delete document from db
     *
     * @param document to delete
     */
    public static void deleteDoc(Document document) {
        documents.deleteOne(eq("_id", document.getDocument_id()));
        deleteDocFromOrder(document);
    }

    /**
     * Delete document from order
     *
     * @param document to delete
     */
    public static void deleteDocFromOrder(Document document) {
        for (org.bson.Document order : orders.find()) {
            ArrayList<org.bson.Document> userOrder = (ArrayList<org.bson.Document>) order.get("documents");
            boolean edited = false;
            // find book in order
            for (int i = 0; i < userOrder.size(); i++) {
                // update one of request
                org.bson.Document doc = userOrder.get(i);
                if (!edited && doc.get("_id").equals(document.getDocument_id())) {
                    userOrder.remove(i);
                    edited = true;
                }
            }
            if (edited) {
                orders.updateOne(
                        eq("_id", order.get("_id")),
                        set("documents", userOrder));

            }
        }

    }

    /**
     * Add user to DataBase
     * If document already in db, REPLACE it
     *
     * @param user what need to add
     */
    public static void addUser(User user) {
        // create unique id
        Object id = user.getUsername();
        user.setUser_id(id);

        // create Json document
        org.bson.Document userJson = new org.bson.Document("_id", id)
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("address", user.getAddress())
                .append("firstName", user.getFirstName())
                .append("secondName", user.getSecondName())
                .append("phone", user.getPhone())
                .append("isFaculty", user.isFaculty())
                .append("priority", user.getPriority())
                .append("notifications", Arrays.asList());

        // check if already have in DataBase this id
        User check = getUser(id);
        if (check != null) {
            users.replaceOne(eq("_id", id), userJson);
            deleteAllUserOrders(user);
        } else {
            users.insertOne(userJson);
        }

        // parse booking to order
        for (Booking booking : user.getBookings()) {
            doOrderWithBooking(user, booking);
        }
    }

    public static ArrayList<Notification> getNotifications(Object id) {
        org.bson.Document orderJson = users.find(eq("_id", id)).first();
        ArrayList<Notification> notifications = new ArrayList<>();

        if (orderJson == null) {
            return notifications;
        }

        ArrayList list = (ArrayList) orderJson.get("notifications");
        for (Object doc : list) {
            Notification n = new Notification(((org.bson.Document) doc).getInteger("type"), ((org.bson.Document) doc).getString("document"), Date.valueOf(((org.bson.Document) doc).getString("date")), ((org.bson.Document) doc).getInteger("fine"));
            notifications.add(n);
        }

        return notifications;
    }

    /**
     * Replace all user notifications in db to users real-time notifications
     *
     * @param user who has notifications
     */
    public static void replaceNotifications(User user) {
        org.bson.Document userJson = users.find(eq("_id", user.getUser_id())).first();

        // if in db
        if (userJson != null) {
            ArrayList<org.bson.Document> notifJson = new ArrayList<>();

            for (Notification notif : user.getNotifications()) {
                notifJson.add(new org.bson.Document("type", notif.getType())
                        .append("document", notif.getDoc())
                        .append("date", notif.getDate().toString())
                        .append("fine", notif.getFine()));
            }

            users.updateOne(
                    eq("_id", user.getUser_id()),
                    set("notifications", notifJson));

        }
    }

    public static boolean findUser (User user) {
        Object id = user.getUsername();
        user.setUser_id(id);
        User check = getUser(id);
        if (check != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get user from DataBase
     *
     * @param id of user
     * @return User or null if not in DataBase
     */
    private static User getUser(Object id) {
        org.bson.Document userJson = users.find(eq("_id", id)).first();

        // if not in DataBase
        if (userJson == null) {
            return null;
        }

        return jsonToUser(userJson);
    }

    /**
     * Get all User
     *
     * @return ArrayList with all user
     */
    public static ArrayList<User> getAllUser() {
        ArrayList<User> allUser = new ArrayList<>();
        for (org.bson.Document json : users.find()) {
            allUser.add(jsonToUser(json));
        }
        return allUser;
    }

    /**
     * Parse org.bson.Document to class User
     *
     * @param userJson that need to parse
     * @return new class User
     */
    private static User jsonToUser(org.bson.Document userJson) {
        ArrayList<Booking> bookings = getAllOrderedDoc(userJson.get("_id"));
        ArrayList<Notification> notifications = getNotifications(userJson.get("_id"));

        User user = new User(userJson.get("_id"), userJson.getString("username"),
                userJson.getString("password"), userJson.getBoolean("isFaculty"),
                userJson.getString("firstName"), userJson.getString("secondName"),
                userJson.getString("address"), userJson.getString("phone"),
                userJson.getInteger("priority"));
        user.setNotifications(notifications);
        user.setBookings(bookings);
        switch (user.getPriority()) {
            case 4: {
                user = new Student(user);
                break;
            }
            case 3: {
                user = new Instructor(user);
                break;
            }
            case 2: {
                user = new TA(user);
                break;
            }
            case 1: {
                user = new VisitingProfessor(user);
                break;
            }
            case 0: {
                user = new Professor(user);
                break;
            }
        }
        return user;
    }

    /**
     * Delete user from database
     *
     * @param user to delete
     */
    public static void deleteUser(User user) {
        users.deleteOne(eq("_id", user.getUser_id()));
        deleteAllUserOrders(user);
    }

    /**
     * Make order between user and document
     *
     * @param user     take doc
     * @param document what take
     */
    public static void doOrder(User user, Document document, int duration, Date date, boolean reqBuLib, boolean reqByUser, boolean hasReceived, boolean hasRenewed) {
        // create unique id
        Object id = user.getUser_id();

        // find if user already took something
        org.bson.Document orderJson = orders.find(eq("_id", id)).first();
        if (orderJson == null) {
            orderJson = new org.bson.Document("_id", id)
                    .append("userId", user.getUser_id())
                    .append("documents", Arrays.asList(new org.bson.Document("_id", document.getDocument_id())
                            .append("reqByLib", reqBuLib)
                            .append("reqByUser", reqByUser)
                            .append("date", date.toString())
                            .append("duration", duration)
                            .append("hasReceived", hasReceived)
                            .append("hasRenewed", hasRenewed)));
            orders.insertOne(orderJson);
        } else {
            boolean hasBook = false;
            ArrayList<org.bson.Document> userOrder = (ArrayList<org.bson.Document>) orderJson.get("documents");

            // find book in order
            for (int i = 0; i < userOrder.size(); i++) {
                // update one of request
                org.bson.Document doc = userOrder.get(i);
                if (!hasBook && doc.get("_id").equals(document.getDocument_id())) {
                    org.bson.Document updated = new org.bson.Document("_id", document.getDocument_id())
                            .append("reqByLib", reqBuLib)
                            .append("reqByUser", reqByUser)
                            .append("date", date.toString())
                            .append("duration", duration)
                            .append("hasReceived", hasReceived)
                            .append("hasRenewed", hasRenewed);
                    userOrder.set(i, updated);
                    hasBook = true;
                }
            }

            if (!hasBook) {
                orders.updateOne(
                        eq("_id", id),
                        push("documents",
                                new org.bson.Document("_id", document.getDocument_id())
                                        .append("reqByLib", reqBuLib)
                                        .append("reqByUser", reqByUser)
                                        .append("date", date.toString())
                                        .append("duration", duration)
                                        .append("hasReceived", hasReceived)
                                        .append("hasRenewed", hasRenewed)));
            } else {
                orders.updateOne(
                        eq("_id", id),
                        set("documents", userOrder));
            }
        }
    }

    /**
     * Do order with booking
     *
     * @param user    that need order
     * @param booking with book and date
     */
    private static void doOrderWithBooking(User user, Booking booking) {
        doOrder(user, booking.getDoc(), booking.getDuration(), booking.getDate(), booking.hasRequestedByLib(), booking.hasRequestedByUser(), booking.hasReceived(), booking.hasRenewed());
    }

    /**
     * Delete one user all orders
     *
     * @param user that order net delete
     */
    public static void deleteAllUserOrders(User user) {
        orders.deleteOne(eq("_id", user.getUser_id()));
    }

    /**
     * Delete book from orders
     *
     * @param user     that have order
     * @param document doc to delete from order
     */
    public static void deleteOrder(User user, Document document) {
        ArrayList<Booking> oldBookings = getAllOrderedDoc(user);
        deleteAllUserOrders(user);

        for (Booking old : oldBookings) {
            if (!old.getDoc().getDocument_id().equals(document.getDocument_id())) {
                doOrderWithBooking(user, old);
            }
        }
    }

    /**
     * Get list of all order from one user
     *
     * @param user that order need get
     * @return ArrayList of all order
     */
    public static ArrayList<Booking> getAllOrderedDoc(User user) {
        // get order id
        Object id = user.getUser_id();
        return getAllOrderedDoc(id);
    }

    /**
     * Helper for getAllOrderedDoc, that found by id
     *
     * @param id of user
     * @return list of all order
     */
    private static ArrayList<Booking> getAllOrderedDoc(Object id) {
        // get order
        org.bson.Document orderJson = orders.find(eq("_id", id)).first();

        if (orderJson == null) {
            return new ArrayList<>();//no order book
        }

        ArrayList<Booking> bookings = new ArrayList<>();
        // make list of booking
        ArrayList list = (ArrayList) orderJson.get("documents");
        for (Object doc : list) {
            Object document_id = ((org.bson.Document) doc).get("_id");
            Document document = jsonToDoc(documents.find(eq("_id", document_id)).first());
            Booking booking = new Booking(document, ((org.bson.Document) doc).getInteger("duration"), Date.valueOf(((org.bson.Document) doc).getString("date")));
            if (((org.bson.Document) doc).getBoolean("reqByLib")) booking.libRequest();
            if (((org.bson.Document) doc).getBoolean("reqByUser")) booking.userRequest();
            if (((org.bson.Document) doc).getBoolean("hasReceived")) booking.setReceived(true);
            if (((org.bson.Document) doc).getBoolean("hasRenewed")) booking.setRenewed();
            bookings.add(booking);
        }
        return bookings;
    }

}
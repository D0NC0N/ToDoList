package Model.data;

import Model.IO.Pair;
import Model.IO.Trinity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private int userId;
    private String userName;

    private ArrayList<ToDoList> toDoLists;
    private Date requestDate;

    private UserData userData;
    private ToDoListData toDoListData;

    public User(String name, String password, Date requestDate) throws IOException {
        userData = new UserData().getInstance();
        toDoListData = new ToDoListData().getInstance();

        this.userName = name;
        this.userId = getUserIdByName(name, password);
        this.requestDate = requestDate;
        toDoLists = toDoListData.getToDoLists(userId, requestDate);
    }


    public void addUser(String name, String password) throws IOException {
        userData.addUser(name, password, userId);
    }

    public String getUserName() {
        return userName;
    }

    public int getUserIdByName(String name, String password) {
        this.userId = userData.getUserId(name, password);
        return userId;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<ToDoList> getToDoLists() {
        return toDoLists;
    }
}

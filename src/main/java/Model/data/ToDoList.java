package Model.data;

import Model.IO.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ToDoList {
    private int toDoListId;
    private int userId;
    private String userName;
    private Date day;

    private ArrayList<Pair<Integer, Boolean>> whoCanWatch; // список id юзеров, которые видят этот лист
    //Pair<Integer, Boolean> - int - это номер usera, boolen - это право на редактирование

    private ArrayList<BasicList> listOfBasics; // лист задач первого уровня, заполняемый по их id;
                                               // новые создаются в окне 3

    public ToDoList(int userId, String userName, Date day) throws IOException {
        toDoListId = getNewToDoListId();
        this.userId = userId;
        this.userName = userName;

        this.day = day;
        whoCanWatch = new ArrayList<>();
        whoCanWatch.add(new Pair<>(userId, true));
        listOfBasics = new ArrayList<>();
    }

    public ToDoList(int toDoListId, int userId, String userName, Date day,
                    ArrayList<Pair<Integer, Boolean>> whoCanWatch, ArrayList<BasicList> listOfBasics) {
        this.toDoListId = toDoListId;
        this.userId = userId;
        this.userName = userName;

        this.day = day;
        this.whoCanWatch = whoCanWatch;
        this.listOfBasics = listOfBasics;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getDay() {
        return day;
    }

    public void setToDoListId(int toDoListId) {
        this.toDoListId = toDoListId;
    }

    public int getToDoListId() {
        return toDoListId;
    }

    public void setListOfBasics(ArrayList<BasicList> listOfBasics) {
        this.listOfBasics = listOfBasics;
    }

    public ArrayList<BasicList> getListOfBasics() {
        return listOfBasics;
    }

    public ArrayList<Pair<Integer, Boolean>> getWhoCanWatch() {
        return whoCanWatch;
    }

    public int canYouWatch(int guestId) {
        if (guestId != userId) {
            for (int i = 0; i < getWhoCanWatch().size(); ++i) {
                if (getWhoCanWatch().get(i).getGroup() == guestId) {
                    return getWhoCanWatch().get(i).getSubgroup() ? 1 : 0;
                }
            }
        }
        return -1;
    }

    public void setWhoCanWatch(ArrayList<Pair<Integer, Boolean>> newWhoCanWatch) {
        this.whoCanWatch = newWhoCanWatch;
    }

    public int getNewToDoListId() throws IOException {
        ToDoListData toDoListData = new ToDoListData().getInstance();
        return toDoListData.toDoListsSize();
    }

    public void addNewBasicList(BasicList newBasicList) {
        listOfBasics.add(newBasicList);
    }

    public void sortBasicByName() {
        listOfBasics.sort(Comparator.comparing(BasicList::getBasicName));
    }

    public void sortBasicBySubDeadline() {
        listOfBasics.sort(Comparator.comparing(BasicList::getDeadline));
    }

    public void sortBasicByPriority() {
        listOfBasics.sort(Comparator.comparing(BasicList::getPriority));
        Collections.reverse(listOfBasics);
    }
}

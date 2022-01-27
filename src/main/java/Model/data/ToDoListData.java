package Model.data;

import Model.IO.Files;
import Model.IO.Pair;
import Model.IO.Trinity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ToDoListData {
    private ArrayList<Pair<
            Trinity<
                    Integer, // id:
                    Integer, // userId;
                    String>, // userName;
            Trinity<
                    Date, // date
                    ArrayList<Pair<Integer, Boolean>>, // whoCanWatch
                    ArrayList<Integer>> // idOfBasics
            >
            >
            toDoInfo;

    private ArrayList<Pair<
            Trinity<
                    Integer, // id:
                    String, // basicName;
                    Integer>, // priority;
            Trinity<
                    String, // deadline
                    String, // comment
                    ArrayList<String>> // subTasks
            >
            >
            basicInfo;

    private ArrayList<ToDoList> toDoLists; //страницы по датам
    String pathToToDoListBD = "C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\ListOfToDoLists.txt";

    private ArrayList<BasicList> basicLists; //задачи
    String pathToBasicListBd = "C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\ListOfBasicLists.txt";

    private volatile static ToDoListData instance;
    private Files files;

    public synchronized ToDoListData getInstance() throws IOException {
        if (instance == null) {
            synchronized (ToDoListData.class) {
                if (instance == null) {
                    instance = new ToDoListData();
                }
            }
        }
        return instance;
    }

    public ToDoListData() throws IOException {
        files = new Files();
        basicInfo = files.infoFromBasicListBD(pathToBasicListBd);
        int id;
        String basicName;
        int priority;

        String deadline;
        String comment;
        ArrayList<String> subTasks;
        basicLists = new ArrayList<>();
        BasicList basicList;
        for (int i = 0; i < basicInfo.size(); ++i) {
            id = basicInfo.get(i).getGroup().getFirst();
            basicName = basicInfo.get(i).getGroup().getSecond();
            priority = basicInfo.get(i).getGroup().getThird();

            deadline = basicInfo.get(i).getSubgroup().getFirst();
            comment = basicInfo.get(i).getSubgroup().getSecond();
            subTasks = basicInfo.get(i).getSubgroup().getThird();

            basicList = new BasicList(id, basicName, priority, deadline, comment, subTasks);
            basicLists.add(basicList);
        }


        toDoInfo = files.infoFromToDoListBD(pathToToDoListBD);
        int toDoListId;
        int userId;
        String userName;

        Date day;
        ArrayList<Pair<Integer, Boolean>> whoCanWatch;
        ArrayList<Integer> idOfBasics;
        toDoLists = new ArrayList<>();
        ToDoList toDoList;
        ArrayList<BasicList> subLists;
        for (int i = 0; i < toDoInfo.size(); ++i) {
            subLists = new ArrayList<>();
            toDoListId = toDoInfo.get(i).getGroup().getFirst();
            userId = toDoInfo.get(i).getGroup().getSecond();
            userName = toDoInfo.get(i).getGroup().getThird();

            day = toDoInfo.get(i).getSubgroup().getFirst();
            whoCanWatch = toDoInfo.get(i).getSubgroup().getSecond();
            idOfBasics = toDoInfo.get(i).getSubgroup().getThird();
            for (BasicList list : basicLists) {
                for (Integer idOfBasic : idOfBasics) {
                    if (list.getBasicId() == idOfBasic) {
                        subLists.add(list);
                    }
                }
            }
            toDoList = new ToDoList(toDoListId, userId, userName, day, whoCanWatch, subLists);
            toDoLists.add(toDoList);
        }

    }
    
    public ArrayList<ToDoList> getToDoLists(int userId, Date requestDate) {
        int year = requestDate.getYear();
        int month = requestDate.getMonth();
        int date = requestDate.getDate();

        ArrayList<ToDoList> userToDoLists = new ArrayList<>();
        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getUserId() == userId && compareDate(requestDate, toDoList.getDay())) {
                userToDoLists.add(toDoList);
            }
        }
        return userToDoLists;
    }

    public ArrayList<ToDoList> guestToDoList(int toDoListId) {
        ArrayList<ToDoList> userToDoLists = new ArrayList<>();
        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getToDoListId() == toDoListId) {
                userToDoLists.add(toDoList);
                break;
            }
        }
        return userToDoLists;
    }

    public BasicList getBasicListById(int id) {
        for (BasicList basicList : basicLists) {
            if (basicList.getBasicId() == id) {
                return basicList;
            }
        }
        return basicLists.get(id);
    }

    public int toDoListsSize() {
        return toDoLists.size();
    }

    public int basicListsSize() {
        return basicLists.size();
    }

    // +++
    public void addNewToDoList(ToDoList newToDoList) throws IOException {
        toDoLists.add(newToDoList);
        files.addNewToDoList(newToDoList, pathToToDoListBD);
    }

    // +++
    public boolean addNewBasicList(BasicList newBasicList, int userId, Date current) throws IOException {
        for (ToDoList toDoList : toDoLists) { //добавили в правильный элемент в списке всех toDoList-ов
            if (toDoList.getUserId() == userId && compareDate(toDoList.getDay(), current)) {
                toDoList.addNewBasicList(newBasicList);
                files.addToToDoListNewBasicId(toDoLists, pathToToDoListBD);
                basicLists.add(newBasicList);
                files.addNewBasicList(newBasicList, pathToBasicListBd);
                return true;
            }
        }
        return false;
    }

    // +++
    public boolean changeBasicList(BasicList newBasicList, int userId, Date current) throws IOException {
        int newId = newBasicList.getBasicId();

        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getUserId() == userId && compareDate(toDoList.getDay(), current)) {
                for (int j = 0; j < toDoList.getListOfBasics().size(); ++j) {
                    if (toDoList.getListOfBasics().get(j).getBasicId() == newId) {
                        toDoList.getListOfBasics().set(j, newBasicList);
                        break;
                    }
                }
                break;
            }
        }

        for (int i = 0; i < basicLists.size(); ++i) {
            if (basicLists.get(i).getBasicId() == newId) {
                files.changeBasicList(basicLists.get(i), newBasicList, pathToBasicListBd);

                basicLists.set(i, newBasicList);
                return true;
            }
        }
        return false;

    }

    // +++
    public boolean deleteBasicList(int basicId, int userId, Date current) throws IOException {
        //убирем id из ListOfToDoLists
        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getUserId() == userId && compareDate(toDoList.getDay(), current)) {
                for (int j = 0; j < toDoList.getListOfBasics().size(); ++j) {
                    if (toDoList.getListOfBasics().get(j).getBasicId() == basicId) {
                        //удаляем из toDoLists
                        toDoList.getListOfBasics().remove(toDoList.getListOfBasics().get(j));

                        files.delToDoListBasicId(toDoLists, pathToToDoListBD);
                        break;
                    }
                }
                break;
            }
        }

        //убирем id из ListOfBasicLists
        for (int i = 0; i < basicLists.size(); ++i) {
            if (basicLists.get(i).getBasicId() == basicId) {
                basicLists.remove(i);
                files.delBasicList(basicLists, pathToBasicListBd);
                return true;
            }
        }
        return false;
    }

    // +++
    public boolean addSubTask(int basicId, String subName) throws IOException {
        for (BasicList basicList : basicLists) {
            if (basicList.getBasicId() == basicId) {
                files.newSubTask(basicList, subName, pathToBasicListBd);
                basicList.addSubTask(subName);
                return true;
            }
        }
        return false;
    }

    // +++
    public void deleteSubTask(int basicId, String subName) throws IOException {
        for (BasicList basicList : basicLists) {
            if (basicList.getBasicId() == basicId) {
                basicList.deleteSubTask(subName);
                files.delSubTask(basicLists, pathToBasicListBd);
                break;
            }
        }
    }

    // +++
    public void changeWhoCanWatch(ArrayList<Pair<Integer, Boolean>> newWhoCanWatch, int userId, Date current) throws IOException {
        int year = current.getYear();
        int month = current.getMonth();
        int date = current.getDate();

        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getUserId() == userId && year == toDoList.getDay().getYear()
                    && month == toDoList.getDay().getMonth() && date == toDoList.getDay().getDate()) {
                files.changeWhoCanWatch(toDoList, newWhoCanWatch, pathToToDoListBD);
                break;
            }
        }
    }

    public ArrayList<Pair<ToDoList, String>> getGuestToDoLists(int guestId) {
        ArrayList<Pair<ToDoList, String>> guestToDoLists = new ArrayList<>();
        int can;
        Pair pair;
        for (ToDoList toDoList : toDoLists) {
            can = toDoList.canYouWatch(guestId);
            if (can != -1) {
                if (can == 1) {
                    pair = new Pair<>(toDoList, "Change");
                } else {
                    pair = new Pair<>(toDoList, "Read");
                }
                guestToDoLists.add(pair);
            }
        }
        return guestToDoLists;
    }

    public void sortBasicByName(int toDoListId) {
        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getToDoListId() == toDoListId) {
                toDoList.sortBasicByName();
                break;
            }
        }
    }

    public void sortBasicBySubDeadline(int toDoListId) {
        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getToDoListId() == toDoListId) {
                toDoList.sortBasicBySubDeadline();
                break;
            }
        }
    }

    public void sortBasicByPriority(int toDoListId) {
        for (ToDoList toDoList : toDoLists) {
            if (toDoList.getToDoListId() == toDoListId) {
                toDoList.sortBasicByPriority();
                break;
            }
        }
    }

    private boolean compareDate(Date firstDate, Date secondDate) {
        return firstDate.getDate() == secondDate.getDate()
                &&  firstDate.getMonth() == secondDate.getMonth()
                && firstDate.getYear() == secondDate.getYear();
    }

    public int lastBasicId() {
        return basicLists.get(basicListsSize()-1).getBasicId();
    }
}

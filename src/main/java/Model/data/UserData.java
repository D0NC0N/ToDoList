package Model.data;

import Model.IO.Files;
import Model.IO.Pair;
import Model.IO.Trinity;

import java.io.IOException;
import java.util.ArrayList;

public class UserData {
    // заполняем всеми данными из UserБД при запуске
    private ArrayList<
            Pair<Integer, // userId
                    Trinity<
                            String, //userName
                            String,  //password
                            ArrayList<Integer>>> //toDoId
            > usersInfo;
    private Files files;
    private volatile static UserData instance;

    public UserData() throws IOException {
        files = new Files();
        //userInfo = files.infoFromUserBD(getClass().getResource("src/main/resources/ListOfUsers.txt"));
        usersInfo = files.infoFromUserBD("C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\ListOfUsers.txt");
    }

    public synchronized UserData getInstance() throws IOException {
        if (instance == null) {
            synchronized (UserData.class) {
                if (instance == null) {
                    instance = new UserData();
                }
            }
        }
        return instance;
    }

    public void addUser(String name, String password, int userId) throws IOException {
        String path = "C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\ListOfUsers.txt";

        Trinity<String, String, ArrayList<Integer>> trinity
                = new Trinity<>(name, password, null);

        Pair<Integer, Trinity<String, String, ArrayList<Integer>>> newUser
                = new Pair<>(userId, trinity);

        boolean isHeNew = true;
        for (int i = 0; i < usersInfo.size(); ++i) {
            if (usersInfo.get(i).getGroup() == userId) {
                isHeNew = false;
                break;
            }
        }
        if(isHeNew) {
            files.writeNewUserToBD(newUser, path, true);
            usersInfo.add(newUser);
        }
    }

    public boolean checkUserData(String name, String password) {
        for (Pair<Integer, Trinity<String, String, ArrayList<Integer>>> info : usersInfo) {
            if (info.getSubgroup().getFirst().equals(name) &&
                    info.getSubgroup().getSecond().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public int getUserId(String name, String password) {
        int id;
        for (int i = 0; i < usersInfo.size(); ++i) {
            if (usersInfo.get(i).getSubgroup().getFirst().equals(name) &&
                    usersInfo.get(i).getSubgroup().getSecond().equals(password)){
                id = usersInfo.get(i).getGroup();
                return id;
            }
            else if (i + 1 == usersInfo.size()) {
                id = usersInfo.get(i).getGroup() + 1;
                return id;
            }
        }
        return -1;
    }

    public ArrayList<Pair<Integer, Trinity<String, String, ArrayList<Integer>>>> getUsersInfo() {
        return usersInfo;
    }
}

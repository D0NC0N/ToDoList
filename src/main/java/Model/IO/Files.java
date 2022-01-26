package Model.IO;

import Model.data.BasicList;
import Model.data.ToDoList;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Files {

    private String readFrom(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader reader = new BufferedReader(fileReader);

        String fileData = reader.readLine();
        StringBuilder sb = new StringBuilder();

        while (fileData != null) {
            sb.append(fileData).append("\n");
            fileData = reader.readLine();
        }
        sb.deleteCharAt(sb.length() - 1);
        reader.close();
        return sb.toString();
    }

    //Из UserData в файл
    public void writeNewUserToBD(Pair<Integer, Trinity<String, String, ArrayList<Integer>>> info, String path, boolean boo) throws IOException {
        FileWriter fileWriter = new FileWriter(path, boo);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        String stringBuilder = info.getGroup() + ": " + //id:

                info.getSubgroup().getFirst() + ";" + //name;
                info.getSubgroup().getSecond() + ";" + //password;
                ";";

        writer.write(stringBuilder + "\r\n");
        writer.flush();
        writer.close();
    }

    public void addNewToDoList(ToDoList newToDoList, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true); //дозаписываем в конец
        BufferedWriter writer = new BufferedWriter(fileWriter);

        writer.write(toDoListInString(newToDoList) + "\r\n");
        writer.flush();
        writer.close();
    }

    public void addNewBasicList(BasicList newBasicList, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true); //дозаписываем в конец
        BufferedWriter writer = new BufferedWriter(fileWriter);

        writer.write(basicListInString(newBasicList) + "\r\n");
        writer.flush();
        writer.close();
    }

    public void addToToDoListNewBasicId(ArrayList<ToDoList> toDoList, String path) throws IOException {
        StringBuilder newFile = new StringBuilder();

        for (int i = 0; i < toDoList.size(); ++i) {
            newFile.append(toDoListInString(toDoList.get(i)));
            if (i + 1 != toDoList.size()) {
                newFile.append("\r\n");
            }
        }

        writeToFile(path, newFile.toString(), false); // false = перезапись
    }

    public void changeBasicList(BasicList prevBasicList, BasicList newBasicList, String path) throws IOException {
        String oldFile = readFrom(path);
        String oldBasicList = basicListInString(prevBasicList);

        StringBuilder list = new StringBuilder();
        list.append(basicListInString(newBasicList));

        if (oldFile.contains(oldBasicList)) {

            oldFile = oldFile.replace(oldBasicList, list);

            //удалить последний элемент - ; и добавить newBasicId + ",;"
            writeToFile(path, oldFile, false); // false = перезапись
        }
    }

    public void changeWhoCanWatch(ToDoList toDoList, ArrayList<Pair<Integer, Boolean>> newWhoCanWatch, String path) throws IOException {
        String oldFile = readFrom(path);
        String oldToDoList = toDoListInString(toDoList);

        toDoList.setWhoCanWatch(newWhoCanWatch);

        StringBuilder newToDoList = new StringBuilder();

        newToDoList.append(toDoListInString(toDoList));

        if (oldFile.contains(oldToDoList)) {
            oldFile = oldFile.replace(oldToDoList, newToDoList);

            writeToFile(path, oldFile, false); // false = перезапись
        }
    }

    public void newSubTask(BasicList prevBasicList, String subTask, String path) throws IOException {
        String oldFile = readFrom(path);
        String oldBasicList = basicListInString(prevBasicList);

        StringBuilder list = new StringBuilder();
        list.append(subTask).append(",;");

        if (oldFile.contains(oldBasicList)) {
            String tmp = oldBasicList + "*";
            String newToDoList = tmp.replace(";*", list);

            oldFile = oldFile.replace(oldBasicList, newToDoList);

            //удалить последний элемент - ; и добавить newBasicId + ",;"
            writeToFile(path, oldFile, false); // false = перезапись
        }
    }

    public void delToDoListBasicId(ArrayList<ToDoList> delToDoList, String path) throws IOException {
        StringBuilder newFile = new StringBuilder();

        for (int i = 0; i < delToDoList.size(); ++i) {
            newFile.append(toDoListInString(delToDoList.get(i)));
            if (i + 1 != delToDoList.size()) {
                newFile.append("\r\n");
            }
        }

        writeToFile(path, newFile.toString(), false); // false = перезапись
    }

    public void delBasicList(ArrayList<BasicList> delBasicList, String path) throws IOException {
        StringBuilder newFile = new StringBuilder();
        for (int i = 0; i < delBasicList.size(); ++i) {
            newFile.append(basicListInString(delBasicList.get(i)));
            if (i + 1 != delBasicList.size()) {
                newFile.append("\r\n");
            }
        }

        writeToFile(path, newFile.toString(), false);// false = перезапись
    }

    public void delSubTask(ArrayList<BasicList> basicList, String path) throws IOException {
        StringBuilder newFile = new StringBuilder();
        for (int i = 0; i < basicList.size(); ++i) {
            newFile.append(basicListInString(basicList.get(i)));
            if (i + 1 != basicList.size()) {
                newFile.append("\r\n");
            }
        }

        writeToFile(path, newFile.toString(), false);// false = перезапись
    }

    //Из файла в UserData
    public ArrayList<Pair<Integer, Trinity<String, String, ArrayList<Integer>>>> infoFromUserBD(String path) throws IOException {
        ArrayList<Pair<Integer, Trinity<String, String, ArrayList<Integer>>>> newList = new ArrayList<>();
        FileReader fileReader = new FileReader(path);
        BufferedReader reader = new BufferedReader(fileReader);

        String buffer;
        buffer = reader.readLine();

        int id;
        String name = null;
        String password = null;
        ArrayList<Integer> toDo;

        int endOfFirst;
        int endOfSecond;

        while (buffer != null && !buffer.equals("")) {
            toDo = new ArrayList<>();
            endOfFirst = buffer.indexOf(": "); // Id:

            id = Integer.parseInt(buffer.substring(0, endOfFirst));
            buffer = buffer.substring(endOfFirst + 2);

            if (buffer.length() != 0) {
                endOfSecond = buffer.indexOf(";");

                name = buffer.substring(0, endOfSecond); //name;

                buffer = buffer.substring(endOfSecond + 1);
                endOfSecond = buffer.indexOf(";");

                password = buffer.substring(0, endOfSecond); //pass;

                buffer = buffer.substring(endOfSecond + 1);
                endOfSecond = buffer.indexOf(";");

                if (endOfSecond != 0) {
                    int idOfList;
                    while (buffer.length() != 0) {
                        endOfSecond = buffer.indexOf(",");
                        idOfList = Integer.parseInt(buffer.substring(0, endOfSecond));

                        toDo.add(idOfList);
                        buffer = buffer.substring(endOfSecond + 1);
                        if (buffer.equals(";")) {
                            break;
                        }
                    }
                }
            }
            Trinity<String, String, ArrayList<Integer>> trinity = new Trinity<>();
            trinity.setFirst(name);
            trinity.setSecond(password);
            trinity.setThird(toDo);


            Pair<Integer, Trinity<String, String, ArrayList<Integer>>> pair = new Pair<>();
            pair.setGroup(id);
            pair.setSubgroup(trinity);

            newList.add(pair);
            buffer = reader.readLine();
        }
        reader.close();
        return newList;
    }

    //Из файла в ToDoListData
    public ArrayList<Pair<
            Trinity<
                    Integer, // id:
                    Integer, // userId;
                    String>, // userName;
            Trinity<
                    Date, // date
                    ArrayList<Pair<Integer, Boolean>>, // whoCanWatch
                    ArrayList<Integer>> // basicLists
            >
            > infoFromToDoListBD(String path) throws IOException {

        ArrayList<Pair<Trinity<Integer, Integer, String>,
                Trinity<Date, ArrayList<Pair<Integer, Boolean>>, ArrayList<Integer>>>
                > newList = new ArrayList<>();

        FileReader fileReader = new FileReader(path);
        BufferedReader reader = new BufferedReader(fileReader);

        String buffer;
        buffer = reader.readLine();

        int id;
        int userId;
        String userName;

        Date date;
        ArrayList<Pair<Integer, Boolean>> whoCanWatch;
        ArrayList<Integer> basicLists;

        int endOfFirst;
        int endOfSecond;

        while (buffer != null && !buffer.equals("")) { // 0: 0; Dima; 121,11,22; 0-1,; 0,1,;
            whoCanWatch = new ArrayList<>();
            basicLists = new ArrayList<>();

            endOfFirst = buffer.indexOf(": ");

            id = Integer.parseInt(buffer.substring(0, endOfFirst));
            buffer = buffer.substring(endOfFirst + 2);
            endOfFirst = buffer.indexOf(";");

            userId = Integer.parseInt(buffer.substring(0, endOfFirst));
            buffer = buffer.substring(endOfFirst + 2);
            endOfFirst = buffer.indexOf(";");

            userName = buffer.substring(0, endOfFirst);
            buffer = buffer.substring(endOfFirst + 2);
            endOfSecond = buffer.indexOf(";");

            String dateString = buffer.substring(0, endOfSecond + 1); // 121,11,22;
            endOfFirst = dateString.indexOf(",");

            int year = Integer.parseInt(dateString.substring(0, endOfFirst));
            dateString = dateString.substring(endOfFirst + 1);
            endOfFirst = dateString.indexOf(",");

            int month = Integer.parseInt(dateString.substring(0, endOfFirst));
            dateString = dateString.substring(endOfFirst + 1);
            endOfFirst = dateString.indexOf(";");

            int day = Integer.parseInt(dateString.substring(0, endOfFirst));

            date = new Date(year, month, day);

            endOfFirst = buffer.indexOf(";");
            buffer = buffer.substring(endOfFirst + 2); //0-1,; 0,1,;

            endOfFirst = buffer.indexOf(";");
            int idOfWatchers;
            int accessTmp;
            boolean access = false;
            String watchers = buffer.substring(0, endOfFirst + 1); // 0-1,;

            while (watchers.length() != 0) {
                endOfFirst = watchers.indexOf("-");
                endOfSecond = watchers.indexOf(",");

                idOfWatchers = Integer.parseInt(watchers.substring(0, endOfFirst));
                accessTmp = Integer.parseInt(watchers.substring(endOfFirst + 1, endOfSecond));
                if (accessTmp == 1) {
                    access = true;
                } else if (accessTmp == 0) {
                    access = false;
                }

                whoCanWatch.add(new Pair<>(idOfWatchers, access));
                watchers = watchers.substring(endOfSecond + 1);
                if (watchers.equals(";")) {
                    break;
                }
            }

            endOfFirst = buffer.indexOf(";");
            buffer = buffer.substring(endOfFirst + 2);

            int idOfLists;
            if (buffer.length() != 1) {
                while (buffer.length() != 0) {
                    endOfSecond = buffer.indexOf(",");
                    idOfLists = Integer.parseInt(buffer.substring(0, endOfSecond));

                    basicLists.add(idOfLists);
                    buffer = buffer.substring(endOfSecond + 1);
                    if (buffer.equals(";")) {
                        break;
                    }
                }
            }

            Trinity<Integer, Integer, String> trinity1 = new Trinity<>();
            trinity1.setFirst(id);
            trinity1.setSecond(userId);
            trinity1.setThird(userName);

            Trinity<Date, ArrayList<Pair<Integer, Boolean>>, ArrayList<Integer>> trinity2 = new Trinity<>();
            trinity2.setFirst(date);
            trinity2.setSecond(whoCanWatch);
            trinity2.setThird(basicLists);

            Pair<Trinity<Integer, Integer, String>, Trinity<Date, ArrayList<Pair<Integer, Boolean>>, ArrayList<Integer>>> pair
                    = new Pair<>(trinity1, trinity2);


            newList.add(pair);
            buffer = reader.readLine();
        }

        reader.close();
        return newList;
    }

    //Из файла в BasicList
    public ArrayList<Pair<
            Trinity<
                    Integer, // id:
                    String, // basicName;
                    Integer>, // priority;
            Trinity<
                    String, // deadline
                    String, // comment
                    ArrayList<String>> // subTasks
            >
            > infoFromBasicListBD(String path) throws IOException {

        ArrayList<Pair<Trinity<Integer, String, Integer>,
                Trinity<String, String, ArrayList<String>>>
                > newList = new ArrayList<>();

        FileReader fileReader = new FileReader(path);
        BufferedReader reader = new BufferedReader(fileReader);
        Scanner file = new Scanner(new File(path));

        int id;
        String basicName;
        int priority;

        String deadline;
        String comment;
        ArrayList<String> subTasks;

        int endOfFirst;
        int endOfSecond;

        String buffer;
        buffer = reader.readLine();
        while (buffer != null && !buffer.equals("")) { //0: NaVi; 5; 12:00; No comments; s1mple,Perfecto,;
            subTasks = new ArrayList<>();

            endOfFirst = buffer.indexOf(": ");

            id = Integer.parseInt(buffer.substring(0, endOfFirst));
            buffer = buffer.substring(endOfFirst + 2); //NaVi; 5; 12:00; No comments; s1mple,Perfecto,;
            endOfFirst = buffer.indexOf(";");

            basicName = buffer.substring(0, endOfFirst);
            buffer = buffer.substring(endOfFirst + 2); //5; 12:00; No comments; s1mple,Perfecto,;
            endOfFirst = buffer.indexOf(";");

            priority = Integer.parseInt(buffer.substring(0, endOfFirst));
            buffer = buffer.substring(endOfFirst + 2); // 12:00; No comments; s1mple,Perfecto,;
            endOfFirst = buffer.indexOf(";");

            String dateString = buffer.substring(0, endOfFirst + 2); // 12:00;

            if (dateString.length() > 2) {
                endOfFirst = dateString.indexOf(":");

                String hour = dateString.substring(0, endOfFirst);

                dateString = dateString.substring(endOfFirst + 1);
                endOfFirst = dateString.indexOf(";");

                String min = dateString.substring(0, endOfFirst);

                deadline = hour + ":" + min;
            } else {
                deadline = "00:00";
            }
            endOfFirst = buffer.indexOf(";");
            buffer = buffer.substring(endOfFirst + 2); //No comments; s1mple,Perfecto,;

            endOfFirst = buffer.indexOf(";");
            comment = buffer.substring(0, endOfFirst);
            buffer = buffer.substring(endOfFirst + 2); //s1mple,Perfecto,;

            String sub;
            if (buffer.length() > 1) {
                while (buffer.length() != 0) {
                    endOfSecond = buffer.indexOf(",");
                    sub = buffer.substring(0, endOfSecond);

                    subTasks.add(sub);
                    buffer = buffer.substring(endOfSecond + 1);
                    if (buffer.equals(";")) {
                        break;
                    }
                }
            }

            Trinity<Integer, String, Integer> trinity1 = new Trinity<>();
            trinity1.setFirst(id);
            trinity1.setSecond(basicName);
            trinity1.setThird(priority);

            Trinity<String, String, ArrayList<String>> trinity2 = new Trinity<>();
            trinity2.setFirst(deadline);
            trinity2.setSecond(comment);
            trinity2.setThird(subTasks);

            Pair<Trinity<Integer, String, Integer>, Trinity<String, String, ArrayList<String>>> pair
                    = new Pair<>(trinity1, trinity2);


            newList.add(pair);
            file.nextLine();
            buffer = reader.readLine();
            if (file.hasNext() && buffer.equals("")) {
                buffer = reader.readLine();
                file.nextLine();
            }
        }
        reader.close();
        return newList;
    }


    private String toDoListInString(ToDoList newToDoList) {
        StringBuilder sb = new StringBuilder(); //0: 0; Dima; 121,11,23; 0-1,; 0,1,;

        sb.append(newToDoList.getToDoListId()).append(": ");
        sb.append(newToDoList.getUserId()).append("; ");
        sb.append(newToDoList.getUserName()).append("; ");

        sb.append(newToDoList.getDay().getYear()).append(",");
        sb.append(newToDoList.getDay().getMonth()).append(",");
        sb.append(newToDoList.getDay().getDate()).append("; ");

        for (int i = 0; i < newToDoList.getWhoCanWatch().size(); ++i) {
            sb.append(newToDoList.getWhoCanWatch().get(i).getGroup()).append("-");
            if (newToDoList.getWhoCanWatch().get(i).getSubgroup().equals(true)) {
                sb.append("1").append(",");
            } else {
                sb.append("0").append(",");
            }
        }
        sb.append("; ");

        for (int i = 0; i < newToDoList.getListOfBasics().size(); ++i) {
            sb.append(newToDoList.getListOfBasics().get(i).getBasicId()).append(",");
        }
        sb.append(";");
        return sb.toString();
    }

    private String basicListInString(BasicList newBasicList) {
        StringBuilder sb = new StringBuilder();
        //0: NaVi; 5; 12:00; No comments; s1mple,Perfecto,;
        //2: Heroic; 4; ; ; cadiaN,;
        //3: Fnatic; 4; ; Comment; ;

        sb.append(newBasicList.getBasicId()).append(": ");
        sb.append(newBasicList.getBasicName()).append("; ");
        sb.append(newBasicList.getPriority()).append("; ");

        sb.append(newBasicList.getDeadline()).append("; ");
        sb.append(newBasicList.getComment()).append("; ");


        for (int i = 0; i < newBasicList.getSubTasks().size(); ++i) {
            sb.append(newBasicList.getSubTasks().get(i)).append(",");
        }
        sb.append(";");

        return sb.toString();
    }

    private void writeToFile(String path, String text, boolean boo) throws IOException {
        FileWriter fileWriter = new FileWriter(path, boo);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(text + "\r\n");
        writer.flush();
        writer.close();
    }

    private void deleteEmpty(String path) throws FileNotFoundException {
        Scanner file = new Scanner(new File(path));
        PrintWriter writer = new PrintWriter("C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\tmp.txt");

        while (file.hasNext()) {
            String line = file.nextLine();
            if (!line.isEmpty()) {
                writer.write(line);
                writer.write("\n");
            }
        }
        Scanner file1 = new Scanner("C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\tmp.txt");
        PrintWriter writer1 = new PrintWriter(path);

        while (file1.hasNext()) {
            String line = file1.nextLine();
            if (!line.isEmpty()) {
                writer1.write(line);
                writer1.write("\n");
            }
        }

        File file2 = new File("C:\\Users\\Oleg\\IdeaProjects\\_ToDoList\\src\\main\\resources\\tmp.txt");

        file2.delete();

        file.close();
        writer.close();
    }
}

package Model.data;

import java.io.IOException;
import java.util.ArrayList;

public class BasicList {
    private int basicId;
    private String basicName; // имя задачи для списка первого уровня
    private int priority;

    private String deadline;
    private String comment;
    private ArrayList<String> subTasks; // лист подзадач

    public BasicList(String basicName, int priority, String deadline, String comment) throws IOException {
        basicId = getNewBasicId();
        this.basicName = basicName;
        this.priority = priority;

        this.deadline = deadline;
        this.comment = comment;
        subTasks = new ArrayList<>();
    }


    public BasicList(int basicId, String basicName, int priority, String deadline, String comment, ArrayList<String> subTasks) {
        this.basicId = basicId;
        this.basicName = basicName;
        this.priority = priority;

        this.deadline = deadline;
        this.comment = comment;
        this.subTasks = subTasks;
    }

    public void setBasicId(int basicId) {
        this.basicId = basicId;
    }

    public int getBasicId() {
        return basicId;
    }

    public void setBasicName(String basicName) {
        this.basicName = basicName;
    }

    public String getBasicName() {
        return basicName;
    }

    public void setSubTasks(ArrayList<String> subTasks) {
        this.subTasks = subTasks;
    }

    public ArrayList<String> getSubTasks() {
        return subTasks;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    private int getNewBasicId() throws IOException {
        ToDoListData toDoListData = new ToDoListData().getInstance();
        return toDoListData.lastBasicId()+1;
    }

    private String changeTime(int num) {
        StringBuilder str = new StringBuilder();
        if (num < 10) {
            str.append("0").append(num);
        }
        else
            str.append(num);

        return str.toString();
    }

    public void addSubTask(String sub) throws IOException {
        subTasks.add(sub);
    }

    public void deleteSubTask(String subName) {
        subTasks.remove(subName);
    }

}

import Model.data.BasicList;
import Model.data.ToDoListData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Tests {
    private ToDoListData toDoListData = new ToDoListData().getInstance();
    private Date current = new Date(122,0,26);
    public Tests() throws IOException {
    }

    private void check(boolean bool) {
        if (!bool) {
            throw new RuntimeException("Test failed");
        }
    }

    @Test
    public void addNewBasicListTest() throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("first");
        arrayList.add("second");
        BasicList basicList = new BasicList(-1, "Test", 5, "00:00", "Clear", arrayList);

        check(toDoListData.addNewBasicList(basicList, 3, current));
    }

    @Test
    public void changeBasicListTest() throws IOException {
        BasicList basicList = new BasicList(-1, "TestS", 10, "21:21", "FULL", new ArrayList<>());

        check(toDoListData.changeBasicList(basicList, 3, current));
    }

    @Test
    public void addSubTaskTest() throws IOException {
        check(toDoListData.addSubTask(-1, "subName"));
    }

    @Test
    public void deleteBasicListTest() throws IOException {
        check(toDoListData.deleteBasicList(-1, 3, current));
    }
}

package Model.IO;

public class Pair<K, k> {
    private K group;
    private k subgroup;

    public Pair() {}
    public Pair(K group, k subgroup) {
        this.group = group;
        this.subgroup = subgroup;
    }

    public K getGroup() {
        return group;
    }

    public k getSubgroup() {
        return subgroup;
    }

    public void setGroup(K group) {
        this.group = group;
    }

    public void setSubgroup(k subgroup) {
        this.subgroup = subgroup;
    }
}

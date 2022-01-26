package Model.IO;

public class Trinity<F, S, Th> {
    private F first;
    private S second;
    private Th third;

    public Trinity(){
    }
    public Trinity(F first, S second, Th third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public Th getThird() {
        return third;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public void setThird(Th third) {
        this.third = third;
    }
}

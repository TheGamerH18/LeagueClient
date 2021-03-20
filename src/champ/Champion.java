package champ;

public abstract class Champion {

    protected String Champname;
    protected int[] position = new int[2];

    public int[] getposition() {
        return position;
    }

    public String getChampname() {
        return Champname;
    }
}
package champ;

public abstract class Champion {

    protected String Champname;
    protected int maxhealth;
    protected int chealth;
    public boolean alife = true;

    public String getChampname() {
        return Champname;
    }

    public void printhealth(int[] chealth) {
        if(chealth[0] == 0){
            alife = false;
        } else {
            this.chealth = chealth[0];
            System.out.println(Champname + "  " + this.chealth + " / " + maxhealth);
            int healthinp = (this.chealth * 100 / maxhealth);
            for(int i = 0; i < 2; i ++) {
                for(int x = 0; x < 100; x ++) {
                    if(x < healthinp) {
                        System.out.print("/");
                    } else {
                        System.out.print("-");
                    }
                }
                System.out.print("\n");
            }
        }
    }

    public abstract int[] q(int[][] positions, int ownid);
}
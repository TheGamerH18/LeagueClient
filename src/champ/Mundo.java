package champ;

public class Mundo extends Champion{

    public Mundo() {
        Champname = "Mundo";
        maxhealth = 2000;
        chealth = maxhealth;
    }

    @Override
    public int[] q(int[][] positions, int ownid) {

        // Range der Attacke
        int range = 4;
        // Damage der Attacke
        int damage = 300;

        int targetid;
        if(ownid==0){
            targetid = 1;
        } else {
            targetid = 0;
        }
        int distancex = Math.abs((positions[ownid][0] - positions[targetid][0]));
        int distancey = Math.abs((positions[ownid][1] - positions[targetid][1]));

        if(distancex <= range && distancey <= range){
            return new int[]{targetid, damage};
        } else {
            return new int[]{targetid, 0};
        }
    }
}
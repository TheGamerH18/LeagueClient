package champ;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Champion {

    protected String Champname;
    protected int maxhealth;
    protected int chealth;
    public boolean alife = true;


    // Vars for Q-Attack - System wide
    protected LocalDateTime q_last = LocalDateTime.now();
    protected boolean q_isrunnable = true;
    protected double q_restcooldown = 0;

    // Vars for Q-Attack spezial
    protected int q_range;
    protected int q_damage;
    protected int q_cooldown;

    public String getChampname() {
        return Champname;
    }

    public void printattacks(){
        checkrunnable();
        System.out.println("----- ----- ----- -----");
        if(q_restcooldown != 0){
            System.out.print("| "+Math.round(q_restcooldown)+" |");
        } else {
            System.out.print("| Q |");
        }
        System.out.println("\n----- ----- ----- -----");
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

    public void checkrunnable() {
        if(!q_isrunnable){
            long duration = Duration.between(q_last, LocalDateTime.now()).toMillis();
            if(duration > q_cooldown){
                q_isrunnable = true;
                q_restcooldown = 0;
            } else {
                q_restcooldown = (q_cooldown - duration);
                q_restcooldown /= 1000;
                q_isrunnable = false;
            }
        }
    }

    public int[] q(int[][] positions, int ownid) {
            // Ziel finden
            int targetid;
            if (ownid == 0) {
                targetid = 1;
            } else {
                targetid = 0;
            }

            // Entfernung zu Ziel
            int distancex = Math.abs((positions[ownid][0] - positions[targetid][0]));
            int distancey = Math.abs((positions[ownid][1] - positions[targetid][1]));

            // Damage wenn in Range, ansonsten 0 Damage
            if (distancex <= q_range && distancey <= q_range && q_isrunnable) {
                q_last = LocalDateTime.now();
                q_isrunnable = false;
                return new int[]{targetid, q_damage};
            } else {
                return new int[]{targetid, 0};
            }
        }
}
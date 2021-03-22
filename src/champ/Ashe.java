package champ;

public class Ashe extends Champion {

    public Ashe() {
        Champname = "Ashe";
        maxhealth = 1000;
        chealth = maxhealth;

        // Vars for Q-Attack
        q_range = 5;
        q_damage = 200;
        q_cooldown = 4000;
    }
}
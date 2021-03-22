package champ;

public class Mundo extends Champion{

    public Mundo() {
        Champname = "Mundo";
        maxhealth = 2000;
        chealth = maxhealth;

        // Vars for Q-Attack
        q_range = 4;
        q_damage = 300;
        q_cooldown = 4000;
    }
}
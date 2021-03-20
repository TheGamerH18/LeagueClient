package mainClient;

import champ.Ashe;
import champ.Mundo;
import com.blogspot.debukkitsblog.net.Datapackage;
import map.Map;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class GameClient {

    public String username;
    public String hostname;

    public GameClient(String hostname, String username) {
        this.username = username;
        this.hostname = hostname;
        // Spam durch Libary unterbinden
        //client.setMuted(true);
    }

    public void start(){

        LeagueClient client = new LeagueClient(hostname, username);
        client.setMuted(true);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Datapackage user = client.sendMessage(new Datapackage("AUTH", username));
        username = (String) user.get(1);
        if (username.equals("Server Full")) {
            client.stop();
            System.out.println(username);
            System.exit(1);
        } else {
            System.out.println(username);
        }

        Ashe a1 = new Ashe();
        Mundo m1 = new Mundo();
        Scanner input = new Scanner(System.in);

        Map cmap = new Map();

        LocalDateTime lastrun = LocalDateTime.now();


        while ((a1.alife) && (m1.alife)) {
            //System.out.println(LocalDateTime.now());
            if(Duration.between(lastrun, LocalDateTime.now()).toMillis() >= 1000) {
                lastrun = LocalDateTime.now();
                System.out.println(lastrun);
                int[][] position;
                position = client.positions;
                String[] champs = {a1.getChampname(), m1.getChampname()};
                cmap.printmap(position, champs);
            }
        }
        input.close();
    }
}

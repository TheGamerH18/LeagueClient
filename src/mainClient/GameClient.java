package mainClient;

import champ.Ashe;
import champ.Champion;
import champ.Mundo;
import com.blogspot.debukkitsblog.net.Datapackage;
import map.Map;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GameClient {

    // Aktuelle User ID
    public int myid;

    // Aktuelle User Position
    public int[] myposition;

    // Server Info
    public String username;
    public String hostname;

    // Player Array
    Champion[] players = new Champion[2];

    // Players alife?
    boolean alife = true;

    // Map Instanz
    Map cmap = new Map();

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
        }
        myid = Integer.parseInt(String.valueOf(user.get(2)))-1;
        System.out.println("Dein User ist: "+myid);

        players[0] = new Ashe();
        players[1] = new Mundo();

        Thread anzeige = new Thread(new Runnable() {
            @Override
            public void run() {
                LocalDateTime lastrun = LocalDateTime.now();
                while (alife) {
                    //System.out.println(LocalDateTime.now());
                    if(Duration.between(lastrun, LocalDateTime.now()).toMillis() >= 1000) {
                        lastrun = LocalDateTime.now();
                        System.out.println(lastrun);
                        if(!client.gameinfo.equals("")){
                            for(int x = 0; x < 26; x++){
                                System.out.println();
                            }
                            if(client.winner != 0){
                                System.out.println("Gewinner ist Spieler Nr. " + client.winner);
                            } else {
                                System.out.println();
                            }
                            System.out.println("Du bist Spieler Nr. " + (myid + 1));
                            System.out.println(client.gameinfo);
                        } else {
                            int[][] position;
                            position = client.positions;
                            myposition = position[myid];
                            String[] champs = {"0","0"};
                            cmap.printmap(position, champs);
                            players[0].printhealth(client.playerhealth[0]);
                            players[1].printhealth(client.playerhealth[1]);
                            players[myid].printattacks();
                        }
                    }
                }
            }
        });
        anzeige.start();

        Thread checkalive = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Champion player : players) {
                    if (!player.alife) {
                        alife = false;
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        checkalive.start();

        Scanner scan = new Scanner(System.in);
        while(alife){
            String input = scan.nextLine();
            inputanalyse(client, input);
        }
        checkalive.interrupt();

        scan.close();
    }

    public void inputanalyse(LeagueClient client, String input) {
        switch(input){
            case "le":
                if(cmap.isenterable(myposition[0], myposition[1] - 1)){
                    myposition[1] -= 1;
                    client.updatePositions(myposition, myid);
                }
                break;
            case "ri":
                if(cmap.isenterable(myposition[0], myposition[1] + 1)){
                    myposition[1] += 1;
                    client.updatePositions(myposition, myid);
                }
                break;
            case "up":
                if(cmap.isenterable(myposition[0] - 1, myposition[1])){
                    myposition[0] -= 1;
                    client.updatePositions(myposition, myid);
                }
                break;
            case "do":
                if(cmap.isenterable(myposition[0] + 1, myposition[1])){
                    myposition[0] += 1;
                    client.updatePositions(myposition, myid);
                }
                break;
            case "q":
                int[] damage = players[myid].q(client.positions, myid);
                if(damage[1] != 0){
                    //            OwnID|TargetID | Damageamount
                    client.damage(myid, damage[0], damage[1]);
                }
                break;
            case "ende":
                System.exit(2);
                break;
        }
    }
}

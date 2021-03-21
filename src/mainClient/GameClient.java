package mainClient;

import champ.Ashe;
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

        Ashe p1 = new Ashe();
        Mundo p2 = new Mundo();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LocalDateTime lastrun = LocalDateTime.now();
                while ((p1.alife) && (p2.alife)) {
                    //System.out.println(LocalDateTime.now());
                    if(Duration.between(lastrun, LocalDateTime.now()).toMillis() >= 1000) {
                        lastrun = LocalDateTime.now();
                        System.out.println(lastrun);
                        if(!client.gameinfo.equals("")){
                            for(int x = 0; x < 24; x++){
                                System.out.println();
                            }
                            System.out.println("Du bist Spieler Nr. " + myid);
                            System.out.println(client.gameinfo);
                        } else {
                            int[][] position;
                            position = client.positions;
                            myposition = position[myid];
                            String[] champs = {"0","0"};
                            cmap.printmap(position, champs);
                            p1.printhealth(client.playerhealth[0]);
                            p2.printhealth(client.playerhealth[1]);
                        }
                    }
                }
            }
        });
        thread.start();

        Scanner scan = new Scanner(System.in);
        while(p1.alife && p2.alife){
            String input = scan.nextLine();
            inputanalyse(client, input);
        }
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
            case "ende":
                System.exit(2);
                break;
        }
    }
}

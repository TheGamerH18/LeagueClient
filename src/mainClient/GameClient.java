package mainClient;

import champ.Ashe;
import champ.Mundo;
import com.blogspot.debukkitsblog.net.Datapackage;
import map.Map;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GameClient {

    public int myid;

    public int[] myposition;

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
        }
        myid = Integer.parseInt(String.valueOf(user.get(2)))-1;
        System.out.println("Dein User ist: "+myid);

        Ashe a1 = new Ashe();
        Mundo m1 = new Mundo();
        Map cmap = new Map();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LocalDateTime lastrun = LocalDateTime.now();
                while ((a1.alife) && (m1.alife)) {
                    //System.out.println(LocalDateTime.now());
                    if(Duration.between(lastrun, LocalDateTime.now()).toMillis() >= 1000) {
                        lastrun = LocalDateTime.now();
                        System.out.println(lastrun);
                        if(!client.gameinfo.equals("")){
                            for(int x = 0; x < 19; x++){
                                System.out.println("");
                            }
                            System.out.println(client.gameinfo);
                        } else {
                            int[][] position;
                            position = client.positions;
                            myposition = position[myid];
                            String[] champs = {a1.getChampname(), m1.getChampname()};
                            cmap.printmap(position, champs);
                        }
                    }
                }
            }
        });
        thread.start();

        Scanner scan = new Scanner(System.in);
        while(a1.alife && m1.alife){
            String input = scan.nextLine();
            inputanalyse(client, input);
        }
        scan.close();
    }

    public void inputanalyse(LeagueClient client, String input) {
        switch(input){
            case "up":
                myposition[0] -= 1;
                client.updatePositions(myposition, myid);
                break;
            case "do":
                myposition[0] += 1;
                client.updatePositions(myposition, myid);
                break;
            case "le":
                myposition[1] -= 1;
                client.updatePositions(myposition, myid);
                break;
            case "ri":
                myposition[1] += 1;
                client.updatePositions(myposition, myid);
                break;
        }
    }
}

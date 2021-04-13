package mainClient;

import java.net.Socket;

import champ.Champion;
import com.blogspot.debukkitsblog.net.Client;
import com.blogspot.debukkitsblog.net.Datapackage;
import com.blogspot.debukkitsblog.net.Executable;

public class LeagueClient extends Client{

    // Positionen von Spielern
    public int[][] positions = new int[2][2];

    // Game Info von Server
    public String gameinfo = "";

    // Spieler Leben [0] = Current Health | [1] = Max Health
    public int[][] playerhealth = new int[2][2];

    // Player Champs
    public Champion[] playerchamps = new Champion[2];
    public boolean champsselected = false;

    // Winner var, saving last winner
    public int winner = 0;

    LeagueClient(String host, String username) {
        super(host, 25598, 1000, false, username, "player");

        registerMethod("PLAYER_CHAMPS", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                for(int i = 0; i < playerchamps.length; i ++){
                    try {
                        Class <?> clazz = Class.forName(String.valueOf(pack.get(i + 1)));
                        playerchamps[i] = (Champion) clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        registerMethod("GAME_INFO", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                int packint = Integer.parseInt(String.valueOf(pack.get(1)));
                if (packint == 0) {
                    gameinfo = "Nicht genug Spieler";
                }
                else if(packint == 6)
                        gameinfo = "";
                else {
                    gameinfo = "Runde beginnt in: "+packint;
                }
            }
        });

        registerMethod("P_HEALTH", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                playerhealth[0][0] = Integer.parseInt(String.valueOf(pack.get(1)));
                playerhealth[0][1] = Integer.parseInt(String.valueOf(pack.get(2)));
                playerhealth[1][0] = Integer.parseInt(String.valueOf(pack.get(3)));
                playerhealth[1][1] = Integer.parseInt(String.valueOf(pack.get(4)));
            }
        });

        registerMethod("POSITIONS", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                positions[0][0] = Integer.parseInt(String.valueOf(pack.get(1)));
                positions[0][1] = Integer.parseInt(String.valueOf(pack.get(2)));
                positions[1][0] = Integer.parseInt(String.valueOf(pack.get(3)));
                positions[1][1] = Integer.parseInt(String.valueOf(pack.get(4)));
            }
        });

        registerMethod("END", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                winner = Integer.parseInt(String.valueOf(pack.get(1)));
            }
        });

        start();
    }

    public void damage(int id, int targetid, int damageamount) {
        sendMessage(new Datapackage("DAMAGE", id, targetid, damageamount));
    }

    public boolean champselect(int id, String Champ){
        Datapackage response = sendMessage(new Datapackage("CHAMP_SELECT", id, Champ));
        String responsecode = String.valueOf(response.get(1));
        return responsecode.equals("Champ accepted");
    }

    public void updatePositions(int[] positions, int id) {
        // System.out.println(id);
        sendMessage(new Datapackage("NEW_POSITION", id, positions[0], positions[1]));
    }
}
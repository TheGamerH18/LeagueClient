package mainClient;

import java.net.Socket;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

import com.blogspot.debukkitsblog.net.Client;
import com.blogspot.debukkitsblog.net.Datapackage;
import com.blogspot.debukkitsblog.net.Executable;
import map.Map;
import champ.*;

public class LeagueClient extends Client{

    public int[][] positions = new int[2][2];
    public String gameinfo = "";

    LeagueClient(String host, String username) {
        super(host, 25598, 1000, false, username, "player");

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

        registerMethod("POSITIONS", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                positions[0][0] = Integer.parseInt(String.valueOf(pack.get(1)));
                positions[0][1] = Integer.parseInt(String.valueOf(pack.get(2)));
                positions[1][0] = Integer.parseInt(String.valueOf(pack.get(3)));
                positions[1][1] = Integer.parseInt(String.valueOf(pack.get(4)));
            }
        });
        start();
    }

    public void updatePositions(int[] positions, int id) {
        System.out.println(id);
        sendMessage(new Datapackage("NEW_POSITION", id, positions[0], positions[1]));
    }
}
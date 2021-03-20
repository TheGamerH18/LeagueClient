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

    LeagueClient(String host, String username) {
        super(host, 25598, 1000, false, username, "player");

        registerMethod("NEW_MSG", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                System.out.println("[CLIENT] New Message: " + pack.get(1) + "::" + pack.get(2));
            }
        });

        registerMethod("POSITIONS", new Executable() {
            @Override
            public void run(Datapackage pack, Socket socket) {
                //System.out.println(pack.get(1).getClass().getName());
                positions[0][0] = Integer.parseInt(String.valueOf(pack.get(1)));
                //System.out.println(pack.get(2).getClass().getName());
                positions[0][1] = Integer.parseInt(String.valueOf(pack.get(2)));
                //System.out.println(pack.get(3).getClass().getName());
                positions[1][0] = Integer.parseInt(String.valueOf(pack.get(3)));
                //System.out.println(pack.get(4).getClass().getName());
                positions[1][1] = Integer.parseInt(String.valueOf(pack.get(4)));
            }
        });
        start();
    }
}
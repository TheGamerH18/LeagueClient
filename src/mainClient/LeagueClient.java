package mainClient;

import java.net.Socket;
import java.util.Scanner;

import com.blogspot.debukkitsblog.net.Client;
import com.blogspot.debukkitsblog.net.Datapackage;
import com.blogspot.debukkitsblog.net.Executable;

public class LeagueClient extends Client{

    LeagueClient(String host, String username) {
        super(host, 25598, 1000, false, username, "player");

        registerMethod("NEW_MSG", new Executable() {

            @Override
            public void run(Datapackage pack, Socket socket) {
                System.out.println("[CLIENT] New Message: " + pack.get(1) + "::" + pack.get(2));
            }

        });
        start();
    }

    public static void main(String[] args) {
        Scanner inputscan = new Scanner(System.in);
        System.out.println("Input Hostname");
        String hostname = inputscan.nextLine();
        System.out.println("Input Username");
        String username = inputscan.nextLine();
        LeagueClient client = new LeagueClient(hostname, username);

        inputscan.close();
    }

}
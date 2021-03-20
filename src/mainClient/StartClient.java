package mainClient;

import java.util.Scanner;

public class StartClient {

    public static void main(String[] args) {

        Scanner inputscan = new Scanner(System.in);
        System.out.println("Input Hostname");
        String hostname = inputscan.nextLine();
        System.out.println("Input Username");
        String username = inputscan.nextLine();

        GameClient spiel = new GameClient(hostname, username);
        spiel.start();

        inputscan.close();
    }
}

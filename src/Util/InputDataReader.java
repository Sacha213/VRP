package Util;

import Entity.Client;
import Entity.Depot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputDataReader {
    private final String fileName;

    public InputDataReader(String fileName) {
        this.fileName = fileName;
    }

    public VRPInstance readData(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Depot depot = null;
        ArrayList<Client> clients = new ArrayList<>();
        int capacity = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("d")) {
                String[] tokens = line.split(" ");
                String id = tokens[0];
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                int readyTime = Integer.parseInt(tokens[3]);
                int dueTime = Integer.parseInt(tokens[4]);
                depot = new Depot(id, x, y, readyTime, dueTime);
            } else if (line.startsWith("c")) {
                String[] tokens = line.split(" ");
                String id = tokens[0];
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                int readyTime = Integer.parseInt(tokens[3]);
                int dueTime = Integer.parseInt(tokens[4]);
                int demand = Integer.parseInt(tokens[5]);
                int serviceTime = Integer.parseInt(tokens[6]);
                clients.add(new Client(id, x, y, readyTime, dueTime, demand, serviceTime, depot));
            } else if (line.startsWith("MAX_QUANTITY:")) {
                String[] tokens = line.split(" ");
                capacity = Integer.parseInt(tokens[1]);
            }
        }
        scanner.close();

        return new VRPInstance(depot, clients, capacity);
    }
}

package Main;

import json.JSON;
import schedule.Schedule;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Schedule sh = new Schedule(8);
        sh.printSchedule();
        addShip(new Scanner(System.in), sh);
        JSON js = new JSON(sh);
        js.getScheduleForModel();
    }

    private static void addShip(Scanner in, Schedule sh){
        while (true) {
            System.out.println("Do you want to manually create note about ship to the schedule?");
            String line = in.nextLine().toLowerCase();
            while (!line.equals("yes") && (!line.equals("no"))) {
                System.out.println("Do you want to manually add another ship to existing schedule?");
                line = in.nextLine();
            }
            if (line.equals("yes")) {
                try {
                    sh.addShipFromConsole();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
    }
}

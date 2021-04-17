package schedule;

import schedule.cargo.Cargo;
import schedule.cargo.TypeOfCargo;
import schedule.ship.NameGenerator;
import schedule.ship.Ship;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * The class forms the ship schedule.
 */
public class Schedule {
    final private LinkedList<Ship> ships;

    {
        ships = new LinkedList<>();
    }

    /**
     * @param numberOfShips the number of ships to create a schedule for
     */
    public Schedule(int numberOfShips) {
        for (int i = 0; i < numberOfShips; i++) {
            NameGenerator gen = new NameGenerator();
            ships.add(new Ship(gen.generateName(), generateCargo(), Time.getRandomTime(Time.DAYS_IN_MONTH)));
        }
    }

    /**
     * @return cargo for ship
     */
    private static Cargo generateCargo() {
        int amount;
        TypeOfCargo kind;
        switch ((int) (Math.random() * TypeOfCargo.values().length)) {
            case 0 -> {
                amount = (int) (Math.random() * Cargo.MAX_AMOUNT);
                kind = TypeOfCargo.BULK;
            }
            case 1 -> {
                amount = (int) (Math.random() * Cargo.MAX_AMOUNT);
                kind = TypeOfCargo.LIQUID;
            }
            default -> {
                amount = (int) (Math.random() * Cargo.MAX_PIECES);
                kind = TypeOfCargo.CONTAINER;
            }
        }
        ;
        return new Cargo(kind, amount);
    }

    public LinkedList<Ship> getShips() {
        return ships;
    }

    /**
     * The function allows you to manually add records via the console.
     */
    public void addShipFromConsole() {
        Scanner in = new Scanner(System.in);
        System.out.println("Ship name: ");
        String name = in.nextLine();
        System.out.println("Type of cargo - BULK, LIQUID or CONTAINER: ");
        String kind = in.nextLine();
        TypeOfCargo type = switch (kind) {
            case "BULK" -> TypeOfCargo.BULK;
            case "LIQUID" -> TypeOfCargo.LIQUID;
            case "CONTAINER" -> TypeOfCargo.CONTAINER;
            default -> throw new IllegalArgumentException("Wrong type");
        };
        System.out.println("Amount of cargo: ");
        int amount = in.nextInt();
        if ((kind.equals(TypeOfCargo.CONTAINER.toString())) ? (amount > Cargo.MAX_PIECES) : (amount > Cargo.MAX_AMOUNT)) {
            throw new IllegalArgumentException("Wrong amount");
        }
        Time arriving = new Time();
        System.out.println("Ship arrival day: ");
        arriving.setDay(in.nextInt());
        System.out.println("Hour: ");
        arriving.setHour(in.nextInt());
        System.out.println("Minute: ");
        arriving.setMinute(in.nextInt());
        ships.add(new Ship(name, new Cargo(type, amount), arriving));
    }

    /**
     * The function prints the schedule of all available ships
     */
    public void printSchedule() {
        for (Ship ship : ships) {
            System.out.printf("%27s:%30s%12s%12s%n", ship.getName(), ship.getCargo().toString(),
                    ship.getArriving().toString(), ship.getUnloading().toString());
        }
    }
}

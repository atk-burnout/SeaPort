package schedule.ship;

import schedule.Time;
import schedule.cargo.Cargo;

/**
 * The class implements the schedule unit. In fact, it is considered as a cargo ship.
 */
public class Ship {
    private final String name;
    private final Cargo cargo;
    private Time arriving;
    private Time unloading;

    /**
     * {@value #BULK_TONS_PER_MINUTES}
     * {@value #LIQUID_TONS_PER_MINUTES}
     * {@value #CONTAINER_PIECES_PER_MINUTES}
     */
    final public static double BULK_TONS_PER_MINUTES = 4D;
    final public static double LIQUID_TONS_PER_MINUTES = 3D;
    final public static double CONTAINER_PIECES_PER_MINUTES = 1D;

    /**
     * @param name cargo ship name
     * @param cargo transported cargo - type and amount
     * @param arriving ship arrival time
     */
    public Ship(String name, Cargo cargo, Time arriving) {
        this.name = name;
        this.cargo = cargo;
        if ((arriving.getHour() < 0) || (arriving.getHour() >= Time.HOURS_IN_DAY) ||
                (arriving.getMinute() < 0) || (arriving.getMinute() >= Time.MINUTES_IN_HOUR)) {
            throw new IllegalArgumentException();
        }
        this.arriving = arriving;

        this.unloading = new Time(0, 0, 0);
        int count = cargo.getAmount();
        switch (cargo.getKind()) {
            case BULK:
                this.unloading.increaseMinutes((int) (count / BULK_TONS_PER_MINUTES));
                break;
            case LIQUID:
                this.unloading.increaseMinutes((int) (count / LIQUID_TONS_PER_MINUTES));
                break;
            case CONTAINER:
                this.unloading.increaseMinutes((int) (count / CONTAINER_PIECES_PER_MINUTES));
                break;
        }
    }

    public Ship(Ship ship) {
        this(ship.name, ship.cargo, new Time(ship.getArriving()));
    }

    public String getName() {
        return name;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Time getArriving() {
        return arriving;
    }

    public Time getUnloading() {
        return unloading;
    }

    @Override
    public String toString() {
        return name + "\t" + cargo.toString() + "\t" + arriving + "\t" + unloading;
    }
}

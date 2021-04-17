package schedule.cargo;

/**
 * A class is a cargo that has a certain type, according to which there is a number of tons or pieces of containers.
 */
public class Cargo {

    final private TypeOfCargo kind;
    final private int amount;

    final public static int MAX_AMOUNT = 8000;
    final public static int MAX_PIECES = 1000;

    /**
     * @param kind   the type of cargo on the ship
     * @param amount cargo quantity: tons for bulk or liquid cargo or pieces per container
     */
    public Cargo(TypeOfCargo kind, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        this.kind = kind;
        this.amount = amount;
    }

    public TypeOfCargo getKind() {
        return kind;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return amount + (kind == TypeOfCargo.CONTAINER ? " pieces" : " tons") + " of type " + kind.toString();
    }
}

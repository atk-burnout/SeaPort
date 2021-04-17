package json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import schedule.Schedule;
import schedule.Time;
import schedule.cargo.Cargo;
import schedule.cargo.TypeOfCargo;
import schedule.ship.Ship;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class JSON {
    final private static String scheduleFilename = "src/json/schedule.json";
    final private LinkedList<Ship> ships;

    public JSON(int numberOfShips) {
        ships = new Schedule(numberOfShips).getShips();
    }

    public JSON(Schedule schedule) {
        ships = schedule.getShips();
    }

    public void getScheduleForModel() {
        JSONObject holder = toJSON(ships);
        try {
            FileWriter fileWriter = new FileWriter(scheduleFilename);
            fileWriter.write(holder.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Ship> getShipsFromJSON() {
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(scheduleFilename));
            LinkedList<Ship> ships = new LinkedList<>();
            JSONArray array = (JSONArray) obj.get("ships");
            for (int i = 0; i < array.size(); i++) {
                JSONObject temp = (JSONObject) array.get(i);
                String cargoParse = temp.get("cargo").toString();
                String[] cargoParams = cargoParse.split(":");
                int amount = Integer.parseInt(cargoParams[0]);
                TypeOfCargo kind;
                if (cargoParams[1].equals(TypeOfCargo.BULK.toString())) {
                    kind = TypeOfCargo.BULK;
                } else if (cargoParams[1].equals(TypeOfCargo.LIQUID.toString())) {
                    kind = TypeOfCargo.LIQUID;
                } else {
                    kind = TypeOfCargo.CONTAINER;
                }
                ships.add(new Ship(temp.get("name").toString(), new Cargo(kind, amount),
                        getTimeFromJSON((JSONObject) temp.get("arriving"))));
            }
            return ships;
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<Ship>();
    }

    public static Time getTimeFromJSON(JSONObject obj) {
        return new Time(Integer.parseInt(obj.get("day").toString()), Integer.parseInt(obj.get("hour").toString()),
                Integer.parseInt(obj.get("minute").toString()));
    }

    private JSONObject toJSON(LinkedList<Ship> ships) {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        for (Ship ship : ships) {
            array.add(toJSON(ship));
        }
        obj.put("ships", array);
        return obj;
    }

    private JSONObject toJSON(Ship ship) {
        JSONObject obj = new JSONObject();
        obj.put("name", ship.getName());
        obj.put("cargo", ship.getCargo().getAmount() + ":" + ship.getCargo().getKind());
        obj.put("arriving", toJSON(ship.getArriving()));
        return obj;
    }

    private JSONObject toJSON(Time time) {
        JSONObject obj = new JSONObject();
        obj.put("day", time.getDay());
        obj.put("hour", time.getHour());
        obj.put("minute", time.getMinute());
        return obj;
    }
}
package schedule;

import java.util.Objects;

public class Time{
    private int day;
    private int hour;
    private int minute;
    /**
     * {@value #DAYS_IN_MONTH}
     * {@value #HOURS_IN_DAY}
     * {@value #MINUTES_IN_HOUR}
     */
    final public static int DAYS_IN_MONTH = 30;
    final public static int HOURS_IN_DAY = 24;
    final public static int MINUTES_IN_HOUR = 60;

    public Time() {
        day = 0;
        hour = 0;
        minute = 0;
    }

    public Time(int day, int hour, int minute) {
        if ((hour < 0) || (hour > HOURS_IN_DAY) || (minute < 0) || (minute > MINUTES_IN_HOUR)) {
            throw new IllegalArgumentException();
        }
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public Time(Time time) {
        this(time.day, time.hour, time.minute);
    }

    public Time(String string) {
        if (string.length() != 8) {
            throw new IllegalArgumentException();
        }
        String[] timeParams = string.split(":");
        int regDay = Integer.parseInt(timeParams[0]);
        if ((regDay < 0) || (regDay > DAYS_IN_MONTH)) {
            throw new IllegalArgumentException();
        }
        day = regDay;
        int regHour = Integer.parseInt(timeParams[1]);
        if ((regHour < 0) || (regHour >= HOURS_IN_DAY)) {
            throw new IllegalArgumentException();
        }
        hour = regHour;
        int regMinute = Integer.parseInt(timeParams[2]);
        if ((regMinute < 0) || (regMinute >= MINUTES_IN_HOUR)) {
            throw new IllegalArgumentException();
        }
        minute = regMinute;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Time time = (Time) obj;
        return day == time.day && hour == time.hour && minute == time.minute;
    }

    public void increaseMinutes(int time) {
        if (minute < 0) {
            throw new IllegalArgumentException();
        }
        this.minute += time;

        int hours = this.minute / MINUTES_IN_HOUR;
        this.minute %= MINUTES_IN_HOUR;
        this.hour += hours;

        int days = this.hour / HOURS_IN_DAY;
        this.hour %= HOURS_IN_DAY;
        this.day += days;
    }

    public static Time getRandomTime(int numberOfDays) {
        return new Time((int) (Math.random() * numberOfDays), (int) (Math.random() * HOURS_IN_DAY),
                (int) (Math.random() * MINUTES_IN_HOUR));
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", day, hour, minute);
    }
}

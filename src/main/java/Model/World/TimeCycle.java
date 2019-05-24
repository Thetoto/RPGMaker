package Model.World;

public class TimeCycle {
    boolean active;
    int night_duration;
    int day_duration;

    public TimeCycle() {
        active = false;
        night_duration = 0;
        day_duration = 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNight_duration() {
        return night_duration;
    }

    public void setNight_duration(int night_duration) {
        this.night_duration = night_duration;
    }

    public int getDay_duration() {
        return day_duration;
    }

    public void setDay_duration(int day_duration) {
        this.day_duration = day_duration;
    }
}

package hexlet.code.domain;

import io.ebean.Model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public final class Weather_history extends Model {
    private Date weather_date;
    private String weather_value;


    public Weather_history(Date weather_date, String weather_value) {
        this.weather_date = weather_date;
        this.weather_value = weather_value;
    }

    public String getWeather_value() {
        return weather_value;
    }
}

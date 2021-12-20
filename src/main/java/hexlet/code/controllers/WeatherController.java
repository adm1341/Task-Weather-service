package hexlet.code.controllers;

import hexlet.code.domain.Weather_history;
import hexlet.code.domain.query.QWeather_history;
import io.javalin.http.Handler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class WeatherController {
    public static Handler weather = ctx -> {
        LocalDate todayLocal = LocalDate.now(ZoneId.of("Europe/Moscow"));
        Date todayUtil = Date.from(todayLocal.atStartOfDay().atZone(ZoneId.of("Europe/Moscow")).toInstant());

        Weather_history history;

        history = getWeather_historyToDate(todayUtil);

        if (history == null) {
            history = createWeather_historyToDate(todayUtil);
        }


        ctx.result(history.getWeather_value());
    };

    private static Weather_history getWeather_historyToDate(Date todayUtil) {
        Weather_history history = new QWeather_history()
                .weather_date.equalTo(todayUtil)
                .findOne();
        return history;
    }

    private static Weather_history createWeather_historyToDate(Date todayUtil) {

        String weather_value = getWeatherToYandex();
        Weather_history newWeather_history = new Weather_history(todayUtil, weather_value);
        newWeather_history.save();

        return newWeather_history;
    }

    private static String getWeatherToYandex() {
        return "-10";
    }
}

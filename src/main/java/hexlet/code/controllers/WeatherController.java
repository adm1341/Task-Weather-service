package hexlet.code.controllers;

import hexlet.code.domain.Weather_history;
import hexlet.code.domain.query.QWeather_history;
import io.javalin.http.Handler;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

        try {
            String htmlString = getHTMLYandex();
            String tempHTML = htmlString.substring(htmlString.indexOf("weather__temp")).substring(15, 18);
            tempHTML.trim();
            tempHTML = tempHTML.replaceAll("\u2212", "-"); //Symbol −
            tempHTML = tempHTML.replaceAll("\u00b0", "");//Symbol °
            return tempHTML;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "-100";
    }

    private static String getHTMLYandex() throws IOException, URISyntaxException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://yandex.ru/"))
                .GET()
                .build();
        HttpResponse response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }
}

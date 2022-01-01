package hexlet.code.pageLoader;

import hexlet.code.App;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class YandexPageLoader {
    public static String getHTML() throws URISyntaxException, IOException, InterruptedException {

        if (App.isProduction()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://yandex.ru/"))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .proxy(ProxySelector.getDefault())
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } else {
            return """
                    <div class='widget__content weather__content-outer'>
                    \t\t\t\t\t\t\t\t\t\t\t\t<div class='weather__content'>
                    \t\t\t\t\t\t\t\t\t\t\t\t\t<a aria-label="-10 °C, небольшой снег" class="home-link home-link_black_yes weather__grade" href="https://yandex.ru/pogoda/?utm_source=home&utm_medium=web&utm_campaign=informer&utm_content=main_informer&utm_term=main_number" target="_blank" rel="noopener">
                    \t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class="weather__icon weather__icon_ovc_-sn" title="небольшой снег"></div>
                    \t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class='weather__temp'>−10°</div>
                    \t\t\t\t\t\t\t\t\t\t\t\t\t</a>
                    \t\t\t\t\t\t\t\t\t\t\t\t\t<div class='weather__forecast'><a aria-label="вечером -14 °C" class="home-link home-link_black_yes weather__forecast-link" href="https://yandex.ru/pogoda/?utm_source=home&utm_medium=web&utm_campaign=informer&utm_content=main_informer&utm_term=current_day_part" target="_blank" rel="noopener">Вечером&#160;−14</a>,
                    \t\t\t\t\t\t\t\t\t\t\t\t\t\t<br><a aria-label="ночью -18 °C" class="home-link home-link_black_yes weather__forecast-link" href="https://yandex.ru/pogoda/?utm_source=home&utm_medium=web&utm_campaign=informer&utm_content=main_informer&utm_term=next_day_part" target="_blank" rel="noopener">ночью&#160;−18</a></div>
                    \t\t\t\t\t\t\t\t\t\t\t\t</div>
                    \t\t\t\t\t\t\t\t\t\t\t</div>""";
        }

    }
}

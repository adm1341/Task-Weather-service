import hexlet.code.App;
import io.ebean.DB;
import io.ebean.Transaction;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.assertj.core.api.Assertions.assertThat;

public class WeatherControllerTest {
    @Test
    void testInit() {
        assertThat(true).isEqualTo(true);
    }

    private static Javalin app;
    private static String baseUrl;
    private static Transaction transaction;


    @BeforeEach
    void beforeEach() {
        transaction = DB.beginTransaction();
    }

    @AfterEach
    void afterEach() {
        transaction.rollback();
    }

    @BeforeAll
    public static void beforeAll() {
        app = App.getApp();
        app.start(0);
        int port = app.port();
        baseUrl = "http://localhost:" + port;

    }

    @AfterAll
    public static void afterAll() {
        app.stop();
    }


    @Test
    void testWeatherSimple() {
        HttpResponse<String> response = Unirest.get(baseUrl + "/weather/").asString();

        Integer weather = Integer.valueOf(response.getBody());

        assertThat(weather).isBetween(-100, 100);
    }
}

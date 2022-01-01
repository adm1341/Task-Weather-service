package hexlet.code;

import hexlet.code.controllers.WeatherController;
import io.javalin.Javalin;

public class App {
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "3000");
        return Integer.valueOf(port);
    }

    private static void addRoutes(Javalin app) {
        app.get("/weather", WeatherController.weather);
    }

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.enableDevLogging();
        });

        addRoutes(app);

        app.before(ctx -> {
            ctx.attribute("ctx", ctx);
        });

        return app;
    }

    private static String getMode() {
        return System.getenv().getOrDefault("APP_ENV", "development");
    }

    public static boolean isProduction() {
        return getMode().equals("production");
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }
}
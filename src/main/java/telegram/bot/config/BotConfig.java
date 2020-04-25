package telegram.bot.config;

public class BotConfig {

    private BotConfig() {
        throw new IllegalStateException("Config class");
    }
    public static final String COVID_TOKEN = "909718704:AAF1O6VxafVioSkxD6bM1UU_gza0uH6MQlI";
    public static final String COVID_USER = "EnrolleeKhpi_bot";

    public static final String API_FIRST_COVID = "https://api.covid19api.com/live/country/";
    public static final String API_SECOND_COVID = "/status/confirmed/date/";

}

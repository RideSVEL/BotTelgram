package telegram.bot.config;

public class BotConfig {

    private BotConfig() {
        throw new IllegalStateException("Config class");
    }
    public static final String COVID_TOKEN = "1197223829:AAGHaA61L8te45gdgR59RI1EwgVx_a2FB9g";
    public static final String COVID_USER = "covidStatsinfobot";

    public static final String API_FIRST_COVID = "https://api.covid19api.com/country/";
    public static final String API_SECOND_COVID = "?from=";
    public static final String API_THIRD_COVID = "&to=";
    public static final String API_WORLD = "https://api.covid19api.com/world/total";
    public static final String ALL_COUNTRIES = "https://api.covid19api.com/countries";

    public static final String STICKER_HI = "src\\main\\resources\\img\\AnimatedSticker.tgs";
    public static final String STICKER_COVID = "src\\main\\resources\\img\\AnimatedStickerKorona.tgs";
    public static final String STICKER_HELP = "src\\main\\resources\\img\\AnimatedStickerHelp.tgs";


}

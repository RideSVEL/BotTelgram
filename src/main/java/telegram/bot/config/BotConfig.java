package telegram.bot.config;

public class BotConfig {

    private BotConfig() {
        throw new IllegalStateException("Config class");
    }

    public static final String COVID_TOKEN = "<token>";
    public static final String COVID_USER = "covidStatsinfobot";

    public static final String API_WORLD = "https://api.thevirustracker.com/free-api?global=stats";
    public static final String ALL_COUNTRIES = "https://api.covid19api.com/countries";
    public static final String COUNTRIES_PATH_TO_TXT = "src\\main\\resources\\countryRu.txt";

    public static final String PROPERTY_COUNTRY = "src\\main\\resources\\countries.properties";
    public static final String PROPERTY_ISO = "src\\main\\resources\\isoCountries.properties";

    public static final String API_OTHER = "https://api.thevirustracker.com/free-api?countryTotal=";

    public static final String STICKER_HI = "src\\main\\resources\\img\\AnimatedSticker.tgs";
    public static final String STICKER_COVID = "src\\main\\resources\\img\\AnimatedStickerKorona.tgs";
    public static final String STICKER_HELP = "src\\main\\resources\\img\\AnimatedStickerHelp.tgs";

    public static final String ENCODE = "Cp1251";

    public static final String[] PATH_STICKERS_CORONA = {"0.tgs", "1.tgs", "2.tgs", "3.tgs", "4.tgs", "5.tgs", "6.tgs", "7.tgs", "8.tgs", "9.tgs", "10.tgs",
            "13.tgs", "14.tgs", "15.tgs", "16.tgs", "17.tgs", "18.tgs", "19.tgs", "20.tgs", "21.tgs", "22.tgs", "23.tgs", "24.tgs", "25.tgs", "26.tgs", "27.tgs",
            "28.tgs", "30.tgs", "31.tgs", "32.tgs", "32.tgs", "33.tgs", "34.tgs", "35.tgs", "36.tgs", "37.tgs", "38.tgs", "39.tgs", "40.tgs", "41.tgs", "42.tgs",
            "43.tgs", "44.tgs", "45.tgs", "46.tgs", "47.tgs", "48.tgs", "49.tgs", "50.tgs", "51.tgs", "52.tgs", "53.tgs", "54.tgs", "55.tgs", "56.tgs"};


}

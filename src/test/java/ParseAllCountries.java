import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.config.BotConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;

public class ParseAllCountries {

    public static void main(String[] args) {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(BotConfig.ALL_COUNTRIES)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        String result = "Фолклендские (Мальвинские) острова, Израиль, Корея (Южная), Пуэрто-Рико, Сент-Винсент и Гренадины, Гайана, Бурунди, Северные Марианские острова, Иордания, Бермудские острова, Науру, Португалия, Бенин, Гондурас, Молдова, Сен-Мартен (французская часть) Кипр, Маврикий, Таджикистан, Гамбия, Французская Гвиана, Греция, Мадагаскар, Южный Судан, Латвия, Люксембург, Филиппины, Тринидад и Тобаго, Соединенные Штаты Америки, Канада, Коморские Острова, Джерси, Ливия, Бельгия, Непал, Дания, Габон, Гернси, Сьерра-Леоне, Словакия, Швеция, Того, Венесуэла (Боливарианская Республика), Армения, Эритрея, Исландия, Тунис, Ангилья, Доминиканская Республика, Мексика, Замбия, Аландские острова, Гвинея-Бисау, Сейшельские Острова, Уганда, Виргинские острова, США , Белиз, Зимбабве, Намибия, Чили, Сан-Томе и Принсипи, Бразилия, Болгария, Острова Кука, Малайзия, Марокко, Румыния, Узбекистан, Алжир, Йемен, Джибути, Австралия, Каймановы острова, Колумбия, Великобритания, Малые отдаленные острова США, Литва, Сент-Люсия, Сирийская Арабская Республика (Сирия), Святой Престол (Ватикан) Город-государство), Ирландия, Иран, Исламская Республика, Южная Джорджия и Южные Сандвичевы острова, Аруба, Андорра, Остров Буве, Республика Косово, Германия, Никарагуа, Парагвай, Французские Южные Территории, Камбоджа, Нигер, Саудовская Аравия, Италия, Перу, Ангола, Антарктида, Нидерланды, Ниуэ, Сингапур, Барбадос, Доминика, Папуа-Новая Гвинея, Сен-Пьер и Микелон, Египет, Фарерские острова, Казахстан, Макао, ЮАР, Маршалловы Острова, Монтсеррат, Мозамбик, Чехия, Шри-Ланка, Испания, Багамские Острова, Лаосская НДР, Украина, Аргентина, Сальвадор, ЮАР, Венгрия, Кувейт, Микронезия, Федеративные Штаты, Гренада, Ямайка, Япония, Монголия, Пакистан, Грузия, Гватемала, Мальдивы, Майотта, Черногория, Бахрейн, Куба , Французская Полинезия, Оман, Танзания, Объединенная Республика, Сент-Китс и Невис, Сенегал, Хорватия, Антигуа и Барбуда, Афганистан, Гана, Гренландия, Ирак, Российская Федерация, Уругвай, Франция, Индия, Питкэрн, Судан, Объединенные Арабские Эмираты, Бангладеш, Эфиопия, Гаити, Кения, Маврикий a, Боливия, Корея (Северная), Реюньон, Тонга, Македония, Республика, Катар, Руанда, Соломоновы Острова, Свазиленд, Гваделупа, Гуам, Кыргызстан, Мьянма, Тайвань, Китайская Республика, Вануату, Эстония, Бутан, Кабо-Верде, Малави, остров Святой Елены, Тувалу, Палау, Тимор-Лешти, Вьетнам, Ливан, Конго (Киншаса), Финляндия, Мартиника, Суринам, Панама, Токелау, Бруней-Даруссалам, острова Уоллис и Футуна, Американское Самоа, Экваториальная Гвинея, Коста-Рика, Кирибати, Лесото, Палестинская территория, Словения, Турция, Туркменистан, острова Сан-Марино, Шпицберген и Ян-Майен, Босния и Герцеговина, Гвинея, Лихтенштейн, Мали, остров Норфолк, Буркина-Фасо, Конго (Браззавиль), Индонезия, Новая Зеландия, Норвегия, Центральноафриканская Республика, Британские Виргинские острова, Новая Каледония, Польша, Сербия, Ботсвана, Сен-Бартельми, Самоа, Китай, Камерун, Чад, Фиджи, острова Херд и Макдональд, Монако, Сомали, Британская территория в Индийском океане, Австрия, Беларусь, Гонконг Гонконг, ЮАР, Остров Мэн, Нидерландские Антильские острова, Таил и Западная Сахара, Албания, Эквадор, Кокосовые (Килинг) острова, Гибралтар, Нигерия, Азербайджан, Остров Рождества, Кот-д'Ивуар, Либерия, Мальта, Швейцария, острова Теркс и Кайкос,";
        String[] results = result.split(",");
//        for (String s : results) {
//            System.out.println(s);
//        }
        System.out.println(results.length);
        if (response != null) {
            JSONArray jsonArray = new JSONArray(response.getBody());
            JSONObject jsonObject;
            System.out.println(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("ISO2").toLowerCase()
                        + " = " + jsonObject.getString("Slug"));
//                System.out.print(jsonObject.getString("Country") + ", ");
//                System.out.println(results[i].toLowerCase().replaceAll(" ", "-") + " = " + jsonObject.getString("Slug"));
            }

        }
    }
}

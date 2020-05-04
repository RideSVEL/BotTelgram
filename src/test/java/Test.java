import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.Russian;
import org.languagetool.rules.RuleMatch;
import telegram.bot.config.BotConfig;
import telegram.bot.entity.Country;
import telegram.bot.model.FormStats;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class Test {
    public static void main(String[] args) throws IOException {
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(simpleDateFormat.format(date));
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(simpleDateFormat.format(cal.getTime()));
//        String result = "{\"Country\":\"Ukraine\",\"CountryCode\":\"UA\",\"Province\":\"\",\"City\":\"\",\"CityCode\":\"\",\"Lat\":\"48.38\",\"Lon\":\"31.17\",\"Confirmed\":8125,\"Deaths\":201,\"Recovered\":782,\"Active\":7142,\"Date\":\"2020-04-25T00:00:00Z\"}";
//        HttpResponse<String> response = getResponse("ukraine", -1);
//        System.out.println(response.getBody());
//        String temp =  response.getBody();
//        temp = temp.replaceAll("\\[", "");
//        temp = temp.replaceAll("\\]", "");
//        System.out.println(temp);
//        Country country = parseCountry(temp);
//        System.out.println(country);
//        JLanguageTool lt = new JLanguageTool(new Russian());
//
//        List<RuleMatch> matches = lt.check("укроена лудшая страна");
//        List<String> list = null;
//        for (RuleMatch match : matches) {
////            System.out.println("Potential error at characters " +
////                    match.getFromPos() + "-" + match.getToPos() + ": " +
////                    match.getMessage());
////            System.out.println("Suggested correction(s): " +
//                list = match.getSuggestedReplacements();
//        }

        Country country = FormStats.spellChecking("укроиНА");
        System.out.println(country);
    }

    private static String generateDate(int amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(cal.getTime()) + "T00:00:00Z";
    }


    public static Country parseCountry(String result) {
        Country country = new Country();
//        JSONObject json = new JSONObject(result);
        JSONArray jsonArray = new JSONArray(result);
        JSONObject temp = jsonArray.getJSONObject(0);


        country.setName(temp.getString("Country"));
        country.setTotalConfirmed(temp.getInt("Confirmed"));
        country.setTotalDeath(temp.getInt("Deaths"));
        country.setTotalRecovered(temp.getInt("Recovered"));
        country.setActive(temp.getInt("Active"));
        //country.setDate(temp.getString("Date"));
        return country;
    }
}

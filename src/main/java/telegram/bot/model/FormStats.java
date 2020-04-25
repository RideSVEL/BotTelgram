package telegram.bot.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.config.BotConfig;
import telegram.bot.entity.Country;

public class FormStats {

    private static String generateDate(int amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(cal.getTime()) + "T00:00:00Z";
    }

    private static HttpResponse<String> getResponse(String countryName, int amount) {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(BotConfig.API_FIRST_COVID + countryName + BotConfig.API_SECOND_COVID + generateDate(amount))
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static Country parseCountry(String result) {
        Country country = new Country();
        JSONArray jsonArray = new JSONArray(result);
        JSONObject temp = jsonArray.getJSONObject(0);
        country.setName(temp.getString("Country"));
        country.setTotalConfirmed(temp.getInt("Confirmed"));
        country.setTotalDeath(temp.getInt("Deaths"));
        country.setTotalRecovered(temp.getInt("Recovered"));
        country.setActive(temp.getInt("Active"));
        country.setDate(temp.getString("Date"));
        return country;
    }

    public static Country getCountry(String countryName) {
        HttpResponse<String> response = getResponse(countryName, -1);
        if (response != null) {
            if (response.getBody().equals("[]\n")) {
                response = getResponse(countryName, -2);
                if (response.getBody().equals("[]\n")) {
                    return null;
                }
                return parseCountry(response.getBody());
            }
            return parseCountry(response.getBody());
        }
        return null;
    }

}


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
        JSONArray jsonArray = new JSONArray(result);
        if (jsonArray.length() != 2) {
            return null;
        }
        Country country = new Country();
        JSONObject yesterday = jsonArray.getJSONObject(0);
        JSONObject now = jsonArray.getJSONObject(1);
        country.setName(now.getString("Country"));
        country.setTotalConfirmed(now.getInt("Confirmed"));
        country.setTotalDeath(now.getInt("Deaths"));
        country.setTotalRecovered(now.getInt("Recovered"));
        country.setActive(now.getInt("Active"));
        country.setDate(now.getString("Date"));
        country.setNewConfirmed(country.getTotalConfirmed() - yesterday.getInt("Confirmed"));
        country.setNewRecovered(country.getTotalRecovered() - yesterday.getInt("Recovered"));
        country.setNewDeath(country.getTotalDeath() - yesterday.getInt("Deaths"));
        return country;
    }

    public static Country getCountry(String countryName) {
        HttpResponse<String> response = getResponse(countryName, -2);
        if (response != null) {
            if (response.getBody().equals("[]\n")) {
                    return null;
                }
            if (new JSONArray(response.getBody()).length() != 2) {
                HttpResponse<String> response2 = getResponse(countryName, -3);
                if (response2 != null) {
                    return parseCountry(response2.getBody());
                }
            }
            return parseCountry(response.getBody());
        }
        return null;
    }

}


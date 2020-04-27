package telegram.bot.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.config.BotConfig;
import telegram.bot.entity.Country;
import telegram.bot.entity.World;

public class FormStats {

    private static String generateDate(int amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(cal.getTime()) + "T00:00:00Z";
    }

    private static HttpResponse<String> getResponse(String countryName, int amount, boolean world) {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            if (world) {
                response = Unirest.get(BotConfig.API_WORLD).asString();
            } else {
                response = Unirest.get(BotConfig.API_FIRST_COVID + countryName + BotConfig.API_SECOND_COVID + generateDate(amount))
                        .asString();
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static Country parseCountry(String response) {
        JSONArray jsonArray = new JSONArray(response);
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

    private static World parseWorld(String response) {
        JSONObject jsonObject = new JSONObject(response);
        World world = new World();
        world.setTotalConfirmed(jsonObject.getInt("TotalConfirmed"));
        world.setTotalDeath(jsonObject.getInt("TotalDeaths"));
        world.setTotalRecovered(jsonObject.getInt("TotalRecovered"));
        return world;
    }

    private static String countriesProperties(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream("src\\main\\resources\\countries.properties");
        BufferedReader inBuf = new BufferedReader(new InputStreamReader(in, "Cp1251"));
        properties.load(inBuf);
        inBuf.close();
        return properties.getProperty(key, null);
    }

    public static World getWorldTotal() {
        HttpResponse<String> response = getResponse(null, 0, true);
        if (response != null) {
            if (response.getBody().equals("[]\n")) {
                return null;
            }
            return parseWorld(response.getBody());
        }
        return null;
    }



    public static Country getCountry(String countryName) {
        String slug = null;
        try {
            slug = countriesProperties(countryName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (slug != null) {
            HttpResponse<String> response = getResponse(countryName.toLowerCase(), -2, false);
            if (response != null) {
                if (response.getBody().equals("[]\n")) {
                    return null;
                }
                if (new JSONArray(response.getBody()).length() != 2) {
                    HttpResponse<String> response2 = getResponse(countryName.toLowerCase(), -3, false);
                    if (response2 != null) {
                        return parseCountry(response2.getBody());
                    }
                }
                return parseCountry(response.getBody());
            }
        }
        return null;
    }

}


package telegram.bot.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    private static HttpResponse<String> getResponse(String countryName, int amount, Cases cases) {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            switch (cases) {
                case world:
                    response = Unirest.get(BotConfig.API_WORLD).asString();
                    break;
                case general:
                    response = Unirest.get(BotConfig.API_FIRST_COVID + countryName + BotConfig.API_SECOND_COVID + generateDate(amount))
                            .asString();
                    break;
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

    public static List<String> getCountries(char symbol) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("src\\main\\resources\\countryRu.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines != null) {
            List<String> result = new ArrayList<>();
            for (String string : lines) {
                if (string.charAt(0) == symbol) {
                    result.add(string);
                }
            }
            return result;
        }
        return null;
    }

    public static World getWorldTotal() {
        HttpResponse<String> response = getResponse(null, 0, Cases.world);
        if (response != null) {
            if (response.getBody().equals("[]\n")) {
                return null;
            }
            return parseWorld(response.getBody());
        }
        return null;
    }

    private static Country parseFrance(String body) {
        JSONArray jsonArray = new JSONArray(body);
        JSONObject now = null;
        JSONObject yesterday = null;
        int half = jsonArray.length() / 2;
        for (int i = 0; i < half; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("Province").equals("")) {
                yesterday = jsonObject;
            }
        }
        for (int i = half; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("Province").equals("")) {
                now = jsonObject;
            }
        }
        Country country = new Country();
        assert now != null;
        assert yesterday != null;
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

    private static Country parseByProvinces(String body) {
        JSONArray jsonArray = new JSONArray(body);
        int lstConfirmed = 0;
        int lstRecovered = 0;
        int lstDeath = 0;
        for (int i = 0; i < jsonArray.length() / 2; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            lstConfirmed = lstConfirmed + jsonObject.getInt("Confirmed");
            lstDeath = lstDeath + jsonObject.getInt("Deaths");
            lstRecovered = lstRecovered + jsonObject.getInt("Recovered");
        }
        Country country = new Country();
        for (int i = jsonArray.length() / 2; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            country.setTotalConfirmed(country.getTotalConfirmed() + jsonObject.getInt("Confirmed"));
            country.setTotalDeath(country.getTotalDeath() + jsonObject.getInt("Deaths"));
            country.setTotalRecovered(country.getTotalRecovered() + jsonObject.getInt("Recovered"));
            country.setActive(country.getActive() + jsonObject.getInt("Active"));
        }
        country.setName(jsonArray.getJSONObject(0).getString("Country"));
        country.setDate(jsonArray.getJSONObject(0).getString("Date"));
        country.setNewDeath(country.getTotalDeath() - lstDeath);
        country.setNewConfirmed(country.getTotalConfirmed() - lstConfirmed);
        country.setNewRecovered(country.getTotalRecovered() - lstRecovered);
        return country;
    }




    public static Country getCountry(String countryName) {
        String slug = null;
        try {
            slug = countriesProperties(countryName.replaceAll(" ", "-").toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (slug != null) {
            HttpResponse<String> response = getResponse(slug.toLowerCase(), -2, Cases.general);
            if (response != null) {
                if (response.getBody().equals("[]\n")) {
                    return null;
                }
                switch (slug) {
                    case "france":
                        return parseFrance(response.getBody());
                    case "china":
                    case "united-states":
                        return parseByProvinces(response.getBody());
                }
                if (new JSONArray(response.getBody()).length() != 2) {
                    HttpResponse<String> response2 = getResponse(slug.toLowerCase(), -3, Cases.general);
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


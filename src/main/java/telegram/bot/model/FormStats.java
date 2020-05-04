package telegram.bot.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.Russian;
import org.languagetool.rules.RuleMatch;
import telegram.bot.config.BotConfig;
import telegram.bot.entity.Country;
import telegram.bot.entity.World;

public class FormStats {

    private static HttpResponse<String> getResponse(String countryName, Cases cases) {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            switch (cases) {
                case other:
                    response = Unirest.get(BotConfig.API_OTHER + countryName).asString();
                    break;
                case world:
                    response = Unirest.get(BotConfig.API_WORLD).asString();
                    break;
            }
        } catch (UnirestException e) {
            return null;
        }
        return response;
    }

    private static String countriesProperties(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(BotConfig.PROPERTY_COUNTRY);
        BufferedReader inBuf = new BufferedReader(new InputStreamReader(in, BotConfig.ENCODE));
        properties.load(inBuf);
        inBuf.close();
        return properties.getProperty(key, null);
    }

    private static String isoProperties(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(BotConfig.PROPERTY_ISO);
        BufferedReader inBuf = new BufferedReader(new InputStreamReader(in, BotConfig.ENCODE));
        properties.load(inBuf);
        inBuf.close();
        return properties.getProperty(key, null);
    }

    public static Country spellChecking(String word) throws IOException {
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ\\s\\p{Punct}]+");
        Matcher matcher = pattern.matcher(word.toLowerCase());
        System.out.println(matcher.matches());
        JLanguageTool lt;
        if (matcher.matches()) {
            lt = new JLanguageTool(new Russian());
        } else {
            lt = new JLanguageTool(new AmericanEnglish());
        }
        List<RuleMatch> matches = lt.check(word.toLowerCase());
        List<String> list = null;
        for (RuleMatch match : matches) {
            list = match.getSuggestedReplacements();
        }
        if (list != null && list.size() != 0) {
            for (String check : list) {
                String key = countriesProperties(check.toLowerCase().replaceAll(" ", "-"));
                if (key != null) {
                    return getCountry(key);
                }
            }
        }
        return null;
    }

    public static List<String> getCountriesByRegex(String regex) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(BotConfig.COUNTRIES_PATH_TO_TXT), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines != null) {
            List<String> result = new ArrayList<>();
            for (String string : lines) {
                if (string.length() == regex.length()) {
                    break;
                }
                Pattern p = Pattern.compile("^" + regex.toLowerCase());
                Matcher m = p.matcher(string.toLowerCase());
                if (m.find()) {
                    result.add(string);
                }
            }
            return result;
        }
        return null;
    }

    public static List<String> getCountries(char symbol) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(BotConfig.COUNTRIES_PATH_TO_TXT), StandardCharsets.UTF_8);
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
        HttpResponse<String> response = getResponse(null, Cases.world);
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                World world = new World();
                world.setTotalConfirmed(jsonObject1.getInt("total_cases"));
                world.setTotalDeath(jsonObject1.getInt("total_deaths"));
                world.setTotalRecovered(jsonObject1.getInt("total_recovered"));
                world.setNewConfirmed(jsonObject1.getInt("total_new_cases_today"));
                world.setNewDeath(jsonObject1.getInt("total_new_deaths_today"));
                world.setNumberCountries(jsonObject1.getInt("total_affected_countries"));
                world.setActive(jsonObject1.getInt("total_active_cases"));
                return world;
            } catch (JSONException ex) {
                return null;
            }
        }
        return null;
    }


    private static Country parseByOtherAPI(String countryCode) {
        HttpResponse<String> response = getResponse(countryCode.toUpperCase(), Cases.other);
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray jsonArray = jsonObject.getJSONArray("countrydata");
                JSONObject json2 = jsonArray.getJSONObject(0);
                Country country = new Country();
                country.setTotalConfirmed(json2.getInt("total_cases"));
                country.setTotalRecovered(json2.getInt("total_recovered"));
                country.setTotalDeath(json2.getInt("total_deaths"));
                country.setNewConfirmed(json2.getInt("total_new_cases_today"));
                country.setNewDeath(json2.getInt("total_new_deaths_today"));
                country.setActive(json2.getInt("total_active_cases"));
                country.setRank(json2.getInt("total_danger_rank"));
                country.setHardState(json2.getInt("total_serious_cases"));
                JSONObject json3 = json2.getJSONObject("info");
                country.setName(json3.getString("title"));
                return country;
            } catch (JSONException ex) {
                return null;
            }
        }
        return null;
    }


    public static Country getCountry(String countryName) {
        String slug;
        try {
            slug = countriesProperties(countryName.replaceAll(" ", "-").toLowerCase());
            if (slug != null) {
                return parseByOtherAPI(isoProperties(slug));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}


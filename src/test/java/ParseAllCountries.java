import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.config.BotConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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



//        String[] results = result.split("\n");
        List<String> lines = null;
        try {
             lines = Files.readAllLines(Paths.get(BotConfig.COUNTRIES_PATH_TO_TXT), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : lines) {
            System.out.println(s);
        }

//        System.out.println(results.length);
        if (response != null) {
            JSONArray jsonArray = new JSONArray(response.getBody());
            JSONObject jsonObject;
            System.out.println(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("Slug") + " = " + jsonObject.getString("ISO2"));
            }

        }
    }
}

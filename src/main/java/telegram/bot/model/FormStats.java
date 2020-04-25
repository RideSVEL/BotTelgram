package telegram.bot.model;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import telegram.bot.entity.Country;

public class FormStats {

    public static String getBody(String countryName, Country country) {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            response = Unirest.get("https://api.covid19api.com/live/country/" + countryName + "/status/confirmed/date/" + simpleDateFormat.format(cal.getTime()) + "T00:00:00Z")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        assert response != null;
        if (response.getBody().equals("[]\n")) {
            return "This county is not valid";
        }
        System.out.println(response.getBody());
        return response.getBody();
    }

}


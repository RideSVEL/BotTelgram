package telegram.bot.model;

import com.mashape.unirest.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.entity.Country;

public class ParseJSON {

    public Country parseCountry(String result) {
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

}

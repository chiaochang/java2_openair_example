package sample;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Country {
    private String name;
    private String code;
    private int cities;

    public static List<Country> getCountries()
    {
        String data = readFromAPI();

        Gson gson = new Gson();
        System.out.println(data);
        JsonObject parsed = gson.fromJson(data, JsonObject.class);
        JsonArray countries = parsed.getAsJsonArray("results");

        ArrayList<Country> result = new ArrayList<Country>();

        for(JsonElement i : countries)
        {
            JsonObject item = i.getAsJsonObject();
            String name = item.getAsJsonPrimitive("name").getAsString();
            String code = item.getAsJsonPrimitive("code").getAsString();
            int cities = item.getAsJsonPrimitive("cities").getAsInt();

            Country obj = new Country(name, code, cities);
            result.add(obj);
        }

        return result;
    }

    public static String readFromAPI()
    {
        String contents = "";
        try {
            URL address = new URL("https://api.openaq.org/v1/countries");

            InputStreamReader reader = new InputStreamReader(address.openStream());
            BufferedReader buffer = new BufferedReader(reader);

            String line = "";
            while((line = buffer.readLine()) != null)
            {
                if (line.isEmpty())
                {
                    break;
                }
                contents += line;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(contents);
        return contents;
    }

    public Country(String name, String code, int cities)
    {
        this.name = name;
        this.code = code;
        this.cities = cities;
        // the fields can only be set upon creation of the object
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public int getCities()
    {
        return cities;
    }

}

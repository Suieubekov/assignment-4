import java.util.HashMap;
import java.util.Map;

class WeatherData {
    private final String location;
    private final double temperature;
    private final String condition;

    public WeatherData(String location, double temperature, String condition) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
    }


    @Override
    public String toString() {
        return "Location: " + location + ", Temperature: " + temperature + "°C, Condition: " + condition;
    }
}

interface WeatherProvider {
    WeatherData getWeather(String location);
}


class OpenWeatherMapProvider implements WeatherProvider {
    @Override
    public WeatherData getWeather(String location) {

        Map<String, Object> apiData = new HashMap<>();
        apiData.put("city", location);
        apiData.put("temperature", 25.5);
        apiData.put("conditions", "Sunny");

        return new WeatherData((String) apiData.get("city"), (double) apiData.get("temperature"), (String) apiData.get("conditions"));
    }
}

class WeatherAPIProvider implements WeatherProvider {
    @Override
    public WeatherData getWeather(String location) {

        Map<String, Object> apiData = new HashMap<>();
        apiData.put("location", location);
        apiData.put("temp", 27.0);
        apiData.put("weather", "Clear");

        return new WeatherData((String) apiData.get("location"), (double) apiData.get("temp"), (String) apiData.get("weather"));
    }
}


class OpenWeatherMapAdapter implements WeatherProvider {
    private final OpenWeatherMapProvider provider;

    public OpenWeatherMapAdapter(OpenWeatherMapProvider provider) {
        this.provider = provider;
    }

    @Override
    public WeatherData getWeather(String location) {
        return provider.getWeather(location);
    }
}

class WeatherAPIAdapter implements WeatherProvider {
    private final WeatherAPIProvider provider;

    public WeatherAPIAdapter(WeatherAPIProvider provider) {
        this.provider = provider;
    }

    @Override
    public WeatherData getWeather(String location) {
        return provider.getWeather(location);
    }
}


public class MobileApp {
    private final WeatherProvider weatherProvider;

    public MobileApp(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public void displayWeather(String location) {
        WeatherData data = weatherProvider.getWeather(location);
        System.out.println(data);
    }

    public static void main(String[] args) {
        MobileApp app1 = new MobileApp(new OpenWeatherMapAdapter(new OpenWeatherMapProvider()));
        app1.displayWeather("Shymkent");

        MobileApp app2 = new MobileApp(new WeatherAPIAdapter(new WeatherAPIProvider()));
        app2.displayWeather("Almaty");
    }
}
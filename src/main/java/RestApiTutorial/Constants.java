package RestApiTutorial;

import io.github.cdimascio.dotenv.Dotenv;

public class Constants {
    final static private String API_KEY = Dotenv.load().get("API_KEY");
    final static private String BASE_URL = "https://api.assemblyai.com/v2/transcript";

    final static private String FILE = Dotenv.load().get("FILE");
    final static private String LANGUAGE = Dotenv.load().get("LANGUAGE");

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getFile() {
        return FILE;
    }

    public static String getLanguage() {
        return LANGUAGE;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}

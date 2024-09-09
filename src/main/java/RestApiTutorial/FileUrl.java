package RestApiTutorial;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUrl {
    private static Gson gson = new Gson();
    public static HttpResponse<String> uploadFileToAssemblyAI()
            throws URISyntaxException, IOException, InterruptedException {

        Path filePath = Paths.get(Constants.getFile());
        byte[] fileBytes = Files.readAllBytes(filePath);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.assemblyai.com/v2/upload"))
                .header("Authorization", Constants.getApiKey())
                .header("Content-Type", "application/octet-stream")
                .POST(HttpRequest.BodyPublishers.ofByteArray(fileBytes))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

package RestApiTutorial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static Transcript transcript = Transcript.getInstance();

    public static void main(String[] args) throws Exception {
        Gson gsonWithUpload = new Gson();

        HttpResponse<String> uploadResponse = FileUrl.uploadFileToAssemblyAI();

        transcript = gsonWithUpload.fromJson(uploadResponse.body(), Transcript.class);
        transcript.setAudio_url(transcript.getUpload_url());
        transcript.setLanguage_code(Constants.getLanguage());

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()  // Указываем, что должны быть сериализованы только поля с @Expose
                .create();

        HttpClient httpClient = HttpClient.newHttpClient();
        String jsonRequest = gson.toJson(transcript);

        HttpResponse<String> response = sendRequestToAssemblyAI(jsonRequest, httpClient, gson);
        System.out.println(response);

        checkStatus(httpClient, gson);
        if (transcript.getText() != null) {
            System.out.println("transcription " + transcript.getStatus());
            System.out.println(transcript.getText());
        } else {
            System.out.println("Transcription failed");
        }
    }

    private static void checkStatus (HttpClient httpClient, Gson gson)
            throws URISyntaxException, IOException, InterruptedException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(Constants.getBaseUrl() + "/" + transcript.getId()))
                .header("Authorization", Constants.getApiKey())
                .build();
        while (true) {
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            transcript = gson.fromJson(getResponse.body(), Transcript.class);
            System.out.println(transcript.getStatus());
            if ("completed".equals(transcript.getStatus())
                    || "error".equals(transcript.getStatus())
                    || transcript.getStatus() == null
            ) {
                break;
            }
            Thread.sleep(1000); // wait for 1 second before making another request
        }

    }

    private static HttpResponse<String> sendRequestToAssemblyAI (
            String jsonRequest,
            HttpClient httpClient,
            Gson gson
            ) throws InterruptedException, URISyntaxException, IOException {

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(Constants.getBaseUrl()))
                .header("Authorization", Constants.getApiKey())
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        transcript = gson.fromJson(postResponse.body(), Transcript.class);

        return postResponse;
    }
}

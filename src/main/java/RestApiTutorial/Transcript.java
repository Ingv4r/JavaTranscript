package RestApiTutorial;

import com.google.gson.annotations.Expose;

public class Transcript {
    private static Transcript instance;
    private Transcript() {}
    public static Transcript getInstance() {
        if (instance == null) {
            instance = new Transcript();
        }
        return instance;
    }

    @Expose
    private String audio_url;
    @Expose
    private String id;
    @Expose
    private String status;
    @Expose
    private String text;
    @Expose
    private String language_code;

    private String upload_url; // Поле не будет сериализовано

    public String getUpload_url() {
        return upload_url;
    }

    public void setUpload_url(String upload_url) {
        this.upload_url = upload_url;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

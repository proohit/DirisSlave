package bot.database;

public class Song {

    private int id;
    private String title;
    private String url;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp;

    public Song(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public Song(int id, String title, String url, String timestamp) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.timestamp = timestamp;
    }

    public Song(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return timestamp == null ? id + " " + title + " " + url : id + " " + title + " " + url + " " + timestamp;
    }
}

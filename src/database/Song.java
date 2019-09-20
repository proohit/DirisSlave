package database;

public class Song {

    private int id;



    private String title;
    private String url;

    public Song(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
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

}

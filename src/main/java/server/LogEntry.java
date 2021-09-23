package server;

public class LogEntry {
    String message;
    String timestamp;
    String level;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LogEntry(String message, String timestamp, String level) {
        this.message = message;
        this.timestamp = timestamp;
        this.level = level;
    }

}

package bot.server;

public class LogEntry {
    String message;
    String timestamp;
    LogLevel level;
    String stackTrace;

    public LogEntry(String message, String timestamp, LogLevel level, String stackTrace) {
        this.message = message;
        this.timestamp = timestamp;
        this.level = level;
        this.stackTrace = stackTrace;
    }

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
        return level.getLevel();
    }

    public void setLevel(String level) {
        this.level = LogLevel.valueOf(level);
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public enum LogLevel {
        DEBUG("DEBUG"), INFO("INFO"), WARN("WARN"), ERROR("ERROR"), UNKNOWN("UNKNOWN");

        private String level;

        LogLevel(String level) {
            this.level = level;
        }

        public String getLevel() {
            return level;
        }
    }
}

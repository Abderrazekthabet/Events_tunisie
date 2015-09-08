package material_design.soussi.com.events_tunisie.map_menu_button;

/**
 * Created by Soussi on 10/05/2015.
 */
public class LogConfig {
    private boolean debug = true; //
    private boolean logFile = false; //
    private String logFilePath; //

    public boolean isDebug() {
        return debug;
    }

    public LogConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public boolean isLogFile() {
        return logFile;
    }

    public LogConfig setLogFile(boolean logFile) {
        this.logFile = logFile;
        return this;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public LogConfig setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
        return this;
    }

}

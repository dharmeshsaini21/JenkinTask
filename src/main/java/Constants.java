import java.io.File;

public class Constants {
    private Constants(){}
    public static final String PROJECT_DIR_PATH = System.getProperty("user.dir");
    public static final String TARGET_PATH = (PROJECT_DIR_PATH + "/target").replace("/", File.separator);

    public static final String CONF_PATH = (TARGET_PATH + "/test-classes/conf.properties").replace("/", File.separator);
}
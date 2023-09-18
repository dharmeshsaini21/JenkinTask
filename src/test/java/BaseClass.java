import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class BaseClass {
    static WebDriver driver;

    @BeforeMethod (onlyForGroups = {"browser"})
    public void setDriver(){
        driver = new ChromeDriver();
    }

    @AfterMethod (onlyForGroups = {"browser"})
    public void tearDown(){
        driver.quit();
    }

    public static void captureScreen(String screenShotPath) {
        try {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(screenShotPath));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.ITestAnnotation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ExtentReport extends TestListenerAdapter implements IAnnotationTransformer {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest extentTest;
    public String reportDir;

    public void createReportDirectory() {
        reportDir = Constants.TARGET_PATH + "/test-output/Reports/";

        if (!new File(reportDir).exists())
            new File(reportDir).mkdirs();
    }

    public void onStart(ITestContext testContext) {
        createReportDirectory();
        htmlReporter = new ExtentHtmlReporter(reportDir + "/HTML_Report.html");
        htmlReporter.loadXMLConfig(Constants.PROJECT_DIR_PATH + "/extent-config.xml");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("User", "Super Admin");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("OA name", "Dharmesh");

        htmlReporter.config().setDocumentTitle("TestNG"); // Tile of report
        htmlReporter.config().setReportName("TestNG Automation Report"); // name of the report
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
        htmlReporter.config().setTheme(Theme.DARK);
    }

    public void onTestSuccess(ITestResult tr) {
        // create new entry in the report
        extentTest = extent.createTest(tr.getName());

        // send the passed information to the report with GREEN color highlighted
        extentTest.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
    }

    public void onTestFailure(ITestResult tr) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String name = tr.getName() + "_Failed_" + timeStamp;

        // create new entry in the report
        extentTest = extent.createTest(tr.getName());

        // send the passed information to the report with GREEN color highlighted
        extentTest.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));

        String screenShotPath = (reportDir + "/Screenshot/" + name + ".png").replace("/", File.separator);
        captureAndEmbedScreenShot(screenShotPath);
    }

    public void onTestSkipped(ITestResult tr) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String name = tr.getName() + "_Skipped_" + timeStamp;

        extentTest = extent.createTest(tr.getName());
        extentTest.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
        // Calling the BaseClass method take Screen Shots.
        String screenShotPath = (reportDir + "/Screenshot/" + name + ".png").replace("/", File.separator);
        captureAndEmbedScreenShot(screenShotPath);
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }

    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryTestCaseOnFailure.class);
    }

    public void captureAndEmbedScreenShot(String screenShotPath){
        // Calling the BaseClass method take Screen Shots.
        BaseClass.captureScreen(screenShotPath);

        if (new File(screenShotPath).exists()) {
            try {
                String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(new File(screenShotPath).toPath()));
                extentTest.log(Status.FAIL, "Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
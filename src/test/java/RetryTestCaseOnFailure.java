import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTestCaseOnFailure implements IRetryAnalyzer {

    int counter = 1;
    int retryLimit = Integer.parseInt((String) ReadProperties.readPropertyValue(Constants.CONF_PATH, "retry.count"));

    @Override
    public boolean retry(ITestResult result) {
        if (counter < retryLimit) {
            counter++;
            return true;
        }
        return false;
    }
}
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestJenkins {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before method output");
    }

    @Test
    public void test() {
        System.out.println("test method output");
    }
}

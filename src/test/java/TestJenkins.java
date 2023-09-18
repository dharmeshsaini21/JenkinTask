import org.testng.Assert;
import org.testng.annotations.Test;

public class TestJenkins extends BaseClass {

    @Test
    public void test1(){
        System.out.println("test 1");
    }

    @Test
    public void test2(){
        System.out.println("test 2");
    }

    @Test
    public void test3(){
        System.out.println("test 3");
    }

    @Test(groups = {"browser"})
    public void test4(){
        driver.get("https://www.google.com");
        System.out.println("test 4");
        Assert.fail("explicitly fails");
    }
}

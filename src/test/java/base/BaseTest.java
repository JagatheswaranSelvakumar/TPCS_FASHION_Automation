package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.in.saragroup.tpcsambur.reports.ExtentManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void beforeSuite() {
        extent = ExtentManager.getInstance();
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
        ExtentManager.openExtentReportFile();
    }

    // Initialize a test for each test method
    protected void startTest(String testName, String description) {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
        test = extent.createTest(testName, description);
    }
}
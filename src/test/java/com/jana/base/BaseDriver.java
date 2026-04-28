package com.jana.base;

import com.jana.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

public class BaseDriver {

    protected AppiumDriver driver;
    private Properties props = new Properties();

    @BeforeClass
    public void setUp() throws Exception {
        // Load config.properties
        FileInputStream fis = new FileInputStream(
                "src/main/resources/config.properties"
        );
        props.load(fis);

        String platform   = props.getProperty("platform").toLowerCase();
        String appiumUrl  = props.getProperty("appium.url");

        DesiredCapabilities caps = new DesiredCapabilities();

        if (platform.equals("android")) {
            caps.setCapability("deviceName",      props.getProperty("android.deviceName"));
            caps.setCapability("platformName",    "Android");
            caps.setCapability("platformVersion", props.getProperty("android.platformVersion"));
            caps.setCapability("appPackage",      props.getProperty("android.appPackage"));
            caps.setCapability("appActivity",     props.getProperty("android.appActivity"));
            caps.setCapability("automationName",  props.getProperty("android.automationName"));

            driver = new AndroidDriver(new URL(appiumUrl), caps);

        } else if (platform.equals("ios")) {
            caps.setCapability("deviceName",      props.getProperty("ios.deviceName"));
            caps.setCapability("platformName",    "iOS");
            caps.setCapability("platformVersion", props.getProperty("ios.platformVersion"));
            caps.setCapability("bundleId",        props.getProperty("ios.bundleId"));
            caps.setCapability("automationName",  props.getProperty("ios.automationName"));

            driver = new IOSDriver(new URL(appiumUrl), caps);
        }

        System.out.println("Driver started on: " + platform);
    }

    @BeforeMethod
    public void startTest(java.lang.reflect.Method method) {
        String testName = method.getName();
        ExtentTest extentTest = ExtentReportManager
                .getInstance()
                .createTest(testName);
        ExtentReportManager.setTest(extentTest);
        ExtentReportManager.getTest().log(Status.INFO, "Test Started: " + testName);
    }

    @AfterMethod
    public void endTest(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.getTest().log(Status.PASS, "Test Passed ✅");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.getTest().log(Status.FAIL, "Test Failed ❌: "
                    + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped ⚠️");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver closed successfully");
        }
        ExtentReportManager.flushReports();
    }
}
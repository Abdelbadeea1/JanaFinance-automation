package com.jana.base;

import com.jana.utils.ConfigReader;
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

import java.net.URL;

public class BaseDriver {

    protected AppiumDriver driver;

    @BeforeClass
    public void setUp() throws Exception {

        String platform  = ConfigReader.get("platform").toLowerCase();
        String appiumUrl = ConfigReader.get("appium.url");

        DesiredCapabilities caps = new DesiredCapabilities();

        if (platform.equals("android")) {
            caps.setCapability("deviceName",      ConfigReader.get("android.deviceName"));
            caps.setCapability("platformName",    "Android");
            caps.setCapability("platformVersion", ConfigReader.get("android.platformVersion"));
            caps.setCapability("appPackage",      ConfigReader.get("android.appPackage"));
            caps.setCapability("appActivity",     ConfigReader.get("android.appActivity"));
            caps.setCapability("automationName",  ConfigReader.get("android.automationName"));

            driver = new AndroidDriver(new URL(appiumUrl), caps);

        } else if (platform.equals("ios")) {
            caps.setCapability("deviceName",      ConfigReader.get("ios.deviceName"));
            caps.setCapability("platformName",    "iOS");
            caps.setCapability("platformVersion", ConfigReader.get("ios.platformVersion"));
            caps.setCapability("bundleId",        ConfigReader.get("ios.bundleId"));
            caps.setCapability("automationName",  ConfigReader.get("ios.automationName"));

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
        ExtentReportManager.getTest().log(
                Status.INFO, "Test Started: " + testName
        );
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
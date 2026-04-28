package com.jana.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(
                    "test-output/ExtentReport.html"
            );
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("JanaFinance Test Report");
            spark.config().setReportName("Mobile Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project",  "JanaFinance");
            extent.setSystemInfo("Platform", System.getProperty("os.name"));
            extent.setSystemInfo("Tester",   "QA Engineer");
        }
        return extent;
    }

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
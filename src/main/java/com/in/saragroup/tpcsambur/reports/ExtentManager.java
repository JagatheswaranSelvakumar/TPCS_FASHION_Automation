package com.in.saragroup.tpcsambur.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.in.saragroup.tpcsambur.utilities.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ExtentManager {
    private static ExtentReports extent;
    private static final Logger log = LogManager.getLogger(ExtentManager.class);

    public static ExtentReports getInstance() {
        if (extent == null) {
            log.info("Initializing ExtentReports instance");
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            log.info("Report path set to: " + reportPath);
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
            log.info("ExtentSparkReporter initialized with path: " + reportPath);
            htmlReporter.config().setDocumentTitle("TPC Sambur Automation Report");
            htmlReporter.config().setReportName("Functional Test Report");
            htmlReporter.config().setTheme(Theme.STANDARD);
            log.info("Configured ExtentSparkReporter with document title, report name, and theme");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            log.info("Attached ExtentSparkReporter to ExtentReports instance");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", System.getProperty("browser", "Chrome"));
            extent.setSystemInfo("Tester", "TPC Sambur Automation Team");
            log.info("Set system information for ExtentReports: Environment, Browser, Tester");
        }
        return extent;
    }


    public static void openExtentReportFile() {
            String filePath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            try {
                File htmlFile = new File(filePath);
                log.info("Attempting to open Extent Report file: " + filePath);
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    log.info("Desktop browsing is supported, opening the report...");
                    URI uri = htmlFile.toURI();
                    Desktop.getDesktop().browse(uri);
                    log.info("Opening Extent Report in the default browser: " + filePath);
                } else {
                   log.error("Desktop browsing is not supported on this platform.");
                }
            } catch (IOException e) {
                log.error("An error occurred while trying to open the file: " + e.getMessage());
            }
        }
    }
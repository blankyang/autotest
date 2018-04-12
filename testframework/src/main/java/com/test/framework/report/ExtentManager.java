package com.test.framework.report;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

public class ExtentManager {
    private static ExtentReports extent;
    
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
            extent = new ExtentReports(filePath, false,NetworkMode.ONLINE);
            extent.loadConfig(new File("extent-config.xml"));
            extent
                .addSystemInfo("Host Name", "yangxiaobin")
                .addSystemInfo("Environment", "QA");
        }
        return extent;
    }
}
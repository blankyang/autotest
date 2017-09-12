package com.test.framework.report;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class ExtentBase {
	private static Logger logger = LogManager.getLogger("ExtentBase");  
    protected ExtentReports extent;
    protected static ExtentTest test;
    protected String logKey;   
    final String filePath = System.getProperty("user.dir") 
    		+ "/report/" 
    		+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
    		+ "/" + new SimpleDateFormat("hh-mm-ss").format(new Date())
    		+ "/TestReport.html";

    @AfterMethod
    protected void afterMethod(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            test.log(LogStatus.FAIL, result.getThrowable().toString());
//        } else if (result.getStatus() == ITestResult.SKIP) {
//            test.log(LogStatus.SKIP, "Test skipped " + result.getThrowable().toString());
//        } else {
//            test.log(LogStatus.PASS, "Test passed");
//        }
        if (result.getStatus() == ITestResult.SKIP) {
            test.log(LogStatus.SKIP, result.getThrowable().toString());
        }
        extent.endTest(test);        
        extent.flush();
    	long time = test.getEndedTime().getTime() - test.getStartedTime().getTime();
    	logger.info(logKey + "," + time + "ms");
    	
   }
   
    @BeforeSuite
    public void beforeSuite() {
        extent = ExtentManager.getReporter(filePath);
    }
    
    @AfterSuite
    protected void afterSuite() {
        extent.close();
    }
    
    
}

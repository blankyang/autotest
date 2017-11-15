package com.test.framework.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class ExtentBase {
    protected ExtentReports extent;
    protected static ExtentTest test;
    protected String logKey;
    protected int sum;  
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

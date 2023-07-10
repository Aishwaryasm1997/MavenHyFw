package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputpath ="./FileInput/Controller.xlsx";
	String outputpath ="./FileOutPut/HybridResults.xlsx";
	String Testcases="MasterTestCases";
	ExtentReports report;
	ExtentTest test;

	public void startTest()throws Throwable
	{
		String Modulestatus = "";
		//create reference object for Excelfile util class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all rows in testcases sheet
		for(int i=1; i<xl.rowCount(Testcases);i++)
		{

			if(xl.getCellData(Testcases, i, 2).equalsIgnoreCase("Y"))
			{
				//read corresponding sheet
				String TCModule = xl.getCellData(Testcases, i, 1);
				//iterate each sheet
				for(int j=1; j<=xl.rowCount(TCModule);j++)
				{
					//define path
					report =new ExtentReports("./Reports/"+TCModule+FunctionLibrary.generateDate()+"     "+".html");
					test= report.startTest(TCModule);
					//read cells from TCModule
					String Description = xl.getCellData(TCModule, j, 0);
					String Function_Name = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						if(Function_Name.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("clickActiion"))
						{
							FunctionLibrary.clickActiion(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("selectDropdown"))
						{
							FunctionLibrary.selectDropdown(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("capturestockitem"))
						{
							FunctionLibrary.capturestockitem(driver, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("mouseClick"))
						{
							FunctionLibrary.mouseClick(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("categoryTable"))
						{
							FunctionLibrary.categoryTable(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("capData"))
						{
							FunctionLibrary.capData(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						//write as pass into status cell in TCModule
						xl.setCellData(TCModule, 0, 5, "Pass", outputpath);
						test.log(LogStatus.PASS, Description);
						Modulestatus ="True";
					}catch(Exception e)
					{
						System.out.println(e.getMessage());

						//write as fail into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						test.log(LogStatus.FAIL, Description);
						Modulestatus ="Fail";
					}
					if(Modulestatus.equalsIgnoreCase("True"))
					{
						//write as pass into testcase sheet
						xl.setCellData(Testcases, i, 3, "Pass", outputpath);
					}
					if(Modulestatus.equalsIgnoreCase("Fail"));
					{
						xl.setCellData(Testcases, i, 3, "Fail", outputpath);
					}
				
				report.endTest(test);
				report.flush();
				}
			}
			else
			{
				//write as blocked into Testcases sheet which are flag to N
				xl.setCellData(Testcases, i, 3, "Blocked", outputpath);
			}
		}
	}
}

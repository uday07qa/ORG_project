package driverfactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.exceldataflow;
import commonFunctions.FunctionalLibrary;

public class AppTest {

	String Fileinput="./xldata/orengeHRMTestcases.xlsx";
	String Fileoutput="./xldata/Generate_report_orenge.xlsx";
	String Tsheet="masterTestcases";
	ExtentReports repo;
	ExtentTest loger;
	public static WebDriver driver;

	@Test
	public void starttest() throws Throwable {

		String	moduler_pass="";
		String	moduler_fail="";

		exceldataflow xl = new exceldataflow(Fileinput);
		int rc= xl.rowCount(Tsheet);
		for(int i=1;i<=rc;i++) {
			if(xl.getCellData(Tsheet, i, 2).equalsIgnoreCase("y")) {
				String TCmodule=xl.getCellData(Tsheet, i, 1);
				repo= new ExtentReports("./orengeHRM/target/orgreports.html");
				loger=	repo.startTest(TCmodule);
				loger.assignAuthor("uday");



				for(int j=1;j<=xl.rowCount(TCmodule);j++) {


					String Description= xl.getCellData(TCmodule, j, 0);
					String Objecttype= xl.getCellData(TCmodule, j, 1);
					String locatertype= xl.getCellData(TCmodule, j, 2);
					String locatervalue= xl.getCellData(TCmodule, j, 3);				
					String Testdata= xl.getCellData(TCmodule, j, 4);
					try {
						if(Objecttype.equalsIgnoreCase("startBrowser")) {
							FunctionalLibrary.startBrowser();
							loger.log(LogStatus.INFO, Description);

						}

						if(Objecttype.equalsIgnoreCase("lanchUrl")) {
							FunctionalLibrary.lanchUrl();
							loger.log(LogStatus.INFO, Description);

						}
						if(Objecttype.equalsIgnoreCase("Typeaction")) {
							FunctionalLibrary.Typeaction(locatertype, locatervalue, Testdata);
							loger.log(LogStatus.INFO, Description);

						}

						if(Objecttype.equalsIgnoreCase("ClickAction")) {
							FunctionalLibrary.ClickAction(locatertype, locatervalue);
							loger.log(LogStatus.INFO, Description);

						}

						if(Objecttype.equalsIgnoreCase("closebrowser")) {
							FunctionalLibrary.closebrowser();
							loger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("Threadsleep")) {
							FunctionalLibrary.Threadsleep(Testdata);
							loger.log(LogStatus.INFO, Description);
						}
						if(Objecttype.equalsIgnoreCase("waitforelement")) {
							FunctionalLibrary.waitforelement(locatertype, locatervalue, Testdata);
							loger.log(LogStatus.INFO, Description);
						}
						
						xl.setCelldata(TCmodule, j, 5, "Pass", Fileoutput);
						loger.log(LogStatus.PASS, Description);
						moduler_pass="true";

					}

					catch (Exception e) {
						System.out.println(e.getMessage());
						xl.setCelldata(TCmodule, j, 5, "Fail", Fileoutput);
						loger.log(LogStatus.FAIL, Description);
						moduler_fail="False";

					//	File screen= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				//		FileUtils.copyFile(screen, new File("./orengeHRM/screenshots/ORGmodule.png"));
					}

					if(moduler_pass.equalsIgnoreCase("true")) {
						xl.setCelldata(Tsheet, i, 3, "PASS", Fileoutput);

					}
					if(moduler_fail.equalsIgnoreCase("false")) {
						xl.setCelldata(Tsheet, i, 3, "FAIL", Fileoutput);

					}
				}

				repo.endTest(loger);
				repo.flush();
				
			}

		
		else {
			xl.setCelldata(Tsheet, i, 3, "Blocked", Fileoutput);
		}
		}}
	}

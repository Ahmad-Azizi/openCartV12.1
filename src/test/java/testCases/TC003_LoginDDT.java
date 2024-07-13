package testCases;

/*
Data is valid - login success - test pass - logout
Data is valid -- login failed - test fail
Data is invalid - login success - test fail - logout
Data is invalid -- login failed - test pass

 */

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider ="LoginData", dataProviderClass = DataProviders.class, groups = "datadriven")
    public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException {

            logger.info("***** Staring TC003_LoginDDT *****");
    //HomePage

        try {

            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            //Login
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin();

            //MyAccount
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    macc.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.assertTrue(false);
                }
            }
            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    macc.clickLogout();
                    Assert.assertTrue(false);
                } else {
                    Assert.assertTrue(true);

                }
            }
        }catch (Exception e) {
        Assert.fail();

        }
        logger.info("***** Finished TC003_LoginDDT *****");
        Thread.sleep(1000);
    }
}
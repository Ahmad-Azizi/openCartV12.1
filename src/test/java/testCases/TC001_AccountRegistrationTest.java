package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verify_Account_registration() {

        logger.info("****** Starting TC001_AccountRegistrationTest *****");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();
            logger.info("Clicked on MyAccount link ");

            AccountRegistrationPage regPage = new AccountRegistrationPage(driver);

            logger.info("Providing customer details");
            regPage.setFirstName(randomeString().toUpperCase());
            regPage.setLastName(randomeString().toUpperCase());
            regPage.setEmail(randomeString() + "@gmail.com");
            regPage.setTelephone(randomeNumber());

            String password = randomeAlphaNumeric();

            regPage.setPassword(password);
            regPage.ConfirmPassword(password);

            regPage.setPrivacyPolicy();
            regPage.clickContinue();

            logger.info("Validating expected message");
            String confmsg = regPage.getConfirmationMsg();

            if (confmsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);

            } else {
                logger.error("Test failed..");
                logger.debug("Debug logs");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {

            Assert.fail();
        }
        logger.info("****** Finished TC001_AccountRegistrationTest *****");

    }
}
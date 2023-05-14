package com.inscaleinterview.testCases;

import org.junit.Test;
import com.inscaleinterview.pageObjects.Question1Page;

public class Question1_TestCase extends BaseClass{

    @Test
    public void Q1() throws Exception{
        driver.get(baseURL);

        Question1Page question1Page = new Question1Page(driver);

        question1Page.managerLogin();
        question1Page.clickAddCustomerMenu();

        question1Page.addCustomer();
        question1Page.verifyCustomerInserted();

        question1Page.deleteCustomers();

    }
    
}

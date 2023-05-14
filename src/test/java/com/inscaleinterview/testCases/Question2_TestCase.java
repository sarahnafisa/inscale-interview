package com.inscaleinterview.testCases;

import org.junit.Test;
import com.inscaleinterview.pageObjects.Question2Page;

public class Question2_TestCase extends BaseClass{
    
    @Test
    public void Q2(){
        driver.get(baseURL);

        Question2Page question2Page = new Question2Page(driver);

        question2Page.customerLogin();
        question2Page.chooseAccount();
        question2Page.depositMoney("50000");
        question2Page.withdrawMoney("3000");
        question2Page.withdrawMoney("2000");
        question2Page.depositMoney("5000");
        question2Page.withdrawMoney("10000");
        question2Page.withdrawMoney("15000");
        question2Page.depositMoney("1500");
    }
}

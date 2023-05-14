package com.inscaleinterview.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.inscaleinterview.testCases.BaseClass;

public class Question2Page extends BaseClass{

    WebDriver ldriver;
    WebElement webelement;
    static WebDriverWait wait = new WebDriverWait(driver, 20); 
    
    public Question2Page(WebDriver rdriver){
        ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    @FindBy(xpath = "//button[normalize-space()='Customer Login']")
    WebElement customerLogin;

    @FindBy(xpath = "//select[@id='userSelect']")
    WebElement selectUser;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    @FindBy(xpath = "//select[@id='accountSelect']")
    WebElement accountSelect;

    @FindBy(xpath = "//button[normalize-space()='Deposit']")
    WebElement depositButton;
    
    @FindBy(xpath = "//input[@placeholder='amount']")
    WebElement amount;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement confirmDeposit;

    @FindBy(xpath = "//button[normalize-space()='Withdrawl']")
    WebElement withdrawalButton;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement confirmWithdrawal;
   
    public void customerLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(customerLogin)).click();
        wait.until(ExpectedConditions.elementToBeClickable(selectUser)).click();
        Select user = new Select(driver.findElement(By.xpath("//select[@id='userSelect']")));
        user.selectByVisibleText("Hermoine Granger");
        loginButton.click();
    }

    public void chooseAccount(){
        wait.until(ExpectedConditions.elementToBeClickable(accountSelect)).click();
        Select account = new Select(driver.findElement(By.xpath("//select[@id='accountSelect']")));
        account.selectByVisibleText("1003");
    }

    public void depositMoney(String setCreditAmount){
        WebElement balance = (WebElement) driver.findElements(By.cssSelector("strong[class=ng-binding]")).get(1);
        String balance2 = balance.getText().toString();
        Integer initialBalance = Integer.parseInt(balance2);
        System.out.println("Initial balance is        : " + initialBalance);
        
        wait.until(ExpectedConditions.elementToBeClickable(depositButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Deposit']")));
        wait.until(ExpectedConditions.elementToBeClickable(amount)).sendKeys(setCreditAmount);
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeposit)).click();

        WebElement newBalance = (WebElement) driver.findElements(By.cssSelector("strong[class=ng-binding]")).get(1);
        String newBalance2 = newBalance.getText().toString();
        Integer newBalance3 = Integer.parseInt(newBalance2);
        System.out.println("Credit amount             : " + setCreditAmount);
        Integer creditAmount = Integer.parseInt(setCreditAmount);

        try{
            Integer finalBalance = initialBalance + creditAmount;
            
            System.out.println("Current account balance is: " + finalBalance);
    
            if(newBalance3.intValue() == finalBalance.intValue()){
                System.out.println("Transaction verified");
            } 
            else{
                System.out.println("Transaction failed. Calculation does not tally.");
            }
        } catch(NumberFormatException e){
            
        }
        System.out.println("--------------------------------------------------");
    }

    public void withdrawMoney(String setDebitAmount){
        WebElement balance = (WebElement) driver.findElements(By.cssSelector("strong[class=ng-binding]")).get(1);
        String balance2 = balance.getText().toString();
        Integer initialBalance = Integer.parseInt(balance2);
        System.out.println("Initial balance is        : " + initialBalance);
    
        wait.until(ExpectedConditions.elementToBeClickable(withdrawalButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Withdraw']")));
        wait.until(ExpectedConditions.elementToBeClickable(amount)).sendKeys(setDebitAmount);
        wait.until(ExpectedConditions.elementToBeClickable(confirmWithdrawal)).click();

        WebElement newBalance = (WebElement) driver.findElements(By.cssSelector("strong[class=ng-binding]")).get(1);
        String newBalance2 = newBalance.getText().toString();
        Integer newBalance3 = Integer.parseInt(newBalance2);
        System.out.println("Debit amount              : " + setDebitAmount);
        Integer debitAmount = Integer.parseInt(setDebitAmount);

        try{
            Integer finalBalance = initialBalance - debitAmount;
            
            System.out.println("Current account balance is: " + finalBalance);
    
            if(newBalance3.intValue() == finalBalance.intValue()){
                System.out.println("Transaction verified");
            } 
            else{
                System.out.println("Transaction failed. Calculation does not tally.");
                System.out.println(newBalance3.intValue() +" vs "+ finalBalance.intValue());
            }
        } catch(NumberFormatException e){
            
        }
        System.out.println("--------------------------------------------------");
    }
}

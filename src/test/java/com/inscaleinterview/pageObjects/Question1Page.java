package com.inscaleinterview.pageObjects;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// import org.junit.Assert;  
import com.inscaleinterview.testCases.BaseClass;

public class Question1Page extends BaseClass{

    WebDriver ldriver;
    static WebDriverWait wait = new WebDriverWait(driver, 20); 
    
    public Question1Page(WebDriver rdriver){
        ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    @FindBy(xpath = "//button[normalize-space()='Bank Manager Login']")
    WebElement bankManagerLogin;

    @FindBy(xpath = "//button[normalize-space()='Add Customer']")
    WebElement addCustomerMenu;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    WebElement firstName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement lastName;

    @FindBy(xpath = "//input[@placeholder='Post Code']")
    WebElement postCode;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement clickAddCustomer;

    @FindBy(xpath = "//button[normalize-space()='Customers']")
    WebElement customersListMenu;

    @FindBy(xpath = "//input[@placeholder='Search Customer']")
    WebElement searchCustomer;

    By listofrowsintable = By.xpath("//table/tbody/tr");

    public void managerLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(bankManagerLogin)).click();
    }

    public void clickAddCustomerMenu(){
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerMenu)).click();
    }


    public void addCustomer() throws Exception {
		for (Customer data : inputData()) {
            wait.until(ExpectedConditions.elementToBeClickable(firstName)).sendKeys(data.getFirstName());
            wait.until(ExpectedConditions.elementToBeClickable(lastName)).sendKeys(data.getLastName());
            wait.until(ExpectedConditions.elementToBeClickable(postCode)).sendKeys(data.getPostCode());

			wait.until(ExpectedConditions.elementToBeClickable(clickAddCustomer)).click();
			Thread.sleep(200);
            alertAccept();
            System.out.println("--------------------------------------------------");
            System.out.println("Customers added");
		}

	}

    //Customers' details to be inserted
    public static List<Customer> inputData() {
		List<Customer> listCustomers = new ArrayList<>();
		listCustomers.add(new Customer("Christopher", "Connely", "L789C349"));
		listCustomers.add(new Customer("Frank", "Christopher", "A897N450"));
		listCustomers.add(new Customer("Christopher", "Minka", "M098Q585"));
		listCustomers.add(new Customer("Connely", "Jackson", "L789C349"));
		listCustomers.add(new Customer("Jackson", "Frank", "L789C349"));
		listCustomers.add(new Customer("Minka", "Jackson", "A897N450"));
		listCustomers.add(new Customer("Jackson", "Connely", "L789C349"));
		return listCustomers;

	}

    static class Customer {
		private String firstName;
		private String lastName;
		private String postCode;

		public Customer(String firstName, String lastName, String postCode) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.postCode = postCode;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getPostCode() {
			return postCode;
		}
	}

    public void alertAccept(){
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(ExpectedConditions.alertIsPresent());
        org.openqa.selenium.Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void customerListMenu(){
        wait.until(ExpectedConditions.elementToBeClickable(customersListMenu)).click();
    }

    public List<Customer> getTableData() {
		List<Customer> listCustomer = new ArrayList<>();
		wait.until(ExpectedConditions.visibilityOfElementLocated(listofrowsintable));
		List<WebElement> listOfRows = driver.findElements(listofrowsintable);

		for (int row = 1; row <= listOfRows.size(); row++) {
			List<WebElement> listOfColumns = driver.findElements(By.xpath("//table/tbody/tr["+ row +"]"));
			if (listOfColumns.size() > 0) {
				for (int column = 1; column < 4; column++) {
                    //get data from each row
					listCustomer.add(new Customer(getColumnsData(row, 1), getColumnsData(row, 2), getColumnsData(row, 3)));
				}
			} else {

			}
		}
		return listCustomer;
	}

    public String getColumnsData(int row, int colum) {
		return driver.findElement(By.xpath("//table/tbody/tr["+ row +"]/td["+ colum +"]")).getText();
	}

    public boolean verifyCustomerInserted() {
        customerListMenu();
		boolean present = false;
		for (Customer cust : inputData()) {
			 List<Customer> listCustomer=getTableData();
             present = listCustomer.stream().filter(i -> i.getFirstName().equals(cust.getFirstName()) && i.getLastName().equals(cust.getLastName()) 
            && i.getPostCode().equals(cust.getPostCode())).findFirst().isPresent();

			if (present) {
                System.out.println("--------------------------------------------------");
				System.out.println("Customers added succesfully");
				return present;
			}
		}
		return present;
	}

    public static List<Customer> customersToDelete() {
		List<Customer> listCustomersDelete = new ArrayList<>();
        listCustomersDelete.add(new Customer("Jackson", "Frank", "L789C349"));
		listCustomersDelete.add(new Customer("Christopher", "Connely", "L789C349"));
		return listCustomersDelete;
	}

    public void deleteCustomers() throws Exception {
		String actualfirstname;
		String actuallastname;
		String actualpostcode;
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listofrowsintable));
		List<WebElement> listOfRows = driver.findElements(listofrowsintable);

		for (Customer cust : customersToDelete()) {
			for (int row = 1; row <= listOfRows.size(); row++) {
				int colmn = 1;
                //get data for 1 row
				actualfirstname = driver.findElement(By.xpath("//table/tbody/tr["+ row +"]/td["+ colmn +"]")).getText();
				actuallastname = driver.findElement(By.xpath("//table/tbody/tr["+ row +"]/td["+ (colmn + 1) +"]")).getText();
				actualpostcode = driver.findElement(By.xpath("//table/tbody/tr["+ row +"]/td["+ (colmn + 2) +"]")).getText();

                //if data in row matches
				if (cust.getFirstName().equalsIgnoreCase(actualfirstname) && cust.getLastName().equalsIgnoreCase(actuallastname) && cust.getPostCode().equalsIgnoreCase(actualpostcode)) {
					WebElement deleteButton = driver.findElement(By.xpath("//*[contains(@class,'ng-scope')]//table/tbody/tr["+ row +"]/td[5]/button[text()='Delete']"));
					wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
                    // to fail the inner for loop after deletion
					row = listOfRows.size() + 1;
				}
			}
		}
        System.out.println("--------------------------------------------------");
		System.out.println("Customers deleted succesfully");
	}
    
}

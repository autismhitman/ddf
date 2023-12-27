package ff.tests;

import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testBase.BaseTest;

public class StockManagement extends BaseTest {
	
	
	@Test
	public void addNewStock(ITestContext con) {
	   
		String companyName="Birla Corporation Ltd.";
		String selectionDate ="26-01-2024";
		String stockQuantity="100";
		String stockPrice="200";
		
		app.log("Adding "+ stockQuantity+" stocks of "+ companyName);
		
		int quantityBeforeModification= app.findCurrentStockQuantity(companyName);
		con.setAttribute("quantityBeforeModification", quantityBeforeModification);
		
		app.click("add_stock_id");
		app.type("stock_name_css", companyName);
		app.wait(1);
		app.clickEnterButton("stock_name_css");
		app.click("stockpurchase_id");
		app.selectDateFromCalendar(selectionDate);
		
		app.type("stockQty_id", stockQuantity);
		app.type("stockprice_id", stockPrice);
		app.click("addStockbtn_id");
		app.waitForPageLoad();
		app.log("Stocks added successfully ");
		
		
	}
	
	
	@Test
	public void verifyStockPresent() {
 
		String companyName="Birla Corporation Ltd.";
		int row =app.getRowNumWithCellData("table_stock_css", companyName);
		if(row==-1)
			app.reportFailure("Stock Not present" + companyName, true);
		
		app.log("Stock found in list" + companyName);
	}
	
	@Parameters({"action"})
	@Test
	public void verifyStockQuantity(ITestContext con, String action) {
		
	 
		String companyName="Birla Corporation Ltd.";
		String selectionDate ="26-01-2024";
		String stockQuantity="100";
		String stockPrice="200";
		
		
		app.log("Verifying stock quantity after action - "+ action);
		// quantity after adding/selling stocks
		int quatityAfterModification = app.findCurrentStockQuantity(companyName);
		int modifiedquantity=Integer.parseInt(stockQuantity);
		int expectedModifiedQuantity=0;
		
		// quantity before adding/selling stocks
		int quatityBeforeModification = (Integer)con.getAttribute("quatityBeforeModification");
		if(action.equals("addstock"))
			expectedModifiedQuantity = quatityAfterModification-quatityBeforeModification;
		else if(action.equals("sellstock"))
			expectedModifiedQuantity = quatityBeforeModification-quatityAfterModification;
		
		app.log("Old Stock Quantity "+quatityBeforeModification);
		app.log("New Stock Quantity "+quatityAfterModification);
		
		if(modifiedquantity != expectedModifiedQuantity)
		    app.reportFailure("Quantity did not match", true);
		
		app.log("Stock Quantity Changed as per expected "+ modifiedquantity);
	}
	
	@Parameters({"action"})
	@Test
	public void verifyTrasactionHistory(String action)) {
		
		String companyName="Birla Corporation Ltd.";
		String selectionDate ="26-01-2024";
		String stockQuantity="100";
		String stockPrice="200";
		
		app.log("Verifying transaction History for "+action+"for quantity "+stockQuantity);
		app.goToTransactionHistory(companyName);
		String changedQuantityDisplayed  = app.getText("latestShareChangeQuantity_xpath");
		app.log("Got Changed Quantity "+ changedQuantityDisplayed);
		
		if(action.equals("sellstock"))
			stockQuantity="-"+stockQuantity;
		
		if(!changedQuantityDisplayed.equals(stockQuantity))
		   app.reportFailure("Got changed quantity in transaction history as "+ changedQuantityDisplayed, true);	
		
		app.log("Transaction History OK");
		
	}

}

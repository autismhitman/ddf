package ff.tests;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testBase.BaseTest;

public class StockManagement extends BaseTest {
	
	
	@Test
	public void addNewStock(ITestContext con) {
	   
		JSONObject data = (JSONObject) con.getAttribute("data");
				
		String companyName=(String) data.get("stockname");
		String selectionDate =(String) data.get("date");
		String stockQuantity=(String) data.get("quantity");
		String stockPrice=(String) data.get("price");
		
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
	public void verifyStockPresent(ITestContext con) {
		
		JSONObject data = (JSONObject) con.getAttribute("data");
		String companyName=(String) data.get("stockname");
 
		int row =app.getRowNumWithCellData("table_stock_css", companyName);
		if(row==-1)
			app.reportFailure("Stock Not present" + companyName, true);
		
		app.log("Stock found in list" + companyName);
	}
	
	@Parameters({"action"})
	@Test
	public void verifyStockQuantity(ITestContext con, String action) {
		
		JSONObject data = (JSONObject) con.getAttribute("data");
		String companyName=(String) data.get("stockname");
		String selectionDate =(String) data.get("date");
		String stockQuantity=(String) data.get("quantity");
		String stockPrice=(String) data.get("price");
		
		
		app.log("Verifying stock quantity after action - "+ action);
		// quantity after adding/selling stocks
		int quantityAfterModification = app.findCurrentStockQuantity(companyName);
		int modifiedquantity=Integer.parseInt(stockQuantity);
		int expectedModifiedQuantity=0;
		
		// quantity before adding/selling stocks
		int quantityBeforeModification = (Integer)con.getAttribute("quantityBeforeModification");
		if(action.equals("addstock"))
			expectedModifiedQuantity = quantityAfterModification-quantityBeforeModification;
		else if(action.equals("sellstock"))
			expectedModifiedQuantity = quantityBeforeModification-quantityAfterModification;
		
		app.log("Old Stock Quantity "+quantityBeforeModification);
		app.log("New Stock Quantity "+quantityAfterModification);
		
		if(modifiedquantity != expectedModifiedQuantity)
		    app.reportFailure("Quantity did not match", true);
		
		app.log("Stock Quantity Changed as per expected "+ modifiedquantity);
	}
	
	@Parameters({"action"})
	@Test
	public void verifyTransactionHistory(ITestContext con,String action) {
		
		JSONObject data = (JSONObject) con.getAttribute("data");
		String companyName=(String) data.get("stockname");
		String selectionDate =(String) data.get("date");
		String stockQuantity=(String) data.get("quantity");
		String stockPrice=(String) data.get("price");
		
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

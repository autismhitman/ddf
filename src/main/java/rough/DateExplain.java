package rough;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateExplain {

	public static void main(String[] args)   {
 
		
		String selectionDate ="6-01-2024";
		
		 
	     
	     try {
	    	 	Date currentDate = new Date();
	    	 	System.out.println(currentDate);
		     
	    	 	Date dateTosel = new SimpleDateFormat("d-MM-yyyy").parse(selectionDate);
	    	 	String day= new SimpleDateFormat("d").format(dateTosel);
	    		String month= new SimpleDateFormat("MMMM").format(dateTosel);
	    		String year= new SimpleDateFormat("yyyy").format(dateTosel);
	    		String monthYearToBeselected = month+" "+ year;
	    		System.out.println( monthYearToBeselected );
			 
			 
			 
			 
			 
			 
			 
			 
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}
	     
	     
	        

	}

}

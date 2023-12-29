package rough;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





public class JReader {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		 
		Map<String, String> classMethodMap = new HashMap<>();
		String path= System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\classmethods.json";
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject)parser.parse(new FileReader(new File(path)));
		
		
		JSONArray classdetails = (JSONArray) jo.get("classdetails");
	 
		for ( int i = 0; i< classdetails.size(); i++) {
			
			JSONObject  classNames  = (JSONObject) classdetails.get(i);
			String className = (String) classNames.get("class");
		    
			JSONArray methods = (JSONArray) classNames.get("methods");
			
			for( int j= 0; j<methods.size(); j++) {
				
				   String methodName = (String) methods.get(j);
				   classMethodMap.put(methodName ,className);
				
			}
		}
		
		System.out.println(classMethodMap);

	}

}

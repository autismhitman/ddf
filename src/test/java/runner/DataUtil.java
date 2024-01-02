package runner;

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



public class DataUtil {

	public Map<String, String> loadClassMethods () throws FileNotFoundException, IOException, ParseException {
		 
		Map<String, String> classMethodMap = new HashMap<>();
	 
		String path=System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\classmethods.json";
		JSONParser parser = new JSONParser();
		JSONObject json=(JSONObject)parser.parse(new FileReader(new File(path)));
		JSONArray classDetails = (JSONArray)json.get("classdetails");
		for(int cMId=0;cMId<classDetails.size();cMId++) {
			JSONObject classDetail = (JSONObject)classDetails.get(cMId);
			String className= (String)classDetail.get("class");
			JSONArray methods= (JSONArray)classDetail.get("methods");
			//System.out.println(className);
			for(int mId=0;mId<methods.size();mId++) {
				String method=(String)methods.get(mId);
				classMethodMap.put(method, className);
			}
			System.out.println("-----------------");
		}
		
		
		return classMethodMap;

	}

}

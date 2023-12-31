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
	
	
	public int getTestDataSets(String pathFile, String dataFlag) throws FileNotFoundException, IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		JSONObject json=(JSONObject)parser.parse(new FileReader(new File(pathFile)));
		JSONArray testDataSets = (JSONArray)json.get("testdata");
		for( int tData=0; tData< testDataSets.size() ; tData++) {
			
			JSONObject  testData=  (JSONObject ) testDataSets.get(tData);
			String flag = (String) testData.get("flag");
			if (dataFlag.equals(flag)) {
				
				JSONArray dataSets = (JSONArray)testData.get("data");
				return dataSets.size();
			}
			
			
		}
		
		return -1;
	}


	public JSONObject getTestData(String datafilePath, String dataFlag, int iteration) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json=(JSONObject)parser.parse(new FileReader(new File( datafilePath)));
		JSONArray testDataSets = (JSONArray)json.get("testdata");
		for( int tData=0; tData< testDataSets.size() ; tData++) {
			
			JSONObject  testData=  (JSONObject ) testDataSets.get(tData);
			String flag = (String) testData.get("flag");
			if (dataFlag.equals(flag)) {
				
				JSONArray dataSets = (JSONArray)testData.get("data");
				return (JSONObject)dataSets.get(iteration);
			}

		}
		
		return null;
	}

}

package kr.ac.cau.jomingyu.doingtogether.utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {

	public static JSONObject getJSONObj(String json) throws ParseException{
		JSONParser jParser = new JSONParser();
		JSONObject jObj = (JSONObject) jParser.parse(json);
		return jObj;
	}
	
	public static String getJSONString(JSONObject jObj){
		return jObj.toJSONString();
	}
	
}

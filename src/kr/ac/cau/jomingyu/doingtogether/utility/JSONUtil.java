package kr.ac.cau.jomingyu.doingtogether.utility;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
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
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> translateJsonToMap(String jsonStr) throws NullPointerException{
		JSONParser parser = new JSONParser();
		try {
			ContainerFactory lhmContianerFactory = new ContainerFactory(){

				@Override
				public Map<String, String> createObjectContainer() {
					return new LinkedHashMap<String, String>();
				}

				@Override
				public List<String> creatArrayContainer() {
					return new LinkedList<String>();
				}
			};
			Object jObj = parser.parse(jsonStr, lhmContianerFactory);
			return (LinkedHashMap<String, String>) jObj;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String translateMapToJson(LinkedHashMap<String, String> map){
		return JSONObject.toJSONString(map);
	}
	
}

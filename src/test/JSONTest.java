package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("D:\\Test\\test.json"));
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray msg = (JSONArray) jsonObject.get("todo");
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				JSONObject todo = iterator.next();
				System.out.println(todo.get("title"));
				System.out.println(todo.get("people"));
				//ArrayList<String> people = new ArrayList<>();
				
				Iterator<JSONObject> pi = ((JSONArray) todo.get("people")).iterator();
				while (pi.hasNext()){
					String str = ""+ pi.next();
					System.out.println(str);
				}
				System.out.println(todo);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

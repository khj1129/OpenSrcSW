import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class movieAPI {

	static String clientID;
	static String clientSecret;
	public static void main(String[] args) throws IOException, ParseException {

		Scanner scan = new Scanner(System.in);
		String moviename;
		System.out.println("client id와 client secret을 입력하세요 ");
		System.out.print("client id : ");
		clientID = scan.next();
		System.out.print("client secret : ");
		clientSecret = scan.next();
		
		System.out.print("검색어를 입력하세요 : ");
		moviename = scan.next();
		getAPI(moviename);
	}

	public static void getAPI(String name) throws IOException, ParseException {
		String text = URLEncoder.encode(name,"UTF-8");
		String apiURL = "https://openapi.naver.com/v1/search/movie?query="+text;
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id",clientID);
		con.addRequestProperty("X-Naver-Client-Secret", clientSecret);
		
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if(responseCode==200) {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}
		else {
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			System.out.println("error occured"+responseCode);
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine=br.readLine())!=null) {
			response.append(inputLine);
		}
		br.close();
		
		String buf = response.toString();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(buf);
		JSONArray infoArray=(JSONArray)jsonObject.get("items");

		for(int i=0;i<infoArray.size(); i++) {
			System.out.println("=item_"+i+"======================================");
			JSONObject itemObject = (JSONObject)infoArray.get(i);
			System.out.printf("%-15s:%s\n","title",itemObject.get("title"));
			System.out.printf("%-15s:%s\n","subtitle",itemObject.get("subtitle"));
			System.out.printf("%-15s:%s\n","director",itemObject.get("director"));
			System.out.printf("%-15s:%s\n","actor",itemObject.get("actor"));
			System.out.printf("%-15s:%s\n","userRating",itemObject.get("userRating"));
		}
	}
}

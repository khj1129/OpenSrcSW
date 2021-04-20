import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.xml.sax.InputSource;

public class getSnippet {

	public void FindMaxKeyword(String path, String quary) throws IOException
	{
		// inputtext에서 입력한 키워드가 가장 많이 포함된 라인을 출력한다
		// 동일한 갯수의 키워드가 포함될 경우에 앞선 라인을 출력
		
		HashMap<String,String> lineHashMap = new HashMap<String,String>;
		HashTable<String,int> divideText = new HashTable<String,int> ();
		int i=0;
		File file = new File(path);
		FileInputStream inputStream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
        InputSource src = new InputSource(reader);
        src.setEncoding("UTF-8");
        
        String allText="";
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        br = new BufferedReader(new java.io.InputStreamReader(inputStream,"UTF-8"));//new FileReader(file));
        String line = null;
        
        
        while ((line = br.readLine()) != null) {
        	
        	i++;
            sb.append(line);
            lineHashMap.put((String)i,line);
            divideText = getDivideText(line);
        }
        br.close();
        allText = sb.toString();
	}
	
	public List<String> getDivdeList(String line)
	{
		List<String> returnList = new ArrayList<>();
		int length = line.Length();
		int i;
		String tempStr="";
		for(i=0; i<length; i++)
		{
			if(line[i]=="")
			{
				returnList.add(tempStr);
			}
			else {
				tempStr+=line[i];
			}
		}
		System.out.println("return list ");
	}
	public HashTable<String,int> getDivideText(String line)
	{
		HashTable<String,int> returnList = new HashTable<String,int>();
		int length = line.Length();
		int i;
		String tempStr="";
		
		for(i=0; i<length; i++)
		{
			if(line[i]=="")
			{
				if(returnList.containsKey(tempStr))
				{
					returnList.get(tempStr)++;
				}
				else {
					returnList.put(tempStr,1);
				}
			}
			else {
				tempStr+=line[i];
			}
		}
	}
}

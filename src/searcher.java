import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordList;

public class searcher
//<<<<<<< HEAD
{
	public void CalcSim(String path, String query)throws ClassNotFoundException, IOException
	{
		int i,j,length,length_Doc;
		double qurSize,docSize, temp; 
		Keyword kwrd;
		HashMap<String,String> percentHashMap;
		String nowTitle,tempStr;
		
		MakeKeyword kw = new MakeKeyword();
        KeywordList kl = kw.GetKeywordList(query);
        length = kl.size();

    	HashMap<String,Double> titleHash = InnerProduct(path,kl);
    	HashMap<String,HashMap<String,Double>> docInnerList = new HashMap<String, HashMap<String, Double>>();
    	HashMap<String,Double> simHash=new HashMap<String,Double>();
    	
    	qurSize=0.0f;
    	
    	for(i=0; i<length; i++)
    	{
    		kwrd = kl.get(i);
    		//sum all the square of query keyword count
    		qurSize +=Math.pow(kwrd.getCnt(),2);
    		//System.out.println(kwrd.getString());
    	}
    	
    	//root of qurSize
    	qurSize = Math.sqrt(qurSize);
    	
    	//get index data dic
    	indexer ie = new indexer();
        HashMap indexMap = ie.GetPos(path);
        //ie.ReadPost(path);
        
        for(i=0; i<length; i++)
        {
        	kwrd = kl.get(i);
        	nowTitle = kwrd.getString();
        	
        	if(indexMap.containsKey(nowTitle))
            {
                percentHashMap = (HashMap<String,String>)indexMap.get(nowTitle);

                //percenthashmap 이라는 특정 키워드의 hash map값을 불러와서, 앞에서 부터 돌면서 해당 doc index의 값들을 넣어준다
                for(String key : percentHashMap.keySet())
                {
                	if(docInnerList.containsKey(key))
                	{
                		docInnerList.get(key).put(nowTitle, Double.parseDouble(percentHashMap.get(key)));
                	}
                	else 
                	{
                		HashMap<String, Double> innerHash = new HashMap<String,Double>();
                		innerHash.put(nowTitle, Double.parseDouble(percentHashMap.get(key)));
                		docInnerList.put(key,innerHash);
                	}
                }

            }
        }
        //문서의 종류별로 query keyword 재배치
        length_Doc = docInnerList.size();
        for(String id : docInnerList.keySet())
        {
        	docSize = 0;
        	for(String keyword : docInnerList.get(id).keySet())
        	{
        		//System.out.println(keyword+" doc added : "+id+", "+docInnerList.get(id).get(keyword));
        		docSize +=Math.pow(docInnerList.get(id).get(keyword),2);
        	}
        	docSize = Math.sqrt(docSize);

        	if(docSize==0)
        		temp = 0;
        	else
        		temp = titleHash.containsKey(id)? titleHash.get(id) /(qurSize * docSize) : 0;
        	//System.out.println(id+" / docSize "+docSize+" qurSize "+qurSize);
        	simHash.put(id, temp);
        	
        }
        
		//print top 3 documents
		List<String> keySetList = new ArrayList<>(simHash.keySet());
        Collections.sort(keySetList,(o1,o2)->(simHash.get(o2).compareTo(simHash.get(o1))));
        
        j=0;
        length = keySetList.size();
        System.out.println("### inputText : "+query);
        System.out.println("### TOP 3 CalcSim Documents ###");
        
        for(i=0; i<3; i++)
        {
        	if(length>i)
        	{
        		temp = simHash.get(keySetList.get(i));;
        		//if(temp!=0)
            	//{
        			j++;
            		System.out.println(j+") id : "+keySetList.get(i)+" , CalcSim : "+String.format("%.2f",temp ));
            	//}
        	}
        }
        if(j==0)
        {
        	System.out.println("해당 query를 가지는 문서가 없습니다.");
        }
        
	}
	public void GetTopSimilarity(String path, String query)throws ClassNotFoundException, IOException
	{
		int i,length;
		double temp=0;
		String nowTitle;
		MakeKeyword kw = new MakeKeyword();
        KeywordList kl = kw.GetKeywordList(query);
        length = kl.size();
		        
		HashMap<String,Double> titleHash = InnerProduct(path, kl);
		
        List<String> keySetList = new ArrayList<>(titleHash.keySet());
        Collections.sort(keySetList,(o1,o2)->(titleHash.get(o2).compareTo(titleHash.get(o1))));
        
        //output top 3 innerProduct dic title 
        System.out.println("### inputText : "+query);
        System.out.println("### TOP 3 InnerProduct Documents ###");
        
        length = keySetList.size();
        for(i=0; i<3; i++)
        {
        	nowTitle = Integer.toString(i);
        	if(length>i&&titleHash.containsKey(keySetList.get(i)))
        	{
            	temp = titleHash.get(keySetList.get(i));
                System.out.println((i+1)+") id : "+keySetList.get(i)+" , InnerProduct : "+String.format("%.2f", temp));
        	}
        }
	}
//}
//=======
//{ 
    public HashMap<String,Double> InnerProduct(String path, KeywordList kl) throws ClassNotFoundException, IOException
    {
    	int i,length=0;
        Keyword kwrd;
        String nowTitle;
        double nowCount=0;
        HashMap<String,String> percentHashMap;
        double temp;
	        
        length = kl.size();
        
        indexer ie = new indexer();
        HashMap indexMap = ie.GetPos(path);

       
        HashMap<String,Double> titleHash = new HashMap<String,Double>();
        
        for(i=0; i<length; i++)
        {
            kwrd = kl.get(i);
            nowTitle = kwrd.getString();
            if(indexMap.containsKey(nowTitle))
            {
                percentHashMap = (HashMap<String,String>)indexMap.get(nowTitle);

                //percenthashmap 이라는 특정 키워드의 hash map값을 불러와서, 앞에서 부터 돌면서 해당 doc index의 값들을 넣어준다
                for(String key : percentHashMap.keySet())
                {
                    //Weight is 1
                    nowCount = kwrd.getCnt() * Double.parseDouble(percentHashMap.get(key));

                    if(titleHash.containsKey(key))
                    {
                        temp = titleHash.get(key);
                        titleHash.put(key, temp+nowCount);
                    }
                    else
                    {
                        titleHash.put(key, nowCount);
                    }
                    
                }
            }
        }
        return titleHash;
    	
    }

}
//>>>>>>> feature

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
{ 
    public void GetTopSimilarity(String path, String query) throws ClassNotFoundException, IOException
    {
        int i,j,length=0;
        String inputText= query;
        Keyword kwrd;
        String nowTitle;
        double nowCount;
        HashMap<String,String> percentHashMap;
        double temp;

        //input query를 형태소별로 나눈다
        MakeKeyword kw = new MakeKeyword();
        KeywordList kl = kw.GetKeywordList(inputText);
        length = kl.size();

        //index.post를 받아온다
        indexer ie = new indexer();
        HashMap indexMap = ie.GetPos(path);

        HashMap<String,Double> titleHash = new HashMap<String,Double>();
        
        //input query의 형태소 리스트를 돌며 각 문서별 가중치를 불러와 더해준다
        for(i=0; i<length; i++)
        {
            kwrd = kl.get(i);
            nowTitle = kwrd.getString();
            if(indexMap.containsKey(nowTitle))
            {
                percentHashMap = (HashMap<String,String>)indexMap.get(nowTitle);

                for(String key : percentHashMap.keySet())
                {
                    //System.out.println("title : "+nowTitle+", DIC index : "+key+", Dic percentage : "+Double.parseDouble(percentHashMap.get(key)));
                    //Weight는 1이다
                    nowCount = 1 * Double.parseDouble(percentHashMap.get(key));

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

        List<String> keySetList = new ArrayList<>(titleHash.keySet());
        Collections.sort(keySetList,(o1,o2)->(titleHash.get(o2).compareTo(titleHash.get(o1))));
        
        length = keySetList.size();

        System.out.println("### inputText : "+query);
        System.out.println("### TOP 3 Documents ###");
        for(i=0; i<3; i++)
        {
            System.out.println((i+1)+") id : "+keySetList.get(i)+" , CalcSim : "+titleHash.get(keySetList.get(i)));
        }
    }

}

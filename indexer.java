
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


class Indexer_Entity
{
    public String Key;
    //id, count
    public HashMap<Integer, Integer> indexerDic = new HashMap<Integer, Integer>();
    public HashMap<String, String> percentageDic = new HashMap<String, String>();
    public int allCount=0;

    public Indexer_Entity(String Key, int id, int count)
    {
        this.Key = Key;
        indexerDic.put(id,count);
        allCount=1;
    }
    public void AddCount(int id, int count)
    {
        indexerDic.put(id,count);
        allCount++;
    }
    public void CalcPercentage(int docLength)
    {
        double nowPercentage=0;
        for(Map.Entry<Integer,Integer> kv : indexerDic.entrySet() )
        {
            nowPercentage = kv.getValue() * Math.log10(docLength/allCount);
            percentageDic.put(kv.getKey().toString(), String.format("%.1f",nowPercentage));
           // System.out.println(docLength+" # title : "+Key+", Freq : "+kv.getValue()+", count : "+allCount+", per : "+nowPercentage);
        }
    }
}

public class indexer 
{
    public HashMap<String,Indexer_Entity> indexerDic = new HashMap<String,Indexer_Entity>();

    public void MakeIndexer(String path) throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException
    {
        String outputPath="C:\\Users\\Chamo\\Documents\\SimpleIR\\index.post";
        int i,length;
        Node node;

        File file = new File(path);

        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream);
        InputSource source = new InputSource(reader);
        source.setEncoding("UTF-8");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(source);
        Element root = document.getDocumentElement();

        NodeList nodes = root.getChildNodes();
        length = nodes.getLength();

        Element ele,childEle;
        String nodeName;
        NodeList childList ;
        for(i=0; i<length; i++)
        {
            node = nodes.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE)
            {
                ele = (Element)node;
                nodeName = ele.getAttribute("id");

                childList = ele.getChildNodes();
                childEle = (Element)childList.item(1);
                ParsingTexts(childEle.getTextContent(),Integer.parseInt(nodeName));
            }
        }

        HashMap<String,HashMap<String,String>> postHashMap = new HashMap<String,HashMap<String,String>>();
        for (Indexer_Entity ie : indexerDic.values()) 
        {
            ie.CalcPercentage(length);
            postHashMap.put(ie.Key,ie.percentageDic);
        }
        
        FileOutputStream fileStream = new FileOutputStream(outputPath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
        objectOutputStream.writeObject(postHashMap);
        objectOutputStream.close();
    }
    
    public void ReadPost(String path) throws IOException, ClassNotFoundException
    {
        FileInputStream inputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        Object object = objectInputStream.readObject();
        objectInputStream.close();

        /*
        var hashMap = (HashMap)object;
        Iterator<String> it = hashMap.keySet().iterator();

        while(it.hasNext())
        {
            String key = it.next();
            var insideHashMap = (HashMap)hashMap.get(key);
            Iterator<String> insideIt = insideHashMap.keySet().iterator();
            String value=""; 
            while(insideIt.hasNext())
            {
                String insideKey = insideIt.next();
                value+=(String)insideHashMap.get(insideKey)+" ";
            }
            System.out.println("key : "+key+" , value : "+value);
        }
        */
    }
    public void ParsingTexts(String allText,int id)
    {
        int i,length;
        String[] splitStr = StringUtils.split(allText,"#");
        length = splitStr.length;
        
        for(i=0; i<length; i++)
        {
            String[] dividedStr = StringUtils.split(splitStr[i]," : ");
            AddKey(dividedStr[0], id, Integer.parseInt(dividedStr[1]));
        }

    }
    public void AddKey(String key, int id, int count)
    {
        if(indexerDic.containsKey(key))
        {
            indexerDic.get(key).AddCount(id,count);
        }
        else
        {
            indexerDic.put(key, new Indexer_Entity(key,id,count));
        }
    }
}

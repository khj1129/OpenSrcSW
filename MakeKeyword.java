import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MakeKeyword 
{
    /*
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException
    {
        String path="C:\\Users\\Chamo\\Documents\\SimpleIR\\2주차 실습 html\\2주차 실습 html";
        SetKeyword(path);
    }
    */
    public NodeList GetNodeList(String path) throws ParserConfigurationException, SAXException, IOException
    {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
        InputSource src = new InputSource(reader);
        src.setEncoding("UTF-8");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(src);
                
        return document.getDocumentElement().getChildNodes();
    }
    public void SetKeyword(String path) throws ParserConfigurationException, TransformerException,IOException, SAXException
    {
        int i,j, docLength;
        String title ="";
        String allText="";
        String newBody="";
        Node node;
        
        String outputPath="C:\\Users\\Chamo\\Documents\\SimpleIR\\index.xml";

        NodeList nodes = GetNodeList(path);
        docLength = nodes.getLength();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document doc = documentBuilder.newDocument();
        Element root = doc.createElement("doc");
        doc.appendChild(root);

        Element ele,childEle;
        String nodeName;
        NodeList childList ;

        System.out.println(docLength);
        for(i=0; i<docLength; i++)
        {
            node = nodes.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE)
            {
                ele = (Element)node;
                nodeName = ele.getAttribute("id");
                childList = ele.getChildNodes();
                title = ((Element)childList.item(0)).getTextContent();
                childEle = (Element)childList.item(1);
                
                //id 등록
                Element newElementID = doc.createElement("doc");
                newElementID.setAttribute("id", nodeName);

                //title 등록
                Element newElementTitle = doc.createElement("title");
                newElementID.appendChild(newElementTitle);

                newElementTitle.appendChild(doc.createTextNode(title));
                System.out.println("title: "+title);
                                                               
                //body 추출 및 등록
                allText = childEle.getTextContent();
                newBody = GetKeyWord(allText);

                Element newElementBody = doc.createElement("body");
                newElementID.appendChild(newElementBody);
                newElementBody.appendChild(doc.createTextNode(newBody));

                root.appendChild(newElementID);
            }
        }
         //파일 생성
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(new File(outputPath)));
        transformer.transform(source,result);
        System.out.println("처리 완료");
    }
         
    public KeywordList GetKeywordList(String allText)
    {
        KeywordExtractor ke = new KeywordExtractor();
        KeywordList kl = ke.extractKeyword(allText,true);

        return kl;
    }
    public String GetKeyWord(String allText)
    {
        int j, length;
        
        KeywordList kl = GetKeywordList(allText);
        length = kl.size();
        
        Keyword kwrd;
        kwrd = kl.get(0);
        String newBody = kwrd.getString()+":"+kwrd.getCnt();
        for(j=1; j<length; j++)
        {
            kwrd = kl.get(j);
            newBody += "#"+kwrd.getString()+":"+kwrd.getCnt();
        }
        System.out.println(newBody);

        return newBody;
    }
}
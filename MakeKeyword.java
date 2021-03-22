import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

public class MakeKeyword 
{
    /*
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException
    {
        String path="C:\\Users\\Chamo\\Documents\\SimpleIR\\2주차 실습 html\\2주차 실습 html";
        SetKeyword(path);
    }
    */
    public void SetKeyword(String path) throws ParserConfigurationException, TransformerException,IOException
    {
        String outputPath="C:\\Users\\Chamo\\Documents\\SimpleIR\\index.xml";

        File folder = new File(path);
        File []fileList = folder.listFiles();
                
        System.out.println("프로그램이 실행됩니다");
        if(!folder.exists())
        {
            System.out.println("경로에 파일이 없습니다");
            return ;
        }

        System.out.println("경로에 파일이 있습니다"+fileList.length);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        //root 생성
        Element root = doc.createElement("doc");
        doc.appendChild(root);
        int index=0;
        int i, length;
        String title ="";
        String allText="";
        String removedPBody="";
        String newBody="";

        MakeCollection mc = new MakeCollection();

        for(File file : fileList)
        {
            if(file.isFile())
            {
                System.out.println("파일을 넣습니다 : "+index);
             
                Element newElementID = doc.createElement("doc");
                newElementID.setAttribute("id", Integer.toString((index)));
                Element newElementTitle = doc.createElement("title");
                newElementID.appendChild(newElementTitle);

                allText = mc.GetAllText(file);
                //title 등록
                title = mc.GetTitleText(allText);//StringUtils.substringBetween(allText, "<title>", "</title>");
                System.out.println("title: "+title);
                newElementTitle.appendChild(doc.createTextNode(title));

                //body 추출 및 등록
                removedPBody  = mc.GetBodyText(allText);

                KeywordExtractor ke = new KeywordExtractor();
                KeywordList kl = ke.extractKeyword(removedPBody,true);

                length = kl.size();
                Keyword kwrd;
                kwrd = kl.get(0);
                newBody = kwrd.getString()+":"+kwrd.getCnt();

                for(i=1; i<length; i++)
                {
                    kwrd = kl.get(i);
                    newBody += "#"+kwrd.getString()+":"+kwrd.getCnt();
                }

                //System.out.println(newBody);
                Element newElementBody = doc.createElement("body");
                newElementID.appendChild(newElementBody);
                newElementBody.appendChild(doc.createTextNode(newBody));

                root.appendChild(newElementID);
                index++;
            }
     
            //파일 생성
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File(outputPath)));
     
            System.out.println("프로그램이 실행됩니다");
            transformer.transform(source,result);

            System.out.println("처리 완료");

        }
    }
         
}
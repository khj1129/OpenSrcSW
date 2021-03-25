import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.lang3.StringUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


class ElementClass
{
    public String title;
    public String body;

    public ElementClass(String title, String body)
    {
        this.title=title;
        this.body = body;
    }
}

public class MakeCollection{
   
   /*
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException
   {
        String path="C:\\Users\\Chamo\\Documents\\SimpleIR\\2주차 실습 html\\2주차 실습 html";
        GetFileTexts(path);
   }
   */
   public void GetFileTexts(String path) throws ParserConfigurationException, TransformerException, IOException
   {
        String outputPath="C:\\Users\\Chamo\\Documents\\SimpleIR\\collection.xml";

        File folder = new File(path);
        File []fileList = folder.listFiles();
        
        System.out.println("프로그램이 실행됩니다");
        if(!folder.exists())
        {
            System.out.println("경로에 파일이 없습니다");
            return ;
        }
        else
        {
            System.out.println("경로에 파일이 있습니다"+fileList.length);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            //root 생성
            Element root = doc.createElement("doc");
            doc.appendChild(root);
            int index=0;
            String title ="";
            String allText="";
            String removedPBody="";
            for(File file : fileList)
            {
                if(file.isFile())
                {
                    System.out.println("파일을 넣습니다 : "+index);
                    
                    Element newElementID = doc.createElement("doc");
                    newElementID.setAttribute("id", Integer.toString((index)));

                    Element newElementTitle = doc.createElement("title");
                    newElementID.appendChild(newElementTitle);

                    allText = GetAllText(file);

                    //title 등록
                    title = GetTitleText(allText);
                    newElementTitle.appendChild(doc.createTextNode(title));

                    //body 추출 및 등록
                    removedPBody = GetBodyText(allText);
                    Element newElementBody = doc.createElement("body");
                    newElementID.appendChild(newElementBody);
                    newElementBody.appendChild(doc.createTextNode(removedPBody));

                    root.appendChild(newElementID);
                    index++;
                }
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
    
    public String GetAllText(File file) throws IOException
    {
        String allText="";
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        br = new BufferedReader(new java.io.InputStreamReader(inputStream,"UTF-8"));//new FileReader(file));
        String line = null;
        
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        allText = sb.toString();

        return allText;
    }
    public static String GetTitleText(String allText)
    {
        String title ="";
        title = StringUtils.substringBetween(allText, "<title>", "</title>");
        
        return title;
    }
    public String GetBodyText(String allText)
    {
        String body, removedPBody,removedPSlashBody = "";
        body = StringUtils.substringBetween(allText, "<div id=\"content\">", "</div>");
        removedPSlashBody  = StringUtils.remove(body,"</p>");
        removedPBody  = StringUtils.remove(removedPSlashBody,"<p>");

        return removedPBody;
    }
}
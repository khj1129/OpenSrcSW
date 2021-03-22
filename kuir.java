import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class kuir {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException 
    {
        String path = "C:\\Users\\Chamo\\Documents\\SimpleIR\\2주차 실습 html\\2주차 실습 html";
          
        if(args[0].equals("-c./data"))
        {
            MakeCollection collection = new MakeCollection();
            collection.GetFileTexts(path);
            System.out.println("set collection");
        }
        else if(args[0].equals("-k./collection.xml"))
        {
            MakeKeyword keyWord = new MakeKeyword();
            keyWord. SetKeyword(path);
            System.out.println("set keyword collection");
        }
         
    }
}

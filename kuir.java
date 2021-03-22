import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class kuir {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException 
    {
        String path = "C:\\Users\\Chamo\\Documents\\SimpleIR\\2주차 실습 html\\2주차 실습 html";
          
        if(args.length>1)
        {
            if(args[1].equals("./data")) //-c 
            {
                MakeCollection collection = new MakeCollection();
                collection.GetFileTexts(path);
                System.out.println("set collection");
            }
            else if(args[1].equals("./collection.xml")) //-k
            {
                MakeKeyword keyWord = new MakeKeyword();
                keyWord. SetKeyword(path);
                System.out.println("set keyword collection");
            }
        }
        else
        {
            System.out.println("입력 값이 없습니다");
        }
        
    }
}

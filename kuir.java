import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class kuir {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException, ClassNotFoundException
    {
        //String path = "C:\\Users\\Chamo\\Documents\\SimpleIR\\2주차 실습 html\\2주차 실습 html";
        
        
        if(args.length>1)
        {
            if(args[0].equals("-c")) // ./data/
            {
                MakeCollection collection = new MakeCollection();
                collection.GetFileTexts(args[1]);
                System.out.println("set collection");
            }
            else if(args[0].equals("-k")) // ./collection.xml
            {
                MakeKeyword keyWord = new MakeKeyword();
                keyWord. SetKeyword(args[1]);
                System.out.println("set keyword collection");
            }
            else if(args[0].equals("-i")) //./index.xml
            {
                indexer _indexer = new indexer();
                _indexer.MakeIndexer(args[1]);
                System.out.println("set indexer");
            }
            //테스트용 
            else if(args[0].equals("-p"))
            {
                indexer _indexer = new indexer();
                _indexer.MakeIndexer(args[1]);
                System.out.println("set indexer print");
            }
        }
        else
        {
            System.out.println("입력 값이 없습니다");
        }


    }
}

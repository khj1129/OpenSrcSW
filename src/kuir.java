//<<<<<<< HEAD
//=======
import java.io.IOException;
//import java.util.Scanner;

//>>>>>>> feature
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
//import java.io.IOException;
//import java.nio.file.Path;
import org.xml.sax.SAXException;

public class kuir {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException, ClassNotFoundException
    {
//<<<<<<< HEAD
        
//=======
        if(args.length>=1)
//>>>>>>> feature
        {
            if(args[0].equals("-c")) // ./data/
            {
                 System.out.println("#### make collection.xml ####");

                MakeCollection collection = new MakeCollection();
                collection.GetFileTexts(args[1]);
                System.out.println("set collection");
            }
            else if(args[0].equals("-k")) // ./collection.xml
            {
                 System.out.println("#### make index.xml ####");

                MakeKeyword keyWord = new MakeKeyword();
                keyWord. SetKeyword(args[1]);
                System.out.println("set keyword collection");
            }
            else if(args[0].equals("-i")) //./index.xml
            {
                 System.out.println("#### make index.post ####");

                indexer _indexer = new indexer();
                _indexer.MakeIndexer(args[1]);
                System.out.println("set indexer");
            }
            else if(args[0].equals("-s")) //./index.post
            {
                if(args.length>3 && args[2].equals("-q"))
                {
                    System.out.println("#### search query ####");

                    searcher sc = new searcher();
                    sc.CalcSim(args[1],args[3]);
                }
            }
        }
        else
        {
            System.out.println("argument가 없습니다.");
            //searcher sc = new searcher();
            //sc.CalcSim("./src/index.post","밀은 일반적으로 무료로 처리할 수 있다.");
        }
        
        
    }
}

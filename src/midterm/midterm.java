import java.io.IOException;

public class midterm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		/*
		if(args.length>3)
		{
			if(args[0].equals("-f") && args[2].equals("-q"))
			{
				getSnippet gs = new getSnippet();
				
				gs.FindMaxKeyword(args[1], args[3]);
				
			}
		}
		*/
		
		getSnippet gs = new getSnippet();
		
		gs.FindMaxKeyword("./input.txt", "라면");
		
	}

}

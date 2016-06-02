package Parser;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Parser {

	public static List<ResultEntry> search(List<String> keywords, List<String> files) {
		List<ResultEntry> results = new ArrayList<ResultEntry>();
		
		for(String path : files) {
			try {
				// Open pdf to extract text
				File f = new File(path);
				PDDocument doc = PDDocument.load(f);
				int totalPages = doc.getNumberOfPages();
				PDFTextStripper ts = new PDFTextStripper();
				
				// Prepare Patterns from keywords
				List<String> patterns = new ArrayList<String>();
				for(String key : keywords) {
					patterns.add(".*" + key + ".*");
				}
				
				// Page by page
				for(int i=1; i<=totalPages; ++i) {
					ts.setStartPage(i);
					ts.setEndPage(i);
					String pageText = ts.getText(doc);
					
					String prevLine = new String();
					String currLine = new String();
					String nextLine = new String();
					
					Scanner sc = new Scanner(pageText);
					sc.useDelimiter("\r\n");
					
					// Line by line
					while(sc.hasNext()) {
						prevLine = currLine;
						currLine = nextLine;
						nextLine = sc.next();
						
						boolean bingo = false;
						for(String pattern : patterns)
							if(Pattern.matches(pattern, currLine))
								bingo = true;
						
						if(bingo) 
							results.add(
									new ResultEntry(
											prevLine+"\n"+currLine+"\n"+nextLine,
											path,
											i));
					}
					prevLine = currLine;
					currLine = nextLine;
					boolean bingo = false;
					for(String pattern : patterns)
						if(Pattern.matches(pattern, currLine))
							bingo = true;
					
					if(bingo) 
						results.add(new ResultEntry(prevLine+"\n"+currLine,path,i));
					
					sc.close();
					
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return results;
	}
}

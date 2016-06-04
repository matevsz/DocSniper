package Parser;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import Main.CDocument;

public class Parser {

	public static List<ResultEntry> search(String keywords, Map<Integer, CDocument> files) {
		List<ResultEntry> results = new ArrayList<ResultEntry>();
		
		for(Map.Entry<Integer, CDocument> path : files.entrySet()) {
			try {
				// Open pdf to extract text
				File f = new File(path.getValue().getPath());
				PDDocument doc = PDDocument.load(f);
				int totalPages = doc.getNumberOfPages();
				PDFTextStripper ts = new PDFTextStripper();
				
				// Prepare Patterns from keywords
				List<String> patterns = new ArrayList<String>();
				Scanner sc = new Scanner(keywords);
				sc.useDelimiter(" ");
				while(sc.hasNext()) {
					patterns.add(".*" + sc.next() + ".*");
				}
				sc.close();
				
				// Page by page
				for(int i=1; i<=totalPages; ++i) {
					ts.setStartPage(i);
					ts.setEndPage(i);
					String pageText = ts.getText(doc);
					
					String prevLine = new String();
					String currLine = new String();
					String nextLine = new String();
					
					sc = new Scanner(pageText);
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
											path.getValue(),
											i));
					}
					prevLine = currLine;
					currLine = nextLine;
					boolean bingo = false;
					for(String pattern : patterns)
						if(Pattern.matches(pattern, currLine))
							bingo = true;
					
					if(bingo) 
						results.add(new ResultEntry(prevLine+"\n"+currLine,path.getValue(),i));
					
					sc.close();
					
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return results;
	}
}

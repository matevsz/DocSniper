package Parser;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import Main.CDocument;
import ReportGenerator.CResultContainer;
import ReportGenerator.CResultContainer.CResultEntry;

public class Parser {

	public static ArrayList<CResultContainer> search(String keywords, Map<Integer, CDocument> files) {
		ArrayList<CResultContainer> results = new ArrayList<>();
		
		// Prepare Patterns from keywords
		List<String> patterns = new ArrayList<String>();
		Scanner sc = new Scanner(keywords);
		sc.useDelimiter(" ");
		while(sc.hasNext()) {
			patterns.add(".*" + sc.next() + ".*");
		}
		sc.close();
		
		for(Map.Entry<Integer, CDocument> path : files.entrySet()) {
			try {
				// New CResultContainer object for each file
				CResultContainer resultFromTheFile = new CResultContainer();
				resultFromTheFile.setFilename(path.getValue().getFileName());
				resultFromTheFile.setLinkToDocument(path.getValue().getPath() + "\\" + path.getValue().getFileName());
				results.add(resultFromTheFile);
				
				// Open pdf to extract text
				File f = new File(path.getValue().getPath() + "\\" + path.getValue().getFileName());
				PDDocument doc = PDDocument.load(f);
				int totalPages = doc.getNumberOfPages();
				PDFTextStripper ts = new PDFTextStripper();
				
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
							resultFromTheFile.setResult(
											prevLine+"\n"+currLine+"\n"+nextLine,
											//path.getValue(),
											new Integer(i).toString());
					}
					prevLine = currLine;
					currLine = nextLine;
					boolean bingo = false;
					for(String pattern : patterns)
						if(Pattern.matches(pattern, currLine))
							bingo = true;
					
					if(bingo) 
						resultFromTheFile.setResult(
								prevLine+"\n"+currLine+"\n"+nextLine,
								//path.getValue(),
								new Integer(i).toString());
					
					sc.close();
					
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return results;
	}
}

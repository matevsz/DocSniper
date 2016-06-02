package ReportGenerator;

import java.util.ArrayList;
import java.util.regex.Pattern;

/***
 * Container class to store search result. Used in report generator. Container is little generic.
 * It mean that programmers using this class can decide what will be displayed as additional
 * informations from search result which is stored in m_oDocSummaryInf and m_oGlobalSummaryInf.
 * One object represent one file. Static variables represent global information, and global summary.
 * That's why they are static variables, because they must exist, no matter what :D
 * 
 * 
 * @author Lukasz Przepiorka
 *
 */
public class CResultContainer
{
	//Some global informations
	private String m_strFilename = "";
	private String m_strLinkToDocument = "";
	//Additional informations which will be displayed for file in his informations section
	private ArrayList<String> m_oDocSummaryInf = new ArrayList<String>();
	//Object for search results
	private ArrayList<CResultEntry> m_oResults = new ArrayList<CResultEntry>();
	
	//Splitter letting store information type and his values in one string
	private final static String INFO_CONTAINER_TYPE_SPLIT_STRING = "<SEPARATOR>";
	//When value contains more than one element will be converted to one string with 
	//this separator beetwen them
	private final static String INFO_CONTAINER_BEETWEN_VALUES = ", ";
	//Additional informations container for global informations section in report
	private static ArrayList<String> m_oGlobalSummaryInf = new ArrayList<String>();
	
	/***
	 * @return doc name
	 */
	public String getFilename()
	{
		return m_strFilename;
	}
	
	/***
	 * @return link to doc file
	 */
	public String getLinkToDocument()
	{
		return m_strLinkToDocument;
	}
	
	/***
	 * @return return entry to results container
	 */
	public ArrayList<CResultEntry> getResultEntry()
	{
		return m_oResults;
	}
	
	/***
	 * @param a_strResult - founded text
	 * @param a_strSiteNumber - site in doc where is founded text
	 */
	public void setResult(String a_strResult, String a_strSiteNumber)
	{
		m_oResults.add(new CResultEntry(a_strResult, a_strSiteNumber));
	}
	
	/***
	 * @param a_strFilename - set doc name
	 */
	public void setFilename(String a_strFilename)
	{
		m_strFilename = a_strFilename;
	}
	
	/***
	 * @param a_strLinkToDocument - set link to doc
	 */
	public void setLinkToDocument(String a_strLinkToDocument)
	{
		m_strLinkToDocument = a_strLinkToDocument;
	}
	
	/***
	 * Set global additional information for file to container
	 * @param a_strInfType - information type e.g year, issn etc
	 * @param a_oValues - values for information type
	 */
	public void setDocSummaryInformation(String a_strInfType, ArrayList<String> a_oValues)
	{
		if(null != a_oValues && null != a_strInfType && a_oValues.size() > 0)
		{
			String toAdd = a_strInfType+INFO_CONTAINER_TYPE_SPLIT_STRING;
			for(int i=0; i<a_oValues.size(); i++)
			{
				toAdd += a_oValues.get(i)+INFO_CONTAINER_BEETWEN_VALUES;
			}
			toAdd.substring(0, toAdd.length()-2);
			m_oDocSummaryInf.add(toAdd);
		}
	}
	
	/***
	 * @param i - additional information for file iteratoe
	 * @return - additional information type and values
	 */
	public String[] getDocSummaryInformation(int i)
	{
		return m_oDocSummaryInf.get(i).split(Pattern.quote(INFO_CONTAINER_TYPE_SPLIT_STRING));
	}
	
	/***
	 * @return additional informations for file size 
	 */
	public int getDocSummaryInformationContainerSize()
	{
		return m_oDocSummaryInf.size();
	}
	
	/***
	 * Set global additional information to container
	 * @param a_strInfType - information type e.g year, issn etc
	 * @param a_oValues - values for information type
	 */
	public static void setGlobalSummaryInformation(String a_strInfType, ArrayList<String> a_oValues)
	{
		if(null != a_oValues && null != a_strInfType && a_oValues.size() > 0)
		{
			String toAdd = a_strInfType+INFO_CONTAINER_TYPE_SPLIT_STRING;
			for(int i=0; i<a_oValues.size(); i++)
			{
				toAdd += a_oValues.get(i)+INFO_CONTAINER_BEETWEN_VALUES;
			}
			toAdd.substring(0, toAdd.length()-2);
			m_oGlobalSummaryInf.add(toAdd);
		}
	}
	
	/***
	 * @param i - additional global information iterator
	 * @return - additional information type and values
	 */
	public static String[] getGlobalSummaryInformation(int i)
	{
		return m_oGlobalSummaryInf.get(i).split(Pattern.quote(INFO_CONTAINER_TYPE_SPLIT_STRING));
	}
	
	/***
	 * @return additionl global informations container size 
	 */
	public static int getGlobalSummaryInformationContainerSize()
	{
		return m_oGlobalSummaryInf.size();
	}
	
	/***
	 * Clearing global additional informations container
	 */
	public static void clearGlobalSummaryInformationsSection()
	{
		m_oGlobalSummaryInf.clear();
	}
	
	/***
	 * Class contains result entry
	 */
	public class CResultEntry {
		//Founded text
		private String m_strResult = "";
		//Number or page
		private String m_strSiteNumber = "";
		
		/***
		 * Constructor which can fill result entry
		 * @param a_strResult - founded text
		 * @param a_strSiteNumber - page
		 */
		CResultEntry(String a_strResult, String a_strSiteNumber)
		{
			m_strResult = a_strResult;
			m_strSiteNumber = a_strSiteNumber;
		}
		
		/***
		 * @return founded text
		 */
		public String getResult()
		{
			return m_strResult;
		}
		
		/***
		 * @return page where text was founded
		 */
		public String getSiteNumber()
		{
			return m_strSiteNumber;
		}
		
		/***
		 * set founded text
		 * @param a_strResult - founded text
		 */
		public void setResult(String a_strResult)
		{
			m_strResult = a_strResult;
		}
		
		/***
		 * set page number
		 * @param a_strSiteNumber - page number
		 */
		public void setSiteNumber(String a_strSiteNumber)
		{
			m_strSiteNumber = a_strSiteNumber;
		}
	}
}
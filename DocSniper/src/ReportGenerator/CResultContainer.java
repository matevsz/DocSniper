package ReportGenerator;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class CResultContainer
{
	private String m_strFilename = "";
	private String m_strLinkToDocument = "";
	private ArrayList<String> m_oDocSummaryInf = new ArrayList<String>();
	private ArrayList<CResultEntry> m_oResults = new ArrayList<CResultEntry>();
	
	private final static String INFO_CONTAINER_TYPE_SPLIT_STRING = "$";
	private final static String INFO_CONTAINER_BEETWEN_VALUES = ", ";
	private static ArrayList<String> m_oGlobalSummaryInf = new ArrayList<String>();
	
	public String getFilename()
	{
		return m_strFilename;
	}
	
	public String getLinkToDocument()
	{
		return m_strLinkToDocument;
	}
	
	public ArrayList<CResultEntry> getResultEntry()
	{
		return m_oResults;
	}
	
	public void setResult(String a_strResult, String a_strSiteNumber)
	{
		m_oResults.add(new CResultEntry(a_strResult, a_strSiteNumber));
	}
	
	public void setFilename(String a_strFilename)
	{
		m_strFilename = a_strFilename;
	}
	
	public void setLinkToDocument(String a_strLinkToDocument)
	{
		m_strLinkToDocument = a_strLinkToDocument;
	}
	
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
	
	public String[] getDocSummaryInformation(int i)
	{
		return m_oDocSummaryInf.get(i).split(Pattern.quote(INFO_CONTAINER_TYPE_SPLIT_STRING));
	}
	
	public int getDocSummaryInformationContainerSize()
	{
		return m_oDocSummaryInf.size();
	}
	
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
	
	public static String[] getGlobalSummaryInformation(int i)
	{
		return m_oGlobalSummaryInf.get(i).split(Pattern.quote(INFO_CONTAINER_TYPE_SPLIT_STRING));
	}
	
	public static int getGlobalSummaryInformationContainerSize()
	{
		return m_oGlobalSummaryInf.size();
	}
	
	public static void clearGlobalSummaryInformationContainer()
	{
		m_oGlobalSummaryInf.clear();
	}
	
	public class CResultEntry {
		private String m_strResult = "";
		private String m_strSiteNumber = "";
		
		CResultEntry(String a_strResult, String a_strSiteNumber)
		{
			m_strResult = a_strResult;
			m_strSiteNumber = a_strSiteNumber;
		}
		
		public String getResult()
		{
			return m_strResult;
		}
		
		public String getSiteNumber()
		{
			return m_strSiteNumber;
		}
		
		public void setResult(String a_strResult)
		{
			m_strResult = a_strResult;
		}
		
		public void setSiteNumber(String a_strSiteNumber)
		{
			m_strSiteNumber = a_strSiteNumber;
		}
	}
}
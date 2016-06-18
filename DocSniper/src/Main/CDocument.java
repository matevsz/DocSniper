package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CDocument
{
	public static final String HEADER = "@article";
	public static final String TITLE = "title";
	public static final String JOURNAL = "journal";
	public static final String VOLUME = "volume";
	public static final String NUMBER = "number";
	public static final String PAGES = "pages";
	public static final String YEAR = "year";
	public static final String NOTE = "note";
	public static final String ISSN = "issn";
	public static final String DOI = "doi";
	public static final String URL = "url";
	public static final String AUTOR = "author";
	public static final String KEYWORDS = "keywords";
	public static final String ABSTRACT = "abstract";
	public static final String FILE = "file";
	
	public static final int TITLE_IDX = 0;
	public static final int JOURNAL_IDX = 1;
	public static final int VOLUME_IDX = 2;
	public static final int NUMBER_IDX = 3;
	public static final int PAGES_IDX = 4;
	public static final int YEAR_IDX = 5;
	public static final int NOTE_IDX = 6;
	public static final int ISSN_IDX = 7;
	public static final int DOI_IDX = 8;
	public static final int URL_IDX = 9;
	public static final int AUTOR_IDX = 10;
	public static final int ABSTRACT_IDX = 11;
	public static final int FILE_IDX = 12;
	
	public static final String ATTR_SEPARATOR = ",";
	public static final String VALUE_SEPARATOR = "\"";
	
	private static final int SPLITER_FIRST = 0;
	private static final int SPLITER_REST = 1;
	private static final int SPLITER_DIVIDER = 2;
	
	private String m_strTitle = "";
	private String m_strJournal = "";
	private String m_strVolume = "";
	private String m_strNumber = "";
	private String m_strPages = "";
	private String m_strYear = "";
	private String m_strNote = "";
	private String m_strISSN = "";
	private String m_strDOI = "";
	private String m_strURL = "";
	private String m_strAutor = "";
	private ArrayList<String> m_oKeywordsList = null;
	private String m_strAbstract = "";
	
	private String m_strPath = "";
	
	private String m_strFileName = null;
	private CCounter m_oCounter = null;
	private boolean m_bIsSelected = false;
	
	public CDocument(String a_strInput, CCounter a_oCounter, String a_strPath)
	{
		m_oKeywordsList = new ArrayList<>();
		
		loadDocInfo(a_strInput);
		
		m_strTitle = (null == m_strTitle) ? "" : m_strTitle;
		m_strJournal = (null == m_strJournal) ? "" : m_strJournal;
		m_strVolume = (null == m_strVolume) ? "" : m_strVolume;
		m_strNumber = (null == m_strNumber) ? "" : m_strNumber;
		m_strPages = (null == m_strPages) ? "" : m_strPages;
		m_strYear = (null == m_strYear) ? "" : m_strYear;
		m_strNote = (null == m_strNote) ? "" : m_strNote;
		m_strISSN = (null == m_strISSN) ? "" : m_strISSN;
		m_strDOI = (null == m_strDOI) ? "" : m_strDOI;
		m_strURL = (null == m_strURL) ? "" : m_strURL;
		m_strAutor = (null == m_strAutor) ? "" : m_strAutor;
		m_strAbstract = (null == m_strAbstract) ? "" : m_strAbstract;
		m_strPath = (null == a_strPath) ? "" : a_strPath;
		
		m_oCounter = a_oCounter;
		
		if(null != m_strFileName)
		{
			File oFile = new File(m_strPath+"\\"+m_strFileName);
			if(!oFile.isFile())
			{
				m_strFileName = null;
			}
		}
		
	}

	public void setFileName(String a_strFileName)
	{
		m_strFileName = a_strFileName;
	}
	
	public String getPath()
	{
		return m_strPath;
	}
	
	public void setSelected(boolean a_bNewState)
	{
		if((true == a_bNewState) && (false == m_bIsSelected))
		{
			m_bIsSelected = a_bNewState;
			m_oCounter.increment();
		}
		else if((false == a_bNewState) && (true == m_bIsSelected))
		{
			m_bIsSelected = a_bNewState;
			m_oCounter.decrement();
		}
	}
	
	public void changeSelection()
	{
		if(false == m_bIsSelected)
		{
			m_bIsSelected = true;
			m_oCounter.increment();
		}
		else
		{
			m_bIsSelected = false;
			m_oCounter.decrement();
		}
	}
	
	public String getFileName()
	{
		return m_strFileName;
	}
	
	public String getTitle()
	{
		return m_strTitle;
	}
	
	public String toString()
	{
		String strReturn = " ";
		if(true == m_bIsSelected)
		{
			strReturn = "[X] " + m_strTitle; 
		}
		else
		{
			strReturn = "[ ] " + m_strTitle;
		}
		
		return strReturn;
	}
	
	public String getJournal()
	{
		return m_strJournal;
	}
	
	public String getVolume()
	{
		return m_strVolume;
	}
	
	public String getNumber()
	{
		return m_strNumber;
	}
	
	public String getPages()
	{
		return m_strPages;
	}
	
	public String getYear()
	{
		return m_strYear;
	}
	
	public String getNote()
	{
		return m_strNote;
	}
	
	public String getISSN()
	{
		return m_strISSN;
	}
	
	public String getDOI()
	{
		return m_strDOI;
	}
	
	public String getURL()
	{
		return m_strURL;
	}
	
	public String getAutor()
	{
		return m_strAutor;
	}
	
	public String getKeywords()
	{
		String strResult = " ";
		
		for(String strIterator : m_oKeywordsList)
		{
			strResult += strIterator;
		}
		
		return strResult;
	}
	
	public String getAbstract()
	{
		return m_strAbstract;
	}
	
	private void loadDocInfo(String a_strInputData)
	{
		String[] tstrSpliter = null;
		
		if( (null != a_strInputData) && (a_strInputData.startsWith(CDocument.HEADER)))
		{
			a_strInputData = a_strInputData.split(CDocument.ATTR_SEPARATOR, SPLITER_DIVIDER)[SPLITER_REST];
			
			while("" != a_strInputData)
			{
				tstrSpliter = a_strInputData.split(CDocument.VALUE_SEPARATOR + CDocument.ATTR_SEPARATOR, SPLITER_DIVIDER);
				
				if(SPLITER_DIVIDER == tstrSpliter.length)
				{
					a_strInputData = tstrSpliter[SPLITER_REST];
				}
				else
				{
					break;
				}
				
				tstrSpliter = tstrSpliter[SPLITER_FIRST].split(" ", SPLITER_DIVIDER);
				switch(tstrSpliter[SPLITER_FIRST])
				{
					case CDocument.KEYWORDS :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_oKeywordsList.add(tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, ""));
					break;
					case CDocument.ABSTRACT :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strAbstract = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR + "}", "");
					break;
					case CDocument.AUTOR :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strAutor = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.DOI :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strDOI = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.ISSN :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strISSN = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.JOURNAL :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strJournal = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.NOTE :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strNote = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.NUMBER :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strNumber = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.PAGES :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strPages = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.TITLE :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strTitle = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.URL :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strURL = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.VOLUME :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strVolume = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.YEAR :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strYear = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
					case CDocument.FILE :
						tstrSpliter = tstrSpliter[SPLITER_REST].split(CDocument.VALUE_SEPARATOR, SPLITER_DIVIDER);
						m_strFileName = tstrSpliter[SPLITER_REST].replace(CDocument.VALUE_SEPARATOR, "");
					break;
				}
			}
		}
	}
	
	public boolean isSelected()
	{
		return m_bIsSelected;
	}
	
	public void save(BufferedWriter a_oBufferedWriter)
	{
		File oFile = null;
		if(null != m_strFileName)
		{
			oFile = new File(m_strFileName);
		}
		try 
		{
			a_oBufferedWriter.write(CDocument.HEADER+"{gen,\n");
			a_oBufferedWriter.write(CDocument.TITLE + " = \"" + m_strTitle + "\",\n");
			a_oBufferedWriter.write(CDocument.JOURNAL + " = \"" + m_strJournal + "\",\n");
			a_oBufferedWriter.write(CDocument.VOLUME + " = \"" + m_strVolume + "\",\n");
			a_oBufferedWriter.write(CDocument.NUMBER + " = \"" + m_strNumber + "\",\n");
			a_oBufferedWriter.write(CDocument.PAGES + " = \"" + m_strPages + "\",\n");
			a_oBufferedWriter.write(CDocument.YEAR + " = \"" + m_strYear + "\",\n");
			a_oBufferedWriter.write(CDocument.NOTE + " = \"" + m_strNote + "\",\n");
			a_oBufferedWriter.write(CDocument.ISSN + " = \"" + m_strISSN + "\",\n");
			a_oBufferedWriter.write(CDocument.DOI + " = \"" + m_strDOI + "\",\n");
			a_oBufferedWriter.write(CDocument.URL + " = \"" + m_strURL + "\",\n");
			a_oBufferedWriter.write(CDocument.AUTOR + " = \"" + m_strAutor + "\",\n");
			
			for(String strIterator : m_oKeywordsList)
			{
				a_oBufferedWriter.write(CDocument.KEYWORDS + " = \"" + strIterator + "\",\n");
			}
			a_oBufferedWriter.write(CDocument.ABSTRACT + " = \"" + m_strAbstract + "\"");
			
			if(null != oFile)
			{
				a_oBufferedWriter.write(",\n");
				a_oBufferedWriter.write(CDocument.FILE + " = \"" + m_strFileName + "\",\n");
			}
			else
			{
				a_oBufferedWriter.write("\n");
			}
			a_oBufferedWriter.write("}\n");
		}
		catch (IOException e)
		{
			CLogger.log(CLogger.WARNING, "Can not write document info to File" + e.getMessage());
		}
	}
}

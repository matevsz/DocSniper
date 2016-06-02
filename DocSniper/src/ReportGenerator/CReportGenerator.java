package ReportGenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ReportGenerator.CResultContainer.CResultEntry;
import Main.CLogger;
import ReportGenerator.CResultContainer;

/***
 * Report Generator class, result is index.html file in application workspace folder
 * "Report\". This class need to filled container object from CResultContainer given
 * as argument in generate(ArrayList<CResultContainer>) method. Singleton is implemented
 * 
 * 
 * @author Lukasz Przepiorka
 *
 */
public class CReportGenerator 
{
	private static CReportGenerator INSTANCE = null;
	private File m_oIndex = null;
	private PrintWriter m_oIndexWriter = null;
	private final String m_strNewLine = System.getProperty("line.separator");
	private ArrayList<CResultContainer> m_oResult = new ArrayList<CResultContainer>();
	private String m_strDate;
	
	private static final int TABLE_HEADER_FILE_NAME_MAX_WIDTH = 30;
	
	/***
	 * Get instance of class if exist, if no create new object
	 * @return class object
	 */
	public static CReportGenerator getInstance()
	{
		if(null == INSTANCE)
		{
			INSTANCE = new CReportGenerator();
		}
		return INSTANCE;
	}
	
	/***
	 * Cleaning all useless data from variables after previous raport
	 * generation process
	 */
	private void cleanData()
	{
		m_strDate = "";
		m_oIndexWriter = null;
		m_oIndex = null;
		m_oResult.clear();
	}
	
	/***
	 * Start generating report
	 * @param a_oResultList - container for results and other things displayed in report
	 */
	public void generate(ArrayList<CResultContainer> a_oResultList)
	{
		if(null != a_oResultList && a_oResultList.size() > 0)
		{
			cleanData();
			m_oResult = a_oResultList;
			createReportFiles();
			fillReportFile();
		}
		else
		{
			CLogger.log(CLogger.WARNING, "Report Generator: Error result container is empty");
		}
	}
	
	/***
	 * Creating report file and directory to store report if no exist.
	 */
	private void createReportFiles()
	{
		DateFormat oDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Calendar oCalender = Calendar.getInstance();
		m_strDate = oDateFormat.format(oCalender.getTime());
		String strMainFolderName = "Report";
		try
		{
			File oReportDirectory = new File(strMainFolderName);
			if(!oReportDirectory.exists())
			{
				oReportDirectory.mkdir();
			}
			final String CONVERTED_DATE = m_strDate.replaceAll("[. :]", "_");
			m_oIndex = new File(strMainFolderName+"/Report_"+CONVERTED_DATE+".html");
			int iNumberToName = 1;
			while(m_oIndex.exists())
			{
				m_oIndex = new File(strMainFolderName+"/Report_"+CONVERTED_DATE+"_"+Integer.toString(iNumberToName)+".html");
				iNumberToName++;
			}
			m_oIndex.createNewFile();
		}
		catch(Exception ex)
		{
			CLogger.log(CLogger.WARNING, "Report Generator: Error in method createReportFiles(). Files not created");
		}
		CLogger.log(CLogger.INFO, "Report Generator: Files created sucesfuly");
	}
	
	/***
	 * This class filling .html report file
	 */
	private void fillReportFile()
	{
		if(!m_oIndex.exists())
		{
			try 
			{
				m_oIndex.createNewFile();
			} 
			catch (IOException e)
			{
				CLogger.log(CLogger.WARNING, "Report Generator: index.html file not created");
			}
		}
		try 
		{
			m_oIndexWriter = new PrintWriter(m_oIndex);
			m_oIndexWriter.write("<!DOCTYPE HTML>"+m_strNewLine);
			m_oIndexWriter.write("<html>"+m_strNewLine);
			m_oIndexWriter.write("<head>"+m_strNewLine);
			m_oIndexWriter.write("   <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"+m_strNewLine);
			m_oIndexWriter.write("   <title>Report</title>"+m_strNewLine);
			m_oIndexWriter.write("   <style>"+m_strNewLine);
			AddStyles();
			m_oIndexWriter.write("   </style>"+m_strNewLine);
			m_oIndexWriter.write("</head>"+m_strNewLine);
			m_oIndexWriter.write("<body>"+m_strNewLine);
			m_oIndexWriter.write("   <div><h2 id = global_summary>GLOBAL INFORMATIONS</h2></div>"+m_strNewLine);
			m_oIndexWriter.write("   <div>Date:"+m_strDate+"</div>"+m_strNewLine);
			//filling additional informations given by user wich should be displayed in global
			//information section
			for(int i=0; i<CResultContainer.getGlobalSummaryInformationContainerSize(); i++)
			{
				String[] strInfo = CResultContainer.getGlobalSummaryInformation(i);
				m_oIndexWriter.write("   <div>"+strInfo[0]+": "+strInfo[1]+"</div>"+m_strNewLine);
			}
			m_oIndexWriter.write("   <div><h2>SUMMARY</h2></div>"+m_strNewLine);
			m_oIndexWriter.write("   <table>"+m_strNewLine);
			m_oIndexWriter.write("  <tr>"+m_strNewLine);
			m_oIndexWriter.write("   <th>Filename</th>"+m_strNewLine);
			m_oIndexWriter.write("   <th>Number of results</th>"+m_strNewLine);
			m_oIndexWriter.write("  </tr>"+m_strNewLine);
			String strFileName = "";
			//cut to long file names in result table
			for(int i=0; i<m_oResult.size(); i++)
			{
				strFileName = m_oResult.get(i).getFilename();
				if(TABLE_HEADER_FILE_NAME_MAX_WIDTH < strFileName.length()-10)
				{
					strFileName = strFileName.substring(0, TABLE_HEADER_FILE_NAME_MAX_WIDTH)+"...";
				}
				m_oIndexWriter.write("  <tr>"+m_strNewLine);
				m_oIndexWriter.write("   <td><a href='#"+m_oResult.get(i).getFilename()+"'>"+strFileName+"</a></td></td>"+m_strNewLine);
				m_oIndexWriter.write("   <td>"+m_oResult.get(i).getResultEntry().size()+"</td>"+m_strNewLine);
				m_oIndexWriter.write("  </tr>"+m_strNewLine);
			}
			m_oIndexWriter.write("   </table>"+m_strNewLine);
			m_oIndexWriter.write("   <div>Date:Files with results: "+getFilesNumberWhichContainsResults()+"/"+m_oResult.size()+"</div>"+m_strNewLine);
			addResultsForEveryDocToReport();
			m_oIndexWriter.write(" </body>"+m_strNewLine);
			m_oIndexWriter.write("</html>");
			m_oIndexWriter.close();
		}
		catch (Exception ex) 
		{
			CLogger.log(CLogger.WARNING, "Report Generator: Error in creating index.html file");
		}
		finally
		{
			//Clearing not needed anymore additional informations container for global informations section
			CResultContainer.clearGlobalSummaryInformationsSection();
			m_oIndexWriter.close();
		}
		CLogger.log(CLogger.INFO, "Report Generator: Index.html file created");
	}
	
	/***
	 * Calculate number of files witch result > 0
	 * @return number of files witch result > 0
	 */
	private int getFilesNumberWhichContainsResults()
	{
		int iFilesNumber = m_oResult.size();
		int iResult = 0;
		for(int i=0; i<iFilesNumber; i++)
		{
			if(m_oResult.get(i).getResultEntry().size() > 0)
			{
				iResult++;
			}
		}
		return iResult;
	}
	
	/***
	 * Adding styles to report
	 * @throws Exception
	 */
	private void AddStyles() throws Exception
	{
		m_oIndexWriter.write("   h2 {"+m_strNewLine);
		m_oIndexWriter.write("       border-bottom: 1px solid black;"+m_strNewLine);
		m_oIndexWriter.write("       min-width: 100%;"+m_strNewLine);
		m_oIndexWriter.write("       background-color: #FFFF66;"+m_strNewLine);
		m_oIndexWriter.write("       color: black;"+m_strNewLine);
		m_oIndexWriter.write("       text-shadow: 2px 2px 4px rgba(221, 156, 16, 1);"+m_strNewLine);
		m_oIndexWriter.write("   }"+m_strNewLine);
		m_oIndexWriter.write(m_strNewLine);
		m_oIndexWriter.write("   table{"+m_strNewLine);
		m_oIndexWriter.write("       border: 1px solid black;"+m_strNewLine);
		m_oIndexWriter.write("   }"+m_strNewLine);
		m_oIndexWriter.write(m_strNewLine);
		m_oIndexWriter.write("   th{"+m_strNewLine);
		m_oIndexWriter.write("       border: 1px solid black;"+m_strNewLine);
		m_oIndexWriter.write("       background-color: #FFFF66;"+m_strNewLine);
		m_oIndexWriter.write("   }"+m_strNewLine);
		m_oIndexWriter.write(m_strNewLine);
		m_oIndexWriter.write("   td{"+m_strNewLine);
		m_oIndexWriter.write("       border: 1px solid black;"+m_strNewLine);
		m_oIndexWriter.write("   }"+m_strNewLine);
		m_oIndexWriter.write(m_strNewLine);
		m_oIndexWriter.write("   body{"+m_strNewLine);
		m_oIndexWriter.write("       background-color: #FAFAD2;"+m_strNewLine);
		m_oIndexWriter.write("   }");
		m_oIndexWriter.write(m_strNewLine);
		m_oIndexWriter.write("   div{"+m_strNewLine);
		m_oIndexWriter.write("       word-wrap: break-word;"+m_strNewLine);
		m_oIndexWriter.write("   }");
		m_oIndexWriter.write(m_strNewLine);
	}
	
	/***
	 * Start process to fill sections in report for every files with result > 0
	 * @throws Exception
	 */
	private void addResultsForEveryDocToReport() throws Exception
	{
		for(int iPDFId=0; iPDFId<m_oResult.size(); iPDFId++)
		{
			if(m_oResult.get(iPDFId).getResultEntry().size() > 0)
			{
				createSectionForOneFile(iPDFId);
			}
		}
	}
	
	/***
	 * Filling section in report for one file
	 * @param iIndex - file index in container
	 * @throws Exception
	 */
	private void createSectionForOneFile(int iIndex) throws Exception
	{
		m_oIndexWriter.write("   <div><h2 id = \""+m_oResult.get(iIndex).getFilename()+"\">"+m_oResult.get(iIndex).getFilename()+".pdf</h2></div>"+m_strNewLine);
		m_oIndexWriter.write("   <div>Search results: "+m_oResult.get(iIndex).getResultEntry().size()+"</div>"+m_strNewLine);
		for(int i=0; i<m_oResult.get(iIndex).getDocSummaryInformationContainerSize(); i++)
		{
			String[] strInfo = m_oResult.get(iIndex).getDocSummaryInformation(i);
			m_oIndexWriter.write("   <div>"+strInfo[0]+": "+strInfo[1]+"</div>"+m_strNewLine);
		}
		m_oIndexWriter.write("   <a href="+m_oResult.get(iIndex).getLinkToDocument()+">Link to document</a>");
		m_oIndexWriter.write("   <div><h2>RESULTS</h2></div>"+m_strNewLine);
		ArrayList<CResultEntry> oResultEntry = m_oResult.get(iIndex).getResultEntry();
		for(int i=0; i<oResultEntry.size(); i++)
		{
			m_oIndexWriter.write("   <div>"+oResultEntry.get(i).getResult()+"</div>"+m_strNewLine);
			m_oIndexWriter.write("   <div>Page: "+oResultEntry.get(i).getSiteNumber()+"</div>"+m_strNewLine);
			m_oIndexWriter.write("   <a href="+m_oResult.get(iIndex).getLinkToDocument()+"#page="+oResultEntry.get(i).getSiteNumber()+">link to founded result in document</a>");
			m_oIndexWriter.write("   <div><h2> </h2></div>"+m_strNewLine);
		}
		m_oIndexWriter.write("   <td><a href='#global_summary'>Back to global informations</a></td></td>"+m_strNewLine);
	}
}
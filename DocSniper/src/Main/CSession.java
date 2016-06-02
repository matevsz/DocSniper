package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSession
{
	public static final String SESSION_FILE_NAME = "SessionDescFile.docs";

	private String m_strName;
	private String m_strPath;
	private CCounter m_oCounter = null;
	
	public ArrayList<CDocument> m_oFileDescriptions = null;
	
	public CSession(String a_strName, String a_strPath)
	{
		m_strName = a_strName;
		m_strPath = a_strPath;
		
		m_oCounter = new CCounter();
		m_oFileDescriptions = loadSession(m_strPath, m_oCounter);
	}
	
	public String name()
	{
		return m_strName;
	}
	
	public String toString()
	{
		String strResult = null;
		if(0 < m_oFileDescriptions.size())
		{
			strResult = (" [ " + m_oCounter.counterStatus() + " ] " +  m_strName);
		}
		else
		{
			strResult = (" [ - ] " +  m_strName);
		}
		return strResult;
	}
	
	public String path()
	{
		return m_strPath;
	}
	
	public CCounter getCounter()
	{
		return m_oCounter;
	}
	
	private ArrayList<CDocument> loadSession(String a_strPathToFile, CCounter a_oCounter)
	{
		ArrayList<CDocument> oResult = new ArrayList<>();
		ArrayList<String> oDocumentsList = new ArrayList<>();
		File oFile = null;
		BufferedReader oFileReader = null;
		String strBuffer = "";
		String strCollector = "";
		a_strPathToFile += "\\" + SESSION_FILE_NAME;
		if(null != a_strPathToFile)
		{
			oFile = new File(a_strPathToFile);
			if(oFile.isFile())
			{
				try {
					oFileReader = new BufferedReader(new FileReader(oFile));
					strCollector = oFileReader.readLine();
					
					while(null != (strBuffer = oFileReader.readLine()))
					{
						if(strBuffer.startsWith(CDocument.HEADER))
						{
							oDocumentsList.add(strCollector + "\",");
							strCollector = "";
						}
						strCollector += strBuffer;
					}
					
					oFileReader.close();
				} catch (FileNotFoundException e) {
					CLogger.log(CLogger.WARNING, "Session file not exists " + e.getMessage());
				} catch (IOException e) {
					CLogger.log(CLogger.WARNING, "Error while reading session file " + e.getMessage());
				}
			}
		}
		
		for( String oIterator : oDocumentsList)
		{
			oResult.add(new CDocument(oIterator, a_oCounter, m_strPath));
		}
		
		return oResult;
	}
	
	public void saveSessionFile()
	{
		File oFile = new File(m_strPath+"/"+SESSION_FILE_NAME);
		BufferedWriter oFileWriter = null;
		
		if(!oFile.isFile())
		{
			try
			{
				oFile.createNewFile();
			}
			catch (IOException e)
			{
				CLogger.log(CLogger.WARNING, "Can not create session file " + m_strName + " " + e.getMessage());
				return;
			}
		}
		try {
			oFileWriter = new BufferedWriter(new FileWriter(oFile));
			
			for(CDocument oIterator : m_oFileDescriptions)
			{
				oIterator.save(oFileWriter);
			}
			
			oFileWriter.close();
		} catch (IOException e) {
			CLogger.log(CLogger.WARNING, "Can not save session " + m_strName + " " + e.getMessage());
			return;
		}
		
		
	}
}

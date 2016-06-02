package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSessionsContainer
{
	private static final String REPO_NAME = "documents.txt";
	
	public static final String FILE_SESSIONS_SEPARATOR = "session";
	private static final String FILE_MAIN_ELEMENT = "Main_sessions_file";
	private static final String FILE_NAME = "name";
	private static final String FILE_PATH = "path";
	private static final String FILE_REGISTRY_SEPARATOR = ",";
	
	private static final int SPLITER_FIRST = 0;
	private static final int SPLITER_REST = 1;
	private static final int SPLITER_DIVIDER = 2;
	
	public static final int NO_ERROR = 1;
	public static final int NULL_NAME = 2;
	public static final int EMPTY_NAME = 3;
	public static final int NULL_PATH = 4;
	public static final int EMPTY_PATH = 5;
	public static final int DIRECTORY_NOT_EXISTS = 6;
	
	private ArrayList<CSession> m_oSessionsData = null;
	
	public CSessionsContainer()
	{
		m_oSessionsData = new ArrayList<CSession>();
	}
	
	public int addSession(String a_strName, String a_strPath)
	{
		int iReturn = NO_ERROR;
		if((null == a_strName))
		{
			iReturn = NULL_NAME;
			CLogger.log(CLogger.WARNING, "Name variable is null");
		}
		else if(null == a_strPath)
		{
			iReturn = NULL_PATH;
			CLogger.log(CLogger.WARNING, "Path variable is null");
		}
		else if(0 == a_strName.length())
		{
			iReturn = EMPTY_NAME;
			CLogger.log(CLogger.WARNING, "Name variable is empty");
		}
		else if(0 == a_strPath.length())
		{
			iReturn = EMPTY_PATH;
			CLogger.log(CLogger.WARNING, "Path variable is empty");
		}
		else if( !(new File(a_strPath)).isDirectory() )
		{
			iReturn = DIRECTORY_NOT_EXISTS;
			CLogger.log(CLogger.WARNING, "Directory not exists");
		}
		else
		{
			m_oSessionsData.add(new CSession(a_strName, a_strPath));
		}
		
		return iReturn;
	}
	
	public int size()
	{
		return m_oSessionsData.size();
	}
	
	public int nodeCount()
	{
		int iResult = 0;
		for(int i = 0; i < m_oSessionsData.size(); i++)
		{
			iResult++;
			if(null != m_oSessionsData.get(i).m_oFileDescriptions)
			{
				iResult += m_oSessionsData.get(i).m_oFileDescriptions.size();
			}
		}
		return iResult;
	}
	
	public CSession get(int a_iIdx)
	{
		CSession oResult = null;
		if((0 <= a_iIdx) && (size() > a_iIdx))
		{
			oResult = m_oSessionsData.get(a_iIdx);
		}
		return oResult;
	}
	
	
	
	public ArrayList<CDocument> getNodes(int a_iIdx)
	{
		return get(a_iIdx).m_oFileDescriptions;
	}
	
	public void saveRepo()
	{
		File oRepoFile = new File(REPO_NAME);
		BufferedWriter oRepoWriter = null;
		
		if(isRepo())
		{
			oRepoFile.delete();
		}
		
		
		try 
		{
			oRepoFile.createNewFile();
			oRepoWriter = new BufferedWriter(new FileWriter(oRepoFile));
			
			oRepoWriter.write("{ \"" + FILE_MAIN_ELEMENT + "\":\r\n");
			
			for(int i = 0; i < m_oSessionsData.size(); i++)
			{
				oRepoWriter.write("\t\""+FILE_SESSIONS_SEPARATOR+"\":{\""+FILE_NAME+"\": \""+m_oSessionsData.get(i).name()+
							"\",\""+FILE_PATH+"\": \""+m_oSessionsData.get(i).path()+"\"}\r\n");
			}
			oRepoWriter.write("}");
			
			oRepoWriter.close();
			for(int i = 0; i < m_oSessionsData.size(); i++)
			{
				m_oSessionsData.get(i).saveSessionFile();
			}
			
		} catch (FileNotFoundException e) {
			CLogger.log(CLogger.WARNING, "Can not create Sessions file " + e.getMessage());
			return;
		} catch (IOException e) {
			CLogger.log(CLogger.WARNING, "Can not write Sessions data to file " + e.getMessage());
		}
		
	}
	
	public void loadRepo()
	{
		File oRepoFile = null;
		BufferedReader oRepoReader = null;
		String strFileData = null;
		String strConcanatedData = "";
		String[] tstrSpliter = null;
		String[] tstrNamePath = null;
		
		String strName = "";
		String strPath = "";
		
		
		m_oSessionsData = new ArrayList<>();
		
		if(isRepo())
		{
			try 
			{
				oRepoFile = new File(REPO_NAME);
				oRepoReader = new BufferedReader(new FileReader(oRepoFile));
				
				while(null != (strFileData = oRepoReader.readLine()))
				{
					strConcanatedData += strFileData;
				}
				oRepoReader.close();
				
			} 
			catch (FileNotFoundException e)
			{
				CLogger.log(CLogger.WARNING, "Can not open file with sessions data" + e.getMessage());
				return;
			} catch (IOException e) {
				CLogger.log(CLogger.WARNING, "Error while reading sessions data file" + e.getMessage());
				return;
			}
			strConcanatedData = strConcanatedData.replaceAll(" ", "").replace("\t", "");
			
			tstrSpliter = strConcanatedData.split("\"" + FILE_SESSIONS_SEPARATOR + "\":", SPLITER_DIVIDER);
			strConcanatedData = tstrSpliter[SPLITER_REST];
			tstrSpliter[SPLITER_FIRST] = tstrSpliter[SPLITER_FIRST].replace("{", "").replace("\"", "").replace(":", "");
			if(FILE_MAIN_ELEMENT.equals(tstrSpliter[SPLITER_FIRST]))
			{
				tstrSpliter = strConcanatedData.split("\"" + FILE_SESSIONS_SEPARATOR + "\":");
				for(int i = 0; i < tstrSpliter.length ; i++)
				{
					tstrSpliter[i] = tstrSpliter[i].replace("\"", "").replace("{", "").replace("}", "");
					tstrNamePath = tstrSpliter[i].split(FILE_REGISTRY_SEPARATOR, SPLITER_DIVIDER); 
					strName = tstrNamePath[SPLITER_FIRST].replace(FILE_NAME + ":", "");
					strPath = tstrNamePath[SPLITER_REST].replace(FILE_PATH + ":", "");
					
					addSession(strName, strPath);
				}
			}
			else
			{
				CLogger.log(CLogger.WARNING, "Sessions file is currupted");
			}
			
		}
	}
	
	static public boolean isRepo()
	{
		File oFile = new File(REPO_NAME);
		boolean bResult = false;
		
		if(oFile.isFile())
		{
			bResult = true;
		}
		
		return bResult;
	}

}

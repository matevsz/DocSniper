package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CLogger
{
	static final private File m_oFile = new File("AppLog.txt");
	static final private String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss.SSS";
	static final private int CALLER = 2;
	
	static final public String INFO = "[INFO]";
	static final public String WARING = "[WARING]";
	
	
	static public boolean createLogFile()
	{
		boolean bResult = true;
		
		if(m_oFile.exists())
		{
			bResult = m_oFile.delete();
		}
		
		if(bResult)
		{
			bResult = isLogFile();
		}

		CLogger.log(CLogger.INFO, "Log file is created");
		return bResult;
	}
	
	static private boolean isLogFile()
	{
		boolean bResult = true;

		if(!m_oFile.isFile())
		{
			try
			{
				m_oFile.createNewFile();
			}
			catch (IOException e) 
			{
				return false;
			}
		}
		return bResult;
	}
	
	static private String timeStamp()
	{
		SimpleDateFormat oDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return oDateFormat.format(new Date());
	}
	
	static public void log(String a_strLogLevel, String a_strLogNote)
	{
		FileOutputStream oFileStream = null;
		StackTraceElement[] oStackTraceElement = Thread.currentThread().getStackTrace();
		String strOutputString = timeStamp() + " - " + a_strLogLevel + " - " + a_strLogNote +" : "+ oStackTraceElement[CALLER] + "\r\n";
		
		if(isLogFile())
		{
			try
			{
				synchronized(m_oFile)
				{
					oFileStream = new FileOutputStream(m_oFile, true);
					oFileStream.write(strOutputString.getBytes(), 0, strOutputString.length());
				}
			}
			catch (FileNotFoundException e)
			{
				return;
			} 
			catch (IOException e)
			{
				return;
			}
		}
		
	}
}

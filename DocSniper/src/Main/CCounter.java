package Main;

public class CCounter 
{
	private int m_iCounter;
	
	public CCounter()
	{
		m_iCounter = 0;
	}
	
	public int counterStatus()
	{
			return m_iCounter;
	}
	
	public void increment()
	{
		m_iCounter++;
	}
	
	public void decrement()
	{
		m_iCounter--;
	}
}

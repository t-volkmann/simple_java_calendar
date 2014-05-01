package assignment_4;

/**
 * Class structure to hold the necessary information to create an event
 * for the calendar
 * 
 * @author travis volkmann
 *
 */
public class EventInfo 
{

		int m;
		int d;
		int y;
		String text;
		String start;
		String end;
		
		public EventInfo(int a, int b, int c, String s, String t1, String t2)
		{
			m = a;
			d = b;
			y = c;
			
			start = t1;
			end = t2;
			
			text = s;
		}
		
		public void setText(String s)
		{
			text = s;
		}
		
		public String getText()
		{
			return text;
		}
		
		public int gDay()
		{ return d; }
		
		public int gMonth()
		{ return m; }
		
		public int gYear()
		{ return y; }
		
		public String gStart()
		{ return start; }
		
		public String gEnd()
		{ return end; }
}
package assignment_4;

import java.util.Calendar;

/**
 * Holds information about the calendar that is being displayed,
 * like the current day being viewed, etc.
 * 
 * @author travis volkmann
 *
 */
public class CalendarData 
{
	private int day;
	private int month;
	private int year;
	private Calendar cal;
	private int maxDays;
	private int startDay;
	
	/**
	 * Default constructor that builds a calendar for the current date in real life
	 */
	public CalendarData()
	{
		
		cal = Calendar.getInstance();
		year = cal.get(cal.YEAR);
		month = (cal.get(cal.MONTH));
		day = cal.get(cal.DATE);
		
		
		maxDays = cal.getActualMaximum(cal.DAY_OF_MONTH); //maximum days in month
		//System.out.println("Max " + maxDays );
		startDay = cal.get(cal.DAY_OF_WEEK_IN_MONTH); // day of week month starts on
	}
	

	
	/**
	 * 
	 * @return the month being viewed
	 */
	public int getMonth()
	{
		return month;
	}
	
	/**
	 * 
	 * @return the current year viewed
	 */
	public int getYear()
	{
		return year;
	}
	
	/**
	 * 
	 * @return the current day
	 */
	public int getDay()
	{
		return day;
	}
	
	/**
	 * 
	 * @return the max num of days in month
	 */
	public int getMax()
	{
		return maxDays;
	}
	
	/**
	 * 
	 * @return the day the week starts on
	 */
	public int getStart()
	{
		return startDay;
	}
	
	/**
	 * Sets the day the calendar is currently on
	 * @param d value for the day
	 */
	public void setDay(int d)
	{
		day = d;
		//System.out.println("Day is now " + day);
		if (d < 1)
		{
			changeCal(d);
		}
		
		else if (d > maxDays)
		{
			changeCal(d);
		}
	}
	
	/**
	 * Changes the calendar to the next or previous month
	 * @param x to tell whether to move forward or backwards a month
	 */
	public void changeCal(int x)
	{
		//System.out.println("New monthh");
		if (x < 1)
		{
			cal.set(year, month-1, 1);
			day = cal.getActualMaximum(cal.DAY_OF_MONTH);
			month = cal.get(cal.MONTH);
			year = cal.get(cal.YEAR);
			
			maxDays = cal.getActualMaximum(cal.DAY_OF_MONTH); //maximum days in month

			startDay = cal.get(cal.DAY_OF_WEEK); // day of week month starts on
			//System.out.println("start " + startDay );
		}
		else
		{
			cal.set(year, month+1, 1);
			day = cal.get(cal.DATE);
			month = cal.get(cal.MONTH);
			year = cal.get(cal.YEAR);
			
			maxDays = cal.getActualMaximum(cal.DAY_OF_MONTH); //maximum days in month
		//	System.out.println("Max " + maxDays );
			startDay = cal.get(cal.DAY_OF_WEEK); // day of week month starts on
			
			
		}
	}
}

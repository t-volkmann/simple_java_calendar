package assignment_4;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Builds all the frames and panels for displaying the main part of the calendar
 * and allows the user to interact with them.
 * 
 * @author travis volkmann
 *
 */
public class CalendarDisplay //implements ChangeListener
{
	private JPanel panel; 
	private JButton eventCreate;
	private JButton arrow1;
	private JButton arrow2;
	private JLabel calLabel;
	private JLabel daysOfWeek;
	private JPanel calDays;
	private JLabel eventInfo;
	private JPanel dayView;
	
	private JButton dateB[];
	
	private int currDate;
	private int daysInMonth;
	private int dayOfWeek;
	
	private CalendarData cal;
	private CreateEvent calEvents;
	
	/**
	 * Constructs a calendar with all the dates in the month inside a of a JFrame that allows
	 * the user to navigate through a calendar adding events as they wish
	 * 
	 * @param t Name of the calendar that displays on the JFrame
	 */
	public CalendarDisplay(String t)
	{
		//JPanel panel = new JPanel();
		panel = new JPanel();
		panel.setLayout(null);
		
		cal = new CalendarData();
		
		//int month = cal.getMonth();
		//int yr = cal.getYear();
		daysInMonth = cal.getMax();
		dayOfWeek = cal.getStart();
		--dayOfWeek;
		currDate = cal.getDay();
		
		// convert int to string of month
		//String sMonth = monthToString(month);
		
		// set calendar month/year && days of the week
		//calLabel = new JLabel(sMonth + " " + yr);
		calTitle();
		daysOfWeek = new JLabel("Su 	       Mo 	       Tu 	       We 	       Th 	       Fr 	       Sa");
		panel.add(calLabel);
		panel.add(daysOfWeek);
		
		// set location and size of calendar month/year label and days of the week
		//calLabel.setLocation(160, 10);
		//calLabel.setSize(100,20);
		daysOfWeek.setLocation(37,30);
		daysOfWeek.setSize(350,10);
		
		// create initial calendar for the current month
		calDays = new JPanel();
		calDays.setLayout(new GridLayout(0, 7));
		calDays.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		calDays.setLocation(25,45);
		calDays.setSize(350,90);
		
		// draw the calendar
		drawCal();
		panel.add(calDays);
		
		// create a panel for where events can be listed for a particular day
		dayView = new JPanel();
		dayView.setLayout(null);
		dayView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		dayView.setLocation(25,150);
		dayView.setSize(745,300);
		
		calEvents = new CreateEvent();
		eventView();
		panel.add(dayView);

		// adds the button to create events
		//JButton eventCreate = new JButton("Create");
		eventCreate = new JButton("Create");
		panel.add(eventCreate);
		eventCreate.setLocation(400,10);
		eventCreate.setSize(85,20);
		
		eventCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calEvents.newEvent(cal.getDay(), cal.getMonth(), cal.getYear());
				
			}
		});
		
		// arrows for incrementing and decrementing the day by 1
		arrowKeys();
		
		// create frame for calendar
		final JFrame frame = new JFrame(t);
		frame.add(panel);
		
		frame.setSize(800,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void calTitle()
	{

		// set calendar month/year && days of the week
		String sMonth = monthToString(cal.getMonth());
		calLabel = new JLabel(sMonth + " " + cal.getYear());
		panel.add(calLabel);
		//daysOfWeek = new JLabel("Su 	       Mo 	       Tu 	       We 	       Th 	       Fr 	       Sa");
		calLabel.setLocation(160, 10);
		calLabel.setSize(200,20);
	}
	/**
	 * Buttons that increment and decrement through the calendar by one day at a time
	 * 
	 */
	public void arrowKeys()
	{
		// adds the increment and decrement button for calendar
		//JButton arrow1 = new JButton("<");
		//JButton arrow2 = new JButton(">");
		arrow1 = new JButton("<");
		arrow2 = new JButton(">");
	
		arrow1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeDate(cal.getDay()-1);
				calDays.removeAll();
				calDays.revalidate();
				
				drawCal();
							
				dayView.removeAll();
				eventView();
			}
		});
		
		arrow2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeDate(cal.getDay()+1);
				calDays.removeAll();
				calDays.revalidate();
				
				drawCal();
							
				dayView.removeAll();
				eventView();
			}
		});
		
		panel.add(arrow1);
		panel.add(arrow2);
		arrow1.setLocation(500,10);
		arrow2.setLocation(565,10);
		arrow1.setSize(60,20);
		arrow2.setSize(60,20);
		
	}
	
	/**
	 * Draws the calendar with all the buttons for a specified month
	 */
	private void drawCal()
	{
		int count = 0;
		int j = 0;
		int max = cal.getMax()+(cal.getStart()-1);
	
		dateB = new JButton[max];
		for ( int i = 0; i < max; i++)
		{
			// if it starts on sunday
			if (dayOfWeek == 0)
			{
				j = i+1;
				final int x = j;
				final String label = "" + (j);
				dateB[i] = new JButton(label);
				setBorder(dateB[i], false);
				dateB[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						changeDate(x);
						
						// test output
						//System.out.println("Date is now " + currDate);
						
						setBorder((JButton)e.getSource(), true);
						
						calDays.removeAll();
						calDays.revalidate();
						drawCal();
						
						dayView.removeAll();
						eventView();
					}
				});
			
				if (j == currDate)
				{
					setBorder(dateB[i], true);
				}
				calDays.add(dateB[i]);
			}
			
			//doesn't start on 1st of week so draws blank buttons
			else
			{
				//System.out.println("Start = " + (cal.getStart()-1));
				if (count < (cal.getStart()-1))
				{
					JButton blank = new JButton();
					setBorder(blank,false);
					calDays.add(blank);
					count++;
				}
				
				else
				{
					j = i+1-dayOfWeek;
					final int x = j;
					final String label = "" + (j);
					dateB[i] = new JButton(label);
					setBorder(dateB[i], false);
			
					
					if (j == currDate)
					{
						//System.out.println("C: " + currDate);
						setBorder(dateB[i], true);						
					}
					
					
					dateB[i].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							changeDate(x);
							
							// test output
							System.out.println("Date is now " + currDate);
							
							setBorder((JButton)e.getSource(), true);
							
							calDays.removeAll();
							calDays.revalidate();
							drawCal();
							
							dayView.removeAll();
							eventView();
						}
					});
					calDays.add(dateB[i]);
				}				
			}
			
		}	//end of for loop
		
	}
	
	/**
	 * Creates the area that lists events and also lists any events for the selected date
	 */
	public void eventView()
	{
		eventInfo = new JLabel("Schedule for " + (cal.getMonth()+1) + "/" + (cal.getDay()));  // change format to Day_of_week month(int)/day
		
		dayView.add(eventInfo);
		eventInfo.setLocation(10,0);
		eventInfo.setSize(150,34);		
		
		EventInfo found = calEvents.searchEvents(cal.getDay(), cal.getMonth(), cal.getYear());
		if (found != null)
		{
			JLabel events = new JLabel("<html> Start: " + found.gStart() + "  End: " + found.gEnd() + "<br>" + found.getText() + "</html>");
			events.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			dayView.add(events);
				events.setLocation(10,30);
				events.setSize(550,50);
		}
		
		else
		{
			dayView.repaint();
		}
	}
	
	/**
	 * Sets a border for a button
	 * @param bt button that will have a border drawn on it
	 * @param borderState if true then draw border, false don't draw border
	 */
	public void setBorder(JButton bt, boolean borderState)
	{
		if(borderState)
		{
			bt.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
		
		else
		{
			bt.setBorder(BorderFactory.createEmptyBorder());
			bt.setOpaque(false);
			bt.setContentAreaFilled(false);
		}
	}
	
	/**
	 * Changes the date that is selected
	 * @param x represents the new date
	 */
	public void changeDate(int x)
	{
		currDate = x;
		cal.setDay(x);
		if (x < 1)
		{
			
			daysInMonth = cal.getMax();
			dayOfWeek = cal.getStart();
			--dayOfWeek;
			currDate = cal.getDay();
			
			calDays.removeAll();
			calDays.revalidate();
			calDays.repaint();
	 
			drawCal();
		
			panel.remove(calLabel);
			calTitle();
		}
		
		else if (x >= cal.getMax())
		{			
			daysInMonth = cal.getMax();
			dayOfWeek = cal.getStart();
			--dayOfWeek;
			currDate = cal.getDay();
			
			calDays.removeAll();
			calDays.revalidate();
			calDays.repaint();
			
			panel.remove(calLabel);
			
			calTitle();
			
			drawCal();
		}
	}
	
	/**
	 * Converts an int to the correct month in string format
	 * @param m a number representing a month. Jan = 0
	 * @return A string to represent the month
	 */
	public String monthToString(int m)
	{
		
		if (m == 0)
			return "January";
		if (m == 1)
			return "February";
		if (m == 2)
			return "March";
		if (m == 3)
			return "April";
		if (m == 4)
			return "May";
		if (m == 5)
			return "June";
		if (m == 6)
			return "July";
		if (m == 7)
			return "August";
		if (m == 8)
			return "September";
		if (m == 9)
			return "October";
		if (m == 10)
			return "November";
		if (m == 11)
			return "December";
		
		return "-1";
	}
}

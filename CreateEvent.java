package assignment_4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;


/**
 * Creates an event that the user creates that is put into an arraylist
 * that is then searchable
 * 
 * @author travis volkmann
 *
 */
public class CreateEvent
{
	private JButton save;
	private JFrame frame;
	private JPanel panel;
	private String[] times = { "12:00AM", "12:15AM", "12:30AM", "12:45AM", "1:00AM", "1:15AM", "1:30AM", "1:45AM", 
							   "2:00AM", "2:15AM", "2:30AM", "2:45AM", "3:00AM", "3:15AM", "3:30AM", "3:45AM",
							   "4:00AM", "4:15AM", "4:30AM", "4:45AM","5:00AM", "5:15AM", "5:30AM", "5:45AM",
							   "6:00AM", "6:15AM", "6:30AM", "6:45AM","7:00AM", "7:15AM", "7:30AM", "7:45AM",
							   "8:00AM", "8:15AM", "8:30AM", "8:45AM","9:00AM", "9:15AM", "9:30AM", "9:45AM",
							   "10:00AM", "10:15AM", "10:30AM", "10:45AM", "11:00AM", "11:15AM", "11:30AM", "11:45AM", 
							   "12:00PM", "12:15PM", "12:30PM", "12:45PM", "1:00PM", "1:15PM", "1:30PM", "1:45PM",
							   "2:00PM", "2:15PM", "2:30PM", "2:45PM","3:00PM", "3:15PM", "3:30PM", "3:45PM",
							   "4:00PM", "4:15PM", "4:30PM", "4:45PM","5:00PM", "5:15PM", "5:30PM", "5:45PM",
							   "6:00PM", "6:15PM", "6:30PM", "6:45PM","7:00PM", "7:15PM", "7:30PM", "7:45PM",
							   "8:00PM", "8:15PM", "8:30PM", "8:45PM","9:00PM", "9:15PM", "9:30PM", "9:45PM",
							   "10:00PM", "10:15PM", "10:30PM", "10:45PM","11:00PM", "11:15PM", "11:30PM", "11:45PM",};
	
	private JTextArea date;
	private JTextPane text;
	
	private ArrayList<EventInfo> events;
	
	private int day;
	private int month;
	private int year;
	
	private int count = 1;
	
	private JComboBox<String> endTime;
	private JComboBox<String> startTime;
	
	private String eT;
	private String sT;
	
	/**
	 * Default constructor that initializes the arraylist
	 */
	public CreateEvent()
	{
		
		events = new ArrayList<>();
		events.add(new EventInfo(0,0,0,"DUMMY DATA", "", ""));

	}

	/**
	 * Creates a new event for a specified day, month and year
	 * @param d day
	 * @param m month
	 * @param y year
	 */
	public void newEvent(int d, int m, int y)
	{
		frame = new JFrame("Create Event");
		frame.setLayout(new BorderLayout());
		frame.setSize(300, 150);
		
		day = d;
		month = m;
		year = y;
		
	    text = new JTextPane();
		//text.setSize(250, 200);
		text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		

		
		date = new JTextArea(1,5);
		date.setBorder(BorderFactory.createLineBorder(Color.black));
		date.insert((m+1) + "/" + d + "/" + y, 0);
		
		startTime = new JComboBox<>(times);
		startTime.setSelectedIndex(0);
	
		
		endTime = new JComboBox<>(times);
		endTime.setSelectedIndex(1);
		
		
		saveButton();
		
		frame.add(text, BorderLayout.NORTH);
		frame.add(save, BorderLayout.SOUTH);
		frame.add(date, BorderLayout.WEST);
		frame.add(startTime, BorderLayout.CENTER);
		frame.add(endTime, BorderLayout.EAST);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Create a save button for the frame
	 */
	private void saveButton()
	{
		save = new JButton("Save");
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				String temp = text.getText();
				eT = (String) endTime.getSelectedItem();
				sT = (String) startTime.getSelectedItem();
				
				EventInfo ev = new EventInfo(month, day, year, temp, sT, eT);
				
				boolean noConflict = timeConflict(sT, eT);
				
				if (!noConflict)
				{
					events.add(ev);
					count++;
				}
				
				else
				{
					final JFrame warning = new JFrame();
					
					warning.setLayout(new FlowLayout());
					warning.setSize(200, 50);
					JButton error = new JButton("Error: Time Conflict");
					warning.add(error);
					warning.setVisible(true);
					error.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							warning.dispose();
							
						}
					});
					
					warning.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
								
				frame.dispose();						
			}
		});
	}
	

	/**
	 * 
	 * @return EventInfo arraylist
	 */
	public ArrayList<EventInfo> getArray()
	{
		return events;
	}
	
	/**
	 * Searches for matching events to display in task list
	 * @param d day
	 * @param m month
	 * @param y year
	 * @return a matching event if found or null if not found
	 */
	public EventInfo searchEvents(int d, int m, int y)
	{
		for(int i = 0; i < events.size(); i++)
		{
			if ( events.get(i).gYear() == y)
				if ( events.get(i).gMonth() == m)
					if( events.get(i).gDay() == d)
					{
						return events.get(i);
					}
		}
		
		return null;
	}
	
	/**
	 * Checks for time conflicts in creating events
	 * @param s start time
	 * @param e end time 
	 * @return true if found, false if not
	 */
	public boolean timeConflict(String s, String e)
	{
		for(int i = 0; i < events.size(); i++)
		{
			if (events.get(i).gStart().equals(s))
				return true;
			if (events.get(i).gEnd().equals(e))
				return true;			
		}
		
		return false;
	}
	
	/**
	 * Size of events
	 * @return num of events in calendar
	 */
	public int calSize()
	{ return count; }
}

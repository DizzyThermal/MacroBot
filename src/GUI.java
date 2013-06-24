import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener
{
	// [0] GUI Objects
	private Container container		= new Container();
	
	private JButton	bStart			= new JButton("Start");
	private JButton bStop			= new JButton("Stop");
	private JButton bExit			= new JButton("Exit");
	
	private JLabel jLInstructions	= new JLabel("Keys To Loop:");
	private JTextField jTFInput		= new JTextField();
	
	private JLabel jLStatus			= new JLabel("Not Running");
	
	// [1] Global Variables
	private Thread bot;
	
	public GUI()
	{
		super("MacroBot");
		setLayout(new BorderLayout());
		
		createPanel();

		setContentPane(container);
	}
	
	public void createPanel()
	{
		// Action Listeners for Button Clicks
		bStart.addActionListener(this);
		bStop.addActionListener(this);
		bExit.addActionListener(this);
				
		// Change Dimensions
		bStart.setPreferredSize(new Dimension(100, 25));
		bStop.setPreferredSize(new Dimension(100, 25));
		bExit.setPreferredSize(new Dimension(100, 25));
		jTFInput.setPreferredSize(new Dimension(340, 25));
		jLInstructions.setPreferredSize(new Dimension(340, 25));
		jLStatus.setPreferredSize(new Dimension(340, 75));
		
		jLStatus.setFont(new Font("Arial", Font.ITALIC, 50));
		
		Insets insets = getContentPane().getInsets();
		Dimension size;
		
		size = bStart.getPreferredSize();
		bStart.setBounds(10 + insets.left, 230 + insets.top, size.width, size.height);
		
		size = bStop.getPreferredSize();
		bStop.setBounds(125 + insets.left, 230 + insets.top, size.width, size.height);
		
		size = bExit.getPreferredSize();
		bExit.setBounds(240 + insets.left, 230 + insets.top, size.width, size.height);
		
		size = jTFInput.getPreferredSize();
		jTFInput.setBounds(10 + insets.left, 30 + insets.top, size.width, size.height);
		
		size = jLInstructions.getPreferredSize();
		jLInstructions.setBounds(10 + insets.left, 5 + insets.top, size.width, size.height);
		
		size = jLStatus.getPreferredSize();
		jLStatus.setBounds(10 + insets.left, 60 + insets.top, size.width, size.height);

		container.add(bStart);
		container.add(bStop);
		container.add(bExit);
		container.add(jTFInput);
		container.add(jLInstructions);
		container.add(jLStatus);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == bStart)
		{
			Robot robot = null;
			try
			{
				robot = new Robot();
			}
			catch (AWTException awte) { awte.printStackTrace(); }

			if(robot != null)
			{
				jLStatus.setText("Running");
				bot = (new Thread()
				{
					@Override
					public void run()
					{
						while(true)
						{
							
						}
					}
				});
				bot.start();
			}
		}
		else if(e.getSource() == bStop)
		{
			jLStatus.setText("Not Running");
			if(bot.isAlive())
				bot.stop();
		}
		else if(e.getSource() == bExit)
		{
			if(bot.isAlive())
				bot.stop();

			System.exit(0);
		}
	}
}
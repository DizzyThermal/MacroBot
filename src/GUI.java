import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
	private RobotThread rThread = null;
	
	public GUI()
	{
		super("MacroBot");
		setLayout(new BorderLayout());
		
		createPanel();

		setContentPane(container);
		
		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing( WindowEvent e )
		    {
		    	if(rThread != null)
		    		rThread.shutdown();
		    	
		    	System.exit(0);
		    }
		}); 
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
		bStop.setEnabled(false);
		
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
			jLStatus.setText("Running");
			bStart.setEnabled(false);
			bStop.setEnabled(true);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			rThread = new RobotThread(jTFInput.getText());
			rThread.start();
		}
		else if(e.getSource() == bStop)
		{
			jLStatus.setText("Not Running");
			bStart.setEnabled(true);
			bStop.setEnabled(false);
			if(rThread != null)
				rThread.shutdown();
		}
		else if(e.getSource() == bExit)
		{
			if(rThread != null)
				rThread.shutdown();
			System.exit(0);
		}
	}
}
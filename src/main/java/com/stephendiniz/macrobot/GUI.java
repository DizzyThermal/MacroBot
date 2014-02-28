package com.stephendiniz.macrobot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame implements ActionListener
{
	// [0] GUI Objects
	private Container container		= new Container();
	
	private JButton	bStart			= new JButton("Start");
	private JButton bStop			= new JButton("Stop");
	private JButton bExit			= new JButton("Exit");
	
	private JLabel jLInstructions	= new JLabel("Keys To Loop:");
	private JTextField jTFInput		= new JTextField();
	
	private JLabel jLDelay			= new JLabel("Key Delay:");
	private JSpinner jSDelay		= new JSpinner(new SpinnerNumberModel(50, 1, 1000000, 1));
	
	private JLabel jLSDelay			= new JLabel("Start Delay:");
	private JSpinner jSSDelay		= new JSpinner(new SpinnerNumberModel(2500, 500, 1000000, 1));
	
	private JLabel jLStatus			= new JLabel("Not Running");
	
	// [1] Global Variables
	private RobotThread rThread = null;
	
	public GUI()
	{
		super("MacroBot");
		setLayout(new BorderLayout());
		
		createPanel(checkForHistory());

		setContentPane(container);
		
		this.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing( WindowEvent e )
		    {
		    	if(rThread != null)
		    		rThread.shutdown();
		    }
		}); 
	}
	
	private String checkForHistory()
	{
		try
		{
			if(new File("keyHistory.txt").exists())
			{
				BufferedReader bReader = new BufferedReader(new FileReader("keyHistory.txt"));
				
				String line = "";
				while((line = bReader.readLine()) != null)
				{
					if(line.equals("") || line == null)
						continue;
					else
						break;
				}
				
				bReader.close();
				
				return line;
			}
		}
		catch(IOException ioe) { ioe.printStackTrace(); }
		
		return null;
	}
	
	private void createPanel(String history)
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
		
		jSDelay.setPreferredSize(new Dimension(80, 25));
		jLDelay.setPreferredSize(new Dimension(100, 25));
		
		jSSDelay.setPreferredSize(new Dimension(80, 25));
		jLSDelay.setPreferredSize(new Dimension(100, 25));
		
		jLStatus.setPreferredSize(new Dimension(340, 75));
		
		jLStatus.setFont(new Font("Arial", Font.BOLD, 50));
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
		
		size = jSDelay.getPreferredSize();
		jSDelay.setBounds(269 + insets.left, 75 + insets.top, size.width, size.height);
		
		size = jLDelay.getPreferredSize();
		jLDelay.setBounds(269 + insets.left, 55 + insets.top, size.width, size.height);
		
		size = jSSDelay.getPreferredSize();
		jSSDelay.setBounds(10 + insets.left, 75 + insets.top, size.width, size.height);
		
		size = jLSDelay.getPreferredSize();
		jLSDelay.setBounds(10 + insets.left, 55 + insets.top, size.width, size.height);
		
		size = jLStatus.getPreferredSize();
		jLStatus.setBounds(10 + insets.left, 120 + insets.top, size.width, size.height);

		container.add(bStart);
		container.add(bStop);
		container.add(bExit);
		
		container.add(jTFInput);
		container.add(jLInstructions);
		
		container.add(jLStatus);
		
		container.add(jLDelay);
		container.add(jSDelay);
		
		container.add(jLSDelay);
		container.add(jSSDelay);
		
		if(history != null)
			jTFInput.setText(history);
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
				Thread.sleep((Integer)jSSDelay.getValue());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			rThread = new RobotThread(jTFInput.getText(), (Integer)jSDelay.getValue());
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

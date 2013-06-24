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
	
	private ArrayList<Integer> getKeyCodes(String input)
	{
		char[] chars = input.toCharArray();
		ArrayList<Integer> keys = new ArrayList<Integer>();
		
		for(int i = 0; i < chars.length; i++)
		{
			String inputStr = "";
			if(chars[i] == '[')
			{
				int start = i+1;
				int stop = start;
				while(chars[stop++] != ']');
				stop = stop-1;
				
				for(int j = 0; j < stop-start; j++)
					inputStr += chars[start++];
			}
			else
			{
				inputStr = Character.toString(chars[i]);
			}
			
			// Characters
			if(inputStr.equals("A") || inputStr.equals("a"))
				keys.add(KeyEvent.VK_A);
			else if(inputStr.equals("B") || inputStr.equals("b"))
				keys.add(KeyEvent.VK_B);
			else if(inputStr.equals("C") || inputStr.equals("c"))
				keys.add(KeyEvent.VK_C);
			else if(inputStr.equals("D") || inputStr.equals("d"))
				keys.add(KeyEvent.VK_D);
			else if(inputStr.equals("E") || inputStr.equals("e"))
				keys.add(KeyEvent.VK_E);
			else if(inputStr.equals("F") || inputStr.equals("f"))
				keys.add(KeyEvent.VK_F);
			else if(inputStr.equals("G") || inputStr.equals("g"))
				keys.add(KeyEvent.VK_G);
			else if(inputStr.equals("H") || inputStr.equals("h"))
				keys.add(KeyEvent.VK_H);
			else if(inputStr.equals("I") || inputStr.equals("i"))
				keys.add(KeyEvent.VK_I);
			else if(inputStr.equals("J") || inputStr.equals("j"))
				keys.add(KeyEvent.VK_J);
			else if(inputStr.equals("K") || inputStr.equals("k"))
				keys.add(KeyEvent.VK_K);
			else if(inputStr.equals("L") || inputStr.equals("l"))
				keys.add(KeyEvent.VK_L);
			else if(inputStr.equals("M") || inputStr.equals("m"))
				keys.add(KeyEvent.VK_M);
			else if(inputStr.equals("N") || inputStr.equals("n"))
				keys.add(KeyEvent.VK_N);
			else if(inputStr.equals("O") || inputStr.equals("o"))
				keys.add(KeyEvent.VK_O);
			else if(inputStr.equals("P") || inputStr.equals("p"))
				keys.add(KeyEvent.VK_P);
			else if(inputStr.equals("Q") || inputStr.equals("q"))
				keys.add(KeyEvent.VK_Q);
			else if(inputStr.equals("R") || inputStr.equals("r"))
				keys.add(KeyEvent.VK_R);
			else if(inputStr.equals("S") || inputStr.equals("s"))
				keys.add(KeyEvent.VK_S);
			else if(inputStr.equals("T") || inputStr.equals("t"))
				keys.add(KeyEvent.VK_T);
			else if(inputStr.equals("U") || inputStr.equals("u"))
				keys.add(KeyEvent.VK_U);
			else if(inputStr.equals("V") || inputStr.equals("v"))
				keys.add(KeyEvent.VK_V);
			else if(inputStr.equals("W") || inputStr.equals("w"))
				keys.add(KeyEvent.VK_W);
			else if(inputStr.equals("X") || inputStr.equals("x"))
				keys.add(KeyEvent.VK_X);
			else if(inputStr.equals("Y") || inputStr.equals("y"))
				keys.add(KeyEvent.VK_Y);
			else if(inputStr.equals("Z") || inputStr.equals("z"))
				keys.add(KeyEvent.VK_Z);
			
			// Numbers
			else if(inputStr.equals("0"))
				keys.add(KeyEvent.VK_0);
			else if(inputStr.equals("1"))
				keys.add(KeyEvent.VK_1);
			else if(inputStr.equals("2"))
				keys.add(KeyEvent.VK_2);
			else if(inputStr.equals("3"))
				keys.add(KeyEvent.VK_3);
			else if(inputStr.equals("4"))
				keys.add(KeyEvent.VK_4);
			else if(inputStr.equals("5"))
				keys.add(KeyEvent.VK_5);
			else if(inputStr.equals("6"))
				keys.add(KeyEvent.VK_6);
			else if(inputStr.equals("7"))
				keys.add(KeyEvent.VK_7);
			else if(inputStr.equals("8"))
				keys.add(KeyEvent.VK_8);
			else if(inputStr.equals("9"))
				keys.add(KeyEvent.VK_9);
			
			// Number Alts
			else if(inputStr.equals("!"))
				keys.add(KeyEvent.VK_EXCLAMATION_MARK);
			else if(inputStr.equals("@"))
				keys.add(KeyEvent.VK_AT);
			else if(inputStr.equals("#"))
				keys.add(KeyEvent.VK_NUMBER_SIGN);
			else if(inputStr.equals("$"))
				keys.add(KeyEvent.VK_DOLLAR);
			//else if(inputStr.equals("%"))
			//	keys.add(KeyEvent.VK_);
			else if(inputStr.equals("^"))
				keys.add(KeyEvent.VK_CIRCUMFLEX);
			else if(inputStr.equals("&"))
				keys.add(KeyEvent.VK_AMPERSAND);
			else if(inputStr.equals("*"))
				keys.add(KeyEvent.VK_ASTERISK);
			else if(inputStr.equals("("))
				keys.add(KeyEvent.VK_LEFT_PARENTHESIS);
			else if(inputStr.equals(")"))
				keys.add(KeyEvent.VK_RIGHT_PARENTHESIS);
			
			// Keys
			else if(inputStr.toLowerCase().equals("esc"))
				keys.add(KeyEvent.VK_ESCAPE);
			else if(inputStr.toLowerCase().equals("space"))
				keys.add(KeyEvent.VK_SPACE);
			else if(inputStr.toLowerCase().equals("enter"))
				keys.add(KeyEvent.VK_ENTER);
			else if(inputStr.toLowerCase().equals("pageup"))
				keys.add(KeyEvent.VK_PAGE_UP);
			else if(inputStr.toLowerCase().equals("pagedown"))
				keys.add(KeyEvent.VK_PAGE_DOWN);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			
			
			else if(inputStr.toLowerCase().equals("alt"))
				keys.add(KeyEvent.VK_ALT);
			else if(inputStr.toLowerCase().equals("&"))
				keys.add(KeyEvent.VK_AMPERSAND);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
			else if(inputStr.toLowerCase().equals("+"))
				keys.add(KeyEvent.VK_ADD);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == bStart)
		{
			jLStatus.setText("Running");
			bStart.setEnabled(false);
			bStop.setEnabled(true);
			bot = (new Thread()
			{
				@Override
				public void run()
				{
					String input = jTFInput.getText();
					if(input.length() == 0)
					{
						bStart.setEnabled(true);
						bStop.setEnabled(false);
						jLStatus.setText("No Keys Entered!");
						this.stop();
					}
					ArrayList<Integer> keys = getKeyCodes(input);
					
					while(true)
					{
						try
						{
							Robot robot = new Robot();
							robot.setAutoDelay(200);
				            for(int i = 0; i < keys.size(); i++)
				            	robot.keyPress(keys.get(i));
						}
						catch (AWTException awte)
						{
							awte.printStackTrace();
							bStart.setEnabled(true);
							bStop.setEnabled(false);
							jLStatus.setText("Error Occured!");
						}
					}
				}
			});
			bot.start();
		}
		else if(e.getSource() == bStop)
		{
			jLStatus.setText("Not Running");
			bStart.setEnabled(true);
			bStop.setEnabled(false);
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
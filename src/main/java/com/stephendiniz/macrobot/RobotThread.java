package com.stephendiniz.macrobot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RobotThread extends Thread
{
	private Robot robot;
	private String commandString;
	private int delay;
	private boolean running;
	private ArrayList<Integer> keys;
	private boolean intenseShutdown = false;
	
	private int SHIFT	= -1;
	private int MULTI	= -2;
	
    public RobotThread(String commandString, int delay)
    {
    	this.commandString = commandString;
    	this.delay = delay;

        try
        {
            this.robot = new Robot();
        }
        catch (AWTException e) { e.printStackTrace(); }
    }

    public void run()
    {
    	running = true;
		keys = getKeyCodes(commandString);
		
		while(running)
		{
			robot.setAutoDelay(delay);
			for(int i = 0; i < keys.size(); i++)
			{
				if(keys.get(i) == SHIFT)
				{
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(keys.get(i+1));
					robot.keyRelease(keys.get(i+1));
					robot.keyRelease(KeyEvent.VK_SHIFT);
					
					i++;
				}
				else
				{
					robot.keyPress(keys.get(i));
					robot.keyRelease(keys.get(i));
				}
			}
		}
    }
    
    public void shutdown()
    {
    	running = false;
    	releaseAllKeys();
    	if(keys != null)
    		keys.clear();
    	writeCommandString();
    	robot = null;
    	this.stop();
    }
    
    private void writeCommandString()
    {
    	try
    	{
	    	File keyHistory = new File("keyHistory.txt");
	    	if(!keyHistory.exists())
	    		keyHistory.createNewFile();
	    	
    		BufferedWriter bWriter = new BufferedWriter(new FileWriter("keyHistory.txt"));

    		bWriter.write(commandString);
    		bWriter.close();
    	}
    	catch(IOException ioe) { ioe.printStackTrace(); }
    }
    
    private void releaseAllKeys()
    {
    	if(robot == null)
    		return;

    	if(!intenseShutdown)
    	{
    		while(keys.size() > 0)
    		{
    			robot.keyRelease(keys.get(0));
    			keys.remove(0);
    		}
    	}
    	else
    	{
	    	// Release 0-9
	    	for(int i = 48; i < 57; i++)
	    		robot.keyRelease(i);
	    	
	    	// Release A-Z
	    	for(int i = 65; i < 90; i++)
	    		robot.keyRelease(i);
	    	
	    	robot.keyRelease(18);
	    	robot.keyRelease(192);
	    	robot.keyRelease(92);
	    	robot.keyRelease(8);
	    	robot.keyRelease(20);
	    	robot.keyRelease(93);
	    	robot.keyRelease(44);
	    	robot.keyRelease(17);
	    	robot.keyRelease(127);
	    	robot.keyRelease(40);
	    	robot.keyRelease(61);
	    	robot.keyRelease(35);
	    	robot.keyRelease(112);
	    	robot.keyRelease(113);
	    	robot.keyRelease(114);
	    	robot.keyRelease(115);
	    	robot.keyRelease(116);
	    	robot.keyRelease(117);
	    	robot.keyRelease(118);
	    	robot.keyRelease(119);
	    	robot.keyRelease(120);
	    	robot.keyRelease(121);
	    	robot.keyRelease(122);
	    	robot.keyRelease(123);
	    	robot.keyRelease(36);
	    	robot.keyRelease(155);
	    	robot.keyRelease(37);
	    	robot.keyRelease(45);
	    	robot.keyRelease(144);
	    	robot.keyRelease(91);
	    	robot.keyRelease(34);
	    	robot.keyRelease(33);
	    	robot.keyRelease(46);
	    	robot.keyRelease(154);
	    	robot.keyRelease(222);
	    	robot.keyRelease(39);
	    	robot.keyRelease(145);
	    	robot.keyRelease(59);
	    	robot.keyRelease(16);
	    	robot.keyRelease(47);
	    	robot.keyRelease(32);
	    	robot.keyRelease(9);
	    	robot.keyRelease(38);
	    	robot.keyRelease(524);
    	}
    }
    
	private ArrayList<Integer> getKeyCodes(String input)
	{
		char[] chars = input.toCharArray();
		ArrayList<Integer> keys = new ArrayList<Integer>();
		
		for(int i = 0; i < chars.length; i++)
		{
			String inputStr = "";
			int multi = 1;
			boolean multiple = false;
			if(chars[i] == '[')
			{
				int start = i;
				int stop = start;
				while(chars[stop] != ']')
					stop++;
				stop = (stop-1);
				
				int counter = start+1;
				for(int j = 0; j < stop-start; j++)
					inputStr += chars[counter++];
				
				i = (stop+1);
				
				if(inputStr.equals(">"))
					inputStr = "right";
				else if(inputStr.equals("<"))
					inputStr = "left";
				else if(inputStr.equals("^"))
					inputStr = "up";
				else if(inputStr.toLowerCase().equals("v"))
					inputStr = "down";
			}
			else if(chars[i] == '{')
			{
				int start = i;
				int stop = start;
				while(chars[stop] != '}')
					stop++;
				stop = (stop-1);
				
				int counter = start+1;
				for(int j = 0; j < stop-start; j++)
					inputStr += chars[counter++];
				
				i = (stop+1);
				
				multi = Math.abs(Integer.parseInt(inputStr));
				multiple = true;
			}
			else
				inputStr = Character.toString(chars[i]);
			
			if(!multiple)
			{
				// Characters
				if(inputStr.equals("a"))
					keys.add(KeyEvent.VK_A);
				else if(inputStr.equals("b"))
					keys.add(KeyEvent.VK_B);
				else if(inputStr.equals("c"))
					keys.add(KeyEvent.VK_C);
				else if(inputStr.equals("d"))
					keys.add(KeyEvent.VK_D);
				else if(inputStr.equals("e"))
					keys.add(KeyEvent.VK_E);
				else if(inputStr.equals("f"))
					keys.add(KeyEvent.VK_F);
				else if(inputStr.equals("g"))
					keys.add(KeyEvent.VK_G);
				else if(inputStr.equals("h"))
					keys.add(KeyEvent.VK_H);
				else if(inputStr.equals("i"))
					keys.add(KeyEvent.VK_I);
				else if(inputStr.equals("j"))
					keys.add(KeyEvent.VK_J);
				else if(inputStr.equals("k"))
					keys.add(KeyEvent.VK_K);
				else if(inputStr.equals("l"))
					keys.add(KeyEvent.VK_L);
				else if(inputStr.equals("m"))
					keys.add(KeyEvent.VK_M);
				else if(inputStr.equals("n"))
					keys.add(KeyEvent.VK_N);
				else if(inputStr.equals("o"))
					keys.add(KeyEvent.VK_O);
				else if(inputStr.equals("p"))
					keys.add(KeyEvent.VK_P);
				else if(inputStr.equals("q"))
					keys.add(KeyEvent.VK_Q);
				else if(inputStr.equals("r"))
					keys.add(KeyEvent.VK_R);
				else if(inputStr.equals("s"))
					keys.add(KeyEvent.VK_S);
				else if(inputStr.equals("t"))
					keys.add(KeyEvent.VK_T);
				else if(inputStr.equals("u"))
					keys.add(KeyEvent.VK_U);
				else if(inputStr.equals("v"))
					keys.add(KeyEvent.VK_V);
				else if(inputStr.equals("w"))
					keys.add(KeyEvent.VK_W);
				else if(inputStr.equals("x"))
					keys.add(KeyEvent.VK_X);
				else if(inputStr.equals("y"))
					keys.add(KeyEvent.VK_Y);
				else if(inputStr.equals("z"))
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
				
				// Other Un-Shifted Keys
				else if(inputStr.toLowerCase().equals("-"))
					keys.add(KeyEvent.VK_MINUS);
				else if(inputStr.toLowerCase().equals("="))
					keys.add(KeyEvent.VK_EQUALS);
				else if(inputStr.toLowerCase().equals("openbracket"))
					keys.add(KeyEvent.VK_OPEN_BRACKET);
				else if(inputStr.toLowerCase().equals("closebracket"))
					keys.add(KeyEvent.VK_CLOSE_BRACKET);
				else if(inputStr.toLowerCase().equals("\\"))
					keys.add(KeyEvent.VK_BACK_SLASH);
				else if(inputStr.toLowerCase().equals(";"))
					keys.add(KeyEvent.VK_SEMICOLON);
				else if(inputStr.toLowerCase().equals("'"))
					keys.add(KeyEvent.VK_QUOTE);
				else if(inputStr.toLowerCase().equals(","))
					keys.add(KeyEvent.VK_COMMA);
				else if(inputStr.toLowerCase().equals("."))
					keys.add(KeyEvent.VK_PERIOD);
				else if(inputStr.toLowerCase().equals("/"))
					keys.add(KeyEvent.VK_SLASH);
				else if(inputStr.toLowerCase().equals("shift"))
					keys.add(KeyEvent.VK_SHIFT);
				else if(inputStr.toLowerCase().equals("tab"))
					keys.add(KeyEvent.VK_TAB);
				else if(inputStr.toLowerCase().equals("capslock"))
					keys.add(KeyEvent.VK_CAPS_LOCK);
				else if(inputStr.toLowerCase().equals("alt"))
					keys.add(KeyEvent.VK_ALT);
				else if(inputStr.toLowerCase().equals("windowskey"))
					keys.add(KeyEvent.VK_WINDOWS);
				else if(inputStr.toLowerCase().equals("space") || inputStr.toLowerCase().equals(" "))
					keys.add(KeyEvent.VK_SPACE);
				else if(inputStr.toLowerCase().equals("enter"))
					keys.add(KeyEvent.VK_ENTER);
				else if(inputStr.toLowerCase().equals("up"))
					keys.add(KeyEvent.VK_UP);
				else if(inputStr.toLowerCase().equals("left"))
					keys.add(KeyEvent.VK_LEFT);
				else if(inputStr.toLowerCase().equals("right"))
					keys.add(KeyEvent.VK_RIGHT);
				else if(inputStr.toLowerCase().equals("down"))
					keys.add(KeyEvent.VK_DOWN);
				else if(inputStr.toLowerCase().equals("insert"))
					keys.add(KeyEvent.VK_INSERT);
				else if(inputStr.toLowerCase().equals("home"))
					keys.add(KeyEvent.VK_HOME);
				else if(inputStr.toLowerCase().equals("pageup"))
					keys.add(KeyEvent.VK_PAGE_UP);
				else if(inputStr.toLowerCase().equals("delete"))
					keys.add(KeyEvent.VK_DELETE);
				else if(inputStr.toLowerCase().equals("end"))
					keys.add(KeyEvent.VK_END);
				else if(inputStr.toLowerCase().equals("pagedown"))
					keys.add(KeyEvent.VK_PAGE_DOWN);
				else if(inputStr.toLowerCase().equals("printscreen"))
					keys.add(KeyEvent.VK_PRINTSCREEN);
				else if(inputStr.toLowerCase().equals("scrolllock"))
					keys.add(KeyEvent.VK_SCROLL_LOCK);
				else if(inputStr.toLowerCase().equals("numlock"))
					keys.add(KeyEvent.VK_NUM_LOCK);
				else if(inputStr.toLowerCase().equals("backspace"))
					keys.add(KeyEvent.VK_BACK_SPACE);
				else if(inputStr.toLowerCase().equals("`"))
					keys.add(KeyEvent.VK_BACK_QUOTE);
				
				// Function Keys
				else if(inputStr.toLowerCase().equals("f1"))
					keys.add(KeyEvent.VK_F1);
				else if(inputStr.toLowerCase().equals("f2"))
					keys.add(KeyEvent.VK_F2);
				else if(inputStr.toLowerCase().equals("f3"))
					keys.add(KeyEvent.VK_F3);
				else if(inputStr.toLowerCase().equals("f4"))
					keys.add(KeyEvent.VK_F4);
				else if(inputStr.toLowerCase().equals("f5"))
					keys.add(KeyEvent.VK_F5);
				else if(inputStr.toLowerCase().equals("f6"))
					keys.add(KeyEvent.VK_F6);
				else if(inputStr.toLowerCase().equals("f7"))
					keys.add(KeyEvent.VK_F7);
				else if(inputStr.toLowerCase().equals("f8"))
					keys.add(KeyEvent.VK_F8);
				else if(inputStr.toLowerCase().equals("f9"))
					keys.add(KeyEvent.VK_F9);
				else if(inputStr.toLowerCase().equals("f10"))
					keys.add(KeyEvent.VK_F10);
				else if(inputStr.toLowerCase().equals("f11"))
					keys.add(KeyEvent.VK_F11);
				else if(inputStr.toLowerCase().equals("f12"))
					keys.add(KeyEvent.VK_F12);
				
				//// Shifted Characters ////
				
				// Letters
				else if(inputStr.equals("A"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_A);
				}
				else if(inputStr.equals("B"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_B);
				}
				else if(inputStr.equals("C"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_C);
				}
				else if(inputStr.equals("D"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_D);
				}
				else if(inputStr.equals("E"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_E);
				}
				else if(inputStr.equals("F"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_F);
				}
				else if(inputStr.equals("G"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_G);
				}
				else if(inputStr.equals("H"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_H);
				}
				else if(inputStr.equals("I"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_I);
				}
				else if(inputStr.equals("J"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_J);
				}
				else if(inputStr.equals("K"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_K);
				}
				else if(inputStr.equals("L"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_L);
				}
				else if(inputStr.equals("M"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_M);
				}
				else if(inputStr.equals("N"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_N);
				}
				else if(inputStr.equals("O"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_O);
				}
				else if(inputStr.equals("P"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_P);
				}
				else if(inputStr.equals("Q"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_Q);
				}
				else if(inputStr.equals("R"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_R);
				}
				else if(inputStr.equals("S"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_S);
				}
				else if(inputStr.equals("T"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_T);
				}
				else if(inputStr.equals("U"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_U);
				}
				else if(inputStr.equals("V"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_V);
				}
				else if(inputStr.equals("W"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_W);
				}
				else if(inputStr.equals("X"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_X);
				}
				else if(inputStr.equals("Y"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_Y);
				}
				else if(inputStr.equals("Z"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_Z);
				}
	
				// Top Row
				else if(inputStr.equals("~"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_BACK_QUOTE);
				}
				else if(inputStr.equals("!"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_1);
				}
				else if(inputStr.equals("@"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_2);
				}
				else if(inputStr.equals("#"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_3);
				}
				else if(inputStr.equals("$"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_4);
				}
				else if(inputStr.equals("%"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_5);
				}
				else if(inputStr.equals("^"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_6);
				}
				else if(inputStr.equals("&"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_7);
				}
				else if(inputStr.equals("*"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_8);
				}
				else if(inputStr.equals("("))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_9);
				}
				else if(inputStr.equals(")"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_0);
				}
				else if(inputStr.equals("_"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_MINUS);
				}
				else if(inputStr.equals("+"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_EQUALS);
				}
	
				// Rest
				else if(inputStr.equals("openbrace"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_OPEN_BRACKET);
				}
				else if(inputStr.equals("closebrace"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_CLOSE_BRACKET);
				}
				else if(inputStr.equals("|"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_BACK_SLASH);
				}
				else if(inputStr.equals(":"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_SEMICOLON);
				}
				else if(inputStr.equals("\""))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_QUOTE);
				}
				else if(inputStr.equals("<"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_COMMA);
				}
				else if(inputStr.equals(">"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_PERIOD);
				}
				else if(inputStr.equals("?"))
				{
					keys.add(SHIFT);
					keys.add(KeyEvent.VK_SLASH);
				}
				
				// Else Trash
				else;
			}
			else
			{
				boolean shifted = false;
				if(keys.size() > 0)
				{
					int key = keys.get(keys.size()-1);
					if(keys.size() > 1)
					{
						if(keys.get(keys.size()-2).equals(-1))
							shifted = true;
					}
					
					for(int j = 0; j < (multi-1); j++)
					{
						if(shifted)
							keys.add(SHIFT);
						
						keys.add(key);
					}
				}
			}
		}
		
		return keys;
	}
}

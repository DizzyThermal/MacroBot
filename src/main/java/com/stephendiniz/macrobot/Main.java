package com.stephendiniz.macrobot;

import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args)
	{
		GUI go = new GUI();

		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setSize(360, 300);
		go.setResizable(true);
		go.setVisible(true);
	}
}

package com.jalizadeh.library.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class LoadScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JButton jbLoad, jbNew, jbExit;
	
	public LoadScreen(String title) {
		super(title);
		
		initWidgets();
		addWidgets();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(470, 70);
		setResizable(false);
	}

	private void initWidgets() {
		panel = new JPanel(new FlowLayout());
		
		jbNew = new JButton("Start New Library");
		jbLoad = new JButton("Load Saved Library");
		jbExit = new JButton("Exit");		
	}

	private void addWidgets() {
		panel.add(jbNew);
		panel.add(jbLoad);
		panel.add(jbExit);
		
		setBackground(new Color(194, 230, 248));
		setContentPane(panel);
	}
	
	public void addActionListener(ActionListener a) {
		jbNew.addActionListener(a);
		jbLoad.addActionListener(a);
		jbExit.addActionListener(a);
	}

	public JButton getButtonNew() {
		return jbNew;
	}

	public JButton getButtonLoad() {
		return jbLoad;
	}

	public JButton getButtonExit() {
		return jbExit;
	}
}

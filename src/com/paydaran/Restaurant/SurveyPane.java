package com.paydaran.Restaurant;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SurveyPane
extends JPanel
{
        SerialComm conn;
        
	public SurveyPane(SerialComm sercomm)
	{
		super();
                conn = sercomm;
		setLayout(new BorderLayout());
		JComponent tabbed = createTabbedPane();
		this.add(tabbed, BorderLayout.CENTER);
	}

	public JComponent createTabbedPane()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		JComponent	component;
		component = new QBankTab();
		tabbedPane.add(component, "بانک سوالات");

		component = new SBankTab();
		tabbedPane.add(component, "بانک سناریوها");
		
		component = new SendTab(conn);
		tabbedPane.add(component, "ارسال");

		component = new DiagramTab();
		tabbedPane.add(component, "نمودار");
	
		return tabbedPane;
	}
}



/*** ChatPane.java ***/
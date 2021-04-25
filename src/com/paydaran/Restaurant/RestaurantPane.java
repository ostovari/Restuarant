package com.paydaran.Restaurant;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RestaurantPane
extends JPanel
{
	private JLabel		m_statusLabel;
        SerialComm conn;
        
	public RestaurantPane(SerialComm sercomm)
	{
		super();
                conn = sercomm;
		setLayout(new BorderLayout());
		JComponent tabbed = createTabbedPane();
		this.add(tabbed, BorderLayout.CENTER);
		m_statusLabel = new JLabel();
		this.add(m_statusLabel, BorderLayout.SOUTH);

		setStatusLine(" ");
	}

	public JComponent createTabbedPane()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		JComponent	component;
		component = new DailySale();
		tabbedPane.add(component, "فروش روزانه");

		component = new ChefOffer();
		tabbedPane.add(component, "پیشنهاد سرآشپز");
		
		component = new FinishedFood();
		tabbedPane.add(component, "غذاهای تمام شده");

		component = new SurveyPane(conn);
		tabbedPane.add(component, "نظرسنجی");
		
		component = new UserPassTab();
		tabbedPane.add(component, "تغییر رمز عبور");
		
		component = new HealthChek(conn);
		tabbedPane.add(component, "چرخه سلامت");
		
		component = new AccessControlTab();
		tabbedPane.add(component, "تعیین سطح دسترسی");

		return tabbedPane;
	}


	public void setStatusLine(String strMessage)
	{
		m_statusLabel.setText(strMessage);
	}
}



/*** ChatPane.java ***/
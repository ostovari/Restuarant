package com.paydaran.Restaurant;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HealthChek extends JPanel{
    SerialComm healthserialcon;
    DefaultListModel modelR;
    DefaultListModel modelL;
    DefaultListModel modelN;
    JPanel showpnl;
    static int later_healthy = 0 , later_unhealthy = 0;
	
	public HealthChek (SerialComm com){
		super();
                healthserialcon = com;
		setLayout(new BorderLayout());
		JPanel basepanel = new JPanel();
		basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
		
		JPanel slct = new JPanel();
		slct.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
               modelR = new DefaultListModel();
               modelL = new DefaultListModel();
               modelN = new DefaultListModel();
               //modelR.addElement(0);
               //modelL.addElement(0);
               JPanel righcol = new JPanel();
               righcol.setLayout(new BoxLayout(righcol, BoxLayout.Y_AXIS));
               JScrollPane scroll = new JScrollPane(new JLabel("تعداد واحدهای سالم"));
               scroll.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
               righcol.add(scroll);
               JList queslist = new JList(modelR);
               queslist.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
               righcol.add(new JScrollPane(queslist));
               slct.add(righcol);
                
               JPanel leftcol = new JPanel();
               leftcol.setLayout(new BoxLayout(leftcol, BoxLayout.Y_AXIS));
               JScrollPane scroll2 = new JScrollPane(new JLabel("تعداد واحدهای ناسالم"));
               scroll2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
               leftcol.add(scroll2);
               JList queslist2 = new JList(modelL);
               queslist2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
               leftcol.add(new JScrollPane(queslist2));
               slct.add(leftcol);
               basepanel.add(slct);
                
               JPanel havijuriPanel = new JPanel();
               havijuriPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                showpnl = new JPanel();
                showpnl.setLayout(new BoxLayout(showpnl, BoxLayout.Y_AXIS));
                JScrollPane scroll6 = new JScrollPane(new JLabel("واحدهای ناسالم"));
                scroll6.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                showpnl.add(scroll6);
                JList nodeList = new JList(modelN);
                nodeList.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                showpnl.add(new JScrollPane(nodeList));
                showpnl.setVisible(false);
                havijuriPanel.add(showpnl);
                basepanel.add(havijuriPanel);
       
		JPanel fnlBtnPanel = new JPanel();
		fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		JButton fnlAksept = new JButton("به روز رسانی");
                fnlAksept.addActionListener(new refresher());
		fnlBtnPanel.add(fnlAksept);
		//fnlAksept.addActionListener(this);
		fnlBtnPanel.add(Box.createHorizontalGlue());
		fnlBtnPanel.add(Box.createHorizontalGlue());
		JButton fnlcancelBtn = new JButton("نمایش گره های ناسالم");
                fnlcancelBtn.addActionListener(new ShowNode());
		fnlBtnPanel.add(fnlcancelBtn);
		basepanel.add(fnlBtnPanel);
				
		this.add(basepanel);               
	}
        
         
        
        public static byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte) v;
	    }
	    return b;
	  }
        public class refresher implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {  
            modelR.removeAllElements();
            modelL.removeAllElements();
            if (RestaurantMain.p == 20){
                showpnl.setVisible(false); 
                int healthy = 0 , unhealthy = 0;
                for(int u = 0 ; u < 5 ; u++){
                    if(RestaurantMain.id[u] == 20)
                        healthy++;
                    else
                        unhealthy++;
                }
               later_healthy = healthy;
               later_unhealthy = unhealthy;
            }
            modelR.addElement(later_healthy);
            modelL.addElement(later_unhealthy);
        }
            
        }
        
        public class ShowNode implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {        
            if (RestaurantMain.p == 20){
                showpnl.setVisible(true);
                modelN.removeAllElements();
                for(int u = 0 ; u < 5 ; u++){
                    if(RestaurantMain.id[u] != 20)
                        modelN.addElement(u+1);
                }
            }else{
                JOptionPane.showMessageDialog(null, "لطفا چند لحظه صبر کنید");
            }
        }
            
        }
} 




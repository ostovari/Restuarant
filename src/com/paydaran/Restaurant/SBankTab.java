package com.paydaran.Restaurant;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class SBankTab extends JPanel implements ActionListener, ItemListener,
						  ChangeListener, PropertyChangeListener {
    private JProgressBar[] volumeMeter = new JProgressBar[2];
    private JSlider[] volumeSlider = new JSlider[2];
    private JComboBox[] volumePort = new JComboBox[2];
    private JCheckBox[] muteButton = new JCheckBox[2];

    private JComboBox[] bufferSelector = new JComboBox[2];

    public SBankTab() {
		
	super();
                
        
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JPanel basepanel = new JPanel();
            basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
            JTabbedPane subTabPane = new JTabbedPane();
            subTabPane.setComponentOrientation(getComponentOrientation()
		.RIGHT_TO_LEFT);
            JComponent	component;
            component = new EmployeeSB();
            subTabPane.add(component, "کارکنان");
		
            component = new CustomerSB();
            subTabPane.add(component, "مشتریان");
            basepanel.add(subTabPane);
            this.add(basepanel, BorderLayout.CENTER);
            JPanel hamel = new JPanel();
            hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
            hamel.setAlignmentX(CENTER_ALIGNMENT);
            this.add(hamel, BorderLayout.CENTER);
            if(PassWordDialog.accessS || PassWordDialog.isadmin){
                basepanel.setVisible(true);
                hamel.setVisible(false);
        }else{
            basepanel.setVisible(false);
            hamel.setVisible(true);
        }
	}

    private void createGUI(int d, String title) {
	JPanel p = new JPanel();
	p.add(new JLabel(title));

	p.add(new JLabel("Mixer:"));

	p.add(new JLabel("Level:"));

	p.add(new JLabel("Buffer size in millis:"));
	
	add(p);
	// init with first port
    }
    
    public void itemStateChanged(ItemEvent e) {}

    public void stateChanged(ChangeEvent e) {}
    
    private void enableButtons() {}
	


    public void propertyChange(PropertyChangeEvent e) {}

    private void startLevelMeterThread() {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
public class EmployeeSB extends JPanel{
		
	JPanel befshowpanel;
	JPanel addtoS;
	JPanel addeqpanel;
	JPanel allqpanel;
	JPanel editSPanel;
	JTextField senarioTxt;
	JList sList;
	JList qnumList;
	JList allQ;
	JList addedQ;
	DefaultListModel qNum;
	DefaultListModel model;
	DefaultListModel model2;
	Database db = new Database();
	JButton addQ;
	JPanel addSpanel;
	JButton removeQ;
        final JRadioButton persian;
        final JRadioButton english;
        final JRadioButton arabic;
		
		public EmployeeSB (){
			super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			
			JPanel radioBPanel = new JPanel();
			radioBPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			final JRadioButton neewSenario = new JRadioButton("تعریف سناریو جدید",true);
			final JRadioButton add2Senario = new JRadioButton("افزودن به سناریو");
			final JRadioButton removeFSenario = new JRadioButton("حذف از سناریو");
			final JRadioButton editSenario = new JRadioButton("ویرایش سناریو");
			
			neewSenario.setMnemonic(KeyEvent.VK_C);
			add2Senario.setMnemonic(KeyEvent.VK_M);
			removeFSenario.setMnemonic(KeyEvent.VK_P);
			editSenario.setMnemonic(KeyEvent.VK_Q);

			neewSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	befshowpanel.setVisible(true);
		        	addtoS.setVisible(false);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				addeqpanel.setVisible(false);
				removeQ.setVisible(false);
				editSPanel.setVisible(false);
		         }           
		      });

			add2Senario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
		        	addtoS.setVisible(true);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				addeqpanel.setVisible(false);
				removeQ.setVisible(false);
				editSPanel.setVisible(false);
		         }           
		      });

			removeFSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {  
		        	befshowpanel.setVisible(false);
		        	addtoS.setVisible(true);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				addeqpanel.setVisible(false);
				removeQ.setVisible(false);
				editSPanel.setVisible(false);
		         }           
		      });
			
			editSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	 befshowpanel.setVisible(false);
		        	 addtoS.setVisible(true);
		        	 allqpanel.setVisible(false);
						addQ.setVisible(false);
						addeqpanel.setVisible(false);
						removeQ.setVisible(false);
						editSPanel.setVisible(true);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(neewSenario);
		      group.add(add2Senario);
		      group.add(removeFSenario);
		      group.add(editSenario);

		      radioBPanel.add(neewSenario);
		      radioBPanel.add(add2Senario);
		      radioBPanel.add(removeFSenario); 
		      radioBPanel.add(editSenario);
		    slct.add(radioBPanel, BorderLayout.EAST);
		    basepanel.add(slct);
		    
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel2 = new JPanel();
                    radioBPanel2.setLayout(new BoxLayout(radioBPanel2, BoxLayout.Y_AXIS));
                    persian = new JRadioButton("فارسی",true);
                    english = new JRadioButton("انگلیسی");
                    arabic = new JRadioButton("عربی");
		
                    persian.setMnemonic(KeyEvent.VK_C);
                    english.setMnemonic(KeyEvent.VK_M);
                    arabic.setMnemonic(KeyEvent.VK_P);
                    
		persian.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
		           //do something
	         }           
	      });

		english.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
	           //do something
	         }           
	      });

		arabic.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
		           //do something
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group2 = new ButtonGroup();
	      group2.add(persian);
	      group2.add(english);
	      group2.add(arabic);

	      radioBPanel2.add(persian);
	      radioBPanel2.add(english);
	      radioBPanel2.add(arabic); 
	    befshowpanel.add(radioBPanel2, BorderLayout.NORTH);  
		   JPanel senarioNam = new JPanel();
		    senarioNam.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    senarioNam.add(new JLabel("نام سناریو"), new BorderLayout());
		    senarioTxt = new JTextField(10);
		    senarioNam.add(senarioTxt);
                    befshowpanel.add(senarioNam);
		    basepanel.add(befshowpanel);
		    
		    addtoS = new JPanel();
		    addtoS.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    model2 = new DefaultListModel();
		    qNum = new DefaultListModel();
		    db.openDb();
			ResultSet rs = db.createQuery("SELECT PAYDARAN.TABLE_SENARIO.S_name , p.Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO LEFT JOIN" +
					" (" +
					" SELECT PAYDARAN.TABLE_SENARIO_Q.S_name , COUNT(Q_id) as Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO_Q" +
					" Group By PAYDARAN.TABLE_SENARIO_Q.S_name " +
					") as p" +
					" on PAYDARAN.TABLE_SENARIO.S_name = p.S_name");
			try {
				while(rs.next()){
				     //Retrieve by column name
                                    model2.addElement(rs.getString("S_name"));
				    qNum.addElement(rs.getInt("Qnumber"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeDb();
		    JPanel righcol = new JPanel();
			righcol.setLayout(new BoxLayout(righcol, BoxLayout.Y_AXIS));
                        JScrollPane scroll = new JScrollPane(new JLabel("نام سناریو"));
                        scroll.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			righcol.add(scroll);
		    sList = new JList(model2);
                    sList.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    sList.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if(add2Senario.isSelected()){
						allqpanel.setVisible(true);
						addQ.setVisible(true);
						addeqpanel.setVisible(true);
						model.clear();
						String senar = sList.getSelectedValue().toString();
						db.openDb();//CAST(S_name AS VARCHAR(128)) =
						ResultSet rs3 = db.createQuery("SELECT Question" +
								" FROM PAYDARAN.TABLE_QUES" +
								" WHERE Q_id IN (SELECT Q_id" +
								" FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+senar+"')");
						try {
							while(rs3.next()){
							     //Retrieve by column name
								model.addElement(rs3.getString("Question"));
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						db.closeDb();
					}else if(removeFSenario.isSelected()){
						removeQ.setVisible(true);
						addeqpanel.setVisible(true);
						model.clear();
						String senar = sList.getSelectedValue().toString();
						db.openDb();
						ResultSet rs3 = db.createQuery("SELECT Question" +
								" FROM PAYDARAN.TABLE_QUES" +
								" WHERE Q_id IN (SELECT Q_id" +
								" FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+senar+"')");
						try {
							while(rs3.next()){
							     //Retrieve by column name
								model.addElement(rs3.getString("Question"));
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						db.closeDb();
					} else if(editSenario.isSelected()){
						
					}
				}
			});
		    righcol.add(new JScrollPane(sList));
		    addtoS.add(righcol);
		    JPanel leftcol = new JPanel();
		    leftcol.setLayout(new BoxLayout(leftcol, BoxLayout.Y_AXIS));
                    JScrollPane scroll2 = new JScrollPane(new JLabel("تعداد سوال"));
                        scroll2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			leftcol.add(scroll2);
		    qnumList = new JList(qNum);
		    leftcol.add(new JScrollPane(qnumList));
		    addtoS.add(leftcol);
		    addtoS.setVisible(false);
		    basepanel.add(addtoS);
		    
		    addSpanel = new JPanel();
		    addSpanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    Vector<String> question = new Vector<String>();
		    db.openDb();
			ResultSet rs2 = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES");
			try {
				while(rs2.next()){
				     //Retrieve by column name
					question.add(rs2.getString("Question"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeDb();
		    allqpanel = new JPanel();
		    allqpanel.setLayout(new BoxLayout(allqpanel, BoxLayout.Y_AXIS));
                    JScrollPane scroll3 = new JScrollPane(new JLabel("سوالات موجود"));
                        scroll3.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    allqpanel.add(scroll3);
		    allQ = new JList(question);
                    allQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    allqpanel.add(new JScrollPane(allQ));
		    allqpanel.setVisible(false);
		    addSpanel.add(allqpanel);
		    
		    addQ = new JButton("اضافه شود به سناریو >>");
		    addQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(allQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = allQ.getSelectedValue().toString();
						String sentemp = sList.getSelectedValue().toString();
						int qID = 0;
                                                int sID = 0;
                                                
						String query1 = "SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(128)) = '"+questemp+"'";
						db.openDb();						
						ResultSet rs3 = db.createQuery(query1);
						try {
							while(rs3.next()){
							     //Retrieve by column name
								qID = rs3.getInt("Q_id");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                                                String querysid = "SELECT S_ID FROM PAYDARAN.TABLE_Senario"
                                                        + " WHERE CAST(S_NAME AS VARCHAR(128)) = '"+sentemp+"'";
                                                ResultSet rs12 = db.createQuery(querysid);
						try {
							while(rs12.next()){
							     //Retrieve by column name
								sID = rs12.getInt("S_ID");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                                                
                                                
						String query2 = "INSERT INTO PAYDARAN.TABLE_SENARIO_Q VALUES ('"+sentemp+"',"+qID+", "+sID+")"; 
						if(db.insertDb(query2)){
							System.out.println("added...!");
							model.addElement(questemp);
                                                        int newqnum = Integer.parseInt(qNum.getElementAt(sList.getSelectedIndex()).toString())+1;
                                                        qNum.removeElementAt(sList.getSelectedIndex());
                                                        qNum.add(sList.getSelectedIndex(), newqnum);
						}else{
							System.out.println("oooops. try again!!");
                                                        JOptionPane.showMessageDialog(null, "سوال مورد نظر در سناریو موجود است");
						}
						db.closeDb();
					}
					
				}
			});
		    addQ.setVisible(false);
		    addSpanel.add(addQ);
		    
		    addeqpanel = new JPanel();
		    addeqpanel.setLayout(new BoxLayout(addeqpanel, BoxLayout.Y_AXIS));
		    model = new DefaultListModel();
		    addedQ = new JList(model);
                    addedQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JScrollPane scroll4 = new JScrollPane(new JLabel("سوالات اضافه شده"));
                        scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    addeqpanel.add(scroll4);
		    addeqpanel.add(new JScrollPane(addedQ));
		    addeqpanel.setVisible(false);
		    addSpanel.add(addeqpanel);
		    
		    removeQ = new JButton("حذف");
		    removeQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(addedQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = addedQ.getSelectedValue().toString();
						String sentemp = sList.getSelectedValue().toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+sentemp+"' AND Q_id in " +
								"(SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(128)) = '"+questemp+"' )";
						db.openDb();						
						if(db.removefromDb(query)){
							System.out.println("removed...!");
							model.removeElement(questemp);
                                                        int newqnum = Integer.parseInt(qNum.getElementAt(sList.getSelectedIndex()).toString())-1;
                                                        qNum.removeElementAt(sList.getSelectedIndex());
                                                        qNum.add(sList.getSelectedIndex(), newqnum);
						}
						else
							System.out.println("oooops. try again!! ");
						db.closeDb();
					}
					
				}
			});
		    removeQ.setVisible(false);
		    addSpanel.add(removeQ);
		    basepanel.add(addSpanel);
		    
		    editSPanel = new JPanel();
		    editSPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    JButton editNamS = new JButton("تغییر نام سناریو");
		    editNamS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(sList.isSelectionEmpty()){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String oldS = sList.getSelectedValue().toString();
						String newS = JOptionPane.showInputDialog(null, "تغییر نام سناریو", oldS);
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_SENARIO "
									  +"SET S_name = '"+newS +"' "
									  +"WHERE CAST(S_name AS VARCHAR(128)) = '"+oldS+"'"; 
						if(db.updateDb(query)){
							System.out.println("updated...!");
						}else{
							System.out.println("oooops...!");
						}
						db.closeDb();
					}
				}
			});
		    editSPanel.add(editNamS);
		    JButton removeS = new JButton("حذف سناریو");
		    removeS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(sList.isSelectionEmpty()){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String senarioname = sList.getSelectedValue().toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+senarioname+"'";
						db.openDb();						
						if(db.removefromDb(query)){
							System.out.println("removed...!");
                                                        qNum.removeElementAt(sList.getSelectedIndex());
							model2.removeElement(senarioname);
						}
						else
							System.out.println("oooops. try again!! ");
						db.closeDb();
					}
				}
			});
		    editSPanel.add(removeS);
		    editSPanel.setVisible(false);
		    basepanel.add(editSPanel);
		    
			JPanel fnlBtnPanel = new JPanel();
			fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			JButton fnlAksept = new JButton("تایید نهایی");
			fnlAksept.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String temp = senarioTxt.getText();
				if(befshowpanel.isVisible() && neewSenario.isSelected() && !temp.isEmpty()){
					db.openDb();
                                        int lang = 0;
                                         if(persian.isSelected()){
                                            lang = 0;
                                        }else if(english.isSelected()){
                                            lang = 1;
                                        }else if(arabic.isSelected()){
                                            lang = 2;
                                        }
					String query = "INSERT INTO PAYDARAN.TABLE_SENARIO (S_NAME, ANSWERER, LANG) VALUES ('"+temp+"','کارکنان', "+lang+")"; 
					if(db.insertDb(query)){
						System.out.println("added...!");
                                                model2.addElement(temp);
                                                qNum.addElement(0);
					}else{
						System.out.println("oooops. try again!!");
					}
					db.closeDb();
				}
					
				}
			});
			fnlBtnPanel.add(fnlAksept);
			fnlBtnPanel.add(Box.createHorizontalGlue());
			fnlBtnPanel.add(Box.createHorizontalGlue());
			JButton fnlcancelBtn = new JButton("انصراف");
			fnlBtnPanel.add(fnlcancelBtn);
			basepanel.add(fnlBtnPanel);
			
			
			this.add(basepanel, BorderLayout.CENTER);
		}
	}

public class CustomerSB extends JPanel{
	JPanel befshowpanel;
	JPanel addtoS;
	JPanel addeqpanel;
	JPanel allqpanel;
	JPanel editSPanel;
	JTextField senarioTxt;
	JList sList;
	JList qnumList;
	JList allQ;
	JList addedQ;
	DefaultListModel qNum;
	DefaultListModel model;
	DefaultListModel model2;
	Database db = new Database();
	JButton addQ;
	JPanel addSpanel;
	JButton removeQ;
        final JRadioButton persian;
        final JRadioButton english;
        final JRadioButton arabic;
	
	public CustomerSB (){
		super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			
			JPanel radioBPanel = new JPanel();
			radioBPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			final JRadioButton neewSenario = new JRadioButton("تعریف سناریو جدید",true);
			final JRadioButton add2Senario = new JRadioButton("افزودن به سناریو");
			final JRadioButton removeFSenario = new JRadioButton("حذف از سناریو");
			final JRadioButton editSenario = new JRadioButton("ویرایش سناریو");
			
			neewSenario.setMnemonic(KeyEvent.VK_C);
			add2Senario.setMnemonic(KeyEvent.VK_M);
			removeFSenario.setMnemonic(KeyEvent.VK_P);
			editSenario.setMnemonic(KeyEvent.VK_Q);

			neewSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	befshowpanel.setVisible(true);
		        	addtoS.setVisible(false);
		        	allqpanel.setVisible(false);
					addQ.setVisible(false);
					addeqpanel.setVisible(false);
					removeQ.setVisible(false);
					editSPanel.setVisible(false);
		         }           
		      });

			add2Senario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
		        	addtoS.setVisible(true);
		        	allqpanel.setVisible(false);
					addQ.setVisible(false);
					addeqpanel.setVisible(false);
					removeQ.setVisible(false);
					editSPanel.setVisible(false);
		         }           
		      });

			removeFSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {  
		        	 befshowpanel.setVisible(false);
		        	 addtoS.setVisible(true);
		        	 allqpanel.setVisible(false);
						addQ.setVisible(false);
						addeqpanel.setVisible(false);
						removeQ.setVisible(false);
						editSPanel.setVisible(false);
		         }           
		      });
			
			editSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	 befshowpanel.setVisible(false);
		        	 addtoS.setVisible(true);
		        	 allqpanel.setVisible(false);
						addQ.setVisible(false);
						addeqpanel.setVisible(false);
						removeQ.setVisible(false);
						editSPanel.setVisible(true);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(neewSenario);
		      group.add(add2Senario);
		      group.add(removeFSenario);
		      group.add(editSenario);

		      radioBPanel.add(neewSenario);
		      radioBPanel.add(add2Senario);
		      radioBPanel.add(removeFSenario); 
		      radioBPanel.add(editSenario);
		    slct.add(radioBPanel, BorderLayout.EAST);
		    basepanel.add(slct);
		    
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel2 = new JPanel();
                    radioBPanel2.setLayout(new BoxLayout(radioBPanel2, BoxLayout.Y_AXIS));
                    persian = new JRadioButton("فارسی",true);
                    english = new JRadioButton("انگلیسی");
                    arabic = new JRadioButton("عربی");
		
                    persian.setMnemonic(KeyEvent.VK_C);
                    english.setMnemonic(KeyEvent.VK_M);
                    arabic.setMnemonic(KeyEvent.VK_P);
                    
		persian.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
		           //do something
	         }           
	      });

		english.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
	           //do something
	         }           
	      });

		arabic.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
		           //do something
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group2 = new ButtonGroup();
	      group2.add(persian);
	      group2.add(english);
	      group2.add(arabic);

	      radioBPanel2.add(persian);
	      radioBPanel2.add(english);
	      radioBPanel2.add(arabic); 
	    befshowpanel.add(radioBPanel2, BorderLayout.NORTH);
                    
		   JPanel senarioNam = new JPanel();
		    senarioNam.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    senarioNam.add(new JLabel("نام سناریو"), new BorderLayout());
		    senarioTxt = new JTextField(10);
		    senarioNam.add(senarioTxt);
                    befshowpanel.add(senarioNam);
		    basepanel.add(befshowpanel);
		    
		    addtoS = new JPanel();
		    addtoS.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    model2 = new DefaultListModel();
		    qNum = new DefaultListModel();
		    db.openDb();
			ResultSet rs = db.createQuery("SELECT PAYDARAN.TABLE_SENARIO.S_name , p.Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO LEFT JOIN" +
					" (" +
					" SELECT PAYDARAN.TABLE_SENARIO_Q.S_name , COUNT(Q_id) as Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO_Q" +
					" Group By PAYDARAN.TABLE_SENARIO_Q.S_name " +
					") as p" +
					" on PAYDARAN.TABLE_SENARIO.S_name = p.S_name");
			try {
				while(rs.next()){
				     //Retrieve by column name
					model2.addElement(rs.getString("S_name"));
				     qNum.addElement(rs.getInt("Qnumber"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeDb();
		    JPanel righcol = new JPanel();
			righcol.setLayout(new BoxLayout(righcol, BoxLayout.Y_AXIS));
                        JScrollPane scroll = new JScrollPane(new JLabel("نام سناریو"));
                        scroll.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			righcol.add(scroll);
		    sList = new JList(model2);
                    sList.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    sList.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if(add2Senario.isSelected()){
						allqpanel.setVisible(true);
						addQ.setVisible(true);
						addeqpanel.setVisible(true);
						model.clear();
						String senar = sList.getSelectedValue().toString();
						db.openDb();
						ResultSet rs3 = db.createQuery("SELECT Question" +
								" FROM PAYDARAN.TABLE_QUES" +
								" WHERE Q_id IN (SELECT Q_id" +
								" FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+senar+"')");
						try {
							while(rs3.next()){
							     //Retrieve by column name
								model.addElement(rs3.getString("Question"));
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						db.closeDb();
					}else if(removeFSenario.isSelected()){
						removeQ.setVisible(true);
						addeqpanel.setVisible(true);
						model.clear();
						String senar = sList.getSelectedValue().toString();
						db.openDb();
						ResultSet rs3 = db.createQuery("SELECT Question" +
								" FROM PAYDARAN.TABLE_QUES" +
								" WHERE Q_id IN (SELECT Q_id" +
								" FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+senar+"')");
						try {
							while(rs3.next()){
							     //Retrieve by column name
								model.addElement(rs3.getString("Question"));
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						db.closeDb();
					} else if(editSenario.isSelected()){
						
					}
				}
			});
		    righcol.add(new JScrollPane(sList));
		    addtoS.add(righcol);
		    JPanel leftcol = new JPanel();
		    leftcol.setLayout(new BoxLayout(leftcol, BoxLayout.Y_AXIS));
                    JScrollPane scroll2 = new JScrollPane(new JLabel("تعداد سوال"));
                        scroll2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			leftcol.add(scroll2);
		    qnumList = new JList(qNum);
		    leftcol.add(new JScrollPane(qnumList));
		    addtoS.add(leftcol);
		    addtoS.setVisible(false);
		    basepanel.add(addtoS);
		    
		    addSpanel = new JPanel();
		    addSpanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    Vector<String> question = new Vector<String>();
		    db.openDb();
			ResultSet rs2 = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES");
			try {
				while(rs2.next()){
				     //Retrieve by column name
					question.add(rs2.getString("Question"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeDb();
		    allqpanel = new JPanel();
		    allqpanel.setLayout(new BoxLayout(allqpanel, BoxLayout.Y_AXIS));
                    JScrollPane scroll3 = new JScrollPane(new JLabel("سوالات موجود"));
                        scroll3.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    allqpanel.add(scroll3);
		    allQ = new JList(question);
                    allQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    allqpanel.add(new JScrollPane(allQ));
		    allqpanel.setVisible(false);
		    addSpanel.add(allqpanel);
		    
		    addQ = new JButton("اضافه شود به سناریو >>");
		    addQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(allQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = allQ.getSelectedValue().toString();
						String sentemp = sList.getSelectedValue().toString();
						int qID = 0;
						String query1 = "SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE Question = '"+questemp+"'";
						db.openDb();						
						ResultSet rs3 = db.createQuery(query1);
						try {
							while(rs3.next()){
							     //Retrieve by column name
								qID = rs3.getInt("Q_id");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String query2 = "INSERT INTO PAYDARAN.TABLE_SENARIO_Q VALUES ('"+sentemp+"','"+qID+"')"; 
						if(db.insertDb(query2)){
							System.out.println("added...!");
							model.addElement(questemp);
                                                        int newqnum = Integer.parseInt(qNum.getElementAt(sList.getSelectedIndex()).toString())+1;
                                                        qNum.removeElementAt(sList.getSelectedIndex());
                                                        qNum.add(sList.getSelectedIndex(), newqnum);
						}else{
							System.out.println("oooops. try again!!");
                                                        JOptionPane.showMessageDialog(null, "سوال مورد نظر در سناریو موجود است");
						}
						db.closeDb();
					}
					
				}
			});
		    addQ.setVisible(false);
		    addSpanel.add(addQ);
		    
		    addeqpanel = new JPanel();
		    addeqpanel.setLayout(new BoxLayout(addeqpanel, BoxLayout.Y_AXIS));
		    model = new DefaultListModel();
		    addedQ = new JList(model);
                    addedQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JScrollPane scroll4 = new JScrollPane(new JLabel("سوالات اضافه شده"));
                        scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    addeqpanel.add(scroll4);
		    addeqpanel.add(new JScrollPane(addedQ));
		    addeqpanel.setVisible(false);
		    addSpanel.add(addeqpanel);
		    
		    removeQ = new JButton("حذف");
		    removeQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(addedQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = addedQ.getSelectedValue().toString();
						String sentemp = sList.getSelectedValue().toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+sentemp+"' AND Q_id in " +
								"(SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(128)) = '"+questemp+"' )";
						db.openDb();						
						if(db.removefromDb(query)){
							System.out.println("removed...!");
							model.removeElement(questemp);
                                                        int newqnum = Integer.parseInt(qNum.getElementAt(sList.getSelectedIndex()).toString())-1;
                                                        qNum.removeElementAt(sList.getSelectedIndex());
                                                        qNum.add(sList.getSelectedIndex(), newqnum);
						}
						else
							System.out.println("oooops. try again!! ");
						db.closeDb();
					}
					
				}
			});
		    removeQ.setVisible(false);
		    addSpanel.add(removeQ);
		    basepanel.add(addSpanel);
		    
		    editSPanel = new JPanel();
		    editSPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    JButton editNamS = new JButton("تغییر نام سناریو");
		    editNamS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(sList.isSelectionEmpty()){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String oldS = sList.getSelectedValue().toString();
						String newS = JOptionPane.showInputDialog(null, "تغییر نام سناریو", oldS);
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_SENARIO "
									  +"SET S_name = '"+newS +"' "
									  +"WHERE CAST(S_name AS VARCHAR(128)) = '"+oldS+"'"; 
						if(db.updateDb(query)){
							System.out.println("updated...!");
						}else{
							System.out.println("oooops...!");
						}
						db.closeDb();
					}
				}
			});
		    editSPanel.add(editNamS);
		    JButton removeS = new JButton("حذف سناریو");
		    removeS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(sList.isSelectionEmpty()){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String senarioname = sList.getSelectedValue().toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO" +
								" WHERE CAST(S_name AS VARCHAR(128)) = '"+senarioname+"'";
						db.openDb();						
						if(db.removefromDb(query)){
							System.out.println("removed...!");
                                                        qNum.removeElementAt(sList.getSelectedIndex());
							model2.removeElement(senarioname);         
						}
						else
							System.out.println("oooops. try again!! ");
						db.closeDb();
					}
				}
			});
		    editSPanel.add(removeS);
		    editSPanel.setVisible(false);
		    basepanel.add(editSPanel);
		    
			JPanel fnlBtnPanel = new JPanel();
			fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			JButton fnlAksept = new JButton("تایید نهایی");
			fnlAksept.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String temp = senarioTxt.getText();
				if(befshowpanel.isVisible() && neewSenario.isSelected() && !temp.isEmpty()){
					db.openDb();
                                        int lang = 0;
                                         if(persian.isSelected()){
                                            lang = 0;
                                        }else if(english.isSelected()){
                                            lang = 1;
                                        }else if(arabic.isSelected()){
                                            lang = 2;
                                        }
					String query = "INSERT INTO PAYDARAN.TABLE_SENARIO VALUES ('"+temp+"','مشتری', "+lang+")"; 
					if(db.insertDb(query)){
						System.out.println("added...!");
                                                model2.addElement(temp);
                                                qNum.addElement(0);
					}else{
						System.out.println("oooops. try again!!");
					}
					db.closeDb();
				}
					
				}
			});
			fnlBtnPanel.add(fnlAksept);
			fnlBtnPanel.add(Box.createHorizontalGlue());
			fnlBtnPanel.add(Box.createHorizontalGlue());
			JButton fnlcancelBtn = new JButton("انصراف");
			fnlBtnPanel.add(fnlcancelBtn);
			basepanel.add(fnlBtnPanel);
			
			
			this.add(basepanel, BorderLayout.CENTER);
		}
	
    }
}
/*** AudioPanel.java ***/
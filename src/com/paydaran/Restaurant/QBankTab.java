package com.paydaran.Restaurant;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class QBankTab
extends JPanel
    implements ActionListener, ItemListener, PropertyChangeListener
{
    private JButton			m_connectButton;




	public QBankTab()
	{
		
		super();
	
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                JPanel basepanel = new JPanel();
		basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
                JTabbedPane subTabPane = new JTabbedPane();
                subTabPane.setComponentOrientation(getComponentOrientation()
			.RIGHT_TO_LEFT);
                JComponent	component;
                component = new EmployeeQB();
                subTabPane.add(component, "کارکنان");
		
                component = new CustomerQB();
                subTabPane.add(component, "مشتریان");
                basepanel.add(subTabPane, BorderLayout.CENTER);
                this.add(basepanel, BorderLayout.CENTER);
                JPanel hamel = new JPanel();
                hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
                hamel.setAlignmentX(CENTER_ALIGNMENT);
                this.add(hamel, BorderLayout.CENTER);
                if(PassWordDialog.accessQ || PassWordDialog.isadmin){
                        basepanel.setVisible(true);
                        hamel.setVisible(false);
                }else{
                    basepanel.setVisible(false);
                    hamel.setVisible(true);
                }
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public class EmployeeQB extends JPanel {
		
	    final JRadioButton addQues;
            final JRadioButton editQues;
            final JRadioButton removeQues;
            JTextField questionTxt;
            JComboBox chsNumber;
            JPanel befshowpanel;
            JPanel fnlBtnPanel;
            JPanel listPanel;
            Database db = new Database();
            DefaultListModel model;
            DefaultListModel nitem;
            JList queslist;
            JList numlist;
            JButton editQbtn;
            JButton editNbtn;
            final JRadioButton persian;
            final JRadioButton english;
            final JRadioButton arabic;
				
            public EmployeeQB (){
			super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			addQues = new JRadioButton("اضافه کردن سوال",true);
			editQues = new JRadioButton("ویرایش سوال");
			removeQues = new JRadioButton("حذف سوال");
			addQues.setMnemonic(KeyEvent.VK_C);
			editQues.setMnemonic(KeyEvent.VK_M);
			removeQues.setMnemonic(KeyEvent.VK_P);

			addQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	listPanel.setVisible(false);
				befshowpanel.setVisible(true);
				fnlBtnPanel.setVisible(true);
		         }           
		      });

			editQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
					listPanel.setVisible(true);
					fnlBtnPanel.setVisible(true);
					editQbtn.setVisible(true);
					editNbtn.setVisible(true);
		         }           
		      });

			removeQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
					listPanel.setVisible(true);
					fnlBtnPanel.setVisible(true);
					editQbtn.setVisible(false);
					editNbtn.setVisible(false);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(addQues);
		      group.add(editQues);
		      group.add(removeQues);

		      slct.add(addQues);
		      slct.add(editQues);
		      slct.add(removeQues); 		    
		      basepanel.add(slct);
		    
                      
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel = new JPanel();
                    radioBPanel.setLayout(new BoxLayout(radioBPanel, BoxLayout.Y_AXIS));
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

	      radioBPanel.add(persian);
	      radioBPanel.add(english);
	      radioBPanel.add(arabic); 
	    befshowpanel.add(radioBPanel, BorderLayout.NORTH);   
		    JPanel showPanel = new JPanel();
		    showPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    JPanel Ques = new JPanel();
		    Ques.setLayout(new BoxLayout(Ques, BoxLayout.Y_AXIS));
		    Ques.add(new JLabel("متن سوال"), ComponentOrientation.RIGHT_TO_LEFT);
		    questionTxt = new JTextField(10);
		    Ques.add(questionTxt);
		    
		    showPanel.add(Ques);
		    JPanel chs = new JPanel();
		    chs.setLayout(new BoxLayout(chs, BoxLayout.Y_AXIS));
		    chs.add(new JLabel("تعداد گزینه"));
		    chsNumber = new JComboBox(new String [] {"2" , "3", "4", "5"});
		    chs.add(chsNumber);
		    showPanel.add(chs);
                    befshowpanel.add(showPanel);
			basepanel.add(befshowpanel);
			
			listPanel = new JPanel();
			listPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
                        model = new DefaultListModel();
			nitem = new DefaultListModel();
			db.openDb();
			ResultSet rs = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES");
			try {
				while(rs.next()){
				     //Retrieve by column name
				     model.addElement(rs.getString("Question"));
				     nitem.addElement(rs.getString("Choices"));
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeDb();
			
			JPanel righcol = new JPanel();
			righcol.setLayout(new BoxLayout(righcol, BoxLayout.Y_AXIS));
                        JScrollPane scroll = new JScrollPane(new JLabel("متن سوال"));
                        scroll.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			righcol.add(scroll);
		    queslist = new JList(model);
                    queslist.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    queslist.addListSelectionListener(new ListSelectionListener() {

                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                numlist.setSelectedIndex(queslist.getSelectedIndex());
                            }
                        });
		    righcol.add(new JScrollPane(queslist));
		    listPanel.add(righcol);
		    editQbtn = new JButton("ویرایش سوال");
		    editQbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String oldQ = queslist.getSelectedValue().toString();
					String newQ = JOptionPane.showInputDialog(null, "تغییر سوال", oldQ);
					if(!newQ.isEmpty() && !oldQ.equals(newQ)){
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_QUES "
									  +"SET Question = '"+newQ +"' "
									  +"WHERE CAST(Question AS VARCHAR(128)) = '"+oldQ+"'"; 
						if(db.updateDb(query)){
							System.out.println("added...!");
                                                        int place = model.indexOf(oldQ);
                                                        model.removeElement(oldQ);
                                                        model.add(place, newQ);
						}else{
							System.out.println("oooops...!");
						}
						db.closeDb();
					}
				}
			});
		    listPanel.add(editQbtn);
		    
		    JPanel leftcol = new JPanel();
		    leftcol.setLayout(new BoxLayout(leftcol, BoxLayout.Y_AXIS));
                    JScrollPane scroll2 = new JScrollPane(new JLabel("تعداد گزینه ها"));
                        scroll2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    leftcol.add(scroll2);
		    numlist = new JList(nitem);
                    numlist.setSize(100, 50);
		    leftcol.add(new JScrollPane(numlist));
		    listPanel.add(leftcol);
		    listPanel.add(new JLabel()); 
		    editNbtn = new JButton("ویرایش گزینه ها");
		    editNbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String itsQ = (String)model.elementAt(numlist.getSelectedIndex());
					String newN = JOptionPane.showInputDialog(null, "تغییر سوال", numlist.getSelectedValue().toString());
					db.openDb();
					String query = "UPDATE PAYDARAN.TABLE_QUES "
								  +"SET Choices = '"+newN +"' "
								  +"WHERE CAST(Question AS VARCHAR(128)) = '"+itsQ+"'"; 
					if(db.updateDb(query)){
						System.out.println("added...!");
					}else{
						System.out.println("ooooops...!");
					}
					db.closeDb();
					
				}
			});
		    listPanel.add(editNbtn);
		    listPanel.setVisible(false);
		    basepanel.add(listPanel);
		    
			
			fnlBtnPanel = new JPanel();
			fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			JButton fnlAksept = new JButton("تایید نهایی");
			fnlAksept.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String temp = questionTxt.getText();
					if(befshowpanel.isVisible() && !temp.isEmpty()){
						db.openDb();
                                                int lang = 0;
                                                if(persian.isSelected()){
                                                    lang = 0;
                                                }else if(english.isSelected()){
                                                    lang = 1;
                                                }else if(arabic.isSelected()){
                                                    lang = 2;
                                                }
						String query = "INSERT INTO PAYDARAN.TABLE_QUES (QUESTION, ANSWERER, CHOICES, LANGUAGE) VALUES ('"+temp+"', 'مشتری', "+chsNumber.getSelectedItem()+", "+lang+")"; 
						if(db.insertDb(query)){
							System.out.println("added...!");
							questionTxt.setText("");
                                                        model.addElement(temp);
                                                        nitem.addElement(chsNumber.getSelectedItem().toString());
                                                        JOptionPane.showMessageDialog(null, "سوال اضافه شد");
						}else{
							System.out.println("oooops...!");
						}
						db.closeDb();
					}else if(listPanel.isVisible() && editQues.isSelected()){
						
					}else if(listPanel.isVisible() && removeQues.isSelected()){
						String temp2;
						temp2 = queslist.getSelectedValue().toString();//CAST(Question AS VARCHAR(128)) = 'T1'
						db.openDb();
						String query = "DELETE FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(128)) = '"+temp2+"'"; 
						if(db.insertDb(query)){
							System.out.println("removed...!");
                                                        nitem.removeElementAt(queslist.getSelectedIndex());
                                                        model.removeElement(temp2);
						}else{
							System.out.println("oooops...!");
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
	
	
	
public class CustomerQB extends JPanel{
	
        JPanel befshowpanel;
	JTextField questionTxt;
	JComboBox chsNumber;
	JPanel listPanel;
	JPanel fnlBtnPanel;
        DefaultListModel model;
	DefaultListModel nitem;
	Database db = new Database();
	JList queslist;
	JList numlist;
	JButton editQbtn;
	JButton editNbtn;
        final JRadioButton persian;
        final JRadioButton english;
        final JRadioButton arabic;
		
		public CustomerQB (){
			super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			
			final JRadioButton addQues = new JRadioButton("اضافه کردن سوال",true);
			final JRadioButton editQues = new JRadioButton("ویرایش سوال");
			final JRadioButton removeQues = new JRadioButton("حذف سوال");
			
			addQues.setMnemonic(KeyEvent.VK_C);
			editQues.setMnemonic(KeyEvent.VK_M);
			removeQues.setMnemonic(KeyEvent.VK_P);

			addQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	listPanel.setVisible(false);
					befshowpanel.setVisible(true);
					fnlBtnPanel.setVisible(true);
		         }           
		      });

			editQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
					listPanel.setVisible(true);
					fnlBtnPanel.setVisible(true);
					editQbtn.setVisible(true);
					editNbtn.setVisible(true);
		         }           
		      });

			removeQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
					listPanel.setVisible(true);
					fnlBtnPanel.setVisible(true);
					editQbtn.setVisible(false);
					editNbtn.setVisible(false);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(addQues);
		      group.add(editQues);
		      group.add(removeQues);

		      slct.add(addQues);
		      slct.add(editQues);
		      slct.add(removeQues); 
		      basepanel.add(slct);
		    
                      befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel = new JPanel();
                    radioBPanel.setLayout(new BoxLayout(radioBPanel, BoxLayout.Y_AXIS));
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

	      radioBPanel.add(persian);
	      radioBPanel.add(english);
	      radioBPanel.add(arabic); 
	    befshowpanel.add(radioBPanel, BorderLayout.NORTH);
                      
		     JPanel showPanel = new JPanel();
			    showPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			    JPanel Ques = new JPanel();
			    Ques.setLayout(new BoxLayout(Ques, BoxLayout.Y_AXIS));
			    Ques.add(new JLabel("متن سوال"));
			    questionTxt = new JTextField(10);
			    Ques.add(questionTxt);
			    
			    showPanel.add(Ques);
			    JPanel chs = new JPanel();
			    chs.setLayout(new BoxLayout(chs, BoxLayout.Y_AXIS));
			    chs.add(new JLabel("تعداد گزینه"));
			    chsNumber = new JComboBox(new String [] {"1", "2" , "3", "4", "5"});
			    chs.add(chsNumber);
			    showPanel.add(chs);
                            befshowpanel.add(showPanel);
				basepanel.add(befshowpanel);
			
				listPanel = new JPanel();
				listPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
				model = new DefaultListModel();
				nitem = new DefaultListModel();
				db.openDb();
				ResultSet rs = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES");
				try {
					while(rs.next()){
					     //Retrieve by column name
					     model.addElement(rs.getString("Question"));
					     nitem.addElement(rs.getString("Choices"));
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				db.closeDb();
				
				JPanel righcol = new JPanel();
				righcol.setLayout(new BoxLayout(righcol, BoxLayout.Y_AXIS));
                                JScrollPane scroll = new JScrollPane(new JLabel("متن سوال"));
                                scroll.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				righcol.add(scroll);
			    queslist = new JList(model);
                            queslist.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                            queslist.addListSelectionListener(new ListSelectionListener() {

                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                numlist.setSelectedIndex(queslist.getSelectedIndex());
                            }
                        });
			    righcol.add(new JScrollPane(queslist));
			    listPanel.add(righcol);
			    editQbtn = new JButton("ویرایش سوال");
			    editQbtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String oldQ = queslist.getSelectedValue().toString();
						String newQ = JOptionPane.showInputDialog(null, "تغییر سوال", oldQ);
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_QUES "
									  +"SET Question = '"+newQ +"' "
									  +"WHERE CAST(Question AS VARCHAR(128)) = '"+oldQ+"'"; 
						if(db.updateDb(query)){
							System.out.println("added...!");
						}else{
							System.out.println("oooops...!");
						}
						db.closeDb();
						
					}
				});
			    listPanel.add(editQbtn);
			    
			    JPanel leftcol = new JPanel();
			    leftcol.setLayout(new BoxLayout(leftcol, BoxLayout.Y_AXIS));
                            JScrollPane scroll2 = new JScrollPane(new JLabel("تعداد گزینه ها"));
                            scroll2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			    leftcol.add(scroll2);
			    numlist = new JList(nitem);
			    leftcol.add(new JScrollPane(numlist));
			    listPanel.add(leftcol);
			    listPanel.add(new JLabel()); 
			    editNbtn = new JButton("ویرایش گزینه ها");
			    editNbtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String itsQ = (String)model.elementAt(numlist.getSelectedIndex());
						String newN = JOptionPane.showInputDialog(null, "تغییر سوال", numlist.getSelectedValue().toString());
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_QUES "
									  +"SET Choices = '"+newN +"' "
									  +"WHERE CAST(Question AS VARCHAR(128)) = '"+itsQ+"'"; 
						if(db.updateDb(query)){
							System.out.println("added...!");
						}else{
							System.out.println("oooops...!");
						}
						db.closeDb();
						
					}
				});
			    listPanel.add(editNbtn);
			    listPanel.setVisible(false);
			    basepanel.add(listPanel);
			    
			    /*JEditorPane editpan = new JEditorPane();
			    basepanel.add(editpan);*/
				
				fnlBtnPanel = new JPanel();
				fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
				JButton fnlAksept = new JButton("تایید نهایی");
				fnlAksept.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String temp = questionTxt.getText();
						if(befshowpanel.isVisible() && !temp.isEmpty()){
							db.openDb();
                                                        int lang = 0;
                                                if(persian.isSelected()){
                                                    lang = 0;
                                                }else if(english.isSelected()){
                                                    lang = 1;
                                                }else if(arabic.isSelected()){
                                                    lang = 2;
                                                }
                                                        
							String query = "INSERT INTO PAYDARAN.TABLE_QUES (QUESTION, ANSWERER, CHOICES, LANGUAGE) VALUES ('"+temp+"','مشتری',"+chsNumber.getSelectedItem()+", "+lang+")"; 
							if(db.insertDb(query)){
								System.out.println("added...!");
                                                                questionTxt.setText("");
                                                                model.addElement(temp);
                                                                nitem.addElement(chsNumber.getSelectedItem());
                                                                JOptionPane.showMessageDialog(null, "سوال اضافه شد");
							}else{
								System.out.println("oooops...!");
							}
							db.closeDb();
						}else if(listPanel.isVisible() && editQues.isSelected()){
							
						}else if(listPanel.isVisible() && removeQues.isSelected()){
							String temp2;
							temp2 = queslist.getSelectedValue().toString();
							db.openDb();
							String query = "DELETE FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(128)) = '"+temp2+"'"; 
							if(db.insertDb(query)){
								System.out.println("removed...!");
                                                                nitem.removeElementAt(queslist.getSelectedIndex());
                                                                model.removeElement(temp2);
							}else{
								System.out.println("oooops...!");
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

/*** ConnectionPanel.java ***/
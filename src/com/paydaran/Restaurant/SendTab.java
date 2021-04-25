package com.paydaran.Restaurant;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

//import sun.io.CharToByteASCII;
//import sun.io.CharToByteUTF8;

public class SendTab
extends JPanel
{
	public static JTextField		s_snumberTextField;
	private JTextField		s_snumber2TextField;
	private JTextField		s_hourTextField;
	private JTextField		s_minutTextField;
	private JTextField		btw_minutTextField;
	private JTextField		btw_secondTextField;
        private JTextField		client_idTextField;
	private JComboBox		s_kindComboBox; 
	String str[] = new String [] {" ","سوال فوری" , "سناریو"};
	private JButton sendSBtn;
        private JButton sendRSMSBtn;
	JPanel qlistPanel;
	JPanel sListPanel;
	JPanel addeqpanel;
        JPanel scadulPanle;
	Vector<String> qitem;
	Vector<String> q_id;
        Vector<String> lang;
	Vector<String> nitem;
	Vector<Integer> qNum;
	Vector<Integer> sid;
	Vector<String> choices;
        Vector<String> SQ_ID;
        Vector<Integer> Lang_ID;
	Database db = new Database();
	JList queslist;
	JList sList;
	JList qnumList;
	JList numlist;
	SerialComm conn ;
	DefaultListModel model;
	DefaultListModel question;

	public SendTab(SerialComm sc )
	{
		conn = sc;
		//try {
			//conn.connect("COM1");
		//} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		//}
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		
		JPanel slct = new JPanel();
		slct.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		slct.add(new JLabel("نوع پیام"));
		slct.add(Box.createHorizontalGlue());
		s_kindComboBox = new JComboBox(str);
		slct.add(s_kindComboBox);
		slct.add(Box.createHorizontalGlue());
		slct.add(Box.createHorizontalGlue());

		/*slct.add(new JLabel("شماره سناریو"));
		slct.add(Box.createHorizontalGlue());
		s_snumberTextField = new JTextField(5);
		slct.add(s_snumberTextField);*/
		slct.add(Box.createHorizontalGlue());
		slct.add(Box.createHorizontalGlue());

		JButton applyButton = new JButton("تایید");
		applyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(s_kindComboBox.getSelectedIndex() == 1){
					qlistPanel.setVisible(true);
					sListPanel.setVisible(false);
                                        sendRSMSBtn.setVisible(true);
                                        sendSBtn.setVisible(false);
                                        scadulPanle.setVisible(false);
				}else if(s_kindComboBox.getSelectedIndex() == 2){
					qlistPanel.setVisible(false);
					sListPanel.setVisible(true);
                                        sendRSMSBtn.setVisible(false);
                                        sendSBtn.setVisible(true);
                                        scadulPanle.setVisible(true);
				}
				
			}
		});
		slct.add(applyButton);
		topPanel.add(slct);
		this.add(topPanel, BorderLayout.CENTER);
		
		qlistPanel = new JPanel();
		qlistPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		qitem = new Vector<String>();
		q_id = new Vector<String>();
                lang = new Vector<String>();
		nitem = new Vector<String>();
		db.openDb();
		ResultSet rs = db.createQuery("SELECT Q_id, Question, Choices, LANGUAGE FROM Table_Ques");
		try {
			while(rs.next()){
			     //Retrieve by column name
				 q_id.add(rs.getString("Q_id"));
			     qitem.add(rs.getString("Question"));
			     nitem.add(rs.getString("Choices"));
                             lang.add(rs.getString("LANGUAGE"));
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeDb();
		
		JPanel qrighcol = new JPanel();
		qrighcol.setLayout(new BoxLayout(qrighcol, BoxLayout.Y_AXIS));
                JScrollPane scroll = new JScrollPane(new JLabel("متن سوال"));
                        scroll.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		qrighcol.add(scroll);
	    queslist = new JList(qitem);
            queslist.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    /*queslist.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				System.out.println(q_id.elementAt(queslist.getSelectedIndex()));
				
			}
		});*/
	    qrighcol.add(new JScrollPane(queslist));
	    qlistPanel.add(qrighcol);
	    
	    JPanel qleftcol = new JPanel();
	    qleftcol.setLayout(new BoxLayout(qleftcol, BoxLayout.Y_AXIS));
            JScrollPane scroll2 = new JScrollPane(new JLabel("تعداد گزینه ها"));
                        scroll2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    qleftcol.add(scroll2);
	    numlist = new JList(nitem);
	    qleftcol.add(new JScrollPane(numlist));
	    qlistPanel.add(qleftcol);
	    qlistPanel.setVisible(false);
            this.add(qlistPanel, BorderLayout.CENTER);
		
            sListPanel = new JPanel();
            sListPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
	    model = new DefaultListModel();
	    qNum = new Vector<Integer>();
	    sid = new Vector<Integer>();
            Lang_ID = new Vector<Integer>();
	    db.openDb();
		ResultSet rs2 = db.createQuery("SELECT PAYDARAN.TABLE_SENARIO.S_name ,PAYDARAN.TABLE_SENARIO.S_id,PAYDARAN.TABLE_SENARIO.LANG, p.Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO LEFT JOIN" +
					" (" +
					" SELECT PAYDARAN.TABLE_SENARIO_Q.S_name , COUNT(Q_id) as Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO_Q" +
					" Group By PAYDARAN.TABLE_SENARIO_Q.S_name " +
					") as p" +
					" on PAYDARAN.TABLE_SENARIO.S_name = p.S_name");
		try {
			while(rs2.next()){
			     //Retrieve by column name
                            model.addElement(rs2.getString("S_name"));
                            sid.add(rs2.getInt("S_id"));
                            Lang_ID.add(rs2.getInt("LANG"));
			    qNum.add(rs2.getInt("Qnumber"));
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.closeDb();
	    JPanel righcol2 = new JPanel();
	    righcol2.setLayout(new BoxLayout(righcol2, BoxLayout.Y_AXIS));
            JScrollPane scroll3 = new JScrollPane(new JLabel("نام سناریو"));
                        scroll3.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    righcol2.add(scroll3);
	    sList = new JList(model);
            sList.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    choices = new Vector<String>();
            SQ_ID = new Vector<String>();
	    sList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
					addeqpanel.setVisible(true);
					question.clear();
                                        choices.clear();
                                        SQ_ID.clear();
					String senar = sList.getSelectedValue().toString();
					db.openDb();
					ResultSet rs3 = db.createQuery("SELECT Q_ID, Question, Choices" +
							" FROM PAYDARAN.TABLE_QUES" +
							" WHERE Q_id IN (SELECT Q_id" +
							" FROM PAYDARAN.TABLE_SENARIO_Q" +
							" WHERE S_name = '"+senar+"')");
					try {
						while(rs3.next()){
						     //Retrieve by column name
                                                        SQ_ID.add(rs3.getString("Q_ID"));
							question.addElement(rs3.getString("Question"));
							choices.add(rs3.getString("Choices"));                               
						  }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                                        //System.out.println(SQ_ID);
                                        //System.out.println(question);
                                        //System.out.println(choices);
					db.closeDb();
				//System.out.println(sid.get(sList.getSelectedIndex()));
			}
		});
	    
	    righcol2.add(new JScrollPane(sList));
	    sListPanel.add(righcol2);
	    
	    JPanel leftcol2 = new JPanel();
	    leftcol2.setLayout(new BoxLayout(leftcol2, BoxLayout.Y_AXIS));
            JScrollPane scroll4 = new JScrollPane(new JLabel("تعداد سوال"));
                        scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		leftcol2.add(scroll4);
	    qnumList = new JList(qNum);
	    leftcol2.add(new JScrollPane(qnumList));
	    sListPanel.add(leftcol2);
	    
	    addeqpanel = new JPanel();
	    addeqpanel.setLayout(new BoxLayout(addeqpanel, BoxLayout.Y_AXIS));
	    question = new DefaultListModel();
	    JList addedQ = new JList(question);
            addedQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            JScrollPane scroll5 = new JScrollPane(new JLabel("سوالات موجود در سناریو"));
                        scroll5.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    addeqpanel.add(scroll5);
	    addeqpanel.add(new JScrollPane(addedQ));
	    addeqpanel.setVisible(false);
	    sListPanel.add(addeqpanel);
	    sListPanel.setVisible(false);
	    this.add(sListPanel, BorderLayout.CENTER);
		
		scadulPanle = new JPanel();
		scadulPanle.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		
		JPanel sNumber = new JPanel();
		sNumber.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		sNumber.add(new JLabel("زمان یندی ارسال سناریو:"));
		//s_snumber2TextField = new JTextField(3);
		//sNumber.add(s_snumber2TextField);
		scadulPanle.add(sNumber);
		
		JPanel tValue = new JPanel();
		tValue.setLayout(new BoxLayout(tValue, BoxLayout.Y_AXIS));
		
		JPanel h = new JPanel();
		h.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		h.add(new JLabel("ساعت"));
		h.add(Box.createHorizontalGlue());
		s_hourTextField = new JTextField(3);
		h.add(s_hourTextField);
		h.add(Box.createHorizontalGlue());
		h.add(Box.createHorizontalGlue());
		h.add(new JLabel("فاصله زمانی بین سوالات دقیقه"));
		h.add(Box.createHorizontalGlue());
		btw_minutTextField = new JTextField(3);
		h.add(btw_minutTextField);
		tValue.add(h);
		
		JPanel i = new JPanel();
		i.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		i.add(new JLabel("دقیقه"));
		i.add(Box.createHorizontalGlue());
		s_minutTextField = new JTextField(3);
		i.add(s_minutTextField);
		i.add(Box.createHorizontalGlue());
		i.add(Box.createHorizontalGlue());
		i.add(new JLabel("فاصله زمانی بین سوالات ثانیه"));
		i.add(Box.createHorizontalGlue());
		btw_secondTextField = new JTextField(3);
		i.add(btw_secondTextField);
		tValue.add(i);
		
		scadulPanle.add(tValue);
                scadulPanle.setVisible(false);
		this.add(scadulPanle, BorderLayout.CENTER);
		
                JPanel idpanel = new JPanel();
		idpanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		idpanel.add(new JLabel("شناسه گیرنده"));
		idpanel.add(Box.createHorizontalGlue());
		client_idTextField = new JTextField(3);
		idpanel.add(client_idTextField);
                this.add(idpanel, BorderLayout.CENTER);
                
		JPanel btnPanel = new JPanel();
		btnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		sendSBtn = new JButton("ارسال سناریو");
		sendSBtn.addActionListener(new sendsenario());
                sendSBtn.setVisible(false);
                
		btnPanel.add(sendSBtn);
		btnPanel.add(Box.createHorizontalGlue());
		btnPanel.add(Box.createHorizontalGlue());
		sendRSMSBtn = new JButton("ارسال پیامک فوری");
		sendRSMSBtn.addActionListener(new sendSMS());
                sendRSMSBtn.setVisible(false);
		btnPanel.add(sendRSMSBtn);
		this.add(btnPanel, BorderLayout.CENTER);

		
		/*JPanel	buttonPanel = new JPanel();

		JButton applyButton2 = new JButton("Apply");
		applyButton2.setActionCommand("apply");
		applyButton2.addActionListener(this);
		buttonPanel.add(applyButton2);
		JButton resetButton = new JButton("Reset");
		resetButton.setActionCommand("reset");
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);

		add(buttonPanel, BorderLayout.SOUTH);

		reset();*/
	}
	
	InputVerifier inputverifire = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			final JTextComponent source = (JTextComponent) input;
			String text = source.getText();
			 if ((text.length() != 0)){
				 return true;
			 }
			 try {
				Integer.parseInt(text);
			} catch (Exception e) {
                            JOptionPane.showMessageDialog(source, "لطفا یک عدد وارد کنید", "خطا",
			              JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
	};
	
	public static byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte) v;
	    }
	    return b;
	  }
public class sendsenario implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
				if(sList.isSelectionEmpty())
					JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
                                else if(client_idTextField.getText().isEmpty()){
                                     JOptionPane.showMessageDialog(null, "لطفا شناسه گیرنده را وارد نمایید");   
                                }
                                    else{
                                    RestaurantMain.flag2 = false;
                                    String ID = client_idTextField.getText().toString();
                                    try {
						Thread.sleep(80);
					} catch (InterruptedException ey){
						// TODO Auto-generated catch block
						ey.printStackTrace();
					}
                                        //send prepacket
					int sendSid = sid.get(sList.getSelectedIndex());
					byte[] notice = new byte[8];
					notice[0] = hexStringToByteArray("7E") [0];
					notice[1] = Byte.parseByte(ID);//"1".getBytes()[0];
					notice[2] =  hexStringToByteArray("0E") [0];
					notice[3] = Byte.parseByte("4");
					notice[4] = Byte.parseByte(sendSid+"");
					//while(s_hourTextField.getText().isEmpty() || s_minutTextField.getText().isEmpty())
						//JOptionPane.showMessageDialog(null, "لطفا ساعت و دقیقه را وارد نمایید");	
					btw_minutTextField.setInputVerifier(inputverifire);
					btw_secondTextField.setInputVerifier(inputverifire);
					try {
						notice[5] = Byte.parseByte(btw_minutTextField.getText());
					} catch (Exception e2) {
						notice[5] = Byte.parseByte("0");
					}
					try {
						notice[6] = Byte.parseByte(btw_secondTextField.getText());
					} catch (Exception e3) {
						notice[6] = Byte.parseByte("0");
					}
                                        notice[7] = Byte.parseByte(Lang_ID.get(sList.getSelectedIndex())+"");
					conn.sendData(notice);
					System.out.println(new String(notice, 0, 7));
					try {
						Thread.sleep(80);
					} catch (InterruptedException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                                        
					//send senario question
					int qNumber = qNum.elementAt(sList.getSelectedIndex());
					for(int i = 0; i < qNumber; i++){
						byte [] currentSQ = question.elementAt(i).toString().getBytes();
                                                int packnum = 0;
                                                if(currentSQ.length%24 == 0)
                                                    packnum = (currentSQ.length/24);
                                                else
                                                    packnum = (currentSQ.length/24)+1;
						//byte [] section = new byte[32];
						Vector<Byte> sQSec = new Vector<Byte>();
						//section[0] = hexStringToByteArray("7E") [0];
						sQSec.add(0 , hexStringToByteArray("7E") [0]);
						//section[1] = Byte.parseByte("1");//"1".getBytes()[0];
						sQSec.add(1, Byte.parseByte(ID));
						//section[2] = Byte.parseByte("8");//.getBytes()[0];
						sQSec.add(2, Byte.parseByte("8"));
						//section[3] = Byte.parseByte(currentSQ.length+4+"");
						sQSec.add(3, Byte.parseByte(currentSQ.length+(4*packnum)+""));//bug:age y soal toolesh bishtar az 28 byte boood?!!!
						//section[4] = Byte.parseByte(i+"");
						sQSec.add(4, Byte.parseByte(i+1+""));
						//section[5] = Byte.parseByte(choices.elementAt(i));
						sQSec.add(5, Byte.parseByte(choices.elementAt(i)));
						//section[6] = Byte.parseByte(qNumber+"");
						sQSec.add(6, Byte.parseByte(qNumber+""));
						//int packnum = (currentSQ.length/24)+1; 
						int q = 0 , j = 0;
						for(int f = 0; f< packnum ; f++){ //bug(fixed ;)) : age tool soal kam bashe in asan ejra nemishe!!!!!!!!!
							//section[7] = Byte.parseByte(f+1+"");
							sQSec.add(7, Byte.parseByte(f+1+""));
							for(j = 8 ; j<32 && q<currentSQ.length; j++){
								//section[j] = currentSQ[q];
								sQSec.add(j, currentSQ[q]);
								q++;
							}
							//while(j<32){
								//section[j] = Byte.parseByte("0");
								//j++;
							//}
							
							sQSec.trimToSize();
							byte [] section = new byte[sQSec.size()];
							for(int w = 0 ; w< sQSec.size() ; w++){
								section[w] = sQSec.elementAt(w);
							}
							conn.sendData(section);
							System.out.println(new String(section, 0, sQSec.size()));
							//clean vector for next proc
							for(int k = sQSec.size() - 1 ; k > 6 ; k--){
								sQSec.removeElementAt(k);
							}
							try {
								Thread.sleep(80);
							} catch (InterruptedException e4) {
								// TODO Auto-generated catch block
								e4.printStackTrace();
							}
						}
					}
                                        
                                        //send Q_ID
                                        Vector<Byte> sQ_idSec = new Vector<Byte>();
						sQ_idSec.add(0 , hexStringToByteArray("7E") [0]);
						sQ_idSec.add(1, Byte.parseByte(ID));
						sQ_idSec.add(2, hexStringToByteArray("0B") [0]);
						sQ_idSec.add(3, Byte.parseByte(qNumber+""));
                                                int packnum = 0;
                                                if(qNumber%28 == 0)
                                                    packnum = (qNumber/28);
                                                else
                                                    packnum = (qNumber/28)+1;
						int q = 0 , j = 0;
						for(int f = 0; f< packnum ; f++){ //bug(fixed ;)) : age tool soal kam bashe in asan ejra nemishe!!!!!!!!!
							//section[7] = Byte.parseByte(f+1+"");
							for(j = 4 ; j<32 && q<qNumber; j++){
								//section[j] = currentSQ[q];
								sQ_idSec.add(j, Byte.parseByte(SQ_ID.elementAt(q)));
								q++;
							}
							//while(j<32){
								//section[j] = Byte.parseByte("0");
								//j++;
							//}
							
							sQ_idSec.trimToSize();
							byte [] section2 = new byte[sQ_idSec.size()];
							for(int w = 0 ; w< sQ_idSec.size() ; w++){
								section2[w] = sQ_idSec.elementAt(w);
							}
							conn.sendData(section2);
							System.out.println(new String(section2, 0, sQ_idSec.size()));
							//clean vector for next proc
							for(int k = sQ_idSec.size() - 1 ; k > 6 ; k--){
								sQ_idSec.removeElementAt(k);
							}
							try {
								Thread.sleep(80);
							} catch (InterruptedException e5) {
								// TODO Auto-generated catch block
								e5.printStackTrace();
							}
						}
                                        RestaurantMain.flag2 = true;
				}
			
            }
    
        }
public class sendSMS implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
				if(queslist.isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
				}                               
				else{ 
                                    RestaurantMain.flag2 = false;
                                    String ID = client_idTextField.getText().toString();
                                    try {
						Thread.sleep(80);
					} catch (InterruptedException ex){
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
                                    //for(int f = 1 ; f<11 ; f++){
					String sendQ = queslist.getSelectedValue().toString();
					String senQnum = nitem.elementAt(queslist.getSelectedIndex());
					byte [] data = sendQ.getBytes();
					//byte [] section = new byte[32];
					Vector<Byte> sec = new Vector<Byte>();
					//section[0] = hexStringToByteArray("7E") [0];
					sec.add(0, hexStringToByteArray("7E") [0]);
					//section[1] = Byte.parseByte("1");//.getBytes()[0];
					sec.add(1, Byte.parseByte(ID));
					//section[2] = Byte.parseByte("5");//"5".getBytes()[0];
					sec.add(2, Byte.parseByte("5"));
					int packnum = 0;
                                        if(data.length%27 == 0)
                                            packnum = data.length/27;
                                        else 
                                            packnum = (data.length/27)+1;
					//section[3] =  Byte.parseByte(data.length+packnum +"");
					sec.add(3, Byte.parseByte(data.length+packnum +""));
					int q = 0 , j = 0;
					for(int i = 0; i< packnum ; i++){ //bug : age tool soal kam bashe in asan ejra nemishe!!!!!!!!!
						//section[4] = Byte.parseByte(i+1+"");
						sec.add(4, Byte.parseByte(i+1+""));
						for(j = 5 ; j<32 && q<data.length; j++){
							sec.add(j, data[q]);
							//section[j] = data[q];
							q++;
						}
						/*while(j<32){
							section[j] = Byte.parseByte("0");
							j++;
						}*/			
						sec.trimToSize();
						byte [] section = new byte [sec.size()];
						for(int w = 0 ; w< sec.size() ; w++){
							section[w] = sec.elementAt(w);
						}
						conn.sendData(section);
						System.out.println(new String(section, 0, sec.size()));
						//clean vector for next package
						for(int k = sec.size() - 1 ; k > 3 ; k--){
							sec.removeElementAt(k);
						}
						try {
							Thread.sleep(90);
						} catch (InterruptedException esms) {							
							esms.printStackTrace();
						}
						
					}
					//byte [] qnum = new byte[32];
					Vector<Byte> qidandchoice = new Vector<Byte>();
					//qnum[0] = hexStringToByteArray("7E") [0];
					qidandchoice.add(0, hexStringToByteArray("7E") [0]);
					//qnum[1] = Byte.parseByte("1");//"1".getBytes()[0];
					qidandchoice.add(1, Byte.parseByte(ID));
					//qnum[2] = Byte.parseByte("10");//"10".getBytes()[0];
					qidandchoice.add(2, Byte.parseByte("10"));
					//qnum[3] = Byte.parseByte("2");
					qidandchoice.add(3, Byte.parseByte("3"));
					//qnum[4] = Byte.parseByte(q_id.elementAt(queslist.getSelectedIndex()));
					qidandchoice.add(4, Byte.parseByte(q_id.elementAt(queslist.getSelectedIndex())));
					//qnum[5] = Byte.parseByte(senQnum);// senQnum.getBytes() [0];
					qidandchoice.add(5, Byte.parseByte(senQnum));
                                        qidandchoice.add(6, Byte.parseByte(lang.elementAt(queslist.getSelectedIndex())));
					qidandchoice.trimToSize();
					byte [] qnum = new byte[qidandchoice.size()];
					for(int ei = 0 ; ei< qidandchoice.size() ; ei++){
						qnum[ei] = qidandchoice.elementAt(ei);
					}
					System.out.println(new String(qnum, 0, qidandchoice.size()));
					conn.sendData(qnum);
                                        try {
							Thread.sleep(90);
						} catch (InterruptedException eu) {							
							eu.printStackTrace();
						}
				//} 
                                        RestaurantMain.flag2 = true;
                             }
			
        }
    
        }
   } 
package com.paydaran.Restaurant;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;

public class DiagramTab
extends JPanel
{
	
	private JTextField		d_qnumberTextField;
	private JTextField		d_snumberTextField;
        private JTextField		d_idTextField;
	private JComboBox		d_kindComboBox;
	private JTextArea recieveData;
        JLabel message;
        JPanel fp;
        JPanel sp;
        JPanel showPanel;
        JPanel topChartPanel;
        JPanel QinS;
        ChartPanel chartPanel;
        JFreeChart barChart;
        JList senarioQ;
        String recievedData;
        String str[] = new String [] {" ", "سوال ثابت","سوال فوری" , "سناریو"};
        Database db = new Database();
        int bad, avrg, good;
        String s1, s2, s3;
        double sat, unsat, avg;
        DefaultListModel model;
        
	public DiagramTab( )
	{
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                JPanel basepanel = new JPanel();
                basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
                
		JPanel topPanel = new JPanel();
		JPanel gotInputPanle = new JPanel();
		gotInputPanle.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		
		JPanel slct = new JPanel();
		slct.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		slct.add(new JLabel("نوع پیام"));
		slct.add(Box.createHorizontalGlue());
		d_kindComboBox = new JComboBox(str);
                d_kindComboBox.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        switch (d_kindComboBox.getSelectedIndex())
                        {
                            case 0:
                                sp.setVisible(false);
                                fp.setVisible(false); 
                            case 1 :
                                sp.setVisible(false);
                                fp.setVisible(false);                                   
                                break;
                            case 2 :
                                fp.setVisible(true);
                                sp.setVisible(false);
                                break;
                            case 3 :
                                fp.setVisible(false);
                                sp.setVisible(true);
                                break;
                            default :
                                break;
                        }
                    }
                }
            });
		slct.add(d_kindComboBox);
		slct.add(Box.createHorizontalGlue());
		slct.add(Box.createHorizontalGlue());
		gotInputPanle.add(slct);
		
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
		fp = new JPanel();
		fp.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		fp.add(new JLabel("شماره سوال"));
		fp.add(Box.createHorizontalGlue());
		d_qnumberTextField = new JTextField(3);
		fp.add(d_qnumberTextField);
                fp.setVisible(false);
		detailPanel.add(fp);
		
		sp = new JPanel();
		sp.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		sp.add(new JLabel("شماره سناریو"));
		sp.add(Box.createHorizontalGlue());
		d_snumberTextField = new JTextField(3);
		sp.add(d_snumberTextField);
                sp.setVisible(false);
		detailPanel.add(sp);
		
		gotInputPanle.add(detailPanel);
                gotInputPanle.add(Box.createHorizontalGlue());

		topPanel.add(gotInputPanle);
		basepanel.add(topPanel, BorderLayout.CENTER);
		
		showPanel = new JPanel();
                showPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                QinS = new JPanel();
		QinS.setLayout(new BoxLayout(QinS, BoxLayout.Y_AXIS));
		model = new DefaultListModel();
		senarioQ = new JList(model);
                senarioQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JScrollPane scroll4 = new JScrollPane(new JLabel("سوالات موجود در سناریو"));
                scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		QinS.add(scroll4);
		QinS.add(new JScrollPane(senarioQ));
		QinS.setVisible(false);
		showPanel.add(QinS);
                
                message = new JLabel("نموداری انتخاب نشده است.");
                showPanel.add(message);
                topChartPanel = new JPanel();
                showPanel.add(topChartPanel);
                //showPanel.setVisible(false);
		basepanel.add(showPanel, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		JButton sendSBtn = new JButton("نمایش نمودار");
                sendSBtn.addActionListener(new clickedbtn());
		btnPanel.add(sendSBtn);
		btnPanel.add(Box.createHorizontalGlue());
		btnPanel.add(Box.createHorizontalGlue());
		JButton sendRSMSBtn = new JButton("به روز رسانی نمودار");
                sendRSMSBtn.addActionListener(new makediagram());
		btnPanel.add(sendRSMSBtn);
		basepanel.add(btnPanel, BorderLayout.CENTER);
                this.add(basepanel, BorderLayout.CENTER);
                
                JPanel hamel = new JPanel();
                hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
                hamel.setAlignmentX(CENTER_ALIGNMENT);
                this.add(hamel, BorderLayout.CENTER);
                
                /*  for test, you can remove it!
                JPanel clientId = new JPanel();
                clientId.setLayout(new BoxLayout(clientId, BoxLayout.Y_AXIS));
                JPanel subCId = new JPanel();
                subCId.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		subCId.add(new JLabel("شماره کلاینت"));
                d_idTextField = new JTextField(3);
                subCId.add(d_idTextField);
                clientId.add(subCId);
                JButton answerReq = new JButton("ارسال درخواست جواب");
                answerReq.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                       if(d_idTextField.getText().toString().equals("")){
                           JOptionPane.showMessageDialog(null, "لطفا شناسه کلاینت را وارد نمایید");
                       }
                       else{
                          // try {
                               String clId = d_idTextField.getText().toString();
                               byte[] permission = new byte[5];
                               permission[0] = hexStringToByteArray("7E") [0];
                               permission[1] = Byte.parseByte(clId);
                               permission[2] = Byte.parseByte("2");
                               permission[3] = Byte.parseByte("1");
                               permission[4] = Byte.parseByte("1");
                               
                              Byte id = new Byte(permission[1]);
                              //id.toString();
                               
                               serialconn.sendData(permission);
                               //System.out.println(id.toString());
                               serialconn.recievedata(5000);
                              //timer task! /*java.util.Timer timer = new java.util.Timer();
                             //  timer.schedule(new TimerTask() {
                                //   
                                  // @Override
                                 //  public void run() {
                                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                     //  recievedData = serialconn.recievedata();
                                    //   System.out.println(recievedData);
                                 //  }
                              // }, 5000);
                              // Thread.sleep(6000);
                              // timer.cancel();
                          // } catch (InterruptedException ex) {
                              // Logger.getLogger(DiagramTab.class.getName()).log(Level.SEVERE, null, ex);
                           //}
                       }
                    }
                });
                clientId.add(answerReq);
                showPanel.add(clientId);
                recieveData = new JTextArea(10, 10);
                //recieveData.setText(recievedData);
                showPanel.add(recieveData);
                */
                
                
                //Diagram
                if(PassWordDialog.accessD || PassWordDialog.isadmin){
                    basepanel.setVisible(true);
                hamel.setVisible(false);
        }else{
            basepanel.setVisible(false);
            hamel.setVisible(true);
            }
		
	}       

        private CategoryDataset createDataset(long [] chartvalue )
   {
      //final String fiat = "FIAT";        
      final String audi = "میزان رضایت";        
     //final String ford = "FORD";        
      final String bad = "ناراضی";        
      final String good = "راضی";        
      final String average = "متوسط";        
      //final String safety = "safety";        
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset(); 
      if(chartvalue[0] == 1){
          for(int i = 1 ; i < chartvalue.length ; i++){
            dataset.addValue( chartvalue[i] , audi , "گزینه "+i );
          }
          
        /*dataset.addValue( unsat , audi , bad );        
        dataset.addValue( avg , audi , average );       
        dataset.addValue( sat , audi , good );*/
      }else{
          for(int i = 1 ; i < chartvalue.length ; i++){
            dataset.addValue( chartvalue[i] , audi , "سوال "+i );
      }
      }
             
           
      //dataset.addValue( 4.0 , audi , safety );
      
      /*dataset.addValue( 1.0 , fiat , speed );        
      dataset.addValue( 3.0 , fiat , userrating );        
      dataset.addValue( 5.0 , fiat , millage ); 
      //dataset.addValue( 5.0 , fiat , safety );  

      dataset.addValue( 4.0 , ford , speed );        
      dataset.addValue( 2.0 , ford , userrating );        
      dataset.addValue( 3.0 , ford , millage );        
      //dataset.addValue( 6.0 , ford , safety );*/           
      return dataset; 
   }
        
    private static class makediagram implements ActionListener {
        Database db = new Database();
        public makediagram() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            /*db.openDb();
                            String query = "INSERT INTO PAYDARAN.TABLE_ANSWER (Q_ID, S_ID, ANSWER, A_KIND) VALUES (5, 1,4 ,2)"; 
                            if(db.insertDb(query)){
				System.out.println("data added to database...!");		
				}else{
				System.out.println("data don't added to database...!");
				}
                            db.closeDb();*/
        }
    }
    
    private class clickedbtn implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(d_kindComboBox.getSelectedIndex()== 0){
                        JOptionPane.showMessageDialog(null, "لطفا نوع پیام را انتخاب نمایید", "خطا",
			              JOptionPane.ERROR_MESSAGE);
                    }else if(d_kindComboBox.getSelectedIndex()== 2 && d_qnumberTextField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "لطفا شماره سوال را وارد نمایید", "خطا",
			              JOptionPane.ERROR_MESSAGE);
                    }else if(d_kindComboBox.getSelectedIndex()== 3 && d_snumberTextField.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "لطفا شماره سناریو را وارد نمایید", "خطا",
			              JOptionPane.ERROR_MESSAGE);
                    }else if(d_kindComboBox.getSelectedIndex()== 1){
                        db.openDb();
                            
                        db.closeDb();
                    }else if(d_kindComboBox.getSelectedIndex()== 2 && !d_qnumberTextField.getText().isEmpty()){
                        int choices = 0;
                        db.openDb();
                             ResultSet rs1 = db.createQuery("SELECT PAYDARAN.TABLE_QUES.CHOICES"
                                     + " FROM PAYDARAN.TABLE_QUES"
                                     + " WHERE PAYDARAN.TABLE_QUES.Q_ID = "+d_qnumberTextField.getText().toString());
                                try {
                                    while(rs1.next()){
                                        //Retrieve by column name
                                        choices = rs1.getInt("CHOICES");               
                                        //System.out.println(choices);
                                      }
                                } catch (SQLException exce) {
                            	// TODO Auto-generated catch block
                            	exce.printStackTrace();
                            }
                                long [] chartValue2 = new long [choices+1];
                                chartValue2[0] = 1 ; //its SMS
                                for(int i = 1 ; i<=choices ; i++){
                                    ResultSet rs2 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = "+i+" and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs2.next()){
				     //Retrieve by column name
                                        chartValue2[i] = rs2.getInt("ANS");
                                        //System.out.println(unsat);
                                    }
                                } catch (SQLException exce) {
                                    // TODO Auto-generated catch block
                                    exce.printStackTrace();
                                }
                                }
                        /*switch(choices)
                        {
                            case 2:
                                //number of 1 answer:
                                ResultSet rs2 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 1 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs2.next()){
				     //Retrieve by column name
                                        unsat = rs2.getInt("ANS");
                                        //System.out.println(unsat);
                                    }
                                } catch (SQLException exce) {
                                    // TODO Auto-generated catch block
                                    exce.printStackTrace();
                                }
                                
                                //number of 2 answer:
                                ResultSet rs3 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 2 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs3.next()){
				     //Retrieve by column name
                                        sat = rs3.getInt("ANS");
                                        //System.out.println(sat);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                break;
                            case 3:
                                //number of 1 answer:
                                ResultSet rs4 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 1 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs4.next()){
				     //Retrieve by column name
                                        unsat = rs4.getInt("ANS");
                                        //System.out.println(unsat);
                                    }
                                } catch (SQLException exce) {
                                    // TODO Auto-generated catch block
                                    exce.printStackTrace();
                                }
                                
                                //number of 2 answer:
                                ResultSet rs5 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 2 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs5.next()){
				     //Retrieve by column name
                                        avg = rs5.getInt("ANS");
                                        //System.out.println(avg);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                
                                //number of 3 answer:
                                ResultSet rs6 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 3 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs6.next()){
				     //Retrieve by column name
                                        sat = rs6.getInt("ANS");
                                        //System.out.println(sat);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                
                                break;
                            case 4:
                                //number of 1 answer:
                                ResultSet rs7 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 1 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs7.next()){
				     //Retrieve by column name
                                        unsat = rs7.getInt("ANS");
                                        //System.out.println(unsat);
                                    }
                                } catch (SQLException exce) {
                                    // TODO Auto-generated catch block
                                    exce.printStackTrace();
                                }
                                
                                //number of 2,3 answer:
                                ResultSet rs8 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE (PAYDARAN.TABLE_ANSWER.ANSWER = 2 or PAYDARAN.TABLE_ANSWER.ANSWER = 3) and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs8.next()){
				     //Retrieve by column name
                                        avg = rs8.getInt("ANS");
                                        //System.out.println(avg);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                
                                //number of 4 answer:
                                ResultSet rs9 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 4 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs9.next()){
				     //Retrieve by column name
                                        sat = rs9.getInt("ANS");
                                        //System.out.println(sat);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                
                                break;
                            case 5:
                                //number of 1 answer:
                                ResultSet rs10 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 1 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs10.next()){
				     //Retrieve by column name
                                        unsat = rs10.getInt("ANS");
                                        //System.out.println(unsat);
                                    }
                                } catch (SQLException exce) {
                                    // TODO Auto-generated catch block
                                    exce.printStackTrace();
                                }
                                
                                //number of 2,3,4 answer:
                                ResultSet rs11 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE (PAYDARAN.TABLE_ANSWER.ANSWER = 2 or PAYDARAN.TABLE_ANSWER.ANSWER = 3 or PAYDARAN.TABLE_ANSWER.ANSWER = 4) and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs11.next()){
				     //Retrieve by column name
                                        avg = rs11.getInt("ANS");
                                        //System.out.println(avg);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                
                                //number of 5 answer:
                                ResultSet rs12 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = 4 and PAYDARAN.TABLE_ANSWER.Q_ID = "+d_qnumberTextField.getText().toString()+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs12.next()){
				     //Retrieve by column name
                                        sat = rs12.getInt("ANS");
                                        //System.out.println(sat);
                                    }
                                } catch (SQLException e2) {
                                    // TODO Auto-generated catch block
                                    e2.printStackTrace();
                                }
                                
                                break;
                            default :
                                break;
                        }*/
                        db.closeDb();
                        //remove current chart
                        topChartPanel.removeAll();
                        topChartPanel.revalidate();                
                        JFreeChart barChart = ChartFactory.createBarChart(
                        "نمودار سوال فوری",           
                        "وضعیت",            
                        "تعداد",            
                        createDataset(chartValue2),          
                        PlotOrientation.VERTICAL,           
                        true, true, false);
                        barChart.removeLegend();
                        chartPanel = new ChartPanel( barChart );        
                        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
                        topChartPanel.setLayout(new BorderLayout());
                        message.setVisible(false);
                        QinS.setVisible(false);
                        chartPanel.setVisible(true);
                        
                        topChartPanel.add(chartPanel);
                        topChartPanel.repaint();
                        
                    }else if(d_kindComboBox.getSelectedIndex()== 3 && !d_snumberTextField.getText().isEmpty()){                   
                        model.clear();			
                        String sname = new String();
                        db.openDb();
			ResultSet srs1 = db.createQuery("SELECT S_NAME FROM PAYDARAN.TABLE_SENARIO"
                                + " WHERE S_ID = "+d_snumberTextField.getText().toString());
			try {
				while(srs1.next()){
				     //Retrieve by column name
					sname = srs1.getString("S_NAME");
                                        //System.out.println(sname);
				  }
			} catch (SQLException srs) {
				// TODO Auto-generated catch block
				srs.printStackTrace();
			}
                        
			ResultSet srs2 = db.createQuery("SELECT Question" +
                                                       " FROM PAYDARAN.TABLE_QUES" +
                                                       " WHERE Q_id IN (SELECT Q_id" +
                                                       " FROM PAYDARAN.TABLE_SENARIO_Q" +
                                                       " WHERE S_NAME = '"+sname+"')");
						try {
							while(srs2.next()){
							     //Retrieve by column name
								model.addElement(srs2.getString("Question"));
							  }
                                                        //System.out.println(model);
						} catch (SQLException esrs2) {
							// TODO Auto-generated catch block
							esrs2.printStackTrace();
						}
                                                
                        int sSize = model.getSize();                        
                        long [] chartValue = new long [sSize+1]; 
                        chartValue[0] = 2; //its Senario
                        for(int i = 1 ; i <= sSize; i++){
                            int currQid = 0;
                            int answererNum = 0;
                            int choices = 0;
                            int maxans;
                            int sumOfAnswer = 0;
                            ResultSet srs3 = db.createQuery("SELECT Q_ID, CHOICES FROM PAYDARAN.TABLE_QUES"
                                + " WHERE CAST(QUESTION AS VARCHAR(32672)) = '"+model.get(i-1).toString()+"'");
                            try {
				while(srs3.next()){
				     //Retrieve by column name
					currQid = srs3.getInt("Q_ID");
                                        choices = srs3.getInt("CHOICES");
                                        //System.out.println(choices);
				  }
                            } catch (SQLException esrs3) {
				// TODO Auto-generated catch block
				esrs3.printStackTrace();
                            }
                            
                            ResultSet srs4 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.Q_ID = "+currQid+" and PAYDARAN.TABLE_ANSWER.S_ID = "+d_snumberTextField.getText().toString()
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID");
                            try {
				while(srs4.next()){
				     //Retrieve by column name
					answererNum = srs4.getInt("ANS");
                                        //System.out.println(answererNum);
				  }
                            } catch (SQLException esrs4) {
				// TODO Auto-generated catch block
				esrs4.printStackTrace();
                            }
                            maxans = answererNum * choices;    
                            ResultSet srs5 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , sum(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.Q_ID = "+currQid+" and PAYDARAN.TABLE_ANSWER.S_ID = "+d_snumberTextField.getText().toString()
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID");
                            try {
				while(srs5.next()){
				     //Retrieve by column name
					sumOfAnswer = srs5.getInt("ANS");
                                        //System.out.println(sumOfAnswer);
                                        //System.out.println(maxans);
				  }
                            } catch (SQLException esrs5) {
				// TODO Auto-generated catch block
				esrs5.printStackTrace();
                            }
                            if(maxans == 0)
                                chartValue[i] = 0;
                            else
                                chartValue[i] = (sumOfAnswer*100/maxans) ;
                            currQid = 0;
                            answererNum = 0;
                            choices = 0;
                            maxans = 0;
                            sumOfAnswer = 0;
                            
                        }                        
                        db.closeDb();
                        //System.out.println(chartValue.length);
                        //remove current chart
                        topChartPanel.removeAll();
                        topChartPanel.revalidate();                
                        JFreeChart barChart = ChartFactory.createBarChart(
                        "نمودار سناریو",           
                        "وضعیت",            
                        "تعداد",            
                        createDataset(chartValue),          
                        PlotOrientation.VERTICAL,           
                        true, true, false);
                        barChart.removeLegend();
                        chartPanel = new ChartPanel( barChart );        
                        chartPanel.setPreferredSize(new java.awt.Dimension( 460 , 267 ) );
                        topChartPanel.setLayout(new BorderLayout());                      
                        chartPanel.setVisible(true);
                        QinS.setVisible(true);
                        message.setVisible(false);
                        topChartPanel.add(chartPanel);
                        topChartPanel.repaint();   
                    }                
                
        }
        
    }
        
} 



/*** InfoPanel.java ***/
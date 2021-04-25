package com.paydaran.Restaurant;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AccessControlTab extends JPanel{
    DefaultTableModel model;
    Database db = new Database();
	private JTable table;
        String str[] = new String [] {"مدیر ارشد","مدیر" , "کارشناس"};
        private JComboBox		semat;
        private JTextField              username;
        private JTextField              password;
        private JCheckBox               Q_Bqnk_Access;
        private JCheckBox               S_Bqnk_Access;
        private JCheckBox               Diagram_Access;
                JPanel                  addpnl;
                JScrollPane             addpnlscrollPane;
        final JRadioButton              neewAccess;
	final JRadioButton              editAccess;
	final JRadioButton              removeAccess;
	
	public AccessControlTab (){
		super();
 
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel basepanel = new JPanel();
		basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
				
		JPanel radioBPanel = new JPanel();
		radioBPanel.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		neewAccess = new JRadioButton("تعیین دسترسی جدبد",true);
		editAccess = new JRadioButton("ویرایش دسترسی");
		removeAccess = new JRadioButton("حذف دسترسی");
		
		neewAccess.setMnemonic(KeyEvent.VK_C);
		editAccess.setMnemonic(KeyEvent.VK_M);
		removeAccess.setMnemonic(KeyEvent.VK_P);

		neewAccess.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {         
		    addpnlscrollPane.setVisible(true);
	         }           
	      });

		editAccess.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {             
	           addpnlscrollPane.setVisible(false);
	         }           
	      });

		removeAccess.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {             
		    addpnlscrollPane.setVisible(false);
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group = new ButtonGroup();
	      group.add(neewAccess);
	      group.add(editAccess);
	      group.add(removeAccess);

	      radioBPanel.add(neewAccess);
	      radioBPanel.add(editAccess);
	      radioBPanel.add(removeAccess); 

	    basepanel.add(radioBPanel);
	    
            JPanel middlepanel = new JPanel();
            middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    JPanel showPanel = new JPanel();
            showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
            Object[] columnNames = {"سمت", "نام کاربری", "رمز عبور", "بانک سوالات", "بانک سناریو", "نمودارها"};
            Object[][] data = null;
                    /*= {
                {"مدیر ارشد", "ali_reza", "", false, false, false},
                {"مدیر", "s_sadeghi", "", false, false,false},
                {"کارشناس", "Apple", "", false, false,false}      
            };*/
            model = new DefaultTableModel(data, columnNames);
            db.openDb();
            ResultSet rs = db.createQuery("SELECT TITLE, USERNAME, PASSWORD, Q_BANK, S_BANK, DIAGRAM FROM PAYDARAN.TABLE_ACCESSCONTROL "
                    + "WHERE TITLE != 'admin'");
            try {
		while(rs.next()){
                    //Retrieve by column name
                    model.addRow(new Object[]{rs.getString("TITLE"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getBoolean("Q_BANK"), rs.getBoolean("S_BANK"), rs.getBoolean("DIAGRAM")}); 
		}
            } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
            db.closeDb();
            table = new JTable(model) {
            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return Boolean.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        showPanel.add(scrollPane);
        
        addpnl = new JPanel();
        //new GridLayout(1, 6)
        addpnl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        semat = new JComboBox(str);
        username = new JTextField(6);
        password = new JTextField(6);
        Q_Bqnk_Access = new JCheckBox();
        S_Bqnk_Access = new JCheckBox();
        Diagram_Access = new JCheckBox();
        addpnl.add(semat);      
        addpnl.add(username);
        addpnl.add(Box.createHorizontalGlue());
        addpnl.add(password);
        addpnl.add(Box.createHorizontalStrut(16));
        addpnl.add(Q_Bqnk_Access); 
        addpnl.add(Box.createHorizontalStrut(43));
        addpnl.add(S_Bqnk_Access);
        addpnl.add(Box.createHorizontalStrut(44));
        addpnl.add(Diagram_Access);
        addpnlscrollPane = new JScrollPane(addpnl);
        addpnlscrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        showPanel.add(addpnlscrollPane);
        middlepanel.add(showPanel);
	basepanel.add(middlepanel);
		
		JPanel fnlBtnPanel = new JPanel();
		fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		JButton fnlAksept = new JButton("تایید نهایی");
		fnlBtnPanel.add(fnlAksept);
		fnlAksept.addActionListener(new DeployOrder());
		fnlBtnPanel.add(Box.createHorizontalGlue());
		fnlBtnPanel.add(Box.createHorizontalGlue());
		JButton fnlcancelBtn = new JButton("انصراف");
		fnlBtnPanel.add(fnlcancelBtn);
		basepanel.add(fnlBtnPanel);

		this.add(basepanel);
                
                
                JPanel hamel = new JPanel();
                hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
                hamel.setAlignmentX(CENTER_ALIGNMENT);
                this.add(hamel, BorderLayout.CENTER);
                
                if(PassWordDialog.isadmin){
                    basepanel.setVisible(true);
                hamel.setVisible(false);
                }else{
                    basepanel.setVisible(false);
                hamel.setVisible(true);
                }
	}

    private class DeployOrder implements ActionListener {

        public DeployOrder() {
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(neewAccess.isSelected()){
                if(!(username.getText().isEmpty()) && !(password.getText().isEmpty())){
                    db.openDb();
                    String query = "INSERT INTO TABLE_ACCESSCONTROL (TITLE, USERNAME, PASSWORD, Q_BANK, S_BANK, DIAGRAM) VALUES"
                        + " ('"+semat.getSelectedItem().toString()+"', '"+username.getText().toString()+"', '"+password.getText().toString()
                        +"', "+Q_Bqnk_Access.isSelected()+", "+S_Bqnk_Access.isSelected()+", "+Diagram_Access.isSelected()+")"; 
                    if(db.insertDb(query)){
                        System.out.println("added...!");
                        model.addRow(new Object[]{semat.getSelectedItem().toString(), username.getText().toString(),
                        password.getText().toString(), Q_Bqnk_Access.isSelected(), S_Bqnk_Access.isSelected(), Diagram_Access.isSelected()});
                        username.setText("");
                        password.setText("");
                    }else{
                        System.out.println("oooops...!");
							}
                    db.closeDb();
                }else 
                    JOptionPane.showMessageDialog(null, "لطفا نام کاربری و رمز عبور را وارد نمایید");
            }else if(editAccess.isSelected()){
                
            }
                
        }
    }
} 




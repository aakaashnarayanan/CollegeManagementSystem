package design;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import database.DbConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class StaffEntryForm {

	private JFrame frame;
	private JTable table;
	private JTextField mark1_text;
	private JTextField mark2_text;
	private JTextField mark3_text;
	private JTextField mark4_text;
	private JTextField mark5_text;
	private JTextField mark6_text;

	Statement stmt;
	ResultSet rs;
	String staff_id;
	String subject_id;

	String DisplayStudentcmd;
	String FetchStudentCmd;
	String UpdateStudentCmd;
	String GetStudentIds;
	String getSubjectIds;

	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JComboBox<String> comboBox1;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { StaffEntryForm window = new
	 * StaffEntryForm(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public StaffEntryForm(String staffid) throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 708, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setTitle("Staff Console");
		staff_id = staffid;
		DbConnect.connect();
		initialize();
		displayStudent(staff_id);
		getSubjectId();
		getStudetnId();
	}

	public void displayStudent(String staff_id) throws Exception {
		DisplayStudentcmd = "select st.stu_id as ID,st.stu_name as NAME,sub.subject_name as SUBJECT,m.mark1,m.mark2,m.mark3,m.mark4,m.mark5,m.mark6,m.total from cms.mark m join cms.student st on m.stu_id = st.stu_id join cms.subject sub on m.subject_id = sub.subject_id where sub.staff_id = "
				+ staff_id + " order by st.stu_id,st.stu_name";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(DisplayStudentcmd);

		table.setModel(DbUtils.resultSetToTableModel(rs));
		System.out.println(DisplayStudentcmd);
	}

	public void getStudetnId() throws Exception {
		GetStudentIds = "select stu_id from cms.mark where subject_id = " + subject_id;
		System.out.println(GetStudentIds);
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(GetStudentIds);
		while (rs.next()) {
			String id = Integer.toString(rs.getInt("stu_id"));
			comboBox1.addItem(id);

		}
	}

	public void getSubjectId() throws Exception {
		getSubjectIds = "select subject_id from cms.subject where staff_id =" + staff_id;
		System.out.println(getSubjectIds);
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(getSubjectIds);
		rs.next();
		subject_id = Integer.toString(rs.getInt("subject_id"));

	}

	public void updateStudentDetials() throws Exception {
		UpdateStudentCmd = "update cms.mark set mark1 = " + mark1_text.getText() + ", mark2 =" + mark2_text.getText()
				+ ", mark3 =" + mark3_text.getText() + ", mark4 =" + mark4_text.getText() + ", mark5 ="
				+ mark5_text.getText() + ", mark6 =" + mark6_text.getText() + "where stu_id = "
				+ comboBox1.getSelectedItem().toString() + "and subject_id =" + subject_id;
		PreparedStatement pstmt = DbConnect.conn.prepareStatement(UpdateStudentCmd);
		pstmt.executeUpdate();
		System.out.println("student details altered..");
	}

	public void fecthStudentDetails() throws Exception {
		FetchStudentCmd = "select st.stu_id as ID,st.stu_name as NAME,sub.subject_name as SUBJECT,m.mark1,m.mark2,m.mark3,m.mark4,m.mark5,m.mark6 from cms.mark m join cms.student st on m.stu_id = st.stu_id join cms.subject sub on m.subject_id = sub.subject_id where sub.staff_id = "
				+ staff_id + " and st.stu_id = " + comboBox1.getSelectedItem().toString()
				+ " order by st.stu_id,st.stu_name";
		// FetchStudentCmd = "select mark1,mark2,mark3,mark4,mark5,mark6 from cms.mark
		// where subject_id = 301 and stu_id = "+stu_id_text.getText();
		System.out.println(FetchStudentCmd);
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(FetchStudentCmd);
		rs.next();
		mark1_text.setText("" + rs.getInt("mark1"));
		mark2_text.setText("" + rs.getInt("mark2"));
		mark3_text.setText("" + rs.getInt("mark3"));
		mark4_text.setText("" + rs.getInt("mark4"));
		mark5_text.setText("" + rs.getInt("mark5"));
		mark6_text.setText("" + rs.getInt("mark6"));
	}

	private void initialize() {
		/*
		 * frame = new JFrame(); frame.setBounds(100, 100, 708, 552);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.getContentPane().setLayout(null);
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 25, 648, 162);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton fetch_btn = new JButton("Fetch");
		fetch_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fecthStudentDetails();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		fetch_btn.setBounds(573, 271, 85, 21);
		frame.getContentPane().add(fetch_btn);

		JButton update_btn = new JButton("update");
		update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateStudentDetials();
					displayStudent(staff_id);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		update_btn.setBounds(573, 316, 85, 21);
		frame.getContentPane().add(update_btn);

		mark1_text = new JTextField();
		mark1_text.setBounds(80, 272, 96, 19);
		frame.getContentPane().add(mark1_text);
		mark1_text.setColumns(10);

		mark2_text = new JTextField();
		mark2_text.setBounds(270, 272, 96, 19);
		frame.getContentPane().add(mark2_text);
		mark2_text.setColumns(10);

		mark3_text = new JTextField();
		mark3_text.setBounds(445, 272, 96, 19);
		frame.getContentPane().add(mark3_text);
		mark3_text.setColumns(10);

		mark4_text = new JTextField();
		mark4_text.setBounds(78, 317, 96, 19);
		frame.getContentPane().add(mark4_text);
		mark4_text.setColumns(10);

		mark5_text = new JTextField();
		mark5_text.setBounds(270, 317, 96, 19);
		frame.getContentPane().add(mark5_text);
		mark5_text.setColumns(10);

		mark6_text = new JTextField();
		mark6_text.setBounds(445, 317, 96, 19);
		frame.getContentPane().add(mark6_text);
		mark6_text.setColumns(10);

		lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(23, 230, 45, 13);
		frame.getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("CIA 1:");
		lblNewLabel_1.setBounds(25, 275, 45, 13);
		frame.getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("CIA 2:");
		lblNewLabel_2.setBounds(215, 275, 45, 13);
		frame.getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("CIA 3 :");
		lblNewLabel_3.setBounds(376, 275, 45, 13);
		frame.getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("CIA 4:");
		lblNewLabel_4.setBounds(23, 320, 45, 13);
		frame.getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("CIA 5 :");
		lblNewLabel_5.setBounds(215, 320, 45, 13);
		frame.getContentPane().add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("CIA 6 :");
		lblNewLabel_6.setBounds(376, 320, 45, 13);
		frame.getContentPane().add(lblNewLabel_6);

		comboBox1 = new JComboBox<String>();
		comboBox1.setBounds(80, 226, 96, 21);
		frame.getContentPane().add(comboBox1);

	}
}

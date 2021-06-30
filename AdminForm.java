package design;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;

import database.DbConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AdminForm {

	private JFrame frame;

	Statement stmt;
	ResultSet rs;

	String displayDatabaseCmd;
	String insertStudentCmd;
	String getDeptCmd;
	String getDeptIdCmd;
	String dpt_id;
	String fetchStudentCmd;
	String deleteStudentCmd;
	String deleteMarkCmd;

	private JTable table;
	private JComboBox DatabaseComboBox;
	private JComboBox<String> Dept_ComboBox;
	private JScrollPane scrollPane;
	private JTextField text_StudentId;
	private JTextField text_StudnetName;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { AdminForm window = new AdminForm();
	 * window.frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); }
	 * } }); }
	 */

	public AdminForm() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 755, 627);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		DbConnect.connect();
		initialize();
		displayDatabase();
		getDeptNames();
	}

	public void displayDatabase() throws Exception {
		displayDatabaseCmd = "select * from cms." + DatabaseComboBox.getSelectedItem().toString();
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(displayDatabaseCmd);

		table.setModel(DbUtils.resultSetToTableModel(rs));

	}

	public void createStudent() throws Exception {
		getDeptId();
		insertStudentCmd = "insert into cms.student values(" + text_StudentId.getText() + "," + "'"
				+ text_StudnetName.getText() + "'," + dpt_id + ")";
		System.out.println(insertStudentCmd);
		// stmt = DbConnect.conn.createStatement();
		// stmt.executeUpdate(displayDatabaseCmd);
		PreparedStatement pstmt = DbConnect.conn.prepareStatement(insertStudentCmd);
		pstmt.executeUpdate();
		System.out.println("student added");
		System.out.println("Triggering addStudentMarks event....!");
	}

	public void getDeptNames() throws Exception {
		getDeptCmd = "select dpt_name from cms.department";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(getDeptCmd);
		while (rs.next()) {
			String id = rs.getString("dpt_name");
			Dept_ComboBox.addItem(id);
		}
	}

	public void getDeptId() throws Exception {
		getDeptIdCmd = "select dpt_id from cms.department where dpt_name ='"
				+ Dept_ComboBox.getSelectedItem().toString() + "'";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(getDeptIdCmd);
		rs.next();
		dpt_id = rs.getString("dpt_id");
		System.out.println(getDeptCmd);
		System.out.println(dpt_id);

	}

	public void getStudentDetails() throws Exception {
		fetchStudentCmd = "select * from cms.student where stu_id =" + text_StudentId.getText();
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(fetchStudentCmd);
		rs.next();
		text_StudnetName.setText(rs.getString("stu_name"));
		// Dept_ComboBox.setName(Integer.toString(rs.getInt("dpt_id")));
	}

	public void deleteStudentDetails() throws Exception {
		deleteMarkCmd = "delete from cms.mark where stu_id =" + text_StudentId.getText();
		deleteStudentCmd = "delete from cms.student where stu_id = " + text_StudentId.getText();
		stmt = DbConnect.conn.createStatement();
		stmt.executeUpdate(deleteMarkCmd);
		System.out.println("Deleted marks for the student...");
		stmt.executeUpdate(deleteStudentCmd);
		System.out.println("deleted the student success");
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		/*
		 * frame = new JFrame(); frame.setBounds(100, 100, 755, 627);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.getContentPane().setLayout(null);
		 */

		scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 23, 685, 187);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		DatabaseComboBox = new JComboBox();
		DatabaseComboBox.setModel(
				new DefaultComboBoxModel(new String[] { "Department", "Student", "Mark ", "Staff", "Subject", "" }));
		DatabaseComboBox.setBounds(437, 234, 126, 21);
		frame.getContentPane().add(DatabaseComboBox);

		JButton DisplayBtn = new JButton("Display");
		DisplayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayDatabase();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		DisplayBtn.setBounds(590, 234, 123, 21);
		frame.getContentPane().add(DisplayBtn);

		JButton Create_student_btn = new JButton("Create Student");
		Create_student_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createStudent();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		Create_student_btn.setBounds(590, 298, 123, 21);
		frame.getContentPane().add(Create_student_btn);

		JButton Fetch_Student_btn = new JButton("Fetch Student");
		Fetch_Student_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getStudentDetails();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		Fetch_Student_btn.setBounds(590, 343, 123, 21);
		frame.getContentPane().add(Fetch_Student_btn);

		JButton Delete_Student_btn = new JButton("Delete Student");
		Delete_Student_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteStudentDetails();
					displayDatabase();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		Delete_Student_btn.setBounds(437, 343, 126, 21);
		frame.getContentPane().add(Delete_Student_btn);

		text_StudentId = new JTextField();
		text_StudentId.setBounds(50, 299, 96, 19);
		frame.getContentPane().add(text_StudentId);
		text_StudentId.setColumns(10);

		text_StudnetName = new JTextField();
		text_StudnetName.setBounds(240, 299, 96, 19);
		frame.getContentPane().add(text_StudnetName);
		text_StudnetName.setColumns(10);

		JLabel lblNewLabel = new JLabel("SID :");
		lblNewLabel.setBounds(10, 302, 45, 13);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("SNAME :");
		lblNewLabel_1.setBounds(173, 302, 45, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("DptName :");
		lblNewLabel_2.setBounds(368, 302, 59, 13);
		frame.getContentPane().add(lblNewLabel_2);

		Dept_ComboBox = new JComboBox<String>();
		Dept_ComboBox.setBounds(437, 298, 126, 21);
		frame.getContentPane().add(Dept_ComboBox);

	}
}

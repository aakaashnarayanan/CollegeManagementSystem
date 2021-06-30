package design;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
	String insertStaffCmd;
	String assignSubjectCmd;

	private JTable table;
	private JComboBox<String> DatabaseComboBox;
	private JComboBox<String> Dept_ComboBox;
	private JScrollPane scrollPane;
	private JTextField text_StudentId;
	private JTextField text_StudnetName;
	private JTextField text_StaffId;
	private JTextField text_staffName;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JTextField text_DptID;
	private JButton AllMark_btn;

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
		frame.setTitle("Admin From");

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

	public void createStudent() {
		insertStudentCmd = "insert into cms.student values(" + text_StudentId.getText() + "," + "'"
				+ text_StudnetName.getText() + "'," + dpt_id + ")";
		System.out.println(insertStudentCmd);
		PreparedStatement pstmt;
		try {
			getDeptId();
			pstmt = DbConnect.conn.prepareStatement(insertStudentCmd);
			pstmt.executeUpdate();
			System.out.println("student added");
			System.out.println("Triggering addStudentMarks event....!");
		} catch (DerbySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(text_StudentId, "Invalid Input");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void createStaff() {

		insertStaffCmd = "insert into cms.staff values(" + text_StaffId.getText() + ",'" + text_staffName.getText()
				+ "'," + text_DptID.getText() + ")";
		PreparedStatement pstmt;
		try {
			pstmt = DbConnect.conn.prepareStatement(insertStaffCmd);
			pstmt.executeUpdate();
			System.out.println("staff added...");
		} catch (DerbySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(text_DptID, "Invalid input");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

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

		DatabaseComboBox = new JComboBox<String>();
		DatabaseComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Department", "Student", "Mark ", "Staff", "Subject", "" }));
		DatabaseComboBox.setBounds(28, 234, 126, 21);
		frame.getContentPane().add(DatabaseComboBox);

		JButton DisplayBtn = new JButton("View");
		DisplayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayDatabase();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		DisplayBtn.setBounds(173, 234, 123, 21);
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

		JButton CreateStaff_btn = new JButton("Create Staff");
		CreateStaff_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createStaff();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		CreateStaff_btn.setBounds(590, 423, 123, 21);
		frame.getContentPane().add(CreateStaff_btn);

		AllMark_btn = new JButton("All Marks");
		AllMark_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new StudentForm();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		AllMark_btn.setBounds(319, 234, 123, 21);
		frame.getContentPane().add(AllMark_btn);

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

		text_StaffId = new JTextField();
		text_StaffId.setBounds(50, 424, 96, 19);
		frame.getContentPane().add(text_StaffId);
		text_StaffId.setColumns(10);

		text_staffName = new JTextField();
		text_staffName.setBounds(240, 424, 96, 19);
		frame.getContentPane().add(text_staffName);
		text_staffName.setColumns(10);

		lblNewLabel_3 = new JLabel("Staff ID");
		lblNewLabel_3.setBounds(10, 427, 45, 13);
		frame.getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("Staff Name :");
		lblNewLabel_4.setBounds(173, 427, 86, 13);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Dpt ID:");
		lblNewLabel_5.setBounds(368, 427, 59, 13);
		frame.getContentPane().add(lblNewLabel_5);

		text_DptID = new JTextField();
		text_DptID.setBounds(437, 424, 96, 19);
		frame.getContentPane().add(text_DptID);
		text_DptID.setColumns(10);

	}
}

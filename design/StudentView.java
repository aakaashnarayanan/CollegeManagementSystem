package design;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;

import database.DbConnect;
import jdk.internal.net.http.common.FlowTube.TubePublisher;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class StudentView {

	private JFrame frame;
	private JTable table;

	Statement stmt;
	ResultSet rs;
	int subject_id;
	String student_id;

	String selectStudentMarkCmd;
	String displaySubjectCmd;

	JComboBox<String> comboBox;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { StudentView window = new
	 * StudentView(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public StudentView(String studentId) throws Exception {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(245, 222, 179));
		frame.setBounds(100, 100, 809, 273);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		initialize();
		DbConnect.connect();
		getSubjectName();
		student_id = studentId;
		displayStudentMark(student_id);
	}

	public void displayStudentMark(String student_Id) throws Exception {
		getSubjectId();
		selectStudentMarkCmd = "select su.subject_name as subject, su.subject_id as SUB_ID, st.staff_name, s.stu_id as STU_ID, m.mark1,m.mark2,m.mark3,m.mark4,m.mark5,m.mark6,m.internal from cms.mark m \r\n"
				+ "join cms.subject su on m.subject_id = su.subject_id \r\n"
				+ "join cms.staff st on st.staff_id = su.staff_id \r\n"
				+ "join cms.student s on m.stu_id = s.stu_id \r\n" + "where m.stu_id =" + student_Id
				+ "and m.subject_id =" + subject_id + " \r\n" + "order by su.subject_name";

		stmt = DbConnect.conn.createStatement();
		System.out.println(selectStudentMarkCmd);
		rs = stmt.executeQuery(selectStudentMarkCmd);

		table.setModel(DbUtils.resultSetToTableModel(rs));
	}

	public void getSubjectId() throws Exception {
		displaySubjectCmd = "select subject_id from cms.subject where subject_name = '"
				+ comboBox.getSelectedItem().toString() + "'";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(displaySubjectCmd);
		rs.next();
		subject_id = rs.getInt("subject_id");
		System.out.println("passed 2");
	}

	public void getSubjectName() throws SQLException {
		displaySubjectCmd = "select subject_name from cms.subject";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(displaySubjectCmd);
		while (rs.next()) {
			String name = rs.getString("subject_name");
			comboBox.addItem(name);
		}
		System.out.println("passed 1");
	}

	private void initialize() {
		/*
		 * frame = new JFrame(); frame.getContentPane().setBackground(new Color(245,
		 * 222, 179)); frame.setBounds(100, 100, 717, 273);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.getContentPane().setLayout(null);
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 759, 109);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(515, 144, 105, 21);
		frame.getContentPane().add(comboBox);

		JButton mark_btn = new JButton("Dispaly");
		mark_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					displayStudentMark(student_id);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mark_btn.setBounds(659, 144, 110, 21);
		frame.getContentPane().add(mark_btn);
	}
}

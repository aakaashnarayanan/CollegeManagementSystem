package design;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTable;

import database.DbConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class MarkForm {

	private JFrame frame;
	private JTable table;
	private JTable table_1;
	private JTable table_2;

	String selectOSCmd;
	String selectDbmsCmd;
	String selectJavaCmd;

	Statement stmt;
	ResultSet rs;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { StudentForm window = new
	 * StudentForm(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public MarkForm() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 773, 509);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setTitle("Student Mark");

		initialize();
		DbConnect.connect();
		displayOd();
		displayDbms();
		displayJava();
	}

	public void displayOd() throws Exception {
		selectOSCmd = "select * from cms.mark where subject_id = 303";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(selectOSCmd);

		table.setModel(DbUtils.resultSetToTableModel(rs));

	}

	public void displayDbms() throws Exception {
		selectDbmsCmd = "select * from cms.mark where subject_id = 302";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(selectDbmsCmd);

		table_1.setModel(DbUtils.resultSetToTableModel(rs));

	}

	public void displayJava() throws Exception {
		selectJavaCmd = "select * from cms.mark where subject_id = 301";
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(selectJavaCmd);

		table_2.setModel(DbUtils.resultSetToTableModel(rs));

	}

	private void initialize() {
		/*
		 * frame = new JFrame(); frame.setBounds(100, 100, 773, 509);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.getContentPane().setLayout(null);
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 739, 113);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("OS Subject");
		lblNewLabel.setBounds(330, 10, 107, 13);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 178, 739, 110);
		frame.getContentPane().add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		JLabel lblNewLabel_1 = new JLabel("DBMS Subject");
		lblNewLabel_1.setBounds(330, 155, 135, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 321, 739, 113);
		frame.getContentPane().add(scrollPane_2);

		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);

		JLabel lblNewLabel_2 = new JLabel("Java Subject");
		lblNewLabel_2.setBounds(330, 298, 91, 13);
		frame.getContentPane().add(lblNewLabel_2);
	}
}

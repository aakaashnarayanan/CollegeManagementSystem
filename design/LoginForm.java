package design;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import database.DbConnect;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class LoginForm {

	private JFrame frame;
	private JTextField Id_text;

	Statement stmt;
	ResultSet rs;

	String findStaffCmd;
	String findStudentCmd;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginForm() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 247);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setTitle("Login Form");
		DbConnect.connect();
		initialize();
	}

	public boolean findStaff(String id) {
		findStaffCmd = "select staff_id from cms.staff ";
		try {
			stmt = DbConnect.conn.createStatement();
			rs = stmt.executeQuery(findStaffCmd);
			while (rs.next()) {
				if (Integer.parseInt(id) == rs.getInt("staff_id")) {
					return true;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(Id_text, "Invalid Input");
			e.printStackTrace();
		}
		return false;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 527, 247);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton Logon_btn = new JButton("Login");
		Logon_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (Id_text.getText().equals("admin")) {
						new AdminForm();
					} else if (findStaff(Id_text.getText())) {
						new StaffEntryForm(Id_text.getText());
					} else {
						JOptionPane.showMessageDialog(Id_text, "Invalid Input");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		Logon_btn.setBounds(292, 97, 85, 21);
		frame.getContentPane().add(Logon_btn);

		Id_text = new JTextField();
		Id_text.setBounds(175, 98, 96, 19);
		frame.getContentPane().add(Id_text);
		Id_text.setColumns(10);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(127, 101, 45, 13);
		frame.getContentPane().add(lblNewLabel);
	}

}

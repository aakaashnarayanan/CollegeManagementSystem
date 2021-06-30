package design;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import database.DbConnect;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginForm {

	private JFrame frame;
	private JTextField Id_text;

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
		DbConnect.connect();
		initialize();
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
					} else {
						new StaffEntryForm(Id_text.getText());
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

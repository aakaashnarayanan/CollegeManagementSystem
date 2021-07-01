package processing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;

import database.DbConnect;

public class MarkCalulcation {

	static Statement stmt;
	static ResultSet rs;

	static String getMarkCmd;
	static Integer arr[] = new Integer[6];

	public static float InternalCalculation(String subject_id, String student_id) throws SQLException {

		int total = 0;
		float internal;

		getMarkCmd = "select * from cms.mark where subject_id = " + subject_id + "and stu_id =" + student_id;
		stmt = DbConnect.conn.createStatement();
		rs = stmt.executeQuery(getMarkCmd);
		while (rs.next()) {
			arr[0] = rs.getInt("mark1");
			arr[1] = rs.getInt("mark2");
			arr[2] = rs.getInt("mark3");
			arr[3] = rs.getInt("mark4");
			arr[4] = rs.getInt("mark5");
			arr[5] = rs.getInt("mark6");
		}
		Arrays.sort(arr, Collections.reverseOrder());
		for (int i = 0; i < 4; i++) {
			total += arr[i];
		}
		internal = total / 4;
		System.out.println("Internal mark calculated..");
		Arrays.fill(arr, null);
		return internal;

	}

}

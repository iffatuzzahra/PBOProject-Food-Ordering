package FoodOrdering;

import java.sql.*;
import javax.swing.*;

public class Model {

	static final String jdbcDriver = "com.mysql.jdbc.Driver";
	static final String DBurl = "jdbc:mysql://localhost/foodorder";
	static final String DBusername = "root";
	static final String DBpassword = "";
	Connection koneksi;
	Statement statement;
	ResultSet resultSet;

	public Model() {
		try {
			Class.forName(jdbcDriver);
			koneksi = DriverManager.getConnection(DBurl, DBusername, DBpassword);
			System.out.println("Koneksi Berhasil");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			System.out.println("Koneksi Gagal");
		}
	}

	public void addFood(String foodName, int price, String categories) {
		try {
			String query = "INSERT INTO food (FoodName, Price, Categories) VALUE('" + foodName + "','" + price + "','"
					+ categories + "') ";
			statement = koneksi.createStatement();
			statement.executeUpdate(query);
			System.out.println("Berhasil Ditambahkan");
			JOptionPane.showMessageDialog(null, "Data Berhasil");
		} catch (Exception sql) {
			System.out.println(sql.getMessage());
			JOptionPane.showMessageDialog(null, sql.getMessage());
		}
	}

	public String[][] readDataMain(String cat) {
		String data[][] = new String[getBanyakData(cat)][3];
		try {
			String sql = "select * from food where Categories='" + cat + "'";
			resultSet = statement.executeQuery(sql);
			int p = 0;
			while (resultSet.next()) {
				data[p][0] = resultSet.getString("FoodId");
				data[p][1] = resultSet.getString("FoodName");
				data[p][2] = resultSet.getString("Price");
				p++;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan!", "Hasil", JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}

	public String[][] readOrderHistory() {
		String data[][] = new String[getBanyakDataOrder()][4];
		try {
			String sql = "SELECT * FROM `order`";
			ResultSet resultSet = statement.executeQuery(sql);
			int p = 0;
			while (resultSet.next()) {
				data[p][0] = Integer.toString(resultSet.getInt("OrderId"));
				data[p][1] = resultSet.getString("CustomerName");
				data[p][2] = Integer.toString(resultSet.getInt("OrderAmount"));
				data[p][3] = Integer.toString(resultSet.getInt("Total"));
				p++;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan!", "Hasil", JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}

	public String[][] readOrderDetail(int id) {
		String data[][] = new String[getBanyakDataDetail(id)][3];
		try {
			String sql = "select * from orderdetail JOIN food ON food.FoodId=orderdetail.FoodId WHERE OrderId='" + id + "'";
			ResultSet resultSet = statement.executeQuery(sql);
			int p = 0;
			while (resultSet.next()) {
				data[p][0] = resultSet.getString("FoodName");
				int amount = resultSet.getInt("AmountOrder");
				int harga = resultSet.getInt("Price") * amount;
				data[p][1] = Integer.toString(amount);
				data[p][2] = Integer.toString(harga);
				p++;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan!", "Hasil", JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}

	public String[][] readBasket() {
		String data[][] = new String[getBanyakDataBasket() + 1][3];
		try {
			int jml = 0;
			int bayar = 0;
			statement = koneksi.createStatement();
			String query = "SELECT * FROM orderbasket JOIN food ON food.FoodId=orderbasket.FName ";
			ResultSet setResult = statement.executeQuery(query);
			while (setResult.next()) {
				data[jml][0] = setResult.getString("FoodName");
				int exs = setResult.getInt("jml");
				data[jml][1] = Integer.toString(exs);
				int dex = setResult.getInt("Price");
				data[jml][2] = Integer.toString(dex);
				bayar = bayar + (dex * exs);
				jml++;
			}
			data[jml][2] = Integer.toString(bayar);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan!", "Hasil", JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}

	public void addToBasket(int id) {

		int jmlData = 0;
		int dataJml = 0;
		try {
			statement = koneksi.createStatement();
			String query = "SELECT * FROM orderbasket WHERE FName='" + id + "'";
			ResultSet setResult = statement.executeQuery(query);
			while (setResult.next()) {
				dataJml = setResult.getInt("Jml");
				jmlData++;
			}
			if (jmlData > 0) {
				dataJml++;
				String sql = "UPDATE orderbasket SET jml='" + dataJml + "' WHERE FName='" + id + "'";
				statement = koneksi.createStatement();
				statement.executeUpdate(sql);
			} else {
				String sql = "INSERT INTO orderbasket VALUE('" + id + "','1')";
				statement = koneksi.createStatement();
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL Errorr");

		}
	}

	public void truncateBasket() {
		try {
			String sql = "TRUNCATE orderbasket ";
			statement = koneksi.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("SQL Errorr");

		}
	}

	public void addToOrder(String namaPemesan) {
		try {
			int jml = 0;
			int bayar = 0;
			int amount = 0;
			int data[][] = new int[getBanyakDataBasket() + 1][3];
			statement = koneksi.createStatement();
			String query = "SELECT * FROM orderbasket JOIN food ON food.FoodId=orderbasket.FName ";

			ResultSet setResult = statement.executeQuery(query);
			while (setResult.next()) {
				data[jml][0] = setResult.getInt("FName");
				data[jml][1] = setResult.getInt("Price");
				data[jml][2] = setResult.getInt("jml");
				amount = amount + data[jml][2];
				bayar = bayar + (data[jml][1] * data[jml][2]);
				jml++;
			}
			String sql = "INSERT INTO `order` (`CustomerName`, `OrderAmount`, `Total`) VALUE ('" + namaPemesan + "','"
					+ amount + "','" + bayar + "')";
			statement.executeUpdate(sql);
			String query2 = "SELECT * FROM `order`";
			ResultSet setResult2 = statement.executeQuery(query2);
			int idOrder = 0;
			while (setResult2.next()) {
				idOrder = setResult2.getInt("OrderId");
			}
			for (int i = 0; i < jml; i++) {
				String sql2 = "INSERT INTO `orderdetail` VALUE('" + data[i][0] + "','" + data[i][2] + "','" + idOrder + "')";
				statement.executeUpdate(sql2);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			System.out.println("SQL Errorr");

		}
	}

	public String[] getEditValue(int id) {
		String data[] = new String[4];
		try {
			statement = koneksi.createStatement();
			String query = "SELECT * FROM food WHERE FoodId='" + id + "'";
			ResultSet setResult = statement.executeQuery(query);
			while (setResult.next()) {
				data[0] = Integer.toString(id);
				data[1] = setResult.getString("FoodName");
				data[2] = Integer.toString(setResult.getInt("Price"));
				data[3] = setResult.getString("Categories");
				// jmlData++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL Errorr");
		}
		return data;
	}

	public void deleteMenu(int id) {
		try {
			String query = "DELETE FROM `food` WHERE `FoodId` = '" + id + "' ";
			statement = koneksi.createStatement();
			statement.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Berhasil Dihapus");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateMenu(int id, String name, int price, String cat) {
		try {
			String query = "UPDATE `food` SET `FoodName`='" + name + "', `Price`='" + price + "', `Categories`='" + cat
					+ "' WHERE `FoodId`='" + id + "' ";
			statement = koneksi.createStatement();
			statement.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Berhasil Dirubah");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public int getBanyakData(String cat) {
		int jmlData = 0;
		try {
			statement = koneksi.createStatement();
			String query = "SELECT * FROM food where Categories='" + cat + "'";
			ResultSet setResult = statement.executeQuery(query);

			while (setResult.next()) {
				jmlData++;
			}
			return jmlData;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL Error");
			return 0;
		}
	}

	public int getBanyakDataOrder() {
		int jmlData = 0;
		try {
			statement = koneksi.createStatement();
			String query = "SELECT * FROM `order`";
			ResultSet setResult = statement.executeQuery(query);
			while (setResult.next()) {
				jmlData++;
			}
			return jmlData;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL Error");
			JOptionPane.showMessageDialog(null, "error diperhitungan jumlah data");
			return 0;
		}
	}

	public int getBanyakDataDetail(int id) {
		int jmlData = 0;
		try {
			statement = koneksi.createStatement();
			String query = "SELECT * FROM orderdetail WHERE OrderId='" + id + "'";
			ResultSet setResult = statement.executeQuery(query);
			while (setResult.next()) {
				jmlData++;
			}
			return jmlData;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL Error");
			return 0;
		}
	}

	public int getBanyakDataBasket() {
		int jmlData = 0;
		try {
			statement = koneksi.createStatement();
			String query = "SELECT * FROM orderbasket";
			ResultSet setResult = statement.executeQuery(query);

			while (setResult.next()) {
				jmlData++;
			}
			return jmlData;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL Error");
			return 0;
		}
	}
}
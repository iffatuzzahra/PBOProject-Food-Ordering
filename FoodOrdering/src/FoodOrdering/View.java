package FoodOrdering;

import java.awt.Dimension;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;

	Controller control;
	JFrame currentFrame;

	JButton bOpenAdd = new JButton("Add Menu");
	JButton bOpenOrder = new JButton("New Order");
	String[] kolom = { "ID", "Name", "Price" };
	String[] kolomH = { "Order ID", "Customer Name", "Amount", "Total" };
	String[] kolomDH = { "Food ID", "Amount", "Price" };
	String[] kolomB = { "Order List", "Amount", "Price" };
	JTable tabeldrinkMain;
	JTable tabeleatMain;
	JTable tabeldrinkOrder;
	JTable tabeleatOrder;
	JTable tabelbasket;
	JTable tabelhistory;
	JTable tabeldetailhistory;
	DefaultTableModel tableModel;
	JScrollPane scrollPane;
	Dimension dimensiMain;
	Dimension dimensiAddMenu;
	JLabel leat = new JLabel("Eat");
	JLabel ldrink = new JLabel("Drink");
	JLabel lhistory = new JLabel("Order History");
	JLabel ldetailhistory = new JLabel("Order Detail (ChooseHistory)");

	public View() {
		dimensiMain = new Dimension(850, 650);
		dimensiAddMenu = new Dimension(500, 270); // lebar,tinggi
		tableModel = new DefaultTableModel(kolom, 0);
		tabeldrinkMain = new JTable(tableModel);
		tabeleatMain = new JTable(tableModel);
		tabeldrinkOrder = new JTable(tableModel);
		tabeleatOrder = new JTable(tableModel);
		tableModel = new DefaultTableModel(kolomB, 0);
		tabelbasket = new JTable(tableModel);
		tableModel = new DefaultTableModel(kolomH, 0);
		tabelhistory = new JTable(tableModel);
		tableModel = new DefaultTableModel(kolomDH, 0);
		tabeldetailhistory = new JTable(tableModel);
		mainView();
	}

	public void refresh() {
		dispose();
	}

	public JFrame mainView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setPreferredSize(dimensiMain);

		add(bOpenAdd);
		add(bOpenOrder);
		bOpenAdd.setBounds(285, 40, 120, 20); // x,y,lebar,tinggi
		bOpenOrder.setBounds(425, 40, 120, 20);

		add(ldrink);
		scrollPane = new JScrollPane(tabeldrinkMain);
		add(scrollPane);
		ldrink.setBounds(200, 80, 90, 20);
		scrollPane.setBounds(20, 110, 380, 210); // x,y,lebar,tinggi
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(leat);
		scrollPane = new JScrollPane(tabeleatMain);
		add(scrollPane);
		leat.setBounds(200, 330, 90, 20);
		scrollPane.setBounds(20, 360, 380, 210); // x,y,lebar,tinggi
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(lhistory);
		scrollPane = new JScrollPane(tabelhistory);
		add(scrollPane);
		lhistory.setBounds(590, 80, 90, 20);
		scrollPane.setBounds(430, 110, 380, 210); // x,y,lebar,tinggi
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(ldetailhistory);
		scrollPane = new JScrollPane(tabeldetailhistory);
		add(scrollPane);
		ldetailhistory.setBounds(550, 330, 200, 20);
		scrollPane.setBounds(430, 360, 380, 210); // x,y,lebar,tinggi
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		pack();
		return currentFrame;
	}

	JLabel lName = new JLabel("Name : ");
	JTextField tfName = new JTextField();
	JLabel lPrice = new JLabel("Price : ");
	JTextField tfPrice = new JTextField();
	JLabel lCategories = new JLabel("Categories : ");
	String categories[] = { "Eat", "Drink" };
	JComboBox<String> cmbCategories = new JComboBox<>(categories);

	JButton btnAddMenu = new JButton("Add Menu");
	JButton btnClearAdd = new JButton("Clear");
	JButton btnBack = new JButton("Back");

	public JFrame addMenuView() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setPreferredSize(dimensiAddMenu);

		add(lName);
		add(tfName);
		add(lPrice);
		add(tfPrice);
		add(lCategories);
		add(cmbCategories);
		add(btnAddMenu);
		add(btnBack);
		add(btnClearAdd);

		lName.setBounds(40, 40, 200, 20); // x,y,lebar,tinggi
		tfName.setBounds(240, 40, 200, 20);
		lPrice.setBounds(40, 70, 200, 20);
		tfPrice.setBounds(240, 70, 200, 20);
		lCategories.setBounds(40, 100, 200, 20);
		cmbCategories.setBounds(240, 100, 200, 20);
		btnAddMenu.setBounds(180, 140, 100, 20);
		btnBack.setBounds(120, 180, 100, 20);
		btnClearAdd.setBounds(240, 180, 100, 20);
		pack();
		return currentFrame;
	}

	JLabel basket = new JLabel("Basket");
	JLabel nama = new JLabel("Customer Name : ");
	JLabel lTotal = new JLabel();

	JButton btnSelesaiOrder = new JButton("Order Now");
	JButton btnClearOrder = new JButton("Clear/Reorder");

	public JFrame newOrder() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setPreferredSize(dimensiMain);

		add(ldrink);
		scrollPane = new JScrollPane(tabeldrinkOrder);
		add(scrollPane);
		ldrink.setBounds(200, 20, 90, 20);// (200,80,90,20)
		scrollPane.setBounds(20, 50, 380, 240); // x,y,lebar,tinggi (20,110,380,210);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(leat);
		scrollPane = new JScrollPane(tabeleatOrder);
		add(scrollPane);
		leat.setBounds(200, 300, 90, 20); // (200,340,90,20);
		scrollPane.setBounds(20, 330, 380, 240); // x,y,lebar,tinggi (20,370,380,210);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(basket);
		scrollPane = new JScrollPane(tabelbasket);
		add(scrollPane);
		basket.setBounds(590, 230, 90, 20);
		scrollPane.setBounds(430, 260, 380, 310); // x,y,lebar,tinggi
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(nama);
		nama.setBounds(500, 50, 250, 20);
		add(tfName);
		tfName.setBounds(500, 80, 250, 20);
		add(btnSelesaiOrder);
		btnSelesaiOrder.setBounds(575, 130, 100, 20);
		add(btnClearOrder);
		btnClearOrder.setBounds(635, 170, 115, 20);
		add(btnBack);
		btnBack.setBounds(500, 170, 115, 20);
		add(lTotal);
		lTotal.setBounds(635, 490, 100, 20);
		pack();
		return currentFrame;
	}

	JLabel lEditTitle = new JLabel("Data ");
	JLabel lId = new JLabel("ID : ");
	JTextField tfId = new JTextField();
	JButton btnDeleteMenu = new JButton("Delete This Menu");
	JButton btnChangeMenu = new JButton("Change Menu Value");

	public JFrame EditDeleteView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setPreferredSize(dimensiMain);

		add(lEditTitle);
		lEditTitle.setBounds(90, 20, 100, 60);
		lEditTitle.setFont(new Font("Calibri", Font.PLAIN, 24));
		add(lId);
		lId.setBounds(40, 80, 100, 20); // x,y,lebar,tinggi
		add(tfId);
		tfId.setBounds(165, 80, 200, 20);
		tfId.setEditable(false);
		add(lName);
		lName.setBounds(40, 110, 100, 20);
		add(tfName);
		tfName.setBounds(165, 110, 200, 20);
		add(lPrice);
		lPrice.setBounds(40, 140, 100, 20);
		add(tfPrice);
		tfPrice.setBounds(165, 140, 200, 20);
		add(lCategories);
		lCategories.setBounds(40, 170, 100, 20);
		add(cmbCategories);
		cmbCategories.setBounds(165, 170, 200, 20);
		add(btnDeleteMenu);
		btnDeleteMenu.setBounds(430, 80, 150, 20);
		add(btnChangeMenu);
		btnChangeMenu.setBounds(430, 125, 150, 20);
		add(btnBack);
		btnBack.setBounds(430, 170, 150, 20);
		return currentFrame;
	}

	public int getId() {
		return Integer.parseInt(tfId.getText());
	}

	public String getName() {
		return tfName.getText();
	}

	public String getPrice() {
		return tfPrice.getText();	
	}

	public String getCat() {
		String selectCat = (String) cmbCategories.getSelectedItem();
		return selectCat;
	}
}
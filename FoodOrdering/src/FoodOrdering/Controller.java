package FoodOrdering;
import java.awt.event.*;
import javax.swing.*;

public class Controller extends  WindowAdapter{
    Model model;
    View view;

    public Controller ( Model model, View view) {
        this.model = model;
        this.view = view;
        readMainData();
        readHistory();

        view.bOpenAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.getContentPane().removeAll();
                view.refresh();
                showRequest(view.addMenuView());
            }
        });
        view.bOpenOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.getContentPane().removeAll();
                view.refresh();
                readMainDataOrder();
                showRequest(view.newOrder());
            }
        });
        view.btnAddMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = view.getName();
                int price = Integer.parseInt(view.getPrice());
                String categories = view.getCat();
                model.addFood(name, price, categories);
            }
        });
        view.btnClearAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTextField();
            }
        });
        view.tabeleatOrder.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                int baris = view.tabeleatOrder.getSelectedRow();
                int dataTerpilih = Integer.parseInt(view.tabeleatOrder.getValueAt(baris, 0).toString());
                System.out.println(dataTerpilih);
                model.addToBasket(dataTerpilih);
                readDataBasket();
            }
        });
        view.tabeldrinkOrder.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                int baris = view.tabeldrinkOrder.getSelectedRow();
                int dataTerpilih = Integer.parseInt(view.tabeldrinkOrder.getValueAt(baris, 0).toString());
                System.out.println(dataTerpilih);
                model.addToBasket(dataTerpilih);
                readDataBasket();
            }
        });
        view.btnSelesaiOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String namaPemesan = view.getName();
                JOptionPane.showMessageDialog(null,"Pesanan Telah Diproses");
                clearTextField();
                model.addToOrder(namaPemesan);
                model.truncateBasket();
                readDataBasket();
                view.getContentPane().removeAll();
                view.refresh();
                readHistory();
                showRequest(view.mainView());
            }
        });
        view.btnClearOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Apakah Anda Yakin? Semua Order Di Keranjang Akan Dihapus", "Pilih Opsi : ", JOptionPane.YES_NO_OPTION);
                if (input==0) {
                    model.truncateBasket();
                    readDataBasket();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak Jadi Dihapus");
                }
            }
        });
        view.tabeleatMain.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                int baris = view.tabeleatMain.getSelectedRow();
                int dataTerpilih = Integer.parseInt(view.tabeleatMain.getValueAt(baris, 0).toString());
                view.getContentPane().removeAll();
                view.refresh();
                clearTextField();
                setInsideEditView(dataTerpilih);
                showRequest(view.EditDeleteView());
                }
        });
        view.tabeldrinkMain.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                int baris = view.tabeldrinkMain.getSelectedRow();
                int dataTerpilih = Integer.parseInt(view.tabeldrinkMain.getValueAt(baris, 0).toString());
                view.getContentPane().removeAll();
                view.refresh();
                clearTextField();
                setInsideEditView(dataTerpilih);
                showRequest(view.EditDeleteView());
                }
        });
        view.tabelhistory.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mousePressed(e);
                int baris = view.tabelhistory.getSelectedRow();
                int dataTerpilih = Integer.parseInt(view.tabelhistory.getValueAt(baris, 0).toString());
                readDetailHistory(dataTerpilih);
                }
        });
        view.btnDeleteMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = view.getId();
                int input = JOptionPane.showConfirmDialog(null, "Yakin Hapus Menu?", "Pilih Opsi : ", JOptionPane.YES_NO_OPTION);
                view.getContentPane().removeAll();
                if (input==0) {
                    model.deleteMenu(id);
                    readMainData();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak Jadi Dihapus");
                }
                view.refresh();
                clearTextField();
                showRequest(view.mainView());
            }
        });
        view.btnChangeMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = view.getId();
                String name = view.getName();
                int price = Integer.parseInt(view.getPrice());
                String cat = view.getCat();
                int input = JOptionPane.showConfirmDialog(null, "Yakin Mengubah Menu?", "Pilih Opsi : ", JOptionPane.YES_NO_OPTION);
                view.getContentPane().removeAll();
                if (input==0) {
                    model.updateMenu(id, name, price, cat);
                    readMainData();
                } else {
                    JOptionPane.showMessageDialog(null, "Tidak Jadi Dirubah");
                }
                view.refresh();
                clearTextField();
                showRequest(view.mainView());
            }
        });
        view.btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.getContentPane().removeAll();
                view.refresh();
                clearTextField();
                model.truncateBasket();
                readDataBasket();
                showRequest(view.mainView());
            }
        });
    }
    
    public void readMainData() {
        try {
            String dataDrink[][] = new String[model.getBanyakData("Drink")][3];
            String dataEat[][] = new String[model.getBanyakData("Eat")][3];
    
            dataDrink = model.readDataMain("Drink");
            dataEat = model.readDataMain("Eat");  
            view.tabeldrinkMain.setModel((new JTable(dataDrink, view.kolom)).getModel());
            view.tabeleatMain.setModel((new JTable(dataEat, view.kolom)).getModel());
        } catch (IllegalArgumentException i) {
            System.err.println(i); 
        }
    }
    public void readHistory() {
        try {
            String data[][] = new String[model.getBanyakDataOrder()][4];
            data = model.readOrderHistory();
            view.tabelhistory.setModel((new JTable(data, view.kolomH)).getModel());
        } catch (IllegalArgumentException i) {
            System.err.println(i); 
        }
    }
    public void readDetailHistory(int id) {
        try {
            String data[][] = new String[model.getBanyakDataDetail(id)][3];
            data = model.readOrderDetail(id);
            view.ldetailhistory.setText("Order Detail (Order ID : "+id+" )");
            view.tabeldetailhistory.setModel((new JTable(data, view.kolomDH)).getModel());
        } catch (IllegalArgumentException i) {
            System.err.println(i); 
        }
    }
    public void readMainDataOrder() {
        try {
            String dataDrink[][] = new String[model.getBanyakData("Drink")][3];
            String dataEat[][] = new String[model.getBanyakData("Eat")][3];
    
            dataDrink = model.readDataMain("Drink");
            dataEat = model.readDataMain("Eat");  
            view.tabeldrinkOrder.setModel((new JTable(dataDrink, view.kolom)).getModel());
            view.tabeleatOrder.setModel((new JTable(dataEat, view.kolom)).getModel());
        } catch (IllegalArgumentException i) {
            System.err.println(i); 
        }
    }
    public void readDataBasket() {
        String databasket[][] = new String[model.getBanyakDataBasket()+1][3];
        databasket = model.readBasket();
        view.tabelbasket.setModel((new JTable(databasket, view.kolomB)).getModel());
    }
    public void setInsideEditView(int dataTerpilih) {
        String data[] = new String[4];
        data = model.getEditValue(dataTerpilih);
        view.tfId.setText(data[0]);
        view.tfName.setText(data[1]);
        view.tfPrice.setText(data[2]);
        view.cmbCategories.setSelectedItem(data[3]);
    }
    public void clearTextField() {
        view.tfName.setText("");
        view.tfPrice.setText("");
        view.cmbCategories.setSelectedItem("Eat");
    }
    public void showRequest (JFrame frame) {
        frame.setVisible(true);
    }
}

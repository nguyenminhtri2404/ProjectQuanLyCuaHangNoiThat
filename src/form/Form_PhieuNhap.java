/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import Class.DSHoaDon;
import Class.DSKhachHang;
import Class.DSKhuyenMai;
import Class.DSNhanVien;
import Class.DSPhieuNhap;
import Class.DSSanPham;
import Class.DsNhaCC;
import Class.HoaDon;
import Class.KhachHang;
import Class.KhuyenMai;
import Class.NhaCungCap;
import Class.NhanVien;
import Class.PhieuNhap;
import Class.SanPham;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 *
 * @author Triss
 */
public class Form_PhieuNhap extends javax.swing.JPanel {

    /**
     * Creates new form Form
     */
DSNhanVien nv;
DsNhaCC ncc;
DSSanPham sp;
DSKhuyenMai km;
DSPhieuNhap pn;

ArrayList<SanPham> dsSP;
ArrayList<NhanVien> dsNhanVien;
ArrayList<KhuyenMai> dsKhuyenMai;
ArrayList<NhaCungCap> dsncc = new ArrayList<>();
ArrayList<PhieuNhap> dsPN2;
ArrayList<PhieuNhap> dsPN;
int vitri = 0;
int flag = 0;
String maNCC;
String maSP;

public Form_PhieuNhap() {
    initComponents();
    pn = new DSPhieuNhap();
    nv = new DSNhanVien();
    ncc = new DsNhaCC();
    sp = new DSSanPham();
    km = new DSKhuyenMai();
    dsSP = sp.layDanhSachSanPham(); // Khởi tạo dsSP bằng dữ liệu từ database hoặc từ một nguồn khác
    dsPN = new ArrayList<>();
    dsPN2 = pn.layDanhSachPhieuNhap();
    dsNhanVien = nv.layDanhSachNhanVien();
    dsncc = ncc.layDanhSachNCC();
    dsKhuyenMai = km.layDanhSachKhuyenMai();

    txt_MaPN.setEnabled(false);
    //txt_TenSP.setEnabled(false);
    txt_GiaBan.setEnabled(false);
    txt_ThanhTien.setEnabled(false);
    txt_NgayLap.setEnabled(false);

    hienThiComboboxNhanVien(dsNhanVien, cbo_TenNV);
    hienThiComboboxNCC(dsncc, cbo_tenNCC, cbo_SanPham);
    hienThiTextBox(vitri);
    
    

    String header[] = {"Mã SP", "Số lượng", "Giá bán"};
    DefaultTableModel tblModel = new DefaultTableModel(header, 0);
    tbl_PhieuNhap.fixTable(jScrollPane2);
    tbl_PhieuNhap.setModel(tblModel);
    txt_MaPN.setText(pn.SinhMaPN());
    //hien thi textbox ngay lap
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        txt_NgayLap.setText(formattedDate);
    //hienThiTablePhieuNhap();
    //hienThiTableCT_PhieuNhap();
    //hienThiComboboxKhuyenMai(dsKhuyenMai, cbo_KM ,txt_MaSP.getText());
    // Tạo một JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();

        // Tạo một JMenuItem
        JMenuItem deleteItem = new JMenuItem("Xóa");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy vị trí dòng được chọn
                int selectedRow = tbl_PhieuNhap.getSelectedRow();
                if (selectedRow >= 0) {
                    // Xóa dòng từ model
                    DefaultTableModel model = (DefaultTableModel) tbl_PhieuNhap.getModel();
                    model.removeRow(selectedRow);
                    // Xóa dòng từ ArrayList
                    dsPN.remove(selectedRow);
                }
            }
        });

        // Thêm JMenuItem vào JPopupMenu
        popupMenu.add(deleteItem);

        // Thêm MouseListener vào tbl_HoaDon để hiển thị JPopupMenu khi click chuột phải
        tbl_PhieuNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // Hiển thị JPopupMenu tại vị trí chuột
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
}

    private void hienThiComboboxNhanVien(ArrayList<NhanVien> nNhanVien, JComboBox<String> cbo) {
        for (NhanVien n : nNhanVien) {
            cbo.addItem(n.getTenNV());
        }
    }

   // Phương thức hiển thị combobox nhà cung cấp
    private void hienThiComboboxNCC(ArrayList<NhaCungCap> nNhaCungCap, JComboBox<String> cbo, JComboBox<String> cboSanPham) {
    // Thêm các nhà cung cấp vào combobox
    for (NhaCungCap ncc : nNhaCungCap) {
        String tenNCC = ncc.getTenNCC();
        if (tenNCC != null) {
            cbo.addItem(tenNCC);
        }
    }

    // Gán sự kiện cho combobox nhà cung cấp
    cbo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Lấy tên nhà cung cấp đã chọn từ combobox
            Object selectedItem = cbo.getSelectedItem();
            if (selectedItem == null) {
                System.err.println("Không thể lấy được mục đã chọn từ JComboBox NCC.");
                return;
            }
            String nhaCungCap = selectedItem.toString();

            // Tìm mã nhà cung cấp từ tên nhà cung cấp
            String maNCC = null;
            for (NhaCungCap ncc : nNhaCungCap) {
                if (ncc.getTenNCC().equals(nhaCungCap)) {
                    maNCC = ncc.getMaNCC();
                    break;
                }
            }

            // Xóa các mục hiện tại trong combobox sản phẩm
            cboSanPham.removeAllItems();

            // Hiển thị sản phẩm tương ứng với nhà cung cấp đã chọn
            if (maNCC != null) {
                for (SanPham sp : dsSP) {
                    if (sp.getMaNCC().equals(maNCC)) {
                        cboSanPham.addItem(sp.getMaSP()); // Thêm mã sản phẩm vào combobox
                    }
                }
            }
        }
    });
}

    private void xoaTextBox()
    {
//        txt_MaSP.setText("");
        txt_TimKiem.setText("");
        txt_SoLuong.setText("");
        txt_GiaBan.setText("");
    }
    private void hienThiTextBox(int vitri)
    {
        if (vitri >= 0 && vitri < dsPN.size()) {
        PhieuNhap n = dsPN.get(vitri);
//        txt_MaSP.setText(n.maSP);
        //txt_TenSP.setText(n.tenSP);
        txt_MaPN.setText(n.maPhieuNhap);
        txt_GiaBan.setText(String.valueOf(n.DonGia));
        txt_SoLuong.setText(String.valueOf(n.soLuong));
//        Date ngayBatDau = n.NgayLap;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate1 = dateFormat.format(ngayBatDau);
        //txt_NgayLap.setText(formattedDate1);
         // Lấy tên danh mục của sản phẩm
        String maNV =n.getMaNhanVien();
        String tenNV = ""; // Tạo một biến tạm để lưu tên danh mục
        String maNCC =n.getMaNCC();
        String tenNCC = ""; // Tạo một biến tạm để lưu tên danh mục
        // Tìm tên danh mục tương ứng với mã danh mục
        for (NhanVien nvien : dsNhanVien) {
            if (nvien.getMaNV().equals(maNV)) {
                tenNV = nvien.getTenNV();
                break;
            }
        }
        // Tìm tên danh mục tương ứng với mã danh mục
        for (NhaCungCap ncc : dsncc) {
            if (ncc.getMaNCC().equals(maNCC)) {
                tenNCC = ncc.getTenNCC();
                break;
            }
        }
        // Hiển thị tên danh mục trong ComboBox cbo_TenDanhMuc
        cbo_TenNV.setSelectedItem(tenNV); // Sử dụng setSelectedItem thay vì setSelectedIndex
        cbo_tenNCC.setSelectedItem(tenNCC);
        
        //txt.setText(n.KichThuoc); manhacung 
    }
    }
    // Phương thức hiển thị bảng phiếu nhập
    private void hienThiTablePhieuNhap() {
        String[] header = {"Mã SP", "Số Lượng", "Giá Bán"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        for (PhieuNhap pn : dsPN) {
            model.addRow(new Object[]{
                 pn.maSP, pn.soLuong, pn.DonGia
            });
        }
        tbl_PhieuNhap.setModel(model);
        tbl_PhieuNhap.fixTable(jScrollPane2);
        tbl_PhieuNhap.setRowSelectionInterval(vitri, vitri);
    }

//    private void hienThiTableCT_PhieuNhap()
//    {
//        String []Header = {"Mã PN","Mã SP","Số Lượng","Giá Bán"};
//        DefaultTableModel model = new DefaultTableModel(Header,0);
//        for(PhieuNhap n:dsPN)
//        {   
//            Object [] chnt = {n.maPhieuNhap,n.maSP,n.soLuong,n.DonGia};
//            model.addRow(chnt);
//        }
//    }
        // Phương thức chuyển đổi để tạo đối tượng PhieuNhap từ giao diện người dùng
    private PhieuNhap convertToObject(){
        PhieuNhap pn = new PhieuNhap();
        pn.maPhieuNhap = txt_MaPN.getText();
        pn.maNhanVien = dsNhanVien.get(cbo_TenNV.getSelectedIndex()).getMaNV();
        pn.maNCC = dsncc.get(cbo_tenNCC.getSelectedIndex()).getMaNCC();
        pn.NgayLap = Date.valueOf(txt_NgayLap.getText());
        return pn;
    }
    private PhieuNhap convertToObjectForTable() {
    PhieuNhap n = new PhieuNhap();
    n.maSP = (String) cbo_SanPham.getSelectedItem();  // Getting the selected item from JComboBox and casting it to String
    n.soLuong = Integer.parseInt(txt_SoLuong.getText());  // Parsing the quantity from text field
    n.DonGia = Integer.parseInt(txt_GiaBan.getText());  // Parsing the price from text field as float
    return n;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new swing.round.RoundPanel();
        jLabel3 = new javax.swing.JLabel();
        btn_Xem = new component.Button();
        btn_Reset = new component.Button();
        btn_Them = new component.Button();
        jScrollPane22 = new javax.swing.JScrollPane();
        txt_TimKiem = new javax.swing.JTextPane();
        btn_TimKiem = new component.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_PhieuNhap = new swing.table.Table();
        btn_Luu = new component.Button();
        PanelSP = new javax.swing.JPanel();
        lb_MaDanhMuc8 = new javax.swing.JLabel();
        txt_SoLuong = new javax.swing.JTextField();
        lb_MaDanhMuc10 = new javax.swing.JLabel();
        lb_MaDanhMuc11 = new javax.swing.JLabel();
        txt_GiaBan = new javax.swing.JTextField();
        lb_MaDanhMuc13 = new javax.swing.JLabel();
        txt_ThanhTien = new javax.swing.JTextField();
        cbo_SanPham = new javax.swing.JComboBox<>();
        btn_ThemSP = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lb_MaDanhMuc = new javax.swing.JLabel();
        txt_MaPN = new javax.swing.JTextField();
        lb_MaDanhMuc1 = new javax.swing.JLabel();
        cbo_TenNV = new javax.swing.JComboBox<>();
        lb_MaDanhMuc3 = new javax.swing.JLabel();
        txt_NgayLap = new javax.swing.JTextField();
        lb_MaDanhMuc4 = new javax.swing.JLabel();
        cbo_tenNCC = new javax.swing.JComboBox<>();
        btn_ThemNCC = new javax.swing.JButton();
        btn_Cash = new component.Button();

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setBackground(new java.awt.Color(0, 0, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("PHIẾU NHẬP");

        btn_Xem.setBackground(new java.awt.Color(238, 230, 255));
        btn_Xem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_delete.png"))); // NOI18N
        btn_Xem.setText("Xem danh sách PN");
        btn_Xem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Xem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XemActionPerformed(evt);
            }
        });

        btn_Reset.setBackground(new java.awt.Color(238, 230, 255));
        btn_Reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_refesh.png"))); // NOI18N
        btn_Reset.setText("Làm mới");
        btn_Reset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetActionPerformed(evt);
            }
        });

        btn_Them.setBackground(new java.awt.Color(238, 230, 255));
        btn_Them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_add.png"))); // NOI18N
        btn_Them.setText("Thêm");
        btn_Them.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        txt_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane22.setViewportView(txt_TimKiem);

        btn_TimKiem.setBackground(new java.awt.Color(238, 230, 255));
        btn_TimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_search.png"))); // NOI18N
        btn_TimKiem.setText("Tìm kiếm");
        btn_TimKiem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        tbl_PhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã PN", "Mã NV", "Ngày nhập", "Mã NCC", "Mã SP", "Số lượng nhập", "Đơn giá nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_PhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_PhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_PhieuNhap);

        btn_Luu.setBackground(new java.awt.Color(238, 230, 255));
        btn_Luu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_save.png"))); // NOI18N
        btn_Luu.setText("Lưu");
        btn_Luu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuActionPerformed(evt);
            }
        });

        PanelSP.setBackground(new java.awt.Color(255, 255, 255));
        PanelSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lb_MaDanhMuc8.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc8.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc8.setText("Mã sản phẩm:");

        txt_SoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SoLuongActionPerformed(evt);
            }
        });

        lb_MaDanhMuc10.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc10.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc10.setText("Số lượng:");

        lb_MaDanhMuc11.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc11.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc11.setText("Đơn giá:");

        lb_MaDanhMuc13.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc13.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc13.setText("Thành tiền:");

        cbo_SanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_SanPhamActionPerformed(evt);
            }
        });

        btn_ThemSP.setText("...");
        btn_ThemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ThemSPMouseClicked(evt);
            }
        });
        btn_ThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelSPLayout = new javax.swing.GroupLayout(PanelSP);
        PanelSP.setLayout(PanelSPLayout);
        PanelSPLayout.setHorizontalGroup(
            PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSPLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc10)
                        .addGap(81, 81, 81)
                        .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_SoLuong)
                            .addComponent(txt_GiaBan)))
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(txt_ThanhTien))
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_MaDanhMuc11)
                            .addComponent(lb_MaDanhMuc13))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc8)
                        .addGap(37, 37, 37)
                        .addComponent(cbo_SanPham, 0, 345, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ThemSP)
                .addGap(4, 4, 4))
        );
        PanelSPLayout.setVerticalGroup(
            PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_SanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemSP))
                .addGap(20, 20, 20)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_MaDanhMuc10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_MaDanhMuc11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_MaDanhMuc13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lb_MaDanhMuc.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc.setText("Mã phiếu nhập:");

        lb_MaDanhMuc1.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc1.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc1.setText("Tên nhân viên:");

        lb_MaDanhMuc3.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc3.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc3.setText("Tên NCC:");

        lb_MaDanhMuc4.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc4.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc4.setText("Ngày lập:");

        cbo_tenNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_tenNCCActionPerformed(evt);
            }
        });

        btn_ThemNCC.setText("...");
        btn_ThemNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ThemNCCMouseClicked(evt);
            }
        });
        btn_ThemNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNCCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_MaDanhMuc)
                    .addComponent(lb_MaDanhMuc1)
                    .addComponent(lb_MaDanhMuc3)
                    .addComponent(lb_MaDanhMuc4))
                .addGap(54, 54, 54)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_NgayLap)
                    .addComponent(cbo_TenNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_MaPN)
                    .addComponent(cbo_tenNCC, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_ThemNCC)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_MaPN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lb_MaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_MaDanhMuc1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbo_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_tenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemNCC))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_NgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        btn_Cash.setBackground(new java.awt.Color(238, 230, 255));
        btn_Cash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_add.png"))); // NOI18N
        btn_Cash.setText("Tính tiền");
        btn_Cash.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Cash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CashActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(516, 516, 516)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(526, 526, 526))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(PanelSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btn_Reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(46, 46, 46)
                        .addComponent(btn_Them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(39, 39, 39)
                        .addComponent(btn_Xem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(50, 50, 50)
                        .addComponent(btn_Luu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(47, 47, 47)
                        .addComponent(btn_Cash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(96, 96, 96))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(PanelSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Luu, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Cash, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Xem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
         xoaTextBox();
    }//GEN-LAST:event_btn_ResetActionPerformed

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        PhieuNhap newPhieuNhap = convertToObjectForTable();

         for (int i = 0; i < tbl_PhieuNhap.getRowCount(); i++) {
            if (tbl_PhieuNhap.getValueAt(i, 0).toString().equals(newPhieuNhap.getMaSP())) {
                int soLuong = Integer.parseInt(tbl_PhieuNhap.getValueAt(i, 1).toString());
                soLuong += newPhieuNhap.getSoLuong();
                tbl_PhieuNhap.setValueAt(soLuong, i, 1);
                return;
            }
        }

        dsPN.add(newPhieuNhap);
        hienThiTablePhieuNhap();
        
        flag = 1;
        txt_MaPN.setEnabled(false);
        //xoaTextBox();
        
        
        btn_Them.setEnabled(true);
        //btn_Sua.setEnabled(false);
        btn_Xem.setEnabled(false);
    }//GEN-LAST:event_btn_ThemActionPerformed

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        if (txt_TimKiem.getText()!=null)
        {
            dsPN = pn.timKiemPhieuNhap(txt_TimKiem.getText());
            vitri = 0;
            hienThiTablePhieuNhap();
            hienThiTextBox(vitri);
        }
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void tbl_PhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_PhieuNhapMouseClicked
     // TODO add your handling code here:
        int vitri = tbl_PhieuNhap.getSelectedRow();
        if (vitri >= 0 && dsPN.size() > 0){
            hienThiTextBox(vitri);

            //Lấy HD được chọn
            PhieuNhap phieuNhapChon = dsPN.get(vitri);

            //Lấy tenKH, SDT
//            txt_TenNCC.setText(ncc.timKiemNhaCungCap(phieuNhapChon.getMaNCC()).get(0).getTenNCC());
//            txt_DienThoai.setText(ncc.timKiemNhaCungCap(phieuNhapChon.getMaNCC()).get(0).getSDT());
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //txt_NgayLap.setText(formatter.format(phieuNhapChon.getNgayLap()));
            txt_SoLuong.setText(String.valueOf(phieuNhapChon.getSoLuong()));
            String maSP = phieuNhapChon.getMaSP();

            //Hiển thị mã KM trên combobox tương ứng
            //hienThiComboboxKhuyenMai(dsKhuyenMai, cbo_KM, maSP);
            //String maKM = cbo_KM.getSelectedItem().toString();
//            System.out.println("Mã KM: " + maKM);
//            //Select maKM đúng dòng
//            for (int i = 0; i < cbo_KM.getItemCount(); i++){
//                if (dsKhuyenMai.get(i).getMaKM().equals(maKM)){
//                    cbo_KM.setSelectedIndex(i);
//                }
//            }
//           
//            float giaKhuyenMai = km.layGiaKM(maKM, maSP);
//            System.out.println("Mã sản phẩm: " + maSP);
//            System.out.println("Mã khuyến mãi: " + maKM);
//            System.out.println("Giá khuyến mãi: " + giaKhuyenMai);

            //Tính thành tiền
            int soLuong = Integer.parseInt(txt_SoLuong.getText());
            float giaBan = Float.parseFloat(txt_GiaBan.getText());
            float thanhTien = (soLuong * giaBan);
            DecimalFormat df =  new DecimalFormat("#");
            df.setMaximumFractionDigits(0);
            txt_ThanhTien.setText(df.format(thanhTien));
        }
        
    }//GEN-LAST:event_tbl_PhieuNhapMouseClicked

    private void btn_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuActionPerformed

        
        PhieuNhap pnNhap = convertToObject();
        if (flag == 1) {
            pn.themPhieuNhap(pnNhap);
            
        // Lặp qua từng sản phẩm trong hóa đơn để thêm vào bảng CT_HOADON
        for (int i = 0; i < tbl_PhieuNhap.getRowCount(); i++) {
            String masp = tbl_PhieuNhap.getValueAt(i, 0).toString();
            int soluong = Integer.parseInt(tbl_PhieuNhap.getValueAt(i, 1).toString());
            int dongia = Integer.parseInt(tbl_PhieuNhap.getValueAt(i, 2).toString());
            
            pn.themCTPhieuNhap(txt_MaPN.getText(),masp,soluong,dongia);
            System.out.println("i: " + i);
        }
        }
//        }else if (flag == 2)
//        {
//            pn.capNhatPhieuNhap(phieunhap);
//        
//        
//        }
//        else if (flag == 3)
//        {
//            int kq= JOptionPane.showConfirmDialog(this,"Bạn có muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_CANCEL_OPTION);
//            if(kq==JOptionPane.YES_OPTION) {
//               pn.xoaPhieuNhap(phieunhap);
//            }
//
//        }


        flag = 0;
        pn = new DSPhieuNhap();
        dsPN = pn.layDanhSachPhieuNhap();
        //hienThiTablePhieuNhap();
        
        btn_Luu.setEnabled(false);
        btn_Them.setEnabled(true);
        //btn_Sua.setEnabled(true);
        btn_Xem.setEnabled(true);
    }//GEN-LAST:event_btn_LuuActionPerformed

    private void txt_SoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SoLuongActionPerformed

        try {
            int soLuongStr = Integer.parseInt(txt_SoLuong.getText());

            // Kiểm tra số lượng có phải là số dương không
            if (soLuongStr < 0) {
                JOptionPane.showMessageDialog(null, "Số lượng không thể là số âm. Vui lòng nhập số lượng hợp lệ.");
                return;
            }

            // Lấy sản phẩm được chọn từ combobox
            String maSP = (String) cbo_SanPham.getSelectedItem();
            int soLuongSP = sp.laySoLuong(maSP);

            // Kiểm tra xem số lượng nhập vào có vượt quá số lượng tồn kho không
            if (soLuongStr > soLuongSP) {
                JOptionPane.showMessageDialog(null, "Số lượng sản phẩm hiện tại còn " + soLuongSP + " sản phẩm. Vui lòng nhập số lượng nhỏ hơn.");
                return;
            }

            // Cập nhật giá bán sỉ của sản phẩm từ combobox
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(0);
            txt_GiaBan.setText(df.format(sp.timKiemSanPham(maSP).get(0).getGiaBan()));

            // Cập nhật lại cột số lượng trong bảng tbl_PhieuNhap
            int selectedRow = tbl_PhieuNhap.getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) tbl_PhieuNhap.getModel();
                model.setValueAt(soLuongStr, selectedRow, 1); // Cập nhật cột "Số Lượng"

                // Cập nhật lại đối tượng PhieuNhap trong danh sách
                dsPN.get(selectedRow).setSoLuong(soLuongStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là một số nguyên. Vui lòng nhập số lượng hợp lệ.");
        }
    }//GEN-LAST:event_txt_SoLuongActionPerformed

    private void btn_ThemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ThemSPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ThemSPMouseClicked

    private void btn_ThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemSPActionPerformed
        // TODO add your handling code here:
        Form_NhapSanPham frmsp = new Form_NhapSanPham();

        JFrame panel = new JFrame();
        panel.setSize(1280, 720);
        panel.setLocationRelativeTo(null);
        panel.getContentPane().add(frmsp);
        panel.setVisible(true);

        panel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SanPham spMoi = frmsp.getSPMoiNhat();
                if (spMoi != null) {
                    cbo_SanPham.addItem(spMoi.getMaSP());
                    //txt_TenSP.setText(spMoi.getTenSP());
                }
                System.out.println("Sản Phẩm mới : " + spMoi);
                System.out.println("Mã sản phẩm mới: "+ spMoi.getMaSP());
            }
        });
    }                                          
    private boolean isUserSelection = true;
    private void Cbo_SanPhamActionPerformed(java.awt.event.ActionEvent evt) {                                            
      if (isUserSelection) {
            // Kiểm tra nếu combo box có mục được chọn
            if (cbo_SanPham.getSelectedItem() != null) {
                // Lấy dữ liệu đã chọn từ combo box (giả sử là mã sản phẩm)
                String maSanPham = (String) cbo_SanPham.getSelectedItem();

                // Giả sử bạn có một phương thức để lấy tên sản phẩm từ mã sản phẩm
                String tenSanPham = layTenSanPhamTuMa(maSanPham);

                // Hiển thị tên sản phẩm trong text box txt_TenSP
                //txt_TenSP.setText(tenSanPham);
            }
        }
    }

    private String layTenSanPhamTuMa(String maSanPham) {
        // Thực hiện việc lấy tên sản phẩm từ mã sản phẩm, có thể là từ danh sách hoặc cơ sở dữ liệu
        // Trong ví dụ này, ta giả định danh sách `dsSP` chứa các đối tượng `SanPham`
        if (dsSP != null) {
            for (SanPham sanPham : dsSP) {
                if (sanPham.getMaSP().equals(maSanPham)) {
                    return sanPham.getTenSP();
                }
            }
        }
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy mã sản phẩm hoặc dsSP là null
    }//GEN-LAST:event_btn_ThemSPActionPerformed

    private void btn_CashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CashActionPerformed
         // TODO add your handling code here:
        //Lấy GIAKHUYENMAI từ thông qua combobox KM
//        String maKM = cbo_KM.getSelectedItem().toString();
          String maSP = cbo_SanPham.getSelectedItem().toString();

//        float giaKhuyenMai = km.layGiaKM(maKM, maSP);
//        System.out.println("Mã sản phẩm: " + maSP);
//        System.out.println("Mã khuyến mãi: " + maKM);
//        System.out.println("Giá khuyến mãi: " + giaKhuyenMai);

        //Tính thành tiền
        int soLuong = Integer.parseInt(txt_SoLuong.getText());
        float giaBan = Float.parseFloat(txt_GiaBan.getText());
        float thanhTien = (soLuong * giaBan);
        DecimalFormat df =  new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        txt_ThanhTien.setText(df.format(thanhTien));
    }//GEN-LAST:event_btn_CashActionPerformed

    private void btn_ThemNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ThemNCCMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ThemNCCMouseClicked

    private void btn_ThemNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNCCActionPerformed
        // TODO add your handling code here:
         // TODO add your handling code here:
        //show form khach hang
        Form_NhapNhaCungCap frmncc = new Form_NhapNhaCungCap();

        JFrame panel = new JFrame();
        panel.setSize(1280, 720);
        panel.setLocationRelativeTo(null);
        panel.getContentPane().add(frmncc);
        panel.setVisible(true);

        panel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                NhaCungCap nccMoi = frmncc.getNCCMoiNhat();
                if (nccMoi != null) {
                    cbo_tenNCC.addItem(nccMoi.getTenNCC());
                    //txt_sodienthoai.setText(nccMoi.getSDT());
                }
                System.out.println("Nhà cung cấp mới : " + nccMoi);
                System.out.println("Mã nhà cung cấp mới: "+ nccMoi.getMaNCC());
            }
        });
        
    }//GEN-LAST:event_btn_ThemNCCActionPerformed

    private void cbo_tenNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_tenNCCActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cbo_tenNCCActionPerformed

    private void cbo_SanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_SanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_SanPhamActionPerformed

    private void btn_XemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XemActionPerformed
        Form_XemChiTietPN frmXemHD = new Form_XemChiTietPN();
        JFrame panel = new JFrame();
        panel.setSize(1280, 720);
        panel.setLocationRelativeTo(null);
        panel.getContentPane().add(frmXemHD);
        panel.setVisible(true);
    }//GEN-LAST:event_btn_XemActionPerformed
    // Hãy gọi phương thức này khi combobox được mở để chọn
    private void openCbo_SanPham() {
        isUserSelection = true;
    }

    // Hãy gọi phương thức này khi người dùng đã chọn một mục trong combobox
    private void selectCbo_SanPham() {
        isUserSelection = false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelSP;
    private component.Button btn_Cash;
    private component.Button btn_Luu;
    private component.Button btn_Reset;
    private component.Button btn_Them;
    private javax.swing.JButton btn_ThemNCC;
    private javax.swing.JButton btn_ThemSP;
    private component.Button btn_TimKiem;
    private component.Button btn_Xem;
    private javax.swing.JComboBox<String> cbo_SanPham;
    private javax.swing.JComboBox<String> cbo_TenNV;
    private javax.swing.JComboBox<String> cbo_tenNCC;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JLabel lb_MaDanhMuc;
    private javax.swing.JLabel lb_MaDanhMuc1;
    private javax.swing.JLabel lb_MaDanhMuc10;
    private javax.swing.JLabel lb_MaDanhMuc11;
    private javax.swing.JLabel lb_MaDanhMuc13;
    private javax.swing.JLabel lb_MaDanhMuc3;
    private javax.swing.JLabel lb_MaDanhMuc4;
    private javax.swing.JLabel lb_MaDanhMuc8;
    private swing.round.RoundPanel roundPanel1;
    private swing.table.Table tbl_PhieuNhap;
    private javax.swing.JTextField txt_GiaBan;
    private javax.swing.JTextField txt_MaPN;
    private javax.swing.JTextField txt_NgayLap;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JTextField txt_ThanhTien;
    private javax.swing.JTextPane txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}

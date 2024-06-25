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
import Class.DanhMuc;
import Class.HoaDon;
import Class.KhachHang;
import Class.KhuyenMai;
import Class.NhanVien;
import Class.PhieuNhap;
import Class.SanPham;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import form.Form_NhapKhachHang;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Triss
 */
public class Form_NhapHoaDon extends javax.swing.JPanel {

    /**
     * Creates new form Form_NhapHoaDon
     */
    DSNhanVien nv;
    DSKhachHang kh;
    DSHoaDon hd;
    DSSanPham sp;
    DSKhuyenMai km;
    DSPhieuNhap pn;
    
    
    ArrayList<HoaDon> dsHD;
    ArrayList<HoaDon> dsHD2;
    ArrayList<SanPham> dsSP;
    ArrayList<NhanVien> dsNhanVien;
    ArrayList<KhuyenMai> dsKhuyenMai;
    ArrayList<PhieuNhap> dsPhieuNhap;
    ArrayList<KhachHang> dsKH = new ArrayList<>();
    int vitri = 0;
    int flag = 0;
    String maKH;
    public Form_NhapHoaDon() {
        initComponents();
        hd = new DSHoaDon();
        nv = new DSNhanVien();
        kh = new DSKhachHang();
        sp = new DSSanPham();
        km = new DSKhuyenMai();
        pn = new DSPhieuNhap();
        dsHD = new ArrayList<>();
        dsHD2 = hd.layDanhSachHoaDon();
        dsNhanVien = nv.layDanhSachNhanVien();
        dsKH = kh.layDanhSachKhachHang();
        dsKhuyenMai = km.layDanhSachKhuyenMai();
        txt_MaHD.setEnabled(false);
        txt_MaHD.setText(hd.SinhMaHD());
        txt_TenSP.setEnabled(false);
        txt_GiaBan.setEnabled(false);
        txt_ThanhTien.setEnabled(false);
        txt_NgayLap.setEnabled(false);

        //hien thi textbox ngay lap
         Date date = new Date(System.currentTimeMillis());
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = dateFormat.format(date);
         txt_NgayLap.setText(formattedDate);


        
        
        
        hienThiComboboxNhanVien(dsNhanVien,cbo_TenNV);
        //hienThiTextBox(vitri);
    
       //hienThiTableHoaDon();
        //hienThiTableCT_HoaDon();
        hienThiComboboxKhuyenMai(dsKhuyenMai, jComboBox1,txt_MaSP.getText());

        // Tạo một JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();

        // Tạo một JMenuItem
        JMenuItem deleteItem = new JMenuItem("Xóa");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy vị trí dòng được chọn
                int selectedRow = tbl_HoaDon.getSelectedRow();
                if (selectedRow >= 0) {
                    // Xóa dòng từ model
                    DefaultTableModel model = (DefaultTableModel) tbl_HoaDon.getModel();
                    model.removeRow(selectedRow);
                    // Xóa dòng từ ArrayList
                    dsHD.remove(selectedRow);
                    //Cập nhật lại tổng tiền
                    float tongTien = 0;
                    for (int i = 0; i < tbl_HoaDon.getRowCount(); i++) {
                        int soluong = Integer.parseInt(tbl_HoaDon.getValueAt(i, 1).toString());
                        float giaban = Float.parseFloat(tbl_HoaDon.getValueAt(i, 2).toString());
                        tongTien += soluong * giaban;
                    }
                    DecimalFormat df = new DecimalFormat("#,###,###");
                    txt_TongTienHD.setText(df.format(tongTien));
                }
            }
        });

        // Thêm JMenuItem vào JPopupMenu
        popupMenu.add(deleteItem);

        // Thêm MouseListener vào tbl_HoaDon để hiển thị JPopupMenu khi click chuột phải
        tbl_HoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // Hiển thị JPopupMenu tại vị trí chuột
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });


        String header[] = {"Mã SP", "Số lượng", "Giá bán"};
        DefaultTableModel tblModel = new DefaultTableModel(header, 0);
        tbl_HoaDon.fixTable(jScrollPane2);
        tbl_HoaDon.setModel(tblModel);


    }
    
    private void hienThiComboboxNhanVien(ArrayList<NhanVien> nNhanVien, JComboBox<String> cbo){
        for (NhanVien n:nNhanVien)
        {
            cbo.addItem(n.getTenNV());
        }
    }

    //hiển thị combobox Mã khuyến mãi theo mã sản phẩm
    private void hienThiComboboxKhuyenMai(ArrayList<KhuyenMai> nKhuyenMai, JComboBox<String> cbo, String maSP) {
    cbo.removeAllItems();
    for (KhuyenMai n : nKhuyenMai) {
        if (n.getMaSP().equals(maSP)) {
            // Convert int to String
            cbo.addItem(n.getMaKM());
            //cbo.addItem(String.valueOf(n.getGiaKM()));
        }
    }
}
    
    private void xoaTextBox()
    {
        txt_MaSP.setText("");
        txt_TimKiem.setText("");
        txt_SoLuong.setText("");
        txt_GiaBan.setText("");
    }
    
    private void hienThiTextBox(int vitri)
    {
        if (vitri >= 0 && vitri < dsHD.size()) {
        HoaDon n = dsHD.get(vitri);
        txt_MaSP.setText(n.maSP);
        txt_TenSP.setText(n.tenSP);
        txt_MaHD.setText(n.maHD);
        txt_GiaBan.setText(String.valueOf(n.GiaBan));
        txt_SoLuong.setText(String.valueOf(n.SoLuong));
//        Date ngayBatDau = n.NgayLap;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate1 = dateFormat.format(ngayBatDau);
//        txt_NgayLap.setText(formattedDate1);
        txt_ThanhTien.setText(String.valueOf(n.ThanhTien));
         // Lấy tên danh mục của sản phẩm
        String maNV =n.getMaNV();
        String tenNV = ""; // Tạo một biến tạm để lưu tên danh mục
        

        // Tìm tên danh mục tương ứng với mã danh mục
        for (NhanVien nvien : dsNhanVien) {
            if (nvien.getMaNV().equals(maNV)) {
                tenNV = nvien.getTenNV();
                break;
            }
        }

        // Hiển thị tên danh mục trong ComboBox cbo_TenDanhMuc
        cbo_TenNV.setSelectedItem(tenNV); // Sử dụng setSelectedItem thay vì setSelectedIndex

        
        //txt.setText(n.KichThuoc); manhacung ,
    }
    }
    
    private void hienThiTableHoaDon() {
        String[] header = {"Mã SP", "Số Lượng", "Giá Bán"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        for (HoaDon hd : dsHD) {
            model.addRow(new Object[]{
                 hd.maSP, hd.SoLuong, hd.GiaBan
            });
        }
        tbl_HoaDon.setModel(model);
        tbl_HoaDon.fixTable(jScrollPane2);
        tbl_HoaDon.setRowSelectionInterval(vitri, vitri);
    }
    
   

    private HoaDon convertToObject(){
        HoaDon n = new HoaDon();
        n.maHD = txt_MaHD.getText();
        n.maNV = dsNhanVien.get(cbo_TenNV.getSelectedIndex()).getMaNV();
        n.maKH = maKH;
        n.NgayLap = Date.valueOf(txt_NgayLap.getText());
        n.ThanhTien = Float.parseFloat(txt_ThanhTien.getText());
        return n;
    }
    
    private HoaDon convertToObjectForTable()
    {
        HoaDon n = new HoaDon();
        n.maSP = txt_MaSP.getText();
        n.SoLuong = Integer.parseInt(txt_SoLuong.getText());
        n.GiaBan = Integer.parseInt(txt_GiaBan.getText());
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_HoaDon = new swing.table.Table();
        btn_XemCTHoaDon = new component.Button();
        jScrollPane21 = new javax.swing.JScrollPane();
        txt_TimKiem = new javax.swing.JTextPane();
        btn_Luu = new component.Button();
        btn_TimKiem = new component.Button();
        PanelSP = new javax.swing.JPanel();
        txt_MaSP = new javax.swing.JTextField();
        lb_MaDanhMuc8 = new javax.swing.JLabel();
        lb_MaDanhMuc9 = new javax.swing.JLabel();
        txt_TenSP = new javax.swing.JTextField();
        txt_SoLuong = new javax.swing.JTextField();
        lb_MaDanhMuc10 = new javax.swing.JLabel();
        lb_MaDanhMuc11 = new javax.swing.JLabel();
        txt_GiaBan = new javax.swing.JTextField();
        lb_MaDanhMuc12 = new javax.swing.JLabel();
        lb_MaDanhMuc13 = new javax.swing.JLabel();
        txt_ThanhTien = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        btn_Reset = new component.Button();
        jPanel3 = new javax.swing.JPanel();
        lb_MaDanhMuc = new javax.swing.JLabel();
        txt_MaHD = new javax.swing.JTextField();
        lb_MaDanhMuc1 = new javax.swing.JLabel();
        cbo_TenNV = new javax.swing.JComboBox<>();
        lb_MaDanhMuc2 = new javax.swing.JLabel();
        txt_DienThoai = new javax.swing.JTextField();
        lb_MaDanhMuc3 = new javax.swing.JLabel();
        txt_TenKH = new javax.swing.JTextField();
        txt_NgayLap = new javax.swing.JTextField();
        lb_MaDanhMuc4 = new javax.swing.JLabel();
        btn_ThemKH = new javax.swing.JButton();
        btn_ThemHD = new component.Button();
        jLabel2 = new javax.swing.JLabel();
        txt_TongTienHD = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_TienThoiLai = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_TienKhachDua = new javax.swing.JTextField();

        setBackground(new java.awt.Color(250, 250, 250));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tbl_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã KH", "Mã NV", "Ngày lập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_HoaDon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        btn_XemCTHoaDon.setBackground(new java.awt.Color(238, 230, 255));
        btn_XemCTHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_add.png"))); // NOI18N
        btn_XemCTHoaDon.setText("Xem danh sách HD");
        btn_XemCTHoaDon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_XemCTHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XemCTHoaDonActionPerformed(evt);
            }
        });

        txt_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane21.setViewportView(txt_TimKiem);

        btn_Luu.setBackground(new java.awt.Color(238, 230, 255));
        btn_Luu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_save.png"))); // NOI18N
        btn_Luu.setText("Lưu");
        btn_Luu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuActionPerformed(evt);
            }
        });

        btn_TimKiem.setBackground(new java.awt.Color(238, 230, 255));
        btn_TimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_search.png"))); // NOI18N
        btn_TimKiem.setText("Tìm kiếm");
        btn_TimKiem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        PanelSP.setBackground(new java.awt.Color(255, 255, 255));
        PanelSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        txt_MaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaSPActionPerformed(evt);
            }
        });

        lb_MaDanhMuc8.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc8.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc8.setText("Mã sản phẩm:");

        lb_MaDanhMuc9.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc9.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc9.setText("Tên sản phẩm:");

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

        lb_MaDanhMuc12.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc12.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc12.setText("Khuyến mãi:");

        lb_MaDanhMuc13.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc13.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc13.setText("Thành tiền:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout PanelSPLayout = new javax.swing.GroupLayout(PanelSP);
        PanelSP.setLayout(PanelSPLayout);
        PanelSPLayout.setHorizontalGroup(
            PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelSPLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc8)
                        .addGap(40, 40, 40)
                        .addComponent(txt_MaSP))
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_MaDanhMuc9)
                            .addComponent(lb_MaDanhMuc10))
                        .addGap(33, 33, 33)
                        .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_SoLuong)
                            .addComponent(txt_TenSP)
                            .addComponent(txt_GiaBan)))
                    .addComponent(lb_MaDanhMuc11)
                    .addComponent(lb_MaDanhMuc13)
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc12)
                        .addGap(52, 52, 52)
                        .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ThanhTien)
                            .addComponent(jComboBox1, 0, 285, Short.MAX_VALUE))))
                .addGap(41, 41, 41))
        );
        PanelSPLayout.setVerticalGroup(
            PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_MaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_MaDanhMuc10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_MaDanhMuc11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lb_MaDanhMuc12, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelSPLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(PanelSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_MaDanhMuc13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        btn_Reset.setBackground(new java.awt.Color(238, 230, 255));
        btn_Reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_refesh.png"))); // NOI18N
        btn_Reset.setText("Làm mới");
        btn_Reset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        lb_MaDanhMuc.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc.setText("Mã hóa đơn:");

        lb_MaDanhMuc1.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc1.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc1.setText("Tên nhân viên:");

        lb_MaDanhMuc2.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc2.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc2.setText("Điện thoại KH:");

        txt_DienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DienThoaiActionPerformed(evt);
            }
        });

        lb_MaDanhMuc3.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc3.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc3.setText("Tên KH:");

        lb_MaDanhMuc4.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc4.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc4.setText("Ngày lập:");

        btn_ThemKH.setText("...");
        btn_ThemKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ThemKHMouseClicked(evt);
            }
        });
        btn_ThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc2)
                        .addGap(58, 58, 58)
                        .addComponent(txt_DienThoai))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_MaDanhMuc)
                            .addComponent(lb_MaDanhMuc1))
                        .addGap(54, 54, 54)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_TenNV, 0, 282, Short.MAX_VALUE)
                            .addComponent(txt_MaHD)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc3)
                        .addGap(117, 117, 117)
                        .addComponent(txt_TenKH))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lb_MaDanhMuc4)
                        .addGap(105, 105, 105)
                        .addComponent(txt_NgayLap)))
                .addGap(18, 18, 18)
                .addComponent(btn_ThemKH)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txt_MaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lb_MaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_MaDanhMuc1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbo_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_DienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemKH))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_MaDanhMuc4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_NgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );

        btn_ThemHD.setBackground(new java.awt.Color(238, 230, 255));
        btn_ThemHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_add.png"))); // NOI18N
        btn_ThemHD.setText("Thêm");
        btn_ThemHD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_ThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemHDActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("HÓA ĐƠN BÁN HÀNG");

        txt_TongTienHD.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_TongTienHD.setForeground(new java.awt.Color(255, 0, 51));
        txt_TongTienHD.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Tổng tiền:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Tiền khách trả:");

        txt_TienThoiLai.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txt_TienThoiLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TienThoiLaiActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Tiền thối lại:");

        txt_TienKhachDua.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txt_TienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TienKhachDuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(395, 395, 395)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addGap(364, 364, 364))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(PanelSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txt_TongTienHD, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(114, 114, 114)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_TienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_TienThoiLai, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(100, 100, 100))))
            .addGroup(layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(btn_ThemHD, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addGap(63, 63, 63)
                .addComponent(btn_XemCTHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(90, 90, 90)
                .addComponent(btn_Reset, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addGap(74, 74, 74)
                .addComponent(btn_Luu, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(169, 169, 169))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Luu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XemCTHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane21)
                    .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_TienThoiLai)
                        .addComponent(txt_TienKhachDua))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_TongTienHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(60, 60, 60))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemKHActionPerformed
        // TODO add your handling code here:
        //show form khach hang
        Form_NhapKhachHang frmKH = new Form_NhapKhachHang();
        
        JFrame panel = new JFrame();
        panel.setSize(1280, 720); 
        panel.setLocationRelativeTo(null); 
        panel.getContentPane().add(frmKH);
        panel.setVisible(true);
    
  
        panel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                KhachHang khachHangMoi = frmKH.getKhachHangMoiNhat();
                if (khachHangMoi != null) {
                    txt_DienThoai.setText(khachHangMoi.getSDT());
                    txt_TenKH.setText(khachHangMoi.getTenKH());
                }
                System.out.println("Khach hang moi: " + khachHangMoi);
                System.out.println("Mã khách hàng: "+ khachHangMoi.getMaKH());
            }
        });
            
        
    }//GEN-LAST:event_btn_ThemKHActionPerformed

    private void btn_XemCTHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XemCTHoaDonActionPerformed
        Form_XemChiTietHoaDon frmXemHD = new Form_XemChiTietHoaDon();
        JFrame panel = new JFrame();
        panel.setSize(1280, 720);
        panel.setLocationRelativeTo(null);
        panel.getContentPane().add(frmXemHD);
        panel.setVisible(true);

    }//GEN-LAST:event_btn_XemCTHoaDonActionPerformed

    private void btn_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuActionPerformed
        // TODO add your handling code here:
        System.out.println("Giá trị của txt_ThanhTien trước khi convertToObject() được gọi: " + txt_ThanhTien.getText());   
        HoaDon hdDon = convertToObject();
        if (flag == 1) {
            hd.themHoaDon(hdDon);
       
        // Lặp qua từng sản phẩm trong hóa đơn để thêm vào bảng CT_HOADON
        for (int i = 0; i < tbl_HoaDon.getRowCount(); i++) {
            String masp = tbl_HoaDon.getValueAt(i, 0).toString();
            int soluong = Integer.parseInt(tbl_HoaDon.getValueAt(i, 1).toString());
            int dongia = Integer.parseInt(tbl_HoaDon.getValueAt(i, 2).toString());
            
            hd.themCTHoaDon(txt_MaHD.getText(),masp,soluong,dongia);
            System.out.println("i: " + i);
        }
        } else if (flag == 2) {
            hd.capNhatHoaDon(hdDon);
        } else if (flag == 3) {
            int kq = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "Thông báo", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_CANCEL_OPTION);
            if (kq == JOptionPane.YES_OPTION) {
                hd.xoaHoaDon(hdDon);
            }
        }


        flag = 0;
        txt_MaHD.setText(hd.SinhMaHD());

        // hd = new DSHoaDon();
        // dsHD2 = hd.layDanhSachHoaDon();
        // hienThiTableHoaDon();
        
        btn_Luu.setEnabled(false);
        btn_ThemHD.setEnabled(true);
        
    }//GEN-LAST:event_btn_LuuActionPerformed

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
         if (txt_TimKiem.getText()!=null)
        {
            dsHD = hd.timKiemHoaDon(txt_TimKiem.getText());
            vitri = 0;
            hienThiTableHoaDon();
            hienThiTextBox(vitri);
        }
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
        // TODO add your handling code here:
        xoaTextBox();
    }//GEN-LAST:event_btn_ResetActionPerformed

    private void btn_ThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemHDActionPerformed

        txt_MaHD.setEnabled(false);

        HoaDon newHoaDon = convertToObjectForTable();

        //kiêm tra xem mã sản phẩm đã tồn tại trong bảng chưa
        for (int i = 0; i < tbl_HoaDon.getRowCount(); i++) {
            if (tbl_HoaDon.getValueAt(i, 0).toString().equals(newHoaDon.getMaSP())) {
                int soLuong = Integer.parseInt(tbl_HoaDon.getValueAt(i, 1).toString());
                soLuong += newHoaDon.getSoLuong();
                tbl_HoaDon.setValueAt(soLuong, i, 1);
                return;
            }
        }

        dsHD.add(newHoaDon);

        // in danh sách hóa đơn
        System.out.println("Danh sách hóa đơn: " + dsHD);

        hienThiTableHoaDon();

        //Tính tổng tiền cột Thành tiền
        float tongTien = 0;
        for (int i = 0; i < tbl_HoaDon.getRowCount(); i++) {
            int soLuong = Integer.parseInt(tbl_HoaDon.getValueAt(i, 1).toString());
            float giaBan = Float.parseFloat(tbl_HoaDon.getValueAt(i, 2).toString());
            tongTien += soLuong * giaBan;
        }
        DecimalFormat df = new DecimalFormat("#,###,###");
        txt_TongTienHD.setText(df.format(tongTien));
        
        flag = 1;

        btn_ThemHD.setEnabled(true);
     
    }//GEN-LAST:event_btn_ThemHDActionPerformed

    private void btn_ThemKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ThemKHMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ThemKHMouseClicked

    private void txt_DienThoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DienThoaiActionPerformed
        // TODO add your handling code here:
        //Trường hợp tìm thấy số điện thoại khách hàng
        if (txt_DienThoai.getText() != "" && kh.DanhSachKhachHang_TheoSDT(txt_DienThoai.getText()).size() > 0 ){
            dsKH = kh.DanhSachKhachHang_TheoSDT(txt_DienThoai.getText());
            KhachHang k = dsKH.get(0);
            maKH = k.getMaKH();
            txt_TenKH.setText(k.getTenKH());
        }
        //Trường hợp không tìm thấy số điện thoại khách hàng
        else{
            JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với số điện thoại trên");
            Form_NhapKhachHang frmKH = new Form_NhapKhachHang();
            
            JFrame panel = new JFrame();
            panel.setSize(1280, 720); 
            panel.setLocationRelativeTo(null); 
            panel.getContentPane().add(frmKH);
            panel.setVisible(true);
        
            //Kiểm tra xem form KhachHang đã đóng chưa
            panel.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    KhachHang khachHangMoi = frmKH.getKhachHangMoiNhat();
                    if (khachHangMoi != null) {
                        txt_DienThoai.setText(khachHangMoi.getSDT());
                        txt_TenKH.setText(khachHangMoi.getTenKH());
                    }
                    System.out.println("Khach hang moi: " + khachHangMoi);
                    System.out.println("Mã khách hàng: "+ khachHangMoi.getMaKH());
                }
            });
        }
    }//GEN-LAST:event_txt_DienThoaiActionPerformed

    private void txt_MaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaSPActionPerformed
        // TODO add your handling code here:
        if (txt_MaSP.getText() != "" && sp.timKiemSanPham(txt_MaSP.getText()).size() > 0){
            dsSP = sp.timKiemSanPham(txt_MaSP.getText());
            SanPham s = dsSP.get(0);
            txt_TenSP.setText(s.getTenSP());
            
            hienThiComboboxKhuyenMai(dsKhuyenMai, jComboBox1,txt_MaSP.getText());
            // DecimalFormat df =  new DecimalFormat("#");
            // df.setMaximumFractionDigits(0);
            // txt_GiaBan.setText(df.format(s.getGiaBan()));;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm với mã sản phẩm trên");
        }
    }//GEN-LAST:event_txt_MaSPActionPerformed

    private void txt_SoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SoLuongActionPerformed
        // TODO add your handling code here:

        int soLuongSP =  sp.laySoLuong(txt_MaSP.getText());

        System.out.println("Số lượng nhập: " + soLuongSP);

        int soLuong = Integer.parseInt(txt_SoLuong.getText());

        //Nếu soLuong > soLuongNhap thì thông báo
        if (soLuong > soLuongSP){
            JOptionPane.showMessageDialog(null, "Số lượng sản phẩm hiện tại còn " + soLuongSP + " sản phẩm. Vui lòng nhập số lượng nhỏ hơn");
            return;
        }
    
        //Nếu soLuong > 10 thì lấy cột GIABANSI
        if (soLuong > 10){
            //Hiển thị thông báo là bán theo giá bán sỉ
            JOptionPane.showMessageDialog(null, "Số lượng mua lớn hơn 10, giá bán sỉ được áp dụng");
            DecimalFormat df =  new DecimalFormat("#");
            df.setMaximumFractionDigits(0);
            txt_GiaBan.setText(df.format(sp.timKiemSanPham(txt_MaSP.getText()).get(0).getGiaBanSi()));
        }
        else{
            DecimalFormat df =  new DecimalFormat("#");
            df.setMaximumFractionDigits(0);
            txt_GiaBan.setText(df.format(sp.timKiemSanPham(txt_MaSP.getText()).get(0).getGiaBan()));
        }

      jComboBox1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Lấy GIAKHUYENMAI từ thông qua combobox KM
            String maSP = txt_MaSP.getText();
            String maKM = jComboBox1.getSelectedItem().toString();

            float giaKhuyenMai = km.layGiaKM(maKM, maSP);
            System.out.println("Mã sản phẩm: " + maSP);
            System.out.println("Mã khuyến mãi: " + maKM);
            System.out.println("Giá khuyến mãi: " + giaKhuyenMai);

            // Tính thành tiền
            int soLuong = Integer.parseInt(txt_SoLuong.getText());
            float giaBan = Float.parseFloat(txt_GiaBan.getText());
            float thanhTien = (soLuong * giaBan) - giaKhuyenMai;
            DecimalFormat df =  new DecimalFormat("#");
            df.setMaximumFractionDigits(0);
            txt_ThanhTien.setText(df.format(thanhTien));
        }
    });
        
        //cập nhật lại cột số lượng trong tbl_HoaDon
        int selectedRow = tbl_HoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) tbl_HoaDon.getModel();
            model.setValueAt(soLuong, selectedRow, 1); // Update the "Số Lượng" column

            // Update the corresponding HoaDon object in the list
            dsHD.get(selectedRow).setSoLuong(soLuong);
        }
        
      float tongTien = 0;
    for (int i = 0; i < tbl_HoaDon.getRowCount(); i++) {
        int soluong = Integer.parseInt(tbl_HoaDon.getValueAt(i, 1).toString());
        float giaban = Float.parseFloat(tbl_HoaDon.getValueAt(i, 2).toString());
        String maSP = tbl_HoaDon.getValueAt(i, 0).toString(); // Assuming the product ID is in the first column
        String maKM = jComboBox1.getSelectedItem().toString();
        float giaKhuyenMai = km.layGiaKM(maKM, maSP);
        tongTien += soluong * (giaban - giaKhuyenMai);
    }

    DecimalFormat df = new DecimalFormat("#,###,###");
    txt_TongTienHD.setText(df.format(tongTien));

    }//GEN-LAST:event_txt_SoLuongActionPerformed

    private void tbl_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HoaDonMouseClicked
        // TODO add your handling code here:
        int vitri = tbl_HoaDon.getSelectedRow();
        if (vitri >= 0 && dsHD.size() > 0){
            hienThiTextBox(vitri);

            //Lấy HD được chọn
            HoaDon hoaDonChon = dsHD.get(vitri);

            //Lấy tenKH, SDT
            txt_TenSP.setText(sp.timKiemSanPham(hoaDonChon.getMaSP()).get(0).getTenSP());
            txt_TenKH.setText(kh.timKiemKhachHang(hoaDonChon.getMaKH()).get(0).getTenKH());
            txt_DienThoai.setText(kh.timKiemKhachHang(hoaDonChon.getMaKH()).get(0).getSDT());
            // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // txt_NgayLap.setText(formatter.format(hoaDonChon.getNgayLap()));
            txt_SoLuong.setText(String.valueOf(hoaDonChon.getSoLuong()));
            String maSP = hoaDonChon.getMaSP();

            //Hiển thị mã KM trên combobox tương ứng
            hienThiComboboxKhuyenMai(dsKhuyenMai, jComboBox1, maSP);
            String maKM = dsKhuyenMai.get(jComboBox1.getSelectedIndex()).getMaKM();
            System.out.println("Mã KM: " + maKM);
            //Select maKM đúng dòng
            for (int i = 0; i < jComboBox1.getItemCount(); i++){
                if (dsKhuyenMai.get(i).getMaKM().equals(maKM)){
                    jComboBox1.setSelectedIndex(i);
                }
            }
           
            float giaKhuyenMai = km.layGiaKM(maKM, maSP);
            System.out.println("Mã sản phẩm: " + maSP);
            System.out.println("Mã khuyến mãi: " + maKM);
            System.out.println("Giá khuyến mãi: " + giaKhuyenMai);

            //Tính thành tiền
            int soLuong = Integer.parseInt(txt_SoLuong.getText());
            float giaBan = Float.parseFloat(txt_GiaBan.getText());
            float thanhTien = (soLuong * giaBan) - giaKhuyenMai;
            DecimalFormat df =  new DecimalFormat("#");
            df.setMaximumFractionDigits(0);
            txt_ThanhTien.setText(df.format(thanhTien));

 
        }

    }//GEN-LAST:event_tbl_HoaDonMouseClicked

    private void txt_TienThoiLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TienThoiLaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TienThoiLaiActionPerformed

    private void txt_TienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TienKhachDuaActionPerformed
        // TODO add your handling code here:
        float tongTien = Float.parseFloat(txt_TongTienHD.getText().replace(",", ""));
        float tienKhachDua = Float.parseFloat(txt_TienKhachDua.getText().replace(",", ""));
        float tienThoiLai = tongTien - tienKhachDua;
        DecimalFormat df = new DecimalFormat("#,###,###");
        txt_TienThoiLai.setText(df.format(tienThoiLai));
    }//GEN-LAST:event_txt_TienKhachDuaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelSP;
    private component.Button btn_Luu;
    private component.Button btn_Reset;
    private component.Button btn_ThemHD;
    private javax.swing.JButton btn_ThemKH;
    private component.Button btn_TimKiem;
    private component.Button btn_XemCTHoaDon;
    private javax.swing.JComboBox<String> cbo_TenNV;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JLabel lb_MaDanhMuc;
    private javax.swing.JLabel lb_MaDanhMuc1;
    private javax.swing.JLabel lb_MaDanhMuc10;
    private javax.swing.JLabel lb_MaDanhMuc11;
    private javax.swing.JLabel lb_MaDanhMuc12;
    private javax.swing.JLabel lb_MaDanhMuc13;
    private javax.swing.JLabel lb_MaDanhMuc2;
    private javax.swing.JLabel lb_MaDanhMuc3;
    private javax.swing.JLabel lb_MaDanhMuc4;
    private javax.swing.JLabel lb_MaDanhMuc8;
    private javax.swing.JLabel lb_MaDanhMuc9;
    private swing.table.Table tbl_HoaDon;
    private javax.swing.JTextField txt_DienThoai;
    private javax.swing.JTextField txt_GiaBan;
    private javax.swing.JTextField txt_MaHD;
    private javax.swing.JTextField txt_MaSP;
    private javax.swing.JTextField txt_NgayLap;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JTextField txt_TenKH;
    private javax.swing.JTextField txt_TenSP;
    private javax.swing.JTextField txt_ThanhTien;
    private javax.swing.JTextField txt_TienKhachDua;
    private javax.swing.JTextField txt_TienThoiLai;
    private javax.swing.JTextPane txt_TimKiem;
    private javax.swing.JLabel txt_TongTienHD;
    // End of variables declaration//GEN-END:variables


}

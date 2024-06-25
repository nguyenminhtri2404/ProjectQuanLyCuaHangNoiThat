/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import Class.DSHoaDon;
import Class.DSNhanVien;
import Class.HoaDon;
import Class.KhachHang;
import Class.NhanVien;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Triss
 */
public class Form_NhapNhanVien extends javax.swing.JPanel {

    /**
     * Creates new form Form_NhapNhanVien
     */
    DSNhanVien nv;
    ArrayList<NhanVien> dsNV;
    int vitri = 0;
    int flag = 0;
    String duongdananh = "";
    private final int maNVMaxLength = 10; // Độ dài tối đa cho mã NCC
    private final int tenNVLength = 50; // Độ dài tối đa cho tên NCC
    public Form_NhapNhanVien() {
        initComponents();
        nv = new DSNhanVien();
        dsNV = nv.layDanhSachNhanVien();
        hienThiTextBox(vitri);
        hienThiTable();
        
    }
    
    private void hienThiTextBox(int vitri)
    {
        NhanVien n = dsNV.get(vitri);
        txt_MaNV.setText(n.MaNV);
        txt_TenNV.setText(n.TenNV);
        txt_ChucVu.setText(n.ChucVu);
        txt_SDT.setText(n.SDT);
        txt_Email.setText(n.Email);
        txt_TenTaiKhoan.setText(n.TenTK);
        txt_MatKhau.setText(n.MatKhau);
        
        // Hiển thị hình ảnh
    if (n.getHinhAnh() != null && !n.getHinhAnh().isEmpty()) {
        try {
            // Tải hình ảnh từ đường dẫn
            ImageIcon originalIcon = new ImageIcon(n.getHinhAnh());
            Image originalImage = originalIcon.getImage();

            // Thay đổi kích thước hình ảnh
            int labelWidth = lb_hinhanh.getWidth();
            int labelHeight = lb_hinhanh.getHeight();
            Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            // Tạo ImageIcon từ hình ảnh đã thay đổi kích thước
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Đặt ImageIcon vào JLabel
            lb_hinhanh.setIcon(scaledIcon);
        } catch (Exception e) {
            e.printStackTrace();
            lb_hinhanh.setIcon(null); // Nếu có lỗi khi tải ảnh, đặt lại label
        }
    } else {
        lb_hinhanh.setIcon(null); // Nếu không có hình ảnh, đặt lại label
    }
    }
    
    private void hienThiTable()
    {
        String []Header = {"Mã Nhân Viên","Tên Nhân Viên","Chức Vụ","SDT","Email","Tài Khoản"};
        DefaultTableModel model = new DefaultTableModel(Header,0);
        for(NhanVien n: dsNV)
        {   
            Object [] chnt = {n.MaNV,n.TenNV,n.ChucVu,n.SDT,n.Email,n.TenTK};
            model.addRow(chnt);
        }
        
        tbl_NhanVien.setModel(model);
        tbl_NhanVien.setRowSelectionInterval(vitri, vitri);
                
    }
    
    private void RangBuocTxt() {
        txt_TenNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String tenNV = txt_TenNV.getText().trim();
                if (tenNV.isEmpty() && tenNV.length() <= tenNVLength) {
                    JOptionPane.showMessageDialog(null, "Tên không được để trống và không vượt quá " + tenNVLength + " ký tự.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    txt_TenNV.setText("");
                    txt_TenNV.requestFocusInWindow();
                }

            }
        });

        txt_SDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    evt.consume();
                }
                if (txt_SDT.getText().length() >= 10 || (txt_SDT.getText().isEmpty() && c != '0')) {
                    evt.consume();
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số 0 đầu tiên và bạn chỉ nhập được 10 số thôi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        txt_Email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(txt_Email.getText());
                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(null, "Email không hợp lệ. Vui lòng nhập lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    txt_Email.requestFocusInWindow();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_MaDanhMuc = new javax.swing.JLabel();
        lb_TenDanhMuc = new javax.swing.JLabel();
        lb_GhiChu = new javax.swing.JLabel();
        lb_TenDanhMuc1 = new javax.swing.JLabel();
        lb_GhiChu1 = new javax.swing.JLabel();
        jScrollPane23 = new javax.swing.JScrollPane();
        txt_MaNV = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane22 = new javax.swing.JScrollPane();
        txt_TenNV = new javax.swing.JTextPane();
        jScrollPane24 = new javax.swing.JScrollPane();
        txt_TenTaiKhoan = new javax.swing.JTextPane();
        jScrollPane25 = new javax.swing.JScrollPane();
        txt_ChucVu = new javax.swing.JTextPane();
        jScrollPane26 = new javax.swing.JScrollPane();
        txt_SDT = new javax.swing.JTextPane();
        btn_Them = new component.Button();
        btn_Sua = new component.Button();
        btn_Reset = new component.Button();
        btn_ChonAnh = new component.Button();
        jScrollPane21 = new javax.swing.JScrollPane();
        txt_TimKiem = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_NhanVien = new swing.table.Table();
        btn_TimKiem = new component.Button();
        lb_GhiChu2 = new javax.swing.JLabel();
        jScrollPane27 = new javax.swing.JScrollPane();
        txt_Email = new javax.swing.JTextPane();
        btn_Xoa = new component.Button();
        lb_GhiChu3 = new javax.swing.JLabel();
        jScrollPane28 = new javax.swing.JScrollPane();
        txt_MatKhau = new javax.swing.JTextPane();
        btn_Luu = new component.Button();
        lb_hinhanh = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        lb_MaDanhMuc.setBackground(new java.awt.Color(0, 0, 255));
        lb_MaDanhMuc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_MaDanhMuc.setForeground(new java.awt.Color(255, 0, 51));
        lb_MaDanhMuc.setText("Mã nhân viên:");

        lb_TenDanhMuc.setBackground(new java.awt.Color(0, 0, 255));
        lb_TenDanhMuc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_TenDanhMuc.setForeground(new java.awt.Color(255, 0, 51));
        lb_TenDanhMuc.setText("Tên nhân viên:");

        lb_GhiChu.setBackground(new java.awt.Color(0, 0, 255));
        lb_GhiChu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_GhiChu.setForeground(new java.awt.Color(255, 0, 51));
        lb_GhiChu.setText("Tên tài khoản:");

        lb_TenDanhMuc1.setBackground(new java.awt.Color(0, 0, 255));
        lb_TenDanhMuc1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_TenDanhMuc1.setForeground(new java.awt.Color(255, 0, 51));
        lb_TenDanhMuc1.setText("Chức vụ:");

        lb_GhiChu1.setBackground(new java.awt.Color(0, 0, 255));
        lb_GhiChu1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_GhiChu1.setForeground(new java.awt.Color(255, 0, 51));
        lb_GhiChu1.setText("Số điện thoại: ");

        txt_MaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane23.setViewportView(txt_MaNV);

        jLabel2.setBackground(new java.awt.Color(0, 0, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("THÔNG TIN NHÂN VIÊN");

        txt_TenNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane22.setViewportView(txt_TenNV);

        txt_TenTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane24.setViewportView(txt_TenTaiKhoan);

        txt_ChucVu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane25.setViewportView(txt_ChucVu);

        txt_SDT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane26.setViewportView(txt_SDT);

        btn_Them.setBackground(new java.awt.Color(238, 230, 255));
        btn_Them.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_add.png"))); // NOI18N
        btn_Them.setText("Thêm");
        btn_Them.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_Sua.setBackground(new java.awt.Color(238, 230, 255));
        btn_Sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_edit.png"))); // NOI18N
        btn_Sua.setText("Sửa");
        btn_Sua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
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

        btn_ChonAnh.setBackground(new java.awt.Color(238, 230, 255));
        btn_ChonAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_select.png"))); // NOI18N
        btn_ChonAnh.setText("Chọn");
        btn_ChonAnh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_ChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChonAnhActionPerformed(evt);
            }
        });

        txt_TimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane21.setViewportView(txt_TimKiem);

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã NV", "Tên NV", "Tên tài khoản", "Chức vụ", "SĐT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_NhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbl_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_NhanVien);

        btn_TimKiem.setBackground(new java.awt.Color(238, 230, 255));
        btn_TimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_search.png"))); // NOI18N
        btn_TimKiem.setText("Tìm kiếm");
        btn_TimKiem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TimKiemActionPerformed(evt);
            }
        });

        lb_GhiChu2.setBackground(new java.awt.Color(0, 0, 255));
        lb_GhiChu2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_GhiChu2.setForeground(new java.awt.Color(255, 0, 51));
        lb_GhiChu2.setText("Email:");

        txt_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane27.setViewportView(txt_Email);

        btn_Xoa.setBackground(new java.awt.Color(238, 230, 255));
        btn_Xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_delete.png"))); // NOI18N
        btn_Xoa.setText("Xóa");
        btn_Xoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaActionPerformed(evt);
            }
        });

        lb_GhiChu3.setBackground(new java.awt.Color(0, 0, 255));
        lb_GhiChu3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_GhiChu3.setForeground(new java.awt.Color(255, 0, 51));
        lb_GhiChu3.setText("Mật khẩu:");

        txt_MatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane28.setViewportView(txt_MatKhau);

        btn_Luu.setBackground(new java.awt.Color(238, 230, 255));
        btn_Luu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icon_save.png"))); // NOI18N
        btn_Luu.setText("Lưu");
        btn_Luu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LuuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(486, 486, 486)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(333, 333, 333))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_hinhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btn_ChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lb_TenDanhMuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_MaDanhMuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lb_GhiChu)
                            .addComponent(lb_GhiChu1)
                            .addComponent(lb_GhiChu2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_GhiChu3)
                                .addGap(99, 99, 99)
                                .addComponent(jScrollPane28))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lb_TenDanhMuc1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane25, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane22, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane24, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane26)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jScrollPane27)))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Luu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(150, 150, 150))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lb_hinhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btn_ChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lb_MaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lb_TenDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_GhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_TenDanhMuc1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(btn_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(btn_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(btn_Luu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_GhiChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_GhiChu2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_GhiChu3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane28, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane21)
                    .addComponent(btn_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        // TODO add your handling code here:
        flag = 1;
//        xoaTextBox();
//        anHienTextbox(true);
//        anHienButton(false);

        txt_TenNV.setText("");
        txt_ChucVu.setText("");
        txt_Email.setText("");
        txt_TenTaiKhoan.setText("");
        txt_SDT.setText("");
        txt_MatKhau.setText("");
       txt_MaNV.setEnabled(false);
       txt_MaNV.setText(nv.SinhMaNhanVien());
        //initializeMaKHField();
        RangBuocTxt();
    }//GEN-LAST:event_btn_ThemActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
        // TODO add your handling code here:
        flag = 2;

        
        btn_Luu.setEnabled(true);
        btn_Them.setEnabled(false);
        //btn_XuatHD.setEnabled(false);
        btn_Sua.setEnabled(false);
        btn_Xoa.setEnabled(false);
        RangBuocTxt();
        

    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
        // TODO add your handling code here:
         txt_TenNV.setText("");
        txt_ChucVu.setText("");
        txt_Email.setText("");
        txt_TenTaiKhoan.setText("");
        txt_SDT.setText("");
        txt_MatKhau.setText("");
    }//GEN-LAST:event_btn_ResetActionPerformed
    
    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg= img.getScaledInstance(200, 200,Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    private void btn_ChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChonAnhActionPerformed
        // TODO add your handling code here:
       try {
        // Lấy đường dẫn thư mục gốc của dự án
        String projectDir = System.getProperty("user.dir");

        // Kết hợp với phần tương đối của đường dẫn của thư mục images.nhanvien
        String relativePath = "src\\images.nhanvien";

        // Tạo đối tượng File từ đường dẫn tương đối
        File directory = new File(projectDir, relativePath);

        JFileChooser f = new JFileChooser(directory.getPath());
        f.setDialogTitle("Mở file");
        f.showOpenDialog(null);
        File ftenanh = f.getSelectedFile();
        duongdananh = "src\\images.nhanvien\\" + ftenanh.getName(); // Lấy đường dẫn tương đối của file

        lb_hinhanh.setIcon(ResizeImage(duongdananh));
    } catch (Exception ex) {
        System.out.println("Chưa chọn ảnh");
        System.out.println(duongdananh);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_btn_ChonAnhActionPerformed

    private void btn_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TimKiemActionPerformed
        // TODO add your handling code here:
        if (txt_TimKiem.getText()!=null)
        {
            dsNV = nv.timKiemNhanVien(txt_TimKiem.getText());
            vitri = 0;
            hienThiTable();
            hienThiTextBox(vitri);
        }
    }//GEN-LAST:event_btn_TimKiemActionPerformed

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaActionPerformed
        // TODO add your handling code here:
        flag = 3;
        btn_Luu.setEnabled(true);
        btn_Them.setEnabled(false);
        btn_Sua.setEnabled(false);
        btn_Xoa.setEnabled(false);
    }//GEN-LAST:event_btn_XoaActionPerformed

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        // TODO add your handling code here:
        vitri = tbl_NhanVien.getSelectedRow();
        hienThiTextBox(vitri);
    }//GEN-LAST:event_tbl_NhanVienMouseClicked
    private NhanVien convertTextbox_to_Obj()
            {   
                NhanVien nhanvien = new NhanVien(txt_MaNV.getText(),txt_TenNV.getText(),txt_TenTaiKhoan.getText(),txt_ChucVu.getText(),txt_SDT.getText(),txt_Email.getText(),txt_MatKhau.getText(),duongdananh);
                return nhanvien;
            }
    private void btn_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LuuActionPerformed
        // TODO add your handling code here:
        NhanVien nvien = convertTextbox_to_Obj();
        if (flag ==1){
            nv.themNhanVien(nvien);
        }
        else if (flag == 2)
        {
            nv.capNhatNhanVien(nvien);
        
        
        }
        else if (flag == 3)
        {
            int kq= JOptionPane.showConfirmDialog(this,"Bạn có muốn xóa không?","Thông báo",JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_CANCEL_OPTION);
            if(kq==JOptionPane.YES_OPTION) {
               nv.xoaNhanVien(nvien);
            }
        }

        
        flag = 0;
        nv = new DSNhanVien();
        dsNV = nv.layDanhSachNhanVien();
        hienThiTable();
        
        btn_Luu.setEnabled(false);
        btn_Them.setEnabled(true);
        btn_Sua.setEnabled(true);
        btn_Xoa.setEnabled(true);
        
    }//GEN-LAST:event_btn_LuuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Button btn_ChonAnh;
    private component.Button btn_Luu;
    private component.Button btn_Reset;
    private component.Button btn_Sua;
    private component.Button btn_Them;
    private component.Button btn_TimKiem;
    private component.Button btn_Xoa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JLabel lb_GhiChu;
    private javax.swing.JLabel lb_GhiChu1;
    private javax.swing.JLabel lb_GhiChu2;
    private javax.swing.JLabel lb_GhiChu3;
    private javax.swing.JLabel lb_MaDanhMuc;
    private javax.swing.JLabel lb_TenDanhMuc;
    private javax.swing.JLabel lb_TenDanhMuc1;
    private javax.swing.JLabel lb_hinhanh;
    private swing.table.Table tbl_NhanVien;
    private javax.swing.JTextPane txt_ChucVu;
    private javax.swing.JTextPane txt_Email;
    private javax.swing.JTextPane txt_MaNV;
    private javax.swing.JTextPane txt_MatKhau;
    private javax.swing.JTextPane txt_SDT;
    private javax.swing.JTextPane txt_TenNV;
    private javax.swing.JTextPane txt_TenTaiKhoan;
    private javax.swing.JTextPane txt_TimKiem;
    // End of variables declaration//GEN-END:variables

}

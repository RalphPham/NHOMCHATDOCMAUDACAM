/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package DUNG;

import LONG.DonHangChiTiet;
import LONG.DonHangChiTietDAO;
import PHU.SanPhamDAO;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QuanLyDonHang extends javax.swing.JFrame {

    DefaultTableModel tableModel;
    DefaultTableModel tableModelCT;

    /**
     * Creates new form QuanLyDonHang
     */
    public QuanLyDonHang() {
        initComponents();
        init();
        loadData();
        loadTenNV();
        loadSDT();
        initCT();
        fillCT();
        loadMaDH();
        loadTenSP();
        addDocumentListeners();
        setLocationRelativeTo(null);
        txtSoLuong.setText("0");
        txtTongTien.setText("0");
    }

    // Bảng Đơn hàng
    public void init() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã DH", "Tên NV", "SDT", "Ngày tạo", "Phương thức TT", "Tổng số lượng", "Tổng tiền"});
        tblDonHang.setModel(tableModel);
    }

    public void loadData() {
        tableModel.setRowCount(0);
        DonHangDAO dao = new DonHangDAO();
        List<DonHang> list = dao.findAll();
        if (list == null || list.isEmpty()) {
            txtSoLuong.setText("0");
            txtTongTien.setText("0");
        } else {
            for (DonHang donHang : list) {
                tableModel.addRow(new Object[]{
                    donHang.getMaDH(),
                    donHang.getTenNV(),
                    donHang.getSDT(),
                    donHang.getNgayTao(),
                    donHang.getPhuongThucThanhToan(),
                    donHang.getTongSoLuong(),
                    donHang.getTongTien()
                });
            }
        }
    }

    public void loadTenNV() {
        DonHangDAO dao = new DonHangDAO();
        List<String> listTenNV = dao.getMaNV();
        cboMaNV.removeAllItems();
        for (String maNV : listTenNV) {
            cboMaNV.addItem(maNV);
        }
    }

    public void loadSDT() {
        DonHangDAO dao = new DonHangDAO();
        List<String> listTenKH = dao.getMaKH();
        cboMaKH.removeAllItems();
        for (String maKH : listTenKH) {
            cboMaKH.addItem(maKH);
        }
    }

    // Bảng Đơn hàng chi tiết
    public void initCT() {
        tableModelCT = new DefaultTableModel();
        tableModelCT.setColumnIdentifiers(new String[]{"MaDonHangChiTiet", "MaDH", "TenSP", "So luong", "Don Gia", "Thanh Tien"});
        tblbang.setModel(tableModelCT);
    }

    public void fillCT() {
        tableModelCT.setNumRows(0);
        DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
        List<DonHangChiTiet> dhctlist = dhctdao.FindAll();
        for (DonHangChiTiet dhct : dhctlist) {
            tableModelCT.addRow(new Object[]{dhct.getMaDonHangChiTiet(), dhct.getMaDH(), dhct.getTenSP(), dhct.getSoLuong(), dhct.getDonGia(), dhct.getThanhTien()});
        }
    }

    public void loadTenSP() {
        DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
        List<String> listMaSP = dhctdao.MaSP();
        cboMaSP.removeAllItems();
        for (String mansp : listMaSP) {
            cboMaSP.addItem(mansp);
        }
    }

    public void loadMaDH() {
        DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
        List<String> listMaNV = dhctdao.MaDH();
        cboMaDH.removeAllItems();
        for (String mancc : listMaNV) {
            cboMaDH.addItem(mancc);
        }
    }

    private void updateThanhTien() {
        try {
            String giaNhapStr = txtdongia.getText().trim();
            int soLuong = (Integer) scrsoluong.getValue();
            if (!giaNhapStr.isEmpty() && soLuong > 0) {
                BigDecimal giaNhap = new BigDecimal(giaNhapStr);
                BigDecimal tongChiPhi = giaNhap.multiply(new BigDecimal(soLuong));
                DecimalFormat df = new DecimalFormat("#,###.##");
                txtthanhtien.setText(df.format(tongChiPhi));
            } else {
                txtthanhtien.setText("");
            }
        } catch (NumberFormatException e) {
            txtthanhtien.setText("");
        }
    }

    private void addDocumentListeners() {
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateThanhTien();
            }
        };

        txtdongia.getDocument().addDocumentListener(documentListener);
        // Thêm ChangeListener cho scrsoluong
        scrsoluong.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                updateThanhTien();
            }
        });
    }

    public void UpdateTongDonHang(String MaDH) {
        DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
        List<DonHangChiTiet> dhctlist = dhctdao.FindAll();
        int tongsoluong = 0;
        BigDecimal tongtien = BigDecimal.ZERO;
        for (DonHangChiTiet dhct : dhctlist) {
            if (dhct.getMaDH().equals(MaDH)) {
                tongsoluong += dhct.getSoLuong();
                tongtien = tongtien.add(dhct.getThanhTien());
            }
        }
        DonHangDAO dhdao = new DonHangDAO();
        DonHang dh = dhdao.findId(MaDH);
        if (dh != null) {
            dh.setTongSoLuong(tongsoluong);
            dh.setTongTien(tongtien);
            dhdao.update(dh);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy đơn hàng " + MaDH, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dh.setTongSoLuong(tongsoluong);
        dh.setTongTien(tongtien);
        dhdao.update(dh);
        if (txtMaDH.getText().equals(MaDH)) {
            txtSoLuong.setText(String.valueOf(tongsoluong));
            txtTongTien.setText(String.valueOf(tongtien));
        }

        loadData();
        fillCT();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cboMaNV = new javax.swing.JComboBox<>();
        btnLamMoi = new javax.swing.JButton();
        cboMaKH = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        txtNgayTao = new javax.swing.JTextField();
        rdoTienMat = new javax.swing.JRadioButton();
        rdoQR = new javax.swing.JRadioButton();
        txtSoLuong = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDonHang = new javax.swing.JTable();
        txtTongTien = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaDH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnsua = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnxoa = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnclear = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btntimkiem = new javax.swing.JButton();
        txtmadhct = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboMaDH = new javax.swing.JComboBox<>();
        txttimkiem = new javax.swing.JTextField();
        cboMaSP = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbang = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtdongia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtthanhtien = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        scrsoluong = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cboMaNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNVActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        cboMaKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setText("Tìm kiếm");

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnTimKiem)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addContainerGap())
        );

        buttonGroup1.add(rdoTienMat);
        rdoTienMat.setText("Tiền mặt");

        buttonGroup1.add(rdoQR);
        rdoQR.setText("QR code");

        txtSoLuong.setEditable(false);

        tblDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDonHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDonHang);

        txtTongTien.setEditable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnSua))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnXoa)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel1.setText("Mã đơn hàng");

        jLabel2.setText("Tên nhân viên");

        jLabel3.setText("SĐT khách hàng");

        jLabel4.setText("Ngày tạo");

        jLabel5.setText("Phương thức thanh toán");

        jLabel6.setText("Số lượng sản phẩm mua");

        jLabel7.setText("Tổng tiền");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 255));
        jLabel8.setText("QUẢN LÝ ĐƠN HÀNG");

        jButton2.setText("Thanh Toán");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(rdoTienMat)
                                .addGap(18, 18, 18)
                                .addComponent(rdoQR))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoTienMat)
                                    .addComponent(rdoQR))))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLamMoi)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(72, 72, 72)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane4.addTab("Đơn hàng", jPanel1);

        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        jLabel10.setText("So Luong");

        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        jLabel11.setText("Don Gia");

        btnclear.setText("Clear");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jLabel12.setText("Thanh Tien");

        btntimkiem.setText("Tìm kiếm");
        btntimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimkiemActionPerformed(evt);
            }
        });

        jLabel13.setText("Tim kiem");

        cboMaDH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboMaSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaSPActionPerformed(evt);
            }
        });

        tblbang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblbang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblbang);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 255));
        jLabel14.setText("ĐƠN HÀNG CHI TIẾT");

        txtdongia.setEditable(false);

        jLabel15.setText("MaDHCT");

        txtthanhtien.setEditable(false);
        txtthanhtien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtthanhtienActionPerformed(evt);
            }
        });

        jLabel16.setText("MaDH");

        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jLabel17.setText("TenSP");

        jButton1.setText("TinhTong");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboMaDH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtmadhct)
                            .addComponent(cboMaSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtthanhtien)
                            .addComponent(txtdongia)
                            .addComponent(txttimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(scrsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(163, 163, 163)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(btnthem)
                    .addComponent(btnsua)
                    .addComponent(btnxoa)
                    .addComponent(btnclear)
                    .addComponent(btntimkiem))
                .addGap(71, 71, 71))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtmadhct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnthem))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cboMaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsua))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cboMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btnclear)
                    .addComponent(scrsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntimkiem)
                    .addComponent(jLabel13)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Đơn hàng chi tiết", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNVActionPerformed
        // TODO add your handling code here:
        String maNV = (String) cboMaNV.getSelectedItem();
        if (maNV != null && !maNV.isEmpty()) {
            DonHangDAO dao = new DonHangDAO();

        }
    }//GEN-LAST:event_cboMaNVActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtMaDH.setText("");
        txtNgayTao.setText("");
        txtSoLuong.setText("0");
        txtTongTien.setText("0");
        txtTimKiem.setText("");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String maDH = txtTimKiem.getText().trim();

        if (maDH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đơn hàng cần tìm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadData();
            return;
        }

        DonHangDAO dao = new DonHangDAO();
        DonHang dh = dao.findId(maDH);

        //Hiển thị lên bảng
        if (dh != null) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{dh.getMaDH(),
                dh.getTenNV(),
                dh.getSDT(),
                dh.getNgayTao(),
                dh.getPhuongThucThanhToan(),
                dh.getTongSoLuong(),
                dh.getTongTien()});

            //Hiển thị thông tin lên textField
            txtMaDH.setText(dh.getMaDH());
            cboMaNV.setSelectedItem(dh.getTenNV());
            cboMaKH.setSelectedItem(dh.getSDT());
            txtNgayTao.setText(String.valueOf(dh.getNgayTao()));
            if (dh.getPhuongThucThanhToan().equals("Tiền mặt")) {
                rdoTienMat.setSelected(true);
            } else {
                rdoQR.setSelected(true);
            }
            txtSoLuong.setText(String.valueOf(dh.getTongSoLuong()));
            txtTongTien.setText(String.valueOf(dh.getTongTien()));
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã đơn hàng");
            return;
        }
        JOptionPane.showMessageDialog(this, "Tìm kiếm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDonHangMouseClicked
        // TODO add your handling code here:
        try {
            int row = tblDonHang.getSelectedRow();
            if (row >= 0) {
                String maDH = (String) tblDonHang.getValueAt(row, 0);
                DonHangDAO dao = new DonHangDAO();
                DonHang dh = dao.findId(maDH);
                if (dh != null) {
                    txtMaDH.setText(dh.getMaDH());
                    cboMaNV.setSelectedItem(dh.getTenNV());
                    cboMaKH.setSelectedItem(dh.getSDT());
                    txtNgayTao.setText(String.valueOf(dh.getNgayTao()));
                    if (dh.getPhuongThucThanhToan().equals("Tiền mặt")) {
                        rdoTienMat.setSelected(true);
                    } else {
                        rdoQR.setSelected(true);
                    }
                    txtSoLuong.setText(String.valueOf(dh.getTongSoLuong()));
                    txtTongTien.setText(String.valueOf(dh.getTongTien()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tblDonHangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (txtMaDH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đơn hàng");
            return;
        }
        DonHangDAO dao = new DonHangDAO();
        if (dao.checkMaDH(txtMaDH.getText())) {
            JOptionPane.showMessageDialog(this, "Mã đơn hàng đã tồn tại!");
            return;
        }
        if (txtNgayTao.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày tạo đơn hàng");
            return;
        }
        if (!rdoTienMat.isSelected() && !rdoQR.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán");
            return;
        }
        try {
            DonHang dh = new DonHang();
            dh.setMaDH(txtMaDH.getText());
            dh.setTenNV((String) cboMaNV.getSelectedItem());
            dh.setSDT((String) cboMaKH.getSelectedItem());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date utilDate = sdf.parse(txtNgayTao.getText().trim());
                dh.setNgayTao(new java.sql.Date(utilDate.getTime()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ngày tạo không hợp lệ. Định dạng đúng: yyyy-MM-dd.");
                return;
            }
            if (rdoTienMat.isSelected()) {
                dh.setPhuongThucThanhToan("Tiền mặt");
            } else {
                dh.setPhuongThucThanhToan("QR code");
            }
            dh.setTongSoLuong(0);
            dh.setTongTien(BigDecimal.ZERO);

            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm đơn hàng không?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.YES_OPTION) {
                if (dao.insert(dh)) {
                    JOptionPane.showMessageDialog(this, "Thêm đơn hàng thành công");
                    loadData(); // Làm mới bảng tblDonHang
                    UpdateTongDonHang(dh.getMaDH()); // Cập nhật tổng số lượng và tổng tiền
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm đơn hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        String maDH = txtMaDH.getText().trim();
        if (maDH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đơn hàng");
            return;
        }
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá " + maDH + " ? ", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (chon == JOptionPane.YES_OPTION) {
            DonHang dh = new DonHang();
            dh.setMaDH(maDH);

            DonHangDAO dao = new DonHangDAO();

            if (dao.delete(dh)) {
                JOptionPane.showMessageDialog(this, "Xoá đơn hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Mã đơn hàng không tồn tại, xoá thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (txtMaDH.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đơn hàng");
            return;
        }
        DonHangDAO dao = new DonHangDAO();
        if (!dao.checkMaDH(txtMaDH.getText())) {
            JOptionPane.showMessageDialog(this, "Mã đơn hàng không tồn tại!");
            return;
        }
        if (txtNgayTao.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày tạo đơn hàng");
            return;
        }
        if (!rdoTienMat.isSelected() && !rdoQR.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán");
            return;
        }
        try {
            DonHang dh = new DonHang();
            dh.setMaDH(txtMaDH.getText());
            dh.setTenNV((String) cboMaNV.getSelectedItem());
            dh.setSDT((String) cboMaKH.getSelectedItem());

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date utilDate = sdf.parse(txtNgayTao.getText().trim());
                dh.setNgayTao(new java.sql.Date(utilDate.getTime()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ngày tạo không hợp lệ. Định dạng đúng: yyyy-MM-dd.");
                return;
            }
            if (rdoTienMat.isSelected()) {
                dh.setPhuongThucThanhToan("Tiền mặt");
            } else {
                dh.setPhuongThucThanhToan("QR code");
            }
            dh.setTongSoLuong(0);
            dh.setTongTien(BigDecimal.ZERO);

            int chon = JOptionPane.showConfirmDialog(this, "Bạn muốn cập nhật thông tin đơn hàng không ?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.YES_OPTION) {
                if (dao.update(dh)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin đơn hàng thành công");
                    UpdateTongDonHang(dh.getMaDH()); // Cập nhật tổng số lượng và tổng tiền
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin đơn hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        // TODO add your handling code here:
        if (txtmadhct.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mời nhập mã đơn hàng chi tiết");
            return;
        }
        int soLuong = (Integer) scrsoluong.getValue();
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số lớn hơn 0");
            return;
        }
        try {
            DonHangChiTiet dhct = new DonHangChiTiet();
            dhct.setMaDonHangChiTiet(txtmadhct.getText());
            dhct.setMaDH((String) cboMaDH.getSelectedItem());
            dhct.setTenSP((String) cboMaSP.getSelectedItem());
            dhct.setSoLuong(soLuong);
            dhct.setDonGia(new BigDecimal(txtdongia.getText()));
            String TongThanhTien = txtthanhtien.getText().replace(",", "");
            dhct.setThanhTien(new BigDecimal(TongThanhTien));
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa sản phẩm không");
            if (chon == JOptionPane.YES_OPTION) {
                DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
                if (dhctdao.update(dhct)) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                    fillCT();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnsuaActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        // TODO add your handling code here:
        if (txtmadhct.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mời nhập mã đơn hàng chi tiết");
            return;
        }
        try {
            DonHangChiTiet dhct = new DonHangChiTiet();
            dhct.setMaDonHangChiTiet(txtmadhct.getText());
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm không");
            if (chon == JOptionPane.YES_OPTION) {
                DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
                if (dhctdao.delete(dhct)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    fillCT();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        txtmadhct.setText("");
        scrsoluong.setValue(0);
        txtdongia.setText("");
        txtthanhtien.setText("");
        txttimkiem.setText("");
    }//GEN-LAST:event_btnclearActionPerformed

    private void btntimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimkiemActionPerformed
        // TODO add your handling code here:
        String maDHCT = txttimkiem.getText().trim();
        if (maDHCT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã để tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            fillCT();
            return;
        }
        DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
        DonHangChiTiet dhct = dhctdao.FindById(maDHCT);
        if (dhct != null) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{
                dhct.getMaDonHangChiTiet(),
                dhct.getMaDH(),
                dhct.getTenSP(),
                dhct.getSoLuong(),
                dhct.getDonGia(),
                dhct.getThanhTien()
            });
            txtmadhct.setText(dhct.getMaDonHangChiTiet());
            cboMaDH.setSelectedItem(dhct.getMaDH());
            cboMaSP.setSelectedItem(dhct.getTenSP());
            scrsoluong.setValue(String.valueOf(dhct.getSoLuong()));
            txtdongia.setText(String.valueOf(dhct.getDonGia()));
            txtthanhtien.setText(String.valueOf(dhct.getThanhTien()));
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy đơn hàng với mã: " + maDHCT, "Thông báo", JOptionPane.ERROR_MESSAGE);
            fillCT();
        }
    }//GEN-LAST:event_btntimkiemActionPerformed

    private void cboMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaSPActionPerformed
        // TODO add your handling code here:
        String TenSP = (String) cboMaSP.getSelectedItem();
        if (TenSP != null && !TenSP.isEmpty()) {
            DonHangChiTietDAO dhctDao = new DonHangChiTietDAO();
            SanPhamDAO spDao = new SanPhamDAO();

            float GiaBan = dhctDao.getGiaBanBySP(TenSP);
            txtdongia.setText(String.valueOf(GiaBan));

            int soLuongTonKho = spDao.getSoLuongByMaSP(TenSP);
            scrsoluong.setValue(soLuongTonKho);
            scrsoluong.setModel(new SpinnerNumberModel(0, 0, soLuongTonKho, 1));
            updateThanhTien();
        } else {
            txtdongia.setText("");
            scrsoluong.setValue(0);
            txtthanhtien.setText("");
        }
    }//GEN-LAST:event_cboMaSPActionPerformed

    private void tblbangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbangMouseClicked
        // TODO add your handling code here:
        int row = tblbang.getSelectedRow();
        if (row >= 0) {
            String MaDHCT = (String) tblbang.getValueAt(row, 0);
            DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
            DonHangChiTiet dhct = dhctdao.FindById(MaDHCT);
            txtmadhct.setText(dhct.getMaDonHangChiTiet());
            cboMaDH.setSelectedItem(dhct.getMaDH());
            cboMaSP.setSelectedItem(dhct.getTenSP());
            scrsoluong.setValue(dhct.getSoLuong());
            txtdongia.setText(String.valueOf(dhct.getDonGia()));
            txtthanhtien.setText(String.valueOf(dhct.getThanhTien()));
        }
    }//GEN-LAST:event_tblbangMouseClicked

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if (txtmadhct.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mời nhập mã đơn hàng chi tiết");
            return;
        }
        int soLuong = (Integer) scrsoluong.getValue();
        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số lớn hơn 0");
            return;
        }
        try {
            DonHangChiTiet dhct = new DonHangChiTiet();
            dhct.setMaDonHangChiTiet(txtmadhct.getText());
            dhct.setMaDH((String) cboMaDH.getSelectedItem());
            dhct.setTenSP((String) cboMaSP.getSelectedItem());
            dhct.setSoLuong(soLuong);
            dhct.setDonGia(new BigDecimal(txtdongia.getText()));
            String TongThanhTien = txtthanhtien.getText().replace(",", "");
            dhct.setThanhTien(new BigDecimal(TongThanhTien));
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm sản phẩm không");
            if (chon == JOptionPane.YES_OPTION) {
                DonHangChiTietDAO dhctdao = new DonHangChiTietDAO();
                if (dhctdao.insert(dhct)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    fillCT();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void txtthanhtienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtthanhtienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtthanhtienActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String maDH = (String) cboMaDH.getSelectedItem();
        if (maDH == null || maDH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã đơn hàng để tính tổng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        UpdateTongDonHang(maDH);
        JOptionPane.showMessageDialog(this, "Tính tổng thành công cho đơn hàng " + maDH, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyDonHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyDonHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btnxoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboMaDH;
    private javax.swing.JComboBox<String> cboMaKH;
    private javax.swing.JComboBox<String> cboMaNV;
    private javax.swing.JComboBox<String> cboMaSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JRadioButton rdoQR;
    private javax.swing.JRadioButton rdoTienMat;
    private javax.swing.JSpinner scrsoluong;
    private javax.swing.JTable tblDonHang;
    private javax.swing.JTable tblbang;
    private javax.swing.JTextField txtMaDH;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtdongia;
    private javax.swing.JTextField txtmadhct;
    private javax.swing.JTextField txtthanhtien;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}

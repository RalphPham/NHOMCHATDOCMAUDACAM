/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Model.KhachHang;
import DAO.KhachHangDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class KhachHangJPanel extends javax.swing.JPanel {

    DefaultTableModel tbModel;

    /**
     * Creates new form KhachHangJPanel
     */
    public KhachHangJPanel() {
        initComponents();
        init();
        loaddatatotable();
    }

    public void init() {
        tbModel = new DefaultTableModel();
        tbModel.setColumnIdentifiers(new String[]{"Mã KH", "Tên KH", "Số điện thoại", "Giới tính", "Địa chỉ"});
        tblBang.setModel(tbModel);
    }

    public void loaddatatotable() {
        tbModel.setRowCount(0);
        KhachHangDAO dao = new KhachHangDAO();
        List<KhachHang> list = dao.FindAll();
        for (KhachHang kh : list) {
            tbModel.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSDT(), kh.getGioiTinh(), kh.getDiaChi()});
        }
    }

    void LamMoi() {
        buttonGroup1.clearSelection();
        txtDiaChi.setText(null);
        txtMaKH.setText(null);
        txtSdt.setText(null);
        txtTenKH.setText(null);
        txtTimKiem.setText(null);
        loaddatatotable();
    }

    void Them() {
        // MaKH, TenKH, SDT, GioiTinh, DiaChi
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        String SDT = txtSdt.getText().trim();
        String diaChi = txtDiaChi.getText().trim();

        // Kiểm tra các trường không được để trống
        if (maKH.isEmpty() || tenKH.isEmpty() || SDT.isEmpty() || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Kiểm tra số điện thoại (chỉ chứa số, đúng 10 chữ số, bắt đầu từ số 0)
        if (!SDT.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 số và bắt đầu từ số 0!");
            return;
        }

        // Kiểm tra giới tính
        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return;
        }

        // Lấy giá trị giới tính từ radio button
        String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";

        // Tạo đối tượng Khách hàng
        KhachHang kh = new KhachHang();
        kh.setMaKH(maKH);
        kh.setTenKH(tenKH);
        kh.setSDT(SDT);
        kh.setGioiTinh(gioiTinh);
        kh.setDiaChi(diaChi);

        // Gọi DAO để thêm khách hàng
        KhachHangDAO khDAO = new KhachHangDAO();
        if (khDAO.insert(kh)) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thành công");
            loaddatatotable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại");
        }
    }

    void Xoa() {
        String MaKH = txtMaKH.getText().trim();
        if (MaKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã Khách hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa Khách hàng với mã: " + MaKH + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            KhachHang kh = new KhachHang();
            kh.setMaKH(MaKH);

            KhachHangDAO deleteKH = new KhachHangDAO();
            boolean result = deleteKH.delete(kh);

            if (result) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Mã Nhân viên không tồn tại, xóa thắt bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        loaddatatotable();
    }

    public void Sua() {
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        String SDT = txtSdt.getText().trim();
        String DiaChi = txtDiaChi.getText().trim();

        // Kiểm tra các trường không được để trống
        if (maKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Kiểm tra định dạng email (nếu có nhập)
        // Kiểm tra số điện thoại (chỉ chứa số và có đúng 10 chữ số)
        if (!SDT.matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 số và bắt đầu từ số 0!");
            return;
        }

        // Lấy giá trị giới tính từ radio button
        String gioiTinh = rdoNam.isSelected() ? "Nam" : "Nữ";

        // Lấy giá trị chức vụ từ radio button
        // Tạo đối tượng Nhân viên
        KhachHang kh = new KhachHang();
        kh.setMaKH(maKH);
        kh.setTenKH(tenKH);
        kh.setSDT(SDT);
        kh.setGioiTinh(gioiTinh);
        kh.setDiaChi(DiaChi);

        // Gọi DAO để cập nhật nhân viên
        KhachHangDAO updateDal = new KhachHangDAO();
        boolean result = updateDal.update(kh);

        if (result) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loaddatatotable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        loaddatatotable();
    }

    public void TimKiemBangClicked() {
        try {
            int row = tblBang.getSelectedRow();
            if (row >= 0) {
                String MaKH = (String) tblBang.getValueAt(row, 0);
                KhachHangDAO KHDAL = new KhachHangDAO();
                KhachHang kh = KHDAL.findByID(MaKH);
                if (kh != null) {
                    txtMaKH.setText(kh.getMaKH());
                    txtTenKH.setText(kh.getTenKH());
                    txtSdt.setText(kh.getSDT());
                    txtDiaChi.setText(kh.getDiaChi());

                    // Xử lý radio button cho giới tính
                    if ("Nam".equals(kh.getGioiTinh())) {
                        rdoNam.setSelected(true);
                    } else if ("Nữ".equals(kh.getGioiTinh())) {
                        rdoNu.setSelected(true);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        loaddatatotable();
    }

    void TimKiem() {
//        String maKH = txtTimKiem.getText().trim();
//
//        // Kiểm tra nếu mã nhân viên để trống
//        if (maKH.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng để tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            loaddatatotable();
//            return;
//        }
//
//        KhachHangDAO NVDAL = new KhachHangDAO();
//        KhachHang kh = NVDAL.findByID(maKH);
//
//        // Kiểm tra nếu không tìm thấy nhân viên
//        
//        if (kh != null) {
//            tbModel.setRowCount(0);
//            tbModel.addRow(new Object[]{
//                kh.getMaKH(),
//                kh.getTenKH(),
//                kh.getSDT(),
//                kh.getGioiTinh(),
//                kh.getDiaChi()
//            });
//        // Hiển thị thông tin nhân viên
//        txtMaKH.setText(kh.getMaKH());
//        txtTenKH.setText(kh.getTenKH());
//        txtSdt.setText(kh.getSDT());
//        txtDiaChi.setText(kh.getDiaChi());
//
//        // Xử lý radio button giới tính
//        if ("Nam".equals(kh.getGioiTinh())) {
//            rdoNam.setSelected(true);
//        } else if ("Nữ".equals(kh.getGioiTinh())) {
//            rdoNu.setSelected(true);
//        } else {
//            rdoNam.setSelected(false);
//            rdoNu.setSelected(false);
//        }
//        }else{
//            JOptionPane.showMessageDialog(this, "Không tìm thấy mã khách hàng");
//            return;
//        }
//        // Thông báo tìm kiếm thành công
//        JOptionPane.showMessageDialog(this, "Tìm kiếm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        if (txtTimKiem.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập vào tìm kiếm và chọn combobox");
            return;
        }
        if (cboTimKiem.getSelectedItem().equals("Mã")) {
            tbModel.setRowCount(0);
            try {
                KhachHangDAO dao = new KhachHangDAO();
                List<KhachHang> list = dao.findByMa(txtTimKiem.getText());
                if (list.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng theo mã", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    for (KhachHang kh : list) {
                        tbModel.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSDT(), kh.getGioiTinh(), kh.getDiaChi()});
                    }
                    JOptionPane.showMessageDialog(this, "Tìm kiếm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    
                }
            } catch (Exception e) {
            }
        }

        if (txtTimKiem.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập vào tìm kiếm và chọn combobox");
            return;
        }
        if (cboTimKiem.getSelectedItem().equals("Tên")) {
            tbModel.setRowCount(0);
            try {
                KhachHangDAO dao = new KhachHangDAO();
                List<KhachHang> list = dao.findByTen(txtTimKiem.getText());
                if (list.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng theo tên", "Thông báo", JOptionPane.WARNING_MESSAGE);
                } else {
                    for (KhachHang kh : list) {
                        tbModel.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSDT(), kh.getGioiTinh(), kh.getDiaChi()});
                    }
                    JOptionPane.showMessageDialog(this, "Tìm kiếm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    
                }
            } catch (Exception e) {
            }
        }
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        cboTimKiem = new javax.swing.JComboBox<>();

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("QUẢN LÝ KHÁCH HÀNG");
        jPanel1.add(jLabel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(758, 536));

        jLabel2.setText("Tìm kiếm");

        jLabel3.setText("Mã khách hàng");

        jLabel4.setText("Tên khách hàng");

        jLabel5.setText("Số điện thoại");

        jLabel6.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel7.setText("Địa chỉ");

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        cboTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã", "Tên" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtSdt, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTenKH)
                                            .addComponent(txtMaKH)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cboTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(55, 55, 55)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNu)))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem)
                    .addComponent(cboTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu)
                    .addComponent(btnLamMoi))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(401, 401, 401)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(438, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        Them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        Sua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        LamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        TimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        // TODO add your handling code here:
        TimKiemBangClicked();
    }//GEN-LAST:event_tblBangMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboTimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblBang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

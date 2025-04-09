/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package KHOI;

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
        String maKH = txtTimKiem.getText().trim();

        // Kiểm tra nếu mã nhân viên để trống
        if (maKH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng để tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loaddatatotable();
            return;
        }

        KhachHangDAO NVDAL = new KhachHangDAO();
        KhachHang kh = NVDAL.findByID(maKH);

        // Kiểm tra nếu không tìm thấy nhân viên
        
        if (kh != null) {
            tbModel.setRowCount(0);
            tbModel.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getSDT(),
                kh.getGioiTinh(),
                kh.getDiaChi()
            });
        // Hiển thị thông tin nhân viên
        txtMaKH.setText(kh.getMaKH());
        txtTenKH.setText(kh.getTenKH());
        txtSdt.setText(kh.getSDT());
        txtDiaChi.setText(kh.getDiaChi());

        // Xử lý radio button giới tính
        if ("Nam".equals(kh.getGioiTinh())) {
            rdoNam.setSelected(true);
        } else if ("Nữ".equals(kh.getGioiTinh())) {
            rdoNu.setSelected(true);
        } else {
            rdoNam.setSelected(false);
            rdoNu.setSelected(false);
        }
        }else{
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã khách hàng");
            return;
        }
        // Thông báo tìm kiếm thành công
        JOptionPane.showMessageDialog(this, "Tìm kiếm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
        jLabel4 = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        rdoNu = new javax.swing.JRadioButton();
        txtTimKiem = new javax.swing.JTextField();
        btnSua = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        rdoNam = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jLabel4.setText("Tìm kiếm");

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel5.setText("Số điện thoại");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jLabel6.setText("Giới tính");

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel7.setText("Địa chỉ");

        jLabel2.setText("Tên khách hàng");

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel3.setText("Mã khách hàng");

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

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Quản lý Khách Hàng");
        jPanel1.add(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                                            .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addGap(53, 53, 53))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimKiem)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThem))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnXoa))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnSua))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu)
                    .addComponent(jLabel6)
                    .addComponent(btnLamMoi))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        Sua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        TimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        LamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        TimKiemBangClicked();
    }//GEN-LAST:event_tblBangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        Them();
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
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

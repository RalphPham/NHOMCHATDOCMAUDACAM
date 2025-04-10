/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package THAI;

import PHU.DataConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class HoaDonJPanel extends javax.swing.JPanel {

    DefaultTableModel tbModel;
    private BigDecimal tongTien;
    private String maDH; // Biến để lưu maDH

    /**
     * Creates new form HoaDon
     */
    public HoaDonJPanel() {
        initComponents();
        init();
        
    }

    void init() {
        tbModel = new DefaultTableModel();
        tbModel.setColumnIdentifiers(new String[]{"Mã hóa đơn", "Ngày thanh toán", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Tổng tiền"});
        tblhoadon.setModel(tbModel);
        loadAllHoaDon();
    }


    

    public void loadAllHoaDon() {
        tbModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        HoaDonDAO hd = new HoaDonDAO();
        DecimalFormat df = new DecimalFormat("#,###"); // Định dạng số

        try {
            List<HoaDon> danhSachHoaDon = hd.findAll();

            if (danhSachHoaDon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có hóa đơn nào đã thanh toán trong cơ sở dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Hiển thị dữ liệu lên bảng
            for (HoaDon hoaDon : danhSachHoaDon) {
                // Hiển thị các dòng chi tiết của hóa đơn
                for (HoaDonChiTiet chiTiet : hoaDon.getChiTietList()) {
                    tbModel.addRow(new Object[]{
                        hoaDon.getMaHoaDon(),
                        hoaDon.getNgayThanhToan().toString(),
                        chiTiet.getTenSP(),
                        chiTiet.getSoLuong(),
                        df.format(chiTiet.getDonGia()),
                        df.format(chiTiet.getThanhTien()),
                        "" // Để trống cột Tổng tiền ở các dòng chi tiết
                    });
                }

                // Thêm dòng tổng tiền cho hóa đơn
                String tongTien = hoaDon.getTongTien() != null ? df.format(hoaDon.getTongTien()) : "0";
                tbModel.addRow(new Object[]{"", "", "", "", "", "Tổng tiền:", tongTien});
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveHoaDon() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DataConnection.open();
            String checkSql = "SELECT COUNT(*) FROM HoaDon WHERE MADH = ?";
            ps = conn.prepareStatement(checkSql);
            ps.setString(1, maDH);
            rs = ps.executeQuery();
            if (!rs.next() || rs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(this, "Hóa đơn chưa được tạo cho đơn hàng " + maDH + "!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            rs.close();
            ps.close();

            // Lấy tổng tiền từ bảng (dòng cuối cùng)
            int lastRow = tbModel.getRowCount() - 1;
            String tongTienStr = (String) tbModel.getValueAt(lastRow, 6); // Cột "Tổng tiền"
            if (tongTienStr == null || tongTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy tổng tiền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tongTienStr = tongTienStr.replace(",", "");
            BigDecimal tongTien = new BigDecimal(tongTienStr);

            // Cập nhật TongTien trong bảng HoaDon
            String updateSql = "UPDATE HoaDon SET TongTien = ? WHERE MADH = ?";
            ps = conn.prepareStatement(updateSql);
            ps.setBigDecimal(1, tongTien);
            ps.setString(2, maDH);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Lưu hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadHoaDon(String maDH) {
        this.maDH = maDH; // Lưu maDH
        tbModel.setRowCount(0); // Xóa dữ liệu cũ
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DecimalFormat df = new DecimalFormat("#,###");
        BigDecimal tongTien = BigDecimal.ZERO; // Biến để lưu tổng tiền

        try {
            conn = DataConnection.open();

            // Truy vấn để lấy dữ liệu hóa đơn
            String sql = "SELECT hd.MaHoaDon, hd.NgayThanhToan, dhct.TenSP, dhct.SoLuong, dhct.DonGia, dhct.ThanhTien "
                    + "FROM HoaDon hd "
                    + "JOIN DonHang dh ON hd.MADH = dh.MADH "
                    + "JOIN DonHangChiTiet dhct ON dhct.MADH = dh.MADH "
                    + "WHERE hd.MADH = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maDH);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu hóa đơn cho đơn hàng " + maDH + "!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tính tổng tiền từ ThanhTien
            while (rs.next()) {
                BigDecimal thanhTien = rs.getBigDecimal("ThanhTien");
                if (thanhTien != null) {
                    tongTien = tongTien.add(thanhTien);
                    // Debug: In giá trị ThanhTien của từng dòng
                    System.out.println("ThanhTien của sản phẩm " + rs.getString("TenSP") + ": " + thanhTien);
                }
                tbModel.addRow(new Object[]{
                    rs.getString("MaHoaDon"),
                    rs.getDate("NgayThanhToan"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuong"),
                    df.format(rs.getBigDecimal("DonGia")),
                    df.format(rs.getBigDecimal("ThanhTien")),
                    "" // Không hiển thị Tổng tiền ở các dòng chi tiết
                });
            }

            // Debug: In tổng tiền sau khi tính
            System.out.println("Tổng tiền của đơn hàng " + maDH + ": " + tongTien);

            // Thêm dòng tổng tiền vào cuối bảng
            if (tongTien.compareTo(BigDecimal.ZERO) > 0) {
                tbModel.addRow(new Object[]{"", "", "", "", "", "Tổng tiền:", df.format(tongTien)});
            } else {
                tbModel.addRow(new Object[]{"", "", "", "", "", "Tổng tiền:", "0"});
                JOptionPane.showMessageDialog(this, "Tổng tiền của đơn hàng " + maDH + " là 0. Vui lòng kiểm tra lại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }

            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblhoadon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        tblhoadon.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblhoadon);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("HÓA ĐƠN");
        jPanel1.add(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblhoadon;
    // End of variables declaration//GEN-END:variables
}

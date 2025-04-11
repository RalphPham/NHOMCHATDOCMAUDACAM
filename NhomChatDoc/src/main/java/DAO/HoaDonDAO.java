/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DataConnection;
import Model.HoaDon;
import Model.HoaDonChiTiet;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class HoaDonDAO {

    public List<HoaDon> findAll() throws Exception {
        Map<String, HoaDon> hoaDonMap = new HashMap<>(); 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DataConnection.open();

            // Truy vấn để lấy hóa đơn đã thanh toán và chi tiết
            String sql = "SELECT hd.MaHoaDon, hd.MaDH, hd.NgayThanhToan, hd.TongTien, "
                    + "dhct.TenSP, dhct.SoLuong, dhct.DonGia, dhct.ThanhTien "
                    + "FROM HoaDon hd "
                    + "JOIN DonHang dh ON hd.MADH = dh.MADH "
                    + "JOIN DonHangChiTiet dhct ON dhct.MADH = dh.MADH "
                    + "WHERE hd.NgayThanhToan IS NOT NULL "
                    + "ORDER BY hd.MaHoaDon";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Duyệt qua các bản ghi
            while (rs.next()) {
                String maHoaDon = rs.getString("MaHoaDon");
                String maDH = rs.getString("MaDH");
                LocalDateTime ngayThanhToan = rs.getTimestamp("NgayThanhToan").toLocalDateTime();
                BigDecimal tongTien = rs.getBigDecimal("TongTien");

                // Tạo hoặc lấy đối tượng HoaDon từ Map
                HoaDon hoaDon = hoaDonMap.get(maHoaDon);
                if (hoaDon == null) {
                    hoaDon = new HoaDon(maHoaDon, maDH, ngayThanhToan, tongTien);
                    hoaDonMap.put(maHoaDon, hoaDon);
                }

                // Tạo đối tượng HoaDonChiTiet
                String tenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                BigDecimal thanhTien = rs.getBigDecimal("ThanhTien");

                HoaDonChiTiet chiTiet = new HoaDonChiTiet(tenSP, soLuong, donGia, thanhTien);
                hoaDon.addChiTiet(chiTiet);
            }

        } catch (Exception e) {
            throw new Exception("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage(), e);
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

        return new ArrayList<>(hoaDonMap.values());
    }
    }


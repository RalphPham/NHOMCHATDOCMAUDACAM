/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package THAI;

import PHU.DataConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class HoaDonDAO {

    public List<Object[]> getHoaDon() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT hd.MaHD, hd.NgayThanhToan, dhct.TenSP, dhct.SoLuong, dhct.DonGia, dhct.SoLuong * ct.DonGia AS ThanhTien "
                + "FROM HoaDon hd "
                + "JOIN DonHang dh ON hd.MaDH = dh.MaDH "
                + "JOIN DonHangChiTiet dhct ON dhct.MaDH = dh.MaDH";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getString("MaHD"),
                    rs.getDate("NgayThanhToan"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getDouble("ThanhTien")
                };
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

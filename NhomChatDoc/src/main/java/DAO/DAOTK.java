/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DataConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class DAOTK {

    public List<Object[]> thongKeTheoNam(int nam) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT sp.TenSP, sp.Hang, "
                + "SUM(dhct.ThanhTien) AS DoanhThu, "
                + "SUM(sp.TongChiPhiNhapHang) AS TongChiPhiNhapHang, "
                + "SUM(dh.TongTien) AS TongTien, "
                + "SUM(dhct.SoLuong * dhct.DonGia) - SUM(sp.TongChiPhiNhapHang) AS LoiNhuan "
                + "FROM DonHangChiTiet dhct "
                + "JOIN DonHang dh ON dh.MaDH = dhct.MaDH "
                + "JOIN SanPham sp ON sp.TenSP = dhct.TenSP "
                + "WHERE YEAR(dh.NgayTaoDH) = ? "
                + "GROUP BY sp.TenSP, sp.Hang";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, nam);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("TenSP"),
                    rs.getString("Hang"),
                    rs.getDouble("DoanhThu"),
                    rs.getDouble("TongChiPhiNhapHang"),
                    rs.getString("LoiNhuan"),
                    rs.getDouble("TongTien")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

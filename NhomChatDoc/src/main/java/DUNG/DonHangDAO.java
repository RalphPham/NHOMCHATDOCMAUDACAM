/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DUNG;

import PHU.DataConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DonHangDAO {

    public boolean insert(DonHang dh) {
        String sql = "insert into DonHang (MaDH, MaNV, MaKH, NgayTaoDH, PhuongThucThanhToan, TongSoLuong, TongTien) values (?,?,?,?,?,?,?)";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dh.getMaDH());
            ps.setString(2, dh.getMaNV());
            ps.setString(3, dh.getMaKH());
            ps.setDate(4, (Date) dh.getNgayTao());
            ps.setString(5, dh.getPhuongThucThanhToan());
            ps.setInt(6, dh.getTongSoLuong());
            ps.setBigDecimal(7, dh.getTongTien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(DonHang dh) {
        String sql = "delete DonHang where MaDH=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dh.getMaDH());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(DonHang dh) {
        String sql = "UPDATE DonHang SET MaNV = ?, MaKH = ?, NgayTaoDH = ?, PhuongThucThanhToan = ?, TongSoLuong = ?, TongTien = ? WHERE MaDH = ?";
        try (Connection conn = DataConnection.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dh.getMaNV());
            pstmt.setString(2, dh.getMaKH());
            pstmt.setDate(3, (Date) dh.getNgayTao());
            pstmt.setString(4, dh.getPhuongThucThanhToan());
            pstmt.setInt(5, dh.getTongSoLuong());
            pstmt.setBigDecimal(6, dh.getTongTien());
            pstmt.setString(7, dh.getMaDH());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DonHang> findAll() {
        String sql = "select * from DonHang";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<DonHang> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DonHang dh = new DonHang();
                dh.setMaDH(rs.getString("MaDH"));
                dh.setMaNV(rs.getString("MaNV"));
                dh.setMaKH(rs.getString("MaKH"));
                dh.setNgayTao(rs.getDate("NgayTaoDH"));
                dh.setPhuongThucThanhToan(rs.getString("PhuongThucThanhToan"));
                dh.setTongSoLuong(rs.getInt("TongSoLuong"));
                dh.setTongTien(rs.getBigDecimal("TongTien"));
                list.add(dh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DonHang findId(String maDH) {
        String sql = "select * from DonHang where MaDH=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DonHang dh = new DonHang();
                dh.setMaDH(rs.getString("MaDH"));
                dh.setMaNV(rs.getString("MaNV"));
                dh.setMaKH(rs.getString("MaKH"));
                dh.setNgayTao(rs.getDate("NgayTaoDH"));
                dh.setPhuongThucThanhToan(rs.getString("PhuongThucThanhToan"));
                dh.setTongSoLuong(rs.getInt("TongSoLuong"));
                dh.setTongTien(rs.getBigDecimal("TongTien"));
                return dh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkMaDH(String maDH) {
        String sql = "SELECT COUNT(*) FROM DonHang WHERE MaDH = ?";
        try (Connection conn = DataConnection.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maDH);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT(*) > 0 tức là đã có mã DH này
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getMaNV() {
        String sql = "select MaNV from NhanVien";
        List<String> dsMaNV = new ArrayList<>();
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dsMaNV.add(rs.getString("MaNV"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaNV;
    }

    public List<String> getMaKH() {
        String sql = "select MaKH from KhachHang";
        List<String> dsMaKH = new ArrayList<>();
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dsMaKH.add(rs.getString("MaKH"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaKH;
    }

    //Tổng số lượng sản phẩm mua
    public int tinhTongSoLuong(DonHang dh) {
        int tongSoLuong = 0;
        String sql = "select sum(SoLuong) from DonHangChiTiet where MaDH=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, dh.getMaDH());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dh.setTongSoLuong(rs.getInt("TongSoLuong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongSoLuong;
    }

    //Tính tổng tiền
}

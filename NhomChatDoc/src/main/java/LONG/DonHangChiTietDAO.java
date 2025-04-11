/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LONG;

import Model.DonHangChiTiet;
import Model.DataConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author famut
 */
public class DonHangChiTietDAO {

    public boolean insert(DonHangChiTiet dhct) {
        String sql = "INSERT INTO DonHangChiTiet values"
                + "(?,?,?,?,?,?)";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            preStm.setString(1, dhct.getMaDonHangChiTiet());
            preStm.setString(2, dhct.getMaDH());
            preStm.setString(3, dhct.getTenSP());
            preStm.setInt(4, dhct.getSoLuong());
            preStm.setBigDecimal(5, dhct.getDonGia());
            preStm.setBigDecimal(6, dhct.getThanhTien());
            return preStm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(DonHangChiTiet dhct) {
        String sql = "UPDATE DonHangChiTiet set MaDH=?,TenSP=?,SoLuong=?,DonGia=?,ThanhTien=? where MaDonHangChiTiet=?";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            preStm.setString(1, dhct.getMaDH());
            preStm.setString(2, dhct.getTenSP());
            preStm.setInt(3, dhct.getSoLuong());
            preStm.setBigDecimal(4, dhct.getDonGia());
            preStm.setBigDecimal(5, dhct.getThanhTien());
            preStm.setString(6, dhct.getMaDonHangChiTiet());
            return preStm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(DonHangChiTiet dhct) {
        String sql = "DELETE FROM DonHangChiTIet where MaDonHangChiTiet=?";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            preStm.setString(1, dhct.getMaDonHangChiTiet());
            return preStm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<DonHangChiTiet> FindAll() {
        String sql = "SELECT*FROM DonHangChiTiet";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            List<DonHangChiTiet> dhctList = new ArrayList<>();
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                DonHangChiTiet dhct = new DonHangChiTiet();
                dhct.setMaDonHangChiTiet(rs.getString("MaDonHangChiTiet"));
                dhct.setMaDH(rs.getString("MaDH"));
                dhct.setTenSP(rs.getString("TenSP"));
                dhct.setSoLuong(rs.getInt("SoLuong"));
                dhct.setDonGia(rs.getBigDecimal("DonGia"));
                dhct.setThanhTien(rs.getBigDecimal("ThanhTien"));
                dhctList.add(dhct);
            }
            return dhctList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DonHangChiTiet FindById(String MaDonHangChiTiet) {
        String sql = "SELECT*FROM DonHangChiTiet where MaDonHangChiTiet=?";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            preStm.setString(1, MaDonHangChiTiet);
            ResultSet rs = preStm.executeQuery();
            if (rs.next()) {
                DonHangChiTiet dhct = new DonHangChiTiet();
                dhct.setMaDonHangChiTiet(rs.getString("MaDonHangChiTiet"));
                dhct.setMaDH(rs.getString("MaDH"));
                dhct.setTenSP(rs.getString("TenSP"));
                dhct.setSoLuong(rs.getInt("SoLuong"));
                dhct.setDonGia(rs.getBigDecimal("DonGia"));
                dhct.setThanhTien(rs.getBigDecimal("ThanhTien"));
                return dhct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> MaSP() {
        String sql = "SELECT TenSP FROM SanPham";
        List<String> dsMaSP = new ArrayList<>();
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                dsMaSP.add(rs.getString("TenSP"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaSP;
    }

    public List<String> MaDH() {
        String sql = "SELECT MaDH FROM DonHang";
        List<String> dsMaDH = new ArrayList<>();
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                dsMaDH.add(rs.getString("MaDH"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaDH;
    }

    public float getGiaBanBySP(String MaSP) {
        float Gia = 0;
        String sql = "SELECT GiaBan from SanPham where TenSP=?";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            preStm.setString(1, MaSP);
            ResultSet rs = preStm.executeQuery();
            if (rs.next()) {
                Gia = rs.getFloat("GiaBan");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Gia;
    }
   public List<DonHangChiTiet> findByMaDH(String maDH) {
        List<DonHangChiTiet> list = new ArrayList<>();
        String sql = "SELECT * FROM DonHangChiTiet WHERE MaDH = ?";
        try (Connection conn = DataConnection.open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DonHangChiTiet dhct = new DonHangChiTiet();
                dhct.setMaDonHangChiTiet(rs.getString("MaDonHangChiTiet"));
                dhct.setMaDH(rs.getString("MaDH"));
                dhct.setTenSP(rs.getString("TenSP"));
                dhct.setSoLuong(rs.getInt("SoLuong"));
                dhct.setDonGia(rs.getBigDecimal("DonGia"));
                dhct.setThanhTien(rs.getBigDecimal("ThanhTien"));
                list.add(dhct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
   public boolean checkMaDHCT(String maDHCT) {
        String sql = "SELECT COUNT(*) FROM DonHangChiTiet WHERE MaDonHangChiTiet = ?";
        try (Connection conn = DataConnection.open(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maDHCT);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

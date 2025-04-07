/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PHU;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author famut
 */
public class SanPhamDAO {

    public boolean insert(SanPham sp) {
        String sql = "Insert into SanPham (MaSP,TenSP,Hang,GiaNhap,GiaBan,SoLuongNhap,ThoiGianNhapHang,TongChiPhiNhapHang,MoTa)"
                + "values (?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = DataConnection.open();
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, sp.getMasp());
            pre.setString(2, sp.getTensp());
            pre.setString(3, sp.getHang());
            pre.setBigDecimal(4, sp.getGianhap());
            pre.setBigDecimal(5, sp.getGiaban());
            pre.setInt(6, sp.getSoluongnhap());
            pre.setDate(7, (java.sql.Date) sp.getThoigiannhaphang());
            pre.setBigDecimal(8, sp.getTongchiphi());
            pre.setString(9, sp.getMota());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SanPham sp) {
        String sql = "Update SanPham set TenSP=?,Hang=?,GiaNhap=?,GiaBan=?,SoLuongNhap=?,ThoiGianNhapHang=?,TongChiPhiNhapHang=?,MoTa=? where MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, sp.getTensp());
            pre.setString(2, sp.getHang());
            pre.setBigDecimal(3, sp.getGianhap());
            pre.setBigDecimal(4, sp.getGiaban());
            pre.setInt(5, sp.getSoluongnhap());
            pre.setDate(6, (java.sql.Date) sp.getThoigiannhaphang());
            pre.setBigDecimal(7, sp.getTongchiphi());
            pre.setString(8, sp.getMota());
            pre.setString(9, sp.getMasp());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(SanPham sp) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);

            p.setString(1, sp.getMasp());

            return p.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SanPham> FindAll() {
        String sql = "Select * from SanPham";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            List<SanPham> list = new ArrayList<>();
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMasp(rs.getString("MaSP"));
                sp.setTensp(rs.getString("TenSP"));
                sp.setHang(rs.getString("Hang"));
                sp.setGianhap(rs.getBigDecimal("GiaNhap"));
                sp.setGiaban(rs.getBigDecimal("GiaBan"));
                sp.setSoluongnhap(rs.getInt("SoluongNhap"));
                sp.setThoigiannhaphang(rs.getDate("ThoiGianNhapHang"));
                sp.setTongchiphi(rs.getBigDecimal("TongChiPhiNhapHang"));
                sp.setMota(rs.getString("MoTa"));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<SanPham> SearchSanPham(String MaSanPham) {
        String sql = "Select * from SanPham where MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            List<SanPham> list = new ArrayList<>();
            p.setString(1, MaSanPham);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMasp(rs.getString("MaSP"));
                sp.setTensp(rs.getString("TenSP"));
                sp.setHang(rs.getString("Hang"));
                sp.setGianhap(rs.getBigDecimal("GiaNhap"));
                sp.setGiaban(rs.getBigDecimal("GiaBan"));
                sp.setSoluongnhap(rs.getInt("SoluongNhap"));
                sp.setThoigiannhaphang(rs.getDate("ThoiGianNhapHang"));
                sp.setTongchiphi(rs.getBigDecimal("TongChiPhiNhapHang"));
                sp.setMota(rs.getString("MoTa"));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SanPham FindId(String maSP) {
        String sql = "Select * from SanPham where MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, maSP);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMasp(rs.getString("MaSP"));
                sp.setTensp(rs.getString("TenSP"));
                sp.setHang(rs.getString("Hang"));
                sp.setGianhap(rs.getBigDecimal("GiaNhap"));
                sp.setGiaban(rs.getBigDecimal("GiaBan"));
                sp.setSoluongnhap(rs.getInt("SoluongNhap"));
                sp.setThoigiannhaphang(rs.getDate("ThoiGianNhapHang"));
                sp.setTongchiphi(rs.getBigDecimal("TongChiPhiNhapHang"));
                sp.setMota(rs.getString("MoTa"));
                return sp;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal TongChiPhiNhapHang(SanPham sp) {
        BigDecimal TongChiPhiNhapHang = BigDecimal.ZERO;
        String sql = "Select(GiaNhap*SoLuongNhap) as TongChiPhiNhapHang from SanPham where MaSP=?";
        try {
            Connection con = DataConnection.open();
            PreparedStatement p = con.prepareStatement(sql);
            p.setString(1, sp.getMasp());
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                TongChiPhiNhapHang = rs.getBigDecimal("TongChiPhiNhapHang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TongChiPhiNhapHang;
    }

    public int getSoLuongByMaSP(String TenSP) {
        String sql = "SELECT SoLuongNhap FROM SanPham WHERE TenSP = ?";
        try (
            Connection con = DataConnection.open();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, TenSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SoLuongNhap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateSoLuong(String maSP, int soLuongMoi) {
        String sql = "UPDATE SanPham SET SoLuongNhap = ? WHERE TenSP = ?";
        try (
                Connection conn = DataConnection.open(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongMoi);
            ps.setString(2, maSP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

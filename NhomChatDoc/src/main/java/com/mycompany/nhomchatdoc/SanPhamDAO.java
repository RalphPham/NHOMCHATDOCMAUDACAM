/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nhomchatdoc;

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
        String sql = "Insert into SanPham (MaSanPham,TenSanPham,Hang,GiaNhap,GiaBan,SoLuongNhap,ThoiGianNhapHang,TongChiPhiNhapHang,MoTa)"
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
            pre.setDate(7, (java.sql.Date) (Date) sp.getThoigiannhaphang());
            pre.setBigDecimal(8, sp.getTongchiphi());
            pre.setString(9, sp.getMota());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SanPham sp) {
        String sql = "Update SanPham set TenSP=?,Hang=?,GiaNhap=?,GiaBan=?,SoLuongNhap=?,ThoiGianNhapHang=?,TongChiPhiNhapHang,MoTa=? where MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, sp.getMasp());
            pre.setString(2, sp.getTensp());
            pre.setString(3, sp.getHang());
            pre.setBigDecimal(4, sp.getGianhap());
            pre.setBigDecimal(5, sp.getGiaban());
            pre.setInt(6, sp.getSoluongnhap());
            pre.setDate(7, (java.sql.Date) (Date) sp.getThoigiannhaphang());
            pre.setBigDecimal(8, sp.getTongchiphi());
            pre.setString(9, sp.getMota());
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
     public boolean delete(SanPham sp){
        String sql="DELETE FROM SanPham WHERE MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);           
           
            p.setString(1, sp.getMasp());
            
            return p.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
     public List<SanPham> FindAll(){
        String sql="Select * from SanPham";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            List<SanPham> list = new ArrayList<>();
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                SanPham sp = new SanPham();
                sp.setMasp(rs.getString("MaSP"));
                sp.setTensp(rs.getString("TenSP"));
                sp.setHang(rs.getString("Hang"));
                sp.setGianhap(rs.getBigDecimal("GiaNhap"));
                sp.setGiaban(rs.getBigDecimal("GiaBan"));
                sp.setSoluongnhap(rs.getInt("Soluongnhap"));
                sp.setThoigiannhaphang(rs.getDate("Thoigiannhaphang"));
                sp.setTongchiphi(rs.getBigDecimal("Tongchiphi"));
                sp.setMota(rs.getString("MoTa"));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      public SanPham FindId(String maSP){
        String sql="Select * from SanPham where MaSP=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, maSP);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                
                SanPham sp = new SanPham();
                  sp.setMasp(rs.getString("MaSP"));
                sp.setTensp(rs.getString("TenSP"));
                sp.setHang(rs.getString("Hang"));
                sp.setGianhap(rs.getBigDecimal("GiaNhap"));
                sp.setGiaban(rs.getBigDecimal("GiaBan"));
                sp.setSoluongnhap(rs.getInt("Soluongnhap"));
                sp.setThoigiannhaphang(rs.getDate("Thoigiannhaphang"));
                sp.setTongchiphi(rs.getBigDecimal("Tongchiphi"));
                sp.setMota(rs.getString("MoTa"));
                return sp;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
}
}

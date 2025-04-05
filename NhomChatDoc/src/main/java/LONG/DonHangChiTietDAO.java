/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LONG;

import PHU.DataConnection;
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
    public boolean insert(DonHangChiTiet dhct){
        String sql="INSERT INTO DonHangChiTiet values"
                + "(?,?,?,?,?,?,?)";
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            preStm.setString(1, dhct.getMaDonHangChiTiet());
            preStm.setString(2, dhct.getMaDH());
            preStm.setString(3, dhct.getMaSP());
            preStm.setInt(4, dhct.getSoLuong());
            preStm.setBigDecimal(5, dhct.getDonGia());
            preStm.setBigDecimal(6, dhct.getThanhTien());
            return preStm.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(DonHangChiTiet dhct){
        String sql="UPDATE DonHangChiTiet set MaDH=?,MaSP=?,SoLuong=?,DonGia=?,ThanhTien=? where MaDonHangChiTiet=?";
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            preStm.setString(1, dhct.getMaDH());
            preStm.setString(2, dhct.getMaSP());
            preStm.setInt(3, dhct.getSoLuong());
            preStm.setBigDecimal(4, dhct.getDonGia());
            preStm.setBigDecimal(5, dhct.getThanhTien());
            preStm.setString(6, dhct.getMaDonHangChiTiet());
            return preStm.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(DonHangChiTiet dhct){
        String sql="DELETE FROM DonHangChiTIet where MaDonHangChiTiet=?";
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            preStm.setString(1, dhct.getMaDonHangChiTiet());
            return preStm.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<DonHangChiTiet> FindAll(){
        String sql="SELECT*FROM DonHangChiTiet";
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            List<DonHangChiTiet> dhctList=new ArrayList<>();
            ResultSet rs=preStm.executeQuery();
            while (rs.next()) {                
                DonHangChiTiet dhct=new DonHangChiTiet();
                dhct.setMaDonHangChiTiet(rs.getString("MaDonHangChiTiet"));
                dhct.setMaDH(rs.getString("MaDH"));
                dhct.setMaSP(rs.getString("MaSP"));
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
    public DonHangChiTiet FindById(String MaDonHangChiTiet){
        String sql="SELECT*FROM DonHangChiTiet where MaDonHangChiTiet=?";
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            preStm.setString(1, MaDonHangChiTiet);
            ResultSet rs=preStm.executeQuery();
            if (rs.next()) {
                DonHangChiTiet dhct=new DonHangChiTiet();
                dhct.setMaDonHangChiTiet(rs.getString("MaDonHangChiTiet"));
                dhct.setMaDH(rs.getString("MaDH"));
                dhct.setMaSP(rs.getString("MaSP"));
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
    public List<String> MaSP(){
        String sql="SELECT MaSP FROM SanPham";
        List<String> dsMaSP=new ArrayList<>();
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            ResultSet rs=preStm.executeQuery();
            while (rs.next()) {                
                dsMaSP.add(rs.getString("MaSP"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMaSP;
    }
    public List<String> MaDH(){
        String sql="SELECT MaDH FROM DonHang";
        List<String> dsMaDH=new ArrayList<>();
        try {
            Connection con=DataConnection.open();
            PreparedStatement preStm=con.prepareStatement(sql);
            ResultSet rs=preStm.executeQuery();
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
        String sql = "SELECT GiaBan from SanPham where MaSP=?";
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
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DataConnection;
import Model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author famut
 */
public class NhanVienDAO {
     public boolean insert(NhanVien nv){
        String sql= "insert into NhanVien(MaNV,TenNV,Email,SDT,GioiTinh,DiaChi,NgaySinh) values(?,?,?,?,?,?,?) ";
        try {
           Connection con= DataConnection.open();
           PreparedStatement pre = con.prepareStatement(sql);
           pre.setString(1,nv.getMaNV());
           pre.setString(2,nv.getTenNV());
           pre.setString(3,nv.getEmail());
           pre.setString(4,nv.getSDT());
           pre.setString(5,nv.getGioiTinh());
           pre.setString(6,nv.getDiaChi());
           pre.setDate(7, (java.sql.Date) nv.getNgaySinh());
           return pre.executeUpdate() >0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
    }
    public boolean update(NhanVien nv){
        String sql = "UPDATE NhanVien set TenNV = ?, Email = ?, SDT = ?, GioiTinh = ?, DiaChi = ?, NgaySinh = ? WHERE MaNV = ?";

        try {
           Connection con= DataConnection.open();
           PreparedStatement pre = con.prepareStatement(sql);          
           pre.setString(1,nv.getTenNV());
           pre.setString(2,nv.getEmail());
           pre.setString(3,nv.getSDT());
           pre.setString(4,nv.getGioiTinh());
           pre.setString(5,nv.getDiaChi());
           pre.setDate(6, (java.sql.Date) nv.getNgaySinh());
           pre.setString(7,nv.getMaNV());
           return pre.executeUpdate() >0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
    }
    public boolean delete(NhanVien nv){
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
        try (Connection con = DataConnection.open();
             PreparedStatement preStm = con.prepareStatement(sql)) {
             
            preStm.setString(1, nv.getMaNV());
            
            int row = preStm.executeUpdate();
            System.out.println("Xóa thành công: " + row + " dòng.");
            
            return row > 0;
        } catch (Exception ex) {
            ex.printStackTrace(); 
        }
        return false; 
        
    }
    public List<NhanVien> findAll(){
       String sql="SELECT * FROM NhanVien";
        try {
           Connection con= DataConnection.open();
           PreparedStatement pre = con.prepareStatement(sql); 
           List<NhanVien> List = new ArrayList<>();
           ResultSet rs = pre.executeQuery();
           while(rs.next()){
               NhanVien nv=new NhanVien();
               nv.setMaNV(rs.getString("MaNV"));
               nv.setTenNV(rs.getString("TenNV"));
               nv.setEmail(rs.getString("Email"));
               nv.setSDT(rs.getString("SDT"));
               nv.setGioiTinh(rs.getString("GioiTinh"));
               nv.setDiaChi(rs.getString("DiaChi"));
               nv.setNgaySinh(rs.getDate("NgaySinh"));
               List.add(nv);
           }
           return List;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public NhanVien findByID(String MaNV){
        String sql = "select * from NhanVien where MaNV = ?";
        try {
            Connection con = DataConnection.open();
            PreparedStatement preStm = con.prepareStatement(sql);
            preStm.setString(1, MaNV);
            ResultSet rs = preStm.executeQuery();
            if(rs.next()){
                NhanVien nv=new NhanVien();
               nv.setMaNV(rs.getString("MaNV"));
               nv.setTenNV(rs.getString("TenNV"));
               nv.setEmail(rs.getString("Email"));
               nv.setSDT(rs.getString("SDT"));
               nv.setGioiTinh(rs.getString("GioiTinh"));
               nv.setDiaChi(rs.getString("DiaChi"));
               nv.setNgaySinh(rs.getDate("NgaySinh"));
                return nv;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public boolean checkMaNV(String maNV) {
    String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNV = ?";
    try (Connection conn = DataConnection.open();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, maNV);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Nếu COUNT(*) > 0 tức là đã có mã NV này
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}

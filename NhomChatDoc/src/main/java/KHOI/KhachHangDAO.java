/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KHOI;

import com.mycompany.nhomchatdoc.DataConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author famut
 */
public class KhachHangDAO {
    public boolean insert(KhachHang kh){
        String sql="Insert into KhachHang (MaKH,TenKH,SDT,GioiTinh,DiaChi)"
                + "values(?,?,?,?,?)";
        try {
            Connection conn=DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, kh.getMaKH());
            p.setString(2, kh.getTenKH());
            p.setString(3, kh.getSDT());
            p.setString(4, kh.getGioiTinh());
            p.setString(5, kh.getDiaChi());
            
            return p.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean update(KhachHang kh){
        String sql="Update KhachHang set TenKH=?,SDT=?,GioiTinh=?,DiaChi=? where MaKH=? ";
        try {
            Connection conn=DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(5, kh.getMaKH());
            p.setString(1, kh.getTenKH());
            p.setString(2, kh.getSDT());
            p.setString(3, kh.getGioiTinh());
            p.setString(4, kh.getDiaChi());
            
            return p.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(KhachHang kh){
        String sql="Delete KhachHang where MaKH=? ";
        try {
            Connection conn=DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, kh.getMaKH());
            
            return p.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<KhachHang> FindAll(){
        String sql="Select * from KhachHang";
        try {
            Connection conn=DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            List<KhachHang> list = new ArrayList<>();
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSDT(rs.getString("SDT"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                list.add(kh);
                
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public KhachHang findByID(String maKH){
        String sql="Select * from KhachHang where MaKH=?";
        try {
            Connection conn=DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, maKH);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSDT(rs.getString("SDT"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setDiaChi(rs.getString("DiaChi"));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

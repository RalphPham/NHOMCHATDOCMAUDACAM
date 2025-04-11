/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DataConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author admin
 */
public class DangNhapDAO {

    public boolean User(String tk){
        String sql = "Select * from DangNhap where TaiKhoan=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, tk);
            ResultSet rs = p.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean Pass(String mk){
        String sql = "Select * from DangNhap where MatKhau=?";
        try {
            Connection conn = DataConnection.open();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, mk);
            ResultSet rs = p.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

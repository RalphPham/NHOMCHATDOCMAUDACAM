/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SOn;

import java.util.Date;

/**
 *
 * @author famut
 */
public class NhanVien {
     private String MaNV,tenNV,email,SDT,gioiTinh,DiaChi;
    private Date NgaySinh;

    @Override
    public String toString() {
        return "NhanVien{" + "MaNV=" + MaNV + ", tenNV=" + tenNV + ", email=" + email + ", SDT=" + SDT + ", gioiTinh=" + gioiTinh + ", DiaChi=" + DiaChi + ", NgaySinh=" + NgaySinh + '}';
    }

    
    public NhanVien() {
    }

    public NhanVien(String MaNV, String tenNV, String email, String SDT, String gioiTinh, String DiaChi, Date NgaySinh) {
        this.MaNV = MaNV;
        this.tenNV = tenNV;
        this.email = email;
        this.SDT = SDT;
        this.gioiTinh = gioiTinh;
        this.DiaChi = DiaChi;
        this.NgaySinh = NgaySinh;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }
}

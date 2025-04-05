/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DUNG;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class DonHang {
    private String maDH, maNV, maKH;
    private Date ngayTao;
    private String phuongThucThanhToan;
    private int tongSoLuong;
    private BigDecimal tongTien;

    public DonHang() {
    }

    public DonHang(String maDH, String maNV, String maKH, Date ngayTao, String phuongThucThanhToan, int tongSoLuong, BigDecimal tongTien) {
        this.maDH = maDH;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayTao = ngayTao;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tongSoLuong = tongSoLuong;
        this.tongTien = tongTien;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public int getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
    
    
}

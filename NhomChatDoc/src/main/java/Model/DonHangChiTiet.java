/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author famut
 */
public class DonHangChiTiet {
    private String MaDonHangChiTiet,MaDH,TenSP;
    private int SoLuong;
    private BigDecimal DonGia,ThanhTien;

    public DonHangChiTiet(String MaDonHangChiTiet, String MaDH, String TenSP, int SoLuong, BigDecimal DonGia, BigDecimal ThanhTien) {
        this.MaDonHangChiTiet = MaDonHangChiTiet;
        this.MaDH = MaDH;
        this.TenSP = TenSP;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.ThanhTien = ThanhTien;
    }

    public DonHangChiTiet() {
    }

    public String getMaDonHangChiTiet() {
        return MaDonHangChiTiet;
    }

    public void setMaDonHangChiTiet(String MaDonHangChiTiet) {
        this.MaDonHangChiTiet = MaDonHangChiTiet;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String MaDH) {
        this.MaDH = MaDH;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public BigDecimal getDonGia() {
        return DonGia;
    }

    public void setDonGia(BigDecimal DonGia) {
        this.DonGia = DonGia;
    }

    public BigDecimal getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(BigDecimal ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    
}

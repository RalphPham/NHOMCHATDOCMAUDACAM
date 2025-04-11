/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author famut
 */
public class HoaDon {

    private String maHoaDon;
    private String maDH;
    private LocalDateTime ngayThanhToan;
    private BigDecimal tongTien;
    private List<HoaDonChiTiet> chiTietList; // Danh sách chi tiết hóa đơn

    // Constructor chỉ với trường bắt buộc
    public HoaDon(String maHoaDon) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống hoặc null.");
        }
        if (maHoaDon.length() > 10) {
            throw new IllegalArgumentException("Mã hóa đơn không được vượt quá 10 ký tự.");
        }
        this.maHoaDon = maHoaDon;
        this.maDH = null;
        this.ngayThanhToan = null;
        this.tongTien = null;
        this.chiTietList = new ArrayList<>();
    }

    // Constructor với tất cả các trường (trừ chi tiết)
    public HoaDon(String maHoaDon, String maDH, LocalDateTime ngayThanhToan, BigDecimal tongTien) {
        if (maHoaDon == null || maHoaDon.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống hoặc null.");
        }
        if (maHoaDon.length() > 10) {
            throw new IllegalArgumentException("Mã hóa đơn không được vượt quá 10 ký tự.");
        }
        if (maDH != null && maDH.length() > 10) {
            throw new IllegalArgumentException("Mã đơn hàng không được vượt quá 10 ký tự.");
        }
        this.maHoaDon = maHoaDon;
        this.maDH = maDH;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.chiTietList = new ArrayList<>();
    }

    // Getters và Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public LocalDateTime getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public List<HoaDonChiTiet> getChiTietList() {
        return chiTietList;
    }

    public void setChiTietList(List<HoaDonChiTiet> chiTietList) {
        this.chiTietList = chiTietList;
    }

    public void addChiTiet(HoaDonChiTiet chiTiet) {
        this.chiTietList.add(chiTiet);
    }
}

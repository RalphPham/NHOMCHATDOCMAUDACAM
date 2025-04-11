/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author famut
 */
public class SanPham {
     public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public BigDecimal getGianhap() {
        return gianhap;
    }

    public void setGianhap(BigDecimal gianhap) {
        this.gianhap = gianhap;
    }

    public BigDecimal getGiaban() {
        return giaban;
    }

    public void setGiaban(BigDecimal giaban) {
        this.giaban = giaban;
    }

    public int getSoluongnhap() {
        return soluongnhap;
    }

    public void setSoluongnhap(int soluongnhap) {
        this.soluongnhap = soluongnhap;
    }

    public Date getThoigiannhaphang() {
        return thoigiannhaphang;
    }

    public void setThoigiannhaphang(Date thoigiannhaphang) {
        this.thoigiannhaphang = thoigiannhaphang;
    }

    public BigDecimal getTongchiphi() {
        return tongchiphi;
    }

    public void setTongchiphi(BigDecimal tongchiphi) {
        this.tongchiphi = tongchiphi;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    private String masp,tensp,hang;
    private BigDecimal gianhap,giaban;
    private int soluongnhap;
    private Date thoigiannhaphang;
    private BigDecimal tongchiphi;
    private String mota;

    public SanPham(String masp, String tensp, String hang, BigDecimal gianhap, BigDecimal giaban, int soluongnhap, Date thoigiannhaphang, BigDecimal tongchiphi, String mota) {
        this.masp = masp;
        this.tensp = tensp;
        this.hang = hang;
        this.gianhap = gianhap;
        this.giaban = giaban;
        this.soluongnhap = soluongnhap;
        this.thoigiannhaphang = thoigiannhaphang;
        this.tongchiphi = tongchiphi;
        this.mota = mota;
    }

    public SanPham() {
    }
}

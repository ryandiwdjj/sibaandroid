package Models;


import java.sql.Date;
import java.sql.Timestamp;

public class penjualan {
    private Integer id;
    private Integer id_pelanggan;
    private Integer id_cabang;
    private Float total_harga_trans;
    private Float discount_penjualan;
    private Float grand_total;
    private String status_transaksi;
    private String status_pembayaran;
    private String no_plat_kendaraan;
    private String tanggal_penjualan;

    private String nama_pelanggan;
    private String alamat_pelanggan;
    private String no_telp_pelanggan;

    private String nama_cabang;
    private String alamat_cabang;
    private String no_telp_cabang;

    private String created_at;

    public penjualan(Integer id, Integer id_pelanggan, Integer id_cabang, Float total_harga_trans, Float discount_penjualan, Float grand_total, String status_transaksi, String status_pembayaran, String no_plat_kendaraan, String tanggal_penjualan) {
        this.id = id;
        this.id_pelanggan = id_pelanggan;
        this.id_cabang = id_cabang;
        this.total_harga_trans = total_harga_trans;
        this.discount_penjualan = discount_penjualan;
        this.grand_total = grand_total;
        this.status_transaksi = status_transaksi;
        this.status_pembayaran = status_pembayaran;
        this.no_plat_kendaraan = no_plat_kendaraan;
        this.tanggal_penjualan = tanggal_penjualan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getAlamat_pelanggan() {
        return alamat_pelanggan;
    }

    public void setAlamat_pelanggan(String alamat_pelanggan) {
        this.alamat_pelanggan = alamat_pelanggan;
    }

    public String getNo_telp_pelanggan() {
        return no_telp_pelanggan;
    }

    public void setNo_telp_pelanggan(String no_telp_pelanggan) {
        this.no_telp_pelanggan = no_telp_pelanggan;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getAlamat_cabang() {
        return alamat_cabang;
    }

    public void setAlamat_cabang(String alamat_cabang) {
        this.alamat_cabang = alamat_cabang;
    }

    public String getNo_telp_cabang() {
        return no_telp_cabang;
    }

    public void setNo_telp_cabang(String no_telp_cabang) {
        this.no_telp_cabang = no_telp_cabang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(Integer id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public Integer getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(Integer id_cabang) {
        this.id_cabang = id_cabang;
    }

    public Float getTotal_harga_trans() {
        return total_harga_trans;
    }

    public void setTotal_harga_trans(Float total_harga_trans) {
        this.total_harga_trans = total_harga_trans;
    }

    public Float getDiscount_penjualan() {
        return discount_penjualan;
    }

    public void setDiscount_penjualan(Float discount_penjualan) {
        this.discount_penjualan = discount_penjualan;
    }

    public Float getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(Float grand_total) {
        this.grand_total = grand_total;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public String getNo_plat_kendaraan() {
        return no_plat_kendaraan;
    }

    public void setNo_plat_kendaraan(String no_plat_kendaraan) {
        this.no_plat_kendaraan = no_plat_kendaraan;
    }

    public String getTanggal_penjualan() {
        return tanggal_penjualan;
    }

    public void setTanggal_penjualan(String tanggal_penjualan) {
        this.tanggal_penjualan = tanggal_penjualan;
    }
}

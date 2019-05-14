package Models;

import com.google.gson.annotations.SerializedName;

public class pengadaan {
    @SerializedName("id")
    Integer id_pengadaan;
    @SerializedName("id_supplier")
    Integer id_supplier;
    @SerializedName("id_cabang")
    Integer id_cabang;
    @SerializedName("tanggal_pengadaan")
    String tanggal_pengadaan;
    @SerializedName("total_harga_pengadaan")
    String total_harga_pengadaan;
    @SerializedName("created_at")
    String created_at;

    String nama_supplier;
    String no_telp_supplier;
    String alamat_supplier;

    String nama_cabang;
    String no_telp_cabang;
    String alamat_cabang;

    public pengadaan(Integer id_supplier, Integer id_cabang, String tanggal_pengadaan, String total_harga_pengadaan, String created_at) {
        this.id_supplier = id_supplier;
        this.id_cabang = id_cabang;
        this.tanggal_pengadaan = tanggal_pengadaan;
        this.total_harga_pengadaan = total_harga_pengadaan;
        this.created_at = created_at;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getNo_telp_supplier() {
        return no_telp_supplier;
    }

    public void setNo_telp_supplier(String no_telp_supplier) {
        this.no_telp_supplier = no_telp_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getNo_telp_cabang() {
        return no_telp_cabang;
    }

    public void setNo_telp_cabang(String no_telp_cabang) {
        this.no_telp_cabang = no_telp_cabang;
    }

    public String getAlamat_cabang() {
        return alamat_cabang;
    }

    public void setAlamat_cabang(String alamat_cabang) {
        this.alamat_cabang = alamat_cabang;
    }

    public Integer getId_pengadaan() {
        return id_pengadaan;
    }

    public void setId_pengadaan(Integer id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public Integer getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Integer id_supplier) {
        this.id_supplier = id_supplier;
    }

    public Integer getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(Integer id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getTanggal_pengadaan() {
        return tanggal_pengadaan;
    }

    public void setTanggal_pengadaan(String tanggal_pengadaan) {
        this.tanggal_pengadaan = tanggal_pengadaan;
    }

    public String getTotal_harga_pengadaan() {
        return total_harga_pengadaan;
    }

    public void setTotal_harga_pengadaan(String total_harga_pengadaan) {
        this.total_harga_pengadaan = total_harga_pengadaan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

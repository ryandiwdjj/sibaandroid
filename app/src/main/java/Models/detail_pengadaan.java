package Models;

import com.google.gson.annotations.SerializedName;

public class detail_pengadaan {
    @SerializedName("id")
    Integer id_detail_pengadaan;
    @SerializedName("id_trans_pengadaan")
    Integer id_trans_pengadaan;
    @SerializedName("id_sparepart")
    Integer id_sparepart;
    @SerializedName("jumlah_pengadaan")
    Integer jumlah_pengadaan;
    @SerializedName("subtotal_pengadaan")
    Float subtotal_pengadaan;

    @SerializedName("kode_sparepart")
    String kode_sparepart;
    @SerializedName("nama_sparepart")
    String nama_sparepart;
    @SerializedName("merk_sparepart")
    String merk_sparepart;
    @SerializedName("tipe_sparepart")
    String tipe_sparepart;
    @SerializedName("gambar_sparepart")
    String gambar_sparepart;
    @SerializedName("jumlah_stok_sparepart")
    Integer jumlah_stok_sparepart;
    @SerializedName("harga_beli_sparepart")
    Float harga_beli_sparepart;
    @SerializedName("harga_jual_sparepart")
    Float harga_jual_sparepart;
    @SerializedName("jumlah_minimal")
    Integer jumlah_minimal;

    public detail_pengadaan(Integer id_detail_pengadaan, Integer id_trans_pengadaan, Integer id_sparepart, Integer jumlah_pengadaan, Float subtotal_pengadaan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
        this.id_trans_pengadaan = id_trans_pengadaan;
        this.id_sparepart = id_sparepart;
        this.jumlah_pengadaan = jumlah_pengadaan;
        this.subtotal_pengadaan = subtotal_pengadaan;

    }



    public detail_pengadaan(Integer id_sparepart, Integer jumlah_pengadaan, String nama_sparepart, String gambar_sparepart, Float harga_beli_sparepart) {
        this.id_sparepart = id_sparepart;
        this.jumlah_pengadaan = jumlah_pengadaan;
        this.nama_sparepart = nama_sparepart;
        this.gambar_sparepart = gambar_sparepart;
        this.harga_beli_sparepart = harga_beli_sparepart;
    }

    public Integer getId_detail_pengadaan() {
        return id_detail_pengadaan;
    }

    public void setId_detail_pengadaan(Integer id_detail_pengadaan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
    }

    public Integer getId_trans_pengadaan() {
        return id_trans_pengadaan;
    }

    public void setId_trans_pengadaan(Integer id_trans_pengadaan) {
        this.id_trans_pengadaan = id_trans_pengadaan;
    }

    public Integer getId_sparepart() {
        return id_sparepart;
    }

    public void setId_sparepart(Integer id_sparepart) {
        this.id_sparepart = id_sparepart;
    }

    public Integer getJumlah_pengadaan() {
        return jumlah_pengadaan;
    }

    public void setJumlah_pengadaan(Integer jumlah_pengadaan) {
        this.jumlah_pengadaan = jumlah_pengadaan;
    }

    public Float getSubtotal_pengadaan() {
        return subtotal_pengadaan;
    }

    public void setSubtotal_pengadaan(Float subtotal_pengadaan) {
        this.subtotal_pengadaan = subtotal_pengadaan;
    }

    public String getNama_sparepart() {
        return nama_sparepart;
    }

    public String getKode_sparepart() {
        return kode_sparepart;
    }

    public void setKode_sparepart(String kode_sparepart) {
        this.kode_sparepart = kode_sparepart;
    }

    public void setNama_sparepart(String nama_sparepart) {
        this.nama_sparepart = nama_sparepart;
    }

    public String getMerk_sparepart() {
        return merk_sparepart;
    }

    public void setMerk_sparepart(String merk_sparepart) {
        this.merk_sparepart = merk_sparepart;
    }

    public String getTipe_sparepart() {
        return tipe_sparepart;
    }

    public void setTipe_sparepart(String tipe_sparepart) {
        this.tipe_sparepart = tipe_sparepart;
    }

    public String getGambar_sparepart() {
        return gambar_sparepart;
    }

    public void setGambar_sparepart(String gambar_sparepart) {
        this.gambar_sparepart = gambar_sparepart;
    }

    public Integer getJumlah_stok_sparepart() {
        return jumlah_stok_sparepart;
    }

    public void setJumlah_stok_sparepart(Integer jumlah_stok_sparepart) {
        this.jumlah_stok_sparepart = jumlah_stok_sparepart;
    }

    public Float getHarga_beli_sparepart() {
        return harga_beli_sparepart;
    }

    public void setHarga_beli_sparepart(Float harga_beli_sparepart) {
        this.harga_beli_sparepart = harga_beli_sparepart;
    }

    public Float getHarga_jual_sparepart() {
        return harga_jual_sparepart;
    }

    public void setHarga_jual_sparepart(Float harga_jual_sparepart) {
        this.harga_jual_sparepart = harga_jual_sparepart;
    }

    public Integer getJumlah_minimal() {
        return jumlah_minimal;
    }

    public void setJumlah_minimal(Integer jumlah_minimal) {
        this.jumlah_minimal = jumlah_minimal;
    }
}

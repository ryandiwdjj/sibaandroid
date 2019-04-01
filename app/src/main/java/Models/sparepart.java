package Models;

public class sparepart {
    Integer id_sparepart;
    String kode_sparepart;
    String nama_sparepart;
    String merk_sparepart;
    String tipe_sparepart;
    String gambar_sparepart;
    Integer jumlah_stok_sparepart;
    Float harga_beli_sparepart;
    Float harga_jual_sparepart;
    Integer jumlah_minimal;

    public sparepart(Integer id_sparepart, String kode_sparepart, String nama_sparepart, String merk_sparepart, String tipe_sparepart, String gambar_sparepart, Integer jumlah_stok_sparepart, Float harga_beli_sparepart, Float harga_jual_sparepart, Integer jumlah_minimal) {
        this.id_sparepart = id_sparepart;
        this.kode_sparepart = kode_sparepart;
        this.nama_sparepart = nama_sparepart;
        this.merk_sparepart = merk_sparepart;
        this.tipe_sparepart = tipe_sparepart;
        this.gambar_sparepart = gambar_sparepart;
        this.jumlah_stok_sparepart = jumlah_stok_sparepart;
        this.harga_beli_sparepart = harga_beli_sparepart;
        this.harga_jual_sparepart = harga_jual_sparepart;
        this.jumlah_minimal = jumlah_minimal;
    }

    public Integer getId_sparepart() {
        return id_sparepart;
    }

    public void setId_sparepart(Integer id_sparepart) {
        this.id_sparepart = id_sparepart;
    }

    public String getKode_sparepart() {
        return kode_sparepart;
    }

    public void setKode_sparepart(String kode_sparepart) {
        this.kode_sparepart = kode_sparepart;
    }

    public String getNama_sparepart() {
        return nama_sparepart;
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

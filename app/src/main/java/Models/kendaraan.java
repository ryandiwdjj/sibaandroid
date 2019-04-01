package Models;

public class kendaraan {
    Integer id_kendaraan;
    String merk_kendaraan;
    String tipe_kendaraan;

    public kendaraan(Integer id_kendaraan, String merk_kendaraan, String tipe_kendaraan) {
        this.id_kendaraan = id_kendaraan;
        this.merk_kendaraan = merk_kendaraan;
        this.tipe_kendaraan = tipe_kendaraan;
    }

    public Integer getId_kendaraan() {
        return id_kendaraan;
    }

    public void setId_kendaraan(Integer id_kendaraan) {
        this.id_kendaraan = id_kendaraan;
    }

    public String getMerk_kendaraan() {
        return merk_kendaraan;
    }

    public void setMerk_kendaraan(String merk_kendaraan) {
        this.merk_kendaraan = merk_kendaraan;
    }

    public String getTipe_kendaraan() {
        return tipe_kendaraan;
    }

    public void setTipe_kendaraan(String tipe_kendaraan) {
        this.tipe_kendaraan = tipe_kendaraan;
    }
}

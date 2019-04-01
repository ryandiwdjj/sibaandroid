package Models;

public class cabang {
    Integer id_cabang;
    String nama_cabang;
    String alamat_cabang;
    String no_telp_cabang;

    public cabang(Integer id_cabang, String nama_cabang, String alamat_cabang, String no_telp_cabang) {
        this.id_cabang = id_cabang;
        this.nama_cabang = nama_cabang;
        this.alamat_cabang = alamat_cabang;
        this.no_telp_cabang = no_telp_cabang;
    }

    public Integer getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(Integer id_cabang) {
        this.id_cabang = id_cabang;
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
}

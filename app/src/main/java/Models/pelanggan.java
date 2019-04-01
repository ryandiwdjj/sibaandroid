package Models;

public class pelanggan {
    Integer id_pelanggan;
    String nama_pelanggan;
    String alamat_pelanggan;
    String no_telp_pelanggan;

    public pelanggan(Integer id_pelanggan, String nama_pelanggan, String alamat_pelanggan, String no_telp_pelanggan) {
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.alamat_pelanggan = alamat_pelanggan;
        this.no_telp_pelanggan = no_telp_pelanggan;
    }

    public Integer getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(Integer id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
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
}

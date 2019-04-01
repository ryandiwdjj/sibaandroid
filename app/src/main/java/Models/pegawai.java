package Models;

public class pegawai {
    Integer id_pegawai;
    Integer id_role;
    String nama_pegawai;
    String alamat_pegawai;
    String no_telp_pegawai;
    Float gaji_perminggu;
    String password_pegawai;
    Integer id_cabang;

    public pegawai(Integer id_pegawai, Integer id_role, String nama_pegawai, String alamat_pegawai, String no_telp_pegawai, Float gaji_perminggu, String password_pegawai, Integer id_cabang) {
        this.id_pegawai = id_pegawai;
        this.id_role = id_role;
        this.nama_pegawai = nama_pegawai;
        this.alamat_pegawai = alamat_pegawai;
        this.no_telp_pegawai = no_telp_pegawai;
        this.gaji_perminggu = gaji_perminggu;
        this.password_pegawai = password_pegawai;
        this.id_cabang = id_cabang;
    }

    public Integer getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(Integer id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public Integer getId_role() {
        return id_role;
    }

    public void setId_role(Integer id_role) {
        this.id_role = id_role;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public String getNo_telp_pegawai() {
        return no_telp_pegawai;
    }

    public void setNo_telp_pegawai(String no_telp_pegawai) {
        this.no_telp_pegawai = no_telp_pegawai;
    }

    public Float getGaji_perminggu() {
        return gaji_perminggu;
    }

    public void setGaji_perminggu(Float gaji_perminggu) {
        this.gaji_perminggu = gaji_perminggu;
    }

    public String getPassword_pegawai() {
        return password_pegawai;
    }

    public void setPassword_pegawai(String password_pegawai) {
        this.password_pegawai = password_pegawai;
    }

    public Integer getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(Integer id_cabang) {
        this.id_cabang = id_cabang;
    }
}

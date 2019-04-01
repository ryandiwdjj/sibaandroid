package Models;

public class role {
    Integer id_role;
    String nama_role;

    public role(Integer id_role, String nama_role) {
        this.id_role = id_role;
        this.nama_role = nama_role;
    }

    public Integer getId_role() {
        return id_role;
    }

    public void setId_role(Integer id_role) {
        this.id_role = id_role;
    }

    public String getNama_role() {
        return nama_role;
    }

    public void setNama_role(String nama_role) {
        this.nama_role = nama_role;
    }
}

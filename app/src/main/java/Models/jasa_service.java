package Models;

public class jasa_service {
    Integer id_jasa;
    String nama_jasa;
    Float harga_jasa;

    public jasa_service(Integer id_jasa, String nama_jasa, Float harga_jasa) {
        this.id_jasa = id_jasa;
        this.nama_jasa = nama_jasa;
        this.harga_jasa = harga_jasa;
    }

    public Integer getId_jasa() {
        return id_jasa;
    }

    public void setId_jasa(Integer id_jasa) {
        this.id_jasa = id_jasa;
    }

    public String getNama_jasa() {
        return nama_jasa;
    }

    public void setNama_jasa(String nama_jasa) {
        this.nama_jasa = nama_jasa;
    }

    public Float getHarga_jasa() {
        return harga_jasa;
    }

    public void setHarga_jasa(Float harga_jasa) {
        this.harga_jasa = harga_jasa;
    }
}

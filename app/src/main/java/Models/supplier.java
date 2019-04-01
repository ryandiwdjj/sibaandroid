package Models;

public class supplier {
    private Integer id_supplier;
    private String nama_supplier;
    private String sales_supplier;
    private String no_telp_supplier;
    private String alamat_supplier;

    public supplier() {

    }
    public supplier(Integer id_supplier, String nama_supplier, String sales_supplier, String no_telp_supplier, String alamat_supplier) {
        this.id_supplier = id_supplier;
        this.nama_supplier = nama_supplier;
        this.sales_supplier = sales_supplier;
        this.no_telp_supplier = no_telp_supplier;
        this.alamat_supplier = alamat_supplier;
    }

    public Integer getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Integer id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getSales_supplier() {
        return sales_supplier;
    }

    public void setSales_supplier(String sales_supplier) {
        this.sales_supplier = sales_supplier;
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
}

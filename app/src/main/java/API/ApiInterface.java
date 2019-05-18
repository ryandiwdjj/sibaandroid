package API;

import java.util.List;

import Models.cabang;
import Models.detail_pengadaan;
import Models.pegawai;
import Models.pelanggan;
import Models.pengadaan;
import Models.penjualan;
import Models.sparepart;
import Models.supplier;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD SUPPLIER
    @GET("mobile/supplier/") //200
    Call<List<supplier>> getSupplier();

    @POST("mobile/supplier/store") //204
    @FormUrlEncoded
    Call<supplier> addSupplier(@Field("nama_supplier")String nama_supplier,
                                   @Field("sales_supplier") String sales_supplier,
                                   @Field("no_telp_supplier") String no_telp_supplier,
                                   @Field("alamat_supplier") String alamat_supplier);

    @PUT("mobile/supplier/update/{id}") //200
    Call<supplier> updateSupplier(@Path("id") int id, @Body supplier supplier);

    @DELETE("supplier/{id}") //204
    Call<supplier> deleteSupplier(@Path("id") int id);

    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD CABANG
    @GET("cabang/all") //200
    Call<List<cabang>> getCabang();


    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD SPAREPART
    @GET("mobile/sparepart/")
    Call<List<sparepart>> getSparepart();

    @Multipart
    @POST("mobile/sparepart/store")
    Call<sparepart> addSparepart(      @Part("kode_sparepart") RequestBody kode_sparepart,
                                       @Part("nama_sparepart") RequestBody nama_sparepart,
                                       @Part("merk_sparepart") RequestBody merk_sparepart,
                                       @Part("tipe_sparepart") RequestBody tipe_sparepart,
                                       @Part MultipartBody.Part picture,
                                       @Part("jumlah_stok_sparepart") RequestBody jumlah_stok_sparepart,
                                       @Part("harga_beli_sparepart") RequestBody harga_beli_sparepart,
                                       @Part("harga_jual_sparepart") RequestBody harga_jual_sparepart,
                                       @Part("jumlah_minimal") RequestBody jumlah_minimal);

    @Multipart
    @POST("mobile/sparepart/update/{id}")
    Call<sparepart> updateSparepart(@Path("id") Integer id,
                                    @Part("kode_sparepart") RequestBody kode_sparepart,
                                    @Part("nama_sparepart") RequestBody nama_sparepart,
                                    @Part("merk_sparepart") RequestBody merk_sparepart,
                                    @Part("tipe_sparepart") RequestBody tipe_sparepart,
                                    @Part MultipartBody.Part picture,
                                    @Part("jumlah_stok_sparepart") RequestBody jumlah_stok_sparepart,
                                    @Part("harga_beli_sparepart") RequestBody harga_beli_sparepart,
                                    @Part("harga_jual_sparepart") RequestBody harga_jual_sparepart,
                                    @Part("jumlah_minimal") RequestBody jumlah_minimal);

    @Multipart
    @POST("mobile/sparepart/update_nonimg/{id}")
    Call<sparepart> updateSparepartNonImage (@Path("id") Integer id,
                                    @Part("kode_sparepart") RequestBody kode_sparepart,
                                    @Part("nama_sparepart") RequestBody nama_sparepart,
                                    @Part("merk_sparepart") RequestBody merk_sparepart,
                                    @Part("tipe_sparepart") RequestBody tipe_sparepart,
                                    @Part("jumlah_stok_sparepart") RequestBody jumlah_stok_sparepart,
                                    @Part("harga_beli_sparepart") RequestBody harga_beli_sparepart,
                                    @Part("harga_jual_sparepart") RequestBody harga_jual_sparepart,
                                    @Part("jumlah_minimal") RequestBody jumlah_minimal);

    @DELETE("sparepart/{id}")
    Call<sparepart> deleteSparepart(@Path("id") int id);

    @GET("mobile/sparepart/cek_stok")
    Call<List<sparepart>> cekStokSparepart();

    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD PEGAWAI

    @POST("pegawai/login")
    @FormUrlEncoded
    Call<pegawai> loginPegawai(@Field("no_telp_pegawai") String no_telp_pegawai
                        ,@Field("password_pegawai") String password_pegawai);

    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD PELANGGAN

    @POST("mobile/pelanggan/login")
    @FormUrlEncoded
    Call<pelanggan> loginPelanggan(@Field("no_telp_pelanggan") String no_telp_pelanggan);

    ////////////////////////////////////////////////////////////////////////////////////////////////TRANSACTION

    @GET("mobile/trans_penjualan/")
    Call<List<penjualan>> getPenjualan();

    @GET("mobile/trans_penjualan/getByid/{id}")
    Call<List<penjualan>> getPenjualanById(@Path("id") Integer id);

    ////////////////////////////////////////////////////////////////////////////////////////////////PENGADAAN

    @GET("mobile/trans_pengadaan/")
    Call<List<pengadaan>> getPengadaan();

    @FormUrlEncoded
    @POST("mobile/trans_pengadaan/store")
    Call<pengadaan> addPengadaan (
            @Field("id_supplier") Integer id_supplier
            , @Field("id_cabang") Integer id_cabang
            , @Field("tanggal_pengadaan") String tanggal_pengadaan);

    @FormUrlEncoded
    @POST("mobile/trans_pengadaan/detail/store")
    Call<detail_pengadaan> addDetailPengadaan (
            @Field("id_trans_pengadaan") Integer id_trans_pengadaan,
            @Field("id_sparepart") Integer id_sparepart
            , @Field("jumlah_pengadaan") Integer jumlah_pengadaan);
}

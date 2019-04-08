package API;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.List;

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
    @GET("suppliers") //200
    Call<List<supplier>> getSupplier();

    @GET("supplier/showByName/{nama_supplier}")
    Call<List<supplier>> getSupplierByName(@Path("nama_supplier") String name);

    @POST("supplier/store") //204
    @FormUrlEncoded
    //Call<supplier> addSupplier(@Body supplier supplier);
    Call<supplier> addSupplier(@Field("nama_supplier")String nama_supplier,
                                   @Field("sales_supplier") String sales_supplier,
                                   @Field("no_telp_supplier") String no_telp_supplier,
                                   @Field("alamat_supplier") String alamat_supplier);

    @PUT("supplier/update/{id}") //200
    Call<supplier> updateSupplier(@Path("id") int id, @Body supplier supplier);

    @DELETE("supplier/{id}") //204
    Call<supplier> deleteSupplier(@Path("id") int id);

    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD SPAREPART
    @GET("spareparts")
    Call<List<sparepart>> getSparepart();

//    @POST("sparepart/store")
//    @FormUrlEncoded
//    Call<sparepart> addSparepart(@Field("kode_sparepart")String kode_sparepart,
//                                 @Field("nama_sparepart") String nama_sparepart,
//                                 @Field("merk_sparepart") String merk_sparepart,
//                                 @Field("tipe_sparepart") String tipe_sparepart,
//                                 @Field("gambar_sparepart") String gambar_sparepart,
//                                 @Field("jumlah_stok_sparepart") Integer jumlah_stok_sparepart,
//                                 @Field("harga_beli_sparepart") Float harga_beli_sparepart,
//                                 @Field("harga_jual_sparepart") Float harga_jual_sparepart,
//                                 @Field("jumlah_minimal") Integer jumlah_minimal);

    @Multipart
    @POST("sparepart/storeWO")
    Call<sparepart> addSparepart(//@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                       //@Part MultipartBody.Part picture,
                                       @Part("kode_sparepart")RequestBody kode_sparepart,
                                       @Part("nama_sparepart") RequestBody nama_sparepart,
                                       @Part("merk_sparepart") RequestBody merk_sparepart,
                                       @Part("tipe_sparepart") RequestBody tipe_sparepart,
                                       @Part("gambar_sparepart") RequestBody gambar_sparepart,
                                       @Part("jumlah_stok_sparepart") RequestBody jumlah_stok_sparepart,
                                       @Part("harga_beli_sparepart") RequestBody harga_beli_sparepart,
                                       @Part("harga_jual_sparepart") RequestBody harga_jual_sparepart,
                                       @Part("jumlah_minimal") RequestBody jumlah_minimal);

    @Multipart
    @POST("sparepart/store")
    Call<sparepart> addSparepartWImage(//@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
                                       @Part MultipartBody.Part picture,
                                       @Part("kode_sparepart")RequestBody kode_sparepart,
                                       @Part("nama_sparepart") RequestBody nama_sparepart,
                                       @Part("merk_sparepart") RequestBody merk_sparepart,
                                       @Part("tipe_sparepart") RequestBody tipe_sparepart,
                                       @Part("jumlah_stok_sparepart") RequestBody jumlah_stok_sparepart,
                                       @Part("harga_beli_sparepart") RequestBody harga_beli_sparepart,
                                       @Part("harga_jual_sparepart") RequestBody harga_jual_sparepart,
                                       @Part("jumlah_minimal") RequestBody jumlah_minimal);
     //desc itu nama field nya

//    @POST("sparepart/store")
//    @FormUrlEncoded
//    Call<sparepart> addSparepart(@Field("kode_sparepart")String kode_sparepart,
//                                 @Field("nama_sparepart") String nama_sparepart,
//                                 @Field("merk_sparepart") String merk_sparepart,
//                                 @Field("tipe_sparepart") String tipe_sparepart,
//                                 @Field("jumlah_stok_sparepart") Integer jumlah_stok_sparepart,
//                                 @Field("harga_beli_sparepart") Float harga_beli_sparepart,
//                                 @Field("harga_jual_sparepart") Float harga_jual_sparepart,
//                                 @Field("jumlah_minimal") Integer jumlah_minimal);

    @PUT("sparepart/update/{id}")
    Call<sparepart> updateSparepart(@Path("id") int id, @Body sparepart sparepart);

    @DELETE("sparepart/{id}")
    Call<sparepart> deleteSparepart(@Path("id") int id);
}

package API;

import java.util.List;

import Models.sparepart;
import Models.supplier;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD SUPPLIER
    @GET("suppliers")
    Call<List<supplier>> getSupplier();

    @POST("supplier/store")
    @FormUrlEncoded
    //Call<supplier> addSupplier(@Body supplier supplier);
    Call<supplier> addSupplier(@Field("nama_supplier")String nama_supplier,
                                   @Field("sales_supplier") String sales_supplier,
                                   @Field("no_telp_supplier") String no_telp_supplier,
                                   @Field("alamat_supplier") String alamat_supplier);

    @PUT("supplier/update/{id}")
    Call<supplier> updateSupplier(@Path("id") int id, @Body supplier supplier);

    @DELETE("supplier/{id}")
    Call<supplier> deleteSupplier(@Path("id") int id);

    ////////////////////////////////////////////////////////////////////////////////////////////////CRUD SPAREPART
    @GET("spareparts")
    Call<List<sparepart>> getSparepart();

//    @POST("sparepart/store")
//    @FormUrlEncoded
//    Call<sparepart> addSparepart(@Field("nama_supplier")String nama_supplier,
//                               @Field("sales_supplier") String sales_supplier,
//                               @Field("no_telp_supplier") String no_telp_supplier,
//                               @Field("alamat_supplier") String alamat_supplier);

    @PUT("sparepart/{id}")
    Call<supplier> updateSparepart(@Path("id") int id, @Body sparepart sparepart);

    @DELETE("sparepart/{id}")
    Call<supplier> deleteSparepart(@Path("id") int id);
}

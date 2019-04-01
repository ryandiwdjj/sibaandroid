package Remote;

import java.util.List;

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

public interface UserService {
    @GET("supplier/")
    Call<List<supplier>> getSuppliers();

    @POST("supplier/store")
    @FormUrlEncoded
    Call<supplier> addSupplier(@Body supplier supplier);
//    Call<supplier> addSupplier(@Field("nama_supplier")String nama_supplier,
//                               @Field("sales_supplier") String sales_supplier,
//                               @Field("no_telp_supplier") String no_telp_supplier,
//                               @Field("alamat_supplier") String alamat_supplier);

    @PUT("supplier/{id}")
    Call<supplier> updateSupplier(@Path("id") int id, @Body supplier supplier);

    @DELETE("supplier/{id}")
    Call<supplier> deleteSupplier(@Path("id") int id);
}

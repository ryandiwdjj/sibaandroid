package API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String URL = "http://192.168.1.7/"; //host laptop saat konek ke wifi
    private static final String API_URL = URL + "siba-web/public/api/"; //host laptop saat konek ke wifi
    private static final String IMAGE_URL = URL + "siba-web/public/images/sparepart/";

    private static Retrofit retrofit;

    public static String getImageUrl (){
        return IMAGE_URL;
    }

    public static String getUrl() {
        return URL;
    }

    public static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

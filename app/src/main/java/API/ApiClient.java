package API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String URL = "http://192.168.1.6:8000/"; //host laptop saat konek ke wifi
    private static final String BASE_URL = "http://192.168.1.6:8000/api/"; //host laptop saat konek ke wifi
    private static Retrofit retrofit;

    public static String getUrl() {
        return URL;
    }

    public static Retrofit getApiClient(){
        if (retrofit==null){
            System.out.println("masuk api client");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

package Remote;

public class APIUtils {
    private APIUtils() {

    }

    public static final String API_URL = "192.168.1.29:8000/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}

package multibashisample.navneet.in.multibashisample.service;

import  multibashisample.navneet.in.multibashisample.model.Items;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sree on 16-12-2016.
 */
public interface RequestInterface {
    @GET("getLessonData.php")
    Call<Items> getPOJO();
}

package multibashisample.navneet.in.multibashisample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import multibashisample.navneet.in.multibashisample.adapter.ViewPagerAdapter;
import  multibashisample.navneet.in.multibashisample.model.ItemResponse;
import  multibashisample.navneet.in.multibashisample.model.Items;
import  multibashisample.navneet.in.multibashisample.service.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<ItemResponse> data;
    private ArrayList<JSONObject> jsARR=new ArrayList<>();

    private String correctString;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private Parcelable recyclerViewState;
    private Gson gson;
    private String json;

    private ViewPagerAdapter pagerAdapter;
    private static String URL="http://www.akshaycrt2k.com/";
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences prefs = getSharedPreferences("sampledata", MODE_PRIVATE);
        String restoredText = prefs.getString("correct", null);
        if (restoredText != null) {
            correctString = prefs.getString("correct", "No name defined");
        }
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (correctString!=null) {
                        if (result.contains(correctString.toLowerCase())) {
//                            Toast.makeText(MainActivity.this, result.get(0) + "\n matched", Toast.LENGTH_SHORT).show();
                            new BottomDialog.Builder(this)
                                    .setTitle("Congrats!")
                                    .setContent(result.get(0)+" complete match ")
                                    .show();
                        } else {
                            String cstring=correctString.toLowerCase();
                            int length=cstring.length();
                            int count=0;
                            float percentage;
                            for (int i=1;i<length;i++){
                                char ns=cstring.charAt(i-1);
                                char bs=cstring.charAt(i);
                                String substring;
                                StringBuilder sb = new StringBuilder();
                                sb.append(ns);
                                sb.append(bs);
                                substring=sb.toString();
                                if (result.get(0).contains(substring)){
                                    Toast.makeText(MainActivity.this, substring+" present", Toast.LENGTH_SHORT).show();
                                    count++;
                                }
                            }
                            percentage=(count*100.0f)/length;
//                            Toast.makeText(MainActivity.this, result.get(0)+" matched "+percentage+" %", Toast.LENGTH_SHORT).show();
                            if (percentage>20.0) {
                                new BottomDialog.Builder(this)
                                        .setTitle("Good, can be better")
                                        .setContent(result.get(0) + " matched " + percentage + " %")
                                        .show();
                            } else {
                                new BottomDialog.Builder(this)
                                        .setTitle("Sorry, try again")
                                        .setContent(result.get(0) + " matched " + percentage + " %")
                                        .show();
                            }
                        }
                    }
                }
                break;
            }

        }
    }



    private void initViews(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
            loadJSON();
    }



    private void loadJSON(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<Items> call1=request.getPOJO();
        call1.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if(response.isSuccess())

                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();

                Items items=response.body();
                data=new ArrayList<>(items.getLessonData());
                pagerAdapter=new ViewPagerAdapter(MainActivity.this,data,data.size());
                viewPager.setAdapter(pagerAdapter);

            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }

        });

    }

}

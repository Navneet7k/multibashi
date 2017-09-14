package multibashisample.navneet.in.multibashisample.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import multibashisample.navneet.in.multibashisample.R;
import multibashisample.navneet.in.multibashisample.model.ItemResponse;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sree on 14-09-2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    private ArrayList<ItemResponse> records;
    LayoutInflater inflater;
    private int numberofRecords;
    MediaPlayer mediaplayer;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public ViewPagerAdapter(Context context, ArrayList<ItemResponse> records,
                            int numberofRecords) {
        this.context = context;
        this.records = records;
        this.numberofRecords = numberofRecords;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.card_row, container,
                false);

        TextView tv_name,tv_version,tv_api_level,city,district,state;
        ImageView play,speech;
        final String audioUrl=records.get(position).getAudioUrl();
        final String type=records.get(position).getType();
        final String pronounciation=records.get(position).getPronunciation();



        tv_name = (TextView)itemView.findViewById(R.id.tv_name);
        tv_version = (TextView)itemView.findViewById(R.id.tv_version);
        tv_api_level=(TextView)itemView.findViewById(R.id.tv_api_level);
        city=(TextView)itemView.findViewById(R.id.city);
        district=(TextView)itemView.findViewById(R.id.district);

        play=(ImageView) itemView.findViewById(R.id.play_btn);
        speech=(ImageView) itemView.findViewById(R.id.speech);


        tv_name.setText(records.get(position).getConceptName());
        tv_version.setText(records.get(position).getTargetScript());
        tv_api_level.setText(records.get(position).getType());
        city.setText(records.get(position).getPronunciation());
        district.setText(records.get(position).getAudioUrl());

        if (type.equals("question")||type.equals("quiz")){
            speech.setVisibility(View.VISIBLE);
        }

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences("sampledata", MODE_PRIVATE).edit();
                editor.putString("correct", pronounciation);
                editor.apply();
            promptSpeechInput(pronounciation);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaplayer = new MediaPlayer();
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaplayer.setDataSource(audioUrl);
                    mediaplayer.prepare();


                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                mediaplayer.start();
            }
        });

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    private void promptSpeechInput(String pronounciation) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say something");
        try {
            ((Activity) context).startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            intent.putExtra("correct", pronounciation);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(context,
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((CardView) object);
    }

    @Override
    public int getCount() {
        return numberofRecords;
    }
}

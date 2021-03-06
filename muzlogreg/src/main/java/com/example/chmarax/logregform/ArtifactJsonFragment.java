package com.example.chmarax.logregform;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.shivtechs.mediaplayemodule.AudioPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.shivtechs.mediaplayemodule.AudioPlayer.MODE_PATH;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtifactJsonFragment extends Fragment implements TextToSpeech.OnInitListener {
    private String k;
    private TextView et;
    private View v;
    private String mMessage,mMessageartifact;
    public String[] images = new String[100];
    private String stringaudio;
    private int len;
    Context context1,context2;
    Adapter adapter;
    List<Model> models;
    ViewPager viewPager;
    Activity activity1;
    FloatingActionButton actionButton;
    TextToSpeech tts;
    String desc;
    String st = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());




    public ArtifactJsonFragment() {

        // Required empty public constructor
    }

    public ArtifactJsonFragment(String s) {
        this.k = s;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_artifactjson, container,  false);

        et = v.findViewById(R.id.textView2);

        activity1 =getActivity();
        context2=getContext();
         tts = new TextToSpeech(getContext(), this);

        new Thread(new Runnable() {
            public void run() {
                try {
                    postRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        ImageView icon = new ImageView(getContext()); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.media) );

         actionButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(icon)
                .setPosition(FloatingActionButton.POSITION_RIGHT_CENTER)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
        // repeat many times:
        ImageView itemIcon = new ImageView(getContext());
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.audio)  );
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        ImageView itemIcon1 = new ImageView(getContext());
        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.texttospeech)  );
        SubActionButton button2 = itemBuilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(getContext());
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.video)  );
        SubActionButton button3 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(getContext());
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.imag)  );
        SubActionButton button4 = itemBuilder.setContentView(itemIcon3).build();

        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)

                .attachTo(actionButton)
                .build();


        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vi) {
                Toast.makeText(getContext(),"Audio playing",Toast.LENGTH_SHORT).show();

                String FILE_PROVIDER_NAME = "com.shivtechs.provider" ;
                //For playing the audio file from the given path
                stringaudio = "https://mangoess.000webhostapp.com/MangoFolder/Njandukalude%20Naattil%20Oridavela%20_%20Enthaavo%20Song%20Vid%20-%20128K%20MP3.mp3";
               AudioPlayer player2 = new AudioPlayer(getActivity(),FILE_PROVIDER_NAME,getActivity().isFinishing(),getActivity().getSupportFragmentManager(),stringaudio,MODE_PATH);
                //For playing the audio file from the android resource
           //    AudioPlayer player = new AudioPlayer(getActivity(),getActivity().isFinishing(),getActivity().getSupportFragmentManager(),R.raw.guitar,MODE_RESOURCE);


                actionMenu.close(true);

            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Text to Speech",Toast.LENGTH_SHORT).show();
                Log.e("languages",tts.getAvailableLanguages().toString());
                tts.setPitch(1f);
                tts.setSpeechRate(1f);
                tts.speak(st.substring(10,1250), TextToSpeech.QUEUE_FLUSH, null, null);
                actionMenu.close(true);
            }





        });
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Video Files",Toast.LENGTH_SHORT).show();
                actionMenu.close(true);
            }





        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Video Files",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    public void run() {
                        getMoreImagePostMethod();

                    }
                }).start();
                actionMenu.close(true);
            }
        });




        return v;
    }

    public void postRequest() throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://muzirisdemot8.herokuapp.com/ipa/getartifactfromqr";//http://muzirisdemot8.herokuapp.com/ipa/getartifactfromqr";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("qrcode", "100");
            //  postdata.put("password", "12345");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mMessage = response.body().string();
                Log.e("xxxxx", mMessage.substring(0, 100));
                try {
                    jsonparsing(mMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });
    }

    public void jsonparsing(String message) throws JSONException {

       JSONObject obj = new JSONObject(message);
       String name = obj.getString("mainimg");
       desc = obj.getString("englishtext");
       Log.e("msggg",name);

       final TextView description =  v.findViewById(R.id.textView2);

        activity1.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setText(description,desc);
            }
        });




//        Glide.with(getContext())
//                .load("https://homepages.cae.wisc.edu/~ece533/images/barbara.bmp")
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(imageView);

      //  Picasso.get().load("http://placehold.it/120x120&text=image4").into(i);
       // Picasso.with(getContext()).load(imgUrl).fit().into(contentImageView);
//        try {
//            ImageView i = (ImageView) v.findViewById(R.id.imageView);
//            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("https://homepages.cae.wisc.edu/~ece533/images/barbara.bmp").getContent());
//            i.setImageBitmap(bitmap);
//            i.setMaxHeight(25);
//            Log.e("imageerror","dsadsadas");
//        } catch (IOException e) {
//            Log.e("imageerror",e.toString());
//            e.printStackTrace();
//        }
     }

    private void setText(final TextView text,final String value){

        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        byte[] decode = Base64.decode(value,Base64.DEFAULT);

        try {
            st = new String (decode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("msggg",e.toString());
            e.printStackTrace();
        }
        text.setText(st);

        Glide.with(context2)
                .load("https://mangoess.000webhostapp.com/MangoFolder/ser.jpg")//"https://files.000webhost.com/handler.php?action=download?action=download&path=%2FImages%2Fser.jpg
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
////

    }


    public void jsonparsing2(String message) throws JSONException {
        JSONArray jsonArr = new JSONArray(message);
        len = jsonArr.length();

        String name = jsonArr.getString(0);
        final String desc = jsonArr.getString(0);
        Log.e("msggg",name);

  //     final TextView description =  v.findViewById(R.id.textView2);


  //      setText(description,desc);

    }

    public void getMoreImagePostMethod(){
        context1 = getContext();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://muzirisdemot8.herokuapp.com/ipa/extraimageartefact";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("id", "1201");
            //  postdata.put("password", "12345");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mMessageartifact = response.body().string();
          Log.e("yyyyyy2", mMessageartifact.toString());
                try {

                    JSONArray jsonArr = new JSONArray(mMessageartifact);
                    len = jsonArr.length();
                    Log.e("yyyyyy",String.valueOf(len));

                    for(int i = 0 ;i<len;i++){
                        images[i] = jsonArr.getString(i);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showthesliderimage(images,len);

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        actionButton.detach();
        tts.shutdown();
    }

    public void showthesliderimage(String[] images, int lenth){

        Toast.makeText(getContext(), String.valueOf(lenth),Toast.LENGTH_LONG).show();
        models = new ArrayList<>();
        for(int i = 0; i< lenth; i++){
          //  String urls = "https://mangoess.000webhostapp.com/MangoFolder/"+images[i];
            models.add(new Model(images[i], "Poster", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
        }

//        Glide.with(context2)
//                .load("https://mangoess.000webhostapp.com/MangoFolder/ser.jpg")//"https://files.000webhost.com/handler.php?action=download?action=download&path=%2FImages%2Fser.jpg
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(imageView);
//
//        models.add(new Model(R.drawable.brochure, "Sticker", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side"));
//        models.add(new Model(R.drawable.poster, "Poster", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
//        models.add(new Model(R.drawable.namecard, "Namecard", "Business cards are cards bearing business information about a company or individual."));
        adapter = new Adapter(models, getContext(),v);
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);


    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("hi_IN");
            int result = tts.setLanguage(locale);


            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity(), "Language not supported", Toast.LENGTH_SHORT).show();
            } else {
                //Disable the button if any.
            }

        } else {
            Toast.makeText(getActivity(), "Init failed", Toast.LENGTH_SHORT).show();
        }
    }
}


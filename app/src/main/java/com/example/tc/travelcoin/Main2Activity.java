package com.example.tc.travelcoin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    private String flag = "";
    private TextView flagTitle, flagData;
    private ImageView flagImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");

        flagTitle = findViewById(R.id.flagTitle);
        flagData = findViewById(R.id.flagData);
        flagImg = findViewById(R.id.flagImg);


        switch (flag) {
            case "Samsung":
                flagTitle.setText("삼성 딜라이트");
                flagData.setText("삼성전자가 제시하는 미래의 가능성\n" +
                        "삼성전자와 함께 당신의 가능성을 발견하고,\n" +
                        "즐거움이 가득한 미래의 라이프스타일을 경험해보세요.\n" +
                        "\n" +
                        "삼성 딜라이트는 삼성전자의 최신 기술과 서비스를 통해\n" +
                        "미래를 체험하는 공간입니다. 당신만의 감성과 상상력을 발견하고\n" +
                        "미래의 주역으로 성장한 당신의 모습을 만나보세요.");
                flagImg.setImageResource(R.drawable.sdlight);
                break;
            case "SK":
                flagTitle.setText("SK T.um");
                flagData.setText("생활 전반이 혁신되는 새로운 세상,\n" +
                        "세상 모든 것이 연결되는 미래\n" +
                        "SK텔레콤은 여러분을 새로운 미래로 안내할 것입니다\n" +
                        "국내 최초 1세대 아날로그 이동전화 시대를 열고,\n" +
                        "세계 최초로 CDMA 기술과 HSDPA 기술을 상용화했습니다.\n" +
                        "국내 최초 LTE 상용화, 세계 최초 LTE-A 상용화 등\n" +
                        "세계 정보통신산업의 혁신을 이끌어왔습니다.\n" +
                        "\n" +
                        "이제 SK텔레콤은\n" +
                        "미래로의 연결을 시도합니다.\n" +
                        "혁신과 열정을 통해 새로운 가치와 미래 세상을 연결해가는\n" +
                        "SK텔레콤의 꿈을 T.um에서 직접 체험해보시기 바랍니다.");
                flagImg.setImageResource(R.drawable.sktttutm);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new VisitPlaceAsync().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class VisitPlaceAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\r\n  \"$class\": \"org.acme.ttcnetwork.VisitPlace\"," +
                            "\r\n  \"traveler\": \"resource:org.acme.ttcnetwork.Traveler#T1\"," +
                            "\r\n  \"place\": \"resource:org.acme.ttcnetwork.Place#" + flag
                            + "\"\r\n}");
            Request request = new Request.Builder()
                    .url(AppConstant.BASE_URL + AppConstant.VISIT)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.e("res", String.valueOf(response.body()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

package com.example.tc.travelcoin;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {

    private TextView textView;

    public TabFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);

        textView = view.findViewById(R.id.money);
        Button btn1 = view.findViewById(R.id.useBtn1);
        Button btn2 = view.findViewById(R.id.useBtn2);
        Button btn3 = view.findViewById(R.id.useBtn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UseMoneyAsync("1250").execute();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UseMoneyAsync("1250").execute();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UseMoneyAsync("3000").execute();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new BalanceAsync().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class UseMoneyAsync extends AsyncTask<Void, Void, String> {

        String money = "";

        public UseMoneyAsync(String money) {
            this.money = money;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new BalanceAsync().execute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\r\n  \"$class\": \"org.acme.ttcnetwork.UseMoney\"," +
                            "\r\n  \"money\": " + money + "," +
                            "\r\n  \"traveler\": \"resource:org.acme.ttcnetwork.Traveler#T1\"\r\n}");
            Request request = new Request.Builder()
                    .url(AppConstant.BASE_URL + AppConstant.USE)
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

    @SuppressLint("StaticFieldLeak")
    class BalanceAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(AppConstant.BASE_URL + AppConstant.TRAVEL + "/T1")
                    .get()
                    .build();

            Response response = null;


            try {
                response = client.newCall(request).execute();

                Gson gson = new Gson();
                BalanceData balanceData = gson.fromJson(response.body().string(), BalanceData.class);

                return balanceData.getMoney() + "";
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }

}

package com.bb.yzbbanban.test_retrofit;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://192.168.1.101:8080/Test_okhttp/";
    private static final String TAG = "MainActivity";
    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        onPostFile();
    }

    private void init() {
        Retrofit re = new Retrofit.Builder().baseUrl(BASE_URL).build();
        String dir = Environment.getExternalStorageDirectory() + "/Pictures";
        File f=new File(dir,"1487780231681.jpg");
        Log.i(TAG, "File getName: "+f.getName());
        if (!f.exists()) {
            Log.i(TAG, "onPostFile: " + f.getAbsolutePath() + "不存在");
            return;

        }

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/octet-stream"),  f);
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("mBan", "uu.jpg", requestFile);

        IUploadImg up = re.create(IUploadImg.class);
        Call<ResponseBody> call = up.uploadImg(filePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });

    }


    private void onPostFile() {
        Request.Builder builder = new Request.Builder();
        String dir = Environment.getExternalStorageDirectory()
                + "/Pictures";
        File file = new File(dir, "1487780231681.jpg");
        if (!file.exists()) {
            Log.i(TAG, "onPostFile: " + file.getAbsolutePath() + "不存在");
            return;

        }

        com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(com.squareup.okhttp.MediaType.parse("application/octet-stream"), file);
        CountingRequestBudy requestBody=new CountingRequestBudy(body, new CountingRequestBudy.Listener() {
            @Override
            public void oRequestProgress(long byteWritten, long contentLength) {
                Log.i(TAG, "oRequestProgress: "+byteWritten+"/"+contentLength);
            }
        });
        Request request = builder.url(BASE_URL + "postFile").post(requestBody).build();
        executeRequest(request);

    }

    private void executeRequest(Request request) {
        com.squareup.okhttp.Call call = okHttpClient.newCall(request);
        call.enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(final com.squareup.okhttp.Response response) throws IOException {
//                Log.i(TAG, "onResponse: "+response.body().string());
                final String rs = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });
    }
}

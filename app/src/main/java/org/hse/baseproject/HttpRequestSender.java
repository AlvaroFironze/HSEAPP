package org.hse.baseproject;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestSender {
    private final static String TAG = "RequestSender";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Future<String> get(String requestString) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();

        Request request = new Request.Builder().url(requestString).build();
        Call call = client.newCall(request);

        CompletableFuture<String> f = new CompletableFuture<>();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
                Log.e(TAG, "error in request", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException(String.format("Unexpected code: %s", response.code()));

                assert response.body() != null;
                String bodyString = response.body().string();

                f.complete(bodyString);
            }
        });

        return f;
    }
}
package zaym.in.card5min.background;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zaym.in.card5min.Constant;

public class GetADSKey extends AsyncTask<Map<String, String>, Void, String> {

    private final MutableLiveData<String> dataADSKey;

    public GetADSKey(MutableLiveData<String> dataADS) {
        this.dataADSKey = dataADS;
    }

    @SafeVarargs
    @Override
    protected final String doInBackground(Map<String, String>... maps) {
        String responseADSKey = null;
        try {
            requestADSKeyClient requestADSKeyClient = new requestADSKeyClient();
            responseADSKey = requestADSKeyClient.run(Constant.Companion.getUrlGetADSKey(), maps[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseADSKey;
    }

    @Override
    protected void onPostExecute(String sPostAdsKey) {
        super.onPostExecute(sPostAdsKey);
        try{
            dataADSKey.postValue(sPostAdsKey);
        } catch (Exception e) {
            e.printStackTrace();
            dataADSKey.postValue("0");
        }

    }

    public static class requestADSKeyClient {
        final OkHttpClient okHttpClientAdsKey = new OkHttpClient();

        String run(String url, Map<String, String> hashmap) throws IOException {
            Request requestAdsKey = new Request.Builder()
                    .url(url)
                    .post(postData())
                    .build();
            try (Response response = okHttpClientAdsKey.newCall(requestAdsKey).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        }

        public RequestBody postData() {
            MultipartBody.Builder builderAdsKey = new MultipartBody.Builder();
            builderAdsKey.setType(MultipartBody.FORM);
            builderAdsKey.addFormDataPart("action", "getAds");
            builderAdsKey.build();
            return builderAdsKey.build();
        }

    }
}

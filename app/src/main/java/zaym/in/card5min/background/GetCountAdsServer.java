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

public class GetCountAdsServer extends AsyncTask<Map<String, String>, Void, String> {

    private final MutableLiveData<Integer> dataADSCounter;

    public GetCountAdsServer(MutableLiveData<Integer> dataADSCounter) {
        this.dataADSCounter = dataADSCounter;
    }

    @SafeVarargs
    @Override
    protected final String doInBackground(Map<String, String>... maps) {
        String responseADSCount = null;
        try {
            counterRequestADSClient counterRequestADSClient = new counterRequestADSClient();
            responseADSCount = counterRequestADSClient.run(
                    Constant.Companion.getUrlGetCountAdsServer(),
                    maps[0]
            );
        } catch (IOException e) {
            e.printStackTrace();
            responseADSCount = "0";
        }

        return responseADSCount;
    }

    @Override
    protected void onPostExecute(String strPostCounter) {
        super.onPostExecute(strPostCounter);
        dataADSCounter.postValue(
                Integer.decode(strPostCounter.replaceAll("\\s+", ""))
        );
    }

    public static class counterRequestADSClient {
        final OkHttpClient okHttpClientADSCount = new OkHttpClient();

        String run(String url, Map<String, String> hashmap) throws IOException {
            Request requestADSCount = new Request.Builder()
                    .url(url)
                    .post(postData())
                    .build();
            try (Response response = okHttpClientADSCount.newCall(requestADSCount).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        }

        public RequestBody postData() {
            MultipartBody.Builder builderADSCount = new MultipartBody.Builder();
            builderADSCount.setType(MultipartBody.FORM);
            builderADSCount.addFormDataPart("action", "getAds");
            builderADSCount.build();
            return builderADSCount.build();
        }

    }
}

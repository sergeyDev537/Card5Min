package zaym.in.card5min.background;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zaym.in.card5min.Constant;
import zaym.in.card5min.models.ModelJSON;
import zaym.in.card5min.managers.ReferrerManager;

@SuppressLint("StaticFieldLeak")
public class GetListCount extends AsyncTask<Map<String, String>, Void, String> {

    private final Context contextBaseRequest;
    private final MutableLiveData<List<ModelJSON>> filesPlaceHolderJSON;

    public GetListCount(Context contextBaseRequest,
                        MutableLiveData<List<ModelJSON>> dataFiles) {
        this.contextBaseRequest = contextBaseRequest;
        this.filesPlaceHolderJSON = dataFiles;
    }


    @SafeVarargs
    @Override
    protected final String doInBackground(Map<String, String>... postData) {
        String responseCode = null;
        try {
            BaseRequestClient baseRequestClient = new BaseRequestClient();
            responseCode = baseRequestClient.run(
                    Constant.Companion.getUrlGetListCount(),
                    postData[0]
            );
            MutableLiveData<String> referrerStringBase = new MutableLiveData();
            ReferrerManager.Companion.getReferrerString(contextBaseRequest, referrerStringBase);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }


    @Override
    protected void onPostExecute(String strPostExecute) {
        convertToList(strPostExecute);
        super.onPostExecute(strPostExecute);
    }

    private void convertToList(String strToConvert) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ModelJSON>>(){}.getType();
            filesPlaceHolderJSON.postValue(gson.fromJson(strToConvert, type));
        } catch (Exception e) {
            e.printStackTrace();
            filesPlaceHolderJSON.postValue(new ArrayList<>());
        }

    }


    public class BaseRequestClient {
        final OkHttpClient okHttpClientBaseRequest = new OkHttpClient();

        String run(String url, Map<String, String> hashmap) throws IOException {
            Request requestBase = new Request.Builder()
                    .url(url)
                    .post(postData(hashmap))
                    .build();
            try (Response response = okHttpClientBaseRequest.newCall(requestBase).execute()) {
                assert response.body() != null;
                return response.body().string();
            }
        }

        public RequestBody postData(Map<String, String> hashmapBase) {
            MultipartBody.Builder builderPost = new MultipartBody.Builder();
            builderPost.setType(MultipartBody.FORM);
            if (hashmapBase != null) {
                for (String key : hashmapBase.keySet()) {
                    builderPost.addFormDataPart(key, Objects.requireNonNull(hashmapBase.get(key)));
                }
            }
            return BackgroundManager.Companion.createParamsRequest(contextBaseRequest, builderPost);
        }

    }
}

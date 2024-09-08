package api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class WifiApiService {
    private static final String API_KEY = "766f7a51626e6f66313235586f615176";  
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/TbPublicWifiInfo/1/1000/";

    // API 호출하는 매소드 
    public JSONArray fetchWifiData() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonData = response.body().string();
            JSONObject jsonObject= new JSONObject(jsonData);

            // API 응답에서 와이파이 데이터를 추출
            return jsonObject.getJSONObject("TbPublicWifiInfo").getJSONArray("row");
        }
    }

    public static void main(String[] args) {
        WifiApiService apiService = new WifiApiService(); 

        try {
            JSONArray wifiData = apiService.fetchWifiData(); // API 호출 및 데이터 가져오기
            System.out.println("성공");
            System.out.println(wifiData); // 가져온 데이터 출력
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}


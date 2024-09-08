package api;

import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiIntoDb {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb1";
    private static final String DB_USER = "testuser1";
    private static final String DB_PASSWORD = "zerobase";

    // MariaDB 연결 메소드 
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // API에서 가져온 데이터를 삽입하는 메소드 
    public void saveWifiData(JSONArray wifiArray) throws SQLException {
        String sql = "INSERT INTO public_wifi (NUMBER, LONGITUDE, LATITUDE, SECTOR, DISTANCE, NAME, ADDRESS, DETAIL_ADD, TYPE, IN_OUT, WORK_DT, FLOOR, SERVICE, NET, REGISTER_YR, CONNECTION)"
                     + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < wifiArray.length(); i++) {
                JSONObject wifiObj = wifiArray.getJSONObject(i);

                pstmt.setString(1, wifiObj.getString("X_SWIFI_MGR_NO"));
                pstmt.setString(2, wifiObj.getString("LNT")); 
                pstmt.setString(3, wifiObj.getString("LAT"));  
                pstmt.setString(4, wifiObj.optString("X_SWIFI_WRDOFC", ""));
                pstmt.setDouble(5, 0.0); 
                pstmt.setString(6, wifiObj.getString("X_SWIFI_MAIN_NM"));
                pstmt.setString(7, wifiObj.getString("X_SWIFI_ADRES1"));
                pstmt.setString(8, wifiObj.optString("X_SWIFI_ADRES2", ""));
                pstmt.setString(9, wifiObj.getString("X_SWIFI_INSTL_TY"));
                pstmt.setString(10, wifiObj.getString("X_SWIFI_INOUT_DOOR"));
                pstmt.setString(11, wifiObj.getString("WORK_DTTM"));
                pstmt.setString(12, wifiObj.optString("X_SWIFI_INSTL_FLOOR", ""));
                pstmt.setString(13, wifiObj.optString("X_SWIFI_SVC_SE", ""));
                pstmt.setString(14, wifiObj.optString("X_SWIFI_CMCWR", ""));
                pstmt.setInt(15, wifiObj.optInt("X_SWIFI_CNSTC_YEAR", 0));
                pstmt.setString(16, wifiObj.optString("X_SWIFI_CNSTC_STATE", ""));

                pstmt.addBatch();  // 여러 행을 한 번에 처리하기 위해 배치에 추가
            }
            pstmt.executeBatch(); // 배치 실행
            System.out.println("데이터가 성공적으로 삽입되었습니다.");
        }
    }
    
    public static void main(String[] args) {
        WifiApiService apiService = new WifiApiService(); 
        ApiIntoDb dbManager = new ApiIntoDb();  

        try {
            // 1. API로부터 데이터 가져오기
            JSONArray wifiData = apiService.fetchWifiData();

            // 2. 데이터베이스에 데이터 삽입
            dbManager.saveWifiData(wifiData);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}




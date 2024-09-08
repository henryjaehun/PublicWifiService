<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Public WiFi Informaiton</title>
</head>
<body>
 <h1>Public WiFi Information</h1>
    <table border="1">
        <tr>
            <th>관리번호</th>
            <th>경도</th>
            <th>위도</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>주소1</th>
            <th>주소2</th>
            <th>설치유형</th>
            <th>내/외부</th>
            <th>작업일자</th>
            <th>설치층</th>
            <th>서비스구분</th>
            <th>네트워크업체</th>
            <th>설치년도</th>
            <th>연결상태</th>
        </tr>

        <%
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try {
                String dbURL = "jdbc:mariadb://localhost:3306/testdb1";
                String dbUser = "testuser1";
                String dbPassword = "zerobase";
                
                Class.forName("org.mariadb.jdbc.Driver");  
                conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);  // MariaDB 연결
                
                String sql = "SELECT * FROM public_wifi";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("NUMBER") + "</td>");
                    out.println("<td>" + rs.getString("LONGITUDE") + "</td>");
                    out.println("<td>" + rs.getString("LATITUDE") + "</td>");
                    out.println("<td>" + rs.getString("SECTOR") + "</td>");
                    out.println("<td>" + rs.getString("NAME") + "</td>");
                    out.println("<td>" + rs.getString("ADDRESS") + "</td>");
                    out.println("<td>" + rs.getString("DETAIL_ADD") + "</td>");
                    out.println("<td>" + rs.getString("TYPE") + "</td>");
                    out.println("<td>" + rs.getString("IN_OUT") + "</td>");
                    out.println("<td>" + rs.getString("WORK_DT") + "</td>");
                    out.println("<td>" + rs.getString("FLOOR") + "</td>");
                    out.println("<td>" + rs.getString("SERVICE") + "</td>");
                    out.println("<td>" + rs.getString("NET") + "</td>");
                    out.println("<td>" + rs.getInt("REGISTER_YR") + "</td>");
                    out.println("<td>" + rs.getString("CONNECTION") + "</td>");
                    out.println("</tr>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) try { rs.close(); } catch (SQLException e) {}
                if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
                if (conn != null) try { conn.close(); } catch (SQLException e) {}
            }
        %>
    </table>
</body>
</html>

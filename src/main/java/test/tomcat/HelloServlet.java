package test.tomcat;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Test Servlet & PostgreSQL</title></head><body>");
        out.println("<h1>🚀 Kiểm tra Servlet và Cơ Sở Dữ Liệu PostgreSQL</h1>");

        // Chuỗi kết nối Database Neon của bạn
        String dbUrl = "jdbc:postgresql://ep-billowing-shape-aohdul7q-pooler.c-2.ap-southeast-1.aws.neon.tech:5432/neondb?sslmode=require";
        String user = "neondb_owner";
        String pass = "npg_AGRoW1ie9BrH";

        try {
            // Nạp Driver PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Kết nối vào database
            Connection conn = DriverManager.getConnection(dbUrl, user, pass);

            // Chạy thử câu lệnh SQL lấy giờ hiện tại của database
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW() as current_time, version() as pg_version");

            if (rs.next()) {
                out.println("<p style='color: green; font-weight: bold;'> Kết nối PostgreSQL thành công 100%!</p>");
                out.println("<p><b>Thời gian trên Database:</b> " + rs.getString("current_time") + "</p>");
                out.println("<p><b>Phiên bản PostgreSQL:</b> " + rs.getString("pg_version") + "</p>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("<p style='color: red; font-weight: bold;'> Lỗi kết nối Database:</p>");
            out.println("<pre>" + e.getMessage() + "</pre>");
        }

        out.println("</body></html>");
    }
}
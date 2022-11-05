package web.myssm.demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//演示Application保存作用域(demo03和demo04)
@WebServlet("/demo05")
public class Demo05Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.向Application保存作用域保存数据,key为uname
        //servletContext:Servlet上下文(也称为application)
        ServletContext application = request.getServletContext();
        application.setAttribute("uname", "lili");
        //2.客户端重定向(request保持域无法获取)
        response.sendRedirect("demo06");

        //3.服务器端转发(request保存域可以获取)
        //request.getRequestDispatcher("demo06").forward(request, response);
        
    }
}

package web.Servlets.old;

import web.myssm.mySpringMVC.old.modifyViewBaseServlet;

//@WebServlet("/update.do")
public class UpdateServlet extends modifyViewBaseServlet {

    /*
    private FruitDAO fruit= new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        //1.从请求中获取参数并对数值类型的参数进行转型
        String fidStr = request.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        Integer price = Integer.parseInt(priceStr);
        String fcountStr = request.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = request.getParameter("remark");

        //2.将数据更新到数据库
        fruit.update(new Fruit(fid,fname,price,fcount,remark));

        //3.修改完在回到index页面上,资源跳转
        //super.processTemplate("index", request, response);//会添加/index.html

        //request.getRequestDispatcher("index").forward(request,response)
        //返回的是更新前的页面
        // ①此处需要重定向,目的是重新给indexServlet发请求,重新获取fruitList,然后覆盖到session中
        // ②这样index.html页面上的session中的数据才是最新的
        //应该使用respond.sendRedirect("index")重定向,这个index是url-pattern是index
        response.sendRedirect("index");
    }

     */
}

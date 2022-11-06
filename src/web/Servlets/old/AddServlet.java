package web.Servlets.old;

import web.fruit.DAO.FruitDAO;
import web.fruit.DAO.impl.FruitDAOImpl;
import web.myssm.mySpringMVC.old.modifyViewBaseServlet;


//@WebServlet("/add.do")
public class AddServlet extends modifyViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();
    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将提交的数据添加到数据库
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);

        //进行客户端重定向
        response.sendRedirect("index");


    }
     */
}

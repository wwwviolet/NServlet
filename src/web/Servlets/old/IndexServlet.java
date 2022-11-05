package web.Servlets.old;


import web.myssm.mySpringMVC.old.modifyViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Servlet从3.0开始支持注解方式的注册
//@WebServlet("/index")
public class IndexServlet extends modifyViewBaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /*
    //在网址上直接访问Index
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        //页数默认为1
        Integer pageNo = 1;

        String operate = request.getParameter("operate");
        //如果operate!=null,说明通过表单的查询按钮点击过来
        //如果为null,则不是表单的查询过来的
        String keyword = null;
        if (StringUtil.isNotEmpty(operate) && "search".equals(operate)){
            //说明是点击表单查询发过来的请求
            //此时pageNo应该还原为1,keyword应该从请求参数获取
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else {
            //说明此处不是通过表单的查询按钮点击过来(如下一页上一页等或地址栏输入)
            //此时keyword应该从session作用域获取
            String pageNoStr = request.getParameter("pageNo");
            //如果传入的值不为空则,查询指定的页数
            if (StringUtil.isNotEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);//如果从请求中读到pageNo,则类型转换,否则,pageNo默认是1
            }
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null){
                keyword = (String) keywordObj;
            }else {
                keyword = "";
            }
        }



//        String pageNoStr = request.getParameter("pageNo");
//        //如果传入的值不为空则,查询指定的页数
//        if (StringUtil.isNotEmpty(pageNoStr)){
//            pageNo = Integer.parseInt(pageNoStr);
//        }
        //1.第一次访问index,会让session中添加pageNo为1,并进行数据展示
        //
//        HttpSession session = request.getSession();

        //重新更新当前页的值
        session.setAttribute("pageNo",pageNo);


        //获取指定页数的数据
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);
        //保存到session作用域
        session.setAttribute("fruitList", fruitList);

        //获取数据的总页数
        //方法1:
//        Integer countPageNum = countPageNum((FruitDAOImpl) fruitDAO);
        //方法2:
        //总记录条数
        int fruitCount = fruitDAO.getFruitCount(keyword);
//        //总页数
        int pageCount = (fruitCount+5-1)/5;//
        session.setAttribute("pageCount",pageCount);

        //此处的视图名称是index
        //那么thymeleaf会将这个逻辑视图名称对应到物理视图名称上去
        //逻辑视图名称: index
        //物理视图名称: view-prefix + 逻辑视图名称 +view-suffix
        //所以真实的视图名称是:/+index+.html(/1.html)
        Object fruitList1 = request.getSession().getAttribute("fruitList");
        System.out.println(fruitList1);
        super.processTemplate("index", request, response);
    }


//    //count pageNum
//    public int countPageNum(FruitDAOImpl fruitList){
//        return fruitList.getFruitList().size()/5;
//    }


*/
}

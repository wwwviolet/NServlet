package web.Controllers;

import web.fruit.DAO.FruitDAO;
import web.fruit.DAO.impl.FruitDAOImpl;
import web.fruit.Pojo.Fruit;
import web.myssm.uitl.StringUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


//@WebServlet("/fruit.do")
//不再是Servlet组件,所有不会调用init方法
//不再继承ViewBaseServlet(作为普通类)
//但是跟ServletAPI还是有耦合
//获取参数的过程统一抽取到父类
public class FruitController{

    //之前FruitServlet时又给Servlet组件,那么其中的init方法一定会被调用
    //init方法内部会出现一句话:super.init();(不会自动调用,需要自己调用)
    //ViewBaseServlet的init()方法不会被执行

    private FruitDAO fruitDAO = new FruitDAOImpl();

    //所有的方法中,respond都不需要了,也不需要设置request也不需要设置utf-8(),在dispatcherServlet中已经设置
    private String index(String operate,String keyword,Integer pageNo,HttpServletRequest request){
        HttpSession session = request.getSession();

        if(pageNo==null){
            pageNo = 1;
        }
        if (StringUtil.isNotEmpty(operate) && "search".equals(operate)){
            pageNo = 1;
            if (StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null){
                keyword = (String) keywordObj;
            }else {
                keyword = "";
            }
        }

        //重新更新当前页的值
        session.setAttribute("pageNo",pageNo);

        //获取指定页数的数据
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);
        //保存到session作用域
        session.setAttribute("fruitList", fruitList);

        //总记录条数
        int fruitCount = fruitDAO.getFruitCount(keyword);
        //总页数
        int pageCount = (fruitCount+5-1)/5;//
        session.setAttribute("pageCount",pageCount);

        return "index";
    }

    private String add(String fname,Integer price,Integer fcount,String remark){
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);
        return "redirect:fruit.do";
    }

    private String del(Integer fid){
        if (fid!=null){
            fruitDAO.delFruit(fid);
            //此时不能使用内部转发,应该使用重定向
            //response.sendRedirect("fruit.do");
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(HttpServletRequest request,Integer fid) {
        //1.查询某个库存记录(根据id查询),需要获取id

        if (fid!=null){
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            //将数据存储到request保存域
            request.setAttribute("fruit", fruit);
            //转发到edit页面
            //super.processTemplate("edit", request, response);
            return "edit";
        }
        return "error";
    }

    private String addRe(HttpServletRequest request) throws ServletException {
        //processTemplate("add", request, response);
        return "add";
    }

    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark) {
        //设置编码
//        request.setCharacterEncoding("utf-8");

        //1.从请求中获取参数并对数值类型的参数进行转型
        /*
        String fidStr = request.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        Integer price = Integer.parseInt(priceStr);
        String fcountStr = request.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = request.getParameter("remark");
         */

        //2.将数据更新到数据库
        fruitDAO.update(new Fruit(fid,fname,price,fcount,remark));
        /*
        //3.修改完在回到index页面上,资源跳转
        //super.processTemplate("index", request, response);//会添加/index.html
        //request.getRequestDispatcher("index").forward(request,response)
        //返回的是更新前的页面
        // ①此处需要重定向,目的是重新给indexServlet发请求,重新获取fruitList,然后覆盖到session中
        // ②这样index.html页面上的session中的数据才是最新的
        //应该使用respond.sendRedirect("index")重定向,这个index是url-pattern是index
        //response.sendRedirect("fruit.do");
         */
        //3.资源跳转
        return "redirect:fruit.do";
    }


    /*
//    //count pageNum
//    public int countPageNum(FruitDAOImpl fruitList){
//        return fruitList.getFruitList().size()/5;
//    }
     */
    //    //注释service方法
//    /*
//    @Override
//
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        /*
//        //设置编码
//        request.setCharacterEncoding("UTF-8");
//        String operateWeb = request.getParameter("operateWeb");
//        if (StringUtil.isEmpty(operateWeb)) {
//            operateWeb = "index";
//        }
//         */
//
//
//        /*
//        //获取当前类所有方法
//        Method[] declaredMethods = this.getClass().getDeclaredMethods();
//        for (Method method : declaredMethods){
//            //获取方法名
//            String name = method.getName();
//            if (operateWeb.equals(name)){
//                //表示找到和operate同名的方法,那么通过反射技术调用它
//                //每个servlet都传入request和respond
//                try {
//                    method.invoke(this, request,response);
//                    return;
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        throw new  RuntimeException("operate值非法!");
//         */
//
//        //使用switch获取operate的值进行调用方法
//        /*
//        switch (operateWeb) {
//            case "index":
//                index(request, response);
//                break;
//            case "add":
//                add(request,response);
//                break;
//            case "del":
//                del(request, response);
//                break;
//            case "edit":
//                edit(request, response);
//                break;
//            case "addRe":
//                addRe(request, response);
//                break;
//            case "update":
//                update(request,response);
//                break;
//            default:
//                throw new  RuntimeException("operate值非法!");
//
//        }
//     }
//         */



    //在网址上直接访问Index
    /*
    public void setServletContext(ServletContext servletContext) throws ServletException {
        this.servletContext = servletContext;
        super.init(servletContext);
    }
 */
}

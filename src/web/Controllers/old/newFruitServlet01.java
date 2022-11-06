package web.Controllers.old;

import web.fruit.DAO.FruitDAO;
import web.fruit.DAO.impl.FruitDAOImpl;
import web.fruit.Pojo.Fruit;
import web.myssm.mySpringMVC.old.modifyViewBaseServlet;
import web.myssm.uitl.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


//@WebServlet("/fruit.do")
public class newFruitServlet01 extends modifyViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这部分转移到dispatcherServlet中
        /*
        //设置编码
        request.setCharacterEncoding("UTF-8");
        String operateWeb = request.getParameter("operateWeb");
        if (StringUtil.isEmpty(operateWeb)) {
            operateWeb = "index";
        }


        //获取当前类所有方法
        Method[] declaredMethods = this.getClass().getDeclaredMethods();
        for (Method method : declaredMethods){
            //获取方法名
            String name = method.getName();
            if (operateWeb.equals(name)){
                //表示找到和operate同名的方法,那么通过反射技术调用它
                //每个servlet都传入request和respond
                try {
                    method.invoke(this, request,response);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new  RuntimeException("operate值非法!");
         */


        //使用switch获取operate的值进行调用方法
        /*
        switch (operateWeb) {
            case "index":
                index(request, response);
                break;
            case "add":
                add(request,response);
                break;
            case "del":
                del(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "addRe":
                addRe(request, response);
                break;
            case "update":
                update(request,response);
                break;
            default:
                throw new  RuntimeException("operate值非法!");

        }
         */
    }

    //在网址上直接访问Index

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        //所以真实的视图名称是:/+index+.html(/copy.html)
        Object fruitList1 = request.getSession().getAttribute("fruitList");
        System.out.println(fruitList1);
        super.processTemplate("index", request, response);
    }


//    //count pageNum
//    public int countPageNum(FruitDAOImpl fruitList){
//        return fruitList.getFruitList().size()/5;
//    }



    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将提交的数据添加到数据库
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);

        //进行客户端重定向
        response.sendRedirect("fruit.do");

    }


    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);
            //此时不能使用内部转发,应该使用重定向
            response.sendRedirect("fruit.do");
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("a");
        //1.查询某个库存记录(根据id查询),需要获取id
        String fidStr = request.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitByFid(fid);
            //将数据存储到request保存域
            request.setAttribute("fruit", fruit);
            //转发到edit页面
            super.processTemplate("edit", request, response);
        }
    }

    private void addRe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processTemplate("add", request, response);
    }


    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        fruitDAO.update(new Fruit(fid,fname,price,fcount,remark));

        //3.修改完在回到index页面上,资源跳转
        //super.processTemplate("index", request, response);//会添加/index.html

        //request.getRequestDispatcher("index").forward(request,response)
        //返回的是更新前的页面
        // ①此处需要重定向,目的是重新给indexServlet发请求,重新获取fruitList,然后覆盖到session中
        // ②这样index.html页面上的session中的数据才是最新的
        //应该使用respond.sendRedirect("index")重定向,这个index是url-pattern是index
        response.sendRedirect("fruit.do");
    }
}

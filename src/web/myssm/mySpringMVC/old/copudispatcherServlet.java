package web.myssm.mySpringMVC.old;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import web.myssm.mySpringMVC.ViewBaseServlet;
import web.myssm.uitl.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;


<<<<<<< HEAD
//@WebServlet("*.do")//通配符,表示拦截所有.do结尾的请求,无需加斜杠
=======
@WebServlet("*.do")//通配符,表示拦截所有.do结尾的请求,无需加斜杠
>>>>>>> origin/Servlet
public class copudispatcherServlet extends ViewBaseServlet {


    private Map<String, Object> beanMap = new HashMap<>();

    //Servlet的生命周期分为实例化,初始化,服务,销毁四个生命周期
    //在构造方法中解析xml文件,把配置文件中的所有bean标签,和实例对象全部加载保存在Map对象中
    //dispatcherServlet()构造器通过反射获取xml文件中所有Controller对象,service方法通过拦截获取请求发过来的参数选择要调用哪个方法
    /*
    public dispatcherServlet(){
        try {
            //得到一个输入流
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document = documentBuilder.parse(resourceAsStream);

            //4.获取所有的bean元素(节点)
            NodeList beanNodeList = document.getElementsByTagName("bean");

            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class<?> controllerBeanClass = Class.forName(className);
                    Object beanObj = controllerBeanClass.newInstance();
                    Field servletContextField = controllerBeanClass.getDeclaredField("servletContext");
                    servletContextField.setAccessible(true);
                    servletContextField.set(beanObj,this.getServletContext());
                    beanMap.put(beanId, beanObj);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
     */

    public copudispatcherServlet() {

    }


    @Override
    public void init() throws ServletException {
        //调用父类的ViewBaseServlet的init()方法
        super.init();
        System.out.println("inti-config被调用");
        try {
            //得到一个输入流
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document = documentBuilder.parse(resourceAsStream);

            //4.获取所有的bean元素(节点)
            NodeList beanNodeList = document.getElementsByTagName("bean");

            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class<?> controllerBeanClass = Class.forName(className);
                    Object beanObj = controllerBeanClass.newInstance();
                    /*
                    Method setServletContext = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
                    setServletContext.invoke(beanObj, this.getServletContext());
                     */
                    /*
                    Field servletContextField = controllerBeanClass.getDeclaredField("servletContext");
                    servletContextField.setAccessible(true);
                    servletContextField.set(beanObj,this.getServletContext());
                    beanMap.put(beanId, beanObj);
                     */
                    beanMap.put(beanId, beanObj);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        //对发过来的请求进行utf-8编码
        request.setCharacterEncoding("UTF-8");


        //1.来自哪个controller
        //假设url是:http://localhost:8080/NServlet/hello.do
        //那么servletPath是:  /hello.do
        //第一步: /hello.do -> hello(字符串截取)
        //第二部: hello ->hello.controller(对应上) 或者fruit -> FruitController(再调用里面的方法依赖于operateWeb,通过反射调用)

        String servletPath = request.getServletPath();
        //System.out.println("servletPath = " + servletPath);
        servletPath = servletPath.substring(1);
        int lastIndexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndexOf);
        //System.out.println(servletPath);
        Object controllerBeanObj = beanMap.get(servletPath);

        //2.获取xx.do请求进来的value,准备调用Controller里的方法
        String operateWeb = request.getParameter("operateWeb");
        if (StringUtil.isEmpty(operateWeb)) {
            operateWeb = "index";
        }


        //3.通过配置文件获取对应的Controller类(多个Controller)的方法,并通过拦截获取的xx.do请求与之对应的value,调用对应的方法
        try {
            //通过反射获取Controller对应的方法
            //反射获取所有的方法
            Method[] methods = controllerBeanObj.getClass().getMethods();
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operateWeb, HttpServletRequest.class);
            if (method != null) {
                //1.统一获取请求参数
                //获取当前方法的参数,返回数组
                Parameter[] parameters = method.getParameters();
                
                //2.controller组件中的方法调用
                //暴力破解
                method.setAccessible(true);
                //通过反射调用Controller的方法
                Object returnObj = method.invoke(controllerBeanObj, request);

                //3.视图处理
                String methodReturnStr = (String) returnObj;
                //查找前缀为redirect:的返回值,如果是则进行重定向
                if (methodReturnStr.startsWith("redirect:")) { //比如: redirect:fruit.do
                    //此处为截取redirect:长度后面的字符串
                    String redirectStr = methodReturnStr.substring("redirect:".length());
                    //对截取后的字符串进行重定向
                    response.sendRedirect(redirectStr);
                } else {
                    //如果不是redirect:开头的返回值,则进行转发
                    super.processTemplate(methodReturnStr, request, response);
                }

            } else {
                throw new RuntimeException("operate值非法!");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        /*
        //获取当前类所有方法
        Method[] declaredMethods = controllerBeanObj.getClass().getDeclaredMethods();
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
    }
}

package web.myssm.uitl;

public class StringUtil {

    //判断字符串是否为null
    public static boolean isEmpty(String str){
        return str==null || "".equals(str);
    }

    //判断是否不为null
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}

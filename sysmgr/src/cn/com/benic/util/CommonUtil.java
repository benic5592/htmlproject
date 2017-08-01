package cn.com.benic.util;

/**
 * Created by Administrator on 2017/6/16.
 */
public class CommonUtil {

    public static boolean strIsEmpty(String str){
        if(null !=str && !str.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    public static int strToInt(String str){
        if(strIsEmpty(str)){
            return 0;
        }
        try {

            return  Integer.parseInt(str);
        } catch (Exception e){
            return 0;
        }
    }

    public static String createId(){

        return "ZB"+System.currentTimeMillis();
    }
}

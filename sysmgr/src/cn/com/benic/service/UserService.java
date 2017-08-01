package cn.com.benic.service;

import cn.com.benic.dao.UserDao;
import cn.com.benic.model.UserModel;
import cn.com.benic.util.CommonConstants;
import cn.com.benic.util.CommonUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public class UserService {

    private UserDao dao;

    public UserService() {

        dao  = new UserDao();
    }

    /**
     * 创建用户
     * @param user
     * @return
     */
    public boolean createUser(UserModel user){
        List<UserModel> lst = dao.getUserByName(user.getName());
        if(null == lst || lst.isEmpty()){
            user.setId(CommonUtil.createId());
            user.setDelFlg(CommonConstants.DELETE_FLG_NOT);
            user.setCreateTime(String.valueOf(System.currentTimeMillis()));
            user.setUpdateTime(String.valueOf(System.currentTimeMillis()));
            int count = dao.createUser(user);
            if(count > 0){
                return true;
            }
        }
        return false;

    }


    /**
     * 检测用户名是否存在
     * @param name
     * @return
     */
    public String checkUserByName(String name,String page){
        List<UserModel> lst = dao.getUserByName(name);
        if(null != lst && !lst.isEmpty()){
            if("login".equals(page)){

                return "";
            } else if ("regist".equals(page)){

                return "用户名已存在";
            } else {

                return "用户名已存在";
            }
        } else {
            if("login".equals(page)){

                return "用户名不存在";
            } else if ("regist".equals(page)){

                return "用户名可以注册";
            } else {

                return "用户名不存在";
            }
        }
    }

    /**
     * 检测用户是否存在
     * @param user
     * @return
     */
    public boolean checkUserByNamePwd(UserModel user){
        List<UserModel> lst = dao.getUserByNamePwd(user);
        if(null != lst && !lst.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
}

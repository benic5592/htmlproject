package cn.com.benic.dao;

import cn.com.benic.model.UserModel;
import cn.com.benic.util.CommonConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public class UserDao {


    /**
     * 通过用户名和密码获取用户
     * @param user
     * @return
     */
    public List<UserModel> getUserByNamePwd(UserModel user){

        PreparedStatement presta=null;
        ResultSet re=null;
        try{
            ConnectionUtil.getConn().setAutoCommit(true);

            String sql="select id,name,pwd,del_flg,create_time,update_time,comments from t_user where name = ? and pwd = ? and del_flg = ?";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1,user.getName());
            presta.setString(2,user.getPwd());
            presta.setString(3, CommonConstants.DELETE_FLG_NOT);
            re=presta.executeQuery();
            List<UserModel> lst = resultToList(re);
            return lst;

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                re.close();


            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
        
        
    }

    /**
     * 通过用户名获取用户
     * @param name
     * @return
     */
    public List<UserModel> getUserByName(String name){

        PreparedStatement presta=null;
        ResultSet re=null;
        try{
            ConnectionUtil.getConn().setAutoCommit(true);

            String sql="select id,name,pwd,del_flg,create_time,update_time,comments from t_user where name = ? ";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1,name);
            re=presta.executeQuery();
            List<UserModel> lst = resultToList(re);
            return lst;

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                re.close();

            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;


    }

    /**
     * 创建用户
     * @param user
     * @return
     */
    public int createUser(UserModel user){

        PreparedStatement presta=null;
        int count = -1;
        try{
            ConnectionUtil.getConn().setAutoCommit(false);

            String sql="insert into t_user (id,name,pwd,del_flg,create_time,update_time,comments) values (?,?,?,?,?,?,?)";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1,user.getId());
            presta.setString(2,user.getName());
            presta.setString(3,user.getPwd());
            presta.setString(4,user.getDelFlg());
            presta.setString(5,user.getCreateTime());
            presta.setString(6,user.getUpdateTime());
            presta.setString(7,user.getComments());
            count = presta.executeUpdate();

            ConnectionUtil.getConn().commit();

            return count;

        }catch(SQLException e){
            e.printStackTrace();
            try {
                ConnectionUtil.getConn().rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return -1;
        }finally{


        }



    }

    /**
     * 查询所有列后转换为对应的集合
     * @param rs
     * @return
     */
    private List<UserModel> resultToList(ResultSet rs){
        List<UserModel> userlist = new ArrayList<UserModel>();
        UserModel user = null;
        try {
            if(rs == null){
                return userlist;
            }
            while(rs.next()){
                user = new UserModel();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPwd(rs.getString("pwd"));
                user.setDelFlg(rs.getString("del_flg"));
                user.setCreateTime(rs.getString("create_time"));
                user.setUpdateTime(rs.getString("update_time"));
                user.setComments(rs.getString("comments"));
                userlist.add(user);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userlist;
    }
}

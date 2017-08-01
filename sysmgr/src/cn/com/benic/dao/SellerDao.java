package cn.com.benic.dao;

import cn.com.benic.model.SellerModel;
import cn.com.benic.util.CommonConstants;
import cn.com.benic.util.CommonUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public class SellerDao {


    /**
     * 创建商家
     * @param sellerModel
     * @return
     */
    public int createSeller(SellerModel sellerModel){

        PreparedStatement presta=null;
        int count = -1;
        try{
            ConnectionUtil.getConn().setAutoCommit(false);

            String sql="insert into t_seller (id,name,tel,type,district ,address,per,del_flg,create_time,update_time,comments) values (?,?,?,?,?,?,?,?,?,?,?)";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1,sellerModel.getId());
            presta.setString(2,sellerModel.getName());
            presta.setString(3,sellerModel.getTel());
            presta.setString(4,sellerModel.getType());
            presta.setString(5,sellerModel.getDistrict());
            presta.setString(6,sellerModel.getAddress());
            presta.setInt(7, CommonUtil.strToInt(sellerModel.getPer()));
            presta.setString(8,sellerModel.getDelFlg());
            presta.setString(9,sellerModel.getCreateTime());
            presta.setString(10,sellerModel.getUpdateTime());
            presta.setString(11,sellerModel.getComments());
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
     * 更新商家
     * @param sellerModel
     * @return
     */
    public int updateSeller(SellerModel sellerModel){

        PreparedStatement presta=null;
        int count = -1;
        try{
            ConnectionUtil.getConn().setAutoCommit(false);

            String sql="update t_seller set name=?,tel=?,type=?,district=? ,address=?,per=?,update_time=? where id=?";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1,sellerModel.getName());
            presta.setString(2,sellerModel.getTel());
            presta.setString(3,sellerModel.getType());
            presta.setString(4,sellerModel.getDistrict());
            presta.setString(5,sellerModel.getAddress());
            presta.setInt(6, CommonUtil.strToInt(sellerModel.getPer()));
            presta.setString(7,sellerModel.getUpdateTime());
            presta.setString(8,sellerModel.getId());
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
     * 删除商家(逻辑删除)
     * @param id
     * @return
     */
    public int deleteSeller(String id){

        PreparedStatement presta=null;
        int count = -1;
        try{
            ConnectionUtil.getConn().setAutoCommit(false);

            String sql="update t_seller set del_flg=? where id=?";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1, CommonConstants.DELETE_FLG_YES);
            presta.setString(2,id);
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
     * 查找商家
     */
    public List<SellerModel> getSellerList(SellerModel sellerModel,int begin,int end){

        PreparedStatement presta=null;
        ResultSet re=null;
        try{
            ConnectionUtil.getConn().setAutoCommit(true);

            StringBuffer sql=new StringBuffer();
            sql.append(" select id,name,tel,type,district ,address,per,del_flg,create_time,update_time,comments ");
            sql.append("from (");
            sql.append("     select seller01.*,rownum as rid ");
            sql.append("     from (");
            sql.append("           select * from t_seller where del_flg='0' ");

            if(!CommonUtil.strIsEmpty(sellerModel.getId())){
                sql.append(" and id like ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getName())){
                sql.append(" and name like ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getTel())){
                sql.append(" and tel = ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getType())){
                sql.append(" and type = ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getDistrict())){
                sql.append(" and district = ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getAddress())){
                sql.append(" and address like ? ");
            }

            if(CommonUtil.strIsEmpty(sellerModel.getPerTo())){
                sql.append(" and per >= ? ");
            } else {
                sql.append(" and per between ? and ? ");
            }


            sql.append("            order by id desc");
            sql.append("           ) seller01 where rownum <=?");
            sql.append("     )seller02 ");
            sql.append("where rid>? ");
            presta= ConnectionUtil.getConn().prepareStatement(sql.toString());

            int count=1;
            if(!CommonUtil.strIsEmpty(sellerModel.getId())){
                presta.setString(count, "%"+sellerModel.getId()+"%");
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getName())){
                presta.setString(count, "%"+sellerModel.getName()+"%");
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getTel())){
                presta.setString(count, sellerModel.getTel());
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getType())){
                presta.setString(count, sellerModel.getType());
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getDistrict())){
                presta.setString(count, sellerModel.getDistrict());
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getAddress())){
                presta.setString(count, "%"+sellerModel.getAddress()+"%");
                count++;
            }

            if(CommonUtil.strIsEmpty(sellerModel.getPerTo())){
                presta.setString(count, sellerModel.getPerFrom());
                count++;
            } else {
                presta.setString(count, sellerModel.getPerFrom());
                count++;
                presta.setString(count, sellerModel.getPerTo());
                count++;
            }

            presta.setInt(count, end);
            count++;
            presta.setInt(count, begin);

            re =presta.executeQuery();

            List<SellerModel> lst = resultToList(re);
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
     * 查找商家总数
     */
    public int getSellerListTotalCount(SellerModel sellerModel){

        PreparedStatement presta=null;
        ResultSet re=null;
        try{
            ConnectionUtil.getConn().setAutoCommit(true);

            StringBuffer sql=new StringBuffer();
            sql.append(" select count(*) ");
            sql.append(" from t_seller where del_flg='0' ");

            if(!CommonUtil.strIsEmpty(sellerModel.getId())){
                sql.append(" and id like ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getName())){
                sql.append(" and name like ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getTel())){
                sql.append(" and tel = ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getType())){
                sql.append(" and type = ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getDistrict())){
                sql.append(" and district = ? ");
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getAddress())){
                sql.append(" and address like ? ");
            }

            if(CommonUtil.strIsEmpty(sellerModel.getPerTo())){
                sql.append(" and per >= ? ");
            } else {
                sql.append(" and per between ? and ? ");
            }
            presta= ConnectionUtil.getConn().prepareStatement(sql.toString());

            int count=1;
            if(!CommonUtil.strIsEmpty(sellerModel.getId())){
                presta.setString(count, "%"+sellerModel.getId()+"%");
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getName())){
                presta.setString(count, "%"+sellerModel.getName()+"%");
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getTel())){
                presta.setString(count, sellerModel.getTel());
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getType())){
                presta.setString(count, sellerModel.getType());
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getDistrict())){
                presta.setString(count, sellerModel.getDistrict());
                count++;
            }
            if(!CommonUtil.strIsEmpty(sellerModel.getAddress())){
                presta.setString(count, "%"+sellerModel.getAddress()+"%");
                count++;
            }

            if(CommonUtil.strIsEmpty(sellerModel.getPerTo())){
                presta.setString(count, sellerModel.getPerFrom());
                count++;
            } else {
                presta.setString(count, sellerModel.getPerFrom());
                count++;
                presta.setString(count, sellerModel.getPerTo());
                count++;
            }

            re =presta.executeQuery();

            if(re.next()){
                return re.getInt(1);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                re.close();


            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        return -1;
    }

    /**
     * 根据ID获取商家信息
     * @param id
     * @return
     */
    public List<SellerModel> getSellerById(String id){

        PreparedStatement presta=null;
        ResultSet re=null;
        try{
            ConnectionUtil.getConn().setAutoCommit(true);

            String sql=" select id,name,tel,type,district ,address,per,del_flg,create_time,update_time,comments  from t_seller where id = ? ";
            presta = ConnectionUtil.getConn().prepareStatement(sql);
            presta.setString(1,id);
            re=presta.executeQuery();
            List<SellerModel> lst = resultToList(re);
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
     * 查询所有列后转换为对应的集合
     * @param rs
     * @return
     */
    private List<SellerModel> resultToList(ResultSet rs){
        List<SellerModel> sellerModels = new ArrayList<SellerModel>();
        SellerModel sellerModel = null;
        try {
            if(rs == null){
                return sellerModels;
            }
            while(rs.next()){
                sellerModel = new SellerModel();
                sellerModel.setId(rs.getString("id"));
                sellerModel.setName(rs.getString("name"));
                sellerModel.setTel(rs.getString("tel"));
                sellerModel.setType(rs.getString("type"));
                sellerModel.setDistrict(rs.getString("district"));
                sellerModel.setAddress(rs.getString("address"));
                sellerModel.setPer(rs.getString("per"));
                sellerModel.setDelFlg(rs.getString("del_flg"));
                sellerModel.setCreateTime(rs.getString("create_time"));
                sellerModel.setUpdateTime(rs.getString("update_time"));
                sellerModel.setComments(rs.getString("comments"));
                sellerModels.add(sellerModel);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sellerModels;
    }
}

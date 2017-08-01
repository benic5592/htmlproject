package cn.com.benic.service;

import cn.com.benic.dao.SellerDao;
import cn.com.benic.model.SellerModel;
import cn.com.benic.util.CommonConstants;
import cn.com.benic.util.CommonUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */
public class SellerService {

    private SellerDao dao;

    public SellerService() {

        dao  = new SellerDao();
    }

    /**
     * 保存商家
     * @param sellerModel
     * @return
     */
    public boolean editSeller(SellerModel sellerModel){
        int count=0;
        if(CommonUtil.strIsEmpty(sellerModel.getId())){
            sellerModel.setId(CommonUtil.createId());
            sellerModel.setDelFlg(CommonConstants.DELETE_FLG_NOT);
            sellerModel.setCreateTime(String.valueOf(System.currentTimeMillis()));
            sellerModel.setUpdateTime(String.valueOf(System.currentTimeMillis()));
            count = dao.createSeller(sellerModel);

        } else {

            sellerModel.setUpdateTime(String.valueOf(System.currentTimeMillis()));
            count = dao.updateSeller(sellerModel);
        }

        if(count > 0){
            return true;
        } else {
            return false;
        }

    }

    /**
     * 删除商家(逻辑删除)
     * @param id
     * @return
     */
    public boolean deleteSeller(String id){

        int  count = dao.deleteSeller(id);

        if(count > 0){
            return true;
        } else {
            return false;
        }

    }

    /**
     * 查找商家
     */
    public List<SellerModel> getSellerList(SellerModel sellerModel, String page, String linage){


        if(CommonUtil.strIsEmpty(sellerModel.getPerFrom())){
            sellerModel.setPerFrom("0");
        }

        int begin = (CommonUtil.strToInt(page) -1) * CommonUtil.strToInt(linage);
        int end = begin + CommonUtil.strToInt(linage);
        return dao.getSellerList(sellerModel,begin,end);

    }

    /**
     * 查找商家总数
     */
    public int getSellerTotalCount(SellerModel sellerModel, String linage){
        if(CommonUtil.strIsEmpty(sellerModel.getPerFrom())){
            sellerModel.setPerFrom("0");
        }

        int totalCount = dao.getSellerListTotalCount(sellerModel);

        int totalPage = 1;
        int step = CommonUtil.strToInt(linage);
        if(step == 0){
            step = 1;
        }
        if(totalCount % step == 0){

            totalPage = totalCount / step;
        } else {

            totalPage = (totalCount / step) + 1;
        }
        if(totalPage == 0){
            totalPage = 1;
        }

        return totalPage;

    }

    /**
     * 通过ID查找商家
     * @param id
     * @return
     */
    public SellerModel getSellerSelectById(String id){

        List<SellerModel> lst = dao.getSellerById(id);
        if(null == lst || lst.isEmpty()){
            return new SellerModel();
        } else {
            return lst.get(0);
        }
    }
}

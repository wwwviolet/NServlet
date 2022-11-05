package web.fruit.DAO.impl;


import web.fruit.DAO.FruitDAO;
import web.fruit.Pojo.Fruit;
import web.myssm.baseDAO.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {


    public boolean addFruit(Fruit fruit) {
        String sql = "insert into fruit values(0,?,?,?,?)";
        int count = executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark()) ;
        //insert语句返回的是自增列的值，而不是影响行数
        //System.out.println(count);
        return count>0;
    }

    @Override
    public void update(Fruit fruit) {
        String sql = "update fruit set fname=?,price=?,fcount=?,remark=? where fid=?";
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    @Override
    public void delFruit(Integer fid) {
        super.executeUpdate("delete from fruit where fid=?", fid);
    }

    @Override
    public int getFruitCount() {
        Object o = super.executeComplexQuery("select count(*) from fruit")[0];
        Long countL = (Long)o;
        Integer count = Integer.valueOf(countL.toString());
        return count;
    }

    @Override
    public int getFruitCount(String keyword) {
        Object o = super.executeComplexQuery("select count(*) from fruit")[0];
        Long countL = (Long)o;
        Integer count = Integer.valueOf(countL.toString());
        return count;
    }


    @Override
    public List<Fruit> getFruitList(Integer pageNo) {
        //SELECT * FROM fruit LIMIT (pageNo-1)*5,5;
        //(pageNo-1)*5,5
        return super.executeQuery("select * from fruit limit ?,5",(pageNo-1)*5);
    }

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return super.executeQuery("select * from fruit where fname like ? or remark like ? limit ?,5","%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);
    }

    @Override
    public List<Fruit> getFruitList() {
        return super.executeQuery("select * from fruit");
    }



    @Override
    public Fruit getFruitByFid(Integer fid) {
        return super.load("select * from fruit where fid=?", fid);
    }

}

package web.fruit.DAO;


import web.fruit.Pojo.Fruit;

import java.util.List;

public interface FruitDAO {

    // get designated page All data from FruitList
    // one page list five data
    //获取指定页码上的库存列表信息,每页显示5条
    List<Fruit> getFruitList(Integer pageNo);
    //根据关键字查询
    List<Fruit> getFruitList(String keyword,Integer pageNo);

    //get All data from Fruit in a list
    List<Fruit> getFruitList();

    //find the fruit's date by fid
    Fruit getFruitByFid(Integer fid);


    //add new Fruit' data
    boolean addFruit(Fruit fruit);

    //modify Fruit's data from Database
    void update(Fruit fruit);

    //delete Fruit's data by fid
    void delFruit(Integer fid);


    //select data's count from database
    //查询库存总记录条数
    int getFruitCount();
    int getFruitCount(String keyword);

}

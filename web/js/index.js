//表示当调用该方法时,对某个url进行跳转(发请求)
// function delFruit(fid){
//     //confirm有确认和取消两个按钮
//     if (confirm('是否确认删除?')){
//         //表示给地址栏附上后面的值
//         window.location.href='del.do?fid='+fid;
//
//     }
// }
function delFruit(fid){
    //confirm有确认和取消两个按钮
    if (confirm('是否确认删除?')){
        //表示给地址栏附上后面的值
        window.location.href='fruit.do?fid='+fid+'&operateWeb=del';

    }
}

// function page(pageNo){
//     window.location.href="index?pageNo="+pageNo;
// }

function page(pageNo){
    //默认是找index方法
    window.location.href="fruit.do?pageNo="+pageNo;
}
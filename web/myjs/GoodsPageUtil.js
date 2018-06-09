//分页处理初始信息
var curPage=1;
var size=5;
var pageNum=1;//初始化总页面信息

//上一页
function getLastPage(){
    if(curPage<=1){
        alert("没有上一页了");
        return false;
    }else{
        curPage-=1;
    }
    getGoodsList(size,curPage);
}
//下一页
function getNextPage(){
    if(curPage>=pageNum){
        alert("没有下一页了");
        return false
    }else{
        curPage+=1;
    }
    getGoodsList(size,curPage);
}
//跳转到某一页
function goPage(goal){
    if(goal>pageNum||goal<1){
        return false;
    }else{
        curPage=goal;
        getGoodsList(size,curPage);
    }
}

//发送xhr请求
function getGoodsList(pageSize,indexPage){
    var url="list.goods?pageSize="+pageSize+"&pageIndex="+indexPage;
    $.post(url,null,callback);
}
//分页回调函数
function callback(data){//list.goods请求被响应
    if(data!=null){
        //清空table数据
        $("tr:has(td)").remove();
        var goodsInfo=eval(data);
        //取出总页面数
        pageNum=goodsInfo.pageNum;
        //取出总行数
        var totalColumn=goodsInfo.totalColumn;
        $("#label_columns").html("共"+totalColumn+"条商品信息");
        //取出商品信息
        var goodsList=goodsInfo.goodsList;
        //将goodsList数组中的对象填充到表格里
        var table=$("#table_goods");
        $.each(goodsList,function(index,goods){
            table.append($('<tr/>')
                .append($('<td/>').html(goods.goodsId))
                .append($('<td/>').html(goods.goodsName))
                .append($('<td/>').html(goods.goodsPrice))
                .append($('<td/>').html(goods.goodsType))
                .append($('<td/>').html(goods.goodsAmount))
                .append($('<button class="templatemo-blue-button width-100" onclick="addToOrders()">添加</button>'))
            );
        });
        //将页面列表填充到下拉框
        var selElement=$("#sel_page")
        var OptionEle=selElement.find("option");
        OptionEle.remove();
        for(i=1;i<=pageNum;i++){
            if(i==curPage){
            selElement.append($("<option selected='selected' value='"+i+"'>"+i+"</option>"));
            }else{
            selElement.append($("<option value='"+i+"'>"+i+"</option>"));
            }
        }

    }
}
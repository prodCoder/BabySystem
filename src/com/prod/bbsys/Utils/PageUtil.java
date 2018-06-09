package com.prod.bbsys.Utils;

import java.util.List;

/**
 * @ProjectName: BabySystem
 * @Package: com.prod.bbsys.Utils
 * @ClassName: PageUtil
 * @Description: 分页处理工具类
 * @Author: prod
 * @CreateDate: 2018/6/9 13:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/6/9 13:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PageUtil {
    private int pageSize;//页面显示行数
    private int pageNum;//页面总数
    private int totalColumn;//总行数
    private int index;//当前页面
    private List data;//当前页面显示的数据

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        if(totalColumn%pageSize==0){
            pageNum=totalColumn/pageSize;
        }else{
            pageNum=totalColumn/pageSize + 1;
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalColumn() {
        return totalColumn;
    }

    public void setTotalColumn(int totalColumn) {
        this.totalColumn = totalColumn;
    }

    public int getIndex() {
        if(index>pageNum){
            index=pageNum;
        }else if(index<1){
            index=1;
        }
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}

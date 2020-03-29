package com.it.cn.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.cn.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Stock extends BaseEntity<Stock> implements Serializable {
    private String id;
    @Excel(name="药品编号",orderNum="1",width = 18)
    private String mid;   //库存表ID
    @Excel(name="药品名称",orderNum="2",width = 18)
    private String mname;  //药品名称
    @Excel(name="药品价格",orderNum="3",width = 18)
    private double mprice;  //药品价格
    @Excel(name="药品数量",orderNum="4",width = 18)
    private int mnum;   //药品数量
    @Excel(name="供应商",orderNum="5",width = 18)
    private String spid;  //供应商号

    /*表格显示格式*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /*格式化日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stime;  //进货时间

    /*表格显示格式*/
    @Excel(name="有效期至",orderNum="6",width = 18)
    @JsonFormat(pattern = "yyyy-MM-dd")
    /*格式化日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ytime;  //有效期
    @Excel(name="药品类别",orderNum="7",width = 18)
    private String stype;
    @Excel(name="是否过期",orderNum="8",width = 18)
    private String overdue; //是否过期
    private int status;

    public Stock(){

    }

    public Stock(String mname, double mprice, int mnum, String spid, Date stime, Date ytime, String stype) {
        this.mname = mname;
        this.mprice = mprice;
        this.mnum = mnum;
        this.spid = spid;
        this.stime = stime;
        this.ytime = ytime;
        this.stype = stype;
    }
}

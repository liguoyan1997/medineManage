package com.it.cn.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.it.cn.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Salesorder extends BaseEntity<Salesorder> implements Serializable {
    private String soid;
    @Excel(name="药品编号",orderNum="1",width = 18)
    private String mid;
    @Excel(name="药品名",orderNum="2",width = 18)
    private String mname;
    @Excel(name="数量",orderNum="3",width = 18)
    private int snum;
    @Excel(name="单价",orderNum="4",width = 18)
    private double price;
    @Excel(name="操作人",orderNum="5",width = 18)
    private String person;
    private int status;

    public Salesorder(String mid, String mname, int snum, double price,String person) {
        this.mid = mid;
        this.mname = mname;
        this.snum = snum;
        this.price = price;
        this.person = person;
    }

    public Salesorder(){

    }

}

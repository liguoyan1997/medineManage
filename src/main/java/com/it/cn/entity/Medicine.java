package com.it.cn.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.cn.base.BaseDao;
import com.it.cn.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * 123 Entity
 *
 * @author 123
 * @date 2019-08-18 16:32:41
 */
@Data
@Entity
public class Medicine extends BaseEntity<Medicine> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Excel(name="药品编号",orderNum="1",width = 18)
    private String mid;

    /**
     *
     */
    @Excel(name="药品名称",orderNum="2",width = 18)
    private String mname;

    /**
     *
     */
    private String mshortname;

    /**
     *
     */
    @Excel(name="药品规格",orderNum="3",width = 18)
    private String mnors;

    /**
     *
     */
    @Excel(name="药品场地",orderNum="4",width = 18)
    private String marea;

    /**
     *
     */
    private String mlot;

    /**
     *
     */
    @Excel(name="进价",orderNum="5",width = 18)
    private double minpri;

    /**
     *
     */
    @Excel(name="批发价",orderNum="6",width = 18)
    private double mwpri;

    /**
     *
     */
    @Excel(name="零售价",orderNum="7",width = 18)
    private double mpri;

    @Excel(name="数量",orderNum="8",width = 18)
    private int mnum;
    /**
     *
     */
    @Excel(name="供应商",orderNum="9",width = 18)
    private String mcon;

    /**
     *
     */
    @Excel(name="类别",orderNum="10",width = 18)
    private String mcate;

    /*表格显示格式*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /*格式化日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jtime;

    /*表格显示格式*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    /*格式化日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date musetime;

    /**
     *
     */
    private String memo;
    /**
     * 药状态
     */
    private Integer status;

}

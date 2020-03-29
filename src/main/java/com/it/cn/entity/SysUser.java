package com.it.cn.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.cn.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity   //实现类的持久化   JPA
@NoArgsConstructor
//@Document(indexName = "sys_user",type = "content")
public class SysUser extends BaseEntity<SysUser> implements Serializable {

    public SysUser(String sysid, @Length(min = 3, max = 20, message = "用户名只能输入3到20个字符") @Pattern(regexp = "^[^[0-9a-zA-Z]+$\\*]*$", message = "用户名限制：包含文字、字母和数字") String sysuserLogin) {
        this.sysid = sysid;
        this.sysuserLogin = sysuserLogin;
    }
    /*主键*/
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)  //定义主键的生成策略
    private String sysid;
    @Length(min = 3, max = 20, message = "用户名只能输入3到20个字符")
    @Pattern(regexp = "^[^[0-9a-zA-Z]+$\\*]*$", message = "用户名限制：包含文字、字母和数字")
    private String sysuserLogin;
    @Pattern(regexp = "^[\\w]{6,12}$", message = "密码限制：包括字母、数字、下划线")
    @Length(min=6, max = 12,message="密码长度只能输入6到12个字符")
    private String sysuserPassWord;
    @Length(min = 2, max = 4, message = "真实姓名只能输入2到6个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5\\*]*$", message = "真实姓名限制：只能输入汉字")
    private String sysuserName;
    private Integer sysuserAge;
    private String sysFile;
    private String roleId;
    private SysRole role;
    private String status;
    private String code;

    /*表格显示格式*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /*格式化日期*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date syscreateTime;
//    private String isRememberMe;

    public String getid(String sysid,String sysuserName){
        return sysid + "-"  + sysuserName;
    }
}

package com.it.cn.entity;

import lombok.Data;

/***
 *
 * 文件发送时所需要的数据格式
 * layim
 * @author lm
 */
@Data
public class Upload{

    /**
     * 上传是否成功
     */
    private Integer code;
    /**
     * 上传消息
     */
    private String msg;
    /**
     * 上传文件名称
     */
    private String fileName;
    /**
     * 地址
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件类型
     */
    private String fileType;


}

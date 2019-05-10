package com.panshi.hujin2.message.domain.qo;

import com.panshi.hujin2.base.domain.page.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * create by shenjiankang on 2018/6/12 10:17
 */
@Data
public class BaseQO implements Serializable {

    private static final long serialVersionUID = 3787118815607486400L;

    private Date createDateMin;
    private Date createDateMax;
    private Date modifyDateMin;
    private Date modifyDateMax;
    private boolean desc = false;//是否降序，默认升序
    private Page page = new Page();

//    private String createUser;
//    private String modifiedUser;
//    private Integer orderBy;//排序依据。这个值写在cn.com.hq.ordermanage.constant.OrderByConstant 中


    public BaseQO() {
        this.page = new Page();
    }
}

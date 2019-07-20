package com.liu.community.dao;

import com.liu.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @ProjectName: community
 * @Package: com.liu.community.dao
 * @ClassName: LoginTicketMapper
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/20 15:07
 * @Version: 1.0
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
    "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")//自动生成主键id
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
        "select id,user_id,ticket,status,expired ","from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);


}

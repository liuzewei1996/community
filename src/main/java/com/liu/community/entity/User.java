package com.liu.community.entity;

import java.util.Date;

/**
 * @ProjectName: community
 * @Package: com.liu.community.entity
 * @ClassName: user
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/17 9:27
 * @Version: 1.0
 */
public class User {
    //创建user数据表的实体类；与数据库中user表相对应；
    //在配置文件中声明了 mybatis.type-aliases-package=com.liu.community.entity
    //和mybatis.mapper-locations=classpath:mapper/*.xml；即映射路径及对应的实体类包名都声明好了
    //不需要在xml中什么方法名的包名了
    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail() {
        return email;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", activationCode='" + activationCode + '\'' +
                ", headerUrl='" + headerUrl + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

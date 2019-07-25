package com.liu.community.controller;

import com.liu.community.service.AlphaService;
import com.liu.community.util.CommunityUtil;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @ProjectName: community
 * @Package: com.liu.community.Controller
 * @ClassName: Hello
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/7/13 21:32
 * @Version: 1.0
 */
@Controller
@RequestMapping("/alpha")
public class Hello {

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "hello";
    }

    @Autowired
    public AlphaService alphaService;

    @RequestMapping("/data")
    @ResponseBody
    public String getdata() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletResponse response, HttpServletRequest request){
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();//key-value健值对
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));
        //注：获取请求参数，在URL中使用？后面的为请求参数，使用 & 添加多个请求参数

        //获取响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
                //java8新特性，在try里面，结束后就会自动执行close方法
        ) {
            writer.write("<h1>刘泽伟</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GET请求（向服务器获取资源）；一般默认情况下就是get请求
    //Get是最常用的方法，通常用于请求服务器发送某个资源，而且应该是安全的和幂等的。
    //students?current=1&limit=20  显示students，分页条件在?后面

    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current", required = false, defaultValue = "1")
                                          int current,
                              @RequestParam(name = "limit") int limit){
        //@RequestParam传入请求参数
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //查询id为123的学生：/student/123
    //传递路径中的参数
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }

    //浏览器向服务器提交数据，post请求；浏览器页面中表单中的数据传到服务器
    //定义一个静态页面：在static目录下,其中：<form method="post" action="/community/alpha/student">
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age){
        System.out.println(name);
        System.out.println(age);
        return  "success";
    }
    //定义一个静态页面：在static目录下
    //浏览器端输入：http://localhost:8080/community/html/student.html ； 即可以显示静态页面



    //------------------------------
    //响应html数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
//    @ResponseBody  //默认就是返回html，不用加此注解
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 23);
        mav.setViewName("/demo/view");//在templates目录下的demo下的view.html
        return mav;
    }

    //更简洁的方式：
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {//加一个model对象参数
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";//直接返回路径
    }


    // 响应JSON数据(异步请求)
    // Java对象 -> JSON字符串 -> JS对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }

    //===========================cookie示例=========================
    @RequestMapping(path="/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie的生效范围
        cookie.setPath("community/alpha");
        //设置cookie的生存时间
        cookie.setMaxAge(60*10);
        //发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookieTest(){
//        System.out.println(id);
        return "a student";
    }

    @RequestMapping(path = "/cookie/getC", method = RequestMethod.GET)
    @ResponseBody
    public String getCookieTest1(@CookieValue("code") String code){
        System.out.println(code);
        return "a student";
    }

    @RequestMapping(path = "/student1", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent1(){
        return "a student";
    }

    //==================================================================
    // session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    // ajax示例
    //访问静态页面：http://localhost:8080/community/html/ajax-demo.html
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功!");
        //会传入到ajax-demo文件的function(data)，data就指返回的对象数据（String对象）

        //可以在控制台点击“发送”，
        // 在网页console项中会显示string {“msg”:"操作成功！"， “code”，0} object 0 操作成功
    }





}

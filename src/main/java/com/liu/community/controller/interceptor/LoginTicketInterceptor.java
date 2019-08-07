package com.liu.community.controller.interceptor;

import com.liu.community.entity.LoginTicket;
import com.liu.community.entity.User;
import com.liu.community.service.UserService;
import com.liu.community.util.CookieUtil;
import com.liu.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //最开始就要获取请求的cookie中的ticket，后面用ticket去查找用户，有的话就暂存
        // 第一步通过cookie得到ticket；从cookie中获取凭证：
        //这个方法是由接口定义的，其中参数不能随便加；所以不能在这里用@Cookie value注解了
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户
                //每个浏览器访问服务器，服务器会产生一个独立的线程来处理请求；服务器是在多线程的环境；
                // 所以存放用户如果仅仅是在一个类定义的变量中，会出现问题
                hostHolder.setUser(user);//存放到了线程对应的对象中去了

                //################################################################################
                // 新增：构建用户认证的结果,并存入SecurityContext,以便于Security进行授权.
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), userService.getAuthorities(user.getId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //使用user，将user存到model里，在模板引擎之前用
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();//模板引擎都执行完之后，整个请求结束，清掉hostHolder

        //################################################################################
        SecurityContextHolder.clearContext();

    }
}

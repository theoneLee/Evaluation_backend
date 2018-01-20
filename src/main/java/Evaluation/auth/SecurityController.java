package Evaluation.auth;

import Evaluation.entity.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SecurityController {
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    private RequestCache requestCache=new HttpSessionRequestCache();//这个类可以保存之前的路径，在重定向到其他url时可以通过他拿到之前引发跳转的请求
    /**
     * 当需要身份认证时跳转到这里（在SecurityConfig上的loginPage使用这个RequestMapping路径）
     * 为html请求，引导到spring security登陆页
     * 为json请求，返回一个状态，叫app自己去处理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Response requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest=requestCache.getRequest(request,response);

        if (savedRequest!=null){
            String targetUrl=savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                redirectStrategy.sendRedirect(request,response,"/sign-in.html");//跳转给本来在SecurityConfig配置的loginPage去处理这个请求
            }
        }
        return new Response().failure("引导用户到登陆页");//savedRequest为空就是json请求，直接通过该controller返回一个401未授权
    }

    @GetMapping(value = "/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Response sessionInvalid(){
        return new Response().failure("session失效");
    }
}

package Evaluation.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * oauth2 认证服务器，可以提供四种授权模式
 * 授权码模式： ?response_type=code&client_id=lee&redirect_url=http://xxx&scope=all
   密码模式：直接访问/oauth/token 带上相应的请求体参数和请求头（目前在安卓端用这种）
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig {
}

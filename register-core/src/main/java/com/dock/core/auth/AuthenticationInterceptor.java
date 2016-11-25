//package com.dock.core.auth;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.dock.core.response.ResponseResult;
//import com.dock.core.utils.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import org.springframework.web.util.WebUtils;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//import java.util.StringTokenizer;
//
///**
// * Created by gaojian on 2016/11/18.
// */
//public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
//    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
//    @Autowired
//    private RedisCache redisCache;
//    @Autowired
//    private MMQProducer mmqProducer;
//
//    public AuthenticationInterceptor() {
//    }
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String uuid_ = StringUtils.getUUID();
//        request.setAttribute("request_id", uuid_);
//        request.setAttribute("request_start", Long.valueOf(System.currentTimeMillis()));
//        logger.debug("^_^ authentication before hander.{}", uuid_);
//        return this.interceptor(request, response, handler);
//    }
//
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
//        logger.debug("^_^ authentication after hander.{}", (String)request.getAttribute("request_id"));
//
//        try {
//            this.sendOperationLog(request);
//        } catch (Exception var6) {
//            logger.error("^-^ Warning.Warning   save log operation error.");
//            logger.error(var6.getMessage(), var6);
//        }
//
//    }
//
//    protected boolean interceptor(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) {
//        logger.debug("AuthenticationInterceptor preHandle invoked.");
//        String method = servletRequest.getMethod();
//        logger.debug("Request method is {} .", method);
//        if("OPTIONS".equalsIgnoreCase(method)) {
//            logger.debug("Request method is {} ignore.", method);
//            servletResponse.setStatus(200);
//            return false;
//        } else {
//            String uri = servletRequest.getRequestURI();
//            logger.debug("RequestURI {}", uri);
//            StringBuffer url = servletRequest.getRequestURL();
//            logger.debug("RequestURL {}", url);
//            String domain = url.delete(url.length() - uri.length(), url.length()).append("/").toString();
//            logger.debug("Domain {}", domain);
//            boolean success = true;
//            boolean skip = false;
//            AllowAnonymous allowAnonymous = (AllowAnonymous)((HandlerMethod)handler).getMethod().getAnnotation(AllowAnonymous.class);
//            if(allowAnonymous != null) {
//                skip = true;
//            }
//
//            AvoidDuplicate justOnce = (AvoidDuplicate)((HandlerMethod)handler).getMethod().getAnnotation(AvoidDuplicate.class);
//            String userid;
//            String username;
//            if(justOnce != null) {
//                userid = StringUtils.msNull(servletRequest.getParameter("ticket"));
//                if(StringUtils.isEmpty(userid)) {
//                    success = false;
//                    this.abortWith(servletResponse, 10108);
//                }
//
//                username = StringUtils.msNull(this.redisCache.get(userid));
//                if(StringUtils.isNotEmpty(username)) {
//                    this.redisCache.del(userid);
//                } else {
//                    success = false;
//                    this.abortWith(servletResponse, 10109);
//                }
//            }
//
//            userid = "184c58cb65ee11e68fe10242ac11000a";
//            username = "alien";
//            if(!skip) {
//                String accessToken = null;
//                String tokenValue;
//                if(success) {
//                    tokenValue = servletRequest.getHeader("Authorization");
//                    logger.debug("http heads is {}", tokenValue);
//                    if(StringUtils.isNotEmpty(tokenValue)) {
//                        if(tokenValue.startsWith("Bearer")) {
//                            accessToken = tokenValue.substring(7);
//                            if(StringUtils.isEmpty(accessToken)) {
//                                success = false;
//                                logger.debug("^-^ bearer authorization token is null, request abort...");
//                                this.abortWith(servletResponse, 10005);
//                            }
//
//                            logger.debug("Get access token from auth header, {}", accessToken);
//                        } else if(tokenValue.startsWith("Basic")) {
//                            String token = tokenValue.substring(6);
//                            logger.debug("Get encoded username & password from auth header, {}", token);
//                            if(StringUtils.isEmpty(this.redisCache.get(token))) {
//                                success = false;
//                                logger.debug("^-^ basic authorization from redis is null, request abort...");
//                                this.abortWith(servletResponse, 10005);
//                            }
//                        }
//                    }
//                }
//
//                if(success) {
//                    if(StringUtils.isEmpty(accessToken)) {
//                        accessToken = StringUtils.msNull(servletRequest.getParameter("access_token"));
//                        logger.debug("Get access token from request parameters, {}", accessToken);
//                    }
//
//                    if(StringUtils.isNotEmpty(accessToken)) {
//                        tokenValue = this.redisCache.get(accessToken);
//                        logger.debug("^_^ get token value from redis by {}, {}", accessToken, tokenValue);
//                        JSONObject token1 = JSON.parseObject(tokenValue);
//                        if(token1 != null) {
//                            userid = token1.getString("id");
//                            username = token1.getString("username");
//                            logger.debug("###### set current user token into servlet request attribute.{}", "user_token");
//                            servletRequest.setAttribute("access_token", accessToken);
//                            servletRequest.setAttribute("user_token_value", tokenValue);
//                            servletRequest.setAttribute("user_token", token1);
//                            servletRequest.setAttribute("user_id", userid);
//                            servletRequest.setAttribute("user_name", username);
//                            HttpSession session = servletRequest.getSession(true);
//                            session.setAttribute("uid", userid);
//                            session.setAttribute("uname", username);
//                            logger.debug("Get username from redis cache, {} : {}", accessToken, username);
//                            if(StringUtils.isEmpty(username)) {
//                                success = false;
//                                logger.debug("o_o user name is null, request abort...");
//                                this.abortWith(servletResponse, 10005);
//                            } else if(!"c7297c6f271811e6a5140242ac110000".equals(userid)) {
//                                String uri_ = method + uri.replaceAll("/([a-z0-9]{32})", "/*");
//                                logger.debug("^_^ {} clean uri {} from {}", uri_, uri);
//                                List resources = this.redisCache.getList("resource_access" + userid);
//                                if(resources == null || !resources.contains(uri_)) {
//                                    logger.debug("o_o {} has no rights of {}, request abort...", userid, uri_);
//                                }
//                            }
//                        } else {
//                            success = false;
//                            logger.error("o_o get token value from redis by {} is null, request abort...", accessToken);
//                            this.abortWith(servletResponse, 10005);
//                        }
//                    } else {
//                        success = false;
//                        logger.debug("o_o access token is null, request abort...");
//                        this.abortWith(servletResponse, 10005);
//                    }
//                }
//            }
//
//            servletRequest.setAttribute("request_method", method);
//            servletRequest.setAttribute("request_uri", uri);
//            servletRequest.setAttribute("request_domain", domain);
//            return success;
//        }
//    }
//
//    private void abortWith(HttpServletResponse response, int code) {
//        logger.debug("^_^ read message from redis cache in authentication interceptor.");
//        String message = "";
//
//        try {
//            message = this.redisCache.msg(code);
//        } catch (Exception var8) {
//            logger.error("o_o get message from redis cache failed. {}", var8.getMessage());
//        }
//
//        ResponseResult responseResult = ResponseResult.getResponse(Integer.valueOf(code)).setMessage(message);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter out = null;
//
//        try {
//            out = response.getWriter();
//            out.append(JSON.toJSONString(responseResult));
//            out.flush();
//        } catch (IOException var7) {
//            logger.error(var7.getMessage(), var7);
//        }
//
//    }
//
//    protected boolean checkAccessToken(String accessToken) throws IOException {
//        URL url = new URL("http://localhost:8081/oauth/accessToken/" + accessToken);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.disconnect();
//        return 200 == conn.getResponseCode();
//    }
//
//    protected boolean checkUserLogin(String encoded) {
//        String decoded = new String(Base64.decodeBase64(encoded.getBytes()), Constants.CHARACTER_SET);
//        logger.debug("decode username and password is {}", decoded);
//        if(decoded.indexOf(":") > -1) {
//            StringTokenizer tokenizer = new StringTokenizer(decoded, ":");
//            String username = tokenizer.nextToken();
//            String password = tokenizer.nextToken();
//            logger.debug("username is {}, password is {}", username, password);
//            if("admin".equals(username) && "123456".equals(password)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    protected void sendOperationLog(HttpServletRequest request) {
//        String userAgentHeader = request.getHeader("User-Agent");
//        logger.debug("Request Header User-Agent is {}", userAgentHeader);
//        JSONObject oplog = new JSONObject();
//        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
//        String userid = (String)request.getAttribute("user_id");
//        String username = (String)request.getAttribute("user_name");
//        String method = (String)request.getAttribute("request_method");
//        String uri = (String)request.getAttribute("request_uri");
//        String domain = (String)request.getAttribute("request_domain");
//        String requestId = (String)request.getAttribute("request_id");
//        long requestStart = ((Long)request.getAttribute("request_start")).longValue();
//        oplog.put("requestId", requestId);
//        oplog.put("userid", userid);
//        oplog.put("username", username);
//        oplog.put("useragent", userAgentHeader);
//        oplog.put("ip", WebUtils.getRemoteHost(request));
//        oplog.put("method", method);
//        oplog.put("uri", uri);
//        oplog.put("domain", domain);
//        oplog.put("os", userAgent.getOperatingSystem().name());
//        oplog.put("browser", userAgent.getBrowser().name());
//        if(userAgent.getBrowserVersion() != null) {
//            oplog.put("browserVersion", userAgent.getBrowserVersion().getVersion());
//        }
//
//        long requestEnd = System.currentTimeMillis();
//        oplog.put("requestStart", Long.valueOf(requestStart));
//        oplog.put("requestEnd", Long.valueOf(requestEnd));
//        oplog.put("requestTime", Long.valueOf(requestEnd - requestStart));
//        logger.debug("Send Operation Log MQ, {}", oplog.toJSONString());
//        this.mmqProducer.sendMessage(LOGGING.OPS_LOGGING.toString(), oplog.toJSONString());
//    }
//}

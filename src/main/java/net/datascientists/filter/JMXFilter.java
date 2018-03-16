package net.datascientists.filter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.datascientists.constants.WSConstants;
import net.datascientists.service.base.BaseService;
import net.datascientists.service.security.TokenManager;
import net.datascientists.vo.HeaderVO;
import net.datascientists.vo.JMXLogVO;

public class JMXFilter extends GenericFilterBean
{

    @Autowired
    @Qualifier("JMXService")
    private BaseService<JMXLogVO> service;
    @Autowired
    private TokenManager tokenManager;
    private UrlPathHelper urlPathHelper = new UrlPathHelper(); 

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = asHttp(request);
//        HttpServletResponse httpResponse = asHttp(response);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        JMXLogVO vo = new JMXLogVO();
        String username = httpRequest.getHeader(WSConstants.AUTH_USERNAME_PROP);
//        String password = httpRequest.getHeader(WSConstants.AUTH_PWD_PROP);
        String token = httpRequest.getHeader(WSConstants.AUTH_TOKEN);

        String resourcePath = urlPathHelper
            .getPathWithinApplication(httpRequest);
        vo.setFunction(resourcePath);
        vo.setDeleted(0);
        setGetOrPostParameters(vo, httpRequest);
        String headers = buildHeaders(httpRequest);
        vo.setHeader(headers);
        if (!StringUtils.isEmpty(token))
        {
            vo.setSessionId(token.substring(0, token.indexOf("."))+tokenManager.parseUsernameFromToken(token)+tokenManager.parseExpiryFromToken(token));
            vo.setUserId(tokenManager.parseUsernameFromToken(token));
        }else{
            vo.setSessionId(token);
            vo.setUserId(username);
        }
        service.save(vo);
        chain.doFilter(request, response);
    }

    private void setGetOrPostParameters(JMXLogVO vo, HttpServletRequest httpRequest)
    {
        if ("GET".equals(httpRequest.getMethod()))
        {
            vo.setGetParameters(httpRequest.getParameterMap().toString());
        }
        else
        {
            vo.setPostParameters(httpRequest.getParameterMap().toString());
        }
    }

    private String buildHeaders(HttpServletRequest httpRequest)
    {
        Gson gson = new Gson();
        List<HeaderVO> list = new ArrayList<>();
        @SuppressWarnings("unchecked")
        Enumeration<String> headerNames = httpRequest.getHeaderNames();

        if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    list.add(new HeaderVO(name, httpRequest.getHeader(name)));
                }
        }
        Type listType = new TypeToken<List<HeaderVO>>() {}.getType();
        return gson.toJson(list,listType);
    }
    
    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }
}

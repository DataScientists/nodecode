package net.datascientists.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.datascientists.service.base.BaseService;
import net.datascientists.service.security.TokenManager;
import net.datascientists.vo.JMXLogVO;

@Provider
@PreMatching
public class JMXFilter implements ContainerRequestFilter
{

    @Autowired
    @Qualifier("JMXService")
    private BaseService<JMXLogVO> service;
    @Autowired
    private TokenManager tokenManager;


    @Override
    public void filter(ContainerRequestContext ctx) throws IOException
    {
        JMXLogVO vo = new JMXLogVO();
        vo.setFunction(ctx.getUriInfo().getPath());
        vo.setDeleted(0);
        if ("GET".equals(ctx.getRequest().getMethod()))
        {
            vo.setGetParameters(ctx.getUriInfo().getRequestUri().toString()
                .replaceAll(ctx.getUriInfo().getPath(), "").replaceAll(ctx.getUriInfo().getBaseUri().toString(), "").toString());
        }
        else
        {
            vo.setPostParameters(ctx.getPropertyNames().toString());
        }
        vo.setHeader(ctx.getHeaders().toString());
        String token = ctx.getHeaderString("x-auth-token");
        if (!StringUtils.isEmpty(token))
        {
            vo.setSessionId(token);
            vo.setUserId(tokenManager.parseUsernameFromToken(token));
        }
        service.save(vo);
    }
}

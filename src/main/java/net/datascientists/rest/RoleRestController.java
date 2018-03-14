package net.datascientists.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;

import net.datascientists.mapper.RoleMapper;
import net.datascientists.rest.base.BaseRestController;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.RoleVO;

@Path("/role")
public class RoleRestController implements BaseRestController<RoleVO>{

	@Autowired
	@Qualifier("RoleService")
	private BaseService<RoleVO> service;
	
	@GET
    @Path(value = "/list")
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
	@Override
    public Response list(){
    	List<RoleVO> list = new ArrayList<RoleVO>();
		try{
			list = (List<RoleVO>) service.list();
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
    }

    @Override
    public Response listDeleted(){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response findById(Long id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response save(RoleVO json){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteSoft(RoleVO json){
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteHard(RoleVO json){
        // TODO Auto-generated method stub
        return null;
    }
	
}

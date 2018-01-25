package net.datascientists.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import net.datascientists.service.UserService;
import net.datascientists.vo.UserVO;

@Path("/admin")
public class AdminRestController implements BaseRestController<UserVO>{

	@Autowired
	private UserService service;
	
	@GET
    @Path(value = "/getUserRoles")
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
	@Override
    public Response list() {
    	List<UserVO> list = new ArrayList<UserVO>();
		try{
			list = service.list();
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
    }

    @Override
    public Response listDeleted()
    {
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
    public Response save(UserVO json)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response update(UserVO json)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteSoft(UserVO json)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteHard(UserVO json)
    {
        // TODO Auto-generated method stub
        return null;
    }
	
}

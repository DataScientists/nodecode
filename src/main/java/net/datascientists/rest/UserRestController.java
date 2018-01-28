package net.datascientists.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import net.datascientists.rest.base.BaseRestController;
import net.datascientists.service.base.BaseService;
import net.datascientists.vo.UserVO;

@Path("/admin")
public class UserRestController implements BaseRestController<UserVO>{

	@Autowired
	@Qualifier("UserService")
	private BaseService<UserVO> service;
	
	
	@GET
    @Path(value = "/list")
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
	@Override
    public Response list(){
    	List<UserVO> list = new ArrayList<UserVO>();
		try{
			list = (List<UserVO>) service.list();
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
    }
	
	@GET
    @Path(value = "/listDeleted")
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Response listDeleted(){
    	List<UserVO> list = new ArrayList<UserVO>();
		try{
			list = (List<UserVO>) service.listDeleted();
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
    }

	@GET
	@Path(value="/findById")
	@Produces(value=MediaType.APPLICATION_JSON_VALUE)
	@Override
	public Response findById(@QueryParam("id") Long id) {
		List<UserVO> list = new ArrayList<UserVO>();
		try{
			list = service.find("id",id);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
	}
	
	@GET
	@Path(value="/findByUserName")
	@Produces(value=MediaType.APPLICATION_JSON_VALUE)
	public Response findByUserName(@QueryParam("userName") String userName) {
		List<UserVO> list = new ArrayList<UserVO>();
		try{
			list = service.find("userName",userName);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
	}
	
	@GET
	@Path(value="/findByUserEmail")
	@Produces(value=MediaType.APPLICATION_JSON_VALUE)
	public Response findByEmail(@QueryParam("email") String email) {
		List<UserVO> list = new ArrayList<UserVO>();
		try{
			list = service.find("email",email);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
	}
  
    
    @Path(value = "/save")
  	@POST
  	@Consumes(value = MediaType.APPLICATION_JSON_VALUE)
  	@Produces(value = MediaType.APPLICATION_JSON_VALUE)
  	public Response save(UserVO json) {
  		
  		try {
  			service.save(json);
  			
  		} catch (Throwable e) {
  			e.printStackTrace();
  			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
  		}
  		return Response.ok().build();
  	}
    
    

    @Path(value="/deleteSoft")
	@POST
	@Override
	public Response deleteSoft(UserVO json) {
		try{
			service.deleteSoft(json);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

    @Path(value="/deleteHard")
	@POST
	@Override
	public Response deleteHard(UserVO json) {
		try{
			service.deleteHard(json);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	
}

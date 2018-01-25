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
import net.datascientists.vo.NoteVO;

@Path("/note")
public class NoteRestController implements BaseRestController<NoteVO>{

	@Autowired
	@Qualifier("NoteService")
	private BaseService<NoteVO> service;

	@GET
	@Path(value="/list")
	@Produces(value=MediaType.APPLICATION_JSON_VALUE)
	@Override
	public Response list() {
		List<NoteVO> list = new ArrayList<NoteVO>();
		try{
			list = (List<NoteVO>) service.list();
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
		List<NoteVO> list = new ArrayList<NoteVO>();
		try{
			list = service.find("id",id);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
	}

	@Path(value="/save")
	@POST
    @Consumes(value=MediaType.APPLICATION_JSON_VALUE)
    @Produces(value=MediaType.APPLICATION_JSON_VALUE)
	@Override
	public Response save(NoteVO json) {
		try{
			service.save(json);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@Path(value="/deleteSoft")
	@POST
	@Override
	public Response deleteSoft(NoteVO json) {
		try{
			service.deleteSoft(json);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

    @Override
    public Response listDeleted()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteHard(NoteVO json)
    {
        // TODO Auto-generated method stub
        return null;
    }


}

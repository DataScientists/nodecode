package net.datascientists.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import net.datascientists.service.NoteService;
import net.datascientists.utilities.CommonUtil;
import net.datascientists.vo.NoteVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/note")
public class NoteRestController implements BaseRestController<NoteVO>{

	@Autowired
	private NoteService service;

	@GET
	@Path(value="/getlist")
	@Produces(value=MediaType.APPLICATION_JSON_VALUE)
	@Override
	public Response list() {
		List<NoteVO> list = new ArrayList<NoteVO>();
		try{
			list = service.list();
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
	}

    @GET
    @Path(value="/getlistbyinterview")
    @Produces(value=MediaType.APPLICATION_JSON_VALUE)
    public Response getListByInterview(@QueryParam("interviewId") Long interviewId) {
        List<NoteVO> list = new ArrayList<NoteVO>();
        try{
            list = service.getListByInterview(interviewId);
        }catch(Throwable e){
            e.printStackTrace();
            return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
        }
        return Response.ok(list).build();
    }

	@GET
	@Path(value="/get")
	@Produces(value=MediaType.APPLICATION_JSON_VALUE)
	@Override
	public Response findById(@QueryParam("id") Long id) {
		List<NoteVO> list = new ArrayList<NoteVO>();
		try{
			list = service.findById(id);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok(list).build();
	}

	@Path(value="/create")
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

	@Path(value="/update")
	@POST
    @Consumes(value=MediaType.APPLICATION_JSON_VALUE)
    @Produces(value=MediaType.APPLICATION_JSON_VALUE)
	@Override
	public Response update(NoteVO json) {
		try{
			service.update(json);
		}catch(Throwable e){
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).type("text/plain").entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

	@Path(value="/delete")
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

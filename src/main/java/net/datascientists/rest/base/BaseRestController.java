package net.datascientists.rest.base;

import javax.ws.rs.core.Response;

public interface BaseRestController<T> {

    public Response list();
    
    public Response listDeleted();

    public Response findById(Long id);
    
    public Response save(T json);

    public Response deleteSoft(T json);
    
    public Response deleteHard(T json);
}
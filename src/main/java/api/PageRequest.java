package api;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.NoArgsConstructor;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
@RegisterForReflection
@NoArgsConstructor
public class PageRequest {

    @QueryParam("pageNum")
    @DefaultValue("0")
    public int pageNum;

//    @QueryParam("pageSize")
//    @DefaultValue("20")
    public final int pageSize = 20;

}
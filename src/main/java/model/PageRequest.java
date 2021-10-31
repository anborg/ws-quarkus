package model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
@RegisterForReflection
@NoArgsConstructor
public class PageRequest {

    @QueryParam("pageNum")
    @DefaultValue("1")
    @Min(1)
    public int pageNum;

//    @QueryParam("pageSize")
//    @DefaultValue("20")
    public final int pageSize = 20;

}
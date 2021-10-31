package api;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.ToString;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

@RegisterForReflection
@ToString
public class PageResults<T> {
    public long pageNum;
    public long pageSize;
    public long pagesCount;
    public long hitsCount;
    public List<T> list;
}
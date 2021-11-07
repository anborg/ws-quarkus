package model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.ToString;

import java.util.List;

@RegisterForReflection
@ToString
@Builder
public class PageResults<T> {
    public long pageNum;
    public long pagesCount;
    public long hitsCount;
    public long pageSize;
    public List<T> list;
}
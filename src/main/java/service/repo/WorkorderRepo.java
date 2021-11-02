package service.repo;

import model.PageRequest;
import model.PageResults;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import model.Workorder;
import org.hibernate.jdbc.Work;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WorkorderRepo implements PanacheRepository<Workorder> {

    public PageResults<Workorder> findActionable(Instant sinceDate, PageRequest pageRequest) {
        Map<String, Object> paramsMap = Map.of("sinceDate", sinceDate);
        PanacheQuery<Workorder> query = find("#WorkOrder.findActionable", paramsMap);
        query.page(Page.of(pageRequest.pageNum-1, pageRequest.pageSize));

       var out = PageResults.<Workorder>builder()
                .pageNum(pageRequest.pageNum)
                .pageSize(pageRequest.pageSize)
                .pagesCount(query.pageCount())
                .hitsCount(query.count())
                .list(query.page(Page.of(pageRequest.pageNum-1, pageRequest.pageSize)).list()).build();
       return out;
    }
}
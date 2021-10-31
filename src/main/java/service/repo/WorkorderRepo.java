package service.repo;

import api.PageRequest;
import api.PageResults;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import model.Workorder;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WorkorderRepo implements PanacheRepository<Workorder> {

    public PageResults<Workorder> findActionable(Instant sinceDate, PageRequest pageRequest) {
        Map<String, Object> paramsMap = Map.of("sinceDate", sinceDate);
//        var out = workorderRepo.find("#WorkOrder.findActionable", paramsMap).list();
        // create a query for all actionable
        PanacheQuery<Workorder> query = find("#WorkOrder.findActionable", paramsMap);
        query.page(Page.of(pageRequest.pageNum-1, pageRequest.pageSize));
        int numberOfPages = query.pageCount();
        // get the first page
        List<Workorder> firstPage = query.list();
       var out =  new PageResults<Workorder>();
       out.pageNum = pageRequest.pageNum;
       out.pageSize = pageRequest.pageSize;
       out.pagesCount = query.pageCount();
       out.hitsCount = query.count();
       out.list =query.page(Page.of(pageRequest.pageNum-1, pageRequest.pageSize)).list();
       return out;

    }
    // put your custom logic here as instance methods
}
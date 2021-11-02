package service;

import model.PageRequest;
import io.quarkus.test.junit.QuarkusTest;
import model.Workorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.TestUtil;
import util.Util;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CisServiceTest {
    private static final Logger log = Logger.getLogger(CisServiceTest.class.getName());

    @Inject
    CisService service;

    @Test
    public void wo_happy_path() {
        final Workorder in = TestUtil.workorder.build4Create();
        log.info("Before service persist: \n\n" + in);
        service.save(in);
        final Optional<Workorder> outOpt = service.byId(in.id);
        var out = outOpt.get();
        //log.info("After service persist: \n\n" + out);
        assertNotNull(out.id);
//        Assertions.assertNotNull(out.addDate);
        Assertions.assertNull(out.modDate);
        Assertions.assertNull(out.modBy);
        final var eamId = "123a";
        service.associateEamId(in.id, eamId);
        final var status = "Z";
        service.updateStatus(in.id, status);

        var out2 = service.byId(in.id).get();
        Assertions.assertNull(out2.modDate);
        Assertions.assertNull(out2.modBy);
    }

    @Test
    public void wo_throw_error_actionable_invalid_page_access(){
        assertThrows(IllegalArgumentException.class, () -> {
            PageRequest page = new PageRequest();
            page.pageNum=0;
            var out = service.getActionable(Util.nowInstant(), page);

        });
    }

    @Test
    public void wo_should_get_paged_actionable(){
        PageRequest page = new PageRequest();
        page.pageNum=1;
        var out = service.getActionable(Util.nowInstant(), page);
        log.info(""+out);
        assertNotNull(out);
    }

}

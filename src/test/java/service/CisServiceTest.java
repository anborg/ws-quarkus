package service;

import api.WorkorderResource;
import io.quarkus.test.junit.QuarkusTest;
import model.Workorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.TestUtil;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

@QuarkusTest
public class CisServiceTest {
    private static final Logger log = Logger.getLogger(WorkorderResource.class.getName());

    @Inject
    CisService service;

    @Test
    public void shouldSaveWorkorder() {
        final Workorder in = TestUtil.workorder.build4Create();
        service.persist(in);
        final Optional<Workorder> outOpt = service.byId(in.id);
        var out = outOpt.get();
        Assertions.assertNotNull(out.id);
        Assertions.assertNotNull(out.addDate);
        Assertions.assertNull(out.modDate);
        Assertions.assertNull(out.modBy);
    }

}
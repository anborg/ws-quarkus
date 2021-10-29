package service;

import api.WorkorderResource;
import io.quarkus.test.junit.QuarkusTest;
import model.Workorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.TestUtil;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@QuarkusTest
public class CisServiceTest {
    private static final Logger log = Logger.getLogger(WorkorderResource.class.getName());

    @Inject
    CisService repo;

    @Test
    public void shouldSaveWorkorder() {
        final Workorder in = TestUtil.workorder.build4Create();
        final Workorder out = repo.persist(in);
        Assertions.assertNotNull(out.id);
        Assertions.assertNotNull(out.addDate);
        Assertions.assertNull(out.modDate);
        Assertions.assertNull(out.modBy);
    }

}

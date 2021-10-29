package test;

import model.Workorder;

public class TestUtil {
    public static class workorder {//deliberately lowercase

        public static Workorder build4Create() {
            final Workorder out = new Workorder();
            out.id = null; //Dont sent for saving.
            out.servicePointId = 123L;
//            out.eamId = "123";
            out.comments = "my comment";
            out.meterServiceProjectCode = "S";
            out.meterWorkTypeCode = "A";
            out.instructions = "my instructions";
            out.status = "N";
            out.addBy = "bla";
            out.dispatchGroupCode = "MYDEPT";
            return out;
        }
        public static Workorder build4Update(Long woId, Workorder in) {
            final Workorder out = in;
            out.id = woId; //Dont sent for saving.
            out.modBy = "bla";
            out.dispatchGroupCode = "MYDEPT";
            return out;
        }
    }
}

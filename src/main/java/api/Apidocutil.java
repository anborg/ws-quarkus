package api;

import model.Workorder;

public class Apidocutil {
    public static class workorder {//deliberately lowercase
        public static final String newObject =
                "{\n" +
                        "  \"servicePointId\": 123,\n" +
                        "  \"eamId\": \"222\",\n" +
                        "  \"createDate\": \"2021-10-30T15:35:56.452Z\",\n" +
                        "  \"dispatchDate\": \"2021-10-30T15:35:56.452Z\",\n" +
                        "  \"dispatchGroupCode\": \"ORG2\",\n" +
                        "  \"meterWorkTypeCode\": \"AA\",\n" +
                        "  \"meterServiceProjectCode\": \"S\",\n" +
                        "  \"comments\": \"my comment\",\n" +
                        "  \"instructions\": \"my instruction\",\n" +
                        "  \"status\": \"N\"\n" +
                        "}"
                ;
    }
}

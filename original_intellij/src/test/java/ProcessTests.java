import com.alienvault.service.IssueService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

//Right now, tests are inoperable when anonymous rate limit is exceeded
public class ProcessTests {
    IssueService testIssues;
    final String accessToken = "251d102814bf9738fe4e446c43ada1183be4a1e6";
    final String[] args1 = {"HassleFreeRecords/hasslefree", "HassleFreeRecords/database-ddl"}; //all valid repos with issues
    final String[] args2 = {"HassleFreeRecordshasslefree", "HassleFreeRecords/dummyRepo"}; //all invalid test repos
    final String[] args3 = {"HassleFreeRecords/hasslefree", "HassleFreeRecords/database-ddl","HassleFreeRecordshasslefree", "HassleFreeRecords/dummyRepo"}; //mix of valid and invalid repos

    private void generateIssueService(String[] args){
        testIssues = new IssueService(args, accessToken);
    }

    //Test 1 for external tracking purposes.
    @Test
    public void testProcessOutputInputsValid(){
        generateIssueService(args1);

        final String expected = "{\"top_day\":{\"occurrences\":{\"HassleFreeRecords/hasslefree\":1,\"HassleFreeRecords/database-ddl\":1},\"day\":\"2018/11/30\"},\"issues\":{\"2018/11/30\":[{\"id\":386063580,\"state\":\"OPEN\",\"title\":\"Test Issue 2\",\"repository\":\"HassleFreeRecords/hasslefree\",\"createdOn\":\"Fri Nov 30 01:03:45 CST 2018\"},{\"id\":386063657,\"state\":\"OPEN\",\"title\":\"Test Issue 3\",\"repository\":\"HassleFreeRecords/database-ddl\",\"createdOn\":\"Fri Nov 30 01:04:06 CST 2018\"}],\"2018/11/29\":[{\"id\":386028849,\"state\":\"CLOSED\",\"title\":\"Test Issue \",\"repository\":\"HassleFreeRecords/hasslefree\",\"createdOn\":\"Thu Nov 29 22:02:21 CST 2018\"}]}}";

        final String actual = testIssues.generateIssueReport();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testProcessErrorReportInputsValid(){
        generateIssueService(args1);

        final String expected = "[]";

        final String actual = testIssues.getInvalidInputs();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testProcessOutputInputsInvalid(){
        generateIssueService(args2);

        final String expected = "{\"Invalid Inputs\":\"[Repository: HassleFreeRecordshasslefree\\nReason for Failure: Input doesn\\u0027t meet owner/repo format., Repository: HassleFreeRecords/dummyRepo\\nReason for Failure: Cannot access repo.]\",\"Report\":\"No results found\"}";

        final String actual = testIssues.generateIssueReport();

        Assert.assertEquals(expected,actual);

    }

    @Test
    public void testProcessErrorReportInputsInvalid(){
        generateIssueService(args2);

        final String expected = "[{\"repoName\":\"HassleFreeRecordshasslefree\",\"reason\":\"Input doesn\\u0027t meet owner/repo format.\"},{\"repoName\":\"HassleFreeRecords/dummyRepo\",\"reason\":\"Cannot access repo.\"}]";

        final String actual = testIssues.getInvalidInputs();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testProcessOutInputsMixed(){
        generateIssueService(args3);

        final String expected = "{\"top_day\":{\"occurrences\":{\"HassleFreeRecords/hasslefree\":1,\"HassleFreeRecords/database-ddl\":1},\"day\":\"2018/11/30\"},\"issues\":{\"2018/11/30\":[{\"id\":386063580,\"state\":\"OPEN\",\"title\":\"Test Issue 2\",\"repository\":\"HassleFreeRecords/hasslefree\",\"createdOn\":\"Fri Nov 30 01:03:45 CST 2018\"},{\"id\":386063657,\"state\":\"OPEN\",\"title\":\"Test Issue 3\",\"repository\":\"HassleFreeRecords/database-ddl\",\"createdOn\":\"Fri Nov 30 01:04:06 CST 2018\"}],\"2018/11/29\":[{\"id\":386028849,\"state\":\"CLOSED\",\"title\":\"Test Issue \",\"repository\":\"HassleFreeRecords/hasslefree\",\"createdOn\":\"Thu Nov 29 22:02:21 CST 2018\"}]}}";

        final String actual = testIssues.generateIssueReport();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testProcessErrorReportInputsMixed(){
        generateIssueService(args3);

        final String expected = "[{\"repoName\":\"HassleFreeRecordshasslefree\",\"reason\":\"Input doesn\\u0027t meet owner/repo format.\"},{\"repoName\":\"HassleFreeRecords/dummyRepo\",\"reason\":\"Cannot access repo.\"}]";

        final String actual = testIssues.getInvalidInputs();

        Assert.assertEquals(expected,actual);
    }

}


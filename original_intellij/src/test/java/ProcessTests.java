import com.alienvault.service.IssueService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProcessTests {
    private IssueService issue;
    final String[] args1 = {"HassleFreeRecords/hasslefree", "HassleFreeRecord/database-ddl"}; //all valid repos with issues
    final String[] args2 = {"HassleFreeRecordshasslefree", "HassleFreeRecords/dummyRepo"}; //all invalid test repos
    final String[] args3 = {"HassleFreeRecords/hasslefree", "HassleFreeRecord/database-ddl","HassleFreeRecordshasslefree", "HassleFreeRecords/dummyRepo"}; //mix of valid and invalid repos

    @Test
    public void testValidRepos(){

    }

    @Test
    public void testInvalidRepos() {

    }

    @Test
    public void testAllRepoTypes() {

    }
}

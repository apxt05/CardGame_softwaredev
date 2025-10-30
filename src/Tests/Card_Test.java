package Tests;


import com.ibm.decision.run.test.junit5.DecisionTest;
import com.ibm.decision.run.test.junit5.JSONTestDirectoryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
 
import java.util.stream.Stream;
 
@DecisionTest(decisionFunction = "<functionID"> /* OR  decisionOperation = "<operationID>" */)
@DisplayName("<DisplayName>")
public class <TestClassName> {
    @TestFactory
    public Stream<DynamicNode> decisionTests() {
        JSONTestDirectoryFactory.verbose = true;
        return JSONTestDirectoryFactory.createTests(this.getClass());
    }
}
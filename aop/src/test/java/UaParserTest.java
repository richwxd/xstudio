import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.IOException;

/**
 * @author xiaobiao
 * @version 2020/2/17
 */
public class UaParserTest {
    @Test
    public void testUaParser() throws IOException {
        String uaString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

        Parser uaParser = new Parser();
        Client c = uaParser.parse(uaString);
        Assertions.assertNotNull(c);
    }
}

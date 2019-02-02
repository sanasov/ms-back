package ms.igrey.dev.msreport.process;

import ms.igrey.dev.msvideo.PrepareContentProcess;
import ms.igrey.dev.msvideo.config.DaoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DaoConfig.class})
public class PrepareContentProcessTest {

    @Autowired
    private  PrepareContentProcess process;

    @Test
    public void fillContentTest() {
        process.fillContent();
    }
}

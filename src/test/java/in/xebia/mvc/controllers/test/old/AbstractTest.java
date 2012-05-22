package in.xebia.mvc.controllers.test.old;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class AbstractTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void should() {
		assertTrue(Boolean.TRUE);
	}
}
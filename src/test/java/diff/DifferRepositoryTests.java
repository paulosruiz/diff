package diff;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import diff.domain.DifferObject;
import diff.repositories.DifferRepository;


/**
 * Differ Service test class
 * 
 * @author paulo.almeida.junior
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DifferRepositoryTests {

	@Autowired
	private DifferRepository repository;

	final String TESTID = new String(DifferRepositoryTests.class.getName());
	final String TESTNOTFOUNDID = new String(DifferRepositoryTests.class.getName()+"NOT");
	@Before
	public void SetUp() {
		try {
			repository.save(new DifferObject(TESTID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}

	}

	@After
	public void UndoSetUp() {
		try {
			repository.delete(new DifferObject(TESTID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}

	}

	@Test
	public void notFindById() {

		try {
			DifferObject response = repository.findById(TESTNOTFOUNDID);
			assertNull("Not found", response);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void findById() {

		try {
			DifferObject response = repository.findById(TESTID);
			assertNotNull("Not found", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

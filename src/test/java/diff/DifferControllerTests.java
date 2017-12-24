package diff;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import diff.domain.DifferBean;
import diff.domain.DifferObject;
import diff.repositories.DifferRepository;
import diff.util.DifferResponseStatus;

/**
 * DifferController Test Class
 * 
 * @author paulo.almeida.junior
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DifferControllerTests {

	@Autowired
	private DifferRepository repository;

	@Autowired
	private MockMvc mockMvc;

	// TODO issue when saving id with package name - ex: diff.DifferControllerTests.
	// When retrieve it brings only diff
	final static String TESTID = new String(DifferControllerTests.class.getName().replace(".", ""));

	final static String INSERTLEFT = new String("/v1/diff/" + TESTID + "/left");
	final static String INSERTRIGHT = new String("/v1/diff/" + TESTID + "/right");
	final static String COMPARE = new String("/v1/diff/" + TESTID);
	final static String RETRIEVEALL = new String("/v1/diff/retrieveAll");

	/**
	 * Converts Object to JSON
	 * 
	 * @param obj
	 * @return
	 */
	protected static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remove the data prepared for the test
	 */
	@After
	public void UndoSetUp() {
		try {
			repository.delete(new DifferObject(TESTID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Insert data in the Left
	 * 
	 * @throws Exception
	 */
	@Test
	public void insertLeftData() throws Exception {
		String leftData = "QkFBQg=="; // BAAB
		DifferBean differBean = new DifferBean(leftData);

		this.mockMvc.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBean)))
				// .param("data", leftData))
				.andDo(print()).andExpect(status().isCreated());

	}

	/**
	 * Insert data in the Right
	 * 
	 * @throws Exception
	 */
	@Test
	public void insertRightData() throws Exception {

		String rightData = "QUFBQkJC"; // AAAAAA
		DifferBean differBean = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBean)))
				.andDo(print()).andExpect(status().isCreated());
	}

	/**
	 * Check the behavior when a id does not exists
	 * 
	 * @throws Exception
	 */
	@Test
	public void notFound() throws Exception {

		this.mockMvc.perform(get(COMPARE)).andDo(print())
				.andExpect(jsonPath("$.status", is(DifferResponseStatus.NOT_FOUND.value()))).andExpect(status().isOk());
	}

	/**
	 * Check the behavior when the data has the same size
	 * 
	 * @throws Exception
	 */
	@Test
	public void sameSize() throws Exception {
		String leftData = "QUFBQkJC"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferBean differBeanLeft = new DifferBean(leftData);
		DifferBean differBeanRight = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanLeft)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(
				post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanRight)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(get(COMPARE)).andDo(print()).andExpect(jsonPath("$.sameSize", is(true)))
				.andExpect(status().isOk());
	}

	/**
	 * Check the behavior when the data does not have the same size
	 * 
	 * @throws Exception
	 */
	@Test
	public void notSameSize() throws Exception {
		String leftData = "QkFBQg=="; // BAAB
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferBean differBeanLeft = new DifferBean(leftData);
		DifferBean differBeanRight = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanLeft)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(
				post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanRight)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(get(COMPARE)).andDo(print()).andExpect(jsonPath("$.sameSize", is(false)))
				.andExpect(status().isOk());

	}

	/**
	 * Check the behavior when the data is not equals
	 * 
	 * @throws Exception
	 */
	@Test
	public void notEquals() throws Exception {
		String leftData = "QkFBQg=="; // BAAB
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferBean differBeanLeft = new DifferBean(leftData);
		DifferBean differBeanRight = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanLeft)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(
				post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanRight)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(get(COMPARE)).andDo(print()).andExpect(jsonPath("$.equals", is(false)))
				.andExpect(status().isOk());
	}

	/**
	 * Checks the behaviour of the response when data is the same
	 * 
	 * @throws Exception
	 */
	@Test
	public void equals() throws Exception {
		String leftData = "QUFBQkJC"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferBean differBeanLeft = new DifferBean(leftData);
		DifferBean differBeanRight = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanLeft)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(
				post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanRight)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(get(COMPARE)).andDo(print()).andExpect(jsonPath("$.equals", is(true)))
				.andExpect(jsonPath("$.sameSize", is(true))).andExpect(status().isOk());
	}

	/**
	 * Check behavior of retrieveAll method
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveAll() throws Exception {
		String leftData = "QkFBQg=="; // BAAB
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferBean differBeanLeft = new DifferBean(leftData);
		DifferBean differBeanRight = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanLeft)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(
				post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanRight)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(get(RETRIEVEALL)).andDo(print()).andExpect(status().isOk());
	}

}

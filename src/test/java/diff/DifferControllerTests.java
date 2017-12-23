package diff;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DifferControllerTests {

	@Autowired
	private DifferRepository repository;

	@Autowired
	private MockMvc mockMvc;

	final static String TESTID = new String(DifferControllerTests.class.getName());
	// final static String TESTID1 = new
	// String(DifferControllerTests.class.getName() + 1);
	// final static String TESTID2 = new
	// String(DifferControllerTests.class.getName() + 2);
	final static String INSERTLEFT = new String("/v1/diff/" + TESTID + "/left");
	final static String INSERTRIGHT = new String("/v1/diff/" + TESTID + "/right");
	final static String COMPARE = new String("/v1/diff/" + TESTID + "/left");

	protected static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@After
	public void UndoSetUp() {
		try {
			repository.delete(new DifferObject(TESTID));
			// repository.delete(new DifferObject(TESTID1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	@Test
	public void insertLeftData() throws Exception {
		String leftData = "QkFBQg=="; // BAAB
		DifferBean differBean = new DifferBean(leftData);

		this.mockMvc.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBean)))
				// .param("data", leftData))
				.andDo(print()).andExpect(status().isCreated());

	}

	@Test
	public void insertRightData() throws Exception {

		String rightData = "QUFBQkJC"; // AAAAAA
		DifferBean differBean = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBean)))
				.andDo(print()).andExpect(status().isCreated());
		// this.mockMvc.perform(post(INSERTRIGHT).param("data",
		// rightData)).andDo(print()).andExpect(status().isCreated());

	}

	@Test
	public void notFound() throws Exception {
		
		this.mockMvc.perform(post(COMPARE).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.status", is(DifferResponseStatus.NOT_FOUND.value())));

	}

	@Test
	public void sameSize() throws Exception {
		String leftData = "QUFBQkJC"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferBean differBeanLeft = new DifferBean(leftData);
		DifferBean differBeanRight = new DifferBean(rightData);

		this.mockMvc
				.perform(post(INSERTLEFT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanLeft)))
				// .param("data", leftData))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(
				post(INSERTRIGHT).contentType(MediaType.APPLICATION_JSON).content(asJsonString(differBeanRight)))
				.andDo(print()).andExpect(status().isCreated());

		this.mockMvc.perform(post(COMPARE).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.equals", is(false)));

	}

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

		this.mockMvc.perform(post(COMPARE).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				//.andExpect(jsonPath("$.sameSize", is(false)));
				.andExpect((flash().attribute("sameSize", "false")));

	}

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

		this.mockMvc.perform(post(COMPARE).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.equals", is(true))).andExpect(jsonPath("$.sameSize", is(true)));

	}

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

		this.mockMvc.perform(post(COMPARE).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.equals", is(false)));

	}

}

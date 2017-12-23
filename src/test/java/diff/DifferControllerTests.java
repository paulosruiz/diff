package diff;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import diff.domain.DifferObject;
import diff.repositories.DifferRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DifferControllerTests {

	@Autowired
	DifferRepository repository;

	DifferObject one, two;

	@Autowired
	private MockMvc mockMvc;
/*
	@Test
	public void createToogle() throws Exception {
		this.mockMvc.perform(get("/toogle/createToogle").param("id", "ToogleTestBlue").param("enable", "true"))
				.andDo(print()).andExpect(status().isOk());

		List<Toogle> toogleOne = repository.findById("ToogleTestBlue");
		assertNotNull(toogleOne);
		repository.delete(toogleOne);
	}

	@Test
	public void createToogleAdmin() throws Exception {

		this.mockMvc.perform(get("/toogle/createToogleAdmin").param("id", "ToogleTestRed").param("enable", "false")
				.param("admin", "ABC")).andDo(print()).andExpect(status().isOk());

		Toogle toogleTwo = repository.findByIdAndAdminAndAdminOnly("ToogleTestRed", "ABC", true);
		assertNotNull(toogleTwo);
		repository.delete(toogleTwo);

	}
*/
}

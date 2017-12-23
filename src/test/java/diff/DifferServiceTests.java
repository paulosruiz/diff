package diff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import diff.domain.DifferObject;
import diff.domain.DifferResponse;
import diff.services.DifferService;
import diff.util.DifferSide;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DifferServiceTests {

	@Autowired
	DifferService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void sameSizeIsEquals() {
		String leftData = "QUFBQkJC"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAAAAA
		Long id = new Long(01);
		service.defineData(id, leftData, DifferSide.LEFT);
		service.defineData(id, rightData, DifferSide.RIGHT);
		DifferResponse response;
		try {
			response = service.compare(id);
			assertTrue("Same size", response.getSameSize());
			assertTrue("Equals", response.getEquals());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sameSizeNotEquals() {

		String leftData = "QUFBQUFB"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAABBB
		Long id = new Long(01);
		byte[] leftDecodedBytes = leftData.getBytes();
		byte[] rightDecodedBytes = rightData.getBytes();
		byte[] leftEncodedBytes = Base64.getEncoder().encode(leftDecodedBytes);
		byte[] rightEncodedBytes = Base64.getEncoder().encode(rightDecodedBytes);

		DifferObject mockedStorage = mock(DifferObject.class);
		/*
		 * when(mockedStorage.get("01", DifferSide.LEFT)).thenReturn(leftDecodedBytes);
		 * when(mockedStorage.get("01",
		 * DifferSide.RIGHT)).thenReturn(rightDecodedBytes);
		 */
		service.defineData(id, leftData, DifferSide.LEFT);
		service.defineData(id, rightData, DifferSide.RIGHT);
		try {
			DifferResponse response = service.compare(id);

			assertTrue("Same size", response.getSameSize());

			assertFalse("Not equals", response.getEquals());

			assertNotNull("There are offsets", response.getOffsets());
			assertThat(response.getOffsets()).hasSize(1);
			assertThat(response.getOffsets().get(0).getLength()).isEqualTo(3);
			assertThat(response.getOffsets().get(0).getOffset()).isEqualTo(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * @Test public void createToogle() throws Exception {
	 * this.mockMvc.perform(get("/toogle/createToogle").param("id",
	 * "ToogleTestBlue").param("enable", "true"))
	 * .andDo(print()).andExpect(status().isOk());
	 * 
	 * List<Toogle> toogleOne = repository.findById("ToogleTestBlue");
	 * assertNotNull(toogleOne); repository.delete(toogleOne); }
	 * 
	 * @Test public void createToogleAdmin() throws Exception {
	 * 
	 * this.mockMvc.perform(get("/toogle/createToogleAdmin").param("id",
	 * "ToogleTestRed").param("enable", "false") .param("admin",
	 * "ABC")).andDo(print()).andExpect(status().isOk());
	 * 
	 * Toogle toogleTwo = repository.findByIdAndAdminAndAdminOnly("ToogleTestRed",
	 * "ABC", true); assertNotNull(toogleTwo); repository.delete(toogleTwo);
	 * 
	 * }
	 */
}

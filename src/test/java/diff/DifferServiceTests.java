package diff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import diff.domain.DifferObject;
import diff.domain.DifferResponse;
import diff.repositories.DifferRepository;
import diff.services.DifferService;
import diff.util.DifferResponseStatus;
import diff.util.DifferSide;

/**
 * DifferService test class
 * 
 * @author paulo.almeida.junior
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DifferServiceTests {

	@Autowired
	private DifferService service;

	@Autowired
	private DifferRepository repository;

	final String TESTID = new String(DifferServiceTests.class.getName());

	/**
	 * Removes each DifferObject created
	 */
	@After
	public void UndoSetUp() {
		try {
			repository.delete(new DifferObject(TESTID));
		} catch (Exception e) {

		}

	}

	/**
	 * Checks if a DifferObject does not exists in the DB
	 */
	@Test
	public void notFound() {

		try {
			DifferResponse response = service.compare(TESTID);
			assertFalse("Same size", response.getSameSize());
			assertFalse("Equals", response.getEquals());
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.NOT_FOUND.value());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Check if a DifferObject does not have the left Data
	 */
	@Test
	public void missingLeft() {
		String rightData = "QUFBQkJC"; // AAAAAA
		try {
			service.defineData(TESTID, rightData, DifferSide.RIGHT);
			DifferResponse response = service.compare(TESTID);
			assertFalse("Same size", response.getSameSize());
			assertFalse("Equals", response.getEquals());
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.MISSING_LEFT.value());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check if a DifferObject does not have the right Data
	 */
	@Test
	public void missingRight() {
		String leftData = "QUFBQkJC"; // AAAAAA
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			DifferResponse response = service.compare(TESTID);
			assertFalse("Same size", response.getSameSize());
			assertFalse("Equals", response.getEquals());
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.MISSING_RIGHT.value());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Check if the Base64 decode error is been handled
	 */
	@Test
	public void Base64Decode() {
		String leftData = "  ";
		String rightData = "  ";
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);
			DifferResponse response = service.compare(TESTID);
			assertFalse("Same size", response.getSameSize());
			assertFalse("Equals", response.getEquals());
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.BASE64_ERROR.value());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Check behavior of DifferObject without the same size
	 */
	@Test
	public void notSameSize() {
		String leftData = "QkFBQg=="; // BAAB
		String rightData = "QUFBQkJC"; // AAAAAA

		DifferResponse response;
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);
			response = service.compare(TESTID);
			System.out.println(response);

			assertFalse("Same size", response.getSameSize());
			assertFalse("Equals", response.getEquals());
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.SIZE_MISMATCH.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check behavior of DifferObject with the same data
	 */
	@Test
	public void sameSizeAndEqual() {
		String leftData = "QUFBQkJC"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAAAAA
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);
			DifferResponse response;

			response = service.compare(TESTID);
			System.out.println(response);
			assertTrue("Same size", response.getSameSize());
			assertTrue("Equals", response.getEquals());
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.EQUALS.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check behavior of DifferObject with offsets in the first and last positions
	 */
	@Test
	public void firstAndLastDifferences() {

		String leftData = "QkFBQg=="; // BAAB
		String rightData = "QUFBQQ=="; // AAAA

		/*
		 * byte[] leftDecodedBytes = leftData.getBytes(); byte[] rightDecodedBytes =
		 * rightData.getBytes(); byte[] leftEncodedBytes =
		 * Base64.getEncoder().encode(leftDecodedBytes); byte[] rightEncodedBytes =
		 * Base64.getEncoder().encode(rightDecodedBytes);
		 */
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);

			DifferResponse response = service.compare(TESTID);

			assertTrue("Same size", response.getSameSize());

			assertFalse("Not equals", response.getEquals());

			assertNotNull("There are offsets", response.getOffsets());
			assertThat(response.getOffsets()).hasSize(2);

			// First offset
			assertThat(response.getOffsets().get(0).getOffset()).isEqualTo(0);
			assertThat(response.getOffsets().get(0).getLength()).isEqualTo(1);

			// Last offset
			assertThat(response.getOffsets().get(1).getOffset()).isEqualTo(3);
			assertThat(response.getOffsets().get(1).getLength()).isEqualTo(1);

			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.CONTENT_MISMATCH.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check behavior of DifferObject with offsets in sufix
	 */
	@Test
	public void sufixDifference() {

		String leftData = "QUJCQg=="; // ABBB
		String rightData = "QUFBQQ=="; // AAAA
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);

			DifferResponse response = service.compare(TESTID);

			assertTrue("Same size", response.getSameSize());

			assertFalse("Not equals", response.getEquals());

			assertNotNull("There are offsets", response.getOffsets());
			assertThat(response.getOffsets()).hasSize(1);

			// Test Offset
			assertThat(response.getOffsets().get(0).getOffset()).isEqualTo(1);
			assertThat(response.getOffsets().get(0).getLength()).isEqualTo(3);

			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.CONTENT_MISMATCH.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check behavior of DifferObject with offsets in a reverse Data
	 * ex: AABB == BBAA
	 */
	@Test
	public void reverseDifference() {

		String leftData = "QUFCQg=="; // AABB
		String rightData = "QkJBQQ=="; // BBAA
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);

			DifferResponse response = service.compare(TESTID);

			assertTrue("Same size", response.getSameSize());

			assertFalse("Not equals", response.getEquals());

			assertNotNull("There are offsets", response.getOffsets());
			assertThat(response.getOffsets()).hasSize(1);

			// Test Offset
			assertThat(response.getOffsets().get(0).getOffset()).isEqualTo(0);
			assertThat(response.getOffsets().get(0).getLength()).isEqualTo(4);

			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.CONTENT_MISMATCH.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check behavior of DifferObject with offsets the middle
	 */
	@Test
	public void middleDifference() {

		String leftData = "QkFBQg=="; // BAAB
		String rightData = "QkJCQg=="; // BBBB
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);

			DifferResponse response = service.compare(TESTID);

			assertTrue("Same size", response.getSameSize());

			assertFalse("Not equals", response.getEquals());

			assertNotNull("There are offsets", response.getOffsets());
			assertThat(response.getOffsets()).hasSize(1);
			// Test Offset
			assertThat(response.getOffsets().get(0).getOffset()).isEqualTo(1);
			assertThat(response.getOffsets().get(0).getLength()).isEqualTo(2);

			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.CONTENT_MISMATCH.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check behavior of DifferObject with sameSize but that are not equals
	 */
	@Test
	public void sameSizeNotEquals() {

		String leftData = "QUFBQUFB"; // AAAAAA
		String rightData = "QUFBQkJC"; // AAABBB

		/*
		 * byte[] leftDecodedBytes = leftData.getBytes(); byte[] rightDecodedBytes =
		 * rightData.getBytes(); byte[] leftEncodedBytes =
		 * Base64.getEncoder().encode(leftDecodedBytes); byte[] rightEncodedBytes =
		 * Base64.getEncoder().encode(rightDecodedBytes);
		 */
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);
			service.defineData(TESTID, rightData, DifferSide.RIGHT);

			DifferResponse response = service.compare(TESTID);

			assertTrue("Same size", response.getSameSize());

			assertFalse("Not equals", response.getEquals());

			assertNotNull("There are offsets", response.getOffsets());
			assertThat(response.getOffsets()).hasSize(1);

			assertThat(response.getOffsets().get(0).getLength()).isEqualTo(3);
			assertThat(response.getOffsets().get(0).getOffset()).isEqualTo(3);
			assertThat(response.getStatus()).isEqualTo(DifferResponseStatus.CONTENT_MISMATCH.value());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Check retrieveAll method
	 */
	@Test
	public void retreiveAll() {

		String leftData = "QUFBQUFB"; // AAAAAA
		
		
		try {
			service.defineData(TESTID, leftData, DifferSide.LEFT);

			List<DifferObject> response = service.retrieveAll();

			assertThat(response).isNotEmpty();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

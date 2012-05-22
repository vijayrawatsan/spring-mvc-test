package in.xebia.mvc.controllers.test.latest;

import static org.springframework.test.web.server.setup.MockMvcBuilders.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.server.MockMvc.*;

import in.xebia.mvc.domain.User;
import in.xebia.mvc.repository.UserRepository;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = WebContextLoader.class, locations = { "classpath:test-context.xml" })
public class MainControllerTestNewWay {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void SetUp() {
		mockMvc = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name("login"));
	}

	@Test
	public void testInvalidLogin() throws Exception {
		mockMvc.perform(post("/login").param("userName", "vijay"))
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(model().attribute("STATUS", "INVALID LOGIN"))
				.andExpect(view().name("redirect:/login"));

	}

	@Test
	public void testSuccessfulLogin() throws Exception {
		mockMvc.perform(
				post("/login").param("userName", "vijay").param("password",
						"1a1dc91c907325c69271ddf0c944bc72"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("STATUS", "SUCCESS"))
				.andExpect(view().name("home"));

	}

	@Test
	public void testDoesNotExistLogin() throws Exception {
		mockMvc.perform(
				post("/login").param("userName", "vijay").param("password",
						"some random pass")).andExpect(status().isOk())
				.andExpect(model().attribute("STATUS", "DOES NOT EXIST"))
				.andExpect(view().name("redirect:/login"));
	}

	@Test
	public void testSpecificUser() throws Exception {
		mockMvc.perform(get("/users/vijay").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().type(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.userName").value("vijay"));
	}

	@Test
	public void testGetAllUsers() throws Exception {
		List<User> users = userRepository.findAll();
		mockMvc.perform(get("/users/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().type(MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.userName[0]").value(
								users.get(0).getUserName()))
				.andExpect(
						jsonPath("$.userName[1]").value(
								users.get(1).getUserName()))
				.andExpect(
						jsonPath("$.userName[2]").value(
								users.get(2).getUserName()));
	}

	/**
	 * Test @RequestParam
	 * 
	 * @throws Exception
	 */
	@Test
	public void testgetUsers() throws Exception {
		mockMvc.perform(get("/users").param("userName", "vijay")).andExpect(
				jsonPath("$.userName").value("vijay"));
	}
	
	@After
	public void destroy(){
		//userRepository.deleteAll();
	}
}

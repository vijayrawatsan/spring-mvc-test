package in.xebia.mvc.controllers.test.old;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.ModelAndViewAssert.assertAndReturnModelAttributeOfType;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import in.xebia.mvc.repository.UserRepository;

import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SuppressWarnings("unused")
public class MainControllerTest extends AbstractTest {

	private static final String SUCCESS = "SUCCESS";

	private static final String INVALID_LOGIN = "INVALID LOGIN";

	private static final String DOES_NOT_EXIST = "DOES NOT EXIST";

	private static final String STATUS = "STATUS";

	/**
	 * json mapper
	 */
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	public void testHome() throws Exception {
		request.setMethod("GET");
		request.setRequestURI("/");
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView modelAndView = handlerAdapter.handle(request, response,
				handler);
		assertViewName(modelAndView, "login");
	}

	@Test
	public void testInvalidLogin() throws Exception {
		request.setMethod("POST");
		request.setRequestURI("/login");
		request.setParameter("userName", "vijayrawat");
		Object handler = handlerMapping.getHandler(request).getHandler();
		final ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		
		assertViewName(modelAndView, "redirect:/login");
		final BindingResult errors = assertAndReturnModelAttributeOfType(modelAndView, 
                "org.springframework.validation.BindingResult.user", 
                BindingResult.class);
		System.out.println(errors.getAllErrors());
		assertEquals(1, errors.getErrorCount());
		assertModelAttributeValue(modelAndView, STATUS, INVALID_LOGIN);
	}
	
	@Test
	public void testSuccessfulLogin() throws Exception {
		request.setMethod("POST");
		request.setRequestURI("/login");
		request.setParameter("userName", "vijay");
		request.setParameter("password", "1a1dc91c907325c69271ddf0c944bc72");
		Object handler = handlerMapping.getHandler(request).getHandler();
		final ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		assertViewName(modelAndView, "home");
		assertModelAttributeValue(modelAndView, STATUS, SUCCESS);
	}
	
	@Test
	public void testDoesNotExistLogin() throws Exception {
		request.setMethod("POST");
		request.setRequestURI("/login");
		request.setParameter("userName", "vijay");
		request.setParameter("password", "some random pass");
		Object handler = handlerMapping.getHandler(request).getHandler();
		final ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		assertViewName(modelAndView, "redirect:/login");
		assertModelAttributeValue(modelAndView, STATUS, DOES_NOT_EXIST);
	}
	
	
	@Test
	public void testGetUsers() throws Exception {
		request.setMethod("GET");
		request.setRequestURI("/users/vijay");
		Object handler = handlerMapping.getHandler(request).getHandler();
		HttpMessageConverter<?>[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(Arrays.asList(messageConverters));
		final ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		assertEquals(mapper.writeValueAsString(userRepository.findByUserName("vijay")), response.getContentAsString());
	}
	
	@Test
	public void testGetAllUsers() throws Exception {
		request.setMethod("GET");
		request.setRequestURI("/users/all");
		Object handler = handlerMapping.getHandler(request).getHandler();
		HttpMessageConverter<?>[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(Arrays.asList(messageConverters));
		final ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		assertEquals(mapper.writeValueAsString(userRepository.findAll()), response.getContentAsString());
	}
	
	/**
	 * Test @RequestParam
	 * @throws Exception
	 */
	@Test
	public void testgetUsers() throws Exception {
		request.setMethod("GET");
		request.setRequestURI("/users");
		request.setParameter("userName", "vijay");
		Object handler = handlerMapping.getHandler(request).getHandler();
		HttpMessageConverter<?>[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(Arrays.asList(messageConverters));
		final ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
		System.out.println(response.getContentAsString());
		assertEquals(mapper.writeValueAsString(userRepository.findByUserName("vijay")), response.getContentAsString());
	}

	/*@AfterClass
	public void destroy(){
		userRepository.deleteAll();
	}*/
}

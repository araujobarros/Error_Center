package br.com.codenation.CentralDeErros;

import br.com.codenation.CentralDeErros.controller.ErrorEventLogController;
import br.com.codenation.CentralDeErros.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static br.com.codenation.CentralDeErros.enums.Roles.DEVELOPER;
import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CentralDeErrosApplicationTests {

	@Autowired
	private ErrorEventLogController errorEventLogController;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;
//	@Autowired
//	private WebApplicationContext webApplicationContext;


	@Autowired
	private MockMvc mockMvc;
//
	@Autowired
	private ObjectMapper objectMapper;

	private static final String CLIENT_ID = "quero-quero";
	private static final String CLIENT_SECRET = "qu3r0-qu3r0";

	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	private static final String EMAIL = "admin";

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	}
//	@Before
//	public void init() throws Exception {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
//	}


	private String obtainAccessToken(String username, String password) throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", CLIENT_ID);
		params.add("username", username);
		params.add("password", password);

		ResultActions result = mockMvc.perform(post("/oauth/token")
						.params(params)
						.with(httpBasic(CLIENT_ID, CLIENT_SECRET))
						.accept(CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

	@Test
	void contextLoads() throws Exception {
		assertThat(errorEventLogController).isNotNull();
	}

	@Test
	public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
		mockMvc.perform(get("/event").param("description", "teste")).andExpect(status().isUnauthorized());
	}

	@Test
	public void givenInvalidRole_whenPostSecureRequest_thenForbidden() throws Exception {
		String accessToken = obtainAccessToken("user", "user");
		String employeeString = "{\"email\":\"jim@yahoo.com\",\"password\":\"123456\", \"role\":\"USER\"}";

		mockMvc.perform(post("/user")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(CONTENT_TYPE)
      			.content(employeeString)
				.accept(CONTENT_TYPE))
				.andExpect(status().isForbidden());
	}


	public void postSomeEvents() throws Exception {
		String accessToken = obtainAccessToken("admin", "admin");
		ArrayList<String> events = new ArrayList<String>();
		events.add("{\"description\":\"description1\",\"level\":\"ERROR\",\"log\":\"log1\",\"origin\":\"origin1\", \"quantity\":\"1\"}");
		events.add("{\"description\":\"description3\",\"level\":\"WARNING\",\"log\":\"log2\",\"origin\":\"origin2\", \"quantity\":\"2\"}");
		events.add("{\"description\":\"description3\",\"level\":\"WARNING\",\"log\":\"log3\",\"origin\":\"origin3\", \"quantity\":\"3\"}");
		events.add("{\"description\":\"description2\",\"level\":\"ERROR\",\"log\":\"log3\",\"origin\":\"origin2\", \"quantity\":\"4\"}");

		for (String event : events) {
			mockMvc.perform(post("/event")
							.header("Authorization", "Bearer " + accessToken)
							.contentType(CONTENT_TYPE)
							.content(event)
							.accept(CONTENT_TYPE))
					.andExpect(status().isCreated());
			Thread.currentThread().sleep(1000);
		}
	}

	@Test
	public void checkFilters_withoutParams() throws Exception {
		String accessToken = obtainAccessToken("admin", "admin");

		postSomeEvents();

		mockMvc.perform(get("/events")
						.header("Authorization", "Bearer " + accessToken)
						.accept("application/json;charset=UTF-8"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.content", hasSize(4)));

//		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
//		verify(errorEventLogRepository).findAll(pageableCaptor.capture());
//		PageRequest pageable = (PageRequest) pageableCaptor.getValue();
//
//		assertThat(pageable).hasNoNullFieldsOrPropertiesExcept();
//		assertThat(pageable).hasFieldOrPropertyWithValue("description", "description1");
	}

	@Test
	public void checkFilter_dateInterval() throws Exception {

		String accessToken = obtainAccessToken("admin", "admin");

		LocalDateTime end = LocalDateTime.now().withNano(0);
		LocalDateTime start = end.minusSeconds(2);

		String uri = "/events?registeredAfter=" + start + "&registeredBefore=" + end;

		mockMvc.perform(get(uri)
						.header("Authorization", "Bearer " + accessToken)
						.accept("application/json;charset=UTF-8"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.content", hasSize(2)));
//				.andExpect(jsonPath("$.content[0].id", is(2)))
//				.andExpect(jsonPath("$.content[1].id", is(3)));
	}

	@Test
	public void checkFilter_quantityInterval() throws Exception {

		String accessToken = obtainAccessToken("admin", "admin");

		mockMvc.perform(get("/events?greaterThan=2&lessThan=3")
						.header("Authorization", "Bearer " + accessToken)
						.accept("application/json;charset=UTF-8"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.content", hasSize(2)));
//				.andExpect(jsonPath("$.content[0].id", is(2)))
//				.andExpect(jsonPath("$.content[1].id", is(3)));
	}

	@Test
	public void checkSortedResults() throws Exception {
		String accessToken = obtainAccessToken("admin", "admin");

		mockMvc.perform(get("/events?sort=origin,desc&sort=level,asc")
						.header("Authorization", "Bearer " + accessToken)
						.accept("application/json;charset=UTF-8"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.content", hasSize(4)))
				.andExpect(jsonPath("$.content[0].id", is(3)))
				.andExpect(jsonPath("$.content[1].id", is(4)))
				.andExpect(jsonPath("$.content[2].id", is(2)))
				.andExpect(jsonPath("$.content[3].id", is(1)));
	}



	@Test
	public void checkAggregationFilters_originAndLevel() throws Exception {

		String accessToken = obtainAccessToken("admin", "admin");

		mockMvc.perform(get("/events?origin=origin2&level=ERROR")
						.header("Authorization", "Bearer " + accessToken)
						.accept("application/json;charset=UTF-8"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content[0].id", is(4)));
	}

	@Test
	public void checkAggregationFilters_descriptionAndLog() throws Exception {

		String accessToken = obtainAccessToken("admin", "admin");

		mockMvc.perform(get("/events?description=tion3&log=g3")
						.header("Authorization", "Bearer " + accessToken)
						.accept("application/json;charset=UTF-8"))

				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content[0].id", is(3)));
	}



}





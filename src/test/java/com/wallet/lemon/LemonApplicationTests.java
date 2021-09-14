package com.wallet.lemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import com.wallet.lemon.movements.IMovementeService;
import com.wallet.lemon.movements.Movement;
import com.wallet.lemon.movements.Constants.MovementTypes;
import com.wallet.lemon.users.IUserService;
import com.wallet.lemon.users.User;
import com.wallet.lemon.wallets.IWalletService;
import com.wallet.lemon.wallets.Constants.Currency;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class LemonApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private IUserService userService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IMovementeService movementService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void givenWac_whenServletContext_thenItProvidesGreetController() {
		ServletContext servletContext = webApplicationContext.getServletContext();


		assertNotNull(servletContext);
		assertTrue(servletContext instanceof MockServletContext);
		assertNotNull(webApplicationContext.getBean("userController"));
		assertNotNull(webApplicationContext.getBean("movementController"));
	}

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	public void createUser() throws Exception {

		JSONObject obj = new JSONObject();
		obj.put("first_name", "test");
		obj.put("last_name", "test");
		obj.put("alias", "testAlias");
		obj.put("email", "test@email");

		var res = this.mockMvc.perform(MockMvcRequestBuilders.post("/users").content(obj.toString().getBytes()).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		assertEquals(res.getResponse().getStatus(), 200);

		var generatedUser = userService.getByAlias("testAlias");
		assertNotNull(generatedUser);
		assertEquals(generatedUser.getEmail(), "test@email");
	}

	@Test
	@Transactional
	public void getUserByAlias() throws Exception {

		var user = userService.create(new User("test", "test", "test@test", "testAlias"));
		var res = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/users?alias=testAlias"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		JSONObject obj = new JSONObject(res.getResponse().getContentAsString());
		assertEquals(obj.getString("email"), user.getEmail());
		assertEquals(obj.getString("first_name"), user.getFirstName());
	}

	@Test
	@Transactional
	public void createMovement() throws Exception {
		var user = userService.create(new User("test", "test", "test@test", "testAlias"));

		JSONObject obj = new JSONObject();
		obj.put("type", "DEPOSIT");
		obj.put("currency", "ARS");
		obj.put("amount", 10.56231);
		obj.put("user_alias", user.getAlias());

		var res = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/movements").content(obj.toString().getBytes())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		var resObj = new JSONObject(res.getResponse().getContentAsString());
		assertEquals("10.56", resObj.getString("amount"));
	}

	@Test
	@Transactional
	public void negativeBalanceFailure() throws Exception {
		var user = userService.create(new User("test", "test", "test@test", "testAlias"));
		var wallet = user.getWallets().stream().findFirst().get();
		movementService.execute(new Movement(MovementTypes.DEPOSIT, Currency.ARS, new BigDecimal(11), user, wallet));
		JSONObject obj = new JSONObject();
		obj.put("type", "EXTRACTION");
		obj.put("currency", wallet.getType().toString());
		obj.put("amount", 10121.56231);
		obj.put("user_alias", user.getAlias());

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/movements").content(obj.toString().getBytes())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}

}

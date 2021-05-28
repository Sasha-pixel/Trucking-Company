package com.example.demo;

import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGettingHomePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRedirectionToLoginWithoutAuthorization() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @Sql(value = "/insert_user_before_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAuthorization() throws Exception {
        this.mockMvc.perform(formLogin().user("test_user").password("qwerty"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));
    }

}

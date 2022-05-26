package com.example.demo;

import com.example.demo.controller.AdminController;
import com.example.demo.models.Admin;
import com.example.demo.models.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTestsAdmin {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<User> jacksonTester;

    @BeforeEach
    public void init(){
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void getUserTest() throws Exception {
        User user = new User("Imie", "Nazwisko", "Haslo", "mail@domena.com", "111222333");
        when(mockUserService.getUser(user.getUserId()))
                .thenReturn(Optional.of(user));
        // To co wyżej działa

        mockMvc.perform(get("/api/user/{userId}", user.getUserId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.userId").value(user.getUserId()));
        // To co wyżej nie działa
        // Błąd: Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported]

//        verify(mockUserService, times(1)).getUser(user.getUserId());
//        verifyNoMoreInteractions(mockUserService);
    }

    @Test
    public void createUserTest() throws Exception {
        User user = new User("Imie", "Nazwisko", "Haslo", "mail@domena.com", "111222333");
        String jsonUser = jacksonTester.write(user).getJson();
        user.setUserId(10L);

        when(mockUserService.setUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/user").content(jsonUser).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL))
                .andDo(print());
    }
}

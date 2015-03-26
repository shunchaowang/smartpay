package com.lambo.smartpay.pay.test;

import com.lambo.smartpay.pay.web.controller.FormStringTrimmer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by swang on 3/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class FormStringTrimmerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void stringFormatting ( ) throws Exception
    {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/test");
        // this should be trimmed, but only start and end of string
        post.param("test", "     Hallo  Welt   ");
        ResultActions result = mockMvc.perform(post);
        result.andExpect(view().name("Hallo  Welt"));
    }

    @Configuration
    @EnableWebMvc
    static class Config {
        @Bean
        FormStringTrimmer formStringTrimmer() {
            return new FormStringTrimmer();
        }

        @Bean
        FormStringTrimmerTestController formStringTrimmerTestController() {
            return new FormStringTrimmerTestController();
        }

    }
}

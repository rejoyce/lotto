package net.rejoyce.example.lotto.controller;

import net.rejoyce.example.lotto.data.ResultsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LottoController.class)
class LottoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup(){
        // just getting in the recent stuff
        ResultsRepository.scrapeAllResults(2022);
    }

    @Test
    void getNum() throws Exception {
        this.mockMvc.perform(get("/result/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"date\":\"2022-01-01\",\"numbers\":[4,5,21,24,25,41,1]}]")));
    }

    @Test
    void getMeta() throws Exception {
        this.mockMvc.perform(get("/meta/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1 has appeared 1 time<br/>1 was first drawn on 2022-01-01<br/>1 was last drawn on 2022-01-01<br/>")));
    }
}
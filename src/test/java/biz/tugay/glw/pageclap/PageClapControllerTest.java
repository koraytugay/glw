package biz.tugay.glw.pageclap;

import biz.tugay.glw.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PageClapControllerTest extends BaseTest {

    @Autowired
    PageClapRepository pageClapRepository;

    @Before
    @After
    public void deleteAll() {
        pageClapRepository.deleteAll();
    }

    @Test
    public void mustReturnAllClaps() throws Exception {
        pageClapRepository.save(PageClap.builder().path("/aPath").clapCount(42).build());
        mockMvc.perform(get("/pageclap/all"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"path\":\"/aPath\",\"clapCount\":42}]"));
    }

    @Test
    public void mustReturnClapCount() throws Exception {
        pageClapRepository.save(PageClap.builder().path("/aPath").clapCount(42).build());
        mockMvc.perform(get("/pageclap/clap-count?path=/aPath"))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }

    @Test
    public void mustNotResetPageClapsIfPwWrong() throws Exception {
        pageClapRepository.save(PageClap.builder().path("/aPath").clapCount(42).build());
        mockMvc.perform(get("/pageclap/reset-page-claps?key=wrongpw"))
                .andExpect(status().isOk());
    }

    @Test
    public void mustClapPage() throws Exception {
        mockMvc.perform(post("/pageclap/clap").param("path", "/aPath"))
                .andExpect(status().isOk());
        assertThat(pageClapRepository.findByPath("/aPath").getClapCount(), equalTo(1));
    }

}

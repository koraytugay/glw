package biz.tugay.glw.pageclap;

import biz.tugay.glw.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PageClapServiceTest extends BaseTest {

    @Autowired
    PageClapService pageClapService;

    @Autowired
    PageClapRepository pageClapRepository;

    @Before
    @After
    public void deleteAll() {
        pageClapRepository.deleteAll();
    }

    @Test
    public void mustClapNonExistingPath() {
        PageClap pageClap = pageClapService.clapPage("/apath");
        assertThat(pageClap.getClapCount(), equalTo(1));
    }

    @Test
    public void mustClapExistingPath() {
        PageClap existingPageClap = PageClap.builder().path("/existingPath").clapCount(10).build();
        pageClapRepository.save(existingPageClap);

        PageClap pageClap = pageClapService.clapPage("/existingPath");
        assertThat(pageClap.getClapCount(), equalTo(11));
    }
}

package biz.tugay.glw.pageclap;


import biz.tugay.glw.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PageClapRepositoryTest extends BaseTest {

    @Autowired
    PageClapRepository pageClapRepository;

    @Before
    @After
    public void deleteAll() {
        pageClapRepository.deleteAll();
    }

    @Test
    public void mustFindByPath() {
        String aPath = "/aPath";
        pageClapRepository.save(PageClap.builder().path(aPath).clapCount(42).build());
        PageClap pageClapByPath = pageClapRepository.findByPath(aPath);
        assertThat(pageClapByPath.getClapCount(), equalTo(42));
    }
}

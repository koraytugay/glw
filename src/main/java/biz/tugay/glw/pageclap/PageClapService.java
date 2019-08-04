package biz.tugay.glw.pageclap;

import org.springframework.stereotype.Service;

@Service
public class PageClapService {

    PageClapRepository pageClapRepository;

    public PageClapService(PageClapRepository pageClapRepository) {
        this.pageClapRepository = pageClapRepository;
    }

    public PageClap clapPage(String path) {
        PageClap pageClap = pageClapRepository.findByPath(path);

        if (pageClap == null) {
            pageClap = PageClap.builder().path(path).build();
        }

        pageClap.incrementByOne();

        return pageClapRepository.save(pageClap);
    }
}

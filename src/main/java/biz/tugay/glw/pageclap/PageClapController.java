package biz.tugay.glw.pageclap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static biz.tugay.glw.util.StringUtils.toHexString;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.security.MessageDigest.getInstance;

@Controller
@RequestMapping(path = "/pageclap")
public class PageClapController {

    PageClapService pageClapService;

    PageClapRepository pageClapRepository;

    public PageClapController(PageClapService pageClapService, PageClapRepository pageClapRepository) {
        this.pageClapService = pageClapService;
        this.pageClapRepository = pageClapRepository;
    }

    @PostMapping(path = "/clap")
    @ResponseBody
    public String clap(@RequestParam("path") String path) {
        PageClap pageClap = pageClapService.clapPage(path);
        return String.valueOf(pageClap.getClapCount());
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<PageClap> all() {
        return pageClapRepository.findAll();
    }

    @GetMapping("/clap-count")
    @ResponseBody
    public String clapCount(@RequestParam("path") String path) {
        PageClap pageClap = pageClapRepository.findByPath(path);
        return String.valueOf(pageClap == null ? 0 : pageClap.getClapCount());
    }

    @GetMapping(path = "/reset-page-claps")
    @ResponseStatus(value = HttpStatus.OK)
    public void resetPageClaps(@RequestParam("key") String key) throws NoSuchAlgorithmException {
        String hash = toHexString(getInstance("SHA-256").digest(key.getBytes(UTF_8)));
        if ("f2750b8058c31420355de026810df1aba6fafb634bfa3e1c7c911bc6600f94b6".equalsIgnoreCase(hash)) {
            pageClapRepository.deleteAll();
        }
    }
}

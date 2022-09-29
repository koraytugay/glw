package biz.tugay.glw.admin.cache;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path = "/admin/cache")
public class CacheController {

    CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping(path = "/evict-cache")
    @ResponseStatus(value = HttpStatus.OK)
    public void evictCache() {
        cacheService.evictCache();
    }

    @GetMapping("/cache-all")
    @ResponseStatus(value = HttpStatus.OK)
    @EventListener(ApplicationReadyEvent.class)
    public void cacheAll() {
        cacheService.cacheAll();
    }

    @GetMapping("/refresh")
    @ResponseStatus(value = HttpStatus.OK)
    public void refresh() {
        cacheService.evictCache();
        cacheService.cacheAll();
    }
}

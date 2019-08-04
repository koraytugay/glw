package biz.tugay.glw.admin.cache;

import biz.tugay.glw.gitlab.GitlabFile;
import biz.tugay.glw.gitlab.service.GitlabFetchService;
import biz.tugay.glw.gitlab.service.GitlabGateway;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Log
public class CacheService {

    GitlabGateway gitlabGateway;
    GitlabFetchService gitlabFetchService;

    public CacheService(GitlabGateway gitlabGateway, GitlabFetchService gitlabFetchService) {
        this.gitlabGateway = gitlabGateway;
        this.gitlabFetchService = gitlabFetchService;
    }

    @CacheEvict(cacheNames = {"gitlabFileCache", "articleCache"}, allEntries = true)
    public void evictCache() {
        log.info("Cache cleared.");
    }

    public void cacheAll() {
        long start = System.currentTimeMillis();
        List<GitlabFile> files = gitlabGateway.fetchFilesRecursivelyToFlatList("6735489", "master", "");

        ExecutorService executorService = Executors.newFixedThreadPool(16);

        files.forEach(file -> {
            if (!file.isDirectory()) {
                executorService.execute(() -> {
                    log.info("Fetching: " + file.getPath());
                    gitlabFetchService.fetchArticle("6735489", "master", file.getPath());
                });
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
            log.info("Took: " + (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            log.warning(e.getMessage());
        }
    }

}

package biz.tugay.glw.gitlab.service;

import biz.tugay.glw.gitlab.EmbedInfo;
import com.google.gson.Gson;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class GitlabFetchService {

    GitlabGateway gitlabGateway;
    GitlabEmbedProcessor gitlabEmbedProcessor;

    public GitlabFetchService(GitlabGateway gitlabGateway, GitlabEmbedProcessor gitlabEmbedProcessor) {
        this.gitlabGateway = gitlabGateway;
        this.gitlabEmbedProcessor = gitlabEmbedProcessor;
    }

    @Cacheable(value = "articleCache")
    public String fetchArticle(String projectId, String branch, String path) {
        String article = gitlabGateway.fetchRawContent(projectId, branch, path);

        String resolvedArticle = "";

        boolean isContent = true;
        try (Scanner scanner = new Scanner(article).useDelimiter("-embed-")) {
            while (scanner.hasNext()) {
                if (isContent) {
                    resolvedArticle = resolvedArticle.concat(scanner.next());
                } else {
                    EmbedInfo embedInfo = new Gson().fromJson(scanner.next(), EmbedInfo.class);
                    resolvedArticle = resolvedArticle.concat(gitlabEmbedProcessor.process(embedInfo));
                }
                isContent = !isContent;
            }
        }

        return resolvedArticle;
    }

}

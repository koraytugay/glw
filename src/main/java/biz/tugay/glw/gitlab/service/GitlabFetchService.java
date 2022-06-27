package biz.tugay.glw.gitlab.service;

import biz.tugay.glw.gitlab.EmbedInfo;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@AllArgsConstructor
public class GitlabFetchService {

    GitlabGateway gitlabGateway;
    GitlabEmbedProcessor gitlabEmbedProcessor;

    @Cacheable(value = "articleCache")
    public String fetchArticle(String projectId, String branch, String path) {
        StringBuilder sb = new StringBuilder();
        boolean isContent = true;
        try (Scanner scanner = new Scanner(gitlabGateway.fetchRawContent(projectId, branch, path)).useDelimiter("-embed-")) {
            while (scanner.hasNext()) {
                if (isContent) {
                    sb.append(scanner.next());
                } 
                else {
                  sb.append(gitlabEmbedProcessor.process(new Gson().fromJson(scanner.next(), EmbedInfo.class)));
                }
                isContent = !isContent;
            }
        }
        return sb.toString();
    }
}

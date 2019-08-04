package biz.tugay.glw.gitlab.util;

import org.springframework.stereotype.Service;

@Service
public class GitlabUrlUtil {

    static final String GITLAB_ROOT = "https://gitlab.com/api/v4/projects/";
    static final String GITLAB_TREE_PATH = "/repository/tree?path=";
    static final String GITLAB_FILES_PATH = "/repository/files/";

    public String buildTreeUrl(String projectId, String branch, String path) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(GITLAB_ROOT)
                .append(projectId)
                .append(GITLAB_TREE_PATH)
                .append(path)
                .append("&ref=")
                .append(branch)
                .append("&per_page=100");

        return stringBuilder.toString();
    }

    public String buildArticleUrl(String projectId, String branch, String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(GITLAB_ROOT)
                .append(projectId)
                .append(GITLAB_FILES_PATH)
                .append(filePath.replaceAll("/", "%2F"))
                .append("/raw?ref=")
                .append(branch);

        return stringBuilder.toString();
    }
}

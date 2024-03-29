package biz.tugay.glw.gitlab.service;

import biz.tugay.glw.gitlab.GitlabFile;
import biz.tugay.glw.gitlab.util.GitlabUrlUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GitlabGateway {

    GitlabUrlUtil gitlabUrlUtil;

    public GitlabGateway(GitlabUrlUtil gitlabUrlUtil) {
        this.gitlabUrlUtil = gitlabUrlUtil;
    }

    @Cacheable("gitlabFileCache")
    public List<GitlabFile> fetchFilesRecursivelyToFlatList(String projectId, String branch, String path) {
        List<GitlabFile> gitlabFiles = fetchFilesAtPath(projectId, branch, path);

        List<GitlabFile> directories = gitlabFiles.stream().filter(GitlabFile::isDirectory).collect(toList());
        gitlabFiles.removeIf(GitlabFile::isDirectory);

        directories.forEach(directory -> {
            gitlabFiles.add(directory);
            gitlabFiles.addAll(fetchFilesRecursivelyToFlatList(projectId, branch, directory.getPath()));
        });

        return gitlabFiles;
    }

    public String fetchRawContent(String projectId, String branch, String path) {
        final String url = gitlabUrlUtil.buildArticleUrl(projectId, branch, path);

        URI uri = UriComponentsBuilder.fromUriString(url).build(true).toUri();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate.getForEntity(uri, String.class).getBody();
    }

    public List<GitlabFile> fetchFilesAtPath(String projectId, String branch, String path) {
        RestTemplate restTemplate = new RestTemplate();

        String url = gitlabUrlUtil.buildTreeUrl(projectId, branch, path);
        ParameterizedTypeReference<List<GitlabFile>> type = new ParameterizedTypeReference<List<GitlabFile>>() {
        };

        return restTemplate.exchange(url, HttpMethod.GET, null, type).getBody();
    }
}

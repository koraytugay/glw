package siz.tugay.glw.gitlab.service;

import siz.tugay.glw.BaseTest;
import biz.tugay.glw.gitlab.GitlabFile;
import siz.tugay.glw.gitlab.GitlabIntegrationTests;
import biz.tugay.glw.gitlab.service.GitlabGateway;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Category(GitlabIntegrationTests.class)
public class GitlabGatewayTest extends BaseTest
{

    @Autowired
    GitlabGateway gitlabGateway;

    @Test
    public void mustFetchFilesAtPath() {
        List<GitlabFile> files = gitlabGateway.fetchFilesAtPath("13225918", "master", "test-dir-01");

        GitlabFile file_01 = new GitlabFile();
        file_01.setPath("test-dir-01/file01.md");

        GitlabFile file_02 = new GitlabFile();
        file_02.setPath("test-dir-01/file02.md");

        assertThat(files.contains(file_01), is(true));
        assertThat(files.contains(file_02), is(true));
    }
}

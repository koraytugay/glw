package biz.tugay.glw.gitlab.service;

import biz.tugay.glw.gitlab.EmbedInfo;
import biz.tugay.glw.gitlab.GitlabFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitlabEmbedProcessor {

    GitlabGateway gitlabGateway;

    public GitlabEmbedProcessor(GitlabGateway gitlabGateway) {
        this.gitlabGateway = gitlabGateway;
    }

    public String process(EmbedInfo embedInfo) {
        String projectId = embedInfo.getProjectId();
        String branch = embedInfo.getBranch();
        String path = embedInfo.getPath();

        List<GitlabFile> gitlabFiles = gitlabGateway.fetchFilesRecursivelyToFlatList(projectId, branch, path);
        gitlabFiles.removeIf(GitlabFile::isDirectory);

        ArrayList<GitlabFile> embedFiles = new ArrayList<>();

        // EmbedInfo#includeOnly is stronger than omit files
        // If there is includeOnly files, omitted files are not relevant
        if (!embedInfo.getIncludeOnly().isEmpty()) {
            gitlabFiles.stream().filter(gitlabFile -> embedInfo.getIncludeOnly().contains(gitlabFile.getName())).forEach(embedFiles::add);
        } else {
            gitlabFiles.stream().filter(gitlabFile -> !embedInfo.getOmitFiles().contains(gitlabFile.getName())).forEach(embedFiles::add);
        }

        String embedBody = "";

        for (GitlabFile gitlabFile : embedFiles) {
            String filename = gitlabFile.getName();
            int dotIndex = filename.lastIndexOf(".");
            String fileExtension = filename.substring(dotIndex + 1);
            embedBody = embedBody.concat("\n").concat(filename).concat("\n");
            embedBody = embedBody.concat("\n```").concat(fileExtension).concat("\n");
            embedBody = embedBody.concat(gitlabGateway.fetchRawContent(projectId, branch, gitlabFile.getPath()).trim());
            embedBody = embedBody.concat("\n```\n");
        }

        return embedBody;
    }
}

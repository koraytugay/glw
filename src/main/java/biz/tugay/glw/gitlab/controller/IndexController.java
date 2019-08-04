package biz.tugay.glw.gitlab.controller;

import biz.tugay.glw.gitlab.GitlabFile;
import biz.tugay.glw.gitlab.service.GitlabGateway;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    GitlabGateway gitlabGateway;

    public IndexController(GitlabGateway gitlabGateway) {
        this.gitlabGateway = gitlabGateway;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<GitlabFile> treeFlat = gitlabGateway.fetchFilesRecursivelyToFlatList("6735489", "master", "");

        // Remove files we do not want to render
        treeFlat.removeIf(gitlabFile -> gitlabFile.getName().equals(".gitignore"));

        model.addAttribute("treeFlat", treeFlat);
        return "index";
    }
}

package biz.tugay.glw.gitlab.controller;

import biz.tugay.glw.gitlab.service.GitlabFetchService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArticleController {

    GitlabFetchService gitlabFetchService;

    MutableDataSet options = new MutableDataSet();
    Parser parser = Parser.builder(options).build();
    HtmlRenderer renderer = HtmlRenderer.builder(options).build();

    public ArticleController(GitlabFetchService gitlabFetchService) {
        this.gitlabFetchService = gitlabFetchService;
    }

    @GetMapping(path = "/**/*.md")
    public String article(Model model, HttpServletRequest req) {
        String requestURI = req.getRequestURI().startsWith("/") ? req.getRequestURI().substring(1) : req.getRequestURI();
        model.addAttribute("content", renderer.render(parser.parse(gitlabFetchService.fetchArticle("6735489", "master", requestURI))));
        return "article";
    }
}

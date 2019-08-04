package biz.tugay.glw.gitlab;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmbedInfo {
    private String projectId;
    private String branch = "master";
    private String path = "";
    private List<String> omitFiles = new ArrayList<>();
    private List<String> includeOnly  = new ArrayList<>();
}

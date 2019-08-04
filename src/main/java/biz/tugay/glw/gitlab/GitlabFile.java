package biz.tugay.glw.gitlab;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "path")
public class GitlabFile implements Serializable {

    String id, name, type, path, mode;
    List<GitlabFile> children = new ArrayList<>();

    public boolean isDirectory() {
        return "tree".equals(type);
    }

    public String nameWoExtension() {
        if (name.startsWith(".") || !name.contains(".")) {
            return name;
        }
        return getName().substring(0, getName().lastIndexOf("."));
    }

}

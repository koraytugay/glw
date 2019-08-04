package biz.tugay.glw.pageclap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "page_clap")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageClap {

    @Id
    @Column(name = "path")
    String path;

    @Column(name = "clap_count")
    int clapCount;

    public void incrementByOne() {
        clapCount++;
    }
}

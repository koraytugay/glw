package biz.tugay.glw.pageclap;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PageClapRepository extends JpaRepository<PageClap, String> {
    PageClap findByPath(String path);
}

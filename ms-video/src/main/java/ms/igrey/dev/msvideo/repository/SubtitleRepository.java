package ms.igrey.dev.msvideo.repository;

import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SubtitleRepository extends ElasticsearchRepository<SubtitleEntity, String> {

//    @Query("{\"query\" : {\"match\" : {\"_all\" : \"?0\"}}}")
    Page<SubtitleEntity> findByLinesContaining(String name, Pageable pageable);
}

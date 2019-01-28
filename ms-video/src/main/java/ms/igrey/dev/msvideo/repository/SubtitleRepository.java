package ms.igrey.dev.msvideo.repository;

import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SubtitleRepository extends ElasticsearchRepository<SubtitleEntity, String> {

}

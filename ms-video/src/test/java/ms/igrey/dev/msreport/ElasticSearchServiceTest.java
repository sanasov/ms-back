package ms.igrey.dev.msreport;

import ms.igrey.dev.msvideo.config.DaoConfig;
import ms.igrey.dev.msvideo.domain.srt.SrtParser;
import ms.igrey.dev.msvideo.domain.srt.Subtitle;
import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import ms.igrey.dev.msvideo.repository.entity.SubtitleEntity;
import ms.igrey.dev.msvideo.service.SubtitleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DaoConfig.class})
public class ElasticSearchServiceTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SubtitleService subtitleService;


    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(SubtitleEntity.class);
        elasticsearchTemplate.createIndex(SubtitleEntity.class);
        subtitleService.save(new Subtitles(new SrtParser("BohemianRhapsody2018.srt").parsedSubtitlesFromOriginalSrtRows()));
    }

    @Test
    public void findByPhraseTest() {
        List<Subtitle> subtitles = subtitleService.findPhrase("Give your mother");
        assertThat(subtitles).hasSize(1);
        assertThat(subtitles.get(0).numberSeq()).isEqualTo(15);
    }
//
//	@Test
//	public void givenPersistedSubtitleEntitys_whenSearchByAuthorsName_thenRightFound() {
//
//		final Page<SubtitleEntity> subtitleByAuthorName = subtitleService
//				.findByAuthorName(johnSmith.getName(), PageRequest.of(0, 10));
//		assertEquals(2L, subtitleByAuthorName.getTotalElements());
//	}
//
//	@Test
//	public void givenCustomQuery_whenSearchByAuthorsName_thenSubtitleEntityIsFound() {
//		final Page<SubtitleEntity> subtitleByAuthorName = subtitleService.findByAuthorNameUsingCustomQuery("Smith", PageRequest.of(0, 10));
//		assertEquals(2L, subtitleByAuthorName.getTotalElements());
//	}
//
//	@Test
//	public void givenTagFilterQuery_whenSearchByTag_thenSubtitleEntityIsFound() {
//		final Page<SubtitleEntity> subtitleByAuthorName = subtitleService.findByFilteredTagQuery("elasticsearch", PageRequest.of(0, 10));
//		assertEquals(3L, subtitleByAuthorName.getTotalElements());
//	}
//
//	@Test
//	public void givenTagFilterQuery_whenSearchByAuthorsName_thenSubtitleEntityIsFound() {
//		final Page<SubtitleEntity> subtitleByAuthorName = subtitleService.findByAuthorsNameAndFilteredTagQuery("Doe", "elasticsearch", PageRequest.of(0, 10));
//		assertEquals(2L, subtitleByAuthorName.getTotalElements());
//	}
//
//	@Test
//	public void givenPersistedSubtitleEntitys_whenUseRegexQuery_thenRightSubtitleEntitysFound() {
//
//		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(regexpQuery("title", ".*data.*"))
//				.build();
//		final List<SubtitleEntity> subtitles = elasticsearchTemplate.queryForList(searchQuery, SubtitleEntity.class);
//
//		assertEquals(1, subtitles.size());
//	}
//
//	@Test
//	public void givenSavedDoc_whenTitleUpdated_thenCouldFindByUpdatedTitle() {
//		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(fuzzyQuery("title", "serch")).build();
//		final List<SubtitleEntity> subtitles = elasticsearchTemplate.queryForList(searchQuery, SubtitleEntity.class);
//
//		assertEquals(1, subtitles.size());
//
//		final SubtitleEntity subtitle = subtitles.get(0);
//		final String newTitle = "Getting started with Search Engines";
//		subtitle.setTitle(newTitle);
//		subtitleService.save(subtitle);
//
//		assertEquals(newTitle, subtitleService.findOne(subtitle.getId()).get().getTitle());
//	}
//
//	@Test
//	public void givenSavedDoc_whenDelete_thenRemovedFromIndex() {
//
//		final String subtitleTitle = "Spring Data Elasticsearch";
//
//		final SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withQuery(matchQuery("title", subtitleTitle).minimumShouldMatch("75%")).build();
//		final List<SubtitleEntity> subtitles = elasticsearchTemplate.queryForList(searchQuery, SubtitleEntity.class);
//		assertEquals(1, subtitles.size());
//		final long count = subtitleService.count();
//
//		subtitleService.delete(subtitles.get(0));
//
//		assertEquals(count - 1, subtitleService.count());
//	}
//
//	@Test
//	public void givenSavedDoc_whenOneTermMatches_thenFindByTitle() {
//		final SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withQuery(matchQuery("title", "Search engines").operator(AND)).build();
//		final List<SubtitleEntity> subtitles = elasticsearchTemplate.queryForList(searchQuery, SubtitleEntity.class);
//		assertEquals(1, subtitles.size());
//	}
}
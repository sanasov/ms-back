package ms.igrey.dev.msvideo.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@ToString
@Getter
@Setter
@Document(indexName = "video_eng", type = "subtitle")
public class SubtitleEntity {
    @Id
    private String id;
    private String start;
    private String end;
    @MultiField(
            mainField = @Field(type = Text, fielddata = true),
            otherFields = {@InnerField(suffix = "verbatim", type = Keyword)}
    )
    private List<String> lines;
    private Integer numberSeq;
    private String filmId;
    @Field(type = Keyword)
    private String[] tags;

    public void setTagArray(String... tags) {
        this.tags = tags;
    }
}

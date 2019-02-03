package ms.igrey.dev.msvideo.repository;

import com.google.gson.Gson;
import feign.Feign;
import feign.codec.StringDecoder;
import ms.igrey.dev.msvideo.api.OmdbFeign;
import ms.igrey.dev.msvideo.api.feignCodec.GsonEncoder;
import ms.igrey.dev.msvideo.dto.OmdbFilmDto;

import java.util.ArrayList;
import java.util.List;

public class OmdbFilmMetaInfoRepository implements FilmMetaInfoRepository {

    @Override
    public OmdbFilmDto findByTitle(String filmTitle) {
        OmdbFeign omdbFeign = Feign.builder()
                .decoder(new StringDecoder())
                .encoder(new GsonEncoder())
                .target(OmdbFeign.class, "http://www.omdbapi.com");

        return new Gson().fromJson(
                omdbFeign.findByTitle(
                        filmTitle.substring(0, filmTitle.indexOf("(")),
                        filmTitle.substring(filmTitle.indexOf("(") + 1, filmTitle.indexOf(")")) // film (year) -> film and year
                ),
                OmdbFilmDto.class);

    }

    @Override
    public List<OmdbFilmDto> findByTitles(List<String> filmTitles) {
        List<OmdbFilmDto> filmsInfo = new ArrayList<>();
        for (String title : filmTitles) {
            filmsInfo.add(findByTitle(title));
        }
        return filmsInfo;
    }

    public static void main(String[] args) {
        System.out.println(new OmdbFilmMetaInfoRepository().findByTitle("Godfather (1972)"));
    }
}




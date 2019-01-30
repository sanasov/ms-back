package ms.igrey.dev.msvideo.repository;

import feign.Feign;
import feign.codec.StringDecoder;
import ms.igrey.dev.msvideo.api.OmdbFeign;
import ms.igrey.dev.msvideo.api.feignCodec.GsonEncoder;

public class OmdbApiFilmRepository implements FilmRepository {

    private final String OMDB_API_KEY = "f30f1c99";

    @Override
    public String findByTitle(String filmTitle) {
        OmdbFeign omdbFeign = Feign.builder()
                .decoder(new StringDecoder())
                .encoder(new GsonEncoder())
                .target(OmdbFeign.class, "http://www.omdbapi.com");

        return omdbFeign.findByTitle(
                filmTitle.substring(0, filmTitle.indexOf("(")),
                filmTitle.substring(filmTitle.indexOf("(") + 1, filmTitle.indexOf(")")) // film (year) -> film and year
        );

    }

    public static void main(String[] args) {
        System.out.println(new OmdbApiFilmRepository().findByTitle("Godfather (1972)"));
    }
}




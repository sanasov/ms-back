package ms.igrey.dev.msvideo.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import feign.Feign;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import ms.igrey.dev.msvideo.api.OmdbFeign;
import ms.igrey.dev.msvideo.api.feignCodec.GsonEncoder;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import static feign.Util.ensureClosed;
import static feign.Util.resolveLastTypeParameter;

public class OmdbApiFilmRepository implements FilmRepository {

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




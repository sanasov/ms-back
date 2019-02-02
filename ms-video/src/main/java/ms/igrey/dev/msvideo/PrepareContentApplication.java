package ms.igrey.dev.msvideo;

import com.google.gson.Gson;
import ms.igrey.dev.msvideo.repository.GoogleDriveSrtRepository;
import ms.igrey.dev.msvideo.repository.OmdbApiFilmRepository;
import ms.igrey.dev.msvideo.service.OmdbFilmService;

public class PrepareContentApplication {


    public static void main(String[] args) {
        System.out.println(
                new Gson().toJson(new OmdbFilmService(
                        new OmdbApiFilmRepository(),
                        new GoogleDriveSrtRepository()
                ).getFilmsInfo())
        );
    }
}

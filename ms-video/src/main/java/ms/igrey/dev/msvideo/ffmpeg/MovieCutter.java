package ms.igrey.dev.msvideo.ffmpeg;

import ms.igrey.dev.msvideo.domain.srt.Subtitles;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MovieCutter {

    private final static String MOVIE_STORAGE_PATH = System.getProperty("user.home") + "/movieHero/movieStorage";
    private final static String CUT_MOVIE_PATH = System.getProperty("user.home") + "/movieHero/cutMovie";
    private final FFmpegExecutor executor;

    public MovieCutter() {
        try {
            FFmpeg ffmpeg = new FFmpeg("C:\\programs\\FFMPEG\\bin\\ffmpeg.exe");
            FFprobe ffprobe = new FFprobe("C:\\programs\\FFMPEG\\bin\\ffprobe.exe");
            this.executor = new FFmpegExecutor(ffmpeg, ffprobe);
        } catch (IOException e) {
            throw new RuntimeException("Could not init MovieCutter", e);
        }
    }

    public void cut(String movieTitle, Subtitles subtitles) {
        List<FFmpegBuilder> builders = subtitles.subtitles().stream()
                .map(subtitle -> ffmpegBuilder(
                        findMovieFile(movieTitle),
                        subtitle.numberSeq(),
                        subtitle.startOffset(),
                        subtitle.duration()
                ))
                .collect(Collectors.toList());
        for (FFmpegBuilder builder : builders) {
            executor.createJob(builder).run();
        }
    }

    private File findMovieFile(String movieTitle) {
        return FileUtils.listFiles(new File(MOVIE_STORAGE_PATH), null, false).stream()
                .filter(file -> file.getName().contains(movieTitle))
                .findAny()
                .orElseThrow(() -> new RuntimeException("There is no movie " + movieTitle + " in movie storage"));
    }

    private FFmpegBuilder ffmpegBuilder(File movieFile, Integer fragmentNumber, Long startOffset, Long duration) {
        return new FFmpegBuilder()
                .overrideOutputFiles(true)
                .setInput(movieFile.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(CUT_MOVIE_PATH + "/" + FilenameUtils.removeExtension(movieFile.getName()) + "/" + fragmentNumber + ".mp4")
                .setFormat("mp4")
                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)
                .setStartOffset(startOffset, TimeUnit.MILLISECONDS)
                .setDuration(duration, TimeUnit.MILLISECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
    }

    private void split() throws IOException {
        File file = ResourceUtils.getFile("C:\\Users\\MI\\Desktop\\films\\Vertigo.avi");
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput("C:\\Users\\MI\\Desktop\\films\\VertigoPart1" + ".mp4")
                .setFormat("mp4")
                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32768)
                .setVideoFrameRate(24, 1)
                .setStartOffset(600, TimeUnit.SECONDS)
                .setDuration(5, TimeUnit.SECONDS)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        executor.createJob(builder).run();
    }
}

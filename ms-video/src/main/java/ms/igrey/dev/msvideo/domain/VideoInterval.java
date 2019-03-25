package ms.igrey.dev.msvideo.domain;

public class VideoInterval {

    private final Integer subtitleSize;
    private final Integer partsAmount;

    public VideoInterval(Integer subtitleSize, Integer partsAmount) {
        this.subtitleSize = subtitleSize;
        this.partsAmount = partsAmount;
    }

    public Integer startNumSeq(Integer partNumber) {
        return ((partNumber - 1) * subtitleSize) / partsAmount;
    }

    public Integer endNumSeq(Integer partNumber) {
        return (partNumber * subtitleSize) / partsAmount - 1;
    }
}
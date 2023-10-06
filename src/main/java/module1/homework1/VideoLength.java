package module1.homework1;

public class VideoLength {
    private VideoLength() {}

    public static int minutesToSeconds(String videoLength) {
        int[] minutesAndSeconds = parseLengthString(videoLength);

        // проверка на фотмат входных данных
        if (minutesAndSeconds.length != 2) {
            return -1;
        }

        // проверка на корректность значений
        int minutes = minutesAndSeconds[0];
        int seconds = minutesAndSeconds[1];
        if (seconds > 59 || seconds < 0 || minutes < 0) {
            return -1;
        }

        return minutesAndSeconds[0] * 60 + minutesAndSeconds[1];
    }

    private static int[] parseLengthString(String videoLength) {
        String[] minutesAndSecondsStrings = videoLength.split(":");
        int[] minutesAndSeconds = new int[minutesAndSecondsStrings.length];

        int idx = 0;
        for (String elem : minutesAndSecondsStrings) {
            minutesAndSeconds[idx++] = Integer.parseInt(elem);
        }

        return minutesAndSeconds;
    }
}

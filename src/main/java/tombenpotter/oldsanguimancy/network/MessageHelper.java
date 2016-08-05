package tombenpotter.oldsanguimancy.network;

public class MessageHelper {

    public static byte[] stringToByteArray(String string) {
        byte[] result = new byte[string.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) string.charAt(i);
        }
        return result;
    }

    public static String byteArrayToString(byte[] array) {
        String result = "";
        for (byte val : array)
            result += (char) val;
        return result;
    }

}

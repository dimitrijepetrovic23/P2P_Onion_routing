public class EncryptedMessage {
    private byte[] encryptedData;  //The encrypted message
    private String nextNodeIp;    //The IP address of the next node

    public EncryptedMessage(byte[] encryptedData, String nextNodeIp) {
        this.encryptedData = encryptedData;
        this.nextNodeIp = nextNodeIp;
    }

    public byte[] getEncryptedData() {
        return encryptedData;
    }

    public String getNextNodeIp() {
        return nextNodeIp;
    }
}
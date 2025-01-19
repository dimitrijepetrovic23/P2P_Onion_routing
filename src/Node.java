import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Node {
    private final KeyPair keyPair;  // Public-private key pair
    private final String ipAddress;  // Fake IP address

    public Node(String ipAddress) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        this.ipAddress = ipAddress;
        this.keyPair = generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    //Here we are generating public-private key pair
    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC","BC"); //We choose bouncy castle provider to be able to use ECC
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyGen.initialize(ecSpec); //Key size
        return keyGen.generateKeyPair();
    }
}
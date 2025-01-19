import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
        //Have to add a provider to be able to use ECC
        Security.addProvider(new BouncyCastleProvider());
        //Create nodes with key pairs and fake IPs
        Node nodeA = new Node("192.168.1.1");
        Node nodeB = new Node("192.168.1.2");
        Node nodeC = new Node("192.168.1.3");
        Node nodeD = new Node("192.168.1.4");

        //Add them to the list of nodes
        List<Node> nodes = new ArrayList<>();
        nodes.add(nodeD);
        nodes.add(nodeC);
        nodes.add(nodeB);
        nodes.add(nodeA);

        //We create a secret message
        String originalMessage = "JFK was assassinated by the CIA!"; //Attempting to be funny :)
        byte[] messageData = originalMessage.getBytes();
        System.out.println(messageData.length);

        //We initialize our encrypted message
        EncryptedMessage layeredMessage = null;

        //We start our multilayer encryption
        for (Node node : nodes) {
            byte[] encryptedData = MultiLayeredEncryption.encrypt(messageData, node.getPublicKey());
            System.out.println(encryptedData.length); //Here i was checking the size of the data length because at first I used RSA and the size was too big so I switched to ECC
            layeredMessage = new EncryptedMessage(encryptedData, node.getIpAddress());
            messageData = encryptedData;
        }

        //We encrypted the message
        System.out.println("Message fully encrypted with multiple layers.");

        //Here we fake the decryption process
        EncryptedMessage currentMessage = layeredMessage;
        for (int i = nodes.size() - 1; i >= 0; i--) {
            Node currentNode = nodes.get(i);

            //Decrypting the current layer
            byte[] decryptedData = MultiLayeredEncryption.decrypt(currentMessage.getEncryptedData(), currentNode.getPrivateKey());
            System.out.println("Node " + currentNode.getIpAddress() + " decrypted its layer.");

            //If this is the final node we display the message
            if (i == 0) {
                String finalMessage = new String(decryptedData);
                System.out.println("Final decrypted message: " + finalMessage);
            } else {
                //If not we pass it to the next node
                currentMessage = new EncryptedMessage(decryptedData, nodes.get(i - 1).getIpAddress());
                System.out.println("Next node to process: " + currentMessage.getNextNodeIp());
            }
        }

    }


}

import java.math.BigInteger;
class KeyPair {
    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final BigInteger k;
    private final BigInteger g;

    public KeyPair(PublicKey publicKey, PrivateKey privateKey, BigInteger k, BigInteger g) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.k = k;
        this.g = g;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public BigInteger getK() {
        return k;
    }

    public BigInteger getG() {
        return g;
    }

}

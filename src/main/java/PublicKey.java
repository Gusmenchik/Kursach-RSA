import java.math.BigInteger;

class PublicKey {
    private final BigInteger n;
    private final BigInteger a;

    public PublicKey(BigInteger n, BigInteger a) {
        this.n = n;
        this.a = a;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getA() {
        return a;
    }
}

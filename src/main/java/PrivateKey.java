import java.math.BigInteger;

class PrivateKey {
    private final BigInteger h;

    public PrivateKey(BigInteger h) {
        this.h = h;
    }

    public BigInteger getH() {
        return h;
    }
}

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RSASignatureScheme {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Генерация ключей
        KeyPair keyPair = generateKeyPair();

        // Пример данных, которые нужно подписать
        String dataToSign = "Hello, World!";

        // Вычисление хэша от данных
        byte[] hash = calculateHash(dataToSign);

        // Генерация подписи
        Signature signature = generateSignature(hash, keyPair);

        // Проверка подписи
        boolean isSignatureValid = verifySignature(hash, signature, keyPair.getPublicKey());

        System.out.println("Original Data: " + dataToSign);
        System.out.println("Hash: " + bytesToHex(hash));
        System.out.println("Signature (R, S): (" + signature.getR() + ", " + signature.getS() + ")");
        System.out.println("Is Signature Valid? " + isSignatureValid);
    }

    private static KeyPair generateKeyPair() {
        // Генерация случайных простых чисел p и q
        BigInteger p = BigInteger.probablePrime(2048, new SecureRandom());
        BigInteger q = BigInteger.probablePrime(2048, new SecureRandom());

        // Вычисление модуля n
        BigInteger n = p.multiply(q);

        // Выбор случайного числа U
        BigInteger u = new BigInteger(2048, new SecureRandom());

        // Вычисление Z = a^U mod n
        BigInteger a = new BigInteger(2048, new SecureRandom());
        BigInteger z = a.modPow(u, n);

        // Определение параметров k и g
        BigInteger h = new BigInteger(2048, new SecureRandom());
        BigInteger k = u.multiply(z).divide(h).mod(n);
        BigInteger g = h.modInverse(n);

        // Создание открытого и закрытого ключей
        PublicKey publicKey = new PublicKey(n, a);
        PrivateKey privateKey = new PrivateKey(h);

        return new KeyPair(publicKey, privateKey, k, g);
    }

    private static byte[] calculateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes());
    }

    private static Signature generateSignature(byte[] hash, KeyPair keyPair) {
        // Вычисление R и S
        BigInteger r = keyPair.getPublicKey().getA().modPow(keyPair.getK(), keyPair.getPublicKey().getN());
        BigInteger s = keyPair.getG().modPow(keyPair.getPrivateKey().getH(), keyPair.getPublicKey().getN());

        return new Signature(r, s);
    }

    private static boolean verifySignature(byte[] hash, Signature signature, PublicKey publicKey) {
        // Проверка подписи
        BigInteger leftSide = signature.getS().modPow(signature.getR(), publicKey.getN());
        BigInteger rightSide = publicKey.getA().modPow(new BigInteger(1, hash), publicKey.getN());

        return leftSide.equals(rightSide);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}

package com.aristotle.core.util;

import com.aristotle.core.exception.AppException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class PasswordUtil {
    final String RANDOM_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    final Random random = new Random();

    public String encryptPassword(String password) throws AppException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] passBytes = password.getBytes();
            byte[] digested = messageDigest.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new AppException(e);
        }
    }

    public boolean checkPassword(String textPassword, String encryptedPassword) throws AppException {
        String convertredEncryptPassword = encryptPassword(textPassword);
        return convertredEncryptPassword.equals(encryptedPassword);
    }

    public String generateRandompassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(RANDOM_CHAR.charAt(random.nextInt(RANDOM_CHAR.length())));
        }
        return sb.toString();
    }
}

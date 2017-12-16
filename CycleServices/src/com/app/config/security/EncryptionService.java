package com.app.config.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Ravi Kumar
 */

public class EncryptionService {
	private static final String CHARSET = "UTF-8";
	private static final String AesEncryptionAlgorithm = "AES";
	private static final String cipherTransformation = "AES/CBC/PKCS5Padding";
	private static final String secretKeyAlgorithm = "PBKDF2WithHmacSHA1";

	private static final String AESAuthenticationPassword = "$@fe#Gu@rd";
	private static final String AESAuthenticationSalt = "%rv@k#!";
	private static final String AESAuthenticationIV = "e675f725e675f725";

	private static final int pswdIterations = 65536;
	private static final int keySize = 128;

	public String encryptData(String plainText) throws Exception {
		byte[] plainTextbytes = plainText.getBytes(CHARSET);
		return Base64.encodeBase64String(
				encrypt(plainTextbytes, generateKey().getEncoded(), AESAuthenticationIV.getBytes(CHARSET)));
	}

	public String decryptData(String encryptedText) throws Exception {
		byte[] cipheredBytes = Base64.decodeBase64(encryptedText);
		return new String(decrypt(cipheredBytes, generateKey().getEncoded(), AESAuthenticationIV.getBytes(CHARSET)),
				CHARSET);
	}

	private byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		SecretKeySpec secretKeySpecy = new SecretKeySpec(key, AesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
		return cipher.doFinal(cipherText);
	}

	private byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, AesEncryptionAlgorithm);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		Cipher cipher = Cipher.getInstance(cipherTransformation);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		return cipher.doFinal(plainText);
	}

	private Key generateKey() throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyAlgorithm);
		PBEKeySpec spec = new PBEKeySpec(AESAuthenticationPassword.toCharArray(),
				AESAuthenticationSalt.getBytes(CHARSET), pswdIterations, keySize);
		SecretKey secretKey = factory.generateSecret(spec);
		return new SecretKeySpec(secretKey.getEncoded(), AesEncryptionAlgorithm);
	}

}

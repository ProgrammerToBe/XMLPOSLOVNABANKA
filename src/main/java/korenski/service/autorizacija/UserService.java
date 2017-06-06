package korenski.service.autorizacija;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import korenski.model.autorizacija.User;

@Service
public class UserService {

	private SecureRandom random = new SecureRandom();
	
	private JavaMailSender mailSender=null;
	
	public boolean checkInput(User user){
		
		
		return true;
	}
	
	public String generatePassword() {
		
	    return new BigInteger(60, random).toString(32);
	  }
	
	public void sendPassToUser(String password, User user){
		
		if(mailSender == null){
			mailSender = new JavaMailSenderImpl();
		    ((JavaMailSenderImpl) mailSender).setHost("smtp.gmail.com");
		    ((JavaMailSenderImpl) mailSender).setPort(587);
		     
		    ((JavaMailSenderImpl) mailSender).setUsername("bsepprojectimpl@gmail.com");
		    ((JavaMailSenderImpl) mailSender).setPassword("LetsGetADigree");
		     
		    Properties props = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.debug", "true");
		    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		}
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo("teauvranju@gmail.com");
        msg.setSubject("Registration");
        msg.setText("Mr(s) " + ",\n You have a registered account under the following username : "+user.getUsername()+".\n Your password is "+password);
        
		try{
			mailSender.send(msg);
			
			System.out.println("Sve je ok proslo!");
			
		}catch(Exception e){
			e.printStackTrace();
			//repository.delete(musterija.getId());
			System.out.println("Imamo gresku");
			
		}
		
	}
	
//	public User handleThePassword(User user, String generatedPass){
//		
//		byte[] salt = generateSalt();
//		
//		byte[] hashedPass = hashPassword(generatedPass, salt);
//		
//		//String hashedPassString =hashedPass.toString();
//		
//		user.setSalt(salt);
//		user.setPassword(hashedPass);
//		
//		return user;
//	}

	private byte[] hashPassword(String password, byte[] salt) {
		
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 256);
	    try {
	      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	      return skf.generateSecret(spec).getEncoded();
	    } catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    } catch (InvalidKeySpecException e) {
	    	e.printStackTrace();
	    }
	    return null;
	}
	
	private byte[] generateSalt() {
		return new SecureRandom().generateSeed(64);
	}
	
	
	public boolean authenticate(String attemptedPassword, byte[] storedPassword, byte[] salt) {
		
		byte[] tmpHash = hashPassword(attemptedPassword, salt);
		
		if (tmpHash.length != storedPassword.length)
			return false;
		
	    for (int i = 0; i < tmpHash.length; i++) {
	      if (tmpHash[i] != storedPassword[i])
	    	  return false;
	    }

	    return true;
	}

	public boolean checkPasswordExpiration(User user) {
		
		if(user.isChangedFirstPassword()){
			//ukoliko je lozinka vec menjana onda nije istekla
			return false;
		}
		
		long tempTime = System.currentTimeMillis();
		
		long creationDate = user.getCreationTime().getTime();
		
		long difference = tempTime - creationDate;
		
		// ako je proslo vise od 2 sata onda lozinka je istekla
		if ( difference > 7200000){
			return true;
		}else{// u suprotnom je validna
			return false;
		}
		
		
	}
}

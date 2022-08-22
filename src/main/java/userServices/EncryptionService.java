package userServices;

public class EncryptionService {

	public String encrypt(String str) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length();) {
			sb.append((char) (str.charAt(i++)+i));
		}
		return sb.toString();
	}
	
	public String decrypt(String str) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length();) {
			sb.append((char) (str.charAt(i++)-i));
		}
		return sb.toString();
	}
		
}

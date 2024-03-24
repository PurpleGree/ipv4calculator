package ipv4Calculator;
import java.util.regex.Matcher;
import ipexceptions.*;
import java.util.regex.Pattern;

public class Ipv4Verifier {
	
	private static final String IPV4_PATTERN = "[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}";
	private static final String SUBNET_MASK_PATTERN = "^1+0+$";
	private String binaryIPaddress ;
	private String binarySubnetMask ;
	
	public boolean verifyNetworkInfo(String ipv4address, String subnetMask) {
		boolean isValidNetworkInfo = false;
		boolean isValidIp = verifyIpv4Address(ipv4address);
		boolean isvalidSubnet = false ;
		
		if(subnetMask.length() == 2)
			isvalidSubnet = verifySubnetMask(Integer.parseInt(subnetMask));
		else if( subnetMask.length() == 32)
			isvalidSubnet = verifySubnetMask(subnetMask);
		
		if(isvalidSubnet && isValidIp)
			isValidNetworkInfo = true;
		return isValidNetworkInfo;
	}
//	
//	public boolean verifyNetworkInfo(String ipv4address, int cidr) {
//		boolean isValidNetworkInfo = false;
//		
//		if(verifyIpv4Address(ipv4address)&& verifySubnetMask(cidr))
//			isValidNetworkInfo = true;
//		return isValidNetworkInfo;
//	}
		
	public boolean verifyIpv4Address(String ipv4Address) {
		boolean isValidIp = false;
		int numOfValidOctets = 0 ;
		String binaryIP ;
		String combinedBinaryIP = "" ;
		
		Pattern pattern = Pattern.compile(IPV4_PATTERN);
		Matcher matchPattern = pattern.matcher(ipv4Address);
		
		
		if(matchPattern.find()) {
			String[] ipOctets = ipv4Address.split("\\.");
			
			for(String stringOctets : ipOctets) {
				int octet = Integer.parseInt(stringOctets);
				if(octet >=0 && octet <=255) {
					binaryIP = Integer.toBinaryString(octet);
					int changeBinaryToInt = Integer.parseInt(binaryIP);
					combinedBinaryIP += String.format("%08d", changeBinaryToInt);
					numOfValidOctets +=1;
				}
			}
			if(numOfValidOctets == 4) {
				isValidIp = true;
				binaryIPaddress = combinedBinaryIP;
			}
			
		}
		return isValidIp;
	}
	
	public boolean verifySubnetMask(int cidr) {
		boolean isValidMask= false;
		
		if(cidr >= 1 && cidr <= 30) {
			isValidMask = true;
		}
		return isValidMask;
	}
		
	
	public boolean verifySubnetMask(String subnetMask) {
		boolean isValidMask= false;
		int numOfValidMask= 0 ;
		String binarySubnet = "";
		String combinedBinarySubnet="";
		
		Pattern pattern = Pattern.compile(IPV4_PATTERN);
		Matcher matchPattern = pattern.matcher(subnetMask);
		
		if(matchPattern.find()) {
			String[] subnetOctets = subnetMask.split("\\.");
			
			for(int index = 0; index < subnetOctets.length ; index++) {
				int currentOctet = Integer.parseInt(subnetOctets[index]);
				if((index !=3) && (currentOctet >0 && currentOctet <=255)) {
					binarySubnet = Integer.toBinaryString(currentOctet);
					binarySubnet = binarySubnet.trim();
					int changeBinaryToInt = Integer.parseInt(binarySubnet);
					combinedBinarySubnet += String.format("%08d", changeBinaryToInt);
					numOfValidMask +=1;
				}
				else if( (index == 3) && (currentOctet >0 && currentOctet <= 252)) {
					binarySubnet = Integer.toBinaryString(currentOctet);
					binarySubnet = binarySubnet.trim();
					int changeBinaryToInt = Integer.parseInt(binarySubnet);
					combinedBinarySubnet += String.format("%08d", changeBinaryToInt);
					numOfValidMask +=1;
				}
			}
			if(numOfValidMask == 4) {
				pattern = Pattern.compile(SUBNET_MASK_PATTERN);
				matchPattern = pattern.matcher(combinedBinarySubnet);
				
				if(matchPattern.find()) {
					binarySubnetMask = matchPattern.group();
					isValidMask = true ;
				}
			}	
		}
		return isValidMask ;
	}
	
	
	public String getBinaryIp(String Ipv4address) {
		boolean isValidIP = verifyIpv4Address(Ipv4address);
		if(isValidIP)
			return binaryIPaddress; 
		else
			throw new InvalidIPaddressExeception();
	}
	
	public String getBinarySubnet(String subnetMask) {
		boolean isValidSubnet = verifySubnetMask(subnetMask);
		if(isValidSubnet)
			return binarySubnetMask; 
		else
			throw new InvalidSubnetMaskException();
	}
	
	
}
	



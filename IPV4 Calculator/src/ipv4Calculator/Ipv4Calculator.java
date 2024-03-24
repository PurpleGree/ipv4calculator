package ipv4Calculator;
import ipexceptions.*;

public class Ipv4Calculator {
	
	private String ipv4Address ;
	private String subnetMask ;
	private String cidrNotation ;
	private static Ipv4Verifier ipv4verifier = new Ipv4Verifier();
	private static Ipv4Calculator  instance = new Ipv4Calculator();
	
	private Ipv4Calculator() {}
	
	public static Ipv4Calculator getInstance(String ipv4Address, String subnetMask) {
		
		if(subnetMask.length() <= 2 && subnetMask != null ) {
			
			try {
				int cidr = Integer.parseInt(subnetMask);
				if(cidr > 0 && cidr < 31) {
//					String networkPortion = 
				}
			}catch (NumberFormatException e) {
				throw new InvalidNetworkInformationException();
			}
			
		}
		
		
		if(ipv4verifier.verifyNetworkInfo(ipv4Address, subnetMask)) {
			instance.setIpv4Address(ipv4Address);
			instance.setSubnetMask(subnetMask);
			return instance;
		}
		else
			throw new InvalidNetworkInformationException();
	}
	
	public void displayIPv4Details() {
		String cidrNotation = String.format("/%d",getNetworkPortion().length());
		System.out.println("Address: "+ ipv4Address);
		System.out.println("Netmask: "+ subnetMask + " = " + cidrNotation);
		System.out.println("=>");
		System.out.println("Network Address: "+ generateNetworkAddress() + " "+ cidrNotation);
		System.out.println("Broadcast Address: "+ generateBroadcastAddresss() +" "+ cidrNotation);
		System.out.println("First Usable Address: "+ generateFirstUsableAddress() +" "+ cidrNotation);
		System.out.println("Last Usable Address: "+ generateLastUsableAddress() +" "+ cidrNotation);
//		System.out.println("Broadcast Address: "+ generateBroadcastAddresss() + " "+ cidrNotation);
		
	}
	
	
	
	private String generateFirstUsableAddress() {
		String networkAddress = generateNetworkAddress();
		char lastdigitOfnetworkAddress = networkAddress.charAt(networkAddress.length()-1);
		int lastdigitOfUsableAddress = Integer.parseInt(String.format("%c", lastdigitOfnetworkAddress))+1;
		return networkAddress.substring(0, networkAddress.length()-1) + lastdigitOfUsableAddress;
	}
	private String generateLastUsableAddress() {
		String broadcastAddress = generateBroadcastAddresss();
		char lastdigitOfnetworkAddress = broadcastAddress.charAt(broadcastAddress.length()-1);
		int lastdigitOfUsableAddress = Integer.parseInt(String.format("%c", lastdigitOfnetworkAddress))-1;
		return broadcastAddress.substring(0, broadcastAddress.length()-1) + lastdigitOfUsableAddress;
	}
	private String hostRange() {
		
	}
	
	public String generateNetworkAddress() {
		String networkAddress = "";
		String[] ipAddressOctets = ipv4Address.split("\\.");
		String[] subnetMaskOctets = subnetMask.split("\\.");
		for(int index = 0 ; index <4 ; index++) {
			int ipv4Octet = Integer.parseInt(ipAddressOctets[index]);
			int subnetMaskOctet = Integer.parseInt(subnetMaskOctets[index]);
			
			networkAddress += (ipv4Octet & subnetMaskOctet);
			if(index != 3)
				networkAddress += ".";
		}
//		this.networkAddress = networkAddress;
		return networkAddress;
	}
	
	private String getNetworkPortion() {
		String[] subnetPortions;
		String binarySubnet = null ;
		try {
			binarySubnet= ipv4verifier.getBinarySubnet(subnetMask);
			}
		catch( InvalidSubnetMaskException e) {
				System.err.println(e.getMessage());
			}
		subnetPortions = binarySubnet.split("0", 2);
		String networkPortion = subnetPortions[0];
//		hostPortion = subnetPortions[1];
		return networkPortion;
		
	}
	
//	public int numOfAddress() {
//		return (int)(Math.pow(2, hostPortion.strip().length()));
//	}
	private String flipBits(String bits) {
		StringBuilder flippedBits = new StringBuilder();
		char[] bitsArray = bits.toCharArray();
		for(char bit : bitsArray) {
			if(bit == '1')
				bit = '0';
			else if(bit == '0')
				bit = '1';
			else
				throw new IllegalArgumentException("only 1s and 0s allowed");
			flippedBits.append(bit);
		}
		return flippedBits.toString();
		
	}
	
	public String generateBroadcastAddresss() {
		int cidrNotation= getNetworkPortion().length();
		String binaryNetworkaddress = ipv4verifier.getBinaryIp(generateNetworkAddress());
		StringBuilder broadcastAddress = new StringBuilder() ;
		String flippedhostbits = flipBits(binaryNetworkaddress.substring(cidrNotation));
		String binaryBcAddress = binaryNetworkaddress.substring(0,cidrNotation) + flippedhostbits ;
		
		int endofOctet = 8; 
		for( int startingIndex = 0 ; startingIndex < binaryBcAddress.length() ; endofOctet+=8) {
			String binaryOctet = binaryBcAddress.substring(startingIndex , endofOctet ) ;
			int decOctet = Integer.parseInt(binaryOctet,2);
			if(startingIndex == 24) 
				broadcastAddress.append(decOctet);
			else
				broadcastAddress.append(decOctet + ".");
			startingIndex = endofOctet ;	
		}
		
		return broadcastAddress.toString();
	}
	
	private void setIpv4Address(String ipv4Address) {
		this.ipv4Address = ipv4Address;
	}
	private void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

}

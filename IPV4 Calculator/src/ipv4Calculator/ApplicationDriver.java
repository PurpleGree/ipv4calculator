package ipv4Calculator;
import java.util.Scanner;

public class ApplicationDriver {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter IPV4Address:");
		String ipaddress =  input.next();
		System.out.println("Enter SubnetMask:");
		String subnetMask = input.next();
		
		Ipv4Calculator calcIP = Ipv4Calculator.getInstance(ipaddress, subnetMask);
		calcIP.displayIPv4Details();
	}
}

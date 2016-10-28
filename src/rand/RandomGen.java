package rand;
import java.math.BigInteger;
import java.util.Random;

public class RandomGen {
	
	private BigInteger rand(BigInteger a, BigInteger c, BigInteger m, BigInteger graine){
		BigInteger val = graine.multiply(a);
		val = val.add(c);
		return val.mod(m);
	}
	
	private void rand48(BigInteger graine){
		for (int i = 0; i < 10; i++) {
			graine = rand(new BigInteger("25214903917"), new BigInteger("11"), new BigInteger("2").pow(48), graine);
			System.out.println(graine);
		}
		 
	}
	
	public void rand(BigInteger graine){
		BigInteger val = null;
		Random rand = new Random();
		int bitFort;

		for (int i = 0; i < 1000; i++) {
			graine = rand(new BigInteger("25214903917"), new BigInteger("11"), new BigInteger("2").pow(48), graine);
			//System.out.println(graine);
			val = graine.divide(new BigInteger("2").pow(16));
			bitFort = val.divide(new BigInteger("2").pow(31)).intValue();
			//System.out.println(bitFort);
			if(bitFort == 0){
				val = val.mod(new BigInteger("2").pow(10));
			}
			else{
				//System.out.println("fort");
				val = val.add(graine);
				val = val.mod(new BigInteger("2").pow(10));
				val = val.add(new BigInteger("100"));
			}
			
			System.out.println(val);	
		}
		
		
	}
	
	
	
	public static void main(String[] args) {
		RandomGen r =new RandomGen();
		r.rand(new BigInteger("123456789112348994"));
	}
	

}

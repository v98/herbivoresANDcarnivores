//Vrinda Mittal
import java.io.*;
import java.util.*;

class grassland{
	protected int availability;
	protected final int[] coordinates;
	protected final int radius;

	public grassland(int units,int xcoord,int ycoord,int r){
		this.availability=units;
		this.coordinates=new int[2];
		coordinates[0]=xcoord;
		coordinates[1]=ycoord;
		this.radius=r;
	}

	public int[] getCoords(){
		return coordinates;
	}

	public int getRadius(){
		return radius;
	}

	public int getA(){
		return availability;
	}

	public void updateA(int ategrass){
		availability-=ategrass;
	}

	
}

public class World{
	public static void main(String[] args)throws IOException{

	}
}
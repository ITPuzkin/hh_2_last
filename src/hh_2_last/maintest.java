package hh_2_last;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class maintest {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length == 0)
			return;
		File file = new File(args[0]);
		if (!file.exists()) {
			System.out.println("Not Found!");
			return;
		}

		ArrayList<int[]> arr = getArray(file);
		arr.trimToSize();

		for (int[] temp : arr) {
			numberInKSystem test1 = new numberInKSystem(temp[0], temp[2]);
			numberInKSystem test2 = new numberInKSystem(temp[1], temp[2]);
			test1 = test1.divide(test2);
			test1.print();
		}

	}

	public static ArrayList<int[]> getArray(File f) throws FileNotFoundException {
		ArrayList<int[]> anw = new ArrayList<>();
		Scanner s = new Scanner(f);
		while (s.hasNextLine()) {
			int[] t = new int[3];
			t[0] = s.nextInt();
			t[1] = s.nextInt();
			t[2] = s.nextInt();
			anw.add(t);
		}
		s.close();
		return anw;
	}

}

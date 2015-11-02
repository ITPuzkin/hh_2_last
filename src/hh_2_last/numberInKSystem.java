package hh_2_last;

import java.util.ArrayList;

public class numberInKSystem {

	public ArrayList<Integer> fractionPart; // дробная в прямом порядке
	public ArrayList<Integer> integerPart; // целая чать хранится в обратном
											// порядке

	public int k;
	public int startNumber;

	public numberInKSystem(int num, int k) {
		this.startNumber = num;
		this.k = k;
		this.fractionPart = new ArrayList<>();
		this.integerPart = convert(num);
		this.fractionPart.add(0);
	}

	public ArrayList<Integer> convert(int a) {
		ArrayList<Integer> temp = new ArrayList<>();
		if (a < k) {
			temp.add(a);
			return temp;
		}
		while (1.0 * a / this.k >= this.k) {
			temp.add(a % k);
			a /= k;
		}
		temp.add(a % k);
		if (a / k != 0)
			temp.add(a / k);

		return temp;
	}

	// сравнивает, -1=меньше 0=равно 1=больше
	public int compare(numberInKSystem num) {

		// сначало сравниваем целые части
		int size1 = this.integerPart.size();
		int size2 = num.integerPart.size();
		if (size1 > size2)
			return 1;// больше разрядов
		else if (size1 < size2)
			return -1;
		else if (size1 == size2) {
			for (int i = size1 - 1; i >= 0; i--) {
				if (this.integerPart.get(i) > num.integerPart.get(i))
					return 1;
				else if (this.integerPart.get(i) < num.integerPart.get(i))
					return -1;
			}
			// проверяем дробную часть
		}
		return 0;
	}

	public int compareInt(int num) {
		numberInKSystem answ = new numberInKSystem(num, k);
		return this.compare(answ);
	}

	public void add(numberInKSystem num) {
		int flag = 0, min = 0;
		int size1 = this.integerPart.size();
		int size2 = num.integerPart.size();
		if (size1 >= size2) {
			min = size2;
			for (int i = min; i < size1; i++)
				num.integerPart.add(i, 0);
		} else {
			min = size1;
			for (int i = min; i < size2; i++)
				this.integerPart.add(i, 0);
		}

		min = this.integerPart.size();
		;
		flag = 0;

		for (int i = 0; i < min; i++) {
			int temp = this.integerPart.get(i);
			if (flag == 1) {
				this.integerPart.set(i, temp + 1);
				temp = this.integerPart.get(i);
				flag = 0;
			}
			if (temp >= k) {
				this.integerPart.set(i, temp - k);
				temp = this.integerPart.get(i);
				flag = 1;
			}
			int bak = temp + num.integerPart.get(i);
			if (bak >= k) {
				this.integerPart.set(i, bak - k);
				flag = 1;
			} else {
				this.integerPart.set(i, bak);
			}
		}
		if (flag == 1) {
			this.integerPart.add(min, 1);
		}
	}

	public void add2(numberInKSystem num) {
		int flag = 0, min = 0;
		int size1 = this.fractionPart.size();
		int size2 = num.integerPart.size();
		if (size1 >= size2) {
			min = size2;
			for (int i = min; i < size1; i++)
				num.integerPart.add(0, 0);
		} else {
			min = size1;
			for (int i = min; i < size2; i++)
				this.fractionPart.add(i, 0);
		}

		min = this.fractionPart.size();
		;
		flag = 0;

		for (int i = min - 1; i >= 0; i--) {
			int temp = this.fractionPart.get(i);
			if (flag == 1) {
				this.fractionPart.set(i, temp + 1);
				temp = this.fractionPart.get(i);
				flag = 0;
			}
			if (temp >= k) {
				this.fractionPart.set(i, temp - k);
				temp = this.fractionPart.get(i);
				flag = 1;
			}
			int bak = temp + num.integerPart.get(i);
			if (bak >= k) {
				this.fractionPart.set(i, bak - k);
				flag = 1;
			} else {
				this.fractionPart.set(i, bak);
			}
		}
		if (flag == 1) {
			this.add(new numberInKSystem(1, k));
		}
	}

	public void sdvigR() {
		this.integerPart.add(0, 0);
	}

	public void sdvigL() {
		this.integerPart.remove(0);
	}

	public numberInKSystem divide(numberInKSystem num) {

		int isFraction = 0;
		int firstSdvig = 0;
		numberInKSystem answ = new numberInKSystem(0, k);
		answ.startNumber = num.startNumber;
		int count = 0;// счетчик длины дробной части

		numberInKSystem bak;

		if (num.compareInt(1) == 0)
			return this;
		if (this.compare(num) == 0)
			return new numberInKSystem(1, k);

		while (this.compareInt(0) != 0 && count < (3 * num.startNumber) + 1) {
			if (this.compare(num) == 1) { // числитель больше знаменателя
				bak = new numberInKSystem(0, k);
				bak.add(num);
				int i = 0;
				while (bak.compare(this) == -1) {
					bak.sdvigR();
					if (firstSdvig == 0) {
					}
					i++;
				}
				firstSdvig = 1;
				if (bak.compare(this) == 0) {
					if (isFraction == 0) {
						int temp = 1;
						for (int h = 0; h < i; h++)
							temp *= k;
						answ.add(new numberInKSystem(1 * temp, k));
						return answ;
					}
				} else {
					bak.sdvigL();
					i--;
				}
				// теперь множим пока не получим
				int j = 0;
				for (j = 1; j < k; j++) {
					int temp = bak.multiply(j).compare(this);
					if (temp == 0) {
						break;
					} else if (temp == 1) {
						j--;
						break;
					}
				}
				if (j == k)
					j--;
				bak = bak.multiply(j);
				// дальше пишем значение в результат
				this.minus(bak);
				if (isFraction == 0) { // пишем в целую часть
					int temp = 1;
					for (int h = 0; h < i; h++)
						temp *= k;
					answ.add(new numberInKSystem(j * temp, k));
				} else {// иначе в дробную
					count++;
					numberInKSystem temp1 = new numberInKSystem(j, k);
					for (int h = 0; h < count - 1; h++)
						temp1.sdvigR();
					answ.add2(temp1);
				}
				if (this.compareInt(0) != 0 && isFraction != 0)
					this.sdvigR();
			} else if (this.compare(num) == -1) {
				if (isFraction == 0) {
					isFraction = 1;
					this.sdvigR();
				} else {
					count++;
					answ.fractionPart.add(0);
					this.sdvigR();
				}
			} else if (this.compare(num) == 0) {
				answ.add(new numberInKSystem(1, k));
				return answ;
			}
		}

		return answ;
	}

	public void minus(numberInKSystem num) {
		int flag = 0, min = 0;
		int size1 = this.integerPart.size();
		int size2 = num.integerPart.size();
		if (size1 >= size2) {
			min = size2;
			for (int i = min; i < size1; i++)
				num.integerPart.add(i, 0);
		} else {
			min = size1;
			for (int i = min; i < size2; i++)
				this.integerPart.add(i, 0);
		}

		int size = num.integerPart.size();
		flag = 0;
		for (int i = 0; i < size; i++) {
			if (flag != 0) {
				this.integerPart.set(i, this.integerPart.get(i) - 1);
				flag = 0;
			}
			this.integerPart.set(i, this.integerPart.get(i) - num.integerPart.get(i));

			if (this.integerPart.get(i) < 0) {
				flag = 1;
				if (i < this.integerPart.size() - 1) {
					this.integerPart.set(i, k + this.integerPart.get(i));
				} else {
					this.integerPart.remove(i);
					return;
				}
			}
		}
		for (int i = this.integerPart.size(); i > 1; i--) {
			if (this.integerPart.get(this.integerPart.size() - 1) == 0)
				this.integerPart.remove(this.integerPart.size() - 1);
		}
	}

	public numberInKSystem multiply(int num) {
		if (num >= k) {
			System.out.println("Error!");
			return null;
		}

		numberTables table = new numberTables(k);
		numberInKSystem answ = null;
		numberInKSystem bak = null;
		int flag = 0;

		for (int i = 0; i < this.integerPart.size(); i++) {
			bak = new numberInKSystem(table.get(this.integerPart.get(i), num), k);
			if (flag != 0) {
				bak.add(new numberInKSystem(flag, k));
				flag = 0;
			}
			if (bak.compareInt(k) >= 0) {
				flag = bak.integerPart.get(1);
			}
			if (answ != null)
				answ.integerPart.add(i, bak.integerPart.get(0));
			else
				answ = new numberInKSystem(bak.integerPart.get(0), k);
		}
		if (flag != 0)
			answ.integerPart.add(this.integerPart.size(), flag);

		return answ;
	}

	public numberInKSystem multiplyBig(numberInKSystem num) {
		numberInKSystem bak = new numberInKSystem(0, k);
		bak.add(this);

		numberInKSystem answer = new numberInKSystem(0, k);

		for (int i = 0; i < num.integerPart.size(); i++) {
			bak = this.multiply(num.integerPart.get(i));
			for (int j = 0; j < i; j++)
				bak.sdvigR();
			answer.add(bak);
		}
		return answer;
	}

	public boolean ciklTest(int dlina, int start) {
		for (int i = 0; i < dlina; i++) {
			if (this.fractionPart.get(start) != this.fractionPart.get(start + dlina))
				return false;
		}
		return true;
	}

	public void print() {
		int[] test = this.ciklDetect();
		for (int i = this.integerPart.size() - 1; i >= 0; i--)
			System.out.print("" + (char) ((this.integerPart.get(i) >= 10 ? (this.integerPart.get(i) + 55)
					: (this.integerPart.get(i) + 48))));
		System.out.print(".");
		if (test != null) {
			for (int i = 0; i < this.fractionPart.size(); i++) {
				if (i == test[0])
					System.out.print("(");
				System.out.print("" + (char) ((this.fractionPart.get(i) >= 10 ? (this.fractionPart.get(i) + 55)
						: (this.fractionPart.get(i) + 48))));
				if (i == (test[0] + test[1])) {
					System.out.print(")");
					break;
				}
			}
		} else {
			for (int i = 0; i < this.fractionPart.size(); i++) {
				System.out.print(this.fractionPart.get(i));
			}
		}
		System.out.println("");
	}

	public int[] ciklDetect() {
		int[] answ = new int[2];
		int flag = 1;
		int window = startNumber;
		int isFirst = -1;
		try {
			while (window > 0) {
				for (int i = 0; i < window; i++) {
					flag = 0;
					for (int j = 0; j < window; j++) {
						if (this.fractionPart.get(i + j) != this.fractionPart.get(i + j + window)) {
							flag = 1;
							break;
						}
					}
					if (flag == 0) {
						if (isFirst != -1) {
							answ[0] = i;
							answ[1] = isFirst - window;
							return answ;
						} else {
							isFirst = window;
						}
						if (ciklTest(window, i)) {
							answ[0] = i;
							answ[1] = window;
						}
						break;
					}
				}
				window--;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return answ;
	}

}

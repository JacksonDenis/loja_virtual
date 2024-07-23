package jd.dev;

import jd.dev.util.ValidaCNPJ;
import jd.dev.util.ValidaCPF;

public class TesteCPFCNPJ {
	public static void main(String[] args) {
		boolean isCnpj = ValidaCNPJ.isCNPJ("66.347.536/0001-96");
		
		System.out.print(isCnpj);
		
		boolean isCPF = ValidaCPF.isCPF("1111111");
		
		System.out.print(isCnpj);
	}
}

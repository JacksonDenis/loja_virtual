package jd.dev.util;

import java.util.InputMismatchException;

public class ValidaCPF {
    public static boolean isCPF(String CPF) {
    	
    	CPF = CPF.replaceAll("\\.","").replaceAll("\\/","").replaceAll("\\-","");
    	
        // Considera-se erro CPF's formados por uma sequência de números iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o código para eventuais erros de conversão de tipo (int)
        try {
            // Calculo do 1º dígito verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char)(r + 48);

            // Calculo do 2º dígito verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char)(r + 48);

            // Verifica se os dígitos calculados conferem com os dígitos informados
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return true;
            else
                return false;
        } catch (InputMismatchException erro) {
            return false;
        }
    }

    public static void main(String[] args) {
        String CPF = "12345678909"; // Exemplo de CPF
        System.out.printf("O CPF %s é válido? %s\n", CPF, isCPF(CPF) ? "Sim" : "Não");
    }
}
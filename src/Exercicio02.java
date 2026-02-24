public class Exercicio02 {
    public static void main(String[] args) {
        // Leitura de dimensões
        int largura = Integer.parseInt(IO.readln("Digite a largura: "));
        int altura = Integer.parseInt(IO.readln("Digite a altura: "));

        for (int i = 1; i <= altura; i++) {
            String linha = "";
            for (int j = 1; j <= largura; j++) {
                if (i == 1 || i == altura || j == 1 || j == largura) {
                    linha += "X";
                } else {
                    linha += " ";
                }
            }
            IO.println(linha);
        }
    }
}
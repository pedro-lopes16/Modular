public class Exercicio01 {
    public static void main(String[] args) {
        // Leitura da quantidade N
        String entradaN = IO.readln("Quantos números você vai digitar? ");
        int n = Integer.parseInt(entradaN);
        int[] vetor = new int[n];
        
        int soma = 0;
        int pares = 0, impares = 0;
        int maiorMedia = 0, menorMetadeMedia = 0;

        // Primeira leitura: preencher vetor e calcular soma/paridade
        for (int i = 0; i < n; i++) {
            String valor = IO.readln("Digite o número " + (i + 1) + ": ");
            vetor[i] = Integer.parseInt(valor);
            soma += vetor[i];

            if (vetor[i] % 2 == 0) pares++;
            else impares++;
        }

        double media = (double) soma / n;

        // Segunda passagem: verificações baseadas na média
        for (int i = 0; i < n; i++) {
            if (vetor[i] > media) maiorMedia++;
            if (vetor[i] < (media / 2)) menorMetadeMedia++;
        }

        // Saídas de dados
        IO.println("Soma = " + soma);
        IO.println("Media = " + media);
        IO.println("Pares: " + pares + " | Impares: " + impares);
        IO.println("Maiores que a media: " + maiorMedia);
        IO.println("Menores que a metade da media: " + menorMetadeMedia);
    }
}
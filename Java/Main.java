public class Main {
    public static void main(String[] args) {
        IElevadorService service = new ElevadorServiceImpl();

        System.out.println("Andar(es) menos utilizado(s): " + service.andarMenosUtilizado());
        System.out.println("Elevador(es) mais frequentado(s): " + service.elevadorMaisFrequentado());
        System.out.println("Período(s) de maior fluxo do(s) elevador(es) mais frequentado(s): " + service.periodoMaiorFluxoElevadorMaisFrequentado());
        System.out.println("Elevador(es) menos frequentado(s): " + service.elevadorMenosFrequentado());
        System.out.println("Período(s) de menor fluxo do(s) elevador(es) menos frequentado(s): " + service.periodoMenorFluxoElevadorMenosFrequentado());
        System.out.println("Período(s) de maior utilização do conjunto de elevadores: " + service.periodoMaiorUtilizacaoConjuntoElevadores());
        
        System.out.println("\nPercentual de uso de cada elevador:");
        System.out.printf("Elevador A: %.2f%%\n", service.percentualDeUsoElevadorA());
        System.out.printf("Elevador B: %.2f%%\n", service.percentualDeUsoElevadorB());
        System.out.printf("Elevador C: %.2f%%\n", service.percentualDeUsoElevadorC());
        System.out.printf("Elevador D: %.2f%%\n", service.percentualDeUsoElevadorD());
        System.out.printf("Elevador E: %.2f%%\n", service.percentualDeUsoElevadorE());
    }
} 
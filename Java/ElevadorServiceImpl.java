import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;


//Implementação do serviço de análise de utilização dos elevadores.
public class ElevadorServiceImpl implements IElevadorService {
    private static final char[] ELEVADORES = {'A', 'B', 'C', 'D', 'E'};
    private static final char[] TURNOS = {'M', 'V', 'N'};
    private static final int ANDAR_MINIMO = 0;
    private static final int ANDAR_MAXIMO = 15;
    private static final double PERCENTUAL_MULTIPLICADOR = 100.0;
    private static final String ARQUIVO_DADOS = "input.json";

    private final List<ElevadorData> dados;

    //Cria uma nova instância do serviço e carrega os dados do arquivo.
    public ElevadorServiceImpl() {
        this.dados = carregarDados();
    }

    // Carrega e valida os dados do arquivo JSON.
    private List<ElevadorData> carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            throw new RuntimeException("Arquivo " + ARQUIVO_DADOS + " não encontrado");
        }

        try (FileReader reader = new FileReader(arquivo)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> jsonData = gson.fromJson(reader, listType);
            
            if (jsonData == null || jsonData.isEmpty()) {
                throw new RuntimeException("Arquivo de dados vazio ou em formato inválido");
            }

            List<ElevadorData> dadosCarregados = jsonData.stream()
                .map(item -> {
                    int andar = ((Double) item.get("andar")).intValue();
                    char elevador = ((String) item.get("elevador")).charAt(0);
                    char turno = ((String) item.get("turno")).charAt(0);

                    validarDados(andar, elevador, turno);
                    return new ElevadorData(andar, elevador, turno);
                })
                .collect(Collectors.toList());

            return Collections.unmodifiableList(dadosCarregados);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo " + ARQUIVO_DADOS, e);
        }
    }

    //Valida os dados carregados do arquivo.
    private void validarDados(int andar, char elevador, char turno) {
        if (andar < ANDAR_MINIMO || andar > ANDAR_MAXIMO) {
            throw new IllegalArgumentException(
                "Andar inválido: " + andar + ". Deve estar entre " + ANDAR_MINIMO + " e " + ANDAR_MAXIMO);
        }

        if (new String(ELEVADORES).indexOf(elevador) == -1) {
            throw new IllegalArgumentException(
                "Elevador inválido: " + elevador + ". Deve ser um dos seguintes: " + Arrays.toString(ELEVADORES));
        }

        if (new String(TURNOS).indexOf(turno) == -1) {
            throw new IllegalArgumentException(
                "Turno inválido: " + turno + ". Deve ser um dos seguintes: " + Arrays.toString(TURNOS));
        }
    }

    @Override
    public List<Integer> andarMenosUtilizado() {
        Map<Integer, Long> frequenciaAndares = dados.stream()
            .collect(Collectors.groupingBy(ElevadorData::getAndar, Collectors.counting()));
        
        long menorFrequencia = frequenciaAndares.values().stream().min(Long::compareTo).orElse(0L);
        
        return frequenciaAndares.entrySet().stream()
            .filter(entry -> entry.getValue() == menorFrequencia)
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<Character> elevadorMaisFrequentado() {
        Map<Character, Long> frequenciaElevadores = dados.stream()
            .collect(Collectors.groupingBy(ElevadorData::getElevador, Collectors.counting()));
        
        long maiorFrequencia = frequenciaElevadores.values().stream().max(Long::compareTo).orElse(0L);
        
        return frequenciaElevadores.entrySet().stream()
            .filter(entry -> entry.getValue() == maiorFrequencia)
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
        List<Character> elevadoresMaisFrequentados = elevadorMaisFrequentado();
        Map<Character, Long> frequenciaTurnos = new HashMap<>();

        for (Character elevador : elevadoresMaisFrequentados) {
            Map<Character, Long> turnosPorElevador = dados.stream()
                .filter(d -> d.getElevador() == elevador)
                .collect(Collectors.groupingBy(ElevadorData::getTurno, Collectors.counting()));
            
            long maiorFluxo = turnosPorElevador.values().stream().max(Long::compareTo).orElse(0L);
            
            turnosPorElevador.entrySet().stream()
                .filter(entry -> entry.getValue() == maiorFluxo)
                .forEach(entry -> frequenciaTurnos.put(entry.getKey(), entry.getValue()));
        }

        long maiorFrequencia = frequenciaTurnos.values().stream().max(Long::compareTo).orElse(0L);
        
        return frequenciaTurnos.entrySet().stream()
            .filter(entry -> entry.getValue() == maiorFrequencia)
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<Character> elevadorMenosFrequentado() {
        Map<Character, Long> frequenciaElevadores = dados.stream()
            .collect(Collectors.groupingBy(ElevadorData::getElevador, Collectors.counting()));
        
        long menorFrequencia = frequenciaElevadores.values().stream().min(Long::compareTo).orElse(0L);
        
        return frequenciaElevadores.entrySet().stream()
            .filter(entry -> entry.getValue() == menorFrequencia)
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
        List<Character> elevadoresMenosFrequentados = elevadorMenosFrequentado();
        Map<Character, Long> frequenciaTurnos = new HashMap<>();

        for (Character elevador : elevadoresMenosFrequentados) {
            Map<Character, Long> turnosPorElevador = dados.stream()
                .filter(d -> d.getElevador() == elevador)
                .collect(Collectors.groupingBy(ElevadorData::getTurno, Collectors.counting()));
            
            long menorFluxo = turnosPorElevador.values().stream().min(Long::compareTo).orElse(0L);
            
            turnosPorElevador.entrySet().stream()
                .filter(entry -> entry.getValue() == menorFluxo)
                .forEach(entry -> frequenciaTurnos.put(entry.getKey(), entry.getValue()));
        }

        long menorFrequencia = frequenciaTurnos.values().stream().min(Long::compareTo).orElse(0L);
        
        return frequenciaTurnos.entrySet().stream()
            .filter(entry -> entry.getValue() == menorFrequencia)
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    @Override
    public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
        Map<Character, Long> frequenciaTurnos = dados.stream()
            .collect(Collectors.groupingBy(ElevadorData::getTurno, Collectors.counting()));
        
        long maiorFrequencia = frequenciaTurnos.values().stream().max(Long::compareTo).orElse(0L);
        
        return frequenciaTurnos.entrySet().stream()
            .filter(entry -> entry.getValue() == maiorFrequencia)
            .map(Map.Entry::getKey)
            .sorted()
            .collect(Collectors.toList());
    }

    private float calcularPercentualUsoElevador(char elevador) {
        long usoElevador = dados.stream()
            .filter(d -> d.getElevador() == elevador)
            .count();
        
        return (float) (usoElevador * PERCENTUAL_MULTIPLICADOR / dados.size());
    }

    @Override
    public float percentualDeUsoElevadorA() {
        return calcularPercentualUsoElevador('A');
    }

    @Override
    public float percentualDeUsoElevadorB() {
        return calcularPercentualUsoElevador('B');
    }

    @Override
    public float percentualDeUsoElevadorC() {
        return calcularPercentualUsoElevador('C');
    }

    @Override
    public float percentualDeUsoElevadorD() {
        return calcularPercentualUsoElevador('D');
    }

    @Override
    public float percentualDeUsoElevadorE() {
        return calcularPercentualUsoElevador('E');
    }
} 
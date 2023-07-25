import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // etapa 3.1
        funcionarios.add(new Funcionario("João", LocalDate.of(1980, 5, 15), new BigDecimal("2500.00"), "Gerente"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1990, 10, 25), new BigDecimal("1800.00"), "Analista"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.of(1995, 12, 8), new BigDecimal("1900.00"), "Assistente"));
        funcionarios.add(new Funcionario("Carlos", LocalDate.of(1988, 7, 3), new BigDecimal("3200.00"), "Gerente"));

        // etapa 3.2
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // etapa 3.3
        funcionarios.forEach(System.out::println);

        // etapa 3.4
        funcionarios.forEach(funcionario -> funcionario.aumentarSalario(10));

        // etapa 3.5
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // etapa 3.6
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(System.out::println);
        });

        // etapa 3.8
        int[] mesesAniversario = {10, 12};
        List<Funcionario> aniversariantes = funcionarios.stream()
                .filter(funcionario -> Arrays.stream(mesesAniversario)
                        .anyMatch(mes -> funcionario.getDataNascimento().getMonthValue() == mes))
                .toList();

        System.out.println("\nAniversariantes em outubro e dezembro:");
        aniversariantes.forEach(System.out::println);

        // etapa 3.9
        Optional<Funcionario> funcionarioMaisVelho = funcionarios.stream()
                .max(Comparator.comparing(Pessoa::getDataNascimento));

        funcionarioMaisVelho.ifPresent(funcionario -> {
            LocalDate hoje = LocalDate.now();
            int idade = hoje.getYear() - funcionario.getDataNascimento().getYear();
            System.out.println("\nFuncionário mais velho: " + funcionario.getNome() + ", Idade: " + idade + " anos");
        });

        // etapa 3.10
        List<Funcionario> funcionariosOrdenados = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .toList();

        System.out.println("\nLista de funcionários por ordem alfabética:");
        funcionariosOrdenados.forEach(System.out::println);

        // etapa 3.11
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("\nTotal dos salários dos funcionários: R$" + String.format("%,.2f", totalSalarios));

        // 3.12
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        Map<Funcionario, Integer> funcionariosSalarioMinimo = new HashMap<>();
        funcionarios.forEach(funcionario ->
                funcionariosSalarioMinimo.put(funcionario, funcionario.getSalario().divide(salarioMinimo, 0, RoundingMode.DOWN).intValue()));

        System.out.println("\nSalários em número de salários mínimos:");
        funcionariosSalarioMinimo.forEach((funcionario, qtdSalarios) ->
                System.out.println(funcionario.getNome() + ": " + qtdSalarios + " salário(s) mínimo(s)"));
    }
}
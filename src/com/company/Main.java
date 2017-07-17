/**
* Created by alunoic on 07/07/17.
*/
package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Date;

public class Main {

  // Tipo da entrada
  Scanner scan = new Scanner(new File("entrada"));
  //Scanner scan = new Scanner(System.in);

  /* Contadores */
  public static int numeroDeUsuarios;
  public static int numeroDeRecursos;

  public static int emProcessoDeAlocacao;
  public static int alocado;
  public static int emAndamento;
  public static int concluido;
  public static int totalDeAlocacoes; // Quantidade de atividades atuais

  public static int aulaTradicional;
  public static int apresentacao;
  public static int laboratorio;
  /* Contadores */

  /* Dados do Sistema */
  public static String[][] usuario = new String[100][4]; //100 usuários, cada um com 4 informações
  public static int[] numeroDeAlocacoesPorUsuario = new int[100];

  public static String[][] recurso = new String[100][3]; //100 recursos, cada um com 3 informações
  public static int[] numeroDeAlocacoesPorRecurso = new int[100];

  public static String[][] atividade = new String[1000][9]; //1000 atividades, cada uma com 9 informações
  public static int[][][] horarioAtividade = new int[1000][2][5]; // 1000 datas, cada uma com início e fim, e cada um com 5 informações
  /* Dados do Sistema*/

  public Main() throws FileNotFoundException {
  }

  // Faz uma busca pelo ID do usuário de acordo com o nome dele
  public int getUsuarioIdByName(String nome) {

    int[] id = new int[numeroDeUsuarios];
    int quantidade = 0;

    for (int i = 0; i < numeroDeUsuarios; i ++) {

      if (nome.toUpperCase().equals(usuario[i][0].toUpperCase())) {
        id[quantidade ++] = i;
      }
    }

    if (quantidade == 0) {

      return(9999);
    } else if (quantidade == 1) {

      return(id[0]);
    }
    if (quantidade > 1) {

      System.out.println("\nForam encontrados " + quantidade + " usuários com este nome!");
      System.out.println("Selecione o usuário desejado:");
      for (int i = 0; i < quantidade; i ++) {

        System.out.println("\t" + i + " - CPF: " + usuario[id[i]][3]);
      }
      int resposta = scan.nextInt(); scan.nextLine();
      return(id[resposta]);
    }

    return(9999);
  }

  // Faz uma busca pelo ID do recurso de acordo com o nome dele
  public int getRecursoIdByName(String nome) {

    for (int i = 0; i < numeroDeRecursos; i ++) {

      if (nome.toUpperCase().equals(recurso[i][0].toUpperCase())) {

        return(i);
      }
    }

    return(9999);
  }

  // Verifica se o CPF digitado já está no "Banco de Dados"
  public boolean cpfValido(String cpf) {

    for (int i = 0; i < numeroDeUsuarios; i ++) {

      if (cpf.equals(usuario[i][3])) {
        return(false);
      }
    }
    return(true);
  }

  // Altera o Status das Atividades
  public void alterarStatusDaAtividade() {

    System.out.println("Deseja alterar algum status? (-1 == N)");
    int i = scan.nextInt();
    if (i == -1) {
      return;
    } else if (i >= totalDeAlocacoes) {
      System.out.println("Comando Inválido!");
      return;
    }

    if (atividade[i][7].equals("EM PROCESSO DE ALOCAÇÃO")) {

      atividade[i][7] = "ALOCADO";
      emProcessoDeAlocacao -= 1;
      alocado += 1;
    } else if (atividade[i][7].equals("ALOCADO")) {

      atividade[i][7] = "EM ANDAMENTO";
      alocado -= 1;
      emAndamento += 1;
    } else if (atividade[i][7].equals("EM ANDAMENTO")) {

      atividade[i][7] = "CONCLUÍDO";
      emAndamento -= 1;
      concluido += 1;
    } else if (atividade[7][7].equals("CONCLUÍDO!")) {

      System.out.println("Essa atividade já foi concluída!");
      return;
    }

    System.out.println("Status da Atividade alterado com sucesso!");
  }

  // Imprime na tela a atividade armazenada no índice 'i'
  public void printarAtividade(int i) {

    System.out.println('\t' + String.valueOf(i) + " - Título: " + atividade[i][0]);
    System.out.println("\t\tDescrição: " + atividade[i][4]);
    System.out.println("\t\tTipo: " + atividade[i][1]);
    System.out.println("\t\tInício: " + horarioAtividade[i][0][0] + ':' + horarioAtividade[i][0][1] + ", "
    + horarioAtividade[i][0][2] + '/' + horarioAtividade[i][0][3] + '/' + horarioAtividade[i][0][4]);
    System.out.println("\t\tTérmino: " + horarioAtividade[i][1][0] + ':' + horarioAtividade[i][1][1] + ", "
      + horarioAtividade[i][1][2] + '/' + horarioAtividade[i][1][3] + '/' + horarioAtividade[i][1][4]);
    System.out.println("\t\tUsuário: " + atividade[i][2]);
    System.out.println("\t\tCPF: " + atividade[i][8]);
    System.out.println("\t\tRecurso: " + atividade[i][3]);
    System.out.println("\t\tParticipantes: " + atividade[i][5]);
    System.out.println("\t\tMaterial de Apoio: " + atividade[i][6]);
    System.out.println("\t\tStatus: " + atividade[i][7] + '\n');
  }

  // Cadastra um usuário
  public void cadastrarUsuario() {

    System.out.println("-------Cadastro de Usuário--------");

    System.out.println("Digite o nome:");
    String nome =  scan.nextLine();

    System.out.println("Digite o CPF do usuário:");
    String CPF = scan.nextLine().toUpperCase();
    if (!cpfValido(CPF)) {
      System.out.println("Usuário já cadastrado!");
      return;
    }

    System.out.println("Digite o e-mail:");
    String email = scan.nextLine();

    System.out.println("Digite o tipo de usuário:");
    String tipo = scan.nextLine().toUpperCase();

    usuario[numeroDeUsuarios][0] = nome;
    usuario[numeroDeUsuarios][1] = email;
    usuario[numeroDeUsuarios][2] = tipo;
    usuario[numeroDeUsuarios][3] = CPF;
    numeroDeAlocacoesPorUsuario[numeroDeUsuarios] = 0;
    numeroDeUsuarios += 1;

    System.out.println("Usuário cadastrado com sucesso!\n");
  } // 1

  // Cadastra um recurso
  public void cadastrarRecurso() {

    System.out.println("-------Cadastro de Recurso--------");

    System.out.println("Digite a identificação: ");
    String identificacao = scan.nextLine();

    System.out.println("Digite o responsável: ");
    String responsavel = scan.nextLine();

    int id = getUsuarioIdByName(responsavel);
    if (id == 9999) {

      System.out.println("Usuário inexistente!");
      return;
    }

    recurso[numeroDeRecursos][0] = identificacao;
    recurso[numeroDeRecursos][1] = responsavel;
    recurso[numeroDeRecursos][2] = usuario[id][3]; // CPF
    numeroDeAlocacoesPorRecurso[numeroDeRecursos] = 0;
    numeroDeRecursos += 1;

    System.out.println("Recurso cadastrado com sucesso!\n");
  } // 2

  // Cadastra uma atividade (aloca um recurso)
  public void cadastrarAtividade() {

    System.out.println("------Cadastro de Atividade-------");

    System.out.println("Recurso desejado: ");
    String recursoDesejado = scan.nextLine();
    int idRecurso = getRecursoIdByName(recursoDesejado);
    if (idRecurso == 9999) {
      System.out.println("Recurso não encontrado!\n");
      return;
    }

    System.out.println("Nome do alocador: ");
    String usuarioAlocador = scan.nextLine();
    int idUsuario = getUsuarioIdByName(usuarioAlocador);
    if (idUsuario == 9999) {
      System.out.println("Usuário não encontrado!\n");
      return;
    }

    System.out.println("Tipo da atividade: ");
    String tipoDaAtividade = scan.nextLine().toUpperCase();
    if ((tipoDaAtividade.equals("AULA TRADICIONAL") || tipoDaAtividade.equals("LABORATÓRIO")) && !usuario[idUsuario][2].equals("PROFESSOR")) {
      System.out.println("Você não pode alocar esse tipo de atividade!\n");
      return;
    }

    System.out.println("Data de início (H:mm d/m/a): ");
    int[][] data = new int[2][5];
    for (int j = 0; j < 5; j ++) {
      data[0][j] = scan.nextInt();
    }
    System.out.println("Data de término (H:mm d/m/a): ");
    for (int j = 0; j < 5; j ++) {
      data[1][j] = scan.nextInt();
    }
    scan.nextLine();

    // Convertendo para minutos
    int dataInicio, dataFim, alocacaoInicio, alocacaoFim;
    dataInicio = (data[0][4] * 365 * 24 * 60) + (data[0][3] * 31 * 24 * 60) + (data[0][2] * 24 * 60) + (data[0][0] * 60) + (data[0][1]);
    dataFim = (data[1][4] * 365 * 24 * 60) + (data[1][3] * 31 * 24 * 60) + (data[1][2] * 24 * 60) + (data[1][0] * 60) + (data[1][1]);
    if (dataInicio > dataFim) {
      System.out.println("A data de início deve ser menor que a data de término!\n");
      return;
    }

    for (int j = 0; j < totalDeAlocacoes; j ++) { // Verificando se o horário está disponível

      if (recursoDesejado.equalsIgnoreCase(atividade[j][3]) || usuario[idUsuario][3].equalsIgnoreCase(atividade[j][8])) { // Situações de horário conflitante

        alocacaoInicio = (horarioAtividade[j][0][4] * 365 * 24 * 60) + (horarioAtividade[j][0][3] * 31 * 24 * 60) + (horarioAtividade[j][0][2] * 24 * 60) + (horarioAtividade[j][0][0] * 60) + (horarioAtividade[j][0][1]);
        alocacaoFim = (horarioAtividade[j][1][4] * 365 * 24 * 60) + (horarioAtividade[j][1][3] * 31 * 24 * 60) + (horarioAtividade[j][1][2] * 24 * 60) + (horarioAtividade[j][1][0] * 60) + (horarioAtividade[j][1][1]);

        if ((dataInicio >= alocacaoInicio && dataInicio <= alocacaoFim) || (dataFim >= alocacaoInicio && dataFim <= alocacaoFim)) {

          System.out.println("Você não pode alocar esse recurso nesse horário!\n");
          return;
        }
      }
    }

    System.out.println("Título da atividade: ");
    String tituloDaAtividade = scan.nextLine();

    System.out.println("Descreva a atividade: ");
    String descricaoDaAtividade = scan.nextLine();

    System.out.println("Participantes da atividade: ");
    String participantesDaAtividade = scan.nextLine();

    System.out.println("Material de apoio: ");
    String materialDeApoioDaAtividade = scan.nextLine();

    horarioAtividade[totalDeAlocacoes][0] = data[0];
    horarioAtividade[totalDeAlocacoes][1] = data[1];

    atividade[totalDeAlocacoes][0] = tituloDaAtividade;
    atividade[totalDeAlocacoes][1] = tipoDaAtividade;
    atividade[totalDeAlocacoes][2] = usuarioAlocador;
    atividade[totalDeAlocacoes][3] = recursoDesejado;
    atividade[totalDeAlocacoes][4] = descricaoDaAtividade;
    atividade[totalDeAlocacoes][5] = participantesDaAtividade;
    atividade[totalDeAlocacoes][6] = materialDeApoioDaAtividade;
    atividade[totalDeAlocacoes][7] = "EM PROCESSO DE ALOCAÇÃO";
    atividade[totalDeAlocacoes][8] = usuario[idUsuario][3]; // CPF

    numeroDeAlocacoesPorRecurso[idRecurso] += 1;
    numeroDeAlocacoesPorUsuario[idUsuario] += 1;

    emProcessoDeAlocacao += 1;
    if (tipoDaAtividade.equals("AULA TRADICIONAL")) {
      aulaTradicional += 1;
    } else if (tipoDaAtividade.equals("LABORATÓRIO")) {
      laboratorio += 1;
    } else {
      apresentacao += 1;
    }
    totalDeAlocacoes += 1;

    System.out.println("Recurso em processo de alocação!\n");
  } // 3

  // Consulta (pesquisa) um usuário
  public void consultarUsuario() {

    System.out.println("-------Consulta de Usuário--------");

    System.out.println("Pesquisar usuário: ");
    String usuarioPesquisado = scan.nextLine();

    int id = getUsuarioIdByName(usuarioPesquisado);

    if (id == 9999) {

      System.out.println("Usuário não encontrado!\n");
    } else {

      System.out.println("Nome: " + usuario[id][0]);
      System.out.println("E-mail: " + usuario[id][1]);
      System.out.println("Tipo: " + usuario[id][2]);
      System.out.println("CPF: " + usuario[id][3]);

      System.out.println("Recurosos: ");
      if (numeroDeAlocacoesPorUsuario[id] > 0) {

        for (int j = 0; j < totalDeAlocacoes; j ++) {

          if (usuario[id][3].equalsIgnoreCase(atividade[j][8])) {

            printarAtividade(j);
          }
        }

        alterarStatusDaAtividade();
      } else {

        System.out.println("\tNenhum recurso alocado");
      }

      System.out.printf("\n");
    }
  } // 4

  // Consulta (pesquisa) um recurso
  public void consultarRecurso() {

    System.out.println("-------Consulta de Recurso--------");

    System.out.println("Pesquisar recurso: ");
    String recursoPesquisado = scan.nextLine();
    int id = getRecursoIdByName(recursoPesquisado);

    if (id == 9999) {

      System.out.println("Recurso não encontrado!\n");
    } else {

      System.out.println("Identificação: " + recurso[id][0]);
      System.out.println("Responsável: " + recurso[id][1]);
      System.out.println("CPF: " + recurso[id][2]);

      System.out.println("Alocações: ");
      if (numeroDeAlocacoesPorRecurso[id] > 0) {

        for (int j = 0; j < totalDeAlocacoes; j ++) {

          if (recursoPesquisado.equals(atividade[j][3])) {

            printarAtividade(j);
          }
        }

        alterarStatusDaAtividade();
      } else {

        System.out.println("\tNenhuma alocação");
      }

      System.out.printf("\n");
    }
  } // 5

  // Visualiza todas as atividades
  public void visualizarAtividades() {

    System.out.println("----Visualização de Atividades----");

    System.out.println("\nAtividades:");
    for (int i = 0; i < totalDeAlocacoes; i ++) {
      printarAtividade(i);
    }

    alterarStatusDaAtividade();
    System.out.printf("\n");
  } // 6

  // Imprime um relatório do sistema
  public void relatorio() {

    System.out.println("------------Relatório-------------");

    System.out.println("Quantidade de Usuários: " + numeroDeUsuarios);
    System.out.println("Recursos: ");
    System.out.println("\tEm processo de alocação: " + emProcessoDeAlocacao);
    System.out.println("\tAlocado: " + alocado);
    System.out.println("\tEm andamento: " + emAndamento);
    System.out.println("\tConcluído: " + concluido);
    System.out.println("\tTotal de alocações: " + totalDeAlocacoes);
    System.out.println("Atividades: ");
    System.out.println("\tAula Tradicional: " + aulaTradicional);
    System.out.println("\tLaboratório: " + laboratorio);
    System.out.println("\tApresentação: " + apresentacao);
    System.out.printf("\n");
  } // 7

  public static void main(String[] args) throws FileNotFoundException {

    Main mini = new Main();

    // Zera os contadores
    numeroDeUsuarios = 0;
    numeroDeRecursos = 0;
    emProcessoDeAlocacao = 0;
    alocado = 0;
    emAndamento = 0;
    concluido = 0;
    totalDeAlocacoes = 0;
    aulaTradicional = 0;
    apresentacao = 0;
    laboratorio = 0;

    boolean logged = true;
    // Menu principal
    while (logged) {

      System.out.println("----------Menu Principal----------");
      System.out.println("1 - Cadastrar Usuário");
      System.out.println("2 - Cadastrar Recurso");
      System.out.println("3 - Cadastrar Atividade"); // Alocar recurso
      System.out.println("4 - Consultar Usuário");
      System.out.println("5 - Consultar Recurso");
      System.out.println("6 - Visualizar Atividades");
      System.out.println("7 - Relatório");
      System.out.println("8 - Sair");

      int comando = mini.scan.nextInt();
      mini.scan.nextLine();

      switch (comando) {
        case 1: mini.cadastrarUsuario();
          break;
        case 2: mini.cadastrarRecurso();
          break;
        case 3: mini.cadastrarAtividade();
          break;
        case 4: mini.consultarUsuario();
          break;
        case 5: mini.consultarRecurso();
          break;
        case 6: mini.visualizarAtividades();
          break;
        case 7: mini.relatorio();
          break;
        case 8: logged = false;
          break;
        default: System.out.println("Por favor, selecione um comando válido!");
          break;
      }
    }

    System.out.println("Até logo! :)");
    return;
  }
}
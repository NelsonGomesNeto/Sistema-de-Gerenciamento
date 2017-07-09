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

  Scanner scan = new Scanner(new File("entrada"));
  //Scanner scan = new Scanner(System.in);

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

  public static String[][] usuario = new String[100][3];
  public static Boolean[] usuarioOcupado = new Boolean[100];
  public static int[] numeroDeAlocacoesPorUsuario = new int[100];

  public static String[][] recurso = new String[100][2];
  public static int[] numeroDeAlocacoesPorRecurso = new int[100];

  public static String[][] atividade = new String[1000][8];
  public static int[][][] horarioAtividade = new int[1000][2][5]; // [][0][] início, [][1][] fim

  public Main() throws FileNotFoundException {
  }

  public int getUsuarioIdByName(String nome) {

    for (int i = 0; i < numeroDeUsuarios; i ++) {

      if (nome.equals(usuario[i][0])) {

        return(i);
      }
    }

    return(9999);
  }

  public void printarAtividade(int i) {

    System.out.println('\t' + String.valueOf(i) + " - Título: " + atividade[i][0]);
    System.out.println("\t\tDescrição: " + atividade[i][4]);
    System.out.println("\t\tTipo: " + atividade[i][1]);
    System.out.println("\t\tInício: " + horarioAtividade[i][0][0] + ':' + horarioAtividade[i][0][1] + ", "
    + horarioAtividade[i][0][2] + '/' + horarioAtividade[i][0][3] + '/' + horarioAtividade[i][0][4]);
    System.out.println("\t\tTérmino: " + horarioAtividade[i][1][0] + ':' + horarioAtividade[i][1][1] + ", "
      + horarioAtividade[i][1][2] + '/' + horarioAtividade[i][1][3] + '/' + horarioAtividade[i][1][4]);
    System.out.println("\t\tUsuário: " + atividade[i][2]);
    System.out.println("\t\tRecurso: " + atividade[i][3]);
    System.out.println("\t\tParticipantes: " + atividade[i][5]);
    System.out.println("\t\tMaterial de Apoio: " + atividade[i][6]);
    System.out.println("\t\tStatus: " + atividade[i][7] + '\n');
  }

  public void cadastrarUsuario() {

    System.out.println("Digite o nome:");
    String nome =  scan.nextLine();

    System.out.println("Digite o e-mail:");
    String email = scan.nextLine();

    System.out.println("Digite o tipo de usuário:");
    String tipo = scan.nextLine().toUpperCase();

    usuario[numeroDeUsuarios][0] = nome;
    usuario[numeroDeUsuarios][1] = email;
    usuario[numeroDeUsuarios][2] = tipo;
    usuarioOcupado[numeroDeUsuarios] = false;
    numeroDeAlocacoesPorUsuario[numeroDeUsuarios] = 0;
    numeroDeUsuarios += 1;

    System.out.println("Usuário cadastrado com sucesso!\n");
  }

  public void cadastrarRecurso() {

    System.out.println("Digite a identificação: ");
    String identificacao = scan.nextLine();

    System.out.println("Digite o responsável: ");
    String responsavel = scan.nextLine();

    recurso[numeroDeRecursos][0] = identificacao;
    recurso[numeroDeRecursos][1] = responsavel;
    numeroDeAlocacoesPorRecurso[numeroDeRecursos] = 0;
    numeroDeRecursos += 1;

    System.out.println("Recurso cadastrado com sucesso!\n");
  }

  public void cadastrarAtividade() {

    boolean permitido = true, localizado = false;

    System.out.println("Recurso desejado: ");
    String recursoDesejado = scan.nextLine();

    System.out.println("Nome do alocador: ");
    String usuarioAlocador = scan.nextLine();

    for (int i = 0; i < numeroDeRecursos; i ++) {

      if (recursoDesejado.equals(recurso[i][0])) {
        localizado = true;

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

        int dataInicio, dataFim, alocacaoInicio, alocacaoFim;
        dataInicio = (data[0][4] * 365 * 24 * 60) + (data[0][3] * 31 * 24 * 60) + (data[0][2] * 24 * 60) + (data[0][0] * 60) + (data[0][1]);
        dataFim = (data[1][4] * 365 * 24 * 60) + (data[1][3] * 31 * 24 * 60) + (data[1][2] * 24 * 60) + (data[1][0] * 60) + (data[1][1]);

        for (int j = 0; j < totalDeAlocacoes; j ++) { // Verificando se o horário está disponível

          if (recursoDesejado.equals(atividade[j][3]) || usuarioAlocador.equals(atividade[j][2])) { // Situações de horário conflitante

            alocacaoInicio = (horarioAtividade[j][0][4] * 365 * 24 * 60) + (horarioAtividade[j][0][3] * 31 * 24 * 60) + (horarioAtividade[j][0][2] * 24 * 60) + (horarioAtividade[j][0][0] * 60) + (horarioAtividade[j][0][1]);
            alocacaoFim = (horarioAtividade[j][1][4] * 365 * 24 * 60) + (horarioAtividade[j][1][3] * 31 * 24 * 60) + (horarioAtividade[j][1][2] * 24 * 60) + (horarioAtividade[j][1][0] * 60) + (horarioAtividade[j][1][1]);

            if ((dataInicio <= alocacaoInicio && dataFim <= alocacaoFim) ||
              (dataInicio >= alocacaoFim && dataFim <= alocacaoFim)    ||
              (dataInicio <=  alocacaoInicio && dataFim >= alocacaoFim)) {

              System.out.println("Você não pode alocar esse recurso nesse horário!\n");
              permitido = false;
              break;
            }
          }
        }

        if (permitido == true) {

          System.out.println("Título da atividade: ");
          String tituloDaAtividade = scan.nextLine();

          System.out.println("Tipo da atividade: ");
          String tipoDaAtividade = scan.nextLine().toUpperCase();

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
          
          numeroDeAlocacoesPorRecurso[i] += 1;
          numeroDeAlocacoesPorUsuario[getUsuarioIdByName(usuarioAlocador)] += 1;

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
        }
      }
    }

    if (localizado == false) {
      System.out.println("Recurso não localizado");
    }
  }

  public void consultarUsuario() {

    System.out.println("Pesquisar usuário: ");
    String usuarioPesquisado = scan.nextLine();

    for (int i = 0; i < numeroDeUsuarios; i ++) {

      if (usuarioPesquisado.toUpperCase().equals(usuario[i][0].toUpperCase())) {

        System.out.println("Nome: " + usuario[i][0]);
        System.out.println("E-mail: " + usuario[i][1]);
        System.out.println("Tipo: " + usuario[i][2]);

        System.out.println("Recurosos: ");
        if (numeroDeAlocacoesPorUsuario[i] > 0) {

          for (int j = 0, k = 0; j < totalDeAlocacoes; j ++) {

            if (usuarioPesquisado.equals(atividade[j][2])) {

              printarAtividade(j);
              k += 1;
            }
          }

          System.out.println("\nDeseja alterar algum status? (-1 == N)");
          int resposta = scan.nextInt(); scan.nextLine();
          if (resposta != -1) {
            alterarStatusDaAtividade(resposta);
          }
        } else {
          System.out.println("\tNenhum recurso alocado");
        }
        System.out.printf("\n");

        return;
      }
    }

    System.out.println("Usuário não encontrado\n");
  }

  public void consultarRecurso() {

    System.out.println("Pesquisar recurso: ");
    String recursoPesquisado = scan.nextLine();

    for (int i = 0; i < numeroDeRecursos; i ++) {

      if (recursoPesquisado.toUpperCase().equals(recurso[i][0].toUpperCase())) {

        System.out.println("Identificação: " + recurso[i][0]);
        System.out.println("Responsável: " + recurso[i][1]);

        System.out.println("Alocações: ");
        if (numeroDeAlocacoesPorRecurso[i] > 0) {

          for (int j = 0, k = 0; j < totalDeAlocacoes; j ++) {

            if (recursoPesquisado.equals(atividade[j][3])) {

              printarAtividade(j);
              k += 1;
            }
          }

          System.out.println("Deseja alterar algum status? (-1 == N)");
          int resposta = scan.nextInt();
          if (resposta != -1) {
            alterarStatusDaAtividade(resposta);
          }
          return;
        } else {
          System.out.println("\tNenhuma alocação\n");
          return;
        }
      }
    }

    System.out.println("Recurso não encontrado\n");
  }

  public void visualizarAtividades() {

    System.out.println("\nAtividades:");
    for (int i = 0; i < totalDeAlocacoes; i ++) {
      printarAtividade(i);
    }
  }

  public void alterarStatusDaAtividade(int i) {

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
    }

    System.out.println("Status da Atividade alterado com sucesso!\n");
  }

  public static void main(String[] args) throws FileNotFoundException {

    Main mini = new Main();

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

    while (true) {

      System.out.println("1 - Cadastrar Usuário");
      System.out.println("2 - Cadastrar Recurso");
      System.out.println("3 - Cadastrar Atividade"); // Alocar recurso
      System.out.println("4 - Consultar Usuário");
      System.out.println("5 - Consultar Recurso");
      System.out.println("6 - Visualizar Atividades");
      
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
        default: System.out.println("Por favor, selecione um comando válido!");
          break;
      }
    }

  }
}

/*    WORKING WORKING
System.out.println("\n\n\n\n Let's see:");
      System.out.println(desejado);
      for (int i = 0; i < 2; i ++) {
          for (int j = 0; j < 5; j ++) {
              System.out.printf("%d|", data[i][j]);
          }
          System.out.println();
      }*/
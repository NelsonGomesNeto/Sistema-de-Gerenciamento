/**
 * Created by alunoic on 07/07/17.
*/
package com.company;

import java.util.Scanner;
import java.util.Date;


public class Main {

    public static int numeroDeUsuarios;
    public static int numeroDeRecursos;
    public static int emProcessoDeAlocacao;
    public static int alocado;
    public static int emAndamento;
    public static int concluido;
    public static int totalDeAlocacoes;
    public static int aulaTradicional;
    public static int apresentacoes;
    public static int laboratorio;

    public static String[][] usuario = new String[100][1000];
    public static int[] numeroDeAlocacoesPorUsuario = new int[100];

    public static String[][] recurso = new String[100][1000];
    public static int[][][] horarioAlocacoes = new int[2][100][1000];
    public static String[] estadoDasAlocacoes = new String[1000];
    public static int[] numeroDeAlocacoesPorRecurso = new int[1000];


    public void cadastrarUsuario() {

        Scanner scan = new Scanner(System.in);

        System.out.println("Digite o nome:");
        String nome = new String();
        nome =  scan.nextLine();

        System.out.println("Digite o e-mail:");
        String email = new String();
        email = scan.nextLine();

        usuario[numeroDeUsuarios][0] = nome;
        usuario[numeroDeUsuarios][1] = email;
        numeroDeAlocacoesPorUsuario[numeroDeUsuarios] = 0;
        numeroDeUsuarios += 1;

    }

    public void cadastrarRecurso() {

        Scanner scan = new Scanner(System.in);

        System.out.println("Identificação: ");
        String identificacao = scan.nextLine();

        System.out.println("Responsável: ");
        String responsavel = scan.nextLine();

        recurso[numeroDeRecursos][0] = identificacao;
        recurso[numeroDeRecursos][1] = responsavel;
        numeroDeAlocacoesPorRecurso[numeroDeRecursos] = 0;
        numeroDeRecursos += 1;
    }

    public void alocarRecurso() {

        boolean permitido = true, localizado = false;

        Scanner scan = new Scanner(System.in);

        System.out.println("Recurso: ");
        String desejado = scan.nextLine();
        System.out.println("Data de início (H:mm d/m/a): ");
        int[][] data = new int[2][5];
        for (int i = 0; i < 5; i ++) {
            data[0][i] = scan.nextInt();
        }
        System.out.println("Data de término (H:mm d/m/a): ");
        for (int i = 0; i < 5; i ++) {
            data[1][i] = scan.nextInt();
        }
        int dataInicio, dataFim, alocacaoInicio, alocacaoFim;
        dataInicio = (data[0][4] * 365 * 24 * 60) + (data[0][3] * 30 * 24 * 60) + (data[0][2] * 24 * 60) + (data[0][1] * 60) + (data[0][0]);
        dataFim = (data[1][4] * 365 * 24 * 60) + (data[1][3] * 30 * 24 * 60) + (data[1][2] * 24 * 60) + (data[1][1] * 60) + (data[1][0]);

        for (int i = 0; i < numeroDeRecursos; i ++) {

            if (desejado.equals(recurso[i][0])) {
                localizado = true;

                for (int j = 0; j < numeroDeAlocacoesPorRecurso[i]; j ++) {

                    alocacaoInicio = (horarioAlocacoes[0][j][4] * 365 * 24 * 60) + (horarioAlocacoes[0][j][3] * 30 * 24 * 60) + (horarioAlocacoes[0][j][2] * 24 * 60) + (horarioAlocacoes[0][j][1] * 60) + (horarioAlocacoes[0][j][0]);
                    alocacaoFim = (horarioAlocacoes[1][j][4] * 365 * 24 * 60) + (horarioAlocacoes[1][j][3] * 30 * 24 * 60) + (horarioAlocacoes[1][j][2] * 24 * 60) + (horarioAlocacoes[1][j][1] * 60) + (horarioAlocacoes[1][j][0]);

                    if ((dataInicio <= alocacaoInicio && dataFim <= alocacaoFim) ||
                            (dataInicio >= alocacaoFim && dataFim <= alocacaoFim)    ||
                            (dataInicio <=  alocacaoInicio && dataFim >= alocacaoFim)) {

                        System.out.println("Você não pode alocar esse recurso nesse horário");
                        permitido = false;
                        break;
                    }

                }

                if (permitido == true) {
                    System.out.println("Alocado, mas, descreva a atividade:");
                }
            }
        }

        if (localizado == false) {
            System.out.println("Recurso não localizado");
        }
    }

    public void consultarUsuario() {

        System.out.println("Nome do usuário: ");
        Scanner scan = new Scanner(System.in);
        String pesquisado = scan.nextLine();

        for (int i = 0; i < numeroDeUsuarios; i ++) {

            if (pesquisado.toUpperCase().equals(usuario[i][0].toUpperCase())) {

                System.out.println("Nome: " + usuario[i][0]);
                System.out.println("E-mail: " + usuario[i][1]);

                System.out.printf("Recurosos: ");
                for (int j = 0; j < numeroDeAlocacoesPorUsuario[i]; j ++) {
                    System.out.println(usuario[i][j + 2] + ";");
                }
                System.out.printf("\n");
            }
        }
    }

    public void consultarRecurso() {

        System.out.println("Nome do Recurso: ");
        Scanner scan = new Scanner(System.in);
        String pesquisado = scan.nextLine();

        for (int i = 0; i < numeroDeRecursos; i ++) {

            if (pesquisado.toUpperCase().equals(recurso[i][0].toUpperCase())) {

                System.out.println("Identificação: " + recurso[i][0]);
                System.out.println("Responsável: " + recurso[i][1]);

                System.out.printf("Alocações: ");
                for (int j = 0; j < numeroDeAlocacoesPorRecurso[i]; j ++) {
                    System.out.println(recurso[i][j + 2] + ";");
                }
                System.out.printf("\n");
            }
        }
    }

    public static void main(String[] args) {

        Main mini = new Main();

        numeroDeUsuarios = 0;
        numeroDeRecursos = 0;
        emProcessoDeAlocacao = 0;
        alocado = 0;
        emAndamento = 0;
        concluido = 0;
        totalDeAlocacoes = 0;
        aulaTradicional = 0;
        apresentacoes = 0;
        laboratorio = 0;


        while (true) {
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Cadastrar Recurso");
            System.out.println("3 - Alocar um Recurso");
            System.out.println("4 - Consultar Usuário");
            System.out.println("5 - Consultar Recurso");
            Scanner scan = new Scanner(System.in);
            int comando = scan.nextInt();

            switch (comando) {
                case 1: mini.cadastrarUsuario();
                    break;
                case 2: mini.cadastrarRecurso();
                    break;
                case 3: mini.alocarRecurso();
                    break;
                case 4: mini.consultarUsuario();
                    break;
                case 5: mini.consultarRecurso();
                    break;
                default:
                    break;
            }

            System.out.println("Working");

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
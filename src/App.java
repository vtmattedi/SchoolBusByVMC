
/* Author: Vitor Mattedi Carvalho. 
   Date: 13/09/2023 [P. 1 & 2]
   Date: 27/09/2023 [P.3]
   Date: 19/10/2023 [P.4]
   Project: Atividade Incremental
        Atividade Incremental da materia POO MATA 55 UFBA. 
*/

import java.time.LocalDate;
import java.util.*;

/**
 * Implements a single static scanner for all classes and methods
 * not the best/most elegant way to solve the scanner not beeing able to be
 * opened and close
 * but should be good enough
 */
class ScannerImpl {
    // a Scanner to be used only once and then closed only once
    public static Scanner scanner = new Scanner(System.in);

    /**
     * Requests a number from the user
     * 
     * @param loop Wanna loop if the input was invalid
     * @return an Integer from Scanner or 0 if loop is false and input was invalid
     */
    public static int reqInteger(boolean loop) {
        boolean done = false;
        int res = 0;
        while (!done) {
            String input = ScannerImpl.scanner.nextLine();
            try {
                res = Integer.parseInt(input);
                done = true;
            } catch (Exception e) {
                if (!loop) {
                    done = true;
                    System.out.println("input invalido. aplicando o padrao.");

                } else
                    System.out.print("'" + input + "' não é um numero valido, favor digite um numero inteiro: ");
            }
        }

        return res;
    }

    /**
     * Requests a double from the user
     * 
     * @param loop Wanna loop if the input was invalid
     * @return an Integer from Scanner or 0 if loop is false and input was invalid
     */
    public static double reqDouble(boolean loop) {
        boolean done = false;
        double res = 0;
        while (!done) {
            String input = ScannerImpl.scanner.nextLine();
            try {
                res = Double.parseDouble(input);
                done = true;
            } catch (Exception e) {
                if (!loop) {
                    done = true;
                    System.out.println("input invalido. aplicando o padrao.");

                } else
                    System.out.print(
                            "'" + input + "' não é um numero valido, favor digite um numero de ponto flutuante: ");
            }
        }

        return res;
    }

    /**
     * Requests a LocalDate from the user
     * 
     * @param loop Wanna loop if the input was invalid
     * @return a LocalDate from Scanner or now if loop is false and input was
     *         invalid
     */
    public static LocalDate reqDate(boolean loop) {
        boolean done = false;
        LocalDate res = LocalDate.now();
        while (!done) {
            try {
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy",
                        Locale.ENGLISH);// Locale.BRAZL or .Potuguese not available from intellisense over remote {nor
                                        // at home}.
                res = LocalDate.parse(scanner.nextLine(),
                        formatter);
                done = true;
            } catch (Exception e) {
                if (!loop) {
                    done = false;
                    System.out.print(" input invalido. Aplicando data de agora.");
                } else
                    System.out.println("Favor forneça uma data de nascimento valida.[formato dia/mes/ano]: ");
            }

        }
        return res;
    }

}

/**
 * PontoDeParada
 */
class PontoDeParada {
    // we need separeted variables to keep count and to register unique IDs,
    // otherwise
    // we either cant guarantee unique IDs, does not properly maintain a count or
    // cant handled objects beening deleted
    private static int _count = 0;
    private static int _nextId = 0;

    /**
     * Nome de ponto de parada
     */
    private String nome;
    /**
     * Latitude do ponto de parada
     */
    private double latitude;
    /**
     * Longitude do ponto de parada
     */
    private double longitute;
    /**
     * ID do ponto de parada
     */
    private int id;
    private ArrayList<Aluno> alunos;

    // This one satisfies the activity altough ID shoud NEVER been asked to be
    // initialized
    PontoDeParada(String nome, double lati, double longi, int id, ArrayList<Aluno> alunos) {
        this.nome = nome;
        this.latitude = lati;
        this.longitute = longi;
        this.id = id;
        this.alunos = alunos;
        _nextId++;
        _count++;
    }

    // Better constructor for the flow of the application.
    PontoDeParada(String nome, double lati, double longi) {
        this.nome = nome;
        this.latitude = lati;
        this.longitute = longi;
        this.id = _nextId;
        this.alunos = new ArrayList<Aluno>();
        _nextId++;
        _count++;
    }

    // we need separeted variables to keep count and to
    protected void finalize() {
        _count--;
    }

    public static int get_count() {
        return _count;
    }

    public static void numeroDePontos() {
        System.out.println("Existem um total de: " + _count + " pontos de parada.");
    }

    /**
     * Reciprocrates a change in a Aluno
     * 
     * @param novoAluno the Aluno object requesting the change
     * @param added     Wheather you are assigning this to the Aluno or changing it
     *                  for other object.
     *                  {@code True} if Aluno is adding this object. {@code False}
     *                  if Aluno is removing this object.
     */
    public void reciprocate(Aluno novoAluno, boolean added) {
        if (added) {
            if (!this.alunos.contains(novoAluno)) {
                this.alunos.add(novoAluno);
            }
        } else {
            if (this.alunos.contains(novoAluno)) {
                this.alunos.remove(novoAluno);
            }
        }

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }

    public int getId() {
        return id;
    }

    /* Maybe this should not be visibile --> private */

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

}

/** */
class Rota {
    // Same as ponto de parada the reasoning behind two static variables
    private static int _count = 0;
    private static int _nextId = 0;
    /**
     * O ID da rota
     */
    private int id;
    private ArrayList<PontoDeParada> pontos;

    Rota() {
        this.id = _nextId;
        this.pontos = new ArrayList<PontoDeParada>();
        _count++;
        _count++;
    }

    Rota(ArrayList<PontoDeParada> pontosDeParadas) {
        this.id = _nextId;
        this.pontos = pontosDeParadas;
        _count++;
        _count++;
    }

    // Melhor do que adicionar uma arraylist de vez no flow do programa até agora.
    /**
     * Adiciona um ponto de parada a lista de pontos associados a essa rota.
     * 
     * @param ponto O ponto de parada para ser associado a esta rota.
     */
    public void addPontoDeParada(PontoDeParada ponto) {
        this.pontos.add(ponto);
    }

    // Same as ponto de parada the reasoning behind two static variables
    protected void finalize() {
        _count--;
    }

    /**
     * Calcula e imprime a demanda dessa Rota, baseado em todos os pontos de parada
     * que ela contém.
     */
    public void calcDemanda() {
        int _acount = 0;
        for (PontoDeParada pontoDeParada : this.pontos) {
            _acount += pontoDeParada.getAlunos().size();
        }
        System.out.println("Demanda da Rota [" + id + "] é de " + _acount + " alunos, em um total de "
                + this.pontos.size() + " paradas.");
    }

    /**
     * Exibe o numero total de Rotas criadas no sistema.
     */
    public static void numeroDeRotas() {
        System.out.println("Numero de rotas: " + _count);
    }

    public int getId() {
        return id;
    }

}

/*
 * General Debugger to lower the clog during ux
 */
class Debugger {
    static boolean enabled = true;

    /**
     * logs something if enabled
     * 
     * @param s the String to be printed
     */
    static void log_e(String s) {
        if (enabled) {
            System.out.println(s);
        }
    }

    /**
     * Enables/Disbales the Debugger
     * 
     * @param enable The desired value of the Debugger
     */
    static void enable(boolean enable) {
        enabled = enable;
    }
}

/**
 * Classe para segurar os valores de informação pessoal das diversas classes
 * pois as informacoes nescessarias sao comuns.
 */
class PersonalInfo {
    // Nome Civil
    private String nomeCivil;
    // Nome Social
    private String nomeSocial;
    // Nome da Mae
    private String mae;
    // nome do pai
    private String pai;
    // Pais de origem
    private String naturalidade;
    // CPF ou CNPJ
    private String cpf_cnpj;
    // Data de Nascimento
    private LocalDate dataNascimento;

    PersonalInfo() {

    }

    PersonalInfo(String nomeCivil, String nomeSocial, String mae, String pai, String naturalidade, String cpf_cnpj,
            LocalDate dataNascimento) {
        this.nomeCivil = nomeCivil;
        if (nomeSocial.equals(null) || nomeSocial.equals("")) {
            this.nomeSocial = nomeCivil; // Preenche o nome social com o nome civil por padrão
        } else
            this.nomeSocial = nomeSocial;
        this.mae = mae;
        this.pai = pai;
        this.naturalidade = naturalidade;
        this.cpf_cnpj = cpf_cnpj;
        this.dataNascimento = dataNascimento;

    }

    /**
     * Preenche os dados apartir da ui
     */
    public void request() {

        System.out.print("Nome Civil: ");
        this.nomeCivil = ScannerImpl.scanner.nextLine();
        System.out.print("Nome Social (deixar em branco caso não queria declarar): ");
        String _name = ScannerImpl.scanner.nextLine();
        if (_name.equals("") || _name.equals("")) {
            this.nomeSocial = _name;
        }
        this.nomeSocial = this.nomeCivil;
        System.out.print("Nome do Pai: ");
        this.pai = ScannerImpl.scanner.nextLine();
        System.out.print("Nome do Mãe: ");
        this.mae = ScannerImpl.scanner.nextLine();
        System.out.print("Naturalidade: ");
        this.naturalidade = ScannerImpl.scanner.nextLine();
        System.out.print("CPF ou CNPJ: ");
        this.cpf_cnpj = ScannerImpl.scanner.nextLine();
        System.out.print("Data de Nascimento [formato dia/mes/ano]: ");
        this.dataNascimento = ScannerImpl.reqDate(true);
        // scanner.close();
    }

    /**
     * Compara duas informaçoes pessoais
     * 
     * @param comparand o outro objeto a ser comapradao
     * @return {@code True} se o nome civil ou o cpf/cnpj for o mesmo ou
     *         {@code False} caso o não tenham o mesmo nome ou o mesmo cpf/cnpj
     */
    public boolean compare(PersonalInfo comparand) {
        // implements a compare method, 2 personalInfo are the same is either civil name
        // or cpf are the same
        if (this.nomeCivil.equals(comparand.getNomeCivil()) || this.cpf_cnpj.equals(comparand.getCpf_cnpj()))
            return true;
        return false;
    }

    public String getNomeCivil() {
        return nomeCivil;
    }

    public void setNomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}

/**
 * Objeto com um Endereço
 */
class Endereço {
    // Nome da rua
    private String rua;
    // Numero do endereco
    private Integer numero;
    // Complemento
    private String complemento;
    // Bairro
    private String bairro;

    public Endereço() {

    }

    public Endereço(String rua, Integer numero, String complemento, String bairro, String telefone) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
    }

    /**
     * Preenche os dados do endereço apartir da ui
     * 
     * @return {@code True} if sucessful {@code False} otherwise
     */
    public boolean request() {

        System.out.print("Rua: ");
        this.rua = ScannerImpl.scanner.nextLine();
        System.out.print("Numero: ");
        // String _num = ScannerImpl.scanner.nextLine();
        this.numero = ScannerImpl.reqInteger(false);
        System.out.print("Complemento: ");
        this.complemento = ScannerImpl.scanner.nextLine();
        System.out.print("Bairro: ");
        this.bairro = ScannerImpl.scanner.nextLine();
        // System.out.print("Telefone: ");
        // this.telefone = ScannerImpl.scanner.nextLine();
        // scanner.close(); //some reason I cant close on methods? and then reopen
        return true;
    }

    /**
     * Gera uma string com os dados do endereço.
     * 
     * @return a string com os dados.
     */
    public String print() {
        String address = "Endereço: " + rua + ", " +
                numero + ", " +
                complemento + ", " +
                bairro + ".";
        return address;
    }

    /* Getters and Setters */

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

}

/**
 * Objeto representando uma Escola
 */
class Escola {
    private String nome;
    private String CNPJ;
    private String telefone;
    private Endereço endereco;
    private ArrayList<Aluno> alunos;

    // Construtor
    public Escola(String nome, String CNPJ, String telefone, Endereço endereço) {
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.telefone = telefone;
        this.endereco = endereço;
        this.alunos = new ArrayList<Aluno>();
    }

    /**
     * Internally executes an transfer requested by public methods
     * 
     * @param transferee The {@code Aluno} object that will be tranfered
     *                   (re-enrolled and then deleted)
     * @param destin     The {@code Escola} object that will recieve the
     *                   {@code Aluno} object
     */
    private void executeTranfer(Aluno transferee, Escola destin) {
        destin.matricularAluno(transferee.getPersonalInfo(), transferee.getEndereco(), transferee.getSerie());
        alunos.remove(transferee);
        Debugger.log_e("Aluno: " + nome + " transferido para a escola: " + destin.getNome());
    }

    /**
     * Transfere um aluno para outra escola aprtir do nome
     * 
     * @param nome    Nome do aluno
     * @param destino nome da escola de destino
     */
    public void transferirAluno(String nome, Escola destino) {
        for (Aluno aluno : alunos) {
            if (aluno.getPersonalInfo().getNomeCivil().equals(nome)) {
                executeTranfer(aluno, destino);
            }
        }
        Debugger.log_e("Aluno: " + nome + " não existe nesta escola.");
    }

    /**
     * Transfere um aluno para outra escola aprtir da matricula
     * 
     * @param matricula matricula do aluno
     * @param destino   nome da escola de destino
     */
    public void transferirAluno(int matricula, Escola destino) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matricula) {
                executeTranfer(aluno, destino);
            }
        }
        Debugger.log_e("Aluno com a matricula: " + Aluno.getMatriculaString(matricula) + " não existe nesta escola.");
    }

    public Aluno matricularAluno(PersonalInfo personalInfo, Endereço endereco, String serie) {
        Aluno _alumn = new Aluno(personalInfo, endereco, generateMatricula(), serie);
        boolean _exists = false;
        for (Aluno aluno : alunos) {
            if (_alumn.compare(aluno)) {
                _exists = true;
            }
        }
        if (_exists) {
            Debugger.log_e("Error: Aluno ja cadastrado!");
        }
        alunos.add(_alumn);
        return _alumn;
    }

    private int generateMatricula() {
        java.security.SecureRandom secureRandom = new java.security.SecureRandom();
        boolean _unique = false;
        int unique_matricula = 0;

        while (!_unique) {
            _unique = true;
            unique_matricula = secureRandom.nextInt(); // can be better but was not specified so far, will cross that
                                                       // bridge when
            // we get there
            for (Aluno aluno : alunos) {
                if (unique_matricula == aluno.getMatricula()) {
                    _unique = false;
                }
            }

        }
        return unique_matricula;

    }

    public String exibirTodos() {
        String _all = "";
        for (Aluno aluno : alunos) {
            _all += aluno.print();
            _all += "\n";
        }
        Debugger.log_e(_all);
        return _all;
    }

    /* Getters and Setters */

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String cNPJ) {
        CNPJ = cNPJ;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereço getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereço endereco) {
        this.endereco = endereco;
    }

}

/**
 * Objeto que representa um Veiculo
 */
class Veiculo {
    // Placa do Carro
    private String placa;
    // Ano do carro
    private Integer ano;
    // Modelo do Carro
    private String modelo;
    // Capacidade do Carro
    private Integer capacidade;
    // tipo do carro Próprio (0) ou alugado (1)
    private Integer tipo;
    // Numero do contrato, apenas para veículo alugado (tipo = 1)
    private Integer numContrato;

    private Contrato contrato;

    // Construtor para veículo alugado
    public Veiculo(String placa, Integer ano, String modelo, Integer capacidade, Integer tipo, Integer numContrato) {
        this.placa = placa;
        this.ano = ano;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.numContrato = numContrato;
    }

    // Construtor para veículo próprio
    public Veiculo(String placa, Integer ano, String modelo, Integer capacidade, Integer tipo) {
        this.placa = placa;
        this.ano = ano;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.numContrato = null; // Não aplicável a veículo próprio
    }

    public Contrato getContrato() {
        return contrato;
    }

    void reciprocate(Contrato contrato, boolean added) {
        this.setContrato(contrato);
    }

    public void setContrato(Contrato contrato) {
        // if this veiculo is on the contrato list, first we reciprocrate the removal
        contrato.reciprocate(this, false);
        this.contrato = contrato;
        // then we reciprocrate the addition
        contrato.reciprocate(this, true);
    }

    // retorna true se o tipo eh alugado
    public boolean isAlugado() {
        return tipo == 1;
    }

    // -------------------- SET & GET ------------------
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(Integer numContrato) {
        if (tipo == 0) {
            System.out.println("Método inválido. Carro propio");
        } else {
            this.numContrato = numContrato;
        }
    }
}

/**
 * Objeto que Representa um motorista
 */
class Motorista {
    private PersonalInfo personalInfo;
    // Endereço
    private Endereço endereco;
    // Numero de contato
    private String telefone;
    // Numero da CNH
    private String numHabilitacao;
    // Tipo da CNH
    private String catHabilitacao;
    // Tipo de contrato 0 = servidor, 1 = PJ
    private Integer tipo;
    // Numero do contrato
    private Integer numContrato;

    private ArrayList<Contrato> contratos;

    public Motorista(PersonalInfo personalInfo, Endereço endereco, String numHabilitacao, String catHabilitacao,
            Integer tipo, Integer numContrato) {
        this.personalInfo = personalInfo;
        this.endereco = endereco;
        this.numHabilitacao = numHabilitacao;
        this.catHabilitacao = catHabilitacao;
        this.tipo = tipo;
        this.numContrato = numContrato;
        this.contratos = new ArrayList<Contrato>();
    }

    // Construtor para motorista pj
    public Motorista(PersonalInfo personalInfo, Endereço endereco, String numHabilitacao, String catHabilitacao,
            Integer tipo) {
        this(personalInfo, endereco, numHabilitacao, catHabilitacao, tipo, null);
    }

    public boolean isTerceirizado() {
        return tipo == 1;
    }

    void reciprocate(Contrato contrato) {
        this.addContrato(contrato);
    }

    public void addContrato(Contrato contrato) {
        if (!contratos.contains(contrato)) {
            contratos.add(contrato);
            contrato.reciprocate(this, true);
        }
    }

    public void setNumContrato(Integer numContrato) {
        if (tipo == 0) {
            System.out.println("Método inválido. Motorista Servidor");
        } else {
            this.numContrato = numContrato;
        }
    }

    /* Getters and Setters */

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNumHabilitacao() {
        return numHabilitacao;
    }

    public void setNumHabilitacao(String numHabilitacao) {
        this.numHabilitacao = numHabilitacao;
    }

    public String getCatHabilitacao() {
        return catHabilitacao;
    }

    public void setCatHabilitacao(String catHabilitacao) {
        this.catHabilitacao = catHabilitacao;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getNumContrato() {
        return numContrato;
    }

    public ArrayList<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(ArrayList<Contrato> contratos) {
        this.contratos = contratos;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Endereço getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereço endereco) {
        this.endereco = endereco;
    }
}

/**
 * Objeto que representa um contrato
 */
class Contrato {
    private int num_contrato;
    private LocalDate data_inicio;
    private LocalDate data_fim;
    private double valor;
    private ArrayList<Veiculo> veiculos;
    private ArrayList<Motorista> motoristas;

    public Contrato(int num_contrato, LocalDate data_inicio, LocalDate data_fim, double valor) {

        this.num_contrato = num_contrato;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.valor = valor;
        this.veiculos = new ArrayList<Veiculo>();
        this.motoristas = new ArrayList<Motorista>();
    }

    public boolean addVeiculo(Veiculo veiculo) {
        if (!veiculo.isAlugado()) {
            Debugger.log_e("Veiculo não eh alugado");
            return false;
        } else if (veiculos.contains(veiculo)) {
            Debugger.log_e("Veiculo ja existe no contrato");
            return false;
        }
        veiculos.add(veiculo);
        veiculo.reciprocate(this, true);
        return true;
    }

    public boolean addMotorista(Motorista motorista) {
        if (!motorista.isTerceirizado()) {
            Debugger.log_e("Motorista não eh terceirizado");
            return false;
        } else if (motoristas.contains(motorista)) {
            Debugger.log_e("Motorista ja existe no contrato");
            return false;
        }
        motoristas.add(motorista);
        motorista.reciprocate(this);
        return true;
    }

    public void reciprocate(Veiculo veiculo, boolean added) {
        if (veiculos.contains(veiculo) && !added) {
            veiculos.remove(veiculo);
        }
        if (!veiculos.contains(veiculo) && !added) {
            veiculos.add(veiculo);
        }
    }

    public void reciprocate(Motorista motorista, boolean added) {
        if (motoristas.contains(motorista) && !added) {
            motoristas.remove(motorista);
        }
        if (!motoristas.contains(motorista) && !added) {
            motoristas.add(motorista);
        }
    }

    /* Getters and Setters */
    public int getNum_contrato() {
        return num_contrato;
    }

    public void setNum_contrato(int num_contrato) {
        this.num_contrato = num_contrato;
    }

    public LocalDate getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

/**
 * Objeto que representa um Aluno.
 */
class Aluno {
    private PersonalInfo personalInfo;
    private Endereço endereco;
    private Escola escola;
    private int matricula;
    private String serie;
    private PontoDeParada pontoDeParada;

    public Aluno(PersonalInfo personalInfo, Endereço endereco, int matricula, String serie) {
        this.personalInfo = personalInfo;
        this.endereco = endereco;
        this.matricula = matricula;
        this.serie = serie;
    }

    public boolean compare(Aluno comparand) {
        // not the same as equal(), equal() = .equals;
        // Compare 2 alunos, if thier name or cpf or matricula are the same, they are
        // the same student
        if (this.matricula == comparand.getMatricula()) // int works
            return true;
        if (this.personalInfo.compare(comparand.getPersonalInfo()))
            return true;
        return false;
    }

    static String getMatriculaString(int matriculaNum) {
        String _matricula = Integer.toHexString(matriculaNum).toUpperCase();
        while (_matricula.length() < 8) {
            _matricula = "0" + _matricula;
        }
        return _matricula;
    }

    public String print() {

        return "Nome: " + this.personalInfo.getNomeSocial() + " CPF: " + this.personalInfo.getCpf_cnpj()
                + " Matricula: " + getMatriculaString(this.matricula) + " Serie: " + this.serie;
    }

    /* Getters and Setters */
    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Endereço getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereço endereco) {
        this.endereco = endereco;
    }

    // These 2 prob. should not exist.
    // private for now
    // Aluno so existe se escola existir.
    private Escola getEscola() {
        return escola;
    }

    // private for now
    private void setEscola(Escola escola) {
        this.escola = escola;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public PontoDeParada getPontoDeParada() {
        return pontoDeParada;
    }

    public void setPontoDeParada(PontoDeParada pontoDeParada) {

        this.pontoDeParada.reciprocate(this, false);
        this.pontoDeParada = pontoDeParada;
        pontoDeParada.reciprocate(this, true);
    }

}
// ------- in-file thoughts -------
// So... Wierd to do things blindly //[P.4] not so blind anymore, but still
// inefficient
// Check on Date -> Calendar change. //[P.4] LocalDate is not deprecated.
// Changes made, all code is now on LTS.
// Debugger Added.
// ------- End P.3 -------
// Added Main function (ui).
// ui can create different objects and choose their parent objects.
// common scanner added.
// Most of the documentation was added.
// Readme created.
// Escola.matricularAluno() modified to return the Aluno created.
// Minor changes to reciprocrate in contrato to maintain cohesion.
// Still some documentation to be done, and some incosistencies to be taken care of
// ------- End P.4 -------
public class App {
    /* Keep track of created objects not the best implementation */
    static ArrayList<Rota> _rotas = new ArrayList<Rota>();
    static ArrayList<PontoDeParada> _pontos = new ArrayList<PontoDeParada>();
    static ArrayList<Escola> _escolas = new ArrayList<Escola>();
    static ArrayList<Aluno> _alunos = new ArrayList<Aluno>();
    static ArrayList<Motorista> _motoristas = new ArrayList<Motorista>();
    static ArrayList<Veiculo> _veiculos = new ArrayList<Veiculo>();
    static ArrayList<Contrato> _contratos = new ArrayList<Contrato>();

    public static void main(String[] args) throws Exception {

        System.out.println("Bem vindo ao sistema SchoolBus by VMC");
        boolean quit = false;
        Debugger.enabled = false;
        String _input = "";
        while (!quit) {
            Menu();
            _input = ScannerImpl.scanner.nextLine();
            int index = _input.indexOf(" ");
            if (index > -1) {
                String command = _input.substring(0, index);
                String arg = _input.substring(index + 1);
                Debugger.log_e("input: index: [" + index + "] cmd: '" + command + "', arg:'" + arg + "'.");
                handleCommand(command, arg);
            } else if (_input.equals("quit") || _input.equals("q")) {
                quit = true;
            } else if (_input.equals("debug")) {
                Debugger.enabled = !Debugger.enabled;
                System.out.println("estado do debugador: " + Debugger.enabled);
            } else {
                System.out.println("O input: '" + _input + "', nāo foi reconhcido como um comando valido.--");
            }

        }
        // Common (unique) closure of resource.
        ScannerImpl.scanner.close();
    }

    /** Prints out the Menu Options */
    public static void Menu() {
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(" digite: criar [aluno|rota|escola|motorista|contrato|veiculo|ponto_de_parada]");
        System.out.println("     ou: listar [pontos_de_parada|rotas]");
        System.out.println("     ou: calcular [id da rota]");
        System.out.println("     ou: relacionar_ponto [nome civil ou cpf do aluno]");
        System.out.println("     ou: [quit|q] para sair,[debug] para ligar/desligar o debugador");
        System.out.println("-------------------------------------------------------------------------------");

    }

    /**
     * Handles the command from the menu.
     * 
     * @param cmd The command to be handled.
     * @param arg The arg for that command.
     */
    private static void handleCommand(String cmd, String arg) {
        if (cmd.equals("criar")) {
            if (arg.equals("escola")) {
                System.out.print("Nome da Escola: ");
                String _nome = ScannerImpl.scanner.nextLine();
                System.out.print("CNPJ: ");
                String _CNPJ = ScannerImpl.scanner.nextLine();
                System.out.print("Telefone: ");
                String _fone = ScannerImpl.scanner.nextLine();
                System.out.print("Enderço: ");
                Endereço _end = new Endereço();
                _end.request();
                var novaEscola = new Escola(_nome, _CNPJ, _fone, _end);
                _escolas.add(novaEscola);
                System.out.println("Escola " + novaEscola.getNome() + " criada.");
            } else if (arg.equals("aluno")) {
                if (_escolas.size() == 0) {
                    // nescessario para a imposicao que n tenha aluno sem escola no sistema.
                    System.out.println("Não existem escolas no sistema. Crie uma escola antes de adicionar um Aluno.");
                    return;
                }
                int _indexEscola = -1;
                int max = _escolas.size();
                System.out.println("Escolha a escola do Aluno pelo numero: ");
                for (int i = 0; i < _escolas.size(); i++) {
                    System.out.println(" - [" + i + "]: " + _escolas.get(i).getNome());
                }
                while (_indexEscola < 0) {
                    _indexEscola = ScannerImpl.reqInteger(true);
                    if (_indexEscola >= max) {
                        _indexEscola = -1;
                    }
                }
                PersonalInfo _pi = new PersonalInfo();
                _pi.request();
                System.out.print("Serie do aluno: ");
                String _serie = ScannerImpl.scanner.nextLine();
                Endereço _end = new Endereço();
                _end.request();
                Aluno _al = _escolas.get(_indexEscola).matricularAluno(_pi, _end, _serie);
                if (_pontos.size() > 0) {
                    System.out.print("Deseja relacionar '" + _pi.getNomeCivil() + "' a um ponto de parada? [y/n] ");
                    String _response = ScannerImpl.scanner.nextLine();
                    if (_response.equals("y")) {

                        System.out.println("Lista de pontos de parada disponiveis (" + _pontos.size() + "): ");
                        int index = 0;
                        for (PontoDeParada ponto : _pontos) {
                            System.out.println(" - [" + index + "]" + " nome: " + ponto.getNome() + ".");
                            index++;
                        }

                        System.out.print("digite o index do ponto de parada do aluno: ");
                        int _pontoIndex = ScannerImpl.reqInteger(true);
                        if (_pontoIndex >= 0 && _pontoIndex < _pontos.size()) {
                            _al.setPontoDeParada(_pontos.get(_pontoIndex));
                            System.out.println("ponto de parada relacionado com sucesso.");
                        } else
                            System.out.println(" o index '" + _pontoIndex
                                    + "' é invalido, aluno ainda não relacionado a nenhum ponto de parada.");
                    }

                }
                _alunos.add(_al);
                System.out.println("Estudante " + _al.getPersonalInfo().getNomeCivil() + " criado(a).");
            } else if (arg.equals("motorista")) {
                PersonalInfo _pi = new PersonalInfo();
                _pi.request();
                System.out.print("Categoria da Habilitacao: ");
                String _catHab = ScannerImpl.scanner.nextLine();
                System.out.print("Numero da Habilitacao: ");
                String _numHab = ScannerImpl.scanner.nextLine();
                System.out.print("Tipo: [0 - CLT, 1 - PJ]");
                int _tipo = ScannerImpl.reqInteger(false);
                int _numContrato = 0;

                if (_tipo == 1) {
                    _numContrato = ScannerImpl.reqInteger(false);
                }
                var _end = new Endereço();
                _end.request();
                var _mot = _tipo == 1 ? new Motorista(_pi, _end, _numHab, _catHab, 1, _numContrato)
                        : new Motorista(_pi, _end, _numHab, _catHab, 0);
                _motoristas.add(_mot);
            } else if (arg.equals("veiculo")) {
                System.out.print("Placa: ");
                String _placa = ScannerImpl.scanner.nextLine();
                System.out.print("Modelo: ");
                String _modelo = ScannerImpl.scanner.nextLine();
                System.out.print("Ano: ");
                int _yr = ScannerImpl.reqInteger(false);
                System.out.print("Capacidade: ");
                int _cap = ScannerImpl.reqInteger(false);
                System.out.print("Tipo: [0 - Própio, 1 - Alugado] [padrao: 0]");
                int _tipo = ScannerImpl.reqInteger(false);
                int _numContrato = 0;

                if (_tipo == 1) {
                    _numContrato = ScannerImpl.reqInteger(false);
                }
                var _car = _tipo == 1 ? new Veiculo(_placa, _yr, _modelo, _cap, 1, _numContrato)
                        : new Veiculo(_placa, _yr, _modelo, _cap, 0);
                _veiculos.add(_car);
                System.out.println("Veiculo adicionado.");
            } else if (arg.equals("contrato")) {
                System.out.println("Data de Inicio: ");
                LocalDate _start = ScannerImpl.reqDate(true);
                System.out.println("Data de Termino: ");
                LocalDate _end = ScannerImpl.reqDate(true);
                System.out.println("Valor: ");
                int _val = ScannerImpl.reqInteger(false);
                System.out.println("Numero do Contrato: ");
                int _numContrato = ScannerImpl.reqInteger(false);
                Contrato _contrato = new Contrato(_numContrato, _start, _end, _val);
                _contratos.add(_contrato);
                System.out.println("Contrato adicionado.");
            } else if (arg.equals("rota")) {
                if (_pontos.size() <= 0) {
                    System.out.println(
                            "Não existem pontos de parada no sistema. Crie pontos de parada antes de criar uma rota.");
                    return;
                }
                System.out.println("Lista de pontos de parada disponiveis: ");
                int index = 0;
                for (PontoDeParada ponto : _pontos) {
                    System.out.println(" - [" + index + "]" + ":" + ponto.getNome() + ".");
                    index++;
                }
                System.out.print(
                        "digite a lista de pontos que deseja adicionar à nova rota separados por ',' (indexes invalido são ignorados), exemplo: '1,3,10' adiciona os pontos 1, 3 e 10 listados acima à sua rota: ");
                String _userPontos = ScannerImpl.scanner.nextLine();
                String[] _splited = _userPontos.split(",");
                ArrayList<Integer> _validPontos = new ArrayList<Integer>();
                for (String _currentSplit : _splited) {
                    // There is no tryParse we have do either this or a convoluted pattern search to
                    // assert that the input was even in the correct format, not the best of
                    // practices but a fair compromise IMO
                    try {
                        Integer _currentPonto = Integer.parseInt(_currentSplit);
                        // only add pontos de parada that exists in our current environment
                        if (_currentPonto < _pontos.size() && _currentPonto >= 0) {
                            // do not repeat same index
                            if (!_validPontos.contains(_currentPonto))
                                _validPontos.add(_currentPonto);
                        }
                    } catch (Exception e) {
                        // Basically means wrong input so do nothing
                    }
                }

                Rota _rota = new Rota();
                // Foi garantido acima que _pontos.get(pontoIndex) existe.
                for (Integer pontoIndex : _validPontos) {
                    _rota.addPontoDeParada(_pontos.get(pontoIndex));
                }

                System.out.println("Rota criada com [" + _validPontos.size() + "] pontos.");

            } else if (arg.equals("ponto_de_parada")) {

                System.out.print("Nome do ponto de parada: ");
                String _name = ScannerImpl.scanner.nextLine();
                System.out.print("Longitude: ");
                double _longi = ScannerImpl.reqDouble(true);
                System.out.print("Latitude: ");
                double _lati = ScannerImpl.reqDouble(true);
                _pontos.add(new PontoDeParada(_name, _lati, _longi));
                System.out.println("Ponto de parada: '" + _name + "' adicionado com sucesso.");
            } else {
                System.out.println(
                        " o argumento: '" + arg + "' não foi reconhecido como valido, tente 'criar ponto_de_parada'.");
            }
        } else if (cmd.equals("listar")) {
            if (arg.equals("rotas")) {
                System.out.println("Lista de rotas disponiveis (" + _rotas.size() + "): ");
                int index = 0;
                for (Rota rota : _rotas) {
                    System.out.println(" - [" + index + "] ID: " + rota.getId() + ".");
                    index++;
                }
            }
            if (arg.equals("pontos_de_parada")) {
                System.out.println("Lista de pontos de parada disponiveis (" + _pontos.size() + "): ");
                int index = 0;
                for (PontoDeParada ponto : _pontos) {
                    System.out
                            .println(
                                    " - [" + index + "]" + " nome: " + ponto.getNome() + " ID: " + ponto.getId() + ".");
                    index++;
                }
            } else {
                System.out.println(
                        " o argumento: '" + arg + "' não foi reconhecido como valido, tente 'listar ponto_de_parada'.");
            }
        } else if (cmd.equals("calcular")) {
            try {
                int val = Integer.parseInt(arg);
                boolean found = false;
                for (Rota rota : _rotas) {
                    if (rota.getId() == val) {
                        rota.calcDemanda();
                    }
                    found = true;
                }
                if (!found) {
                    System.out.println("A rota com ID:" + val + " não existe. Tente o comando listar rotas.");
                }
            } catch (Exception e) {
                System.out.println("'" + arg + "' nāo é um ID valido para as rotas. os IDs são do tipo int.");
            }
        } else if (cmd.equals("relacionar_ponto")) {

            if (_alunos.size() <= 0) {
                System.out.println("Não há nenhum aluno registrado no sistema.");
            return;
            }
            boolean found = false;
            int _alunoIndex = 0;
            int index = 0;

            for (Aluno aluno : _alunos) {
                index++;
                if (aluno.getPersonalInfo().getNomeCivil().equals(arg)
                        || aluno.getPersonalInfo().getCpf_cnpj().equals(arg)) {
                    found = true;
                    _alunoIndex = index;
                }
            }
            if (found) {
                System.out.println("Lista de pontos de parada disponiveis (" + _pontos.size() + "): ");
                index = 0;
                for (PontoDeParada ponto : _pontos) {
                    System.out.println(" - [" + index + "]" + " nome: " + ponto.getNome() + ".");
                    index++;
                }

                System.out.print("digite o index do ponto de parada do aluno: ");
                int _pontoIndex = ScannerImpl.reqInteger(true);
                if (_pontoIndex >= 0 && _pontoIndex < _pontos.size()) {
                    _alunos.get(_alunoIndex).setPontoDeParada(_pontos.get(_pontoIndex));
                    System.out.println("ponto de parada relacionado com sucesso.");
                } else
                    System.out.println(" o index '" + _pontoIndex
                            + "' é invalido, nenhuma mudança efetivada.");
            } else
                System.out.println("Não existe nenhum aluno com nome civil ou cpf: '" + arg + "'.");
        } else

        {
            System.out.println(" O comando: '" + cmd + "', nāo foi reconhcido como um comando valido.");
        }
    }
}

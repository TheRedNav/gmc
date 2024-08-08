package br.com.bb.dsc.util;

import java.util.ArrayList;
import java.util.List;

import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.resposta.DadosRespostaConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.ListaRegistro;
import br.com.bb.dsc.models.Facts;
import br.com.bb.dsc.models.Items;
import br.com.bb.dsc.models.Objeto;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;

public class Factory {
    
    //Facts
    public static Facts getFacts(){
        Facts facts = new Facts();
        facts.setAttributeId("core.name");
        facts.setValue("Requisição de serviços ao executante");
        facts.setLabel("name");
        facts.setDescription("Name of the object");
        facts.setProviderId("AxonScanner");
        facts.setXid("GLOSSARIO##InfaAxon#AxonGlossary#6062");
        facts.setModifiedBy("system");
        facts.setProjectedFrom("string");
        facts.setReadOnly(true);
        return facts;
    }

    public static List<Facts> getListFacts(){
        List<Facts> listaFacts = new ArrayList<>();
        listaFacts.add(getFacts());
        listaFacts.add(getFacts());
        listaFacts.add(getFacts());
        return listaFacts;
    }

    //ITEMS
    public static Items getItems(){
        Items items = new Items();
        items.setId("string");
        items.setFacts(getListFacts());
        return items;
    }

    public static List<Items> getListItems(){
        List<Items> listItems = new ArrayList<>();
        listItems.add(getItems());
        listItems.add(getItems());
        listItems.add(getItems());
        return listItems;
    }

    //OBJETO
    public static Objeto getObjeto(){
        Objeto objeto = new Objeto();
        objeto.setEtag("string");
        objeto.setItems(getListItems());
        return objeto;
    }

    public static List<Objeto> getListObjeto(){
        List<Objeto> listObjeto = new ArrayList<>();
        listObjeto.add(getObjeto());
        listObjeto.add(getObjeto());
        listObjeto.add(getObjeto());
        return listObjeto;
    }

    //Papel
    public static Papel getPapel(){
        Papel papel = new Papel();
        papel.setId("REC_HIVE_SISTEMAS_teste://Hive Metastore/hive_d1d/cicl_vida_cli");
        papel.setNomePapel("PROD:hive_d1d.cicl_vida_cli");
        papel.setTipoPapel(212);
        papel.setSiglaSistema("D1D");
        return papel;
    }

    public static List<Papel> getListPapel(){
        List<Papel> listPapel = new ArrayList<>();
        listPapel.add(getPapel());
        listPapel.add(getPapel());
        listPapel.add(getPapel());
        return listPapel;
    }

    //Unidade
    public static Unidade getUnidade(){
        Unidade unidade = new Unidade();
        unidade.setId("REC_HIVE_SISTEMAS_teste://Hive Metastore/hive_d1d/cicl_vida_cli");
        unidade.setUor(86684);
        unidade.setNome("DIREC-CLI. VAR. PF");
        unidade.setCriticidade("#INTERNA");
        return unidade;
    }

    public static List<Unidade> getListUnidade(){
        List<Unidade> listUnidade = new ArrayList<>();
        listUnidade.add(getUnidade());
        listUnidade.add(getUnidade());
        listUnidade.add(getUnidade());
        return listUnidade;
    }

    //listaRegistro
    public static ListaRegistro getListaRegistro(){
        ListaRegistro listaRegistro = new ListaRegistro();
        listaRegistro.setCodigoCriticidadePapel("$30");
        listaRegistro.setCodigoPapel("BG#0618");
        listaRegistro.setCodigoSistema( "D1D");
        listaRegistro.setCodigoSubsistema("D1D");
        listaRegistro.setIndicadorUsuarioAutorizacaoPapel("N");
        listaRegistro.setNomePapel("PROD:hive_d1d.cicl_vida_cli");
        listaRegistro.setSiglaSistema("D1D");
        return listaRegistro;
    }

    public static ArrayList<ListaRegistro> getListListaRegistro(){
        ArrayList<ListaRegistro> listListaRegistro = new ArrayList<>();
        listListaRegistro.add(getListaRegistro());
        listListaRegistro.add(getListaRegistro());
        listListaRegistro.add(getListaRegistro());
        return listListaRegistro;
    }


    //DadosRespostaConsultarDetalhesPapelPeloCodigo
    public static DadosRespostaConsultarDetalhesPapelPeloCodigo getDadosRespostaConsultarDetalhesPapelPeloCodigo(){
        DadosRespostaConsultarDetalhesPapelPeloCodigo dadosRespostaConsultarDetalhesPapelPeloCodigo = new DadosRespostaConsultarDetalhesPapelPeloCodigo();
        dadosRespostaConsultarDetalhesPapelPeloCodigo.setCodigoUnidadeOrganizacionalGestorPapel(86684);
        dadosRespostaConsultarDetalhesPapelPeloCodigo.setNomeUnidadeOrganizacionalGestorPapel("DIREC-CLI. VAR. PF");
        dadosRespostaConsultarDetalhesPapelPeloCodigo.setCodigoCriticidadePapel("$20");
        return dadosRespostaConsultarDetalhesPapelPeloCodigo;
    }


}

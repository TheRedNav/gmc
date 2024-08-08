A forma que o projeto está desenhado:

1 - Primeiro passo, mvn clean install para verificar se está tudo instalado e funcionando corretamente. 

2 - Alterar no pom.xml e incluir o número da operação que irei utilizar .
        Vamos usar a operação 8846657 como exemplo. 
        Precisamos entrar na Plataforma para pegar alguns dados e já fazer alguns testes para nos certificarmos de que a operação(IIB) está funcionando, e também pegarmos alguns dados que iremos necessitar em breve. 
        I - Acessar Plataforma > Tecnologia > Construção > Catálogo > Operações.
        II - Consultar a operação. 
            Se não tiver o número, pesquisar por palavra ou termo chave. 
        III - Após a identificação e teste da operação, clicar no botão DEPENDÊNCIA MAVEM, se for um projeto em Java, ou DEPENDÊNCIA NPM se for um projeto em TypeScript
        IV - Copiar o código que será fornecido para dentro do arquivo pom.xml. Veja este exemplo:
            <dependency>
                <groupId>br.com.bb.aqm.operacao</groupId>
                <artifactId>Op8846657-v1</artifactId>
                <version>4.7.0</version>
            </dependency>
        V - Após isto, abrir o terminal e executar o comando mvn clean install para que o projeto busque automáticamente a dependência.


3 - Criar a classe com a operação.
    No pacote br.com.bb.dsc.iib, criar uma operação com os seguintes moldes.
        I - Importar todas as classes padrões que já estão funcionando. 
            import javax.enterprise.context.RequestScoped;
            import javax.inject.Inject;
            import javax.ws.rs.Consumes;
            import javax.ws.rs.Path;
            import javax.ws.rs.Produces;
            import javax.ws.rs.core.MediaType;

            // classes de integração Consumidor Curió e IntegraçãoIIB
            import br.com.bb.dsc.integration.ConsumidorCurio;
            import br.com.bb.dsc.integration.IntegracaoIIB;  
            
            // talvez estas não sejam necessárias. Elas são referentes a classes do Eclipse. 
            import org.eclipse.microprofile.opentracing.Traced;
            import org.eclipse.microprofile.rest.client.inject.RestClient;


        II - Importar as classes de requisição e resposta da operação.
            Neste exemplo, temos as seguintes classes: 
            import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.requisicao.DadosRequisicaoListarEntidadesDominiosCriticos; - Classe de requisição
            import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.resposta.DadosRespostaListarEntidadesDominiosCriticos; - Classe de resposta
            
            **** Como saber quais são os nomes das classes?

        III - Após isto, temos que criar os outros parâmetros. O exemplo é o seguinte:

            @RequestScoped
            @Traced
            @Path("Op8846657v1") // Alterar aqui com o número da operação(IIB)
            @Consumes(MediaType.APPLICATION_JSON)
            @Produces(MediaType.APPLICATION_JSON)

        IV - Criar as classe que irá ler a operação:

            public class Op8846657 {
                @Inject
                @RestClient
                ConsumidorCurio consumidor;
            
                @IntegracaoIIB
                
                public DadosRespostaListarEntidadesDominiosCriticos listarCriticidade (int numeroPagina){
                
                    DadosRequisicaoListarEntidadesDominiosCriticos requisicao = new DadosRequisicaoListarEntidadesDominiosCriticos();
                    requisicao.setNumeroPagina(numeroPagina);
                    DadosRespostaListarEntidadesDominiosCriticos resposta = consumidor.executarOp8846657v1(requisicao);
                    
                    return resposta;
                    }
                }

        

4 - Alterar a classe OperacoesService.java

    Aqui precisamos criar a classe que irá fazer o chamado da operação(IIB)

        I - Incluir o código da operação para ser injetada na aplicação. O exemplo e o seguinte:
            
            @Inject
            Op8846657 op8846657;

        II - Feito isto, agora precisamos criar o método que irá ler a injeção.
            
            *** Acho que aqui que será criado o for para ler a lista de todos os 

            public ArrayList<ListaEntidades> listaCriticidade(int pagina){
                return op8846657.listarCriticidade(pagina).getListaEntidades();
                }

5 - Alterar a classe ConsumidorCurio.java
    Aqui também precisamos incluir a operação para que ela seja chamada pelo Curió. 
    *** Meu Deus, eu não tenho ideia do que tudo isto significa. 

       I - Incluir o código
        
        @POST
        @Path("op8846657v1")
        @IntegracaoIIB
        DadosRespostaListarEntidadesDominiosCriticos executarOp8846657v1(DadosRequisicaoListarEntidadesDominiosCriticos requisicao);



















        
















        - Como saber se a operação que estou criando está funcionando?
            I - Consigo consultar a Operação(IIB) em Plataforma > Tecnologia > Construção > Catálogo > Operações. 
                    Aqui consigo pesquisar e consultar todos as operações que exitem e todas as que eu estou utilizando. 
                    Neste exemplo, vamos usar a operação 8846657.


II - 
            III - 
package br.com.jrsr.financeapi.infrastructure.components;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseOutputText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpenAIComponent {

    @Value("${openai.key}")
    private String key;

    public String send(String prompt){

        // Cria o cliente OpenAI com a chave da API
        OpenAIClient client = OpenAIOkHttpClient.builder().apiKey(key).build();

        // Configura os parâmetros da solicitação
        ResponseCreateParams params = ResponseCreateParams.builder().input(prompt).model(ChatModel.GPT_4_1).build();

        // Envia a solicitação e obtém as mensagens de resposta
        List<ResponseOutputMessage> messages = client.responses().create(params).output()
                .stream().flatMap(item -> item.message().stream()).toList();

        // Extrai o texto da primeira mensagem de resposta
        ResponseOutputText response = messages.getFirst().content().getFirst().asOutputText();

        // Retorna o texto da resposta
        return response.text();
    }
}

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class AddIndex {
    public static void main(String[] args) throws IOException {
        //1.创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //2创建索引
        //2.1 创建索引请求对象,包含索引名
        CreateIndexRequest request = new CreateIndexRequest("user");
        //2.2 发送索引请求，获取响应
        CreateIndexResponse response = esClient.indices().create(request,
                RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        // 响应状态
        System.out.println("操作状态 = " + acknowledged);

        //3.关闭ES客户端
        esClient.close();
    }
}

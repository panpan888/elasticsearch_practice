package 查询;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import com.alibaba.fastjson.JSON;
/*
 * 根据term条件查询
 */
public class EsDocQueryByTerm中文 {
    public static void main(String[] args) throws Exception {
        //1.创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 2.构造条件查询 : termQuery
        SearchRequest request = new SearchRequest();
        request.indices("user");//2.1 指定user索引
        //2.2 两种方法指定term的查询条件
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30)));//和全量查询的不同就在这里
        //上面这行的代码的分解如下:
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("sex","男");
//        sourceBuilder.query(termsQueryBuilder);
//        request.source(sourceBuilder);
        //3.高级客户端进行搜索
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //4.遍历搜索结果
        SearchHits hits = response.getHits();//4.1从响应中取出命中结果,并遍历之
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for ( SearchHit hit : hits ) {
            //4.2 转json格式再打印
            JSONObject jsonObject = JSONObject.parseObject(hit.getSourceAsString());
            String pretty = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
            System.out.println(pretty);
        }

        //5.关闭es高级客户端
        esClient.close();
    }
}

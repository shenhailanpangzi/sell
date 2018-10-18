package com.lanpang.sell.repository;

import com.lanpang.sell.SellApplication;
import com.lanpang.sell.dataobject.Article;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * elasticsearch测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes=Application.class)
@SpringBootTest(classes= SellApplication.class)
public class ArticleSearchRepositoryTest {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /*测试自动创建mapping
     *curl '192.168.0.91:9200/_cat/indices?v'
     * curl -XGET "http://192.168.0.91:9200/article_index/_mapping?pretty"
     *
     *  */
    @Test
    public void test(){
        System.out.println("演示初始化");
    }



    /*
     *保存测试
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void testSave(){
        Date date = new Date();
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);


        Article article=new Article(1L,"srpignMVC教程","srpignMVC","srpignMVC入门到放弃",date,22L);
//        Article article1=new Article(4L,"srpig教程","spring","spring入门到放弃",createdate,20L);
//        Article article2=new Article(5L,"srpigCloud教程","springCloud","springCloud入门到放弃",createdate,20L);
//        Article article3=new Article(6L,"java教程","java","java入门到放弃",createdate,120L);
//        Article article4=new Article(7L,"php教程","php","php入门到放弃",createdate,160L);

        articleSearchRepository.save(article);
//        articleSearchRepository.save(article1);
//        articleSearchRepository.save(article2);
//        articleSearchRepository.save(article3);
//        articleSearchRepository.save(article4);

//        Article article8=new Article(8L,"mysql教程","mysql","mysql入门到放弃",createdate,460L);
//        Article article9=new Article(9L,"redis教程","redis","redis入门到放弃",createdate,60L);
//        Article article10=new Article(10L,"c教程","c","c教程入门到放弃",createdate,600L);
        //bulk index 批量方式插入
//        List<Article> sampleEntities = Arrays.asList(article4,article8);
//        articleSearchRepository.save(sampleEntities);

    }



    /*
     *获取所有测试
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void testFetchAll(){
        for (Article article : articleSearchRepository.findAll()) {
            System.out.println(article.toString());
        }
    }


    /*
     *精确查找
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void testFetchArticle(){
        for (Article article : articleSearchRepository.findByTitle("srpignMVC教程")) {
            System.out.println(article.toString());
        }
    }




    /*
     *分页测试
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void testPage(){

        List<Article> list;
        // list=articleSearchRepository.findByTitleAndClickCount("教程",20 );//and
        // list=articleSearchRepository.findByTitleOrClickCount("教程",20 );//or

        // 分页参数:分页从0开始，clickCount倒序
        Pageable pageable = new PageRequest(0, 5, Sort.Direction.DESC,"clickCount");
        Page<Article> pageageRsutl=articleSearchRepository.findByContent("入门",pageable );
        System.out.println("总页数"+pageageRsutl.getTotalPages());
        list= pageageRsutl.getContent();//结果


        for (Article article : list) {
            System.out.println(article.toString());
        }
    }


    /*
     *其他查找
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void testDls(){

        List<Article> list;

        // 创建搜索 DSL:多条件搜索
        /* 搜索模式: boolQuery */
        Pageable pageable = new PageRequest(0, 5);//分页
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(boolQuery().should(QueryBuilders.matchQuery("content", "教程")),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .add(boolQuery().should(QueryBuilders.matchQuery("clickCount", 20)),
                        ScoreFunctionBuilders.weightFactorFunction(1000));


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();

        System.out.println("\n search  DSL  = \n " + searchQuery.getQuery().toString());

        Page<Article> searchPageResults = articleSearchRepository.search(searchQuery);
        list= searchPageResults.getContent();//结果

        for (Article article : list) {
            System.out.println(article.toString());
        }
    }



    /*
     *聚合查询测试
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void testScore(){

        List<Article> list;

        // 创建搜索 DSL 查询:weightFactorFunction是评分函数，官网的控制相关度中有详细讲解价格，地理位置因素
        /* 搜索模式 */
        String SCORE_MODE_SUM = "sum"; // 权重分求和模式
        Float  MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        // Function Score Query
        Pageable pageable = new PageRequest(0, 5);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(boolQuery().should(QueryBuilders.matchQuery("content", "教程")),
                        ScoreFunctionBuilders.weightFactorFunction(1000))
                .add(boolQuery().should(QueryBuilders.matchQuery("clickCount", 20)),
                        ScoreFunctionBuilders.weightFactorFunction(1000)).
                        scoreMode(SCORE_MODE_SUM).setMinScore(MIN_SCORE);//分值模式设置为:求和,


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable).build();

        System.out.println("\n search  DSL  = \n " + searchQuery.getQuery().toString());

        Page<Article> searchPageResults = articleSearchRepository.search(searchQuery);
        list= searchPageResults.getContent();//结果

        for (Article article : list) {
            System.out.println(article.toString());
        }
    }


    /*
     *elasticsearchTemplate自定义查询：提交时间倒叙
     *elasticsearchTemplate
     * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
     */
    @Test
    public void etmTest() {


        //查询关键字
        String word="c入门";

        // 分页设置,postTime倒序
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.DESC,"postTime");

        SearchQuery searchQuery;

        //0.使用queryStringQuery完成单字符串查询queryStringQuery(word, "title")
        //1.multiMatchQuery多个字段匹配 .operator(MatchQueryBuilder.Operator.AND)多项匹配使用and查询即完全匹配都存在才查出来
        //searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(word, "title", "content").operator(MatchQueryBuilder.Operator.AND)).withPageable(pageable).build();

        //2.多条件查询：title和content必须包含word=“XXX”且clickCount必须大于200的以postTime倒序分页结果
        word="教程";
        searchQuery = new NativeSearchQueryBuilder().withQuery(boolQuery().must(multiMatchQuery(word, "title", "content").operator(MatchQueryBuilder.Operator.AND)).must(rangeQuery("clickCount").gt(200))).withPageable(pageable).build();

        List<Article> list= elasticsearchTemplate.queryForList(searchQuery, Article.class);

        for (Article article : list) {
            System.out.println(article.toString());
        }
    }
//   elasticsearchTemplate 查询
    @Test
    public void testa() {
        //这一步是最关键的
        Client client = elasticsearchTemplate.getClient();
        // @Document(indexName = "product", type = "book")
        SearchRequestBuilder srb = client.prepareSearch("article_index").setTypes("article");
        SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet(); // 查询所有
        SearchHits hits = sr.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            list.add(source);
            System.out.println(hit.getSourceAsString());
        }
    }
}
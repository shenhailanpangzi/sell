package com.lanpang.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//配置自动扫描mapper文件
@MapperScan(basePackages = "com.lanpang.sell.dataobject.mapper")
public class SellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);
	}

//	@Autowired
//	private ArticleSearchRepository articleSearchRepository;
//	@Autowired
//	private ElasticsearchTemplate elasticsearchTemplate;
//	/*测试自动创建mapping
//	 *curl '192.168.0.91:9200/_cat/indices?v'
//	 * curl -XGET "http://192.168.0.91:9200/article_index/_mapping?pretty"
//	 *
//	 *  */
//	@Test
//	public void test(){
//		System.out.println("演示初始化");
//	}
//
//
//
//	/*
//	 *保存测试
//	 * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
//	 */
//	@Test
//	public void testSave(){
//		Article article=new Article(3L,"srpignMVC教程","srpignMVC","srpignMVC入门到放弃",new Date(),22L);
//		Article article1=new Article(4L,"srpig教程","spring","spring入门到放弃",new Date(),20L);
//		Article article2=new Article(5L,"srpigCloud教程","springCloud","springCloud入门到放弃",new Date(),20L);
//		Article article3=new Article(6L,"java教程","java","java入门到放弃",new Date(),120L);
//		Article article4=new Article(7L,"php教程","php","php入门到放弃",new Date(),160L);
//
////        articleSearchRepository.save(article);
////        articleSearchRepository.save(article1);
////        articleSearchRepository.save(article2);
////        articleSearchRepository.save(article3);
////        articleSearchRepository.save(article4);
//
//		Article article8=new Article(8L,"mysql教程","mysql","mysql入门到放弃",new Date(),460L);
//		Article article9=new Article(9L,"redis教程","redis","redis入门到放弃",new Date(),60L);
//		Article article10=new Article(10L,"c教程","c","c教程入门到放弃",new Date(),600L);
//		//bulk index 批量方式插入
//		List<Article> sampleEntities = Arrays.asList( article10);
//		articleSearchRepository.save(sampleEntities);
//
//	}
//
//
//
//	/*
//	 *获取所有测试
//	 * curl '192.168.0.91:9200/article_index/article/_search?q=*&pretty'
//	 */
//	@Test
//	public void testFetchAll(){
//		for (Article article : articleSearchRepository.findAll()) {
//			System.out.println(article.toString());
//		}
//	}

}
